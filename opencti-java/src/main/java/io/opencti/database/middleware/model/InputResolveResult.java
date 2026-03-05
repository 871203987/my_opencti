package io.opencti.database.middleware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 输入引用解析结果
 * 原文件: middleware.js:782-988 inputResolveRefs 返回值
 * 
 * 封装输入解析后的结果，包含解析后的输入数据和未解析的ID列表。
 */
public class InputResolveResult {

    private Map<String, Object> resolvedInput;
    private Set<String> resolvedIds;
    private Set<String> unresolvedIds;
    private List<String> errors;

    public InputResolveResult() {
        this.errors = new ArrayList<>();
    }

    public Map<String, Object> getResolvedInput() {
        return resolvedInput;
    }

    public void setResolvedInput(Map<String, Object> resolvedInput) {
        this.resolvedInput = resolvedInput;
    }

    public Set<String> getResolvedIds() {
        return resolvedIds;
    }

    public void setResolvedIds(Set<String> resolvedIds) {
        this.resolvedIds = resolvedIds;
    }

    public Set<String> getUnresolvedIds() {
        return unresolvedIds;
    }

    public void setUnresolvedIds(Set<String> unresolvedIds) {
        this.unresolvedIds = unresolvedIds;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public boolean hasUnresolvedIds() {
        return unresolvedIds != null && !unresolvedIds.isEmpty();
    }

    /**
     * 创建成功的解析结果
     */
    public static InputResolveResult success(Map<String, Object> resolvedInput, Set<String> resolvedIds) {
        InputResolveResult result = new InputResolveResult();
        result.setResolvedInput(resolvedInput);
        result.setResolvedIds(resolvedIds);
        return result;
    }

    /**
     * 创建部分成功的解析结果
     */
    public static InputResolveResult partial(Map<String, Object> resolvedInput, 
            Set<String> resolvedIds, Set<String> unresolvedIds) {
        InputResolveResult result = new InputResolveResult();
        result.setResolvedInput(resolvedInput);
        result.setResolvedIds(resolvedIds);
        result.setUnresolvedIds(unresolvedIds);
        return result;
    }
}
