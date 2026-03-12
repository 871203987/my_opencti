# OpenCTI 后端情报功能重写计划

## 一、项目概述

### 1.1 重写范围
本次重写涵盖OpenCTI后端的情报存储、查询、接入三大核心功能模块。

### 1.2 技术栈
- Java 21
- Maven构建工具
- Spring Boot框架
- Elasticsearch（搜索引擎）
- Redis（缓存）
- RabbitMQ（消息队列）
- MinIO（文件存储）

---

## 二、模块分析

### 2.1 情报存储模块

#### 2.1.1 数据库存储层
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| Elasticsearch引擎 | `opencti-platform/opencti-graphql/src/database/engine.ts` | `database/elasticsearch/ElasticsearchClient.java` |
| Redis缓存 | `opencti-platform/opencti-graphql/src/database/redis.ts` | `database/redis/RedisClient.java` |
| RabbitMQ消息队列 | `opencti-platform/opencti-graphql/src/database/rabbitmq.js` | `database/rabbitmq/RabbitMQClient.java` |
| MinIO文件存储 | `opencti-platform/opencti-graphql/src/database/file-storage.ts` | `database/storage/FileStorageService.java` |
| 数据中间件 | `opencti-platform/opencti-graphql/src/database/middleware.js` | `database/middleware/DataMiddleware.java` |

#### 2.1.2 数据模型层（STIX对象）
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| STIX领域对象(SDO) | `opencti-platform/opencti-graphql/src/schema/stixDomainObject.ts` | `types/stix/sdo/*.java` |
| STIX可观察对象(SCO) | `opencti-platform/opencti-graphql/src/schema/stixCyberObservable.ts` | `types/stix/sco/*.java` |
| STIX关系对象(SRO) | `opencti-platform/opencti-graphql/src/schema/stixCoreRelationship.ts` | `types/stix/sro/*.java` |
| STIX元对象(SMO) | `opencti-platform/opencti-graphql/src/schema/stixMetaObject.ts` | `types/stix/smo/*.java` |
| 内部对象 | `opencti-platform/opencti-graphql/src/schema/internalObject.ts` | `types/internal/*.java` |
| ID生成策略 | `opencti-platform/opencti-graphql/src/schema/identifier.js` | `schema/identifier/IdGenerator.java` |

#### 2.1.3 STIX转换器
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| STIX核心处理 | `opencti-platform/opencti-graphql/src/database/stix.ts` | `database/stix/StixProcessor.java` |
| STIX 2.0转换器 | `opencti-platform/opencti-graphql/src/database/stix-2-0-converter.ts` | `database/stix/converter/Stix20Converter.java` |
| STIX 2.1转换器 | `opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts` | `database/stix/converter/Stix21Converter.java` |
| SCO转换器 | `opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts` | `database/stix/converter/Stix21ScoConverter.java` |
| SRO转换器 | `opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts` | `database/stix/converter/Stix21SroConverter.java` |
| 通用转换工具 | `opencti-platform/opencti-graphql/src/database/stix-common-converter.ts` | `database/stix/converter/StixCommonConverter.java` |
| 转换工具函数 | `opencti-platform/opencti-graphql/src/database/stix-converter-utils.ts` | `database/stix/converter/StixConverterUtils.java` |

### 2.2 情报查询模块

#### 2.2.1 GraphQL API层
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| STIX核心解析器 | `opencti-platform/opencti-graphql/src/resolvers/stix.js` | `graphql/resolver/StixResolver.java` |
| STIX核心对象解析器 | `opencti-platform/opencti-graphql/src/resolvers/stixCoreObject.js` | `graphql/resolver/StixCoreObjectResolver.java` |
| STIX领域对象解析器 | `opencti-platform/opencti-graphql/src/resolvers/stixDomainObject.js` | `graphql/resolver/StixDomainObjectResolver.java` |
| 可观察对象解析器 | `opencti-platform/opencti-graphql/src/resolvers/stixCyberObservable.js` | `graphql/resolver/StixCyberObservableResolver.java` |
| 关系解析器 | `opencti-platform/opencti-graphql/src/resolvers/stixCoreRelationship.js` | `graphql/resolver/StixCoreRelationshipResolver.java` |
| 目击关系解析器 | `opencti-platform/opencti-graphql/src/resolvers/stixSightingRelationship.js` | `graphql/resolver/StixSightingRelationshipResolver.java` |
| 连接器解析器 | `opencti-platform/opencti-graphql/src/resolvers/connector.js` | `graphql/resolver/ConnectorResolver.java` |
| TAXII解析器 | `opencti-platform/opencti-graphql/src/resolvers/taxii.js` | `graphql/resolver/TaxiiResolver.java` |
| 流数据解析器 | `opencti-platform/opencti-graphql/src/resolvers/stream.js` | `graphql/resolver/StreamResolver.java` |

#### 2.2.2 领域服务层
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| STIX领域服务 | `opencti-platform/opencti-graphql/src/domain/stix.js` | `domain/StixService.java` |
| 核心对象服务 | `opencti-platform/opencti-graphql/src/domain/stixCoreObject.js` | `domain/StixCoreObjectService.java` |
| 领域对象服务 | `opencti-platform/opencti-graphql/src/domain/stixDomainObject.js` | `domain/StixDomainObjectService.java` |
| 可观察对象服务 | `opencti-platform/opencti-graphql/src/domain/stixCyberObservable.js` | `domain/StixCyberObservableService.java` |
| 连接器服务 | `opencti-platform/opencti-graphql/src/domain/connector.ts` | `domain/ConnectorService.java` |
| TAXII服务 | `opencti-platform/opencti-graphql/src/domain/taxii.js` | `domain/TaxiiService.java` |
| 流数据服务 | `opencti-platform/opencti-graphql/src/domain/stream.js` | `domain/StreamService.java` |
| Feed服务 | `opencti-platform/opencti-graphql/src/domain/feed.ts` | `domain/FeedService.java` |
| 工作流服务 | `opencti-platform/opencti-graphql/src/domain/work.js` | `domain/WorkService.java` |

#### 2.2.3 搜索与过滤
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| 过滤工具 | `opencti-platform/opencti-graphql/src/utils/filtering/filtering-utils.ts` | `utils/filtering/FilteringUtils.java` |
| STIX过滤 | `opencti-platform/opencti-graphql/src/utils/filtering/filtering-stix/stix-filtering.ts` | `utils/filtering/StixFiltering.java` |
| 过滤解析 | `opencti-platform/opencti-graphql/src/utils/filtering/filtering-resolution.ts` | `utils/filtering/FilterResolution.java` |
| 布尔逻辑引擎 | `opencti-platform/opencti-graphql/src/utils/filtering/boolean-logic-engine.ts` | `utils/filtering/BooleanLogicEngine.java` |
| 查询构建器 | `opencti-platform/opencti-graphql/src/database/engine.ts` | `database/elasticsearch/query/QueryBuilder.java` |
| 过滤构建器 | `opencti-platform/opencti-graphql/src/database/engine.ts` | `database/elasticsearch/query/FilterBuilder.java` |

### 2.3 情报接入模块

#### 2.3.1 连接器模块
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| 连接器领域服务 | `opencti-platform/opencti-graphql/src/connector/connector-domain.ts` | `connector/ConnectorDomainService.java` |
| CSV导入连接器 | `opencti-platform/opencti-graphql/src/connector/importCsv/*.ts` | `connector/importcsv/*.java` |
| 连接器管理器 | `opencti-platform/opencti-graphql/src/manager/connectorManager.js` | `manager/ConnectorManager.java` |
| 接入管理器 | `opencti-platform/opencti-graphql/src/manager/ingestionManager.ts` | `manager/IngestionManager.java` |
| 任务管理器 | `opencti-platform/opencti-graphql/src/manager/taskManager.js` | `manager/TaskManager.java` |
| 规则管理器 | `opencti-platform/opencti-graphql/src/manager/ruleManager.ts` | `manager/RuleManager.java` |
| 同步管理器 | `opencti-platform/opencti-graphql/src/manager/syncManager.js` | `manager/SyncManager.java` |

#### 2.3.2 数据接入模块
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| TAXII接入 | `opencti-platform/opencti-graphql/src/modules/ingestion/ingestion-taxii.ts` | `modules/ingestion/TaxiiIngestion.java` |
| TAXII领域逻辑 | `opencti-platform/opencti-graphql/src/modules/ingestion/ingestion-taxii-domain.ts` | `modules/ingestion/TaxiiIngestionService.java` |
| RSS接入 | `opencti-platform/opencti-graphql/src/modules/ingestion/ingestion-rss.ts` | `modules/ingestion/RssIngestion.java` |
| RSS领域逻辑 | `opencti-platform/opencti-graphql/src/modules/ingestion/ingestion-rss-domain.ts` | `modules/ingestion/RssIngestionService.java` |
| CSV接入 | `opencti-platform/opencti-graphql/src/modules/ingestion/ingestion-csv.ts` | `modules/ingestion/CsvIngestion.java` |
| JSON接入 | `opencti-platform/opencti-graphql/src/modules/ingestion/ingestion-json.ts` | `modules/ingestion/JsonIngestion.java` |
| 通用接入逻辑 | `opencti-platform/opencti-graphql/src/modules/ingestion/ingestion-common.ts` | `modules/ingestion/IngestionCommon.java` |

#### 2.3.3 数据解析器
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| CSV解析器 | `opencti-platform/opencti-graphql/src/parser/csv-parser.ts` | `parser/CsvParser.java` |
| CSV映射器 | `opencti-platform/opencti-graphql/src/parser/csv-mapper.ts` | `parser/CsvMapper.java` |
| CSV辅助工具 | `opencti-platform/opencti-graphql/src/parser/csv-helper.ts` | `parser/CsvHelper.java` |
| CSV打包器 | `opencti-platform/opencti-graphql/src/parser/csv-bundler.ts` | `parser/CsvBundler.java` |
| STIX Bundle创建器 | `opencti-platform/opencti-graphql/src/parser/bundle-creator.ts` | `parser/BundleCreator.java` |
| JSON映射器 | `opencti-platform/opencti-graphql/src/parser/json-mapper.ts` | `parser/JsonMapper.java` |

#### 2.3.4 HTTP接口层
| 功能 | 原文件路径 | Java重写目标路径 |
|------|-----------|-----------------|
| 平台HTTP服务 | `opencti-platform/opencti-graphql/src/http/httpPlatform.js` | `http/HttpPlatformController.java` |
| TAXII HTTP接口 | `opencti-platform/opencti-graphql/src/http/httpTaxii.js` | `http/HttpTaxiiController.java` |
| Feed滚动接口 | `opencti-platform/opencti-graphql/src/http/httpRollingFeed.ts` | `http/HttpRollingFeedController.java` |
| HTTP服务器 | `opencti-platform/opencti-graphql/src/http/httpServer.js` | `http/HttpServerConfig.java` |

---

## 三、重写任务分解

### 阶段一：基础架构层（预计2-3周）

#### 任务1.1：数据库存储层实现
- **目标**：实现Elasticsearch、Redis、RabbitMQ、MinIO客户端
- **涉及文件**：
  - `database/elasticsearch/ElasticsearchClient.java`
  - `database/redis/RedisClient.java`
  - `database/rabbitmq/RabbitMQClient.java`
  - `database/storage/FileStorageService.java`
- **依赖**：无
- **验收标准**：各客户端能正常连接并执行基本操作

#### 任务1.2：STIX数据模型定义
- **目标**：定义所有STIX对象类型
- **涉及文件**：
  - `types/stix/sdo/*.java` (Attack-Pattern, Campaign, Malware等)
  - `types/stix/sco/*.java` (IPv4, Domain, File等)
  - `types/stix/sro/*.java` (Relationship, Sighting等)
  - `types/stix/smo/*.java` (Marking, Label等)
  - `types/internal/*.java` (Connector, User等)
- **依赖**：无
- **验收标准**：所有STIX对象类型定义完整

#### 任务1.3：ID生成策略
- **目标**：实现STIX标准ID生成
- **涉及文件**：
  - `schema/identifier/IdGenerator.java`
- **依赖**：任务1.2
- **验收标准**：ID生成符合STIX规范

### 阶段二：STIX转换层（预计2-3周）

#### 任务2.1：STIX核心处理器
- **目标**：实现STIX对象的核心处理逻辑
- **涉及文件**：
  - `database/stix/StixProcessor.java`
- **依赖**：任务1.2, 1.3
- **验收标准**：能正确处理STIX对象

#### 任务2.2：STIX转换器实现
- **目标**：实现STIX 2.0/2.1格式转换
- **涉及文件**：
  - `database/stix/converter/StixConverter.java` (接口)
  - `database/stix/converter/Stix20Converter.java`
  - `database/stix/converter/Stix21Converter.java`
  - `database/stix/converter/Stix21ScoConverter.java`
  - `database/stix/converter/Stix21SroConverter.java`
  - `database/stix/converter/StixCommonConverter.java`
  - `database/stix/converter/StixConverterUtils.java`
- **依赖**：任务2.1
- **验收标准**：STIX 2.0和2.1格式能正确互转

### 阶段三：数据中间件层（预计2周）

#### 任务3.1：数据中间件实现
- **目标**：实现数据操作中间件
- **涉及文件**：
  - `database/middleware/DataMiddleware.java`
- **依赖**：任务1.1, 2.2
- **验收标准**：中间件能正确处理数据流转

### 阶段四：领域服务层（预计3-4周）

#### 任务4.1：STIX核心服务
- **目标**：实现STIX核心领域服务
- **涉及文件**：
  - `domain/StixService.java`
  - `domain/StixCoreObjectService.java`
- **依赖**：任务3.1
- **验收标准**：CRUD操作正常

#### 任务4.2：STIX对象服务
- **目标**：实现各类STIX对象服务
- **涉及文件**：
  - `domain/StixDomainObjectService.java`
  - `domain/StixCyberObservableService.java`
- **依赖**：任务4.1
- **验收标准**：各类对象服务正常

#### 任务4.3：连接器服务
- **目标**：实现连接器相关服务
- **涉及文件**：
  - `domain/ConnectorService.java`
  - `domain/WorkService.java`
- **依赖**：任务4.1
- **验收标准**：连接器管理正常

#### 任务4.4：TAXII和流服务
- **目标**：实现TAXII和流数据服务
- **涉及文件**：
  - `domain/TaxiiService.java`
  - `domain/StreamService.java`
  - `domain/FeedService.java`
- **依赖**：任务4.1
- **验收标准**：TAXII和流数据服务正常

### 阶段五：搜索与过滤层（预计2周）

#### 任务5.1：过滤工具实现
- **目标**：实现过滤和搜索功能
- **涉及文件**：
  - `utils/filtering/FilteringUtils.java`
  - `utils/filtering/StixFiltering.java`
  - `utils/filtering/FilterResolution.java`
  - `utils/filtering/BooleanLogicEngine.java`
  - `database/elasticsearch/query/QueryBuilder.java`
  - `database/elasticsearch/query/FilterBuilder.java`
- **依赖**：任务1.1, 4.1
- **验收标准**：搜索和过滤功能正常

### 阶段六：GraphQL API层（预计2-3周）

#### 任务6.1：GraphQL解析器实现
- **目标**：实现GraphQL解析器
- **涉及文件**：
  - `graphql/resolver/StixResolver.java`
  - `graphql/resolver/StixCoreObjectResolver.java`
  - `graphql/resolver/StixDomainObjectResolver.java`
  - `graphql/resolver/StixCyberObservableResolver.java`
  - `graphql/resolver/StixCoreRelationshipResolver.java`
  - `graphql/resolver/StixSightingRelationshipResolver.java`
- **依赖**：任务4.2, 5.1
- **验收标准**：GraphQL查询正常

#### 任务6.2：其他解析器实现
- **目标**：实现其他解析器
- **涉及文件**：
  - `graphql/resolver/ConnectorResolver.java`
  - `graphql/resolver/TaxiiResolver.java`
  - `graphql/resolver/StreamResolver.java`
- **依赖**：任务6.1
- **验收标准**：所有解析器正常

### 阶段七：情报接入层（预计3-4周）

#### 任务7.1：连接器模块
- **目标**：实现连接器核心功能
- **涉及文件**：
  - `connector/ConnectorDomainService.java`
  - `manager/ConnectorManager.java`
  - `manager/TaskManager.java`
- **依赖**：任务4.3
- **验收标准**：连接器管理正常

#### 任务7.2：数据解析器
- **目标**：实现数据解析功能
- **涉及文件**：
  - `parser/CsvParser.java`
  - `parser/CsvMapper.java`
  - `parser/CsvHelper.java`
  - `parser/CsvBundler.java`
  - `parser/BundleCreator.java`
  - `parser/JsonMapper.java`
- **依赖**：任务7.1
- **验收标准**：各类数据能正确解析

#### 任务7.3：数据接入模块
- **目标**：实现数据接入功能
- **涉及文件**：
  - `modules/ingestion/TaxiiIngestion.java`
  - `modules/ingestion/TaxiiIngestionService.java`
  - `modules/ingestion/RssIngestion.java`
  - `modules/ingestion/RssIngestionService.java`
  - `modules/ingestion/CsvIngestion.java`
  - `modules/ingestion/JsonIngestion.java`
  - `modules/ingestion/IngestionCommon.java`
- **依赖**：任务7.2
- **验收标准**：各类数据源能正常接入

#### 任务7.4：接入管理器
- **目标**：实现接入管理功能
- **涉及文件**：
  - `manager/IngestionManager.java`
  - `manager/RuleManager.java`
  - `manager/SyncManager.java`
- **依赖**：任务7.3
- **验收标准**：接入管理功能正常

### 阶段八：HTTP接口层（预计1-2周）

#### 任务8.1：HTTP接口实现
- **目标**：实现HTTP接口
- **涉及文件**：
  - `http/HttpPlatformController.java`
  - `http/HttpTaxiiController.java`
  - `http/HttpRollingFeedController.java`
  - `http/HttpServerConfig.java`
- **依赖**：任务7.3
- **验收标准**：HTTP接口正常

---

## 四、依赖关系图

```
阶段一：基础架构层
├── 任务1.1：数据库存储层
├── 任务1.2：STIX数据模型
└── 任务1.3：ID生成策略
    ↓
阶段二：STIX转换层
├── 任务2.1：STIX核心处理器
└── 任务2.2：STIX转换器
    ↓
阶段三：数据中间件层
└── 任务3.1：数据中间件
    ↓
阶段四：领域服务层
├── 任务4.1：STIX核心服务
├── 任务4.2：STIX对象服务
├── 任务4.3：连接器服务
└── 任务4.4：TAXII和流服务
    ↓
阶段五：搜索与过滤层
└── 任务5.1：过滤工具实现
    ↓
阶段六：GraphQL API层
├── 任务6.1：GraphQL解析器
└── 任务6.2：其他解析器
    ↓
阶段七：情报接入层
├── 任务7.1：连接器模块
├── 任务7.2：数据解析器
├── 任务7.3：数据接入模块
└── 任务7.4：接入管理器
    ↓
阶段八：HTTP接口层
└── 任务8.1：HTTP接口实现
```

---

## 五、风险评估

### 5.1 技术风险
| 风险 | 影响 | 缓解措施 |
|------|------|---------|
| Elasticsearch复杂查询 | 高 | 提前研究ES Java客户端API |
| STIX规范理解偏差 | 高 | 对照原代码逐行理解 |
| GraphQL性能问题 | 中 | 使用DataLoader优化 |
| 多线程并发问题 | 中 | 使用Spring异步特性 |

### 5.2 进度风险
| 风险 | 影响 | 缓解措施 |
|------|------|---------|
| 任务复杂度低估 | 高 | 每个任务预留缓冲时间 |
| 依赖延迟 | 中 | 明确依赖关系，提前沟通 |

---

## 六、验收标准

### 6.1 功能验收
- [ ] 所有STIX对象类型能正确存储和查询
- [ ] STIX 2.0/2.1格式能正确互转
- [ ] GraphQL API能正常响应
- [ ] TAXII/RSS/CSV/JSON数据源能正常接入
- [ ] 搜索和过滤功能正常

### 6.2 性能验收
- [ ] 查询响应时间 < 500ms
- [ ] 数据导入速度 > 1000条/秒
- [ ] 并发连接支持 > 1000

### 6.3 质量验收
- [ ] 代码覆盖率 > 80%
- [ ] 无严重Bug
- [ ] 文档完整

---

## 七、注意事项

1. **逐行阅读源码**：重写前必须逐行阅读相关源码，理解其逻辑和功能
2. **保持一致性**：类名、属性名尽量保持和源码一致
3. **注释规范**：注释尽量用中文，类和方法必须注释重写的原文件路径
4. **分阶段编译**：每个子任务完成后必须进行编译
5. **不修改types属性**：原则上不修改types下的类属性
6. **功能不简化**：功能不能简化实现，必须和原项目功能一致

---

计划制定完成，等待确认后开始执行。
