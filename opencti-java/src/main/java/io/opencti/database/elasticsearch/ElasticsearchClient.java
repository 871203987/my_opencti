package io.opencti.database.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

import java.util.List;
import java.util.Map;

/**
 * Elasticsearch客户端接口
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供ES/OpenSearch核心操作，包括:
 * - 引擎初始化和版本检测
 * - 原始搜索、索引、删除操作
 * - 批量操作
 * - 健康检查
 */
public interface ElasticsearchClient {

    /**
     * 初始化搜索引擎
     * 重写自: engine.ts - searchEngineInit() (行355-437)
     * 
     * 初始化ES/OpenSearch客户端，检测引擎类型和版本
     * @return 初始化是否成功
     */
    boolean searchEngineInit();

    /**
     * 获取搜索引擎版本
     * 重写自: engine.ts - searchEngineVersion() (行341-353)
     * 
     * @return 引擎信息 (platform, version)
     */
    EngineVersion searchEngineVersion();

    /**
     * 检查引擎是否存活
     * 重写自: engine.ts - isEngineAlive() (行4672-4690)
     * 
     * @return 引擎是否存活
     */
    boolean isEngineAlive();

    /**
     * 是否启用运行时排序
     * 重写自: engine.ts - isRuntimeSortEnable() (行438)
     * 
     * @return 是否启用运行时排序
     */
    boolean isRuntimeSortEnable();

    /**
     * 是否启用附件处理器
     * 重写自: engine.ts - isAttachmentProcessorEnabled() (行282-284)
     * 
     * @return 是否启用附件处理器
     */
    boolean isAttachmentProcessorEnabled();

    /**
     * 获取引擎平台类型
     * @return 引擎平台 (elk/opensearch)
     */
    String getEnginePlatform();

    /**
     * 获取引擎版本号
     * @return 版本号字符串
     */
    String getEngineVersion();

    /**
     * 原始搜索
     * 重写自: engine.ts - elRawSearch() (行440-460)
     * 
     * @param query 查询条件
     * @return 搜索响应
     */
    SearchResponse<Map> elRawSearch(Map<String, Object> query);

    /**
     * 原始获取
     * 重写自: engine.ts - elRawGet() (行462-469)
     * 
     * @param id 文档ID
     * @param index 索引名
     * @return 文档内容
     */
    Map<String, Object> elRawGet(String id, String index);

    /**
     * 原始索引
     * 重写自: engine.ts - elRawIndex() (行470-477)
     * 
     * @param args 索引参数
     * @return 索引结果
     */
    Map<String, Object> elRawIndex(Map<String, Object> args);

    /**
     * 原始删除
     * 重写自: engine.ts - elRawDelete() (行478-485)
     * 
     * @param args 删除参数
     * @return 删除结果
     */
    Map<String, Object> elRawDelete(Map<String, Object> args);

    /**
     * 按查询删除
     * 重写自: engine.ts - elRawDeleteByQuery() (行486-493)
     * 
     * @param query 查询条件
     * @return 删除结果
     */
    Map<String, Object> elRawDeleteByQuery(Map<String, Object> query);

    /**
     * 批量操作
     * 重写自: engine.ts - elRawBulk() (行494-501)
     * 
     * @param args 批量操作参数
     * @return 批量操作结果
     */
    Map<String, Object> elRawBulk(Map<String, Object> args);

    /**
     * 按查询更新
     * 重写自: engine.ts - elRawUpdateByQuery() (行502-509)
     * 
     * @param query 查询条件
     * @return 更新结果
     */
    Map<String, Object> elRawUpdateByQuery(Map<String, Object> query);

    /**
     * 重建索引
     * 重写自: engine.ts - elRawReindexByQuery() (行510-517)
     * 
     * @param query 重建索引参数
     * @return 重建结果
     */
    Map<String, Object> elRawReindexByQuery(Map<String, Object> query);

    /**
     * 原始计数
     * 重写自: engine.ts - elRawCount() (行3077-3088)
     * 
     * @param query 查询条件
     * @return 文档数量
     */
    long elRawCount(Map<String, Object> query);

    /**
     * 检查索引是否存在
     * 重写自: engine.ts - elIndexExists() (行728-735)
     * 
     * @param indexName 索引名
     * @return 是否存在
     */
    boolean elIndexExists(String indexName);

    /**
     * 创建索引
     * 重写自: engine.ts - elCreateIndex() (行1357-1375)
     * 
     * @param indexName 索引名
     * @param mappingProperties 映射属性
     * @return 创建结果
     */
    boolean elCreateIndex(String indexName, Map<String, Object> mappingProperties);

    /**
     * 删除索引
     * 重写自: engine.ts - elDeleteIndex() (行1342-1356)
     * 
     * @param indexName 索引名
     * @return 删除结果
     */
    boolean elDeleteIndex(String indexName);

    /**
     * 获取平台索引列表
     * 重写自: engine.ts - elPlatformIndices() (行745-753)
     * 
     * @return 索引列表
     */
    List<Map<String, Object>> elPlatformIndices();

    /**
     * 获取索引映射
     * 重写自: engine.ts - elPlatformMapping() (行754-761)
     * 
     * @param indexName 索引名
     * @return 映射信息
     */
    Map<String, Object> elPlatformMapping(String indexName);

    /**
     * 获取索引设置
     * 重写自: engine.ts - elIndexSetting() (行762-775)
     * 
     * @param indexName 索引名
     * @return 设置信息
     */
    Map<String, Object> elIndexSetting(String indexName);

    /**
     * 引擎版本信息
     */
    record EngineVersion(String platform, String version) {}
}
