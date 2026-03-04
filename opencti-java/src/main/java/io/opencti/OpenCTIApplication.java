package io.opencti;

import io.opencti.common.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * OpenCTI Java 应用启动类
 * 重写自: opencti-platform/opencti-graphql/src/back.js
 * 
 * Spring Boot 主启动类，负责初始化整个应用
 */
@SpringBootApplication
@EnableConfigurationProperties({
        ElasticsearchProperties.class,
        RedisProperties.class,
        RabbitMQProperties.class,
        MinioProperties.class,
        AppProperties.class,
        ProvidersProperties.class,
        TelemetryProperties.class
})
@EnableAsync
public class OpenCTIApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenCTIApplication.class, args);
    }
}
