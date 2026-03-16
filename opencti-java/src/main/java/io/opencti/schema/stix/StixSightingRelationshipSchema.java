package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;

/**
 * STIX 目击关系 Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/stixSightingRelationship.ts
 *
 * 该文件定义了STIX目击关系的基本类型常量，以及目击关系的类型判断方法。
 */
public final class StixSightingRelationshipSchema {

    private StixSightingRelationshipSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * STIX目击关系类型常量
     * 原代码: export const STIX_SIGHTING_RELATIONSHIP = 'stix-sighting-relationship';
     */
    public static final String STIX_SIGHTING_RELATIONSHIP = "stix-sighting-relationship";

    /**
     * 判断是否为STIX目击关系
     * 原代码: export const isStixSightingRelationship = (type: string): boolean => type === STIX_SIGHTING_RELATIONSHIP;
     *
     * @param type 类型
     * @return 如果是STIX目击关系返回 true，否则返回 false
     */
    public static boolean isStixSightingRelationship(String type) {
        return STIX_SIGHTING_RELATIONSHIP.equals(type);
    }
}
