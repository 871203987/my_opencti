# Schema STIX Domain Object 模块重写 Spec

## Why
Phase 3 Schema定义层的下一个任务是重写 `schema/stixDomainObject.ts` 文件。该文件定义了STIX域对象(SDO)的类型常量（约20+种实体类型），以及SDO类型判断方法、容器对象判断方法、位置对象判断方法等。这是构建完整STIX实体体系的核心模块，为上层业务提供统一的SDO类型判断接口。

## What Changes
- 创建 `StixDomainObjectSchema.java` 类
- 重写SDO属性常量（ATTRIBUTE_NAME, ATTRIBUTE_DESCRIPTION等）
- 重写SDO实体类型常量（ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_CAMPAIGN等20+种）
- 重写SDO分类列表（STIX_DOMAIN_OBJECT_CONTAINERS, STIX_DOMAIN_OBJECT_IDENTITIES等）
- 重写SDO类型判断方法（isStixDomainObject, isStixDomainObjectContainer等）
- 重写别名相关方法（isStixObjectAliased, resolveAliasesField）
- 重写组织限制列表（STIX_ORGANIZATIONS_UNRESTRICTED, STIX_ORGANIZATIONS_RESTRICTED）
- 保持与源码逻辑完全一致

## Impact
- 影响文件: `opencti-platform/opencti-graphql/src/schema/stixDomainObject.ts` (~201行)
- 目标文件: `opencti-java/src/main/java/io/opencti/schema/stix/StixDomainObjectSchema.java`
- 依赖模块:
  - `schema.general` (ABSTRACT_STIX_DOMAIN_OBJECT, ENTITY_TYPE_CONTAINER等常量)
  - `schema.internalObject` (ENTITY_TYPE_INTERNAL_FILE, ENTITY_TYPE_TAXII_COLLECTION等)
  - `schema.attribute-definition` (aliases, xOpenctiAliases)
  - `schema.SchemaTypesDefinition` (类型注册机制)
- 被依赖模块:
  - `schema.stixCoreObject` (isStixDomainObject方法)
  - `schema.stixCyberObservable` (SDO类型判断)
  - `domain`层各实体模块

## ADDED Requirements

### Requirement: SDO属性常量
系统 SHALL 提供以下SDO属性常量：

- ATTRIBUTE_NAME = "name"
- ATTRIBUTE_ABSTRACT = "attribute_abstract"
- ATTRIBUTE_EXPLANATION = "explanation"
- ATTRIBUTE_DESCRIPTION = "description"
- ATTRIBUTE_DESCRIPTION_OPENCTI = "x_opencti_description"
- ATTRIBUTE_ALIASES = "aliases"
- ATTRIBUTE_ALIASES_OPENCTI = "x_opencti_aliases"
- ATTRIBUTE_ADDITIONAL_NAMES = "x_opencti_additional_names"

### Requirement: SDO实体类型常量
系统 SHALL 提供以下SDO实体类型常量：

#### 攻击相关
- ENTITY_TYPE_ATTACK_PATTERN = "Attack-Pattern"
- ENTITY_TYPE_CAMPAIGN = "Campaign"
- ENTITY_TYPE_COURSE_OF_ACTION = "Course-Of-Action"
- ENTITY_TYPE_INFRASTRUCTURE = "Infrastructure"
- ENTITY_TYPE_INTRUSION_SET = "Intrusion-Set"
- ENTITY_TYPE_MALWARE = "Malware"
- ENTITY_TYPE_THREAT_ACTOR_GROUP = "Threat-Actor-Group"
- ENTITY_TYPE_TOOL = "Tool"
- ENTITY_TYPE_VULNERABILITY = "Vulnerability"
- ENTITY_TYPE_INCIDENT = "Incident"
- ENTITY_TYPE_DATA_COMPONENT = "Data-Component"
- ENTITY_TYPE_DATA_SOURCE = "Data-Source"

#### 容器相关
- ENTITY_TYPE_CONTAINER_NOTE = "Note"
- ENTITY_TYPE_CONTAINER_OBSERVED_DATA = "Observed-Data"
- ENTITY_TYPE_CONTAINER_OPINION = "Opinion"
- ENTITY_TYPE_CONTAINER_REPORT = "Report"

#### 身份相关
- ENTITY_TYPE_IDENTITY_INDIVIDUAL = "Individual"
- ENTITY_TYPE_IDENTITY_SECTOR = "Sector"
- ENTITY_TYPE_IDENTITY_SYSTEM = "System"

#### 位置相关
- ENTITY_TYPE_LOCATION_CITY = "City"
- ENTITY_TYPE_LOCATION_COUNTRY = "Country"
- ENTITY_TYPE_LOCATION_REGION = "Region"
- ENTITY_TYPE_LOCATION_POSITION = "Position"

#### 其他
- ENTITY_TYPE_RESOLVED_FILTERS = "Resolved-Filters"

### Requirement: SDO分类列表
系统 SHALL 提供以下SDO分类列表：

#### STIX_DOMAIN_OBJECT_CONTAINER_CASES
包含以下Case容器类型：
- ENTITY_TYPE_CONTAINER_CASE_INCIDENT
- ENTITY_TYPE_CONTAINER_CASE_RFI
- ENTITY_TYPE_CONTAINER_CASE_RFT

#### STIX_DOMAIN_OBJECT_CONTAINERS
包含所有容器类型（Cases + Note + Observed-Data + Opinion + Report + Grouping + Feedback + Task）

#### STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS
包含可共享容器类型：
- ENTITY_TYPE_CONTAINER_OBSERVED_DATA
- ENTITY_TYPE_CONTAINER_GROUPING
- ENTITY_TYPE_CONTAINER_REPORT
- 所有Case类型

#### STIX_DOMAIN_OBJECT_IDENTITIES
包含身份类型：
- ENTITY_TYPE_IDENTITY_INDIVIDUAL
- ENTITY_TYPE_IDENTITY_SECTOR
- ENTITY_TYPE_IDENTITY_SYSTEM

#### STIX_DOMAIN_OBJECT_LOCATIONS
包含位置类型：
- ENTITY_TYPE_LOCATION_CITY
- ENTITY_TYPE_LOCATION_COUNTRY
- ENTITY_TYPE_LOCATION_REGION
- ENTITY_TYPE_LOCATION_POSITION

#### STIX_DOMAIN_OBJECT_THREAT_ACTORS
包含威胁行为者类型：
- ENTITY_TYPE_THREAT_ACTOR_GROUP
- ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL

#### STIX_DOMAIN_OBJECTS
包含所有SDO类型（约20+种）

#### STIX_DOMAIN_OBJECT_ALIASED
包含支持别名的SDO类型：
- ENTITY_TYPE_COURSE_OF_ACTION
- ENTITY_TYPE_ATTACK_PATTERN
- ENTITY_TYPE_CAMPAIGN
- ENTITY_TYPE_INFRASTRUCTURE
- ENTITY_TYPE_INTRUSION_SET
- ENTITY_TYPE_MALWARE
- ENTITY_TYPE_THREAT_ACTOR_GROUP
- ENTITY_TYPE_TOOL
- ENTITY_TYPE_INCIDENT
- ENTITY_TYPE_VULNERABILITY

### Requirement: SDO类型判断方法
系统 SHALL 提供以下SDO类型判断方法：

#### Scenario: 判断是否为SDO
- **WHEN** 调用 isStixDomainObject(type)
- **THEN** 如果类型是SDO、身份、位置、容器、威胁行为者，或等于ABSTRACT_STIX_DOMAIN_OBJECT，返回true
- **AND** 否则返回false

#### Scenario: 判断是否为容器
- **WHEN** 调用 isStixDomainObjectContainer(type)
- **THEN** 如果类型在STIX_DOMAIN_OBJECT_CONTAINERS列表中，或等于ENTITY_TYPE_CONTAINER，返回true
- **AND** 否则返回false

#### Scenario: 判断是否为可共享容器
- **WHEN** 调用 isStixDomainObjectShareableContainer(type)
- **THEN** 如果类型在STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS列表中，返回true
- **AND** 否则返回false

#### Scenario: 判断是否为身份
- **WHEN** 调用 isStixDomainObjectIdentity(type)
- **THEN** 如果类型在STIX_DOMAIN_OBJECT_IDENTITIES列表中，或等于ENTITY_TYPE_IDENTITY，返回true
- **AND** 否则返回false

#### Scenario: 判断是否为位置
- **WHEN** 调用 isStixDomainObjectLocation(type)
- **THEN** 如果类型在STIX_DOMAIN_OBJECT_LOCATIONS列表中，或等于ENTITY_TYPE_LOCATION，返回true
- **AND** 否则返回false

#### Scenario: 判断是否为威胁行为者
- **WHEN** 调用 isStixDomainObjectThreatActor(type)
- **THEN** 如果类型在STIX_DOMAIN_OBJECT_THREAT_ACTORS列表中，或等于ENTITY_TYPE_THREAT_ACTOR，返回true
- **AND** 否则返回false

#### Scenario: 判断是否为Case
- **WHEN** 调用 isStixDomainObjectCase(type)
- **THEN** 如果类型在STIX_DOMAIN_OBJECT_CONTAINER_CASES列表中，或等于ENTITY_TYPE_CONTAINER_CASE，返回true
- **AND** 否则返回false

### Requirement: 别名相关方法
系统 SHALL 提供以下别名相关方法：

#### Scenario: 判断是否支持别名
- **WHEN** 调用 isStixObjectAliased(type)
- **THEN** 如果类型在STIX_DOMAIN_OBJECT_ALIASED列表中，或是身份/位置类型（除安全平台外），返回true
- **AND** 否则返回false

#### Scenario: 注册别名类型
- **WHEN** 调用 registerStixDomainAliased(type)
- **THEN** 将类型添加到STIX_DOMAIN_OBJECT_ALIASED列表中

#### Scenario: 解析别名字段
- **WHEN** 调用 resolveAliasesField(type)
- **THEN** 如果类型是Course-Of-Action、Vulnerability、Grouping、Identity或Location，返回xOpenctiAliases
- **AND** 否则返回aliases

### Requirement: 组织限制列表
系统 SHALL 提供以下组织限制列表：

#### STIX_ORGANIZATIONS_UNRESTRICTED
包含不受组织限制的类型列表（约10种抽象类型和实体类型）

#### STIX_ORGANIZATIONS_RESTRICTED
包含受组织限制的特殊类型列表（如DELETE_OPERATION）

### Requirement: 类型注册
系统 SHALL 在类加载时自动注册SDO类型：
- 调用 SchemaTypesDefinition.register(ENTITY_TYPE_CONTAINER, STIX_DOMAIN_OBJECT_CONTAINERS)
- 调用 SchemaTypesDefinition.register(ENTITY_TYPE_IDENTITY, STIX_DOMAIN_OBJECT_IDENTITIES)
- 调用 SchemaTypesDefinition.register(ENTITY_TYPE_LOCATION, STIX_DOMAIN_OBJECT_LOCATIONS)
- 调用 SchemaTypesDefinition.register(ENTITY_TYPE_THREAT_ACTOR, STIX_DOMAIN_OBJECT_THREAT_ACTORS)
- 调用 SchemaTypesDefinition.register(ABSTRACT_STIX_DOMAIN_OBJECT, STIX_DOMAIN_OBJECTS)

## Implementation Notes
1. 所有常量使用 `public static final String` 定义
2. 所有列表使用 `Collections.unmodifiableSet` 包装以提高查询效率
3. 类型判断方法使用 `public static` 定义
4. 使用静态代码块在类加载时自动注册类型
5. 类必须添加注释说明重写的原文件路径
6. 每个常量/方法必须添加注释说明对应的原代码
7. 依赖其他模块的常量（如ENTITY_TYPE_CONTAINER_CASE_INCIDENT等）需要先检查是否已定义
