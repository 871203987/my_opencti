package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import io.opencti.database.elasticsearch.query.FilterBuilder;
import io.opencti.database.elasticsearch.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Elasticsearch元素操作类
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供元素级别的操作，包括删除、索引、重建等
 */
@Component
public class ElasticsearchElement {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchElement.class);

    private final ElasticsearchClient client;
    private final ElasticsearchDocument document;
    private final ElasticsearchBulk bulk;
    private final ElasticsearchConfig config;

    public ElasticsearchElement(ElasticsearchClient client, ElasticsearchDocument document,
                                 ElasticsearchBulk bulk, ElasticsearchConfig config) {
        this.client = client;
        this.document = document;
        this.bulk = bulk;
        this.config = config;
    }

    /**
     * 删除实例
     * 重写自: engine.ts - elDeleteInstances() (行3793-3809)
     * 
     * @param indices 索引列表
     * @param ids ID列表
     * @return 删除数量
     */
    public int elDeleteInstances(List<String> indices, List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        
        try {
            Query query = QueryBuilder.ids(ids);
            
            DeleteByQueryResponse response = client.deleteByQuery(d -> d
                    .index(indices)
                    .query(query)
                    .refresh(true));
            
            return response.deleted() != null ? response.deleted().intValue() : 0;
        } catch (Exception e) {
            log.error("[SEARCH] Delete instances failed", e);
            throw new RuntimeException("Delete instances failed", e);
        }
    }

    /**
     * 移除关系连接
     * 重写自: engine.ts - elRemoveRelationConnection() (行3811-3896)
     * 
     * @param indices 索引列表
     * @param fromId 起始ID
     * @param toId 目标ID
     * @param relationType 关系类型
     * @return 更新数量
     */
    public int elRemoveRelationConnection(List<String> indices, String fromId, String toId, String relationType) {
        // TODO: 实现完整的关系连接移除逻辑
        log.info("[SEARCH] Remove relation connection: from={}, to={}, type={}", fromId, toId, relationType);
        return 0;
    }

    /**
     * 计算删除元素影响
     * 重写自: engine.ts - computeDeleteElementsImpacts() (行3897-3940)
     * 
     * @param elements 要删除的元素列表
     * @return 影响信息
     */
    public DeleteImpact computeDeleteElementsImpacts(List<Map<String, Object>> elements) {
        // TODO: 实现完整的删除影响计算
        return new DeleteImpact(Collections.emptyList(), Collections.emptyList());
    }

    /**
     * 重建索引元素
     * 重写自: engine.ts - elReindexElements() (行3941-3995)
     * 
     * @param sourceIndex 源索引
     * @param destIndex 目标索引
     * @param ids 元素ID列表
     * @return 重建数量
     */
    public int elReindexElements(String sourceIndex, String destIndex, List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        
        try {
            // 先获取源数据
            List<Map<String, Object>> documents = new ArrayList<>();
            for (String id : ids) {
                Map<String, Object> doc = document.elRawGet(id, sourceIndex);
                if (doc != null) {
                    documents.add(doc);
                }
            }
            
            // 批量写入目标索引
            Map<String, Map<String, Object>> bulkDocs = new HashMap<>();
            for (Map<String, Object> doc : documents) {
                String id = String.valueOf(doc.get("internal_id"));
                bulkDocs.put(id, doc);
            }
            
            ElasticsearchBulk.BulkResult result = bulk.elBulkIndex(destIndex, bulkDocs);
            return result.count();
        } catch (Exception e) {
            log.error("[SEARCH] Reindex elements failed", e);
            throw new RuntimeException("Reindex elements failed", e);
        }
    }

    /**
     * 索引元素
     * 重写自: engine.ts - elIndexElements() (行4338-4490)
     * 
     * @param index 索引名
     * @param elements 元素列表
     * @return 索引数量
     */
    public int elIndexElements(String index, List<Map<String, Object>> elements) {
        if (elements == null || elements.isEmpty()) {
            return 0;
        }
        
        try {
            Map<String, Map<String, Object>> bulkDocs = new HashMap<>();
            for (Map<String, Object> element : elements) {
                String id = String.valueOf(element.get("internal_id"));
                if (id != null && !id.equals("null")) {
                    bulkDocs.put(id, element);
                }
            }
            
            ElasticsearchBulk.BulkResult result = bulk.elBulkIndex(index, bulkDocs);
            return result.count();
        } catch (Exception e) {
            log.error("[SEARCH] Index elements failed", e);
            throw new RuntimeException("Index elements failed", e);
        }
    }

    /**
     * 删除元素
     * 重写自: engine.ts - elDeleteElements() (行4593-4657)
     * 
     * @param indices 索引列表
     * @param elements 元素列表
     * @return 删除数量
     */
    public int elDeleteElements(List<String> indices, List<Map<String, Object>> elements) {
        if (elements == null || elements.isEmpty()) {
            return 0;
        }
        
        List<String> ids = new ArrayList<>();
        for (Map<String, Object> element : elements) {
            String id = String.valueOf(element.get("internal_id"));
            if (id != null && !id.equals("null")) {
                ids.add(id);
            }
        }
        
        return elDeleteInstances(indices, ids);
    }

    /**
     * 更新元素
     * 重写自: engine.ts - elUpdateElement() (行4658-4671)
     * 
     * @param index 索引名
     * @param id 元素ID
     * @param updates 更新内容
     * @return 更新结果
     */
    public ElasticsearchDocument.UpdateResult elUpdateElement(String index, String id, Map<String, Object> updates) {
        return document.elUpdate(index, id, updates, ElasticsearchConstants.ES_RETRY_ON_CONFLICT);
    }

    /**
     * 更新关系连接
     * 重写自: engine.ts - elUpdateRelationConnections() (行4491-4502)
     * 
     * @param index 索引名
     * @param elements 元素列表
     * @return 更新数量
     */
    public int elUpdateRelationConnections(String index, List<Map<String, Object>> elements) {
        // TODO: 实现完整的关系连接更新逻辑
        log.info("[SEARCH] Update relation connections for {} elements", elements.size());
        return 0;
    }

    /**
     * 更新实体连接
     * 重写自: engine.ts - elUpdateEntityConnections() (行4503-4592)
     * 
     * @param index 索引名
     * @param elements 元素列表
     * @return 更新数量
     */
    public int elUpdateEntityConnections(String index, List<Map<String, Object>> elements) {
        // TODO: 实现完整的实体连接更新逻辑
        log.info("[SEARCH] Update entity connections for {} elements", elements.size());
        return 0;
    }

    /**
     * 重建关系
     * 重写自: engine.ts - elRebuildRelation() (行1596-1653)
     * 
     * @param index 索引名
     * @param relationId 关系ID
     * @return 是否成功
     */
    public boolean elRebuildRelation(String index, String relationId) {
        // TODO: 实现完整的关系重建逻辑
        log.info("[SEARCH] Rebuild relation: {}", relationId);
        return true;
    }

    /**
     * 获取要移除的关系
     * 重写自: engine.ts - getRelationsToRemove() (行3781-3792)
     * 
     * @param indices 索引列表
     * @param entityId 实体ID
     * @return 关系ID列表
     */
    public List<String> getRelationsToRemove(List<String> indices, String entityId) {
        try {
            Query query = QueryBuilder.bool(
                    List.of(QueryBuilder.term("fromId", entityId)),
                    null,
                    null
            );
            
            SearchRequest request = SearchRequest.of(s -> s
                    .index(indices)
                    .query(query)
                    .source(src -> src.filter(f -> f.includes("internal_id")))
                    .size(10000));
            
            SearchResponse<Map> response = client.search(request, Map.class);
            
            List<String> relationIds = new ArrayList<>();
            for (Hit<Map> hit : response.hits().hits()) {
                Map<String, Object> source = hit.source();
                if (source != null && source.containsKey("internal_id")) {
                    relationIds.add(String.valueOf(source.get("internal_id")));
                }
            }
            
            return relationIds;
        } catch (Exception e) {
            log.error("[SEARCH] Get relations to remove failed", e);
            return Collections.emptyList();
        }
    }

    /**
     * 删除影响信息
     */
    public record DeleteImpact(
            List<String> deletedElements,
            List<String> updatedElements
    ) {}
}
