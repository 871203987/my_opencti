package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.TrackHits;
import io.opencti.database.elasticsearch.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Elasticsearch聚合操作类
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供计数、直方图、聚合等统计功能
 */
@Component
public class ElasticsearchAggregation {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchAggregation.class);

    private final ElasticsearchClient client;
    private final ElasticsearchConfig config;

    public ElasticsearchAggregation(ElasticsearchClient client, ElasticsearchConfig config) {
        this.client = client;
        this.config = config;
    }

    /**
     * 计数
     * 重写自: engine.ts - elCount() (行3089-3103)
     * 
     * @param indices 索引列表
     * @param query 查询条件
     * @return 文档数量
     */
    public long elCount(List<String> indices, co.elastic.clients.elasticsearch._types.query_dsl.Query query) {
        try {
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                    .index(indices)
                    .size(0)
                    .trackTotalHits(TrackHits.of(t -> t.enabled(true)));
            
            if (query != null) {
                requestBuilder.query(query);
            }
            
            SearchResponse<Map> response = client.search(requestBuilder.build(), Map.class);
            
            return response.hits().total() != null ? response.hits().total().value() : 0;
        } catch (Exception e) {
            log.error("[SEARCH] Count failed", e);
            throw new RuntimeException("Count failed", e);
        }
    }

    /**
     * 直方图计数
     * 重写自: engine.ts - elHistogramCount() (行3104-3168)
     * 
     * @param indices 索引列表
     * @param field 字段名
     * @param interval 间隔
     * @param query 查询条件
     * @return 直方图数据
     */
    public List<HistogramBucket> elHistogramCount(List<String> indices, String field, String interval,
                                                   co.elastic.clients.elasticsearch._types.query_dsl.Query query) {
        try {
            String aggregationName = "histogram";
            
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                    .index(indices)
                    .size(0)
                    .aggregations(Map.of(aggregationName, Aggregation.of(a -> a
                            .dateHistogram(dh -> dh
                                    .field(field)
                                    .calendarInterval(CalendarInterval.valueOf(interval.toLowerCase()))))));
            
            if (query != null) {
                requestBuilder.query(query);
            }
            
            SearchResponse<Map> response = client.search(requestBuilder.build(), Map.class);
            
            List<HistogramBucket> buckets = new ArrayList<>();
            Aggregate aggregate = response.aggregations().get(aggregationName);
            
            if (aggregate != null && aggregate.isDateHistogram()) {
                for (DateHistogramBucket bucket : aggregate.dateHistogram().buckets().array()) {
                    buckets.add(new HistogramBucket(
                            bucket.keyAsString(),
                            bucket.docCount()
                    ));
                }
            }
            
            return buckets;
        } catch (Exception e) {
            log.error("[SEARCH] Histogram count failed", e);
            throw new RuntimeException("Histogram count failed", e);
        }
    }

    /**
     * 聚合计数
     * 重写自: engine.ts - elAggregationCount() (行3169-3262)
     * 
     * @param indices 索引列表
     * @param field 字段名
     * @param query 查询条件
     * @param size 返回数量
     * @return 聚合结果
     */
    public List<TermsBucket> elAggregationCount(List<String> indices, String field,
                                                 co.elastic.clients.elasticsearch._types.query_dsl.Query query, int size) {
        try {
            String aggregationName = "terms";
            
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                    .index(indices)
                    .size(0)
                    .aggregations(Map.of(aggregationName, Aggregation.of(a -> a
                            .terms(t -> t
                                    .field(field + ".keyword")
                                    .size(size > 0 ? size : ElasticsearchConstants.MAX_AGGREGATION_SIZE)))));
            
            if (query != null) {
                requestBuilder.query(query);
            }
            
            SearchResponse<Map> response = client.search(requestBuilder.build(), Map.class);
            
            List<TermsBucket> buckets = new ArrayList<>();
            Aggregate aggregate = response.aggregations().get(aggregationName);
            
            if (aggregate != null && aggregate.isSterms()) {
                for (StringTermsBucket bucket : aggregate.sterms().buckets().array()) {
                    buckets.add(new TermsBucket(
                            bucket.key().stringValue(),
                            bucket.docCount()
                    ));
                }
            }
            
            return buckets;
        } catch (Exception e) {
            log.error("[SEARCH] Aggregation count failed", e);
            throw new RuntimeException("Aggregation count failed", e);
        }
    }

    /**
     * 关系聚合计数
     * 重写自: engine.ts - elAggregationRelationsCount() (行3263-3374)
     * 
     * @param indices 索引列表
     * @param query 查询条件
     * @param field 字段名
     * @return 聚合结果
     */
    public List<TermsBucket> elAggregationRelationsCount(List<String> indices,
                                                          co.elastic.clients.elasticsearch._types.query_dsl.Query query, String field) {
        return elAggregationCount(indices, field, query, ElasticsearchConstants.MAX_AGGREGATION_SIZE);
    }

    /**
     * 嵌套聚合
     * 重写自: engine.ts - elAggregationNestedTermsWithFilter() (行3375-3424)
     * 
     * @param indices 索引列表
     * @param nestedPath 嵌套路径
     * @param field 字段名
     * @param filterQuery 过滤条件
     * @return 聚合结果
     */
    public List<NestedBucket> elAggregationNestedTermsWithFilter(List<String> indices, String nestedPath,
                                                                   String field, co.elastic.clients.elasticsearch._types.query_dsl.Query filterQuery) {
        try {
            String aggregationName = "nested_terms";
            
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                    .index(indices)
                    .size(0)
                    .aggregations(Map.of(aggregationName, Aggregation.of(a -> a
                            .nested(n -> n.path(nestedPath))
                            .aggregations("inner", Aggregation.of(ia -> ia
                                    .filter(f -> {
                                        if (filterQuery != null) {
                                            f.bool(b -> b.must(filterQuery));
                                        }
                                        return f;
                                    })
                                    .aggregations("terms", Aggregation.of(ta -> ta
                                            .terms(t -> t.field(field + ".keyword")))))))));
            
            SearchResponse<Map> response = client.search(requestBuilder.build(), Map.class);
            
            List<NestedBucket> buckets = new ArrayList<>();
            Aggregate aggregate = response.aggregations().get(aggregationName);
            
            if (aggregate != null && aggregate.isNested()) {
                Aggregate innerAggregate = aggregate.nested().aggregations().get("inner");
                if (innerAggregate != null && innerAggregate.isFilter()) {
                    Aggregate termsAggregate = innerAggregate.filter().aggregations().get("terms");
                    if (termsAggregate != null && termsAggregate.isSterms()) {
                        for (StringTermsBucket bucket : termsAggregate.sterms().buckets().array()) {
                            buckets.add(new NestedBucket(
                                    bucket.key().stringValue(),
                                    bucket.docCount()
                            ));
                        }
                    }
                }
            }
            
            return buckets;
        } catch (Exception e) {
            log.error("[SEARCH] Nested aggregation failed", e);
            throw new RuntimeException("Nested aggregation failed", e);
        }
    }

    /**
     * 聚合列表
     * 重写自: engine.ts - elAggregationsList() (行3425-3585)
     * 
     * @param indices 索引列表
     * @param aggregations 聚合配置
     * @param query 查询条件
     * @return 聚合结果Map
     */
    public Map<String, Object> elAggregationsList(List<String> indices,
                                                   Map<String, Aggregation> aggregations,
                                                   co.elastic.clients.elasticsearch._types.query_dsl.Query query) {
        try {
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                    .index(indices)
                    .size(0);
            
            if (query != null) {
                requestBuilder.query(query);
            }
            
            if (aggregations != null) {
                requestBuilder.aggregations(aggregations);
            }
            
            SearchResponse<Map> response = client.search(requestBuilder.build(), Map.class);
            
            Map<String, Object> results = new HashMap<>();
            if (response.aggregations() != null) {
                response.aggregations().forEach((key, value) -> {
                    results.put(key, convertAggregate(value));
                });
            }
            
            return results;
        } catch (Exception e) {
            log.error("[SEARCH] Aggregations list failed", e);
            throw new RuntimeException("Aggregations list failed", e);
        }
    }

    /**
     * 属性值列表
     * 重写自: engine.ts - elAttributeValues() (行3586-3630)
     * 
     * @param indices 索引列表
     * @param field 字段名
     * @param search 搜索字符串
     * @param size 返回数量
     * @return 值列表
     */
    public List<String> elAttributeValues(List<String> indices, String field, String search, int size) {
        try {
            String aggregationName = "values";
            
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                    .index(indices)
                    .size(0)
                    .aggregations(Map.of(aggregationName, Aggregation.of(a -> a
                            .terms(t -> t
                                    .field(field + ".keyword")
                                    .size(size > 0 ? size : ElasticsearchConstants.MAX_AGGREGATION_SIZE)
                                    .include(inc -> {
                                        if (search != null && !search.isEmpty()) {
                                            inc.regexp(".*" + search.toLowerCase() + ".*");
                                        }
                                        return inc;
                                    })))));
            
            SearchResponse<Map> response = client.search(requestBuilder.build(), Map.class);
            
            List<String> values = new ArrayList<>();
            Aggregate aggregate = response.aggregations().get(aggregationName);
            
            if (aggregate != null && aggregate.isSterms()) {
                for (StringTermsBucket bucket : aggregate.sterms().buckets().array()) {
                    values.add(bucket.key().stringValue());
                }
            }
            
            return values;
        } catch (Exception e) {
            log.error("[SEARCH] Attribute values failed", e);
            throw new RuntimeException("Attribute values failed", e);
        }
    }

    /**
     * 转换聚合结果
     */
    private Object convertAggregate(Aggregate aggregate) {
        if (aggregate == null) {
            return null;
        }
        
        if (aggregate.isSterms()) {
            List<Map<String, Object>> buckets = new ArrayList<>();
            for (StringTermsBucket bucket : aggregate.sterms().buckets().array()) {
                Map<String, Object> bucketMap = new HashMap<>();
                bucketMap.put("key", bucket.key().stringValue());
                bucketMap.put("doc_count", bucket.docCount());
                buckets.add(bucketMap);
            }
            return buckets;
        }
        
        if (aggregate.isDateHistogram()) {
            List<Map<String, Object>> buckets = new ArrayList<>();
            for (DateHistogramBucket bucket : aggregate.dateHistogram().buckets().array()) {
                Map<String, Object> bucketMap = new HashMap<>();
                bucketMap.put("key", bucket.keyAsString());
                bucketMap.put("doc_count", bucket.docCount());
                buckets.add(bucketMap);
            }
            return buckets;
        }
        
        if (aggregate.isCardinality()) {
            return aggregate.cardinality().value();
        }
        
        if (aggregate.isSum()) {
            return aggregate.sum().value();
        }
        
        if (aggregate.isAvg()) {
            return aggregate.avg().value();
        }
        
        if (aggregate.isMax()) {
            return aggregate.max().value();
        }
        
        if (aggregate.isMin()) {
            return aggregate.min().value();
        }
        
        return aggregate.toString();
    }

    // ==================== 聚合结果类 ====================

    public record HistogramBucket(String key, long docCount) {}

    public record TermsBucket(String key, long docCount) {}

    public record NestedBucket(String key, long docCount) {}
}
