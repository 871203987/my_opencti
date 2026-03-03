package io.opencti.database.storage;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件存储客户端实现类
 * 重写自: opencti-platform/opencti-graphql/src/database/raw-file-storage.ts
 * 
 * 使用MinIO Java SDK实现S3兼容存储操作
 */
@Component
public class FileStorageClientImpl implements FileStorageClient {

    private static final Logger log = LoggerFactory.getLogger(FileStorageClientImpl.class);

    private final FileStorageConfig config;
    private MinioClient minioClient;

    public FileStorageClientImpl(FileStorageConfig config) {
        this.config = config;
    }

    /**
     * 初始化文件存储客户端
     * 重写自: raw-file-storage.ts - initializeFileStorageClient() (行67-81)
     * 
     * 创建MinIO客户端实例，配置凭证和连接参数
     */
    @Override
    @PostConstruct
    public void initializeClient() {
        String endpoint = config.getEndpoint();
        String region = config.getRegion();
        String accessKey = config.getAccessKey();
        String secretKey = config.getSecretKey();
        boolean useSsl = config.isUseSsl();

        MinioClient.Builder builder = MinioClient.builder()
                .region(region)
                .credentials(accessKey, secretKey)
                .secure(useSsl);

        if (endpoint != null) {
            builder.endpoint(endpoint);
        }

        this.minioClient = builder.build();
        
        log.info("[FILE STORAGE] MinIO client initialized - endpoint: {}, bucket: {}, ssl: {}", 
                endpoint, config.getBucketName(), useSsl);
    }

    /**
     * 初始化存储桶
     * 重写自: raw-file-storage.ts - initializeBucket() (行83-94)
     * 
     * 检查存储桶是否存在，不存在则创建
     */
    @Override
    public boolean initializeBucket() {
        String bucketName = config.getBucketName();
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
                log.info("[FILE STORAGE] Bucket created: {}", bucketName);
            }
            return true;
        } catch (Exception e) {
            log.error("[FILE STORAGE] Failed to initialize bucket: {}", bucketName, e);
            throw new RuntimeException("Failed to initialize bucket", e);
        }
    }

    /**
     * 删除存储桶
     * 重写自: raw-file-storage.ts - deleteBucket() (行96-104)
     */
    @Override
    public void deleteBucket() {
        String bucketName = config.getBucketName();
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            log.info("[FILE STORAGE] Bucket deleted: {}", bucketName);
        } catch (Exception e) {
            log.info("[FILE STORAGE] Bucket cannot be deleted.", e);
        }
    }

    /**
     * 初始化存储
     * 重写自: raw-file-storage.ts - storageInit() (行106-109)
     * 
     * 初始化客户端和存储桶
     */
    @Override
    public void storageInit() {
        initializeClient();
        initializeBucket();
    }

    /**
     * 检查存储是否存活
     * 重写自: raw-file-storage.ts - isStorageAlive() (行111)
     */
    @Override
    public boolean isStorageAlive() {
        return initializeBucket();
    }

    /**
     * 从存储中删除文件
     * 重写自: raw-file-storage.ts - deleteFileFromStorage() (行113-118)
     */
    @Override
    public void deleteFileFromStorage(String id) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(id)
                    .build());
            log.debug("[FILE STORAGE] File deleted: {}", id);
        } catch (Exception e) {
            log.error("[FILE STORAGE] Failed to delete file: {}", id, e);
            throw new RuntimeException("Failed to delete file: " + id, e);
        }
    }

    /**
     * 下载文件
     * 重写自: raw-file-storage.ts - downloadFile() (行126-146)
     * 
     * @return 文件输入流，文件不存在时返回null
     */
    @Override
    public InputStream downloadFile(String id) {
        try {
            GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(id)
                    .build());
            return response;
        } catch (ErrorResponseException e) {
            if ("NoSuchKey".equals(e.errorResponse().code())) {
                log.debug("[FILE STORAGE] File not found: {}", id);
                return null;
            }
            log.error("[FILE STORAGE] Cannot retrieve file from S3: {}", id, e);
            throw new RuntimeException("Cannot retrieve file from S3: " + id, e);
        } catch (Exception e) {
            log.error("[FILE STORAGE] Cannot retrieve file from S3: {}", id, e);
            throw new RuntimeException("Cannot retrieve file from S3: " + id, e);
        }
    }

    /**
     * 流转字符串
     * 重写自: raw-file-storage.ts - streamToString() (行148-158)
     */
    @Override
    public String streamToString(InputStream stream, String encoding) {
        if (stream == null) {
            throw new IllegalArgumentException("Stream cannot be null");
        }
        try {
            byte[] bytes = stream.readAllBytes();
            return new String(bytes, Charset.forName(encoding));
        } catch (IOException e) {
            log.error("[FILE STORAGE] Failed to convert stream to string", e);
            throw new RuntimeException("Failed to convert stream to string", e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                log.debug("[FILE STORAGE] Failed to close stream", e);
            }
        }
    }

    /**
     * 获取文件内容
     * 重写自: raw-file-storage.ts - getFileContent() (行160-169)
     */
    @Override
    public String getFileContent(String id, String encoding) {
        InputStream stream = downloadFile(id);
        if (stream == null) {
            return null;
        }
        return streamToString(stream, encoding);
    }

    /**
     * 复制文件
     * 重写自: raw-file-storage.ts - rawCopyFile() (行171-179)
     */
    @Override
    public void copyFile(String sourceId, String targetId) {
        try {
            minioClient.copyObject(CopyObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(targetId)
                    .source(CopySource.builder()
                            .bucket(config.getBucketName())
                            .object(sourceId)
                            .build())
                    .build());
            log.debug("[FILE STORAGE] File copied from {} to {}", sourceId, targetId);
        } catch (Exception e) {
            log.error("[FILE STORAGE] Failed to copy file from {} to {}", sourceId, targetId, e);
            throw new RuntimeException("Failed to copy file", e);
        }
    }

    /**
     * 获取文件大小
     * 重写自: raw-file-storage.ts - getFileSize() (行184-194)
     */
    @Override
    public long getFileSize(String fileS3Path) {
        try {
            StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(fileS3Path)
                    .build());
            return stat.size();
        } catch (Exception e) {
            log.error("[FILE STORAGE] Failed to get file size: {}", fileS3Path, e);
            throw new RuntimeException("Load file from storage fail: " + fileS3Path, e);
        }
    }

    /**
     * 上传文件
     * 重写自: raw-file-storage.ts - rawUpload() (行196-206)
     */
    @Override
    public void upload(String key, InputStream body) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(key)
                    .stream(body, -1, 10485760)
                    .build());
            log.debug("[FILE STORAGE] File uploaded: {}", key);
        } catch (Exception e) {
            log.error("[FILE STORAGE] Failed to upload file: {}", key, e);
            throw new RuntimeException("Failed to upload file: " + key, e);
        } finally {
            try {
                body.close();
            } catch (IOException e) {
                log.debug("[FILE STORAGE] Failed to close upload stream", e);
            }
        }
    }

    /**
     * 上传文件(字节数组)
     * 重写自: raw-file-storage.ts - rawUpload() (行196-206)
     */
    @Override
    public void upload(String key, byte[] body) {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(body)) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(config.getBucketName())
                    .object(key)
                    .stream(stream, body.length, -1)
                    .build());
            log.debug("[FILE STORAGE] File uploaded: {}", key);
        } catch (Exception e) {
            log.error("[FILE STORAGE] Failed to upload file: {}", key, e);
            throw new RuntimeException("Failed to upload file: " + key, e);
        }
    }

    /**
     * 列出对象
     * 重写自: raw-file-storage.ts - rawListObjects() (行208-218)
     */
    @Override
    public List<Item> listObjects(String directory, boolean recursive) {
        ListObjectsResult result = listObjects(directory, recursive, null);
        return result.contents();
    }

    /**
     * 列出对象(带分页)
     * 重写自: raw-file-storage.ts - rawListObjects() (行208-218)
     */
    @Override
    public ListObjectsResult listObjects(String directory, boolean recursive, String continuationToken) {
        List<Item> items = new ArrayList<>();
        boolean isTruncated = false;
        String nextToken = null;

        try {
            Iterable<Result<Item>> results;
            if (continuationToken != null) {
                results = minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(config.getBucketName())
                        .prefix(directory)
                        .recursive(recursive)
                        .delimiter(recursive ? null : "/")
                        .startAfter(continuationToken)
                        .build());
            } else {
                results = minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(config.getBucketName())
                        .prefix(directory)
                        .recursive(recursive)
                        .delimiter(recursive ? null : "/")
                        .build());
            }

            for (Result<Item> result : results) {
                items.add(result.get());
            }

        } catch (Exception e) {
            log.error("[FILE STORAGE] Storage files read fail", e);
        }

        return new ListObjectsResult(items, isTruncated, nextToken);
    }

    /**
     * 获取存储桶名称
     */
    @Override
    public String getBucketName() {
        return config.getBucketName();
    }
}
