package io.opencti.database.elasticsearch.query;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;

import java.util.ArrayList;
import java.util.List;

/**
 * ES过滤器构建器
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供数据限制和访问控制过滤器的构建方法
 */
public class FilterBuilder {

    /**
     * 构建数据限制过滤器
     * 重写自: engine.ts - buildDataRestrictions() (行618-726)
     * 
     * @param userIds 用户ID列表
     * @param groupIds 组ID列表
     * @param markingIds 标记ID列表
     * @param bypass 是否绕过权限检查
     * @return 过滤器列表
     */
    public static List<Query> buildDataRestrictions(
            List<String> userIds,
            List<String> groupIds,
            List<String> markingIds,
            boolean bypass) {
        
        List<Query> mustFilters = new ArrayList<>();
        List<Query> mustNotFilters = new ArrayList<>();
        
        if (bypass) {
            return mustFilters;
        }
        
        // 构建用户访问过滤器
        List<Query> accessFilters = buildUserMemberAccessFilter(userIds, groupIds, false, false);
        mustFilters.addAll(accessFilters);
        
        // 构建标记限制过滤器
        if (markingIds != null && !markingIds.isEmpty()) {
            Query markingFilter = buildMarkingFilter(markingIds);
            mustFilters.add(markingFilter);
        }
        
        return mustFilters;
    }

    /**
     * 构建用户成员访问过滤器
     * 重写自: engine.ts - buildUserMemberAccessFilter() (行561-616)
     * 
     * @param userIds 用户ID列表
     * @param groupIds 组ID列表
     * @param includeAuthorities 是否包含权限
     * @param excludeEmptyAuthorizedMembers 是否排除空授权成员
     * @return 过滤器列表
     */
    public static List<Query> buildUserMemberAccessFilter(
            List<String> userIds,
            List<String> groupIds,
            boolean includeAuthorities,
            boolean excludeEmptyAuthorizedMembers) {
        
        List<Query> filters = new ArrayList<>();
        
        if (userIds == null || userIds.isEmpty()) {
            return filters;
        }
        
        // 构建授权成员ID条件
        List<String> authorizedIds = new ArrayList<>();
        authorizedIds.add("ALL"); // MEMBER_ACCESS_ALL
        authorizedIds.addAll(userIds);
        
        Query authorizedMembersIdsTerms = Query.of(q -> q.terms(t -> t
                .field("authorized_members.id.keyword")
                .terms(ts -> ts.value(authorizedIds))));
        
        // 构建组限制条件
        List<Query> groupConditions = new ArrayList<>();
        groupConditions.add(Query.of(q -> q.bool(b -> b
                .mustNot(mn -> mn.exists(e -> e.field("authorized_members.groups_restriction_ids"))))));
        
        if (groupIds != null && !groupIds.isEmpty()) {
            // TODO: 实现terms_set查询
        }
        
        Query groupRestrictionCondition = Query.of(q -> q.bool(b -> b
                .should(groupConditions)));
        
        // 构建授权过滤器
        List<Query> authorizedFilters = new ArrayList<>();
        authorizedFilters.add(Query.of(q -> q.bool(b -> b
                .must(authorizedMembersIdsTerms)
                .must(groupRestrictionCondition))));
        
        // 构建should条件
        List<Query> shouldConditions = new ArrayList<>();
        
        // 空授权成员条件
        if (!excludeEmptyAuthorizedMembers) {
            Query emptyAuthorizedMembers = Query.of(q -> q.bool(b -> b
                    .mustNot(mn -> mn.nested(n -> n
                            .path("authorized_members")
                            .query(mn2 -> mn2.matchAll(m -> m))))));
            shouldConditions.add(emptyAuthorizedMembers);
        }
        
        // 嵌套查询
        Query nestedQuery = Query.of(q -> q.nested(n -> n
                .path("authorized_members")
                .query(nq -> nq.bool(nb -> nb.should(authorizedFilters)))));
        shouldConditions.add(nestedQuery);
        
        // 构建最终过滤器
        filters.add(Query.of(q -> q.bool(b -> b.should(shouldConditions))));
        
        return filters;
    }

    /**
     * 构建标记过滤器
     * 重写自: engine.ts - buildDataRestrictions() 标记部分 (行634-674)
     * 
     * @param allowedMarkingIds 允许的标记ID列表
     * @return 标记过滤器
     */
    public static Query buildMarkingFilter(List<String> allowedMarkingIds) {
        if (allowedMarkingIds == null || allowedMarkingIds.isEmpty()) {
            // 如果没有标记，只能访问无标记数据
            return Query.of(q -> q.bool(b -> b
                    .mustNot(mn -> mn.exists(e -> e.field("object_marking_refs")))));
        }
        
        // 构建should条件
        List<Query> shouldConditions = new ArrayList<>();
        
        // 无标记条件
        shouldConditions.add(Query.of(q -> q.bool(b -> b
                .mustNot(mn -> mn.exists(e -> e.field("object_marking_refs"))))));
        
        // 允许的标记条件
        // TODO: 实现完整的标记过滤逻辑
        
        return Query.of(q -> q.bool(b -> b
                .should(shouldConditions)
                .minimumShouldMatch(1)));
    }

    /**
     * 构建组织过滤器
     * 重写自: engine.ts - buildDataRestrictions() 组织部分 (行676-723)
     * 
     * @param organizationIds 组织ID列表
     * @param platformOrganization 平台组织ID
     * @return 组织过滤器
     */
    public static Query buildOrganizationFilter(List<String> organizationIds, String platformOrganization) {
        if (platformOrganization != null && !platformOrganization.isEmpty()) {
            // 平台有特定组织，只有该组织的用户可以访问空定义
            if (organizationIds == null || !organizationIds.contains(platformOrganization)) {
                return Query.of(q -> q.exists(e -> e.field("creator_id")));
            }
        }
        
        return null;
    }

    /**
     * 构建类型过滤器
     * 
     * @param types 类型列表
     * @return 类型过滤器
     */
    public static Query buildTypeFilter(List<String> types) {
        if (types == null || types.isEmpty()) {
            return null;
        }
        
        return Query.of(q -> q.terms(t -> t
                .field("entity_type.keyword")
                .terms(ts -> ts.value(types))));
    }

    /**
     * 构建ID过滤器
     * 
     * @param ids ID列表
     * @return ID过滤器
     */
    public static Query buildIdsFilter(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        
        return Query.of(q -> q.terms(t -> t
                .field("internal_id.keyword")
                .terms(ts -> ts.value(ids))));
    }

    /**
     * 构建时间范围过滤器
     * 
     * @param field 时间字段
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 时间范围过滤器
     */
    public static Query buildTimeRangeFilter(String field, Object startTime, Object endTime) {
        if (startTime == null && endTime == null) {
            return null;
        }
        
        return Query.of(q -> q.range(r -> {
            r.field(field);
            if (startTime != null) {
                r.gte(JsonData.of(startTime));
            }
            if (endTime != null) {
                r.lte(JsonData.of(endTime));
            }
            return r;
        }));
    }

    /**
     * 构建关系方向过滤器
     * 重写自: engine.ts - 关系查询构建
     * 
     * @param fromId 起始ID
     * @param toId 目标ID
     * @return 关系过滤器
     */
    public static Query buildRelationFilter(String fromId, String toId) {
        List<Query> mustQueries = new ArrayList<>();
        
        if (fromId != null && !fromId.isEmpty()) {
            mustQueries.add(Query.of(q -> q.term(t -> t
                    .field("rel_from.internal_id.keyword")
                    .value(fromId))));
        }
        
        if (toId != null && !toId.isEmpty()) {
            mustQueries.add(Query.of(q -> q.term(t -> t
                    .field("rel_to.internal_id.keyword")
                    .value(toId))));
        }
        
        if (mustQueries.isEmpty()) {
            return null;
        }
        
        return Query.of(q -> q.bool(b -> b.must(mustQueries)));
    }

    /**
     * 构建本地must过滤器
     * 重写自: engine.ts - buildLocalMustFilter() (行2136-2300)
     * 
     * @param filters 过滤条件Map
     * @return 过滤器列表
     */
    public static List<Query> buildLocalMustFilter(Map<String, Object> filters) {
        List<Query> mustFilters = new ArrayList<>();
        
        if (filters == null || filters.isEmpty()) {
            return mustFilters;
        }
        
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (value == null) {
                continue;
            }
            
            if (value instanceof List) {
                @SuppressWarnings("unchecked")
                List<String> values = (List<String>) value;
                if (!values.isEmpty()) {
                    mustFilters.add(Query.of(q -> q.terms(t -> t
                            .field(key + ".keyword")
                            .terms(ts -> ts.value(values)))));
                }
            } else {
                mustFilters.add(Query.of(q -> q.term(t -> t
                        .field(key + ".keyword")
                        .value(String.valueOf(value)))));
            }
        }
        
        return mustFilters;
    }
}
