# STIX转换器模块重写检查清单 (checklist.md)

## 一、源码阅读检查

### 1.1 基础文件阅读

| 文件 | 状态 | 阅读日期 | 备注 |
|------|------|----------|------|
| `database/stix-2-0-converter.ts` | ⬜ | | STIX 2.0转换器 |
| `database/stix-2-1-converter.ts` | ⬜ | | STIX 2.1转换器（核心） |
| `database/stix-common-converter.ts` | ⬜ | | 通用转换器入口 |
| `database/stix-converter-utils.ts` | ⬜ | | 转换工具函数 |
| `database/stix-ref.ts` | ⬜ | | STIX引用关系映射 |
| `database/stix-representative.ts` | ⬜ | | STIX实体表示提取 |
| `database/stix.ts` | ⬜ | | STIX核心定义和映射 |

### 1.2 依赖类型文件阅读

| 文件 | 状态 | 阅读日期 | 备注 |
|------|------|----------|------|
| `types/stix-2-0-common.d.ts` | ⬜ | | STIX 2.0基础类型 |
| `types/stix-2-0-sdo.d.ts` | ⬜ | | STIX 2.0 SDO类型 |
| `types/stix-2-0-smo.d.ts` | ⬜ | | STIX 2.0 SMO类型 |
| `types/stix-2-1-common.d.ts` | ⬜ | | STIX 2.1基础类型 |
| `types/stix-2-1-sdo.d.ts` | ⬜ | | STIX 2.1 SDO类型 |
| `types/stix-2-1-sco.d.ts` | ⬜ | | STIX 2.1 SCO类型 |
| `types/stix-2-1-sro.d.ts` | ⬜ | | STIX 2.1 SRO类型 |
| `types/stix-2-1-smo.d.ts` | ⬜ | | STIX 2.1 SMO类型 |
| `types/stix-2-1-extensions.ts` | ⬜ | | STIX扩展定义 |
| `types/store.d.ts` | ⬜ | | 存储类型定义 |

### 1.3 Schema文件阅读

| 文件 | 状态 | 阅读日期 | 备注 |
|------|------|----------|------|
| `schema/stixDomainObject.ts` | ⬜ | | STIX域对象Schema |
| `schema/stixCyberObservable.ts` | ⬜ | | STIX网络可观测Schema |
| `schema/stixCoreRelationship.ts` | ⬜ | | STIX核心关系Schema |
| `schema/stixRefRelationship.ts` | ⬜ | | STIX引用关系Schema |
| `schema/general.js` | ⬜ | | 通用Schema定义 |

---

## 二、代码实现检查

### 2.1 T1: 基础模型和常量定义

| 检查项 | 状态 | 备注 |
|--------|------|------|
| StixConstants.java 创建 | ⬜ | |
| - STIX_SPEC_VERSION 常量 | ⬜ | |
| - MAX_TRANSIENT_STIX_IDS 常量 | ⬜ | |
| - FROM_START_STR/UNTIL_END_STR 常量 | ⬜ | |
| - 关系类型常量 (REL_BUILT_IN/NEW/EXTENDED) | ⬜ | |
| StixExtensions.java 创建 | ⬜ | |
| - STIX_EXT_OCTI 常量 | ⬜ | |
| - STIX_EXT_OCTI_SCO 常量 | ⬜ | |
| - STIX_EXT_MITRE 常量 | ⬜ | |
| StixOpenctiExtension.java 创建 | ⬜ | |
| - extensionType 属性 | ⬜ | |
| - id/type 属性 | ⬜ | |
| - 时间属性 (createdAt/updatedAt/modifiedAt) | ⬜ | |
| - aliases/files/stixIds 属性 | ⬜ | |
| - isInferred 属性 | ⬜ | |
| - grantedRefs/creatorIds 属性 | ⬜ | |
| - assigneeIds/participantIds 属性 | ⬜ | |
| - authorizedMembers 属性 | ⬜ | |
| - workflowId/labelsIds 属性 | ⬜ | |
| - createdByRefId/createdByRefType 属性 | ⬜ | |
| - pirInformation/metrics 属性 | ⬜ | |
| StixMitreExtension.java 创建 | ⬜ | |
| - extensionType 属性 | ⬜ | |
| - id/detection 属性 | ⬜ | |
| - permissionsRequired/platforms 属性 | ⬜ | |
| - collectionLayers 属性 | ⬜ | |
| StixBundle.java 创建 | ⬜ | |
| - id 属性 (bundle--uuid格式) | ⬜ | |
| - specVersion 属性 | ⬜ | |
| - type 属性 | ⬜ | |
| - objects 属性 | ⬜ | |
| **编译验证** | ⬜ | mvn compile -DskipTests |

### 2.2 T2: 转换工具函数

| 检查项 | 状态 | 备注 |
|--------|------|------|
| StixConverterUtils.java 创建 | ⬜ | |
| - assertType() 方法 | ⬜ | |
| - convertToStixDate(Date) 方法 | ⬜ | |
| - convertToStixDate(String) 方法 | ⬜ | |
| - cleanObject() 方法 | ⬜ | |
| - isValidStix() 方法 | ⬜ | |
| - convertObjectReferences() 方法 | ⬜ | |
| **编译验证** | ⬜ | mvn compile -DskipTests |

### 2.3 T3: STIX 2.0转换器

| 检查项 | 状态 | 备注 |
|--------|------|------|
| Stix20Converter.java 创建 | ⬜ | |
| - buildStixId() 方法 | ⬜ | |
| - convertTypeToStix2Type() 方法 | ⬜ | |
| - buildKillChainPhases() 方法 | ⬜ | |
| - buildExternalReferences() 方法 | ⬜ | |
| - buildStixObject() 方法 | ⬜ | |
| - buildStixDomain() 方法 | ⬜ | |
| - convertMalwareToStix() 方法 | ⬜ | |
| - convertReportToStix() 方法 | ⬜ | |
| - convertNoteToStix() 方法 | ⬜ | |
| - convertObservedDataToStix() 方法 | ⬜ | |
| - convertOpinionToStix() 方法 | ⬜ | |
| - convertStoreToStix_2_0() 方法 | ⬜ | |
| 自定义实体类型处理 | ⬜ | Task/Feedback/Case等 |
| **编译验证** | ⬜ | mvn compile -DskipTests |

### 2.4 T4: STIX 2.1转换器 - 基础构建

| 检查项 | 状态 | 备注 |
|--------|------|------|
| Stix21Converter.java 基础框架创建 | ⬜ | |
| - isTrustedStixId() 方法 | ⬜ | |
| - convertTypeToStixType() 方法 | ⬜ | |
| - buildOCTIExtensions() 方法 | ⬜ | |
| - buildMITREExtensions() 方法 | ⬜ | |
| - buildStixObject() 方法 | ⬜ | |
| - buildStixDomain() 方法 | ⬜ | |
| - buildStixRelationship() 方法 | ⬜ | |
| - buildStixMarkings() 方法 | ⬜ | |
| - buildStixCyberObservable() 方法 | ⬜ | |
| - buildKillChainPhases() 方法 | ⬜ | |
| - buildExternalReferences() 方法 | ⬜ | |
| - buildWindowsRegistryValueType() 方法 | ⬜ | |
| - buildEmailBodyMultipart() 方法 | ⬜ | |
| **编译验证** | ⬜ | mvn compile -DskipTests |

### 2.5 T5: STIX 2.1转换器 - SDO转换

| 检查项 | 状态 | 备注 |
|--------|------|------|
| SDO转换方法实现 | ⬜ | |
| - convertIdentityToStix() | ⬜ | 身份 |
| - convertLocationToStix() | ⬜ | 位置 |
| - convertIncidentToStix() | ⬜ | 事件 |
| - convertCampaignToStix() | ⬜ | 攻击活动 |
| - convertToolToStix() | ⬜ | 工具 |
| - convertVulnerabilityToStix() | ⬜ | 漏洞 (含CVSS) |
| - convertThreatActorGroupToStix() | ⬜ | 威胁行为者组 |
| - convertInfrastructureToStix() | ⬜ | 基础设施 |
| - convertIntrusionSetToStix() | ⬜ | 入侵集 |
| - convertCourseOfActionToStix() | ⬜ | 应对措施 |
| - convertMalwareToStix() | ⬜ | 恶意软件 |
| - convertAttackPatternToStix() | ⬜ | 攻击模式 |
| - convertReportToStix() | ⬜ | 报告 |
| - convertNoteToStix() | ⬜ | 笔记 |
| - convertObservedDataToStix() | ⬜ | 观测数据 |
| - convertOpinionToStix() | ⬜ | 意见 |
| - convertInternalToStix() | ⬜ | 内部对象 |
| CVSS评分属性处理 | ⬜ | CVSS2/3/4 |
| **编译验证** | ⬜ | mvn compile -DskipTests |

### 2.6 T6: STIX 2.1转换器 - SCO转换

| 检查项 | 状态 | 备注 |
|--------|------|------|
| SCO转换方法实现 | ⬜ | |
| - convertArtifactToStix() | ⬜ | 工件 |
| - convertAutonomousSystemToStix() | ⬜ | 自治系统 |
| - convertCryptocurrencyWalletToStix() | ⬜ | 加密货币钱包 |
| - convertCryptographicKeyToStix() | ⬜ | 加密密钥 |
| - convertDirectoryToStix() | ⬜ | 目录 |
| - convertDomainNameToStix() | ⬜ | 域名 |
| - convertEmailAddressToStix() | ⬜ | 邮箱地址 |
| - convertEmailMessageToStix() | ⬜ | 邮件消息 |
| - convertFileToStix() | ⬜ | 文件 |
| - convertHostnameToStix() | ⬜ | 主机名 |
| - convertIPv4AddressToStix() | ⬜ | IPv4地址 |
| - convertIPv6AddressToStix() | ⬜ | IPv6地址 |
| - convertMacAddressToStix() | ⬜ | MAC地址 |
| - convertMutexToStix() | ⬜ | 互斥体 |
| - convertNetworkTrafficToStix() | ⬜ | 网络流量 |
| - convertProcessToStix() | ⬜ | 进程 |
| - convertSoftwareToStix() | ⬜ | 软件 |
| - convertTextToStix() | ⬜ | 文本 |
| - convertBankAccountToStix() | ⬜ | 银行账户 |
| - convertCredentialToStix() | ⬜ | 凭证 |
| - convertTrackingNumberToStix() | ⬜ | 追踪号 |
| - convertPhoneNumberToStix() | ⬜ | 电话号码 |
| - convertMediaContentToStix() | ⬜ | 媒体内容 |
| - convertPaymentCardToStix() | ⬜ | 支付卡 |
| - convertURLToStix() | ⬜ | URL |
| - convertUserAgentToStix() | ⬜ | 用户代理 |
| - convertUserAccountToStix() | ⬜ | 用户账户 |
| - convertWindowsRegistryKeyToStix() | ⬜ | 注册表键 |
| - convertX509CertificateToStix() | ⬜ | X509证书 |
| - convertPersonaToStix() | ⬜ | 角色 |
| - convertSSHKeyToStix() | ⬜ | SSH密钥 |
| - convertWindowsRegistryValueToStix() | ⬜ | 注册表值 |
| - convertEmailMimePartToStix() | ⬜ | 邮件MIME部分 |
| 扩展属性处理 | ⬜ | OCTI_SCO扩展 |
| **编译验证** | ⬜ | mvn compile -DskipTests |

### 2.7 T7: STIX 2.1转换器 - SRO/SMO转换

| 检查项 | 状态 | 备注 |
|--------|------|------|
| SRO转换方法实现 | ⬜ | |
| - checkInstanceCompletion() | ⬜ | |
| - convertRelationToStix() | ⬜ | 关系 |
| - convertSightingToStix() | ⬜ | 目击 |
| - convertInPirRelToStix() | ⬜ | PIR关系 |
| SMO转换方法实现 | ⬜ | |
| - convertMarkingToStix() | ⬜ | 标记定义 |
| - convertLabelToStix() | ⬜ | 标签 |
| - convertKillChainPhaseToStix() | ⬜ | 杀伤链阶段 |
| - convertExternalReferenceToStix() | ⬜ | 外部引用 |
| 主转换入口 | ⬜ | |
| - convertToStix_2_1() | ⬜ | 内部转换 |
| - convertStoreToStix_2_1() | ⬜ | 主入口 |
| - buildStixBundle() | ⬜ | Bundle构建 |
| - idsValuesRemap() | ⬜ | ID重映射 |
| 转换器注册 | ⬜ | |
| - registerStixDomainConverter() | ⬜ | |
| - registerStixMetaConverter() | ⬜ | |
| - registerStixRepresentativeConverter() | ⬜ | |
| **编译验证** | ⬜ | mvn compile -DskipTests |

### 2.8 T8: STIX实体表示

| 检查项 | 状态 | 备注 |
|--------|------|------|
| StixRepresentative.java 创建 | ⬜ | |
| - extractStixRepresentative() | ⬜ | |
| - extractStixRepresentativeForUser() | ⬜ | |
| - extractUserAccessPropertiesFromSighting() | ⬜ | |
| - extractUserAccessPropertiesFromRelationship() | ⬜ | |
| - extractUserAccessPropertiesFromStixObject() | ⬜ | |
| 所有实体类型表示提取 | ⬜ | 61种 |
| **编译验证** | ⬜ | mvn compile -DskipTests |

### 2.9 T9: STIX核心定义和映射

| 检查项 | 状态 | 备注 |
|--------|------|------|
| StixCoreRelationshipsMapping.java 创建 | ⬜ | |
| - stixCoreRelationshipsMapping 映射表 | ⬜ | 300+条规则 |
| - cleanStixIds() | ⬜ | |
| - onlyStableStixIds() | ⬜ | |
| - checkStixCoreRelationshipMapping() | ⬜ | |
| - isRelationBuiltin() | ⬜ | |
| - checkRelationshipRef() | ⬜ | |
| StixRefMapping.java 创建 | ⬜ | |
| - schemaRelationsRefTypesMapping() | ⬜ | |
| StixConverter.java 创建 | ⬜ | |
| - convertStoreToStix() | ⬜ | 统一入口 |
| **编译验证** | ⬜ | mvn compile -DskipTests |

### 2.10 T10: 整合测试

| 检查项 | 状态 | 备注 |
|--------|------|------|
| StixConverterUtilsTest.java 创建 | ⬜ | |
| - 日期转换测试 | ⬜ | |
| - 对象清理测试 | ⬜ | |
| - STIX验证测试 | ⬜ | |
| Stix20ConverterTest.java 创建 | ⬜ | |
| - 各实体类型转换测试 | ⬜ | |
| Stix21ConverterTest.java 创建 | ⬜ | |
| - SDO转换测试 | ⬜ | |
| - SCO转换测试 | ⬜ | |
| - SRO转换测试 | ⬜ | |
| - SMO转换测试 | ⬜ | |
| - Bundle构建测试 | ⬜ | |
| StixRepresentativeTest.java 创建 | ⬜ | |
| - 表示提取测试 | ⬜ | |
| StixCoreRelationshipsMappingTest.java 创建 | ⬜ | |
| - 关系映射检查测试 | ⬜ | |
| **测试通过** | ⬜ | mvn test |

---

## 三、注释规范检查

### 3.1 类注释检查

| 文件 | 原文件路径注释 | 状态 |
|------|----------------|------|
| StixConstants.java | database/stix.ts | ⬜ |
| StixExtensions.java | types/stix-2-1-extensions.ts | ⬜ |
| StixOpenctiExtension.java | types/stix-2-1-extensions.ts | ⬜ |
| StixMitreExtension.java | types/stix-2-1-extensions.ts | ⬜ |
| StixBundle.java | database/stix-2-1-converter.ts | ⬜ |
| StixConverterUtils.java | database/stix-converter-utils.ts | ⬜ |
| Stix20Converter.java | database/stix-2-0-converter.ts | ⬜ |
| Stix21Converter.java | database/stix-2-1-converter.ts | ⬜ |
| StixRepresentative.java | database/stix-representative.ts | ⬜ |
| StixCoreRelationshipsMapping.java | database/stix.ts | ⬜ |
| StixRefMapping.java | database/stix-ref.ts | ⬜ |
| StixConverter.java | database/stix-common-converter.ts | ⬜ |

### 3.2 方法注释检查

每个方法必须有注释说明原方法路径，格式如下：
```java
/**
 * 原文件: database/stix-2-1-converter.ts
 * 原方法: convertMalwareToStix
 */
public StixMalware convertMalwareToStix(StoreEntity instance, String type) {
    // ...
}
```

---

## 四、功能验收检查

### 4.1 STIX 2.0转换验收

| 实体类型 | 转换测试 | 状态 |
|----------|----------|------|
| Malware | ⬜ | |
| Report | ⬜ | |
| Note | ⬜ | |
| ObservedData | ⬜ | |
| Opinion | ⬜ | |

### 4.2 STIX 2.1 SDO转换验收

| 实体类型 | 转换测试 | 状态 |
|----------|----------|------|
| Identity (Individual) | ⬜ | |
| Identity (Organization) | ⬜ | |
| Identity (Sector) | ⬜ | |
| Identity (System) | ⬜ | |
| Location (Region) | ⬜ | |
| Location (Country) | ⬜ | |
| Location (City) | ⬜ | |
| Location (Position) | ⬜ | |
| Incident | ⬜ | |
| Campaign | ⬜ | |
| Tool | ⬜ | |
| Vulnerability | ⬜ | |
| ThreatActorGroup | ⬜ | |
| Infrastructure | ⬜ | |
| IntrusionSet | ⬜ | |
| CourseOfAction | ⬜ | |
| Malware | ⬜ | |
| AttackPattern | ⬜ | |
| Report | ⬜ | |
| Note | ⬜ | |
| ObservedData | ⬜ | |
| Opinion | ⬜ | |

### 4.3 STIX 2.1 SCO转换验收

| 实体类型 | 转换测试 | 状态 |
|----------|----------|------|
| Artifact | ⬜ | |
| AutonomousSystem | ⬜ | |
| CryptocurrencyWallet | ⬜ | |
| CryptographicKey | ⬜ | |
| Directory | ⬜ | |
| DomainName | ⬜ | |
| EmailAddress | ⬜ | |
| EmailMessage | ⬜ | |
| File | ⬜ | |
| Hostname | ⬜ | |
| IPv4Address | ⬜ | |
| IPv6Address | ⬜ | |
| MacAddress | ⬜ | |
| Mutex | ⬜ | |
| NetworkTraffic | ⬜ | |
| Process | ⬜ | |
| Software | ⬜ | |
| Text | ⬜ | |
| BankAccount | ⬜ | |
| Credential | ⬜ | |
| TrackingNumber | ⬜ | |
| PhoneNumber | ⬜ | |
| MediaContent | ⬜ | |
| PaymentCard | ⬜ | |
| URL | ⬜ | |
| UserAgent | ⬜ | |
| UserAccount | ⬜ | |
| WindowsRegistryKey | ⬜ | |
| X509Certificate | ⬜ | |
| Persona | ⬜ | |
| SSHKey | ⬜ | |
| WindowsRegistryValueType | ⬜ | |
| EmailMimePartType | ⬜ | |

### 4.4 STIX 2.1 SRO转换验收

| 实体类型 | 转换测试 | 状态 |
|----------|----------|------|
| Relationship | ⬜ | |
| Sighting | ⬜ | |
| PIR Relationship | ⬜ | |

### 4.5 STIX 2.1 SMO转换验收

| 实体类型 | 转换测试 | 状态 |
|----------|----------|------|
| MarkingDefinition | ⬜ | |
| Label | ⬜ | |
| KillChainPhase | ⬜ | |
| ExternalReference | ⬜ | |

---

## 五、最终验收

### 5.1 编译验收

| 检查项 | 命令 | 状态 |
|--------|------|------|
| 编译通过 | `mvn compile -DskipTests` | ⬜ |
| 无警告 | `mvn compile -DskipTests` | ⬜ |

### 5.2 测试验收

| 检查项 | 命令 | 状态 |
|--------|------|------|
| 单元测试通过 | `mvn test` | ⬜ |
| 测试覆盖率 > 80% | `mvn jacoco:report` | ⬜ |

### 5.3 文档验收

| 检查项 | 状态 |
|--------|------|
| MODULE_OVERVIEW.md 更新 | ⬜ |
| 项目重写计划.md 更新 | ⬜ |

### 5.4 代码统计

| 指标 | 预估 | 实际 |
|------|------|------|
| Java文件数 | ~15 | |
| 代码行数 | ~4500-5000 | |
| 测试文件数 | ~5 | |
| 测试用例数 | ~100+ | |

---

## 六、问题记录

| 日期 | 问题描述 | 解决方案 | 状态 |
|------|----------|----------|------|
| | | | |

---

## 签字确认

| 角色 | 姓名 | 日期 | 签字 |
|------|------|------|------|
| 开发者 | | | |
| 审核者 | | | |
