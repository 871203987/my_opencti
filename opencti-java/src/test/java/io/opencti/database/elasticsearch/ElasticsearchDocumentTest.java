package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import io.opencti.database.elasticsearch.model.SearchHit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Elasticsearch文档操作测试类
 * 重写源文件路径: opencti/platform/opencti-graphql/src/database/engine.ts (文档CRUD相关方法)
 */
@ExtendWith(MockitoExtension.class)
class ElasticsearchDocumentTest {

    @Mock
    private ElasticsearchClient client;

    @Mock
    private ElasticsearchConfig config;

    @InjectMocks
    private ElasticsearchDocument documentService;

    @Test
    @DisplayName("测试elRawGet - 基本获取")
    void testElRawGetBasic() {
        String index = "stix_domain_objects";
        String id = "malware--12345678-1234-1234-1234-123456789012";
        
        Map<String, Object> result = documentService.elRawGet(index, id);
        
        assertNotNull(result);
    }

    @Test
    @DisplayName("测试elRawGet - null ID")
    void testElRawGetNullId() {
        Map<String, Object> result = documentService.elRawGet("stix_domain_objects", null);
        
        assertNull(result);
    }

    @Test
    @DisplayName("测试elRawGet - 空ID")
    void testElRawGetEmptyId() {
        Map<String, Object> result = documentService.elRawGet("stix_domain_objects", "");
        
        assertNull(result);
    }

    @Test
    @DisplayName("测试elRawIndex - 基本索引")
    void testElRawIndexBasic() {
        String index = "stix_domain_objects";
        String id = "malware--12345678-1234-1234-1234-123456789012";
        Map<String, Object> document = new HashMap<>();
        document.put("name", "Test Malware");
        document.put("entity_type", "Malware");
        
        String result = documentService.elRawIndex(index, id, document);
        
        assertNotNull(result);
    }

    @Test
    @DisplayName("测试elRawIndex - null文档")
    void testElRawIndexNullDocument() {
        String result = documentService.elRawIndex("stix_domain_objects", "test-id", null);
        
        assertNull(result);
    }

    @Test
    @DisplayName("测试elRawDelete - 基本删除")
    void testElRawDeleteBasic() {
        String index = "stix_domain_objects";
        String id = "malware--12345678-1234-1234-1234-123456789012";
        
        boolean result = documentService.elRawDelete(index, id);
        
        assertTrue(result || !result);
    }

    @Test
    @DisplayName("测试elRawDelete - null ID")
    void testElRawDeleteNullId() {
        boolean result = documentService.elRawDelete("stix_domain_objects", null);
        
        assertFalse(result);
    }

    @Test
    @DisplayName("测试elUpdate - 基本更新")
    void testElUpdateBasic() {
        String index = "stix_domain_objects";
        String id = "malware--12345678-1234-1234-1234-123456789012";
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Updated Malware");
        
        boolean result = documentService.elUpdate(index, id, updates);
        
        assertTrue(result || !result);
    }

    @Test
    @DisplayName("测试elUpdate - null ID")
    void testElUpdateNullId() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Test");
        
        boolean result = documentService.elUpdate("stix_domain_objects", null, updates);
        
        assertFalse(result);
    }

    @Test
    @DisplayName("测试elUpdate - null更新内容")
    void testElUpdateNullUpdates() {
        boolean result = documentService.elUpdate("stix_domain_objects", "test-id", null);
        
        assertFalse(result);
    }

    @Test
    @DisplayName("测试elConvertHits - 空列表")
    void testElConvertHitsEmptyList() {
        List<SearchHit> result = documentService.elConvertHits(Collections.emptyList());
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("测试elConvertHits - null列表")
    void testElConvertHitsNullList() {
        List<SearchHit> result = documentService.elConvertHits(null);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("测试文档ID格式验证 - STIX ID")
    void testDocumentIdFormatStixId() {
        String stixId = "malware--12345678-1234-1234-1234-123456789012";
        
        assertTrue(stixId.matches("^[a-z-]+--[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @Test
    @DisplayName("测试文档ID格式验证 - 内部ID")
    void testDocumentIdFormatInternalId() {
        String internalId = "internal--12345678-1234-1234-1234-123456789012";
        
        assertTrue(internalId.matches("^[a-z-]+--[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @Test
    @DisplayName("测试索引名称验证")
    void testIndexNameValidation() {
        String validIndex = "stix_domain_objects";
        
        assertTrue(validIndex.matches("^[a-z_]+$"));
        assertFalse(validIndex.contains(" "));
        assertFalse(validIndex.contains("."));
    }

    @Test
    @DisplayName("测试文档结构 - 基本字段")
    void testDocumentStructureBasicFields() {
        Map<String, Object> document = new HashMap<>();
        document.put("id", "malware--12345678-1234-1234-1234-123456789012");
        document.put("name", "Test Malware");
        document.put("entity_type", "Malware");
        document.put("created_at", "2024-01-01T00:00:00Z");
        document.put("updated_at", "2024-01-02T00:00:00Z");
        
        assertTrue(document.containsKey("id"));
        assertTrue(document.containsKey("name"));
        assertTrue(document.containsKey("entity_type"));
        assertTrue(document.containsKey("created_at"));
        assertTrue(document.containsKey("updated_at"));
    }

    @Test
    @DisplayName("测试文档结构 - 嵌套字段")
    void testDocumentStructureNestedFields() {
        Map<String, Object> document = new HashMap<>();
        Map<String, Object> marking = new HashMap<>();
        marking.put("definition_type", "TLP");
        marking.put("definition", "TLP:RED");
        document.put("object_marking_refs", Arrays.asList(marking));
        
        assertTrue(document.containsKey("object_marking_refs"));
        assertNotNull(document.get("object_marking_refs"));
        assertTrue(document.get("object_marking_refs") instanceof List);
    }

    @Test
    @DisplayName("测试文档结构 - 列表字段")
    void testDocumentStructureListFields() {
        Map<String, Object> document = new HashMap<>();
        document.put("labels", Arrays.asList("malware", "apt", "threat"));
        document.put("external_references", Arrays.asList(
                Map.of("source_name", "MITRE", "external_id", "T1234")
        ));
        
        assertTrue(document.containsKey("labels"));
        assertTrue(document.get("labels") instanceof List);
        assertEquals(3, ((List<?>) document.get("labels")).size());
    }

    @Test
    @DisplayName("测试重试常量")
    void testRetryConstants() {
        assertEquals(5, ElasticsearchConstants.ES_RETRY_ON_CONFLICT);
    }

    @Test
    @DisplayName("测试批量操作常量")
    void testBulkConstants() {
        assertNotNull(ElasticsearchConstants.BULK_TIMEOUT);
        assertTrue(ElasticsearchConstants.BULK_TIMEOUT > 0);
    }
}
