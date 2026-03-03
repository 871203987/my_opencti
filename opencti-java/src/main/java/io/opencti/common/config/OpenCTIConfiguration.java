package io.opencti.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

/**
 * 重写自: opencti-graphql/src/config/conf.js
 * OpenCTI主配置类
 * 
 * 所有配置值从application.yml加载，不使用硬编码默认值
 */
@ConfigurationProperties(prefix = "opencti")
@Validated
public record OpenCTIConfiguration(
    @Valid AppProperties app,
    @Valid ElasticsearchProperties elasticsearch,
    @Valid RedisProperties redis,
    @Valid RabbitMQProperties rabbitmq,
    @Valid MinioProperties minio,
    @Valid TelemetryProperties telemetry,
    @Valid ProvidersProperties providers
) {}
