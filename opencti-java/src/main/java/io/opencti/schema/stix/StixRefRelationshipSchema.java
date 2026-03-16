package io.opencti.schema.stix;

import io.opencti.schema.SchemaTypesDefinition;
import io.opencti.schema.general.SchemaGeneral;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * STIX 引用关系 Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/stixRefRelationship.ts
 *
 * 该文件定义了STIX引用关系的类型常量，包括SCO引用关系和元引用关系，
 * 以及引用关系的类型判断方法。这是构建完整STIX引用关系体系的基础模块。
 */
public final class StixRefRelationshipSchema {

    private StixRefRelationshipSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== STIX引用关系类型常量 ====================
    // 原代码: export const RELATION_OPERATING_SYSTEM = 'operating-system';
    public static final String RELATION_OPERATING_SYSTEM = "operating-system";

    // 原代码: export const RELATION_SAMPLE = 'sample';
    public static final String RELATION_SAMPLE = "sample";

    // 原代码: export const RELATION_CONTAINS = 'contains';
    public static final String RELATION_CONTAINS = "contains";

    // 原代码: export const RELATION_RESOLVES_TO = 'obs_resolves-to';
    public static final String RELATION_RESOLVES_TO = "obs_resolves-to";

    // 原代码: export const RELATION_BELONGS_TO = 'obs_belongs-to';
    public static final String RELATION_BELONGS_TO = "obs_belongs-to";

    // 原代码: export const RELATION_FROM = 'from';
    public static final String RELATION_FROM = "from";

    // 原代码: export const RELATION_SENDER = 'sender';
    public static final String RELATION_SENDER = "sender";

    // 原代码: export const RELATION_TO = 'to';
    public static final String RELATION_TO = "to";

    // 原代码: export const RELATION_CC = 'cc';
    public static final String RELATION_CC = "cc";

    // 原代码: export const RELATION_BCC = 'bcc';
    public static final String RELATION_BCC = "bcc";

    // 原代码: export const RELATION_RAW_EMAIL = 'raw-email';
    public static final String RELATION_RAW_EMAIL = "raw-email";

    // 原代码: export const RELATION_BODY_RAW = 'body-raw';
    public static final String RELATION_BODY_RAW = "body-raw";

    // 原代码: export const RELATION_PARENT_DIRECTORY = 'parent-directory';
    public static final String RELATION_PARENT_DIRECTORY = "parent-directory";

    // 原代码: export const RELATION_CONTENT = 'obs_content';
    public static final String RELATION_CONTENT = "obs_content";

    // 原代码: export const RELATION_SRC = 'src';
    public static final String RELATION_SRC = "src";

    // 原代码: export const RELATION_DST = 'dst';
    public static final String RELATION_DST = "dst";

    // 原代码: export const RELATION_SRC_PAYLOAD = 'src-payload';
    public static final String RELATION_SRC_PAYLOAD = "src-payload";

    // 原代码: export const RELATION_DST_PAYLOAD = 'dst-payload';
    public static final String RELATION_DST_PAYLOAD = "dst-payload";

    // 原代码: export const RELATION_ENCAPSULATES = 'encapsulates';
    public static final String RELATION_ENCAPSULATES = "encapsulates";

    // 原代码: export const RELATION_ENCAPSULATED_BY = 'encapsulated-by';
    public static final String RELATION_ENCAPSULATED_BY = "encapsulated-by";

    // 原代码: export const RELATION_OPENED_CONNECTION = 'opened-connection';
    public static final String RELATION_OPENED_CONNECTION = "opened-connection";

    // 原代码: export const RELATION_CREATOR_USER = 'creator-user';
    public static final String RELATION_CREATOR_USER = "creator-user";

    // 原代码: export const RELATION_IMAGE = 'image';
    public static final String RELATION_IMAGE = "image";

    // 原代码: export const RELATION_PARENT = 'parent';
    public static final String RELATION_PARENT = "parent";

    // 原代码: export const RELATION_CHILD = 'child';
    public static final String RELATION_CHILD = "child";

    // 原代码: export const RELATION_BODY_MULTIPART = 'body-multipart';
    public static final String RELATION_BODY_MULTIPART = "body-multipart";

    // 原代码: export const RELATION_VALUES = 'values';
    public static final String RELATION_VALUES = "values";

    // 原代码: export const RELATION_SERVICE_DLL = 'service-dll';
    public static final String RELATION_SERVICE_DLL = "service-dll";

    // ==================== 元引用关系类型常量 ====================
    // 原代码: export const RELATION_CREATED_BY = 'created-by';
    public static final String RELATION_CREATED_BY = "created-by";

    // 原代码: export const RELATION_OBJECT_MARKING = 'object-marking';
    public static final String RELATION_OBJECT_MARKING = "object-marking";

    // 原代码: export const RELATION_OBJECT_LABEL = 'object-label';
    public static final String RELATION_OBJECT_LABEL = "object-label";

    // 原代码: export const RELATION_OBJECT = 'object';
    public static final String RELATION_OBJECT = "object";

    // 原代码: export const RELATION_EXTERNAL_REFERENCE = 'external-reference';
    public static final String RELATION_EXTERNAL_REFERENCE = "external-reference";

    // 原代码: export const RELATION_INTERNAL_FILE = 'internal-file';
    public static final String RELATION_INTERNAL_FILE = "internal-file";

    // 原代码: export const RELATION_WORK = 'work';
    public static final String RELATION_WORK = "work";

    // 原代码: export const RELATION_KILL_CHAIN_PHASE = 'kill-chain-phase';
    public static final String RELATION_KILL_CHAIN_PHASE = "kill-chain-phase";

    // 原代码: export const RELATION_GRANTED_TO = 'granted';
    public static final String RELATION_GRANTED_TO = "granted";

    // 原代码: export const RELATION_OBJECT_ASSIGNEE = 'object-assignee';
    public static final String RELATION_OBJECT_ASSIGNEE = "object-assignee";

    // 原代码: export const RELATION_OBJECT_PARTICIPANT = 'object-participant';
    public static final String RELATION_OBJECT_PARTICIPANT = "object-participant";

    // 原代码: export const RELATION_BORN_IN = 'born-in';
    public static final String RELATION_BORN_IN = "born-in";

    // 原代码: export const RELATION_ETHNICITY = 'of-ethnicity';
    public static final String RELATION_ETHNICITY = "of-ethnicity";

    // ==================== STIX引用关系类型集合 ====================
    // 原代码: export const STIX_REF_RELATIONSHIPS = [...]
    public static final Set<String> STIX_REF_RELATIONSHIPS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            RELATION_OPERATING_SYSTEM,
            RELATION_SAMPLE,
            RELATION_CONTAINS,
            RELATION_RESOLVES_TO,
            RELATION_BELONGS_TO,
            RELATION_FROM,
            RELATION_SENDER,
            RELATION_TO,
            RELATION_CC,
            RELATION_BCC,
            RELATION_RAW_EMAIL,
            RELATION_BODY_RAW,
            RELATION_PARENT_DIRECTORY,
            RELATION_CONTENT,
            RELATION_SRC,
            RELATION_DST,
            RELATION_SRC_PAYLOAD,
            RELATION_DST_PAYLOAD,
            RELATION_ENCAPSULATES,
            RELATION_ENCAPSULATED_BY,
            RELATION_OPENED_CONNECTION,
            RELATION_CREATOR_USER,
            RELATION_IMAGE,
            RELATION_PARENT,
            RELATION_CHILD,
            RELATION_BODY_MULTIPART,
            RELATION_VALUES,
            RELATION_SERVICE_DLL,
            "x_opencti_linked-to"
    )));

    // ==================== 元引用关系类型集合 ====================
    // 原代码: META_RELATIONS
    public static final Set<String> META_RELATIONSHIPS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            RELATION_CREATED_BY,
            RELATION_OBJECT_MARKING,
            RELATION_OBJECT_LABEL,
            RELATION_OBJECT,
            RELATION_EXTERNAL_REFERENCE,
            RELATION_INTERNAL_FILE,
            RELATION_WORK,
            RELATION_KILL_CHAIN_PHASE,
            RELATION_GRANTED_TO,
            RELATION_OBJECT_ASSIGNEE,
            RELATION_OBJECT_PARTICIPANT,
            RELATION_BORN_IN,
            RELATION_ETHNICITY
    )));

    // ==================== 类型判断方法 ====================

    /**
     * 判断是否为STIX引用关系
     * 原代码: export const isStixRefRelationship = (type) => schemaTypesDefinition.isTypeIncludedIn(type, ABSTRACT_STIX_REF_RELATIONSHIP)
     *   || type === ABSTRACT_STIX_REF_RELATIONSHIP;
     *
     * @param type 类型
     * @return 如果是STIX引用关系返回 true，否则返回 false
     */
    public static boolean isStixRefRelationship(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ABSTRACT_STIX_REF_RELATIONSHIP)
                || type.equals(SchemaGeneral.ABSTRACT_STIX_REF_RELATIONSHIP)
                || STIX_REF_RELATIONSHIPS.contains(type)
                || META_RELATIONSHIPS.contains(type);
    }

    /**
     * 判断是否为STIX元引用关系
     * 原代码: const isStixMetaRelationship = (type) => META_RELATIONS.some((ref) => ref.databaseName === type);
     *
     * @param type 类型
     * @return 如果是STIX元引用关系返回 true，否则返回 false
     */
    public static boolean isStixMetaRelationship(String type) {
        return type != null && META_RELATIONSHIPS.contains(type);
    }

    /**
     * 判断是否为STIX引用单向关系
     * 原代码: export const isStixRefUnidirectionalRelationship = (type) => isStixMetaRelationship(type) && type !== RELATION_OBJECT;
     *
     * @param type 类型
     * @return 如果是STIX引用单向关系返回 true，否则返回 false
     */
    public static boolean isStixRefUnidirectionalRelationship(String type) {
        return isStixMetaRelationship(type) && !RELATION_OBJECT.equals(type);
    }

    // ==================== 静态代码块：类型注册 ====================
    static {
        java.util.Set<String> allRefRelationships = new java.util.HashSet<>();
        allRefRelationships.addAll(STIX_REF_RELATIONSHIPS);
        allRefRelationships.addAll(META_RELATIONSHIPS);
        SchemaTypesDefinition.register(SchemaGeneral.ABSTRACT_STIX_REF_RELATIONSHIP, allRefRelationships);
    }
}
