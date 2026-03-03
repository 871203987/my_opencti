# OpenCTI Java 重写计划（更新版）

## 一、项目结构优化

### 1.1 问题分析

当前多模块结构存在的问题：
1. **与源码不一致** - TypeScript 项目是单一应用结构，Java 项目拆分成了多个 Maven 模块
2. **配置重复** - opencti-common 和 opencti-database 都有 RedisProperties
3. **依赖管理复杂** - 需要在多个 pom.xml 中维护依赖关系
4. **违反规则** - 重写时没有先检查现有代码，导致重复

### 1.2 新的项目结构

```
opencti-java/
├── pom.xml                              # 单一 POM
├── src/
│   ├── main/
│   │   ├── java/io/opencti/
│   │   │   ├── OpenCTIApplication.java  # 唯一启动类
│   │   │   ├── common/                  # 对应 TS 的 config/
│   │   │   │   ├── config/              # 配置类
│   │   │   │   ├── exception/           # 异常类
│   │   │   │   ├── types/               # 类型定义
│   │   │   │   └── utils/               # 工具类
│   │   │   ├── database/                # 对应 TS 的 database/
│   │   │   │   ├── redis/               # Redis 相关
│   │   │   │   ├── elasticsearch/       # ES 相关（待开发）
│   │   │   │   └── rabbitmq/            # RabbitMQ（待开发）
│   │   │   ├── domain/                  # 对应 TS 的 domain/（待开发）
│   │   │   ├── manager/                 # 对应 TS 的 manager/（待开发）
│   │   │   ├── modules/                 # 对应 TS 的 modules/（待开发）
│   │   │   ├── graphql/                 # 对应 TS 的 graphql/（待开发）
│   │   │   └── http/                    # 对应 TS 的 http/（待开发）
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── application-test.yml
│   └── test/
│       └── java/io/opencti/
│           ├── common/
│           ├── database/
│           └── ...
```

### 1.3 与 TypeScript 源码的对应关系

| TypeScript (src/) | Java (src/main/java/io/opencti/) |
|-------------------|----------------------------------|
| config/ | common/config/ |
| database/ | database/ |
| domain/ | domain/ |
| manager/ | manager/ |
| modules/ | modules/ |
| graphql/ | graphql/ |
| http/ | http/ |
| listener/ | listener/ |
| lock/ | lock/ |
| connector/ | connector/ |
| back.js | OpenCTIApplication.java |

---

## 二、重构执行步骤

### Step 1: 创建新的目录结构
- 创建 `src/main/java/io/opencti/` 目录
- 创建各子包目录：common, database, domain, manager, modules, graphql, http

### Step 2: 移动 common 模块代码
- 移动 `opencti-common/src/main/java/io/opencti/common/config/` → `src/main/java/io/opencti/common/config/`
- 移动 `opencti-common/src/main/java/io/opencti/common/exception/` → `src/main/java/io/opencti/common/exception/`
- 移动 `opencti-common/src/main/java/io/opencti/common/types/` → `src/main/java/io/opencti/common/types/`
- 移动 `opencti-common/src/main/java/io/opencti/common/utils/` → `src/main/java/io/opencti/common/utils/`

### Step 3: 移动 database 模块代码
- 移动 `opencti-database/src/main/java/io/opencti/database/redis/` → `src/main/java/io/opencti/database/redis/`
- **删除重复的 RedisProperties**（使用 common 模块的）

### Step 4: 移动测试代码
- 移动 `opencti-test/src/test/java/io/opencti/common/` → `src/test/java/io/opencti/common/`
- 移动 `opencti-test/src/test/java/io/opencti/database/` → `src/test/java/io/opencti/database/`

### Step 5: 合并 pom.xml
- 合并所有依赖到单一 `pom.xml`
- 删除子模块 pom.xml

### Step 6: 创建启动类
- 创建 `src/main/java/io/opencti/OpenCTIApplication.java`

### Step 7: 更新包引用
- 更新所有 import 语句（包名不变，但需要验证）
- 更新 @ComponentScan 等注解

### Step 8: 编译验证
- 运行 `mvn compile`
- 运行 `mvn test`

### Step 9: 清理旧结构
- 删除 `opencti-common/` 目录
- 删除 `opencti-database/` 目录
- 删除 `opencti-test/` 目录

---

## 三、更新后的阶段计划

### Phase 0: 项目结构重构（新增）

**目标**: 将多模块结构重构为单模块结构

**预估时间**: 2小时

**输出文件**:
```
opencti-java/
├── pom.xml
├── src/main/java/io/opencti/
│   ├── OpenCTIApplication.java
│   ├── common/
│   └── database/
└── src/test/java/io/opencti/
```

**验证标准**: `mvn clean compile test` 成功

---

### Phase 1: 基础设施层（更新）

#### Phase 1.1 配置管理模块
**原文件**: `src/config/`
**Java目录**: `src/main/java/io/opencti/common/config/`

**更新内容**:
- 删除 `OpenCTICommonApplication.java`（改为 `OpenCTIApplication.java`）
- 保留所有配置类，路径不变

#### Phase 1.2 工具类模块
**原文件**: `src/utils/`
**Java目录**: `src/main/java/io/opencti/common/utils/`

**更新内容**:
- 路径不变

#### Phase 1.3 类型定义模块
**原文件**: `src/types/`
**Java目录**: `src/main/java/io/opencti/common/types/`

**更新内容**:
- 路径不变

---

### Phase 2: 数据库层（更新）

#### Phase 2.1 Redis 客户端
**原文件**: `src/database/redis.ts`, `src/database/redis-stream.ts`, `src/database/cache.ts`
**Java目录**: `src/main/java/io/opencti/database/redis/`

**更新内容**:
- **删除** `RedisProperties.java`（使用 `common/config/RedisProperties.java`）
- 更新 `RedisConfig.java` 引用 `common.config.RedisProperties`
- 其他文件路径不变

#### Phase 2.2 Elasticsearch 客户端（待开发）
**原文件**: `src/database/engine.ts`, `src/database/repository.js`
**Java目录**: `src/main/java/io/opencti/database/elasticsearch/`

#### Phase 2.3 RabbitMQ 客户端（待开发）
**原文件**: `src/database/rabbitmq.ts`
**Java目录**: `src/main/java/io/opencti/database/rabbitmq/`

---

### Phase 3: 业务层（待开发）

#### Phase 3.1 Domain 层
**原文件**: `src/domain/`
**Java目录**: `src/main/java/io/opencti/domain/`

#### Phase 3.2 Manager 层
**原文件**: `src/manager/`
**Java目录**: `src/main/java/io/opencti/manager/`

#### Phase 3.3 Modules 层
**原文件**: `src/modules/`
**Java目录**: `src/main/java/io/opencti/modules/`

---

### Phase 4: 接口层（待开发）

#### Phase 4.1 GraphQL 层
**原文件**: `src/graphql/`
**Java目录**: `src/main/java/io/opencti/graphql/`

#### Phase 4.2 HTTP 层
**原文件**: `src/http/`
**Java目录**: `src/main/java/io/opencti/http/`

---

## 四、文件变更清单

### 4.1 需要删除的文件

| 文件路径 | 原因 |
|----------|------|
| `opencti-java/opencti-common/pom.xml` | 合并到父 POM |
| `opencti-java/opencti-database/pom.xml` | 合并到父 POM |
| `opencti-java/opencti-test/pom.xml` | 合并到父 POM |
| `opencti-java/opencti-database/src/main/java/io/opencti/database/redis/RedisProperties.java` | 与 common 重复 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/OpenCTICommonApplication.java` | 改为统一启动类 |

### 4.2 需要更新的文件

| 文件路径 | 更新内容 |
|----------|----------|
| `opencti-java/pom.xml` | 合并所有依赖，删除 modules 配置 |
| `opencti-java/opencti-database/src/main/java/io/opencti/database/redis/RedisConfig.java` | 更新 import 引用 |
| 所有测试文件 | 更新路径 |

### 4.3 需要新建的文件

| 文件路径 | 说明 |
|----------|------|
| `opencti-java/src/main/java/io/opencti/OpenCTIApplication.java` | 统一启动类 |

---

## 五、重写规则重申

在执行重写任务时，必须严格遵守以下规则：

1. **先读源码** - 重写前必须逐行阅读 TypeScript 源码，理解其逻辑和功能
2. **检查现有代码** - 重写前必须检查 Java 项目中是否已有相关实现
3. **注释原文件路径** - 每个类必须注释重写的原文件路径
4. **注释原方法路径** - 每个方法必须注释重写的原文件方法路径
5. **保持逻辑一致** - 必须保持和源码逻辑一致
6. **保持功能一致** - 必须保持和原项目功能一致，不能多也不能少
7. **分任务完成** - 如果任务量比较大（超过500行代码），必须分多个子任务完成
8. **编译验证** - 每个子任务完成后必须进行编译

---

## 六、验收标准

| 标准 | 验证方式 |
|------|----------|
| 项目结构符合计划 | 目录结构检查 |
| Maven 编译成功 | `mvn clean compile` |
| 单元测试通过 | `mvn test` |
| 无重复代码 | 代码审查 |
| 注释完整 | 代码审查 |
| 与源码结构一致 | 对比检查 |
