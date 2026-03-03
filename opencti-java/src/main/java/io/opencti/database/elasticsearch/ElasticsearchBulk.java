package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.core.bulk.DeleteOperation;
import co.elastic.clients.elasticsearch.core.bulk.UpdateOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Elasticsearch批量操作类
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供批量索引、删除、更新操作
 */
@Component
public class ElasticsearchBulk {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchBulk.class);

    private final ElasticsearchClient client;
    private final ElasticsearchConfig config;

    public ElasticsearchBulk(ElasticsearchClient client, ElasticsearchConfig config) {
        this.client = client;
        this.config = config;
    }

    /**
     * 批量操作
     * 重写自: engine.ts - elRawBulk() (行494-501)
     * 
     * @param operations 批量操作列表
     * @return 批量操作结果
     */
    public BulkResult elBulk(List<BulkOperation> operations) {
        if (operations == null || operations.isEmpty()) {
            return new BulkResult(false, 0, 0, Collections.emptyList());
        }
        
        try {
            BulkRequest.Builder builder = new BulkRequest.Builder()
                    .operations(operations)
                    .refresh(true);
            
            BulkResponse response = client.bulk(builder.build());
            
            List<BulkError> errors = new ArrayList<>();
            if (response.errors()) {
                for (BulkResponseItem item : response.items()) {
                    if (item.error() != null) {
                        errors.add(new BulkError(
                                item.index(),
                                item.id(),
                                item.error().type(),
                                item.error().reason()
                        ));
                    }
                }
            }
            
            return new BulkResult(
                    response.errors(),
                    response.items().size(),
                    response.took(),
                    errors
            );
        } catch (Exception e) {
            log.error("[SEARCH] Bulk operation failed", e);
            throw new RuntimeException("Bulk operation failed", e);
        }
    }

    /**
     * 批量索引文档
     * 重写自: engine.ts - elBulk() (行3633-3643)
     * 
     * @param index 索引名
     * @param documents 文档列表（id -> document）
     * @return 批量操作结果
     */
    public BulkResult elBulkIndex(String index, Map<String, Map<String, Object>> documents) {
        if (documents == null || documents.isEmpty()) {
            return new BulkResult(false, 0, 0, Collections.emptyList());
        }
        
        List<BulkOperation> operations = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : documents.entrySet()) {
            operations.add(BulkOperation.of(b -> b
                    .index(IndexOperation.of(i -> i
                            .index(index)
                            .id(entry.getKey())
                            .document(entry.getValue())))));
        }
        
        return elBulk(operations);
    }

    /**
     * 批量删除文档
     * 
     * @param index 索引名
     * @param ids 文档ID列表
     * @return 批量操作结果
     */
    public BulkResult elBulkDelete(String index, List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return new BulkResult(false, 0, 0, Collections.emptyList());
        }
        
        List<BulkOperation> operations = new ArrayList<>();
        for (String id : ids) {
            operations.add(BulkOperation.of(b -> b
                    .delete(DeleteOperation.of(d -> d
                            .index(index)
                            .id(id)))));
        }
        
        return elBulk(operations);
    }

    /**
     * 批量更新文档
     * 
     * @param index 索引名
     * @param documents 文档列表（id -> document）
     * @return 批量操作结果
     */
    public BulkResult elBulkUpdate(String index, Map<String, Map<String, Object>> documents) {
        if (documents == null || documents.isEmpty()) {
            return new BulkResult(false, 0, 0, Collections.emptyList());
        }
        
        List<BulkOperation> operations = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : documents.entrySet()) {
            operations.add(BulkOperation.of(b -> b
                    .update(UpdateOperation.of(u -> u
                            .index(index)
                            .id(entry.getKey())
                            .doc(entry.getValue())
                            .retryOnConflict(ElasticsearchConstants.ES_RETRY_ON_CONFLICT)))));
        }
        
        return elBulk(operations);
    }

    /**
     * 从NDJSON字符串执行批量操作
     * 重写自: engine.ts - elRawBulk() (行494-501)
     * 
     * @param ndjson NDJSON格式的批量操作
     * @return 批量操作结果
     */
    public BulkResult elBulkFromNdjson(String ndjson) {
        if (ndjson == null || ndjson.isEmpty()) {
            return new BulkResult(false, 0, 0, Collections.emptyList());
        }
        
        try {
            BulkResponse response = client.bulk(b -> b
                    .operations(new ByteArrayInputStream(ndjson.getBytes(StandardCharsets.UTF_8))));
            
            List<BulkError> errors = new ArrayList<>();
            if (response.errors()) {
                for (BulkResponseItem item : response.items()) {
                    if (item.error() != null) {
                        errors.add(new BulkError(
                                item.index(),
                                item.id(),
                                item.error().type(),
                                item.error().reason()
                        ));
                    }
                }
            }
            
            return new BulkResult(
                    response.errors(),
                    response.items().size(),
                    response.took(),
                    errors
            );
        } catch (Exception e) {
            log.error("[SEARCH] Bulk from NDJSON failed", e);
            throw new RuntimeException("Bulk from NDJSON failed", e);
        }
    }

    /**
     * 构建批量索引操作
     * 
     * @param index 索引名
     * @param id 文档ID
     * @param document 文档内容
     * @return 批量操作
     */
    public static BulkOperation buildIndexOperation(String index, String id, Map<String, Object> document) {
        return BulkOperation.of(b -> b
                .index(IndexOperation.of(i -> i
                        .index(index)
                        .id(id)
                        .document(document))));
    }

    /**
     * 构建批量删除操作
     * 
     * @param index 索引名
     * @param id 文档ID
     * @return 批量操作
     */
    public static BulkOperation buildDeleteOperation(String index, String id) {
        return BulkOperation.of(b -> b
                .delete(DeleteOperation.of(d -> d
                        .index(index)
                        .id(id))));
    }

    /**
     * 构建批量更新操作
     * 
     * @param index 索引名
     * @param id 文档ID
     * @param document 更新内容
     * @return 批量操作
     */
    public static BulkOperation buildUpdateOperation(String index, String id, Map<String, Object> document) {
        return BulkOperation.of(b -> b
                .update(UpdateOperation.of(u -> u
                        .index(index)
                        .id(id)
                        .doc(document)
                        .retryOnConflict(ElasticsearchConstants.ES_RETRY_ON_CONFLICT))));
    }

    /**
     * 批量操作结果
     */
    public record BulkResult(
            boolean errors,
            int count,
            long took,
            List<BulkError> errorItems
    ) {
        public boolean hasErrors() {
            return errors;
        }
        
        public int errorCount() {
            return errorItems != null ? errorItems.size() : 0;
        }
    }

    /**
     * 批量操作错误项
     */
    public record BulkError(
            String index,
            String id,
            String errorType,
            String errorReason
    ) {}
}
