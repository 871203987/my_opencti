# STIX Meta Object Schema 重写规范

## Why
STIX元对象(StixMetaObject)是OpenCTI中用于定义STIX元数据实体的核心Schema模块，包括Label、External-Reference、Kill-Chain-Phase、Marking-Definition等类型。这些元数据实体用于标记、分类和引用外部资源，是构建完整STIX数据模型的基础组件。

## What Changes
- 新增 `StixMetaObjectSchema.java` - STIX元对象Schema定义
- 新增 `StixMetaObjectSchemaTest.java` - 单元测试
- 定义4种STIX元对象类型常量
- 定义1个嵌入式对象列表
- 实现类型判断方法

## Impact
- 新增文件: 2个 (1个主文件 + 1个测试文件)
- 预估代码量: ~150行
- 依赖: SchemaTypesDefinition, SchemaGeneral
- 影响模块: 后续attribute-definition模块会依赖此模块

## ADDED Requirements

### Requirement: STIX Meta Object Schema定义
The system SHALL provide STIX元对象的完整Schema定义。

#### Scenario: 类型常量定义
- **WHEN** 系统需要引用STIX元对象类型
- **THEN** 应提供ENTITY_TYPE_LABEL, ENTITY_TYPE_EXTERNAL_REFERENCE, ENTITY_TYPE_KILL_CHAIN_PHASE, ENTITY_TYPE_MARKING_DEFINITION常量

#### Scenario: 嵌入式对象列表
- **WHEN** 系统需要获取所有嵌入式STIX对象
- **THEN** 应提供STIX_EMBEDDED_OBJECT列表（包含Label, External-Reference, Kill-Chain-Phase）

#### Scenario: 元对象列表
- **WHEN** 系统需要获取所有STIX元对象
- **THEN** 应提供STIX_META_OBJECT列表（包含所有4种类型）

#### Scenario: 类型判断
- **WHEN** 系统需要判断一个类型是否为STIX元对象
- **THEN** isStixMetaObject(type)方法应返回正确的布尔值

## 源码参考
- 原文件: `opencti-platform/opencti-graphql/src/schema/stixMetaObject.ts`
- 重写文件: `opencti-java/src/main/java/io/opencti/schema/stix/StixMetaObjectSchema.java`
