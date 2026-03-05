package io.opencti.database.middleware.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 变更记录模型
 * 原文件: middleware.js:2088-2158 buildChanges 返回值
 * 
 * 表示属性变更的详细信息，用于事件流和审计日志。
 */
public class ChangeRecord {

    private String field;
    private List<Object> previous;
    private List<Object> newValue;
    private List<Object> added;
    private List<Object> removed;

    public ChangeRecord() {
        this.previous = new ArrayList<>();
        this.newValue = new ArrayList<>();
        this.added = new ArrayList<>();
        this.removed = new ArrayList<>();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<Object> getPrevious() {
        return previous;
    }

    public void setPrevious(List<Object> previous) {
        this.previous = previous;
    }

    public List<Object> getNewValue() {
        return newValue;
    }

    public void setNewValue(List<Object> newValue) {
        this.newValue = newValue;
    }

    public List<Object> getAdded() {
        return added;
    }

    public void setAdded(List<Object> added) {
        this.added = added;
    }

    public List<Object> getRemoved() {
        return removed;
    }

    public void setRemoved(List<Object> removed) {
        this.removed = removed;
    }

    public boolean hasChanges() {
        return !added.isEmpty() || !removed.isEmpty() || 
               (previous != null && !previous.equals(newValue));
    }

    /**
     * 创建简单变更记录
     */
    public static ChangeRecord of(String field, List<Object> previous, List<Object> newValue) {
        ChangeRecord record = new ChangeRecord();
        record.setField(field);
        record.setPrevious(previous);
        record.setNewValue(newValue);
        return record;
    }

    /**
     * 创建增量变更记录
     */
    public static ChangeRecord ofIncremental(String field, List<Object> previous, 
            List<Object> newValue, List<Object> added, List<Object> removed) {
        ChangeRecord record = new ChangeRecord();
        record.setField(field);
        record.setPrevious(previous);
        record.setNewValue(newValue);
        record.setAdded(added);
        record.setRemoved(removed);
        return record;
    }
}
