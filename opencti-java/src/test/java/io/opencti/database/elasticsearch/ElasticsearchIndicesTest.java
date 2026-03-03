package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * ElasticsearchIndices测试类
 * 重写源文件路径: opencti/platform/opencti-graphql/src/database/engine.ts (索引管理相关方法)
 */
@ExtendWith(MockitoExtension.class)
class ElasticsearchIndicesTest {

    @Mock
    private ElasticsearchClient client;

    @Mock
    private ElasticsearchIndicesClient indicesClient;

    @BeforeEach
    void setUp() {
        when(client.indices()).thenReturn(indicesClient);
    }

    @Test
    @DisplayName("测试索引名称常量")
    void testIndexConstants() {
        assertNotNull(ElasticsearchConstants.STIX_DOMAIN_OBJECTS_INDEX);
        assertNotNull(ElasticsearchConstants.STIX_CORE_RELATIONSHIPS_INDEX);
        assertNotNull(ElasticsearchConstants.STIX_SIGHTING_RELATIONSHIPS_INDEX);
        assertNotNull(ElasticsearchConstants.STIX_META_OBJECTS_INDEX);
        assertNotNull(ElasticsearchConstants.STIX_META_RELATIONSHIPS_INDEX);
        assertNotNull(ElasticsearchConstants.STIX_CORE_OBJECTS_INDEX);
        assertNotNull(ElasticsearchConstants.WORKBENCH_INDEX);
        assertNotNull(ElasticsearchConstants.INTERNAL_INDEX);
    }

    @Test
    @DisplayName("测试平台索引列表")
    void testPlatformIndicesList() {
        List<String> platformIndices = ElasticsearchConstants.INDEX_NAMES;
        
        assertNotNull(platformIndices);
        assertFalse(platformIndices.isEmpty());
        assertTrue(platformIndices.contains(ElasticsearchConstants.STIX_DOMAIN_OBJECTS_INDEX));
        assertTrue(platformIndices.contains(ElasticsearchConstants.STIX_CORE_RELATIONSHIPS_INDEX));
        assertTrue(platformIndices.contains(ElasticsearchConstants.STIX_SIGHTING_RELATIONSHIPS_INDEX));
        assertTrue(platformIndices.contains(ElasticsearchConstants.STIX_META_OBJECTS_INDEX));
        assertTrue(platformIndices.contains(ElasticsearchConstants.STIX_META_RELATIONSHIPS_INDEX));
        assertTrue(platformIndices.contains(ElasticsearchConstants.STIX_CORE_OBJECTS_INDEX));
        assertTrue(platformIndices.contains(ElasticsearchConstants.WORKBENCH_INDEX));
    }

    @Test
    @DisplayName("测试映射定义完整性")
    void testMappingDefinitions() {
        Map<String, Object> shortMapping = ElasticsearchMapping.SHORT_MAPPING;
        assertNotNull(shortMapping);
        assertEquals("short", shortMapping.get("type"));
        
        Map<String, Object> textMapping = ElasticsearchMapping.TEXT_MAPPING;
        assertNotNull(textMapping);
        assertEquals("text", textMapping.get("type"));
        
        Map<String, Object> dateMapping = ElasticsearchMapping.DATE_MAPPING;
        assertNotNull(dateMapping);
        assertEquals("date", dateMapping.get("type"));
    }

    @Test
    @DisplayName("测试属性映射生成器 - 字符串类型")
    void testAttributeMappingGeneratorString() {
        Map<String, Object> mapping = ElasticsearchMapping.attributeMappingGenerator("string");
        assertNotNull(mapping);
        assertEquals("text", mapping.get("type"));
    }

    @Test
    @DisplayName("测试属性映射生成器 - 整数类型")
    void testAttributeMappingGeneratorInteger() {
        Map<String, Object> mapping = ElasticsearchMapping.attributeMappingGenerator("integer");
        assertNotNull(mapping);
        assertEquals("integer", mapping.get("type"));
    }

    @Test
    @DisplayName("测试属性映射生成器 - 浮点类型")
    void testAttributeMappingGeneratorFloat() {
        Map<String, Object> mapping = ElasticsearchMapping.attributeMappingGenerator("float");
        assertNotNull(mapping);
        assertEquals("float", mapping.get("type"));
    }

    @Test
    @DisplayName("测试属性映射生成器 - 布尔类型")
    void testAttributeMappingGeneratorBoolean() {
        Map<String, Object> mapping = ElasticsearchMapping.attributeMappingGenerator("boolean");
        assertNotNull(mapping);
        assertEquals("boolean", mapping.get("type"));
    }

    @Test
    @DisplayName("测试属性映射生成器 - 日期类型")
    void testAttributeMappingGeneratorDate() {
        Map<String, Object> mapping = ElasticsearchMapping.attributeMappingGenerator("date");
        assertNotNull(mapping);
        assertEquals("date", mapping.get("type"));
    }

    @Test
    @DisplayName("测试属性映射生成器 - 未知类型默认处理")
    void testAttributeMappingGeneratorUnknownType() {
        Map<String, Object> mapping = ElasticsearchMapping.attributeMappingGenerator("unknown_type");
        assertNotNull(mapping);
    }

    @Test
    @DisplayName("测试属性映射生成器 - 空类型处理")
    void testAttributeMappingGeneratorNullType() {
        Map<String, Object> mapping = ElasticsearchMapping.attributeMappingGenerator(null);
        assertNotNull(mapping);
    }

    @Test
    @DisplayName("测试引擎映射生成器")
    void testEngineMappingGenerator() {
        Map<String, Object> mapping = ElasticsearchMapping.engineMappingGenerator();
        assertNotNull(mapping);
        assertTrue(mapping.containsKey("properties") || mapping.containsKey("dynamic_templates"));
    }

    @Test
    @DisplayName("测试分页常量")
    void testPaginationConstants() {
        assertEquals(5000, ElasticsearchConstants.DEFAULT_MAX_SEARCH_RESULT);
        assertNotNull(ElasticsearchConstants.DEFAULT_SEARCH_OFFSET);
        assertNotNull(ElasticsearchConstants.DEFAULT_SEARCH_ORDER);
    }

    @Test
    @DisplayName("测试操作常量")
    void testOperationConstants() {
        assertNotNull(ElasticsearchConstants.OPERATION_CREATE);
        assertNotNull(ElasticsearchConstants.OPERATION_UPDATE);
        assertNotNull(ElasticsearchConstants.OPERATION_DELETE);
        assertNotNull(ElasticsearchConstants.OPERATION_INDEX);
    }

    @Test
    @DisplayName("测试索引命名规范")
    void testIndexNamingConvention() {
        List<String> indices = ElasticsearchConstants.INDEX_NAMES;
        for (String index : indices) {
            assertFalse(index.contains(" "));
            assertFalse(index.contains("."));
            assertTrue(index.matches("[a-z_]+"));
        }
    }

    @Test
    @DisplayName("测试内部索引配置")
    void testInternalIndexConfiguration() {
        assertEquals("opencti_internal", ElasticsearchConstants.INTERNAL_INDEX);
    }
}
