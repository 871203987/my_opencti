package io.opencti.database.middleware.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据构建结果
 * 原文件: middleware.js buildEntityData/buildRelationData 返回值
 * 
 * 表示实体或关系构建后的数据结构，包含元素和关联关系。
 */
public class BuildDataResult {

    private Map<String, Object> element;
    private List<Map<String, Object>> relations;

    public BuildDataResult() {
        this.element = new HashMap<>();
        this.relations = new ArrayList<>();
    }

    public Map<String, Object> getElement() {
        return element;
    }

    public void setElement(Map<String, Object> element) {
        this.element = element;
    }

    public List<Map<String, Object>> getRelations() {
        return relations;
    }

    public void setRelations(List<Map<String, Object>> relations) {
        this.relations = relations;
    }

    public void addRelation(Map<String, Object> relation) {
        this.relations.add(relation);
    }

    public void addRelations(List<Map<String, Object>> relations) {
        this.relations.addAll(relations);
    }

    public boolean hasRelations() {
        return !relations.isEmpty();
    }

    public Object getElementProperty(String key) {
        return element.get(key);
    }

    public void setElementProperty(String key, Object value) {
        element.put(key, value);
    }

    /**
     * 创建实体构建结果
     */
    public static BuildDataResult ofEntity(Map<String, Object> element) {
        BuildDataResult result = new BuildDataResult();
        result.setElement(element);
        return result;
    }

    /**
     * 创建带关系的构建结果
     */
    public static BuildDataResult of(Map<String, Object> element, List<Map<String, Object>> relations) {
        BuildDataResult result = new BuildDataResult();
        result.setElement(element);
        result.setRelations(relations);
        return result;
    }
}
