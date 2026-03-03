# Elasticsearch 引擎模块重写计划

## 一、源码分析

### 1.1 源文件清单

| 文件 | 行数 | 说明 |
|------|------|------|
| `src/database/engine.ts` | 4690行 | 核心搜索引擎模块 |
| `src/database/utils.ts` | ~500行 | 数据库工具函数和索引常量 |
| `src/database/draft-engine.ts` | ~300行 | 草稿引擎扩展 |

**总计**: 约5490行TypeScript代码

### 1.2 engine.ts 功能模块分析

| 功能模块 | 行号范围 | 主要方法数 | 复杂度 |
|----------|----------|------------|--------|
| 常量和配置 | 1-280 | 15+ | ⭐⭐ |
| 引擎初始化 | 282-437 | 4 | ⭐⭐⭐⭐ |
| 原始操作 | 440-517 | 8 | ⭐⭐⭐ |
| 迁移操作 | 519-559 | 3 | ⭐⭐⭐ |
| 数据限制 | 561-726 | 2 | ⭐⭐⭐⭐ |
| 索引管理 | 728-1406 | 12 | ⭐⭐⭐⭐ |
| 映射生成 | 876-1267 | 5 | ⭐⭐⭐⭐ |
| 查询构建 | 1444-2554 | 10+ | ⭐⭐⭐⭐⭐ |
| 数据加载 | 1654-2008 | 6 | ⭐⭐⭐⭐ |
| 分页列表 | 2919-3104 | 4 | ⭐⭐⭐⭐ |
| 计数聚合 | 3077-3580 | 8 | ⭐⭐⭐⭐ |
| 索引操作 | 3633-3810 | 6 | ⭐⭐⭐ |
| 元素操作 | 3897-4672 | 12 | ⭐⭐⭐⭐⭐ |
| 统计健康 | 4672-4690 | 2 | ⭐⭐ |

### 1.3 核心导出函数清单

```typescript
// 引擎初始化
export const searchEngineInit = async (): Promise<boolean>
export const searchEngineVersion = async (): Promise<{ platform: string; version: string }>
export const isRuntimeSortEnable = (): boolean
export const isAttachmentProcessorEnabled = () => boolean

// 原始操作
export const elRawSearch = (context, user, types, query)
export const elRawGet = async (args: { id: string; index: string })
export const elRawIndex = async (args: any)
export const elRawDelete = async (args: any)
export const elRawDeleteByQuery = async (query: any)
export const elRawBulk = async (args: any)
export const elRawUpdateByQuery = async (query: any)
export const elRawReindexByQuery = async (query: any)
export const elRawCount = async (query: any): Promise<number>

// 索引管理
export const elIndexExists = async (indexName: string): Promise<boolean>
export const elIndexGetAlias = async (indexName: string)
export const elPlatformIndices = async ()
export const elPlatformMapping = async (index)
export const elIndexSetting = async (index)
export const elPlatformTemplates = async ()
export const elCreateIndex = async (index, mappingProperties)
export const elDeleteIndex = async (index: string)
export const elCreateIndices = async (indexesToCreate)
export const elDeleteIndices = async (indexesToDelete)
export const initializeSchema = async ()
export const elUpdateIndicesMappings = async ()
export const engineMappingGenerator = ()

// 数据限制
export const buildDataRestrictions = async (context, user, opts)

// 数据加载
export const elFindByIds = async <T>(context, user, ids, opts)
export const elLoadById = async <T>(context, user, id, opts)
export const elBatchIds = async <T>(context, user, ids, type, opts)
export const elBatchIdsWithRelCount = async <T>(context, user, ids, type, opts)
export const elConvertHits = async <T>(data)
export const elConvertHitsToMap = async <T>(hits, key)

// 分页列表
export const elPaginate = async <T>(context, user, indices, opts)
export const elList = async <T>(context, user, indices, opts)
export const elConnection = async <T>(context, user, indices, opts)
export const elLoadBy = async <T>(context, user, field, value, opts)

// 计数聚合
export const elCount = async (context, user, indices, opts)
export const elHistogramCount = async (context, user, indices, opts)
export const elAggregationCount = async (context, user, field, opts)
export const elAggregationRelationsCount = async (context, user, opts)
export const elAggregationNestedTermsWithFilter = async (context, user, opts)
export const elAggregationsList = async (context, user, opts)
export const elAttributeValues = async (context, user, field, opts)

// 索引操作
export const elBulk = async (args: any)
export const elIndex = async (indexName, documentBody, opts)
export const elUpdate = async (indexName, documentId, documentBody, retry)
export const elReplace = async (indexName, documentId, documentBody, opts)
export const elDelete = (indexName, documentId)

// 元素操作
export const elDeleteInstances = async <T>(context, user, elements)
export const elRemoveRelationConnection = async (context, user, opts)
export const computeDeleteElementsImpacts = async (context, user, elements)
export const elReindexElements = async (context, user, elements, indexName)
export const elIndexElements = async (context, user, elements)
export const elDeleteElements = async (context, user, elements, opts)
export const elUpdateElement = async (context, user, instance)
export const elUpdateRelationConnections = async (elements)
export const elUpdateEntityConnections = async (elements)

// 统计健康
export const getStats = (indices)
export const isEngineAlive = async ()
```

### 1.4 依赖关系

```
engine.ts
├── utils.ts (索引常量、分页工具)
├── cache.ts (缓存管理)
├── schema/*.ts (Schema定义)
├── config/conf.ts (配置)
├── config/errors.ts (错误定义)
└── types/*.ts (类型定义)
```

## 二、已有基础设施分析

### 2.1 已存在的配置类

**ElasticsearchProperties.java** (common/config/):
```java
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
    boolean engineCheck,
    String indexCreationPattern,
    boolean searchWildcardPrefix,
    boolean searchFuzzy,
    @Min(1) int maxRuntimeResolutions
)
```

### 2.2 已存在的依赖

**pom.xml**:
- `elasticsearch-java: 8.17.1` ✅ 已配置
- `spring-boot: 3.3.6` ✅ 已配置

### 2.3 已存在的异常类

- `DatabaseException.java` ✅ 已存在
- `ConfigurationException.java` ✅ 已存在
- `OpenCTIException.java` ✅ 已存在

## 三、Java实现计划

### 3.1 目录结构

```
src/main/java/io/opencti/database/elasticsearch/
├── ElasticsearchClient.java              # ES客户端接口
├── ElasticsearchClientImpl.java          # ES客户端实现
├── ElasticsearchConfig.java              # ES配置类(使用ElasticsearchProperties)
├── ElasticsearchConstants.java           # 常量定义
├── ElasticsearchIndices.java             # 索引管理
├── ElasticsearchMapping.java             # 映射管理
├── ElasticsearchSearch.java              # 搜索操作
├── ElasticsearchDocument.java            # 文档操作
├── ElasticsearchAggregation.java         # 聚合操作
├── ElasticsearchBulk.java                # 批量操作
├── query/
│   ├── QueryBuilder.java                 # 查询构建器
│   ├── FilterBuilder.java                # 过滤器构建器
│   ├── SortBuilder.java                  # 排序构建器
│   └── AggregationBuilder.java           # 聚合构建器
└── model/
    ├── SearchHit.java                    # 搜索结果
    ├── SearchResponse.java               # 搜索响应
    ├── BulkResponse.java                 # 批量响应
    └── IndexResult.java                  # 索引结果
```

### 3.2 子任务划分

根据重写原则第10条，代码量超过500行需分多个子任务完成。engine.ts有4690行，需要分成多个子任务。

#### 子任务1: 基础常量和配置

**预估代码量**: ~250行

| 文件 | 说明 |
|------|------|
| `ElasticsearchConstants.java` | 常量定义 (~200行) |
| `ElasticsearchConfig.java` | 配置类扩展 (~50行) |

**功能清单**:
- [ ] 索引名称常量 (ES_INDEX_PREFIX, INDEX_*, READ_INDEX_*)
- [ ] 分页常量 (ES_DEFAULT_PAGINATION, ES_MAX_PAGINATION)
- [ ] 操作常量 (BULK_TIMEOUT, ES_RETRY_ON_CONFLICT)
- [ ] 角色常量 (ROLE_FROM, ROLE_TO)
- [ ] 使用已有的ElasticsearchProperties

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比utils.ts常量定义

---

#### 子任务2: ES客户端初始化

**预估代码量**: ~400行

| 文件 | 说明 |
|------|------|
| `ElasticsearchClient.java` | 客户端接口 (~150行) |
| `ElasticsearchClientImpl.java` | 客户端实现 (~250行) |

**功能清单**:
- [ ] searchEngineInit - 引擎初始化
- [ ] searchEngineVersion - 获取版本
- [ ] isEngineAlive - 健康检查
- [ ] isRuntimeSortEnable - 运行时排序检查
- [ ] 支持Elasticsearch和OpenSearch双引擎检测

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts初始化部分 (行355-437)

---

#### 子任务3: 索引管理

**预估代码量**: ~450行

| 文件 | 说明 |
|------|------|
| `ElasticsearchIndices.java` | 索引管理 (~300行) |
| `ElasticsearchMapping.java` | 映射管理 (~150行) |

**功能清单**:
- [ ] elIndexExists - 索引是否存在 (行728-735)
- [ ] elCreateIndex - 创建索引 (行1357-1375)
- [ ] elDeleteIndex - 删除索引 (行1342-1356)
- [ ] elCreateIndices - 批量创建 (行1376-1391)
- [ ] elDeleteIndices - 批量删除 (行1406-1443)
- [ ] elPlatformIndices - 获取平台索引 (行745-753)
- [ ] elPlatformMapping - 获取映射 (行754-761)
- [ ] elIndexSetting - 获取设置 (行762-775)
- [ ] initializeSchema - 初始化Schema (行1392-1405)
- [ ] engineMappingGenerator - 映射生成器 (行962-964)

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts索引管理部分

---

#### 子任务4: 原始操作

**预估代码量**: ~350行

| 文件 | 说明 |
|------|------|
| `ElasticsearchDocument.java` | 文档操作基础 (~200行) |
| `ElasticsearchBulk.java` | 批量操作 (~150行) |

**功能清单**:
- [ ] elRawSearch - 原始搜索 (行440-460)
- [ ] elRawGet - 原始获取 (行462-469)
- [ ] elRawIndex - 原始索引 (行470-477)
- [ ] elRawDelete - 原始删除 (行478-485)
- [ ] elRawDeleteByQuery - 按查询删除 (行486-493)
- [ ] elRawBulk - 批量操作 (行494-501)
- [ ] elRawUpdateByQuery - 按查询更新 (行502-509)
- [ ] elRawCount - 原始计数 (行3077-3088)

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts原始操作部分

---

#### 子任务5: 查询构建器

**预估代码量**: ~500行

| 文件 | 说明 |
|------|------|
| `query/QueryBuilder.java` | 查询构建器 (~200行) |
| `query/FilterBuilder.java` | 过滤器构建器 (~150行) |
| `query/SortBuilder.java` | 排序构建器 (~150行) |

**功能清单**:
- [ ] buildDataRestrictions - 数据限制构建 (行618-726)
- [ ] buildUserMemberAccessFilter - 用户访问过滤 (行561-616)
- [ ] elGenerateFullTextSearchShould - 全文搜索 (行2008-2086)
- [ ] elGenerateFieldTextSearchShould - 字段搜索 (行2087-2135)
- [ ] buildLocalMustFilter - 本地过滤 (行2136-2300)
- [ ] computeQueryIndices - 计算查询索引 (行1444-1595)

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts查询构建部分

---

#### 子任务6: 搜索操作

**预估代码量**: ~450行

| 文件 | 说明 |
|------|------|
| `ElasticsearchSearch.java` | 搜索操作 (~300行) |
| `model/SearchHit.java` | 搜索结果 (~50行) |
| `model/SearchResponse.java` | 搜索响应 (~100行) |

**功能清单**:
- [ ] elFindByIds - 按ID查找 (行1720-1844)
- [ ] elLoadById - 按ID加载 (行1845-1861)
- [ ] elBatchIds - 批量ID (行1862-1871)
- [ ] elConvertHits - 转换结果 (行1678-1702)
- [ ] elConvertHitsToMap - 转换为Map (行1654-1677)
- [ ] specialElasticCharsEscape - 特殊字符转义 (行1703-1719)

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts搜索部分

---

#### 子任务7: 分页和列表

**预估代码量**: ~400行

| 文件 | 说明 |
|------|------|
| `ElasticsearchSearch.java` | 分页扩展 (~400行) |

**功能清单**:
- [ ] elPaginate - 分页查询 (行2919-3035)
- [ ] elList - 列表查询 (行3046-3055)
- [ ] elConnection - 连接查询 (行3036-3045)
- [ ] elLoadBy - 按字段加载 (行3056-3076)

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts分页部分

---

#### 子任务8: 聚合操作

**预估代码量**: ~400行

| 文件 | 说明 |
|------|------|
| `ElasticsearchAggregation.java` | 聚合操作 (~250行) |
| `query/AggregationBuilder.java` | 聚合构建器 (~150行) |

**功能清单**:
- [ ] elCount - 计数 (行3089-3103)
- [ ] elHistogramCount - 直方图计数 (行3104-3168)
- [ ] elAggregationCount - 聚合计数 (行3169-3262)
- [ ] elAggregationRelationsCount - 关系聚合计数 (行3263-3374)
- [ ] elAggregationNestedTermsWithFilter - 嵌套聚合 (行3375-3424)
- [ ] elAggregationsList - 聚合列表 (行3425-3585)
- [ ] elAttributeValues - 属性值 (行3586-3630)

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts聚合部分

---

#### 子任务9: 文档操作

**预估代码量**: ~350行

| 文件 | 说明 |
|------|------|
| `ElasticsearchDocument.java` | 文档操作扩展 (~350行) |

**功能清单**:
- [ ] elBulk - 批量操作 (行3633-3643)
- [ ] elIndex - 索引文档 (行3645-3675)
- [ ] elUpdate - 更新文档 (行3677-3700)
- [ ] elReplace - 替换文档 (行3701-3722)
- [ ] elDelete - 删除文档 (行3723-3780)

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts文档操作部分

---

#### 子任务10: 元素操作

**预估代码量**: ~500行

| 文件 | 说明 |
|------|------|
| `ElasticsearchDocument.java` | 元素操作扩展 (~500行) |

**功能清单**:
- [ ] elDeleteInstances - 删除实例 (行3793-3809)
- [ ] elRemoveRelationConnection - 移除关系连接 (行3811-3896)
- [ ] computeDeleteElementsImpacts - 计算删除影响 (行3897-3940)
- [ ] elReindexElements - 重建索引 (行3941-3995)
- [ ] elIndexElements - 索引元素 (行4338-4490)
- [ ] elDeleteElements - 删除元素 (行4593-4657)
- [ ] elUpdateElement - 更新元素 (行4658-4671)

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts元素操作部分

---

#### 子任务11: 关系连接操作

**预估代码量**: ~350行

| 文件 | 说明 |
|------|------|
| `ElasticsearchDocument.java` | 关系操作扩展 (~350行) |

**功能清单**:
- [ ] elUpdateRelationConnections - 更新关系连接 (行4491-4502)
- [ ] elUpdateEntityConnections - 更新实体连接 (行4503-4592)
- [ ] elRebuildRelation - 重建关系 (行1596-1653)
- [ ] getRelationsToRemove - 获取要移除的关系 (行3781-3792)

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比engine.ts关系操作部分

---

#### 子任务12: 单元测试

**预估代码量**: ~500行

| 文件 | 说明 |
|------|------|
| `ElasticsearchClientTest.java` | 客户端测试 (~200行) |
| `ElasticsearchSearchTest.java` | 搜索测试 (~150行) |
| `ElasticsearchDocumentTest.java` | 文档测试 (~150行) |

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 测试验证: `mvn test -q`

---

#### 子任务13: 更新文档

**更新内容**:

1. **更新 opencti-java/MODULE_OVERVIEW.md**:
   - 添加 `database/elasticsearch/` 目录结构
   - 更新模块状态表
   - 添加Elasticsearch模块详情
   - 更新文件清单和代码行数
   - 更新测试覆盖统计

2. **更新 项目重写计划.md**:
   - 更新 Phase 2.4 Elasticsearch 引擎状态为 ✅ 已完成
   - 添加完成日期
   - 更新下一步行动

**验证步骤**:
1. 确保文档目录结构包含所有文件
2. 确保状态和进度正确更新

---

## 四、执行顺序

```
子任务1 (基础常量和配置) 
    ↓ 编译验证
子任务2 (ES客户端初始化)
    ↓ 编译验证
子任务3 (索引管理)
    ↓ 编译验证
子任务4 (原始操作)
    ↓ 编译验证
子任务5 (查询构建器)
    ↓ 编译验证
子任务6 (搜索操作)
    ↓ 编译验证
子任务7 (分页和列表)
    ↓ 编译验证
子任务8 (聚合操作)
    ↓ 编译验证
子任务9 (文档操作)
    ↓ 编译验证
子任务10 (元素操作)
    ↓ 编译验证
子任务11 (关系连接操作)
    ↓ 编译验证
子任务12 (单元测试)
    ↓ 测试验证
子任务13 (更新文档)
    ↓ 文档验证
完成
```

## 五、依赖说明

### 5.1 已完成模块 (可直接使用)

- `common/config/ElasticsearchProperties.java` ✅ ES配置属性类已存在
- `common/exception/DatabaseException.java` ✅ 数据库异常已存在
- `database/redis/` ✅ Redis客户端已完成
- `database/rabbitmq/` ✅ RabbitMQ客户端已完成
- `database/storage/` ✅ MinIO文件存储已完成
- `pom.xml` ✅ elasticsearch-java 8.17.1依赖已配置

### 5.2 待依赖模块 (需占位符)

以下模块在engine.ts中被引用，但尚未实现：
- `schema/*.ts` → Schema定义模块
- `cache.ts` → 缓存管理模块
- `middleware-loader.ts` → 数据加载中间件

**处理方案**: 
- 在当前阶段，这些依赖用接口或占位符实现
- 相关方法标记为TODO，待后续模块完成后补充

## 六、验证标准

### 6.1 编译验证

每个子任务完成后执行:
```bash
cd opencti-java && mvn compile -q
```

### 6.2 源码对比

每个子任务完成后:
- 逐行对比TypeScript源码和Java实现
- 确保功能逻辑一致
- 确保没有多余或缺失功能

### 6.3 测试验证

全部完成后执行:
```bash
cd opencti-java && mvn test -q
```

## 七、预计时间

| 子任务 | 预计时间 |
|--------|----------|
| 子任务1: 基础常量和配置 | 1小时 |
| 子任务2: ES客户端初始化 | 2小时 |
| 子任务3: 索引管理 | 2小时 |
| 子任务4: 原始操作 | 1.5小时 |
| 子任务5: 查询构建器 | 2.5小时 |
| 子任务6: 搜索操作 | 2小时 |
| 子任务7: 分页和列表 | 2小时 |
| 子任务8: 聚合操作 | 2小时 |
| 子任务9: 文档操作 | 2小时 |
| 子任务10: 元素操作 | 2.5小时 |
| 子任务11: 关系连接操作 | 2小时 |
| 子任务12: 单元测试 | 2小时 |
| 子任务13: 更新文档 | 0.5小时 |
| **总计** | **24小时** |

## 八、风险提示

1. **代码量巨大**: engine.ts有4690行，是整个项目的核心模块
2. **双引擎支持**: 需要同时支持Elasticsearch和OpenSearch
3. **依赖未实现模块**: Schema定义、缓存管理等模块尚未实现
4. **查询复杂度**: 查询构建逻辑非常复杂，需要仔细实现
5. **性能考虑**: 批量操作和分页需要考虑性能优化
