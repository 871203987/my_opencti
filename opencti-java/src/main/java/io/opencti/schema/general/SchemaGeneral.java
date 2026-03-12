package io.opencti.schema.general;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Schema 通用常量定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/general.js
 *
 * 该文件定义了OpenCTI系统中使用的基础常量、类型标识和工具函数，
 * 是整个Schema层的基础，其他所有Schema模块都依赖这些定义。
 */
public final class SchemaGeneral {

    // 私有构造函数，防止实例化
    private SchemaGeneral() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== STIX 类型常量 ====================
    // 原代码: export const STIX_TYPE_RELATION = 'relationship';
    public static final String STIX_TYPE_RELATION = "relationship";

    // 原代码: export const STIX_TYPE_SIGHTING = 'sighting';
    public static final String STIX_TYPE_SIGHTING = "sighting";

    // ==================== 权限常量 ====================
    // 原代码: export const KNOWLEDGE_UPDATE = 'KNUPDATE';
    public static final String KNOWLEDGE_UPDATE = "KNUPDATE";

    // 原代码: export const KNOWLEDGE_FRONTEND_EXPORT = 'KNFRONTENDEXPORT';
    public static final String KNOWLEDGE_FRONTEND_EXPORT = "KNFRONTENDEXPORT";

    // 原代码: export const KNOWLEDGE_COLLABORATION = 'KNPARTICIPATE';
    public static final String KNOWLEDGE_COLLABORATION = "KNPARTICIPATE";

    // ==================== ID 类型常量 ====================
    // 原代码: export const ID_INTERNAL = 'internal_id';
    public static final String ID_INTERNAL = "internal_id";

    // 原代码: export const ID_INFERRED = 'inferred_id';
    public static final String ID_INFERRED = "inferred_id";

    // 原代码: export const ID_STANDARD = 'standard_id';
    public static final String ID_STANDARD = "standard_id";

    // 原代码: export const INTERNAL_IDS_ALIASES = 'i_aliases_ids';
    public static final String INTERNAL_IDS_ALIASES = "i_aliases_ids";

    // 原代码: export const IDS_STIX = 'x_opencti_stix_ids';
    public static final String IDS_STIX = "x_opencti_stix_ids";

    // 原代码: export const BASE_TYPE_RELATION = 'RELATION';
    public static final String BASE_TYPE_RELATION = "RELATION";

    // 原代码: export const BASE_TYPE_ENTITY = 'ENTITY';
    public static final String BASE_TYPE_ENTITY = "ENTITY";

    // ==================== 输入字段常量 ====================
    // 原代码: export const INPUT_GRANTED_REFS = 'objectOrganization';
    public static final String INPUT_GRANTED_REFS = "objectOrganization";

    // 原代码: export const INPUT_EXTERNAL_REFS = 'externalReferences';
    public static final String INPUT_EXTERNAL_REFS = "externalReferences";

    // 原代码: export const INPUT_WORKS = 'works';
    public static final String INPUT_WORKS = "works";

    // 原代码: export const INPUT_INTERNAL_FILES = 'internalFiles';
    public static final String INPUT_INTERNAL_FILES = "internalFiles";

    // 原代码: export const INPUT_KILLCHAIN = 'killChainPhases';
    public static final String INPUT_KILLCHAIN = "killChainPhases";

    // 原代码: export const INPUT_CREATED_BY = 'createdBy';
    public static final String INPUT_CREATED_BY = "createdBy";

    // 原代码: export const INPUT_LABELS = 'objectLabel';
    public static final String INPUT_LABELS = "objectLabel";

    // 原代码: export const INPUT_MARKINGS = 'objectMarking';
    public static final String INPUT_MARKINGS = "objectMarking";

    // 原代码: export const INPUT_ASSIGNEE = 'objectAssignee';
    public static final String INPUT_ASSIGNEE = "objectAssignee";

    // 原代码: export const INPUT_PARTICIPANT = 'objectParticipant';
    public static final String INPUT_PARTICIPANT = "objectParticipant";

    // 原代码: export const INPUT_OBJECTS = 'objects';
    public static final String INPUT_OBJECTS = "objects";

    // 原代码: export const INPUT_DOMAIN_FROM = 'from';
    public static final String INPUT_DOMAIN_FROM = "from";

    // 原代码: export const INPUT_DOMAIN_TO = 'to';
    public static final String INPUT_DOMAIN_TO = "to";

    // 原代码: export const INPUT_BORN_IN = 'bornIn';
    public static final String INPUT_BORN_IN = "bornIn";

    // 原代码: export const INPUT_ETHNICITY = 'ethnicity';
    public static final String INPUT_ETHNICITY = "ethnicity";

    // 原代码: export const INPUT_AUTHORIZED_MEMBERS = 'restricted_members';
    public static final String INPUT_AUTHORIZED_MEMBERS = "restricted_members";

    // 原代码: export const INPUT_IN_PIR = 'inPir';
    public static final String INPUT_IN_PIR = "inPir";

    // ==================== 前缀常量 ====================
    // 原代码: export const REL_INDEX_PREFIX = 'rel_';
    public static final String REL_INDEX_PREFIX = "rel_";

    // 原代码: export const INTERNAL_PREFIX = 'i_';
    public static final String INTERNAL_PREFIX = "i_";

    // 原代码: export const RULE_PREFIX = 'i_rule_';
    public static final String RULE_PREFIX = "i_rule_";

    // ==================== 连接器类型常量 ====================
    // 原代码: export const CONNECTOR_INTERNAL_ENRICHMENT = 'INTERNAL_ENRICHMENT';
    public static final String CONNECTOR_INTERNAL_ENRICHMENT = "INTERNAL_ENRICHMENT";

    // 原代码: export const CONNECTOR_INTERNAL_IMPORT_FILE = 'INTERNAL_IMPORT_FILE';
    public static final String CONNECTOR_INTERNAL_IMPORT_FILE = "INTERNAL_IMPORT_FILE";

    // 原代码: export const CONNECTOR_INTERNAL_ANALYSIS = 'INTERNAL_ANALYSIS';
    public static final String CONNECTOR_INTERNAL_ANALYSIS = "INTERNAL_ANALYSIS";

    // 原代码: export const CONNECTOR_INTERNAL_EXPORT_FILE = 'INTERNAL_EXPORT_FILE';
    public static final String CONNECTOR_INTERNAL_EXPORT_FILE = "INTERNAL_EXPORT_FILE";

    // 原代码: export const CONNECTOR_INTERNAL_NOTIFICATION = 'INTERNAL_NOTIFICATION';
    public static final String CONNECTOR_INTERNAL_NOTIFICATION = "INTERNAL_NOTIFICATION";

    // 原代码: export const CONNECTOR_INTERNAL_INGESTION = 'INTERNAL_INGESTION';
    public static final String CONNECTOR_INTERNAL_INGESTION = "INTERNAL_INGESTION";

    // ==================== UUID 常量 ====================
    // 原代码: export const OASIS_NAMESPACE = '00abedb4-aa42-466c-9c01-fed23315a9b7';
    public static final String OASIS_NAMESPACE = "00abedb4-aa42-466c-9c01-fed23315a9b7";

    // 原代码: export const OPENCTI_NAMESPACE = 'b639ff3b-00eb-42ed-aa36-a8dd6f8fb4cf';
    public static final String OPENCTI_NAMESPACE = "b639ff3b-00eb-42ed-aa36-a8dd6f8fb4cf";

    // 原代码: export const OPENCTI_PLATFORM_UUID = 'd06053cb-7123-404b-b092-6606411702d2';
    public static final String OPENCTI_PLATFORM_UUID = "d06053cb-7123-404b-b092-6606411702d2";

    // 原代码: export const OPENCTI_ADMIN_UUID = '88ec0c6a-13ce-5e39-b486-354fe4a7084f';
    public static final String OPENCTI_ADMIN_UUID = "88ec0c6a-13ce-5e39-b486-354fe4a7084f";

    // 原代码: export const OPENCTI_SYSTEM_UUID = '6a4b11e1-90ca-4e42-ba42-db7bc7f7d505';
    public static final String OPENCTI_SYSTEM_UUID = "6a4b11e1-90ca-4e42-ba42-db7bc7f7d505";

    // ==================== 关系抽象类型常量 ====================
    // 原代码: export const ABSTRACT_BASIC_RELATIONSHIP = 'basic-relationship';
    public static final String ABSTRACT_BASIC_RELATIONSHIP = "basic-relationship";

    // 原代码: export const ABSTRACT_INTERNAL_RELATIONSHIP = 'internal-relationship';
    public static final String ABSTRACT_INTERNAL_RELATIONSHIP = "internal-relationship";

    // 原代码: export const ABSTRACT_STIX_RELATIONSHIP = 'stix-relationship';
    public static final String ABSTRACT_STIX_RELATIONSHIP = "stix-relationship";

    // 原代码: export const ABSTRACT_STIX_CORE_RELATIONSHIP = 'stix-core-relationship';
    public static final String ABSTRACT_STIX_CORE_RELATIONSHIP = "stix-core-relationship";

    // 原代码: export const ABSTRACT_STIX_CYBER_OBSERVABLE_RELATIONSHIP = 'stix-cyber-observable-relationship';
    public static final String ABSTRACT_STIX_CYBER_OBSERVABLE_RELATIONSHIP = "stix-cyber-observable-relationship";

    // 原代码: export const ABSTRACT_STIX_REF_RELATIONSHIP = 'stix-ref-relationship';
    public static final String ABSTRACT_STIX_REF_RELATIONSHIP = "stix-ref-relationship";

    // 原代码: export const ABSTRACT_STIX_META_RELATIONSHIP = 'stix-meta-relationship';
    public static final String ABSTRACT_STIX_META_RELATIONSHIP = "stix-meta-relationship";

    // ==================== 实体抽象类型常量 ====================
    // 原代码: export const ABSTRACT_BASIC_OBJECT = 'Basic-Object';
    public static final String ABSTRACT_BASIC_OBJECT = "Basic-Object";

    // 原代码: export const ABSTRACT_STIX_OBJECT = 'Stix-Object';
    public static final String ABSTRACT_STIX_OBJECT = "Stix-Object";

    // 原代码: export const ABSTRACT_STIX_META_OBJECT = 'Stix-Meta-Object';
    public static final String ABSTRACT_STIX_META_OBJECT = "Stix-Meta-Object";

    // 原代码: export const ABSTRACT_STIX_CORE_OBJECT = 'Stix-Core-Object';
    public static final String ABSTRACT_STIX_CORE_OBJECT = "Stix-Core-Object";

    // 原代码: export const ABSTRACT_STIX_DOMAIN_OBJECT = 'Stix-Domain-Object';
    public static final String ABSTRACT_STIX_DOMAIN_OBJECT = "Stix-Domain-Object";

    // 原代码: export const ABSTRACT_STIX_CYBER_OBSERVABLE = 'Stix-Cyber-Observable';
    public static final String ABSTRACT_STIX_CYBER_OBSERVABLE = "Stix-Cyber-Observable";

    // 原代码: export const ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE = 'Hashed-Observable';
    public static final String ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE = "Hashed-Observable";

    // 原代码: export const ABSTRACT_INTERNAL_OBJECT = 'Internal-Object';
    public static final String ABSTRACT_INTERNAL_OBJECT = "Internal-Object";

    // ==================== 抽象类型常量 ====================
    // 原代码: export const ENTITY_TYPE_CONTAINER = 'Container';
    public static final String ENTITY_TYPE_CONTAINER = "Container";

    // 原代码: export const ENTITY_TYPE_IDENTITY = 'Identity';
    public static final String ENTITY_TYPE_IDENTITY = "Identity";

    // 原代码: export const ENTITY_TYPE_LOCATION = 'Location';
    public static final String ENTITY_TYPE_LOCATION = "Location";

    // 原代码: export const ENTITY_TYPE_THREAT_ACTOR = 'Threat-Actor';
    public static final String ENTITY_TYPE_THREAT_ACTOR = "Threat-Actor";

    // ==================== 抽象类型集合 ====================
    // 原代码: export const ABSTRACT_TYPES = [...]
    public static final Set<String> ABSTRACT_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ABSTRACT_BASIC_OBJECT,
            ABSTRACT_INTERNAL_OBJECT,
            ABSTRACT_STIX_OBJECT,
            ABSTRACT_STIX_CORE_OBJECT,
            ABSTRACT_STIX_DOMAIN_OBJECT,
            ENTITY_TYPE_CONTAINER,
            // ENTITY_TYPE_CONTAINER_CASE 在源码中从外部导入，暂时不包含
            ENTITY_TYPE_IDENTITY,
            ENTITY_TYPE_THREAT_ACTOR,
            ENTITY_TYPE_LOCATION,
            ABSTRACT_STIX_META_OBJECT,
            ABSTRACT_STIX_CYBER_OBSERVABLE,
            ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE,
            ABSTRACT_BASIC_RELATIONSHIP,
            ABSTRACT_INTERNAL_RELATIONSHIP,
            ABSTRACT_STIX_RELATIONSHIP,
            ABSTRACT_STIX_CORE_RELATIONSHIP,
            ABSTRACT_STIX_REF_RELATIONSHIP,
            ABSTRACT_STIX_META_RELATIONSHIP,
            ABSTRACT_STIX_CYBER_OBSERVABLE_RELATIONSHIP
    )));

    // 原代码: export const KNOWLEDGE_TYPES = [...]
    public static final Set<String> KNOWLEDGE_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ABSTRACT_BASIC_OBJECT,
            ABSTRACT_STIX_OBJECT,
            ABSTRACT_STIX_CORE_OBJECT,
            ABSTRACT_STIX_DOMAIN_OBJECT,
            ENTITY_TYPE_CONTAINER,
            // ENTITY_TYPE_CONTAINER_CASE 在源码中从外部导入，暂时不包含
            ENTITY_TYPE_IDENTITY,
            ENTITY_TYPE_THREAT_ACTOR,
            ENTITY_TYPE_LOCATION,
            ABSTRACT_STIX_META_OBJECT,
            ABSTRACT_STIX_CYBER_OBSERVABLE,
            ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE,
            ABSTRACT_BASIC_RELATIONSHIP,
            ABSTRACT_STIX_RELATIONSHIP,
            ABSTRACT_STIX_CORE_RELATIONSHIP,
            ABSTRACT_STIX_REF_RELATIONSHIP,
            ABSTRACT_STIX_META_RELATIONSHIP,
            ABSTRACT_STIX_CYBER_OBSERVABLE_RELATIONSHIP
    )));

    // ==================== 工具方法 ====================

    /**
     * 构建引用关系键
     * 原代码: export const buildRefRelationKey = (type, field = ID_INTERNAL) => `${REL_INDEX_PREFIX}${type}.${field}`;
     *
     * @param type 类型
     * @param field 字段名，默认为 ID_INTERNAL
     * @return 格式为 "rel_{type}.{field}" 的字符串
     */
    public static String buildRefRelationKey(String type, String field) {
        if (field == null || field.isEmpty()) {
            field = ID_INTERNAL;
        }
        return REL_INDEX_PREFIX + type + "." + field;
    }

    /**
     * 构建引用关系键（使用默认字段）
     * 原代码: export const buildRefRelationKey = (type, field = ID_INTERNAL) => `${REL_INDEX_PREFIX}${type}.${field}`;
     *
     * @param type 类型
     * @return 格式为 "rel_{type}.internal_id" 的字符串
     */
    public static String buildRefRelationKey(String type) {
        return buildRefRelationKey(type, ID_INTERNAL);
    }

    /**
     * 构建引用关系搜索键
     * 原代码: export const buildRefRelationSearchKey = (type, field = ID_INTERNAL) => `${buildRefRelationKey(type, field)}.keyword`;
     *
     * @param type 类型
     * @param field 字段名，默认为 ID_INTERNAL
     * @return 格式为 "rel_{type}.{field}.keyword" 的字符串
     */
    public static String buildRefRelationSearchKey(String type, String field) {
        return buildRefRelationKey(type, field) + ".keyword";
    }

    /**
     * 构建引用关系搜索键（使用默认字段）
     * 原代码: export const buildRefRelationSearchKey = (type, field = ID_INTERNAL) => `${buildRefRelationKey(type, field)}.keyword`;
     *
     * @param type 类型
     * @return 格式为 "rel_{type}.internal_id.keyword" 的字符串
     */
    public static String buildRefRelationSearchKey(String type) {
        return buildRefRelationSearchKey(type, ID_INTERNAL);
    }

    /**
     * 判断是否为抽象类型
     * 原代码: export const isAbstract = (type) => R.includes(type, ABSTRACT_TYPES);
     *
     * @param type 类型
     * @return 如果是抽象类型返回 true，否则返回 false
     */
    public static boolean isAbstract(String type) {
        return type != null && ABSTRACT_TYPES.contains(type);
    }

    /**
     * 判断是否为知识类型
     * 原代码: export const isKnowledge = (type) => R.includes(type, KNOWLEDGE_TYPES);
     *
     * @param type 类型
     * @return 如果是知识类型返回 true，否则返回 false
     */
    public static boolean isKnowledge(String type) {
        return type != null && KNOWLEDGE_TYPES.contains(type);
    }
}
