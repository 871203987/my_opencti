# Schema STIX Relationship 模块重写 Spec

## Why
Phase 3 Schema定义层的下一个任务是重写 `schema/stixRelationship.ts` 文件。该文件定义了STIX关系的组合类型判断方法，包括核心关系、目击关系、引用关系的组合判断逻辑。这是构建完整STIX关系体系的重要组成部分，为上层业务提供统一的关系类型判断接口。

## What Changes
- 创建 `StixRelationshipSchema.java` 类
- 重写 `isStixRelationshipExceptRef(type)` 方法 - 判断是否为核心关系或目击关系（不含引用关系）
- 重写 `isStixRelationship(type)` 方法 - 判断是否为STIX关系（含引用关系）
- 重写 `isBasicRelationship(type)` 方法 - 判断是否为基本关系
- 重写 `isStixRelation(sco)` 方法 - 类型守卫函数判断是否为StixRelation
- 保持与源码逻辑完全一致

## Impact
- 影响文件: `opencti-platform/opencti-graphql/src/schema/stixRelationship.ts` (~20行)
- 目标文件: `opencti-java/src/main/java/io/opencti/schema/stix/StixRelationshipSchema.java`
- 依赖模块:
  - `schema.stixCoreRelationship` (isStixCoreRelationship方法)
  - `schema.stixSightingRelationship` (isStixSightingRelationship方法)
  - `schema.stixRefRelationship` (isStixRefRelationship方法)
  - `schema.internalRelationship` (isInternalRelationship方法)
  - `schema.general` (ABSTRACT_STIX_RELATIONSHIP, ABSTRACT_BASIC_RELATIONSHIP常量)
- 被依赖模块:
  - `domain.stixCoreRelationship` (关系领域操作)
  - `database.middleware` (数据中间件关系处理)

## ADDED Requirements

### Requirement: STIX关系类型判断方法
系统 SHALL 提供以下STIX关系类型判断方法：

#### Scenario: 判断是否为核心关系或目击关系（不含引用关系）
- **WHEN** 调用 `isStixRelationshipExceptRef(type)`
- **THEN** 如果类型是STIX核心关系或STIX目击关系，返回true
- **AND** 否则返回false

#### Scenario: 判断是否为STIX关系（含引用关系）
- **WHEN** 调用 `isStixRelationship(type)`
- **THEN** 如果类型是STIX核心关系、STIX目击关系、STIX引用关系，或等于ABSTRACT_STIX_RELATIONSHIP，返回true
- **AND** 否则返回false

#### Scenario: 判断是否为基本关系
- **WHEN** 调用 `isBasicRelationship(type)`
- **THEN** 如果类型是内部关系、STIX关系，或等于ABSTRACT_BASIC_RELATIONSHIP，返回true
- **AND** 否则返回false

#### Scenario: 判断是否为StixRelation对象
- **WHEN** 调用 `isStixRelation(sco)`
- **THEN** 如果对象包含relationship_type属性，返回true
- **AND** 否则返回false

## Implementation Notes
1. 所有方法使用 `public static` 定义
2. 方法参数类型为 `String`（类型判断）或 `StixCoreObject`（对象判断）
3. 返回值类型为 `boolean`
4. 类必须添加注释说明重写的原文件路径
5. 每个方法必须添加注释说明对应的原代码
6. 依赖的其他schema方法通过静态导入或类引用方式调用
