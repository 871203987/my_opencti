package io.opencti.schema.internal;

import io.opencti.schema.SchemaTypesDefinition;
import io.opencti.schema.general.SchemaGeneral;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * InternalObjectSchema 测试类
 * 测试所有常量和类型判断方法
 */
class InternalObjectSchemaTest {

    // ==================== 系统配置对象常量测试 ====================
    @Test
    void testSystemConfigConstants() {
        assertEquals("Settings", InternalObjectSchema.ENTITY_TYPE_SETTINGS);
        assertEquals("MigrationStatus", InternalObjectSchema.ENTITY_TYPE_MIGRATION_STATUS);
        assertEquals("MigrationReference", InternalObjectSchema.ENTITY_TYPE_MIGRATION_REFERENCE);
    }

    // ==================== 规则管理对象常量测试 ====================
    @Test
    void testRuleManagementConstants() {
        assertEquals("RuleManager", InternalObjectSchema.ENTITY_TYPE_RULE_MANAGER);
        assertEquals("Rule", InternalObjectSchema.ENTITY_TYPE_RULE);
    }

    // ==================== 用户权限对象常量测试 ====================
    @Test
    void testUserPermissionConstants() {
        assertEquals("Group", InternalObjectSchema.ENTITY_TYPE_GROUP);
        assertEquals("User", InternalObjectSchema.ENTITY_TYPE_USER);
        assertEquals("Role", InternalObjectSchema.ENTITY_TYPE_ROLE);
        assertEquals("Capability", InternalObjectSchema.ENTITY_TYPE_CAPABILITY);
    }

    // ==================== 连接器对象常量测试 ====================
    @Test
    void testConnectorConstants() {
        assertEquals("Connector", InternalObjectSchema.ENTITY_TYPE_CONNECTOR);
        assertEquals("ConnectorManager", InternalObjectSchema.ENTITY_TYPE_CONNECTOR_MANAGER);
    }

    // ==================== 历史记录对象常量测试 ====================
    @Test
    void testHistoryConstants() {
        assertEquals("History", InternalObjectSchema.ENTITY_TYPE_HISTORY);
        assertEquals("PirHistory", InternalObjectSchema.ENTITY_TYPE_PIR_HISTORY);
        assertEquals("Activity", InternalObjectSchema.ENTITY_TYPE_ACTIVITY);
    }

    // ==================== 任务工作对象常量测试 ====================
    @Test
    void testTaskWorkConstants() {
        assertEquals("work", InternalObjectSchema.ENTITY_TYPE_WORK);
        assertEquals("BackgroundTask", InternalObjectSchema.ENTITY_TYPE_BACKGROUND_TASK);
        assertEquals("RetentionRule", InternalObjectSchema.ENTITY_TYPE_RETENTION_RULE);
    }

    // ==================== 同步集合对象常量测试 ====================
    @Test
    void testSyncCollectionConstants() {
        assertEquals("Sync", InternalObjectSchema.ENTITY_TYPE_SYNC);
        assertEquals("TaxiiCollection", InternalObjectSchema.ENTITY_TYPE_TAXII_COLLECTION);
        assertEquals("InternalFile", InternalObjectSchema.ENTITY_TYPE_INTERNAL_FILE);
        assertEquals("Feed", InternalObjectSchema.ENTITY_TYPE_FEED);
        assertEquals("StreamCollection", InternalObjectSchema.ENTITY_TYPE_STREAM_COLLECTION);
    }

    // ==================== 状态主题对象常量测试 ====================
    @Test
    void testStatusThemeConstants() {
        assertEquals("StatusTemplate", InternalObjectSchema.ENTITY_TYPE_STATUS_TEMPLATE);
        assertEquals("Status", InternalObjectSchema.ENTITY_TYPE_STATUS);
        assertEquals("Theme", InternalObjectSchema.ENTITY_TYPE_THEME);
    }

    // ==================== 模块相关对象常量测试 ====================
    @Test
    void testModuleRelatedConstants() {
        assertEquals("Workspace", InternalObjectSchema.ENTITY_TYPE_WORKSPACE);
        assertEquals("PublicDashboard", InternalObjectSchema.ENTITY_TYPE_PUBLIC_DASHBOARD);
        assertEquals("DeleteOperation", InternalObjectSchema.ENTITY_TYPE_DELETE_OPERATION);
        assertEquals("DraftWorkspace", InternalObjectSchema.ENTITY_TYPE_DRAFT_WORKSPACE);
        assertEquals("ExclusionList", InternalObjectSchema.ENTITY_TYPE_EXCLUSION_LIST);
        assertEquals("FintelTemplate", InternalObjectSchema.ENTITY_TYPE_FINTEL_TEMPLATE);
        assertEquals("SavedFilter", InternalObjectSchema.ENTITY_TYPE_SAVED_FILTER);
        assertEquals("Pir", InternalObjectSchema.ENTITY_TYPE_PIR);
        assertEquals("FintelDesign", InternalObjectSchema.ENTITY_TYPE_FINTEL_DESIGN);
        assertEquals("EmailTemplate", InternalObjectSchema.ENTITY_TYPE_EMAIL_TEMPLATE);
        assertEquals("Form", InternalObjectSchema.ENTITY_TYPE_FORM);
    }

    // ==================== 内部对象分类列表测试 ====================
    @Test
    void testDatedInternalObjects() {
        assertNotNull(InternalObjectSchema.DATED_INTERNAL_OBJECTS);
        assertEquals(16, InternalObjectSchema.DATED_INTERNAL_OBJECTS.size());

        // 验证包含的类型
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Settings"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Group"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("User"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Role"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Capability"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Connector"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Workspace"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Sync"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("PublicDashboard"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("DeleteOperation"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("DraftWorkspace"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("ExclusionList"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("SavedFilter"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Pir"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Form"));
        assertTrue(InternalObjectSchema.DATED_INTERNAL_OBJECTS.contains("Theme"));
    }

    @Test
    void testInternalObjects() {
        assertNotNull(InternalObjectSchema.INTERNAL_OBJECTS);
        assertEquals(35, InternalObjectSchema.INTERNAL_OBJECTS.size());

        // 验证包含所有内部对象类型
        assertTrue(InternalObjectSchema.INTERNAL_OBJECTS.contains("Settings"));
        assertTrue(InternalObjectSchema.INTERNAL_OBJECTS.contains("User"));
        assertTrue(InternalObjectSchema.INTERNAL_OBJECTS.contains("Group"));
        assertTrue(InternalObjectSchema.INTERNAL_OBJECTS.contains("Role"));
        assertTrue(InternalObjectSchema.INTERNAL_OBJECTS.contains("Connector"));
        assertTrue(InternalObjectSchema.INTERNAL_OBJECTS.contains("work"));
        assertTrue(InternalObjectSchema.INTERNAL_OBJECTS.contains("Workspace"));
        assertTrue(InternalObjectSchema.INTERNAL_OBJECTS.contains("Theme"));
    }

    @Test
    void testHistoryObjects() {
        assertNotNull(InternalObjectSchema.HISTORY_OBJECTS);
        assertEquals(1, InternalObjectSchema.HISTORY_OBJECTS.size());
        assertTrue(InternalObjectSchema.HISTORY_OBJECTS.contains("work"));
    }

    // ==================== 类型判断方法测试 ====================
    @Test
    void testIsInternalObject() {
        // 测试内部对象类型
        assertTrue(InternalObjectSchema.isInternalObject("Settings"));
        assertTrue(InternalObjectSchema.isInternalObject("User"));
        assertTrue(InternalObjectSchema.isInternalObject("Group"));
        assertTrue(InternalObjectSchema.isInternalObject("work"));
        assertTrue(InternalObjectSchema.isInternalObject("Workspace"));

        // 测试抽象内部对象类型
        assertTrue(InternalObjectSchema.isInternalObject(SchemaGeneral.ABSTRACT_INTERNAL_OBJECT));

        // 测试非内部对象类型
        assertFalse(InternalObjectSchema.isInternalObject("Malware"));
        assertFalse(InternalObjectSchema.isInternalObject("Attack-Pattern"));

        // 测试 null
        assertFalse(InternalObjectSchema.isInternalObject(null));
    }

    @Test
    void testIsDatedInternalObject() {
        // 测试带日期的内部对象
        assertTrue(InternalObjectSchema.isDatedInternalObject("Settings"));
        assertTrue(InternalObjectSchema.isDatedInternalObject("User"));
        assertTrue(InternalObjectSchema.isDatedInternalObject("Group"));
        assertTrue(InternalObjectSchema.isDatedInternalObject("Workspace"));
        assertTrue(InternalObjectSchema.isDatedInternalObject("Theme"));

        // 测试不带日期的内部对象
        assertFalse(InternalObjectSchema.isDatedInternalObject("work"));
        assertFalse(InternalObjectSchema.isDatedInternalObject("TaxiiCollection"));
        assertFalse(InternalObjectSchema.isDatedInternalObject("Rule"));

        // 测试 null
        assertFalse(InternalObjectSchema.isDatedInternalObject(null));
    }

    @Test
    void testIsHistoryObject() {
        // 测试历史对象
        assertTrue(InternalObjectSchema.isHistoryObject("work"));

        // 测试非历史对象
        assertFalse(InternalObjectSchema.isHistoryObject("Settings"));
        assertFalse(InternalObjectSchema.isHistoryObject("User"));
        assertFalse(InternalObjectSchema.isHistoryObject("Malware"));

        // 测试 null
        assertFalse(InternalObjectSchema.isHistoryObject(null));
    }

    // ==================== 类型注册测试 ====================
    @Test
    void testTypeRegistration() {
        // 验证类型是否已注册到 SchemaTypesDefinition
        assertTrue(SchemaTypesDefinition.isTypeIncludedIn("Settings", SchemaGeneral.ABSTRACT_INTERNAL_OBJECT));
        assertTrue(SchemaTypesDefinition.isTypeIncludedIn("User", SchemaGeneral.ABSTRACT_INTERNAL_OBJECT));
        assertTrue(SchemaTypesDefinition.isTypeIncludedIn("Group", SchemaGeneral.ABSTRACT_INTERNAL_OBJECT));
        assertTrue(SchemaTypesDefinition.isTypeIncludedIn("work", SchemaGeneral.ABSTRACT_INTERNAL_OBJECT));

        // 验证获取所有类型
        assertEquals(35, SchemaTypesDefinition.getTypes(SchemaGeneral.ABSTRACT_INTERNAL_OBJECT).size());
    }
}
