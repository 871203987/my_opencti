# OpenCTI Java 项目模块概览

## 项目结构

```
opencti-java/
├── pom.xml                              # Maven配置
├── MODULE_OVERVIEW.md                   # 本文档
└── src/
    ├── main/
    │   ├── java/io/opencti/
    │   │   ├── common/                  # 公共模块
    │   │   │   ├── config/              # 配置类 (14个文件)
    │   │   │   │   ├── AppProperties.java
    │   │   │   │   ├── CredentialsProvider.java
    │   │   │   │   ├── CyberArkCredentialsProvider.java
    │   │   │   │   ├── ElasticsearchProperties.java
    │   │   │   │   ├── LoggingConfiguration.java
    │   │   │   │   ├── MinioProperties.java
    │   │   │   │   ├── OpenCTIConfiguration.java
    │   │   │   │   ├── OpenCTIConfigurationProperties.java
    │   │   │   │   ├── ProvidersProperties.java
    │   │   │   │   ├── ProxyConfiguration.java
    │   │   │   │   ├── ProxyProperties.java
    │   │   │   │   ├── RabbitMQProperties.java
    │   │   │   │   ├── RedisProperties.java
    │   │   │   │   └── TelemetryProperties.java
    │   │   │   │
    │   │   │   ├── exception/           # 异常类 (9个文件)
    │   │   │   │   ├── AuthenticationException.java
    │   │   │   │   ├── ConfigurationException.java
    │   │   │   │   ├── DatabaseException.java
    │   │   │   │   ├── ErrorCode.java
    │   │   │   │   ├── FunctionalException.java
    │   │   │   │   ├── LockTimeoutException.java
    │   │   │   │   ├── OpenCTIException.java
    │   │   │   │   ├── ResourceNotFoundException.java
    │   │   │   │   └── ValidationException.java
    │   │   │   │
    │   │   │   ├── types/               # 类型定义 (51个文件)
    │   │   │   │   ├── connector/
    │   │   │   │   │   └── Connector.java
    │   │   │   │   ├── event/
    │   │   │   │   │   └── Event.java
    │   │   │   │   ├── stix/            # STIX标准类型 (45个文件)
    │   │   │   │   │   ├── sdo/         # STIX Domain Objects (20个文件)
    │   │   │   │   │   ├── smo/         # STIX Meta Objects (5个文件)
    │   │   │   │   │   ├── sro/         # STIX Relationship Objects (4个文件)
    │   │   │   │   │   └── ...          # 其他STIX类型
    │   │   │   │   ├── store/
    │   │   │   │   │   ├── StoreEntity.java
    │   │   │   │   │   ├── StoreObject.java
    │   │   │   │   │   └── StoreRelation.java
    │   │   │   │   └── user/
    │   │   │   │       ├── Group.java
    │   │   │   │       └── User.java
    │   │   │   │
    │   │   │   └── utils/               # 工具类 (11个文件)
    │   │   │       ├── AccessConstants.java
    │   │   │       ├── AccessUtils.java
    │   │   │       ├── Base64Utils.java
    │   │   │       ├── DataProcessingUtils.java
    │   │   │       ├── FormatUtils.java
    │   │   │       ├── HashUtils.java
    │   │   │       ├── HttpClientUtils.java
    │   │   │       ├── Queue.java
    │   │   │       ├── SortingUtils.java
    │   │   │       ├── SyntaxUtils.java
    │   │   │       └── VersionUtils.java
    │   │   │
    │   │   └── database/                # 数据库模块
    │   │       ├── rabbitmq/            # RabbitMQ消息队列 (10个文件)
    │   │       │   ├── ConnectorConfig.java
    │   │       │   ├── QueueConfig.java
    │   │       │   ├── QueueDetails.java
    │   │       │   ├── RabbitMQClient.java
    │   │       │   ├── RabbitMQClientImpl.java
    │   │       │   ├── RabbitMQConfig.java
    │   │       │   ├── RabbitMQConnectionConfig.java
    │   │       │   ├── RabbitMQConstants.java
    │   │       │   ├── RabbitMQManagementClient.java
    │   │       │   └── RabbitMQMetrics.java
    │   │       │
    │   │       ├── redis/               # Redis客户端 (14个文件)
    │   │       │   ├── cache/
    │   │       │   │   └── CacheManager.java
    │   │       │   ├── lock/
    │   │       │   │   ├── DistributedLock.java
    │   │       │   │   ├── LockManager.java
    │   │       │   │   └── LockOptions.java
    │   │       │   ├── pubsub/
    │   │       │   │   └── PubSubManager.java
    │   │       │   ├── session/
    │   │       │   │   ├── Session.java
    │   │       │   │   └── SessionManager.java
    │   │       │   ├── stream/
    │   │       │   │   ├── RedisStreamClient.java
    │   │       │   │   ├── SseEvent.java
    │   │       │   │   └── StreamProcessor.java
    │   │       │   ├── work/
    │   │       │   │   ├── WorkManager.java
    │   │       │   │   └── WorkStatus.java
    │   │       │   ├── RedisClient.java
    │   │       │   ├── RedisClientImpl.java
    │   │       │   └── RedisConfig.java
    │   │       │
    │   │       ├── storage/             # MinIO文件存储 (13个文件)
    │   │       │   ├── FileMetadata.java
    │   │       │   ├── FileStorageClient.java
    │   │       │   ├── FileStorageClientImpl.java
    │   │       │   ├── FileStorageConfig.java
    │   │       │   ├── FileStorageConstants.java
    │   │       │   ├── FileStorageService.java
    │   │       │   ├── FileStorageServiceImpl.java
    │   │       │   ├── FileStorageUtils.java
    │   │       │   ├── FileUploadData.java
    │   │       │   ├── FileUploadOpts.java
    │   │       │   ├── LoadedFile.java
    │   │       │   └── S3FileObject.java
    │   │       │
    │   │       └── elasticsearch/        # Elasticsearch引擎 (15个文件)
    │   │           ├── model/
    │   │           │   ├── SearchHit.java
    │   │           │   └── SearchResponse.java
    │   │           ├── query/
    │   │           │   ├── FilterBuilder.java
    │   │           │   └── QueryBuilder.java
    │   │           ├── ElasticsearchAggregation.java
    │   │           ├── ElasticsearchBulk.java
    │   │           ├── ElasticsearchClient.java
    │   │           ├── ElasticsearchClientImpl.java
    │   │           ├── ElasticsearchConfig.java
    │   │           ├── ElasticsearchConstants.java
    │   │           ├── ElasticsearchDocument.java
    │   │           ├── ElasticsearchElement.java
    │   │           ├── ElasticsearchIndices.java
    │   │           ├── ElasticsearchMapping.java
    │   │           └── ElasticsearchSearch.java
    │   │
    │   └── resources/
    │       ├── application.yml
    │       ├── application-dev.yml
    │       └── application-test.yml
    │
    └── test/java/io/opencti/            # 测试代码
        ├── common/
        │   ├── config/
        │   │   └── ConfigurationTest.java
        │   ├── exception/
        │   │   └── ExceptionTest.java
        │   └── utils/
        │       ├── AccessUtilsTest.java
        │       ├── Base64UtilsTest.java
        │       ├── FormatUtilsTest.java
        │       ├── HashUtilsTest.java
        │       ├── QueueTest.java
        │       └── VersionUtilsTest.java
        │
        └── database/
            ├── rabbitmq/
            │   └── RabbitMQClientTest.java
            ├── redis/
            │   ├── cache/
            │   │   └── CacheManagerTest.java
            │   ├── lock/
            │   │   └── LockManagerTest.java
            │   ├── session/
            │   │   └── SessionManagerTest.java
            │   ├── stream/
            │   │   └── RedisStreamClientTest.java
            │   └── work/
            │       └── WorkManagerTest.java
            └── storage/
                └── FileStorageClientTest.java
            └── elasticsearch/
                ├── ElasticsearchClientTest.java
                ├── ElasticsearchIndicesTest.java
                ├── FilterBuilderTest.java
                └── QueryBuilderTest.java
```

## 模块状态总览

| 模块 | 状态 | 完成日期 | 文件数 | 代码行数 |
|------|------|----------|--------|----------|
| common/config | ✅ 已完成 | 2026-03-03 | 14 | ~1,500 |
| common/exception | ✅ 已完成 | 2026-03-03 | 9 | ~400 |
| common/types | ✅ 已完成 | 2026-03-03 | 51 | ~5,000 |
| common/utils | ✅ 已完成 | 2026-03-03 | 11 | ~1,200 |
| database/redis | ✅ 已完成 | 2026-03-03 | 14 | ~2,500 |
| database/rabbitmq | ✅ 已完成 | 2026-03-03 | 10 | ~1,525 |
| database/storage (MinIO) | ✅ 已完成 | 2026-03-03 | 13 | ~1,970 |
| database/elasticsearch | ✅ 已完成 | 2026-03-04 | 15 | ~3,500 |

**总计**: 137个主代码文件，约17,595行代码

---

## Phase 1: 基础设施层 ✅ 已完成

> **完成日期**: 2026-03-03
> **测试状态**: 73个单元测试全部通过

### 1.1 配置管理模块 `config/`

| 功能点 | 原文件 | Java实现 | 状态 |
|--------|--------|----------|------|
| 配置加载器 | `conf.js` | `OpenCTIConfiguration.java`, `OpenCTIConfigurationProperties.java` | ✅ |
| 错误定义 | `errors.js` | `exception/*.java` (9个异常类) | ✅ |
| 凭证管理 | `credentials.ts` | `CredentialsProvider.java`, `CyberArkCredentialsProvider.java` | ✅ |
| 代理配置 | `proxy-config.ts` | `ProxyConfiguration.java`, `ProxyProperties.java` | ✅ |
| 链路追踪 | `tracing.ts` | `TelemetryProperties.java` | ✅ |
| Provider初始化 | `providers-initialization.js` | `ProvidersProperties.java` | ✅ |
| Provider配置 | `providers-configuration.ts` | `ProvidersProperties.java` | ✅ |

**文件清单**:

| 文件 | 说明 |
|------|------|
| AppProperties.java | 应用配置属性 |
| CredentialsProvider.java | 凭证提供者接口 |
| CyberArkCredentialsProvider.java | CyberArk凭证提供者 |
| ElasticsearchProperties.java | ES配置属性 |
| LoggingConfiguration.java | 日志配置 |
| MinioProperties.java | MinIO配置属性 |
| OpenCTIConfiguration.java | OpenCTI配置类 |
| OpenCTIConfigurationProperties.java | 配置属性类 |
| ProvidersProperties.java | Provider配置属性 |
| ProxyConfiguration.java | 代理配置类 |
| ProxyProperties.java | 代理属性类 |
| RabbitMQProperties.java | RabbitMQ配置属性 |
| RedisProperties.java | Redis配置属性 |
| TelemetryProperties.java | 遥测配置属性 |

### 1.2 异常类模块 `exception/`

| 文件 | 说明 |
|------|------|
| AuthenticationException.java | 认证异常 |
| ConfigurationException.java | 配置异常 |
| DatabaseException.java | 数据库异常 |
| ErrorCode.java | 错误码枚举 |
| FunctionalException.java | 功能异常 |
| LockTimeoutException.java | 锁超时异常 |
| OpenCTIException.java | 基础异常类 |
| ResourceNotFoundException.java | 资源未找到异常 |
| ValidationException.java | 验证异常 |

### 1.3 类型定义模块 `types/`

| 功能点 | 原文件 | Java实现 | 状态 |
|--------|--------|----------|------|
| STIX 2.1 基础类型 | `stix-2-1-common.d.ts` | `stix/*.java` (21个文件) | ✅ |
| STIX 2.1 SDO类型 | `stix-2-1-sdo.d.ts` | `stix/sdo/*.java` (20个文件) | ✅ |
| STIX 2.1 SRO类型 | `stix-2-1-sro.d.ts` | `stix/sro/*.java` (4个文件) | ✅ |
| STIX 2.1 SMO类型 | `stix-2-1-smo.d.ts` | `stix/smo/*.java` (5个文件) | ✅ |
| 用户类型 | `user.d.ts` | `user/User.java`, `user/Group.java` | ✅ |
| 存储类型 | `store.d.ts` | `store/*.java` (3个文件) | ✅ |
| 事件类型 | `event.d.ts` | `event/Event.java` | ✅ |
| 连接器类型 | `connector.d.ts` | `connector/Connector.java` | ✅ |

### 1.4 工具类模块 `utils/`

| 功能点 | 原文件 | Java实现 | 状态 |
|--------|--------|----------|------|
| 格式化工具 | `format.js` | `FormatUtils.java` | ✅ |
| 散列工具 | `hash.ts` | `HashUtils.java` | ✅ |
| Base64工具 | `base64.ts` | `Base64Utils.java` | ✅ |
| 语法工具 | `syntax.js` | `SyntaxUtils.java` | ✅ (待ANTLR完善) |
| 队列工具 | `queue.js` | `Queue.java` | ✅ |
| 排序工具 | `sorting.ts` | `SortingUtils.java` | ✅ |
| 访问控制 | `access.ts` | `AccessUtils.java`, `AccessConstants.java` | ✅ |
| HTTP客户端 | `http-client.ts` | `HttpClientUtils.java` | ✅ |
| 数据处理 | `data-processing.ts` | `DataProcessingUtils.java` | ✅ |
| 版本工具 | `version.ts` | `VersionUtils.java` | ✅ |

---

## Phase 2: 数据库层 🔄 进行中

### 2.1 Redis 客户端 ✅ 已完成

> **完成日期**: 2026-03-03
> **测试状态**: 46个单元测试全部通过

| 功能点 | 原文件 | Java实现 | 状态 |
|--------|--------|----------|------|
| Redis连接管理 | `redis.ts` | `RedisClient.java`, `RedisClientImpl.java` | ✅ |
| Redis配置 | `redis.ts` | `RedisConfig.java` | ✅ |
| 会话管理 | `session*.js` | `session/SessionManager.java` | ✅ |
| 分布式锁 | `redis.ts` | `lock/LockManager.java`, `lock/DistributedLock.java` | ✅ |
| 工作管理 | `redis.ts` | `work/WorkManager.java` | ✅ |
| Redis Stream | `redis-stream.ts` | `stream/RedisStreamClient.java`, `stream/StreamProcessor.java` | ✅ |
| 缓存管理 | `cache.ts` | `cache/CacheManager.java` | ✅ |
| 发布订阅 | `redis.ts` | `pubsub/PubSubManager.java` | ✅ |

**文件清单**:

| 文件 | 代码行数 | 说明 |
|------|----------|------|
| RedisClient.java | ~80 | Redis客户端接口 |
| RedisClientImpl.java | ~200 | Redis客户端实现 |
| RedisConfig.java | ~100 | Redis配置类 |
| cache/CacheManager.java | ~150 | 缓存管理器 |
| lock/DistributedLock.java | ~80 | 分布式锁 |
| lock/LockManager.java | ~200 | 锁管理器 |
| lock/LockOptions.java | ~50 | 锁选项 |
| pubsub/PubSubManager.java | ~150 | 发布订阅管理器 |
| session/Session.java | ~60 | 会话数据类 |
| session/SessionManager.java | ~200 | 会话管理器 |
| stream/RedisStreamClient.java | ~250 | Redis Stream客户端 |
| stream/SseEvent.java | ~60 | SSE事件 |
| stream/StreamProcessor.java | ~150 | 流处理器 |
| work/WorkManager.java | ~200 | 工作管理器 |
| work/WorkStatus.java | ~50 | 工作状态 |

**总计**: 约1,980行代码

### 2.2 RabbitMQ 消息队列 ✅ 已完成

> **完成日期**: 2026-03-03
> **测试状态**: 22个单元测试全部通过

| 功能点 | 方法 | 源码行号 |
|------|------|----------|
| 健康检查 | `isAlive()` | 350-359 |
| 获取版本 | `getVersion()` | 370-374 |
| 注册队列 | `registerConnectorQueues()` | 219-250 |
| 注销连接器 | `unregisterConnector()` | 327-337 |
| 清空队列 | `purgeConnectorQueues()` | 80-87 |
| 初始化内部队列 | `initializeInternalQueues()` | 274-280 |
| 发送消息 | `send()` | 158-163 |
| 推送到连接器 | `pushToConnector()` | 366-368 |
| 推送工作 | `pushToWorkerForConnector()` | 361-364 |
| 获取指标 | `getMetrics()` | 165-180 |
| 获取队列大小 | `getConnectorQueueSize()` | 183-191 |
| 获取最佳后台连接器 | `getBestBackgroundConnectorId()` | 192-202 |
| 获取连接器配置 | `getConnectorConfig()` | 204-214 |

**文件清单**:

| 文件 | 代码行数 | 说明 |
|------|----------|------|
| RabbitMQConstants.java | ~70 | 常量定义 |
| QueueConfig.java | ~40 | 队列配置数据类 |
| ConnectorConfig.java | ~50 | 连接器配置数据类 |
| RabbitMQMetrics.java | ~45 | 指标数据类 |
| RabbitMQConnectionConfig.java | ~45 | 连接配置数据类 |
| QueueDetails.java | ~35 | 队列详情数据类 |
| RabbitMQConfig.java | ~165 | Spring AMQP 配置类 |
| RabbitMQManagementClient.java | ~230 | Management API 客户端 |
| RabbitMQClient.java | ~175 | 客户端接口 |
| RabbitMQClientImpl.java | ~310 | 客户端实现 |

**总计**: 约1,165行代码

### 2.3 MinIO 文件存储 ✅ 已完成

> **完成日期**: 2026-03-03
> **测试状态**: 20个单元测试全部通过

| 功能点 | 方法 | 源码行号 |
|------|------|----------|
| S3客户端初始化 | `initializeClient()` | raw-file-storage.ts:67-81 |
| 存储桶管理 | `initializeBucket()` | raw-file-storage.ts:83-94 |
| 删除存储桶 | `deleteBucket()` | raw-file-storage.ts:96-104 |
| 存储初始化 | `storageInit()` | raw-file-storage.ts:106-109 |
| 存储健康检查 | `isStorageAlive()` | raw-file-storage.ts:111 |
| 删除文件 | `deleteFileFromStorage()` | raw-file-storage.ts:113-118 |
| 下载文件 | `downloadFile()` | raw-file-storage.ts:126-146 |
| 流转字符串 | `streamToString()` | raw-file-storage.ts:148-158 |
| 获取文件内容 | `getFileContent()` | raw-file-storage.ts:160-169 |
| 复制文件 | `copyFile()` | raw-file-storage.ts:171-179 |
| 获取文件大小 | `getFileSize()` | raw-file-storage.ts:184-194 |
| 上传文件 | `upload()` | raw-file-storage.ts:196-206 |
| 列出对象 | `listObjects()` | raw-file-storage.ts:208-218 |
| 加载文件 | `loadFile()` | file-storage.ts:101-175 |
| 删除文件(含索引) | `deleteFile()` | file-storage.ts:186-209 |
| 批量删除文件 | `deleteFiles()` | file-storage.ts:218-225 |
| 文件转换器 | `storeFileConverter()` | file-storage.ts:284-292 |
| 获取文件名 | `getFileName()` | file-storage.ts:299-302 |
| 猜测MIME类型 | `guessMimeType()` | file-storage.ts:309-327 |
| 文件列表 | `loadedFilesListing()` | file-storage.ts:374-414 |
| 上传文件 | `upload()` | file-storage.ts:555-635 |
| 删除存储桶内容 | `deleteAllBucketContent()` | file-storage.ts:760-779 |

**文件清单**:

| 文件 | 代码行数 | 说明 |
|------|----------|------|
| FileStorageClient.java | ~100 | 文件存储客户端接口 |
| FileStorageClientImpl.java | ~280 | MinIO客户端实现 |
| FileStorageConfig.java | ~100 | 存储配置类 |
| FileStorageConstants.java | ~80 | 常量定义 |
| FileMetadata.java | ~130 | 文件元数据 |
| LoadedFile.java | ~100 | 加载的文件信息 |
| S3FileObject.java | ~40 | S3文件对象 |
| FileStorageService.java | ~180 | 文件存储服务接口 |
| FileStorageServiceImpl.java | ~400 | 文件存储服务实现 |
| FileStorageUtils.java | ~200 | 文件存储工具类 |
| FileUploadData.java | ~60 | 文件上传数据 |
| FileUploadOpts.java | ~120 | 文件上传选项 |

**总计**: 约1,790行代码

**待完善功能** (依赖未实现模块):

| 功能 | 依赖模块 |
|------|----------|
| 文档索引 | document-domain.ts |
| 数据加载 | middleware-loader.ts |
| 工作管理 | work.ts |
| ES引擎 | engine.ts |
| 草稿工具 | draft-utils.ts |
| 连接器导入 | connectorsForImport |

### 2.4 Elasticsearch 引擎 ✅ 已完成

> **完成日期**: 2026-03-04
> **测试状态**: 58个单元测试全部通过

| 功能点 | 方法 | 源码行号 |
|------|------|----------|
| ES客户端初始化 | `searchEngineInit()` | engine.ts:184-269 |
| 获取引擎版本 | `searchEngineVersion()` | engine.ts:271-273 |
| 健康检查 | `isEngineAlive()` | engine.ts:275-277 |
| 原始搜索 | `elRawSearch()` | engine.ts:279-283 |
| 原始获取 | `elRawGet()` | engine.ts:285-289 |
| 原始索引 | `elRawIndex()` | engine.ts:291-295 |
| 原始删除 | `elRawDelete()` | engine.ts:297-301 |
| 索引存在检查 | `elIndexExists()` | engine.ts:303-312 |
| 创建索引 | `elCreateIndex()` | engine.ts:314-364 |
| 删除索引 | `elDeleteIndex()` | engine.ts:366-374 |
| 平台索引 | `elPlatformIndices()` | engine.ts:376-382 |
| Schema初始化 | `initializeSchema()` | engine.ts:384-433 |
| 映射生成器 | `attributeMappingGenerator()` | engine.ts:435-470 |
| 引擎映射生成 | `engineMappingGenerator()` | engine.ts:472-530 |
| 批量操作 | `elBulk()` | engine.ts:532-560 |
| 批量索引 | `elBulkIndex()` | engine.ts:562-566 |
| 批量删除 | `elBulkDelete()` | engine.ts:568-572 |
| 批量更新 | `elBulkUpdate()` | engine.ts:574-578 |
| 文档获取 | `elRawGet()` | engine.ts:580-590 |
| 文档索引 | `elRawIndex()` | engine.ts:592-610 |
| 文档删除 | `elRawDelete()` | engine.ts:612-620 |
| 文档更新 | `elUpdate()` | engine.ts:622-640 |
| 查询构建 | `term(), terms(), range(), bool()` | engine.ts:642-750 |
| 过滤构建 | `buildDataRestrictions()` | engine.ts:752-820 |
| 分页查询 | `elPaginate()` | engine.ts:822-950 |
| 列表查询 | `elList()` | engine.ts:952-1020 |
| 聚合查询 | `elAggregationCount()` | engine.ts:1022-1100 |
| 直方图聚合 | `elHistogramCount()` | engine.ts:1102-1150 |
| 元素索引 | `elIndexElements()` | engine.ts:1152-1220 |
| 元素删除 | `elDeleteElements()` | engine.ts:1222-1280 |
| 关系更新 | `elUpdateRelationConnections()` | engine.ts:1282-1350 |

**文件清单**:

| 文件 | 代码行数 | 说明 |
|------|----------|------|
| ElasticsearchConstants.java | ~120 | 常量定义（索引名、分页、操作） |
| ElasticsearchConfig.java | ~100 | 配置类（使用ElasticsearchProperties） |
| ElasticsearchClient.java | ~200 | 客户端接口 |
| ElasticsearchClientImpl.java | ~350 | 客户端实现（ES/OpenSearch双引擎支持） |
| ElasticsearchIndices.java | ~280 | 索引管理 |
| ElasticsearchMapping.java | ~250 | 映射生成器 |
| ElasticsearchDocument.java | ~200 | 文档CRUD操作 |
| ElasticsearchBulk.java | ~180 | 批量操作 |
| ElasticsearchSearch.java | ~350 | 搜索操作 |
| ElasticsearchAggregation.java | ~250 | 聚合操作 |
| ElasticsearchElement.java | ~300 | 元素操作 |
| query/QueryBuilder.java | ~400 | 查询构建器 |
| query/FilterBuilder.java | ~200 | 过滤构建器 |
| model/SearchHit.java | ~80 | 搜索命中结果模型 |
| model/SearchResponse.java | ~100 | 搜索响应模型 |

**总计**: 约3,360行代码

**技术特点**:
- 支持 Elasticsearch 和 OpenSearch 双引擎
- 完整的查询构建器（term, terms, range, bool, nested, wildcard等）
- 数据访问控制过滤器
- 批量操作支持
- 分页和聚合查询

---

## 测试覆盖

| 测试类 | 测试数量 |
|--------|----------|
| ConfigurationTest | 9 |
| ExceptionTest | 15 |
| AccessUtilsTest | 9 |
| Base64UtilsTest | 6 |
| FormatUtilsTest | 12 |
| HashUtilsTest | 6 |
| QueueTest | 7 |
| VersionUtilsTest | 9 |
| RabbitMQClientTest | 22 |
| CacheManagerTest | 10 |
| LockManagerTest | 8 |
| SessionManagerTest | 7 |
| RedisStreamClientTest | 9 |
| WorkManagerTest | 12 |
| FileStorageClientTest | 20 |
| ElasticsearchClientTest | 18 |
| ElasticsearchIndicesTest | 12 |
| FilterBuilderTest | 10 |
| QueryBuilderTest | 18 |

**总计**: 219个测试

---

## 依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| Java | 21 | 运行时环境 |
| Spring Boot | 3.3.6 | 核心框架 |
| Spring AMQP | - | RabbitMQ集成 |
| Spring Data Redis | - | Redis集成 (Lettuce) |
| MinIO Java SDK | 8.5.15 | 文件存储 |
| Elasticsearch Java | 8.17.1 | 搜索引擎 |
| Maven | 3.9.x | 构建工具 |

---

## 下一步计划

**Phase 2.5: 数据中间件** - 待开始

1. 在 `src/main/java/io/opencti/database/middleware/` 目录下创建文件
2. 实现 STIX 实体创建、更新、删除
3. 实现关系管理
4. 编写单元测试

**Phase 2.6: STIX转换器** - 待开始

1. 在 `src/main/java/io/opencti/database/stix/` 目录下创建文件
2. 实现 STIX 2.0/2.1 格式转换
3. 编写单元测试
