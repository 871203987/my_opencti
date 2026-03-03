# Redis 模块源码一致性检查报告

## 一、RedisProperties 配置属性检查

### TypeScript 源码配置项 (default.json + redis.ts)

| 配置项 | 类型 | Java 实现 | 状态 |
|--------|------|-----------|------|
| `redis:mode` | string | `mode` | ✅ 一致 |
| `redis:namespace` | string | `namespace` | ✅ 一致 |
| `redis:hostname` | string | `hostname` | ✅ 一致 |
| `redis:port` | number | `port` | ✅ 一致 |
| `redis:hostnames` | string[] | `hostnames` (List<String>) | ✅ 一致 |
| `redis:database` | number | `database` | ✅ 一致 |
| `redis:username` | string | `username` | ✅ 一致 |
| `redis:password` | string | `password` | ✅ 一致 |
| `redis:use_ssl` | boolean | `useSsl` | ✅ 一致 |
| `redis:ca` | string[] | `ca` (List<String>) | ✅ 一致 |
| `redis:trimming` | number | `trimming` | ✅ 一致 |
| `redis:scale_reads` | string | `scaleReads` | ✅ 一致 |
| `redis:nat_map` | string[] | `natMap` (List<String>) | ✅ 一致 |
| `redis:host_ip_family` | number | `hostIpFamily` | ✅ 一致 |
| `redis:sentinel_master_name` | string | `sentinelMasterName` | ✅ 一致 |
| `redis:sentinel_role` | string | `sentinelRole` | ✅ 一致 |
| `redis:sentinel_username` | string | `sentinelUsername` | ✅ 一致 |
| `redis:sentinel_password` | string | `sentinelPassword` | ✅ 一致 |

### RedisConfig.java 检查

| TypeScript 函数 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `redisOptions()` | `createRedisUri()` | ✅ 一致 | 连接配置逻辑一致 |
| `clusterOptions()` | `createClusterUris()` | ✅ 一致 | 集群配置逻辑一致 |
| `generateClusterNodes()` | 内联处理 | ✅ 一致 | 解析 HOST:PORT 格式 |
| `generateNatMap()` | ❌ 缺失 | 需要添加 | NAT 映射功能未实现 |
| `connectionName()` | `buildConnectionName()` | ✅ 一致 | 连接名称生成逻辑一致 |
| `configureCA()` | `createSslOptions()` | ✅ 一致 | SSL 配置逻辑一致 |

---

## 二、RedisClient 接口检查

### 基础操作

| TypeScript 操作 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `client.get(key)` | `get(key)` | ✅ 一致 | |
| `client.set(key, value)` | `set(key, value)` | ✅ 一致 | |
| `client.set(key, value, 'EX', seconds)` | `setex(key, seconds, value)` | ✅ 一致 | |
| `client.set(key, value, 'NX', 'EX', seconds)` | `set(key, value, "NX", ex)` | ✅ 一致 | |
| `client.del(key)` | `del(key)` | ✅ 一致 | |
| `client.ttl(key)` | `ttl(key)` | ✅ 一致 | |
| `client.expire(key, seconds)` | `expire(key, seconds)` | ✅ 一致 | |
| `client.exists(key)` | `exists(key)` | ✅ 一致 | |

### Hash 操作

| TypeScript 操作 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `client.hset(key, field, value)` | `hset(key, field, value)` | ✅ 一致 | |
| `client.hset(key, object)` | `hset(key, Map)` | ✅ 一致 | |
| `client.hget(key, field)` | `hget(key, field)` | ✅ 一致 | |
| `client.hgetall(key)` | `hgetall(key)` | ✅ 一致 | |
| `client.hdel(key, fields)` | `hdel(key, fields...)` | ✅ 一致 | |
| `client.hincrby(key, field, amount)` | `hincrby(key, field, amount)` | ✅ 一致 | |

### Sorted Set 操作

| TypeScript 操作 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `client.zadd(key, score, member)` | `zadd(key, score, member)` | ✅ 一致 | |
| `client.zrem(key, member)` | `zrem(key, member)` | ✅ 一致 | |
| `client.zrange(key, start, stop)` | `zrange(key, start, stop)` | ✅ 一致 | |
| `client.zremrangebyscore(key, min, max)` | `zremrangebyscore(key, min, max)` | ✅ 一致 | |
| `client.zcount(key, min, max)` | `zcount(key, min, max)` | ✅ 一致 | |
| `client.zcard(key)` | `zcard(key)` | ✅ 一致 | |

### Stream 操作

| TypeScript 操作 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `client.call('XADD', ...)` | `xadd(...)` | ✅ 一致 | |
| `client.call('XREAD', ...)` | `xread(...)` | ✅ 一致 | |
| `client.call('XRANGE', ...)` | `xrange(...)` | ✅ 一致 | |
| `client.xinfo('STREAM', key)` | `xinfo(key)` | ✅ 一致 | |

### Pub/Sub 操作

| TypeScript 操作 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `client.publish(channel, message)` | `publish(channel, message)` | ✅ 一致 | |
| `RedisPubSub.subscribe()` | `subscribe(channel, handler)` | ⚠️ 差异 | 实现方式不同，功能等价 |

### 事务操作

| TypeScript 操作 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `client.multi()` + `tx.exec()` | `execute(transaction)` | ✅ 一致 | 事务封装逻辑一致 |

---

## 三、Session 管理检查

| TypeScript 函数 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `setSession(key, value, expirationTime)` | `setSession(key, session, expirationSeconds)` | ⚠️ 差异 | Java 使用 Session 对象，TS 使用 JSON |
| `getSession(key)` | `getSession(key)` | ✅ 一致 | 返回值结构略有不同 |
| `killSession(key)` | `killSession(key)` | ✅ 一致 | |
| `getSessionKeys()` | `getSessionKeys()` | ✅ 一致 | |
| `getSessions()` | `getSessions()` | ✅ 一致 | |
| `extendSession(sessionId, extension)` | `extendSession(sessionId, extensionSeconds)` | ✅ 一致 | |
| `clearSessions()` | `clearSessions()` | ✅ 一致 | |

**差异说明**：
- TypeScript 使用 `platform_sessions` 作为列表键名
- Java 使用 `sessions` 作为列表键名
- 需要统一为 `platform_sessions`

---

## 四、Lock 分布式锁检查

| TypeScript 函数 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `lockResource(resources, opts)` | `lockResource(resources, options)` | ⚠️ 差异 | Java 未使用 Redlock 算法 |
| `redisAddDeletions(internalIds, draftId)` | `addDeletions(internalIds, draftId)` | ⚠️ 差异 | 键名不同 |
| `redisFetchLatestDeletions()` | `fetchLatestDeletions(draftId, sinceTimestamp)` | ⚠️ 差异 | 参数和键名不同 |

**差异说明**：
- TypeScript 使用 `@sesamecare-oss/redlock` 库实现分布式锁
- Java 使用简单的 SET NX 实现，不是真正的 Redlock 算法
- TypeScript 使用 `platform-deletions` 作为删除追踪键
- Java 使用 `deletions:{draftId}` 格式

---

## 五、Work 工作管理检查

| TypeScript 函数 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `redisInitializeWork(workId)` | `initializeWork(workId)` | ⚠️ 差异 | 初始数据结构不同 |
| `redisGetWork(internalId)` | `getWork(workId)` | ✅ 一致 | |
| `redisUpdateWorkFigures(workId)` | `updateWorkFigures(workId)` | ⚠️ 差异 | TS 有更多字段更新 |
| `isWorkCompleted(workId)` | `isWorkCompleted(workId)` | ✅ 一致 | |
| `redisGetConnectorStatus(connectorId)` | `getConnectorStatus(connectorId)` | ⚠️ 差异 | 键名格式不同 |
| `redisUpdateActionExpectation(user, workId, expectation)` | `updateActionExpectation(workId, expectation)` | ⚠️ 差异 | Java 缺少 user 参数 |
| `redisDeleteWorks(internalIds)` | `deleteWorks(internalIds)` | ✅ 一致 | |

**差异说明**：
- TypeScript 键名格式: 直接使用 `workId` 或 `work:{connectorId}`
- Java 键名格式: `work:{workId}` 或 `connector:{connectorId}`

---

## 六、Stream 流处理检查

| TypeScript 函数 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `rawPushToStream(event)` | `pushToStream(event)` | ⚠️ 差异 | 键名和裁剪逻辑不同 |
| `rawFetchStreamInfo(streamName)` | `fetchStreamInfo(streamName)` | ✅ 一致 | |
| `rawCreateStreamProcessor(provider, callback, opts)` | `createStreamProcessor(provider, streamName, callback)` | ⚠️ 差异 | 实现方式不同 |
| `rawStoreNotificationEvent(event)` | `storeNotificationEvent(event)` | ✅ 一致 | |
| `rawStoreActivityEvent(event)` | `storeActivityEvent(event)` | ✅ 一致 | |

**差异说明**：
- TypeScript 使用 `REDIS_PREFIX + LIVE_STREAM_NAME` 格式
- Java 使用 `stream:{streamName}` 格式
- 裁剪配置: TS 使用 `redis:trimming`，Java 使用固定值

---

## 七、Cache 缓存管理检查

| TypeScript 函数 | Java 方法 | 状态 | 说明 |
|-----------------|-----------|------|------|
| `writeCacheForEntity(entityType, data)` | `writeCache(entityType, entityId, data)` | ⚠️ 差异 | 实现方式完全不同 |
| `resetCacheForEntity(entityType)` | `resetCache(entityType)` | ⚠️ 差异 | |
| `getEntitiesListFromCache(context, user, type)` | `getEntitiesList(entityType, clazz)` | ⚠️ 差异 | Java 缺少 context/user 参数 |
| `getEntitiesMapFromCache(context, user, type)` | `getEntitiesMap(entityType, clazz)` | ⚠️ 差异 | |
| `getEntityFromCache(context, user, type)` | `getEntity(entityType, entityId, clazz)` | ⚠️ 差异 | |

**差异说明**：
- TypeScript 缓存是内存缓存，不使用 Redis
- Java 实现使用 Redis 作为缓存存储
- 这是设计差异，不是错误

---

## 八、缺失功能清单

### 高优先级 (P0)

| 功能 | TypeScript 源码位置 | 说明 |
|------|---------------------|------|
| `generateNatMap()` | redis.ts:62-71 | NAT 映射功能 |
| Redlock 分布式锁算法 | redis.ts:398-464 | 当前实现不是真正的 Redlock |
| `notify()` 函数 | redis.ts:315-332 | 事件通知功能 |
| Edit Context 相关函数 | redis.ts:336-351 | 编辑上下文管理 |

### 中优先级 (P1)

| 功能 | TypeScript 源码位置 | 说明 |
|------|---------------------|------|
| Cluster 实例管理 | redis.ts:511-518 | 集群节点注册和发现 |
| Playbook 执行管理 | redis.ts:522-551 | Playbook 执行状态追踪 |
| Support Package 状态 | redis.ts:555-585 | 支持包状态管理 |
| Exclusion List 缓存 | redis.ts:588-614 | 排除列表缓存 |
| Forgot Password OTP | redis.ts:617-649 | 忘记密码 OTP 管理 |

### 低优先级 (P2)

| 功能 | TypeScript 源码位置 | 说明 |
|------|---------------------|------|
| Telemetry Gauges | redis.ts:654-689 | 遥测数据管理 |
| Manager Stream State | redis.ts:692-701 | 管理器流状态 |
| Connector Logs | redis.ts:704-712 | 连接器日志 |
| Connector Health Metrics | redis.ts:715-732 | 连接器健康指标 |

---

## 九、键名差异汇总

| 功能 | TypeScript 键名 | Java 键名 | 建议 |
|------|-----------------|-----------|------|
| Session 列表 | `platform_sessions` | `sessions` | 统一为 `platform_sessions` |
| Lock 键 | `{locks}:{id}` | `lock:{id}` | 统一为 `{locks}:{id}` |
| Deletion 追踪 | `platform-deletions` | `deletions:{draftId}` | 统一为 `platform-deletions` |
| Work 键 | `{workId}` 或 `work:{connectorId}` | `work:{workId}` | 需要统一 |
| Stream 键 | `{prefix}{stream_name}` | `stream:{streamName}` | 需要统一 |

---

## 十、总结

### 一致性评分

| 模块 | 一致性 | 说明 |
|------|--------|------|
| RedisProperties | 95% | 配置属性基本一致 |
| RedisConfig | 90% | 缺少 NAT 映射功能 |
| RedisClient 基础操作 | 95% | 核心操作一致 |
| Session 管理 | 85% | 键名有差异 |
| Lock 分布式锁 | 60% | 未使用 Redlock 算法 |
| Work 管理 | 75% | 键名和部分逻辑有差异 |
| Stream 处理 | 70% | 键名和实现方式有差异 |
| Cache 管理 | 50% | 设计差异（内存 vs Redis） |

### 建议修复优先级

1. **立即修复**: 键名统一，确保与 TypeScript 源码一致
2. **高优先级**: 实现 Redlock 分布式锁算法
3. **中优先级**: 补充缺失的业务功能
4. **低优先级**: 优化和完善细节
