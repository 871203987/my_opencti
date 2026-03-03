package io.opencti.database.storage;

import java.util.Date;

/**
 * 加载的文件信息
 * 重写自: opencti-platform/opencti-graphql/src/database/file-storage.ts
 * 
 * 原始接口定义 (行76-86):
 * export interface LoadedFile {
 *   id: string;
 *   name: string;
 *   size: number | undefined;
 *   information: string;
 *   lastModified: Date;
 *   lastModifiedSinceMin: Date | number;
 *   metaData: FileMetadata;
 *   uploadStatus: string;
 *   internal_id?: string;
 * }
 */
public class LoadedFile {
    
    private String id;
    private String name;
    private Long size;
    private String information;
    private Date lastModified;
    private Object lastModifiedSinceMin;
    private FileMetadata metaData;
    private String uploadStatus;
    private String internalId;

    public LoadedFile() {
    }

    public LoadedFile(String id, String name, Long size, String information, 
                      Date lastModified, FileMetadata metaData, String uploadStatus) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.information = information;
        this.lastModified = lastModified;
        this.metaData = metaData;
        this.uploadStatus = uploadStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Object getLastModifiedSinceMin() {
        return lastModifiedSinceMin;
    }

    public void setLastModifiedSinceMin(Object lastModifiedSinceMin) {
        this.lastModifiedSinceMin = lastModifiedSinceMin;
    }

    public FileMetadata getMetaData() {
        return metaData;
    }

    public void setMetaData(FileMetadata metaData) {
        this.metaData = metaData;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }
}
