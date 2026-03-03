package io.opencti.database.elasticsearch;

import io.opencti.common.config.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Elasticsearch配置类
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 使用已有的ElasticsearchProperties配置属性
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchConfig {

    private final ElasticsearchProperties properties;

    public ElasticsearchConfig(ElasticsearchProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取ES连接URL
     * 重写自: engine.ts - searchEngineInit (行362)
     */
    public String getUrl() {
        return properties.url();
    }

    /**
     * 获取索引前缀
     * 重写自: utils.ts - ES_INDEX_PREFIX (行24)
     */
    public String getIndexPrefix() {
        String prefix = properties.indexPrefix();
        return prefix != null && !prefix.isEmpty() ? prefix : "opencti";
    }

    /**
     * 获取用户名
     */
    public String getUsername() {
        return properties.username();
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return properties.password();
    }

    /**
     * 获取最大重试次数
     */
    public int getMaxRetries() {
        return properties.maxRetries() > 0 ? properties.maxRetries() : 3;
    }

    /**
     * 获取请求超时时间
     */
    public long getRequestTimeout() {
        return properties.requestTimeout() > 0 ? properties.requestTimeout() : 3600000L;
    }

    /**
     * 获取引擎选择器
     * 重写自: engine.ts - engineSelector (行398)
     */
    public String getEngineSelector() {
        String selector = properties.engineSelector();
        return selector != null && !selector.isEmpty() ? selector : "auto";
    }

    /**
     * 是否检查引擎类型
     * 重写自: engine.ts - engineCheck (行399)
     */
    public boolean isEngineCheck() {
        return properties.engineCheck();
    }

    /**
     * 获取索引创建模式
     * 重写自: engine.ts - ES_INDEX_PATTERN_SUFFIX (行212)
     */
    public String getIndexCreationPattern() {
        String pattern = properties.getIndexCreationPattern();
        return pattern != null && !pattern.isEmpty() ? pattern : "-000001";
    }

    /**
     * 是否启用通配符前缀搜索
     * 重写自: engine.ts - ES_DEFAULT_WILDCARD_PREFIX (行200)
     */
    public boolean isSearchWildcardPrefix() {
        return properties.searchWildcardPrefix();
    }

    /**
     * 是否启用模糊搜索
     * 重写自: engine.ts - ES_DEFAULT_FUZZY (行201)
     */
    public boolean isSearchFuzzy() {
        return properties.searchFuzzy();
    }

    /**
     * 获取最大结果窗口
     * 重写自: engine.ts - ES_MAX_RESULT_WINDOW (行213)
     */
    public int getMaxResultWindow() {
        return properties.maxResultWindow() > 0 ? properties.maxResultWindow() : 100000;
    }

    /**
     * 获取最大运行时解析数
     * 重写自: engine.ts - MAX_RUNTIME_RESOLUTION_SIZE (行209)
     */
    public int getMaxRuntimeResolutions() {
        return properties.maxRuntimeResolutions() > 0 ? properties.maxRuntimeResolutions() : 5000;
    }

    /**
     * 获取滚动持续时间
     */
    public long getScrollDuration() {
        return properties.scrollDuration() > 0 ? properties.scrollDuration() : 60000L;
    }

    /**
     * 获取滚动大小
     */
    public int getScrollSize() {
        return properties.scrollSize() > 0 ? properties.scrollSize() : 1000;
    }

    /**
     * 获取批量最大大小
     */
    public int getBulkMaxSize() {
        return properties.bulkMaxSize() > 0 ? properties.bulkMaxSize() : 5000;
    }

    /**
     * 是否SSL验证
     */
    public boolean isSslVerification() {
        return properties.sslVerification();
    }

    /**
     * 获取SSL CA证书
     */
    public String getSslCa() {
        return properties.sslCa();
    }

    /**
     * 获取最大并发搜索数
     */
    public int getMaxConcurrentSearches() {
        return properties.maxConcurrentSearches() > 0 ? properties.maxConcurrentSearches() : 10;
    }
}
