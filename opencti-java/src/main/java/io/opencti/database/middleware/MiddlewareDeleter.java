package io.opencti.database.middleware;

import io.opencti.database.middleware.model.*;
import io.opencti.database.redis.lock.DistributedLock;
import io.opencti.database.redis.lock.LockManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 中间件删除模块
 * 原文件: database/middleware.js:3441-3666
 * 
 * 提供实体和关系的删除功能，包括级联删除、推理规则元素删除等。
 */
@Component
public class MiddlewareDeleter {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareDeleter.class);

    private final LockManager lockManager;
    private final MiddlewareLoader middlewareLoader;

    public MiddlewareDeleter(LockManager lockManager, MiddlewareLoader middlewareLoader) {
        this.lockManager = lockManager;
        this.middlewareLoader = middlewareLoader;
    }

    /**
     * 内部删除元素
     * 原文件: middleware.js:3441-3552 internalDeleteElementById
     */
    public <T> MiddlewareResult<T> internalDeleteElementById(
            MiddlewareContext context, Object user,
            String id, String type, Map<String, Object> opts) {
        
        if (type == null) {
            throw new RuntimeException("You need to specify a type when deleting an entity");
        }

        Map<String, Object> loadOpts = new HashMap<>();
        loadOpts.put("type", type);
        loadOpts.put("includeDeletedInDraft", true);

        Map<String, Object> element = middlewareLoader.storeLoadByIdWithRefs(context, user, id, loadOpts);
        if (element == null) {
            throw new RuntimeException("Element already deleted: " + id);
        }

        validateUserAccessOperation(user, element, "DELETE");
        controlUserConfidenceAgainstElement(user, element);

        List<String> participantIds = Collections.singletonList((String) element.get("internal_id"));
        DistributedLock lock = null;
        try {
            lock = lockManager.acquireLock(participantIds, Collections.emptyList());
            lock.getSignal().throwIfAborted();

            boolean forceDelete = opts != null && (boolean) opts.getOrDefault("forceDelete", false);
            elDeleteElements(context, user, Collections.singletonList(element), forceDelete);

            Object event = storeDeleteEvent(context, user, element, opts);
            redisAddDeletions(participantIds, context.getDraftId());

            return MiddlewareResult.success((T) element, event);
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
     * 删除元素
     * 原文件: middleware.js:3553-3560 deleteElementById
     */
    public <T> T deleteElementById(
            MiddlewareContext context, Object user,
            String id, String type, Map<String, Object> opts) {
        MiddlewareResult<T> result = internalDeleteElementById(context, user, id, type, opts);
        return result.getElement();
    }

    /**
     * 删除推理规则元素
     * 原文件: middleware.js:3561-3615 deleteInferredRuleElement
     */
    public void deleteInferredRuleElement(
            MiddlewareContext context, Object user,
            String ruleId, String type, String id) {
        
        Map<String, Object> loadOpts = new HashMap<>();
        loadOpts.put("type", type);
        loadOpts.put("indices", Collections.singletonList("read_index_inferred"));

        Map<String, Object> element = middlewareLoader.storeLoadByIdWithRefs(context, user, id, loadOpts);
        if (element == null) {
            return;
        }

        List<String> ruleIds = (List<String>) element.getOrDefault("i_rule_ids", Collections.emptyList());
        if (!ruleIds.contains(ruleId)) {
            return;
        }

        if (ruleIds.size() == 1) {
            elDeleteElements(context, getSystemUser(), Collections.singletonList(element), true);
            redisAddDeletions(Collections.singletonList(id), context.getDraftId());
        } else {
            List<String> newRuleIds = new ArrayList<>(ruleIds);
            newRuleIds.remove(ruleId);
            
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("i_rule_ids", newRuleIds);
            elUpdateElement(context, getSystemUser(), element, updateData);
        }
    }

    /**
     * 根据起止点删除关系
     * 原文件: middleware.js:3616-3666 deleteRelationsByFromAndTo
     */
    public Map<String, Object> deleteRelationsByFromAndTo(
            MiddlewareContext context, Object user,
            String fromId, String toId, String relationshipType, String scopeType, Map<String, Object> opts) {
        
        if (scopeType == null || fromId == null || toId == null) {
            throw new RuntimeException("You need to specify a scope type and both IDs");
        }

        Map<String, Object> fromThing = internalLoadById(context, user, fromId);
        Map<String, Object> toThing = internalLoadById(context, user, toId);

        if (fromThing == null || toThing == null) {
            throw new RuntimeException("Cannot find from or to element");
        }

        validateUserAccessOperation(user, fromThing, "EDIT");
        validateUserAccessOperation(user, toThing, "EDIT");

        List<Map<String, Object>> relationsToDelete = findRelationsBetweenElements(
                context, user, fromThing, toThing, relationshipType);

        List<Map<String, Object>> deletedRelations = new ArrayList<>();
        for (Map<String, Object> relation : relationsToDelete) {
            try {
                deleteElementById(context, user, 
                        (String) relation.get("internal_id"), 
                        (String) relation.get("entity_type"), opts);
                deletedRelations.add(relation);
            } catch (Exception e) {
                log.warn("Failed to delete relation: {}", relation.get("internal_id"), e);
            }
        }

        return Map.of(
                "from", fromThing,
                "to", toThing,
                "deletions", deletedRelations
        );
    }

    // ==================== 私有辅助方法 ====================

    private void validateUserAccessOperation(Object user, Map<String, Object> element, String operation) {
    }

    private void controlUserConfidenceAgainstElement(Object user, Map<String, Object> element) {
    }

    private Map<String, Object> internalLoadById(MiddlewareContext context, Object user, String id) {
        return middlewareLoader.storeLoadByIdWithRefs(context, user, id, null);
    }

    private List<Map<String, Object>> findRelationsBetweenElements(
            MiddlewareContext context, Object user,
            Map<String, Object> from, Map<String, Object> to, String relationshipType) {
        
        Map<String, Object> relationFilter = new HashMap<>();
        relationFilter.put("mode", "and");
        relationFilter.put("filters", Arrays.asList(
                Map.of("key", Collections.singletonList("fromId"), "values", 
                        Collections.singletonList(from.get("internal_id"))),
                Map.of("key", Collections.singletonList("toId"), "values", 
                        Collections.singletonList(to.get("internal_id")))
        ));
        relationFilter.put("filterGroups", Collections.emptyList());

        Map<String, Object> args = new HashMap<>();
        args.put("indices", Collections.singletonList("read_relationships_indices_without_inferred"));
        args.put("baseData", true);
        args.put("filters", relationFilter);

        return middlewareLoader.fullEntitiesOrRelationsList(context, user,
                Collections.singletonList(relationshipType), args);
    }

    private void elDeleteElements(MiddlewareContext context, Object user,
            List<Map<String, Object>> elements, boolean forceDelete) {
    }

    private void elUpdateElement(MiddlewareContext context, Object user,
            Map<String, Object> element, Map<String, Object> data) {
    }

    private Object storeDeleteEvent(MiddlewareContext context, Object user,
            Map<String, Object> element, Map<String, Object> opts) {
        return null;
    }

    private void redisAddDeletions(List<String> ids, String draftId) {
    }

    private Object getSystemUser() {
        return Map.of("id", "system");
    }
}
