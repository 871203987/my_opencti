# OpenCTI Java 编译错误修复规范

## Why

项目在编译时出现大量错误，主要原因是：
1. 部分类缺少Lombok的@Slf4j注解导致log变量未定义
2. ErrorCode枚举缺少必要的方法
3. Elasticsearch Java Client 8.17.1 API与代码不兼容
4. MinIO SDK 8.5.15 API变化

## What Changes

### 1. 通用模块修复 (common/)
- **BREAKING**: ErrorCode枚举需要添加getHttpStatus()、getMessage()、getCode()方法
- 为CyberArkCredentialsProvider、LoggingConfiguration、ProxyConfiguration添加@Slf4j注解
- 修复OpenCTIException和AuthenticationException中对ErrorCode方法的调用

### 2. Elasticsearch模块修复 (database/elasticsearch/)
- **BREAKING**: QueryBuilder.range()方法需要适配ES 8.x的UntypedRangeQuery API
- **BREAKING**: FilterBuilder需要适配ES 8.x的FieldValue类型转换
- **BREAKING**: ElasticsearchAggregation需要使用正确的aggregations()方法
- **BREAKING**: ElasticsearchBulk需要适配Refresh枚举和UpdateOperation API
- **BREAKING**: ElasticsearchClientImpl接口方法签名需要与实现匹配
- **BREAKING**: ElasticsearchIndices需要适配ES 8.x的API变化

### 3. MinIO存储模块修复 (database/storage/)
- FileStorageClientImpl需要使用正确的endpoint()方法签名
- FileStorageServiceImpl需要使用Item.objectName()替代Item.key()

### 4. RabbitMQ模块修复 (database/rabbitmq/)
- RabbitMQManagementClient需要移除不存在的setRequestCustomizer方法

## Impact

- Affected specs: 所有已实现的模块
- Affected code:
  - `common/config/CyberArkCredentialsProvider.java`
  - `common/config/LoggingConfiguration.java`
  - `common/config/ProxyConfiguration.java`
  - `common/exception/ErrorCode.java`
  - `common/exception/OpenCTIException.java`
  - `common/exception/AuthenticationException.java`
  - `database/elasticsearch/query/QueryBuilder.java`
  - `database/elasticsearch/query/FilterBuilder.java`
  - `database/elasticsearch/ElasticsearchAggregation.java`
  - `database/elasticsearch/ElasticsearchBulk.java`
  - `database/elasticsearch/ElasticsearchClient.java`
  - `database/elasticsearch/ElasticsearchClientImpl.java`
  - `database/elasticsearch/ElasticsearchIndices.java`
  - `database/storage/FileStorageClientImpl.java`
  - `database/storage/FileStorageServiceImpl.java`
  - `database/rabbitmq/RabbitMQManagementClient.java`

## ADDED Requirements

### Requirement: Lombok注解一致性
所有使用log变量的类必须添加@Slf4j注解或手动定义Logger。

#### Scenario: 日志记录
- **WHEN** 类需要记录日志
- **THEN** 必须通过@Slf4j注解或手动定义private static final Logger log

### Requirement: Elasticsearch Java Client 8.x API兼容
所有ES相关代码必须兼容elasticsearch-java 8.17.1版本API。

#### Scenario: RangeQuery构建
- **WHEN** 构建range查询
- **THEN** 必须使用UntypedRangeQuery或类型特定的RangeQuery

#### Scenario: TermsQuery字段值
- **WHEN** 构建terms查询
- **THEN** 必须将List<String>转换为List<FieldValue>

#### Scenario: Bulk操作刷新策略
- **WHEN** 执行bulk操作
- **THEN** 必须使用Refresh枚举而非boolean

### Requirement: MinIO SDK 8.5.x API兼容
所有MinIO相关代码必须兼容minio-java 8.5.15版本API。

#### Scenario: 获取对象名称
- **WHEN** 遍历MinIO对象列表
- **THEN** 必须使用Item.objectName()而非Item.key()

### Requirement: Spring Boot 3.x HTTP客户端兼容
HTTP客户端配置必须兼容Spring Boot 3.3.6版本。

#### Scenario: SSL配置
- **WHEN** 配置不安全SSL
- **THEN** 不能使用已移除的setRequestCustomizer方法

## MODIFIED Requirements

### Requirement: ErrorCode枚举完整性
ErrorCode枚举必须包含以下方法：
```java
public int getHttpStatus();
public String getMessage();
public String getCode();
```

### Requirement: ElasticsearchClient接口一致性
接口方法签名必须与ElasticsearchClientImpl实现完全匹配。

## REMOVED Requirements

### Requirement: 旧版API调用
**Reason**: ES 8.x和MinIO 8.5.x已移除相关API
**Migration**: 使用新API替代
