# Schema General 模块重写 Spec

## Why
Phase 3 Schema定义层的第一个任务是重写 `schema/general.js` 文件。该文件定义了OpenCTI系统中使用的基础常量、类型标识和工具函数，是整个Schema层的基础，其他所有Schema模块都依赖这些定义。

## What Changes
- 创建 `io.opencti.schema.general` 包
- 重写所有常量定义（STIX类型、ID类型、输入字段名等）
- 重写工具方法（isAbstract, isKnowledge等）
- 保持与源码逻辑完全一致

## Impact
- 影响文件: `opencti-platform/opencti-graphql/src/schema/general.js` (~134行)
- 目标文件: `opencti-java/src/main/java/io/opencti/schema/general/SchemaGeneral.java`
- 依赖模块: 所有后续Schema模块、类型定义模块

## ADDED Requirements

### Requirement: 常量定义
系统 SHALL 提供以下常量定义：

#### STIX类型常量
- STIX_TYPE_RELATION = "relationship"
- STIX_TYPE_SIGHTING = "sighting"

#### 权限常量
- KNOWLEDGE_UPDATE = "KNUPDATE"
- KNOWLEDGE_FRONTEND_EXPORT = "KNFRONTENDEXPORT"
- KNOWLEDGE_COLLABORATION = "KNPARTICIPATE"

#### ID类型常量
- ID_INTERNAL = "internal_id"
- ID_INFERRED = "inferred_id"
- ID_STANDARD = "standard_id"
- INTERNAL_IDS_ALIASES = "i_aliases_ids"
- IDS_STIX = "x_opencti_stix_ids"
- BASE_TYPE_RELATION = "RELATION"
- BASE_TYPE_ENTITY = "ENTITY"

#### 输入字段常量
- INPUT_GRANTED_REFS = "objectOrganization"
- INPUT_EXTERNAL_REFS = "externalReferences"
- INPUT_WORKS = "works"
- INPUT_INTERNAL_FILES = "internalFiles"
- INPUT_KILLCHAIN = "killChainPhases"
- INPUT_CREATED_BY = "createdBy"
- INPUT_LABELS = "objectLabel"
- INPUT_MARKINGS = "objectMarking"
- INPUT_ASSIGNEE = "objectAssignee"
- INPUT_PARTICIPANT = "objectParticipant"
- INPUT_OBJECTS = "objects"
- INPUT_DOMAIN_FROM = "from"
- INPUT_DOMAIN_TO = "to"
- INPUT_BORN_IN = "bornIn"
- INPUT_ETHNICITY = "ethnicity"
- INPUT_AUTHORIZED_MEMBERS = "restricted_members"
- INPUT_IN_PIR = "inPir"

#### 前缀常量
- REL_INDEX_PREFIX = "rel_"
- INTERNAL_PREFIX = "i_"
- RULE_PREFIX = "i_rule_"

#### 连接器类型常量
- CONNECTOR_INTERNAL_ENRICHMENT = "INTERNAL_ENRICHMENT"
- CONNECTOR_INTERNAL_IMPORT_FILE = "INTERNAL_IMPORT_FILE"
- CONNECTOR_INTERNAL_ANALYSIS = "INTERNAL_ANALYSIS"
- CONNECTOR_INTERNAL_EXPORT_FILE = "INTERNAL_EXPORT_FILE"
- CONNECTOR_INTERNAL_NOTIFICATION = "INTERNAL_NOTIFICATION"
- CONNECTOR_INTERNAL_INGESTION = "INTERNAL_INGESTION"

#### UUID常量
- OASIS_NAMESPACE = "00abedb4-aa42-466c-9c01-fed23315a9b7"
- OPENCTI_NAMESPACE = "b639ff3b-00eb-42ed-aa36-a8dd6f8fb4cf"
- OPENCTI_PLATFORM_UUID = "d06053cb-7123-404b-b092-6606411702d2"
- OPENCTI_ADMIN_UUID = "88ec0c6a-13ce-5e39-b486-354fe4a7084f"
- OPENCTI_SYSTEM_UUID = "6a4b11e1-90ca-4e42-ba42-db7bc7f7d505"

#### 关系抽象类型常量
- ABSTRACT_BASIC_RELATIONSHIP = "basic-relationship"
- ABSTRACT_INTERNAL_RELATIONSHIP = "internal-relationship"
- ABSTRACT_STIX_RELATIONSHIP = "stix-relationship"
- ABSTRACT_STIX_CORE_RELATIONSHIP = "stix-core-relationship"
- ABSTRACT_STIX_CYBER_OBSERVABLE_RELATIONSHIP = "stix-cyber-observable-relationship"
- ABSTRACT_STIX_REF_RELATIONSHIP = "stix-ref-relationship"
- ABSTRACT_STIX_META_RELATIONSHIP = "stix-meta-relationship"

#### 实体抽象类型常量
- ABSTRACT_BASIC_OBJECT = "Basic-Object"
- ABSTRACT_STIX_OBJECT = "Stix-Object"
- ABSTRACT_STIX_META_OBJECT = "Stix-Meta-Object"
- ABSTRACT_STIX_CORE_OBJECT = "Stix-Core-Object"
- ABSTRACT_STIX_DOMAIN_OBJECT = "Stix-Domain-Object"
- ABSTRACT_STIX_CYBER_OBSERVABLE = "Stix-Cyber-Observable"
- ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE = "Hashed-Observable"
- ABSTRACT_INTERNAL_OBJECT = "Internal-Object"

#### 抽象类型常量
- ENTITY_TYPE_CONTAINER = "Container"
- ENTITY_TYPE_IDENTITY = "Identity"
- ENTITY_TYPE_LOCATION = "Location"
- ENTITY_TYPE_THREAT_ACTOR = "Threat-Actor"

### Requirement: 工具方法
系统 SHALL 提供以下工具方法：

#### Scenario: 构建引用关系键
- **WHEN** 调用 buildRefRelationKey(type, field)
- **THEN** 返回格式为 "rel_{type}.{field}" 的字符串
- **AND** field 默认为 "internal_id"

#### Scenario: 构建引用关系搜索键
- **WHEN** 调用 buildRefRelationSearchKey(type, field)
- **THEN** 返回格式为 "rel_{type}.{field}.keyword" 的字符串
- **AND** field 默认为 "internal_id"

#### Scenario: 判断是否为抽象类型
- **WHEN** 调用 isAbstract(type)
- **THEN** 如果 type 在 ABSTRACT_TYPES 列表中返回 true
- **AND** 否则返回 false

#### Scenario: 判断是否为知识类型
- **WHEN** 调用 isKnowledge(type)
- **THEN** 如果 type 在 KNOWLEDGE_TYPES 列表中返回 true
- **AND** 否则返回 false

## Implementation Notes
1. 所有常量使用 `public static final String` 定义
2. 工具方法使用 `public static` 定义
3. 类必须添加注释说明重写的原文件路径
4. 每个常量/方法必须添加注释说明对应的原代码
5. ABSTRACT_TYPES 和 KNOWLEDGE_TYPES 使用 Set 存储以提高查询效率
