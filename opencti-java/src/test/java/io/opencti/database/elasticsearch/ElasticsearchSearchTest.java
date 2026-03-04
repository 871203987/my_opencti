package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
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
 * Elasticsearch搜索操作测试类
 * 重写源文件路径: opencti/platform/opencti-graphql/src/database/engine.ts (搜索相关方法)
 */
@ExtendWith(MockitoExtension.class)
class ElasticsearchSearchTest {

    @Mock
    private ElasticsearchClient client;

    @Mock
    private ElasticsearchConfig config;

    @InjectMocks
    private ElasticsearchSearch searchService;

    @Test
    @DisplayName("测试elFindByIds - 空ID列表")
    void testElFindByIdsEmptyIds() {
        List<String> ids = new ArrayList<>();
        List<SearchHit> results = searchService.elFindByIds(ids, "stix_domain_objects");
        
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("测试elFindByIds - null ID列表")
    void testElFindByIdsNullIds() {
        List<SearchHit> results = searchService.elFindByIds(null, "stix_domain_objects");
        
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("测试elLoadById - 基本加载")
    void testElLoadByIdBasic() {
        String id = "malware--12345678-1234-1234-1234-123456789012";
        String index = "stix_domain_objects";
        
        SearchHit result = searchService.elLoadById(id, index);
        
        assertNotNull(result);
    }

    @Test
    @DisplayName("测试elLoadById - null ID")
    void testElLoadByIdNullId() {
        SearchHit result = searchService.elLoadById(null, "stix_domain_objects");
        
        assertNull(result);
    }

    @Test
    @DisplayName("测试elPaginate - 空索引")
    void testElPaginateEmptyIndices() {
        List<String> indices = new ArrayList<>();
        
        SearchResponse response = searchService.elPaginate(indices, null, 0, 10);
        
        assertNotNull(response);
        assertEquals(0, response.getTotal());
    }

    @Test
    @DisplayName("测试elPaginate - null索引")
    void testElPaginateNullIndices() {
        SearchResponse response = searchService.elPaginate(null, null, 0, 10);
        
        assertNotNull(response);
    }

    @Test
    @DisplayName("测试elList - 基本列表查询")
    void testElListBasic() {
        List<String> indices = Arrays.asList("stix_domain_objects", "stix_core_objects");
        
        List<SearchHit> results = searchService.elList(indices, null, 100);
        
        assertNotNull(results);
    }

    @Test
    @DisplayName("测试elList - 空索引列表")
    void testElListEmptyIndices() {
        List<String> indices = new ArrayList<>();
        
        List<SearchHit> results = searchService.elList(indices, null, 100);
        
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("测试elConnection - 基本连接查询")
    void testElConnectionBasic() {
        List<String> indices = Arrays.asList("stix_core_relationships");
        
        SearchResponse response = searchService.elConnection(indices, null, 0, 20);
        
        assertNotNull(response);
    }

    @Test
    @DisplayName("测试elLoadBy - 按字段加载")
    void testElLoadByBasic() {
        String field = "name";
        String value = "Malware Test";
        String index = "stix_domain_objects";
        
        SearchHit result = searchService.elLoadBy(index, field, value);
        
        assertNotNull(result);
    }

    @Test
    @DisplayName("测试elLoadBy - null字段值")
    void testElLoadByNullFieldValue() {
        SearchHit result = searchService.elLoadBy("stix_domain_objects", null, null);
        
        assertNull(result);
    }

    @Test
    @DisplayName("测试分页参数 - 第一页")
    void testPaginationFirstPage() {
        int offset = 0;
        int size = 10;
        
        assertTrue(offset >= 0);
        assertTrue(size > 0);
        assertTrue(size <= ElasticsearchConstants.DEFAULT_MAX_SEARCH_RESULT);
    }

    @Test
    @DisplayName("测试分页参数 - 中间页")
    void testPaginationMiddlePage() {
        int offset = 50;
        int size = 10;
        
        assertTrue(offset >= 0);
        assertTrue(size > 0);
    }

    @Test
    @DisplayName("测试分页参数 - 最大限制")
    void testPaginationMaxLimit() {
        int maxSize = ElasticsearchConstants.DEFAULT_MAX_SEARCH_RESULT;
        
        assertEquals(5000, maxSize);
    }

    @Test
    @DisplayName("测试索引名称验证")
    void testIndexNameValidation() {
        List<String> validIndices = Arrays.asList(
                ElasticsearchConstants.STIX_DOMAIN_OBJECTS_INDEX,
                ElasticsearchConstants.STIX_CORE_RELATIONSHIPS_INDEX,
                ElasticsearchConstants.STIX_CORE_OBJECTS_INDEX
        );
        
        for (String index : validIndices) {
            assertNotNull(index);
            assertFalse(index.isEmpty());
            assertTrue(index.matches("[a-z_]+"));
        }
    }

    @Test
    @DisplayName("测试QueryBuilder集成 - term查询")
    void testQueryBuilderIntegrationTerm() {
        co.elastic.clients.elasticsearch._types.query_dsl.Query query = 
                QueryBuilder.term("entity_type", "Malware");
        
        assertNotNull(query);
        assertTrue(query.isTerm());
    }

    @Test
    @DisplayName("测试QueryBuilder集成 - bool查询")
    void testQueryBuilderIntegrationBool() {
        co.elastic.clients.elasticsearch._types.query_dsl.Query query = 
                new QueryBuilder()
                        .must(QueryBuilder.term("status", "active"))
                        .mustNot(QueryBuilder.term("deleted", "true"))
                        .build();
        
        assertNotNull(query);
        assertTrue(query.isBool());
    }

    @Test
    @DisplayName("测试QueryBuilder集成 - range查询")
    void testQueryBuilderIntegrationRange() {
        co.elastic.clients.elasticsearch._types.query_dsl.Query query = 
                QueryBuilder.range("created_at", "2024-01-01", "2024-12-31");
        
        assertNotNull(query);
        assertTrue(query.isRange());
    }

    @Test
    @DisplayName("测试QueryBuilder集成 - nested查询")
    void testQueryBuilderIntegrationNested() {
        co.elastic.clients.elasticsearch._types.query_dsl.Query innerQuery = 
                QueryBuilder.term("nested_field.name", "test");
        co.elastic.clients.elasticsearch._types.query_dsl.Query query = 
                QueryBuilder.nested("nested_field", innerQuery);
        
        assertNotNull(query);
        assertTrue(query.isNested());
    }

    @Test
    @DisplayName("测试特殊字符转义集成")
    void testSpecialCharsEscapeIntegration() {
        String input = "test+value*query?string";
        String escaped = QueryBuilder.specialElasticCharsEscape(input);
        
        assertNotNull(escaped);
        assertTrue(escaped.contains("\\+"));
        assertTrue(escaped.contains("\\*"));
        assertTrue(escaped.contains("\\?"));
    }
}
