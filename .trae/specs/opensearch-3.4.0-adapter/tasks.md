# OpenCTI-Java 适配 OpenSearch 3.4.0 任务列表

## 任务概览

本任务列表详细规划了将OpenCTI-Java项目适配到OpenSearch 3.4.0的所有工作项。任务按照依赖关系和逻辑顺序排列，每个任务都包含具体的实现步骤和验证标准。

---

## Task 1: 添加OpenSearch Java客户端依赖 [已完成]

**描述**: 在pom.xml中添加OpenSearch 3.4.0 Java客户端依赖，同时保持现有Elasticsearch客户端依赖以确保向后兼容。

**子任务**:
- [x] SubTask 1.1: 添加OpenSearch Java客户端依赖到dependencyManagement
  - 添加`org.opensearch.client:opensearch-java:3.4.0`
  - 添加`org.opensearch.client:opensearch-rest-client:3.4.0`
- [x] SubTask 1.2: 在dependencies部分添加OpenSearch依赖
- [x] SubTask 1.3: 验证Jackson版本兼容性
- [x] SubTask 1.4: 执行Maven编译确保依赖正确加载

**验证标准**:
- `mvn dependency:tree`显示OpenSearch客户端依赖已正确添加
- 项目编译无错误

---

## Task 2: 扩展ElasticsearchProperties配置类 [已完成]

**描述**: 在`ElasticsearchProperties`类中添加OpenSearch特定的配置属性，包括AWS认证、区域设置等。

**子任务**:
- [x] SubTask 2.1: 添加引擎选择器属性`engineSelector`
  - 默认值为`"auto"`
  - 可选值: `"auto"`, `"elk"`, `"opensearch"`
- [x] SubTask 2.2: 添加引擎检查属性`engineCheck`
  - 默认值为`true`
- [x] SubTask 2.3: 添加AWS相关配置属性
  - `region`: AWS区域
  - `service`: AWS服务名称（默认`"es"`）
  - `accessKey`: AWS访问密钥
  - `secretKey`: AWS密钥
- [x] SubTask 2.4: 验证配置类可以正确加载所有新属性

**验证标准**:
- 配置类可以正确加载application.yml中的所有新属性
- 默认值设置正确

---

## Task 3: 更新ElasticsearchConstants常量类 [已完成]

**描述**: 在常量类中添加引擎类型常量和相关配置常量。

**子任务**:
- [x] SubTask 3.1: 添加引擎类型常量
  - `ENGINE_ELK = "elk"`
  - `ENGINE_OPENSEARCH = "opensearch"`
- [x] SubTask 3.2: 添加默认配置常量
  - 默认引擎选择器
  - 默认引擎检查开关
- [x] SubTask 3.3: 编译验证

**验证标准**:
- 常量类编译通过
- 常量值与源码保持一致

---

## Task 4: 扩展ElasticsearchClient接口 [已完成]

**描述**: 扩展客户端接口以支持引擎类型获取和运行时排序检测。

**子任务**:
- [x] SubTask 4.1: 添加引擎平台类型获取方法
  - `String getEnginePlatform()`
- [x] SubTask 4.2: 添加引擎版本号获取方法
  - `String getEngineVersion()`
- [x] SubTask 4.3: 添加运行时排序启用状态方法
  - `boolean isRuntimeSortEnable()`
- [x] SubTask 4.4: 添加附件处理器启用状态方法
  - `boolean isAttachmentProcessorEnabled()`
- [x] SubTask 4.5: 添加`EngineVersion`记录类

**验证标准**:
- 接口编译通过
- 所有方法签名符合Java规范

---

## Task 5: 实现引擎检测和选择逻辑 [已完成]

**描述**: 实现与源码一致的引擎自动检测和手动选择逻辑。

**子任务**:
- [x] SubTask 5.1: 实现`searchEngineVersion`方法
  - 使用OpenSearch客户端获取引擎信息
  - 解析`distribution`字段确定引擎类型
  - 解析`number`字段获取版本号
- [x] SubTask 5.2: 实现`searchEngineInit`方法中的引擎选择逻辑
  - 根据`engine-selector`配置选择客户端
  - 实现`auto`模式的自动检测
  - 实现`elk`和`opensearch`模式的手动选择
  - 根据`engine-check`配置验证引擎类型
- [x] SubTask 5.3: 实现运行时排序启用状态检测
  - 仅Elasticsearch 7.12+启用运行时排序
  - 使用版本号判断
- [x] SubTask 5.4: 实现响应格式处理工具方法`oebp`
  - OpenSearch响应从`body`字段提取
  - Elasticsearch直接返回响应

**验证标准**:
- 可以正确检测Elasticsearch引擎
- 可以正确检测OpenSearch引擎
- 手动选择模式工作正常
- 引擎检查功能正常

---

## Task 6: 实现OpenSearch客户端连接配置 [已完成]

**描述**: 实现OpenSearch客户端的连接配置，包括基本认证和AWS SigV4签名认证。

**子任务**:
- [x] SubTask 6.1: 实现基本连接配置
  - URL、用户名、密码配置
  - SSL/TLS配置
  - 超时和重试配置
- [x] SubTask 6.2: 实现AWS SigV4签名认证配置（可选）
  - 区域配置
  - 服务名称配置
  - 凭证提供者配置
- [x] SubTask 6.3: 实现客户端实例创建和管理

**验证标准**:
- 可以连接到本地OpenSearch实例
- 可以连接到AWS OpenSearch服务

---

## Task 7: 实现数据类型映射适配 [待实现]

**描述**: 在映射生成器中根据引擎类型动态选择正确的字段类型。

**子任务**:
- [ ] SubTask 7.1: 修改`ElasticsearchMapping`类
  - 添加引擎类型判断逻辑
  - Elasticsearch使用`flattened`类型
  - OpenSearch使用`flat_object`类型
- [ ] SubTask 7.2: 更新所有使用扁平化类型的映射定义
- [ ] SubTask 7.3: 编译验证

**验证标准**:
- 生成的Elasticsearch映射使用`flattened`类型
- 生成的OpenSearch映射使用`flat_object`类型

---

## Task 8: 实现附件处理器配置适配 [待实现]

**描述**: 根据引擎类型配置正确的附件处理器Pipeline。

**子任务**:
- [ ] SubTask 8.1: 实现Elasticsearch附件处理器配置
  - 使用`attachment`处理器
  - 设置`remove_binary: true`
- [ ] SubTask 8.2: 实现OpenSearch附件处理器配置
  - 使用`attachment`处理器（不带remove_binary）
  - 添加`remove`处理器移除`file_data`字段
- [ ] SubTask 8.3: 实现`elConfigureAttachmentProcessor`方法
  - 根据引擎类型选择配置
  - 返回配置是否成功

**验证标准**:
- Elasticsearch Pipeline配置正确
- OpenSearch Pipeline配置正确

---

## Task 9: 实现生命周期策略适配 [待实现]

**描述**: 根据引擎类型实现相应的索引生命周期管理策略。

**子任务**:
- [ ] SubTask 9.1: 研究源码中的ILM和ISM实现
  - 阅读`engine.ts`中相关代码（行786-839）
- [ ] SubTask 9.2: 实现Elasticsearch ILM策略
  - 创建ILM策略API调用
  - 配置热-温-冷阶段
- [ ] SubTask 9.3: 实现OpenSearch ISM策略
  - 创建ISM策略API调用
  - 配置状态转换
- [ ] SubTask 9.4: 在`ElasticsearchIndices`类中添加策略创建方法

**验证标准**:
- Elasticsearch ILM策略创建成功
- OpenSearch ISM策略创建成功

---

## Task 10: 更新ElasticsearchConfig配置类 [已完成]

**描述**: 更新配置类以支持新的配置属性和方法。

**子任务**:
- [x] SubTask 10.1: 添加引擎选择器获取方法
- [x] SubTask 10.2: 添加引擎检查获取方法
- [x] SubTask 10.3: 添加AWS配置获取方法
- [x] SubTask 10.4: 编译验证

**验证标准**:
- 配置类编译通过
- 所有新方法工作正常

---

## Task 11: 实现双客户端支持架构 [已完成]

**描述**: 重构客户端实现以支持同时维护Elasticsearch和OpenSearch客户端实例。

**子任务**:
- [x] SubTask 11.1: 设计双客户端架构
  - 同时维护两个客户端实例
  - 根据引擎类型选择使用哪个实例
- [x] SubTask 11.2: 实现客户端实例管理
  - 创建两个客户端实例
  - 根据检测结果切换活动客户端
- [x] SubTask 11.3: 实现统一的操作接口
  - 所有操作通过活动客户端执行
  - 响应格式统一处理

**验证标准**:
- 可以同时创建两个客户端实例
- 可以根据配置切换客户端
- 所有操作通过正确的客户端执行

---

## Task 12: 更新application.yml配置示例 [已完成]

**描述**: 更新application.yml文件，添加OpenSearch相关配置示例。

**子任务**:
- [x] SubTask 12.1: 添加引擎选择器配置示例
- [x] SubTask 12.2: 添加引擎检查配置示例
- [x] SubTask 12.3: 添加AWS配置示例（注释状态）
- [x] SubTask 12.4: 更新配置文档注释

**验证标准**:
- application.yml格式正确
- 所有新配置有清晰的注释说明

---

## Task 13: 编写单元测试 [待实现]

**描述**: 为OpenSearch适配功能编写单元测试。

**子任务**:
- [ ] SubTask 13.1: 编写引擎检测测试
  - 测试自动检测逻辑
  - 测试手动选择逻辑
- [ ] SubTask 13.2: 编写响应格式处理测试
  - 测试`oebp`方法
- [ ] SubTask 13.3: 编写映射类型选择测试
- [ ] SubTask 13.4: 编写配置类测试

**验证标准**:
- 所有单元测试通过
- 测试覆盖率符合项目标准

---

## Task 14: 集成测试和验证 [待实现]

**描述**: 进行集成测试，验证OpenSearch 3.4.0适配功能完整可用。

**子任务**:
- [ ] SubTask 14.1: 搭建OpenSearch 3.4.0测试环境
- [ ] SubTask 14.2: 测试连接和基本操作
  - 索引创建
  - 文档CRUD
  - 搜索查询
- [ ] SubTask 14.3: 测试附件处理器
- [ ] SubTask 14.4: 测试生命周期策略
- [ ] SubTask 14.5: 测试与Elasticsearch的兼容性

**验证标准**:
- 所有集成测试通过
- OpenSearch 3.4.0功能完整可用
- Elasticsearch向后兼容正常

---

## Task 15: 更新MODULE_OVERVIEW.md文档 [待实现]

**描述**: 更新项目文档，记录OpenSearch 3.4.0适配的相关信息。

**子任务**:
- [ ] SubTask 15.1: 更新架构说明
  - 添加双客户端支持说明
- [ ] SubTask 15.2: 更新配置说明
  - 添加新配置项说明
- [ ] SubTask 15.3: 更新功能列表
  - 标记OpenSearch支持状态
- [ ] SubTask 15.4: 更新目录结构

**验证标准**:
- 文档更新完整
- 与实际代码保持一致

---

## 任务依赖关系

```
Task 1 (依赖)
    |
    v
Task 2 (配置属性) ----> Task 3 (常量) ----> Task 4 (接口)
    |                                        |
    |                                        v
    |                                   Task 10 (配置类)
    |                                        |
    v                                        v
Task 6 (客户端配置) <-------------------- Task 5 (引擎检测)
    |                                        |
    v                                        v
Task 11 (双客户端架构) <------------------ Task 8 (附件处理器)
    |                                        |
    v                                        v
Task 7 (映射适配)                         Task 9 (生命周期策略)
    |                                        |
    v                                        v
Task 12 (配置示例) <---------------------- Task 13 (单元测试)
    |
    v
Task 14 (集成测试)
    |
    v
Task 15 (文档更新)
```

## 并行执行建议

以下任务可以并行执行：
- Task 1, Task 2, Task 3 可以并行
- Task 7, Task 8, Task 9 可以并行（在Task 5完成后）
- Task 12, Task 13 可以并行

## 风险点

1. **依赖冲突**: OpenSearch和Elasticsearch客户端可能有依赖冲突，需要仔细管理
2. **API差异**: 两个客户端的API可能有细微差异，需要仔细测试
3. **版本兼容性**: 需要确保与Spring Boot 3.3.6兼容

## 回滚策略

如果适配过程中出现严重问题，可以：
1. 回滚pom.xml中的依赖更改
2. 恢复原有的单客户端实现
3. 项目可以继续使用Elasticsearch 7.17.9客户端
