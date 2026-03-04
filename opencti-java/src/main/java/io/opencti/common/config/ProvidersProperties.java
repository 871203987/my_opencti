package io.opencti.common.config;

import jakarta.validation.Valid;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/providers-configuration.ts
 * 认证提供者配置属性
 */
@ConfigurationProperties(prefix = "opencti.providers")
public record ProvidersProperties(
    List<AuthProvider> authProviders,
    boolean localEnabled
) {
    public record AuthProvider(
        String name,
        String type,
        String identifier,
        String label,
        boolean enabled,
        Map<String, Object> config,
        Map<String, Object> mapping
    ) {}
}
