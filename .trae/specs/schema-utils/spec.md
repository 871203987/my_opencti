# Schema Utils 重写规范

## Why
Schema工具模块(schemaUtils.js)是OpenCTI中提供Schema相关工具函数的核心模块，包括ID验证、类型转换、父类型获取等功能。这些工具函数用于数据验证、类型转换和实体关系处理，是构建完整数据模型的基础工具。

## What Changes
- 新增 `SchemaUtils.java` - Schema工具类
- 新增 `SchemaUtilsTest.java` - 单元测试
- 实现ID验证方法（isStixId, isInternalId, isAnId）
- 实现哈希方法（shortHash）
- 实现日期验证方法（isValidDate）
- 实现类型转换方法（generateInternalType, convertStixToInternalTypes）
- 实现父类型获取方法（getParentTypes）
- 实现类型过滤方法（keepMostRestrictiveTypes）

## Impact
- 新增文件: 2个 (1个主文件 + 1个测试文件)
- 预估代码量: ~250行
- 依赖: SchemaGeneral, SchemaIdentifier, InternalObjectSchema, StixCoreObjectSchema, StixRelationshipSchema, StixDomainObjectSchema, StixMetaObjectSchema, StixCyberObservableSchema, StixCoreRelationshipSchema, StixRefRelationshipSchema, StixSightingRelationshipSchema
- 影响模块: 后续数据转换、验证模块会依赖此模块

## ADDED Requirements

### Requirement: Schema工具方法
The system SHALL provide完整的Schema工具函数集。

#### Scenario: ID验证
- **WHEN** 系统需要验证ID格式
- **THEN** 应提供isStixId, isInternalId, isAnId方法进行验证

#### Scenario: 哈希计算
- **WHEN** 系统需要计算短哈希
- **THEN** shortHash方法应返回8位十六进制哈希值

#### Scenario: 日期验证
- **WHEN** 系统需要验证日期字符串
- **THEN** isValidDate方法应验证ISO 8601格式日期

#### Scenario: 类型转换
- **WHEN** 系统需要将STIX类型转换为内部类型
- **THEN** generateInternalType方法应返回正确的内部类型

#### Scenario: 父类型获取
- **WHEN** 系统需要获取类型的所有父类型
- **THEN** getParentTypes方法应返回父类型列表

#### Scenario: 类型过滤
- **WHEN** 系统需要过滤掉冗余的父类型
- **THEN** keepMostRestrictiveTypes方法应返回最具体的类型列表

## 源码参考
- 原文件: `opencti-platform/opencti-graphql/src/schema/schemaUtils.js`
- 重写文件: `opencti-java/src/main/java/io/opencti/schema/utils/SchemaUtils.java`
