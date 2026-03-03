package io.opencti.database.storage;

import io.opencti.common.config.MinioProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储配置类
 * 重写自: opencti-platform/opencti-graphql/src/database/raw-file-storage.ts
 * 
 * 配置MinIO/S3客户端连接参数
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class FileStorageConfig {

    private final MinioProperties properties;

    public FileStorageConfig(MinioProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取端点URL
     * 重写自: raw-file-storage.ts - getEndpoint() (行59-65)
     * 
     * 如果使用AWS S3，返回null让SDK自动选择端点
     * 否则构建MinIO端点URL
     */
    public String getEndpoint() {
        if ("s3.amazonaws.com".equals(properties.endpoint())) {
            return null;
        }
        String protocol = properties.useSsl() ? "https" : "http";
        return String.format("%s://%s:%d", protocol, properties.endpoint(), properties.port());
    }

    /**
     * 获取区域
     */
    public String getRegion() {
        return properties.bucketRegion() != null ? properties.bucketRegion() : "us-east-1";
    }

    /**
     * 获取存储桶名称
     */
    public String getBucketName() {
        return properties.bucketName() != null ? properties.bucketName() : "opencti-bucket";
    }

    /**
     * 获取访问密钥
     */
    public String getAccessKey() {
        return properties.accessKey();
    }

    /**
     * 获取秘密密钥
     */
    public String getSecretKey() {
        return properties.secretKey();
    }

    /**
     * 是否使用SSL
     */
    public boolean isUseSsl() {
        return properties.useSsl();
    }

    /**
     * 是否使用AWS角色
     * 重写自: raw-file-storage.ts - buildCredentialProvider() (行36-57)
     */
    public boolean isUseAwsRole() {
        return properties.useAwsRole();
    }

    /**
     * 是否禁用校验验证
     */
    public boolean isDisableChecksumValidation() {
        return properties.disableChecksumValidation();
    }

    /**
     * 获取排除文件列表
     * 重写自: raw-file-storage.ts - excludedFiles (行42)
     */
    public java.util.List<String> getExcludedFiles() {
        return properties.getExcludedFiles();
    }

    /**
     * 获取连接超时时间
     */
    public int getConnectionTimeout() {
        return properties.connectionTimeout() > 0 ? properties.connectionTimeout() : 10000;
    }

    /**
     * 获取写入超时时间
     */
    public int getWriteTimeout() {
        return properties.writeTimeout() > 0 ? properties.writeTimeout() : 60000;
    }

    /**
     * 获取读取超时时间
     */
    public int getReadTimeout() {
        return properties.readTimeout() > 0 ? properties.readTimeout() : 60000;
    }

    /**
     * 是否使用路径样式访问
     */
    public boolean isPathStyleAccess() {
        return properties.pathStyleAccess();
    }

    /**
     * 获取最大连接数
     */
    public int getMaxConnections() {
        return properties.maxConnections() > 0 ? properties.maxConnections() : 100;
    }
}
