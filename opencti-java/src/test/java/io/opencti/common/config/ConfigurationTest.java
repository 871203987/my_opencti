package io.opencti.common.config;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 重写自: opencti-graphql/tests/01-unit/config/test_conf.js
 * 配置属性测试
 */
class ConfigurationTest {

    @Test
    void testAppPropertiesRecord() {
        AppProperties.AppLogsProperties appLogs = new AppProperties.AppLogsProperties(
            "info", true, true, 7, "./logs",
            List.of("password", "secret", "token"), false
        );
        
        AppProperties.SessionProperties session = new AppProperties.SessionProperties(
            "test-secret", 28800000, 1, "Strict", 0
        );
        
        AppProperties app = new AppProperties(
            4000, "", "http://localhost:4000/", "OpenCTI", "Platform",
            "OpenCTI Platform", "/static/favicon.ico", "", false, false,
            "6.9.5", "admin@opencti.io", "password", "token", true,
            "auto", false, "TLP:CLEAR", false, true, true,
            "https://map.opencti.io/dark/{z}/{x}/{y}.png",
            "https://map.opencti.io/light/{z}/{x}/{y}.png",
            false, false, "default", "", false, "https://hub.filigran.io",
            false, "", false, false, false, "", "",
            appLogs, null, session, null, null, null, null, null, null
        );
        
        assertEquals(4000, app.port());
        assertEquals("OpenCTI", app.title());
        assertNotNull(app.appLogs());
        assertEquals("info", app.appLogs().logsLevel());
    }

    @Test
    void testElasticsearchPropertiesRecord() {
        ElasticsearchProperties es = new ElasticsearchProperties(
            "http://localhost:9200", "", "", "opencti",
            List.of("http://localhost:9200"), 5, 120000L, 120000L,
            60000L, 5000, 4, 4, 5000, 5000, 10,
            true, "", "", "", false, "auto",
            10, 5000,
            // 新增配置项
            true, "-000001", false, false, 5000
        );
        
        assertEquals("http://localhost:9200", es.url());
        assertEquals("opencti", es.indexPrefix());
        assertTrue(es.engineCheck());
        assertEquals("-000001", es.getIndexCreationPattern());
    }

    @Test
    void testRedisPropertiesRecord() {
        RedisProperties redis = new RedisProperties(
            "single", "", "localhost", 6379, null,
            "", "", 0, false, null,
            "", "", "", 5, 10000, 30000,
            3, "", "", "", "",
            "", "", null, null, null, "",
            100, 20, 5, 100, 10, 10000,
            2000000L, "all", null, 4
        );
        
        assertEquals("localhost", redis.hostname());
        assertEquals(6379, redis.port());
    }

    @Test
    void testRabbitMQPropertiesRecord() {
        RabbitMQProperties rabbitmq = new RabbitMQProperties(
            "localhost", 5672, "guest", "guest", "/",
            false, null, 30000, 30, 10, 5, 5000,
            10, "opencti", "", 10, false,
            // 新增配置项
            15672, false
        );
        
        assertEquals("localhost", rabbitmq.hostname());
        assertEquals(5672, rabbitmq.port());
        assertEquals("guest", rabbitmq.username());
        assertEquals(15672, rabbitmq.getPortManagement());
    }

    @Test
    void testMinioPropertiesRecord() {
        MinioProperties minio = new MinioProperties(
            "localhost", 9000, false, "accessKey", "secretKey",
            "opencti", "us-east-1", 30000, 60000, 60000, true, 100, "", true,
            // 新增配置项
            false, List.of(".DS_Store"), false
        );
        
        assertEquals("localhost", minio.endpoint());
        assertEquals(9000, minio.port());
        assertEquals("opencti", minio.bucketName());
        assertEquals(List.of(".DS_Store"), minio.getExcludedFiles());
    }

    @Test
    void testTelemetryPropertiesRecord() {
        TelemetryProperties.MetricsProperties metrics = new TelemetryProperties.MetricsProperties(
            false, "", 14269, false
        );
        
        TelemetryProperties.TracingProperties tracing = new TelemetryProperties.TracingProperties(
            false, "", "", 1.0
        );
        
        TelemetryProperties telemetry = new TelemetryProperties(
            false, "", "", "opencti", "6.9.5", 1.0, true, metrics, tracing
        );
        
        assertFalse(telemetry.enabled());
        assertEquals("opencti", telemetry.serviceName());
    }

    @Test
    void testProvidersPropertiesRecord() {
        ProvidersProperties.AuthProvider provider = new ProvidersProperties.AuthProvider(
            "local", "local", "local", "Local", true, Map.of(), Map.of()
        );
        
        ProvidersProperties providers = new ProvidersProperties(
            List.of(provider), true
        );
        
        assertTrue(providers.localEnabled());
        assertEquals(1, providers.authProviders().size());
    }

    @Test
    void testProxyPropertiesRecord() {
        ProxyProperties proxy = new ProxyProperties(
            "", "", "", List.of(), true, 30000, 60000
        );
        
        assertFalse(proxy.hasHttpProxy());
        assertFalse(proxy.hasHttpsProxy());
    }

    @Test
    void testOpenCTIConfigurationRecord() {
        AppProperties.AppLogsProperties appLogs = new AppProperties.AppLogsProperties(
            "info", true, true, 7, "./logs", List.of(), false
        );
        
        AppProperties.SessionProperties session = new AppProperties.SessionProperties(
            "secret", 28800000, 1, "Strict", 0
        );
        
        AppProperties app = new AppProperties(
            4000, "", "http://localhost:4000/", "OpenCTI", "Platform",
            "OpenCTI Platform", "/static/favicon.ico", "", false, false,
            "6.9.5", "admin@opencti.io", "password", "token", true,
            "auto", false, "TLP:CLEAR", false, true, true,
            "", "", false, false, "default", "", false, "",
            false, "", false, false, false, "", "",
            appLogs, null, session, null, null, null, null, null, null
        );
        
        ElasticsearchProperties es = new ElasticsearchProperties(
            "http://localhost:9200", "", "", "opencti", List.of("http://localhost:9200"),
            5, 120000L, 120000L, 60000L, 5000, 4, 4, 5000, 5000, 10,
            true, "", "", "", false, "auto", 10, 5000,
            true, "-000001", false, false, 5000
        );
        
        RedisProperties redis = new RedisProperties(
            "single", "", "localhost", 6379, null,
            "", "", 0, false, null,
            "", "", "", 5, 10000, 30000,
            3, "", "", "", "",
            "", "", null, null, null, "",
            100, 20, 5, 100, 10, 10000,
            2000000L, "all", null, 4
        );
        
        RabbitMQProperties rabbitmq = new RabbitMQProperties(
            "localhost", 5672, "guest", "guest", "/",
            false, null, 30000, 30, 10, 5, 5000,
            10, "opencti", "", 10, false,
            15672, false
        );
        
        MinioProperties minio = new MinioProperties(
            "localhost", 9000, false, "accessKey", "secretKey",
            "opencti", "us-east-1", 30000, 60000, 60000, true, 100, "", true,
            false, List.of(".DS_Store"), false
        );
        
        TelemetryProperties.MetricsProperties metrics = new TelemetryProperties.MetricsProperties(
            false, "", 14269, false
        );
        
        TelemetryProperties.TracingProperties tracing = new TelemetryProperties.TracingProperties(
            false, "", "", 1.0
        );
        
        TelemetryProperties telemetry = new TelemetryProperties(
            false, "", "", "opencti", "6.9.5", 1.0, true, metrics, tracing
        );
        
        ProvidersProperties providers = new ProvidersProperties(List.of(), true);
        
        OpenCTIConfiguration config = new OpenCTIConfiguration(
            app, es, redis, rabbitmq, minio, telemetry, providers
        );
        
        assertNotNull(config);
        assertEquals(4000, config.app().port());
        assertEquals("http://localhost:9200", config.elasticsearch().url());
        assertEquals("localhost", config.redis().hostname());
    }
}
