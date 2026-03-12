# Checklist - Schema General 模块重写

## 代码实现检查

- [x] SchemaGeneral.java 文件已创建在正确的包路径下
- [x] 类注释包含原文件路径: `opencti-platform/opencti-graphql/src/schema/general.js`
- [x] 所有常量使用 `public static final String` 定义
- [x] 常量注释与原代码一一对应

### 常量定义检查

- [x] STIX 类型常量 (2个)
  - [x] STIX_TYPE_RELATION = "relationship"
  - [x] STIX_TYPE_SIGHTING = "sighting"

- [x] 权限常量 (3个)
  - [x] KNOWLEDGE_UPDATE = "KNUPDATE"
  - [x] KNOWLEDGE_FRONTEND_EXPORT = "KNFRONTENDEXPORT"
  - [x] KNOWLEDGE_COLLABORATION = "KNPARTICIPATE"

- [x] ID 类型常量 (7个)
  - [x] ID_INTERNAL = "internal_id"
  - [x] ID_INFERRED = "inferred_id"
  - [x] ID_STANDARD = "standard_id"
  - [x] INTERNAL_IDS_ALIASES = "i_aliases_ids"
  - [x] IDS_STIX = "x_opencti_stix_ids"
  - [x] BASE_TYPE_RELATION = "RELATION"
  - [x] BASE_TYPE_ENTITY = "ENTITY"

- [x] 输入字段常量 (17个)
  - [x] INPUT_GRANTED_REFS = "objectOrganization"
  - [x] INPUT_EXTERNAL_REFS = "externalReferences"
  - [x] INPUT_WORKS = "works"
  - [x] INPUT_INTERNAL_FILES = "internalFiles"
  - [x] INPUT_KILLCHAIN = "killChainPhases"
  - [x] INPUT_CREATED_BY = "createdBy"
  - [x] INPUT_LABELS = "objectLabel"
  - [x] INPUT_MARKINGS = "objectMarking"
  - [x] INPUT_ASSIGNEE = "objectAssignee"
  - [x] INPUT_PARTICIPANT = "objectParticipant"
  - [x] INPUT_OBJECTS = "objects"
  - [x] INPUT_DOMAIN_FROM = "from"
  - [x] INPUT_DOMAIN_TO = "to"
  - [x] INPUT_BORN_IN = "bornIn"
  - [x] INPUT_ETHNICITY = "ethnicity"
  - [x] INPUT_AUTHORIZED_MEMBERS = "restricted_members"
  - [x] INPUT_IN_PIR = "inPir"

- [x] 前缀常量 (3个)
  - [x] REL_INDEX_PREFIX = "rel_"
  - [x] INTERNAL_PREFIX = "i_"
  - [x] RULE_PREFIX = "i_rule_"

- [x] 连接器类型常量 (6个)
  - [x] CONNECTOR_INTERNAL_ENRICHMENT = "INTERNAL_ENRICHMENT"
  - [x] CONNECTOR_INTERNAL_IMPORT_FILE = "INTERNAL_IMPORT_FILE"
  - [x] CONNECTOR_INTERNAL_ANALYSIS = "INTERNAL_ANALYSIS"
  - [x] CONNECTOR_INTERNAL_EXPORT_FILE = "INTERNAL_EXPORT_FILE"
  - [x] CONNECTOR_INTERNAL_NOTIFICATION = "INTERNAL_NOTIFICATION"
  - [x] CONNECTOR_INTERNAL_INGESTION = "INTERNAL_INGESTION"

- [x] UUID 常量 (5个)
  - [x] OASIS_NAMESPACE = "00abedb4-aa42-466c-9c01-fed23315a9b7"
  - [x] OPENCTI_NAMESPACE = "b639ff3b-00eb-42ed-aa36-a8dd6f8fb4cf"
  - [x] OPENCTI_PLATFORM_UUID = "d06053cb-7123-404b-b092-6606411702d2"
  - [x] OPENCTI_ADMIN_UUID = "88ec0c6a-13ce-5e39-b486-354fe4a7084f"
  - [x] OPENCTI_SYSTEM_UUID = "6a4b11e1-90ca-4e42-ba42-db7bc7f7d505"

- [x] 关系抽象类型常量 (7个)
  - [x] ABSTRACT_BASIC_RELATIONSHIP = "basic-relationship"
  - [x] ABSTRACT_INTERNAL_RELATIONSHIP = "internal-relationship"
  - [x] ABSTRACT_STIX_RELATIONSHIP = "stix-relationship"
  - [x] ABSTRACT_STIX_CORE_RELATIONSHIP = "stix-core-relationship"
  - [x] ABSTRACT_STIX_CYBER_OBSERVABLE_RELATIONSHIP = "stix-cyber-observable-relationship"
  - [x] ABSTRACT_STIX_REF_RELATIONSHIP = "stix-ref-relationship"
  - [x] ABSTRACT_STIX_META_RELATIONSHIP = "stix-meta-relationship"

- [x] 实体抽象类型常量 (8个)
  - [x] ABSTRACT_BASIC_OBJECT = "Basic-Object"
  - [x] ABSTRACT_STIX_OBJECT = "Stix-Object"
  - [x] ABSTRACT_STIX_META_OBJECT = "Stix-Meta-Object"
  - [x] ABSTRACT_STIX_CORE_OBJECT = "Stix-Core-Object"
  - [x] ABSTRACT_STIX_DOMAIN_OBJECT = "Stix-Domain-Object"
  - [x] ABSTRACT_STIX_CYBER_OBSERVABLE = "Stix-Cyber-Observable"
  - [x] ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE = "Hashed-Observable"
  - [x] ABSTRACT_INTERNAL_OBJECT = "Internal-Object"

- [x] 抽象类型常量 (4个)
  - [x] ENTITY_TYPE_CONTAINER = "Container"
  - [x] ENTITY_TYPE_IDENTITY = "Identity"
  - [x] ENTITY_TYPE_LOCATION = "Location"
  - [x] ENTITY_TYPE_THREAT_ACTOR = "Threat-Actor"

### 工具方法检查

- [x] buildRefRelationKey(type, field) 方法
  - [x] 返回格式正确: "rel_{type}.{field}"
  - [x] field 默认为 ID_INTERNAL ("internal_id")

- [x] buildRefRelationSearchKey(type, field) 方法
  - [x] 返回格式正确: "rel_{type}.{field}.keyword"
  - [x] field 默认为 ID_INTERNAL

- [x] isAbstract(type) 方法
  - [x] 使用 Set 存储 ABSTRACT_TYPES 以提高性能
  - [x] 正确判断类型是否在列表中

- [x] isKnowledge(type) 方法
  - [x] 使用 Set 存储 KNOWLEDGE_TYPES 以提高性能
  - [x] 正确判断类型是否在列表中

## 编译检查

- [x] SchemaGeneral.java 单独编译成功
- [x] 无编译错误
- [ ] Maven 整体编译通过 (待其他文件修复)

## 测试检查

- [x] SchemaGeneralTest.java 测试类已创建
- [x] 常量值测试通过
- [x] 工具方法测试通过
- [ ] 所有测试用例通过 Maven 运行 (待其他文件修复)

## 总结

- **已完成**: SchemaGeneral.java (337行代码，62个常量，4个工具方法)
- **已完成**: SchemaGeneralTest.java (201行代码，16个测试方法)
- **待解决**: 项目中其他文件存在 BOM 字符问题，导致整体编译失败
