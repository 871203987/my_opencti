# Phase 2.2: RabbitMQ 客户端实现计划

## 一、任务概述

### 1.1 当前项目进度

| 阶段 | 状态 | 完成日期 |
|------|------|----------|
| Phase 1: 基础设施层 | ✅ 已完成 | 2026-03-03 |
| Phase 2.1: Redis 客户端 | ✅ 已完成 | 2026-03-03 |
| Phase 2.2: RabbitMQ 客户端 | ⏳ 待开始 | - |
| Phase 2.3: MinIO 文件存储 | ⏳ 后续 | - |
| Phase 2.4: Elasticsearch 引擎 | ⏳ 后续 | - |

### 1.2 本阶段目标

实现 RabbitMQ 消息队列客户端，为连接器通信、后台任务调度提供消息基础设施。

---

## 二、源码分析

### 2.1 源文件位置

`opencti-platform/opencti-graphql/src/database/rabbitmq.js`

### 2.2 核心功能清单

| # | 功能 | 函数名 | 行号 | 说明 |
|---|------|--------|------|------|
| 1 | 配置获取 | `config()` | 50-59 | 获取连接配置 |
| 2 | HTTP 客户端 | `amqpHttpClient()` | 61-74 | Management API 客户端 |
| 3 | 清空队列 | `purgeConnectorQueues()` | 80-87 | 清空连接器队列 |
| 4 | 队列详情 | `getConnectorQueueDetails()` | 89-108 | 获取队列消息数量和大小 |
| 5 | 执行器 | `amqpExecute()` | 110-156 | AMQP 操作执行器 |
| 6 | 发送消息 | `send()` | 158-163 | 发送消息到交换机 |
| 7 | 指标获取 | `metrics()` | 165-180 | 获取 RabbitMQ 指标 |
| 8 | 队列大小 | `getConnectorQueueSize()` | 183-191 | 获取连接器队列大小 |
| 9 | 最佳连接器 | `getBestBackgroundConnectorId()` | 192-202 | 获取负载最低的后台任务连接器 |
| 10 | 连接器配置 | `connectorConfig()` | 204-214 | 生成连接器配置 |
| 11 | 路由键 | `listenRouting()` / `pushRouting()` | 216-217 | 生成路由键 |
| 12 | 注册队列 | `registerConnectorQueues()` | 219-250 | 注册连接器队列 |
| 13 | 内部队列 | `getInternalQueues()` | 269-272 | 获取内部队列列表 |
| 14 | 初始化队列 | `initializeInternalQueues()` | 274-280 | 初始化内部队列 |
| 15 | Playbook 队列 | `getInternalPlaybookQueues()` | 282-290 | 获取 Playbook 队列 |
| 16 | Sync 队列 | `getInternalSyncQueues()` | 292-300 | 获取同步队列 |
| 17 | 队列一致性 | `enforceQueuesConsistency()` | 305-325 | 强制队列一致性 |
| 18 | 注销连接器 | `unregisterConnector()` | 327-337 | 删除连接器队列 |
| 19 | 删除交换机 | `unregisterExchanges()` | 339-348 | 删除交换机 |
| 20 | 健康检查 | `rabbitMQIsAlive()` | 350-359 | 检查 RabbitMQ 是否存活 |
| 21 | 推送工作 | `pushToWorkerForConnector()` | 361-364 | 推送工作到连接器 |
| 22 | 推送消息 | `pushToConnector()` | 366-368 | 推送消息到连接器 |
| 23 | 获取版本 | `getRabbitMQVersion()` | 370-374 | 获取 RabbitMQ 版本 |
| 24 | 消费队列 | `consumeQueue()` | 376-431 | 消费队列消息 |

### 2.3 关键常量

```typescript
// 源码第 15-16 行
export const CONNECTOR_EXCHANGE = `${RABBIT_QUEUE_PREFIX}amqp.connector.exchange`;
export const WORKER_EXCHANGE = `${RABBIT_QUEUE_PREFIX}amqp.worker.exchange`;

// 源码第 29-30 行
const RABBITMQ_PUSH_QUEUE_PREFIX = `${RABBIT_QUEUE_PREFIX}push_`;
const RABBITMQ_LISTEN_QUEUE_PREFIX = `${RABBIT_QUEUE_PREFIX}listen_`;
```

### 2.4 配置项

```typescript
// 源码第 18-39 行
const USE_SSL = booleanConf('rabbitmq:use_ssl', false);
const QUEUE_TYPE = conf.get('rabbitmq:queue_type');
const RABBITMQ_CA = (conf.get('rabbitmq:use_ssl_ca') ?? []).map((path) => loadCert(path));
const RABBITMQ_CA_CERT = readFileFromConfig('rabbitmq:use_ssl_cert');
const RABBITMQ_CA_KEY = readFileFromConfig('rabbitmq:use_ssl_key');
const RABBITMQ_CA_PFX = readFileFromConfig('rabbitmq:use_ssl_pfx');
const RABBITMQ_CA_PASSPHRASE = conf.get('rabbitmq:use_ssl_passphrase');
const RABBITMQ_REJECT_UNAUTHORIZED = booleanConf('rabbitmq:use_ssl_reject_unauthorized', false);
const RABBITMQ_MGMT_REJECT_UNAUTHORIZED = booleanConf('rabbitmq:management_ssl_reject_unauthorized', false);
const HOSTNAME = conf.get('rabbitmq:hostname');
const PORT = conf.get('rabbitmq:port');
const USERNAME = conf.get('rabbitmq:username');
const PASSWORD = conf.get('rabbitmq:password');
const VHOST = conf.get('rabbitmq:vhost');
const USE_SSL_MGMT = booleanConf('rabbitmq:management_ssl', false);
const HOSTNAME_MGMT = conf.get('rabbitmq:hostname_management') || HOSTNAME;
const PORT_MGMT = conf.get('rabbitmq:port_management');
```

---

## 三、Java 实现结构

### 3.1 目录结构

```
src/main/java/io/opencti/database/rabbitmq/
├── RabbitMQClient.java           # RabbitMQ 客户端接口
├── RabbitMQClientImpl.java       # RabbitMQ 客户端实现
├── RabbitMQConfig.java           # Spring AMQP 配置类
├── RabbitMQConstants.java        # 常量定义
├── RabbitMQManagementClient.java # Management API 客户端
├── QueueConfig.java              # 队列配置数据类
├── ConnectorConfig.java          # 连接器配置数据类
└── RabbitMQMetrics.java          # 指标数据类
```

### 3.2 测试目录

```
src/test/java/io/opencti/database/rabbitmq/
└── RabbitMQClientTest.java       # 单元测试
```

---

## 四、子任务划分

由于总代码量约 1250 行，超过 500 行，需要分多个子任务完成。

### 子任务 1: 基础类和配置 (约 250 行)

| 文件 | 代码量 | 说明 |
|------|--------|------|
| `RabbitMQConstants.java` | ~50 行 | 常量定义 |
| `QueueConfig.java` | ~30 行 | 队列配置数据类 |
| `ConnectorConfig.java` | ~50 行 | 连接器配置数据类 |
| `RabbitMQMetrics.java` | ~30 行 | 指标数据类 |
| `RabbitMQConnectionConfig.java` | ~40 行 | 连接配置数据类 |
| `QueueDetails.java` | ~50 行 | 队列详情数据类 |

**完成后**: 编译验证

### 子任务 2: Spring 配置类 (约 150 行)

| 文件 | 代码量 | 说明 |
|------|--------|------|
| `RabbitMQConfig.java` | ~150 行 | Spring AMQP 配置类 |

**完成后**: 编译验证

### 子任务 3: Management API 客户端 (约 250 行)

| 文件 | 代码量 | 说明 |
|------|--------|------|
| `RabbitMQManagementClient.java` | ~250 行 | Management API 客户端 |

**完成后**: 编译验证

### 子任务 4: 客户端接口 (约 100 行)

| 文件 | 代码量 | 说明 |
|------|--------|------|
| `RabbitMQClient.java` | ~100 行 | 客户端接口定义 |

**完成后**: 编译验证

### 子任务 5: 客户端实现 (约 400 行)

| 文件 | 代码量 | 说明 |
|------|--------|------|
| `RabbitMQClientImpl.java` | ~400 行 | 客户端实现 |

**完成后**: 编译验证

### 子任务 6: 单元测试 (约 300 行)

| 文件 | 代码量 | 说明 |
|------|--------|------|
| `RabbitMQClientTest.java` | ~300 行 | 单元测试 |

**完成后**: 编译验证 + 测试运行

---

## 五、详细实现步骤

### 子任务 1: 基础类和配置

#### 步骤 1.1: 创建常量类

**文件**: `RabbitMQConstants.java`

```java
/**
 * RabbitMQ 常量定义
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js 第 15-30 行
 */
public final class RabbitMQConstants {
    public static String connectorExchange(String prefix) {
        return prefix + "amqp.connector.exchange";
    }
    
    public static String workerExchange(String prefix) {
        return prefix + "amqp.worker.exchange";
    }
    
    public static String pushQueuePrefix(String prefix) {
        return prefix + "push_";
    }
    
    public static String listenQueuePrefix(String prefix) {
        return prefix + "listen_";
    }
    
    public static String listenRouting(String prefix, String connectorId) {
        return prefix + "listen_routing_" + connectorId;
    }
    
    public static String pushRouting(String prefix, String connectorId) {
        return prefix + "push_routing_" + connectorId;
    }
}
```

#### 步骤 1.2: 创建数据类

**文件**: `QueueConfig.java`
```java
/**
 * 队列配置
 * 重写源文件: rabbitmq.js 第 229-234 行
 */
public record QueueConfig(
    String id,
    String name,
    String type,
    List<String> scope
) {}
```

**文件**: `ConnectorConfig.java`
```java
/**
 * 连接器配置
 * 重写源文件: rabbitmq.js 第 204-214 行
 */
public record ConnectorConfig(
    RabbitMQConnectionConfig connection,
    String push,
    String pushRouting,
    String pushExchange,
    String listen,
    String listenRouting,
    String listenExchange,
    String listenCallbackUri,
    String deadLetterRouting
) {}
```

**文件**: `RabbitMQMetrics.java`
```java
/**
 * RabbitMQ 指标
 * 重写源文件: rabbitmq.js 第 165-180 行
 */
public record RabbitMQMetrics(
    Map<String, Object> overview,
    int consumers,
    List<Map<String, Object>> queues
) {}
```

**文件**: `RabbitMQConnectionConfig.java`
```java
/**
 * RabbitMQ 连接配置
 * 重写源文件: rabbitmq.js 第 50-59 行
 */
public record RabbitMQConnectionConfig(
    String host,
    String vhost,
    boolean useSsl,
    int port,
    String user,
    String pass
) {}
```

**文件**: `QueueDetails.java`
```java
/**
 * 队列详情
 * 重写源文件: rabbitmq.js 第 89-108 行
 */
public record QueueDetails(
    int messagesNumber,
    long messagesSize
) {}
```

### 子任务 2: Spring 配置类

**文件**: `RabbitMQConfig.java`

```java
/**
 * RabbitMQ Spring 配置
 * 重写源文件: rabbitmq.js 第 41-48 行, 110-156 行
 */
@Configuration
public class RabbitMQConfig {
    
    @Bean
    public ConnectionFactory connectionFactory(RabbitMQProperties properties) {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(properties.hostname());
        factory.setPort(properties.port());
        factory.setUsername(properties.username());
        factory.setPassword(properties.password());
        factory.setVirtualHost(properties.vhost());
        
        // SSL 配置
        if (properties.useSsl()) {
            configureSsl(factory, properties);
        }
        
        return factory;
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        return template;
    }
}
```

### 子任务 3: Management API 客户端

**文件**: `RabbitMQManagementClient.java`

```java
/**
 * RabbitMQ Management API 客户端
 * 重写源文件: rabbitmq.js 第 61-74 行, 80-108 行, 165-180 行
 */
@Component
public class RabbitMQManagementClient {
    
    private final RestTemplate restTemplate;
    private final String baseUrl;
    
    /**
     * 获取队列详情
     * 重写源文件: rabbitmq.js 第 89-108 行
     */
    public QueueDetails getConnectorQueueDetails(String connectorId) {
        // 实现获取队列详情
    }
    
    /**
     * 清空队列
     * 重写源文件: rabbitmq.js 第 80-87 行
     */
    public void purgeConnectorQueues(String connectorId) {
        // 实现清空队列
    }
    
    /**
     * 获取指标
     * 重写源文件: rabbitmq.js 第 165-180 行
     */
    public RabbitMQMetrics getMetrics() {
        // 实现获取指标
    }
}
```

### 子任务 4: 客户端接口

**文件**: `RabbitMQClient.java`

```java
/**
 * RabbitMQ 客户端接口
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js
 */
public interface RabbitMQClient {
    
    // ===== 连接管理 =====
    
    /**
     * 检查 RabbitMQ 是否存活
     * 重写源文件: rabbitmq.js 第 350-359 行
     */
    boolean isAlive();
    
    /**
     * 获取 RabbitMQ 版本
     * 重写源文件: rabbitmq.js 第 370-374 行
     */
    String getVersion();
    
    // ===== 队列管理 =====
    
    /**
     * 注册连接器队列
     * 重写源文件: rabbitmq.js 第 219-250 行
     */
    ConnectorConfig registerConnectorQueues(String id, String name, String type, List<String> scope);
    
    /**
     * 注销连接器
     * 重写源文件: rabbitmq.js 第 327-337 行
     */
    void unregisterConnector(String id);
    
    /**
     * 清空连接器队列
     * 重写源文件: rabbitmq.js 第 80-87 行
     */
    void purgeConnectorQueues(String connectorId);
    
    /**
     * 初始化内部队列
     * 重写源文件: rabbitmq.js 第 274-280 行
     */
    void initializeInternalQueues();
    
    /**
     * 强制队列一致性
     * 重写源文件: rabbitmq.js 第 305-325 行
     */
    void enforceQueuesConsistency();
    
    // ===== 消息操作 =====
    
    /**
     * 发送消息
     * 重写源文件: rabbitmq.js 第 158-163 行
     */
    void send(String exchangeName, String routingKey, String message);
    
    /**
     * 推送消息到连接器
     * 重写源文件: rabbitmq.js 第 366-368 行
     */
    void pushToConnector(String connectorId, Object message);
    
    /**
     * 推送工作到连接器
     * 重写源文件: rabbitmq.js 第 361-364 行
     */
    void pushToWorkerForConnector(String connectorId, Object message);
    
    // ===== 指标 =====
    
    /**
     * 获取指标
     * 重写源文件: rabbitmq.js 第 165-180 行
     */
    RabbitMQMetrics getMetrics();
    
    /**
     * 获取连接器队列大小
     * 重写源文件: rabbitmq.js 第 183-191 行
     */
    int getConnectorQueueSize(String connectorId);
    
    /**
     * 获取最佳后台连接器 ID
     * 重写源文件: rabbitmq.js 第 192-202 行
     */
    String getBestBackgroundConnectorId();
    
    // ===== 配置 =====
    
    /**
     * 获取连接器配置
     * 重写源文件: rabbitmq.js 第 204-214 行
     */
    ConnectorConfig getConnectorConfig(String connectorId);
}
```

### 子任务 5: 客户端实现

**文件**: `RabbitMQClientImpl.java`

```java
/**
 * RabbitMQ 客户端实现
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js
 */
@Component
public class RabbitMQClientImpl implements RabbitMQClient {
    
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQManagementClient managementClient;
    private final RabbitMQProperties properties;
    
    // 实现所有接口方法...
}
```

### 子任务 6: 单元测试

**文件**: `RabbitMQClientTest.java`

```java
@ExtendWith(MockitoExtension.class)
class RabbitMQClientTest {
    
    @Mock
    private RabbitTemplate rabbitTemplate;
    
    @Mock
    private RabbitMQManagementClient managementClient;
    
    @InjectMocks
    private RabbitMQClientImpl rabbitMQClient;
    
    @Test
    void testIsAlive() {
        // 测试健康检查
    }
    
    @Test
    void testRegisterConnectorQueues() {
        // 测试队列注册
    }
    
    @Test
    void testSend() {
        // 测试消息发送
    }
    
    // 更多测试...
}
```

---

## 六、Maven 依赖

```xml
<!-- RabbitMQ - Spring AMQP -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

---

## 七、风险与应对

| 风险 | 等级 | 应对措施 |
|------|------|----------|
| SSL 配置复杂 | 🟡 | 参考源码第 111-119 行实现 |
| Management API 认证 | 🟡 | 使用 HTTP Basic Auth |
| 消息确认机制 | 🟡 | 使用 ConfirmCallback |
| 队列类型 (classic/quorum) | 🟡 | 根据配置动态设置 |

---

## 八、执行顺序

| 序号 | 子任务 | 预估时间 | 完成后操作 |
|------|--------|----------|------------|
| 1 | 子任务 1: 基础类和配置 | 1 小时 | 编译验证 |
| 2 | 子任务 2: Spring 配置类 | 1 小时 | 编译验证 |
| 3 | 子任务 3: Management API 客户端 | 2 小时 | 编译验证 |
| 4 | 子任务 4: 客户端接口 | 0.5 小时 | 编译验证 |
| 5 | 子任务 5: 客户端实现 | 3 小时 | 编译验证 |
| 6 | 子任务 6: 单元测试 | 2 小时 | 编译验证 + 测试运行 |

**总计**: 约 9.5 小时（约 1.5 天）

---

## 九、下一步行动

确认计划后，按以下顺序执行：

1. **子任务 1**: 创建 `database/rabbitmq/` 目录结构，实现基础类和配置
2. **子任务 2**: 实现 `RabbitMQConfig.java`
3. **子任务 3**: 实现 `RabbitMQManagementClient.java`
4. **子任务 4**: 实现 `RabbitMQClient.java` 接口
5. **子任务 5**: 实现 `RabbitMQClientImpl.java`
6. **子任务 6**: 编写单元测试
7. 更新 MODULE_OVERVIEW.md 文档
8. 更新项目重写计划.md 文档
