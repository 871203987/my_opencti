package io.opencti.schema.validator;

import io.opencti.schema.attribute.AttributeDefinitionSchema;
import io.opencti.schema.attribute.AttributeDefinitionSchema.AttributeDefinition;
import io.opencti.schema.attribute.AttributeDefinitionSchema.AttrType;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Schema 验证器
 * 重写自: opencti-platform/opencti-graphql/src/schema/schema-validator.ts
 *
 * 提供数据验证功能，包括属性格式验证、必填属性验证、可更新属性验证等。
 */
public final class SchemaValidator {

    private SchemaValidator() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== 编辑操作类型 ====================
    public enum EditOperation {
        REPLACE,
        ADD,
        REMOVE
    }

    // ==================== 编辑输入 ====================
    public static class EditInput {
        public String key;
        public List<Object> value;
        public EditOperation operation;
        public String objectPath; // 用于对象类型的路径

        public EditInput(String key, List<Object> value, EditOperation operation) {
            this.key = key;
            this.value = value;
            this.operation = operation;
        }
    }

    // ==================== 验证异常 ====================
    public static class ValidationException extends RuntimeException {
        private final String attributeName;

        public ValidationException(String message, String attributeName) {
            super(message);
            this.attributeName = attributeName;
        }

        public ValidationException(String message, String attributeName, Throwable cause) {
            super(message, cause);
            this.attributeName = attributeName;
        }

        public String getAttributeName() {
            return attributeName;
        }
    }

    // ==================== 属性格式验证 ====================

    /**
     * 验证并格式化属性
     *
     * @param attributeName 属性名称
     * @param attributeDefinition 属性定义
     * @param editInput 编辑输入
     * @throws ValidationException 验证失败时抛出
     */
    public static void validateAndFormatSchemaAttribute(
            String attributeName,
            AttributeDefinition attributeDefinition,
            EditInput editInput) {

        // 基础验证
        if (attributeDefinition == null || isEmptyField(editInput.value)) {
            return;
        }

        // 检查是否为对象补丁（对象类型且有objectPath）
        boolean isPatchObject = attributeDefinition.getType() == AttrType.OBJECT && editInput.objectPath != null;

        // 检查多值属性
        if (!isPatchObject && !attributeDefinition.isMultiple() && editInput.value.size() > 1) {
            throw new ValidationException("Attribute cannot be multiple", attributeName);
        }

        // 根据类型进行验证
        switch (attributeDefinition.getType()) {
            case STRING:
                validateStringAttribute(attributeName, editInput);
                break;
            case BOOLEAN:
                validateBooleanAttribute(attributeName, editInput);
                break;
            case DATE:
                validateDateAttribute(attributeName, editInput);
                break;
            case NUMERIC:
                validateNumericAttribute(attributeName, editInput);
                break;
            default:
                // 其他类型暂不验证
                break;
        }
    }

    /**
     * 验证字符串属性
     */
    private static void validateStringAttribute(String attributeName, EditInput editInput) {
        List<Object> values = new ArrayList<>();
        for (Object value : editInput.value) {
            if (value != null && !(value instanceof String)) {
                throw new ValidationException("Attribute must be a string", attributeName);
            }
            // trim处理
            values.add(value != null ? ((String) value).trim() : value);
        }
        editInput.value = values;
    }

    /**
     * 验证布尔属性
     */
    private static void validateBooleanAttribute(String attributeName, EditInput editInput) {
        for (Object value : editInput.value) {
            if (value != null && !(value instanceof Boolean) && !(value instanceof String)) {
                throw new ValidationException("Attribute must be a boolean/string", attributeName);
            }
        }
    }

    /**
     * 验证日期属性
     */
    private static void validateDateAttribute(String attributeName, EditInput editInput) {
        for (Object value : editInput.value) {
            if (value != null) {
                if (!(value instanceof String)) {
                    throw new ValidationException("Attribute must be a date string", attributeName);
                }
                try {
                    Instant.parse((String) value);
                } catch (DateTimeParseException e) {
                    throw new ValidationException("Attribute must be a valid ISO 8601 date", attributeName);
                }
            }
        }
    }

    /**
     * 验证数值属性
     */
    private static void validateNumericAttribute(String attributeName, EditInput editInput) {
        for (Object value : editInput.value) {
            if (value != null) {
                try {
                    Double.parseDouble(value.toString());
                } catch (NumberFormatException e) {
                    throw new ValidationException("Attribute must be a numeric/string", attributeName);
                }
            }
        }
    }

    // ==================== 必填属性验证 ====================

    /**
     * 验证必填属性（基础方法）
     *
     * @param input 输入数据
     * @param mandatoryAttributes 必填属性列表
     * @param isCreation 是否为创建操作
     * @param validation 验证函数
     */
    public static void validateMandatoryAttributes(
            Map<String, Object> input,
            List<String> mandatoryAttributes,
            boolean isCreation,
            ValidationFunction validation) {

        if (mandatoryAttributes == null || mandatoryAttributes.isEmpty()) {
            return;
        }

        List<String> inputKeys = new ArrayList<>(input.keySet());

        for (String attrName : mandatoryAttributes) {
            if (!validation.validate(inputKeys, attrName, input)) {
                throw new ValidationException("This attribute is mandatory", attrName);
            }
        }
    }

    /**
     * 创建时必填属性验证
     *
     * @param input 输入数据
     * @param mandatoryAttributes 必填属性列表
     */
    public static void validateMandatoryAttributesOnCreation(
            Map<String, Object> input,
            List<String> mandatoryAttributes) {

        validateMandatoryAttributes(input, mandatoryAttributes, true, (inputKeys, mandatoryKey, data) -> {
            // 创建时：必须包含该键且值非空
            if (!inputKeys.contains(mandatoryKey)) {
                return false;
            }
            Object value = data.get(mandatoryKey);
            return isNotEmptyField(value);
        });
    }

    /**
     * 更新时必填属性验证
     *
     * @param input 输入数据
     * @param mandatoryAttributes 必填属性列表
     */
    public static void validateMandatoryAttributesOnUpdate(
            Map<String, Object> input,
            List<String> mandatoryAttributes) {

        validateMandatoryAttributes(input, mandatoryAttributes, false, (inputKeys, mandatoryKey, data) -> {
            // 更新时：如果包含该键，则值必须非空
            if (!inputKeys.contains(mandatoryKey)) {
                return true; // 未提供该属性，不验证
            }
            Object value = data.get(mandatoryKey);
            return isNotEmptyField(value);
        });
    }

    // ==================== 可更新属性验证 ====================

    /**
     * 验证可更新属性
     *
     * @param updatableAttributes 可更新属性列表
     * @param input 输入数据
     * @return 不可更新的属性列表
     */
    public static List<String> validateUpdatableAttribute(
            List<String> updatableAttributes,
            Map<String, Object> input) {

        List<String> invalidKeys = new ArrayList<>();

        if (updatableAttributes == null) {
            // 如果未提供可更新属性列表，则认为所有输入属性都不可更新
            invalidKeys.addAll(input.keySet());
            return invalidKeys;
        }

        for (String key : input.keySet()) {
            if (!updatableAttributes.contains(key)) {
                invalidKeys.add(key);
            }
        }

        return invalidKeys;
    }

    // ==================== 综合输入验证 ====================

    /**
     * 验证创建输入
     *
     * @param instanceType 实例类型
     * @param input 输入数据
     * @param attributeDefinitions 属性定义映射
     * @param mandatoryAttributes 必填属性列表
     * @throws ValidationException 验证失败时抛出
     */
    public static void validateInputCreation(
            String instanceType,
            Map<String, Object> input,
            Map<String, AttributeDefinition> attributeDefinitions,
            List<String> mandatoryAttributes) {

        // 1. 格式验证
        List<EditInput> editInputs = convertToEditInputs(input);
        for (EditInput editInput : editInputs) {
            AttributeDefinition attrDef = attributeDefinitions != null ?
                    attributeDefinitions.get(editInput.key) : null;
            validateAndFormatSchemaAttribute(editInput.key, attrDef, editInput);
        }

        // 2. 必填属性验证
        if (mandatoryAttributes != null && !mandatoryAttributes.isEmpty()) {
            validateMandatoryAttributesOnCreation(input, mandatoryAttributes);
        }
    }

    /**
     * 验证更新输入
     *
     * @param instanceType 实例类型
     * @param input 输入数据
     * @param attributeDefinitions 属性定义映射
     * @param mandatoryAttributes 必填属性列表
     * @param updatableAttributes 可更新属性列表
     * @throws ValidationException 验证失败时抛出
     */
    public static void validateInputUpdate(
            String instanceType,
            Map<String, Object> input,
            Map<String, AttributeDefinition> attributeDefinitions,
            List<String> mandatoryAttributes,
            List<String> updatableAttributes) {

        // 1. 格式验证
        List<EditInput> editInputs = convertToEditInputs(input);
        for (EditInput editInput : editInputs) {
            AttributeDefinition attrDef = attributeDefinitions != null ?
                    attributeDefinitions.get(editInput.key) : null;
            validateAndFormatSchemaAttribute(editInput.key, attrDef, editInput);
        }

        // 2. 必填属性验证
        if (mandatoryAttributes != null && !mandatoryAttributes.isEmpty()) {
            validateMandatoryAttributesOnUpdate(input, mandatoryAttributes);
        }

        // 3. 可更新属性验证
        List<String> invalidKeys = validateUpdatableAttribute(updatableAttributes, input);
        if (!invalidKeys.isEmpty()) {
            throw new ValidationException("You cannot update incompatible attribute", invalidKeys.get(0));
        }
    }

    // ==================== 辅助方法 ====================

    /**
     * 检查字段是否为空
     */
    public static boolean isEmptyField(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).trim().isEmpty();
        }
        if (value instanceof List) {
            return ((List<?>) value).isEmpty();
        }
        return false;
    }

    /**
     * 检查字段是否非空
     */
    public static boolean isNotEmptyField(Object value) {
        return !isEmptyField(value);
    }

    /**
     * 将输入数据转换为EditInput列表
     */
    private static List<EditInput> convertToEditInputs(Map<String, Object> input) {
        List<EditInput> editInputs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            Object value = entry.getValue();
            List<Object> valueList;
            if (value instanceof List) {
                valueList = (List<Object>) value;
            } else {
                valueList = new ArrayList<>();
                valueList.add(value);
            }
            editInputs.add(new EditInput(entry.getKey(), valueList, EditOperation.REPLACE));
        }
        return editInputs;
    }

    /**
     * 验证函数接口
     */
    @FunctionalInterface
    public interface ValidationFunction {
        boolean validate(List<String> inputKeys, String mandatoryKey, Map<String, Object> data);
    }
}
