package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StixDomainObjectSchema 单元测试
 */
class StixDomainObjectSchemaTest {

    // ==================== SDO属性常量测试 ====================

    @Test
    void testAttributeConstants() {
        assertEquals("name", StixDomainObjectSchema.ATTRIBUTE_NAME);
        assertEquals("attribute_abstract", StixDomainObjectSchema.ATTRIBUTE_ABSTRACT);
        assertEquals("explanation", StixDomainObjectSchema.ATTRIBUTE_EXPLANATION);
        assertEquals("description", StixDomainObjectSchema.ATTRIBUTE_DESCRIPTION);
        assertEquals("x_opencti_description", StixDomainObjectSchema.ATTRIBUTE_DESCRIPTION_OPENCTI);
        assertEquals("aliases", StixDomainObjectSchema.ATTRIBUTE_ALIASES);
        assertEquals("x_opencti_aliases", StixDomainObjectSchema.ATTRIBUTE_ALIASES_OPENCTI);
        assertEquals("x_opencti_additional_names", StixDomainObjectSchema.ATTRIBUTE_ADDITIONAL_NAMES);
    }

    // ==================== SDO实体类型常量测试 ====================

    @Test
    void testEntityTypeConstants() {
        // 攻击相关
        assertEquals("Attack-Pattern", StixDomainObjectSchema.ENTITY_TYPE_ATTACK_PATTERN);
        assertEquals("Campaign", StixDomainObjectSchema.ENTITY_TYPE_CAMPAIGN);
        assertEquals("Course-Of-Action", StixDomainObjectSchema.ENTITY_TYPE_COURSE_OF_ACTION);
        assertEquals("Infrastructure", StixDomainObjectSchema.ENTITY_TYPE_INFRASTRUCTURE);
        assertEquals("Intrusion-Set", StixDomainObjectSchema.ENTITY_TYPE_INTRUSION_SET);
        assertEquals("Malware", StixDomainObjectSchema.ENTITY_TYPE_MALWARE);
        assertEquals("Threat-Actor-Group", StixDomainObjectSchema.ENTITY_TYPE_THREAT_ACTOR_GROUP);
        assertEquals("Tool", StixDomainObjectSchema.ENTITY_TYPE_TOOL);
        assertEquals("Vulnerability", StixDomainObjectSchema.ENTITY_TYPE_VULNERABILITY);
        assertEquals("Incident", StixDomainObjectSchema.ENTITY_TYPE_INCIDENT);
        assertEquals("Data-Component", StixDomainObjectSchema.ENTITY_TYPE_DATA_COMPONENT);
        assertEquals("Data-Source", StixDomainObjectSchema.ENTITY_TYPE_DATA_SOURCE);

        // 容器相关
        assertEquals("Note", StixDomainObjectSchema.ENTITY_TYPE_CONTAINER_NOTE);
        assertEquals("Observed-Data", StixDomainObjectSchema.ENTITY_TYPE_CONTAINER_OBSERVED_DATA);
        assertEquals("Opinion", StixDomainObjectSchema.ENTITY_TYPE_CONTAINER_OPINION);
        assertEquals("Report", StixDomainObjectSchema.ENTITY_TYPE_CONTAINER_REPORT);

        // 身份相关
        assertEquals("Individual", StixDomainObjectSchema.ENTITY_TYPE_IDENTITY_INDIVIDUAL);
        assertEquals("Sector", StixDomainObjectSchema.ENTITY_TYPE_IDENTITY_SECTOR);
        assertEquals("System", StixDomainObjectSchema.ENTITY_TYPE_IDENTITY_SYSTEM);

        // 位置相关
        assertEquals("City", StixDomainObjectSchema.ENTITY_TYPE_LOCATION_CITY);
        assertEquals("Country", StixDomainObjectSchema.ENTITY_TYPE_LOCATION_COUNTRY);
        assertEquals("Region", StixDomainObjectSchema.ENTITY_TYPE_LOCATION_REGION);
        assertEquals("Position", StixDomainObjectSchema.ENTITY_TYPE_LOCATION_POSITION);

        // 其他
        assertEquals("Resolved-Filters", StixDomainObjectSchema.ENTITY_TYPE_RESOLVED_FILTERS);
    }

    // ==================== SDO分类列表测试 ====================

    @Test
    void testStixDomainObjectContainers() {
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_CONTAINERS.contains("Note"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_CONTAINERS.contains("Report"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_CONTAINERS.contains("Case-Incident"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_CONTAINERS.contains("Grouping"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_CONTAINERS.contains("Task"));
        assertFalse(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_CONTAINERS.contains("Malware"));
    }

    @Test
    void testStixDomainObjectShareableContainers() {
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS.contains("Observed-Data"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS.contains("Grouping"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS.contains("Report"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS.contains("Case-Incident"));
        assertFalse(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS.contains("Note"));
    }

    @Test
    void testStixDomainObjectIdentities() {
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_IDENTITIES.contains("Individual"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_IDENTITIES.contains("Sector"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_IDENTITIES.contains("System"));
        assertFalse(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_IDENTITIES.contains("Organization"));
    }

    @Test
    void testStixDomainObjectLocations() {
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_LOCATIONS.contains("City"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_LOCATIONS.contains("Country"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_LOCATIONS.contains("Region"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_LOCATIONS.contains("Position"));
    }

    @Test
    void testStixDomainObjectThreatActors() {
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_THREAT_ACTORS.contains("Threat-Actor-Group"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_THREAT_ACTORS.contains("Threat-Actor-Individual"));
    }

    @Test
    void testStixDomainObjects() {
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECTS.contains("Attack-Pattern"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECTS.contains("Malware"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECTS.contains("Campaign"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECTS.contains("Report"));
        assertFalse(StixDomainObjectSchema.STIX_DOMAIN_OBJECTS.contains("Note")); // Note是容器，不在基础SDO列表中
    }

    @Test
    void testStixDomainObjectAliased() {
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_ALIASED.contains("Attack-Pattern"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_ALIASED.contains("Malware"));
        assertTrue(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_ALIASED.contains("Campaign"));
        assertFalse(StixDomainObjectSchema.STIX_DOMAIN_OBJECT_ALIASED.contains("Data-Component"));
    }

    // ==================== SDO类型判断方法测试 ====================

    @Test
    void testIsStixDomainObject() {
        // SDO类型应该返回true
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Attack-Pattern"));
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Malware"));
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Campaign"));

        // 身份类型应该返回true
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Individual"));
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Sector"));

        // 位置类型应该返回true
        assertTrue(StixDomainObjectSchema.isStixDomainObject("City"));
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Country"));

        // 容器类型应该返回true
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Report"));
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Note"));

        // 威胁行为者类型应该返回true
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Threat-Actor-Group"));

        // 抽象类型应该返回true
        assertTrue(StixDomainObjectSchema.isStixDomainObject(SchemaGeneral.ABSTRACT_STIX_DOMAIN_OBJECT));

        // 非SDO类型应该返回false
        assertFalse(StixDomainObjectSchema.isStixDomainObject("Settings"));
        assertFalse(StixDomainObjectSchema.isStixDomainObject("User"));

        // null应该返回false
        assertFalse(StixDomainObjectSchema.isStixDomainObject(null));
    }

    @Test
    void testIsStixDomainObjectContainer() {
        assertTrue(StixDomainObjectSchema.isStixDomainObjectContainer("Report"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectContainer("Note"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectContainer("Case-Incident"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectContainer("Grouping"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectContainer(SchemaGeneral.ENTITY_TYPE_CONTAINER));

        assertFalse(StixDomainObjectSchema.isStixDomainObjectContainer("Malware"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectContainer("Attack-Pattern"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectContainer(null));
    }

    @Test
    void testIsStixDomainObjectShareableContainer() {
        assertTrue(StixDomainObjectSchema.isStixDomainObjectShareableContainer("Observed-Data"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectShareableContainer("Grouping"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectShareableContainer("Report"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectShareableContainer("Case-Incident"));

        assertFalse(StixDomainObjectSchema.isStixDomainObjectShareableContainer("Note"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectShareableContainer("Opinion"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectShareableContainer(null));
    }

    @Test
    void testIsStixDomainObjectIdentity() {
        assertTrue(StixDomainObjectSchema.isStixDomainObjectIdentity("Individual"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectIdentity("Sector"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectIdentity("System"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectIdentity(SchemaGeneral.ENTITY_TYPE_IDENTITY));

        assertFalse(StixDomainObjectSchema.isStixDomainObjectIdentity("Organization"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectIdentity("Malware"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectIdentity(null));
    }

    @Test
    void testIsStixDomainObjectLocation() {
        assertTrue(StixDomainObjectSchema.isStixDomainObjectLocation("City"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectLocation("Country"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectLocation("Region"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectLocation("Position"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectLocation(SchemaGeneral.ENTITY_TYPE_LOCATION));

        assertFalse(StixDomainObjectSchema.isStixDomainObjectLocation("Malware"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectLocation("Attack-Pattern"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectLocation(null));
    }

    @Test
    void testIsStixDomainObjectThreatActor() {
        assertTrue(StixDomainObjectSchema.isStixDomainObjectThreatActor("Threat-Actor-Group"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectThreatActor("Threat-Actor-Individual"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectThreatActor(SchemaGeneral.ENTITY_TYPE_THREAT_ACTOR));

        assertFalse(StixDomainObjectSchema.isStixDomainObjectThreatActor("Malware"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectThreatActor("Campaign"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectThreatActor(null));
    }

    @Test
    void testIsStixDomainObjectCase() {
        assertTrue(StixDomainObjectSchema.isStixDomainObjectCase("Case-Incident"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectCase("Case-Rfi"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectCase("Case-Rft"));
        assertTrue(StixDomainObjectSchema.isStixDomainObjectCase(StixDomainObjectSchema.ENTITY_TYPE_CONTAINER_CASE));

        assertFalse(StixDomainObjectSchema.isStixDomainObjectCase("Report"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectCase("Note"));
        assertFalse(StixDomainObjectSchema.isStixDomainObjectCase(null));
    }

    // ==================== 别名相关方法测试 ====================

    @Test
    void testIsStixObjectAliased() {
        // 在STIX_DOMAIN_OBJECT_ALIASED列表中的类型
        assertTrue(StixDomainObjectSchema.isStixObjectAliased("Attack-Pattern"));
        assertTrue(StixDomainObjectSchema.isStixObjectAliased("Malware"));
        assertTrue(StixDomainObjectSchema.isStixObjectAliased("Campaign"));

        // 身份类型（除安全平台外）
        assertTrue(StixDomainObjectSchema.isStixObjectAliased("Individual"));
        assertTrue(StixDomainObjectSchema.isStixObjectAliased("Sector"));

        // 位置类型
        assertTrue(StixDomainObjectSchema.isStixObjectAliased("City"));
        assertTrue(StixDomainObjectSchema.isStixObjectAliased("Country"));

        // 安全平台不应该支持别名
        assertFalse(StixDomainObjectSchema.isStixObjectAliased("Security-Platform"));

        // 其他类型
        assertFalse(StixDomainObjectSchema.isStixObjectAliased("Data-Component"));
        assertFalse(StixDomainObjectSchema.isStixObjectAliased(null));
    }

    @Test
    void testRegisterStixDomainAliased() {
        // 注册新类型
        StixDomainObjectSchema.registerStixDomainAliased("Custom-Type");

        // 验证已注册
        assertTrue(StixDomainObjectSchema.isDynamicallyAliased("Custom-Type"));

        // 验证未注册的类型返回false
        assertFalse(StixDomainObjectSchema.isDynamicallyAliased("Unregistered-Type"));

        // null不应该导致异常
        StixDomainObjectSchema.registerStixDomainAliased(null);
        assertFalse(StixDomainObjectSchema.isDynamicallyAliased(null));
    }

    @Test
    void testResolveAliasesField() {
        // Course-Of-Action, Vulnerability, Grouping应该返回x_opencti_aliases
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES_OPENCTI,
                StixDomainObjectSchema.resolveAliasesField("Course-Of-Action"));
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES_OPENCTI,
                StixDomainObjectSchema.resolveAliasesField("Vulnerability"));
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES_OPENCTI,
                StixDomainObjectSchema.resolveAliasesField("Grouping"));

        // 身份类型应该返回x_opencti_aliases
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES_OPENCTI,
                StixDomainObjectSchema.resolveAliasesField("Individual"));
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES_OPENCTI,
                StixDomainObjectSchema.resolveAliasesField("Sector"));

        // 位置类型应该返回x_opencti_aliases
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES_OPENCTI,
                StixDomainObjectSchema.resolveAliasesField("City"));
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES_OPENCTI,
                StixDomainObjectSchema.resolveAliasesField("Country"));

        // 其他类型应该返回aliases
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES,
                StixDomainObjectSchema.resolveAliasesField("Attack-Pattern"));
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES,
                StixDomainObjectSchema.resolveAliasesField("Malware"));

        // null应该返回默认aliases
        assertEquals(StixDomainObjectSchema.ATTRIBUTE_ALIASES,
                StixDomainObjectSchema.resolveAliasesField(null));
    }

    // ==================== 组织限制列表测试 ====================

    @Test
    void testStixOrganizationsUnrestricted() {
        assertTrue(StixDomainObjectSchema.STIX_ORGANIZATIONS_UNRESTRICTED.contains(SchemaGeneral.ABSTRACT_INTERNAL_OBJECT));
        assertTrue(StixDomainObjectSchema.STIX_ORGANIZATIONS_UNRESTRICTED.contains(SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP));
        assertTrue(StixDomainObjectSchema.STIX_ORGANIZATIONS_UNRESTRICTED.contains("Organization"));
        assertTrue(StixDomainObjectSchema.STIX_ORGANIZATIONS_UNRESTRICTED.contains("Sector"));
        assertTrue(StixDomainObjectSchema.STIX_ORGANIZATIONS_UNRESTRICTED.contains(SchemaGeneral.ENTITY_TYPE_LOCATION));
    }

    @Test
    void testStixOrganizationsRestricted() {
        assertTrue(StixDomainObjectSchema.STIX_ORGANIZATIONS_RESTRICTED.contains("DeleteOperation"));
        assertEquals(1, StixDomainObjectSchema.STIX_ORGANIZATIONS_RESTRICTED.size());
    }

    // ==================== 集成测试 ====================

    @Test
    void testTypeHierarchy() {
        // Malware是SDO
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Malware"));
        // 但不是容器
        assertFalse(StixDomainObjectSchema.isStixDomainObjectContainer("Malware"));
        // 也不是身份
        assertFalse(StixDomainObjectSchema.isStixDomainObjectIdentity("Malware"));

        // Report是SDO
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Report"));
        // 也是容器
        assertTrue(StixDomainObjectSchema.isStixDomainObjectContainer("Report"));
        // 也是可共享容器
        assertTrue(StixDomainObjectSchema.isStixDomainObjectShareableContainer("Report"));

        // Individual是SDO
        assertTrue(StixDomainObjectSchema.isStixDomainObject("Individual"));
        // 也是身份
        assertTrue(StixDomainObjectSchema.isStixDomainObjectIdentity("Individual"));
        // 支持别名
        assertTrue(StixDomainObjectSchema.isStixObjectAliased("Individual"));

        // City是SDO
        assertTrue(StixDomainObjectSchema.isStixDomainObject("City"));
        // 也是位置
        assertTrue(StixDomainObjectSchema.isStixDomainObjectLocation("City"));
        // 支持别名
        assertTrue(StixDomainObjectSchema.isStixObjectAliased("City"));
    }
}
