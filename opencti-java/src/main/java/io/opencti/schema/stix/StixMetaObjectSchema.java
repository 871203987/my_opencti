package io.opencti.schema.stix;

import io.opencti.schema.SchemaTypesDefinition;
import io.opencti.schema.general.SchemaGeneral;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * STIX 元对象 (Stix-Meta-Object) Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/stixMetaObject.ts
 *
 * 该文件定义了STIX元对象的类型常量，包括Label、External-Reference、Kill-Chain-Phase、
 * Marking-Definition等。这些元数据实体用于标记、分类和引用外部资源。
 */
public final class StixMetaObjectSchema {

    private StixMetaObjectSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== STIX元对象类型常量 ====================
    // 原代码: export const ENTITY_TYPE_LABEL = 'Label';
    public static final String ENTITY_TYPE_LABEL = "Label";

    // 原代码: export const ENTITY_TYPE_EXTERNAL_REFERENCE = 'External-Reference';
    public static final String ENTITY_TYPE_EXTERNAL_REFERENCE = "External-Reference";

    // 原代码: export const ENTITY_TYPE_KILL_CHAIN_PHASE = 'Kill-Chain-Phase';
    public static final String ENTITY_TYPE_KILL_CHAIN_PHASE = "Kill-Chain-Phase";

    // 原代码: export const ENTITY_TYPE_MARKING_DEFINITION = 'Marking-Definition';
    public static final String ENTITY_TYPE_MARKING_DEFINITION = "Marking-Definition";

    // ==================== STIX元对象列表 ====================
    // 原代码: export const STIX_EMBEDDED_OBJECT = [ENTITY_TYPE_LABEL, ENTITY_TYPE_EXTERNAL_REFERENCE, ENTITY_TYPE_KILL_CHAIN_PHASE];
    public static final List<String> STIX_EMBEDDED_OBJECT = Collections.unmodifiableList(Arrays.asList(
            ENTITY_TYPE_LABEL,
            ENTITY_TYPE_EXTERNAL_REFERENCE,
            ENTITY_TYPE_KILL_CHAIN_PHASE
    ));

    // 原代码: const STIX_META_OBJECT = [...STIX_EMBEDDED_OBJECT, ENTITY_TYPE_MARKING_DEFINITION];
    public static final List<String> STIX_META_OBJECT = Collections.unmodifiableList(Arrays.asList(
            ENTITY_TYPE_LABEL,
            ENTITY_TYPE_EXTERNAL_REFERENCE,
            ENTITY_TYPE_KILL_CHAIN_PHASE,
            ENTITY_TYPE_MARKING_DEFINITION
    ));

    // ==================== 静态代码块：类型注册 ====================
    static {
        // 原代码: schemaTypesDefinition.register(ABSTRACT_STIX_EMBEDDED_OBJECT, STIX_EMBEDDED_OBJECT);
        // 注意：ABSTRACT_STIX_EMBEDDED_OBJECT 在Java中未定义，使用字符串字面量
        SchemaTypesDefinition.register("Stix-Embedded-Object", STIX_EMBEDDED_OBJECT);

        // 原代码: schemaTypesDefinition.register(ABSTRACT_STIX_META_OBJECT, STIX_META_OBJECT);
        SchemaTypesDefinition.register(SchemaGeneral.ABSTRACT_STIX_META_OBJECT, STIX_META_OBJECT);
    }

    // ==================== 类型判断方法 ====================
    // 原代码:
    // export const isStixMetaObject = (type: string) => schemaTypesDefinition.isTypeIncludedIn(type, ABSTRACT_STIX_META_OBJECT)
    //   || type === ABSTRACT_STIX_META_OBJECT;
    /**
     * 判断给定类型是否为STIX元对象
     *
     * @param type 类型字符串
     * @return 如果是STIX元对象返回true，否则返回false
     */
    public static boolean isStixMetaObject(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ABSTRACT_STIX_META_OBJECT)
                || type.equals(SchemaGeneral.ABSTRACT_STIX_META_OBJECT);
    }
}
