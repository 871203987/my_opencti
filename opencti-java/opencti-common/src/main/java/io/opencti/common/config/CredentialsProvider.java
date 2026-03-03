package io.opencti.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Optional;

/**
 * 重写自: opencti-graphql/src/config/credentials.ts
 * 凭证提供者接口
 */
public interface CredentialsProvider {

    String getName();

    boolean isEnabled();

    Optional<String> getCredential(String key);

    Optional<String> getCredential(String key, String defaultValue);

    Map<String, String> getAllCredentials();

    void refresh();

    default String requireCredential(String key) {
        return getCredential(key)
                .orElseThrow(() -> new IllegalStateException("Required credential not found: " + key));
    }
}
