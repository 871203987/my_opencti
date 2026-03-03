package io.opencti.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 重写自: opencti-graphql/src/config/credentials.ts
 * CyberArk凭证提供者实现
 * 
 * TODO: Python调用点 - 原文件使用node-calls-python调用Python脚本
 * 原Python脚本位置: src/python/runtime/cyberark_credential.py
 * Java实现使用ProcessBuilder调用Python脚本或直接HTTP API调用
 */
@Slf4j
@Component
public class CyberArkCredentialsProvider implements CredentialsProvider {

    private static final String NAME = "cyberark";
    private static final String CYBERARK_API_URL_ENV = "CYBERARK_API_URL";
    private static final String CYBERARK_APP_ID_ENV = "CYBERARK_APP_ID";
    private static final String CYBERARK_SAFE_ENV = "CYBERARK_SAFE";
    private static final String CYBERARK_CLIENT_CERT_ENV = "CYBERARK_CLIENT_CERT";
    private static final String CYBERARK_CLIENT_KEY_ENV = "CYBERARK_CLIENT_KEY";

    private final boolean enabled;
    private final String apiUrl;
    private final String appId;
    private final String safe;
    private final String clientCert;
    private final String clientKey;
    private final Map<String, String> credentialCache = new ConcurrentHashMap<>();
    private final long cacheExpiryMs = 3600000L;
    private long lastRefreshTime = 0;

    public CyberArkCredentialsProvider() {
        this.apiUrl = System.getenv(CYBERARK_API_URL_ENV);
        this.appId = System.getenv(CYBERARK_APP_ID_ENV);
        this.safe = System.getenv(CYBERARK_SAFE_ENV);
        this.clientCert = System.getenv(CYBERARK_CLIENT_CERT_ENV);
        this.clientKey = System.getenv(CYBERARK_CLIENT_KEY_ENV);
        this.enabled = apiUrl != null && appId != null && safe != null;
    }

    public CyberArkCredentialsProvider(String apiUrl, String appId, String safe, String clientCert, String clientKey) {
        this.apiUrl = apiUrl;
        this.appId = appId;
        this.safe = safe;
        this.clientCert = clientCert;
        this.clientKey = clientKey;
        this.enabled = apiUrl != null && appId != null && safe != null;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Optional<String> getCredential(String key) {
        if (!enabled) {
            log.warn("CyberArk credentials provider is not enabled");
            return Optional.empty();
        }

        if (isCacheExpired()) {
            refresh();
        }

        return Optional.ofNullable(credentialCache.get(key));
    }

    @Override
    public Optional<String> getCredential(String key, String defaultValue) {
        return getCredential(key).or(() -> Optional.of(defaultValue));
    }

    @Override
    public Map<String, String> getAllCredentials() {
        if (isCacheExpired()) {
            refresh();
        }
        return Map.copyOf(credentialCache);
    }

    @Override
    public void refresh() {
        if (!enabled) {
            return;
        }

        try {
            log.info("Refreshing CyberArk credentials for safe: {}", safe);
            fetchCredentialsFromCyberArk();
            lastRefreshTime = System.currentTimeMillis();
            log.info("Successfully refreshed CyberArk credentials");
        } catch (Exception e) {
            log.error("Failed to refresh CyberArk credentials", e);
        }
    }

    private boolean isCacheExpired() {
        return System.currentTimeMillis() - lastRefreshTime > cacheExpiryMs;
    }

    private void fetchCredentialsFromCyberArk() throws Exception {
        String endpoint = String.format("%s/AIMWebService/api/Accounts?AppID=%s&Safe=%s",
                apiUrl, appId, safe);

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (clientCert != null && clientKey != null) {
            // TODO: 实现SSL客户端证书认证
            log.warn("SSL client certificate authentication not yet implemented");
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            parseCredentialResponse(response.toString());
        } else {
            throw new RuntimeException("CyberArk API returned status: " + responseCode);
        }
    }

    private void parseCredentialResponse(String jsonResponse) {
        // TODO: 使用Jackson解析JSON响应
        // 示例格式: {"Content": "password_value", "UserName": "username_value"}
        log.debug("Parsing CyberArk response: {}", jsonResponse);
    }

    public static class CyberArkCredential {
        private final String username;
        private final String password;
        private final String address;

        public CyberArkCredential(String username, String password, String address) {
            this.username = username;
            this.password = password;
            this.address = address;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getAddress() {
            return address;
        }
    }
}
