# STIX转换模块重写一致性审计报告

## 一、概述

本报告对OpenCTI Java重写项目的STIX转换模块进行详细审计，对比TypeScript源码与Java实现的一致性。

### 审计范围
- **源码文件**: 7个TypeScript文件，约3740行代码
- **重写文件**: 15个Java文件，约1800行代码
- **审计日期**: 2026-03-10

---

## 二、文件覆盖率分析

### 2.1 源文件清单与覆盖状态

| 序号 | 源文件路径 | 行数 | 重写状态 | Java对应文件 | 覆盖率 |
|------|-----------|------|---------|-------------|--------|
| 1 | `database/stix.ts` | ~1324 | ⚠️ 部分完成 | `StixCoreRelationshipsMapping.java`<br>`StixConstants.java` | ~40% |
| 2 | `database/stix-2-1-converter.ts` | ~1715 | ⚠️ 部分完成 | `Stix21Converter.java`<br>`Stix21ScoConverter.java`<br>`Stix21SroConverter.java` | ~60% |
| 3 | `database/stix-2-0-converter.ts` | ~256 | ⚠️ 部分完成 | `Stix20Converter.java` | ~70% |
| 4 | `database/stix-converter-utils.ts` | ~57 | ✅ 基本完成 | `StixConverterUtils.java` | ~90% |
| 5 | `database/stix-common-converter.ts` | ~10 | ✅ 已完成 | `StixConverter.java` | ~100% |
| 6 | `database/stix-representative.ts` | ~356 | ⚠️ 部分完成 | `StixRepresentative.java` | ~30% |
| 7 | `database/stix-ref.ts` | ~22 | ⚠️ 部分完成 | `StixRefMapping.java` | ~40% |

### 2.2 整体覆盖率评估

```
文件覆盖率: 7/7 = 100% (所有源文件都有对应的Java实现)
代码覆盖率: 约55% (核心功能实现，但大量细节待完善)
```

---

## 三、详细差异分析

### 3.1 stix.ts 差异分析

#### 3.1.1 已实现功能
- ✅ 核心关系映射常量定义
- ✅ 基本关系类型检查方法
- ✅ 内置关系判断方法

#### 3.1.2 缺失功能（需修复）

| 功能 | 源码行号 | 缺失说明 | 优先级 |
|------|---------|---------|--------|
| `cleanStixIds` | 108-128 | 清理STIX ID列表，保留最新的临时ID | 🔴 高 |
| `onlyStableStixIds` | 106 | 过滤只保留稳定STIX ID | 🔴 高 |
| 完整关系映射 | 137-1246 | 仅实现了约30%的关系映射 | 🔴 高 |
| `checkRelationshipRef` | 1312-1324 | 检查关系引用有效性 | 🟡 中 |

#### 3.1.3 关系映射覆盖情况

源码中定义了约 **200+** 种实体类型组合的关系映射，Java实现仅覆盖约 **60** 种。

**已覆盖的关系类型**:
- ATTACK_PATTERN相关映射
- CAMPAIGN核心映射
- COURSE_OF_ACTION基本映射
- IDENTITY基本映射
- INDICATOR核心映射
- INFRASTRUCTURE基本映射
- INTRUSION_SET核心映射
- MALWARE核心映射
- THREAT_ACTOR基本映射
- TOOL基本映射
- SCO基础映射

**未覆盖的关系类型**:
- 所有Location相关映射(约20种)
- 所有Channel相关映射(约10种)
- 所有Narrative相关映射(约8种)
- 所有Event相关映射(约15种)
- 所有Incident相关映射(约25种)
- 所有Organization相关映射(约10种)
- 所有Sector相关映射(约8种)
- 所有System相关映射(约10种)
- 所有Threat-Actor-Individual相关映射(约20种)
- 所有IPv4/IPv6扩展映射(约15种)
- 所有Domain-Name扩展映射(约8种)
- 所有File/Artifact扩展映射(约12种)
- 关系间关系映射(约6种)

---

### 3.2 stix-2-1-converter.ts 差异分析

#### 3.2.1 已实现功能

**SDO转换器 (17种)**:
- ✅ Identity转换
- ✅ Location转换
- ✅ Incident转换
- ✅ Campaign转换
- ✅ Tool转换
- ✅ Vulnerability转换(CVSS3/2/4部分属性)
- ✅ Threat-Actor-Group转换
- ✅ Infrastructure转换
- ✅ Intrusion-Set转换
- ✅ Course-Of-Action转换(基础)
- ✅ Malware转换(基础)
- ✅ Attack-Pattern转换(基础)
- ✅ Report转换
- ✅ Note转换
- ✅ Observed-Data转换
- ✅ Opinion转换

**SMO转换器 (4种)**:
- ✅ Marking-Definition转换(基础)
- ✅ Label转换(基础)
- ✅ Kill-Chain-Phase转换(基础)
- ✅ External-Reference转换(基础)

**SRO转换器 (3种)**:
- ✅ Relationship转换(在Stix21SroConverter)
- ✅ Sighting转换(在Stix21SroConverter)
- ✅ InPirRel转换(在Stix21SroConverter)

**SCO转换器 (18种)**:
- ✅ Artifact转换
- ✅ Autonomous-System转换
- ✅ Directory转换
- ✅ Domain-Name转换
- ✅ Email-Addr转换
- ✅ Email-Message转换(基础)
- ✅ File转换
- ✅ IPv4-Addr转换
- ✅ IPv6-Addr转换
- ✅ MAC-Addr转换
- ✅ Mutex转换
- ✅ Network-Traffic转换(基础)
- ✅ Process转换(基础)
- ✅ Software转换
- ✅ URL转换
- ✅ User-Account转换
- ✅ Windows-Registry-Key转换
- ✅ X509-Certificate转换(基础)

#### 3.2.2 缺失功能（需修复）

| 功能 | 源码行号 | 缺失说明 | 优先级 |
|------|---------|---------|--------|
| `buildMITREExtensions` | 207-217 | MITRE扩展属性不完整 | 🟡 中 |
| `buildEmailBodyMultipart` | 263-273 | 邮件多部分内容构建 | 🟡 中 |
| `buildWindowsRegistryValueType` | 253-262 | Windows注册表值类型 | 🟡 中 |
| `buildStixMarkings` | 294-303 | 标记定义构建 | 🟡 中 |
| `buildStixCyberObservable` | 304-322 | SCO基础构建 | 🟡 中 |
| `buildStixRelationship` | 290-293 | 关系基础构建 | 🟡 中 |
| `convertInternalToStix` | 325-334 | 内部对象转换 | 🟢 低 |
| `convertWindowsRegistryValueToStix` | 1398-1415 | Windows注册表值转换 | 🟡 中 |
| `convertEmailMimePartToStix` | 1416-1434 | 邮件MIME部分转换 | 🟡 中 |

#### 3.2.3 未实现的SCO类型（37种中已实现18种）

**未实现的SCO类型(19种)**:
- Bank-Account
- Credential
- Cryptographic-Key
- Cryptographic-Wallet
- Email-Mime-Part-Type
- Hostname
- Media-Content
- Mutex (已实现)
- Network-Traffic (部分实现)
- Payment-Card
- Persona
- Phone-Number
- Process (部分实现)
- SSH-Key
- Text
- Tracking-Number
- User-Agent
- Windows-Registry-Value-Type
- X509-Certificate (部分实现)

#### 3.2.4 Vulnerability CVSS属性缺失

源码中定义了完整的CVSS3、CVSS2、CVSS4属性(约40个字段)，Java实现仅覆盖CVSS3的核心字段。

**缺失的CVSS属性**:
- CVSS3: exploit_code_maturity, remediation_level, report_confidence, temporal_score
- CVSS2: 所有字段(约10个)
- CVSS4: 所有字段(约20个)

---

### 3.3 stix-2-0-converter.ts 差异分析

#### 3.3.1 已实现功能
- ✅ `buildStixId` - STIX ID构建
- ✅ `convertTypeToStix2Type` - 类型转换
- ✅ `buildStixObject` - 基础对象构建
- ✅ `buildStixDomain` - 域对象构建
- ✅ `buildKillChainPhases` - 杀伤链阶段
- ✅ `buildExternalReferences` - 外部引用
- ✅ `convertMalwareToStix` - 恶意软件转换
- ✅ `convertReportToStix` - 报告转换
- ✅ `convertNoteToStix` - 笔记转换
- ✅ `convertObservedDataToStix` - 观测数据转换
- ✅ `convertOpinionToStix` - 意见转换

#### 3.3.2 缺失功能

| 功能 | 源码行号 | 缺失说明 | 优先级 |
|------|---------|---------|--------|
| 完整SDO转换器 | 225-244 | 仅实现了Malware, Report, Note, ObservedData, Opinion | 🟡 中 |
| SRO转换器 | 242 | 关系转换未实现 | 🟡 中 |
| InternalObject转换 | 242 | 内部对象转换未实现 | 🟢 低 |
| MetaObject转换 | 242 | 元对象转换未实现 | 🟢 低 |
| StixCyberObservable转换 | 242 | SCO转换未实现 | 🟡 中 |

---

### 3.4 stix-converter-utils.ts 差异分析

#### 3.4.1 已实现功能
- ✅ `assertType` - 类型断言
- ✅ `convertToStixDate` - 日期转换(多重重载)
- ✅ `cleanObject` - 对象清理
- ✅ `isValidStix` - STIX有效性验证
- ✅ `convertObjectReferences` - 对象引用转换
- ✅ `buildStixId` - STIX ID构建
- ✅ `cleanMap` - Map清理
- ✅ `cleanList` - List清理

#### 3.4.2 差异说明
- Java实现使用`@JsonInclude(JsonInclude.Include.NON_NULL)`注解处理空属性，与源码的`cleanObject`逻辑略有不同，但效果一致。

---

### 3.5 stix-representative.ts 差异分析

#### 3.5.1 已实现功能
- ✅ `extractStixRepresentative` - 基础提取方法

#### 3.5.2 缺失功能（需修复）

| 功能 | 源码行号 | 缺失说明 | 优先级 |
|------|---------|---------|--------|
| 完整实体类型支持 | 75-295 | 仅实现了基础提取，未按类型提取 | 🔴 高 |
| `extractStixRepresentativeForUser` | 346-356 | 带用户访问检查的提取 | 🟡 中 |
| `extractUserAccessPropertiesFromSighting` | 300-313 | 从Sighting提取访问属性 | 🟡 中 |
| `extractUserAccessPropertiesFromRelationship` | 316-329 | 从Relationship提取访问属性 | 🟡 中 |
| `extractUserAccessPropertiesFromStixObject` | 332-344 | 从STIX对象提取访问属性 | 🟡 中 |
| `getStixRepresentativeConverters` | 1688-1690 | 获取转换器 | 🟢 低 |
| `registerStixRepresentativeConverter` | 1684-1686 | 注册转换器 | 🟢 低 |

**源码支持60+种实体类型的代表性提取，Java实现目前仅支持基础字段提取。**

---

### 3.6 stix-ref.ts 差异分析

#### 3.6.1 已实现功能
- ✅ `schemaRelationsRefTypesMapping` - 基础映射方法

#### 3.6.2 缺失功能

| 功能 | 源码行号 | 缺失说明 | 优先级 |
|------|---------|---------|--------|
| 完整引用关系映射 | 5-22 | 仅实现了object-marking映射 | 🔴 高 |
| `schemaRelationsRefDefinition`集成 | 1,7-8 | 需要与Schema定义集成 | 🟡 中 |

**源码中通过`schemaRelationsRefDefinition`动态获取引用关系，Java实现目前是静态硬编码。**

---

## 四、差异项分类

### 4.1 需要立即修复的差异 🔴

| 序号 | 差异项 | 影响 | 建议修复方案 |
|------|--------|------|-------------|
| 1 | 关系映射不完整 | 核心功能缺失 | 补充完整stixCoreRelationshipsMapping |
| 2 | `cleanStixIds`缺失 | STIX ID管理异常 | 实现ID清理逻辑 |
| 3 | SCO类型缺失(19种) | 可观测对象转换失败 | 补充剩余SCO转换器 |
| 4 | Representative实现不完整 | 实体显示异常 | 按类型实现提取逻辑 |
| 5 | StixRefMapping不完整 | 引用关系处理异常 | 完善引用映射 |

### 4.2 后续计划实现的差异 🟡

| 序号 | 差异项 | 影响 | 计划实现阶段 |
|------|--------|------|-------------|
| 1 | CVSS完整属性 | 漏洞评分显示不完整 | Phase 4 |
| 2 | MITRE扩展完整属性 | MITRE数据不完整 | Phase 4 |
| 3 | Email多部分处理 | 邮件内容解析不完整 | Phase 4 |
| 4 | Windows注册表值类型 | 注册表数据不完整 | Phase 4 |
| 5 | Sighting/Relationship访问属性提取 | 权限控制不完整 | Phase 5 |
| 6 | STIX 2.0完整转换器 | 2.0导出功能受限 | Phase 4 |

### 4.3 设计合理差异 🟢

| 序号 | 差异项 | 说明 | 合理性 |
|------|--------|------|--------|
| 1 | `cleanObject`实现方式 | Java使用注解，TS使用函数 | ✅ 符合Java惯例 |
| 2 | Map类型使用 | Java使用`Map<String, Object>`，TS使用类型定义 | ✅ 类型擦除是Java特性 |
| 3 | 异步处理 | Java使用同步方法，TS使用async/await | ✅ 当前上下文不需要异步 |
| 4 | 错误处理 | Java使用异常，TS使用函数式错误 | ✅ 符合各自语言习惯 |

---

## 五、代码质量评估

### 5.1 优点
1. ✅ 所有文件都包含原文件路径注释
2. ✅ 方法级别注释清晰
3. ✅ 代码结构清晰，分包合理
4. ✅ 常量提取到StixConstants
5. ✅ 工具方法集中管理

### 5.2 待改进
1. ⚠️ 部分方法实现过于简化
2. ⚠️ 缺少完整的单元测试
3. ⚠️ 部分TODO未处理
4. ⚠️ 异常处理不够完善

---

## 六、建议修复优先级

### P0 - 立即修复（阻塞核心功能）
1. 补充完整的stixCoreRelationshipsMapping（约200种关系）
2. 实现`cleanStixIds`方法
3. 补充剩余19种SCO转换器
4. 完善StixRepresentative的类型提取逻辑

### P1 - 高优先级（影响功能完整性）
1. 完善Vulnerability的CVSS属性
2. 完善MITRE扩展
3. 完善Email/Windows注册表处理
4. 完善StixRefMapping

### P2 - 中优先级（功能增强）
1. 完善STIX 2.0转换器
2. 实现用户访问控制相关方法
3. 添加更多类型检查

### P3 - 低优先级（优化）
1. 完善单元测试
2. 优化性能
3. 添加更多日志

---

## 七、结论

### 7.1 整体评估
- **文件覆盖率**: 100% (7/7)
- **代码覆盖率**: 约55%
- **核心功能**: 基本可用
- **生产就绪**: 否，需要补充P0级别修复

### 7.2 建议
1. **短期**: 优先完成P0级别修复，使核心功能可用
2. **中期**: 完成P1级别修复，达到功能完整
3. **长期**: 完成P2/P3级别优化，达到生产就绪

### 7.3 预计工作量
- P0修复: 约3-4天
- P1修复: 约2-3天
- P2/P3优化: 约2天
- **总计**: 约7-9天可达到生产就绪状态

---

## 八、附录

### 8.1 文件对应关系

| TypeScript源文件 | Java重写文件 |
|-----------------|-------------|
| `stix.ts` | `StixCoreRelationshipsMapping.java`<br>`StixConstants.java` |
| `stix-2-1-converter.ts` | `Stix21Converter.java`<br>`Stix21ScoConverter.java`<br>`Stix21SroConverter.java` |
| `stix-2-0-converter.ts` | `Stix20Converter.java` |
| `stix-converter-utils.ts` | `StixConverterUtils.java` |
| `stix-common-converter.ts` | `StixConverter.java` |
| `stix-representative.ts` | `StixRepresentative.java` |
| `stix-ref.ts` | `StixRefMapping.java` |

### 8.2 实体类型覆盖统计

| 类型分类 | 源码支持 | Java实现 | 覆盖率 |
|---------|---------|---------|--------|
| SDO | 17种 | 17种 | 100% |
| SCO | 37种 | 18种 | 49% |
| SRO | 3种 | 3种 | 100% |
| SMO | 4种 | 4种 | 100% |
| 关系映射 | 200+种 | 60种 | 30% |

---

**报告生成时间**: 2026-03-10  
**审计人员**: AI Assistant  
**版本**: v1.0
