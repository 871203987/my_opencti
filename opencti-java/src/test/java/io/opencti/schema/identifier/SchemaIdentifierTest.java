package io.opencti.schema.identifier;

import io.opencti.schema.general.SchemaGeneral;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SchemaIdentifier 测试类
 * 测试所有常量和工具方法
 */
class SchemaIdentifierTest {

    // ==================== 哈希算法常量测试 ====================
    @Test
    void testHashAlgorithmConstants() {
        assertEquals("MD5", SchemaIdentifier.MD5);
        assertEquals("SHA-1", SchemaIdentifier.SHA_1);
        assertEquals("SHA-256", SchemaIdentifier.SHA_256);
        assertEquals("SHA-512", SchemaIdentifier.SHA_512);
        assertEquals("SHA3-256", SchemaIdentifier.SHA3_256);
        assertEquals("SHA3-512", SchemaIdentifier.SHA3_512);
        assertEquals("SSDEEP", SchemaIdentifier.SSDEEP);
    }

    // ==================== 字段名常量测试 ====================
    @Test
    void testFieldNameConstants() {
        assertEquals("i_relations_from", SchemaIdentifier.INTERNAL_FROM_FIELD);
        assertEquals("i_relations_to", SchemaIdentifier.INTERNAL_TO_FIELD);
        assertEquals("name", SchemaIdentifier.NAME_FIELD);
        assertEquals("opencti_type", SchemaIdentifier.INNER_TYPE);
        assertEquals("value", SchemaIdentifier.VALUE_FIELD);
        assertEquals("first_seen", SchemaIdentifier.FIRST_SEEN);
        assertEquals("last_seen", SchemaIdentifier.LAST_SEEN);
        assertEquals("start_time", SchemaIdentifier.START_TIME);
        assertEquals("stop_time", SchemaIdentifier.STOP_TIME);
        assertEquals("valid_from", SchemaIdentifier.VALID_FROM);
        assertEquals("first_observed", SchemaIdentifier.FIRST_OBSERVED);
        assertEquals("last_observed", SchemaIdentifier.LAST_OBSERVED);
        assertEquals("valid_until", SchemaIdentifier.VALID_UNTIL);
        assertEquals("revoked", SchemaIdentifier.REVOKED);
        assertEquals("x_mitre_id", SchemaIdentifier.X_MITRE_ID_FIELD);
        assertEquals("x_opencti_detection", SchemaIdentifier.X_DETECTION);
        assertEquals("x_opencti_workflow_id", SchemaIdentifier.X_WORKFLOW_ID);
        assertEquals("x_opencti_score", SchemaIdentifier.X_SCORE);
    }

    // ==================== TLP标记常量测试 ====================
    @Test
    void testTlpMarkingConstants() {
        assertEquals("613f2e26-407d-48c7-9eca-b8e91df99dc9", SchemaIdentifier.MARKING_TLP_CLEAR_ID);
        assertEquals("marking-definition--613f2e26-407d-48c7-9eca-b8e91df99dc9", SchemaIdentifier.MARKING_TLP_CLEAR);
        assertEquals("34098fce-860f-48ae-8e50-ebd3cc5e41da", SchemaIdentifier.MARKING_TLP_GREEN_ID);
        assertEquals("marking-definition--34098fce-860f-48ae-8e50-ebd3cc5e41da", SchemaIdentifier.MARKING_TLP_GREEN);
        assertEquals("f88d31f6-486f-44da-b317-01333bde0b82", SchemaIdentifier.MARKING_TLP_AMBER_ID);
        assertEquals("marking-definition--f88d31f6-486f-44da-b317-01333bde0b82", SchemaIdentifier.MARKING_TLP_AMBER);
        assertEquals("826578e1-40ad-459f-bc73-ede076f81f37", SchemaIdentifier.MARKING_TLP_AMBER_STRICT_ID);
        assertEquals("marking-definition--826578e1-40ad-459f-bc73-ede076f81f37", SchemaIdentifier.MARKING_TLP_AMBER_STRICT);
        assertEquals("5e57c739-391a-4eb3-b6be-7d15ca92d5ed", SchemaIdentifier.MARKING_TLP_RED_ID);
        assertEquals("marking-definition--5e57c739-391a-4eb3-b6be-7d15ca92d5ed", SchemaIdentifier.MARKING_TLP_RED);
    }

    @Test
    void testStaticMarkingIds() {
        assertNotNull(SchemaIdentifier.STATIC_MARKING_IDS);
        assertEquals(5, SchemaIdentifier.STATIC_MARKING_IDS.size());
        assertTrue(SchemaIdentifier.STATIC_MARKING_IDS.contains(SchemaIdentifier.MARKING_TLP_CLEAR));
        assertTrue(SchemaIdentifier.STATIC_MARKING_IDS.contains(SchemaIdentifier.MARKING_TLP_GREEN));
        assertTrue(SchemaIdentifier.STATIC_MARKING_IDS.contains(SchemaIdentifier.MARKING_TLP_AMBER));
        assertTrue(SchemaIdentifier.STATIC_MARKING_IDS.contains(SchemaIdentifier.MARKING_TLP_AMBER_STRICT));
        assertTrue(SchemaIdentifier.STATIC_MARKING_IDS.contains(SchemaIdentifier.MARKING_TLP_RED));
    }

    @Test
    void testStaticStandardIds() {
        assertNotNull(SchemaIdentifier.STATIC_STANDARD_IDS);
        assertEquals(6, SchemaIdentifier.STATIC_STANDARD_IDS.size());
    }

    // ==================== 工具方法测试 ====================
    @Test
    void testNormalizeName() {
        assertEquals("test", SchemaIdentifier.normalizeName("TEST"));
        assertEquals("test", SchemaIdentifier.normalizeName("  TEST  "));
        assertEquals("test name", SchemaIdentifier.normalizeName("Test Name"));
        assertEquals("", SchemaIdentifier.normalizeName(null));
        assertEquals("", SchemaIdentifier.normalizeName(""));
    }

    @Test
    void testGetStaticIdFromData() {
        Map<String, Object> data = new HashMap<>();
        data.put("definition_type", "TLP");
        data.put("definition", "TLP:WHITE");

        String id = SchemaIdentifier.getStaticIdFromData(data);
        assertEquals(SchemaIdentifier.MARKING_TLP_CLEAR_ID, id);

        // 测试不匹配的数据
        Map<String, Object> noMatchData = new HashMap<>();
        noMatchData.put("definition_type", "TLP");
        noMatchData.put("definition", "TLP:UNKNOWN");
        assertNull(SchemaIdentifier.getStaticIdFromData(noMatchData));
    }

    @Test
    void testGenerateInternalId() {
        String id1 = SchemaIdentifier.generateInternalId();
        String id2 = SchemaIdentifier.generateInternalId();

        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2);

        // 验证UUID格式
        assertDoesNotThrow(() -> UUID.fromString(id1));
        assertDoesNotThrow(() -> UUID.fromString(id2));
    }

    @Test
    void testGenerateWorkId() {
        String connectorId = "test-connector";
        SchemaIdentifier.WorkId workId = SchemaIdentifier.generateWorkId(connectorId);

        assertNotNull(workId);
        assertNotNull(workId.getId());
        assertTrue(workId.getId().startsWith("work_" + connectorId + "_"));
        assertTrue(workId.getTimestamp() > 0);

        // 验证格式
        String[] parts = workId.getId().split("_");
        assertEquals(3, parts.length);
        assertEquals("work", parts[0]);
        assertEquals(connectorId, parts[1]);
    }

    @Test
    void testGenerateFileIndexId() {
        String fileId = "test-file-id";
        String indexId = SchemaIdentifier.generateFileIndexId(fileId);

        assertNotNull(indexId);
        assertDoesNotThrow(() -> UUID.fromString(indexId));

        // 相同的fileId应该生成相同的indexId
        String indexId2 = SchemaIdentifier.generateFileIndexId(fileId);
        assertEquals(indexId, indexId2);

        // 不同的fileId应该生成不同的indexId
        String indexId3 = SchemaIdentifier.generateFileIndexId("different-file-id");
        assertNotEquals(indexId, indexId3);
    }

    // ==================== ID生成方法测试 ====================
    @Test
    void testGenerateStandardId() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Test Entity");

        String id1 = SchemaIdentifier.generateStandardId("TestType", data);
        String id2 = SchemaIdentifier.generateStandardId("TestType", data);

        assertNotNull(id1);
        assertNotNull(id2);
        assertEquals(id1, id2); // 相同的数据应该生成相同的ID

        // 不同的数据应该生成不同的ID
        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "Different Entity");
        String id3 = SchemaIdentifier.generateStandardId("TestType", data2);
        assertNotEquals(id1, id3);
    }

    // ==================== 别名ID生成测试 ====================
    @Test
    void testGenerateAliasesId() {
        Map<String, Object> instance = new HashMap<>();
        instance.put("entity_type", "Malware");
        instance.put("name", "Test Malware");

        List<String> aliases = Arrays.asList("Alias1", "Alias2", "Alias3");
        List<String> aliasIds = SchemaIdentifier.generateAliasesId(aliases, instance);

        assertNotNull(aliasIds);
        assertEquals(3, aliasIds.size());

        // 验证每个别名都生成了唯一的ID
        Set<String> uniqueIds = new HashSet<>(aliasIds);
        assertEquals(3, uniqueIds.size());
    }

    @Test
    void testGenerateAliasesIdWithNullOrEmpty() {
        Map<String, Object> instance = new HashMap<>();
        instance.put("entity_type", "Malware");

        // 测试null
        List<String> result1 = SchemaIdentifier.generateAliasesId(null, instance);
        assertTrue(result1.isEmpty());

        // 测试空列表
        List<String> result2 = SchemaIdentifier.generateAliasesId(Collections.emptyList(), instance);
        assertTrue(result2.isEmpty());

        // 测试没有entity_type的实例
        Map<String, Object> noTypeInstance = new HashMap<>();
        List<String> result3 = SchemaIdentifier.generateAliasesId(Arrays.asList("Alias1"), noTypeInstance);
        assertTrue(result3.isEmpty());
    }

    @Test
    void testGenerateAliasesIdsForInstance() {
        Map<String, Object> instance = new HashMap<>();
        instance.put("entity_type", "Malware");
        instance.put("aliases", Arrays.asList("Alias1", "Alias2"));
        instance.put("x_opencti_aliases", Arrays.asList("Alias3", "Alias4"));

        List<String> aliasIds = SchemaIdentifier.generateAliasesIdsForInstance(instance);

        assertNotNull(aliasIds);
        assertEquals(4, aliasIds.size());
    }

    // ==================== 实例ID获取测试 ====================
    @Test
    void testGetInstanceIds() {
        Map<String, Object> instance = new HashMap<>();
        instance.put("internal_id", "internal-123");
        instance.put("standard_id", "standard-456");
        instance.put("x_opencti_stix_ids", Arrays.asList("stix-1", "stix-2"));

        List<String> ids = SchemaIdentifier.getInstanceIds(instance);

        assertNotNull(ids);
        assertTrue(ids.contains("internal-123"));
        assertTrue(ids.contains("standard-456"));
        assertTrue(ids.contains("stix-1"));
        assertTrue(ids.contains("stix-2"));
    }

    @Test
    void testGetInstanceIdsWithoutInternal() {
        Map<String, Object> instance = new HashMap<>();
        instance.put("internal_id", "internal-123");
        instance.put("standard_id", "standard-456");

        List<String> ids = SchemaIdentifier.getInstanceIds(instance, true);

        assertNotNull(ids);
        assertFalse(ids.contains("internal-123"));
        assertTrue(ids.contains("standard-456"));
    }

    // ==================== 输入ID获取测试 ====================
    @Test
    void testGetInputIds() {
        Map<String, Object> input = new HashMap<>();
        input.put("internal_id", "internal-123");
        input.put("standard_id", "standard-456");
        input.put("stix_id", "stix-789");
        input.put("x_opencti_stix_ids", Arrays.asList("stix-1", "stix-2"));

        List<String> ids = SchemaIdentifier.getInputIds("Malware", input);

        assertNotNull(ids);
        assertTrue(ids.contains("internal-123"));
        assertTrue(ids.contains("standard-456"));
        assertTrue(ids.contains("stix-789"));
        assertTrue(ids.contains("stix-1"));
        assertTrue(ids.contains("stix-2"));
    }

    @Test
    void testGetInputIdsWithFromRule() {
        Map<String, Object> from = new HashMap<>();
        from.put("internal_id", "from-id");

        Map<String, Object> to = new HashMap<>();
        to.put("internal_id", "to-id");

        Map<String, Object> input = new HashMap<>();
        input.put("from", from);
        input.put("to", to);

        List<String> ids = SchemaIdentifier.getInputIds("relationship", input, true);

        assertNotNull(ids);
        assertTrue(ids.contains("from-id-relationship-to-id"));
        assertTrue(ids.contains("to-id-relationship-from-id"));
    }

    // ==================== 占位符方法测试 ====================
    @Test
    void testIsSupportedStixType() {
        assertTrue(SchemaIdentifier.isSupportedStixType("malware"));
        assertTrue(SchemaIdentifier.isSupportedStixType("attack-pattern"));
        assertFalse(SchemaIdentifier.isSupportedStixType(null));
        assertFalse(SchemaIdentifier.isSupportedStixType(""));
    }

    @Test
    void testIsFieldContributingToStandardId() {
        Map<String, Object> instance = new HashMap<>();
        assertTrue(SchemaIdentifier.isFieldContributingToStandardId(instance, Arrays.asList("name")));
        assertFalse(SchemaIdentifier.isFieldContributingToStandardId(instance, Collections.emptyList()));
        assertFalse(SchemaIdentifier.isFieldContributingToStandardId(instance, null));
    }
}
