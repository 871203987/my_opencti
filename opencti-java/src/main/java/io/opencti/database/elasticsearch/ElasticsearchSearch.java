package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TrackHits;
import io.opencti.database.elasticsearch.model.SearchHit;
import io.opencti.database.elasticsearch.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Elasticsearch搜索操作类
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供搜索、分页、列表等功能
 */
@Component
public class ElasticsearchSearch {

    private static final Logger log = LoggerFactory.getLogger(ElasticsearchSearch.class);

    private final ElasticsearchClient client;
    private final ElasticsearchConfig config;

    public ElasticsearchSearch(ElasticsearchClient client, ElasticsearchConfig config) {
        this.client = client;
        this.config = config;
    }

    /**
     * 按ID查找文档
     * 重写自: engine.ts - elFindByIds() (行1720-1844)
     * 
     * @param indices 索引列表
     * @param ids ID列表
     * @param opts 选项
     * @return 文档列表
     */
    public List<Map<String, Object>> elFindByIds(List<String> indices, List<String> ids, SearchOptions opts) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        
        try {
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.filter(QueryBuilder.ids(ids));
            
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                    .index(indices)
                    .query(queryBuilder.build())
                    .size(ids.size());
            
            if (opts != null && opts.getTrackTotalHits()) {
                requestBuilder.trackTotalHits(TrackHits.of(t -> t.enabled(true)));
            }
            
            SearchResponse<Map> response = client.search(requestBuilder.build(), Map.class);
            
            return convertHits(response.hits().hits());
        } catch (Exception e) {
            log.error("[SEARCH] Find by IDs failed", e);
            throw new RuntimeException("Find by IDs failed", e);
        }
    }

    /**
     * 按ID加载单个文档
     * 重写自: engine.ts - elLoadById() (行1845-1861)
     * 
     * @param indices 索引列表
     * @param id 文档ID
     * @param opts 选项
     * @return 文档内容
     */
    public Map<String, Object> elLoadById(List<String> indices, String id, SearchOptions opts) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        
        List<Map<String, Object>> results = elFindByIds(indices, List.of(id), opts);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * 批量获取ID
     * 重写自: engine.ts - elBatchIds() (行1862-1871)
     * 
     * @param indices 索引列表
     * @param ids ID列表
     * @return 文档列表
     */
    public List<Map<String, Object>> elBatchIds(List<String> indices, List<String> ids) {
        return elFindByIds(indices, ids, null);
    }

    /**
     * 分页查询
     * 重写自: engine.ts - elPaginate() (行2919-3035)
     * 
     * @param indices 索引列表
     * @param opts 分页选项
     * @return 搜索响应
     */
    public io.opencti.database.elasticsearch.model.SearchResponse elPaginate(List<String> indices, PaginateOptions opts) {
        try {
            SearchRequest.Builder requestBuilder = new SearchRequest.Builder()
                    .index(indices);
            
            // 构建查询
            if (opts != null && opts.getQuery() != null) {
                requestBuilder.query(opts.getQuery());
            } else {
                requestBuilder.query(QueryBuilder.matchAll());
            }
            
            // 设置分页
            int first = opts != null && opts.getFirst() != null ? opts.getFirst() : ElasticsearchConstants.ES_DEFAULT_PAGINATION;
            int after = opts != null && opts.getAfter() != null ? opts.getAfter() : 0;
            requestBuilder.from(after).size(first);
            
            // 设置排序
            if (opts != null && opts.getOrderBy() != null) {
                SortOrder order = opts.getOrderAsc() != null && opts.getOrderAsc() ? SortOrder.Asc : SortOrder.Desc;
                requestBuilder.sort(s -> s.field(f -> f.field(opts.getOrderBy()).order(order)));
            }
            
            // 设置trackTotalHits
            if (opts != null && opts.getTrackTotalHits()) {
                requestBuilder.trackTotalHits(TrackHits.of(t -> t.enabled(true)));
            }
            
            // 设置聚合
            if (opts != null && opts.getAggregations() != null) {
                requestBuilder.aggregations(opts.getAggregations());
            }
            
            // 执行搜索
            SearchResponse<Map> response = client.search(requestBuilder.build(), Map.class);
            
            // 转换结果
            List<SearchHit> hits = convertToSearchHits(response.hits().hits());
            long total = response.hits().total() != null ? response.hits().total().value() : 0;
            
            io.opencti.database.elasticsearch.model.SearchResponse searchResponse = 
                    new io.opencti.database.elasticsearch.model.SearchResponse(hits, total, response.hits().maxScore());
            searchResponse.setScrollId(response.scrollId());
            searchResponse.setTook(response.took());
            searchResponse.setTimedOut(response.timedOut());
            
            return searchResponse;
        } catch (Exception e) {
            log.error("[SEARCH] Paginate failed", e);
            throw new RuntimeException("Paginate failed", e);
        }
    }

    /**
     * 列表查询
     * 重写自: engine.ts - elList() (行3046-3055)
     * 
     * @param indices 索引列表
     * @param opts 列表选项
     * @return 文档列表
     */
    public List<Map<String, Object>> elList(List<String> indices, ListOptions opts) {
        PaginateOptions paginateOpts = new PaginateOptions();
        if (opts != null) {
            paginateOpts.setQuery(opts.getQuery());
            paginateOpts.setFirst(opts.getFirst());
            paginateOpts.setAfter(opts.getAfter());
            paginateOpts.setOrderBy(opts.getOrderBy());
            paginateOpts.setOrderAsc(opts.getOrderAsc());
        }
        
        io.opencti.database.elasticsearch.model.SearchResponse response = elPaginate(indices, paginateOpts);
        return response.getHits().stream()
                .map(SearchHit::getSource)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 连接查询（用于GraphQL连接）
     * 重写自: engine.ts - elConnection() (行3036-3045)
     * 
     * @param indices 索引列表
     * @param opts 选项
     * @return 连接响应
     */
    public ConnectionResponse elConnection(List<String> indices, ConnectionOptions opts) {
        PaginateOptions paginateOpts = new PaginateOptions();
        if (opts != null) {
            paginateOpts.setQuery(opts.getQuery());
            paginateOpts.setFirst(opts.getFirst());
            paginateOpts.setAfter(opts.getAfter());
            paginateOpts.setOrderBy(opts.getOrderBy());
            paginateOpts.setOrderAsc(opts.getOrderAsc());
            paginateOpts.setTrackTotalHits(true);
        }
        
        io.opencti.database.elasticsearch.model.SearchResponse response = elPaginate(indices, paginateOpts);
        
        ConnectionResponse connection = new ConnectionResponse();
        connection.setEdges(response.getHits().stream()
                .map(hit -> new Edge(hit.getSource(), hit.getId()))
                .collect(Collectors.toList()));
        connection.setPageInfo(new PageInfo(
                response.getHits().isEmpty() ? null : response.getHits().get(0).getId(),
                response.getHits().isEmpty() ? null : response.getHits().get(response.getHits().size() - 1).getId(),
                false,
                false
        ));
        connection.setTotalCount(response.getTotal());
        
        return connection;
    }

    /**
     * 按字段加载
     * 重写自: engine.ts - elLoadBy() (行3056-3076)
     * 
     * @param indices 索引列表
     * @param field 字段名
     * @param value 字段值
     * @param opts 选项
     * @return 文档内容
     */
    public Map<String, Object> elLoadBy(List<String> indices, String field, String value, SearchOptions opts) {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.filter(QueryBuilder.term(field, value));
        
        ListOptions listOpts = new ListOptions();
        listOpts.setQuery(queryBuilder.build());
        listOpts.setFirst(1);
        
        List<Map<String, Object>> results = elList(indices, listOpts);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * 转换ES Hit列表为Map列表
     */
    private List<Map<String, Object>> convertHits(List<Hit<Map>> hits) {
        List<Map<String, Object>> results = new ArrayList<>();
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
     * 转换ES Hit列表为SearchHit列表
     */
    private List<SearchHit> convertToSearchHits(List<Hit<Map>> hits) {
        List<SearchHit> results = new ArrayList<>();
        for (Hit<Map> hit : hits) {
            SearchHit searchHit = new SearchHit();
            searchHit.setId(hit.id());
            searchHit.setIndex(hit.index());
            searchHit.setScore(hit.score());
            searchHit.setSource(hit.source());
            if (hit.highlight() != null) {
                searchHit.setHighlight(new HashMap<>(hit.highlight()));
            }
            if (hit.sort() != null) {
                searchHit.setSortValues(hit.sort().toArray());
            }
            results.add(searchHit);
        }
        return results;
    }

    // ==================== 选项类 ====================

    public static class SearchOptions {
        private Boolean trackTotalHits = false;

        public Boolean getTrackTotalHits() {
            return trackTotalHits;
        }

        public void setTrackTotalHits(Boolean trackTotalHits) {
            this.trackTotalHits = trackTotalHits;
        }
    }

    public static class PaginateOptions extends SearchOptions {
        private co.elastic.clients.elasticsearch._types.query_dsl.Query query;
        private Integer first;
        private Integer after;
        private String orderBy;
        private Boolean orderAsc = true;
        private Map<String, Aggregation> aggregations;

        public co.elastic.clients.elasticsearch._types.query_dsl.Query getQuery() {
            return query;
        }

        public void setQuery(co.elastic.clients.elasticsearch._types.query_dsl.Query query) {
            this.query = query;
        }

        public Integer getFirst() {
            return first;
        }

        public void setFirst(Integer first) {
            this.first = first;
        }

        public Integer getAfter() {
            return after;
        }

        public void setAfter(Integer after) {
            this.after = after;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public Boolean getOrderAsc() {
            return orderAsc;
        }

        public void setOrderAsc(Boolean orderAsc) {
            this.orderAsc = orderAsc;
        }

        public Map<String, Aggregation> getAggregations() {
            return aggregations;
        }

        public void setAggregations(Map<String, Aggregation> aggregations) {
            this.aggregations = aggregations;
        }
    }

    public static class ListOptions extends SearchOptions {
        private co.elastic.clients.elasticsearch._types.query_dsl.Query query;
        private Integer first;
        private Integer after;
        private String orderBy;
        private Boolean orderAsc = true;

        public co.elastic.clients.elasticsearch._types.query_dsl.Query getQuery() {
            return query;
        }

        public void setQuery(co.elastic.clients.elasticsearch._types.query_dsl.Query query) {
            this.query = query;
        }

        public Integer getFirst() {
            return first;
        }

        public void setFirst(Integer first) {
            this.first = first;
        }

        public Integer getAfter() {
            return after;
        }

        public void setAfter(Integer after) {
            this.after = after;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public Boolean getOrderAsc() {
            return orderAsc;
        }

        public void setOrderAsc(Boolean orderAsc) {
            this.orderAsc = orderAsc;
        }
    }

    public static class ConnectionOptions extends SearchOptions {
        private co.elastic.clients.elasticsearch._types.query_dsl.Query query;
        private Integer first;
        private Integer after;
        private String orderBy;
        private Boolean orderAsc = true;

        public co.elastic.clients.elasticsearch._types.query_dsl.Query getQuery() {
            return query;
        }

        public void setQuery(co.elastic.clients.elasticsearch._types.query_dsl.Query query) {
            this.query = query;
        }

        public Integer getFirst() {
            return first;
        }

        public void setFirst(Integer first) {
            this.first = first;
        }

        public Integer getAfter() {
            return after;
        }

        public void setAfter(Integer after) {
            this.after = after;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public Boolean getOrderAsc() {
            return orderAsc;
        }

        public void setOrderAsc(Boolean orderAsc) {
            this.orderAsc = orderAsc;
        }
    }

    // ==================== 响应类 ====================

    public static class ConnectionResponse {
        private List<Edge> edges;
        private PageInfo pageInfo;
        private long totalCount;

        public List<Edge> getEdges() {
            return edges;
        }

        public void setEdges(List<Edge> edges) {
            this.edges = edges;
        }

        public PageInfo getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfo pageInfo) {
            this.pageInfo = pageInfo;
        }

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }
    }

    public static class Edge {
        private Map<String, Object> node;
        private String cursor;

        public Edge(Map<String, Object> node, String cursor) {
            this.node = node;
            this.cursor = cursor;
        }

        public Map<String, Object> getNode() {
            return node;
        }

        public void setNode(Map<String, Object> node) {
            this.node = node;
        }

        public String getCursor() {
            return cursor;
        }

        public void setCursor(String cursor) {
            this.cursor = cursor;
        }
    }

    public static class PageInfo {
        private String startCursor;
        private String endCursor;
        private boolean hasNextPage;
        private boolean hasPreviousPage;

        public PageInfo(String startCursor, String endCursor, boolean hasNextPage, boolean hasPreviousPage) {
            this.startCursor = startCursor;
            this.endCursor = endCursor;
            this.hasNextPage = hasNextPage;
            this.hasPreviousPage = hasPreviousPage;
        }

        public String getStartCursor() {
            return startCursor;
        }

        public void setStartCursor(String startCursor) {
            this.startCursor = startCursor;
        }

        public String getEndCursor() {
            return endCursor;
        }

        public void setEndCursor(String endCursor) {
            this.endCursor = endCursor;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }
    }
}
