package io.opencti.database.middleware.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 更新选项
 * 原文件: middleware.js 更新操作选项参数
 * 
 * 封装更新操作的选项配置。
 */
public class UpdateOptions {

    private boolean bypassValidation;
    private boolean impactStandardId;
    private boolean upsert;
    private boolean noEnrich;
    private boolean bypassIndividualUpdate;
    private boolean elementAlreadyResolved;
    private List<String> references;
    private String commitMessage;
    private List<String> locks;
    private Map<String, String> operations;

    public UpdateOptions() {
        this.bypassValidation = false;
        this.impactStandardId = true;
        this.upsert = false;
        this.noEnrich = false;
        this.bypassIndividualUpdate = false;
        this.elementAlreadyResolved = false;
        this.operations = new HashMap<>();
        this.locks = new java.util.ArrayList<>();
    }

    public boolean isBypassValidation() {
        return bypassValidation;
    }

    public void setBypassValidation(boolean bypassValidation) {
        this.bypassValidation = bypassValidation;
    }

    public boolean isImpactStandardId() {
        return impactStandardId;
    }

    public void setImpactStandardId(boolean impactStandardId) {
        this.impactStandardId = impactStandardId;
    }

    public boolean isUpsert() {
        return upsert;
    }

    public void setUpsert(boolean upsert) {
        this.upsert = upsert;
    }

    public boolean isNoEnrich() {
        return noEnrich;
    }

    public void setNoEnrich(boolean noEnrich) {
        this.noEnrich = noEnrich;
    }

    public boolean isBypassIndividualUpdate() {
        return bypassIndividualUpdate;
    }

    public void setBypassIndividualUpdate(boolean bypassIndividualUpdate) {
        this.bypassIndividualUpdate = bypassIndividualUpdate;
    }

    public boolean isElementAlreadyResolved() {
        return elementAlreadyResolved;
    }

    public void setElementAlreadyResolved(boolean elementAlreadyResolved) {
        this.elementAlreadyResolved = elementAlreadyResolved;
    }

    public List<String> getReferences() {
        return references;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public List<String> getLocks() {
        return locks;
    }

    public void setLocks(List<String> locks) {
        this.locks = locks;
    }

    public Map<String, String> getOperations() {
        return operations;
    }

    public void setOperations(Map<String, String> operations) {
        this.operations = operations;
    }

    public String getOperation(String key) {
        return operations.get(key);
    }

    public void setOperation(String key, String operation) {
        operations.put(key, operation);
    }

    /**
     * 创建默认选项
     */
    public static UpdateOptions defaults() {
        return new UpdateOptions();
    }

    /**
     * 创建跳过验证选项
     */
    public static UpdateOptions bypassValidation() {
        UpdateOptions options = new UpdateOptions();
        options.setBypassValidation(true);
        return options;
    }

    /**
     * 创建Upsert选项
     */
    public static UpdateOptions upsert() {
        UpdateOptions options = new UpdateOptions();
        options.setUpsert(true);
        return options;
    }

    /**
     * 创建带引用的更新选项
     */
    public static UpdateOptions withReferences(List<String> references, String commitMessage) {
        UpdateOptions options = new UpdateOptions();
        options.setReferences(references);
        options.setCommitMessage(commitMessage);
        return options;
    }
}
