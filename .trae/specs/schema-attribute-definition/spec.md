# Attribute Definition Schema 重写规范

## Why
属性定义(Attribute Definition)是OpenCTI中用于定义实体属性的核心Schema模块，包括属性类型、映射定义、各种实体属性（如ID、创建时间、修改时间、别名等）。这些属性定义用于数据验证、存储映射和UI展示，是构建完整实体模型的基础。

## What Changes
- 新增 `AttributeDefinitionSchema.java` - 属性定义Schema
- 新增 `AttributeDefinitionSchemaTest.java` - 单元测试
- 定义属性类型枚举（string, date, numeric, boolean, object, ref）
- 定义MandatoryType枚举（internal, external, customizable, no）
- 定义各种属性定义类（DateAttribute, BooleanAttribute, NumericAttribute等）
- 定义全局属性（id, internalId, standardId, createdAt, updatedAt等）
- 定义STIX域对象属性（aliases, confidence, revoked等）

## Impact
- 新增文件: 2个 (1个主文件 + 1个测试文件)
- 预估代码量: ~800-1000行
- 依赖: SchemaTypesDefinition, SchemaGeneral, InternalObjectSchema
- 影响模块: 后续所有实体定义模块都会依赖此模块

## ADDED Requirements

### Requirement: 属性定义Schema
The system SHALL provide完整的属性定义体系。

#### Scenario: 属性类型定义
- **WHEN** 系统需要定义实体属性
- **THEN** 应提供AttributeDefinition类型体系，支持string、date、numeric、boolean、object、ref等类型

#### Scenario: 全局属性定义
- **WHEN** 系统需要使用通用属性
- **THEN** 应提供id、internalId、standardId、createdAt、updatedAt等全局属性定义

#### Scenario: STIX属性定义
- **WHEN** 系统需要定义STIX实体属性
- **THEN** 应提供aliases、confidence、revoked、created、modified等STIX标准属性

#### Scenario: 类型守卫方法
- **WHEN** 系统需要判断属性类型
- **THEN** 应提供isNumericAttribute、isDateAttribute、isStringAttribute等类型守卫方法

## 源码参考
- 原文件: `opencti-platform/opencti-graphql/src/schema/attribute-definition.ts`
- 重写文件: `opencti-java/src/main/java/io/opencti/schema/attribute/AttributeDefinitionSchema.java`

## 子任务划分
由于原文件超过500行，按重写原则分为以下子任务：
1. T1: 基础类型定义（AttrType, MandatoryType枚举，基础接口）
2. T2: 属性定义类（各种Attribute类定义）
3. T3: 全局属性定义（id, internalId, standardId等）
4. T4: STIX属性定义（aliases, confidence, created等）
5. T5: 类型守卫方法
6. T6: 单元测试
