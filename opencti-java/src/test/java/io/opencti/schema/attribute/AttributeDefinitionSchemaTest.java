package io.opencti.schema.attribute;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AttributeDefinitionSchema 单元测试
 * 测试属性定义Schema的正确性
 */
@DisplayName("AttributeDefinitionSchema 测试")
class AttributeDefinitionSchemaTest {

    // ==================== 枚举测试 ====================

    @Test
    @DisplayName("AttrType枚举应包含所有类型")
    void testAttrTypeEnum() {
        assertEquals(6, AttributeDefinitionSchema.AttrType.values().length);
        assertNotNull(AttributeDefinitionSchema.AttrType.STRING);
        assertNotNull(AttributeDefinitionSchema.AttrType.DATE);
        assertNotNull(AttributeDefinitionSchema.AttrType.NUMERIC);
        assertNotNull(AttributeDefinitionSchema.AttrType.BOOLEAN);
        assertNotNull(AttributeDefinitionSchema.AttrType.OBJECT);
        assertNotNull(AttributeDefinitionSchema.AttrType.REF);
    }

    @Test
    @DisplayName("MandatoryType枚举应包含所有类型")
    void testMandatoryTypeEnum() {
        assertEquals(4, AttributeDefinitionSchema.MandatoryType.values().length);
        assertNotNull(AttributeDefinitionSchema.MandatoryType.INTERNAL);
        assertNotNull(AttributeDefinitionSchema.MandatoryType.EXTERNAL);
        assertNotNull(AttributeDefinitionSchema.MandatoryType.CUSTOMIZABLE);
        assertNotNull(AttributeDefinitionSchema.MandatoryType.NO);
    }

    @Test
    @DisplayName("AttributeFormat枚举应包含所有格式")
    void testAttributeFormatEnum() {
        assertEquals(6, AttributeDefinitionSchema.AttributeFormat.values().length);
        assertNotNull(AttributeDefinitionSchema.AttributeFormat.ID);
        assertNotNull(AttributeDefinitionSchema.AttributeFormat.SHORT);
        assertNotNull(AttributeDefinitionSchema.AttributeFormat.TEXT);
        assertNotNull(AttributeDefinitionSchema.AttributeFormat.ENUM);
        assertNotNull(AttributeDefinitionSchema.AttributeFormat.VOCABULARY);
        assertNotNull(AttributeDefinitionSchema.AttributeFormat.JSON);
    }

    @Test
    @DisplayName("NumericPrecision枚举应包含所有精度")
    void testNumericPrecisionEnum() {
        assertEquals(3, AttributeDefinitionSchema.NumericPrecision.values().length);
        assertNotNull(AttributeDefinitionSchema.NumericPrecision.INTEGER);
        assertNotNull(AttributeDefinitionSchema.NumericPrecision.LONG);
        assertNotNull(AttributeDefinitionSchema.NumericPrecision.FLOAT);
    }

    // ==================== 属性类测试 ====================

    @Test
    @DisplayName("DateAttribute应正确创建")
    void testDateAttribute() {
        AttributeDefinitionSchema.DateAttribute attr = new AttributeDefinitionSchema.DateAttribute(
                "test_date", "Test Date", AttributeDefinitionSchema.MandatoryType.NO
        );
        assertEquals("test_date", attr.getName());
        assertEquals("Test Date", attr.getLabel());
        assertEquals(AttributeDefinitionSchema.AttrType.DATE, attr.getType());
        assertFalse(attr.isMultiple());
        assertEquals(AttributeDefinitionSchema.MandatoryType.NO, attr.getMandatoryType());
    }

    @Test
    @DisplayName("BooleanAttribute应正确创建")
    void testBooleanAttribute() {
        AttributeDefinitionSchema.BooleanAttribute attr = new AttributeDefinitionSchema.BooleanAttribute(
                "test_bool", "Test Boolean", AttributeDefinitionSchema.MandatoryType.NO
        );
        assertEquals("test_bool", attr.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.BOOLEAN, attr.getType());
    }

    @Test
    @DisplayName("NumericAttribute应正确创建")
    void testNumericAttribute() {
        AttributeDefinitionSchema.NumericAttribute attr = new AttributeDefinitionSchema.NumericAttribute(
                "test_num", "Test Number", AttributeDefinitionSchema.MandatoryType.NO,
                AttributeDefinitionSchema.NumericPrecision.INTEGER
        );
        assertEquals("test_num", attr.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.NUMERIC, attr.getType());
        assertEquals(AttributeDefinitionSchema.NumericPrecision.INTEGER, attr.getPrecision());
        assertFalse(attr.isScalable());

        attr.setScalable(true);
        assertTrue(attr.isScalable());
    }

    @Test
    @DisplayName("TextAttribute应正确创建")
    void testTextAttribute() {
        AttributeDefinitionSchema.TextAttribute attr = new AttributeDefinitionSchema.TextAttribute(
                "test_text", "Test Text", AttributeDefinitionSchema.MandatoryType.NO,
                AttributeDefinitionSchema.AttributeFormat.TEXT
        );
        assertEquals("test_text", attr.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.STRING, attr.getType());
        assertEquals(AttributeDefinitionSchema.AttributeFormat.TEXT, attr.getFormat());
    }

    @Test
    @DisplayName("IdAttribute应正确创建")
    void testIdAttribute() {
        AttributeDefinitionSchema.IdAttribute attr = new AttributeDefinitionSchema.IdAttribute(
                "test_id", "Test ID", AttributeDefinitionSchema.MandatoryType.NO,
                new String[]{"TestType"}
        );
        assertEquals("test_id", attr.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.STRING, attr.getType());
        assertEquals(AttributeDefinitionSchema.AttributeFormat.ID, attr.getFormat());
        assertArrayEquals(new String[]{"TestType"}, attr.getEntityTypes());
    }

    @Test
    @DisplayName("EnumAttribute应正确创建")
    void testEnumAttribute() {
        String[] values = {"value1", "value2", "value3"};
        AttributeDefinitionSchema.EnumAttribute attr = new AttributeDefinitionSchema.EnumAttribute(
                "test_enum", "Test Enum", AttributeDefinitionSchema.MandatoryType.NO, values
        );
        assertEquals("test_enum", attr.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.STRING, attr.getType());
        assertEquals(AttributeDefinitionSchema.AttributeFormat.ENUM, attr.getFormat());
        assertArrayEquals(values, attr.getValues());
    }

    @Test
    @DisplayName("VocabAttribute应正确创建")
    void testVocabAttribute() {
        AttributeDefinitionSchema.VocabAttribute attr = new AttributeDefinitionSchema.VocabAttribute(
                "test_vocab", "Test Vocab", AttributeDefinitionSchema.MandatoryType.NO, "test_category"
        );
        assertEquals("test_vocab", attr.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.STRING, attr.getType());
        assertEquals(AttributeDefinitionSchema.AttributeFormat.VOCABULARY, attr.getFormat());
        assertEquals("test_category", attr.getVocabularyCategory());
    }

    @Test
    @DisplayName("JsonAttribute应正确创建")
    void testJsonAttribute() {
        AttributeDefinitionSchema.JsonAttribute attr = new AttributeDefinitionSchema.JsonAttribute(
                "test_json", "Test JSON", AttributeDefinitionSchema.MandatoryType.NO
        );
        assertEquals("test_json", attr.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.STRING, attr.getType());
        assertEquals(AttributeDefinitionSchema.AttributeFormat.JSON, attr.getFormat());
    }

    // ==================== 全局属性测试 ====================

    @Test
    @DisplayName("ATTR_INTERNAL_ID应正确配置")
    void testAttrInternalId() {
        assertEquals("internal_id", AttributeDefinitionSchema.ATTR_INTERNAL_ID.getName());
        assertEquals("Internal ID", AttributeDefinitionSchema.ATTR_INTERNAL_ID.getLabel());
        assertEquals(AttributeDefinitionSchema.MandatoryType.INTERNAL, AttributeDefinitionSchema.ATTR_INTERNAL_ID.getMandatoryType());
        assertEquals(AttributeDefinitionSchema.AttrType.STRING, AttributeDefinitionSchema.ATTR_INTERNAL_ID.getType());
    }

    @Test
    @DisplayName("ATTR_STANDARD_ID应正确配置")
    void testAttrStandardId() {
        assertEquals("standard_id", AttributeDefinitionSchema.ATTR_STANDARD_ID.getName());
        assertEquals("Standard ID", AttributeDefinitionSchema.ATTR_STANDARD_ID.getLabel());
        assertEquals(AttributeDefinitionSchema.MandatoryType.INTERNAL, AttributeDefinitionSchema.ATTR_STANDARD_ID.getMandatoryType());
    }

    @Test
    @DisplayName("ATTR_CREATED_AT应正确配置")
    void testAttrCreatedAt() {
        assertEquals("created_at", AttributeDefinitionSchema.ATTR_CREATED_AT.getName());
        assertEquals("Platform creation date", AttributeDefinitionSchema.ATTR_CREATED_AT.getLabel());
        assertEquals(AttributeDefinitionSchema.AttrType.DATE, AttributeDefinitionSchema.ATTR_CREATED_AT.getType());
        assertEquals(AttributeDefinitionSchema.MandatoryType.INTERNAL, AttributeDefinitionSchema.ATTR_CREATED_AT.getMandatoryType());
    }

    @Test
    @DisplayName("ATTR_UPDATED_AT应正确配置")
    void testAttrUpdatedAt() {
        assertEquals("updated_at", AttributeDefinitionSchema.ATTR_UPDATED_AT.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.DATE, AttributeDefinitionSchema.ATTR_UPDATED_AT.getType());
    }

    // ==================== STIX属性测试 ====================

    @Test
    @DisplayName("ATTR_ALIASES应正确配置")
    void testAttrAliases() {
        assertEquals("aliases", AttributeDefinitionSchema.ATTR_ALIASES.getName());
        assertEquals("Aliases", AttributeDefinitionSchema.ATTR_ALIASES.getLabel());
        assertTrue(AttributeDefinitionSchema.ATTR_ALIASES.isMultiple());
        assertEquals(AttributeDefinitionSchema.AttributeFormat.SHORT, AttributeDefinitionSchema.ATTR_ALIASES.getFormat());
    }

    @Test
    @DisplayName("ATTR_CREATED应正确配置")
    void testAttrCreated() {
        assertEquals("created", AttributeDefinitionSchema.ATTR_CREATED.getName());
        assertEquals("Creation date", AttributeDefinitionSchema.ATTR_CREATED.getLabel());
        assertEquals(AttributeDefinitionSchema.AttrType.DATE, AttributeDefinitionSchema.ATTR_CREATED.getType());
        assertEquals(AttributeDefinitionSchema.MandatoryType.EXTERNAL, AttributeDefinitionSchema.ATTR_CREATED.getMandatoryType());
    }

    @Test
    @DisplayName("ATTR_MODIFIED应正确配置")
    void testAttrModified() {
        assertEquals("modified", AttributeDefinitionSchema.ATTR_MODIFIED.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.DATE, AttributeDefinitionSchema.ATTR_MODIFIED.getType());
        assertEquals(AttributeDefinitionSchema.MandatoryType.EXTERNAL, AttributeDefinitionSchema.ATTR_MODIFIED.getMandatoryType());
    }

    @Test
    @DisplayName("ATTR_REVOKED应正确配置")
    void testAttrRevoked() {
        assertEquals("revoked", AttributeDefinitionSchema.ATTR_REVOKED.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.BOOLEAN, AttributeDefinitionSchema.ATTR_REVOKED.getType());
    }

    @Test
    @DisplayName("ATTR_CONFIDENCE应正确配置")
    void testAttrConfidence() {
        assertEquals("confidence", AttributeDefinitionSchema.ATTR_CONFIDENCE.getName());
        assertEquals(AttributeDefinitionSchema.AttrType.NUMERIC, AttributeDefinitionSchema.ATTR_CONFIDENCE.getType());
        assertEquals(AttributeDefinitionSchema.NumericPrecision.INTEGER, AttributeDefinitionSchema.ATTR_CONFIDENCE.getPrecision());
        assertEquals(AttributeDefinitionSchema.MandatoryType.CUSTOMIZABLE, AttributeDefinitionSchema.ATTR_CONFIDENCE.getMandatoryType());
    }

    @Test
    @DisplayName("ATTR_IDENTITY_CLASS应正确配置")
    void testAttrIdentityClass() {
        assertEquals("identity_class", AttributeDefinitionSchema.ATTR_IDENTITY_CLASS.getName());
        assertEquals(AttributeDefinitionSchema.AttributeFormat.ENUM, AttributeDefinitionSchema.ATTR_IDENTITY_CLASS.getFormat());
        assertEquals(5, AttributeDefinitionSchema.ATTR_IDENTITY_CLASS.getValues().length);
    }

    @Test
    @DisplayName("ATTR_X_OPENCTI_LOCATION_TYPE应正确配置")
    void testAttrLocationType() {
        assertEquals("x_opencti_location_type", AttributeDefinitionSchema.ATTR_X_OPENCTI_LOCATION_TYPE.getName());
        assertEquals(5, AttributeDefinitionSchema.ATTR_X_OPENCTI_LOCATION_TYPE.getValues().length);
    }

    // ==================== 类型守卫方法测试 ====================

    @Test
    @DisplayName("isNumericAttribute对数值属性应返回true")
    void testIsNumericAttribute() {
        assertTrue(AttributeDefinitionSchema.isNumericAttribute(AttributeDefinitionSchema.ATTR_CONFIDENCE));
        assertFalse(AttributeDefinitionSchema.isNumericAttribute(AttributeDefinitionSchema.ATTR_CREATED_AT));
    }

    @Test
    @DisplayName("isDateAttribute对日期属性应返回true")
    void testIsDateAttribute() {
        assertTrue(AttributeDefinitionSchema.isDateAttribute(AttributeDefinitionSchema.ATTR_CREATED_AT));
        assertFalse(AttributeDefinitionSchema.isDateAttribute(AttributeDefinitionSchema.ATTR_REVOKED));
    }

    @Test
    @DisplayName("isBooleanAttribute对布尔属性应返回true")
    void testIsBooleanAttribute() {
        assertTrue(AttributeDefinitionSchema.isBooleanAttribute(AttributeDefinitionSchema.ATTR_REVOKED));
        assertFalse(AttributeDefinitionSchema.isBooleanAttribute(AttributeDefinitionSchema.ATTR_ALIASES));
    }

    @Test
    @DisplayName("isStringAttribute对字符串属性应返回true")
    void testIsStringAttribute() {
        assertTrue(AttributeDefinitionSchema.isStringAttribute(AttributeDefinitionSchema.ATTR_ALIASES));
        assertTrue(AttributeDefinitionSchema.isStringAttribute(AttributeDefinitionSchema.ATTR_INTERNAL_ID));
        assertFalse(AttributeDefinitionSchema.isStringAttribute(AttributeDefinitionSchema.ATTR_CREATED_AT));
    }

    @Test
    @DisplayName("isComplexAttribute对对象属性应返回true")
    void testIsComplexAttribute() {
        AttributeDefinitionSchema.ObjectAttribute objAttr = new AttributeDefinitionSchema.ObjectAttribute(
                "test_obj", "Test Object", AttributeDefinitionSchema.MandatoryType.NO,
                false, new AttributeDefinitionSchema.MappingDefinition[]{}
        );
        assertTrue(AttributeDefinitionSchema.isComplexAttribute(objAttr));
        assertFalse(AttributeDefinitionSchema.isComplexAttribute(AttributeDefinitionSchema.ATTR_CREATED_AT));
    }

    @Test
    @DisplayName("isRefAttribute对引用属性应返回true")
    void testIsRefAttribute() {
        assertTrue(AttributeDefinitionSchema.isRefAttribute(AttributeDefinitionSchema.ATTR_CREATORS));
        assertFalse(AttributeDefinitionSchema.isRefAttribute(AttributeDefinitionSchema.ATTR_CREATED_AT));
    }

    @Test
    @DisplayName("类型守卫方法对null应返回false")
    void testTypeGuardsWithNull() {
        assertFalse(AttributeDefinitionSchema.isNumericAttribute(null));
        assertFalse(AttributeDefinitionSchema.isDateAttribute(null));
        assertFalse(AttributeDefinitionSchema.isBooleanAttribute(null));
        assertFalse(AttributeDefinitionSchema.isStringAttribute(null));
        assertFalse(AttributeDefinitionSchema.isComplexAttribute(null));
        assertFalse(AttributeDefinitionSchema.isRefAttribute(null));
    }

    // ==================== 属性默认值测试 ====================

    @Test
    @DisplayName("属性默认值应正确设置")
    void testAttributeDefaults() {
        AttributeDefinitionSchema.DateAttribute attr = new AttributeDefinitionSchema.DateAttribute(
                "test", "Test", AttributeDefinitionSchema.MandatoryType.NO
        );
        assertTrue(attr.isUpsert());
        assertFalse(attr.isUpsertForceReplace());
        assertTrue(attr.isFilterable());
        assertFalse(attr.isEditDefault());
        assertNull(attr.getUpdate()); // null means true
    }

    // ==================== 属性设置器测试 ====================

    @Test
    @DisplayName("属性设置器应正确工作")
    void testAttributeSetters() {
        AttributeDefinitionSchema.DateAttribute attr = new AttributeDefinitionSchema.DateAttribute(
                "test", "Test", AttributeDefinitionSchema.MandatoryType.NO
        );

        attr.setDescription("Test description");
        assertEquals("Test description", attr.getDescription());

        attr.setUpsert(false);
        assertFalse(attr.isUpsert());

        attr.setUpsertForceReplace(true);
        assertTrue(attr.isUpsertForceReplace());

        attr.setFilterable(false);
        assertFalse(attr.isFilterable());

        attr.setEditDefault(true);
        assertTrue(attr.isEditDefault());

        attr.setUpdate(false);
        assertFalse(attr.getUpdate());

        attr.setFeatureFlag("test_flag");
        assertEquals("test_flag", attr.getFeatureFlag());
    }
}
