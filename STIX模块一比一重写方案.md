# STIX模块一比一重写方案

## 一、源码项目结构分析

### 1.1 源码目录结构 (opencti-graphql/src)

```
src/
├── types/                          # 类型定义层 (TypeScript类型)
│   ├── stix-2-1-common.d.ts       # STIX通用类型定义
│   ├── stix-2-1-sdo.d.ts          # STIX Domain Objects类型
│   ├── stix-2-1-sco.d.ts          # STIX Cyber Observable类型
│   ├── stix-2-1-sro.d.ts          # STIX Relationship Objects类型
│   ├── stix-2-1-smo.d.ts          # STIX Meta Objects类型
│   ├── stix-2-1-extensions.ts     # STIX扩展定义
│   ├── stix-2-0-common.d.ts       # STIX 2.0类型
│   ├── stix-2-0-sdo.d.ts          # STIX 2.0 SDO
│   ├── stix-2-0-smo.d.ts          # STIX 2.0 SMO
│   └── store.d.ts                 # 存储层类型定义
│
├── database/                       # 数据访问层
│   ├── stix.ts                    # STIX核心关系映射
│   ├── stix-2-1-converter.ts      # STIX 2.1转换器
│   ├── stix-2-0-converter.ts      # STIX 2.0转换器
│   ├── stix-common-converter.ts   # 通用转换器入口
│   ├── stix-converter-utils.ts    # 转换工具函数
│   ├── stix-representative.ts     # STIX实体表示提取
│   └── stix-ref.ts                # STIX引用关系
│
└── schema/                         # 模式定义层
    ├── stixDomainObject.ts        # SDO模式
    ├── stixCyberObservable.ts     # SCO模式
    ├── stixCoreRelationship.ts    # 核心关系模式
    ├── stixRefRelationship.ts     # 引用关系模式
    └── internalRelationship.ts    # 内部关系模式
```

### 1.2 源码分层架构

```
┌─────────────────────────────────────────────────────────────┐
│  类型定义层 (types/)                                         │
│  - 纯类型定义，无业务逻辑                                     │
│  - 定义STIX对象的数据结构                                     │
│  - 定义Store层的数据结构                                      │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  模式层 (schema/)                                            │
│  - 定义实体类型常量                                           │
│  - 定义关系类型常量                                           │
│  - 提供类型检查函数                                           │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│  数据访问层 (database/)                                       │
│  - STIX转换逻辑                                              │
│  - 关系映射定义                                              │
│  - 工具函数                                                  │
└─────────────────────────────────────────────────────────────┘
```

---

## 二、当前Java项目问题分析

### 2.1 现有问题

1. **重复类问题**
   - `StixCoreRelationshipsMapping` 有两个版本
   - `StixBundle` 有两个版本
   - `StixOpenctiExtension` 有两个版本
   - `StixMitreExtension` 有两个版本

2. **包结构混乱**
   - `common.types.stix` 和 `database.stix.model` 职责不清
   - Extension类分散在两个包中

3. **与源码结构不一致**
   - 源码中types和database是分开的
   - Java项目中界限模糊

---

## 三、一比一重写方案

### 3.1 设计原则

1. **严格遵循源码结构** - types和database完全分离
2. **一比一映射** - 每个源码文件对应一个Java文件
3. **职责清晰** - 类型定义层只定义数据结构，database层处理转换逻辑
4. **消除重复** - 删除重复类，统一实现

### 3.2 推荐的Java包结构

```
opencti-java/src/main/java/io/opencti/
│
├── types/                                          # 对应源码 types/
│   ├── stix/                                      # STIX类型定义
│   │   ├── common/                                # 通用类型
│   │   │   ├── StixId.java                       # stix-2-1-common.d.ts
│   │   │   ├── StixDate.java                     # stix-2-1-common.d.ts
│   │   │   ├── StixKillChainPhase.java           # stix-2-1-common.d.ts
│   │   │   ├── StixBundle.java                   # stix-2-1-common.d.ts
│   │   │   ├── StixObject.java                   # stix-2-1-common.d.ts
│   │   │   ├── StixInternal.java                 # stix-2-1-common.d.ts
│   │   │   ├── StixDomainObject.java             # stix-2-1-common.d.ts
│   │   │   ├── StixCyberObject.java              # stix-2-1-common.d.ts
│   │   │   ├── StixRelationshipObject.java       # stix-2-1-common.d.ts
│   │   │   ├── StixOpenctiExtension.java         # stix-2-1-common.d.ts
│   │   │   ├── StixMitreExtension.java           # stix-2-1-common.d.ts
│   │   │   ├── StixFileExtension.java            # stix-2-1-common.d.ts
│   │   │   └── StixMetric.java                   # stix-2-1-common.d.ts
│   │   │
│   │   ├── sdo/                                   # SDO类型
│   │   │   ├── StixAttackPattern.java            # stix-2-1-sdo.d.ts
│   │   │   ├── StixCampaign.java                 # stix-2-1-sdo.d.ts
│   │   │   ├── StixCourseOfAction.java           # stix-2-1-sdo.d.ts
│   │   │   ├── StixIdentity.java                 # stix-2-1-sdo.d.ts
│   │   │   ├── StixIncident.java                 # stix-2-1-sdo.d.ts
│   │   │   ├── StixInfrastructure.java           # stix-2-1-sdo.d.ts
│   │   │   ├── StixIntrusionSet.java             # stix-2-1-sdo.d.ts
│   │   │   ├── StixLocation.java                 # stix-2-1-sdo.d.ts
│   │   │   ├── StixMalware.java                  # stix-2-1-sdo.d.ts
│   │   │   ├── StixMalwareAnalysis.java          # stix-2-1-sdo.d.ts
│   │   │   ├── StixNote.java                     # stix-2-1-sdo.d.ts
│   │   │   ├── StixObservedData.java             # stix-2-1-sdo.d.ts
│   │   │   ├── StixOpinion.java                  # stix-2-1-sdo.d.ts
│   │   │   ├── StixReport.java                   # stix-2-1-sdo.d.ts
│   │   │   ├── StixThreatActor.java              # stix-2-1-sdo.d.ts
│   │   │   ├── StixTool.java                     # stix-2-1-sdo.d.ts
│   │   │   └── StixVulnerability.java            # stix-2-1-sdo.d.ts
│   │   │
│   │   ├── sco/                                   # SCO类型
│   │   │   ├── StixArtifact.java                 # stix-2-1-sco.d.ts
│   │   │   ├── StixAutonomousSystem.java         # stix-2-1-sco.d.ts
│   │   │   ├── StixDirectory.java                # stix-2-1-sco.d.ts
│   │   │   ├── StixDomainName.java               # stix-2-1-sco.d.ts
│   │   │   ├── StixEmailAddr.java                # stix-2-1-sco.d.ts
│   │   │   ├── StixEmailMessage.java             # stix-2-1-sco.d.ts
│   │   │   ├── StixFile.java                     # stix-2-1-sco.d.ts
│   │   │   ├── StixIpv4Addr.java                 # stix-2-1-sco.d.ts
│   │   │   ├── StixIpv6Addr.java                 # stix-2-1-sco.d.ts
│   │   │   ├── StixMacAddr.java                  # stix-2-1-sco.d.ts
│   │   │   ├── StixMutex.java                    # stix-2-1-sco.d.ts
│   │   │   ├── StixNetworkTraffic.java           # stix-2-1-sco.d.ts
│   │   │   ├── StixProcess.java                  # stix-2-1-sco.d.ts
│   │   │   ├── StixSoftware.java                 # stix-2-1-sco.d.ts
│   │   │   ├── StixUrl.java                      # stix-2-1-sco.d.ts
│   │   │   ├── StixUserAccount.java              # stix-2-1-sco.d.ts
│   │   │   ├── StixWindowsRegistryKey.java       # stix-2-1-sco.d.ts
│   │   │   └── StixX509Certificate.java          # stix-2-1-sco.d.ts
│   │   │
│   │   ├── sro/                                   # SRO类型
│   │   │   ├── StixRelation.java                 # stix-2-1-sro.d.ts
│   │   │   └── StixSighting.java                 # stix-2-1-sro.d.ts
│   │   │
│   │   └── smo/                                   # SMO类型
│   │       ├── StixMarkingDefinition.java        # stix-2-1-smo.d.ts
│   │       ├── StixExternalReference.java        # stix-2-1-smo.d.ts
│   │       └── StixLabel.java                    # stix-2-1-smo.d.ts
│   │
│   └── store/                                     # Store类型 (store.d.ts)
│       ├── StoreObject.java
│       ├── StoreEntity.java
│       ├── StoreRelation.java
│       ├── StoreCyberObservable.java
│       ├── StoreFile.java
│       ├── KillChainPhase.java
│       └── ExternalReference.java
│
├── schema/                                        # 对应源码 schema/
│   ├── stix/                                     # STIX模式定义
│   │   ├── StixDomainObjectSchema.java           # stixDomainObject.ts
│   │   ├── StixCyberObservableSchema.java        # stixCyberObservable.ts
│   │   ├── StixCoreRelationshipSchema.java       # stixCoreRelationship.ts
│   │   ├── StixRefRelationshipSchema.java        # stixRefRelationship.ts
│   │   └── InternalRelationshipSchema.java       # internalRelationship.ts
│   │
│   └── constants/                                # 常量定义
│       ├── StixExtensionConstants.java           # stix-2-1-extensions.ts
│       └── StixRelationshipConstants.java        # 关系常量
│
└── database/                                      # 对应源码 database/
    └── stix/                                     # STIX数据库操作
        ├── StixCoreRelationshipsMapping.java     # stix.ts
        ├── StixUtils.java                        # stix.ts
        ├── StixConverter.java                    # stix-common-converter.ts
        ├── Stix21Converter.java                  # stix-2-1-converter.ts
        ├── Stix20Converter.java                  # stix-2-0-converter.ts
        ├── StixConverterUtils.java               # stix-converter-utils.ts
        ├── StixRepresentative.java               # stix-representative.ts
        └── StixRefMapping.java                   # stix-ref.ts
```

---

## 四、类合并/删除方案

### 4.1 删除重复类

| 删除类 | 保留类 | 原因 |
|--------|--------|------|
| `database.stix.mapping.StixCoreRelationshipsMapping` | `database.stix.StixCoreRelationshipsMapping` | 保留完整实现 |
| `database.stix.model.StixBundle` | `types.stix.common.StixBundle` | types层定义数据结构 |
| `database.stix.model.StixOpenctiExtension` | `types.stix.common.StixOpenctiExtension` | types层定义数据结构 |
| `database.stix.model.StixMitreExtension` | `types.stix.common.StixMitreExtension` | types层定义数据结构 |
| `database.stix.model.StixOpenctiScoExtension` | `types.stix.common.StixOpenctiExtension` | 合并到主Extension类 |
| `database.stix.model.StixExtensions` | - | 未使用，删除 |

### 4.2 删除整个包

```
❌ 删除 database/stix/mapping/ 整个包
❌ 删除 database/stix/model/ 整个包
❌ 删除 database/stix/representative/ 整个包 (合并到database/stix/)
```

### 4.3 移动类

| 原位置 | 新位置 | 说明 |
|--------|--------|------|
| `common.types.stix.sdo.*` | `types.stix.sdo.*` | 统一包结构 |
| `common.types.stix.sco.*` | `types.stix.sco.*` | 统一包结构 |
| `common.types.stix.sro.*` | `types.stix.sro.*` | 统一包结构 |
| `common.types.stix.smo.*` | `types.stix.smo.*` | 统一包结构 |
| `common.types.store.*` | `types.store.*` | 统一包结构 |

---

## 五、具体实施步骤

### 步骤1: 删除重复包和类

```bash
# 删除重复包
rm -rf opencti-java/src/main/java/io/opencti/database/stix/mapping/
rm -rf opencti-java/src/main/java/io/opencti/database/stix/model/
rm -rf opencti-java/src/main/java/io/opencti/database/stix/representative/

# 移动StixRepresentative.java
mv opencti-java/src/main/java/io/opencti/database/stix/representative/StixRepresentative.java \
   opencti-java/src/main/java/io/opencti/database/stix/StixRepresentative.java
```

### 步骤2: 调整包结构

```bash
# 创建新的包结构
mkdir -p opencti-java/src/main/java/io/opencti/types/stix/common
mkdir -p opencti-java/src/main/java/io/opencti/types/stix/sdo
mkdir -p opencti-java/src/main/java/io/opencti/types/stix/sco
mkdir -p opencti-java/src/main/java/io/opencti/types/stix/sro
mkdir -p opencti-java/src/main/java/io/opencti/types/stix/smo
mkdir -p opencti-java/src/main/java/io/opencti/types/store
mkdir -p opencti-java/src/main/java/io/opencti/schema/stix
mkdir -p opencti-java/src/main/java/io/opencti/schema/constants

# 移动现有类
mv opencti-java/src/main/java/io/opencti/common/types/stix/*.java \
   opencti-java/src/main/java/io/opencti/types/stix/common/

mv opencti-java/src/main/java/io/opencti/common/types/stix/sdo/*.java \
   opencti-java/src/main/java/io/opencti/types/stix/sdo/

mv opencti-java/src/main/java/io/opencti/common/types/stix/sco/*.java \
   opencti-java/src/main/java/io/opencti/types/stix/sco/

mv opencti-java/src/main/java/io/opencti/common/types/stix/sro/*.java \
   opencti-java/src/main/java/io/opencti/types/stix/sro/

mv opencti-java/src/main/java/io/opencti/common/types/stix/smo/*.java \
   opencti-java/src/main/java/io/opencti/types/stix/smo/

mv opencti-java/src/main/java/io/opencti/common/types/store/*.java \
   opencti-java/src/main/java/io/opencti/types/store/

# 删除旧的common.types包
rm -rf opencti-java/src/main/java/io/opencti/common/types/
```

### 步骤3: 更新import语句

所有被移动的类需要更新package声明和import语句。

### 步骤4: 编译验证

```bash
cd opencti-java
mvn clean compile -DskipTests
```

---

## 六、最终项目结构对比

### 源码结构 vs Java结构

| 源码文件 | Java文件 | 包路径 |
|---------|---------|--------|
| `types/stix-2-1-common.d.ts` | `types/stix/common/*.java` | `io.opencti.types.stix.common` |
| `types/stix-2-1-sdo.d.ts` | `types/stix/sdo/*.java` | `io.opencti.types.stix.sdo` |
| `types/stix-2-1-sco.d.ts` | `types/stix/sco/*.java` | `io.opencti.types.stix.sco` |
| `types/stix-2-1-sro.d.ts` | `types/stix/sro/*.java` | `io.opencti.types.stix.sro` |
| `types/stix-2-1-smo.d.ts` | `types/stix/smo/*.java` | `io.opencti.types.stix.smo` |
| `types/store.d.ts` | `types/store/*.java` | `io.opencti.types.store` |
| `database/stix.ts` | `database/stix/StixCoreRelationshipsMapping.java` | `io.opencti.database.stix` |
| `database/stix-2-1-converter.ts` | `database/stix/Stix21Converter.java` | `io.opencti.database.stix` |
| `database/stix-2-0-converter.ts` | `database/stix/Stix20Converter.java` | `io.opencti.database.stix` |
| `database/stix-common-converter.ts` | `database/stix/StixConverter.java` | `io.opencti.database.stix` |
| `database/stix-converter-utils.ts` | `database/stix/StixConverterUtils.java` | `io.opencti.database.stix` |
| `database/stix-representative.ts` | `database/stix/StixRepresentative.java` | `io.opencti.database.stix` |
| `database/stix-ref.ts` | `database/stix/StixRefMapping.java` | `io.opencti.database.stix` |

---

## 七、优势分析

### 7.1 与源码保持一致

- ✅ 完全遵循源码的types/database分层
- ✅ 一比一映射源码文件结构
- ✅ 职责清晰，易于维护

### 7.2 消除重复

- ✅ 删除所有重复类
- ✅ 统一Extension定义在types层
- ✅ 转换逻辑统一在database层

### 7.3 易于理解和维护

- ✅ 新开发者可以快速理解项目结构
- ✅ 与源码文档对应，便于参考
- ✅ 便于后续功能扩展

---

## 八、注意事项

1. **移动类时需要更新所有import语句**
2. **保持类的原有功能不变**
3. **每个子任务完成后必须编译验证**
4. **更新MODULE_OVERVIEW.md文档**
5. **更新项目重写计划.md文档**

---

**方案制定时间**: 2026-03-11  
**制定人**: AI Assistant  
**版本**: v1.0
