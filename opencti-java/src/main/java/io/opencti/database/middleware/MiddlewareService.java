package io.opencti.database.middleware;

import io.opencti.database.middleware.model.*;

import java.util.List;
import java.util.Map;

/**
 * 中间件服务接口
 * 原文件: database/middleware.js
 * 
 * 提供数据访问和操作的核心接口，包括实体和关系的CRUD操作。
 */
public interface MiddlewareService {

    // ==================== 加载操作 ====================

    /**
     * 批量加载器
     * 原文件: middleware.js:283-296 batchLoader
     */
    <T> DataLoader<T> batchLoader(Loader<T> loader, MiddlewareContext context, Object user);

    /**
     * 加载实体
     * 原文件: middleware.js:328-334 loadEntity
     */
    Map<String, Object> loadEntity(MiddlewareContext context, Object user, 
            List<String> entityTypes, Map<String, Object> args);

    /**
     * 加载元素及其依赖
     * 原文件: middleware.js:409-485 loadElementsWithDependencies
     */
    List<Map<String, Object>> loadElementsWithDependencies(MiddlewareContext context, Object user,
            List<Map<String, Object>> elements, Map<String, Object> opts);

    /**
     * 根据ID加载并解析引用
     * 原文件: middleware.js:503-507 storeLoadByIdsWithRefs
     */
    List<Map<String, Object>> storeLoadByIdsWithRefs(MiddlewareContext context, Object user,
            List<String> ids, Map<String, Object> opts);

    /**
     * 根据ID加载单个元素并解析引用
     * 原文件: middleware.js:508-511 storeLoadByIdWithRefs
     */
    Map<String, Object> storeLoadByIdWithRefs(MiddlewareContext context, Object user,
            String id, Map<String, Object> opts);

    // ==================== STIX 加载 ====================

    /**
     * 根据ID加载STIX对象
     * 原文件: middleware.js:512-516 stixLoadById
     */
    Object stixLoadById(MiddlewareContext context, Object user, String id, Map<String, Object> opts);

    /**
     * 批量加载STIX对象
     * 原文件: middleware.js:531-546 stixLoadByIds
     */
    List<Object> stixLoadByIds(MiddlewareContext context, Object user, 
            List<String> ids, Map<String, Object> opts);

    /**
     * 根据过滤条件加载STIX对象
     * 原文件: middleware.js:560-563 stixLoadByFilters
     */
    List<Object> stixLoadByFilters(MiddlewareContext context, Object user,
            List<String> types, Map<String, Object> args);

    // ==================== 统计操作 ====================

    /**
     * 实体时间序列
     * 原文件: middleware.js:635-640 timeSeriesEntities
     */
    List<Map<String, Object>> timeSeriesEntities(MiddlewareContext context, Object user,
            List<String> types, Map<String, Object> args);

    /**
     * 关系时间序列
     * 原文件: middleware.js:641-647 timeSeriesRelations
     */
    List<Map<String, Object>> timeSeriesRelations(MiddlewareContext context, Object user,
            Map<String, Object> args);

    /**
     * 实体分布统计
     * 原文件: middleware.js:683-721 distributionEntities
     */
    List<Map<String, Object>> distributionEntities(MiddlewareContext context, Object user,
            List<String> types, Map<String, Object> args);

    /**
     * 关系分布统计
     * 原文件: middleware.js:722-742 distributionRelations
     */
    List<Map<String, Object>> distributionRelations(MiddlewareContext context, Object user,
            Map<String, Object> args);

    // ==================== 创建操作 ====================

    /**
     * 创建实体
     * 原文件: middleware.js:3390-3401 createEntity
     */
    <T> MiddlewareResult<T> createEntity(MiddlewareContext context, Object user,
            Map<String, Object> input, String type, CreateOptions opts);

    /**
     * 创建关系
     * 原文件: middleware.js:3111-3114 createRelation
     */
    <T> T createRelation(MiddlewareContext context, Object user,
            Map<String, Object> input, CreateOptions opts);

    /**
     * 创建关系（原始）
     * 原文件: middleware.js:2919-3110 createRelationRaw
     */
    <T> MiddlewareResult<T> createRelationRaw(MiddlewareContext context, Object user,
            Map<String, Object> rawInput, CreateOptions opts);

    /**
     * 批量创建关系
     * 原文件: middleware.js:3141-3150 createRelations
     */
    <T> List<T> createRelations(MiddlewareContext context, Object user,
            List<Map<String, Object>> inputs, CreateOptions opts);

    // ==================== 更新操作 ====================

    /**
     * 更新属性
     * 原文件: middleware.js:2598-2613 updateAttribute
     */
    <T> MiddlewareResult<T> updateAttribute(MiddlewareContext context, Object user,
            String id, String type, List<UpdateInput> inputs, UpdateOptions opts);

    /**
     * 补丁更新属性
     * 原文件: middleware.js:2615-2618 patchAttribute
     */
    <T> MiddlewareResult<T> patchAttribute(MiddlewareContext context, Object user,
            String id, String type, Map<String, Object> patch, UpdateOptions opts);

    // ==================== 删除操作 ====================

    /**
     * 删除元素
     * 原文件: middleware.js:3553-3560 deleteElementById
     */
    <T> T deleteElementById(MiddlewareContext context, Object user,
            String id, String type, DeleteOptions opts);

    /**
     * 内部删除元素
     * 原文件: middleware.js:3441-3552 internalDeleteElementById
     */
    <T> MiddlewareResult<T> internalDeleteElementById(MiddlewareContext context, Object user,
            String id, String type, DeleteOptions opts);

    /**
     * 根据起止点删除关系
     * 原文件: middleware.js:3616-3666 deleteRelationsByFromAndTo
     */
    Map<String, Object> deleteRelationsByFromAndTo(MiddlewareContext context, Object user,
            String fromId, String toId, String relationshipType, String scopeType, DeleteOptions opts);

    // ==================== 合并操作 ====================

    /**
     * 合并实体
     * 原文件: middleware.js:1582-1633 mergeEntities
     */
    <T> T mergeEntities(MiddlewareContext context, Object user,
            String targetEntityId, List<String> sourceEntityIds, Map<String, Object> opts);

    // ==================== 输入解析 ====================

    /**
     * 解析输入引用
     * 原文件: middleware.js:782-988 inputResolveRefs
     */
    Map<String, Object> inputResolveRefs(MiddlewareContext context, Object user,
            Map<String, Object> input, String type, Object entitySetting);

    /**
     * 验证创建者
     * 原文件: middleware.js:769-780 validateCreatedBy
     */
    void validateCreatedBy(MiddlewareContext context, Object user, String createdById);

    // ==================== 访问控制 ====================

    /**
     * 检查是否可请求访问
     * 原文件: middleware.js:252-280 canRequestAccess
     */
    List<Map<String, Object>> canRequestAccess(MiddlewareContext context, Object user,
            List<Map<String, Object>> elements);

    /**
     * 数据加载器接口
     */
    @FunctionalInterface
    interface Loader<T> {
        List<T> load(MiddlewareContext context, Object user, List<T> elements);
    }

    /**
     * 数据加载器
     */
    interface DataLoader<T> {
        T load(T element);
    }
}
