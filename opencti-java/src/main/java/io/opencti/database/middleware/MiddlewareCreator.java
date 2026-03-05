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
 * 中间件创建模块
 * 原文件: database/middleware.js:2866-3416
 * 
 * 提供实体和关系的创建功能，包括Upsert逻辑、推理规则支持等。
 */
@Component
public class MiddlewareCreator {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareCreator.class);

    private final LockManager lockManager;
    private final MiddlewareLoader middlewareLoader;
    private final MiddlewareInputResolver inputResolver;
    private final MiddlewareUpdater middlewareUpdater;

    public MiddlewareCreator(LockManager lockManager, MiddlewareLoader middlewareLoader,
            MiddlewareInputResolver inputResolver, MiddlewareUpdater middlewareUpdater) {
        this.lockManager = lockManager;
        this.middlewareLoader = middlewareLoader;
        this.inputResolver = inputResolver;
        this.middlewareUpdater = middlewareUpdater;
    }

    /**
     * 获取已存在的关系
     * 原文件: middleware.js:2866-2917 getExistingRelations
     */
    public List<Map<String, Object>> getExistingRelations(
            MiddlewareContext context, Object user,
            Map<String, Object> input, Map<String, Object> opts) {
        
        Map<String, Object> from = (Map<String, Object>) input.get("from");
        Map<String, Object> to = (Map<String, Object>) input.get("to");
        String relationshipType = (String) input.get("relationship_type");
        String fromRule = opts != null ? (String) opts.get("fromRule") : null;

        List<Map<String, Object>> existingRelationships = new ArrayList<>();

        if (fromRule != null) {
            Map<String, Object> fromRuleArgs = new HashMap<>();
            fromRuleArgs.put("fromId", from.get("internal_id"));
            fromRuleArgs.put("toId", to.get("internal_id"));
            fromRuleArgs.put("indices", Collections.singletonList("read_index_inferred_relationships"));

            List<Map<String, Object>> inferredRelationships = topRelationsList(context, 
                    getSystemUser(), relationshipType, fromRuleArgs);
            existingRelationships.addAll(inferredRelationships);
        } else {
            List<Map<String, Object>> deduplicationFilters = buildRelationDeduplicationFilters(input);
            
            Map<String, Object> searchFilters = new HashMap<>();
            searchFilters.put("mode", "or");
            
            Map<String, Object> idsFilter = new HashMap<>();
            idsFilter.put("key", "ids");
            idsFilter.put("values", getInputIds(relationshipType, input, false));
            searchFilters.put("filters", Collections.singletonList(idsFilter));
            
            List<Map<String, Object>> filterGroupFilters = new ArrayList<>();
            
            Map<String, Object> fromFilter = new HashMap<>();
            fromFilter.put("key", Collections.singletonList("connections"));
            fromFilter.put("nested", Arrays.asList(
                    Map.of("key", "internal_id", "values", Collections.singletonList(from.get("internal_id"))),
                    Map.of("key", "role", "values", Collections.singletonList("*_from"))));
            fromFilter.put("values", Collections.emptyList());
            filterGroupFilters.add(fromFilter);
            
            Map<String, Object> toFilter = new HashMap<>();
            toFilter.put("key", Collections.singletonList("connections"));
            toFilter.put("nested", Arrays.asList(
                    Map.of("key", "internal_id", "values", Collections.singletonList(to.get("internal_id"))),
                    Map.of("key", "role", "values", Collections.singletonList("*_to"))));
            toFilter.put("values", Collections.emptyList());
            filterGroupFilters.add(toFilter);
            
            filterGroupFilters.addAll(deduplicationFilters);

            Map<String, Object> filterGroup = new HashMap<>();
            filterGroup.put("mode", "and");
            filterGroup.put("filters", filterGroupFilters);
            filterGroup.put("filterGroups", Collections.emptyList());
            searchFilters.put("filterGroups", Collections.singletonList(filterGroup));

            Map<String, Object> manualArgs = new HashMap<>();
            manualArgs.put("indices", Collections.singletonList("read_relationships_indices_without_inferred"));
            manualArgs.put("filters", searchFilters);

            List<Map<String, Object>> manualRelationships = topRelationsList(context, 
                    getSystemUser(), relationshipType, manualArgs);
            existingRelationships.addAll(manualRelationships);
        }

        return existingRelationships;
    }

    /**
     * 创建关系(原始)
     * 原文件: middleware.js:2919-3110 createRelationRaw
     */
    public <T> MiddlewareResult<T> createRelationRaw(
            MiddlewareContext context, Object user,
            Map<String, Object> rawInput, Map<String, Object> opts) {
        
        String fromRule = opts != null ? (String) opts.get("fromRule") : null;
        List<String> locks = opts != null ? (List<String>) opts.getOrDefault("locks", Collections.emptyList()) : Collections.emptyList();
        String fromId = (String) rawInput.get("fromId");
        String toId = (String) rawInput.get("toId");
        String relationshipType = (String) rawInput.get("relationship_type");

        Map<String, Object> input = new HashMap<>(rawInput);
        Integer confidenceLevelToApply = controlCreateInputWithUserConfidence(user, input, relationshipType);
        input.put("confidence", confidenceLevelToApply);

        if (fromId != null && fromId.equals(toId)) {
            throw new UnsupportedOperationException(
                    "Relation cant be created with the same source and target");
        }

        Map<String, Object> resolvedInput = inputResolver.inputResolveRefs(context, user, input, relationshipType, null);
        Map<String, Object> from = (Map<String, Object>) resolvedInput.get("from");
        Map<String, Object> to = (Map<String, Object>) resolvedInput.get("to");

        validateUserAccessOperation(user, from, "EDIT");
        validateUserAccessOperation(user, to, "EDIT");

        checkRelationConsistency(context, user, relationshipType, from, to);

        if (from.get("internal_id").equals(to.get("internal_id"))) {
            throw new UnsupportedOperationException(
                    "Relation cant be created with the same source and target");
        }

        List<String> inputIds = getInputIds(relationshipType, resolvedInput, fromRule);
        List<String> participantIds = new ArrayList<>(inputIds);
        participantIds.removeAll(locks);

        DistributedLock lock = null;
        try {
            lock = lockManager.acquireLock(participantIds, Collections.emptyList());

            List<Map<String, Object>> existingRelationships = getExistingRelations(context, user, resolvedInput, opts);
            Map<String, Object> existingRelationship = null;

            if (!existingRelationships.isEmpty()) {
                List<Map<String, Object>> filteredRelations = userFilterStoreElements(context, user, existingRelationships);
                if (filteredRelations.isEmpty()) {
                    throw new UnsupportedOperationException("Restricted relation already exists");
                }
                existingRelationship = middlewareLoader.storeLoadByIdWithRefs(context, user,
                        (String) filteredRelations.get(0).get("internal_id"), null);
            }

            if (existingRelationship != null) {
                if (fromRule != null) {
                    return upsertRelationRule(context, user, existingRelationship, input, 
                            mergeMaps(opts, Map.of("locks", participantIds)));
                }
                return upsertElement(context, user, existingRelationship, relationshipType, resolvedInput,
                        mergeMaps(opts, Map.of("locks", participantIds, "elementAlreadyResolved", true)));
            }

            BuildDataResult dataRel = buildRelationData(context, user, resolvedInput, opts);
            lock.getSignal().throwIfAborted();
            indexCreatedElement(context, user, dataRel);

            Map<String, Object> relationData = mergeMaps(resolvedInput, dataRel.getElement());
            Object event = storeCreateRelationEvent(context, user, relationData, opts);

            return MiddlewareResult.created((T) relationData, event);
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
     * 创建关系
     * 原文件: middleware.js:3111-3114 createRelation
     */
    public <T> T createRelation(
            MiddlewareContext context, Object user,
            Map<String, Object> input, Map<String, Object> opts) {
        MiddlewareResult<T> result = createRelationRaw(context, user, input, opts);
        return result.getElement();
    }

    /**
     * 创建推理关系
     * 原文件: middleware.js:3115-3139 createInferredRelation
     */
    public <T> MiddlewareResult<T> createInferredRelation(
            MiddlewareContext context, Object user,
            Map<String, Object> input, Map<String, Object> opts) {
        Map<String, Object> inferredOpts = new HashMap<>(opts != null ? opts : new HashMap<>());
        inferredOpts.put("fromRule", input.get("rule"));
        return createRelationRaw(context, user, input, inferredOpts);
    }

    /**
     * 批量创建关系
     * 原文件: middleware.js:3141-3150 createRelations
     */
    public <T> List<T> createRelations(
            MiddlewareContext context, Object user,
            List<Map<String, Object>> inputs, Map<String, Object> opts) {
        List<T> createdRelations = new ArrayList<>();
        for (Map<String, Object> input : inputs) {
            T relation = createRelation(context, user, input, opts);
            createdRelations.add(relation);
        }
        return createdRelations;
    }

    /**
     * 获取已存在的实体
     * 原文件: middleware.js:3155-3166 getExistingEntities
     */
    public List<Map<String, Object>> getExistingEntities(
            MiddlewareContext context, Object user,
            Map<String, Object> input, String type) {
        
        String standardId = (String) input.get("standard_id");
        List<String> stixIds = (List<String>) input.get("stix_ids");
        List<String> aliasesIds = (List<String>) input.get("i_aliases_ids");

        List<String> idsToSearch = new ArrayList<>();
        if (standardId != null) {
            idsToSearch.add(standardId);
        }
        if (stixIds != null) {
            idsToSearch.addAll(stixIds);
        }
        if (aliasesIds != null) {
            idsToSearch.addAll(aliasesIds);
        }

        if (idsToSearch.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Map<String, Object>> foundElements = middlewareLoader.elFindByIds(context, 
                getSystemUser(), idsToSearch, Map.of("type", Collections.singletonList(type)));
        return new ArrayList<>(foundElements.values());
    }

    /**
     * 创建实体(原始)
     * 原文件: middleware.js:3168-3388 createEntityRaw
     */
    public <T> MiddlewareResult<T> createEntityRaw(
            MiddlewareContext context, Object user,
            Map<String, Object> rawInput, String type, Map<String, Object> opts) {
        
        Map<String, Object> input = new HashMap<>(rawInput);
        Integer confidenceLevelToApply = controlCreateInputWithUserConfidence(user, input, type);
        input.put("confidence", confidenceLevelToApply);

        validateUserAccessOperation(user, input, "EDIT");

        Map<String, Object> resolvedInput = inputResolver.inputResolveRefs(context, user, input, type, null);
        List<String> participantIds = getInputIds(type, resolvedInput, opts != null ? (String) opts.get("fromRule") : null);

        DistributedLock lock = null;
        try {
            lock = lockManager.acquireLock(participantIds, 
                    opts != null ? (List<String>) opts.getOrDefault("locks", Collections.emptyList()) : Collections.emptyList());

            String standardId = (String) resolvedInput.get("standard_id");
            if (standardId == null) {
                standardId = generateStandardId(type, resolvedInput);
            }

            List<Map<String, Object>> existingEntities = getExistingEntities(context, user, resolvedInput, type);

            if (existingEntities.isEmpty()) {
                BuildDataResult dataEntity = buildEntityData(context, user, resolvedInput, type, opts);
                lock.getSignal().throwIfAborted();
                indexCreatedElement(context, user, dataEntity);

                Map<String, Object> createdElement = mergeMaps(resolvedInput, dataEntity.getElement());
                Object event = storeCreateEntityEvent(context, user, createdElement, 
                        generateCreateMessage(createdElement), opts);

                return MiddlewareResult.created((T) createdElement, event);
            }

            List<Map<String, Object>> filteredEntities = userFilterStoreElements(context, user, existingEntities);
            if (filteredEntities.isEmpty()) {
                throw new UnsupportedOperationException("Restricted entity already exists");
            }

            if (filteredEntities.size() == 1) {
                Map<String, Object> element = middlewareLoader.storeLoadByIdWithRefs(context, user,
                        (String) filteredEntities.get(0).get("internal_id"), Map.of("type", type));
                return upsertElement(context, user, element, type, resolvedInput, opts);
            }

            return MiddlewareResult.unchanged((T) filteredEntities.get(0));
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
     * 创建实体
     * 原文件: middleware.js:3390-3401 createEntity
     */
    public <T> MiddlewareResult<T> createEntity(
            MiddlewareContext context, Object user,
            Map<String, Object> input, String type, Map<String, Object> opts) {
        MiddlewareResult<T> result = createEntityRaw(context, user, input, type, opts);
        if (result.isCreation()) {
            triggerCreateEntityAutoEnrichment(context, user, result.getElement());
        } else if (result.getEvent() != null) {
            triggerEntityUpdateAutoEnrichment(context, user, result.getElement());
        }
        return result;
    }

    /**
     * 创建推理实体
     * 原文件: middleware.js:3403-3416 createInferredEntity
     */
    public <T> MiddlewareResult<T> createInferredEntity(
            MiddlewareContext context, Object user,
            Map<String, Object> input, String type, Map<String, Object> opts) {
        Map<String, Object> inferredOpts = new HashMap<>(opts != null ? opts : new HashMap<>());
        inferredOpts.put("fromRule", input.get("rule"));
        return createEntityRaw(context, user, input, type, inferredOpts);
    }

    // ==================== 私有辅助方法 ====================

    private List<Map<String, Object>> topRelationsList(MiddlewareContext context, Object user,
            String relationshipType, Map<String, Object> args) {
        return middlewareLoader.topEntitiesOrRelationsList(context, user,
                Collections.singletonList(relationshipType), args);
    }

    private List<Map<String, Object>> buildRelationDeduplicationFilters(Map<String, Object> input) {
        return Collections.emptyList();
    }

    private List<String> getInputIds(String type, Map<String, Object> input, Object fromRule) {
        List<String> ids = new ArrayList<>();
        String internalId = (String) input.get("internal_id");
        if (internalId != null) ids.add(internalId);
        String standardId = (String) input.get("standard_id");
        if (standardId != null) ids.add(standardId);
        return ids;
    }

    private Integer controlCreateInputWithUserConfidence(Object user, Map<String, Object> input, String type) {
        return 100;
    }

    private void validateUserAccessOperation(Object user, Object element, String operation) {
    }

    private void checkRelationConsistency(MiddlewareContext context, Object user,
            String relationshipType, Map<String, Object> from, Map<String, Object> to) {
    }

    private List<Map<String, Object>> userFilterStoreElements(MiddlewareContext context, Object user,
            List<Map<String, Object>> elements) {
        return elements;
    }

    private <T> MiddlewareResult<T> upsertRelationRule(MiddlewareContext context, Object user,
            Map<String, Object> instance, Map<String, Object> input, Map<String, Object> opts) {
        return MiddlewareResult.unchanged((T) instance);
    }

    private <T> MiddlewareResult<T> upsertElement(MiddlewareContext context, Object user,
            Object element, String type, Map<String, Object> basePatch, Map<String, Object> opts) {
        Map<String, Object> resolvedElement = element instanceof Map ? (Map<String, Object>) element : 
                middlewareLoader.storeLoadByIdWithRefs(context, user, element.toString(), Map.of("type", type));
        
        if (resolvedElement == null) {
            throw new RuntimeException("Cant find element to resolve");
        }

        List<Map<String, Object>> inputs = generateInputsForUpsert(context, user, resolvedElement, type, basePatch);
        if (inputs.isEmpty()) {
            return MiddlewareResult.unchanged((T) resolvedElement);
        }

        Map<String, Object> updateOpts = new HashMap<>();
        updateOpts.put("upsert", true);
        return middlewareUpdater.updateAttributeFromLoadedWithRefs(context, user, resolvedElement, inputs, updateOpts);
    }

    private BuildDataResult buildRelationData(MiddlewareContext context, Object user,
            Map<String, Object> input, Map<String, Object> opts) {
        BuildDataResult result = new BuildDataResult();
        result.setElement(input);
        return result;
    }

    private BuildDataResult buildEntityData(MiddlewareContext context, Object user,
            Map<String, Object> input, String type, Map<String, Object> opts) {
        BuildDataResult result = new BuildDataResult();
        result.setElement(input);
        return result;
    }

    private void indexCreatedElement(MiddlewareContext context, Object user, BuildDataResult data) {
    }

    private Object storeCreateRelationEvent(MiddlewareContext context, Object user,
            Map<String, Object> relation, Map<String, Object> opts) {
        return null;
    }

    private Object storeCreateEntityEvent(MiddlewareContext context, Object user,
            Map<String, Object> entity, String message, Map<String, Object> opts) {
        return null;
    }

    private String generateCreateMessage(Map<String, Object> element) {
        return "Entity created";
    }

    private String generateStandardId(String type, Map<String, Object> data) {
        return type + "--" + UUID.randomUUID().toString();
    }

    private List<Map<String, Object>> generateInputsForUpsert(MiddlewareContext context, Object user,
            Map<String, Object> element, String type, Map<String, Object> patch) {
        return new ArrayList<>();
    }

    private void triggerCreateEntityAutoEnrichment(MiddlewareContext context, Object user, Object element) {
    }

    private void triggerEntityUpdateAutoEnrichment(MiddlewareContext context, Object user, Object element) {
    }

    private Object getSystemUser() {
        return Map.of("id", "system");
    }

    private Map<String, Object> mergeMaps(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, Object> result = new HashMap<>();
        if (map1 != null) result.putAll(map1);
        if (map2 != null) result.putAll(map2);
        return result;
    }
}
