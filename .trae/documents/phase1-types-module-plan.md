# Phase 1.3 类型定义模块实施计划

## 一、任务概述

### 1.1 任务目标
重写 OpenCTI GraphQL 后端的类型定义模块 `types/`，使用 Java 21 实现。

### 1.2 原项目文件清单

| 原文件 | 行数 | 功能说明 | 优先级 |
|--------|------|----------|--------|
| `stix-2-1-common.d.ts` | ~150行 | STIX 2.1 通用类型定义 | P0 |
| `stix-2-1-sdo.d.ts` | ~300行 | STIX 2.1 域对象(SDO)类型 | P0 |
| `stix-2-1-sco.d.ts` | ~400行 | STIX 2.1 网络可观测对象(SCO)类型 | P0 |
| `stix-2-1-sro.d.ts` | ~100行 | STIX 2.1 关系对象(SRO)类型 | P0 |
| `stix-2-1-smo.d.ts` | ~100行 | STIX 2.1 元对象(SMO)类型 | P0 |
| `stix-2-1-extensions.ts` | ~50行 | STIX 2.1 扩展定义 | P0 |
| `stix-2-0-*.d.ts` | ~200行 | STIX 2.0 类型定义 | P1 |
| `store.d.ts` | ~500行 | 数据存储类型定义 | P0 |
| `user.d.ts` | ~100行 | 用户相关类型 | P0 |
| `event.d.ts` | ~100行 | 事件类型定义 | P0 |
| `connector.d.ts` | ~100行 | 连接器类型 | P0 |
| `group.d.ts` | ~50行 | 用户组类型 | P0 |
| `settings.d.ts` | ~50行 | 设置类型 | P0 |
| `log.d.ts` | ~50行 | 日志类型 | P0 |
| `graphql.d.ts` | ~100行 | GraphQL类型 | P0 |

### 1.3 预估代码量
~2,500行 Java 代码

---

## 二、实施步骤

### Step 1: 创建类型定义目录结构

**目标**: 在 `opencti-common` 模块下创建 `types` 包

**输出目录**:
```
opencti-common/src/main/java/io/opencti/common/types/
├── stix/
│   ├── StixObject.java              # STIX基础对象
│   ├── StixDomainObject.java        # STIX域对象(SDO)
│   ├── StixCyberObject.java         # STIX网络可观测对象(SCO)
│   ├── StixRelationshipObject.java  # STIX关系对象(SRO)
│   ├── StixMetaObject.java          # STIX元对象(SMO)
│   ├── StixBundle.java              # STIX Bundle
│   ├── StixId.java                  # STIX ID
│   ├── StixDate.java                # STIX日期
│   └── sdo/                         # 具体SDO类型
│       ├── StixAttackPattern.java
│       ├── StixCampaign.java
│       ├── StixCourseOfAction.java
│       ├── StixIdentity.java
│       ├── StixIncident.java
│       ├── StixInfrastructure.java
│       ├── StixIntrusionSet.java
│       ├── StixLocation.java
│       ├── StixMalware.java
│       ├── StixThreatActor.java
│       ├── StixTool.java
│       ├── StixVulnerability.java
│       └── ...
├── store/
│   ├── StoreObject.java             # 存储基础对象
│   ├── StoreEntity.java             # 存储实体
│   ├── StoreRelation.java           # 存储关系
│   └── StoreIdentifier.java         # 存储标识符
├── User.java                        # 用户类型
├── Group.java                       # 用户组类型
├── Event.java                       # 事件类型
├── Connector.java                   # 连接器类型
└── Settings.java                    # 设置类型
```

**验证标准**: 目录创建成功

---

### Step 2: 实现STIX基础类型

#### 2.1 StixId.java

**原文件**: `src/types/stix-2-1-common.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-common.d.ts
 * STIX标识符
 */
public record StixId(String type, UUID uuid) {
    public static StixId parse(String id);
    public static StixId generate(String type);
    public String toString();
}
```

#### 2.2 StixDate.java

**原文件**: `src/types/stix-2-1-common.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-common.d.ts
 * STIX日期类型
 */
public record StixDate(Instant value) {
    public static StixDate now();
    public static StixDate parse(String dateStr);
    public String toIsoString();
}
```

#### 2.3 StixKillChainPhase.java

**原文件**: `src/types/stix-2-1-common.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-common.d.ts
 * Kill Chain阶段
 */
public record StixKillChainPhase(
    String killChainName,
    String phaseName
) {}
```

**验证标准**: 基础类型编译通过

---

### Step 3: 实现STIX扩展类型

#### 3.1 StixOpenctiExtension.java

**原文件**: `src/types/stix-2-1-common.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-common.d.ts
 * OpenCTI扩展
 */
public record StixOpenctiExtension(
    String extensionType,
    UUID id,
    List<StixFileExtension> files,
    List<String> aliases,
    List<StixId> grantedRefs,
    String type,
    StixDate createdAt,
    StixDate updatedAt,
    StixDate modifiedAt,
    boolean isInferred,
    String workflowId,
    List<String> assigneeIds,
    List<String> participantIds,
    List<String> creatorIds,
    List<AuthorizedMember> authorizedMembers,
    List<String> labelsIds,
    String createdByRefId
) {}
```

#### 3.2 StixMitreExtension.java

**原文件**: `src/types/stix-2-1-common.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-common.d.ts
 * MITRE扩展
 */
public record StixMitreExtension(
    String extensionType,
    String id,
    String detection,
    List<String> permissionsRequired,
    List<String> platforms,
    List<String> collectionLayers
) {}
```

**验证标准**: 扩展类型编译通过

---

### Step 4: 实现STIX核心对象

#### 4.1 StixObject.java

**原文件**: `src/types/stix-2-1-common.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-common.d.ts
 * STIX基础对象
 */
public record StixObject(
    StixId id,
    String type,
    String specVersion,
    List<StixId> objectMarkingRefs,
    Map<String, Object> extensions
) {}
```

#### 4.2 StixDomainObject.java

**原文件**: `src/types/stix-2-1-common.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-common.d.ts
 * STIX域对象(SDO)
 */
public record StixDomainObject(
    StixId id,
    String type,
    String specVersion,
    StixId createdByRef,
    StixDate created,
    StixDate modified,
    boolean revoked,
    List<String> labels,
    int confidence,
    String lang,
    List<StixExternalReference> externalReferences,
    List<StixId> objectMarkingRefs,
    Map<String, Object> extensions
) {}
```

#### 4.3 StixCyberObject.java

**原文件**: `src/types/stix-2-1-common.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-common.d.ts
 * STIX网络可观测对象(SCO)
 */
public record StixCyberObject(
    StixId id,
    String type,
    String specVersion,
    List<StixId> objectMarkingRefs,
    boolean defanged,
    Integer score,
    Map<String, Object> extensions
) {}
```

**验证标准**: 核心对象编译通过

---

### Step 5: 实现具体SDO类型

#### 5.1 StixAttackPattern.java

**原文件**: `src/types/stix-2-1-sdo.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * 攻击模式
 */
public record StixAttackPattern(
    StixId id,
    String name,
    String description,
    List<String> aliases,
    List<StixKillChainPhase> killChainPhases,
    // 继承自StixDomainObject的字段
    StixId createdByRef,
    StixDate created,
    StixDate modified,
    // ...
) implements StixDomainObjectInterface {}
```

#### 5.2 StixCampaign.java

**原文件**: `src/types/stix-2-1-sdo.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * 战役
 */
public record StixCampaign(
    StixId id,
    String name,
    String description,
    List<String> aliases,
    StixDate firstSeen,
    StixDate lastSeen,
    String objective
) implements StixDomainObjectInterface {}
```

#### 5.3 其他SDO类型

按照相同模式实现：
- `StixCourseOfAction` - 行动方案
- `StixIdentity` - 身份
- `StixIncident` - 事件
- `StixInfrastructure` - 基础设施
- `StixIntrusionSet` - 入侵集
- `StixLocation` - 位置
- `StixMalware` - 恶意软件
- `StixThreatActor` - 威胁行为者
- `StixTool` - 工具
- `StixVulnerability` - 漏洞

**验证标准**: 所有SDO类型编译通过

---

### Step 6: 实现Store类型

#### 6.1 StoreObject.java

**原文件**: `src/types/store.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/store.d.ts
 * 存储基础对象
 */
public record StoreObject(
    String id,
    String internalId,
    StixId standardId,
    String entityType,
    String baseType,
    List<String> parentTypes,
    String specVersion,
    Instant createdAt,
    Instant updatedAt,
    Instant refreshedAt,
    List<StoreFile> files,
    List<String> aliases
) {}
```

#### 6.2 StoreEntity.java

**原文件**: `src/types/store.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/store.d.ts
 * 存储实体
 */
public record StoreEntity(
    String id,
    String internalId,
    StixId standardId,
    String entityType,
    // ... 其他字段
) extends StoreObject {}
```

**验证标准**: Store类型编译通过

---

### Step 7: 实现用户相关类型

#### 7.1 User.java

**原文件**: `src/types/user.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/user.d.ts
 * 用户类型
 */
public record User(
    String id,
    String internalId,
    String name,
    String email,
    List<UserRole> roles,
    List<UserCapability> capabilities,
    UserOrigin origin,
    List<Group> groups,
    List<String> organizationIds
) {}
```

#### 7.2 Group.java

**原文件**: `src/types/group.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/group.d.ts
 * 用户组类型
 */
public record Group(
    String id,
    String name,
    String description,
    List<String> memberIds,
    List<String> capabilityIds
) {}
```

**验证标准**: 用户类型编译通过

---

### Step 8: 实现事件和连接器类型

#### 8.1 Event.java

**原文件**: `src/types/event.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/event.d.ts
 * 事件类型
 */
public record Event(
    String id,
    String type,
    String entityType,
    String entityId,
    Instant timestamp,
    Map<String, Object> data
) {}
```

#### 8.2 Connector.java

**原文件**: `src/types/connector.d.ts`

```java
/**
 * 重写自: opencti-graphql/src/types/connector.d.ts
 * 连接器类型
 */
public record Connector(
    String id,
    String name,
    String type,
    String scope,
    Instant lastRun,
    ConnectorConfiguration configuration
) {}
```

**验证标准**: 事件和连接器类型编译通过

---

### Step 9: 编写单元测试

**测试文件**:
- `StixIdTest.java`
- `StixDateTest.java`
- `StixObjectTest.java`
- `StixDomainObjectTest.java`
- `StoreObjectTest.java`
- `UserTest.java`

**验证标准**: 测试覆盖率 > 80%

---

### Step 10: 编译验证

**任务**:
1. 执行 `mvn clean compile test`
2. 检查代码风格
3. 验证所有测试通过

**验证标准**: 
- 编译无错误
- 所有测试通过
- 代码注释完整（包含原文件路径）

---

## 三、文件清单

### 3.1 新建文件

| 文件路径 | 说明 |
|----------|------|
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixId.java` | STIX ID |
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixDate.java` | STIX日期 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixKillChainPhase.java` | Kill Chain阶段 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixOpenctiExtension.java` | OpenCTI扩展 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixMitreExtension.java` | MITRE扩展 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixObject.java` | STIX基础对象 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixDomainObject.java` | STIX域对象 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixCyberObject.java` | STIX网络可观测对象 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixRelationshipObject.java` | STIX关系对象 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/StixBundle.java` | STIX Bundle |
| `opencti-common/src/main/java/io/opencti/common/types/stix/sdo/StixAttackPattern.java` | 攻击模式 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/sdo/StixCampaign.java` | 战役 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/sdo/StixIdentity.java` | 身份 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/sdo/StixIncident.java` | 事件 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/sdo/StixMalware.java` | 恶意软件 |
| `opencti-common/src/main/java/io/opencti/common/types/stix/sdo/StixVulnerability.java` | 漏洞 |
| `opencti-common/src/main/java/io/opencti/common/types/store/StoreObject.java` | 存储基础对象 |
| `opencti-common/src/main/java/io/opencti/common/types/store/StoreEntity.java` | 存储实体 |
| `opencti-common/src/main/java/io/opencti/common/types/User.java` | 用户类型 |
| `opencti-common/src/main/java/io/opencti/common/types/Group.java` | 用户组类型 |
| `opencti-common/src/main/java/io/opencti/common/types/Event.java` | 事件类型 |
| `opencti-common/src/main/java/io/opencti/common/types/Connector.java` | 连接器类型 |

### 3.2 测试文件

| 文件路径 | 说明 |
|----------|------|
| `opencti-test/src/test/java/io/opencti/common/types/StixIdTest.java` | STIX ID测试 |
| `opencti-test/src/test/java/io/opencti/common/types/StixDateTest.java` | STIX日期测试 |
| `opencti-test/src/test/java/io/opencti/common/types/StixObjectTest.java` | STIX对象测试 |
| `opencti-test/src/test/java/io/opencti/common/types/UserTest.java` | 用户类型测试 |

### 3.3 文件数量统计

| 类型 | 数量 |
|------|------|
| Java源文件 | 22 |
| 测试文件 | 4 |
| **总计** | **26** |

---

## 四、风险与注意事项

### 4.1 风险点

| 风险 | 等级 | 应对措施 |
|------|------|----------|
| STIX类型数量多 | 🟡 中 | 使用record类型简化代码 |
| 类型继承复杂 | 🟡 中 | 使用接口+record组合 |
| 扩展字段处理 | 🟡 中 | 使用Map存储扩展字段 |

### 4.2 注意事项

1. **每个Java类必须添加注释**，标明重写的原文件路径
2. **使用Java record类型**，简化不可变数据类
3. **使用接口定义公共行为**，便于类型扩展
4. **保持与STIX 2.1规范一致**

---

## 五、验收标准

| 标准 | 验证方式 |
|------|----------|
| Maven编译成功 | `mvn clean compile` |
| 单元测试通过 | `mvn test` |
| 代码注释完整 | 代码审查 |
| 类型定义完整 | 对照原类型文件 |

---

## 六、时间估算

| 步骤 | 预估时间 |
|------|----------|
| Step 1: 创建目录结构 | 0.5小时 |
| Step 2: STIX基础类型 | 1小时 |
| Step 3: STIX扩展类型 | 1小时 |
| Step 4: STIX核心对象 | 1.5小时 |
| Step 5: 具体SDO类型 | 3小时 |
| Step 6: Store类型 | 1小时 |
| Step 7: 用户相关类型 | 1小时 |
| Step 8: 事件和连接器类型 | 1小时 |
| Step 9: 单元测试 | 2小时 |
| Step 10: 编译验证 | 0.5小时 |
| **总计** | **12.5小时（约2天）** |
