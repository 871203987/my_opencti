# 当前重写实现与源码一致性检查计划

## 一、检查范围

根据项目重写计划，已完成的模块：

| Phase | 模块 | 状态 |
|-------|------|------|
| Phase 1.1 | config/ | ✅ 已完成 |
| Phase 1.2 | utils/ | ✅ 已完成 |
| Phase 1.3 | types/ | ✅ 已完成 |
| Phase 2.1 | database/redis/ | ✅ 已完成 |

## 二、检查步骤

### Step 1: config 模块检查

需要对比的文件：

| Java 文件 | TypeScript 源文件 |
|-----------|-------------------|
| `AppProperties.java` | `config/default.json` |
| `ElasticsearchProperties.java` | `config/default.json` (elasticsearch 配置) |
| `MinioProperties.java` | `config/default.json` (minio 配置) |
| `RabbitMQProperties.java` | `config/default.json` (rabbitmq 配置) |
| `RedisProperties.java` | `config/default.json` (redis 配置) |
| `ProxyConfiguration.java` | `config/proxy-config.ts` |
| `CredentialsProvider.java` | `config/credentials.ts` |
| `OpenCTIConfiguration.java` | `config/conf.js` |

### Step 2: utils 模块检查

需要对比的文件：

| Java 文件 | TypeScript 源文件 |
|-----------|-------------------|
| `FormatUtils.java` | `utils/format.js` |
| `HashUtils.java` | `utils/hash.ts` |
| `Base64Utils.java` | `utils/base64.ts` |
| `SyntaxUtils.java` | `utils/syntax.js` |
| `Queue.java` | `utils/queue.js` |
| `SortingUtils.java` | `utils/sorting.ts` |
| `AccessUtils.java` | `utils/access.ts` |
| `HttpClientUtils.java` | `utils/http-client.ts` |
| `DataProcessingUtils.java` | `utils/data-processing.ts` |
| `VersionUtils.java` | `utils/version.ts` |

### Step 3: types 模块检查

需要对比的文件：

| Java 文件 | TypeScript 源文件 |
|-----------|-------------------|
| `stix/*.java` | `types/stix-2-1-common.d.ts` |
| `stix/sdo/*.java` | `types/stix-2-1-sdo.d.ts` |
| `stix/sro/*.java` | `types/stix-2-1-sro.d.ts` |
| `stix/smo/*.java` | `types/stix-2-1-smo.d.ts` |
| `user/*.java` | `types/user.d.ts` |
| `store/*.java` | `types/store.d.ts` |
| `event/Event.java` | `types/event.d.ts` |
| `connector/Connector.java` | `types/connector.d.ts` |

### Step 4: database/redis 模块检查

需要对比的文件：

| Java 文件 | TypeScript 源文件 |
|-----------|-------------------|
| `RedisClient.java` | `database/redis.ts` (接口定义) |
| `RedisClientImpl.java` | `database/redis.ts` (实现) |
| `RedisConfig.java` | `database/redis.ts` (配置相关) |
| `session/SessionManager.java` | `database/redis.ts` (session 相关) |
| `lock/LockManager.java` | `database/redis.ts` (lock 相关) |
| `lock/DistributedLock.java` | `database/redis.ts` (lock 实现) |
| `work/WorkManager.java` | `database/redis.ts` (work 相关) |
| `stream/RedisStreamClient.java` | `database/redis-stream.ts` |
| `stream/SseEvent.java` | `database/redis-stream.ts` |
| `stream/StreamProcessor.java` | `database/redis-stream.ts` |
| `cache/CacheManager.java` | `database/cache.ts` |
| `pubsub/PubSubManager.java` | `database/redis.ts` (pubsub 相关) |

## 三、检查维度

每个文件需要检查以下维度：

### 3.1 功能完整性
- [ ] 所有公开方法都已实现
- [ ] 所有参数都已处理
- [ ] 所有返回值类型正确

### 3.2 逻辑一致性
- [ ] 算法逻辑与源码一致
- [ ] 边界条件处理一致
- [ ] 错误处理一致

### 3.3 配置一致性
- [ ] 配置项名称一致
- [ ] 配置项类型一致
- [ ] 默认值一致

### 3.4 键名一致性（Redis）
- [ ] Redis 键名格式一致
- [ ] 键前缀处理一致
- [ ] 过期时间处理一致

## 四、输出格式

每个模块输出检查报告，包含：

1. **一致性评分**: 0-100%
2. **已实现功能列表**
3. **缺失功能列表**
4. **差异说明**
5. **修复建议**

## 五、执行顺序

1. 先检查 config 模块（基础设施）
2. 再检查 utils 模块（工具类）
3. 然后检查 types 模块（类型定义）
4. 最后检查 database/redis 模块（已完成 Redis 检查，需要复查）

## 六、预期产出

完成所有检查后，生成：
1. 每个模块的一致性检查报告
2. 总体一致性评分
3. 需要修复的问题清单
4. 后续开发建议
