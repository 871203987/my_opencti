package io.opencti.database.stix.converter;

import io.opencti.common.types.store.StoreEntity;
import io.opencti.common.types.store.StoreObject;
import io.opencti.common.types.stix.StixDate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Stix21Converter单元测试
 */
class Stix21ConverterTest {

    @Test
    void testIsTrustedStixId() {
        assertTrue(Stix21Converter.isTrustedStixId("malware--12345678-1234-1234-1234-123456789abc"));
        assertFalse(Stix21Converter.isTrustedStixId(null));
        assertFalse(Stix21Converter.isTrustedStixId(""));
    }

    @Test
    void testConvertTypeToStixType() {
        assertEquals("malware", Stix21Converter.convertTypeToStixType("Malware"));
        assertEquals("attack-pattern", Stix21Converter.convertTypeToStixType("AttackPattern"));
        assertEquals("x-opencti-task", Stix21Converter.convertTypeToStixType("Task"));
        assertEquals("x-opencti-incident", Stix21Converter.convertTypeToStixType("Case-Incident"));
    }

    @Test
    void testBuildStixObject() {
        StoreObject obj = new StoreObject();
        obj.setStandardId("malware--12345678-1234-1234-1234-123456789abc");
        obj.setEntityType("Malware");
        obj.setCreatedAt(new StixDate("2024-01-15T10:30:00.000Z"));
        obj.setUpdatedAt(new StixDate("2024-01-15T10:30:00.000Z"));

        Map<String, Object> result = Stix21Converter.buildStixObject(obj);

        assertNotNull(result);
        assertEquals("malware--12345678-1234-1234-1234-123456789abc", result.get("id"));
        assertEquals("malware", result.get("type"));
        assertEquals("2.1", result.get("spec_version"));
    }

    @Test
    void testBuildStixDomain() {
        StoreEntity entity = new StoreEntity();
        entity.setStandardId("malware--12345678-1234-1234-1234-123456789abc");
        entity.setEntityType("Malware");
        entity.setName("Test Malware");
        entity.setDescription("Test Description");
        entity.setConfidence(85);

        Map<String, Object> result = Stix21Converter.buildStixDomain(entity);

        assertNotNull(result);
        assertEquals("Test Malware", result.get("name"));
        assertEquals("Test Description", result.get("description"));
        assertEquals(85, result.get("confidence"));
    }

    @Test
    void testConvertMalwareToStix() {
        StoreEntity entity = new StoreEntity();
        entity.setStandardId("malware--12345678-1234-1234-1234-123456789abc");
        entity.setEntityType("Malware");
        entity.setName("Test Malware");
        entity.setIsFamily(false);
        entity.setMalwareTypes(Arrays.asList("trojan", "backdoor"));

        Map<String, Object> result = Stix21Converter.convertMalwareToStix(entity);

        assertNotNull(result);
        assertEquals("Test Malware", result.get("name"));
        assertEquals(false, result.get("is_family"));
        @SuppressWarnings("unchecked")
        List<String> types = (List<String>) result.get("malware_types");
        assertNotNull(types);
        assertEquals(2, types.size());
    }

    @Test
    void testConvertReportToStix() {
        StoreEntity entity = new StoreEntity();
        entity.setStandardId("report--12345678-1234-1234-1234-123456789abc");
        entity.setEntityType("Report");
        entity.setName("Test Report");
        entity.setReportTypes(Arrays.asList("threat-report"));

        Map<String, Object> result = Stix21Converter.convertReportToStix(entity);

        assertNotNull(result);
        assertEquals("Test Report", result.get("name"));
        @SuppressWarnings("unchecked")
        List<String> types = (List<String>) result.get("report_types");
        assertNotNull(types);
        assertEquals(1, types.size());
    }

    @Test
    void testConvertNoteToStix() {
        StoreEntity entity = new StoreEntity();
        entity.setStandardId("note--12345678-1234-1234-1234-123456789abc");
        entity.setEntityType("Note");
        entity.setName("Test Note");
        entity.setContent("This is a test note content");
        entity.setAuthors(Arrays.asList("Author1", "Author2"));

        Map<String, Object> result = Stix21Converter.convertNoteToStix(entity);

        assertNotNull(result);
        assertEquals("Test Note", result.get("name"));
        assertEquals("This is a test note content", result.get("content"));
        @SuppressWarnings("unchecked")
        List<String> authors = (List<String>) result.get("authors");
        assertNotNull(authors);
        assertEquals(2, authors.size());
    }

    @Test
    void testConvertObservedDataToStix() {
        StoreEntity entity = new StoreEntity();
        entity.setStandardId("observed-data--12345678-1234-1234-1234-123456789abc");
        entity.setEntityType("Observed-Data");
        entity.setName("Test Observed Data");
        entity.setNumberObserved(100);

        Map<String, Object> result = Stix21Converter.convertObservedDataToStix(entity);

        assertNotNull(result);
        assertEquals("Test Observed Data", result.get("name"));
        assertEquals(100, result.get("number_observed"));
    }

    @Test
    void testConvertOpinionToStix() {
        StoreEntity entity = new StoreEntity();
        entity.setStandardId("opinion--12345678-1234-1234-1234-123456789abc");
        entity.setEntityType("Opinion");
        entity.setName("Test Opinion");
        entity.setOpinion("strongly-disagree");

        Map<String, Object> result = Stix21Converter.convertOpinionToStix(entity);

        assertNotNull(result);
        assertEquals("Test Opinion", result.get("name"));
        assertEquals("strongly-disagree", result.get("opinion"));
    }

    @Test
    void testConvertStoreToStix_2_1_Null() {
        assertNull(Stix21Converter.convertStoreToStix_2_1(null));
    }

    @Test
    void testConvertStoreToStix_2_1_StoreObject() {
        StoreObject obj = new StoreObject();
        obj.setStandardId("malware--12345678-1234-1234-1234-123456789abc");
        obj.setEntityType("Malware");

        Map<String, Object> result = Stix21Converter.convertStoreToStix_2_1(obj);

        assertNotNull(result);
        assertEquals("malware--12345678-1234-1234-1234-123456789abc", result.get("id"));
    }

    @Test
    void testBuildOCTIExtensions() {
        StoreObject obj = new StoreObject();
        obj.setInternalId("internal-123");
        obj.setEntityType("Malware");
        obj.setStandardId("malware--12345678-1234-1234-1234-123456789abc");

        Map<String, Object> result = Stix21Converter.buildOCTIExtensions(obj);

        assertNotNull(result);
        assertTrue(result.containsKey("extension-definition--ea279b3d-ef0f-5a38-9d0d-6b11fe7f8f4f"));
    }
}
