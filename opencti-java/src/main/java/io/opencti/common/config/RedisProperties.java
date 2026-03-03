package io.opencti.common.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * 重写自: opencti-graphql/src/config/conf.js
 * Redis配置属性
 */
public record RedisProperties(
    @NotBlank String hostname,
    @Min(1) int port,
    String username,
    String password,
    @Min(0) int database,
    boolean useSsl,
    String ca,
    String cert,
    String key,
    String keyPassword,
    @Min(1) int maxRetries,
    @Min(1) int connectionTimeout,
    @Min(1) int operationTimeout,
    String mode,
    String clusterNodes,
    @Min(1) int clusterMaxRedirects,
    String masterName,
    String sentinelHosts,
    String sentinelMasterName,
    String sentinelPassword,
    @Min(1) int poolMaxTotal,
    @Min(1) int poolMaxIdle,
    @Min(0) int poolMinIdle,
    @Min(1) int streamBatchSize,
    @Min(1) int streamMaxConsumers,
    @Min(1) int streamMaxPendingEntries
) {}
