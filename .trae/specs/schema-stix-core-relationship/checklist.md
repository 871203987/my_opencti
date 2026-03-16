# Checklist - Schema STIX Core Relationship 模块重写

## 代码实现检查项

- [x] StixCoreRelationshipSchema.java 文件已创建
- [x] 类注释包含原文件路径: opencti-platform/opencti-graphql/src/schema/stixCoreRelationship.ts
- [x] 标准STIX关系类型常量已定义（40种）
  - [x] RELATION_DELIVERS
  - [x] RELATION_TARGETS
  - [x] RELATION_USES
  - [x] RELATION_ATTRIBUTED_TO
  - [x] RELATION_COMPROMISES
  - [x] RELATION_ORIGINATES_FROM
  - [x] RELATION_INVESTIGATES
  - [x] RELATION_MITIGATES
  - [x] RELATION_LOCATED_AT
  - [x] RELATION_INDICATES
  - [x] RELATION_BASED_ON
  - [x] RELATION_COMMUNICATES_WITH
  - [x] RELATION_CONSISTS_OF
  - [x] RELATION_CONTROLS
  - [x] RELATION_HAS
  - [x] RELATION_HOSTS
  - [x] RELATION_OWNS
  - [x] RELATION_AUTHORED_BY
  - [x] RELATION_BEACONS_TO
  - [x] RELATION_EXFILTRATES_TO
  - [x] RELATION_DOWNLOADS
  - [x] RELATION_DROPS
  - [x] RELATION_EXPLOITS
  - [x] RELATION_VARIANT_OF
  - [x] RELATION_CHARACTERIZES
  - [x] RELATION_ANALYSIS_OF
  - [x] RELATION_STATIC_ANALYSIS_OF
  - [x] RELATION_DYNAMIC_ANALYSIS_OF
  - [x] RELATION_IMPERSONATES
  - [x] RELATION_REMEDIATES
  - [x] RELATION_RELATED_TO
  - [x] RELATION_DERIVED_FROM
  - [x] RELATION_DUPLICATE_OF
  - [x] RELATION_BELONGS_TO
  - [x] RELATION_RESOLVES_TO
  - [x] RELATION_TECHNOLOGY
  - [x] RELATION_TECHNOLOGY_TO
  - [x] RELATION_TECHNOLOGY_FROM
  - [x] RELATION_TRANSFERRED_TO
  - [x] RELATION_DEMONSTRATES
- [x] OpenCTI扩展关系类型常量已定义（15种）
  - [x] RELATION_PART_OF
  - [x] RELATION_COOPERATES_WITH
  - [x] RELATION_PARTICIPATES_IN
  - [x] RELATION_PUBLISHES
  - [x] RELATION_AMPLIFIES
  - [x] RELATION_SUBNARRATIVE_OF
  - [x] RELATION_EMPLOYED_BY
  - [x] RELATION_RESIDES_IN
  - [x] RELATION_CITIZEN_OF
  - [x] RELATION_NATIONAL_OF
  - [x] RELATION_KNOWN_AS
  - [x] RELATION_REPORTS_TO
  - [x] RELATION_SUPPORTS
  - [x] RELATION_SHOULD_COVER
  - [x] RELATION_HAS_COVERED
- [x] MITRE扩展关系类型常量已定义（3种）
  - [x] RELATION_SUBTECHNIQUE_OF
  - [x] RELATION_REVOKED_BY
  - [x] RELATION_DETECTS
- [x] STIX_CORE_RELATIONSHIPS列表已定义（包含全部58种关系）
- [x] isStixCoreRelationship方法已实现
- [x] stixCoreRelationshipOptions选项已定义
- [x] 代码通过编译检查

## 单元测试检查项

- [x] StixCoreRelationshipSchemaTest.java 文件已创建
- [x] 测试类包含58种关系类型常量的值验证
- [x] 测试STIX_CORE_RELATIONSHIPS列表包含所有关系
- [x] 测试isStixCoreRelationship方法正确性
  - [x] 测试列表中的关系类型返回true
  - [x] 测试ABSTRACT_STIX_CORE_RELATIONSHIP返回true
  - [x] 测试不在列表中的类型返回false
- [x] 所有测试用例通过（15个测试）

## 文档检查项

- [x] 类注释包含原文件路径
- [x] 每个常量包含原代码注释
- [x] 每个方法包含原代码注释
- [x] 代码符合Java编码规范
