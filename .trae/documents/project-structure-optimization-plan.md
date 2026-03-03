# 项目结构优化计划

## 一、问题分析

### 1. 当前 Java 项目结构

```
opencti-java/
├── pom.xml                          # 父 POM
├── opencti-common/                  # 公共模块
│   ├── pom.xml
│   └── src/main/java/io/opencti/common/
│       ├── config/                  # 配置类
│       ├── exception/               # 异常类
│       ├── types/                   # 类型定义
│       └── utils/                   # 工具类
├── opencti-database/                # 数据库模块
│   ├── pom.xml
│   └── src/main/java/io/opencti/database/
│       └── redis/                   # Redis 相关
└── opencti-test/                    # 测试模块
    ├── pom.xml
    └── src/test/java/io/opencti/
```

### 2. TypeScript 原始项目结构

```
opencti-graphql/
└── src/
    ├── config/                      # 配置
    ├── database/                    # 数据库（redis, elasticsearch, rabbitmq）
    ├── domain/                      # 业务领域
    ├── manager/                     # 管理器
    ├── modules/                     # 功能模块
    ├── graphql/                     # GraphQL
    ├── http/                        # HTTP 服务
    ├── listener/                    # 监听器
    ├── lock/                        # 锁
    ├── connector/                   # 连接器
    └── back.js                      # 启动入口
```

### 3. 当前结构的问题

| 问题 | 说明 |
|------|------|
| **模块划分过早** | 在项目初期就拆分成多个 Maven 模块，增加了复杂度 |
| **配置重复** | opencti-common 和 opencti-database 都有 RedisProperties |
| **依赖管理复杂** | 需要在多个 pom.xml 中维护依赖关系 |
| **与源码结构不一致** | TypeScript 项目是单一应用结构 |
| **违反规则** | 重写时没有先检查现有代码，导致重复 |

## 二、优化方案

### 1. 新的项目结构

```
opencti-java/
├── pom.xml                              # 单一 POM
├── src/
│   ├── main/
│   │   ├── java/io/opencti/
│   │   │   ├── OpenCTIApplication.java  # 唯一启动类
│   │   │   ├── common/                  # 公共模块
│   │   │   │   ├── config/              # 配置
│   │   │   │   │   ├── AppProperties.java
│   │   │   │   │   ├── ElasticsearchProperties.java
│   │   │   │   │   ├── MinioProperties.java
│   │   │   │   │   ├── RabbitMQProperties.java
│   │   │   │   │   ├── RedisProperties.java
│   │   │   │   │   └── ...
│   │   │   │   ├── exception/           # 异常
│   │   │   │   │   ├── OpenCTIException.java
│   │   │   │   │   └── ...
│   │   │   │   └── utils/               # 工具
│   │   │   │       ├── FormatUtils.java
│   │   │   │       └── ...
│   │   │   ├── database/                # 数据库模块
│   │   │   │   ├── redis/               # Redis
│   │   │   │   │   ├── RedisClient.java
│   │   │   │   │   ├── RedisClientImpl.java
│   │   │   │   │   ├── RedisConfig.java
│   │   │   │   │   ├── RedisMode.java
│   │   │   │   │   ├── cache/
│   │   │   │   │   ├── lock/
│   │   │   │   │   ├── pubsub/
│   │   │   │   │   ├── session/
│   │   │   │   │   ├── stream/
│   │   │   │   │   └── work/
│   │   │   │   ├── elasticsearch/       # Elasticsearch（待开发）
│   │   │   │   └── rabbitmq/            # RabbitMQ（待开发）
│   │   │   ├── domain/                  # 业务领域（待开发）
│   │   │   ├── manager/                 # 管理器（待开发）
│   │   │   ├── modules/                 # 功能模块（待开发）
│   │   │   ├── graphql/                 # GraphQL（待开发）
│   │   │   └── http/                    # HTTP 服务（待开发）
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

### 2. 与 TypeScript 源码的对应关系

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

### 3. 优势对比

| 方面 | 当前结构（多模块） | 新结构（单模块） |
|------|-------------------|------------------|
| 配置管理 | 多个 pom.xml，配置分散 | 单一 pom.xml，配置集中 |
| 代码重复 | 容易出现重复类 | 包结构清晰，避免重复 |
| 编译速度 | 需要编译多个模块 | 单模块编译更快 |
| 与源码一致性 | 不一致 | 高度一致 |
| 开发复杂度 | 高（模块间依赖） | 低（包间依赖） |
| 重写便利性 | 需要跨模块检查 | 同一模块内检查 |

## 三、执行步骤

### 步骤 1：创建新的目录结构
- 创建 src/main/java/io/opencti/ 目录
- 创建各子包目录

### 步骤 2：移动 common 模块代码
- 移动 config/ → common/config/
- 移动 exception/ → common/exception/
- 移动 types/ → common/types/
- 移动 utils/ → common/utils/

### 步骤 3：移动 database 模块代码
- 移动 redis/ → database/redis/
- 删除重复的 RedisProperties

### 步骤 4：移动测试代码
- 移动所有测试类到 src/test/java/io/opencti/

### 步骤 5：合并 pom.xml
- 合并所有依赖到单一 pom.xml
- 删除子模块 pom.xml

### 步骤 6：创建启动类
- 创建 OpenCTIApplication.java

### 步骤 7：更新包引用
- 更新所有 import 语句
- 更新 @ComponentScan 等注解

### 步骤 8：编译验证
- 运行 mvn compile
- 运行 mvn test

### 步骤 9：清理旧结构
- 删除 opencti-common/ 目录
- 删除 opencti-database/ 目录
- 删除 opencti-test/ 目录

## 四、风险与注意事项

1. **包名变更**：所有类的包名会改变，需要更新所有 import
2. **测试路径**：测试类的路径和包名需要同步更新
3. **配置文件**：application.yml 中的配置可能需要调整
4. **IDE 缓存**：重构后需要刷新 IDE 缓存

## 五、确认事项

请确认以下问题后开始执行：

1. 是否同意上述结构优化方案？
2. 是否有其他需要考虑的结构调整？
3. 是否立即开始执行重构？
