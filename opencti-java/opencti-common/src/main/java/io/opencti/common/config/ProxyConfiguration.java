package io.opencti.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * 重写自: opencti-graphql/src/config/proxy-config.ts
 * HTTP代理配置类
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ProxyProperties.class)
public class ProxyConfiguration {

    private final ProxyProperties properties;

    public ProxyConfiguration(ProxyProperties properties) {
        this.properties = properties;
    }

    public Proxy getHttpProxyObject() {
        if (!properties.hasHttpProxy()) {
            return Proxy.NO_PROXY;
        }
        return createProxy(properties.httpProxy());
    }

    public Proxy getHttpsProxyObject() {
        if (!properties.hasHttpsProxy()) {
            return Proxy.NO_PROXY;
        }
        return createProxy(properties.httpsProxy());
    }

    private Proxy createProxy(String proxyUrl) {
        try {
        String host = proxyUrl;
        int port = 8080;

        if (proxyUrl.contains("://")) {
            host = proxyUrl.split("://")[1];
        }

        if (host.contains(":")) {
            String[] parts = host.split(":");
                host = parts[0];
                port = Integer.parseInt(parts[1]);
            }

            return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        } catch (Exception e) {
            log.error("Failed to parse proxy URL: {}", proxyUrl, e);
            return Proxy.NO_PROXY;
        }
    }

    public boolean shouldBypassProxy(String targetHost) {
        String noProxy = properties.noProxy();
        if (noProxy == null || noProxy.isEmpty()) {
            return false;
        }

        String[] noProxyHosts = noProxy.split(",");
        for (String noProxyHost : noProxyHosts) {
            String trimmedHost = noProxyHost.trim();
            if (trimmedHost.isEmpty()) {
                continue;
            }

            if (targetHost.equals(trimmedHost) || targetHost.endsWith("." + trimmedHost)) {
                return true;
            }

            if (trimmedHost.startsWith(".") && targetHost.endsWith(trimmedHost)) {
                return true;
            }
        }

        return false;
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(properties.connectTimeout());
        factory.setReadTimeout(properties.readTimeout());

        if (properties.hasHttpsProxy()) {
            factory.setProxy(getHttpsProxyObject());
            log.info("RestTemplate configured with HTTPS proxy: {}", properties.httpsProxy());
        } else if (properties.hasHttpProxy()) {
            factory.setProxy(getHttpProxyObject());
            log.info("RestTemplate configured with HTTP proxy: {}", properties.httpProxy());
        }

        return new RestTemplate(factory);
    }
}
