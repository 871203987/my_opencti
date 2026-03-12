package io.opencti.database.elasticsearch;

import java.util.HashMap;
import java.util.Map;

/**
 * Elasticsearch映射管理类
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供映射生成和管理功能
 */
public final class ElasticsearchMapping {

    private ElasticsearchMapping() {
    }

    // ==================== 基础映射类型 ====================
    
    /**
     * 短字符串映射
     * 重写自: engine.ts - shortMapping (行229)
     */
    public static final Map<String, Object> SHORT_MAPPING = Map.of(
            "type", "keyword"
    );

    /**
     * 长文本映射
     * 重写自: engine.ts - textMapping (行230)
     */
    public static final Map<String, Object> TEXT_MAPPING = Map.of(
            "type", "text"
    );

    /**
     * 日期映射
     * 重写自: engine.ts - dateMapping (行231)
     */
    public static final Map<String, Object> DATE_MAPPING = Map.of(
            "type", "date"
    );

    /**
     * 布尔映射
     * 重写自: engine.ts - booleanMapping (行232)
     */
    public static final Map<String, Object> BOOLEAN_MAPPING = Map.of(
            "type", "boolean"
    );

    /**
     * 数值映射生成器
     * 重写自: engine.ts - numericMapping (行233-243)
     * 
     * @param precision 精度
     * @return 映射配置
     */
    public static Map<String, Object> numericMapping(String precision) {
        if ("long".equals(precision)) {
            return Map.of("type", "long");
        } else if ("double".equals(precision)) {
            return Map.of("type", "double");
        } else if ("float".equals(precision)) {
            return Map.of("type", "float");
        }
        return Map.of("type", "integer");
    }

    // ==================== 短字符串格式 ====================
    
    /**
     * 短字符串格式列表
     * 重写自: engine.ts - shortStringFormats (行246)
     */
    public static final java.util.List<String> SHORT_STRING_FORMATS = java.util.List.of(
            "short",
            "date",
            "datetime",
            "time",
            "integer",
            "float",
            "double",
            "long",
            "boolean",
            "enum",
            "text-attached",
            "text-attached-text",
            "json",
            "markdown",
            "html"
    );

    /**
     * 长字符串格式列表
     * 重写自: engine.ts - longStringFormats (行247)
     */
    public static final java.util.List<String> LONG_STRING_FORMATS = java.util.List.of(
            "text",
            "textarea"
    );

    // ==================== 映射生成器 ====================

    /**
     * 属性映射生成器（简化版，兼容测试代码）
     *
     * @param type 属性类型
     * @return 映射配置
     */
    public static Map<String, Object> attributeMappingGenerator(String type) {
        return attributeMappingGenerator(type, null, null);
    }

    /**
     * 属性映射生成器
     * 重写自: engine.ts - attributeMappingGenerator() (行877-915)
     *
     * @param type 属性类型
     * @param format 属性格式
     * @param precision 数值精度
     * @return 映射配置
     */
    public static Map<String, Object> attributeMappingGenerator(String type, String format, String precision) {
        if ("string".equals(type)) {
            if (SHORT_STRING_FORMATS.contains(format)) {
                return SHORT_MAPPING;
            }
            if (LONG_STRING_FORMATS.contains(format)) {
                return TEXT_MAPPING;
            }
            throw new IllegalArgumentException("Cant generated string mapping for format: " + format);
        }
        if ("date".equals(type)) {
            return DATE_MAPPING;
        }
        if ("numeric".equals(type)) {
            return numericMapping(precision);
        }
        if ("boolean".equals(type)) {
            return BOOLEAN_MAPPING;
        }
        if ("object".equals(type)) {
            // For flat object
            if ("flat".equals(format)) {
                return Map.of("type", "flattened");
            }
            // For nested object
            if ("nested".equals(format)) {
                return Map.of(
                        "type", "nested",
                        "dynamic", "strict"
                );
            }
            // For standard object
            return Map.of(
                    "dynamic", "strict"
            );
        }
        throw new IllegalArgumentException("Cant generated mapping for type: " + type);
    }

    /**
     * 引擎映射生成器
     * 重写自: engine.ts - engineMappingGenerator() (行962-964)
     * 
     * @return 完整的映射配置
     */
    public static Map<String, Object> engineMappingGenerator() {
        Map<String, Object> properties = new HashMap<>();
        
        // 基础字段映射
        properties.put("internal_id", SHORT_MAPPING);
        properties.put("standard_id", SHORT_MAPPING);
        properties.put("entity_type", SHORT_MAPPING);
        properties.put("created_at", DATE_MAPPING);
        properties.put("updated_at", DATE_MAPPING);
        
        // 内部字段
        properties.put("_index", SHORT_MAPPING);
        properties.put("id", SHORT_MAPPING);
        properties.put("sort", Map.of("type", "integer"));
        
        // 关系字段
        properties.put("from", SHORT_MAPPING);
        properties.put("to", SHORT_MAPPING);
        properties.put("fromId", SHORT_MAPPING);
        properties.put("toId", SHORT_MAPPING);
        properties.put("fromType", SHORT_MAPPING);
        properties.put("toType", SHORT_MAPPING);
        
        // 构建完整映射
        Map<String, Object> mappings = new HashMap<>();
        mappings.put("dynamic", "strict");
        mappings.put("properties", properties);
        
        return mappings;
    }

    /**
     * 构建索引映射
     * 重写自: engine.ts - buildIndexMapping()
     * 
     * @param indexName 索引名
     * @return 映射配置
     */
    public static Map<String, Object> buildIndexMapping(String indexName) {
        Map<String, Object> baseMapping = engineMappingGenerator();
        
        // 根据索引类型添加特定字段
        if (indexName.contains("relationship")) {
            addRelationshipFields(baseMapping);
        } else if (indexName.contains("stix")) {
            addStixFields(baseMapping);
        }
        
        return baseMapping;
    }

    /**
     * 添加关系字段
     */
    private static void addRelationshipFields(Map<String, Object> mapping) {
        @SuppressWarnings("unchecked")
        Map<String, Object> properties = (Map<String, Object>) mapping.get("properties");
        if (properties != null) {
            properties.put("relationship_type", SHORT_MAPPING);
            properties.put("confidence", Map.of("type", "integer"));
            properties.put("start_time", DATE_MAPPING);
            properties.put("stop_time", DATE_MAPPING);
            properties.put("description", TEXT_MAPPING);
        }
    }

    /**
     * 添加STIX字段
     */
    private static void addStixFields(Map<String, Object> mapping) {
        @SuppressWarnings("unchecked")
        Map<String, Object> properties = (Map<String, Object>) mapping.get("properties");
        if (properties != null) {
            properties.put("name", TEXT_MAPPING);
            properties.put("description", TEXT_MAPPING);
            properties.put("labels", Map.of("type", "keyword"));
            properties.put("confidence", Map.of("type", "integer"));
            properties.put("revoked", BOOLEAN_MAPPING);
            properties.put("lang", SHORT_MAPPING);
            properties.put("external_references", Map.of(
                    "type", "nested",
                    "dynamic", "true"
            ));
            properties.put("object_marking_refs", Map.of("type", "keyword"));
            properties.put("granular_markings", Map.of(
                    "type", "nested",
                    "dynamic", "true"
            ));
        }
    }

    /**
     * 获取默认索引设置
     * 重写自: engine.ts - updateCoreSettings() (行841-874)
     * 
     * @return 索引设置
     */
    public static Map<String, Object> getDefaultIndexSettings() {
        Map<String, Object> settings = new HashMap<>();
        settings.put("index.max_result_window", 100000);
        settings.put("index.number_of_shards", 1);
        settings.put("index.number_of_replicas", 0);
        
        // 分析器配置
        Map<String, Object> analysis = new HashMap<>();
        Map<String, Object> normalizer = new HashMap<>();
        normalizer.put("string_normalizer", Map.of(
                "type", "custom",
                "filter", java.util.List.of("lowercase", "asciifolding")
        ));
        analysis.put("normalizer", normalizer);
        settings.put("analysis", analysis);
        
        return settings;
    }
}
