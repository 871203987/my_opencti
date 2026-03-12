package io.opencti.database.elasticsearch;

/**
 * Elasticsearch常量定义
 * 重写自: opencti-platform/opencti-graphql/src/database/utils.ts
 *         opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 包含索引名称、分页参数、操作常量等
 */
public final class ElasticsearchConstants {

    private ElasticsearchConstants() {
    }

    // ==================== 引擎类型 ====================
    
    public static final String ENGINE_ELK = "elk";
    public static final String ENGINE_OPENSEARCH = "opensearch";

    // ==================== 索引前缀 ====================
    
    /**
     * 索引前缀
     * 重写自: utils.ts - ES_INDEX_PREFIX (行24)
     */
    public static final String ES_INDEX_PREFIX = "opencti";

    // ==================== 实体索引 ====================
    
    /**
     * 已删除对象索引
     * 重写自: utils.ts - INDEX_DELETED_OBJECTS (行44)
     */
    public static final String INDEX_DELETED_OBJECTS = ES_INDEX_PREFIX + "_deleted_objects";
    public static final String READ_INDEX_DELETED_OBJECTS = INDEX_DELETED_OBJECTS + "*";

    /**
     * 文件索引
     * 重写自: utils.ts - INDEX_FILES (行46)
     */
    public static final String INDEX_FILES = ES_INDEX_PREFIX + "_files";
    public static final String READ_INDEX_FILES = INDEX_FILES + "*";

    /**
     * 历史索引
     * 重写自: utils.ts - INDEX_HISTORY (行48)
     */
    public static final String INDEX_HISTORY = ES_INDEX_PREFIX + "_history";
    public static final String READ_INDEX_HISTORY = INDEX_HISTORY + "*";

    /**
     * 内部对象索引
     * 重写自: utils.ts - INDEX_INTERNAL_OBJECTS (行50)
     */
    public static final String INDEX_INTERNAL_OBJECTS = ES_INDEX_PREFIX + "_internal_objects";
    public static final String READ_INDEX_INTERNAL_OBJECTS = INDEX_INTERNAL_OBJECTS + "*";

    /**
     * STIX元对象索引
     * 重写自: utils.ts - INDEX_STIX_META_OBJECTS (行52)
     */
    public static final String INDEX_STIX_META_OBJECTS = ES_INDEX_PREFIX + "_stix_meta_objects";
    public static final String READ_INDEX_STIX_META_OBJECTS = INDEX_STIX_META_OBJECTS + "*";

    /**
     * STIX域对象索引
     * 重写自: utils.ts - INDEX_STIX_DOMAIN_OBJECTS (行54)
     */
    public static final String INDEX_STIX_DOMAIN_OBJECTS = ES_INDEX_PREFIX + "_stix_domain_objects";
    public static final String READ_INDEX_STIX_DOMAIN_OBJECTS = INDEX_STIX_DOMAIN_OBJECTS + "*";

    /**
     * STIX网络可观测对象索引
     * 重写自: utils.ts - INDEX_STIX_CYBER_OBSERVABLES (行56)
     */
    public static final String INDEX_STIX_CYBER_OBSERVABLES = ES_INDEX_PREFIX + "_stix_cyber_observables";
    public static final String READ_INDEX_STIX_CYBER_OBSERVABLES = INDEX_STIX_CYBER_OBSERVABLES + "*";

    // ==================== 关系索引 ====================
    
    /**
     * 内部关系索引
     * 重写自: utils.ts - INDEX_INTERNAL_RELATIONSHIPS (行60)
     */
    public static final String INDEX_INTERNAL_RELATIONSHIPS = ES_INDEX_PREFIX + "_internal_relationships";
    public static final String READ_INDEX_INTERNAL_RELATIONSHIPS = INDEX_INTERNAL_RELATIONSHIPS + "*";

    /**
     * STIX核心关系索引
     * 重写自: utils.ts - INDEX_STIX_CORE_RELATIONSHIPS (行62)
     */
    public static final String INDEX_STIX_CORE_RELATIONSHIPS = ES_INDEX_PREFIX + "_stix_core_relationships";
    public static final String READ_INDEX_STIX_CORE_RELATIONSHIPS = INDEX_STIX_CORE_RELATIONSHIPS + "*";

    /**
     * STIX目击关系索引
     * 重写自: utils.ts - INDEX_STIX_SIGHTING_RELATIONSHIPS (行64)
     */
    public static final String INDEX_STIX_SIGHTING_RELATIONSHIPS = ES_INDEX_PREFIX + "_stix_sighting_relationships";
    public static final String READ_INDEX_STIX_SIGHTING_RELATIONSHIPS = INDEX_STIX_SIGHTING_RELATIONSHIPS + "*";

    /**
     * STIX元关系索引
     * 重写自: utils.ts - INDEX_STIX_META_RELATIONSHIPS (行68)
     */
    public static final String INDEX_STIX_META_RELATIONSHIPS = ES_INDEX_PREFIX + "_stix_meta_relationships";
    public static final String READ_INDEX_STIX_META_RELATIONSHIPS = INDEX_STIX_META_RELATIONSHIPS + "*";

    // ==================== 推断索引 ====================
    
    /**
     * 推断实体索引
     * 重写自: utils.ts - INDEX_INFERRED_ENTITIES (行72)
     */
    public static final String INDEX_INFERRED_ENTITIES = ES_INDEX_PREFIX + "_inferred_entities";
    public static final String READ_INDEX_INFERRED_ENTITIES = INDEX_INFERRED_ENTITIES + "*";

    /**
     * 推断关系索引
     * 重写自: utils.ts - INDEX_INFERRED_RELATIONSHIPS (行74)
     */
    public static final String INDEX_INFERRED_RELATIONSHIPS = ES_INDEX_PREFIX + "_inferred_relationships";
    public static final String READ_INDEX_INFERRED_RELATIONSHIPS = INDEX_INFERRED_RELATIONSHIPS + "*";

    /**
     * 草稿对象索引
     * 重写自: utils.ts - INDEX_DRAFT_OBJECTS (行76)
     */
    public static final String INDEX_DRAFT_OBJECTS = ES_INDEX_PREFIX + "_draft_objects";
    public static final String READ_INDEX_DRAFT_OBJECTS = INDEX_DRAFT_OBJECTS + "*";

    // ==================== 索引集合 ====================
    
    /**
     * 写入平台索引列表
     * 重写自: utils.ts - WRITE_PLATFORM_INDICES (行88-100)
     */
    public static final java.util.List<String> WRITE_PLATFORM_INDICES = java.util.List.of(
            INDEX_DELETED_OBJECTS,
            INDEX_FILES,
            INDEX_HISTORY,
            INDEX_INTERNAL_OBJECTS,
            INDEX_STIX_META_OBJECTS,
            INDEX_STIX_DOMAIN_OBJECTS,
            INDEX_STIX_CYBER_OBSERVABLES,
            INDEX_INTERNAL_RELATIONSHIPS,
            INDEX_STIX_CORE_RELATIONSHIPS,
            INDEX_INFERRED_ENTITIES,
            INDEX_INFERRED_RELATIONSHIPS,
            INDEX_DRAFT_OBJECTS,
            INDEX_STIX_SIGHTING_RELATIONSHIPS,
            INDEX_STIX_META_RELATIONSHIPS
    );

    /**
     * 读取数据索引列表
     * 重写自: utils.ts - READ_DATA_INDICES (行102)
     */
    public static final java.util.List<String> READ_DATA_INDICES = java.util.List.of(
            READ_INDEX_INTERNAL_OBJECTS,
            READ_INDEX_STIX_META_OBJECTS,
            READ_INDEX_STIX_DOMAIN_OBJECTS,
            READ_INDEX_STIX_CYBER_OBSERVABLES,
            READ_INDEX_INFERRED_ENTITIES
    );

    /**
     * 读取关系索引列表
     * 重写自: utils.ts - READ_RELATIONSHIPS_INDICES (行104)
     */
    public static final java.util.List<String> READ_RELATIONSHIPS_INDICES = java.util.List.of(
            READ_INDEX_INTERNAL_RELATIONSHIPS,
            READ_INDEX_STIX_CORE_RELATIONSHIPS,
            READ_INDEX_STIX_SIGHTING_RELATIONSHIPS,
            READ_INDEX_STIX_META_RELATIONSHIPS,
            READ_INDEX_INFERRED_RELATIONSHIPS
    );

    /**
     * 读取平台索引列表
     * 重写自: utils.ts - READ_PLATFORM_INDICES (行106)
     */
    public static final java.util.List<String> READ_PLATFORM_INDICES = java.util.List.of(
            READ_INDEX_DELETED_OBJECTS,
            READ_INDEX_FILES,
            READ_INDEX_HISTORY,
            READ_INDEX_INTERNAL_OBJECTS,
            READ_INDEX_STIX_META_OBJECTS,
            READ_INDEX_STIX_DOMAIN_OBJECTS,
            READ_INDEX_STIX_CYBER_OBSERVABLES,
            READ_INDEX_INTERNAL_RELATIONSHIPS,
            READ_INDEX_STIX_CORE_RELATIONSHIPS,
            READ_INDEX_STIX_SIGHTING_RELATIONSHIPS,
            READ_INDEX_STIX_META_RELATIONSHIPS,
            READ_INDEX_INFERRED_ENTITIES,
            READ_INDEX_INFERRED_RELATIONSHIPS,
            READ_INDEX_DRAFT_OBJECTS
    );

    // ==================== 分页常量 ====================
    
    /**
     * 最小固定分页
     * 重写自: engine.ts - ES_MINIMUM_FIXED_PAGINATION (行205)
     */
    public static final int ES_MINIMUM_FIXED_PAGINATION = 20;

    /**
     * 默认分页结果数
     * 重写自: engine.ts - ES_DEFAULT_PAGINATION (行206)
     */
    public static final int ES_DEFAULT_PAGINATION = 500;

    /**
     * 最大分页结果数
     * 重写自: engine.ts - ES_MAX_PAGINATION (行207)
     */
    public static final int ES_MAX_PAGINATION = 5000;

    /**
     * 最大批量操作数
     * 重写自: engine.ts - MAX_BULK_OPERATIONS (行208)
     */
    public static final int MAX_BULK_OPERATIONS = 5000;

    /**
     * 最大运行时解析数
     * 重写自: engine.ts - MAX_RUNTIME_RESOLUTION_SIZE (行209)
     */
    public static final int MAX_RUNTIME_RESOLUTION_SIZE = 5000;

    /**
     * 最大关联容器解析数
     * 重写自: engine.ts - MAX_RELATED_CONTAINER_RESOLUTION (行210)
     */
    public static final int MAX_RELATED_CONTAINER_RESOLUTION = 1000;

    /**
     * 最大关联容器对象解析数
     * 重写自: engine.ts - MAX_RELATED_CONTAINER_OBJECT_RESOLUTION (行211)
     */
    public static final int MAX_RELATED_CONTAINER_OBJECT_RESOLUTION = 100000;

    // ==================== 操作常量 ====================
    
    /**
     * 重试冲突次数
     * 重写自: engine.ts - ES_RETRY_ON_CONFLICT (行222)
     */
    public static final int ES_RETRY_ON_CONFLICT = 30;

    /**
     * 批量操作超时
     * 重写自: engine.ts - BULK_TIMEOUT (行223)
     */
    public static final String BULK_TIMEOUT = "1h";

    /**
     * 最大映射数
     * 重写自: engine.ts - ES_MAX_MAPPINGS (行224)
     */
    public static final int ES_MAX_MAPPINGS = 3000;

    /**
     * 最大聚合大小
     * 重写自: engine.ts - MAX_AGGREGATION_SIZE (行225)
     */
    public static final int MAX_AGGREGATION_SIZE = 100;

    // ==================== 角色常量 ====================
    
    /**
     * 来源角色
     * 重写自: engine.ts - ROLE_FROM (行227)
     */
    public static final String ROLE_FROM = "from";

    /**
     * 目标角色
     * 重写自: engine.ts - ROLE_TO (行228)
     */
    public static final String ROLE_TO = "to";

    // ==================== 事件类型 ====================
    
    /**
     * 创建事件
     * 重写自: utils.ts - EVENT_TYPE_CREATE (行31)
     */
    public static final String EVENT_TYPE_CREATE = "create";

    /**
     * 删除事件
     * 重写自: utils.ts - EVENT_TYPE_DELETE (行32)
     */
    public static final String EVENT_TYPE_DELETE = "delete";

    /**
     * 依赖事件
     * 重写自: utils.ts - EVENT_TYPE_DEPENDENCIES (行33)
     */
    public static final String EVENT_TYPE_DEPENDENCIES = "init-dependencies";

    /**
     * 初始化事件
     * 重写自: utils.ts - EVENT_TYPE_INIT (行34)
     */
    public static final String EVENT_TYPE_INIT = "init-create";

    /**
     * 更新事件
     * 重写自: utils.ts - EVENT_TYPE_UPDATE (行35)
     */
    public static final String EVENT_TYPE_UPDATE = "update";

    /**
     * 合并事件
     * 重写自: utils.ts - EVENT_TYPE_MERGE (行36)
     */
    public static final String EVENT_TYPE_MERGE = "merge";

    // ==================== 更新操作 ====================
    
    /**
     * 添加操作
     * 重写自: utils.ts - UPDATE_OPERATION_ADD (行39)
     */
    public static final String UPDATE_OPERATION_ADD = "add";

    /**
     * 替换操作
     * 重写自: utils.ts - UPDATE_OPERATION_REPLACE (行40)
     */
    public static final String UPDATE_OPERATION_REPLACE = "replace";

    /**
     * 删除操作
     * 重写自: utils.ts - UPDATE_OPERATION_REMOVE (行41)
     */
    public static final String UPDATE_OPERATION_REMOVE = "remove";

    // ==================== 测试代码兼容常量 ====================
    
    /**
     * 兼容旧测试代码的索引名称常量
     */
    public static final String STIX_DOMAIN_OBJECTS_INDEX = INDEX_STIX_DOMAIN_OBJECTS;
    public static final String STIX_CORE_RELATIONSHIPS_INDEX = INDEX_STIX_CORE_RELATIONSHIPS;
    public static final String STIX_SIGHTING_RELATIONSHIPS_INDEX = INDEX_STIX_SIGHTING_RELATIONSHIPS;
    public static final String STIX_META_OBJECTS_INDEX = INDEX_STIX_META_OBJECTS;
    public static final String STIX_META_RELATIONSHIPS_INDEX = INDEX_STIX_META_RELATIONSHIPS;
    public static final String STIX_CORE_OBJECTS_INDEX = INDEX_STIX_CYBER_OBSERVABLES;
    public static final String WORKBENCH_INDEX = INDEX_DRAFT_OBJECTS;
    public static final String INTERNAL_INDEX = INDEX_INTERNAL_OBJECTS;
    
    /**
     * 索引名称列表
     */
    public static final java.util.List<String> INDEX_NAMES = WRITE_PLATFORM_INDICES;
    
    /**
     * 搜索默认参数
     */
    public static final int DEFAULT_MAX_SEARCH_RESULT = ES_MAX_PAGINATION;
    public static final int DEFAULT_SEARCH_OFFSET = 0;
    public static final String DEFAULT_SEARCH_ORDER = "asc";
    
    /**
     * 操作类型常量
     */
    public static final String OPERATION_CREATE = EVENT_TYPE_CREATE;
    public static final String OPERATION_UPDATE = EVENT_TYPE_UPDATE;
    public static final String OPERATION_DELETE = EVENT_TYPE_DELETE;
    public static final String OPERATION_INDEX = "index";

    // ==================== 特殊字段 ====================
    
    /**
     * 内部ID字段
     */
    public static final String INTERNAL_ID_FIELD = "internal_id";

    /**
     * 标准ID字段
     */
    public static final String STANDARD_ID_FIELD = "standard_id";

    /**
     * 实体类型字段
     */
    public static final String ENTITY_TYPE_FIELD = "entity_type";

    /**
     * 内部来源字段
     * 重写自: identifier.ts - INTERNAL_FROM_FIELD
     */
    public static final String INTERNAL_FROM_FIELD = "from";

    /**
     * 内部目标字段
     * 重写自: identifier.ts - INTERNAL_TO_FIELD
     */
    public static final String INTERNAL_TO_FIELD = "to";

    // ==================== 工具方法 ====================
    
    /**
     * 判断是否为推断索引
     * 重写自: utils.ts - isInferredIndex (行79-81)
     */
    public static boolean isInferredIndex(String index) {
        if (index == null || index.isEmpty()) {
            return false;
        }
        return index.startsWith(INDEX_INFERRED_ENTITIES) || index.startsWith(INDEX_INFERRED_RELATIONSHIPS);
    }

    /**
     * 判断是否为草稿索引
     * 重写自: utils.ts - isDraftIndex (行82)
     */
    public static boolean isDraftIndex(String index) {
        if (index == null || index.isEmpty()) {
            return false;
        }
        return index.startsWith(INDEX_DRAFT_OBJECTS);
    }

    /**
     * 根据概念类型推断索引
     * 重写自: utils.ts - inferIndexFromConceptType
     */
    public static String inferIndexFromConceptType(String conceptType) {
        // TODO: 实现完整的类型推断逻辑
        return INDEX_STIX_DOMAIN_OBJECTS;
    }
}
