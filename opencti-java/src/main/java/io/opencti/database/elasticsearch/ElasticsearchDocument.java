package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Elasticsearch文档操作类
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供文档的CRUD操作
 */
@Component
public class ElasticsearchDocument {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchDocument.class);

    private final ElasticsearchClient client;
    private final ElasticsearchConfig config;

    public ElasticsearchDocument(ElasticsearchClient client, ElasticsearchConfig config) {
        this.client = client;
        this.config = config;
    }

    /**
     * 原始搜索
     * 重写自: engine.ts - elRawSearch() (行440-460)
     * 
     * @param indices 索引列表
     * @param query 查询条件
     * @return 搜索结果
     */
    public SearchResponse<Map> elRawSearch(List<String> indices, Map<String, Object> query) {
        try {
            SearchRequest.Builder builder = new SearchRequest.Builder();
            
            // 设置索引
            if (indices != null && !indices.isEmpty()) {
                builder.index(indices);
            }
            
            // TODO: 完整实现查询构建
            
            return client.search(builder.build(), Map.class);
        } catch (Exception e) {
            log.error("[SEARCH] Raw search failed", e);
            throw new RuntimeException("Search failed", e);
        }
    }

    /**
     * 原始获取
     * 重写自: engine.ts - elRawGet() (行462-469)
     * 
     * @param id 文档ID
     * @param index 索引名
     * @return 文档内容
     */
    public Map<String, Object> elRawGet(String id, String index) {
        try {
            GetResponse<Map> response = client.get(g -> g
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
     * 原始索引（创建/更新文档）
     * 重写自: engine.ts - elRawIndex() (行470-477)
     * 
     * @param index 索引名
     * @param id 文档ID
     * @param document 文档内容
     * @return 索引结果
     */
    public IndexResult elRawIndex(String index, String id, Map<String, Object> document) {
        try {
            IndexResponse response = client.index(i -> i
                    .index(index)
                    .id(id)
                    .document(document));
            
            return new IndexResult(
                    response.index(),
                    response.id(),
                    response.result().jsonValue(),
                    response.version(),
                    response.seqNo(),
                    response.primaryTerm()
            );
        } catch (Exception e) {
            log.error("[SEARCH] Raw index failed for id: {}, index: {}", id, index, e);
            throw new RuntimeException("Index failed", e);
        }
    }

    /**
     * 原始删除
     * 重写自: engine.ts - elRawDelete() (行478-485)
     * 
     * @param index 索引名
     * @param id 文档ID
     * @return 删除结果
     */
    public DeleteResult elRawDelete(String index, String id) {
        try {
            DeleteResponse response = client.delete(d -> d
                    .index(index)
                    .id(id));
            
            return new DeleteResult(
                    response.index(),
                    response.id(),
                    response.result().jsonValue(),
                    response.version()
            );
        } catch (Exception e) {
            log.error("[SEARCH] Raw delete failed for id: {}, index: {}", id, index, e);
            throw new RuntimeException("Delete failed", e);
        }
    }

    /**
     * 按查询删除
     * 重写自: engine.ts - elRawDeleteByQuery() (行486-493)
     * 
     * @param index 索引名
     * @param query 查询条件
     * @return 删除结果
     */
    public DeleteByQueryResult elRawDeleteByQuery(String index, Map<String, Object> query) {
        try {
            DeleteByQueryResponse response = client.deleteByQuery(d -> d
                    .index(index)
                    .refresh(true));
            
            return new DeleteByQueryResult(
                    response.deleted() != null ? response.deleted() : 0,
                    response.took() != null ? response.took() : 0,
                    response.timedOut() != null ? response.timedOut() : false
            );
        } catch (Exception e) {
            log.error("[SEARCH] Delete by query failed for index: {}", index, e);
            throw new RuntimeException("Delete by query failed", e);
        }
    }

    /**
     * 按查询更新
     * 重写自: engine.ts - elRawUpdateByQuery() (行502-509)
     * 
     * @param index 索引名
     * @param query 查询条件
     * @param script 更新脚本
     * @return 更新结果
     */
    public UpdateByQueryResult elRawUpdateByQuery(String index, Map<String, Object> query, String script) {
        try {
            UpdateByQueryResponse response = client.updateByQuery(u -> u
                    .index(index)
                    .refresh(true));
            
            return new UpdateByQueryResult(
                    response.updated() != null ? response.updated() : 0,
                    response.took() != null ? response.took() : 0,
                    response.timedOut() != null ? response.timedOut() : false
            );
        } catch (Exception e) {
            log.error("[SEARCH] Update by query failed for index: {}", index, e);
            throw new RuntimeException("Update by query failed", e);
        }
    }

    /**
     * 重建索引
     * 重写自: engine.ts - elRawReindexByQuery() (行510-517)
     * 
     * @param sourceIndex 源索引
     * @param destIndex 目标索引
     * @return 重建结果
     */
    public ReindexResult elRawReindexByQuery(String sourceIndex, String destIndex) {
        try {
            ReindexResponse response = client.reindex(r -> r
                    .source(s -> s.index(sourceIndex))
                    .dest(d -> d.index(destIndex)));
            
            return new ReindexResult(
                    response.total() != null ? response.total() : 0,
                    response.took() != null ? response.took() : 0,
                    response.timedOut() != null ? response.timedOut() : false
            );
        } catch (Exception e) {
            log.error("[SEARCH] Reindex failed from {} to {}", sourceIndex, destIndex, e);
            throw new RuntimeException("Reindex failed", e);
        }
    }

    /**
     * 原始计数
     * 重写自: engine.ts - elRawCount() (行3077-3088)
     * 
     * @param indices 索引列表
     * @param query 查询条件
     * @return 文档数量
     */
    public long elRawCount(List<String> indices, Map<String, Object> query) {
        try {
            CountResponse response = client.count(c -> {
                if (indices != null && !indices.isEmpty()) {
                    c.index(indices);
                }
                return c;
            });
            
            return response.count();
        } catch (Exception e) {
            log.error("[SEARCH] Count failed", e);
            throw new RuntimeException("Count failed", e);
        }
    }

    /**
     * 更新文档（简化版，兼容测试代码）
     *
     * @param index 索引名
     * @param id 文档ID
     * @param document 更新内容
     * @return 更新结果
     */
    public UpdateResult elUpdate(String index, String id, Map<String, Object> document) {
        return elUpdate(index, id, document, ElasticsearchConstants.ES_RETRY_ON_CONFLICT);
    }

    /**
     * 更新文档
     * 重写自: engine.ts - elUpdate() (行3677-3700)
     *
     * @param index 索引名
     * @param id 文档ID
     * @param document 更新内容
     * @param retryOnConflict 冲突重试次数
     * @return 更新结果
     */
    public UpdateResult elUpdate(String index, String id, Map<String, Object> document, int retryOnConflict) {
        try {
            UpdateResponse<Map> response = client.update(u -> u
                    .index(index)
                    .id(id)
                    .retryOnConflict(retryOnConflict > 0 ? retryOnConflict : ElasticsearchConstants.ES_RETRY_ON_CONFLICT)
                    .doc(document), Map.class);
            
            return new UpdateResult(
                    response.index(),
                    response.id(),
                    response.result().jsonValue(),
                    response.version()
            );
        } catch (Exception e) {
            log.error("[SEARCH] Update failed for id: {}, index: {}", id, index, e);
            throw new RuntimeException("Update failed", e);
        }
    }

    /**
     * 替换文档
     * 重写自: engine.ts - elReplace() (行3701-3722)
     * 
     * @param index 索引名
     * @param id 文档ID
     * @param document 新文档内容
     * @return 替换结果
     */
    public IndexResult elReplace(String index, String id, Map<String, Object> document) {
        return elRawIndex(index, id, document);
    }

    /**
     * 删除文档
     * 重写自: engine.ts - elDelete() (行3723-3780)
     * 
     * @param index 索引名
     * @param id 文档ID
     * @return 删除结果
     */
    public DeleteResult elDelete(String index, String id) {
        return elRawDelete(index, id);
    }

    /**
     * 转换搜索结果
     * 重写自: engine.ts - elConvertHits() (行1678-1702)
     * 
     * @param hits 搜索命中列表
     * @return 转换后的文档列表
     */
    public List<Map<String, Object>> elConvertHits(List<Hit<Map>> hits) {
        List<Map<String, Object>> results = new ArrayList<>();
        if (hits == null) {
            return results;
        }
        
        for (Hit<Map> hit : hits) {
            Map<String, Object> doc = hit.source();
            if (doc != null) {
                doc.put("_index", hit.index());
                doc.put("_id", hit.id());
                doc.put("_score", hit.score());
                results.add(doc);
            }
        }
        
        return results;
    }

    /**
     * 转换搜索结果为Map
     * 重写自: engine.ts - elConvertHitsToMap() (行1654-1677)
     * 
     * @param hits 搜索命中列表
     * @param key 键字段名
     * @return 键值对映射
     */
    public Map<String, Map<String, Object>> elConvertHitsToMap(List<Hit<Map>> hits, String key) {
        Map<String, Map<String, Object>> result = new HashMap<>();
        if (hits == null || key == null) {
            return result;
        }
        
        for (Hit<Map> hit : hits) {
            Map<String, Object> doc = hit.source();
            if (doc != null) {
                Object keyValue = doc.get(key);
                if (keyValue != null) {
                    doc.put("_index", hit.index());
                    doc.put("_id", hit.id());
                    result.put(String.valueOf(keyValue), doc);
                }
            }
        }
        
        return result;
    }

    // ==================== 结果记录类 ====================
    
    public record IndexResult(
            String index,
            String id,
            String result,
            long version,
            long seqNo,
            long primaryTerm
    ) {}
    
    public record DeleteResult(
            String index,
            String id,
            String result,
            long version
    ) {}
    
    public record DeleteByQueryResult(
            long deleted,
            long took,
            boolean timedOut
    ) {}
    
    public record UpdateByQueryResult(
            long updated,
            long took,
            boolean timedOut
    ) {}
    
    public record ReindexResult(
            long total,
            long took,
            boolean timedOut
    ) {}
    
    public record UpdateResult(
            String index,
            String id,
            String result,
            long version
    ) {}
}
