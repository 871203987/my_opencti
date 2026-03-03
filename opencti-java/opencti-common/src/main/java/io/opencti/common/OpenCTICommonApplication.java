package io.opencti.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import io.opencti.common.config.OpenCTIConfiguration;

/**
 * 重写自: opencti-graphql/src/primary.js
 * OpenCTI Common模块启动类
 */
@SpringBootApplication
@EnableConfigurationProperties(OpenCTIConfiguration.class)
public class OpenCTICommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenCTICommonApplication.class, args);
    }
}
