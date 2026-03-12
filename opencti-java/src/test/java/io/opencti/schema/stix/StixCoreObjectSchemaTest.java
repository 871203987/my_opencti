package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;
import io.opencti.schema.internal.InternalRelationshipSchema;
import io.opencti.types.store.StoreObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StixCoreObjectSchema 测试类
 * 测试所有常量和类型判断方法
 */
class StixCoreObjectSchemaTest {

    // ==================== 内部可导出类型列表测试 ====================
    @Test
    void testInternalExportableTypes() {
        assertNotNull(StixCoreObjectSchema.INTERNAL_EXPORTABLE_TYPES);
        assertEquals(2, StixCoreObjectSchema.INTERNAL_EXPORTABLE_TYPES.size());

        // 验证包含的关系类型
        assertTrue(StixCoreObjectSchema.INTERNAL_EXPORTABLE_TYPES.contains(InternalRelationshipSchema.RELATION_PARTICIPATE_TO));
        assertTrue(StixCoreObjectSchema.INTERNAL_EXPORTABLE_TYPES.contains(InternalRelationshipSchema.RELATION_IN_PIR));
    }

    // ==================== STIX核心对象选项测试 ====================
    @Test
    void testStixCoreObjectsOrdering() {
        assertNotNull(StixCoreObjectSchema.STIX_CORE_OBJECTS_ORDERING);
        assertEquals(4, StixCoreObjectSchema.STIX_CORE_OBJECTS_ORDERING.size());

        // 验证排序字段映射
        assertEquals("opinions_metrics.mean", StixCoreObjectSchema.STIX_CORE_OBJECTS_ORDERING.get("opinions_metrics_mean"));
        assertEquals("opinions_metrics.max", StixCoreObjectSchema.STIX_CORE_OBJECTS_ORDERING.get("opinions_metrics_max"));
        assertEquals("opinions_metrics.min", StixCoreObjectSchema.STIX_CORE_OBJECTS_ORDERING.get("opinions_metrics_min"));
        assertEquals("opinions_metrics.total", StixCoreObjectSchema.STIX_CORE_OBJECTS_ORDERING.get("opinions_metrics_total"));
    }

    // ==================== 类型判断方法测试 ====================
    @Test
    void testIsStixCoreObject() {
        // 测试抽象STIX核心对象类型
        assertTrue(StixCoreObjectSchema.isStixCoreObject(SchemaGeneral.ABSTRACT_STIX_CORE_OBJECT));

        // 测试非STIX核心对象类型（桩实现返回false）
        assertFalse(StixCoreObjectSchema.isStixCoreObject("Malware"));
        assertFalse(StixCoreObjectSchema.isStixCoreObject("Attack-Pattern"));

        // 测试 null
        assertFalse(StixCoreObjectSchema.isStixCoreObject(null));
    }

    @Test
    void testIsStixObject() {
        // 测试抽象STIX对象类型
        assertTrue(StixCoreObjectSchema.isStixObject(SchemaGeneral.ABSTRACT_STIX_OBJECT));

        // 测试抽象STIX核心对象类型
        assertTrue(StixCoreObjectSchema.isStixObject(SchemaGeneral.ABSTRACT_STIX_CORE_OBJECT));

        // 测试非STIX对象类型（桩实现返回false）
        assertFalse(StixCoreObjectSchema.isStixObject("Settings"));
        assertFalse(StixCoreObjectSchema.isStixObject("User"));

        // 测试 null
        assertFalse(StixCoreObjectSchema.isStixObject(null));
    }

    @Test
    void testIsBasicObject() {
        // 测试抽象基本对象类型
        assertTrue(StixCoreObjectSchema.isBasicObject(SchemaGeneral.ABSTRACT_BASIC_OBJECT));

        // 测试抽象STIX对象类型
        assertTrue(StixCoreObjectSchema.isBasicObject(SchemaGeneral.ABSTRACT_STIX_OBJECT));

        // 测试内部对象类型
        assertTrue(StixCoreObjectSchema.isBasicObject("Settings"));
        assertTrue(StixCoreObjectSchema.isBasicObject("User"));
        assertTrue(StixCoreObjectSchema.isBasicObject("Group"));

        // 测试非基本对象类型
        assertFalse(StixCoreObjectSchema.isBasicObject("UnknownType"));

        // 测试 null
        assertFalse(StixCoreObjectSchema.isBasicObject(null));
    }

    @Test
    void testIsStixExportableInStreamData() {
        // 创建模拟的 StoreObject 实例
        StoreObject stixObject = new StoreObject() {
            @Override
            public String getEntityType() {
                return SchemaGeneral.ABSTRACT_STIX_OBJECT;
            }

            @Override
            public String getInternalId() {
                return "test-internal-id";
            }

            @Override
            public String getStandardId() {
                return "test-standard-id";
            }
        };

        StoreObject participateToObject = new StoreObject() {
            @Override
            public String getEntityType() {
                return InternalRelationshipSchema.RELATION_PARTICIPATE_TO;
            }

            @Override
            public String getInternalId() {
                return "test-internal-id";
            }

            @Override
            public String getStandardId() {
                return "test-standard-id";
            }
        };

        StoreObject inPirObject = new StoreObject() {
            @Override
            public String getEntityType() {
                return InternalRelationshipSchema.RELATION_IN_PIR;
            }

            @Override
            public String getInternalId() {
                return "test-internal-id";
            }

            @Override
            public String getStandardId() {
                return "test-standard-id";
            }
        };

        StoreObject nonExportableObject = new StoreObject() {
            @Override
            public String getEntityType() {
                return "UnknownType";
            }

            @Override
            public String getInternalId() {
                return "test-internal-id";
            }

            @Override
            public String getStandardId() {
                return "test-standard-id";
            }
        };

        // 测试可导出的STIX对象
        assertTrue(StixCoreObjectSchema.isStixExportableInStreamData(stixObject));

        // 测试可导出的内部关系类型
        assertTrue(StixCoreObjectSchema.isStixExportableInStreamData(participateToObject));
        assertTrue(StixCoreObjectSchema.isStixExportableInStreamData(inPirObject));

        // 测试不可导出的对象
        assertFalse(StixCoreObjectSchema.isStixExportableInStreamData(nonExportableObject));

        // 测试 null
        assertFalse(StixCoreObjectSchema.isStixExportableInStreamData(null));
    }

    @Test
    void testIsStixExportableInStreamDataWithNullEntityType() {
        StoreObject nullEntityTypeObject = new StoreObject() {
            @Override
            public String getEntityType() {
                return null;
            }

            @Override
            public String getInternalId() {
                return "test-internal-id";
            }

            @Override
            public String getStandardId() {
                return "test-standard-id";
            }
        };

        assertFalse(StixCoreObjectSchema.isStixExportableInStreamData(nullEntityTypeObject));
    }
}
