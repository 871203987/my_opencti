package io.opencti.schema.general;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SchemaGeneral 测试类
 * 测试所有常量和工具方法
 */
class SchemaGeneralTest {

    // ==================== STIX 类型常量测试 ====================
    @Test
    void testStixTypeConstants() {
        assertEquals("relationship", SchemaGeneral.STIX_TYPE_RELATION);
        assertEquals("sighting", SchemaGeneral.STIX_TYPE_SIGHTING);
    }

    // ==================== 权限常量测试 ====================
    @Test
    void testKnowledgeConstants() {
        assertEquals("KNUPDATE", SchemaGeneral.KNOWLEDGE_UPDATE);
        assertEquals("KNFRONTENDEXPORT", SchemaGeneral.KNOWLEDGE_FRONTEND_EXPORT);
        assertEquals("KNPARTICIPATE", SchemaGeneral.KNOWLEDGE_COLLABORATION);
    }

    // ==================== ID 类型常量测试 ====================
    @Test
    void testIdTypeConstants() {
        assertEquals("internal_id", SchemaGeneral.ID_INTERNAL);
        assertEquals("inferred_id", SchemaGeneral.ID_INFERRED);
        assertEquals("standard_id", SchemaGeneral.ID_STANDARD);
        assertEquals("i_aliases_ids", SchemaGeneral.INTERNAL_IDS_ALIASES);
        assertEquals("x_opencti_stix_ids", SchemaGeneral.IDS_STIX);
        assertEquals("RELATION", SchemaGeneral.BASE_TYPE_RELATION);
        assertEquals("ENTITY", SchemaGeneral.BASE_TYPE_ENTITY);
    }

    // ==================== 输入字段常量测试 ====================
    @Test
    void testInputFieldConstants() {
        assertEquals("objectOrganization", SchemaGeneral.INPUT_GRANTED_REFS);
        assertEquals("externalReferences", SchemaGeneral.INPUT_EXTERNAL_REFS);
        assertEquals("works", SchemaGeneral.INPUT_WORKS);
        assertEquals("internalFiles", SchemaGeneral.INPUT_INTERNAL_FILES);
        assertEquals("killChainPhases", SchemaGeneral.INPUT_KILLCHAIN);
        assertEquals("createdBy", SchemaGeneral.INPUT_CREATED_BY);
        assertEquals("objectLabel", SchemaGeneral.INPUT_LABELS);
        assertEquals("objectMarking", SchemaGeneral.INPUT_MARKINGS);
        assertEquals("objectAssignee", SchemaGeneral.INPUT_ASSIGNEE);
        assertEquals("objectParticipant", SchemaGeneral.INPUT_PARTICIPANT);
        assertEquals("objects", SchemaGeneral.INPUT_OBJECTS);
        assertEquals("from", SchemaGeneral.INPUT_DOMAIN_FROM);
        assertEquals("to", SchemaGeneral.INPUT_DOMAIN_TO);
        assertEquals("bornIn", SchemaGeneral.INPUT_BORN_IN);
        assertEquals("ethnicity", SchemaGeneral.INPUT_ETHNICITY);
        assertEquals("restricted_members", SchemaGeneral.INPUT_AUTHORIZED_MEMBERS);
        assertEquals("inPir", SchemaGeneral.INPUT_IN_PIR);
    }

    // ==================== 前缀常量测试 ====================
    @Test
    void testPrefixConstants() {
        assertEquals("rel_", SchemaGeneral.REL_INDEX_PREFIX);
        assertEquals("i_", SchemaGeneral.INTERNAL_PREFIX);
        assertEquals("i_rule_", SchemaGeneral.RULE_PREFIX);
    }

    // ==================== 连接器类型常量测试 ====================
    @Test
    void testConnectorTypeConstants() {
        assertEquals("INTERNAL_ENRICHMENT", SchemaGeneral.CONNECTOR_INTERNAL_ENRICHMENT);
        assertEquals("INTERNAL_IMPORT_FILE", SchemaGeneral.CONNECTOR_INTERNAL_IMPORT_FILE);
        assertEquals("INTERNAL_ANALYSIS", SchemaGeneral.CONNECTOR_INTERNAL_ANALYSIS);
        assertEquals("INTERNAL_EXPORT_FILE", SchemaGeneral.CONNECTOR_INTERNAL_EXPORT_FILE);
        assertEquals("INTERNAL_NOTIFICATION", SchemaGeneral.CONNECTOR_INTERNAL_NOTIFICATION);
        assertEquals("INTERNAL_INGESTION", SchemaGeneral.CONNECTOR_INTERNAL_INGESTION);
    }

    // ==================== UUID 常量测试 ====================
    @Test
    void testUuidConstants() {
        assertEquals("00abedb4-aa42-466c-9c01-fed23315a9b7", SchemaGeneral.OASIS_NAMESPACE);
        assertEquals("b639ff3b-00eb-42ed-aa36-a8dd6f8fb4cf", SchemaGeneral.OPENCTI_NAMESPACE);
        assertEquals("d06053cb-7123-404b-b092-6606411702d2", SchemaGeneral.OPENCTI_PLATFORM_UUID);
        assertEquals("88ec0c6a-13ce-5e39-b486-354fe4a7084f", SchemaGeneral.OPENCTI_ADMIN_UUID);
        assertEquals("6a4b11e1-90ca-4e42-ba42-db7bc7f7d505", SchemaGeneral.OPENCTI_SYSTEM_UUID);
    }

    // ==================== 关系抽象类型常量测试 ====================
    @Test
    void testAbstractRelationshipConstants() {
        assertEquals("basic-relationship", SchemaGeneral.ABSTRACT_BASIC_RELATIONSHIP);
        assertEquals("internal-relationship", SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP);
        assertEquals("stix-relationship", SchemaGeneral.ABSTRACT_STIX_RELATIONSHIP);
        assertEquals("stix-core-relationship", SchemaGeneral.ABSTRACT_STIX_CORE_RELATIONSHIP);
        assertEquals("stix-cyber-observable-relationship", SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE_RELATIONSHIP);
        assertEquals("stix-ref-relationship", SchemaGeneral.ABSTRACT_STIX_REF_RELATIONSHIP);
        assertEquals("stix-meta-relationship", SchemaGeneral.ABSTRACT_STIX_META_RELATIONSHIP);
    }

    // ==================== 实体抽象类型常量测试 ====================
    @Test
    void testAbstractObjectConstants() {
        assertEquals("Basic-Object", SchemaGeneral.ABSTRACT_BASIC_OBJECT);
        assertEquals("Stix-Object", SchemaGeneral.ABSTRACT_STIX_OBJECT);
        assertEquals("Stix-Meta-Object", SchemaGeneral.ABSTRACT_STIX_META_OBJECT);
        assertEquals("Stix-Core-Object", SchemaGeneral.ABSTRACT_STIX_CORE_OBJECT);
        assertEquals("Stix-Domain-Object", SchemaGeneral.ABSTRACT_STIX_DOMAIN_OBJECT);
        assertEquals("Stix-Cyber-Observable", SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE);
        assertEquals("Hashed-Observable", SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE);
        assertEquals("Internal-Object", SchemaGeneral.ABSTRACT_INTERNAL_OBJECT);
    }

    // ==================== 抽象类型常量测试 ====================
    @Test
    void testEntityTypeConstants() {
        assertEquals("Container", SchemaGeneral.ENTITY_TYPE_CONTAINER);
        assertEquals("Identity", SchemaGeneral.ENTITY_TYPE_IDENTITY);
        assertEquals("Location", SchemaGeneral.ENTITY_TYPE_LOCATION);
        assertEquals("Threat-Actor", SchemaGeneral.ENTITY_TYPE_THREAT_ACTOR);
    }

    // ==================== 抽象类型集合测试 ====================
    @Test
    void testAbstractTypesSet() {
        assertNotNull(SchemaGeneral.ABSTRACT_TYPES);
        assertTrue(SchemaGeneral.ABSTRACT_TYPES.contains(SchemaGeneral.ABSTRACT_BASIC_OBJECT));
        assertTrue(SchemaGeneral.ABSTRACT_TYPES.contains(SchemaGeneral.ABSTRACT_STIX_OBJECT));
        assertTrue(SchemaGeneral.ABSTRACT_TYPES.contains(SchemaGeneral.ENTITY_TYPE_CONTAINER));
        assertTrue(SchemaGeneral.ABSTRACT_TYPES.contains(SchemaGeneral.ABSTRACT_BASIC_RELATIONSHIP));
        assertFalse(SchemaGeneral.ABSTRACT_TYPES.contains("NonExistentType"));
    }

    @Test
    void testKnowledgeTypesSet() {
        assertNotNull(SchemaGeneral.KNOWLEDGE_TYPES);
        assertTrue(SchemaGeneral.KNOWLEDGE_TYPES.contains(SchemaGeneral.ABSTRACT_BASIC_OBJECT));
        assertTrue(SchemaGeneral.KNOWLEDGE_TYPES.contains(SchemaGeneral.ABSTRACT_STIX_RELATIONSHIP));
        assertTrue(SchemaGeneral.KNOWLEDGE_TYPES.contains(SchemaGeneral.ENTITY_TYPE_CONTAINER));
        assertFalse(SchemaGeneral.KNOWLEDGE_TYPES.contains("NonExistentType"));
    }

    // ==================== 工具方法测试 ====================
    @Test
    void testBuildRefRelationKey() {
        // 测试带字段参数
        assertEquals("rel_Malware.internal_id", SchemaGeneral.buildRefRelationKey("Malware", "internal_id"));
        assertEquals("rel_Malware.standard_id", SchemaGeneral.buildRefRelationKey("Malware", "standard_id"));

        // 测试默认字段
        assertEquals("rel_Malware.internal_id", SchemaGeneral.buildRefRelationKey("Malware"));

        // 测试 null 字段（应使用默认值）
        assertEquals("rel_Malware.internal_id", SchemaGeneral.buildRefRelationKey("Malware", null));

        // 测试空字符串字段（应使用默认值）
        assertEquals("rel_Malware.internal_id", SchemaGeneral.buildRefRelationKey("Malware", ""));
    }

    @Test
    void testBuildRefRelationSearchKey() {
        // 测试带字段参数
        assertEquals("rel_Malware.internal_id.keyword", SchemaGeneral.buildRefRelationSearchKey("Malware", "internal_id"));
        assertEquals("rel_Malware.standard_id.keyword", SchemaGeneral.buildRefRelationSearchKey("Malware", "standard_id"));

        // 测试默认字段
        assertEquals("rel_Malware.internal_id.keyword", SchemaGeneral.buildRefRelationSearchKey("Malware"));
    }

    @Test
    void testIsAbstract() {
        // 测试抽象类型
        assertTrue(SchemaGeneral.isAbstract(SchemaGeneral.ABSTRACT_BASIC_OBJECT));
        assertTrue(SchemaGeneral.isAbstract(SchemaGeneral.ABSTRACT_STIX_OBJECT));
        assertTrue(SchemaGeneral.isAbstract(SchemaGeneral.ENTITY_TYPE_CONTAINER));
        assertTrue(SchemaGeneral.isAbstract(SchemaGeneral.ABSTRACT_BASIC_RELATIONSHIP));

        // 测试非抽象类型
        assertFalse(SchemaGeneral.isAbstract("Malware"));
        assertFalse(SchemaGeneral.isAbstract("NonExistentType"));

        // 测试 null
        assertFalse(SchemaGeneral.isAbstract(null));
    }

    @Test
    void testIsKnowledge() {
        // 测试知识类型
        assertTrue(SchemaGeneral.isKnowledge(SchemaGeneral.ABSTRACT_BASIC_OBJECT));
        assertTrue(SchemaGeneral.isKnowledge(SchemaGeneral.ABSTRACT_STIX_RELATIONSHIP));
        assertTrue(SchemaGeneral.isKnowledge(SchemaGeneral.ENTITY_TYPE_CONTAINER));

        // 测试非知识类型
        assertFalse(SchemaGeneral.isKnowledge("NonExistentType"));

        // 测试 null
        assertFalse(SchemaGeneral.isKnowledge(null));
    }
}
