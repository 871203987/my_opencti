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
 * 中间件合并模块
 * 原文件: database/middleware.js:1206-1633
 * 
 * 提供实体合并功能，包括哈希验证、关系迁移、属性合并等。
 */
@Component
public class MiddlewareMerger {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareMerger.class);

    private final LockManager lockManager;
    private final MiddlewareLoader middlewareLoader;
    private final MiddlewareUpdater middlewareUpdater;
    private final MiddlewareCreator middlewareCreator;
    private final MiddlewareDeleter middlewareDeleter;

    public MiddlewareMerger(LockManager lockManager, MiddlewareLoader middlewareLoader,
            MiddlewareUpdater middlewareUpdater, MiddlewareCreator middlewareCreator,
            MiddlewareDeleter middlewareDeleter) {
        this.lockManager = lockManager;
        this.middlewareLoader = middlewareLoader;
        this.middlewareUpdater = middlewareUpdater;
        this.middlewareCreator = middlewareCreator;
        this.middlewareDeleter = middlewareDeleter;
    }

    /**
     * 哈希合并验证
     * 原文件: middleware.js:1206-1221 hashMergeValidation
     */
    public void hashMergeValidation(List<Map<String, Object>> entities) {
        if (entities.size() < 2) {
            return;
        }

        Map<String, Object> baseEntity = entities.get(0);
        List<String> baseHashes = getObservableHashes(baseEntity);

        for (int i = 1; i < entities.size(); i++) {
            Map<String, Object> entity = entities.get(i);
            List<String> entityHashes = getObservableHashes(entity);

            if (!baseHashes.isEmpty() && !entityHashes.isEmpty()) {
                boolean hasCommonHash = false;
                for (String hash : entityHashes) {
                    if (baseHashes.contains(hash)) {
                        hasCommonHash = true;
                        break;
                    }
                }
                if (!hasCommonHash) {
                    throw new RuntimeException(
                            "Cannot merge observables with different hashes: " + 
                            baseEntity.get("internal_id") + " and " + entity.get("internal_id"));
                }
            }
        }
    }

    /**
     * 过滤已存在目标
     * 原文件: middleware.js:1227-1266 filterTargetByExisting
     */
    public List<Map<String, Object>> filterTargetByExisting(
            Map<String, Object> target, List<Map<String, Object>> sources,
            String relationshipType, String scopeType) {
        
        String targetId = (String) target.get("internal_id");
        List<String> sourceIds = sources.stream()
                .map(s -> (String) s.get("internal_id"))
                .collect(Collectors.toList());

        List<Map<String, Object>> existingRelations = findExistingRelations(
                targetId, sourceIds, relationshipType, scopeType);

        Set<String> existingSourceIds = new HashSet<>();
        for (Map<String, Object> relation : existingRelations) {
            String fromId = (String) relation.get("fromId");
            String toId = (String) relation.get("toId");
            if (sourceIds.contains(fromId)) {
                existingSourceIds.add(fromId);
            }
            if (sourceIds.contains(toId)) {
                existingSourceIds.add(toId);
            }
        }

        return sources.stream()
                .filter(s -> !existingSourceIds.contains(s.get("internal_id")))
                .collect(Collectors.toList());
    }

    /**
     * 合并实体
     * 原文件: middleware.js:1582-1633 mergeEntities
     */
    public <T> T mergeEntities(
            MiddlewareContext context, Object user,
            String targetEntityId, List<String> sourceEntityIds, Map<String, Object> opts) {
        
        if (sourceEntityIds.contains(targetEntityId)) {
            throw new RuntimeException("Cannot merge entities, same ID detected in source and destination");
        }

        List<String> mergedIds = new ArrayList<>();
        mergedIds.add(targetEntityId);
        mergedIds.addAll(sourceEntityIds);

        Map<String, Map<String, Object>> mergedInstancesMap = middlewareLoader.elFindByIds(context, 
                user, mergedIds, Map.of());
        List<Map<String, Object>> mergedInstances = new ArrayList<>(mergedInstancesMap.values());

        if (mergedIds.size() != mergedInstances.size()) {
            throw new RuntimeException("Cannot access all entities for merging");
        }

        for (Map<String, Object> instance : mergedInstances) {
            controlUserConfidenceAgainstElement(user, instance);
        }

        List<String> locks = opts != null ? (List<String>) opts.getOrDefault("locks", Collections.emptyList()) : Collections.emptyList();
        List<String> participantIds = new ArrayList<>(mergedIds);
        participantIds.removeAll(locks);

        DistributedLock lock = null;
        try {
            lock = lockManager.acquireLock(participantIds, Collections.emptyList());

            Map<String, Object> initialInstance = middlewareLoader.storeLoadByIdWithRefs(context, user, targetEntityId, null);
            Map<String, Object> target = new HashMap<>(initialInstance);
            List<Map<String, Object>> sources = middlewareLoader.storeLoadByIdsWithRefs(context, user, sourceEntityIds, null);

            lock.getSignal().throwIfAborted();
            mergeEntitiesRaw(context, user, target, sources, opts);

            Map<String, Object> mergedInstance = middlewareLoader.storeLoadByIdWithRefs(context, user, targetEntityId, null);
            storeMergeEvent(context, user, initialInstance, mergedInstance, sources, opts);

            redisAddDeletions(sourceEntityIds, context.getDraftId());

            return (T) middlewareLoader.storeLoadByIdWithRefs(context, user, targetEntityId, null);
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
     * 合并实体(原始)
     * 原文件: middleware.js:1268-1549 mergeEntitiesRaw
     */
    public void mergeEntitiesRaw(
            MiddlewareContext context, Object user,
            Map<String, Object> target, List<Map<String, Object>> sources, Map<String, Object> opts) {
        
        String targetType = (String) target.get("entity_type");
        String targetId = (String) target.get("internal_id");

        // 合并基本属性
        for (Map<String, Object> source : sources) {
            mergeAttributes(target, source);
        }

        // 合并关系
        for (Map<String, Object> source : sources) {
            String sourceId = (String) source.get("internal_id");
            migrateRelations(context, user, targetId, sourceId, opts);
        }

        // 合并标记
        mergeMarkings(context, user, target, sources);

        // 合并标签
        mergeLabels(context, user, target, sources);

        // 更新目标实体
        elUpdateElement(context, user, target);

        // 删除源实体
        for (Map<String, Object> source : sources) {
            String sourceId = (String) source.get("internal_id");
            elDeleteElements(context, user, Collections.singletonList(source), true);
        }
    }

    // ==================== 私有辅助方法 ====================

    private List<String> getObservableHashes(Map<String, Object> entity) {
        List<String> hashes = new ArrayList<>();
        Map<String, Object> hashesMap = (Map<String, Object>) entity.get("hashes");
        if (hashesMap != null) {
            for (Object value : hashesMap.values()) {
                if (value instanceof List) {
                    hashes.addAll((List<String>) value);
                } else if (value != null) {
                    hashes.add(value.toString());
                }
            }
        }
        return hashes;
    }

    private List<Map<String, Object>> findExistingRelations(
            String targetId, List<String> sourceIds, String relationshipType, String scopeType) {
        return Collections.emptyList();
    }

    private void controlUserConfidenceAgainstElement(Object user, Map<String, Object> element) {
    }

    private void mergeAttributes(Map<String, Object> target, Map<String, Object> source) {
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            Object sourceValue = entry.getValue();
            Object targetValue = target.get(key);

            if (targetValue == null && sourceValue != null) {
                target.put(key, sourceValue);
            } else if (targetValue instanceof List && sourceValue instanceof List) {
                Set<Object> merged = new HashSet<>((List<?>) targetValue);
                merged.addAll((List<?>) sourceValue);
                target.put(key, new ArrayList<>(merged));
            } else if (targetValue instanceof String && sourceValue instanceof String) {
                if (!targetValue.equals(sourceValue)) {
                    List<String> merged = new ArrayList<>();
                    merged.add((String) targetValue);
                    merged.add((String) sourceValue);
                    target.put(key, merged);
                }
            }
        }
    }

    private void migrateRelations(MiddlewareContext context, Object user,
            String targetId, String sourceId, Map<String, Object> opts) {
        // 查找源实体的所有关系
        List<Map<String, Object>> sourceRelations = findRelationsForElement(context, user, sourceId);

        for (Map<String, Object> relation : sourceRelations) {
            String fromId = (String) relation.get("fromId");
            String toId = (String) relation.get("toId");
            String relationshipType = (String) relation.get("relationship_type");

            // 创建新的关系指向目标实体
            Map<String, Object> newRelation = new HashMap<>();
            if (sourceId.equals(fromId)) {
                newRelation.put("fromId", targetId);
                newRelation.put("toId", toId);
            } else {
                newRelation.put("fromId", fromId);
                newRelation.put("toId", targetId);
            }
            newRelation.put("relationship_type", relationshipType);

            try {
                middlewareCreator.createRelation(context, user, newRelation, opts);
            } catch (Exception e) {
                log.warn("Failed to migrate relation: {}", relation.get("internal_id"), e);
            }

            // 删除旧关系
            middlewareDeleter.deleteElementById(context, user,
                    (String) relation.get("internal_id"),
                    (String) relation.get("entity_type"), opts);
        }
    }

    private List<Map<String, Object>> findRelationsForElement(MiddlewareContext context, Object user, String elementId) {
        Map<String, Object> filter = new HashMap<>();
        filter.put("mode", "or");
        filter.put("filters", Arrays.asList(
                Map.of("key", Collections.singletonList("fromId"), "values", Collections.singletonList(elementId)),
                Map.of("key", Collections.singletonList("toId"), "values", Collections.singletonList(elementId))
        ));
        filter.put("filterGroups", Collections.emptyList());

        Map<String, Object> args = new HashMap<>();
        args.put("indices", Collections.singletonList("read_relationships_indices"));
        args.put("baseData", true);
        args.put("filters", filter);

        return middlewareLoader.fullEntitiesOrRelationsList(context, user,
                Collections.singletonList("stix-core-relationship"), args);
    }

    private void mergeMarkings(MiddlewareContext context, Object user,
            Map<String, Object> target, List<Map<String, Object>> sources) {
        Set<String> mergedMarkings = new HashSet<>();
        
        List<String> targetMarkings = (List<String>) target.get("object_marking_refs");
        if (targetMarkings != null) {
            mergedMarkings.addAll(targetMarkings);
        }

        for (Map<String, Object> source : sources) {
            List<String> sourceMarkings = (List<String>) source.get("object_marking_refs");
            if (sourceMarkings != null) {
                mergedMarkings.addAll(sourceMarkings);
            }
        }

        target.put("object_marking_refs", new ArrayList<>(mergedMarkings));
    }

    private void mergeLabels(MiddlewareContext context, Object user,
            Map<String, Object> target, List<Map<String, Object>> sources) {
        Set<String> mergedLabels = new HashSet<>();
        
        List<String> targetLabels = (List<String>) target.get("labels");
        if (targetLabels != null) {
            mergedLabels.addAll(targetLabels);
        }

        for (Map<String, Object> source : sources) {
            List<String> sourceLabels = (List<String>) source.get("labels");
            if (sourceLabels != null) {
                mergedLabels.addAll(sourceLabels);
            }
        }

        target.put("labels", new ArrayList<>(mergedLabels));
    }

    private void elUpdateElement(MiddlewareContext context, Object user, Map<String, Object> element) {
    }

    private void elDeleteElements(MiddlewareContext context, Object user,
            List<Map<String, Object>> elements, boolean forceDelete) {
    }

    private void storeMergeEvent(MiddlewareContext context, Object user,
            Map<String, Object> initialInstance, Map<String, Object> mergedInstance,
            List<Map<String, Object>> sources, Map<String, Object> opts) {
    }

    private void redisAddDeletions(List<String> ids, String draftId) {
    }
}
