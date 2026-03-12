package io.opencti.schema.internal;

import io.opencti.schema.SchemaTypesDefinition;
import io.opencti.schema.general.SchemaGeneral;
import io.opencti.types.store.StoreCommon;
import io.opencti.types.store.StoreRelationPir;

import java.util.*;

/**
 * 内部关系 Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/internalRelationship.ts
 *
 * 该文件定义了OpenCTI系统中所有内部关系的类型常量（如migrates、member-of、has-role等），
 * 以及内部关系的类型判断方法。这是区分内部关系和其他类型关系的基础模块。
 */
public final class InternalRelationshipSchema {

    // 私有构造函数，防止实例化
    private InternalRelationshipSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== 内部关系类型常量 ====================
    // 原代码: export const RELATION_MIGRATES = 'migrates';
    public static final String RELATION_MIGRATES = "migrates";

    // 原代码: export const RELATION_MEMBER_OF = 'member-of';
    public static final String RELATION_MEMBER_OF = "member-of";

    // 原代码: export const RELATION_PARTICIPATE_TO = 'participate-to';
    public static final String RELATION_PARTICIPATE_TO = "participate-to";

    // 原代码: export const RELATION_ALLOWED_BY = 'allowed-by';
    public static final String RELATION_ALLOWED_BY = "allowed-by";

    // 原代码: export const RELATION_HAS_ROLE = 'has-role';
    public static final String RELATION_HAS_ROLE = "has-role";

    // 原代码: export const RELATION_HAS_CAPABILITY = 'has-capability';
    public static final String RELATION_HAS_CAPABILITY = "has-capability";

    // 原代码: export const RELATION_HAS_CAPABILITY_IN_DRAFT = 'has-capability-in-draft';
    public static final String RELATION_HAS_CAPABILITY_IN_DRAFT = "has-capability-in-draft";

    // 原代码: export const RELATION_ACCESSES_TO = 'accesses-to';
    public static final String RELATION_ACCESSES_TO = "accesses-to";

    // 原代码: export const RELATION_IN_PIR = 'in-pir';
    public static final String RELATION_IN_PIR = "in-pir";

    // ==================== 内部关系列表 ====================
    // 原代码: export const INTERNAL_RELATIONSHIPS = [...]
    public static final Set<String> INTERNAL_RELATIONSHIPS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            RELATION_MIGRATES,
            RELATION_MEMBER_OF,
            RELATION_ALLOWED_BY,
            RELATION_HAS_ROLE,
            RELATION_HAS_CAPABILITY,
            RELATION_HAS_CAPABILITY_IN_DRAFT,
            RELATION_ACCESSES_TO,
            RELATION_PARTICIPATE_TO,
            RELATION_IN_PIR
    )));

    // ==================== 类型判断方法 ====================

    /**
     * 判断是否为内部关系
     * 原代码: export const isInternalRelationship = (type: string) => schemaTypesDefinition.isTypeIncludedIn(type, ABSTRACT_INTERNAL_RELATIONSHIP)
     *   || type === ABSTRACT_INTERNAL_RELATIONSHIP;
     *
     * @param type 类型
     * @return 如果是内部关系返回 true，否则返回 false
     */
    public static boolean isInternalRelationship(String type) {
        return type != null && (SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP)
                || type.equals(SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP));
    }

    /**
     * 判断是否为PIR关系实例
     * 原代码: export const isStoreRelationPir = (instance: StoreCommon): instance is StoreRelationPir => {
     *   return (instance as StoreRelationPir).entity_type === RELATION_IN_PIR;
     * };
     *
     * @param instance 实例
     * @return 如果是PIR关系实例返回 true，否则返回 false
     */
    public static boolean isStoreRelationPir(StoreCommon instance) {
        return instance != null && RELATION_IN_PIR.equals(instance.getEntityType());
    }

    // ==================== 静态代码块：类型注册 ====================
    static {
        // 原代码: schemaTypesDefinition.register(ABSTRACT_INTERNAL_RELATIONSHIP, INTERNAL_RELATIONSHIPS);
        SchemaTypesDefinition.register(SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP, INTERNAL_RELATIONSHIPS);
    }
}
