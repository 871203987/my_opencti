package io.opencti.schema.validator;

import io.opencti.schema.attribute.AttributeDefinitionSchema;
import io.opencti.schema.attribute.AttributeDefinitionSchema.AttributeDefinition;
import io.opencti.schema.attribute.AttributeDefinitionSchema.DateAttribute;
import io.opencti.schema.attribute.AttributeDefinitionSchema.MandatoryType;
import io.opencti.schema.attribute.AttributeDefinitionSchema.NumericAttribute;
import io.opencti.schema.attribute.AttributeDefinitionSchema.NumericPrecision;
import io.opencti.schema.attribute.AttributeDefinitionSchema.TextAttribute;
import io.opencti.schema.attribute.AttributeDefinitionSchema.AttributeFormat;
import io.opencti.schema.validator.SchemaValidator.EditInput;
import io.opencti.schema.validator.SchemaValidator.EditOperation;
import io.opencti.schema.validator.SchemaValidator.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SchemaValidator 单元测试
 * 测试Schema验证器的正确性
 */
@DisplayName("SchemaValidator 测试")
class SchemaValidatorTest {

    // ==================== 辅助方法测试 ====================

    @Test
    @DisplayName("isEmptyField应正确判断空值")
    void testIsEmptyField() {
        assertTrue(SchemaValidator.isEmptyField(null));
        assertTrue(SchemaValidator.isEmptyField(""));
        assertTrue(SchemaValidator.isEmptyField("   "));
        assertTrue(SchemaValidator.isEmptyField(Collections.emptyList()));

        assertFalse(SchemaValidator.isEmptyField("value"));
        assertFalse(SchemaValidator.isEmptyField(123));
        assertFalse(SchemaValidator.isEmptyField(Arrays.asList("item")));
    }

    @Test
    @DisplayName("isNotEmptyField应正确判断非空值")
    void testIsNotEmptyField() {
        assertFalse(SchemaValidator.isNotEmptyField(null));
        assertFalse(SchemaValidator.isNotEmptyField(""));
        assertTrue(SchemaValidator.isNotEmptyField("value"));
    }

    // ==================== 属性格式验证测试 ====================

    @Test
    @DisplayName("validateAndFormatSchemaAttribute对字符串属性应正确验证")
    void testValidateStringAttribute() {
        TextAttribute attrDef = new TextAttribute("name", "Name", MandatoryType.NO, AttributeFormat.SHORT);
        List<Object> values = new ArrayList<>();
        values.add("  test value  ");
        EditInput input = new EditInput("name", values, EditOperation.REPLACE);

        SchemaValidator.validateAndFormatSchemaAttribute("name", attrDef, input);

        // 验证trim处理
        assertEquals("test value", input.value.get(0));
    }

    @Test
    @DisplayName("validateAndFormatSchemaAttribute对无效字符串应抛出异常")
    void testValidateStringAttributeInvalid() {
        TextAttribute attrDef = new TextAttribute("name", "Name", MandatoryType.NO, AttributeFormat.SHORT);
        List<Object> values = new ArrayList<>();
        values.add(123); // 非字符串
        EditInput input = new EditInput("name", values, EditOperation.REPLACE);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateAndFormatSchemaAttribute("name", attrDef, input);
        });
        assertEquals("name", exception.getAttributeName());
    }

    @Test
    @DisplayName("validateAndFormatSchemaAttribute对布尔属性应正确验证")
    void testValidateBooleanAttribute() {
        AttributeDefinitionSchema.BooleanAttribute attrDef = new AttributeDefinitionSchema.BooleanAttribute(
                "active", "Active", MandatoryType.NO);
        List<Object> values = new ArrayList<>();
        values.add(true);
        EditInput input = new EditInput("active", values, EditOperation.REPLACE);

        assertDoesNotThrow(() -> {
            SchemaValidator.validateAndFormatSchemaAttribute("active", attrDef, input);
        });
    }

    @Test
    @DisplayName("validateAndFormatSchemaAttribute对无效布尔应抛出异常")
    void testValidateBooleanAttributeInvalid() {
        AttributeDefinitionSchema.BooleanAttribute attrDef = new AttributeDefinitionSchema.BooleanAttribute(
                "active", "Active", MandatoryType.NO);
        List<Object> values = new ArrayList<>();
        values.add(123); // 非布尔
        EditInput input = new EditInput("active", values, EditOperation.REPLACE);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateAndFormatSchemaAttribute("active", attrDef, input);
        });
    }

    @Test
    @DisplayName("validateAndFormatSchemaAttribute对日期属性应正确验证")
    void testValidateDateAttribute() {
        DateAttribute attrDef = new DateAttribute("created", "Created", MandatoryType.NO);
        List<Object> values = new ArrayList<>();
        values.add("2023-01-01T00:00:00Z");
        EditInput input = new EditInput("created", values, EditOperation.REPLACE);

        assertDoesNotThrow(() -> {
            SchemaValidator.validateAndFormatSchemaAttribute("created", attrDef, input);
        });
    }

    @Test
    @DisplayName("validateAndFormatSchemaAttribute对无效日期应抛出异常")
    void testValidateDateAttributeInvalid() {
        DateAttribute attrDef = new DateAttribute("created", "Created", MandatoryType.NO);
        List<Object> values = new ArrayList<>();
        values.add("invalid-date");
        EditInput input = new EditInput("created", values, EditOperation.REPLACE);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateAndFormatSchemaAttribute("created", attrDef, input);
        });
    }

    @Test
    @DisplayName("validateAndFormatSchemaAttribute对数值属性应正确验证")
    void testValidateNumericAttribute() {
        NumericAttribute attrDef = new NumericAttribute("count", "Count", MandatoryType.NO, NumericPrecision.INTEGER);
        List<Object> values = new ArrayList<>();
        values.add(42);
        EditInput input = new EditInput("count", values, EditOperation.REPLACE);

        assertDoesNotThrow(() -> {
            SchemaValidator.validateAndFormatSchemaAttribute("count", attrDef, input);
        });
    }

    @Test
    @DisplayName("validateAndFormatSchemaAttribute对无效数值应抛出异常")
    void testValidateNumericAttributeInvalid() {
        NumericAttribute attrDef = new NumericAttribute("count", "Count", MandatoryType.NO, NumericPrecision.INTEGER);
        List<Object> values = new ArrayList<>();
        values.add("not-a-number");
        EditInput input = new EditInput("count", values, EditOperation.REPLACE);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateAndFormatSchemaAttribute("count", attrDef, input);
        });
    }

    @Test
    @DisplayName("validateAndFormatSchemaAttribute对多值非多值属性应抛出异常")
    void testValidateMultipleAttribute() {
        TextAttribute attrDef = new TextAttribute("name", "Name", MandatoryType.NO, AttributeFormat.SHORT);
        // 非多值属性但有多个值
        List<Object> values = Arrays.asList("value1", "value2");
        EditInput input = new EditInput("name", values, EditOperation.REPLACE);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateAndFormatSchemaAttribute("name", attrDef, input);
        });
        assertEquals("name", exception.getAttributeName());
    }

    // ==================== 必填属性验证测试 ====================

    @Test
    @DisplayName("validateMandatoryAttributesOnCreation应验证必填属性")
    void testValidateMandatoryAttributesOnCreation() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Test");
        // 缺少必填属性 "description"

        List<String> mandatoryAttrs = Arrays.asList("name", "description");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateMandatoryAttributesOnCreation(input, mandatoryAttrs);
        });
        assertEquals("description", exception.getAttributeName());
    }

    @Test
    @DisplayName("validateMandatoryAttributesOnCreation对空必填值应抛出异常")
    void testValidateMandatoryAttributesOnCreationEmpty() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", ""); // 空值

        List<String> mandatoryAttrs = Collections.singletonList("name");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateMandatoryAttributesOnCreation(input, mandatoryAttrs);
        });
    }

    @Test
    @DisplayName("validateMandatoryAttributesOnCreation对有效输入应通过")
    void testValidateMandatoryAttributesOnCreationValid() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Test");
        input.put("description", "Description");

        List<String> mandatoryAttrs = Arrays.asList("name", "description");

        assertDoesNotThrow(() -> {
            SchemaValidator.validateMandatoryAttributesOnCreation(input, mandatoryAttrs);
        });
    }

    @Test
    @DisplayName("validateMandatoryAttributesOnUpdate应验证已提供的必填属性")
    void testValidateMandatoryAttributesOnUpdate() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", ""); // 空值

        List<String> mandatoryAttrs = Collections.singletonList("name");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateMandatoryAttributesOnUpdate(input, mandatoryAttrs);
        });
    }

    @Test
    @DisplayName("validateMandatoryAttributesOnUpdate对未提供的必填属性应通过")
    void testValidateMandatoryAttributesOnUpdateNotProvided() {
        Map<String, Object> input = new HashMap<>();
        input.put("other", "value");

        List<String> mandatoryAttrs = Collections.singletonList("name");

        // 未提供name属性，不应验证
        assertDoesNotThrow(() -> {
            SchemaValidator.validateMandatoryAttributesOnUpdate(input, mandatoryAttrs);
        });
    }

    // ==================== 可更新属性验证测试 ====================

    @Test
    @DisplayName("validateUpdatableAttribute应返回不可更新的属性")
    void testValidateUpdatableAttribute() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Test");
        input.put("id", "123"); // 不可更新

        List<String> updatableAttrs = Collections.singletonList("name");

        List<String> invalidKeys = SchemaValidator.validateUpdatableAttribute(updatableAttrs, input);

        assertEquals(1, invalidKeys.size());
        assertTrue(invalidKeys.contains("id"));
    }

    @Test
    @DisplayName("validateUpdatableAttribute对空可更新列表应返回所有属性")
    void testValidateUpdatableAttributeNull() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Test");
        input.put("id", "123");

        List<String> invalidKeys = SchemaValidator.validateUpdatableAttribute(null, input);

        assertEquals(2, invalidKeys.size());
    }

    @Test
    @DisplayName("validateUpdatableAttribute对有效输入应返回空列表")
    void testValidateUpdatableAttributeValid() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Test");

        List<String> updatableAttrs = Arrays.asList("name", "description");

        List<String> invalidKeys = SchemaValidator.validateUpdatableAttribute(updatableAttrs, input);

        assertTrue(invalidKeys.isEmpty());
    }

    // ==================== 综合输入验证测试 ====================

    @Test
    @DisplayName("validateInputCreation应执行完整验证")
    void testValidateInputCreation() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Test");
        input.put("count", 42);

        Map<String, AttributeDefinition> attrDefs = new HashMap<>();
        attrDefs.put("name", new TextAttribute("name", "Name", MandatoryType.NO, AttributeFormat.SHORT));
        attrDefs.put("count", new NumericAttribute("count", "Count", MandatoryType.NO, NumericPrecision.INTEGER));

        List<String> mandatoryAttrs = Collections.singletonList("name");

        assertDoesNotThrow(() -> {
            SchemaValidator.validateInputCreation("TestType", input, attrDefs, mandatoryAttrs);
        });
    }

    @Test
    @DisplayName("validateInputCreation对无效格式应抛出异常")
    void testValidateInputCreationInvalidFormat() {
        Map<String, Object> input = new HashMap<>();
        input.put("count", "not-a-number");

        Map<String, AttributeDefinition> attrDefs = new HashMap<>();
        attrDefs.put("count", new NumericAttribute("count", "Count", MandatoryType.NO, NumericPrecision.INTEGER));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateInputCreation("TestType", input, attrDefs, null);
        });
    }

    @Test
    @DisplayName("validateInputUpdate应执行完整验证")
    void testValidateInputUpdate() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", "Test");

        Map<String, AttributeDefinition> attrDefs = new HashMap<>();
        attrDefs.put("name", new TextAttribute("name", "Name", MandatoryType.NO, AttributeFormat.SHORT));

        List<String> updatableAttrs = Collections.singletonList("name");

        assertDoesNotThrow(() -> {
            SchemaValidator.validateInputUpdate("TestType", input, attrDefs, null, updatableAttrs);
        });
    }

    @Test
    @DisplayName("validateInputUpdate对不可更新属性应抛出异常")
    void testValidateInputUpdateNotUpdatable() {
        Map<String, Object> input = new HashMap<>();
        input.put("id", "123");

        List<String> updatableAttrs = Collections.singletonList("name");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            SchemaValidator.validateInputUpdate("TestType", input, null, null, updatableAttrs);
        });
        assertEquals("id", exception.getAttributeName());
    }

    // ==================== EditOperation测试 ====================

    @Test
    @DisplayName("EditOperation枚举应包含所有操作")
    void testEditOperation() {
        assertEquals(3, EditOperation.values().length);
        assertNotNull(EditOperation.REPLACE);
        assertNotNull(EditOperation.ADD);
        assertNotNull(EditOperation.REMOVE);
    }

    // ==================== ValidationException测试 ====================

    @Test
    @DisplayName("ValidationException应正确存储属性名")
    void testValidationException() {
        ValidationException exception = new ValidationException("Test message", "testAttr");
        assertEquals("Test message", exception.getMessage());
        assertEquals("testAttr", exception.getAttributeName());
    }

    @Test
    @DisplayName("ValidationException应支持cause")
    void testValidationExceptionWithCause() {
        Throwable cause = new RuntimeException("Original error");
        ValidationException exception = new ValidationException("Test message", "testAttr", cause);
        assertEquals("Test message", exception.getMessage());
        assertEquals("testAttr", exception.getAttributeName());
        assertEquals(cause, exception.getCause());
    }
}
