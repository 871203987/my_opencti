package io.opencti.database.elasticsearch;

import io.opencti.database.elasticsearch.query.QueryBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * QueryBuilder测试类
 * 重写源文件路径: opencti/platform/opencti-graphql/src/database/engine.ts (查询构建相关方法)
 */
class QueryBuilderTest {

    @Test
    @DisplayName("测试term查询 - 基本构建")
    void testTermBasic() {
        Map<String, Object> query = QueryBuilder.term("field", "value");
        
        assertNotNull(query);
        assertTrue(query.containsKey("term"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> termQuery = (Map<String, Object>) query.get("term");
        assertTrue(termQuery.containsKey("field"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) termQuery.get("field");
        assertEquals("value", fieldQuery.get("value"));
    }

    @Test
    @DisplayName("测试term查询 - 数值类型")
    void testTermNumericValue() {
        Map<String, Object> query = QueryBuilder.term("count", 42);
        
        assertNotNull(query);
        @SuppressWarnings("unchecked")
        Map<String, Object> termQuery = (Map<String, Object>) query.get("term");
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) termQuery.get("count");
        assertEquals(42, fieldQuery.get("value"));
    }

    @Test
    @DisplayName("测试term查询 - 布尔类型")
    void testTermBooleanValue() {
        Map<String, Object> query = QueryBuilder.term("active", true);
        
        assertNotNull(query);
        @SuppressWarnings("unchecked")
        Map<String, Object> termQuery = (Map<String, Object>) query.get("term");
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) termQuery.get("active");
        assertEquals(true, fieldQuery.get("value"));
    }

    @Test
    @DisplayName("测试terms查询 - 字符串列表")
    void testTermsStringList() {
        List<String> values = Arrays.asList("value1", "value2", "value3");
        Map<String, Object> query = QueryBuilder.terms("tags", values);
        
        assertNotNull(query);
        assertTrue(query.containsKey("terms"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> termsQuery = (Map<String, Object>) query.get("terms");
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) termsQuery.get("tags");
        @SuppressWarnings("unchecked")
        List<String> queryValues = (List<String>) fieldQuery.get("value");
        assertEquals(3, queryValues.size());
    }

    @Test
    @DisplayName("测试terms查询 - 数值列表")
    void testTermsNumericList() {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);
        Map<String, Object> query = QueryBuilder.terms("ids", values);
        
        assertNotNull(query);
        @SuppressWarnings("unchecked")
        Map<String, Object> termsQuery = (Map<String, Object>) query.get("terms");
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) termsQuery.get("ids");
        @SuppressWarnings("unchecked")
        List<Integer> queryValues = (List<Integer>) fieldQuery.get("value");
        assertEquals(5, queryValues.size());
    }

    @Test
    @DisplayName("测试range查询 - gte")
    void testRangeGte() {
        Map<String, Object> options = new HashMap<>();
        options.put("gte", 100);
        
        Map<String, Object> query = QueryBuilder.range("score", options);
        
        assertNotNull(query);
        @SuppressWarnings("unchecked")
        Map<String, Object> rangeQuery = (Map<String, Object>) query.get("range");
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) rangeQuery.get("score");
        assertEquals(100, fieldQuery.get("gte"));
    }

    @Test
    @DisplayName("测试range查询 - lte")
    void testRangeLte() {
        Map<String, Object> options = new HashMap<>();
        options.put("lte", 1000);
        
        Map<String, Object> query = QueryBuilder.range("price", options);
        
        assertNotNull(query);
        @SuppressWarnings("unchecked")
        Map<String, Object> rangeQuery = (Map<String, Object>) query.get("range");
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) rangeQuery.get("price");
        assertEquals(1000, fieldQuery.get("lte"));
    }

    @Test
    @DisplayName("测试range查询 - gt和lt")
    void testRangeGtLt() {
        Map<String, Object> options = new HashMap<>();
        options.put("gt", 0);
        options.put("lt", 100);
        
        Map<String, Object> query = QueryBuilder.range("percentage", options);
        
        assertNotNull(query);
        @SuppressWarnings("unchecked")
        Map<String, Object> rangeQuery = (Map<String, Object>) query.get("range");
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) rangeQuery.get("percentage");
        assertEquals(0, fieldQuery.get("gt"));
        assertEquals(100, fieldQuery.get("lt"));
    }

    @Test
    @DisplayName("测试range查询 - 日期范围")
    void testRangeDateRange() {
        Map<String, Object> options = new HashMap<>();
        options.put("gte", "2024-01-01T00:00:00Z");
        options.put("lte", "2024-12-31T23:59:59Z");
        options.put("format", "strict_date_optional_time");
        
        Map<String, Object> query = QueryBuilder.range("created_at", options);
        
        assertNotNull(query);
        @SuppressWarnings("unchecked")
        Map<String, Object> rangeQuery = (Map<String, Object>) query.get("range");
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) rangeQuery.get("created_at");
        assertEquals("2024-01-01T00:00:00Z", fieldQuery.get("gte"));
        assertEquals("2024-12-31T23:59:59Z", fieldQuery.get("lte"));
        assertEquals("strict_date_optional_time", fieldQuery.get("format"));
    }

    @Test
    @DisplayName("测试exists查询")
    void testExists() {
        Map<String, Object> query = QueryBuilder.exists("description");
        
        assertNotNull(query);
        assertTrue(query.containsKey("exists"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> existsQuery = (Map<String, Object>) query.get("exists");
        assertEquals("description", existsQuery.get("field"));
    }

    @Test
    @DisplayName("测试nested查询")
    void testNested() {
        Map<String, Object> innerQuery = QueryBuilder.term("comments.author", "john");
        Map<String, Object> query = QueryBuilder.nested("comments", innerQuery);
        
        assertNotNull(query);
        assertTrue(query.containsKey("nested"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> nestedQuery = (Map<String, Object>) query.get("nested");
        assertEquals("comments", nestedQuery.get("path"));
        assertTrue(nestedQuery.containsKey("query"));
    }

    @Test
    @DisplayName("测试bool查询 - 基本构建")
    void testBoolBasic() {
        Map<String, Object> query = QueryBuilder.bool();
        
        assertNotNull(query);
        assertTrue(query.containsKey("bool"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolQuery = (Map<String, Object>) query.get("bool");
        assertTrue(boolQuery.isEmpty() || boolQuery.containsKey("must") 
                || boolQuery.containsKey("should") || boolQuery.containsKey("must_not"));
    }

    @Test
    @DisplayName("测试bool查询 - must子句")
    void testBoolMust() {
        Map<String, Object> boolQuery = QueryBuilder.bool();
        Map<String, Object> termQuery = QueryBuilder.term("status", "active");
        
        QueryBuilder.addMust(boolQuery, termQuery);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolMap = (Map<String, Object>) boolQuery.get("bool");
        assertTrue(boolMap.containsKey("must"));
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> mustClauses = (List<Map<String, Object>>) boolMap.get("must");
        assertEquals(1, mustClauses.size());
    }

    @Test
    @DisplayName("测试bool查询 - should子句")
    void testBoolShould() {
        Map<String, Object> boolQuery = QueryBuilder.bool();
        Map<String, Object> termQuery1 = QueryBuilder.term("color", "red");
        Map<String, Object> termQuery2 = QueryBuilder.term("color", "blue");
        
        QueryBuilder.addShould(boolQuery, termQuery1);
        QueryBuilder.addShould(boolQuery, termQuery2);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolMap = (Map<String, Object>) boolQuery.get("bool");
        assertTrue(boolMap.containsKey("should"));
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> shouldClauses = (List<Map<String, Object>>) boolMap.get("should");
        assertEquals(2, shouldClauses.size());
    }

    @Test
    @DisplayName("测试bool查询 - must_not子句")
    void testBoolMustNot() {
        Map<String, Object> boolQuery = QueryBuilder.bool();
        Map<String, Object> termQuery = QueryBuilder.term("deleted", "true");
        
        QueryBuilder.addMustNot(boolQuery, termQuery);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolMap = (Map<String, Object>) boolQuery.get("bool");
        assertTrue(boolMap.containsKey("must_not"));
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> mustNotClauses = (List<Map<String, Object>>) boolMap.get("must_not");
        assertEquals(1, mustNotClauses.size());
    }

    @Test
    @DisplayName("测试bool查询 - filter子句")
    void testBoolFilter() {
        Map<String, Object> boolQuery = QueryBuilder.bool();
        Map<String, Object> rangeQuery = QueryBuilder.range("created_at", Map.of("gte", "2024-01-01"));
        
        QueryBuilder.addFilter(boolQuery, rangeQuery);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolMap = (Map<String, Object>) boolQuery.get("bool");
        assertTrue(boolMap.containsKey("filter"));
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> filterClauses = (List<Map<String, Object>>) boolMap.get("filter");
        assertEquals(1, filterClauses.size());
    }

    @Test
    @DisplayName("测试bool查询 - 复合查询")
    void testBoolComplex() {
        Map<String, Object> boolQuery = QueryBuilder.bool();
        
        QueryBuilder.addMust(boolQuery, QueryBuilder.term("type", "Malware"));
        QueryBuilder.addMust(boolQuery, QueryBuilder.term("status", "active"));
        QueryBuilder.addMustNot(boolQuery, QueryBuilder.term("revoked", "true"));
        QueryBuilder.addShould(boolQuery, QueryBuilder.term("confidence", "high"));
        QueryBuilder.addFilter(boolQuery, QueryBuilder.range("created_at", Map.of("gte", "2024-01-01")));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolMap = (Map<String, Object>) boolQuery.get("bool");
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> mustClauses = (List<Map<String, Object>>) boolMap.get("must");
        assertEquals(2, mustClauses.size());
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> mustNotClauses = (List<Map<String, Object>>) boolMap.get("must_not");
        assertEquals(1, mustNotClauses.size());
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> shouldClauses = (List<Map<String, Object>>) boolMap.get("should");
        assertEquals(1, shouldClauses.size());
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> filterClauses = (List<Map<String, Object>>) boolMap.get("filter");
        assertEquals(1, filterClauses.size());
    }

    @Test
    @DisplayName("测试特殊字符转义 - 基本字符")
    void testSpecialElasticCharsEscapeBasic() {
        String input = "test+value";
        String escaped = QueryBuilder.specialElasticCharsEscape(input);
        
        assertTrue(escaped.contains("\\+"));
        assertFalse(escaped.contains("+"));
    }

    @Test
    @DisplayName("测试特殊字符转义 - 多种特殊字符")
    void testSpecialElasticCharsEscapeMultiple() {
        String input = "test+value-query*string?\"escaped\"";
        String escaped = QueryBuilder.specialElasticCharsEscape(input);
        
        assertTrue(escaped.contains("\\+"));
        assertTrue(escaped.contains("\\-"));
        assertTrue(escaped.contains("\\*"));
        assertTrue(escaped.contains("\\?"));
    }

    @Test
    @DisplayName("测试特殊字符转义 - 无特殊字符")
    void testSpecialElasticCharsEscapeNoSpecialChars() {
        String input = "normal_string";
        String escaped = QueryBuilder.specialElasticCharsEscape(input);
        
        assertEquals(input, escaped);
    }

    @Test
    @DisplayName("测试特殊字符转义 - 空字符串")
    void testSpecialElasticCharsEscapeEmpty() {
        String input = "";
        String escaped = QueryBuilder.specialElasticCharsEscape(input);
        
        assertEquals("", escaped);
    }

    @Test
    @DisplayName("测试全文搜索Should生成")
    void testGenerateFullTextSearchShould() {
        List<String> fields = Arrays.asList("name", "description", "content");
        String searchTerm = "malware analysis";
        
        List<Map<String, Object>> shouldQueries = QueryBuilder.generateFullTextSearchShould(fields, searchTerm);
        
        assertNotNull(shouldQueries);
        assertEquals(3, shouldQueries.size());
        
        for (Map<String, Object> query : shouldQueries) {
            assertTrue(query.containsKey("query_string"));
        }
    }

    @Test
    @DisplayName("测试全文搜索Should生成 - 空字段列表")
    void testGenerateFullTextSearchShouldEmptyFields() {
        List<String> fields = new ArrayList<>();
        String searchTerm = "test";
        
        List<Map<String, Object>> shouldQueries = QueryBuilder.generateFullTextSearchShould(fields, searchTerm);
        
        assertNotNull(shouldQueries);
        assertTrue(shouldQueries.isEmpty());
    }

    @Test
    @DisplayName("测试全文搜索Should生成 - 空搜索词")
    void testGenerateFullTextSearchShouldEmptySearchTerm() {
        List<String> fields = Arrays.asList("name", "description");
        String searchTerm = "";
        
        List<Map<String, Object>> shouldQueries = QueryBuilder.generateFullTextSearchShould(fields, searchTerm);
        
        assertNotNull(shouldQueries);
    }

    @Test
    @DisplayName("测试wildcard查询")
    void testWildcard() {
        Map<String, Object> query = QueryBuilder.wildcard("name", "mal*");
        
        assertNotNull(query);
        assertTrue(query.containsKey("wildcard"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> wildcardQuery = (Map<String, Object>) query.get("wildcard");
        assertTrue(wildcardQuery.containsKey("name"));
    }

    @Test
    @DisplayName("测试matchAll查询")
    void testMatchAll() {
        Map<String, Object> query = QueryBuilder.matchAll();
        
        assertNotNull(query);
        assertTrue(query.containsKey("match_all"));
    }

    @Test
    @DisplayName("测试ids查询")
    void testIds() {
        List<String> ids = Arrays.asList("id1", "id2", "id3");
        Map<String, Object> query = QueryBuilder.ids(ids);
        
        assertNotNull(query);
        assertTrue(query.containsKey("ids"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> idsQuery = (Map<String, Object>) query.get("ids");
        @SuppressWarnings("unchecked")
        List<String> queryIds = (List<String>) idsQuery.get("values");
        assertEquals(3, queryIds.size());
    }
}
