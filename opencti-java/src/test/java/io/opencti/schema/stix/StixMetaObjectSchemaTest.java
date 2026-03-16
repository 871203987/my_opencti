package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StixMetaObjectSchema 单元测试
 * 测试STIX元对象Schema定义的正确性
 */
@DisplayName("StixMetaObjectSchema 测试")
class StixMetaObjectSchemaTest {

    // ==================== 类型常量测试 ====================

    @Test
    @DisplayName("ENTITY_TYPE_LABEL 常量值应为 'Label'")
    void testEntityTypeLabel() {
        assertEquals("Label", StixMetaObjectSchema.ENTITY_TYPE_LABEL);
    }

    @Test
    @DisplayName("ENTITY_TYPE_EXTERNAL_REFERENCE 常量值应为 'External-Reference'")
    void testEntityTypeExternalReference() {
        assertEquals("External-Reference", StixMetaObjectSchema.ENTITY_TYPE_EXTERNAL_REFERENCE);
    }

    @Test
    @DisplayName("ENTITY_TYPE_KILL_CHAIN_PHASE 常量值应为 'Kill-Chain-Phase'")
    void testEntityTypeKillChainPhase() {
        assertEquals("Kill-Chain-Phase", StixMetaObjectSchema.ENTITY_TYPE_KILL_CHAIN_PHASE);
    }

    @Test
    @DisplayName("ENTITY_TYPE_MARKING_DEFINITION 常量值应为 'Marking-Definition'")
    void testEntityTypeMarkingDefinition() {
        assertEquals("Marking-Definition", StixMetaObjectSchema.ENTITY_TYPE_MARKING_DEFINITION);
    }

    // ==================== 列表测试 ====================

    @Test
    @DisplayName("STIX_EMBEDDED_OBJECT 列表应包含3种类型")
    void testStixEmbeddedObjectSize() {
        assertEquals(3, StixMetaObjectSchema.STIX_EMBEDDED_OBJECT.size());
    }

    @Test
    @DisplayName("STIX_EMBEDDED_OBJECT 列表应包含 Label")
    void testStixEmbeddedObjectContainsLabel() {
        assertTrue(StixMetaObjectSchema.STIX_EMBEDDED_OBJECT.contains("Label"));
    }

    @Test
    @DisplayName("STIX_EMBEDDED_OBJECT 列表应包含 External-Reference")
    void testStixEmbeddedObjectContainsExternalReference() {
        assertTrue(StixMetaObjectSchema.STIX_EMBEDDED_OBJECT.contains("External-Reference"));
    }

    @Test
    @DisplayName("STIX_EMBEDDED_OBJECT 列表应包含 Kill-Chain-Phase")
    void testStixEmbeddedObjectContainsKillChainPhase() {
        assertTrue(StixMetaObjectSchema.STIX_EMBEDDED_OBJECT.contains("Kill-Chain-Phase"));
    }

    @Test
    @DisplayName("STIX_META_OBJECT 列表应包含4种类型")
    void testStixMetaObjectSize() {
        assertEquals(4, StixMetaObjectSchema.STIX_META_OBJECT.size());
    }

    @Test
    @DisplayName("STIX_META_OBJECT 列表应包含所有STIX_EMBEDDED_OBJECT类型")
    void testStixMetaObjectContainsAllEmbedded() {
        assertTrue(StixMetaObjectSchema.STIX_META_OBJECT.containsAll(StixMetaObjectSchema.STIX_EMBEDDED_OBJECT));
    }

    @Test
    @DisplayName("STIX_META_OBJECT 列表应包含 Marking-Definition")
    void testStixMetaObjectContainsMarkingDefinition() {
        assertTrue(StixMetaObjectSchema.STIX_META_OBJECT.contains("Marking-Definition"));
    }

    // ==================== 类型判断方法测试 ====================

    @Test
    @DisplayName("isStixMetaObject 对 Label 应返回 true")
    void testIsStixMetaObjectForLabel() {
        assertTrue(StixMetaObjectSchema.isStixMetaObject("Label"));
    }

    @Test
    @DisplayName("isStixMetaObject 对 External-Reference 应返回 true")
    void testIsStixMetaObjectForExternalReference() {
        assertTrue(StixMetaObjectSchema.isStixMetaObject("External-Reference"));
    }

    @Test
    @DisplayName("isStixMetaObject 对 Kill-Chain-Phase 应返回 true")
    void testIsStixMetaObjectForKillChainPhase() {
        assertTrue(StixMetaObjectSchema.isStixMetaObject("Kill-Chain-Phase"));
    }

    @Test
    @DisplayName("isStixMetaObject 对 Marking-Definition 应返回 true")
    void testIsStixMetaObjectForMarkingDefinition() {
        assertTrue(StixMetaObjectSchema.isStixMetaObject("Marking-Definition"));
    }

    @Test
    @DisplayName("isStixMetaObject 对抽象类型 Stix-Meta-Object 应返回 true")
    void testIsStixMetaObjectForAbstractType() {
        assertTrue(StixMetaObjectSchema.isStixMetaObject(SchemaGeneral.ABSTRACT_STIX_META_OBJECT));
    }

    @Test
    @DisplayName("isStixMetaObject 对非元对象类型应返回 false")
    void testIsStixMetaObjectForNonMetaObject() {
        assertFalse(StixMetaObjectSchema.isStixMetaObject("Attack-Pattern"));
        assertFalse(StixMetaObjectSchema.isStixMetaObject("Malware"));
        assertFalse(StixMetaObjectSchema.isStixMetaObject("Invalid-Type"));
    }

    @Test
    @DisplayName("isStixMetaObject 对 null 应返回 false")
    void testIsStixMetaObjectForNull() {
        assertFalse(StixMetaObjectSchema.isStixMetaObject(null));
    }

    @Test
    @DisplayName("isStixMetaObject 对空字符串应返回 false")
    void testIsStixMetaObjectForEmptyString() {
        assertFalse(StixMetaObjectSchema.isStixMetaObject(""));
    }
}
