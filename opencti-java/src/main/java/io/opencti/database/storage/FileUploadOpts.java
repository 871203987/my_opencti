package io.opencti.database.storage;

import java.util.List;

/**
 * 文件上传选项
 * 重写自: opencti-platform/opencti-graphql/src/database/file-storage.ts
 * 
 * 原始接口定义 (行651-658):
 * export interface FileUploadOpts {
 *   entity?: BasicStoreBase;
 *   meta?: Record<string, any>;
 *   noTriggerImport?: boolean;
 *   errorOnExisting?: boolean;
 *   file_markings?: string[];
 *   importContextEntities?: BasicStoreEntity[];
 * }
 */
public class FileUploadOpts {
    
    private Object entity;
    private java.util.Map<String, Object> meta;
    private boolean noTriggerImport;
    private boolean errorOnExisting;
    private List<String> fileMarkings;
    private List<Object> importContextEntities;

    public FileUploadOpts() {
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public java.util.Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(java.util.Map<String, Object> meta) {
        this.meta = meta;
    }

    public boolean isNoTriggerImport() {
        return noTriggerImport;
    }

    public void setNoTriggerImport(boolean noTriggerImport) {
        this.noTriggerImport = noTriggerImport;
    }

    public boolean isErrorOnExisting() {
        return errorOnExisting;
    }

    public void setErrorOnExisting(boolean errorOnExisting) {
        this.errorOnExisting = errorOnExisting;
    }

    public List<String> getFileMarkings() {
        return fileMarkings;
    }

    public void setFileMarkings(List<String> fileMarkings) {
        this.fileMarkings = fileMarkings;
    }

    public List<Object> getImportContextEntities() {
        return importContextEntities;
    }

    public void setImportContextEntities(List<Object> importContextEntities) {
        this.importContextEntities = importContextEntities;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Object entity;
        private java.util.Map<String, Object> meta;
        private boolean noTriggerImport;
        private boolean errorOnExisting;
        private List<String> fileMarkings;
        private List<Object> importContextEntities;

        public Builder entity(Object entity) {
            this.entity = entity;
            return this;
        }

        public Builder meta(java.util.Map<String, Object> meta) {
            this.meta = meta;
            return this;
        }

        public Builder noTriggerImport(boolean noTriggerImport) {
            this.noTriggerImport = noTriggerImport;
            return this;
        }

        public Builder errorOnExisting(boolean errorOnExisting) {
            this.errorOnExisting = errorOnExisting;
            return this;
        }

        public Builder fileMarkings(List<String> fileMarkings) {
            this.fileMarkings = fileMarkings;
            return this;
        }

        public Builder importContextEntities(List<Object> importContextEntities) {
            this.importContextEntities = importContextEntities;
            return this;
        }

        public FileUploadOpts build() {
            FileUploadOpts opts = new FileUploadOpts();
            opts.setEntity(entity);
            opts.setMeta(meta);
            opts.setNoTriggerImport(noTriggerImport);
            opts.setErrorOnExisting(errorOnExisting);
            opts.setFileMarkings(fileMarkings);
            opts.setImportContextEntities(importContextEntities);
            return opts;
        }
    }
}
