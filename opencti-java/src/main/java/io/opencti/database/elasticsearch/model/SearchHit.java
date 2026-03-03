package io.opencti.database.elasticsearch.model;

import java.util.Map;

/**
 * ES搜索命中结果
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 表示单个搜索结果文档
 */
public class SearchHit {

    private String id;
    private String index;
    private Double score;
    private Map<String, Object> source;
    private Map<String, Object> highlight;
    private Object[] sortValues;

    public SearchHit() {
    }

    public SearchHit(String id, String index, Double score, Map<String, Object> source) {
        this.id = id;
        this.index = index;
        this.score = score;
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }

    public Map<String, Object> getHighlight() {
        return highlight;
    }

    public void setHighlight(Map<String, Object> highlight) {
        this.highlight = highlight;
    }

    public Object[] getSortValues() {
        return sortValues;
    }

    public void setSortValues(Object[] sortValues) {
        this.sortValues = sortValues;
    }

    /**
     * 获取内部ID
     */
    public String getInternalId() {
        if (source != null && source.containsKey("internal_id")) {
            return String.valueOf(source.get("internal_id"));
        }
        return id;
    }

    /**
     * 获取实体类型
     */
    public String getEntityType() {
        if (source != null && source.containsKey("entity_type")) {
            return String.valueOf(source.get("entity_type"));
        }
        return null;
    }

    /**
     * 获取标准ID
     */
    public String getStandardId() {
        if (source != null && source.containsKey("standard_id")) {
            return String.valueOf(source.get("standard_id"));
        }
        return null;
    }
}
