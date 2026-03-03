package io.opencti.common.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 重写自: opencti-graphql/src/config/conf.js
 * Elasticsearch数据库配置属性
 */
public record ElasticsearchProperties(
    @NotBlank String url,
    String username,
    String password,
    @NotBlank String indexPrefix,
    List<String> urls,
    @Min(1) int maxRetries,
    @Min(1) long requestTimeout,
    @Min(1) long pingTimeout,
    @Min(1) long scrollDuration,
    @Min(1) int scrollSize,
    @Min(1) int maxConcurrentSearches,
    @Min(1) int maxConcurrentNormalizations,
    @Min(1) int bulkMaxSize,
    @Min(1) int bulkMaxTime,
    @Min(1) int bulkMaxRetry,
    boolean sslVerification,
    String sslCert,
    String sslKey,
    String sslCa,
    boolean useCurl,
    String engineSelector,
    @Min(1) int maxConnectionRetries,
    @Min(1) int maxResultWindow
) {}
