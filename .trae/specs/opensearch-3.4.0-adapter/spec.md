# OpenCTI-Java 适配 OpenSearch 3.4.0 规格说明

## Why

OpenCTI-Java项目当前使用Elasticsearch 7.17.9客户端（`elasticsearch-rest-high-level-client`），虽然该版本与OpenSearch 3.x有一定兼容性，但为了更好地支持OpenSearch 3.4.0的新特性和确保长期稳定性，需要引入官方的OpenSearch Java客户端。同时，参考源码`opencti-platform/opencti-graphql`项目使用了`@opensearch-project/opensearch` 3.5.1客户端，opencti-java需要与之保持一致的功能和兼容性处理。

## What Changes

### 1. 依赖更新
- **新增**: OpenSearch Java客户端 3.4.0 (`org.opensearch.client:opensearch-java:3.4.0`)
- **新增**: OpenSearch REST客户端 (`org.opensearch.client:opensearch-rest-client:3.4.0`)
- **保留**: 现有Elasticsearch 7.17.9客户端作为向后兼容支持
- **更新**: Jackson相关依赖以支持OpenSearch客户端的JSON序列化

### 2. 引擎检测与选择机制
- 实现与源码一致的引擎自动检测逻辑（`engine_selector: auto/elk/opensearch`）
- 支持运行时动态检测后端是Elasticsearch还是OpenSearch
- 根据检测结果自动选择相应的客户端实现

### 3. 响应格式适配
- 实现`oebp`（OpenSearch/ELK Body Parser）函数处理响应格式差异
- OpenSearch响应包装在`body`字段中，而Elasticsearch 8+直接返回

### 4. 数据类型映射适配
- **flattened vs flat_object**: Elasticsearch使用`flattened`类型，OpenSearch使用`flat_object`类型
- 在映射生成器中根据引擎类型动态选择正确的字段类型

### 5. 附件处理器配置差异
- **Elasticsearch**: 使用`remove_binary: true`参数
- **OpenSearch**: 使用单独的`remove`处理器
- 实现条件配置以支持两种引擎

### 6. 生命周期策略差异
- **Elasticsearch (ILM)**: 实现索引生命周期管理策略
- **OpenSearch (ISM)**: 实现索引状态管理策略
- 根据引擎类型选择正确的策略实现

### 7. 运行时排序支持检测
- 仅Elasticsearch 7.12+支持运行时排序
- 实现版本检测和运行时排序启用状态判断

## Impact

### 受影响的代码文件
- `pom.xml` - 依赖管理
- `ElasticsearchConfig.java` - 配置类扩展
- `ElasticsearchClient.java` - 接口可能需要扩展
- `ElasticsearchClientImpl.java` - 新增OpenSearch客户端实现
- `ElasticsearchMapping.java` - 映射类型适配
- `ElasticsearchIndices.java` - 索引生命周期策略适配
- `ElasticsearchConstants.java` - 引擎类型常量

### 受影响的配置项
- `opencti.elasticsearch.engine-selector` - 引擎选择器
- `opencti.elasticsearch.engine-check` - 是否验证引擎类型
- 新增OpenSearch特定配置（AWS签名、区域等）

## ADDED Requirements

### Requirement: OpenSearch 3.4.0 客户端支持
系统应提供对OpenSearch 3.4.0的官方Java客户端支持。

#### Scenario: 依赖配置
- **GIVEN** 项目的pom.xml文件
- **WHEN** 添加OpenSearch Java客户端依赖
- **THEN** 应包含`org.opensearch.client:opensearch-java:3.4.0`和`org.opensearch.client:opensearch-rest-client:3.4.0`
- **AND** 保持现有Elasticsearch 7.17.9客户端依赖以支持向后兼容

#### Scenario: 引擎自动检测
- **GIVEN** 配置`engine-selector`为`auto`
- **WHEN** 应用启动时
- **THEN** 系统应先尝试使用OpenSearch客户端连接
- **AND** 根据响应中的`distribution`字段检测引擎类型
- **AND** 根据检测结果使用相应的客户端

#### Scenario: 手动引擎选择
- **GIVEN** 配置`engine-selector`为`elk`或`opensearch`
- **WHEN** 应用启动时
- **THEN** 系统应直接使用指定的客户端
- **AND** 如果`engine-check`为true且检测到的引擎与配置不符，应抛出配置错误

### Requirement: 响应格式统一处理
系统应统一处理Elasticsearch和OpenSearch的响应格式差异。

#### Scenario: 响应解析
- **GIVEN** 执行搜索、索引、删除等操作
- **WHEN** 接收到响应
- **THEN** 如果引擎是OpenSearch，应从`response.body`中提取实际数据
- **AND** 如果引擎是Elasticsearch 8+，直接使用响应对象

### Requirement: 数据类型映射适配
系统应根据引擎类型使用正确的字段类型映射。

#### Scenario: 映射字段类型选择
- **GIVEN** 创建索引映射
- **WHEN** 需要定义扁平化对象字段
- **THEN** 如果引擎是Elasticsearch，使用`flattened`类型
- **AND** 如果引擎是OpenSearch，使用`flat_object`类型

### Requirement: 附件处理器配置
系统应根据引擎类型配置正确的附件处理器。

#### Scenario: Elasticsearch附件处理器
- **GIVEN** 引擎类型为Elasticsearch
- **WHEN** 配置附件处理器Pipeline
- **THEN** 使用`attachment`处理器并设置`remove_binary: true`

#### Scenario: OpenSearch附件处理器
- **GIVEN** 引擎类型为OpenSearch
- **WHEN** 配置附件处理器Pipeline
- **THEN** 使用`attachment`处理器（不带remove_binary）
- **AND** 添加单独的`remove`处理器移除`file_data`字段

### Requirement: 生命周期策略适配
系统应根据引擎类型实现相应的索引生命周期管理。

#### Scenario: Elasticsearch ILM策略
- **GIVEN** 引擎类型为Elasticsearch
- **WHEN** 创建生命周期策略
- **THEN** 使用Elasticsearch的ILM（Index Lifecycle Management）API

#### Scenario: OpenSearch ISM策略
- **GIVEN** 引擎类型为OpenSearch
- **WHEN** 创建生命周期策略
- **THEN** 使用OpenSearch的ISM（Index State Management）API

## MODIFIED Requirements

### Requirement: 引擎初始化和配置
现有引擎初始化逻辑需要扩展以支持双客户端。

**修改内容**:
1. 在`ElasticsearchConfig`中增加OpenSearch特定配置属性
2. 修改`searchEngineInit`方法实现引擎检测和选择逻辑
3. 添加`EngineVersion`记录类存储引擎类型和版本

### Requirement: 客户端接口和实现
现有`ElasticsearchClient`接口和实现需要扩展。

**修改内容**:
1. 接口中可能需要添加引擎类型获取方法
2. 实现类需要支持两种客户端的切换
3. 添加`oebp`工具方法处理响应格式

## REMOVED Requirements

无移除的需求。

## 兼容性说明

### 向后兼容性
- 保持对Elasticsearch 7.x/8.x的支持
- 现有配置无需修改即可继续工作
- 默认行为保持不变（自动检测）

### OpenSearch 3.4.0 特定功能
- 支持AWS SigV4签名认证
- 支持OpenSearch特定的插件和扩展
- 兼容OpenSearch的安全插件

## 参考源码

### 关键源码文件
- `opencti-platform/opencti-graphql/src/database/engine.ts` (行1-500)
  - 引擎初始化和选择逻辑（行355-437）
  - 引擎版本检测（行341-353）
  - 附件处理器配置（行296-338）
  - 响应格式处理`oebp`（行286-294）

### 源码依赖版本
- `@opensearch-project/opensearch`: 3.5.1
- `@elastic/elasticsearch`: 8.19.1

### OpenSearch Java客户端版本选择
选择3.4.0版本的原因：
1. 与源码Node.js客户端3.5.1版本接近，保持功能一致性
2. OpenSearch 3.4.0是稳定版本，支持Java 21
3. 提供完整的REST API支持
