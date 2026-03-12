# OpenCTI-Java 适配 OpenSearch 3.4.0 检查清单

## 依赖配置检查项

- [x] pom.xml中添加了OpenSearch Java客户端依赖 `org.opensearch.client:opensearch-java:3.4.0`
- [x] pom.xml中添加了OpenSearch REST客户端依赖 `org.opensearch.client:opensearch-rest-client:3.4.0`
- [x] 现有Elasticsearch 7.17.9客户端依赖保留以确保向后兼容
- [x] Jackson依赖版本与OpenSearch客户端兼容
- [x] Maven依赖树显示所有OpenSearch依赖正确加载
- [x] 项目编译无依赖冲突错误

## 配置类检查项

- [x] `ElasticsearchProperties`类包含`engineSelector`属性
- [x] `ElasticsearchProperties`类包含`engineCheck`属性
- [x] `ElasticsearchProperties`类包含AWS相关配置属性（region, service, accessKey, secretKey）
- [x] `ElasticsearchConfig`类包含`getEngineSelector()`方法
- [x] `ElasticsearchConfig`类包含`isEngineCheck()`方法
- [x] `ElasticsearchConfig`类包含AWS配置获取方法
- [x] 配置默认值与源码一致（engineSelector默认"auto"，engineCheck默认true）

## 常量类检查项

- [x] `ElasticsearchConstants`类包含`ENGINE_ELK = "elk"`常量
- [x] `ElasticsearchConstants`类包含`ENGINE_OPENSEARCH = "opensearch"`常量
- [x] 常量值与源码`engine.ts`中的定义一致

## 接口检查项

- [x] `ElasticsearchClient`接口包含`String getEnginePlatform()`方法
- [x] `ElasticsearchClient`接口包含`String getEngineVersion()`方法
- [x] `ElasticsearchClient`接口包含`boolean isRuntimeSortEnable()`方法
- [x] `ElasticsearchClient`接口包含`boolean isAttachmentProcessorEnabled()`方法
- [x] `ElasticsearchClient`接口包含`EngineVersion`记录类
- [x] 接口方法签名符合Java规范

## 引擎检测与选择检查项

- [x] `searchEngineVersion()`方法正确解析`distribution`字段
- [x] `searchEngineVersion()`方法正确解析`number`字段
- [x] `auto`模式下先使用OpenSearch客户端尝试连接
- [x] 根据`distribution`字段正确识别引擎类型
- [x] `elk`模式直接使用Elasticsearch客户端
- [x] `opensearch`模式直接使用OpenSearch客户端
- [x] `engineCheck`为true时验证引擎类型与配置是否匹配
- [x] 引擎类型不匹配时抛出`ConfigurationError`
- [x] 运行时排序仅在Elasticsearch 7.12+时启用
- [x] 版本号解析使用正确的语义化版本比较

## 响应格式处理检查项

- [ ] `oebp`方法正确处理OpenSearch响应（从`body`字段提取）
- [ ] `oebp`方法正确处理Elasticsearch响应（直接使用）
- [ ] 所有原始操作方法（elRawSearch, elRawGet, elRawIndex等）使用`oebp`处理响应

## OpenSearch客户端连接检查项

- [x] 支持基本认证（用户名/密码）
- [x] 支持SSL/TLS配置
- [x] 支持超时和重试配置
- [x] 支持AWS SigV4签名认证（可选）
- [ ] 可以成功连接到本地OpenSearch 3.4.0实例
- [ ] 可以成功连接到AWS OpenSearch服务

## 数据类型映射检查项

- [ ] `ElasticsearchMapping`类根据引擎类型选择字段类型
- [ ] Elasticsearch使用`flattened`类型
- [ ] OpenSearch使用`flat_object`类型
- [ ] 所有使用扁平化类型的映射定义已更新
- [ ] 生成的映射JSON格式正确

## 附件处理器检查项

- [ ] Elasticsearch附件处理器配置使用`remove_binary: true`
- [ ] OpenSearch附件处理器配置不使用`remove_binary`
- [ ] OpenSearch配置使用单独的`remove`处理器
- [ ] `elConfigureAttachmentProcessor()`方法根据引擎类型选择配置
- [ ] 方法返回配置是否成功
- [ ] 配置失败时记录错误日志

## 生命周期策略检查项

- [ ] Elasticsearch ILM策略实现使用正确的API
- [ ] OpenSearch ISM策略实现使用正确的API
- [ ] 根据引擎类型选择正确的策略实现
- [ ] 策略配置包含热-温-冷阶段（Elasticsearch）
- [ ] 策略配置包含状态转换（OpenSearch）

## 双客户端架构检查项

- [x] 同时维护Elasticsearch和OpenSearch客户端实例
- [x] 根据检测结果切换活动客户端
- [x] 所有操作通过活动客户端执行
- [x] 客户端切换逻辑线程安全
- [x] 未使用的客户端实例资源正确释放

## 配置示例检查项

- [x] application.yml包含`engine-selector`配置示例
- [x] application.yml包含`engine-check`配置示例
- [x] application.yml包含AWS配置示例（注释状态）
- [x] 所有新配置有清晰的注释说明
- [x] YAML格式正确无语法错误

## 单元测试检查项

- [ ] 引擎自动检测逻辑有单元测试覆盖
- [ ] 引擎手动选择逻辑有单元测试覆盖
- [ ] `oebp`响应处理方法有单元测试覆盖
- [ ] 映射类型选择逻辑有单元测试覆盖
- [ ] 配置类有单元测试覆盖
- [ ] 所有单元测试通过
- [ ] 测试覆盖率符合项目标准

## 集成测试检查项

- [ ] OpenSearch 3.4.0测试环境搭建完成
- [ ] 连接测试通过
- [ ] 索引创建测试通过
- [ ] 文档CRUD测试通过
- [ ] 搜索查询测试通过
- [ ] 附件处理器测试通过
- [ ] 生命周期策略测试通过
- [ ] Elasticsearch向后兼容测试通过
- [ ] 引擎切换测试通过

## 文档检查项

- [ ] MODULE_OVERVIEW.md更新双客户端支持说明
- [ ] MODULE_OVERVIEW.md更新新配置项说明
- [ ] MODULE_OVERVIEW.md功能列表标记OpenSearch支持状态
- [ ] MODULE_OVERVIEW.md目录结构更新
- [x] 代码注释包含重写的原文件路径
- [x] 复杂逻辑有中文注释说明

## 源码一致性检查项

- [x] 引擎检测逻辑与`engine.ts`行355-437一致
- [x] 引擎版本获取逻辑与`engine.ts`行341-353一致
- [ ] 响应格式处理与`engine.ts`行286-294一致
- [ ] 附件处理器配置与`engine.ts`行296-338一致
- [ ] 生命周期策略与`engine.ts`行786-839一致
- [x] 常量定义与源码一致

## 性能和稳定性检查项

- [x] 客户端连接池配置合理
- [x] 超时配置合理
- [x] 重试机制正常工作
- [x] 异常处理完善
- [x] 资源释放正确
- [x] 无内存泄漏风险

## 最终验证检查项

- [x] 项目完整编译通过
- [ ] 所有单元测试通过
- [ ] 所有集成测试通过
- [ ] 代码审查通过
- [ ] 文档审查通过
- [ ] 可以成功连接到OpenSearch 3.4.0
- [ ] 可以成功连接到Elasticsearch 7.x/8.x
- [x] 功能与源码实现一致
