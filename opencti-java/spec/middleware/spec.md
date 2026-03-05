# 数据中间件 (Middleware) 重写规范

## 1. 概述

### 1.1 目标
将 OpenCTI GraphQL 后端的 `database/middleware.js` 文件使用 Java 21 重写，保持与原项目逻辑和功能完全一致。

### 1.2 源文件信息
- **源文件路径**: `opencti-platform/opencti-graphql/src/database/middleware.js`
- **源文件行数**: ~3666 行
- **导出函数数量**: 48 个
- **复杂度**: ⭐⭐⭐⭐⭐ (最高)

### 1.3 重写目录
- **目标目录**: `opencti-java/src/main/java/io/opencti/database/middleware/`

## 2. 功能模块划分

根据源码分析，middleware.js 可划分为以下功能模块：

### 2.1 加载器模块 (Loader)
负责从 Elasticsearch 加载数据，包括批量加载、依赖解析等。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| batchLoader | 283-296 | ⭐⭐ |
| loadElementMetaDependencies | 338-407 | ⭐⭐⭐⭐ |
| loadElementsWithDependencies | 409-485 | ⭐⭐⭐⭐ |
| storeLoadByIdsWithRefs | 503-507 | ⭐⭐ |
| storeLoadByIdWithRefs | 508-511 | ⭐⭐ |
| stixLoadById | 512-516 | ⭐⭐ |
| stixLoadByIds | 531-546 | ⭐⭐⭐ |
| stixLoadByFilters | 560-563 | ⭐⭐ |

### 2.2 图形统计模块 (Graphics)
负责时间序列和分布统计查询。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| timeSeriesHistory | 630-634 | ⭐⭐ |
| timeSeriesEntities | 635-640 | ⭐⭐ |
| timeSeriesRelations | 641-647 | ⭐⭐ |
| distributionHistory | 648-682 | ⭐⭐⭐ |
| distributionEntities | 683-721 | ⭐⭐⭐ |
| distributionRelations | 722-742 | ⭐⭐⭐ |

### 2.3 输入解析模块 (Input Resolution)
负责解析和验证输入数据，解析引用关系。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| validateCreatedBy | 769-780 | ⭐⭐ |
| inputResolveRefs | 782-988 | ⭐⭐⭐⭐⭐ |
| transformPatchToInput | 1635-1648 | ⭐⭐ |

### 2.4 更新模块 (Update)
负责实体属性的更新操作。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| updateAttributeMetaResolved | 2160-2552 | ⭐⭐⭐⭐⭐ |
| updateAttributeFromLoadedWithRefs | 2554-2580 | ⭐⭐⭐ |
| updateAttribute | 2598-2613 | ⭐⭐⭐ |
| patchAttribute | 2615-2618 | ⭐⭐ |
| generateUpdateMessage | 2024-2063 | ⭐⭐⭐ |
| buildChanges | 2088-2158 | ⭐⭐⭐ |

### 2.5 创建模块 (Create)
负责实体和关系的创建操作。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| createEntity | 3390-3401 | ⭐⭐⭐⭐ |
| createEntityRaw | 3168-3388 | ⭐⭐⭐⭐⭐ |
| createRelation | 3111-3114 | ⭐⭐ |
| createRelationRaw | 2919-3110 | ⭐⭐⭐⭐⭐ |
| createRelations | 3141-3150 | ⭐⭐ |
| createInferredEntity | 3403-3416 | ⭐⭐⭐ |
| createInferredRelation | 3115-3139 | ⭐⭐⭐ |

### 2.6 删除模块 (Delete)
负责实体和关系的删除操作。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| internalDeleteElementById | 3441-3552 | ⭐⭐⭐⭐⭐ |
| deleteElementById | 3553-3560 | ⭐⭐ |
| deleteInferredRuleElement | 3561-3615 | ⭐⭐⭐⭐ |
| deleteRelationsByFromAndTo | 3616-3666 | ⭐⭐⭐ |

### 2.7 合并模块 (Merge)
负责实体的合并操作。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| mergeEntities | 1582-1633 | ⭐⭐⭐⭐⭐ |
| hashMergeValidation | 1206-1221 | ⭐⭐ |

### 2.8 规则模块 (Rules)
负责推理规则相关的数据处理。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| createRuleDataPatch | 2638-2695 | ⭐⭐⭐ |
| upsertEntityRule | 2711-2723 | ⭐⭐⭐ |
| upsertRelationRule | 2724-2744 | ⭐⭐⭐ |

### 2.9 访问控制模块 (Access Control)
负责请求访问相关功能。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| canRequestAccess | 252-280 | ⭐⭐⭐ |

### 2.10 工具方法模块 (Utilities)
各种辅助工具方法。

| 功能 | 源码行号 | 复杂度 |
|------|----------|--------|
| updatedInputsToData | 1038-1048 | ⭐⭐ |
| mergeInstanceWithInputs | 1049-1055 | ⭐⭐ |
| buildRestrictedEntity | 580-597 | ⭐⭐ |

## 3. 子任务划分

由于代码量超过3500行，根据重写原则第10条，必须分多个子任务完成。建议按以下顺序划分：

### 子任务 1: 基础模型和接口定义 (预计 ~500 行)
- MiddlewareContext.java - 中间件上下文
- MiddlewareResult.java - 操作结果封装
- InputResolveResult.java - 输入解析结果
- UpdateInput.java - 更新输入模型
- CreateInput.java - 创建输入模型
- DeleteOptions.java - 删除选项

### 子任务 2: 加载器模块 (预计 ~600 行)
- MiddlewareLoader.java - 加载器接口
- MiddlewareLoaderImpl.java - 加载器实现
  - batchLoader()
  - loadElementMetaDependencies()
  - loadElementsWithDependencies()
  - storeLoadByIdsWithRefs()
  - storeLoadByIdWithRefs()

### 子任务 3: STIX 加载模块 (预计 ~400 行)
- StixLoader.java - STIX 加载器
  - stixLoadById()
  - stixLoadByIds()
  - stixLoadByFilters()
  - stixBundleByIdStringify()
  - stixLoadByIdStringify()

### 子任务 4: 图形统计模块 (预计 ~400 行)
- MiddlewareStatistics.java - 统计模块
  - timeSeriesHistory()
  - timeSeriesEntities()
  - timeSeriesRelations()
  - distributionHistory()
  - distributionEntities()
  - distributionRelations()

### 子任务 5: 输入解析模块 (预计 ~700 行)
- InputResolver.java - 输入解析器
  - validateCreatedBy()
  - inputResolveRefs()
  - transformPatchToInput()
  - depsKeys()
  - idVocabulary()

### 子任务 6: 更新模块 (预计 ~900 行)
- MiddlewareUpdater.java - 更新模块
  - updateAttributeMetaResolved()
  - updateAttributeFromLoadedWithRefs()
  - updateAttribute()
  - patchAttribute()
  - patchAttributeFromLoadedWithRefs()
  - generateUpdateMessage()
  - buildChanges()

### 子任务 7: 创建模块 (预计 ~800 行)
- MiddlewareCreator.java - 创建模块
  - createEntity()
  - createEntityRaw()
  - createRelation()
  - createRelationRaw()
  - createRelations()
  - createInferredEntity()
  - createInferredRelation()
  - getExistingEntities()
  - getExistingRelations()

### 子任务 8: 删除模块 (预计 ~500 行)
- MiddlewareDeleter.java - 删除模块
  - internalDeleteElementById()
  - deleteElementById()
  - deleteInferredRuleElement()
  - deleteRelationsByFromAndTo()

### 子任务 9: 合并模块 (预计 ~600 行)
- MiddlewareMerger.java - 合并模块
  - mergeEntities()
  - mergeEntitiesRaw()
  - hashMergeValidation()
  - filterTargetByExisting()

### 子任务 10: 规则和访问控制模块 (预计 ~400 行)
- MiddlewareRules.java - 规则模块
  - createRuleDataPatch()
  - upsertEntityRule()
  - upsertRelationRule()
- MiddlewareAccess.java - 访问控制
  - canRequestAccess()
  - buildRestrictedEntity()

### 子任务 11: 工具方法和整合 (预计 ~400 行)
- MiddlewareUtils.java - 工具方法
  - updatedInputsToData()
  - mergeInstanceWithInputs()
  - partialInstanceWithInputs()
- MiddlewareService.java - 主服务接口
- MiddlewareServiceImpl.java - 主服务实现

## 4. 依赖关系

### 4.1 内部依赖
- `database/elasticsearch/` - ES 引擎操作
- `database/redis/` - Redis 缓存和锁
- `database/rabbitmq/` - 消息队列
- `database/storage/` - 文件存储
- `common/types/` - STIX 类型定义
- `common/utils/` - 工具类
- `common/exception/` - 异常类

### 4.2 外部依赖
- Spring Boot 3.3.x
- Elasticsearch Java Client 8.x
- Spring Data Redis (Lettuce)

## 5. 关键技术点

### 5.1 分布式锁
源码使用 Redis 分布式锁确保并发操作的数据一致性：
```java
// 原文件: middleware.js:2984-2985
lock = await lockResources(participantIds, { draftId: getDraftContext(context, user) });
```

### 5.2 事件流
创建、更新、删除操作都会发布事件到 Redis Stream：
```java
// 原文件: middleware.js:3377
const event = await storeCreateEntityEvent(context, user, createdElement, dataMessage, opts);
```

### 5.3 Upsert 逻辑
创建时会检查是否存在相同实体，存在则更新：
```java
// 原文件: middleware.js:3245-3337
if (existingEntities.length > 0) {
  // 处理已存在实体的合并或更新逻辑
}
```

### 5.4 置信度控制
用户操作受置信度限制：
```java
// 原文件: middleware.js:2926-2927
const { confidenceLevelToApply } = controlCreateInputWithUserConfidence(user, input, relationshipType);
```

### 5.5 草稿支持
支持草稿工作区模式：
```java
// 原文件: middleware.js:2948-2949
const draftId = getDraftContext(context, user);
const draft = draftId ? await findDraftById(context, user, draftId) : null;
```

## 6. 测试策略

### 6.1 单元测试
- 每个子任务完成后编写对应单元测试
- 使用 Mockito 模拟依赖组件
- 测试覆盖率目标: 80%+

### 6.2 集成测试
- 使用 TestContainers 启动 ES/Redis 容器
- 测试完整的 CRUD 流程

## 7. 风险评估

| 风险 | 等级 | 应对措施 |
|------|------|----------|
| 复杂度高 | 🔴 高 | 分子任务逐步实现，每个子任务编译验证 |
| 依赖模块多 | 🟡 中 | 先确保依赖模块已完成并测试通过 |
| 并发控制复杂 | 🟡 中 | 仔细实现分布式锁逻辑 |
| 类型转换复杂 | 🟡 中 | 使用 Java 泛型和类型安全设计 |

## 8. 预估工作量

| 子任务 | 预估代码行数 | 预估时间 |
|--------|--------------|----------|
| 子任务 1 | ~500 | 0.5天 |
| 子任务 2 | ~600 | 0.5天 |
| 子任务 3 | ~400 | 0.5天 |
| 子任务 4 | ~400 | 0.5天 |
| 子任务 5 | ~700 | 1天 |
| 子任务 6 | ~900 | 1天 |
| 子任务 7 | ~800 | 1天 |
| 子任务 8 | ~500 | 0.5天 |
| 子任务 9 | ~600 | 0.5天 |
| 子任务 10 | ~400 | 0.5天 |
| 子任务 11 | ~400 | 0.5天 |
| **总计** | **~5600** | **7天** |
