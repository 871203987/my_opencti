package io.opencti.database.redis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SslOptions;
import io.opencti.common.config.RedisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis configuration.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original functions: redisOptions, clusterOptions, sentinelOptions, createRedisClient
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

    private final RedisProperties properties;

    public RedisConfig(RedisProperties properties) {
        this.properties = properties;
    }

    /**
     * Create Redis URI for single mode.
     * Original: redisOptions function
     */
    public RedisURI createRedisUri(String provider) {
        RedisURI.Builder builder = RedisURI.builder()
                .withHost(properties.hostname())
                .withPort(properties.port())
                .withDatabase(properties.database())
                .withTimeout(Duration.ofSeconds(10));

        if (properties.username() != null && !properties.username().isEmpty()) {
            builder.withAuthentication(properties.username(), properties.password() != null ? properties.password() : "");
        } else if (properties.password() != null && !properties.password().isEmpty()) {
            builder.withPassword(properties.password().toCharArray());
        }

        if (Boolean.TRUE.equals(properties.useSsl())) {
            builder.withSsl(true);
            builder.withVerifyPeer(false);
        }

        builder.withClientName(buildConnectionName(provider));
        return builder.build();
    }

    /**
     * Create cluster URIs for cluster mode.
     * Original: generateClusterNodes function
     */
    public List<RedisURI> createClusterUris(String provider) {
        List<RedisURI> uris = new ArrayList<>();
        List<String> hostnames = properties.hostnames();
        if (hostnames == null || hostnames.isEmpty()) {
            uris.add(createRedisUri(provider));
            return uris;
        }

        for (String hostname : hostnames) {
            String[] parts = hostname.split(":");
            String host = parts[0];
            int port = parts.length > 1 ? Integer.parseInt(parts[1]) : properties.port();

            RedisURI.Builder builder = RedisURI.builder()
                    .withHost(host)
                    .withPort(port)
                    .withTimeout(Duration.ofSeconds(10));

            if (properties.username() != null && !properties.username().isEmpty()) {
                builder.withAuthentication(properties.username(), properties.password() != null ? properties.password() : "");
            } else if (properties.password() != null && !properties.password().isEmpty()) {
                builder.withPassword(properties.password().toCharArray());
            }

            if (Boolean.TRUE.equals(properties.useSsl())) {
                builder.withSsl(true);
                builder.withVerifyPeer(false);
            }

            builder.withClientName(buildConnectionName(provider));
            uris.add(builder.build());
        }
        return uris;
    }

    /**
     * Build connection name.
     * Original: connectionName function
     */
    private String buildConnectionName(String provider) {
        String prefix = properties.keyPrefix() != null ? properties.keyPrefix() : "opencti:";
        return prefix + provider.replaceAll(" ", "_");
    }

    /**
     * Create SSL options from CA certificates.
     * Original: configureCA function
     */
    private SslOptions createSslOptions() {
        if (properties.ca() == null || properties.ca().isEmpty()) {
            return SslOptions.create();
        }

        try {
            SslOptions.Builder builder = SslOptions.builder();
            for (String caPath : properties.ca()) {
                File caFile = new File(caPath);
                if (caFile.exists()) {
                    builder.trustManager(caFile);
                }
            }
            return builder.build();
        } catch (Exception e) {
            log.warn("[REDIS] Failed to load CA certificates: {}", e.getMessage());
            return SslOptions.create();
        }
    }

    /**
     * Create client options.
     * Original: redisOptions.retryStrategy
     */
    public ClientOptions createClientOptions(boolean autoReconnect) {
        ClientOptions.Builder builder = ClientOptions.builder()
                .autoReconnect(autoReconnect)
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS);

        if (Boolean.TRUE.equals(properties.useSsl())) {
            builder.sslOptions(createSslOptions());
        }

        return builder.build();
    }

    /**
     * Get Redis properties.
     */
    public RedisProperties getProperties() {
        return properties;
    }
}
