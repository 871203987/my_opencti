# Schema Identifier 模块重写 Spec

## Why
Phase 3 Schema定义层的第二个任务是重写 `schema/identifier.js` 文件。该文件负责OpenCTI系统中所有实体的ID生成策略，包括Standard ID（基于UUID v5）、Internal ID（基于UUID v4）、以及各种实体类型的ID贡献字段定义。这是整个系统的核心基础模块，所有实体创建都依赖此模块生成唯一标识符。

## What Changes
- 创建 `io.opencti.schema.identifier` 包
- 重写所有ID生成相关常量（哈希算法、字段名、TLP标记等）
- 重写ID生成策略定义（50+种实体类型的ID贡献字段）
- 重写ID生成工具方法（idGen, generateStandardId, generateInternalId等）
- 重写哈希可观测对象的ID生成逻辑
- 保持与源码逻辑完全一致

## Impact
- 影响文件: `opencti-platform/opencti-graphql/src/schema/identifier.js` (~579行)
- 目标文件: `opencti-java/src/main/java/io/opencti/schema/identifier/SchemaIdentifier.java`
- 依赖模块: 
  - `schema.general.SchemaGeneral` (命名空间常量)
  - `schema.internalObject` (内部对象类型判断)
  - `schema.stixDomainObject` (STIX域对象类型判断)
  - `schema.stixMetaObject` (STIX元对象类型判断)
  - `schema.stixCyberObservable` (STIX可观测对象类型判断)
  - `schema.stixRelationship` (关系类型判断)
  - `schema.stixCoreRelationship` (核心关系类型判断)
  - `schema.stixSightingRelationship` (目击关系类型判断)
  - `schema.stixRefRelationship` (引用关系类型判断)
  - `database.stix.StixConverter` (类型转换)

## ADDED Requirements

### Requirement: 常量定义
系统 SHALL 提供以下常量定义：

#### 哈希算法常量
- MD5 = "MD5"
- SHA_1 = "SHA-1"
- SHA_256 = "SHA-256"
- SHA_512 = "SHA-512"
- SHA3_256 = "SHA3-256"
- SHA3_512 = "SHA3-512"
- SSDEEP = "SSDEEP"

#### 字段名常量
- INTERNAL_FROM_FIELD = "i_relations_from"
- INTERNAL_TO_FIELD = "i_relations_to"
- NAME_FIELD = "name"
- INNER_TYPE = "opencti_type"
- VALUE_FIELD = "value"
- FIRST_SEEN = "first_seen"
- LAST_SEEN = "last_seen"
- START_TIME = "start_time"
- STOP_TIME = "stop_time"
- VALID_FROM = "valid_from"
- FIRST_OBSERVED = "first_observed"
- LAST_OBSERVED = "last_observed"
- VALID_UNTIL = "valid_until"
- REVOKED = "revoked"
- X_MITRE_ID_FIELD = "x_mitre_id"
- X_DETECTION = "x_opencti_detection"
- X_WORKFLOW_ID = "x_opencti_workflow_id"
- X_SCORE = "x_opencti_score"

#### TLP标记定义常量
- MARKING_TLP_CLEAR_ID = "613f2e26-407d-48c7-9eca-b8e91df99dc9"
- MARKING_TLP_CLEAR = "marking-definition--613f2e26-407d-48c7-9eca-b8e91df99dc9"
- MARKING_TLP_GREEN_ID = "34098fce-860f-48ae-8e50-ebd3cc5e41da"
- MARKING_TLP_GREEN = "marking-definition--34098fce-860f-48ae-8e50-ebd3cc5e41da"
- MARKING_TLP_AMBER_ID = "f88d31f6-486f-44da-b317-01333bde0b82"
- MARKING_TLP_AMBER = "marking-definition--f88d31f6-486f-44da-b317-01333bde0b82"
- MARKING_TLP_AMBER_STRICT_ID = "826578e1-40ad-459f-bc73-ede076f81f37"
- MARKING_TLP_AMBER_STRICT = "marking-definition--826578e1-40ad-459f-bc73-ede076f81f37"
- MARKING_TLP_RED_ID = "5e57c739-391a-4eb3-b6be-7d15ca92d5ed"
- MARKING_TLP_RED = "marking-definition--5e57c739-391a-4eb3-b6be-7d15ca92d5ed"
- STATIC_MARKING_IDS: 包含所有静态TLP标记ID的列表
- STATIC_STANDARD_IDS: 包含静态ID和数据映射的列表

### Requirement: ID贡献字段定义
系统 SHALL 为以下实体类型定义ID贡献字段：

#### STIX网络可观测对象 (20种)
- ENTITY_AUTONOMOUS_SYSTEM: [{ src: 'number' }]
- ENTITY_DIRECTORY: [{ src: 'path' }]
- ENTITY_DOMAIN_NAME: [{ src: 'value' }]
- ENTITY_EMAIL_ADDR: [{ src: 'value' }]
- ENTITY_EMAIL_MESSAGE: [{ src: 'from', dest: 'from_ref' }, { src: 'subject' }, { src: 'body' }]
- ENTITY_HASHED_OBSERVABLE_ARTIFACT: [[{ src: 'hashes' }], [{ src: 'url' }]]
- ENTITY_HASHED_OBSERVABLE_STIX_FILE: [[{ src: 'hashes' }], [{ src: NAME_FIELD }]]
- ENTITY_HASHED_OBSERVABLE_X509_CERTIFICATE: [[{ src: 'hashes' }], [{ src: 'serial_number' }], [{ src: 'subject' }]]
- ENTITY_IPV4_ADDR: [{ src: 'value' }]
- ENTITY_IPV6_ADDR: [{ src: 'value' }]
- ENTITY_MAC_ADDR: [{ src: 'value' }]
- ENTITY_MUTEX: [{ src: NAME_FIELD }]
- ENTITY_NETWORK_TRAFFIC: [{ src: 'start' }, { src: 'end' }, { src: INPUT_SRC, dest: 'src_ref' }, { src: INPUT_DST, dest: 'dst_ref' }, { src: 'src_port' }, { src: 'dst_port' }, { src: 'protocols' }]
- ENTITY_PROCESS: [{ src: 'pid', dependencies: ['command_line'] }, { src: 'command_line' }]
- ENTITY_SOFTWARE: [{ src: NAME_FIELD }, { src: 'cpe' }, { src: 'swid' }, { src: 'vendor' }, { src: 'version' }]
- ENTITY_URL: [{ src: 'value' }]
- ENTITY_USER_ACCOUNT: [{ src: 'account_type' }, { src: 'user_id' }, { src: 'account_login' }]
- ENTITY_WINDOWS_REGISTRY_KEY: [{ src: 'attribute_key', dst: 'key' }, { src: 'values' }]
- 以及扩展类型: CRYPTOGRAPHIC_KEY, CRYPTOGRAPHIC_WALLET, HOSTNAME, USER_AGENT, TEXT, BANK_ACCOUNT, PHONE_NUMBER, CREDENTIAL, TRACKING_NUMBER, PAYMENT_CARD, MEDIA_CONTENT, SSH_KEY, PERSONA
- 嵌入式类型: EMAIL_MIME_PART_TYPE, WINDOWS_REGISTRY_VALUE_TYPE

#### 内部对象 (20种)
- ENTITY_TYPE_SETTINGS: () -> OPENCTI_PLATFORM_UUID
- ENTITY_TYPE_MIGRATION_STATUS: () -> uuidv4()
- ENTITY_TYPE_MIGRATION_REFERENCE: [{ src: 'title' }, { src: 'timestamp' }]
- ENTITY_TYPE_GROUP: [{ src: NAME_FIELD }]
- ENTITY_TYPE_USER: [{ src: 'user_email' }]
- ENTITY_TYPE_ROLE: [{ src: NAME_FIELD }]
- ENTITY_TYPE_CAPABILITY: [{ src: NAME_FIELD }]
- ENTITY_TYPE_CONNECTOR: () -> uuidv4()
- ENTITY_TYPE_CONNECTOR_MANAGER: () -> uuidv4()
- ENTITY_TYPE_RULE_MANAGER: () -> uuidv4()
- ENTITY_TYPE_RULE: () -> uuidv4()
- ENTITY_TYPE_HISTORY: () -> uuidv4()
- ENTITY_TYPE_STATUS_TEMPLATE: [{ src: NAME_FIELD }]
- ENTITY_TYPE_STATUS: [{ src: 'template_id' }, { src: 'type' }, { src: 'scope' }]
- ENTITY_TYPE_FEED: () -> uuidv4()
- ENTITY_TYPE_TAXII_COLLECTION: () -> uuidv4()
- ENTITY_TYPE_BACKGROUND_TASK: () -> uuidv4()
- ENTITY_TYPE_RETENTION_RULE: () -> uuidv4()
- ENTITY_TYPE_SYNC: () -> uuidv4()
- ENTITY_TYPE_STREAM_COLLECTION: () -> uuidv4()
- ENTITY_TYPE_INTERNAL_FILE: () -> uuidv4()
- ENTITY_TYPE_WORK: () -> uuidv4()
- ENTITY_TYPE_THEME: () -> uuidv4()

#### STIX域对象 (18种)
- ENTITY_TYPE_ATTACK_PATTERN: [[{ src: X_MITRE_ID_FIELD }], [{ src: NAME_FIELD }]]
- ENTITY_TYPE_CAMPAIGN: [{ src: NAME_FIELD }]
- ENTITY_TYPE_CONTAINER_NOTE: [{ src: 'content' }, { src: 'created', dependencies: ['content'] }]
- ENTITY_TYPE_CONTAINER_OBSERVED_DATA: [{ src: 'objects' }]
- ENTITY_TYPE_CONTAINER_OPINION: [{ src: 'opinion' }, { src: 'created', dependencies: ['opinion'] }]
- ENTITY_TYPE_CONTAINER_REPORT: [{ src: NAME_FIELD }, { src: 'published' }]
- ENTITY_TYPE_COURSE_OF_ACTION: [[{ src: X_MITRE_ID_FIELD }], [{ src: NAME_FIELD }]]
- ENTITY_TYPE_IDENTITY_INDIVIDUAL: [{ src: NAME_FIELD }, { src: 'identity_class', dependencies: [NAME_FIELD] }]
- ENTITY_TYPE_IDENTITY_SECTOR: [{ src: NAME_FIELD }, { src: 'identity_class', dependencies: [NAME_FIELD] }]
- ENTITY_TYPE_IDENTITY_SYSTEM: [{ src: NAME_FIELD }, { src: 'identity_class', dependencies: [NAME_FIELD] }]
- ENTITY_TYPE_INFRASTRUCTURE: [{ src: NAME_FIELD }]
- ENTITY_TYPE_INTRUSION_SET: [{ src: NAME_FIELD }]
- ENTITY_TYPE_LOCATION_CITY: [{ src: NAME_FIELD }, { src: 'x_opencti_location_type', dependencies: [NAME_FIELD] }]
- ENTITY_TYPE_LOCATION_COUNTRY: [{ src: NAME_FIELD }, { src: 'x_opencti_location_type', dependencies: [NAME_FIELD] }]
- ENTITY_TYPE_LOCATION_REGION: [{ src: NAME_FIELD }, { src: 'x_opencti_location_type', dependencies: [NAME_FIELD] }]
- ENTITY_TYPE_LOCATION_POSITION: [[{ src: 'latitude' }, { src: 'longitude' }], [{ src: NAME_FIELD }]]
- ENTITY_TYPE_MALWARE: [{ src: NAME_FIELD }]
- ENTITY_TYPE_THREAT_ACTOR_GROUP: [{ src: NAME_FIELD }, { src: INNER_TYPE, dependencies: [NAME_FIELD] }]
- ENTITY_TYPE_TOOL: [{ src: NAME_FIELD }]
- ENTITY_TYPE_VULNERABILITY: [{ src: NAME_FIELD }]
- ENTITY_TYPE_INCIDENT: [{ src: NAME_FIELD }, { src: 'created', dependencies: [NAME_FIELD] }]

#### STIX元对象 (4种)
- ENTITY_TYPE_MARKING_DEFINITION: [{ src: 'definition', dependencies: ['definition_type'] }, { src: 'definition_type' }]
- ENTITY_TYPE_LABEL: [{ src: 'value' }]
- ENTITY_TYPE_KILL_CHAIN_PHASE: [{ src: 'phase_name' }, { src: 'kill_chain_name' }]
- ENTITY_TYPE_EXTERNAL_REFERENCE: [[{ src: 'url' }], [{ src: 'source_name', dependencies: ['external_id'] }, { src: 'external_id' }]]

#### 关系对象
- relationship: [{ src: 'relationship_type' }, { src: 'from', dest: 'source_ref', dependencies: ['to'] }, { src: 'to', dest: 'target_ref', dependencies: ['from'] }, { src: 'start_time' }, { src: 'stop_time' }]
- sighting: [{ src: 'relationship_type', dest: 'type' }, { src: 'from', dest: 'sighting_of_ref', dependencies: ['to'] }, { src: 'to', dest: 'where_sighted_refs', dependencies: ['from'] }, { src: 'first_seen' }, { src: 'last_seen' }]

### Requirement: 工具方法
系统 SHALL 提供以下工具方法：

#### Scenario: 规范化名称
- **WHEN** 调用 normalizeName(name)
- **THEN** 返回小写并去除首尾空格的字符串
- **AND** 如果name为null或空，返回空字符串

#### Scenario: 生成Standard ID
- **WHEN** 调用 generateStandardId(type, data)
- **THEN** 根据实体类型选择不同的ID生成策略
- **AND** STIX元对象/域对象/可观测对象使用OASIS命名空间生成UUID v5
- **AND** 内部对象使用OpenCTI命名空间生成UUID v5
- **AND** 关系类型生成基于UUID v4的ID

#### Scenario: 生成Internal ID
- **WHEN** 调用 generateInternalId()
- **THEN** 返回UUID v4格式的字符串

#### Scenario: 生成工作ID
- **WHEN** 调用 generateWorkId(connectorId)
- **THEN** 返回格式为 "work_{connectorId}_{timestamp}" 的对象
- **AND** 包含id和timestamp字段

#### Scenario: 生成文件索引ID
- **WHEN** 调用 generateFileIndexId(fileId)
- **THEN** 基于fileId使用OpenCTI命名空间生成UUID v5

#### Scenario: 生成别名ID列表
- **WHEN** 调用 generateAliasesId(rawAliases, instance)
- **THEN** 如果实体类型支持别名，为每个别名生成Standard ID
- **AND** 返回唯一ID列表

#### Scenario: 获取实例所有ID
- **WHEN** 调用 getInstanceIds(instance, withoutInternal)
- **THEN** 返回包含internal_id、standard_id、x_opencti_stix_ids、别名ID、哈希ID的列表
- **AND** 如果withoutInternal为true，不包含internal_id

#### Scenario: 判断字段是否贡献Standard ID
- **WHEN** 调用 isFieldContributingToStandardId(instance, keys)
- **THEN** 判断指定字段是否参与Standard ID生成
- **AND** 返回布尔值

#### Scenario: 判断Standard ID是否升级
- **WHEN** 调用 isStandardIdUpgraded(previous, updated)
- **THEN** 比较两个实例的ID生成方式
- **AND** 如果升级（添加字段）返回true

#### Scenario: 判断Standard ID是否降级
- **WHEN** 调用 isStandardIdDowngraded(previous, updated)
- **THEN** 比较两个实例的ID生成方式
- **AND** 如果降级（删除字段）返回true

## Implementation Notes
1. 使用Java UUID类生成UUID v4和v5
2. UUID v5需要使用命名空间（OASIS_NAMESPACE或OPENCTI_NAMESPACE）
3. 使用JSON Canonicalization（RFC 8785）对数据进行规范化
4. ID贡献字段定义使用Map结构存储
5. 支持多种ID生成方式（函数、字段列表、多路径选择）
6. 需要处理依赖关系（dependencies字段）
7. 需要实现Resolver来处理字段值转换（如小写、大写、日期格式化等）
