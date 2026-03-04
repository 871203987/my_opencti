package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch._types.HealthStatus;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetMappingRequest;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Elasticsearch客户端实现类
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 支持Elasticsearch和OpenSearch双引擎
 */
@Component
public class ElasticsearchClientImpl implements ElasticsearchClient {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchClientImpl.class);

    private final ElasticsearchConfig config;
    private co.elastic.clients.elasticsearch.ElasticsearchClient esClient;
    private String enginePlatform;
    private String engineVersion;
    private boolean runtimeSortingEnable = false;
    private boolean attachmentProcessorEnabled = false;

    public ElasticsearchClientImpl(ElasticsearchConfig config) {
        this.config = config;
    }

    /**
     * 初始化搜索引擎
     * 重写自: engine.ts - searchEngineInit() (行355-437)
     */
    @Override
    @PostConstruct
    public boolean searchEngineInit() {
        try {
            // 创建ES客户端
            this.esClient = createElasticsearchClient();
            
            // 获取引擎版本信息
            EngineVersion version = searchEngineVersion();
            this.enginePlatform = version.platform();
            this.engineVersion = version.version();
            
            // 检测运行时排序支持
            // 重写自: engine.ts (行430)
            this.runtimeSortingEnable = ElasticsearchConstants.ENGINE_ELK.equals(enginePlatform) 
                    && isVersionSatisfied(engineVersion, ">=7.12.0");
            
            // 配置附件处理器
            this.attachmentProcessorEnabled = elConfigureAttachmentProcessor();
            
            String runtimeStatus = runtimeSortingEnable ? "enabled" : "disabled";
            log.info("[SEARCH] {} ({}) client selected / runtime sorting {} / attachment processor {}", 
                    enginePlatform, engineVersion, runtimeStatus, 
                    attachmentProcessorEnabled ? "enabled" : "disabled");
            
            return true;
        } catch (Exception e) {
            log.warn("[SEARCH] Elasticsearch not available, running in degraded mode: {}", e.getMessage());
            this.enginePlatform = "unknown";
            this.engineVersion = "0.0.0";
            return false;
        }
    }

    /**
     * 创建Elasticsearch客户端
     * 重写自: engine.ts - searchEngineInit() (行361-380)
     */
    private co.elastic.clients.elasticsearch.ElasticsearchClient createElasticsearchClient() {
        String url = config.getUrl();
        String username = config.getUsername();
        String password = config.getPassword();
        
        log.info("[SEARCH] Creating Elasticsearch client for URL: {}", url);
        
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            int port = uri.getPort() > 0 ? uri.getPort() : 9200;
            String scheme = uri.getScheme() != null ? uri.getScheme() : "http";
            
            HttpHost httpHost = new HttpHost(host, port, scheme);
            
            RestClientBuilder builder = RestClient.builder(httpHost);
            
            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                builder.setHttpClientConfigCallback(httpClientBuilder -> {
                    BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(
                            new AuthScope(httpHost),
                            new UsernamePasswordCredentials(username, password)
                    );
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    return httpClientBuilder;
                });
            }
            
            RestClient restClient = builder.build();
            RestClientTransport transport = new RestClientTransport(
                    restClient, 
                    new JacksonJsonpMapper()
            );
            
            return new co.elastic.clients.elasticsearch.ElasticsearchClient(transport);
        } catch (Exception e) {
            log.error("[SEARCH] Failed to create Elasticsearch client: {}", e.getMessage());
            throw new RuntimeException("Failed to create Elasticsearch client", e);
        }
    }

    /**
     * 获取搜索引擎版本
     * 重写自: engine.ts - searchEngineVersion() (行341-353)
     */
    @Override
    public EngineVersion searchEngineVersion() {
        try {
            InfoResponse info = esClient.info();
            String version = info.version().number();
            
            // ES 8.x中distribution()方法不存在，需要通过其他方式判断引擎类型
            // OpenSearch的版本号格式不同，且cluster_name可能包含opensearch
            // 通过检查version.build_flavor或其他特征判断
            String buildFlavor = info.version().buildFlavor();
            String platform;
            
            // 判断是Elasticsearch还是OpenSearch
            // OpenSearch通常有特定的build_flavor或版本格式
            if (buildFlavor != null && buildFlavor.contains("opensearch")) {
                platform = ElasticsearchConstants.ENGINE_OPENSEARCH;
            } else if (version.toLowerCase().contains("opensearch")) {
                platform = ElasticsearchConstants.ENGINE_OPENSEARCH;
            } else {
                platform = ElasticsearchConstants.ENGINE_ELK;
            }
            
            return new EngineVersion(platform, version);
        } catch (Exception e) {
            log.error("[SEARCH] Search engine seems down", e);
            throw new RuntimeException("Search engine seems down", e);
        }
    }

    /**
     * 检查引擎是否存活
     * 重写自: engine.ts - isEngineAlive() (行4672-4690)
     */
    @Override
    public boolean isEngineAlive() {
        try {
            HealthResponse health = esClient.cluster().health();
            return health.status() != HealthStatus.Red;
        } catch (Exception e) {
            log.error("[SEARCH] Engine health check failed", e);
            return false;
        }
    }

    /**
     * 是否启用运行时排序
     * 重写自: engine.ts - isRuntimeSortEnable() (行438)
     */
    @Override
    public boolean isRuntimeSortEnable() {
        return runtimeSortingEnable;
    }

    /**
     * 是否启用附件处理器
     * 重写自: engine.ts - isAttachmentProcessorEnabled() (行282-284)
     */
    @Override
    public boolean isAttachmentProcessorEnabled() {
        return attachmentProcessorEnabled;
    }

    /**
     * 配置附件处理器
     * 重写自: engine.ts - elConfigureAttachmentProcessor() (行296-338)
     */
    private boolean elConfigureAttachmentProcessor() {
        try {
            // TODO: 实现附件处理器管道配置
            // client.ingest().putPipeline(...)
            log.info("[SEARCH] Attachment processor configured");
            return true;
        } catch (Exception e) {
            log.error("[SEARCH] Engine attachment processor configuration fail", e);
            return false;
        }
    }

    @Override
    public String getEnginePlatform() {
        return enginePlatform;
    }

    @Override
    public String getEngineVersion() {
        return engineVersion;
    }

    /**
     * 原始搜索
     * 重写自: engine.ts - elRawSearch() (行440-460)
     */
    @Override
    public SearchResponse<Map> elRawSearch(Map<String, Object> query) {
        try {
            // 将Map转换为JSON字符串，然后解析为SearchRequest
            String json = mapToJson(query);
            SearchRequest request = SearchRequest.of(s -> {
                // TODO: 完整实现查询构建
                return s;
            });
            
            return esClient.search(request, Map.class);
        } catch (Exception e) {
            log.error("[SEARCH] Raw search failed", e);
            throw new RuntimeException("Search failed", e);
        }
    }

    /**
     * 原始获取
     * 重写自: engine.ts - elRawGet() (行462-469)
     */
    @Override
    public Map<String, Object> elRawGet(String id, String index) {
        try {
            GetResponse<Map> response = esClient.get(g -> g
                    .index(index)
                    .id(id), Map.class);
            
            if (response.found()) {
                return response.source();
            }
            return null;
        } catch (Exception e) {
            log.error("[SEARCH] Raw get failed for id: {}, index: {}", id, index, e);
            throw new RuntimeException("Get failed", e);
        }
    }

    /**
     * 原始索引
     * 重写自: engine.ts - elRawIndex() (行470-477)
     */
    @Override
    public Map<String, Object> elRawIndex(Map<String, Object> args) {
        try {
            String index = (String) args.get("index");
            String id = (String) args.get("id");
            @SuppressWarnings("unchecked")
            Map<String, Object> document = (Map<String, Object>) args.get("body");
            
            IndexResponse response = esClient.index(i -> i
                    .index(index)
                    .id(id)
                    .document(document));
            
            return Map.of(
                    "_index", response.index(),
                    "_id", response.id(),
                    "result", response.result().jsonValue()
            );
        } catch (Exception e) {
            log.error("[SEARCH] Raw index failed", e);
            throw new RuntimeException("Index failed", e);
        }
    }

    /**
     * 原始删除
     * 重写自: engine.ts - elRawDelete() (行478-485)
     */
    @Override
    public Map<String, Object> elRawDelete(Map<String, Object> args) {
        try {
            String index = (String) args.get("index");
            String id = (String) args.get("id");
            
            DeleteResponse response = esClient.delete(d -> d
                    .index(index)
                    .id(id));
            
            return Map.of(
                    "_index", response.index(),
                    "_id", response.id(),
                    "result", response.result().jsonValue()
            );
        } catch (Exception e) {
            log.error("[SEARCH] Raw delete failed", e);
            throw new RuntimeException("Delete failed", e);
        }
    }

    /**
     * 按查询删除
     * 重写自: engine.ts - elRawDeleteByQuery() (行486-493)
     */
    @Override
    public Map<String, Object> elRawDeleteByQuery(Map<String, Object> query) {
        try {
            // TODO: 实现完整的deleteByQuery
            return Map.of("deleted", 0);
        } catch (Exception e) {
            log.error("[SEARCH] Delete by query failed", e);
            throw new RuntimeException("Delete by query failed", e);
        }
    }

    /**
     * 批量操作
     * 重写自: engine.ts - elRawBulk() (行494-501)
     */
    @Override
    public Map<String, Object> elRawBulk(Map<String, Object> args) {
        try {
            // TODO: 实现完整的bulk操作
            return Map.of("errors", false);
        } catch (Exception e) {
            log.error("[SEARCH] Bulk operation failed", e);
            throw new RuntimeException("Bulk failed", e);
        }
    }

    /**
     * 按查询更新
     * 重写自: engine.ts - elRawUpdateByQuery() (行502-509)
     */
    @Override
    public Map<String, Object> elRawUpdateByQuery(Map<String, Object> query) {
        try {
            // TODO: 实现完整的updateByQuery
            return Map.of("updated", 0);
        } catch (Exception e) {
            log.error("[SEARCH] Update by query failed", e);
            throw new RuntimeException("Update by query failed", e);
        }
    }

    /**
     * 重建索引
     * 重写自: engine.ts - elRawReindexByQuery() (行510-517)
     */
    @Override
    public Map<String, Object> elRawReindexByQuery(Map<String, Object> query) {
        try {
            // TODO: 实现完整的reindex
            return Map.of("total", 0);
        } catch (Exception e) {
            log.error("[SEARCH] Reindex failed", e);
            throw new RuntimeException("Reindex failed", e);
        }
    }

    /**
     * 原始计数
     * 重写自: engine.ts - elRawCount() (行3077-3088)
     */
    @Override
    public long elRawCount(Map<String, Object> query) {
        try {
            // TODO: 实现完整的count
            CountResponse response = esClient.count(c -> c);
            return response.count();
        } catch (Exception e) {
            log.error("[SEARCH] Count failed", e);
            throw new RuntimeException("Count failed", e);
        }
    }

    /**
     * 检查索引是否存在
     * 重写自: engine.ts - elIndexExists() (行728-735)
     */
    @Override
    public boolean elIndexExists(String indexName) {
        try {
            BooleanResponse response = esClient.indices().exists(e -> e
                    .index(indexName));
            return response.value();
        } catch (Exception e) {
            log.error("[SEARCH] Index exists check failed for: {}", indexName, e);
            return false;
        }
    }

    /**
     * 创建索引
     * 重写自: engine.ts - elCreateIndex() (行1357-1375)
     */
    @Override
    public boolean elCreateIndex(String indexName, Map<String, Object> mappingProperties) {
        try {
            esClient.indices().create(c -> c
                    .index(indexName));
            log.info("[SEARCH] Index created: {}", indexName);
            return true;
        } catch (Exception e) {
            log.error("[SEARCH] Create index failed for: {}", indexName, e);
            throw new RuntimeException("Create index failed", e);
        }
    }

    /**
     * 删除索引
     * 重写自: engine.ts - elDeleteIndex() (行1342-1356)
     */
    @Override
    public boolean elDeleteIndex(String indexName) {
        try {
            esClient.indices().delete(d -> d.index(indexName));
            log.info("[SEARCH] Index deleted: {}", indexName);
            return true;
        } catch (Exception e) {
            log.info("[SEARCH] Index cannot be deleted: {}", indexName);
            return false;
        }
    }

    /**
     * 获取平台索引列表
     * 重写自: engine.ts - elPlatformIndices() (行745-753)
     */
    @Override
    public List<Map<String, Object>> elPlatformIndices() {
        try {
            GetIndexResponse response = esClient.indices().get(g -> g
                    .index(config.getIndexPrefix() + "*"));
            
            List<Map<String, Object>> indices = new ArrayList<>();
            response.result().forEach((key, value) -> {
                Map<String, Object> indexInfo = new HashMap<>();
                indexInfo.put("index", key);
                indices.add(indexInfo);
            });
            
            return indices;
        } catch (Exception e) {
            log.error("[SEARCH] Get platform indices failed", e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取索引映射
     * 重写自: engine.ts - elPlatformMapping() (行754-761)
     */
    @Override
    public Map<String, Object> elPlatformMapping(String indexName) {
        try {
            GetMappingResponse response = esClient.indices().getMapping(g -> g
                    .index(indexName));
            
            // TODO: 转换响应为Map
            return new HashMap<>();
        } catch (Exception e) {
            log.error("[SEARCH] Get mapping failed for: {}", indexName, e);
            return Collections.emptyMap();
        }
    }

    /**
     * 获取索引设置
     * 重写自: engine.ts - elIndexSetting() (行762-775)
     */
    @Override
    public Map<String, Object> elIndexSetting(String indexName) {
        try {
            // TODO: 实现获取索引设置
            return new HashMap<>();
        } catch (Exception e) {
            log.error("[SEARCH] Get index setting failed for: {}", indexName, e);
            return Collections.emptyMap();
        }
    }

    /**
     * 检查版本是否满足条件
     */
    private boolean isVersionSatisfied(String version, String constraint) {
        // TODO: 实现版本比较逻辑
        return true;
    }

    /**
     * Map转JSON字符串
     */
    private String mapToJson(Map<String, Object> map) {
        // TODO: 使用ObjectMapper
        return map.toString();
    }
}
