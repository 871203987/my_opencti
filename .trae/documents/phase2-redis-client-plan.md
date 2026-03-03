# Phase 2.1: Redis客户端实现计划

## 一、任务概述

将OpenCTI的Redis客户端从TypeScript/Node.js重写为Java 21实现。

### 原始文件
- `opencti-platform/opencti-graphql/src/database/redis.ts` (~730行)
- `opencti-platform/opencti-graphql/src/database/redis-stream.ts` (~246行)
- `opencti-platform/opencti-graphql/src/database/cache.ts` (~178行)

### 预估工作量
- 源码总量: ~1,154行
- 预估Java代码: ~2,500行
- 预估时间: 2天

---

## 二、功能分析

### 2.1 Redis连接模式
| 模式 | 说明 | 优先级 |
|------|------|--------|
| Single | 单机模式 | P0 |
| Cluster | 集群模式 | P0 |
| Sentinel | 哨兵模式 | P1 |

### 2.2 核心功能模块

#### A. 连接管理
- `createRedisClient()` - 创建Redis客户端
- `initializeRedisClients()` - 初始化多个客户端连接
- `shutdownRedisClients()` - 关闭所有连接
- `redisIsAlive()` - 健康检查
- `getRedisVersion()` - 获取版本

#### B. 基础操作
- `redisTx()` - 事务操作
- `setKeyWithList()` - 带列表的键设置
- `delKeyWithList()` - 带列表的键删除
- `keysFromList()` - 从列表获取键

#### C. 会话管理
- `setSession()` - 设置会话
- `getSession()` - 获取会话
- `killSession()` - 终止会话
- `getSessions()` - 获取所有会话
- `extendSession()` - 延长会话

#### D. 分布式锁
- `lockResource()` - 获取锁
- `redisAddDeletions()` - 添加删除标记
- `redisFetchLatestDeletions()` - 获取最新删除

#### E. 工作管理
- `redisInitializeWork()` - 初始化工作
- `redisGetWork()` - 获取工作状态
- `redisUpdateWorkFigures()` - 更新工作进度
- `isWorkCompleted()` - 检查工作完成

#### F. 发布订阅
- `pubSubSubscription()` - 订阅
- `pubSubAsyncIterator()` - 异步迭代器
- `notify()` - 发布通知

#### G. Redis Stream
- `rawPushToStream()` - 推送到流
- `rawFetchStreamInfo()` - 获取流信息
- `rawCreateStreamProcessor()` - 创建流处理器
- `rawStoreNotificationEvent()` - 存储通知事件
- `rawStoreActivityEvent()` - 存储活动事件

#### H. 缓存管理
- `writeCacheForEntity()` - 写入缓存
- `resetCacheForEntity()` - 重置缓存
- `getEntitiesMapFromCache()` - 获取实体Map
- `getEntitiesListFromCache()` - 获取实体列表

---

## 三、实施步骤

### Step 1: 创建模块结构 (30分钟)

```
opencti-database/
├── pom.xml
└── src/main/java/io/opencti/database/
    └── redis/
        ├── RedisClient.java           # 主客户端
        ├── RedisConfig.java           # 配置类
        ├── RedisConnectionFactory.java # 连接工厂
        ├── RedisMode.java             # 连接模式枚举
        ├── RedisProperties.java       # 配置属性
        ├── session/
        │   ├── SessionManager.java    # 会话管理
        │   └── Session.java           # 会话对象
        ├── lock/
        │   ├── DistributedLock.java   # 分布式锁
        │   └── LockManager.java       # 锁管理器
        ├── stream/
        │   ├── RedisStreamClient.java # Stream客户端
        │   └── StreamProcessor.java   # 流处理器
        ├── cache/
        │   └── CacheManager.java      # 缓存管理
        └── work/
            └── WorkManager.java       # 工作管理
```

### Step 2: 实现配置和连接 (1小时)

**文件清单:**
1. `RedisProperties.java` - 配置属性
2. `RedisMode.java` - 连接模式枚举
3. `RedisConfig.java` - Spring配置类
4. `RedisConnectionFactory.java` - 连接工厂

**关键实现:**
- 支持Single/Cluster/Sentinel三种模式
- SSL/TLS支持
- 自动重连策略
- 连接池配置

### Step 3: 实现基础客户端 (1小时)

**文件清单:**
1. `RedisClient.java` - 主客户端接口和实现

**关键方法:**
```java
public interface RedisClient {
    // 基础操作
    String get(String key);
    void set(String key, String value);
    void setex(String key, int seconds, String value);
    void del(String key);
    Long ttl(String key);
    Boolean expire(String key, int seconds);
    
    // 哈希操作
    void hset(String key, String field, String value);
    void hset(String key, Map<String, String> data);
    String hget(String key, String field);
    Map<String, String> hgetall(String key);
    Long hincrby(String key, String field, long amount);
    
    // 有序集合
    Long zadd(String key, double score, String member);
    Long zrem(String key, String member);
    List<String> zrange(String key, long start, long stop);
    Long zremrangebyscore(String key, String min, String max);
    
    // 事务
    <T> T execute(TransactionCallback<T> callback);
    
    // 健康检查
    boolean isAlive();
    String getVersion();
}
```

### Step 4: 实现会话管理 (45分钟)

**文件清单:**
1. `Session.java` - 会话对象
2. `SessionManager.java` - 会话管理器

**关键方法:**
```java
public class SessionManager {
    void setSession(String key, Object value, int expirationSeconds);
    Optional<Session> getSession(String key);
    Session killSession(String key);
    List<String> getSessionKeys();
    List<Session> getSessions();
    void extendSession(String sessionId, int extension);
    void clearSessions();
}
```

### Step 5: 实现分布式锁 (1小时)

**文件清单:**
1. `DistributedLock.java` - 锁对象
2. `LockManager.java` - 锁管理器

**关键方法:**
```java
public class LockManager {
    DistributedLock lockResource(List<String> resources, LockOptions options);
    void addDeletions(List<String> internalIds, String draftId);
    List<String> fetchLatestDeletions();
}

public class DistributedLock {
    void unlock();
    void extend();
    AbortSignal getSignal();
}
```

### Step 6: 实现工作管理 (30分钟)

**文件清单:**
1. `WorkManager.java` - 工作管理器

**关键方法:**
```java
public class WorkManager {
    void initializeWork(String workId);
    Map<String, String> getWork(String workId);
    WorkStatus updateWorkFigures(String workId);
    WorkStatus isWorkCompleted(String workId);
    void updateActionExpectation(String workId, int expectation);
    String getConnectorStatus(String connectorId);
    void deleteWorks(List<String> internalIds);
}
```

### Step 7: 实现Redis Stream (1小时)

**文件清单:**
1. `RedisStreamClient.java` - Stream客户端
2. `StreamProcessor.java` - 流处理器

**关键方法:**
```java
public class RedisStreamClient {
    void pushToStream(BaseEvent event);
    StreamInfo fetchStreamInfo(String streamName);
    StreamProcessor createStreamProcessor(String provider, Consumer<List<SseEvent>> callback);
    void storeNotificationEvent(StreamNotifEvent event);
    void storeActivityEvent(ActivityStreamEvent event);
}
```

### Step 8: 实现缓存管理 (45分钟)

**文件清单:**
1. `CacheManager.java` - 缓存管理器

**关键方法:**
```java
public class CacheManager {
    <T> void writeCache(String entityType, T data);
    void resetCache(String entityType);
    <T> List<T> getEntitiesList(String entityType);
    <T> Map<String, T> getEntitiesMap(String entityType);
    void refreshCache(BasicStoreCommon instance);
}
```

### Step 9: 编写单元测试 (1小时)

**测试文件:**
1. `RedisClientTest.java` - 客户端测试
2. `SessionManagerTest.java` - 会话测试
3. `LockManagerTest.java` - 锁测试
4. `WorkManagerTest.java` - 工作测试
5. `RedisStreamClientTest.java` - Stream测试

### Step 10: 编译验证 (15分钟)

- Maven编译
- 运行所有测试
- 代码质量检查

---

## 四、依赖配置

### pom.xml 新增依赖

```xml
<dependencies>
    <!-- Redis - Lettuce -->
    <dependency>
        <groupId>io.lettuce</groupId>
        <artifactId>lettuce-core</artifactId>
    </dependency>
    
    <!-- Redis PubSub for GraphQL -->
    <dependency>
        <groupId>io.lettuce</groupId>
        <artifactId>lettuce-core</artifactId>
    </dependency>
    
    <!-- JSON处理 -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    
    <!-- 测试 -->
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>testcontainers</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 五、文件清单汇总

| 序号 | 文件路径 | 预估行数 | 说明 |
|------|----------|----------|------|
| 1 | `redis/RedisMode.java` | 20 | 连接模式枚举 |
| 2 | `redis/RedisProperties.java` | 80 | 配置属性 |
| 3 | `redis/RedisConfig.java` | 100 | Spring配置 |
| 4 | `redis/RedisConnectionFactory.java` | 150 | 连接工厂 |
| 5 | `redis/RedisClient.java` | 300 | 主客户端接口 |
| 6 | `redis/RedisClientImpl.java` | 400 | 客户端实现 |
| 7 | `redis/session/Session.java` | 50 | 会话对象 |
| 8 | `redis/session/SessionManager.java` | 150 | 会话管理 |
| 9 | `redis/lock/DistributedLock.java` | 80 | 分布式锁 |
| 10 | `redis/lock/LockManager.java` | 200 | 锁管理器 |
| 11 | `redis/lock/LockOptions.java` | 30 | 锁选项 |
| 12 | `redis/work/WorkManager.java` | 150 | 工作管理 |
| 13 | `redis/work/WorkStatus.java` | 40 | 工作状态 |
| 14 | `redis/stream/RedisStreamClient.java` | 200 | Stream客户端 |
| 15 | `redis/stream/StreamProcessor.java` | 150 | 流处理器 |
| 16 | `redis/stream/SseEvent.java` | 40 | SSE事件 |
| 17 | `redis/cache/CacheManager.java` | 150 | 缓存管理 |
| 18 | `redis/pubsub/PubSubManager.java` | 100 | 发布订阅 |

**总计**: ~2,290行

---

## 六、注意事项

### 6.1 Python调用点
无Python调用

### 6.2 与原项目差异
- 使用Lettuce替代ioredis
- 使用Spring Data Redis抽象
- 分布式锁使用Redisson或自实现

### 6.3 测试策略
- 使用Testcontainers启动Redis容器
- Mock测试用于单元测试
- 集成测试使用真实Redis

---

## 七、验收标准

1. ✅ 所有源文件添加原文件路径注释
2. ✅ 所有方法添加原方法路径注释
3. ✅ Maven编译通过
4. ✅ 单元测试覆盖率 > 80%
5. ✅ 支持Single/Cluster/Sentinel模式
6. ✅ 分布式锁功能正常
7. ✅ 会话管理功能正常
8. ✅ Redis Stream功能正常
