package io.opencti.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 重写自: opencti-graphql/src/config/proxy-config.ts
 * HTTP代理配置属性
 */
@ConfigurationProperties(prefix = "opencti.proxy")
public record ProxyProperties(
    String httpProxy,
    String httpsProxy,
    String noProxy,
    List<String> httpsProxyCa,
    boolean httpsProxyRejectUnauthorized,
    int connectTimeout,
    int readTimeout
) {
    public boolean hasHttpProxy() {
        return httpProxy != null && !httpProxy.isEmpty();
    }

    public boolean hasHttpsProxy() {
        return httpsProxy != null && !httpsProxy.isEmpty();
    }
}
