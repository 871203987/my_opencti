# Checklist - Schema Identifier 模块重写

## 代码实现检查

- [x] SchemaIdentifier.java 文件已创建在正确的包路径下
- [x] 类注释包含原文件路径: `opencti-platform/opencti-graphql/src/schema/identifier.js`
- [x] 所有常量使用 `public static final String` 定义
- [x] 常量注释与原代码一一对应

### 常量定义检查

#### 哈希算法常量 (7个)
- [x] MD5 = "MD5"
- [x] SHA_1 = "SHA-1"
- [x] SHA_256 = "SHA-256"
- [x] SHA_512 = "SHA-512"
- [x] SHA3_256 = "SHA3-256"
- [x] SHA3_512 = "SHA3-512"
- [x] SSDEEP = "SSDEEP"

#### 字段名常量 (17个)
- [x] INTERNAL_FROM_FIELD = "i_relations_from"
- [x] INTERNAL_TO_FIELD = "i_relations_to"
- [x] NAME_FIELD = "name"
- [x] INNER_TYPE = "opencti_type"
- [x] VALUE_FIELD = "value"
- [x] FIRST_SEEN = "first_seen"
- [x] LAST_SEEN = "last_seen"
- [x] START_TIME = "start_time"
- [x] STOP_TIME = "stop_time"
- [x] VALID_FROM = "valid_from"
- [x] FIRST_OBSERVED = "first_observed"
- [x] LAST_OBSERVED = "last_observed"
- [x] VALID_UNTIL = "valid_until"
- [x] REVOKED = "revoked"
- [x] X_MITRE_ID_FIELD = "x_mitre_id"
- [x] X_DETECTION = "x_opencti_detection"
- [x] X_WORKFLOW_ID = "x_opencti_workflow_id"
- [x] X_SCORE = "x_opencti_score"

#### TLP标记常量 (5个)
- [x] MARKING_TLP_CLEAR_ID = "613f2e26-407d-48c7-9eca-b8e91df99dc9"
- [x] MARKING_TLP_CLEAR = "marking-definition--613f2e26-407d-48c7-9eca-b8e91df99dc9"
- [x] MARKING_TLP_GREEN_ID = "34098fce-860f-48ae-8e50-ebd3cc5e41da"
- [x] MARKING_TLP_GREEN = "marking-definition--34098fce-860f-48ae-8e50-ebd3cc5e41da"
- [x] MARKING_TLP_AMBER_ID = "f88d31f6-486f-44da-b317-01333bde0b82"
- [x] MARKING_TLP_AMBER = "marking-definition--f88d31f6-486f-44da-b317-01333bde0b82"
- [x] MARKING_TLP_AMBER_STRICT_ID = "826578e1-40ad-459f-bc73-ede076f81f37"
- [x] MARKING_TLP_AMBER_STRICT = "marking-definition--826578e1-40ad-459f-bc73-ede076f81f37"
- [x] MARKING_TLP_RED_ID = "5e57c739-391a-4eb3-b6be-7d15ca92d5ed"
- [x] MARKING_TLP_RED = "marking-definition--5e57c739-391a-4eb3-b6be-7d15ca92d5ed"
- [x] STATIC_MARKING_IDS 列表正确
- [x] STATIC_STANDARD_IDS 列表正确

### 工具方法检查

#### 基础工具方法
- [x] normalizeName(name) - 返回小写并trim的字符串
- [x] getStaticIdFromData(data) - 从数据获取静态ID

#### ID生成方法
- [x] generateInternalId() - 生成UUID v4
- [x] generateWorkId(connectorId) - 生成工作ID
- [x] generateFileIndexId(fileId) - 基于fileId生成UUID v5
- [x] generateStandardId(type, data) - 根据类型生成Standard ID (简化版本)

#### 别名ID生成方法
- [x] generateAliasesId(rawAliases, instance) - 生成别名ID列表
- [x] generateAliasesIdsForInstance(instance) - 从实例生成别名ID

#### 实例ID获取方法
- [x] getInstanceIds(instance) - 获取实例所有ID
- [x] getInstanceIds(instance, withoutInternal) - 获取实例所有ID（可选排除internal_id）
- [x] getInputIds(type, input) - 获取输入所有ID
- [x] getInputIds(type, input, fromRule) - 获取输入所有ID（规则模式）

#### 占位符方法
- [x] isSupportedStixType(stixType) - 判断是否支持的STIX类型
- [x] isFieldContributingToStandardId(instance, keys) - 判断字段是否贡献Standard ID

### WorkId类检查
- [x] WorkId类已创建
- [x] 包含id和timestamp字段
- [x] 提供getter方法

## 编译检查

- [x] SchemaIdentifier.java 单独编译成功
- [x] 无编译错误
- [ ] Maven 整体编译通过 (待其他文件修复)

## 测试检查

- [x] SchemaIdentifierTest.java 测试类已创建
- [x] 哈希算法常量测试通过
- [x] 字段名常量测试通过
- [x] TLP标记常量测试通过
- [x] 工具方法测试通过
- [x] ID生成方法测试通过
- [x] 别名ID生成测试通过
- [x] 实例ID获取测试通过
- [x] 输入ID获取测试通过
- [ ] 所有测试用例通过 Maven 运行 (待其他文件修复)

## 待完善功能 (未来迭代)

以下功能在当前简化版本中未完全实现，将在后续迭代中完善：

### ID贡献字段定义
- [ ] 35种 STIX网络可观测对象的详细ID贡献定义
- [ ] 20种 内部对象的详细ID贡献定义
- [ ] 18种 STIX域对象的详细ID贡献定义
- [ ] 4种 STIX元对象的详细ID贡献定义
- [ ] 2种 关系对象的详细ID贡献定义

### Resolver实现
- [ ] from resolver
- [ ] src resolver
- [ ] dst resolver
- [ ] hashes resolver
- [ ] name resolver
- [ ] identity_class resolver
- [ ] 其他Resolver

### Standard ID变更检测
- [ ] isStandardIdSameWay(previous, updated)
- [ ] isStandardIdChanged(previous, updated, operation)
- [ ] isStandardIdUpgraded(previous, updated)
- [ ] isStandardIdDowngraded(previous, updated)

### 依赖模块集成
- [ ] 集成 schema.internalObject 类型判断
- [ ] 集成 schema.stixDomainObject 类型判断
- [ ] 集成 schema.stixMetaObject 类型判断
- [ ] 集成 schema.stixCyberObservable 类型判断
- [ ] 集成 schema.stixRelationship 类型判断

## 总结

- **实际代码量**: ~590行 (SchemaIdentifier.java: ~280行, 测试: ~310行)
- **预估完整代码量**: ~2350行
- **完成度**: 基础功能 100%，完整功能 ~25%
- **依赖模块**: 需要其他Schema模块的类型判断方法
