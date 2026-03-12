# Schema STIX Core Object 模块重写 Spec

## Why
Phase 3 Schema定义层的第五个任务是重写 `schema/stixCoreObject.ts` 文件。该文件定义了STIX核心对象的类型判断方法，包括STIX核心对象、STIX对象、基本对象的判断逻辑，以及内部可导出类型列表和STIX核心对象选项配置。

## What Changes
- 创建 `io.opencti.schema.stix` 包
- 重写内部可导出类型列表（INTERNAL_EXPORTABLE_TYPES）
- 重写类型判断方法（isStixCoreObject, isStixObject, isBasicObject, isStixExportableInStreamData）
- 重写STIX核心对象选项（stixCoreObjectOptions）
- 保持与源码逻辑完全一致

## Impact
- 影响文件: `opencti-platform/opencti-graphql/src/schema/stixCoreObject.ts` (~27行)
- 目标文件: `opencti-java/src/main/java/io/opencti/schema/stix/StixCoreObjectSchema.java`
- 依赖模块:
  - `schema.stixDomainObject` (isStixDomainObject方法)
  - `schema.stixCyberObservable` (isStixCyberObservable方法)
  - `schema.stixMetaObject` (isStixMetaObject方法)
  - `schema.internalObject` (isInternalObject方法)
  - `schema.stixRelationship` (isStixRelationshipExceptRef方法)
  - `schema.internalRelationship` (RELATION_PARTICIPATE_TO, RELATION_IN_PIR常量)
  - `schema.general` (ABSTRACT_BASIC_OBJECT, ABSTRACT_STIX_CORE_OBJECT, ABSTRACT_STIX_OBJECT常量)
  - `types.store` (StoreObject类型)

## ADDED Requirements

### Requirement: 内部可导出类型列表
系统 SHALL 提供以下内部可导出类型列表：

- INTERNAL_EXPORTABLE_TYPES: 包含 RELATION_PARTICIPATE_TO 和 RELATION_IN_PIR

### Requirement: 类型判断方法
系统 SHALL 提供以下类型判断方法：

#### Scenario: 判断是否为STIX核心对象
- **WHEN** 调用 isStixCoreObject(type)
- **THEN** 如果类型是STIX域对象或STIX网络可观测对象，或等于 ABSTRACT_STIX_CORE_OBJECT，返回 true
- **AND** 否则返回 false

#### Scenario: 判断是否为STIX对象
- **WHEN** 调用 isStixObject(type)
- **THEN** 如果类型是STIX核心对象或STIX元对象，或等于 ABSTRACT_STIX_OBJECT，返回 true
- **AND** 否则返回 false

#### Scenario: 判断是否为基本对象
- **WHEN** 调用 isBasicObject(type)
- **THEN** 如果类型是内部对象或STIX对象，或等于 ABSTRACT_BASIC_OBJECT，返回 true
- **AND** 否则返回 false

#### Scenario: 判断是否为可在流数据中导出的STIX对象
- **WHEN** 调用 isStixExportableInStreamData(instance)
- **THEN** 如果实例的 entity_type 是STIX对象，或是非引用STIX关系，或在 INTERNAL_EXPORTABLE_TYPES 列表中，返回 true
- **AND** 否则返回 false

### Requirement: STIX核心对象选项
系统 SHALL 提供以下STIX核心对象选项：

#### StixCoreObjectsOrdering
包含以下排序字段映射：
- opinions_metrics_mean -> "opinions_metrics.mean"
- opinions_metrics_max -> "opinions_metrics.max"
- opinions_metrics_min -> "opinions_metrics.min"
- opinions_metrics_total -> "opinions_metrics.total"

## Implementation Notes
1. 所有常量使用 `public static final` 定义
2. 类型判断方法使用 `public static` 定义
3. 选项配置使用 `Map` 或自定义类实现
4. 类必须添加注释说明重写的原文件路径
5. 每个常量/方法必须添加注释说明对应的原代码
6. 依赖其他Schema模块的方法需要先用桩实现
