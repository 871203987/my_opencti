# Phase 1.2 工具类模块实施计划

## 一、任务概述

### 1.1 任务目标

重写 OpenCTI GraphQL 后端的工具类模块 `utils/`，使用 Java 21 实现。

### 1.2 原项目文件清单

| 原文件                  | 行数     | 功能说明       | 优先级 |
| -------------------- | ------ | ---------- | --- |
| `format.js`          | \~300行 | 日期、时间格式化   | P0  |
| `hash.ts`            | \~20行  | MD5/SHA等哈希 | P0  |
| `base64.ts`          | \~10行  | Base64编解码  | P0  |
| `queue.js`           | \~60行  | 任务队列       | P0  |
| `sorting.ts`         | \~80行  | 数据排序       | P0  |
| `version.ts`         | \~10行  | 版本比较       | P0  |
| `syntax.js`          | \~200行 | STIX语法处理   | P0  |
| `access.ts`          | \~800行 | 权限检查核心     | P0  |
| `http-client.ts`     | \~60行  | HTTP请求封装   | P0  |
| `data-processing.ts` | \~100行 | 数据转换处理     | P0  |

### 1.3 预估代码量

\~1,640行 Java 代码

***

## 二、实施步骤

### Step 1: 创建工具类目录结构

**目标**: 在 `opencti-common` 模块下创建 `utils` 包

**输出目录**:

```
opencti-common/src/main/java/io/opencti/common/utils/
├── FormatUtils.java           # 格式化工具
├── HashUtils.java             # 哈希工具
├── Base64Utils.java           # Base64工具
├── Queue.java                 # 队列实现
├── SortingUtils.java          # 排序工具
├── VersionUtils.java          # 版本工具
├── SyntaxUtils.java           # 语法工具
├── AccessConstants.java       # 访问控制常量
├── AccessUtils.java           # 访问控制工具
├── HttpClientUtils.java       # HTTP客户端工具
└── DataProcessingUtils.java   # 数据处理工具
```

**验证标准**: 目录创建成功

***

### Step 2: 实现基础工具类

#### 2.1 HashUtils.java

**原文件**: `src/utils/hash.ts`

**功能**:

* SHA-256 哈希计算

* 哈希比较

```java
/**
 * 重写自: opencti-graphql/src/utils/hash.ts
 * 哈希工具类
 */
public final class HashUtils {
    public static String hashSHA256(String input);
    public static boolean compareHashSHA256(String input, String hash);
}
```

#### 2.2 Base64Utils.java

**原文件**: `src/utils/base64.ts`

**功能**:

* Base64 编码

* Base64 解码

```java
/**
 * 重写自: opencti-graphql/src/utils/base64.ts
 * Base64工具类
 */
public final class Base64Utils {
    public static String encode(String data);
    public static String encode(Object data);
    public static <T> T decode(String data, Class<T> clazz);
    public static String decodeToString(String data);
}
```

#### 2.3 VersionUtils.java

**原文件**: `src/utils/version.ts`

**功能**:

* 版本比较（使用语义化版本）

```java
/**
 * 重写自: opencti-graphql/src/utils/version.ts
 * 版本工具类
 */
public final class VersionUtils {
    public static boolean isCompatibleVersionWithMinimal(String version, String minimalVersion);
    public static int compareVersions(String version1, String version2);
}
```

**验证标准**: 单元测试通过

***

### Step 3: 实现格式化工具类

**原文件**: `src/utils/format.js`

**Java实现文件**: `FormatUtils.java`

**功能**:

* 调度周期转换（PT1D, PT12H, PT6H 等）

* 日期格式化（UTC）

* 日期范围判断

* 时间戳计算

```java
/**
 * 重写自: opencti-graphql/src/utils/format.js
 * 格式化工具类
 */
public final class FormatUtils {
    // 常量
    public static final String FROM_START_STR = "1970-01-01T00:00:00.000Z";
    public static final String UNTIL_END_STR = "5138-11-16T09:46:40.000Z";
    
    // 调度周期转换
    public static long schedulingPeriodToMs(String schedulingPeriod);
    
    // 日期格式化
    public static ZonedDateTime utcDate(Instant date);
    public static long utcEpochTime(Instant date);
    public static String now();
    public static String nowTime();
    
    // 日期范围
    public static boolean isDateInRange(Instant startDate, Duration duration, Instant specificDate);
    
    // 事件ID计算
    public static String computeDateFromEventId(String eventId);
    public static String streamEventId(Instant date, int index);
}
```

**验证标准**: 日期格式化测试通过

***

### Step 4: 实现队列工具类

**原文件**: `src/utils/queue.js`

**Java实现文件**: `Queue.java`

**功能**:

* FIFO 队列实现

* enqueue/dequeue/peek 操作

```java
/**
 * 重写自: opencti-graphql/src/utils/queue.js
 * FIFO队列实现
 */
public class Queue<T> {
    public int size();
    public boolean isEmpty();
    public void enqueue(T item);
    public T dequeue();
    public T peek();
    public void clear();
}
```

**验证标准**: 队列操作测试通过

***

### Step 5: 实现排序工具类

**原文件**: `src/utils/sorting.ts`

**Java实现文件**: `SortingUtils.java`

**功能**:

* Elasticsearch 排序构建

* 属性类型判断排序

```java
/**
 * 重写自: opencti-graphql/src/utils/sorting.ts
 * 排序工具类
 */
public final class SortingUtils {
    public static Map<String, Object> buildElasticSortingForAttributeCriteria(
        String orderCriteria, 
        String orderMode,
        String pirId
    );
}
```

**验证标准**: 排序构建测试通过

***

### Step 6: 实现语法工具类

**原文件**: `src/utils/syntax.js`

**Java实现文件**: `SyntaxUtils.java`

**功能**:

* STIX Pattern 解析（依赖 ANTLR）

* 从 Indicator Pattern 提取 Observable

**注意**: 此功能依赖 STIX Pattern 解析器（Phase 3），暂时实现接口定义

```java
/**
 * 重写自: opencti-graphql/src/utils/syntax.js
 * STIX语法工具类
 * 
 * TODO: 依赖 STIX Pattern 解析器（Phase 3.2）
 */
public final class SyntaxUtils {
    public static final String STIX_PATTERN_TYPE = "stix";
    
    // 暂时返回空列表，待 ANTLR 解析器实现后完善
    public static List<Map<String, Object>> extractObservablesFromIndicatorPattern(String pattern);
    public static boolean validateObservableGeneration(String observableType, String indicatorPattern);
}
```

**验证标准**: 接口定义完成

***

### Step 7: 实现访问控制常量类

**原文件**: `src/utils/access.ts` (常量部分)

**Java实现文件**: `AccessConstants.java`

**功能**:

* 权限常量定义

* 角色常量定义

* 系统用户UUID定义

```java
/**
 * 重写自: opencti-graphql/src/utils/access.ts
 * 访问控制常量类
 */
public final class AccessConstants {
    // 权限常量
    public static final String BYPASS = "BYPASS";
    public static final String KNOWLEDGE = "KNOWLEDGE";
    public static final String KNOWLEDGE_KNUPDATE = "KNOWLEDGE_KNUPDATE";
    public static final String KNOWLEDGE_KNDELETE = "KNOWLEDGE_KNDELETE";
    // ... 其他权限
    
    // 角色常量
    public static final String ROLE_DEFAULT = "Default";
    public static final String ROLE_ADMINISTRATOR = "Administrator";
    
    // 系统用户UUID
    public static final String OPENCTI_SYSTEM_UUID = "...";
    public static final String REDACTED_USER_UUID = "...";
    // ... 其他UUID
    
    // 访问权限级别
    public enum MemberAccessRight {
        VIEW, USE, EDIT, ADMIN
    }
    
    // 访问操作
    public enum AccessOperation {
        EDIT, DELETE, MANAGE_ACCESS, MANAGE_AUTHORITIES_ACCESS
    }
}
```

**验证标准**: 常量定义完整

***

### Step 8: 实现访问控制工具类

**原文件**: `src/utils/access.ts` (工具方法部分)

**Java实现文件**: `AccessUtils.java`

**功能**:

* 权限检查

* 用户访问验证

* 数据标记验证

**注意**: 此功能依赖用户类型定义（Phase 1.3），暂时实现基础框架

```java
/**
 * 重写自: opencti-graphql/src/utils/access.ts
 * 访问控制工具类
 * 
 * TODO: 依赖用户类型定义（Phase 1.3 types/）
 */
public final class AccessUtils {
    // 基础权限检查
    public static boolean hasCapability(AuthUser user, String capability);
    public static boolean isBypass(AuthUser user);
    
    // 访问权限检查
    public static boolean canEdit(AuthUser user, String entityId);
    public static boolean canDelete(AuthUser user, String entityId);
    public static boolean canManageAccess(AuthUser user, String entityId);
}
```

**验证标准**: 接口定义完成

***

### Step 9: 实现HTTP客户端工具类

**原文件**: `src/utils/http-client.ts`

**Java实现文件**: `HttpClientUtils.java`

**功能**:

* HTTP 客户端封装

* 代理支持

* 证书支持

```java
/**
 * 重写自: opencti-graphql/src/utils/http-client.ts
 * HTTP客户端工具类
 */
public final class HttpClientUtils {
    public static HttpClient createHttpClient(
        String baseURL,
        Map<String, String> headers,
        boolean rejectUnauthorized,
        Certificates certificates
    );
    
    public static HttpResponse get(String url, Map<String, String> headers);
    public static HttpResponse post(String url, Object data, Map<String, String> headers);
    public static HttpResponse delete(String url, Map<String, String> headers);
    public static HttpResponse head(String url, Map<String, String> headers);
}
```

**验证标准**: HTTP请求测试通过

***

### Step 10: 实现数据处理工具类

**原文件**: `src/utils/data-processing.ts`

**Java实现文件**: `DataProcessingUtils.java`

**功能**:

* 数据转换

* 数据清理

```java
/**
 * 重写自: opencti-graphql/src/utils/data-processing.ts
 * 数据处理工具类
 */
public final class DataProcessingUtils {
    public static Map<String, Object> unflatten(Map<String, Object> data);
    public static Map<String, Object> flatten(Map<String, Object> data);
}
```

**验证标准**: 数据处理测试通过

***

### Step 11: 编写单元测试

**测试文件**:

* `HashUtilsTest.java`

* `Base64UtilsTest.java`

* `VersionUtilsTest.java`

* `FormatUtilsTest.java`

* `QueueTest.java`

* `SortingUtilsTest.java`

* `AccessConstantsTest.java`

* `HttpClientUtilsTest.java`

* `DataProcessingUtilsTest.java`

**验证标准**: 测试覆盖率 > 80%

***

### Step 12: 编译验证

**任务**:

1. 执行 `mvn clean compile test`
2. 检查代码风格
3. 验证所有测试通过

**验证标准**:

* 编译无错误

* 所有测试通过

* 代码注释完整（包含原文件路径）

***

## 三、依赖清单

```xml
<!-- 已有依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- 新增依赖 -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```

***

## 四、文件清单

### 4.1 新建文件

| 文件路径                                                                            | 说明        |
| ------------------------------------------------------------------------------- | --------- |
| `opencti-common/src/main/java/io/opencti/common/utils/HashUtils.java`           | 哈希工具      |
| `opencti-common/src/main/java/io/opencti/common/utils/Base64Utils.java`         | Base64工具  |
| `opencti-common/src/main/java/io/opencti/common/utils/VersionUtils.java`        | 版本工具      |
| `opencti-common/src/main/java/io/opencti/common/utils/FormatUtils.java`         | 格式化工具     |
| `opencti-common/src/main/java/io/opencti/common/utils/Queue.java`               | 队列实现      |
| `opencti-common/src/main/java/io/opencti/common/utils/SortingUtils.java`        | 排序工具      |
| `opencti-common/src/main/java/io/opencti/common/utils/SyntaxUtils.java`         | 语法工具      |
| `opencti-common/src/main/java/io/opencti/common/utils/AccessConstants.java`     | 访问控制常量    |
| `opencti-common/src/main/java/io/opencti/common/utils/AccessUtils.java`         | 访问控制工具    |
| `opencti-common/src/main/java/io/opencti/common/utils/HttpClientUtils.java`     | HTTP客户端工具 |
| `opencti-common/src/main/java/io/opencti/common/utils/DataProcessingUtils.java` | 数据处理工具    |

### 4.2 测试文件

| 文件路径                                                                              | 说明         |
| --------------------------------------------------------------------------------- | ---------- |
| `opencti-test/src/test/java/io/opencti/common/utils/HashUtilsTest.java`           | 哈希工具测试     |
| `opencti-test/src/test/java/io/opencti/common/utils/Base64UtilsTest.java`         | Base64工具测试 |
| `opencti-test/src/test/java/io/opencti/common/utils/VersionUtilsTest.java`        | 版本工具测试     |
| `opencti-test/src/test/java/io/opencti/common/utils/FormatUtilsTest.java`         | 格式化工具测试    |
| `opencti-test/src/test/java/io/opencti/common/utils/QueueTest.java`               | 队列测试       |
| `opencti-test/src/test/java/io/opencti/common/utils/SortingUtilsTest.java`        | 排序工具测试     |
| `opencti-test/src/test/java/io/opencti/common/utils/AccessConstantsTest.java`     | 访问常量测试     |
| `opencti-test/src/test/java/io/opencti/common/utils/HttpClientUtilsTest.java`     | HTTP客户端测试  |
| `opencti-test/src/test/java/io/opencti/common/utils/DataProcessingUtilsTest.java` | 数据处理测试     |

### 4.3 文件数量统计

| 类型      | 数量     |
| ------- | ------ |
| Java源文件 | 11     |
| 测试文件    | 9      |
| **总计**  | **20** |

***

## 五、风险与注意事项

### 5.1 风险点

| 风险                 | 等级   | 应对措施               |
| ------------------ | ---- | ------------------ |
| SyntaxUtils依赖ANTLR | 🟡 中 | 暂时实现接口，Phase 3完善   |
| AccessUtils依赖用户类型  | 🟡 中 | 暂时实现接口，Phase 1.3完善 |
| 日期格式兼容性            | 🟢 低 | 使用Java 8时间API      |

### 5.2 注意事项

1. **每个Java类必须添加注释**，标明重写的原文件路径
2. **每个方法必须添加注释**，标明重写的原文件方法路径
3. **工具类使用final修饰**，防止继承
4. **工具类构造函数私有化**，防止实例化

***

## 六、验收标准

| 标准          | 验证方式                |
| ----------- | ------------------- |
| Maven编译成功   | `mvn clean compile` |
| 单元测试通过      | `mvn test`          |
| 代码覆盖率 > 80% | JaCoCo报告            |
| 代码注释完整      | 代码审查                |

***

## 七、时间估算

| 步骤                | 预估时间          |
| ----------------- | ------------- |
| Step 1: 创建目录结构    | 0.5小时         |
| Step 2: 基础工具类     | 1小时           |
| Step 3: 格式化工具类    | 1.5小时         |
| Step 4: 队列工具类     | 0.5小时         |
| Step 5: 排序工具类     | 1小时           |
| Step 6: 语法工具类     | 1小时           |
| Step 7: 访问控制常量    | 1小时           |
| Step 8: 访问控制工具    | 1小时           |
| Step 9: HTTP客户端工具 | 1小时           |
| Step 10: 数据处理工具   | 0.5小时         |
| Step 11: 单元测试     | 2小时           |
| Step 12: 编译验证     | 0.5小时         |
| **总计**            | **12小时（约2天）** |

