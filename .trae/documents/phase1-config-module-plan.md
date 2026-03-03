# Phase 1.1 配置管理模块实施计划

## 一、任务概述

### 1.1 任务目标
重写 OpenCTI GraphQL 后端的配置管理模块 `config/`，使用 Java 21 + Spring Boot 3.x 实现。

### 1.2 原项目文件清单

| 原文件 | 行数 | 功能说明 |
|--------|------|----------|
| `src/config/conf.js` | ~600行 | 主配置加载器，日志配置 |
| `src/config/errors.js` | ~200行 | 自定义异常类定义 |
| `src/config/credentials.ts` | ~50行 | 远程凭证获取（CyberArk） |
| `src/config/proxy-config.ts` | ~150行 | HTTP代理配置 |
| `src/config/tracing.ts` | ~150行 | OpenTelemetry链路追踪 |
| `src/config/providers-initialization.js` | ~200行 | 认证Provider初始化 |
| `src/config/providers-configuration.ts` | ~300行 | 认证Provider配置 |
| `config/default.json` | ~500行 | 默认配置文件 |
| `config/test.json` | ~100行 | 测试环境配置 |

### 1.3 预估代码量
~2,500行 Java 代码

---

## 二、实施步骤

### Step 1: 创建 Maven 项目结构

**目标**: 创建 `opencti-java` 目录和基础 Maven 项目结构

**输出文件**:
```
opencti-java/
├── pom.xml                                    # 父POM
├── opencti-common/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/io/opencti/common/
│       │   │   ├── OpenCTICommonApplication.java
│       │   │   ├── config/
│       │   │   ├── exception/
│       │   │   └── utils/
│       │   └── resources/
│       │       ├── application.yml
│       │       ├── application-default.yml
│       │       └── application-dev.yml
│       └── test/
│           └── java/io/opencti/common/
└── opencti-test/
    ├── pom.xml
    └── src/test/java/io/opencti/
```

**具体任务**:
1. 创建父POM，配置Spring Boot 3.3.x、Java 21
2. 创建 `opencti-common` 子模块
3. 创建 `opencti-test` 测试模块
4. 配置基础依赖：spring-boot-starter, lombok, junit5, mockito

**验证标准**: `mvn clean compile` 成功

---

### Step 2: 实现异常定义模块

**原文件**: `src/config/errors.js`

**Java实现文件**:
- `opencti-common/src/main/java/io/opencti/common/exception/OpenCTIException.java`
- `opencti-common/src/main/java/io/opencti/common/exception/AuthenticationException.java`
- `opencti-common/src/main/java/io/opencti/common/exception/DatabaseException.java`
- `opencti-common/src/main/java/io/opencti/common/exception/FunctionalException.java`
- `opencti-common/src/main/java/io/opencti/common/exception/ValidationException.java`
- `opencti-common/src/main/java/io/opencti/common/exception/ConfigurationException.java`
- `opencti-common/src/main/java/io/opencti/common/exception/ResourceNotFoundException.java`
- `opencti-common/src/main/java/io/opencti/common/exception/ErrorCode.java`

**异常分类**:

| 类别 | 异常类 | 错误码 | HTTP状态 |
|------|--------|--------|----------|
| 认证异常 | AuthenticationException | AUTH_FAILURE, AUTH_REQUIRED, OTP_REQUIRED, FORBIDDEN_ACCESS | 401/403 |
| 技术异常 | DatabaseException, ConfigurationException, UnknownException | DATABASE_ERROR, CONFIGURATION_ERROR, UNKNOWN_ERROR | 500 |
| 业务异常 | FunctionalException, ValidationException | FUNCTIONAL_ERROR, VALIDATION_ERROR | 400/500 |
| 资源异常 | ResourceNotFoundException | RESOURCE_NOT_FOUND, MISSING_REFERENCE_ERROR | 404 |
| 锁异常 | LockTimeoutException | LOCK_ERROR | 500 |

**验证标准**: 编译通过，单元测试覆盖所有异常类

---

### Step 3: 实现配置加载器

**原文件**: `src/config/conf.js`

**Java实现文件**:
- `opencti-common/src/main/java/io/opencti/common/config/OpenCTIConfiguration.java`
- `opencti-common/src/main/java/io/opencti/common/config/AppConfiguration.java`
- `opencti-common/src/main/java/io/opencti/common/config/DatabaseConfiguration.java`
- `opencti-common/src/main/java/io/opencti/common/config/RedisConfiguration.java`
- `opencti-common/src/main/java/io/opencti/common/config/RabbitMQConfiguration.java`
- `opencti-common/src/main/java/io/opencti/common/config/MinIOConfiguration.java`
- `opencti-common/src/main/java/io/opencti/common/config/TelemetryConfiguration.java`

**配置类结构**:

```java
/**
 * 重写自: opencti-graphql/src/config/conf.js
 * 主配置类
 */
@ConfigurationProperties(prefix = "opencti")
public class OpenCTIConfiguration {
    private AppConfiguration app;
    private DatabaseConfiguration elasticsearch;
    private RedisConfiguration redis;
    private RabbitMQConfiguration rabbitmq;
    private MinIOConfiguration minio;
    // ...
}
```

**配置项映射**:

| 原配置路径 | Java配置路径 | 说明 |
|------------|--------------|------|
| `app.port` | `opencti.app.port` | 服务端口 |
| `app.base_url` | `opencti.app.base-url` | 基础URL |
| `elasticsearch.url` | `opencti.elasticsearch.url` | ES地址 |
| `redis.hostname` | `opencti.redis.hostname` | Redis地址 |
| `rabbitmq.hostname` | `opencti.rabbitmq.hostname` | RabbitMQ地址 |
| `minio.endpoint` | `opencti.minio.endpoint` | MinIO地址 |

**验证标准**: 配置加载测试通过

---

### Step 4: 实现日志配置

**原文件**: `src/config/conf.js` (日志部分)

**Java实现文件**:
- `opencti-common/src/main/java/io/opencti/common/config/LoggingConfiguration.java`
- `opencti-common/src/main/java/io/opencti/common/utils/LogUtils.java`

**日志配置项**:
```yaml
opencti:
  app:
    app-logs:
      logs-level: info
      logs-files: true
      logs-console: true
      logs-max-files: 7
      logs-directory: ./logs
      logs-redacted-inputs:
        - password
        - secret
        - token
```

**验证标准**: 日志输出格式与原项目一致

---

### Step 5: 实现凭证管理

**原文件**: `src/config/credentials.ts`

**Java实现文件**:
- `opencti-common/src/main/java/io/opencti/common/config/CredentialsProvider.java`
- `opencti-common/src/main/java/io/opencti/common/config/CyberArkCredentialsProvider.java`
- `opencti-common/src/main/java/io/opencti/common/config/CredentialsConfiguration.java`

**功能说明**:
- 支持从 CyberArk 获取远程凭证
- 支持本地配置文件凭证
- 凭证缓存和刷新

**验证标准**: CyberArk凭证获取测试通过

---

### Step 6: 实现代理配置

**原文件**: `src/config/proxy-config.ts`

**Java实现文件**:
- `opencti-common/src/main/java/io/opencti/common/config/ProxyConfiguration.java`
- `opencti-common/src/main/java/io/opencti/common/config/ProxyConfigAdapter.java`

**代理配置项**:
```yaml
opencti:
  http-proxy: ""
  https-proxy: ""
  no-proxy: ""
  https-proxy-ca: []
  https-proxy-reject-unauthorized: false
```

**验证标准**: 代理配置验证测试通过

---

### Step 7: 实现链路追踪配置

**原文件**: `src/config/tracing.ts`

**Java实现文件**:
- `opencti-common/src/main/java/io/opencti/common/config/TracingConfiguration.java`
- `opencti-common/src/main/java/io/opencti/common/telemetry/TelemetryManager.java`

**追踪配置项**:
```yaml
opencti:
  app:
    telemetry:
      tracing:
        enabled: false
        exporter-otlp: ""
        exporter-zipkin: ""
      metrics:
        enabled: false
        exporter-otlp: ""
        exporter-prometheus: 14269
```

**依赖**:
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
</dependency>
<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-otlp</artifactId>
</dependency>
```

**验证标准**: OpenTelemetry集成测试通过

---

### Step 8: 创建默认配置文件

**原文件**: `config/default.json`

**Java实现文件**:
- `opencti-common/src/main/resources/application.yml`
- `opencti-common/src/main/resources/application-default.yml`
- `opencti-common/src/main/resources/application-dev.yml`

**配置文件结构**:
```yaml
# application.yml
spring:
  application:
    name: opencti
  profiles:
    active: default

opencti:
  app:
    port: 4000
    base-path: ""
    base-url: "http://localhost:4000/"
    # ... 完整配置映射
```

**验证标准**: 配置文件加载测试通过

---

### Step 9: 编写单元测试

**测试文件**:
- `opencti-test/src/test/java/io/opencti/common/config/ConfigurationTest.java`
- `opencti-test/src/test/java/io/opencti/common/exception/ExceptionTest.java`
- `opencti-test/src/test/java/io/opencti/common/config/ProxyConfigurationTest.java`

**测试覆盖**:
- 配置加载测试
- 配置验证测试
- 异常创建和属性测试
- 代理配置验证测试

**验证标准**: 测试覆盖率 > 80%

---

### Step 10: 编译验证与文档

**任务**:
1. 执行 `mvn clean compile test`
2. 检查代码风格
3. 更新项目文档

**验证标准**: 
- 编译无错误
- 所有测试通过
- 代码注释完整（包含原文件路径）

---

## 三、依赖清单

```xml
<!-- Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Logging -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>

<!-- Telemetry -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
</dependency>

<!-- Test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 四、文件清单

### 4.1 新建文件

| 文件路径 | 说明 |
|----------|------|
| `opencti-java/pom.xml` | 父POM |
| `opencti-java/opencti-common/pom.xml` | common模块POM |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/OpenCTIConfiguration.java` | 主配置类 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/AppConfiguration.java` | App配置 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/DatabaseConfiguration.java` | 数据库配置 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/RedisConfiguration.java` | Redis配置 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/RabbitMQConfiguration.java` | RabbitMQ配置 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/MinIOConfiguration.java` | MinIO配置 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/ProxyConfiguration.java` | 代理配置 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/TelemetryConfiguration.java` | 遥测配置 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/LoggingConfiguration.java` | 日志配置 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/CredentialsProvider.java` | 凭证提供者接口 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/config/CyberArkCredentialsProvider.java` | CyberArk凭证实现 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/exception/OpenCTIException.java` | 基础异常 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/exception/ErrorCode.java` | 错误码枚举 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/exception/AuthenticationException.java` | 认证异常 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/exception/DatabaseException.java` | 数据库异常 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/exception/FunctionalException.java` | 功能异常 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/exception/ValidationException.java` | 验证异常 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/exception/ConfigurationException.java` | 配置异常 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/exception/ResourceNotFoundException.java` | 资源未找到异常 |
| `opencti-java/opencti-common/src/main/java/io/opencti/common/exception/LockTimeoutException.java` | 锁超时异常 |
| `opencti-java/opencti-common/src/main/resources/application.yml` | 主配置文件 |
| `opencti-java/opencti-common/src/main/resources/application-default.yml` | 默认配置 |
| `opencti-java/opencti-common/src/main/resources/application-dev.yml` | 开发配置 |
| `opencti-java/opencti-test/pom.xml` | 测试模块POM |
| `opencti-java/opencti-test/src/test/java/io/opencti/common/config/ConfigurationTest.java` | 配置测试 |
| `opencti-java/opencti-test/src/test/java/io/opencti/common/exception/ExceptionTest.java` | 异常测试 |

### 4.2 文件数量统计

| 类型 | 数量 |
|------|------|
| POM文件 | 3 |
| Java源文件 | 22 |
| 配置文件 | 3 |
| 测试文件 | 2 |
| **总计** | **30** |

---

## 五、风险与注意事项

### 5.1 风险点

| 风险 | 等级 | 应对措施 |
|------|------|----------|
| 配置项过多，映射遗漏 | 🟡 中 | 对照原配置文件逐项检查 |
| CyberArk集成复杂 | 🟡 中 | 先实现接口，后续完善 |
| 日志格式不一致 | 🟢 低 | 参考原项目日志格式 |

### 5.2 注意事项

1. **每个Java类必须添加注释**，标明重写的原文件路径
2. **每个方法必须添加注释**，标明重写的原文件方法路径
3. **保持配置项名称一致**，便于后续迁移
4. **使用Spring Boot配置验证**，确保配置正确性

---

## 六、验收标准

| 标准 | 验证方式 |
|------|----------|
| Maven编译成功 | `mvn clean compile` |
| 单元测试通过 | `mvn test` |
| 代码覆盖率 > 80% | JaCoCo报告 |
| 配置加载正确 | 集成测试 |
| 日志输出正常 | 手动验证 |
| 代码注释完整 | 代码审查 |

---

## 七、时间估算

| 步骤 | 预估时间 |
|------|----------|
| Step 1: 创建项目结构 | 2小时 |
| Step 2: 实现异常模块 | 2小时 |
| Step 3: 实现配置加载器 | 4小时 |
| Step 4: 实现日志配置 | 2小时 |
| Step 5: 实现凭证管理 | 3小时 |
| Step 6: 实现代理配置 | 2小时 |
| Step 7: 实现链路追踪 | 3小时 |
| Step 8: 创建配置文件 | 2小时 |
| Step 9: 编写单元测试 | 4小时 |
| Step 10: 编译验证 | 1小时 |
| **总计** | **25小时（约3-4天）** |
