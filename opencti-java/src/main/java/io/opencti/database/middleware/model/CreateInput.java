package io.opencti.database.middleware.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建输入模型
 * 原文件: middleware.js 创建输入结构
 * 
 * 表示创建实体或关系时的输入数据。
 */
public class CreateInput {

    private String internalId;
    private String standardId;
    private String stixId;
    private String entityType;
    private String relationshipType;
    private String fromId;
    private String toId;
    private Map<String, Object> attributes;
    private boolean update;
    private Integer confidence;

    public CreateInput() {
        this.attributes = new HashMap<>();
        this.update = false;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getStixId() {
        return stixId;
    }

    public void setStixId(String stixId) {
        this.stixId = stixId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public List<Object> getAttributeAsList(String key) {
        Object value = attributes.get(key);
        if (value instanceof List) {
            return (List<Object>) value;
        }
        return null;
    }

    public String getAttributeAsString(String key) {
        Object value = attributes.get(key);
        return value != null ? value.toString() : null;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public boolean isRelation() {
        return fromId != null && toId != null;
    }

    public boolean isEntity() {
        return !isRelation();
    }

    /**
     * 创建实体输入
     */
    public static CreateInput forEntity(String entityType, Map<String, Object> attributes) {
        CreateInput input = new CreateInput();
        input.setEntityType(entityType);
        input.setAttributes(attributes);
        return input;
    }

    /**
     * 创建关系输入
     */
    public static CreateInput forRelation(String relationshipType, String fromId, String toId, 
            Map<String, Object> attributes) {
        CreateInput input = new CreateInput();
        input.setRelationshipType(relationshipType);
        input.setEntityType(relationshipType);
        input.setFromId(fromId);
        input.setToId(toId);
        if (attributes != null) {
            input.setAttributes(attributes);
        }
        return input;
    }
}
