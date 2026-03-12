# Schema Internal Relationship 模块重写 Spec

## Why
Phase 3 Schema定义层的第四个任务是重写 `schema/internalRelationship.ts` 文件。该文件定义了OpenCTI系统中所有内部关系的类型常量（如migrates、member-of、has-role等），以及内部关系的类型判断方法。这是区分内部关系和其他类型关系的基础模块。

## What Changes
- 创建 `io.opencti.schema.internal` 包（已存在）
- 重写所有内部关系类型常量（9种）
- 重写内部关系列表（INTERNAL_RELATIONSHIPS）
- 重写类型判断方法（isInternalRelationship, isStoreRelationPir）
- 保持与源码逻辑完全一致

## Impact
- 影响文件: `opencti-platform/opencti-graphql/src/schema/internalRelationship.ts` (~35行)
- 目标文件: `opencti-java/src/main/java/io/opencti/schema/internal/InternalRelationshipSchema.java`
- 依赖模块:
  - `schema.general.SchemaGeneral` (ABSTRACT_INTERNAL_RELATIONSHIP常量)
  - `schema.SchemaTypesDefinition` (类型注册，已存在)
  - `types.store` (StoreCommon, StoreRelationPir类型)

## ADDED Requirements

### Requirement: 内部关系类型常量
系统 SHALL 提供以下内部关系类型常量：

- RELATION_MIGRATES = "migrates"
- RELATION_MEMBER_OF = "member-of"
- RELATION_PARTICIPATE_TO = "participate-to"
- RELATION_ALLOWED_BY = "allowed-by"
- RELATION_HAS_ROLE = "has-role"
- RELATION_HAS_CAPABILITY = "has-capability"
- RELATION_HAS_CAPABILITY_IN_DRAFT = "has-capability-in-draft"
- RELATION_ACCESSES_TO = "accesses-to"
- RELATION_IN_PIR = "in-pir"

### Requirement: 内部关系列表
系统 SHALL 定义以下内部关系列表：

#### INTERNAL_RELATIONSHIPS
包含以下9种关系类型：
- migrates
- member-of
- allowed-by
- has-role
- has-capability
- has-capability-in-draft
- accesses-to
- participate-to
- in-pir

### Requirement: 类型判断方法
系统 SHALL 提供以下类型判断方法：

#### Scenario: 判断是否为内部关系
- **WHEN** 调用 isInternalRelationship(type)
- **THEN** 如果类型在 INTERNAL_RELATIONSHIPS 列表中，或等于 ABSTRACT_INTERNAL_RELATIONSHIP，返回 true
- **AND** 否则返回 false

#### Scenario: 判断是否为PIR关系实例
- **WHEN** 调用 isStoreRelationPir(instance)
- **THEN** 如果实例的 entity_type 等于 RELATION_IN_PIR，返回 true
- **AND** 否则返回 false

### Requirement: 类型注册
系统 SHALL 在类加载时自动注册内部关系类型：
- 调用 SchemaTypesDefinition.register(ABSTRACT_INTERNAL_RELATIONSHIP, INTERNAL_RELATIONSHIPS)

## Implementation Notes
1. 所有常量使用 `public static final String` 定义
2. 使用 `Set` 存储类型列表以提高查询效率
3. 类型判断方法使用 `public static` 定义
4. 使用静态代码块在类加载时自动注册类型
5. 类必须添加注释说明重写的原文件路径
6. 每个常量/方法必须添加注释说明对应的原代码
7. isStoreRelationPir 方法需要 StoreCommon 和 StoreRelationPir 类型支持
