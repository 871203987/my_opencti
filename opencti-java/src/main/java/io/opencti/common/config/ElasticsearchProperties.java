package io.opencti.common.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 重写自: opencti-platform/opencti-graphql/config/default.json
 * Elasticsearch数据库配置属性
 * 
 * 原始配置项:
 * - index_prefix: 索引前缀
 * - url: 连接URL
 * - engine_selector: 引擎选择器
 * - engine_check: 引擎检查开关
 * - index_creation_pattern: 索引创建模式
 * - search_wildcard_prefix: 搜索通配符前缀
 * - search_fuzzy: 模糊搜索
 * - max_pagination_result: 最大分页结果
 * - default_pagination_result: 默认分页结果
 * - max_bulk_operations: 最大批量操作
 * - max_runtime_resolutions: 最大运行时解析
 * - max_concurrency: 最大并发数
 */
public record ElasticsearchProperties(
    @NotBlank String url,
    String username,
    String password,
    @NotBlank String indexPrefix,
    List<String> urls,
    @Min(1) int maxRetries,
    @Min(1) long requestTimeout,
    @Min(1) long pingTimeout,
    @Min(1) long scrollDuration,
    @Min(1) int scrollSize,
    @Min(1) int maxConcurrentSearches,
    @Min(1) int maxConcurrentNormalizations,
    @Min(1) int bulkMaxSize,
    @Min(1) int bulkMaxTime,
    @Min(1) int bulkMaxRetry,
    boolean sslVerification,
    String sslCert,
    String sslKey,
    String sslCa,
    boolean useCurl,
    String engineSelector,
    @Min(1) int maxConnectionRetries,
    @Min(1) int maxResultWindow,
    // 新增配置项（与 TypeScript 源码一致）
    boolean engineCheck,
    String indexCreationPattern,
    boolean searchWildcardPrefix,
    boolean searchFuzzy,
    @Min(1) int maxRuntimeResolutions
) {
    /**
     * 获取索引创建模式
     * 默认值: "-000001"
     */
    public String getIndexCreationPattern() {
        return indexCreationPattern != null ? indexCreationPattern : "-000001";
    }
}
