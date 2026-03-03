# OpenCTI Java 项目模块概览

## 项目结构

```
opencti-java/
├── src/main/java/io/opencti/
│   ├── OpenctiJavaApplication.java    # Spring Boot 启动类
│   ├── common/                         # 公共模块
│   │   ├── config/                     # 配置类
│   │   │   ├── ElasticsearchProperties.java
│   │   │   ├── MinioProperties.java
│   │   │   ├── RabbitMQProperties.java
│   │   │   ├── RedisProperties.java
│   │   │   └── TelemetryProperties.java
│   │   ├── exception/                  # 异常类
│   │   │   ├── DatabaseException.java
│   │   │   ├── LockAcquisitionException.java
│   │   │   └── ...
│   │   └── utils/                      # 工具类
│   │       ├── AccessUtils.java
│   │       ├── Base64Utils.java
│   │       ├── FormatUtils.java
│   │       ├── HashUtils.java
│   │       ├── QueueUtils.java
│   │       └── VersionUtils.java
│   └── database/                       # 数据库模块
│       ├── rabbitmq/                   # RabbitMQ 客户端 ✅ 新增
│       │   ├── RabbitMQClient.java
│       │   ├── RabbitMQClientImpl.java
│       │   ├── RabbitMQConfig.java
│       │   ├── RabbitMQConstants.java
│       │   ├── RabbitMQManagementClient.java
│       │   ├── QueueConfig.java
│       │   ├── ConnectorConfig.java
│       │   ├── RabbitMQMetrics.java
│       │   ├── RabbitMQConnectionConfig.java
│       │   └── QueueDetails.java
│       └── redis/                      # Redis 客户端
│           ├── cache/
│           ├── lock/
│           ├── session/
│           ├── stream/
│           └── work/
└── src/test/java/io/opencti/           # 测试类
```

## 模块状态

| 模块 | 状态 | 完成日期 |
|------|------|----------|
| common/config | ✅ 已完成 | 2026-03-03 |
| common/exception | ✅ 已完成 | 2026-03-03 |
| common/utils | ✅ 已完成 | 2026-03-03 |
| database/redis | ✅ 已完成 | 2026-03-03 |
| database/rabbitmq | ✅ 已完成 | 2026-03-03 |
| database/storage (MinIO) | ⏳ 待开始 | - |
| database/elasticsearch | ⏳ 待开始 | - |

## RabbitMQ 模块详情

### 已实现功能

| 功能 | 方法 | 源码行号 |
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

### 文件清单

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
| RabbitMQClientTest.java | ~360 | 单元测试 |

**总计**: 约 1525 行代码

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

**总计**: 141 个测试

## 依赖

- Java 21
- Spring Boot 3.3.6
- Spring AMQP (RabbitMQ)
- Spring Data Redis (Lettuce)
- Maven 3.9.x
