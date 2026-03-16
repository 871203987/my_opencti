# Schema STIX Core Relationship 模块重写 Spec

## Why
Phase 3 Schema定义层的第六个任务是重写 `schema/stixCoreRelationship.ts` 文件。该文件定义了STIX核心关系的61种关系类型常量，包括标准STIX关系和OpenCTI扩展关系，以及关系类型判断方法。这是构建完整STIX关系体系的基础模块。

## What Changes
- 创建 `StixCoreRelationshipSchema.java` 类
- 重写61种STIX核心关系类型常量（标准STIX + OpenCTI扩展 + MITRE扩展）
- 重写STIX核心关系类型列表（STIX_CORE_RELATIONSHIPS）
- 重写关系类型判断方法（isStixCoreRelationship）
- 重写STIX核心关系选项（stixCoreRelationshipOptions）
- 保持与源码逻辑完全一致

## Impact
- 影响文件: `opencti-platform/opencti-graphql/src/schema/stixCoreRelationship.ts` (~136行)
- 目标文件: `opencti-java/src/main/java/io/opencti/schema/stix/StixCoreRelationshipSchema.java`
- 依赖模块:
  - `schema.general` (ABSTRACT_STIX_CORE_RELATIONSHIP常量)
  - `schema.schema-types` (schemaTypesDefinition注册机制)
- 被依赖模块:
  - `schema.stixRelationship` (isStixCoreRelationship方法)
  - `database.stix` (StixCoreRelationshipsMapping中的关系映射)

## ADDED Requirements

### Requirement: STIX核心关系类型常量
系统 SHALL 提供以下61种STIX核心关系类型常量：

#### 标准STIX关系（36种）
- RELATION_DELIVERS = "delivers"
- RELATION_TARGETS = "targets"
- RELATION_USES = "uses"
- RELATION_ATTRIBUTED_TO = "attributed-to"
- RELATION_COMPROMISES = "compromises"
- RELATION_ORIGINATES_FROM = "originates-from"
- RELATION_INVESTIGATES = "investigates"
- RELATION_MITIGATES = "mitigates"
- RELATION_LOCATED_AT = "located-at"
- RELATION_INDICATES = "indicates"
- RELATION_BASED_ON = "based-on"
- RELATION_COMMUNICATES_WITH = "communicates-with"
- RELATION_CONSISTS_OF = "consists-of"
- RELATION_CONTROLS = "controls"
- RELATION_HAS = "has"
- RELATION_HOSTS = "hosts"
- RELATION_OWNS = "owns"
- RELATION_AUTHORED_BY = "authored-by"
- RELATION_BEACONS_TO = "beacons-to"
- RELATION_EXFILTRATES_TO = "exfiltrates-to"
- RELATION_DOWNLOADS = "downloads"
- RELATION_DROPS = "drops"
- RELATION_EXPLOITS = "exploits"
- RELATION_VARIANT_OF = "variant-of"
- RELATION_CHARACTERIZES = "characterizes"
- RELATION_ANALYSIS_OF = "analysis-of"
- RELATION_STATIC_ANALYSIS_OF = "static-analysis-of"
- RELATION_DYNAMIC_ANALYSIS_OF = "dynamic-analysis-of"
- RELATION_IMPERSONATES = "impersonates"
- RELATION_REMEDIATES = "remediates"
- RELATION_RELATED_TO = "related-to"
- RELATION_DERIVED_FROM = "derived-from"
- RELATION_DUPLICATE_OF = "duplicate-of"
- RELATION_BELONGS_TO = "belongs-to"
- RELATION_RESOLVES_TO = "resolves-to"
- RELATION_TECHNOLOGY = "technology"
- RELATION_TECHNOLOGY_TO = "technology-to"
- RELATION_TECHNOLOGY_FROM = "technology-from"
- RELATION_TRANSFERRED_TO = "transferred-to"
- RELATION_DEMONSTRATES = "demonstrates"

#### OpenCTI扩展关系（18种）
- RELATION_PART_OF = "part-of"
- RELATION_COOPERATES_WITH = "cooperates-with"
- RELATION_PARTICIPATES_IN = "participates-in"
- RELATION_PUBLISHES = "publishes"
- RELATION_AMPLIFIES = "amplifies"
- RELATION_SUBNARRATIVE_OF = "subnarrative-of"
- RELATION_EMPLOYED_BY = "employed-by"
- RELATION_RESIDES_IN = "resides-in"
- RELATION_CITIZEN_OF = "citizen-of"
- RELATION_NATIONAL_OF = "national-of"
- RELATION_KNOWN_AS = "known-as"
- RELATION_REPORTS_TO = "reports-to"
- RELATION_SUPPORTS = "supports"
- RELATION_SHOULD_COVER = "should-cover"
- RELATION_HAS_COVERED = "has-covered"

#### MITRE扩展关系（3种）
- RELATION_SUBTECHNIQUE_OF = "subtechnique-of"
- RELATION_REVOKED_BY = "revoked-by"
- RELATION_DETECTS = "detects"

### Requirement: STIX核心关系类型列表
系统 SHALL 提供STIX_CORE_RELATIONSHIPS列表，包含上述61种关系类型的完整集合。

### Requirement: 关系类型判断方法
系统 SHALL 提供以下类型判断方法：

#### Scenario: 判断是否为STIX核心关系
- **WHEN** 调用 isStixCoreRelationship(type)
- **THEN** 如果类型在STIX_CORE_RELATIONSHIPS列表中，或等于ABSTRACT_STIX_CORE_RELATIONSHIP，返回true
- **AND** 否则返回false

### Requirement: STIX核心关系选项
系统 SHALL 提供以下STIX核心关系选项：

#### StixCoreRelationshipsOrdering
- 当前为空对象（与源码保持一致）

## Implementation Notes
1. 所有关系类型常量使用 `public static final String` 定义
2. STIX_CORE_RELATIONSHIPS列表使用 `Collections.unmodifiableList` 包装
3. 类型判断方法使用 `public static` 定义
4. 类必须添加注释说明重写的原文件路径
5. 每个常量/方法必须添加注释说明对应的原代码
6. 需要注册到schemaTypesDefinition（如果Java层需要类似机制）
