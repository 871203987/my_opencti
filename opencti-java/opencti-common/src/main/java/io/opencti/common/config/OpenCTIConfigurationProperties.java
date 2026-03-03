package io.opencti.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 重写自: opencti-graphql/src/config/conf.js
 * 配置类注册中心
 */
@Configuration
@EnableConfigurationProperties(OpenCTIConfiguration.class)
public class OpenCTIConfigurationProperties {}
