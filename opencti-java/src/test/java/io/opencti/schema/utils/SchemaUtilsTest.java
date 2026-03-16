package io.opencti.schema.utils;

import io.opencti.schema.general.SchemaGeneral;
import io.opencti.schema.stix.StixCyberObservableSchema;
import io.opencti.schema.stix.StixDomainObjectSchema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SchemaUtils 单元测试
 * 测试Schema工具类的正确性
 */
@DisplayName("SchemaUtils 测试")
class SchemaUtilsTest {

    // ==================== ID验证方法测试 ====================

    @Test
    @DisplayName("isStixId对有效的STIX ID应返回true")
    void testIsStixIdValid() {
        assertTrue(SchemaUtils.isStixId("attack-pattern--12345678-1234-1234-1234-123456789abc"));
        assertTrue(SchemaUtils.isStixId("malware--abcdef12-3456-7890-abcd-ef1234567890"));
        assertTrue(SchemaUtils.isStixId("threat-actor--00000000-0000-0000-0000-000000000000"));
    }

    @Test
    @DisplayName("isStixId对无效的STIX ID应返回false")
    void testIsStixIdInvalid() {
        assertFalse(SchemaUtils.isStixId("invalid"));
        assertFalse(SchemaUtils.isStixId("attack-pattern--123")); // 太短
        assertFalse(SchemaUtils.isStixId("12345678-1234-1234-1234-123456789abc")); // 缺少类型前缀
        assertFalse(SchemaUtils.isStixId(""));
        assertFalse(SchemaUtils.isStixId(null));
    }

    @Test
    @DisplayName("isInternalId对有效的UUID应返回true")
    void testIsInternalIdValid() {
        assertTrue(SchemaUtils.isInternalId("12345678-1234-1234-1234-123456789abc"));
        assertTrue(SchemaUtils.isInternalId("abcdef12-3456-7890-abcd-ef1234567890"));
        assertTrue(SchemaUtils.isInternalId("00000000-0000-0000-0000-000000000000"));
    }

    @Test
    @DisplayName("isInternalId对无效的UUID应返回false")
    void testIsInternalIdInvalid() {
        assertFalse(SchemaUtils.isInternalId("invalid"));
        assertFalse(SchemaUtils.isInternalId("123")); // 太短
        assertFalse(SchemaUtils.isInternalId("attack-pattern--12345678-1234-1234-1234-123456789abc")); // STIX ID
        assertFalse(SchemaUtils.isInternalId(""));
        assertFalse(SchemaUtils.isInternalId(null));
    }

    @Test
    @DisplayName("isAnId对有效的ID应返回true")
    void testIsAnIdValid() {
        // STIX ID
        assertTrue(SchemaUtils.isAnId("attack-pattern--12345678-1234-1234-1234-123456789abc"));
        // UUID
        assertTrue(SchemaUtils.isAnId("12345678-1234-1234-1234-123456789abc"));
    }

    @Test
    @DisplayName("isAnId对无效的ID应返回false")
    void testIsAnIdInvalid() {
        assertFalse(SchemaUtils.isAnId("invalid"));
        assertFalse(SchemaUtils.isAnId(""));
        assertFalse(SchemaUtils.isAnId(null));
    }

    // ==================== 哈希方法测试 ====================

    @Test
    @DisplayName("shortHash应返回8位十六进制字符串")
    void testShortHash() {
        String hash1 = SchemaUtils.shortHash("test");
        assertEquals(8, hash1.length());
        assertTrue(hash1.matches("^[0-9a-f]{8}$"));

        String hash2 = SchemaUtils.shortHash("test");
        assertEquals(hash1, hash2); // 相同输入应产生相同输出

        String hash3 = SchemaUtils.shortHash("different");
        assertEquals(8, hash3.length());
        assertNotEquals(hash1, hash3); // 不同输入应产生不同输出
    }

    @Test
    @DisplayName("shortHash对null应返回有效哈希")
    void testShortHashNull() {
        String hash = SchemaUtils.shortHash(null);
        assertEquals(8, hash.length());
        assertTrue(hash.matches("^[0-9a-f]{8}$"));
    }

    // ==================== 日期验证方法测试 ====================

    @Test
    @DisplayName("isValidDate对有效的ISO日期应返回true")
    void testIsValidDateValid() {
        assertTrue(SchemaUtils.isValidDate("2023-01-01T00:00:00Z"));
        assertTrue(SchemaUtils.isValidDate("2023-12-31T23:59:59Z"));
        assertTrue(SchemaUtils.isValidDate("2023-06-15T12:30:45Z"));
    }

    @Test
    @DisplayName("isValidDate对无效的日期应返回false")
    void testIsValidDateInvalid() {
        assertFalse(SchemaUtils.isValidDate("invalid"));
        assertFalse(SchemaUtils.isValidDate("2023-01-01")); // 缺少时间部分
        assertFalse(SchemaUtils.isValidDate("2023-13-01T00:00:00Z")); // 无效月份
        assertFalse(SchemaUtils.isValidDate(""));
        assertFalse(SchemaUtils.isValidDate(null));
    }

    // ==================== 类型转换方法测试 ====================

    @Test
    @DisplayName("pascalize应正确转换字符串")
    void testPascalize() {
        assertEquals("Attack-Pattern", SchemaUtils.pascalize("attack-pattern"));
        assertEquals("Malware", SchemaUtils.pascalize("malware"));
        assertEquals("Threat-Actor", SchemaUtils.pascalize("threat-actor"));
        assertEquals("Ipv4-Addr", SchemaUtils.pascalize("ipv4-addr"));
        assertEquals("", SchemaUtils.pascalize(""));
        assertNull(SchemaUtils.pascalize(null));
    }

    @Test
    @DisplayName("generateInternalType应正确转换STIX实体")
    void testGenerateInternalType() {
        // Sighting关系
        Map<String, Object> sightingEntity = new HashMap<>();
        sightingEntity.put("type", "sighting");
        assertEquals("stix-sighting-relationship", SchemaUtils.generateInternalType(sightingEntity));

        // Identity
        Map<String, Object> identityEntity = new HashMap<>();
        identityEntity.put("type", "identity");
        identityEntity.put("identity_class", "individual");
        assertEquals("Individual", SchemaUtils.generateInternalType(identityEntity));

        // Identity with class
        Map<String, Object> sectorEntity = new HashMap<>();
        sectorEntity.put("type", "identity");
        sectorEntity.put("identity_class", "class");
        assertEquals("Sector", SchemaUtils.generateInternalType(sectorEntity));

        // Location
        Map<String, Object> locationEntity = new HashMap<>();
        locationEntity.put("type", "location");
        locationEntity.put("x_opencti_location_type", "Country");
        assertEquals("Country", SchemaUtils.generateInternalType(locationEntity));

        // IPv4
        Map<String, Object> ipv4Entity = new HashMap<>();
        ipv4Entity.put("type", "ipv4-addr");
        assertEquals(StixCyberObservableSchema.ENTITY_IPV4_ADDR, SchemaUtils.generateInternalType(ipv4Entity));

        // File
        Map<String, Object> fileEntity = new HashMap<>();
        fileEntity.put("type", "file");
        assertEquals(StixCyberObservableSchema.ENTITY_HASHED_OBSERVABLE_STIX_FILE, SchemaUtils.generateInternalType(fileEntity));

        // Default
        Map<String, Object> defaultEntity = new HashMap<>();
        defaultEntity.put("type", "custom-type");
        assertEquals("Custom-Type", SchemaUtils.generateInternalType(defaultEntity));
    }

    @Test
    @DisplayName("generateInternalType对空实体应返回null")
    void testGenerateInternalTypeNull() {
        assertNull(SchemaUtils.generateInternalType(null));
        assertNull(SchemaUtils.generateInternalType(new HashMap<>()));
    }

    @Test
    @DisplayName("convertStixToInternalTypes应返回类型列表")
    void testConvertStixToInternalTypes() {
        // Sighting
        List<String> sightingTypes = SchemaUtils.convertStixToInternalTypes("sighting");
        assertTrue(sightingTypes.contains("stix-sighting-relationship"));

        // Default
        List<String> defaultTypes = SchemaUtils.convertStixToInternalTypes("custom-type");
        assertTrue(defaultTypes.contains("Custom-Type"));
    }

    // ==================== 父类型获取方法测试 ====================

    @Test
    @DisplayName("getParentTypes应返回正确的父类型列表")
    void testGetParentTypes() {
        // 使用Basic Object类型测试
        List<String> basicObjectParents = SchemaUtils.getParentTypes(SchemaGeneral.ABSTRACT_BASIC_OBJECT);
        assertTrue(basicObjectParents.contains(SchemaGeneral.ABSTRACT_BASIC_OBJECT));
    }

    @Test
    @DisplayName("getParentTypes对不支持的类型应抛出异常")
    void testGetParentTypesUnsupported() {
        assertThrows(IllegalArgumentException.class, () -> SchemaUtils.getParentTypes("unsupported-type"));
    }

    @Test
    @DisplayName("keepMostRestrictiveTypes应过滤冗余父类型")
    void testKeepMostRestrictiveTypes() {
        // 示例: [Basic-Object, Stix-Relationship] 应该返回原列表（没有父子关系）
        List<String> types = Arrays.asList(
                SchemaGeneral.ABSTRACT_BASIC_OBJECT,
                SchemaGeneral.ABSTRACT_STIX_RELATIONSHIP
        );

        List<String> result = SchemaUtils.keepMostRestrictiveTypes(types);

        // 两个类型都应该保留（没有父子关系）
        assertTrue(result.contains(SchemaGeneral.ABSTRACT_BASIC_OBJECT));
        assertTrue(result.contains(SchemaGeneral.ABSTRACT_STIX_RELATIONSHIP));
    }

    @Test
    @DisplayName("keepMostRestrictiveTypes对空列表应返回空列表")
    void testKeepMostRestrictiveTypesEmpty() {
        assertTrue(SchemaUtils.keepMostRestrictiveTypes(null).isEmpty());
        assertTrue(SchemaUtils.keepMostRestrictiveTypes(Collections.emptyList()).isEmpty());
    }

    @Test
    @DisplayName("keepMostRestrictiveTypes对单元素列表应返回原列表")
    void testKeepMostRestrictiveTypesSingle() {
        List<String> types = Collections.singletonList(StixDomainObjectSchema.ENTITY_TYPE_ATTACK_PATTERN);
        List<String> result = SchemaUtils.keepMostRestrictiveTypes(types);
        assertEquals(1, result.size());
        assertEquals(StixDomainObjectSchema.ENTITY_TYPE_ATTACK_PATTERN, result.get(0));
    }
}
