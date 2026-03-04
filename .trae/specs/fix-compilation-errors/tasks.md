# Tasks

## 任务1: 修复ErrorCode枚举缺失方法
- [ ] 子任务1.1: 在ErrorCode枚举中添加getHttpStatus()方法
- [ ] 子任务1.2: 在ErrorCode枚举中添加getMessage()方法  
- [ ] 子任务1.3: 在ErrorCode枚举中添加getCode()方法
- [ ] 子任务1.4: 修复OpenCTIException中对ErrorCode方法的调用
- [ ] 子任务1.5: 修复AuthenticationException中对ErrorCode方法的调用

## 任务2: 修复缺少@Slf4j注解的类
- [ ] 子任务2.1: 为CyberArkCredentialsProvider添加@Slf4j注解
- [ ] 子任务2.2: 为LoggingConfiguration添加@Slf4j注解
- [ ] 子任务2.3: 为ProxyConfiguration添加@Slf4j注解

## 任务3: 修复QueryBuilder API兼容性
- [ ] 子任务3.1: 修复range()方法使用UntypedRangeQuery API
- [ ] 子任务3.2: 修复terms()方法的FieldValue类型转换
- [ ] 子任务3.3: 验证其他查询方法API兼容性

## 任务4: 修复FilterBuilder API兼容性
- [ ] 子任务4.1: 修复terms查询的FieldValue类型转换
- [ ] 子任务4.2: 修复range查询使用UntypedRangeQuery
- [ ] 子任务4.3: 修复nested查询的Map类型问题

## 任务5: 修复ElasticsearchAggregation API兼容性
- [ ] 子任务5.1: 修复aggregation()方法调用为aggregations()
- [ ] 子任务5.2: 验证聚合查询构建器API

## 任务6: 修复ElasticsearchBulk API兼容性
- [ ] 子任务6.1: 修复refresh参数从boolean改为Refresh枚举
- [ ] 子任务6.2: 修复UpdateOperation的doc()方法调用
- [ ] 子任务6.3: 移除或修复elBulkFromNdjson方法

## 任务7: 修复ElasticsearchClient接口一致性
- [ ] 子任务7.1: 添加缺失的EngineVersion内部类
- [ ] 子任务7.2: 同步接口方法签名与实现类
- [ ] 子任务7.3: 修复distribution()方法调用

## 任务8: 修复ElasticsearchIndices API兼容性
- [ ] 子任务8.1: 修复IndicesRequest.Builder的format()方法
- [ ] 子任务8.2: 修复TemplatesRequest.Builder的format()方法
- [ ] 子任务8.3: 修复GetSettingsResponse类型问题
- [ ] 子任务8.4: 修复ILM Phase的minAge()方法使用Time对象
- [ ] 子任务8.5: 修复jsonValue()方法调用

## 任务9: 修复MinIO存储模块API兼容性
- [ ] 子任务9.1: 修复FileStorageClientImpl的endpoint()方法签名
- [ ] 子任务9.2: 修复FileStorageServiceImpl的Item.key()改为objectName()
- [ ] 子任务9.3: 确保FileStorageConfig的getPort()方法可用

## 任务10: 修复RabbitMQ模块API兼容性
- [ ] 子任务10.1: 移除RabbitMQManagementClient的setRequestCustomizer调用

## 任务11: 编译验证
- [ ] 子任务11.1: 执行mvn compile验证所有错误已修复
- [ ] 子任务11.2: 执行mvn test验证测试通过

## 任务12: 运行验证
- [ ] 子任务12.1: 执行mvn spring-boot:run验证项目可启动

# Task Dependencies

- [任务2] 可与 [任务1] 并行执行
- [任务3-8] 依赖 [任务1-2] 完成
- [任务9-10] 可与 [任务3-8] 并行执行
- [任务11] 依赖 [任务1-10] 全部完成
- [任务12] 依赖 [任务11] 完成
