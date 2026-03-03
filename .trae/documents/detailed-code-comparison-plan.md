# 逐行代码对比检查计划

## 一、检查范围

根据项目重写计划，已完成的模块：

| Phase | 模块 | 文件数 |
|-------|------|--------|
| Phase 1.1 | config/ | 14个文件 |
| Phase 1.2 | utils/ | 11个文件 |
| Phase 1.3 | types/ | 51个文件 |
| Phase 2.1 | database/redis/ | 17个文件 |

**总计**: 93个 Java 文件需要对比检查

## 二、检查方法

### 2.1 检查维度

每个文件检查以下维度：

1. **功能完整性**: 所有公开方法是否都已实现
2. **参数一致性**: 参数类型和数量是否一致
3. **返回值一致性**: 返回值类型是否一致
4. **逻辑一致性**: 算法逻辑是否一致
5. **键名一致性**: Redis 键名是否一致（仅 Redis 模块）

### 2.2 差异分类

| 分类 | 说明 | 处理方式 |
|------|------|----------|
| **当前修复** | 影响功能正确性的差异 | 立即修复 |
| **后续实现** | 属于后续 Phase 的功能 | 标记待实现 |
| **设计差异** | Java 与 TypeScript 设计不同但功能等价 | 记录说明 |
| **命名差异** | 命名风格差异（如 snake_case vs camelCase） | 无需修复 |

## 三、检查步骤

### Step 1: Config 模块检查（14个文件）

逐个对比：
- AppProperties.java ↔ default.json (app.*)
- ElasticsearchProperties.java ↔ default.json (elasticsearch.*)
- MinioProperties.java ↔ default.json (minio.*)
- RabbitMQProperties.java ↔ default.json (rabbitmq.*)
- RedisProperties.java ↔ default.json (redis.*)
- ProxyConfiguration.java ↔ proxy-config.ts
- CredentialsProvider.java ↔ credentials.ts
- OpenCTIConfiguration.java ↔ conf.js
- 其他配置类

### Step 2: Utils 模块检查（11个文件）

逐个对比：
- FormatUtils.java ↔ format.js
- HashUtils.java ↔ hash.ts
- Base64Utils.java ↔ base64.ts
- SyntaxUtils.java ↔ syntax.js
- Queue.java ↔ queue.js
- SortingUtils.java ↔ sorting.ts
- AccessUtils.java ↔ access.ts
- HttpClientUtils.java ↔ http-client.ts
- DataProcessingUtils.java ↔ data-processing.ts
- VersionUtils.java ↔ version.ts

### Step 3: Types 模块检查（51个文件）

逐个对比：
- stix/*.java ↔ stix-2-1-common.d.ts
- stix/sdo/*.java ↔ stix-2-1-sdo.d.ts
- stix/sro/*.java ↔ stix-2-1-sro.d.ts
- stix/smo/*.java ↔ stix-2-1-smo.d.ts
- user/*.java ↔ user.d.ts
- store/*.java ↔ store.d.ts
- event/*.java ↔ event.d.ts
- connector/*.java ↔ connector.d.ts

### Step 4: Database/Redis 模块检查（17个文件）

逐个对比：
- RedisClient.java ↔ redis.ts (接口)
- RedisClientImpl.java ↔ redis.ts (实现)
- RedisConfig.java ↔ redis.ts (配置)
- session/SessionManager.java ↔ redis.ts (session)
- session/Session.java ↔ redis.ts (session 数据结构)
- lock/LockManager.java ↔ redis.ts (lock)
- lock/DistributedLock.java ↔ redis.ts (lock 实现)
- lock/LockOptions.java ↔ redis.ts (lock 选项)
- work/WorkManager.java ↔ redis.ts (work)
- work/WorkStatus.java ↔ redis.ts (work 状态)
- stream/RedisStreamClient.java ↔ redis-stream.ts
- stream/SseEvent.java ↔ redis-stream.ts
- stream/StreamProcessor.java ↔ redis-stream.ts
- cache/CacheManager.java ↔ cache.ts
- pubsub/PubSubManager.java ↔ redis.ts (pubsub)

## 四、输出格式

每个文件输出：

```markdown
### 文件名.java

**源文件**: TypeScript 源文件路径

**一致性评分**: X%

**已实现功能**:
- [x] 功能1
- [x] 功能2

**缺失功能**:
- [ ] 功能3 (属于 Phase X)

**差异说明**:
1. 差异1: 说明
2. 差异2: 说明

**修复建议**:
- 当前修复: 无 / 具体建议
- 后续实现: 功能列表
```

## 五、执行计划

由于文件数量较多（93个），将分批执行：

### 第一批：Config 模块（14个文件）
### 第二批：Utils 模块（11个文件）
### 第三批：Types 模块（51个文件）
### 第四批：Database/Redis 模块（17个文件）

每批完成后生成详细报告，最后汇总。
