# Redis 模块源码一致性检查计划

## 一、检查范围

### 需要检查的文件

| Java 文件 | TypeScript 源文件 |
|-----------|-------------------|
| `common/config/RedisProperties.java` | `config/default.json` (redis 配置部分) |
| `database/redis/RedisConfig.java` | `database/redis.ts` (配置相关函数) |
| `database/redis/RedisClient.java` | `database/redis.ts` (接口定义) |
| `database/redis/RedisClientImpl.java` | `database/redis.ts` (实现) |
| `database/redis/session/SessionManager.java` | `database/redis.ts` (session 相关) |
| `database/redis/lock/LockManager.java` | `database/redis.ts` (lock 相关) |
| `database/redis/lock/DistributedLock.java` | `database/redis.ts` (lock 实现) |
| `database/redis/work/WorkManager.java` | `database/redis.ts` (work 相关) |
| `database/redis/stream/RedisStreamClient.java` | `database/redis-stream.ts` |
| `database/redis/cache/CacheManager.java` | `database/cache.ts` |

## 二、检查步骤

### Step 1: RedisProperties 配置属性检查
- 对比 `default.json` 中的 redis 配置项
- 确保所有配置属性都已实现
- 确保属性类型一致

### Step 2: RedisConfig 配置类检查
- 对比 `redis.ts` 中的 `redisOptions`, `clusterOptions`, `sentinelOptions` 函数
- 检查连接配置逻辑是否一致

### Step 3: RedisClient 接口检查
- 对比 `redis.ts` 中导出的方法
- 确保所有 Redis 操作方法都已定义

### Step 4: RedisClientImpl 实现检查
- 逐行对比 `redis.ts` 中的实现逻辑
- 检查 Redis 命令的使用是否正确
- 检查错误处理是否一致

### Step 5: Session 管理检查
- 对比 session 相关函数
- 检查 session 数据结构

### Step 6: Lock 分布式锁检查
- 对比 lock 相关函数
- 检查 Redlock 实现逻辑

### Step 7: Work 工作管理检查
- 对比 work 相关函数
- 检查工作状态管理

### Step 8: Stream 流处理检查
- 对比 `redis-stream.ts` 实现
- 检查事件发布订阅逻辑

### Step 9: Cache 缓存管理检查
- 对比 `cache.ts` 实现
- 检查缓存操作逻辑

## 三、输出格式

每个检查项将输出：
1. **一致** ✅ - 代码逻辑与源码一致
2. **差异** ⚠️ - 存在差异，需要说明原因
3. **缺失** ❌ - 源码有但 Java 实现缺失
4. **多余** ➕ - Java 实现有但源码没有

## 四、执行计划

1. 读取 TypeScript 源码
2. 读取 Java 实现代码
3. 逐行对比分析
4. 输出检查报告
5. 如有差异，提出修复建议
