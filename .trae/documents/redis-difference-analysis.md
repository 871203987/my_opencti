# Redis 模块差异分析报告

## 一、差异分类原则

根据项目重写计划的优先级定义：
- **P0**: 核心基础，其他模块依赖 → 当前必须修复
- **P1**: 核心业务功能 → 当前或近期修复
- **P2**: 重要业务功能 → 后续实现
- **P3**: 扩展功能 → 后续实现

---

## 二、需要立即修复的差异（当前 Phase 2.1 范围）

### 2.1 键名不一致 ❌ 必须修复

| 功能 | TypeScript 键名 | Java 键名 | 影响 | 修复方案 |
|------|-----------------|-----------|------|----------|
| Session 列表 | `platform_sessions` | `sessions` | 与前端/其他模块不兼容 | 统一为 `platform_sessions` |
| Lock 键 | `{locks}:{id}` | `lock:{id}` | 锁无法跨进程共享 | 统一为 `{locks}:{id}` |
| Deletion 追踪 | `platform-deletions` | `deletions:{draftId}` | 删除检测失效 | 统一为 `platform-deletions` |

**原因**: 键名不一致会导致与其他模块（TypeScript 版本或其他 Java 模块）无法互操作。

### 2.2 RedisConfig 缺少 NAT 映射 ⚠️ 建议修复

| 功能 | TypeScript | Java | 说明 |
|------|------------|------|------|
| `generateNatMap()` | redis.ts:62-71 | 缺失 | 集群 NAT 环境必需 |

**建议**: 添加 `generateNatMap()` 方法，但可以标记为可选功能。

---

## 三、属于后续 Phase 的功能（当前不修复）

### 3.1 Phase 2.2-2.7 范围（数据库层其他模块）

| 功能 | 源码位置 | 属于 Phase | 说明 |
|------|----------|------------|------|
| `notify()` 函数 | redis.ts:315-332 | Phase 2.2 | 依赖 ES 和 PubSub |
| Edit Context 相关 | redis.ts:336-351 | Phase 2.2 | 依赖 GraphQL 层 |
| 数据中间件相关 | redis.ts | Phase 2.3 | 依赖 ES 客户端 |

### 3.2 Phase 7 范围（后台管理器层）

| 功能 | 源码位置 | 属于 Phase | 说明 |
|------|----------|------------|------|
| Cluster 实例管理 | redis.ts:511-518 | Phase 7 | clusterManager |
| Playbook 执行管理 | redis.ts:522-551 | Phase 7 | playbookManager |
| Support Package 状态 | redis.ts:555-585 | Phase 7 | 支持包功能 |
| Exclusion List 缓存 | redis.ts:588-614 | Phase 7 | exclusionListManager |
| Forgot Password OTP | redis.ts:617-649 | Phase 7 | 认证模块 |
| Telemetry Gauges | redis.ts:654-689 | Phase 7 | telemetryManager |
| Manager Stream State | redis.ts:692-701 | Phase 7 | 各管理器状态 |
| Connector Logs | redis.ts:704-712 | Phase 7 | connectorManager |
| Connector Health Metrics | redis.ts:715-732 | Phase 7 | connectorManager |

---

## 四、设计差异（非错误，无需修复）

### 4.1 Cache 管理差异

| 方面 | TypeScript | Java | 说明 |
|------|------------|------|------|
| 存储位置 | 内存 (`const cache = {}`) | Redis | 设计选择不同 |
| 依赖参数 | context, user | 无 | Java 简化了接口 |

**结论**: 这是合理的设计差异。TypeScript 的缓存是进程内存缓存，Java 使用 Redis 作为分布式缓存更适合微服务架构。

### 4.2 分布式锁实现差异

| 方面 | TypeScript | Java | 说明 |
|------|------------|------|------|
| 算法 | Redlock (Redisson) | SET NX | 实现复杂度不同 |
| 自动续期 | 支持 | 部分支持 | 功能差异 |

**结论**: 当前 SET NX 实现可以满足基本需求。Redlock 算法可以在 Phase 7 的 LockManager 中升级。

---

## 五、修复优先级汇总

### 立即修复（Phase 2.1 收尾）

| 序号 | 问题 | 优先级 | 预估时间 |
|------|------|--------|----------|
| 1 | Session 键名 `sessions` → `platform_sessions` | P0 | 10分钟 |
| 2 | Lock 键名 `lock:` → `{locks}:` | P0 | 10分钟 |
| 3 | Deletion 键名 `deletions:` → `platform-deletions` | P0 | 10分钟 |
| 4 | Work 键名统一 | P0 | 10分钟 |
| 5 | Stream 键名统一 | P0 | 10分钟 |

### 后续实现（按 Phase 计划）

| Phase | 功能 | 说明 |
|-------|------|------|
| 2.2 | `notify()` 函数 | ES 就绪后实现 |
| 2.2 | Edit Context | GraphQL 层就绪后实现 |
| 7 | Cluster 实例管理 | clusterManager 模块 |
| 7 | Playbook 管理 | playbookManager 模块 |
| 7 | Telemetry | telemetryManager 模块 |
| 7 | Connector 相关 | connectorManager 模块 |

---

## 六、结论

### 需要修复的差异

1. **键名不一致** - 必须立即修复，影响与其他模块的互操作性

### 属于后续实现的功能

1. **业务功能** (notify, Edit Context 等) - 属于 Phase 2.2-2.7
2. **管理器功能** (Cluster, Playbook, Telemetry 等) - 属于 Phase 7

### 设计差异（无需修复）

1. **Cache 实现** - 内存 vs Redis，合理的设计选择
2. **Lock 实现** - SET NX vs Redlock，当前满足基本需求

---

## 七、下一步行动

1. 修复键名不一致问题
2. 编译验证
3. 提交修复
4. 继续 Phase 2.2 RabbitMQ 客户端开发
