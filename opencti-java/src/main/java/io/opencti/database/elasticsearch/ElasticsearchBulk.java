package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
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
                    .refresh(Refresh.True);
            
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
            Map<String, Object> doc = entry.getValue();
            operations.add(BulkOperation.of(b -> b
                    .index(IndexOperation.of(i -> i
                            .index(index)
                            .id(entry.getKey())
                            .document(doc)))));
        }
        
        return elBulk(operations);
    }

    /**
     * 从NDJSON字符串执行批量操作
     * 重写自: engine.ts - elRawBulk() (行494-501)
     * 
     * 注意: Elasticsearch Java Client 8.x 不支持直接从InputStream读取NDJSON
     * 此方法解析NDJSON并构建BulkOperation列表
     * 
     * @param ndjson NDJSON格式的批量操作
     * @return 批量操作结果
     */
    public BulkResult elBulkFromNdjson(String ndjson) {
        if (ndjson == null || ndjson.isEmpty()) {
            return new BulkResult(false, 0, 0, Collections.emptyList());
        }
        
        try {
            List<BulkOperation> operations = parseNdjson(ndjson);
            return elBulk(operations);
        } catch (Exception e) {
            log.error("[SEARCH] Bulk from NDJSON failed", e);
            throw new RuntimeException("Bulk from NDJSON failed", e);
        }
    }
    
    /**
     * 解析NDJSON格式字符串为批量操作列表
     * 
     * @param ndjson NDJSON格式字符串
     * @return 批量操作列表
     */
    private List<BulkOperation> parseNdjson(String ndjson) {
        List<BulkOperation> operations = new ArrayList<>();
        String[] lines = ndjson.split("\n");
        
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }
            
            Map<String, Object> actionLine = parseJsonLine(line);
            if (actionLine.isEmpty()) {
                continue;
            }
            
            if (actionLine.containsKey("index")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> indexMeta = (Map<String, Object>) actionLine.get("index");
                String index = (String) indexMeta.get("_index");
                String id = (String) indexMeta.get("_id");
                
                if (i + 1 < lines.length) {
                    String docLine = lines[i + 1].trim();
                    if (!docLine.isEmpty()) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> document = parseJsonLine(docLine);
                        operations.add(buildIndexOperation(index, id, document));
                        i++;
                    }
                }
            } else if (actionLine.containsKey("delete")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> deleteMeta = (Map<String, Object>) actionLine.get("delete");
                String index = (String) deleteMeta.get("_index");
                String id = (String) deleteMeta.get("_id");
                operations.add(buildDeleteOperation(index, id));
            } else if (actionLine.containsKey("update")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> updateMeta = (Map<String, Object>) actionLine.get("update");
                String index = (String) updateMeta.get("_index");
                String id = (String) updateMeta.get("_id");
                
                if (i + 1 < lines.length) {
                    String docLine = lines[i + 1].trim();
                    if (!docLine.isEmpty()) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> document = parseJsonLine(docLine);
                        operations.add(buildUpdateOperation(index, id, document));
                        i++;
                    }
                }
            }
        }
        
        return operations;
    }
    
    /**
     * 解析JSON行
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> parseJsonLine(String line) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(line, Map.class);
        } catch (Exception e) {
            log.warn("Failed to parse JSON line: {}", line, e);
            return Collections.emptyMap();
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
                .index(IndexOperation.of(i -> i
                        .index(index)
                        .id(id)
                        .document(document))));
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
