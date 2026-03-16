package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;
import io.opencti.schema.internal.InternalRelationshipSchema;
import io.opencti.types.stix.common.StixObject;
import io.opencti.types.stix.sro.StixRelation;

/**
 * STIX 关系 Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/stixRelationship.ts
 *
 * 该文件定义了STIX关系的组合类型判断方法，包括核心关系、目击关系、引用关系的组合判断逻辑。
 * 这是构建完整STIX关系体系的重要组成部分，为上层业务提供统一的关系类型判断接口。
 */
public final class StixRelationshipSchema {

    private StixRelationshipSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * 判断是否为核心关系或目击关系（不含引用关系）
     * 原代码: export const isStixRelationshipExceptRef = (type: string) =>
     *   isStixCoreRelationship(type) || isStixSightingRelationship(type);
     *
     * @param type 类型
     * @return 如果是STIX核心关系或STIX目击关系返回 true，否则返回 false
     */
    public static boolean isStixRelationshipExceptRef(String type) {
        if (type == null) {
            return false;
        }
        return StixCoreRelationshipSchema.isStixCoreRelationship(type)
                || StixSightingRelationshipSchema.isStixSightingRelationship(type);
    }

    /**
     * 判断是否为STIX关系（含引用关系）
     * 原代码: export const isStixRelationship = (type: string) =>
     *   isStixCoreRelationship(type)
     *   || isStixSightingRelationship(type)
     *   || isStixRefRelationship(type)
     *   || type === ABSTRACT_STIX_RELATIONSHIP;
     *
     * @param type 类型
     * @return 如果是STIX关系返回 true，否则返回 false
     */
    public static boolean isStixRelationship(String type) {
        if (type == null) {
            return false;
        }
        return StixCoreRelationshipSchema.isStixCoreRelationship(type)
                || StixSightingRelationshipSchema.isStixSightingRelationship(type)
                || StixRefRelationshipSchema.isStixRefRelationship(type)
                || type.equals(SchemaGeneral.ABSTRACT_STIX_RELATIONSHIP);
    }

    /**
     * 判断是否为基本关系
     * 原代码: export const isBasicRelationship = (type: string) =>
     *   isInternalRelationship(type) || isStixRelationship(type) || type === ABSTRACT_BASIC_RELATIONSHIP;
     *
     * @param type 类型
     * @return 如果是基本关系返回 true，否则返回 false
     */
    public static boolean isBasicRelationship(String type) {
        if (type == null) {
            return false;
        }
        return InternalRelationshipSchema.isInternalRelationship(type)
                || isStixRelationship(type)
                || type.equals(SchemaGeneral.ABSTRACT_BASIC_RELATIONSHIP);
    }

    /**
     * 判断是否为StixRelation对象
     * 原代码: export const isStixRelation = (sco: StixCoreObject): sco is StixRelation => {
     *   return (sco as StixRelation).relationship_type !== undefined;
     * };
     *
     * @param sco STIX核心对象
     * @return 如果是StixRelation对象返回 true，否则返回 false
     */
    public static boolean isStixRelation(StixObject sco) {
        if (sco == null) {
            return false;
        }
        return sco instanceof StixRelation;
    }
}
