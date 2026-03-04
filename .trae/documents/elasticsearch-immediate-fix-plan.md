# Elasticsearch模块立即修复计划

## 一、修复概述

根据elasticsearch模块重写一致性检查报告，当前需要立即修复的差异有4项：

| 序号 | 修复项 | 优先级 | 预计工作量 |
|------|--------|--------|-----------|
| 1 | SSL完整配置 | P0 | 中等 |
| 2 | AWS SigV4认证 | P0 | 较高 |
| 3 | elUpdateIndicesMappings() | P0 | 高 |
| 4 | 关系连接更新逻辑 | P0 | 高 |

---

## 二、详细修复计划

### 2.1 SSL完整配置修复

#### 2.1.1 源码分析

**源码位置**: `engine.ts:357-380`

```typescript
// 源码关键逻辑
const configurationOptions = {
  node: url,
  auth: { username, password },
  tls: searchEngineConfiguration.ssl_verification
    ? {
        ca: searchEngineConfiguration.ssl_ca,
        rejectUnauthorized: searchEngineConfiguration.ssl_verification,
      }
    : undefined,
  agent: {
    https: {
      rejectUnauthorized: searchEngineConfiguration.ssl_verification,
      ca: searchEngineConfiguration.ssl_ca,
    },
  },
};
```

#### 2.1.2 当前Java实现问题

**文件**: `ElasticsearchClientImpl.java:84-96`

```java
private ElasticsearchClient createElasticsearchClient() {
    // TODO: 完整实现SSL配置和认证
    // 目前使用基本配置
    String url = config.getUrl();
    String username = config.getUsername();
    String password = config.getPassword();
    
    log.info("[SEARCH] Creating Elasticsearch client for URL: {}", url);
    
    // 使用Spring Boot自动配置的ElasticsearchClient
    // 这里返回null，由Spring注入
    return null;
}
```

**问题**:
1. 未实现SSL/TLS配置
2. 未处理CA证书
3. 未实现ssl_verification开关
4. 返回null依赖Spring注入，但未配置SSL

#### 2.1.3 修复步骤

**步骤1**: 创建SSL配置类 `ElasticsearchSslConfig.java`

```java
// 文件路径: src/main/java/io/opencti/database/elasticsearch/ElasticsearchSslConfig.java
// 功能: 
//   - 加载CA证书
//   - 创建SSLContext
//   - 配置HostnameVerifier
```

**步骤2**: 修改 `ElasticsearchClientImpl.java` 的 `createElasticsearchClient()` 方法

```java
// 需要实现:
// 1. 读取SSL配置
// 2. 创建带SSL的RestClient
// 3. 创建ElasticsearchClient
```

**步骤3**: 更新 `ElasticsearchConfig.java` 添加SSL相关配置获取方法

**步骤4**: 添加单元测试 `ElasticsearchSslConfigTest.java`

#### 2.1.4 涉及文件

| 文件 | 操作 |
|------|------|
| `ElasticsearchSslConfig.java` | 新建 |
| `ElasticsearchClientImpl.java` | 修改 |
| `ElasticsearchConfig.java` | 修改 |
| `ElasticsearchSslConfigTest.java` | 新建 |

---

### 2.2 AWS SigV4认证修复

#### 2.2.1 源码分析

**源码位置**: `engine.ts:384-393`

```typescript
// AWS OpenSearch SigV4认证
if (searchEngineConfiguration.engine_selector === 'aws') {
  const AwsSigv4Signer = await import('@opensearch-project/opensearch/aws');
  const { defaultProvider } = await import('@aws-sdk/credential-provider-node');
  configurationOptions.auth = undefined; // 清除基本认证
  configurationOptionsAws = {
    ...configurationOptions,
    connectionClass: AwsSigv4Signer.AwsSigv4Signer,
    awsConfig: new NodeHttpHandler({
      connectionTimeout: 600000,
      socketTimeout: 600000,
    }),
    credentials: defaultProvider({
      timeout: 600000,
      maxRetries: 10,
    }),
  };
}
```

#### 2.2.2 当前Java实现问题

**文件**: `ElasticsearchClientImpl.java`

**问题**:
1. 完全未实现AWS SigV4认证
2. 未添加AWS SDK依赖
3. 未处理engine_selector配置

#### 2.2.3 修复步骤

**步骤1**: 添加Maven依赖

```xml
<!-- pom.xml -->
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>auth</artifactId>
</dependency>
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>regions</artifactId>
</dependency>
```

**步骤2**: 创建AWS认证配置类 `AwsSigV4Config.java`

```java
// 文件路径: src/main/java/io/opencti/database/elasticsearch/AwsSigV4Config.java
// 功能:
//   - 创建AWS凭证提供者
//   - 配置AWS区域
//   - 实现SigV4签名
```

**步骤3**: 修改 `ElasticsearchClientImpl.java` 添加AWS客户端创建逻辑

```java
// 需要实现:
// 1. 检测engine_selector配置
// 2. 如果是'aws'，使用AWS SDK创建客户端
// 3. 否则使用基本认证
```

**步骤4**: 更新 `ElasticsearchConfig.java` 添加AWS配置获取方法

**步骤5**: 添加单元测试 `AwsSigV4ConfigTest.java`

#### 2.2.4 涉及文件

| 文件 | 操作 |
|------|------|
| `pom.xml` | 修改 |
| `AwsSigV4Config.java` | 新建 |
| `ElasticsearchClientImpl.java` | 修改 |
| `ElasticsearchConfig.java` | 修改 |
| `AwsSigV4ConfigTest.java` | 新建 |

---

### 2.3 elUpdateIndicesMappings() 修复

#### 2.3.1 源码分析

**源码位置**: `engine.ts:1268-1341`

```typescript
export const elUpdateIndicesMappings = async (): Promise<void> => {
  // 1. 更新核心设置
  await updateCoreSettings();
  
  // 2. 重置模板
  const mappingProperties = engineMappingGenerator();
  const templates = await elPlatformTemplates();
  for (let index = 0; index < templates.length; index += 1) {
    const template = templates[index];
    await updateIndexTemplate(template.name, mappingProperties);
  }
  
  // 3. 更新当前索引
  const indices = await elPlatformIndices();
  for (let indicesIndex = 0; indicesIndex < indices.length; indicesIndex += 1) {
    const { index } = indices[indicesIndex];
    const { rollover_alias } = await elIndexSetting(index);
    const indexMappingProperties = await elPlatformMapping(index);
    const platformSettings = computeIndexSettings(rollover_alias);
    
    // 更新索引设置
    await engine.indices.putSettings({ index, body: platformSettings });
    
    // 类型冲突检测和处理
    const indexMappingEntries = Object.entries(indexMappingProperties);
    for (let indexMapping = 0; indexMapping < indexMappingEntries.length; indexMapping += 1) {
      const [indexMappingKey, indexMappingValue] = indexMappingEntries[indexMapping];
      const mappingToCreate = mappingProperties[indexMappingKey];
      const currentType = indexMappingValue.type ?? 'object';
      const expectedType = mappingToCreate?.type ?? 'object';
      
      if (mappingToCreate && currentType !== expectedType) {
        // 不兼容升级检测，覆盖目标
        mappingProperties[indexMappingKey] = indexMappingProperties[indexMappingKey];
      }
    }
    
    // 使用jsonpatch计算差异
    const operations = jsonpatch.compare(
      sortMappingsKeys(indexMappingProperties), 
      sortMappingsKeys(mappingProperties)
    );
    
    // 只能添加新映射，不能替换已有映射
    const addOperations = operations
      .filter((o) => o.op === UPDATE_OPERATION_ADD)
      .filter((o) => {
        const isPropertiesCompletion = o.path.endsWith('/properties');
        const isDirectType = o.value.type;
        const isObjectType = o.value.properties;
        return R.is(Object, o.value) && (isPropertiesCompletion || isDirectType || isObjectType);
      });
    
    if (addOperations.length > 0) {
      const properties = jsonpatch.applyPatch(indexMappingProperties, addOperations).newDocument;
      await engine.indices.putMapping({ index, body: { properties } });
    }
  }
};
```

#### 2.3.2 当前Java实现问题

**文件**: `ElasticsearchIndices.java`

**问题**:
1. 完全未实现 `elUpdateIndicesMappings()` 方法
2. 未实现 `updateIndexTemplate()` 方法
3. 未实现 `computeIndexSettings()` 方法
4. 未实现映射差异计算逻辑

#### 2.3.3 修复步骤

**步骤1**: 添加json-patch依赖

```xml
<!-- pom.xml -->
<dependency>
    <groupId>com.github.java-json-tools</groupId>
    <artifactId>json-patch</artifactId>
    <version>1.13</version>
</dependency>
```

**步骤2**: 创建映射更新工具类 `MappingUpdateUtil.java`

```java
// 文件路径: src/main/java/io/opencti/database/elasticsearch/MappingUpdateUtil.java
// 功能:
//   - sortMappingsKeys(): 排序映射键
//   - computeMappingDiff(): 计算映射差异
//   - filterAddOperations(): 过滤添加操作
//   - applyMappingPatch(): 应用映射补丁
```

**步骤3**: 在 `ElasticsearchIndices.java` 中实现以下方法

```java
// 需要实现:
// 1. elUpdateIndicesMappings() - 主方法
// 2. updateIndexTemplate() - 更新索引模板
// 3. computeIndexSettings() - 计算索引设置
// 4. detectTypeCollision() - 检测类型冲突
```

**步骤4**: 添加单元测试 `MappingUpdateUtilTest.java`

#### 2.3.4 涉及文件

| 文件 | 操作 |
|------|------|
| `pom.xml` | 修改 |
| `MappingUpdateUtil.java` | 新建 |
| `ElasticsearchIndices.java` | 修改 |
| `MappingUpdateUtilTest.java` | 新建 |

---

### 2.4 关系连接更新逻辑修复

#### 2.4.1 源码分析

**源码位置**: `engine.ts:4491-4538`

```typescript
// elUpdateRelationConnections - 更新关系连接
export const elUpdateRelationConnections = async (elements: any[]) => {
  if (elements.length > 0) {
    const source = 'def conn = ctx._source.connections.find(c -> c.internal_id == params.id); '
      + 'for (change in params.changes.entrySet()) { conn[change.getKey()] = change.getValue() }';
    const bodyUpdate = elements.flatMap((doc) => [
      { update: { _index: doc._index, _id: doc._id ?? doc.id, retry_on_conflict: ES_RETRY_ON_CONFLICT } },
      { script: { source, params: { id: doc.toReplace, changes: doc.data } } },
    ]);
    const bulkPromise = elBulk({ refresh: true, timeout: BULK_TIMEOUT, body: bodyUpdate });
    await Promise.all([bulkPromise]);
  }
};

// elUpdateEntityConnections - 更新实体连接
export const elUpdateEntityConnections = async (elements: any[]) => {
  if (elements.length > 0) {
    const source = `if (ctx._source[params.key] == null) {
      ctx._source[params.key] = params.to;
    } else if (params.from == null) {
      ctx._source[params.key].addAll(params.to);
    } else {
      def values = params.to;
      for (current in ctx._source[params.key]) {
        if (current != params.from && !values.contains(current)) { values.add(current); }
      }
      ctx._source[params.key] = values;
    }
  `;
    const bodyUpdate = elements.flatMap((doc) => {
      const refField = isStixRefRelationship(doc.relationType) && isInferredIndex(doc._index) 
        ? ID_INFERRED : ID_INTERNAL;
      return [
        { update: { _index: doc._index, _id: doc._id ?? doc.id, retry_on_conflict: ES_RETRY_ON_CONFLICT } },
        {
          script: {
            source,
            params: {
              key: buildRefRelationKey(doc.relationType, refField),
              from: doc.toReplace,
              to: addMultipleFormat(doc),
            },
          },
        },
      ];
    });
    await elBulk({ refresh: true, timeout: BULK_TIMEOUT, body: bodyUpdate });
  }
};
```

#### 2.4.2 当前Java实现问题

**文件**: `ElasticsearchElement.java`

**问题**:
1. `elUpdateRelationConnections()` 标记为TODO
2. `elUpdateEntityConnections()` 标记为TODO
3. 缺少Painless脚本构建逻辑
4. 缺少批量更新操作构建

#### 2.4.3 修复步骤

**步骤1**: 创建脚本构建工具类 `ScriptBuilder.java`

```java
// 文件路径: src/main/java/io/opencti/database/elasticsearch/ScriptBuilder.java
// 功能:
//   - buildRelationConnectionScript(): 构建关系连接更新脚本
//   - buildEntityConnectionScript(): 构建实体连接更新脚本
//   - buildRefRelationKey(): 构建引用关系键
```

**步骤2**: 在 `ElasticsearchElement.java` 中实现以下方法

```java
// 需要实现:
// 1. elUpdateRelationConnections() - 更新关系连接
// 2. elUpdateEntityConnections() - 更新实体连接
// 3. elUpdateConnectionsOfElement() - 更新元素的连接
```

**步骤3**: 更新 `ElasticsearchBulk.java` 添加脚本更新支持

**步骤4**: 添加单元测试 `ElasticsearchConnectionTest.java`

#### 2.4.4 涉及文件

| 文件 | 操作 |
|------|------|
| `ScriptBuilder.java` | 新建 |
| `ElasticsearchElement.java` | 修改 |
| `ElasticsearchBulk.java` | 修改 |
| `ElasticsearchConnectionTest.java` | 新建 |

---

## 三、执行计划

### 3.1 任务分解

| 阶段 | 任务 | 预计时间 | 依赖 |
|------|------|----------|------|
| 1 | SSL完整配置修复 | 2小时 | 无 |
| 2 | AWS SigV4认证修复 | 3小时 | 阶段1 |
| 3 | elUpdateIndicesMappings()修复 | 4小时 | 无 |
| 4 | 关系连接更新逻辑修复 | 4小时 | 无 |
| 5 | 编译验证 | 0.5小时 | 阶段1-4 |
| 6 | 单元测试 | 2小时 | 阶段5 |

### 3.2 执行顺序

```
阶段1 (SSL配置) ──┬──> 阶段5 (编译) ──> 阶段6 (测试)
阶段2 (AWS认证) ──┤
阶段3 (映射更新) ─┤
阶段4 (关系连接) ─┘
```

### 3.3 验证标准

1. **编译通过**: `mvn compile` 无错误
2. **测试通过**: 所有新增测试用例通过
3. **功能验证**: 
   - SSL连接可正常建立
   - AWS认证可正常工作
   - 索引映射可正常更新
   - 关系连接可正常更新

---

## 四、风险评估

| 风险 | 可能性 | 影响 | 缓解措施 |
|------|--------|------|----------|
| AWS SDK版本冲突 | 中 | 高 | 使用与Spring Boot兼容的版本 |
| json-patch库兼容性 | 低 | 中 | 选择成熟的实现库 |
| Painless脚本语法差异 | 中 | 中 | 严格按源码逻辑实现 |
| SSL证书格式问题 | 低 | 中 | 支持PEM和JKS格式 |

---

## 五、验收标准

### 5.1 代码质量

- [ ] 所有新增代码有中文注释
- [ ] 所有新增方法标注源码位置
- [ ] 代码风格符合项目规范

### 5.2 功能完整性

- [ ] SSL配置完整实现
- [ ] AWS SigV4认证完整实现
- [ ] elUpdateIndicesMappings()完整实现
- [ ] 关系连接更新逻辑完整实现

### 5.3 测试覆盖

- [ ] SSL配置测试用例
- [ ] AWS认证测试用例
- [ ] 映射更新测试用例
- [ ] 关系连接测试用例

---

## 六、后续工作

修复完成后，建议继续处理以下P1优先级任务：

1. 完善元素操作中的关系连接逻辑
2. 补充缺失的单元测试
3. 随着其他模块开发，完善依赖功能
