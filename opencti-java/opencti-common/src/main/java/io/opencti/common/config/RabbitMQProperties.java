package io.opencti.common.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * 重写自: opencti-graphql/src/config/conf.js
 * RabbitMQ配置属性
 */
public record RabbitMQProperties(
    @NotBlank String hostname,
    @Min(1) int port,
    String username,
    String password,
    String vhost,
    boolean useSsl,
    String ca,
    String cert,
    String key,
    String keyPassword,
    boolean verifyPeer,
    @Min(1) int connectionTimeout,
    @Min(1) int heartbeatInterval,
    @Min(1) int prefetchCount,
    @Min(1) int maxRetries,
    @Min(1) int retryDelay,
    @Min(1) int queueMaxPriority,
    String exchangeName,
    String queuePrefix,
    @Min(1) int consumerConcurrency,
    boolean useQuorumQueues
) {}
