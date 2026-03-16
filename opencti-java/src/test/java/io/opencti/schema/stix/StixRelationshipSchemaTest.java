package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;
import io.opencti.schema.internal.InternalRelationshipSchema;
import io.opencti.types.stix.common.StixObject;
import io.opencti.types.stix.sro.StixRelation;
import io.opencti.types.stix.sro.StixSighting;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StixRelationshipSchema 单元测试
 */
class StixRelationshipSchemaTest {

    // ==================== isStixRelationshipExceptRef 测试 ====================

    @Test
    void testIsStixRelationshipExceptRef_WithCoreRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isStixRelationshipExceptRef("uses"));
        assertTrue(StixRelationshipSchema.isStixRelationshipExceptRef("targets"));
        assertTrue(StixRelationshipSchema.isStixRelationshipExceptRef("indicates"));
    }

    @Test
    void testIsStixRelationshipExceptRef_WithSightingRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isStixRelationshipExceptRef("stix-sighting-relationship"));
    }

    @Test
    void testIsStixRelationshipExceptRef_WithRefRelationship_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isStixRelationshipExceptRef("created-by"));
        assertFalse(StixRelationshipSchema.isStixRelationshipExceptRef("object-marking"));
    }

    @Test
    void testIsStixRelationshipExceptRef_WithInternalRelationship_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isStixRelationshipExceptRef("member-of"));
        assertFalse(StixRelationshipSchema.isStixRelationshipExceptRef("has-role"));
    }

    @Test
    void testIsStixRelationshipExceptRef_WithNull_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isStixRelationshipExceptRef(null));
    }

    @Test
    void testIsStixRelationshipExceptRef_WithUnknownType_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isStixRelationshipExceptRef("unknown-type"));
    }

    // ==================== isStixRelationship 测试 ====================

    @Test
    void testIsStixRelationship_WithCoreRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isStixRelationship("uses"));
        assertTrue(StixRelationshipSchema.isStixRelationship("targets"));
        assertTrue(StixRelationshipSchema.isStixRelationship("indicates"));
    }

    @Test
    void testIsStixRelationship_WithSightingRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isStixRelationship("stix-sighting-relationship"));
    }

    @Test
    void testIsStixRelationship_WithRefRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isStixRelationship("created-by"));
        assertTrue(StixRelationshipSchema.isStixRelationship("object-marking"));
        assertTrue(StixRelationshipSchema.isStixRelationship("operating-system"));
    }

    @Test
    void testIsStixRelationship_WithAbstractStixRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isStixRelationship(SchemaGeneral.ABSTRACT_STIX_RELATIONSHIP));
    }

    @Test
    void testIsStixRelationship_WithInternalRelationship_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isStixRelationship("member-of"));
        assertFalse(StixRelationshipSchema.isStixRelationship("has-role"));
    }

    @Test
    void testIsStixRelationship_WithNull_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isStixRelationship(null));
    }

    @Test
    void testIsStixRelationship_WithUnknownType_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isStixRelationship("unknown-type"));
    }

    // ==================== isBasicRelationship 测试 ====================

    @Test
    void testIsBasicRelationship_WithInternalRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isBasicRelationship("member-of"));
        assertTrue(StixRelationshipSchema.isBasicRelationship("has-role"));
        assertTrue(StixRelationshipSchema.isBasicRelationship("migrates"));
    }

    @Test
    void testIsBasicRelationship_WithCoreRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isBasicRelationship("uses"));
        assertTrue(StixRelationshipSchema.isBasicRelationship("targets"));
    }

    @Test
    void testIsBasicRelationship_WithSightingRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isBasicRelationship("stix-sighting-relationship"));
    }

    @Test
    void testIsBasicRelationship_WithRefRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isBasicRelationship("created-by"));
        assertTrue(StixRelationshipSchema.isBasicRelationship("object-marking"));
    }

    @Test
    void testIsBasicRelationship_WithAbstractBasicRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isBasicRelationship(SchemaGeneral.ABSTRACT_BASIC_RELATIONSHIP));
    }

    @Test
    void testIsBasicRelationship_WithAbstractStixRelationship_ReturnsTrue() {
        assertTrue(StixRelationshipSchema.isBasicRelationship(SchemaGeneral.ABSTRACT_STIX_RELATIONSHIP));
    }

    @Test
    void testIsBasicRelationship_WithNull_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isBasicRelationship(null));
    }

    @Test
    void testIsBasicRelationship_WithUnknownType_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isBasicRelationship("unknown-type"));
    }

    // ==================== isStixRelation 测试 ====================

    @Test
    void testIsStixRelation_WithStixRelation_ReturnsTrue() {
        StixRelation relation = new StixRelation();
        assertTrue(StixRelationshipSchema.isStixRelation(relation));
    }

    @Test
    void testIsStixRelation_WithStixSighting_ReturnsFalse() {
        StixSighting sighting = new StixSighting();
        assertFalse(StixRelationshipSchema.isStixRelation(sighting));
    }

    @Test
    void testIsStixRelation_WithNull_ReturnsFalse() {
        assertFalse(StixRelationshipSchema.isStixRelation(null));
    }

    // ==================== 集成测试 ====================

    @Test
    void testRelationshipTypeHierarchy() {
        // 核心关系应该是STIX关系，也是基本关系
        String coreRelation = "uses";
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship(coreRelation));
        assertTrue(StixRelationshipSchema.isStixRelationshipExceptRef(coreRelation));
        assertTrue(StixRelationshipSchema.isStixRelationship(coreRelation));
        assertTrue(StixRelationshipSchema.isBasicRelationship(coreRelation));

        // 目击关系应该是STIX关系，也是基本关系
        String sightingRelation = StixSightingRelationshipSchema.STIX_SIGHTING_RELATIONSHIP;
        assertTrue(StixSightingRelationshipSchema.isStixSightingRelationship(sightingRelation));
        assertTrue(StixRelationshipSchema.isStixRelationshipExceptRef(sightingRelation));
        assertTrue(StixRelationshipSchema.isStixRelationship(sightingRelation));
        assertTrue(StixRelationshipSchema.isBasicRelationship(sightingRelation));

        // 引用关系应该是STIX关系（不含引用关系判断应该返回false），也是基本关系
        String refRelation = "created-by";
        assertTrue(StixRefRelationshipSchema.isStixRefRelationship(refRelation));
        assertFalse(StixRelationshipSchema.isStixRelationshipExceptRef(refRelation));
        assertTrue(StixRelationshipSchema.isStixRelationship(refRelation));
        assertTrue(StixRelationshipSchema.isBasicRelationship(refRelation));

        // 内部关系不是STIX关系，但是基本关系
        String internalRelation = "member-of";
        assertTrue(InternalRelationshipSchema.isInternalRelationship(internalRelation));
        assertFalse(StixRelationshipSchema.isStixRelationshipExceptRef(internalRelation));
        assertFalse(StixRelationshipSchema.isStixRelationship(internalRelation));
        assertTrue(StixRelationshipSchema.isBasicRelationship(internalRelation));
    }
}
