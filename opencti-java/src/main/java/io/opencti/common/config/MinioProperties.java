package io.opencti.common.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * 重写自: opencti-graphql/src/config/conf.js
 * MinIO文件存储配置属性
 */
public record MinioProperties(
    @NotBlank String endpoint,
    @Min(1) int port,
    boolean useSsl,
    @NotBlank String accessKey,
    @NotBlank String secretKey,
    @NotBlank String bucketName,
    String region,
    @Min(1) int connectionTimeout,
    @Min(1) int writeTimeout,
    @Min(1) int readTimeout,
    boolean pathStyleAccess,
    @Min(1) int maxConnections,
    String endpointExternal,
    boolean bucketCreate
) {}
