# OpenCTI GraphQL 后端模块说明文档

## 1. 模块概述

opencti-graphql 是 OpenCTI 平台的核心后端模块，提供 GraphQL API 服务，实现威胁情报的存储、查询、分析和自动化处理。该模块基于 Node.js 和 Express 构建，使用 Elasticsearch 作为搜索引擎，Redis 作为缓存，RabbitMQ 作为消息队列。

## 2. 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Node.js | >= 20.0.0 | 运行时环境 |
| Express | 5.2.1 | HTTP 服务器框架 |
| Apollo Server | 5.2.0 | GraphQL 服务器 |
| Elasticsearch | 8.19.1 | 搜索引擎 |
| Redis (ioredis) | 5.8.2 | 缓存和会话存储 |
| RabbitMQ (amqplib) | 0.10.9 | 消息队列 |
| TypeScript | 5.9.3 | 类型安全 |
| Winston | 3.19.0 | 日志管理 |

## 3. 目录结构

```
opencti-graphql/
├── builder/              # 构建脚本
│   ├── dev/              # 开发环境构建
│   ├── prod/             # 生产环境构建
│   └── schema/           # Schema 生成脚本
├── config/               # 配置文件
│   ├── default.json      # 默认配置
│   └── test.json         # 测试配置
├── src/
│   ├── config/           # 配置模块
│   ├── connector/        # 内置连接器
│   ├── database/         # 数据库层
│   ├── domain/           # 业务逻辑层
│   ├── enterprise-edition/ # 企业版功能
│   ├── generated/        # 自动生成的代码
│   ├── graphql/          # GraphQL 配置
│   ├── http/             # HTTP 服务
│   ├── listener/         # 事件监听器
│   ├── lock/             # 分布式锁
│   ├── manager/          # 后台管理器
│   ├── migrations/       # 数据库迁移
│   ├── modules/          # 功能模块
│   ├── parser/           # 数据解析器
│   ├── python/           # Python 集成
│   ├── resolvers/        # GraphQL 解析器
│   ├── rules/            # 推理规则引擎
│   ├── schema/           # 数据模型定义
│   ├── types/            # TypeScript 类型定义
│   └── utils/            # 工具函数
└── static/               # 静态资源（国旗图标等）
```

## 4. 核心文件说明

### 4.1 入口文件

| 文件 | 说明 |
|------|------|
| back.js | 应用程序入口，导入所有模块并启动平台 |
| boot.js | 平台启动和停止逻辑，包含信号处理 |
| initialization.js | 平台初始化，检查依赖、数据库迁移、默认数据创建 |
| managers.js | 管理器模块的启动和关闭 |
| instrumentation.js | Pyroscope 性能分析集成 |

### 4.2 配置模块 (src/config/)

| 文件 | 说明 |
|------|------|
| conf.js | 主配置文件，包含日志、代理、功能开关等配置 |
| errors.js | 错误类型定义，包含认证错误、数据库错误、功能错误等 |
| tracing.ts | OpenTelemetry 追踪配置 |
| credentials.ts | 凭证管理 |
| providers-configuration.ts | 认证提供者配置 |
| providers-initialization.js | 认证提供者初始化 |

### 4.3 数据库层 (src/database/)

| 文件 | 说明 |
|------|------|
| engine.ts | Elasticsearch/Opensearch 搜索引擎核心操作 |
| middleware.js | 数据访问中间件，提供 CRUD 操作 |
| middleware-loader.ts | 数据加载器，优化批量查询 |
| cache.ts | 缓存管理 |
| redis.ts | Redis 客户端操作 |
| rabbitmq.js | RabbitMQ 消息队列操作 |
| session.js | 会话管理 |
| smtp.js | SMTP 邮件服务 |
| stix.ts | STIX 数据处理 |
| stix-2-0-converter.ts | STIX 2.0 格式转换 |
| stix-2-1-converter.ts | STIX 2.1 格式转换 |
| file-storage.ts | 文件存储（MinIO/S3） |
| migration.js | 数据库迁移管理 |
| draft-engine.ts | 草稿引擎 |
| stream/ | 数据流处理 |

### 4.4 GraphQL 层 (src/graphql/)

| 文件 | 说明 |
|------|------|
| graphql.js | Apollo Server 创建和配置 |
| schema.js | GraphQL Schema 组装和验证 |
| authDirective.ts | 认证指令 |
| sseMiddleware.js | Server-Sent Events 中间件 |
| subscriptionWrapper.ts | 订阅包装器 |
| loggerPlugin.js | 日志插件 |
| telemetryPlugin.js | 遥测插件 |
| tracingPlugin.js | 追踪插件 |

### 4.5 HTTP 服务 (src/http/)

| 文件 | 说明 |
|------|------|
| httpServer.js | HTTP/HTTPS 服务器创建 |
| httpPlatform.js | 平台 HTTP 路由 |
| httpTaxii.js | TAXII 协议服务 |
| httpRollingFeed.ts | 滚动订阅源服务 |
| httpChatbotProxy.ts | 聊天机器人代理 |
| httpAuthenticatedContext.js | 认证上下文创建 |

### 4.6 管理器模块 (src/manager/)

| 文件 | 说明 |
|------|------|
| index.ts | 管理器模块导出 |
| managerModule.ts | 管理器模块基类 |
| connectorManager.js | 连接器管理，处理 Work 状态 |
| taskManager.js | 后台任务调度执行 |
| notificationManager.ts | 通知管理，处理触发器和摘要 |
| publisherManager.ts | 发布管理器 |
| syncManager.js | 数据同步管理 |
| ingestionManager.ts | 数据摄取管理 |
| ruleManager.ts | 推理规则引擎 |
| historyManager.ts | 历史记录管理 |
| playbookManager/ | Playbook 自动化工作流 |
| clusterManager.ts | 集群管理 |
| cacheManager.ts | 缓存管理 |
| expiredManager.js | 过期数据清理 |
| retentionManager.ts | 数据保留策略 |
| telemetryManager.ts | 遥测数据管理 |
| indicatorDecayManager.ts | 指标衰减管理 |
| fileIndexManager.ts | 文件索引管理 |
| garbageCollectionManager.ts | 垃圾回收 |
| hubRegistrationManager.ts | Hub 注册管理 |
| exclusionListCacheBuildManager.ts | 排除列表缓存构建 |
| exclusionListCacheSyncManager.ts | 排除列表缓存同步 |
| pirManager.ts | PIR 管理 |
| activityListener.ts | 活动监听器 |
| activityManager.ts | 活动管理器 |

### 4.7 功能模块 (src/modules/)

模块目录包含各种 STIX 实体和平台功能：

| 模块 | 说明 |
|------|------|
| ai/ | AI 功能（NLQ、LLM 集成） |
| auth/ | 认证模块 |
| attackPattern/ | 攻击模式 |
| campaign/ | 攻击活动 |
| case/ | 案例管理（事件、RFI、RFT、反馈） |
| channel/ | 渠道 |
| catalog/ | 目录 |
| decayRule/ | 衰减规则 |
| deleteOperation/ | 删除操作 |
| disseminationList/ | 分发列表 |
| draftWorkspace/ | 草稿工作区 |
| emailTemplate/ | 邮件模板 |
| entitySetting/ | 实体设置 |
| event/ | 事件 |
| exclusionList/ | 排除列表 |
| externalReference/ | 外部引用 |
| feed/ | 订阅源 |
| fintelDesign/ | Fintel 设计 |
| fintelTemplate/ | Fintel 模板 |
| form/ | 表单 |
| grouping/ | 分组 |
| indicator/ | 指标 |
| ingestion/ | 数据摄取（CSV、JSON、RSS、TAXII） |
| internal/ | 内部模块（CSV/JSON 映射器、文档） |
| intrusionSet/ | 入侵集 |
| language/ | 语言 |
| malware/ | 恶意软件 |
| malwareAnalysis/ | 恶意软件分析 |
| managerConfiguration/ | 管理器配置 |
| metrics/ | 指标统计 |
| narrative/ | 叙事 |
| note/ | 笔记 |
| notification/ | 通知 |
| notifier/ | 通知器 |
| observedData/ | 观测数据 |
| opinion/ | 意见 |
| organization/ | 组织 |
| pir/ | PIR |
| playbook/ | Playbook |
| publicDashboard/ | 公共仪表板 |
| region/ | 区域 |
| report/ | 报告 |
| requestAccess/ | 访问请求 |
| savedFilter/ | 保存的过滤器 |
| securityCoverage/ | 安全覆盖 |
| securityPlatform/ | 安全平台 |
| task/ | 任务 |
| theme/ | 主题 |
| threatActorIndividual/ | 威胁行为者（个人） |
| threatActorGroup/ | 威胁行为者（组织） |
| tool/ | 工具 |
| vocabulary/ | 词汇表 |
| vulnerability/ | 漏洞 |
| workspace/ | 工作区 |
| xtm/ | XTM Hub 集成 |

### 4.8 领域层 (src/domain/)

| 文件 | 说明 |
|------|------|
| user.js | 用户管理 |
| group.js | 用户组管理 |
| settings.js | 平台设置 |
| connector.ts | 连接器管理 |
| stix.js | STIX 数据处理 |
| stixCoreObject.js | STIX 核心对象 |
| stixCoreRelationship.js | STIX 核心关系 |
| stixCyberObservable.js | STIX 网络可观测对象 |
| stixDomainObject.js | STIX 域对象 |
| backgroundTask.js | 后台任务 |
| enrichment.js | 数据富化 |
| feed.ts | 订阅源 |
| file.js | 文件管理 |
| taxii.js | TAXII 服务 |
| stream.js | 数据流 |
| rules.ts | 推理规则 |
| xtm-hub.ts | XTM Hub 集成 |

### 4.9 Schema 定义 (src/schema/)

| 文件 | 说明 |
|------|------|
| general.js | 通用 Schema 定义 |
| identifier.js | 标识符生成 |
| module.ts | 模块 Schema |
| stixCoreObject.js | STIX 核心对象 Schema |
| stixCoreRelationship.js | STIX 核心关系 Schema |
| stixCyberObservable.js | STIX 网络可观测对象 Schema |
| stixDomainObject.js | STIX 域对象 Schema |
| stixMetaObject.js | STIX 元对象 Schema |
| stixSightingRelationship.js | STIX 目击关系 Schema |
| internalObject.js | 内部对象 Schema |
| internalRelationship.js | 内部关系 Schema |

## 5. 核心功能

### 5.1 平台启动流程

1. **依赖检查** - 检查 Elasticsearch、MinIO、RabbitMQ、Redis、SMTP、Python
2. **锁管理器初始化** - 初始化分布式锁
3. **缓存管理器启动** - 启动 Redis 缓存
4. **平台初始化** - 数据库 Schema 创建、迁移、默认数据
5. **模块启动** - 启动所有管理器模块
6. **API 服务启动** - 启动 HTTP/HTTPS 服务器

### 5.2 GraphQL API

- 使用 Apollo Server 5.x
- 支持 WebSocket 订阅
- 支持 GraphQL Upload 文件上传
- 内置查询复杂度限制
- 支持 GraphQL Armor 安全防护

### 5.3 数据存储

- **Elasticsearch/Opensearch** - 主数据存储和搜索引擎
- **Redis** - 缓存、会话、分布式锁
- **MinIO/S3** - 文件存储
- **RabbitMQ** - 消息队列

### 5.4 认证和授权

- 支持多种认证策略：
  - 本地认证（用户名/密码）
  - LDAP/AD
  - SAML
  - OAuth（Google、GitHub、Facebook）
  - 客户端证书
- 基于角色的访问控制（RBAC）
- 数据标记（TLP）访问控制
- 组织隔离

### 5.5 后台任务

- 任务调度器
- 批量操作支持
- 任务类型：
  - 列表任务
  - 查询任务
  - 规则任务

### 5.6 通知系统

- 实时触发器
- 定期摘要
- 多种通知渠道（Webhook、邮件等）

### 5.7 数据同步

- TAXII 协议支持
- 实时数据流
- 远程平台同步

## 6. 配置选项

### 6.1 主要配置项

| 配置项 | 说明 |
|--------|------|
| app:port | 服务端口（默认 4000） |
| app:base_url | 基础 URL |
| app:admin:password | 管理员密码 |
| app:admin:token | API Token |
| elasticsearch:url | Elasticsearch URL |
| redis:hostname | Redis 主机名 |
| rabbitmq:hostname | RabbitMQ 主机名 |
| minio:endpoint | MinIO 端点 |

### 6.2 管理器开关

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| connector_manager:enabled | true | 连接器管理器 |
| notification_manager:enabled | true | 通知管理器 |
| publisher_manager:enabled | true | 发布管理器 |
| task_scheduler:enabled | false | 任务调度器 |
| sync_manager:enabled | false | 同步管理器 |
| ingestion_manager:enabled | false | 摄取管理器 |
| rule_engine:enabled | false | 规则引擎 |
| playbook_manager:enabled | false | Playbook 管理器 |

## 7. 日志系统

### 7.1 日志类型

- **APP** - 应用日志
- **AUDIT** - 审计日志

### 7.2 日志配置

| 配置项 | 说明 |
|--------|------|
| app:app_logs:logs_level | 日志级别 |
| app:app_logs:logs_files | 文件日志开关 |
| app:app_logs:logs_console | 控制台日志开关 |
| app:app_logs:logs_directory | 日志目录 |

## 8. 错误处理

### 8.1 错误类型

| 类型 | HTTP 状态码 | 说明 |
|------|-------------|------|
| AUTH_FAILURE | 401 | 认证失败 |
| AUTH_REQUIRED | 401 | 需要认证 |
| FORBIDDEN_ACCESS | 403 | 禁止访问 |
| DATABASE_ERROR | 500 | 数据库错误 |
| FUNCTIONAL_ERROR | 400 | 功能错误 |
| VALIDATION_ERROR | 500 | 验证错误 |
| RESOURCE_NOT_FOUND | 404 | 资源未找到 |

## 9. 迁移系统

数据库迁移文件位于 `src/migrations/` 目录，按时间戳命名：

- 1608047073975-fix_missing_deletion.js
- 1610015108552-clean_works.js
- ...

## 10. 开发命令

| 命令 | 说明 |
|------|------|
| yarn start | 开发模式启动 |
| yarn build | 生产构建 |
| yarn test | 运行测试 |
| yarn lint | 代码检查 |
| yarn check-ts | TypeScript 类型检查 |

## 11. 依赖关系

```
opencti-graphql
├── client-python (pycti)
├── opencti-worker
└── opencti-front (前端)
```
