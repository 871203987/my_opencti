package io.opencti.database.middleware.model;

import java.util.List;

/**
 * 更新输入模型
 * 原文件: middleware.js 输入结构
 * 
 * 表示单个属性的更新操作，包含键、值、操作类型等信息。
 */
public class UpdateInput {

    private String key;
    private List<Object> value;
    private List<Object> previous;
    private String operation;
    private String objectPath;

    public static final String OPERATION_REPLACE = "replace";
    public static final String OPERATION_ADD = "add";
    public static final String OPERATION_REMOVE = "remove";

    public UpdateInput() {
    }

    public UpdateInput(String key, List<Object> value) {
        this.key = key;
        this.value = value;
        this.operation = OPERATION_REPLACE;
    }

    public UpdateInput(String key, List<Object> value, String operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Object> getValue() {
        return value;
    }

    public void setValue(List<Object> value) {
        this.value = value;
    }

    public List<Object> getPrevious() {
        return previous;
    }

    public void setPrevious(List<Object> previous) {
        this.previous = previous;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
    }

    public boolean isAddOperation() {
        return OPERATION_ADD.equals(operation);
    }

    public boolean isRemoveOperation() {
        return OPERATION_REMOVE.equals(operation);
    }

    public boolean isReplaceOperation() {
        return OPERATION_REPLACE.equals(operation) || operation == null;
    }

    /**
     * 创建替换操作
     */
    public static UpdateInput replace(String key, List<Object> value) {
        return new UpdateInput(key, value, OPERATION_REPLACE);
    }

    /**
     * 创建添加操作
     */
    public static UpdateInput add(String key, List<Object> value) {
        return new UpdateInput(key, value, OPERATION_ADD);
    }

    /**
     * 创建移除操作
     */
    public static UpdateInput remove(String key, List<Object> value) {
        return new UpdateInput(key, value, OPERATION_REMOVE);
    }

    /**
     * 创建带前值的更新
     */
    public static UpdateInput withPrevious(String key, List<Object> value, List<Object> previous) {
        UpdateInput input = new UpdateInput(key, value);
        input.setPrevious(previous);
        return input;
    }
}
