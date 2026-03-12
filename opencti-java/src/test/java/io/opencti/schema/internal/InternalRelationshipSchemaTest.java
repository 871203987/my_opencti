package io.opencti.schema.internal;

import io.opencti.schema.SchemaTypesDefinition;
import io.opencti.schema.general.SchemaGeneral;
import io.opencti.types.store.StoreCommon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * InternalRelationshipSchema 测试类
 * 测试所有常量和类型判断方法
 */
class InternalRelationshipSchemaTest {

    // ==================== 内部关系类型常量测试 ====================
    @Test
    void testInternalRelationshipConstants() {
        assertEquals("migrates", InternalRelationshipSchema.RELATION_MIGRATES);
        assertEquals("member-of", InternalRelationshipSchema.RELATION_MEMBER_OF);
        assertEquals("participate-to", InternalRelationshipSchema.RELATION_PARTICIPATE_TO);
        assertEquals("allowed-by", InternalRelationshipSchema.RELATION_ALLOWED_BY);
        assertEquals("has-role", InternalRelationshipSchema.RELATION_HAS_ROLE);
        assertEquals("has-capability", InternalRelationshipSchema.RELATION_HAS_CAPABILITY);
        assertEquals("has-capability-in-draft", InternalRelationshipSchema.RELATION_HAS_CAPABILITY_IN_DRAFT);
        assertEquals("accesses-to", InternalRelationshipSchema.RELATION_ACCESSES_TO);
        assertEquals("in-pir", InternalRelationshipSchema.RELATION_IN_PIR);
    }

    // ==================== 内部关系列表测试 ====================
    @Test
    void testInternalRelationships() {
        assertNotNull(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS);
        assertEquals(9, InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.size());

        // 验证包含所有关系类型
        assertTrue(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.contains("migrates"));
        assertTrue(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.contains("member-of"));
        assertTrue(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.contains("allowed-by"));
        assertTrue(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.contains("has-role"));
        assertTrue(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.contains("has-capability"));
        assertTrue(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.contains("has-capability-in-draft"));
        assertTrue(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.contains("accesses-to"));
        assertTrue(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.contains("participate-to"));
        assertTrue(InternalRelationshipSchema.INTERNAL_RELATIONSHIPS.contains("in-pir"));
    }

    // ==================== 类型判断方法测试 ====================
    @Test
    void testIsInternalRelationship() {
        // 测试内部关系类型
        assertTrue(InternalRelationshipSchema.isInternalRelationship("migrates"));
        assertTrue(InternalRelationshipSchema.isInternalRelationship("member-of"));
        assertTrue(InternalRelationshipSchema.isInternalRelationship("has-role"));
        assertTrue(InternalRelationshipSchema.isInternalRelationship("in-pir"));

        // 测试抽象内部关系类型
        assertTrue(InternalRelationshipSchema.isInternalRelationship(SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP));

        // 测试非内部关系类型
        assertFalse(InternalRelationshipSchema.isInternalRelationship("relationship"));
        assertFalse(InternalRelationshipSchema.isInternalRelationship("sighting"));

        // 测试 null
        assertFalse(InternalRelationshipSchema.isInternalRelationship(null));
    }

    @Test
    void testIsStoreRelationPir() {
        // 创建模拟的 StoreCommon 实例
        StoreCommon pirInstance = new StoreCommon() {
            @Override
            public String getEntityType() {
                return "in-pir";
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

        StoreCommon nonPirInstance = new StoreCommon() {
            @Override
            public String getEntityType() {
                return "member-of";
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

        // 测试 PIR 关系实例
        assertTrue(InternalRelationshipSchema.isStoreRelationPir(pirInstance));

        // 测试非 PIR 关系实例
        assertFalse(InternalRelationshipSchema.isStoreRelationPir(nonPirInstance));

        // 测试 null
        assertFalse(InternalRelationshipSchema.isStoreRelationPir(null));
    }

    // ==================== 类型注册测试 ====================
    @Test
    void testTypeRegistration() {
        // 验证类型是否已注册到 SchemaTypesDefinition
        assertTrue(SchemaTypesDefinition.isTypeIncludedIn("migrates", SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP));
        assertTrue(SchemaTypesDefinition.isTypeIncludedIn("member-of", SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP));
        assertTrue(SchemaTypesDefinition.isTypeIncludedIn("has-role", SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP));
        assertTrue(SchemaTypesDefinition.isTypeIncludedIn("in-pir", SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP));

        // 验证获取所有类型
        assertEquals(9, SchemaTypesDefinition.getTypes(SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP).size());
    }
}
