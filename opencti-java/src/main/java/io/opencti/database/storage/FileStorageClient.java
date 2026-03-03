package io.opencti.database.storage;

import io.minio.ListObjectsArgs;
import io.minio.Result;
import io.minio.messages.Item;

import java.io.InputStream;
import java.util.List;

/**
 * 文件存储客户端接口
 * 重写自: opencti-platform/opencti-graphql/src/database/raw-file-storage.ts
 * 
 * 提供底层S3/MinIO存储操作，包括:
 * - 客户端初始化和存储桶管理
 * - 文件上传、下载、删除、复制
 * - 文件列表和元数据获取
 */
public interface FileStorageClient {

    /**
     * 初始化文件存储客户端
     * 重写自: raw-file-storage.ts - initializeFileStorageClient() (行67-81)
     * 
     * 创建S3客户端实例，配置凭证和连接参数
     */
    void initializeClient();

    /**
     * 初始化存储桶
     * 重写自: raw-file-storage.ts - initializeBucket() (行83-94)
     * 
     * 检查存储桶是否存在，不存在则创建
     * @return 存储桶是否可用
     */
    boolean initializeBucket();

    /**
     * 删除存储桶
     * 重写自: raw-file-storage.ts - deleteBucket() (行96-104)
     */
    void deleteBucket();

    /**
     * 初始化存储
     * 重写自: raw-file-storage.ts - storageInit() (行106-109)
     * 
     * 初始化客户端和存储桶
     */
    void storageInit();

    /**
     * 检查存储是否存活
     * 重写自: raw-file-storage.ts - isStorageAlive() (行111)
     * 
     * @return 存储是否可用
     */
    boolean isStorageAlive();

    /**
     * 从存储中删除文件
     * 重写自: raw-file-storage.ts - deleteFileFromStorage() (行113-118)
     * 
     * @param id 文件ID (S3 Key)
     */
    void deleteFileFromStorage(String id);

    /**
     * 下载文件
     * 重写自: raw-file-storage.ts - downloadFile() (行126-146)
     * 
     * @param id 文件ID (S3 Key)
     * @return 文件输入流，文件不存在时返回null
     */
    InputStream downloadFile(String id);

    /**
     * 流转字符串
     * 重写自: raw-file-storage.ts - streamToString() (行148-158)
     * 
     * @param stream 输入流
     * @param encoding 编码格式
     * @return 字符串内容
     */
    String streamToString(InputStream stream, String encoding);

    /**
     * 获取文件内容
     * 重写自: raw-file-storage.ts - getFileContent() (行160-169)
     * 
     * @param id 文件ID (S3 Key)
     * @param encoding 编码格式
     * @return 文件内容字符串
     */
    String getFileContent(String id, String encoding);

    /**
     * 复制文件
     * 重写自: raw-file-storage.ts - rawCopyFile() (行171-179)
     * 
     * @param sourceId 源文件ID
     * @param targetId 目标文件ID
     */
    void copyFile(String sourceId, String targetId);

    /**
     * 获取文件大小
     * 重写自: raw-file-storage.ts - getFileSize() (行184-194)
     * 
     * @param fileS3Path 文件S3路径
     * @return 文件大小(字节)
     */
    long getFileSize(String fileS3Path);

    /**
     * 上传文件
     * 重写自: raw-file-storage.ts - rawUpload() (行196-206)
     * 
     * @param key 文件键
     * @param body 文件内容流
     */
    void upload(String key, InputStream body);

    /**
     * 上传文件(字节数组)
     * 重写自: raw-file-storage.ts - rawUpload() (行196-206)
     * 
     * @param key 文件键
     * @param body 文件内容字节数组
     */
    void upload(String key, byte[] body);

    /**
     * 列出对象
     * 重写自: raw-file-storage.ts - rawListObjects() (行208-218)
     * 
     * @param directory 目录路径
     * @param recursive 是否递归
     * @return 对象列表
     */
    List<Item> listObjects(String directory, boolean recursive);

    /**
     * 列出对象(带分页)
     * 重写自: raw-file-storage.ts - rawListObjects() (行208-218)
     * 
     * @param directory 目录路径
     * @param recursive 是否递归
     * @param continuationToken 分页令牌
     * @return 对象列表结果
     */
    ListObjectsResult listObjects(String directory, boolean recursive, String continuationToken);

    /**
     * 获取存储桶名称
     * @return 存储桶名称
     */
    String getBucketName();

    /**
     * 列表对象结果
     */
    record ListObjectsResult(
        List<Item> contents,
        boolean isTruncated,
        String nextContinuationToken
    ) {}
}
