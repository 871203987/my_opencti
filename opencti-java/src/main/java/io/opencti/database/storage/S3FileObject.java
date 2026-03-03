package io.opencti.database.storage;

/**
 * S3文件对象
 * 重写自: opencti-platform/opencti-graphql/src/database/file-storage.ts
 * 
 * 原始接口定义 (行93-96):
 * interface S3FileObject {
 *   Key: string;
 *   mimeType: string;
 * }
 */
public class S3FileObject {
    
    private String key;
    private String mimeType;

    public S3FileObject() {
    }

    public S3FileObject(String key, String mimeType) {
        this.key = key;
        this.mimeType = mimeType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
