# 编译错误修复检查清单

## 通用模块检查
- [ ] ErrorCode枚举包含getHttpStatus()方法
- [ ] ErrorCode枚举包含getMessage()方法
- [ ] ErrorCode枚举包含getCode()方法
- [ ] OpenCTIException正确调用ErrorCode方法
- [ ] AuthenticationException正确调用ErrorCode方法
- [ ] CyberArkCredentialsProvider有@Slf4j注解或Logger定义
- [ ] LoggingConfiguration有@Slf4j注解或Logger定义
- [ ] ProxyConfiguration有@Slf4j注解或Logger定义

## Elasticsearch模块检查
- [ ] QueryBuilder.range()使用正确的UntypedRangeQuery API
- [ ] QueryBuilder.terms()正确转换FieldValue类型
- [ ] FilterBuilder.terms()正确转换FieldValue类型
- [ ] FilterBuilder.range()使用正确的API
- [ ] ElasticsearchAggregation使用aggregations()方法
- [ ] ElasticsearchBulk使用Refresh枚举
- [ ] ElasticsearchBulk的UpdateOperation使用正确API
- [ ] ElasticsearchClient接口与实现类方法签名一致
- [ ] ElasticsearchClientImpl包含EngineVersion内部类
- [ ] ElasticsearchIndices使用正确的format()方法
- [ ] ElasticsearchIndices使用正确的minAge()方法

## MinIO存储模块检查
- [ ] FileStorageClientImpl使用正确的endpoint()方法签名
- [ ] FileStorageServiceImpl使用Item.objectName()
- [ ] FileStorageConfig包含getPort()方法

## RabbitMQ模块检查
- [ ] RabbitMQManagementClient不使用setRequestCustomizer方法

## 编译验证
- [ ] mvn compile执行成功，无错误
- [ ] mvn test执行成功，测试通过

## 运行验证
- [ ] mvn spring-boot:run执行成功，项目启动
