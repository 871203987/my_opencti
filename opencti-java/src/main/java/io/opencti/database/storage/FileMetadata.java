package io.opencti.database.storage;

import java.util.List;
import java.util.Map;

/**
 * 文件元数据
 * 重写自: opencti-platform/opencti-graphql/src/database/file-storage.ts
 * 
 * 原始接口定义 (行56-70):
 * interface FileMetadata {
 *   [key: string]: string | number | boolean | string[] | undefined;
 *   version?: string;
 *   mimetype?: string;
 *   encoding?: string;
 *   filename?: string;
 *   creator_id?: string;
 *   entity_id?: string;
 *   messages?: string[];
 *   errors?: string[];
 *   file_markings?: string[];
 *   order?: number;
 *   description?: string;
 *   inCarousel?: boolean;
 * }
 */
public class FileMetadata {
    
    private Map<String, Object> additionalProperties;
    private String version;
    private String mimetype;
    private String encoding;
    private String filename;
    private String creatorId;
    private String entityId;
    private List<String> messages;
    private List<String> errors;
    private List<String> fileMarkings;
    private Integer order;
    private String description;
    private Boolean inCarousel;

    public FileMetadata() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getFileMarkings() {
        return fileMarkings;
    }

    public void setFileMarkings(List<String> fileMarkings) {
        this.fileMarkings = fileMarkings;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInCarousel() {
        return inCarousel;
    }

    public void setInCarousel(Boolean inCarousel) {
        this.inCarousel = inCarousel;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public Object getAdditionalProperty(String key) {
        return additionalProperties != null ? additionalProperties.get(key) : null;
    }

    public void setAdditionalProperty(String key, Object value) {
        if (additionalProperties == null) {
            additionalProperties = new java.util.HashMap<>();
        }
        additionalProperties.put(key, value);
    }
}
