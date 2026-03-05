package io.opencti.database.middleware.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建选项
 * 原文件: middleware.js 创建操作选项参数
 * 
 * 封装创建操作的选项配置。
 */
public class CreateOptions {

    private boolean bypassValidation;
    private boolean impactStandardId;
    private boolean complete;
    private boolean restore;
    private String fromRule;
    private List<String> references;
    private String commitMessage;
    private List<String> locks;

    public CreateOptions() {
        this.bypassValidation = false;
        this.impactStandardId = true;
        this.complete = false;
        this.restore = false;
        this.locks = new ArrayList<>();
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

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isRestore() {
        return restore;
    }

    public void setRestore(boolean restore) {
        this.restore = restore;
    }

    public String getFromRule() {
        return fromRule;
    }

    public void setFromRule(String fromRule) {
        this.fromRule = fromRule;
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

    public boolean isFromRule() {
        return fromRule != null && !fromRule.isEmpty();
    }

    /**
     * 创建默认选项
     */
    public static CreateOptions defaults() {
        return new CreateOptions();
    }

    /**
     * 创建跳过验证选项
     */
    public static CreateOptions bypassValidation() {
        CreateOptions options = new CreateOptions();
        options.setBypassValidation(true);
        return options;
    }

    /**
     * 创建完整结果选项
     */
    public static CreateOptions complete() {
        CreateOptions options = new CreateOptions();
        options.setComplete(true);
        return options;
    }

    /**
     * 创建恢复选项
     */
    public static CreateOptions restore() {
        CreateOptions options = new CreateOptions();
        options.setRestore(true);
        return options;
    }

    /**
     * 创建推理规则选项
     */
    public static CreateOptions fromRule(String fromRule) {
        CreateOptions options = new CreateOptions();
        options.setFromRule(fromRule);
        options.setBypassValidation(true);
        options.setImpactStandardId(false);
        return options;
    }

    /**
     * 创建带引用的创建选项
     */
    public static CreateOptions withReferences(List<String> references, String commitMessage) {
        CreateOptions options = new CreateOptions();
        options.setReferences(references);
        options.setCommitMessage(commitMessage);
        return options;
    }
}
