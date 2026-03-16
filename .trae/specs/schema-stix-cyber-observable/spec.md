# STIX Cyber Observable Schema 重写规范

## Why
STIX网络可观测对象(StixCyberObservable)是OpenCTI中用于表示网络安全相关可观测数据的核心Schema模块，包括IP地址、域名、文件、进程、网络流量等37种类型。这些可观测对象是威胁情报分析的基础数据，用于标识和追踪网络中的实体。

## What Changes
- 新增 `StixCyberObservableSchema.java` - STIX网络可观测对象Schema定义
- 新增 `StixCyberObservableSchemaTest.java` - 单元测试
- 定义37种STIX网络可观测对象类型常量
- 定义2个分类列表（哈希可观测对象、所有可观测对象）
- 实现2个类型判断方法

## Impact
- 新增文件: 2个 (1个主文件 + 1个测试文件)
- 预估代码量: ~250行
- 依赖: SchemaTypesDefinition, SchemaGeneral
- 影响模块: 后续STIX转换器、数据中间件等模块会依赖此模块

## ADDED Requirements

### Requirement: STIX Cyber Observable Schema定义
The system SHALL provide STIX网络可观测对象的完整Schema定义。

#### Scenario: 类型常量定义
- **WHEN** 系统需要引用STIX网络可观测对象类型
- **THEN** 应提供37种类型常量，包括标准STIX类型和OpenCTI自定义类型

#### Scenario: 哈希可观测对象列表
- **WHEN** 系统需要获取所有支持哈希值的可观测对象
- **THEN** 应提供STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES列表（Artifact, StixFile, X509-Certificate）

#### Scenario: 所有可观测对象列表
- **WHEN** 系统需要获取所有网络可观测对象类型
- **THEN** 应提供STIX_CYBER_OBSERVABLES列表（包含37种类型）

#### Scenario: 类型判断
- **WHEN** 系统需要判断一个类型是否为STIX网络可观测对象
- **THEN** isStixCyberObservable(type)方法应返回正确的布尔值

#### Scenario: 哈希可观测对象判断
- **WHEN** 系统需要判断一个类型是否支持哈希值
- **THEN** isStixCyberObservableHashedObservable(type)方法应返回正确的布尔值

## 源码参考
- 原文件: `opencti-platform/opencti-graphql/src/schema/stixCyberObservable.ts`
- 重写文件: `opencti-java/src/main/java/io/opencti/schema/stix/StixCyberObservableSchema.java`
