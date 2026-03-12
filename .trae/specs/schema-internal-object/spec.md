# Schema Internal Object 模块重写 Spec

## Why
Phase 3 Schema定义层的第三个任务是重写 `schema/internalObject.ts` 文件。该文件定义了OpenCTI系统中所有内部对象的类型常量（如Settings、User、Group、Role等），以及内部对象的类型判断方法。这是区分内部对象和其他类型对象的基础模块。

## What Changes
- 创建 `io.opencti.schema.internal` 包
- 重写所有内部对象类型常量（35+种）
- 重写内部对象分类列表（DATED_INTERNAL_OBJECTS, INTERNAL_OBJECTS, HISTORY_OBJECTS）
- 重写类型判断方法（isInternalObject, isDatedInternalObject, isHistoryObject）
- 保持与源码逻辑完全一致

## Impact
- 影响文件: `opencti-platform/opencti-graphql/src/schema/internalObject.ts` (~104行)
- 目标文件: `opencti-java/src/main/java/io/opencti/schema/internal/InternalObjectSchema.java`
- 依赖模块:
  - `schema.general.SchemaGeneral` (ABSTRACT_INTERNAL_OBJECT常量)
  - `schema.SchemaTypesDefinition` (类型注册)

## ADDED Requirements

### Requirement: 内部对象类型常量
系统 SHALL 提供以下内部对象类型常量：

#### 系统配置对象
- ENTITY_TYPE_SETTINGS = "Settings"
- ENTITY_TYPE_MIGRATION_STATUS = "MigrationStatus"
- ENTITY_TYPE_MIGRATION_REFERENCE = "MigrationReference"

#### 规则管理对象
- ENTITY_TYPE_RULE_MANAGER = "RuleManager"
- ENTITY_TYPE_RULE = "Rule"

#### 用户权限对象
- ENTITY_TYPE_GROUP = "Group"
- ENTITY_TYPE_USER = "User"
- ENTITY_TYPE_ROLE = "Role"
- ENTITY_TYPE_CAPABILITY = "Capability"

#### 连接器对象
- ENTITY_TYPE_CONNECTOR = "Connector"
- ENTITY_TYPE_CONNECTOR_MANAGER = "ConnectorManager"

#### 历史记录对象
- ENTITY_TYPE_HISTORY = "History"
- ENTITY_TYPE_PIR_HISTORY = "PirHistory"
- ENTITY_TYPE_ACTIVITY = "Activity"

#### 任务工作对象
- ENTITY_TYPE_WORK = "work"
- ENTITY_TYPE_BACKGROUND_TASK = "BackgroundTask"
- ENTITY_TYPE_RETENTION_RULE = "RetentionRule"

#### 同步集合对象
- ENTITY_TYPE_SYNC = "Sync"
- ENTITY_TYPE_TAXII_COLLECTION = "TaxiiCollection"
- ENTITY_TYPE_INTERNAL_FILE = "InternalFile"
- ENTITY_TYPE_FEED = "Feed"
- ENTITY_TYPE_STREAM_COLLECTION = "StreamCollection"

#### 状态主题对象
- ENTITY_TYPE_STATUS_TEMPLATE = "StatusTemplate"
- ENTITY_TYPE_STATUS = "Status"
- ENTITY_TYPE_THEME = "Theme"

#### 模块相关对象（从其他模块导入）
- ENTITY_TYPE_WORKSPACE = "Workspace" (from workspace-types)
- ENTITY_TYPE_PUBLIC_DASHBOARD = "PublicDashboard" (from publicDashboard-types)
- ENTITY_TYPE_DELETE_OPERATION = "DeleteOperation" (from deleteOperation-types)
- ENTITY_TYPE_DRAFT_WORKSPACE = "DraftWorkspace" (from draftWorkspace-types)
- ENTITY_TYPE_EXCLUSION_LIST = "ExclusionList" (from exclusionList-types)
- ENTITY_TYPE_FINTEL_TEMPLATE = "FintelTemplate" (from fintelTemplate-types)
- ENTITY_TYPE_SAVED_FILTER = "SavedFilter" (from savedFilter-types)
- ENTITY_TYPE_PIR = "Pir" (from pir-types)
- ENTITY_TYPE_FINTEL_DESIGN = "FintelDesign" (from fintelDesign-types)
- ENTITY_TYPE_EMAIL_TEMPLATE = "EmailTemplate" (from emailTemplate-types)
- ENTITY_TYPE_FORM = "Form" (from form-types)

### Requirement: 内部对象分类列表
系统 SHALL 定义以下内部对象分类：

#### DATED_INTERNAL_OBJECTS
包含以下类型：
- Settings, Group, User, Role, Capability, Connector
- Workspace, Sync, PublicDashboard, DeleteOperation
- DraftWorkspace, ExclusionList, SavedFilter, Pir, Form, Theme

#### INTERNAL_OBJECTS
包含所有内部对象类型（35种）

#### HISTORY_OBJECTS
包含：work

### Requirement: 类型判断方法
系统 SHALL 提供以下类型判断方法：

#### Scenario: 判断是否为内部对象
- **WHEN** 调用 isInternalObject(type)
- **THEN** 如果类型在 INTERNAL_OBJECTS 列表中，或等于 ABSTRACT_INTERNAL_OBJECT，返回 true
- **AND** 否则返回 false

#### Scenario: 判断是否为带日期的内部对象
- **WHEN** 调用 isDatedInternalObject(type)
- **THEN** 如果类型在 DATED_INTERNAL_OBJECTS 列表中，返回 true
- **AND** 否则返回 false

#### Scenario: 判断是否为历史对象
- **WHEN** 调用 isHistoryObject(type)
- **THEN** 如果类型在 HISTORY_OBJECTS 列表中，返回 true
- **AND** 否则返回 false

### Requirement: 类型注册
系统 SHALL 在类加载时自动注册内部对象类型：
- 调用 SchemaTypesDefinition.register(ABSTRACT_INTERNAL_OBJECT, INTERNAL_OBJECTS)

## Implementation Notes
1. 所有常量使用 `public static final String` 定义
2. 使用 `Set` 存储类型列表以提高查询效率
3. 类型判断方法使用 `public static` 定义
4. 使用静态代码块在类加载时自动注册类型
5. 类必须添加注释说明重写的原文件路径
6. 每个常量/方法必须添加注释说明对应的原代码
