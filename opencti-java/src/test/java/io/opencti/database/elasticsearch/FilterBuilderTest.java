package io.opencti.database.elasticsearch;

import io.opencti.database.elasticsearch.query.FilterBuilder;
import io.opencti.database.elasticsearch.query.QueryBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FilterBuilder测试类
 * 重写源文件路径: opencti/platform/opencti-graphql/src/database/engine.ts (buildDataRestrictions方法)
 */
class FilterBuilderTest {

    @Test
    @DisplayName("测试buildDataRestrictions - 基本用户访问过滤")
    void testBuildDataRestrictionsBasicUserAccess() {
        Set<String> userMemberAccessIds = new HashSet<>(Arrays.asList("group1", "group2"));
        Set<String> userMarkingIds = new HashSet<>(Arrays.asList("marking1", "marking2"));
        
        Map<String, Object> restrictions = FilterBuilder.buildDataRestrictions(
                userMemberAccessIds,
                userMarkingIds,
                false
        );
        
        assertNotNull(restrictions);
        assertTrue(restrictions.containsKey("bool"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolQuery = (Map<String, Object>) restrictions.get("bool");
        assertTrue(boolQuery.containsKey("should") || boolQuery.containsKey("must"));
    }

    @Test
    @DisplayName("测试buildUserMemberAccessFilter - 成员访问过滤构建")
    void testBuildUserMemberAccessFilter() {
        Set<String> memberIds = new HashSet<>(Arrays.asList("org1", "org2", "org3"));
        
        Map<String, Object> filter = FilterBuilder.buildUserMemberAccessFilter(memberIds);
        
        assertNotNull(filter);
        assertTrue(filter.containsKey("bool"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolQuery = (Map<String, Object>) filter.get("bool");
        assertTrue(boolQuery.containsKey("should"));
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> shouldClauses = (List<Map<String, Object>>) boolQuery.get("should");
        assertFalse(shouldClauses.isEmpty());
    }

    @Test
    @DisplayName("测试buildMarkingFilter - 标记过滤构建")
    void testBuildMarkingFilter() {
        Set<String> markingIds = new HashSet<>(Arrays.asList("TLP:RED", "TLP:GREEN"));
        
        Map<String, Object> filter = FilterBuilder.buildMarkingFilter(markingIds);
        
        assertNotNull(filter);
        assertTrue(filter.containsKey("bool"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolQuery = (Map<String, Object>) filter.get("bool");
        assertTrue(boolQuery.containsKey("should") || boolQuery.containsKey("must"));
    }

    @Test
    @DisplayName("测试buildDataRestrictions - 空用户访问")
    void testBuildDataRestrictionsEmptyUserAccess() {
        Set<String> userMemberAccessIds = new HashSet<>();
        Set<String> userMarkingIds = new HashSet<>();
        
        Map<String, Object> restrictions = FilterBuilder.buildDataRestrictions(
                userMemberAccessIds,
                userMarkingIds,
                false
        );
        
        assertNotNull(restrictions);
    }

    @Test
    @DisplayName("测试buildDataRestrictions - bypass权限")
    void testBuildDataRestrictionsBypass() {
        Set<String> userMemberAccessIds = new HashSet<>(Arrays.asList("group1"));
        Set<String> userMarkingIds = new HashSet<>(Arrays.asList("marking1"));
        
        Map<String, Object> restrictions = FilterBuilder.buildDataRestrictions(
                userMemberAccessIds,
                userMarkingIds,
                true
        );
        
        assertNotNull(restrictions);
    }

    @Test
    @DisplayName("测试buildUserMemberAccessFilter - 空成员列表")
    void testBuildUserMemberAccessFilterEmpty() {
        Set<String> memberIds = new HashSet<>();
        
        Map<String, Object> filter = FilterBuilder.buildUserMemberAccessFilter(memberIds);
        
        assertNotNull(filter);
    }

    @Test
    @DisplayName("测试buildMarkingFilter - 空标记列表")
    void testBuildMarkingFilterEmpty() {
        Set<String> markingIds = new HashSet<>();
        
        Map<String, Object> filter = FilterBuilder.buildMarkingFilter(markingIds);
        
        assertNotNull(filter);
    }

    @Test
    @DisplayName("测试buildDataRestrictions - 单个成员和标记")
    void testBuildDataRestrictionsSingleMemberAndMarking() {
        Set<String> userMemberAccessIds = new HashSet<>(Collections.singletonList("single-group"));
        Set<String> userMarkingIds = new HashSet<>(Collections.singletonList("single-marking"));
        
        Map<String, Object> restrictions = FilterBuilder.buildDataRestrictions(
                userMemberAccessIds,
                userMarkingIds,
                false
        );
        
        assertNotNull(restrictions);
        assertTrue(restrictions.containsKey("bool"));
    }

    @Test
    @DisplayName("测试buildUserMemberAccessFilter - 大量成员")
    void testBuildUserMemberAccessFilterLargeSet() {
        Set<String> memberIds = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            memberIds.add("member-" + i);
        }
        
        Map<String, Object> filter = FilterBuilder.buildUserMemberAccessFilter(memberIds);
        
        assertNotNull(filter);
        assertTrue(filter.containsKey("bool"));
    }

    @Test
    @DisplayName("测试buildMarkingFilter - 多种标记类型")
    void testBuildMarkingFilterMultipleTypes() {
        Set<String> markingIds = new HashSet<>(Arrays.asList(
                "TLP:RED",
                "TLP:GREEN",
                "TLP:AMBER",
                "TLP:WHITE",
                "PAP:CLEAR"
        ));
        
        Map<String, Object> filter = FilterBuilder.buildMarkingFilter(markingIds);
        
        assertNotNull(filter);
        assertTrue(filter.containsKey("bool"));
    }

    @Test
    @DisplayName("测试过滤组合 - 与QueryBuilder配合使用")
    void testFilterCombinationWithQueryBuilder() {
        Set<String> userMemberAccessIds = new HashSet<>(Arrays.asList("group1"));
        Set<String> userMarkingIds = new HashSet<>(Arrays.asList("marking1"));
        
        Map<String, Object> restrictions = FilterBuilder.buildDataRestrictions(
                userMemberAccessIds,
                userMarkingIds,
                false
        );
        
        Map<String, Object> mainQuery = QueryBuilder.bool();
        Map<String, Object> termQuery = QueryBuilder.term("entity_type", "Malware");
        QueryBuilder.addMust(mainQuery, termQuery);
        
        assertNotNull(mainQuery);
        assertNotNull(restrictions);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolMain = (Map<String, Object>) mainQuery.get("bool");
        assertTrue(boolMain.containsKey("must"));
    }
}
