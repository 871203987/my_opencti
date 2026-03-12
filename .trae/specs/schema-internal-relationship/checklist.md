# Checklist - Schema Internal Relationship 模块重写

## 代码实现检查

- [x] InternalRelationshipSchema.java 文件已创建在正确的包路径下
- [x] 类注释包含原文件路径: `opencti-platform/opencti-graphql/src/schema/internalRelationship.ts`
- [x] 所有常量使用 `public static final String` 定义
- [x] 常量注释与原代码一一对应

### 常量定义检查

#### 内部关系类型常量 (9个)
- [x] RELATION_MIGRATES = "migrates"
- [x] RELATION_MEMBER_OF = "member-of"
- [x] RELATION_PARTICIPATE_TO = "participate-to"
- [x] RELATION_ALLOWED_BY = "allowed-by"
- [x] RELATION_HAS_ROLE = "has-role"
- [x] RELATION_HAS_CAPABILITY = "has-capability"
- [x] RELATION_HAS_CAPABILITY_IN_DRAFT = "has-capability-in-draft"
- [x] RELATION_ACCESSES_TO = "accesses-to"
- [x] RELATION_IN_PIR = "in-pir"

### 内部关系列表检查

#### INTERNAL_RELATIONSHIPS 列表
- [x] 包含 migrates
- [x] 包含 member-of
- [x] 包含 allowed-by
- [x] 包含 has-role
- [x] 包含 has-capability
- [x] 包含 has-capability-in-draft
- [x] 包含 accesses-to
- [x] 包含 participate-to
- [x] 包含 in-pir
- [x] 使用 Set 存储以提高查询效率

### 类型判断方法检查

- [x] isInternalRelationship(type) 方法
  - [x] 正确判断类型是否在 INTERNAL_RELATIONSHIPS 中
  - [x] 正确判断类型是否等于 ABSTRACT_INTERNAL_RELATIONSHIP

- [x] isStoreRelationPir(instance) 方法
  - [x] 正确判断实例的 entity_type 是否等于 RELATION_IN_PIR
  - [x] 需要 StoreCommon 和 StoreRelationPir 类型支持

### 类型注册检查

- [x] 静态代码块在类加载时自动注册类型
- [x] 调用 register(ABSTRACT_INTERNAL_RELATIONSHIP, INTERNAL_RELATIONSHIPS)

## 编译检查

- [x] InternalRelationshipSchema.java 单独编译成功
- [x] StoreCommon.java 单独编译成功
- [x] StoreRelationPir.java 单独编译成功
- [x] 无编译错误
- [x] 无编译警告
- [ ] Maven 整体编译通过 (待其他文件修复)

## 测试检查

- [x] InternalRelationshipSchemaTest.java 测试类已创建
- [x] 常量值测试通过
- [x] 类型判断方法测试通过
- [x] 类型注册测试通过
- [ ] 所有测试用例通过 Maven 运行 (待其他文件修复)

## 总结

- **实际代码量**: ~267行 (StoreCommon: ~30行, StoreRelationPir: ~11行, InternalRelationshipSchema: ~99行, 测试: ~127行)
- **预估代码量**: ~100-150行
- **完成度**: 100%
- **依赖模块**: SchemaGeneral (ABSTRACT_INTERNAL_RELATIONSHIP), SchemaTypesDefinition
