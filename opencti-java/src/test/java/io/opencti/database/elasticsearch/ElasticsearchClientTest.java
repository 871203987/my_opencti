package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;
import io.opencti.database.elasticsearch.model.SearchHit;
import io.opencti.database.elasticsearch.model.SearchResponse;
import io.opencti.database.elasticsearch.query.QueryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Elasticsearch客户端测试类
 * 重写源文件路径: opencti/platform/opencti-graphql/src/database/engine.ts
 */
@ExtendWith(MockitoExtension.class)
class ElasticsearchClientTest {

    @Mock
    private ElasticsearchClient elasticsearchClient;

    @Mock
    private ElasticsearchIndicesClient indicesClient;

    @InjectMocks
    private ElasticsearchClientImpl elasticsearchClientImpl;

    @BeforeEach
    void setUp() {
        when(elasticsearchClient.indices()).thenReturn(indicesClient);
    }

    @Test
    @DisplayName("测试常量定义正确性")
    void testConstants() {
        assertEquals("opencti_internal", ElasticsearchConstants.INTERNAL_INDEX);
        assertEquals("stix_domain_objects", ElasticsearchConstants.STIX_DOMAIN_OBJECTS_INDEX);
        assertEquals("stix_core_relationships", ElasticsearchConstants.STIX_CORE_RELATIONSHIPS_INDEX);
        assertEquals("stix_sighting_relationships", ElasticsearchConstants.STIX_SIGHTING_RELATIONSHIPS_INDEX);
        assertEquals("stix_meta_objects", ElasticsearchConstants.STIX_META_OBJECTS_INDEX);
        assertEquals("stix_meta_relationships", ElasticsearchConstants.STIX_META_RELATIONSHIPS_INDEX);
        assertEquals("stix_core_objects", ElasticsearchConstants.STIX_CORE_OBJECTS_INDEX);
        assertEquals("workbench_index", ElasticsearchConstants.WORKBENCH_INDEX);
        assertEquals("default_max_search_result", ElasticsearchConstants.DEFAULT_MAX_SEARCH_RESULT);
        assertEquals(5000, ElasticsearchConstants.DEFAULT_MAX_SEARCH_RESULT);
    }

    @Test
    @DisplayName("测试QueryBuilder - term查询构建")
    void testQueryBuilderTerm() {
        Map<String, Object> query = QueryBuilder.term("status", "active");
        
        assertNotNull(query);
        assertTrue(query.containsKey("term"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> termQuery = (Map<String, Object>) query.get("term");
        assertTrue(termQuery.containsKey("status"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) termQuery.get("status");
        assertEquals("active", fieldQuery.get("value"));
    }

    @Test
    @DisplayName("测试QueryBuilder - terms查询构建")
    void testQueryBuilderTerms() {
        List<String> values = Arrays.asList("value1", "value2", "value3");
        Map<String, Object> query = QueryBuilder.terms("category", values);
        
        assertNotNull(query);
        assertTrue(query.containsKey("terms"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> termsQuery = (Map<String, Object>) query.get("terms");
        assertTrue(termsQuery.containsKey("category"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) termsQuery.get("category");
        @SuppressWarnings("unchecked")
        List<String> queryValues = (List<String>) fieldQuery.get("value");
        assertEquals(3, queryValues.size());
        assertTrue(queryValues.contains("value1"));
        assertTrue(queryValues.contains("value2"));
        assertTrue(queryValues.contains("value3"));
    }

    @Test
    @DisplayName("测试QueryBuilder - range查询构建")
    void testQueryBuilderRange() {
        Map<String, Object> rangeOptions = new HashMap<>();
        rangeOptions.put("gte", "2024-01-01");
        rangeOptions.put("lte", "2024-12-31");
        
        Map<String, Object> query = QueryBuilder.range("created_at", rangeOptions);
        
        assertNotNull(query);
        assertTrue(query.containsKey("range"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> rangeQuery = (Map<String, Object>) query.get("range");
        assertTrue(rangeQuery.containsKey("created_at"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldQuery = (Map<String, Object>) rangeQuery.get("created_at");
        assertEquals("2024-01-01", fieldQuery.get("gte"));
        assertEquals("2024-12-31", fieldQuery.get("lte"));
    }

    @Test
    @DisplayName("测试QueryBuilder - exists查询构建")
    void testQueryBuilderExists() {
        Map<String, Object> query = QueryBuilder.exists("description");
        
        assertNotNull(query);
        assertTrue(query.containsKey("exists"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> existsQuery = (Map<String, Object>) query.get("exists");
        assertEquals("description", existsQuery.get("field"));
    }

    @Test
    @DisplayName("测试QueryBuilder - nested查询构建")
    void testQueryBuilderNested() {
        Map<String, Object> innerQuery = QueryBuilder.term("nested_field.name", "test");
        Map<String, Object> query = QueryBuilder.nested("nested_field", innerQuery);
        
        assertNotNull(query);
        assertTrue(query.containsKey("nested"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> nestedQuery = (Map<String, Object>) query.get("nested");
        assertEquals("nested_field", nestedQuery.get("path"));
        assertTrue(nestedQuery.containsKey("query"));
    }

    @Test
    @DisplayName("测试QueryBuilder - bool查询构建")
    void testQueryBuilderBool() {
        Map<String, Object> must = QueryBuilder.term("status", "active");
        Map<String, Object> mustNot = QueryBuilder.term("deleted", "true");
        
        Map<String, Object> query = QueryBuilder.bool();
        QueryBuilder.addMust(query, must);
        QueryBuilder.addMustNot(query, mustNot);
        
        assertNotNull(query);
        assertTrue(query.containsKey("bool"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> boolQuery = (Map<String, Object>) query.get("bool");
        assertTrue(boolQuery.containsKey("must"));
        assertTrue(boolQuery.containsKey("must_not"));
    }

    @Test
    @DisplayName("测试QueryBuilder - 特殊字符转义")
    void testSpecialElasticCharsEscape() {
        String input = "test+value\\-query*string?\"escaped\"";
        String escaped = QueryBuilder.specialElasticCharsEscape(input);
        
        assertFalse(escaped.contains("+"));
        assertFalse(escaped.contains("-"));
        assertFalse(escaped.contains("*"));
        assertFalse(escaped.contains("?"));
        assertTrue(escaped.contains("\\+"));
        assertTrue(escaped.contains("\\-"));
        assertTrue(escaped.contains("\\*"));
        assertTrue(escaped.contains("\\?"));
    }

    @Test
    @DisplayName("测试QueryBuilder - 全文搜索Should构建")
    void testGenerateFullTextSearchShould() {
        List<String> searchFields = Arrays.asList("name", "description");
        String searchTerm = "test query";
        
        List<Map<String, Object>> shouldQueries = QueryBuilder.generateFullTextSearchShould(searchFields, searchTerm);
        
        assertNotNull(shouldQueries);
        assertEquals(2, shouldQueries.size());
        
        for (Map<String, Object> shouldQuery : shouldQueries) {
            assertTrue(shouldQuery.containsKey("query_string"));
        }
    }

    @Test
    @DisplayName("测试SearchHit模型")
    void testSearchHitModel() {
        SearchHit hit = new SearchHit();
        hit.setId("test-id-123");
        hit.setIndex("stix_domain_objects");
        hit.setScore(1.5f);
        
        Map<String, Object> source = new HashMap<>();
        source.put("name", "Test Entity");
        source.put("entity_type", "Malware");
        hit.setSource(source);
        
        assertEquals("test-id-123", hit.getId());
        assertEquals("stix_domain_objects", hit.getIndex());
        assertEquals(1.5f, hit.getScore());
        assertEquals("Test Entity", hit.getSource().get("name"));
        assertEquals("Malware", hit.getSource().get("entity_type"));
    }

    @Test
    @DisplayName("测试SearchResponse模型")
    void testSearchResponseModel() {
        SearchResponse response = new SearchResponse();
        
        SearchResponse.ShardsInfo shardsInfo = new SearchResponse.ShardsInfo();
        shardsInfo.setTotal(5);
        shardsInfo.setSuccessful(5);
        shardsInfo.setFailed(0);
        response.setShardsInfo(shardsInfo);
        
        response.setTotal(100L);
        response.setMaxScore(2.5f);
        
        List<SearchHit> hits = new ArrayList<>();
        SearchHit hit1 = new SearchHit();
        hit1.setId("id-1");
        hit1.setScore(2.5f);
        hits.add(hit1);
        response.setHits(hits);
        
        assertEquals(5, response.getShardsInfo().getTotal());
        assertEquals(5, response.getShardsInfo().getSuccessful());
        assertEquals(0, response.getShardsInfo().getFailed());
        assertEquals(100L, response.getTotal());
        assertEquals(2.5f, response.getMaxScore());
        assertEquals(1, response.getHits().size());
    }

    @Test
    @DisplayName("测试ElasticsearchMapping - 短映射生成")
    void testShortMapping() {
        Map<String, Object> mapping = ElasticsearchMapping.SHORT_MAPPING;
        
        assertNotNull(mapping);
        assertEquals("short", mapping.get("type"));
    }

    @Test
    @DisplayName("测试ElasticsearchMapping - 文本映射生成")
    void testTextMapping() {
        Map<String, Object> mapping = ElasticsearchMapping.TEXT_MAPPING;
        
        assertNotNull(mapping);
        assertEquals("text", mapping.get("type"));
        assertTrue(mapping.containsKey("fields"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> fields = (Map<String, Object>) mapping.get("fields");
        assertTrue(fields.containsKey("keyword"));
    }

    @Test
    @DisplayName("测试ElasticsearchMapping - 日期映射生成")
    void testDateMapping() {
        Map<String, Object> mapping = ElasticsearchMapping.DATE_MAPPING;
        
        assertNotNull(mapping);
        assertEquals("date", mapping.get("type"));
    }

    @Test
    @DisplayName("测试ElasticsearchMapping - 属性映射生成器")
    void testAttributeMappingGenerator() {
        Map<String, Object> mapping = ElasticsearchMapping.attributeMappingGenerator("string");
        assertEquals("text", mapping.get("type"));
        
        mapping = ElasticsearchMapping.attributeMappingGenerator("integer");
        assertEquals("integer", mapping.get("type"));
        
        mapping = ElasticsearchMapping.attributeMappingGenerator("boolean");
        assertEquals("boolean", mapping.get("type"));
        
        mapping = ElasticsearchMapping.attributeMappingGenerator("date");
        assertEquals("date", mapping.get("type"));
    }

    @Test
    @DisplayName("测试ElasticsearchConstants - 索引名称列表")
    void testIndexNamesList() {
        List<String> indexNames = ElasticsearchConstants.INDEX_NAMES;
        
        assertNotNull(indexNames);
        assertTrue(indexNames.contains(ElasticsearchConstants.STIX_DOMAIN_OBJECTS_INDEX));
        assertTrue(indexNames.contains(ElasticsearchConstants.STIX_CORE_RELATIONSHIPS_INDEX));
        assertTrue(indexNames.contains(ElasticsearchConstants.STIX_SIGHTING_RELATIONSHIPS_INDEX));
        assertTrue(indexNames.contains(ElasticsearchConstants.STIX_META_OBJECTS_INDEX));
        assertTrue(indexNames.contains(ElasticsearchConstants.STIX_META_RELATIONSHIPS_INDEX));
        assertTrue(indexNames.contains(ElasticsearchConstants.STIX_CORE_OBJECTS_INDEX));
        assertTrue(indexNames.contains(ElasticsearchConstants.WORKBENCH_INDEX));
    }

    @Test
    @DisplayName("测试QueryBuilder - 空值处理")
    void testQueryBuilderNullHandling() {
        Map<String, Object> termQuery = QueryBuilder.term("field", null);
        assertNotNull(termQuery);
        
        Map<String, Object> termsQuery = QueryBuilder.terms("field", null);
        assertNotNull(termsQuery);
        
        Map<String, Object> rangeQuery = QueryBuilder.range("field", null);
        assertNotNull(rangeQuery);
    }

    @Test
    @DisplayName("测试QueryBuilder - 空列表处理")
    void testQueryBuilderEmptyListHandling() {
        List<String> emptyList = new ArrayList<>();
        Map<String, Object> termsQuery = QueryBuilder.terms("field", emptyList);
        
        assertNotNull(termsQuery);
        @SuppressWarnings("unchecked")
        Map<String, Object> termsMap = (Map<String, Object>) termsQuery.get("terms");
        @SuppressWarnings("unchecked")
        Map<String, Object> fieldMap = (Map<String, Object>) termsMap.get("field");
        @SuppressWarnings("unchecked")
        List<String> values = (List<String>) fieldMap.get("value");
        assertTrue(values.isEmpty());
    }
}
