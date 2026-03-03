package io.opencti.database.storage;

import java.io.InputStream;

/**
 * 文件上传数据
 * 重写自: opencti-platform/opencti-graphql/src/database/file-storage.ts
 * 
 * 原始接口定义 (行660-664):
 * export interface FileUploadData {
 *   createReadStream: () => Readable;
 *   filename: string;
 *   mimeType?: string;
 * }
 */
public class FileUploadData {
    
    private InputStream stream;
    private String filename;
    private String mimeType;

    public FileUploadData() {
    }

    public FileUploadData(InputStream stream, String filename) {
        this.stream = stream;
        this.filename = filename;
    }

    public FileUploadData(InputStream stream, String filename, String mimeType) {
        this.stream = stream;
        this.filename = filename;
        this.mimeType = mimeType;
    }

    public InputStream createReadStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
