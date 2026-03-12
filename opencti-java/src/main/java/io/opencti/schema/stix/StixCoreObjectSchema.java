package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;
import io.opencti.schema.internal.InternalObjectSchema;
import io.opencti.schema.internal.InternalRelationshipSchema;
import io.opencti.types.store.StoreObject;

import java.util.*;

/**
 * STIX 核心对象 Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/stixCoreObject.ts
 *
 * 该文件定义了STIX核心对象的类型判断方法，包括STIX核心对象、STIX对象、基本对象的判断逻辑，
 * 以及内部可导出类型列表和STIX核心对象选项配置。
 */
public final class StixCoreObjectSchema {

    // 私有构造函数，防止实例化
    private StixCoreObjectSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== 内部可导出类型列表 ====================
    // 原代码: export const INTERNAL_EXPORTABLE_TYPES = [RELATION_PARTICIPATE_TO, RELATION_IN_PIR];
    public static final Set<String> INTERNAL_EXPORTABLE_TYPES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            InternalRelationshipSchema.RELATION_PARTICIPATE_TO,
            InternalRelationshipSchema.RELATION_IN_PIR
    )));

    // ==================== STIX核心对象选项 ====================
    // 原代码: export const stixCoreObjectOptions = { StixCoreObjectsOrdering: { ... } };
    public static final Map<String, String> STIX_CORE_OBJECTS_ORDERING;

    static {
        Map<String, String> ordering = new HashMap<>();
        ordering.put("opinions_metrics_mean", "opinions_metrics.mean");
        ordering.put("opinions_metrics_max", "opinions_metrics.max");
        ordering.put("opinions_metrics_min", "opinions_metrics.min");
        ordering.put("opinions_metrics_total", "opinions_metrics.total");
        STIX_CORE_OBJECTS_ORDERING = Collections.unmodifiableMap(ordering);
    }

    // ==================== 依赖模块的桩方法 ====================
    // 这些方法将在后续迭代中由其他Schema模块提供

    /**
     * 判断是否为STIX域对象（桩实现）
     * 原代码: import { isStixDomainObject } from './stixDomainObject';
     */
    private static boolean isStixDomainObject(String type) {
        // 桩实现，将在后续迭代中完善
        return false;
    }

    /**
     * 判断是否为STIX网络可观测对象（桩实现）
     * 原代码: import { isStixCyberObservable } from './stixCyberObservable';
     */
    private static boolean isStixCyberObservable(String type) {
        // 桩实现，将在后续迭代中完善
        return false;
    }

    /**
     * 判断是否为STIX元对象（桩实现）
     * 原代码: import { isStixMetaObject } from './stixMetaObject';
     */
    private static boolean isStixMetaObject(String type) {
        // 桩实现，将在后续迭代中完善
        return false;
    }

    /**
     * 判断是否为非引用STIX关系（桩实现）
     * 原代码: import { isStixRelationshipExceptRef } from './stixRelationship';
     */
    private static boolean isStixRelationshipExceptRef(String type) {
        // 桩实现，将在后续迭代中完善
        return false;
    }

    // ==================== 类型判断方法 ====================

    /**
     * 判断是否为STIX核心对象
     * 原代码: export const isStixCoreObject = (type: string) => isStixDomainObject(type) || isStixCyberObservable(type) || type === ABSTRACT_STIX_CORE_OBJECT;
     *
     * @param type 类型
     * @return 如果是STIX核心对象返回 true，否则返回 false
     */
    public static boolean isStixCoreObject(String type) {
        return type != null && (isStixDomainObject(type) || isStixCyberObservable(type)
                || type.equals(SchemaGeneral.ABSTRACT_STIX_CORE_OBJECT));
    }

    /**
     * 判断是否为STIX对象
     * 原代码: export const isStixObject = (type: string) => isStixCoreObject(type) || isStixMetaObject(type) || type === ABSTRACT_STIX_OBJECT;
     *
     * @param type 类型
     * @return 如果是STIX对象返回 true，否则返回 false
     */
    public static boolean isStixObject(String type) {
        return type != null && (isStixCoreObject(type) || isStixMetaObject(type)
                || type.equals(SchemaGeneral.ABSTRACT_STIX_OBJECT));
    }

    /**
     * 判断是否为基本对象
     * 原代码: export const isBasicObject = (type: string) => isInternalObject(type) || isStixObject(type) || type === ABSTRACT_BASIC_OBJECT;
     *
     * @param type 类型
     * @return 如果是基本对象返回 true，否则返回 false
     */
    public static boolean isBasicObject(String type) {
        return type != null && (InternalObjectSchema.isInternalObject(type) || isStixObject(type)
                || type.equals(SchemaGeneral.ABSTRACT_BASIC_OBJECT));
    }

    /**
     * 判断是否为可在流数据中导出的STIX对象
     * 原代码: export const isStixExportableInStreamData = (instance: StoreObject) => isStixObject(instance.entity_type)
     *   || isStixRelationshipExceptRef(instance.entity_type)
     *   || INTERNAL_EXPORTABLE_TYPES.includes(instance.entity_type);
     *
     * @param instance 实例
     * @return 如果可在流数据中导出返回 true，否则返回 false
     */
    public static boolean isStixExportableInStreamData(StoreObject instance) {
        if (instance == null || instance.getEntityType() == null) {
            return false;
        }
        String entityType = instance.getEntityType();
        return isStixObject(entityType) || isStixRelationshipExceptRef(entityType)
                || INTERNAL_EXPORTABLE_TYPES.contains(entityType);
    }
}
