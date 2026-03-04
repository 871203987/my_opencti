package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.IndicesResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.elasticsearch.indices.PutIndexTemplateResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Elasticsearch索引管理类
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供索引的创建、删除、查询等管理功能
 */
@Component
public class ElasticsearchIndices {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchIndices.class);

    private final ElasticsearchClient client;
    private final ElasticsearchConfig config;

    public ElasticsearchIndices(ElasticsearchClient client, ElasticsearchConfig config) {
        this.client = client;
        this.config = config;
    }

    /**
     * 检查索引是否存在
     * 重写自: engine.ts - elIndexExists() (行728-735)
     * 
     * @param indexName 索引名
     * @return 是否存在
     */
    public boolean elIndexExists(String indexName) {
        try {
            BooleanResponse response = client.indices().exists(e -> e
                    .index(indexName));
            return response.value();
        } catch (Exception e) {
            log.error("[SEARCH] Index exists check failed for: {}", indexName, e);
            return false;
        }
    }

    /**
     * 获取索引别名
     * 重写自: engine.ts - elIndexGetAlias() (行736-744)
     * 
     * @param indexName 索引名
     * @return 别名映射
     */
    public Map<String, Object> elIndexGetAlias(String indexName) {
        try {
            var response = client.indices().getAlias(a -> a
                    .index(indexName));
            
            Map<String, Object> result = new HashMap<>();
            response.result().forEach((key, value) -> {
                result.put(key, Map.of("aliases", value.aliases().keySet()));
            });
            return result;
        } catch (Exception e) {
            log.error("[SEARCH] Get alias failed for: {}", indexName, e);
            return Collections.emptyMap();
        }
    }

    /**
     * 获取平台索引列表
     * 重写自: engine.ts - elPlatformIndices() (行745-753)
     * 
     * @return 索引列表
     */
    public List<Map<String, Object>> elPlatformIndices() {
        try {
            String indexPrefix = config.getIndexPrefix();
            // ES 8.x Java Client的cat().indices()默认返回JSON格式，不需要format()方法
            IndicesResponse response = client.cat().indices(i -> i
                    .index(indexPrefix + "*"));
            
            List<Map<String, Object>> indices = new ArrayList<>();
            response.valueBody().forEach(record -> {
                Map<String, Object> indexInfo = new HashMap<>();
                indexInfo.put("index", record.index());
                indexInfo.put("health", record.health());
                indexInfo.put("status", record.status());
                indexInfo.put("uuid", record.uuid());
                indexInfo.put("pri", record.pri());
                indexInfo.put("rep", record.rep());
                indexInfo.put("docsCount", record.docsCount());
                indexInfo.put("storeSize", record.storeSize());
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
     * 
     * @param indexName 索引名
     * @return 映射属性
     */
    public Map<String, Object> elPlatformMapping(String indexName) {
        try {
            var response = client.indices().getMapping(m -> m
                    .index(indexName));
            
            // 获取映射属性
            var indexMapping = response.get(indexName);
            if (indexMapping != null && indexMapping.mappings() != null) {
                return propertiesToMap(indexMapping.mappings().properties());
            }
            return Collections.emptyMap();
        } catch (Exception e) {
            log.error("[SEARCH] Get mapping failed for: {}", indexName, e);
            return Collections.emptyMap();
        }
    }

    /**
     * 获取索引设置
     * 重写自: engine.ts - elIndexSetting() (行762-775)
     * 
     * @param indexName 索引名
     * @return 设置信息
     */
    public IndexSettingResult elIndexSetting(String indexName) {
        try {
            var response = client.indices().getSettings(s -> s
                    .index(indexName));
            
            var indexSettings = response.get(indexName);
            if (indexSettings != null && indexSettings.settings() != null) {
                var settings = indexSettings.settings();
                Map<String, Object> settingsMap = new HashMap<>();
                
                if (settings.index() != null) {
                    settingsMap.put("number_of_shards", settings.index().numberOfShards());
                    settingsMap.put("number_of_replicas", settings.index().numberOfReplicas());
                    settingsMap.put("max_result_window", settings.index().maxResultWindow());
                }
                
                // 获取rollover别名
                String rolloverAlias = null;
                if (settings.index() != null && settings.index().lifecycle() != null) {
                    rolloverAlias = settings.index().lifecycle().rolloverAlias();
                }
                
                return new IndexSettingResult(settingsMap, rolloverAlias);
            }
            return new IndexSettingResult(Collections.emptyMap(), null);
        } catch (Exception e) {
            log.error("[SEARCH] Get index setting failed for: {}", indexName, e);
            return new IndexSettingResult(Collections.emptyMap(), null);
        }
    }

    /**
     * 获取平台模板列表
     * 重写自: engine.ts - elPlatformTemplates() (行776-784)
     * 
     * @return 模板列表
     */
    public List<Map<String, Object>> elPlatformTemplates() {
        try {
            String indexPrefix = config.getIndexPrefix();
            // ES 8.x Java Client的cat().templates()默认返回JSON格式，不需要format()方法
            var response = client.cat().templates(t -> t
                    .name(indexPrefix + "*"));
            
            List<Map<String, Object>> templates = new ArrayList<>();
            response.valueBody().forEach(record -> {
                Map<String, Object> templateInfo = new HashMap<>();
                templateInfo.put("name", record.name());
                templateInfo.put("indexPatterns", record.indexPatterns());
                templates.add(templateInfo);
            });
            
            return templates;
        } catch (Exception e) {
            log.error("[SEARCH] Get platform templates failed", e);
            return Collections.emptyList();
        }
    }

    /**
     * 创建索引
     * 重写自: engine.ts - elCreateIndex() (行1357-1375)
     * 
     * @param indexName 索引名
     * @param mappingProperties 映射属性
     * @return 创建结果
     */
    public boolean elCreateIndex(String indexName, Map<String, Object> mappingProperties) {
        try {
            // 先创建索引模板
            elCreateIndexTemplate(indexName, mappingProperties);
            
            // 创建索引名称（带后缀）
            String fullIndexName = indexName + config.getIndexCreationPattern();
            
            // 检查索引是否已存在
            if (elIndexExists(fullIndexName)) {
                log.info("[SEARCH] Index already exists: {}", fullIndexName);
                return false;
            }
            
            // 创建索引并设置别名
            client.indices().create(c -> c
                    .index(fullIndexName)
                    .aliases(indexName, a -> a.isWriteIndex(true)));
            
            log.info("[SEARCH] Index created: {} with alias: {}", fullIndexName, indexName);
            return true;
        } catch (Exception e) {
            log.error("[SEARCH] Create index failed for: {}", indexName, e);
            throw new RuntimeException("Create index failed", e);
        }
    }

    /**
     * 创建索引模板
     * 重写自: engine.ts - elCreateIndexTemplate()
     * 
     * @param indexName 索引名
     * @param mappingProperties 映射属性
     */
    private void elCreateIndexTemplate(String indexName, Map<String, Object> mappingProperties) {
        try {
            client.indices().putIndexTemplate(p -> p
                    .name(indexName)
                    .indexPatterns(indexName + "*")
                    .template(t -> t
                            .mappings(m -> {
                                // TODO: 完整实现映射属性设置
                                return m;
                            })));
            
            log.debug("[SEARCH] Index template created for: {}", indexName);
        } catch (Exception e) {
            log.error("[SEARCH] Create index template failed for: {}", indexName, e);
        }
    }

    /**
     * 删除索引
     * 重写自: engine.ts - elDeleteIndex() (行1342-1356)
     * 
     * @param indexName 索引名
     */
    public void elDeleteIndex(String indexName) {
        try {
            // 获取别名对应的索引
            Map<String, Object> aliases = elIndexGetAlias(indexName);
            if (!aliases.isEmpty()) {
                Set<String> indices = aliases.keySet();
                client.indices().delete(d -> d.index(String.join(",", indices)));
                log.info("[SEARCH] Index '{}' deleted successfully", indices);
            }
        } catch (Exception e) {
            log.error("[SEARCH] Delete index failed for: {}", indexName, e);
        }
    }

    /**
     * 批量创建索引
     * 重写自: engine.ts - elCreateIndices() (行1376-1389)
     * 
     * @param indexesToCreate 要创建的索引列表
     * @return 创建的索引列表
     */
    public List<String> elCreateIndices(List<String> indexesToCreate) {
        try {
            // 更新核心设置
            updateCoreSettings();
            
            // 创建生命周期策略
            elCreateLifecyclePolicy();
            
            List<String> createdIndices = new ArrayList<>();
            Map<String, Object> mappingProperties = new HashMap<>(); // TODO: engineMappingGenerator()
            
            for (String index : indexesToCreate) {
                if (elCreateIndex(index, mappingProperties)) {
                    createdIndices.add(index);
                }
            }
            
            return createdIndices;
        } catch (Exception e) {
            log.error("[SEARCH] Create indices failed", e);
            throw new RuntimeException("Create indices failed", e);
        }
    }

    /**
     * 批量删除索引
     * 重写自: engine.ts - elDeleteIndices() (行1406-1429)
     * 
     * @param indexesToDelete 要删除的索引列表
     */
    public void elDeleteIndices(List<String> indexesToDelete) {
        for (String index : indexesToDelete) {
            try {
                client.indices().delete(d -> d.index(index));
                log.info("[SEARCH] Index deleted: {}", index);
            } catch (Exception e) {
                log.error("[SEARCH] Delete index failed for: {}", index, e);
            }
        }
    }

    /**
     * 初始化Schema
     * 重写自: engine.ts - initializeSchema() (行1392-1404)
     * 
     * @return 是否成功
     */
    public boolean initializeSchema() {
        // 检查内部索引是否已存在
        if (elIndexExists(ElasticsearchConstants.INDEX_INTERNAL_OBJECTS)) {
            throw new RuntimeException("Fail initialize schema, index already exists. "
                    + "Please remove your elastic/opensearch data and restart.");
        }
        
        // 创建默认索引
        elCreateIndices(ElasticsearchConstants.WRITE_PLATFORM_INDICES);
        log.info("[INIT] Search engine indexes loaded");
        
        return true;
    }

    /**
     * 更新核心设置
     * 重写自: engine.ts - updateCoreSettings() (行841-874)
     */
    private void updateCoreSettings() {
        try {
            String indexPrefix = config.getIndexPrefix();
            
            client.cluster().putComponentTemplate(p -> p
                    .name(indexPrefix + "-core-settings")
                    .template(t -> t
                            .settings(s -> s
                                    .index(i -> i
                                            .maxResultWindow(config.getMaxResultWindow())
                                            .numberOfShards("1")
                                            .numberOfReplicas("0")))));
            
            log.info("[SEARCH] Core settings updated");
        } catch (Exception e) {
            log.error("[SEARCH] Update core settings failed", e);
            throw new RuntimeException("Creating component template fail", e);
        }
    }

    /**
     * 创建生命周期策略
     * 重写自: engine.ts - elCreateLifecyclePolicy() (行785-840)
     */
    private void elCreateLifecyclePolicy() {
        try {
            String indexPrefix = config.getIndexPrefix();
            
            // ES ILM策略
            // ES 8.x中minAge需要使用Time对象
            client.ilm().putLifecycle(p -> p
                    .name(indexPrefix + "-ilm-policy")
                    .policy(policy -> policy
                            .phases(phases -> phases
                                    .hot(hot -> hot
                                            .minAge(t -> t.time("0ms"))
                                            .actions(actions -> actions
                                                    .rollover(r -> r
                                                            .maxPrimaryShardSize("50gb")
                                                            .maxDocs(100000000L))
                                                    .setPriority(sp -> sp.priority(100)))))));
            
            log.info("[SEARCH] Lifecycle policy created");
        } catch (Exception e) {
            log.error("[SEARCH] Create lifecycle policy failed", e);
            throw new RuntimeException("Creating lifecycle policy fail", e);
        }
    }

    /**
     * 将ES属性转换为Map
     */
    private Map<String, Object> propertiesToMap(Map<String, co.elastic.clients.elasticsearch._types.mapping.Property> properties) {
        Map<String, Object> result = new HashMap<>();
        if (properties == null) {
            return result;
        }
        
        properties.forEach((key, value) -> {
            Map<String, Object> propMap = new HashMap<>();
            if (value._kind() != null) {
                propMap.put("type", value._kind().jsonValue());
            }
            result.put(key, propMap);
        });
        
        return result;
    }

    /**
     * 索引设置结果
     */
    public record IndexSettingResult(Map<String, Object> settings, String rolloverAlias) {}
}
