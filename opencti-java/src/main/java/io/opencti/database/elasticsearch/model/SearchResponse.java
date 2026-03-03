package io.opencti.database.elasticsearch.model;

import java.util.List;

/**
 * ES搜索响应
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 表示搜索操作的完整响应
 */
public class SearchResponse {

    private List<SearchHit> hits;
    private long total;
    private Double maxScore;
    private String scrollId;
    private Map<String, Object> aggregations;
    private boolean timedOut;
    private long took;
    private ShardsInfo shards;

    public SearchResponse() {
    }

    public SearchResponse(List<SearchHit> hits, long total, Double maxScore) {
        this.hits = hits;
        this.total = total;
        this.maxScore = maxScore;
    }

    public List<SearchHit> getHits() {
        return hits;
    }

    public void setHits(List<SearchHit> hits) {
        this.hits = hits;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }

    public Map<String, Object> getAggregations() {
        return aggregations;
    }

    public void setAggregations(Map<String, Object> aggregations) {
        this.aggregations = aggregations;
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    public long getTook() {
        return took;
    }

    public void setTook(long took) {
        this.took = took;
    }

    public ShardsInfo getShards() {
        return shards;
    }

    public void setShards(ShardsInfo shards) {
        this.shards = shards;
    }

    /**
     * 是否有结果
     */
    public boolean hasHits() {
        return hits != null && !hits.isEmpty();
    }

    /**
     * 获取命中数量
     */
    public int getHitCount() {
        return hits != null ? hits.size() : 0;
    }

    /**
     * 分片信息
     */
    public static class ShardsInfo {
        private int total;
        private int successful;
        private int failed;
        private int skipped;

        public ShardsInfo() {
        }

        public ShardsInfo(int total, int successful, int failed) {
            this.total = total;
            this.successful = successful;
            this.failed = failed;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSuccessful() {
            return successful;
        }

        public void setSuccessful(int successful) {
            this.successful = successful;
        }

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }

        public int getSkipped() {
            return skipped;
        }

        public void setSkipped(int skipped) {
            this.skipped = skipped;
        }

        public boolean isComplete() {
            return failed == 0 && successful == total;
        }
    }
}
