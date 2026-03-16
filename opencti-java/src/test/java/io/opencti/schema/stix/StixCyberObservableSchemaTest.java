package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StixCyberObservableSchema 单元测试
 * 测试STIX网络可观测对象Schema定义的正确性
 */
@DisplayName("StixCyberObservableSchema 测试")
class StixCyberObservableSchemaTest {

    // ==================== 类型常量测试 ====================

    @Test
    @DisplayName("ENTITY_AUTONOMOUS_SYSTEM 常量值应为 'Autonomous-System'")
    void testEntityAutonomousSystem() {
        assertEquals("Autonomous-System", StixCyberObservableSchema.ENTITY_AUTONOMOUS_SYSTEM);
    }

    @Test
    @DisplayName("ENTITY_DOMAIN_NAME 常量值应为 'Domain-Name'")
    void testEntityDomainName() {
        assertEquals("Domain-Name", StixCyberObservableSchema.ENTITY_DOMAIN_NAME);
    }

    @Test
    @DisplayName("ENTITY_IPV4_ADDR 常量值应为 'IPv4-Addr'")
    void testEntityIpv4Addr() {
        assertEquals("IPv4-Addr", StixCyberObservableSchema.ENTITY_IPV4_ADDR);
    }

    @Test
    @DisplayName("ENTITY_HASHED_OBSERVABLE_ARTIFACT 常量值应为 'Artifact'")
    void testEntityArtifact() {
        assertEquals("Artifact", StixCyberObservableSchema.ENTITY_HASHED_OBSERVABLE_ARTIFACT);
    }

    @Test
    @DisplayName("ENTITY_HASHED_OBSERVABLE_STIX_FILE 常量值应为 'StixFile'")
    void testEntityStixFile() {
        assertEquals("StixFile", StixCyberObservableSchema.ENTITY_HASHED_OBSERVABLE_STIX_FILE);
    }

    @Test
    @DisplayName("ENTITY_HOSTNAME 常量值应为 'Hostname'（OpenCTI自定义类型）")
    void testEntityHostname() {
        assertEquals("Hostname", StixCyberObservableSchema.ENTITY_HOSTNAME);
    }

    @Test
    @DisplayName("ENTITY_PERSONA 常量值应为 'Persona'（OpenCTI自定义类型）")
    void testEntityPersona() {
        assertEquals("Persona", StixCyberObservableSchema.ENTITY_PERSONA);
    }

    // ==================== 列表测试 ====================

    @Test
    @DisplayName("STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES 列表应包含3种类型")
    void testStixCyberObservablesHashedObservablesSize() {
        assertEquals(3, StixCyberObservableSchema.STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES.size());
    }

    @Test
    @DisplayName("STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES 列表应包含 Artifact")
    void testHashedObservablesContainsArtifact() {
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES.contains("Artifact"));
    }

    @Test
    @DisplayName("STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES 列表应包含 StixFile")
    void testHashedObservablesContainsStixFile() {
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES.contains("StixFile"));
    }

    @Test
    @DisplayName("STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES 列表应包含 X509-Certificate")
    void testHashedObservablesContainsX509Certificate() {
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES.contains("X509-Certificate"));
    }

    @Test
    @DisplayName("STIX_CYBER_OBSERVABLES 列表应包含33种类型")
    void testStixCyberObservablesSize() {
        assertEquals(33, StixCyberObservableSchema.STIX_CYBER_OBSERVABLES.size());
    }

    @Test
    @DisplayName("STIX_CYBER_OBSERVABLES 列表应包含所有哈希可观测对象")
    void testStixCyberObservablesContainsAllHashed() {
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES.containsAll(
                StixCyberObservableSchema.STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES));
    }

    @Test
    @DisplayName("STIX_CYBER_OBSERVABLES 列表应包含标准STIX类型")
    void testStixCyberObservablesContainsStandardTypes() {
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES.contains("Domain-Name"));
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES.contains("IPv4-Addr"));
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES.contains("Process"));
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES.contains("Url"));
    }

    @Test
    @DisplayName("STIX_CYBER_OBSERVABLES 列表应包含OpenCTI自定义类型")
    void testStixCyberObservablesContainsCustomTypes() {
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES.contains("Hostname"));
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES.contains("Persona"));
        assertTrue(StixCyberObservableSchema.STIX_CYBER_OBSERVABLES.contains("Cryptographic-Key"));
    }

    // ==================== 类型判断方法测试 - isStixCyberObservable ====================

    @Test
    @DisplayName("isStixCyberObservable 对 Domain-Name 应返回 true")
    void testIsStixCyberObservableForDomainName() {
        assertTrue(StixCyberObservableSchema.isStixCyberObservable("Domain-Name"));
    }

    @Test
    @DisplayName("isStixCyberObservable 对 IPv4-Addr 应返回 true")
    void testIsStixCyberObservableForIpv4Addr() {
        assertTrue(StixCyberObservableSchema.isStixCyberObservable("IPv4-Addr"));
    }

    @Test
    @DisplayName("isStixCyberObservable 对 Artifact 应返回 true")
    void testIsStixCyberObservableForArtifact() {
        assertTrue(StixCyberObservableSchema.isStixCyberObservable("Artifact"));
    }

    @Test
    @DisplayName("isStixCyberObservable 对 Hostname 应返回 true")
    void testIsStixCyberObservableForHostname() {
        assertTrue(StixCyberObservableSchema.isStixCyberObservable("Hostname"));
    }

    @Test
    @DisplayName("isStixCyberObservable 对抽象类型 Stix-Cyber-Observable 应返回 true")
    void testIsStixCyberObservableForAbstractType() {
        assertTrue(StixCyberObservableSchema.isStixCyberObservable(SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE));
    }

    @Test
    @DisplayName("isStixCyberObservable 对非可观测对象类型应返回 false")
    void testIsStixCyberObservableForNonObservable() {
        assertFalse(StixCyberObservableSchema.isStixCyberObservable("Attack-Pattern"));
        assertFalse(StixCyberObservableSchema.isStixCyberObservable("Malware"));
        assertFalse(StixCyberObservableSchema.isStixCyberObservable("Label"));
        assertFalse(StixCyberObservableSchema.isStixCyberObservable("Invalid-Type"));
    }

    @Test
    @DisplayName("isStixCyberObservable 对 null 应返回 false")
    void testIsStixCyberObservableForNull() {
        assertFalse(StixCyberObservableSchema.isStixCyberObservable(null));
    }

    @Test
    @DisplayName("isStixCyberObservable 对空字符串应返回 false")
    void testIsStixCyberObservableForEmptyString() {
        assertFalse(StixCyberObservableSchema.isStixCyberObservable(""));
    }

    // ==================== 类型判断方法测试 - isStixCyberObservableHashedObservable ====================

    @Test
    @DisplayName("isStixCyberObservableHashedObservable 对 Artifact 应返回 true")
    void testIsHashedObservableForArtifact() {
        assertTrue(StixCyberObservableSchema.isStixCyberObservableHashedObservable("Artifact"));
    }

    @Test
    @DisplayName("isStixCyberObservableHashedObservable 对 StixFile 应返回 true")
    void testIsHashedObservableForStixFile() {
        assertTrue(StixCyberObservableSchema.isStixCyberObservableHashedObservable("StixFile"));
    }

    @Test
    @DisplayName("isStixCyberObservableHashedObservable 对 X509-Certificate 应返回 true")
    void testIsHashedObservableForX509Certificate() {
        assertTrue(StixCyberObservableSchema.isStixCyberObservableHashedObservable("X509-Certificate"));
    }

    @Test
    @DisplayName("isStixCyberObservableHashedObservable 对抽象类型 Hashed-Observable 应返回 true")
    void testIsHashedObservableForAbstractType() {
        assertTrue(StixCyberObservableSchema.isStixCyberObservableHashedObservable(
                SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE));
    }

    @Test
    @DisplayName("isStixCyberObservableHashedObservable 对非哈希类型应返回 false")
    void testIsHashedObservableForNonHashed() {
        assertFalse(StixCyberObservableSchema.isStixCyberObservableHashedObservable("Domain-Name"));
        assertFalse(StixCyberObservableSchema.isStixCyberObservableHashedObservable("IPv4-Addr"));
        assertFalse(StixCyberObservableSchema.isStixCyberObservableHashedObservable("Process"));
        assertFalse(StixCyberObservableSchema.isStixCyberObservableHashedObservable("Hostname"));
    }

    @Test
    @DisplayName("isStixCyberObservableHashedObservable 对 null 应返回 false")
    void testIsHashedObservableForNull() {
        assertFalse(StixCyberObservableSchema.isStixCyberObservableHashedObservable(null));
    }
}
