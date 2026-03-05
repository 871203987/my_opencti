package io.opencti.database.middleware.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除选项
 * 原文件: middleware.js:3441 opts 参数
 * 
 * 封装删除操作的选项配置。
 */
public class DeleteOptions {

    private boolean forceDelete;
    private boolean forceRefresh;
    private boolean restore;
    private List<String> references;
    private String commitMessage;
    private List<String> locks;

    public DeleteOptions() {
        this.forceDelete = false;
        this.forceRefresh = true;
        this.restore = false;
        this.references = new ArrayList<>();
        this.locks = new ArrayList<>();
    }

    public boolean isForceDelete() {
        return forceDelete;
    }

    public void setForceDelete(boolean forceDelete) {
        this.forceDelete = forceDelete;
    }

    public boolean isForceRefresh() {
        return forceRefresh;
    }

    public void setForceRefresh(boolean forceRefresh) {
        this.forceRefresh = forceRefresh;
    }

    public boolean isRestore() {
        return restore;
    }

    public void setRestore(boolean restore) {
        this.restore = restore;
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

    /**
     * 创建默认删除选项
     */
    public static DeleteOptions defaults() {
        return new DeleteOptions();
    }

    /**
     * 创建强制删除选项
     */
    public static DeleteOptions forceDelete() {
        DeleteOptions options = new DeleteOptions();
        options.setForceDelete(true);
        return options;
    }

    /**
     * 创建恢复选项
     */
    public static DeleteOptions restore() {
        DeleteOptions options = new DeleteOptions();
        options.setRestore(true);
        return options;
    }

    /**
     * 创建带引用的删除选项
     */
    public static DeleteOptions withReferences(List<String> references, String commitMessage) {
        DeleteOptions options = new DeleteOptions();
        options.setReferences(references);
        options.setCommitMessage(commitMessage);
        return options;
    }
}
