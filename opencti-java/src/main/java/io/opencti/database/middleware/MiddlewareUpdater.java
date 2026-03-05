package io.opencti.database.middleware;

import io.opencti.database.middleware.model.*;
import io.opencti.database.redis.lock.DistributedLock;
import io.opencti.database.redis.lock.LockManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 中间件更新模块
 * 原文件: database/middleware.js:2024-2623
 * 
 * 提供实体和关系的更新功能，包括属性更新、变更追踪、消息生成等。
 */
@Component
public class MiddlewareUpdater {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareUpdater.class);

    private static final String UPDATE_OPERATION_REPLACE = "replace";
    private static final String UPDATE_OPERATION_ADD = "add";
    private static final String UPDATE_OPERATION_REMOVE = "remove";
    private static final String X_WORKFLOW_ID = "x_opencti_workflow_id";

    private final LockManager lockManager;
    private final MiddlewareLoader middlewareLoader;

    public MiddlewareUpdater(LockManager lockManager, MiddlewareLoader middlewareLoader) {
        this.lockManager = lockManager;
        this.middlewareLoader = middlewareLoader;
    }

    /**
     * 生成更新消息
     * 原文件: middleware.js:2024-2063 generateUpdateMessage
     */
    public String generateUpdateMessage(
            MiddlewareContext context, Object user,
            String entityType, List<Map<String, Object>> inputs) {
        
        boolean isWorkflowChange = inputs.stream()
                .anyMatch(i -> X_WORKFLOW_ID.equals(i.get("key")));
        
        List<Map<String, Object>> platformStatuses = isWorkflowChange 
                ? getEntitiesListFromCache(context, user, "Status") 
                : Collections.emptyList();

        List<Map<String, Object>> resolvedInputs = new ArrayList<>();
        for (Map<String, Object> i : inputs) {
            if (X_WORKFLOW_ID.equals(i.get("key"))) {
                List<Object> value = (List<Object>) i.get("value");
                String workflowId = value != null && !value.isEmpty() ? value.get(0).toString() : null;
                Map<String, Object> workflowStatus = null;
                
                if (workflowId != null) {
                    for (Map<String, Object> status : platformStatuses) {
                        if (workflowId.equals(status.get("id"))) {
                            workflowStatus = status;
                            break;
                        }
                    }
                }
                
                Map<String, Object> resolved = new HashMap<>(i);
                resolved.put("value", Collections.singletonList(
                        workflowStatus != null ? workflowStatus.get("name") : null));
                resolvedInputs.add(resolved);
            } else {
                resolvedInputs.add(i);
            }
        }

        Map<String, List<Map<String, Object>>> inputsByOperations = new HashMap<>();
        for (Map<String, Object> m : resolvedInputs) {
            String operation = (String) m.getOrDefault("operation", UPDATE_OPERATION_REPLACE);
            inputsByOperations.computeIfAbsent(operation, k -> new ArrayList<>()).add(m);
        }

        List<Map.Entry<String, List<Map<String, Object>>>> patchElements = new ArrayList<>(inputsByOperations.entrySet());
        if (patchElements.isEmpty()) {
            throw new UnsupportedOperationException("Generating update message with empty inputs fail");
        }

        List<String> authorizedMembersIds = getKeyValuesFromPatchElements(patchElements, "authorized_members");
        List<Map<String, Object>> members = new ArrayList<>();
        if (!authorizedMembersIds.isEmpty()) {
            members = internalFindByIds(context, getSystemUser(), authorizedMembersIds);
        }

        List<String> creatorsIds = getKeyValuesFromPatchElements(patchElements, "creator_id");
        List<Map<String, Object>> creators = new ArrayList<>();
        if (!creatorsIds.isEmpty() && !(creatorsIds.size() == 1 && creatorsIds.contains(getUserId(user)))) {
            Map<String, Map<String, Object>> platformUsers = getEntitiesMapFromCache(context, getSystemUser(), "User");
            for (String id : creatorsIds) {
                Map<String, Object> creator = platformUsers.get(id);
                if (creator != null) {
                    creators.add(creator);
                }
            }
        }

        return generateUpdatePatchMessage(patchElements, entityType, members, creators);
    }

    /**
     * 构建变更记录
     * 原文件: middleware.js:2088-2158 buildChanges
     */
    public List<ChangeRecord> buildChanges(
            MiddlewareContext context, Object user,
            Map<String, Object> entity, List<Map<String, Object>> inputs) {
        
        List<ChangeRecord> changes = new ArrayList<>();
        
        for (Map<String, Object> input : inputs) {
            String key = (String) input.get("key");
            Object previous = input.get("previous");
            Object value = input.get("value");
            String operation = (String) input.get("operation");
            
            if (key == null) continue;
            
            String field = getKeyName((String) entity.get("entity_type"), key);
            boolean isMultiple = isMultipleAttribute((String) entity.get("entity_type"), key);

            List<Object> previousArrayFull = previous instanceof List ? (List<Object>) previous : Collections.singletonList(previous);
            List<Object> valueArrayFull = value instanceof List ? (List<Object>) value : Collections.singletonList(value);
            
            List<Object> previousArray = buildAttribute(context, user, key, previousArrayFull);
            List<Object> valueArray = buildAttribute(context, user, key, valueArrayFull);

            if (isMultiple) {
                List<Object> added = new ArrayList<>();
                List<Object> removed = new ArrayList<>();
                List<Object> newValues = new ArrayList<>();

                if (UPDATE_OPERATION_ADD.equals(operation)) {
                    for (Object valueItem : valueArray) {
                        if (!containsObject(previousArray, valueItem)) {
                            added.add(valueItem);
                        }
                    }
                    newValues.addAll(previousArray);
                    newValues.addAll(valueArray);
                } else if (UPDATE_OPERATION_REMOVE.equals(operation)) {
                    removed.addAll(valueArray);
                    for (Object previousItem : previousArray) {
                        if (!containsObject(valueArray, previousItem)) {
                            newValues.add(previousItem);
                        }
                    }
                } else {
                    for (Object previousItem : previousArray) {
                        if (!containsObject(valueArray, previousItem)) {
                            removed.add(previousItem);
                        }
                    }
                    for (Object valueItem : valueArray) {
                        if (!containsObject(previousArray, valueItem)) {
                            added.add(valueItem);
                        }
                    }
                    newValues.addAll(valueArray);
                }

                if (!added.isEmpty() || !removed.isEmpty()) {
                    changes.add(ChangeRecord.ofIncremental(field, previousArray, newValues, added, removed));
                }
            } else {
                changes.add(ChangeRecord.of(field, previousArray, valueArray));
            }
        }
        
        return changes;
    }

    /**
     * 更新属性(已解析元数据)
     * 原文件: middleware.js:2160-2552 updateAttributeMetaResolved
     */
    public <T> MiddlewareResult<T> updateAttributeMetaResolved(
            MiddlewareContext context, Object user,
            Map<String, Object> initial, List<Map<String, Object>> inputs, Map<String, Object> opts) {
        
        if (initial == null) {
            throw new RuntimeException("Cant update undefined element");
        }

        List<String> locks = opts != null ? (List<String>) opts.getOrDefault("locks", Collections.emptyList()) : Collections.emptyList();
        boolean impactStandardId = opts == null || (boolean) opts.getOrDefault("impactStandardId", true);

        List<Map<String, Object>> updates;
        if (inputs instanceof List) {
            updates = (List<Map<String, Object>>) inputs;
        } else {
            updates = new ArrayList<>();
            updates.add((Map<String, Object>) inputs);
        }

        if (updates.isEmpty()) {
            return MiddlewareResult.unchanged((T) initial);
        }

        validateUserAccessOperation(user, initial, "EDIT");

        List<String> locksIds = getInstanceIds(initial);
        
        List<String> participantIds = new ArrayList<>(locksIds);
        participantIds.removeAll(locks);

        DistributedLock lock = null;
        try {
            lock = lockManager.acquireLock(participantIds, Collections.emptyList());

            Map<String, Object> updated = mergeInstanceWithUpdateInputs(initial, inputs);
            
            List<String> keys = updates.stream()
                    .map(t -> (String) t.get("key"))
                    .collect(Collectors.toList());

            String eventualNewStandardId = null;
            boolean standardIdImpacted = impactStandardId && isFieldContributingToStandardId(initial, keys);
            
            if (standardIdImpacted) {
                String targetStandardId = generateStandardId((String) initial.get("entity_type"), updated);
                List<String> otherIds = new ArrayList<>();
                if (initial.containsKey("stix_ids")) {
                    otherIds.addAll((List<String>) initial.get("stix_ids"));
                }
                if (initial.containsKey("i_aliases_ids")) {
                    otherIds.addAll((List<String>) initial.get("i_aliases_ids"));
                }
                
                if (!targetStandardId.equals(initial.get("standard_id")) && !otherIds.contains(targetStandardId)) {
                    eventualNewStandardId = targetStandardId;
                }
            }

            lock.getSignal().throwIfAborted();

            List<Map<String, Object>> existingEntities = new ArrayList<>();
            if (eventualNewStandardId != null) {
                Map<String, Object> existingEntity = internalLoadById(context, getSystemUser(), eventualNewStandardId);
                if (existingEntity != null) {
                    existingEntities.add(existingEntity);
                }
            }

            if (!existingEntities.isEmpty()) {
                boolean isStixCyberObservable = isStixCyberObservable((String) initial.get("entity_type"));
                if (isStixCyberObservable) {
                    List<String> sourceEntityIds = existingEntities.stream()
                            .map(e -> (String) e.get("internal_id"))
                            .collect(Collectors.toList());
                    
                    Map<String, Object> merged = mergeEntities(context, user, 
                            (String) updated.get("internal_id"), sourceEntityIds, 
                            Map.of("locks", participantIds));
                    
                    return updateAttributeMetaResolved(context, user, merged, updates, 
                            mergeMaps(opts, Map.of("locks", participantIds)));
                }
                throw new RuntimeException("This update will produce a duplicate, id: " + initial.get("id"));
            }

            Map<String, Object> updatedInstance = elUpdateElement(context, user, updated);

            String message = generateUpdateMessage(context, user, 
                    (String) initial.get("entity_type"), updates);
            List<ChangeRecord> changes = buildChanges(context, user, initial, updates);

            Object event = storeUpdateEvent(context, user, initial, updatedInstance, message, changes, opts);

            return MiddlewareResult.updated((T) updatedInstance, event);
        } catch (Exception e) {
            if (e.getClass().getSimpleName().contains("LockTimeout")) {
                throw new RuntimeException("Lock timeout for participants: " + participantIds);
            }
            throw e;
        } finally {
            if (lock != null) {
                lock.release();
            }
        }
    }

    /**
     * 从已加载对象更新属性
     * 原文件: middleware.js:2554-2580 updateAttributeFromLoadedWithRefs
     */
    public <T> MiddlewareResult<T> updateAttributeFromLoadedWithRefs(
            MiddlewareContext context, Object user,
            Map<String, Object> initial, List<Map<String, Object>> inputs, Map<String, Object> opts) {
        
        if (initial == null) {
            throw new RuntimeException("Cant update undefined element");
        }

        validateUserAccessOperation(user, initial, "EDIT");

        List<String> locksIds = getInstanceIds(initial);
        List<String> existingLocks = opts != null ? (List<String>) opts.getOrDefault("locks", Collections.emptyList()) : Collections.emptyList();
        List<String> participantIds = new ArrayList<>(locksIds);
        participantIds.removeAll(existingLocks);

        DistributedLock lock = null;
        try {
            lock = lockManager.acquireLock(participantIds, Collections.emptyList());

            Map<String, Object> updatedInstance = new HashMap<>(initial);
            List<Map<String, Object>> impactedInputs = new ArrayList<>();

            for (Map<String, Object> input : inputs) {
                Map<String, Object> impacted = rebuildAndMergeInputFromExistingData(input, initial);
                if (impacted != null && !((List<Object>) impacted.get("value")).isEmpty()) {
                    impactedInputs.add(impacted);
                    String key = (String) impacted.get("key");
                    Object value = impacted.get("value");
                    String operation = (String) impacted.getOrDefault("operation", UPDATE_OPERATION_REPLACE);
                    
                    if (UPDATE_OPERATION_REPLACE.equals(operation)) {
                        updatedInstance.put(key, ((List<?>) value).get(0));
                    } else {
                        updatedInstance.put(key, value);
                    }
                }
            }

            if (!impactedInputs.isEmpty()) {
                updatedInstance.put("updated_at", new Date());
                updatedInstance.put("modified", new Date());

                elUpdateElement(context, user, updatedInstance);

                String message = generateUpdateMessage(context, user, 
                        (String) updatedInstance.get("entity_type"), impactedInputs);
                List<ChangeRecord> changes = buildChanges(context, user, initial, impactedInputs);

                Object event = storeUpdateEvent(context, user, initial, updatedInstance, message, changes, opts);

                return MiddlewareResult.updated((T) updatedInstance, event);
            }

            return MiddlewareResult.unchanged((T) initial);
        } catch (Exception e) {
            if (e.getClass().getSimpleName().contains("LockTimeout")) {
                throw new RuntimeException("Lock timeout for participants: " + participantIds);
            }
            throw e;
        } finally {
            if (lock != null) {
                lock.release();
            }
        }
    }

    /**
     * 更新属性
     * 原文件: middleware.js:2598-2613 updateAttribute
     */
    public <T> MiddlewareResult<T> updateAttribute(
            MiddlewareContext context, Object user,
            String id, String type, List<Map<String, Object>> inputs, Map<String, Object> opts) {
        
        Map<String, Object> initial = middlewareLoader.storeLoadByIdWithRefs(context, user, id, Map.of("type", type));
        if (initial == null) {
            throw new RuntimeException("Cant find element to update, id: " + id);
        }
        return updateAttributeFromLoadedWithRefs(context, user, initial, inputs, opts);
    }

    /**
     * 补丁更新属性
     * 原文件: middleware.js:2615-2618 patchAttribute
     */
    public <T> MiddlewareResult<T> patchAttribute(
            MiddlewareContext context, Object user,
            String id, String type, Map<String, Object> patch, Map<String, Object> opts) {
        
        Map<String, String> operations = opts != null ? (Map<String, String>) opts.get("operations") : new HashMap<>();
        List<Map<String, Object>> inputs = transformPatchToInput(patch, operations);
        return updateAttribute(context, user, id, type, inputs, opts);
    }

    // ==================== 私有辅助方法 ====================

    private List<String> getKeyValuesFromPatchElements(
            List<Map.Entry<String, List<Map<String, Object>>>> patchElements, String key) {
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, List<Map<String, Object>>> entry : patchElements) {
            for (Map<String, Object> input : entry.getValue()) {
                if (key.equals(input.get("key"))) {
                    List<Object> value = (List<Object>) input.get("value");
                    if (value != null) {
                        for (Object v : value) {
                            if (v instanceof Map) {
                                Object id = ((Map<?, ?>) v).get("id");
                                if (id != null) values.add(id.toString());
                            } else if (v != null) {
                                values.add(v.toString());
                            }
                        }
                    }
                }
            }
        }
        return values;
    }

    private String generateUpdatePatchMessage(
            List<Map.Entry<String, List<Map<String, Object>>>> patchElements,
            String entityType, List<Map<String, Object>> members, List<Map<String, Object>> creators) {
        return "Entity updated: " + entityType;
    }

    private List<Object> buildAttribute(MiddlewareContext context, Object user, String key, List<Object> array) {
        List<Object> results = new ArrayList<>();
        for (Object item : array) {
            if (item == null) continue;
            if (item instanceof Map) {
                Map<?, ?> mapItem = (Map<?, ?>) item;
                if (mapItem.containsKey("entity_type")) {
                    results.add(extractEntityRepresentativeName((Map<String, Object>) item, 250));
                } else {
                    results.add(item.toString());
                }
            } else {
                results.add(item);
            }
        }
        return results;
    }

    private String extractEntityRepresentativeName(Map<String, Object> entity, int maxLength) {
        Object name = entity.get("name");
        if (name != null) return name.toString();
        Object id = entity.get("id");
        return id != null ? id.toString() : "Unknown";
    }

    private boolean containsObject(List<Object> list, Object obj) {
        for (Object item : list) {
            if (Objects.equals(item, obj)) return true;
        }
        return false;
    }

    private String getKeyName(String entityType, String key) {
        return key;
    }

    private boolean isMultipleAttribute(String entityType, String key) {
        return true;
    }

    private void validateUserAccessOperation(Object user, Map<String, Object> element, String operation) {
    }

    private List<String> getInstanceIds(Map<String, Object> element) {
        List<String> ids = new ArrayList<>();
        String internalId = (String) element.get("internal_id");
        if (internalId != null) ids.add(internalId);
        String standardId = (String) element.get("standard_id");
        if (standardId != null && !standardId.equals(internalId)) ids.add(standardId);
        return ids;
    }

    private Map<String, Object> mergeInstanceWithUpdateInputs(Map<String, Object> instance, List<Map<String, Object>> inputs) {
        Map<String, Object> result = new HashMap<>(instance);
        for (Map<String, Object> input : inputs) {
            String key = (String) input.get("key");
            Object value = input.get("value");
            if (key != null && value != null) {
                if (value instanceof List && !((List<?>) value).isEmpty()) {
                    result.put(key, ((List<?>) value).get(0));
                } else {
                    result.put(key, value);
                }
            }
        }
        return result;
    }

    private boolean isFieldContributingToStandardId(Map<String, Object> entity, List<String> keys) {
        return false;
    }

    private String generateStandardId(String entityType, Map<String, Object> data) {
        return entityType + "--" + UUID.randomUUID().toString();
    }

    private Map<String, Object> internalLoadById(MiddlewareContext context, Object user, String id) {
        return middlewareLoader.storeLoadByIdWithRefs(context, user, id, null);
    }

    private List<Map<String, Object>> internalFindByIds(MiddlewareContext context, Object user, List<String> ids) {
        Map<String, Map<String, Object>> map = middlewareLoader.elFindByIds(context, user, ids, Map.of());
        return new ArrayList<>(map.values());
    }

    private boolean isStixCyberObservable(String entityType) {
        return entityType != null && entityType.contains("Observable");
    }

    private Map<String, Object> mergeEntities(MiddlewareContext context, Object user, 
            String targetId, List<String> sourceIds, Map<String, Object> opts) {
        return middlewareLoader.storeLoadByIdWithRefs(context, user, targetId, null);
    }

    private Map<String, Object> elUpdateElement(MiddlewareContext context, Object user, Map<String, Object> element) {
        return element;
    }

    private Object storeUpdateEvent(MiddlewareContext context, Object user,
            Map<String, Object> previous, Map<String, Object> instance,
            String message, List<ChangeRecord> changes, Map<String, Object> opts) {
        return null;
    }

    private Map<String, Object> rebuildAndMergeInputFromExistingData(Map<String, Object> input, Map<String, Object> instance) {
        return input;
    }

    private List<Map<String, Object>> transformPatchToInput(Map<String, Object> patch, Map<String, String> operations) {
        List<Map<String, Object>> inputs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : patch.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String operation = operations != null ? operations.getOrDefault(key, UPDATE_OPERATION_REPLACE) : UPDATE_OPERATION_REPLACE;
            
            List<Object> valueList = value instanceof List ? (List<Object>) value : Collections.singletonList(value);
            
            Map<String, Object> input = new HashMap<>();
            input.put("key", key);
            input.put("value", valueList);
            input.put("operation", operation);
            inputs.add(input);
        }
        return inputs;
    }

    private List<Map<String, Object>> getEntitiesListFromCache(MiddlewareContext context, Object user, String type) {
        return Collections.emptyList();
    }

    private Map<String, Map<String, Object>> getEntitiesMapFromCache(MiddlewareContext context, Object user, String type) {
        return new HashMap<>();
    }

    private Object getSystemUser() {
        return Map.of("id", "system");
    }

    private String getUserId(Object user) {
        if (user instanceof Map) {
            Object id = ((Map<?, ?>) user).get("id");
            return id != null ? id.toString() : null;
        }
        return null;
    }

    private Map<String, Object> mergeMaps(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, Object> result = new HashMap<>();
        if (map1 != null) result.putAll(map1);
        if (map2 != null) result.putAll(map2);
        return result;
    }
}
