# Schema Validator 重写规范

## Why
Schema验证模块(schema-validator.ts)是OpenCTI中用于数据验证的核心模块，包括属性格式验证、必填属性验证、更新属性验证等功能。这些验证功能确保数据的完整性和一致性，是数据持久化前的关键检查环节。

## What Changes
- 新增 `SchemaValidator.java` - Schema验证器类
- 新增 `SchemaValidatorTest.java` - 单元测试
- 实现属性格式验证（字符串、布尔、日期、数值类型）
- 实现必填属性验证（创建和更新场景）
- 实现可更新属性验证
- 实现输入创建验证（综合验证）
- 实现输入更新验证（综合验证）

## Impact
- 新增文件: 2个 (1个主文件 + 1个测试文件)
- 预估代码量: ~300行
- 依赖: SchemaAttributesDefinition, SchemaRelationsRefDefinition, EntitySetting, ValidatorRegister, AccessUtils
- 影响模块: 后续数据创建、更新模块会依赖此模块

## ADDED Requirements

### Requirement: 属性格式验证
The system SHALL provide属性格式验证功能。

#### Scenario: 字符串属性验证
- **WHEN** 验证字符串属性
- **THEN** 应检查值是否为字符串，支持trim处理

#### Scenario: 布尔属性验证
- **WHEN** 验证布尔属性
- **THEN** 应检查值是否为布尔或字符串类型

#### Scenario: 日期属性验证
- **WHEN** 验证日期属性
- **THEN** 应检查值是否为有效的ISO 8601格式

#### Scenario: 数值属性验证
- **WHEN** 验证数值属性
- **THEN** 应检查值是否可转换为数字

### Requirement: 必填属性验证
The system SHALL provide必填属性验证功能。

#### Scenario: 创建时必填验证
- **WHEN** 创建实体时
- **THEN** 应验证所有必填属性已提供且非空

#### Scenario: 更新时必填验证
- **WHEN** 更新实体时
- **THEN** 应验证已提供的必填属性非空

#### Scenario: 外部引用强制验证
- **WHEN** 实体设置要求强制外部引用且用户无绕过权限
- **THEN** 应验证至少提供一个外部引用

### Requirement: 可更新属性验证
The system SHALL provide可更新属性验证功能。

#### Scenario: 更新属性检查
- **WHEN** 更新实体属性时
- **THEN** 应验证属性是否允许更新（update !== false）

### Requirement: 综合输入验证
The system SHALL provide综合输入验证功能。

#### Scenario: 创建输入验证
- **WHEN** 创建实体时
- **THEN** 应执行格式验证、必填验证和功能验证

#### Scenario: 更新输入验证
- **WHEN** 更新实体时
- **THEN** 应执行格式验证、必填验证、可更新验证和功能验证

## 源码参考
- 原文件: `opencti-platform/opencti-graphql/src/schema/schema-validator.ts`
- 重写文件: `opencti-java/src/main/java/io/opencti/schema/validator/SchemaValidator.java`
