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
 * Stix20Converter单元测试
 */
class Stix20ConverterTest {

    @Test
    void testBuildStixId() {
        String result = Stix20Converter.buildStixId("Malware", "malware--12345678-1234-1234-1234-123456789abc");
        assertEquals("malware--12345678-1234-1234-1234-123456789abc", result);
    }

    @Test
    void testBuildStixId_Null() {
        String result = Stix20Converter.buildStixId("Malware", null);
        assertNull(result);
    }

    @Test
    void testConvertTypeToStix2Type() {
        assertEquals("malware", Stix20Converter.convertTypeToStix2Type("Malware"));
        assertEquals("attack-pattern", Stix20Converter.convertTypeToStix2Type("AttackPattern"));
        assertEquals("x-opencti-task", Stix20Converter.convertTypeToStix2Type("Task"));
        assertEquals("x-opencti-feedback", Stix20Converter.convertTypeToStix2Type("Feedback"));
    }

    @Test
    void testBuildStixObject() {
        StoreObject obj = new StoreObject();
        obj.setStandardId("malware--12345678-1234-1234-1234-123456789abc");
        obj.setEntityType("Malware");
        obj.setCreatedAt(new StixDate("2024-01-15T10:30:00.000Z"));
        obj.setUpdatedAt(new StixDate("2024-01-15T10:30:00.000Z"));

        Map<String, Object> result = Stix20Converter.buildStixObject(obj);

        assertNotNull(result);
        assertEquals("malware--12345678-1234-1234-1234-123456789abc", result.get("id"));
        assertEquals("malware", result.get("type"));
        assertEquals("2.0", result.get("spec_version"));
    }

    @Test
    void testBuildStixDomain() {
        StoreEntity entity = new StoreEntity();
        entity.setStandardId("malware--12345678-1234-1234-1234-123456789abc");
        entity.setEntityType("Malware");
        entity.setName("Test Malware");
        entity.setDescription("Test Description");
        entity.setConfidence(85);

        Map<String, Object> result = Stix20Converter.buildStixDomain(entity);

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

        Map<String, Object> result = Stix20Converter.convertMalwareToStix(entity);

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

        Map<String, Object> result = Stix20Converter.convertReportToStix(entity);

        assertNotNull(result);
        assertEquals("Test Report", result.get("name"));
        @SuppressWarnings("unchecked")
        List<String> types = (List<String>) result.get("report_types");
        assertNotNull(types);
        assertEquals(1, types.size());
    }

    @Test
    void testConvertStoreToStix_2_0_Null() {
        assertNull(Stix20Converter.convertStoreToStix_2_0(null));
    }

    @Test
    void testConvertStoreToStix_2_0_StoreObject() {
        StoreObject obj = new StoreObject();
        obj.setStandardId("malware--12345678-1234-1234-1234-123456789abc");
        obj.setEntityType("Malware");

        Map<String, Object> result = Stix20Converter.convertStoreToStix_2_0(obj);

        assertNotNull(result);
        assertEquals("malware--12345678-1234-1234-1234-123456789abc", result.get("id"));
    }
}
