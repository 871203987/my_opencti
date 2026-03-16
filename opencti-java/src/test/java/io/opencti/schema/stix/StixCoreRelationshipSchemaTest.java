package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StixCoreRelationshipSchema 单元测试
 */
class StixCoreRelationshipSchemaTest {

    // ==================== 标准STIX关系常量测试 ====================

    @Test
    void testStandardStixRelationConstants() {
        assertEquals("delivers", StixCoreRelationshipSchema.RELATION_DELIVERS);
        assertEquals("targets", StixCoreRelationshipSchema.RELATION_TARGETS);
        assertEquals("uses", StixCoreRelationshipSchema.RELATION_USES);
        assertEquals("attributed-to", StixCoreRelationshipSchema.RELATION_ATTRIBUTED_TO);
        assertEquals("compromises", StixCoreRelationshipSchema.RELATION_COMPROMISES);
        assertEquals("originates-from", StixCoreRelationshipSchema.RELATION_ORIGINATES_FROM);
        assertEquals("investigates", StixCoreRelationshipSchema.RELATION_INVESTIGATES);
        assertEquals("mitigates", StixCoreRelationshipSchema.RELATION_MITIGATES);
        assertEquals("located-at", StixCoreRelationshipSchema.RELATION_LOCATED_AT);
        assertEquals("indicates", StixCoreRelationshipSchema.RELATION_INDICATES);
        assertEquals("based-on", StixCoreRelationshipSchema.RELATION_BASED_ON);
        assertEquals("communicates-with", StixCoreRelationshipSchema.RELATION_COMMUNICATES_WITH);
        assertEquals("consists-of", StixCoreRelationshipSchema.RELATION_CONSISTS_OF);
        assertEquals("controls", StixCoreRelationshipSchema.RELATION_CONTROLS);
        assertEquals("has", StixCoreRelationshipSchema.RELATION_HAS);
        assertEquals("hosts", StixCoreRelationshipSchema.RELATION_HOSTS);
        assertEquals("owns", StixCoreRelationshipSchema.RELATION_OWNS);
        assertEquals("authored-by", StixCoreRelationshipSchema.RELATION_AUTHORED_BY);
        assertEquals("beacons-to", StixCoreRelationshipSchema.RELATION_BEACONS_TO);
        assertEquals("exfiltrates-to", StixCoreRelationshipSchema.RELATION_EXFILTRATES_TO);
        assertEquals("downloads", StixCoreRelationshipSchema.RELATION_DOWNLOADS);
        assertEquals("drops", StixCoreRelationshipSchema.RELATION_DROPS);
        assertEquals("exploits", StixCoreRelationshipSchema.RELATION_EXPLOITS);
        assertEquals("variant-of", StixCoreRelationshipSchema.RELATION_VARIANT_OF);
        assertEquals("characterizes", StixCoreRelationshipSchema.RELATION_CHARACTERIZES);
        assertEquals("analysis-of", StixCoreRelationshipSchema.RELATION_ANALYSIS_OF);
        assertEquals("static-analysis-of", StixCoreRelationshipSchema.RELATION_STATIC_ANALYSIS_OF);
        assertEquals("dynamic-analysis-of", StixCoreRelationshipSchema.RELATION_DYNAMIC_ANALYSIS_OF);
        assertEquals("impersonates", StixCoreRelationshipSchema.RELATION_IMPERSONATES);
        assertEquals("remediates", StixCoreRelationshipSchema.RELATION_REMEDIATES);
        assertEquals("related-to", StixCoreRelationshipSchema.RELATION_RELATED_TO);
        assertEquals("derived-from", StixCoreRelationshipSchema.RELATION_DERIVED_FROM);
        assertEquals("duplicate-of", StixCoreRelationshipSchema.RELATION_DUPLICATE_OF);
        assertEquals("belongs-to", StixCoreRelationshipSchema.RELATION_BELONGS_TO);
        assertEquals("resolves-to", StixCoreRelationshipSchema.RELATION_RESOLVES_TO);
        assertEquals("technology", StixCoreRelationshipSchema.RELATION_TECHNOLOGY);
        assertEquals("technology-to", StixCoreRelationshipSchema.RELATION_TECHNOLOGY_TO);
        assertEquals("technology-from", StixCoreRelationshipSchema.RELATION_TECHNOLOGY_FROM);
        assertEquals("transferred-to", StixCoreRelationshipSchema.RELATION_TRANSFERRED_TO);
        assertEquals("demonstrates", StixCoreRelationshipSchema.RELATION_DEMONSTRATES);
    }

    // ==================== OpenCTI扩展关系常量测试 ====================

    @Test
    void testOpenCtiExtensionRelationConstants() {
        assertEquals("part-of", StixCoreRelationshipSchema.RELATION_PART_OF);
        assertEquals("cooperates-with", StixCoreRelationshipSchema.RELATION_COOPERATES_WITH);
        assertEquals("participates-in", StixCoreRelationshipSchema.RELATION_PARTICIPATES_IN);
        assertEquals("publishes", StixCoreRelationshipSchema.RELATION_PUBLISHES);
        assertEquals("amplifies", StixCoreRelationshipSchema.RELATION_AMPLIFIES);
        assertEquals("subnarrative-of", StixCoreRelationshipSchema.RELATION_SUBNARRATIVE_OF);
        assertEquals("employed-by", StixCoreRelationshipSchema.RELATION_EMPLOYED_BY);
        assertEquals("resides-in", StixCoreRelationshipSchema.RELATION_RESIDES_IN);
        assertEquals("citizen-of", StixCoreRelationshipSchema.RELATION_CITIZEN_OF);
        assertEquals("national-of", StixCoreRelationshipSchema.RELATION_NATIONAL_OF);
        assertEquals("known-as", StixCoreRelationshipSchema.RELATION_KNOWN_AS);
        assertEquals("reports-to", StixCoreRelationshipSchema.RELATION_REPORTS_TO);
        assertEquals("supports", StixCoreRelationshipSchema.RELATION_SUPPORTS);
        assertEquals("should-cover", StixCoreRelationshipSchema.RELATION_SHOULD_COVER);
        assertEquals("has-covered", StixCoreRelationshipSchema.RELATION_HAS_COVERED);
    }

    // ==================== MITRE扩展关系常量测试 ====================

    @Test
    void testMitreExtensionRelationConstants() {
        assertEquals("subtechnique-of", StixCoreRelationshipSchema.RELATION_SUBTECHNIQUE_OF);
        assertEquals("revoked-by", StixCoreRelationshipSchema.RELATION_REVOKED_BY);
        assertEquals("detects", StixCoreRelationshipSchema.RELATION_DETECTS);
    }

    // ==================== STIX核心关系列表测试 ====================

    @Test
    void testStixCoreRelationshipsListSize() {
        assertEquals(58, StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIPS.size());
    }

    @Test
    void testStixCoreRelationshipsListContainsAllRelations() {
        Set<String> expectedRelations = new HashSet<>(Arrays.asList(
                "delivers", "targets", "uses", "beacons-to", "attributed-to",
                "exfiltrates-to", "compromises", "downloads", "exploits", "characterizes",
                "analysis-of", "static-analysis-of", "dynamic-analysis-of", "derived-from", "duplicate-of",
                "originates-from", "investigates", "located-at", "based-on", "hosts",
                "owns", "authored-by", "communicates-with", "mitigates", "controls",
                "has", "consists-of", "indicates", "variant-of", "impersonates",
                "remediates", "related-to", "drops", "part-of", "cooperates-with",
                "participates-in", "subtechnique-of", "revoked-by", "belongs-to", "resolves-to",
                "technology", "technology-to", "technology-from", "transferred-to", "demonstrates",
                "detects", "publishes", "amplifies", "subnarrative-of", "employed-by",
                "resides-in", "citizen-of", "national-of", "known-as", "reports-to",
                "supports", "should-cover", "has-covered"
        ));

        Set<String> actualRelations = new HashSet<>(StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIPS);
        assertEquals(expectedRelations, actualRelations);
    }

    @Test
    void testStixCoreRelationshipsListIsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> {
            StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIPS.add("new-relation");
        });
    }

    // ==================== isStixCoreRelationship 方法测试 ====================

    @Test
    void testIsStixCoreRelationshipWithValidRelations() {
        // 测试标准STIX关系
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship("delivers"));
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship("targets"));
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship("uses"));
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship("related-to"));

        // 测试OpenCTI扩展关系
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship("part-of"));
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship("cooperates-with"));
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship("participates-in"));

        // 测试MITRE扩展关系
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship("subtechnique-of"));
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship("detects"));
    }

    @Test
    void testIsStixCoreRelationshipWithAbstractType() {
        assertTrue(StixCoreRelationshipSchema.isStixCoreRelationship(SchemaGeneral.ABSTRACT_STIX_CORE_RELATIONSHIP));
    }

    @Test
    void testIsStixCoreRelationshipWithInvalidTypes() {
        assertFalse(StixCoreRelationshipSchema.isStixCoreRelationship("invalid-relation"));
        assertFalse(StixCoreRelationshipSchema.isStixCoreRelationship(""));
        assertFalse(StixCoreRelationshipSchema.isStixCoreRelationship("internal-relationship"));
        assertFalse(StixCoreRelationshipSchema.isStixCoreRelationship("stix-ref-relationship"));
    }

    @Test
    void testIsStixCoreRelationshipWithNull() {
        assertFalse(StixCoreRelationshipSchema.isStixCoreRelationship(null));
    }

    // ==================== STIX核心关系选项测试 ====================

    @Test
    void testStixCoreRelationshipOptionsNotNull() {
        assertNotNull(StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIP_OPTIONS);
    }

    @Test
    void testStixCoreRelationshipOptionsContainsOrdering() {
        assertTrue(StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIP_OPTIONS.containsKey("StixCoreRelationshipsOrdering"));
    }

    @Test
    void testStixCoreRelationshipOptionsOrderingIsEmptyMap() {
        Object ordering = StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIP_OPTIONS.get("StixCoreRelationshipsOrdering");
        assertTrue(ordering instanceof Map);
        assertTrue(((Map<?, ?>) ordering).isEmpty());
    }

    @Test
    void testStixCoreRelationshipOptionsIsUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () -> {
            StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIP_OPTIONS.put("new-key", "new-value");
        });
    }

    // ==================== 边界条件测试 ====================

    @Test
    void testAllRelationConstantsInList() {
        // 验证所有定义的常量都在STIX_CORE_RELATIONSHIPS列表中
        assertTrue(StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIPS.contains(StixCoreRelationshipSchema.RELATION_DELIVERS));
        assertTrue(StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIPS.contains(StixCoreRelationshipSchema.RELATION_TARGETS));
        assertTrue(StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIPS.contains(StixCoreRelationshipSchema.RELATION_USES));
        assertTrue(StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIPS.contains(StixCoreRelationshipSchema.RELATION_PART_OF));
        assertTrue(StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIPS.contains(StixCoreRelationshipSchema.RELATION_SUBTECHNIQUE_OF));
        assertTrue(StixCoreRelationshipSchema.STIX_CORE_RELATIONSHIPS.contains(StixCoreRelationshipSchema.RELATION_DETECTS));
    }
}
