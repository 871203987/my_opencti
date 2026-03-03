package io.opencti.common.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 重写自: opencti-platform/opencti-graphql/config/default.json
 * MinIO文件存储配置属性
 * 
 * 原始配置项:
 * - bucket_name: 存储桶名称
 * - bucket_region: 存储桶区域
 * - endpoint: 端点地址
 * - port: 端口
 * - use_ssl: 是否使用SSL
 * - access_key: 访问密钥
 * - secret_key: 秘密密钥
 * - use_aws_role: 是否使用AWS角色
 * - excluded_files: 排除文件列表
 * - disable_checksum_validation: 禁用校验验证
 */
public record MinioProperties(
    @NotBlank String endpoint,
    @Min(1) int port,
    boolean useSsl,
    @NotBlank String accessKey,
    @NotBlank String secretKey,
    @NotBlank String bucketName,
    String bucketRegion,
    @Min(1) int connectionTimeout,
    @Min(1) int writeTimeout,
    @Min(1) int readTimeout,
    boolean pathStyleAccess,
    @Min(1) int maxConnections,
    String endpointExternal,
    boolean bucketCreate,
    // 新增配置项（与 TypeScript 源码一致）
    boolean useAwsRole,
    List<String> excludedFiles,
    boolean disableChecksumValidation
) {
    /**
     * 获取排除文件列表
     * 默认值: [".DS_Store"]
     */
    public List<String> getExcludedFiles() {
        return excludedFiles != null ? excludedFiles : List.of(".DS_Store");
    }
}
