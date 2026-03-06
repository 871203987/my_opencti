# STIX转换器模块任务分解 (tasks.md)

## 任务总览

| 任务ID | 任务名称 | 预估行数 | 预估时间 | 依赖 |
|--------|----------|----------|----------|------|
| T1 | 基础模型和常量定义 | ~300 | 0.5天 | 无 |
| T2 | 转换工具函数 | ~200 | 0.5天 | T1 |
| T3 | STIX 2.0转换器 | ~400 | 1天 | T1, T2 |
| T4 | STIX 2.1转换器 - 基础构建 | ~500 | 1天 | T1, T2 |
| T5 | STIX 2.1转换器 - SDO转换 | ~800 | 2天 | T4 |
| T6 | STIX 2.1转换器 - SCO转换 | ~1200 | 2天 | T4 |
| T7 | STIX 2.1转换器 - SRO/SMO转换 | ~400 | 1天 | T4 |
| T8 | STIX实体表示 | ~400 | 1天 | T4 |
| T9 | STIX核心定义和映射 | ~500 | 1天 | T1 |
| T10 | 整合测试 | ~200 | 1天 | T3-T9 |

**总计**: 约4900行代码，约10天工作量

---

## T1: 基础模型和常量定义

### 1.1 任务描述
创建STIX转换器所需的基础模型类和常量定义。

### 1.2 源文件映射
| 源文件 | 目标文件 |
|--------|----------|
| `types/stix-2-1-extensions.ts` | `model/StixExtensions.java` |
| `database/stix.ts` (常量部分) | `StixConstants.java` |

### 1.3 具体任务项

#### T1.1 创建StixConstants.java
```
原文件: database/stix.ts
路径: src/main/java/io/opencti/database/stix/StixConstants.java
```

内容:
- `STIX_SPEC_VERSION = "2.1"`
- `MAX_TRANSIENT_STIX_IDS = 200`
- `FROM_START_STR` / `UNTIL_END_STR` 常量
- 关系类型常量: `REL_BUILT_IN`, `REL_NEW`, `REL_EXTENDED`

#### T1.2 创建StixExtensions.java
```
原文件: types/stix-2-1-extensions.ts
路径: src/main/java/io/opencti/database/stix/model/StixExtensions.java
```

内容:
- `STIX_EXT_OCTI = "extension-definition--ea279b3d-ef0f-5a38-9d0d-6b11fe7f8f4f"`
- `STIX_EXT_OCTI_SCO = "extension-definition--950eb0c8-658a-4f5b-a1d2-707d338415cc"`
- `STIX_EXT_MITRE = "extension-definition--322b8f8b-ab4d-4043-a55d-827a2a0c4d2e"`

#### T1.3 创建StixOpenctiExtension.java
```
原文件: types/stix-2-1-extensions.ts
路径: src/main/java/io/opencti/database/stix/model/StixOpenctiExtension.java
```

属性:
- extensionType
- id, type
- createdAt, updatedAt, modifiedAt
- aliases, files, stixIds
- isInferred
- grantedRefs, creatorIds
- assigneeIds, participantIds
- authorizedMembers
- workflowId
- labelsIds
- createdByRefId, createdByRefType
- pirInformation, metrics

#### T1.4 创建StixMitreExtension.java
```
原文件: types/stix-2-1-extensions.ts
路径: src/main/java/io/opencti/database/stix/model/StixMitreExtension.java
```

属性:
- extensionType
- id (x_mitre_id)
- detection
- permissionsRequired
- platforms
- collectionLayers

#### T1.5 创建StixBundle.java
```
原文件: database/stix-2-1-converter.ts (buildStixBundle函数)
路径: src/main/java/io/opencti/database/stix/model/StixBundle.java
```

属性:
- id (bundle--uuid格式)
- specVersion
- type = "bundle"
- objects (List<StixObject>)

### 1.4 验收标准
- [ ] 所有常量定义完整
- [ ] 扩展类属性完整
- [ ] 编译通过

---

## T2: 转换工具函数

### 2.1 任务描述
创建STIX转换器通用的工具函数。

### 2.2 源文件映射
| 源文件 | 目标文件 |
|--------|----------|
| `database/stix-converter-utils.ts` | `converter/StixConverterUtils.java` |

### 2.3 具体任务项

#### T2.1 创建StixConverterUtils.java
```
原文件: database/stix-converter-utils.ts
路径: src/main/java/io/opencti/database/stix/converter/StixConverterUtils.java
```

方法:
- `assertType(String type, String instanceType)` - 类型断言
- `convertToStixDate(Date date)` - 日期转换(Date版本)
- `convertToStixDate(String date)` - 日期转换(String版本)
- `cleanObject(T data)` - 清理空属性
- `isValidStix(StixObject data)` - 验证STIX对象
- `convertObjectReferences(StoreEntity instance, boolean isInferred)` - 转换对象引用

### 2.4 验收标准
- [ ] 所有工具方法实现完整
- [ ] 边界条件处理正确
- [ ] 编译通过

---

## T3: STIX 2.0转换器

### 3.1 任务描述
实现STIX 2.0格式的转换器。

### 3.2 源文件映射
| 源文件 | 目标文件 |
|--------|----------|
| `database/stix-2-0-converter.ts` | `converter/Stix20Converter.java` |

### 3.3 具体任务项

#### T3.1 创建Stix20Converter.java
```
原文件: database/stix-2-0-converter.ts
路径: src/main/java/io/opencti/database/stix/converter/Stix20Converter.java
```

方法:
- `buildStixId(String instanceType, String standardId)` - 构建STIX ID
- `convertTypeToStix2Type(String type)` - 类型转换
- `buildKillChainPhases(StoreEntity instance)` - 构建杀伤链阶段
- `buildExternalReferences(StoreObject instance)` - 构建外部引用
- `buildStixObject(StoreObject instance)` - 构建基础STIX对象
- `buildStixDomain(StoreEntity instance)` - 构建STIX域对象
- `convertMalwareToStix(StoreEntity instance)` - 转换恶意软件
- `convertReportToStix(StoreEntity instance)` - 转换报告
- `convertNoteToStix(StoreEntity instance)` - 转换笔记
- `convertObservedDataToStix(StoreEntity instance)` - 转换观测数据
- `convertOpinionToStix(StoreEntity instance)` - 转换意见
- `convertStoreToStix_2_0(StoreCommon instance)` - 主转换入口

### 3.4 验收标准
- [ ] 所有转换方法实现完整
- [ ] 自定义实体类型处理正确
- [ ] 编译通过

---

## T4: STIX 2.1转换器 - 基础构建

### 4.1 任务描述
实现STIX 2.1转换器的基础构建方法。

### 4.2 源文件映射
| 源文件 | 目标文件 |
|--------|----------|
| `database/stix-2-1-converter.ts` (基础构建部分) | `converter/Stix21Converter.java` |

### 4.3 具体任务项

#### T4.1 创建Stix21Converter.java基础框架
```
原文件: database/stix-2-1-converter.ts
路径: src/main/java/io/opencti/database/stix/converter/Stix21Converter.java
```

基础方法:
- `isTrustedStixId(String stixId)` - 检查可信STIX ID
- `convertTypeToStixType(String type)` - 类型转换
- `buildOCTIExtensions(StoreObject instance)` - 构建OpenCTI扩展
- `buildMITREExtensions(StoreEntity instance)` - 构建MITRE扩展
- `buildStixObject(StoreObject instance)` - 构建基础STIX对象
- `buildStixDomain(StoreEntity instance)` - 构建STIX域对象
- `buildStixRelationship(StoreRelation instance)` - 构建STIX关系对象
- `buildStixMarkings(StoreEntity instance)` - 构建STIX标记对象
- `buildStixCyberObservable(StoreCyberObservable instance)` - 构建STIX网络可观测对象

#### T4.2 元数据构建方法
- `buildKillChainPhases(StoreEntity instance)` - 构建杀伤链阶段
- `buildExternalReferences(StoreObject instance)` - 构建外部引用
- `buildWindowsRegistryValueType(StoreCyberObservable instance)` - 构建注册表值类型
- `buildEmailBodyMultipart(StoreCyberObservable instance)` - 构建邮件多部分体

### 4.4 验收标准
- [ ] 所有基础构建方法实现完整
- [ ] 扩展构建正确
- [ ] 编译通过

---

## T5: STIX 2.1转换器 - SDO转换

### 5.1 任务描述
实现STIX域对象(SDO)的转换方法。

### 5.2 源文件映射
| 源文件 | 目标文件 |
|--------|----------|
| `database/stix-2-1-converter.ts` (SDO部分) | `converter/Stix21Converter.java` |

### 5.3 具体任务项

#### T5.1 SDO转换方法 (17种实体类型)
```
原文件: database/stix-2-1-converter.ts
路径: src/main/java/io/opencti/database/stix/converter/Stix21Converter.java
```

方法:
- `convertIdentityToStix(StoreEntity instance, String type)` - 转换身份
- `convertLocationToStix(StoreEntity instance, String type)` - 转换位置
- `convertIncidentToStix(StoreEntity instance, String type)` - 转换事件
- `convertCampaignToStix(StoreEntity instance, String type)` - 转换攻击活动
- `convertToolToStix(StoreEntity instance, String type)` - 转换工具
- `convertVulnerabilityToStix(StoreEntity instance, String type)` - 转换漏洞
- `convertThreatActorGroupToStix(StoreEntity instance, String type)` - 转换威胁行为者组
- `convertInfrastructureToStix(StoreEntity instance, String type)` - 转换基础设施
- `convertIntrusionSetToStix(StoreEntity instance, String type)` - 转换入侵集
- `convertCourseOfActionToStix(StoreEntity instance, String type)` - 转换应对措施
- `convertMalwareToStix(StoreEntity instance, String type)` - 转换恶意软件
- `convertAttackPatternToStix(StoreEntity instance, String type)` - 转换攻击模式
- `convertReportToStix(StoreEntity instance, String type)` - 转换报告
- `convertNoteToStix(StoreEntity instance, String type)` - 转换笔记
- `convertObservedDataToStix(StoreEntity instance, String type)` - 转换观测数据
- `convertOpinionToStix(StoreEntity instance, String type)` - 转换意见
- `convertInternalToStix(StoreEntity instance, String type)` - 转换内部对象

### 5.4 验收标准
- [ ] 所有17种SDO转换方法实现完整
- [ ] CVSS评分属性处理正确
- [ ] 编译通过

---

## T6: STIX 2.1转换器 - SCO转换

### 6.1 任务描述
实现STIX网络可观测对象(SCO)的转换方法。

### 6.2 源文件映射
| 源文件 | 目标文件 |
|--------|----------|
| `database/stix-2-1-converter.ts` (SCO部分) | `converter/Stix21Converter.java` |

### 6.3 具体任务项

#### T6.1 SCO转换方法 (37种可观测类型)
```
原文件: database/stix-2-1-converter.ts
路径: src/main/java/io/opencti/database/stix/converter/Stix21Converter.java
```

方法:
- `convertArtifactToStix(StoreCyberObservable instance, String type)` - 转换工件
- `convertAutonomousSystemToStix(StoreCyberObservable instance, String type)` - 转换自治系统
- `convertCryptocurrencyWalletToStix(StoreCyberObservable instance, String type)` - 转换加密货币钱包
- `convertCryptographicKeyToStix(StoreCyberObservable instance, String type)` - 转换加密密钥
- `convertDirectoryToStix(StoreCyberObservable instance, String type)` - 转换目录
- `convertDomainNameToStix(StoreCyberObservable instance, String type)` - 转换域名
- `convertEmailAddressToStix(StoreCyberObservable instance, String type)` - 转换邮箱地址
- `convertEmailMessageToStix(StoreCyberObservable instance, String type)` - 转换邮件消息
- `convertFileToStix(StoreCyberObservable instance, String type)` - 转换文件
- `convertHostnameToStix(StoreCyberObservable instance, String type)` - 转换主机名
- `convertIPv4AddressToStix(StoreCyberObservable instance, String type)` - 转换IPv4地址
- `convertIPv6AddressToStix(StoreCyberObservable instance, String type)` - 转换IPv6地址
- `convertMacAddressToStix(StoreCyberObservable instance, String type)` - 转换MAC地址
- `convertMutexToStix(StoreCyberObservable instance, String type)` - 转换互斥体
- `convertNetworkTrafficToStix(StoreCyberObservable instance, String type)` - 转换网络流量
- `convertProcessToStix(StoreCyberObservable instance, String type)` - 转换进程
- `convertSoftwareToStix(StoreCyberObservable instance, String type)` - 转换软件
- `convertTextToStix(StoreCyberObservable instance, String type)` - 转换文本
- `convertBankAccountToStix(StoreCyberObservable instance, String type)` - 转换银行账户
- `convertCredentialToStix(StoreCyberObservable instance, String type)` - 转换凭证
- `convertTrackingNumberToStix(StoreCyberObservable instance, String type)` - 转换追踪号
- `convertPhoneNumberToStix(StoreCyberObservable instance, String type)` - 转换电话号码
- `convertMediaContentToStix(StoreCyberObservable instance, String type)` - 转换媒体内容
- `convertPaymentCardToStix(StoreCyberObservable instance, String type)` - 转换支付卡
- `convertURLToStix(StoreCyberObservable instance, String type)` - 转换URL
- `convertUserAgentToStix(StoreCyberObservable instance, String type)` - 转换用户代理
- `convertUserAccountToStix(StoreCyberObservable instance, String type)` - 转换用户账户
- `convertWindowsRegistryKeyToStix(StoreCyberObservable instance, String type)` - 转换注册表键
- `convertX509CertificateToStix(StoreCyberObservable instance, String type)` - 转换X509证书
- `convertPersonaToStix(StoreCyberObservable instance, String type)` - 转换角色
- `convertSSHKeyToStix(StoreCyberObservable instance, String type)` - 转换SSH密钥
- `convertWindowsRegistryValueToStix(StoreCyberObservable instance)` - 转换注册表值
- `convertEmailMimePartToStix(StoreCyberObservable instance)` - 转换邮件MIME部分

### 6.4 验收标准
- [ ] 所有37种SCO转换方法实现完整
- [ ] 扩展属性处理正确
- [ ] 编译通过

---

## T7: STIX 2.1转换器 - SRO/SMO转换

### 7.1 任务描述
实现STIX关系对象(SRO)和元对象(SMO)的转换方法。

### 7.2 源文件映射
| 源文件 | 目标文件 |
|--------|----------|
| `database/stix-2-1-converter.ts` (SRO/SMO部分) | `converter/Stix21Converter.java` |

### 7.3 具体任务项

#### T7.1 SRO转换方法
```
原文件: database/stix-2-1-converter.ts
路径: src/main/java/io/opencti/database/stix/converter/Stix21Converter.java
```

方法:
- `checkInstanceCompletion(StoreRelation instance)` - 检查实例完整性
- `convertRelationToStix(StoreRelation instance)` - 转换关系
- `convertSightingToStix(StoreRelation instance)` - 转换目击
- `convertInPirRelToStix(StoreRelationPir instance)` - 转换PIR关系

#### T7.2 SMO转换方法
- `convertMarkingToStix(StoreEntity instance)` - 转换标记定义
- `convertLabelToStix(StoreEntity instance)` - 转换标签
- `convertKillChainPhaseToStix(StoreEntity instance)` - 转换杀伤链阶段
- `convertExternalReferenceToStix(StoreEntity instance)` - 转换外部引用

#### T7.3 主转换入口
- `convertToStix_2_1(StoreCommon instance)` - 内部转换方法
- `convertStoreToStix_2_1(StoreCommon instance)` - 主转换入口
- `buildStixBundle(List<StixObject> stixObjects)` - 构建STIX Bundle
- `idsValuesRemap(...)` - ID值重映射

#### T7.4 转换器注册
- `registerStixDomainConverter(String type, ConvertFn fn)` - 注册域转换器
- `registerStixMetaConverter(String type, ConvertFn fn)` - 注册元转换器
- `registerStixRepresentativeConverter(String type, RepresentativeFn fn)` - 注册表示转换器

### 7.4 验收标准
- [ ] 所有SRO转换方法实现完整
- [ ] 所有SMO转换方法实现完整
- [ ] 主转换入口正确
- [ ] 编译通过

---

## T8: STIX实体表示

### 8.1 任务描述
实现STIX实体表示提取功能。

### 8.2 源文件映射
| 源文件 | 目标文件 |
|--------|----------|
| `database/stix-representative.ts` | `representative/StixRepresentative.java` |

### 8.3 具体任务项

#### T8.1 创建StixRepresentative.java
```
原文件: database/stix-representative.ts
路径: src/main/java/io/opencti/database/stix/representative/StixRepresentative.java
```

方法:
- `extractStixRepresentative(StixObject stix, RepresentativeOptions options, boolean withArrowForRelationships)` - 提取表示
- `extractStixRepresentativeForUser(AuthContext context, AuthUser user, StixObject stix, boolean withArrowForRelationships)` - 带用户检查的提取
- `extractUserAccessPropertiesFromSighting(StixSighting sighting)` - 提取目击访问属性
- `extractUserAccessPropertiesFromRelationship(StixRelation relation)` - 提取关系访问属性
- `extractUserAccessPropertiesFromStixObject(StixObject instance)` - 提取对象访问属性

### 8.4 验收标准
- [ ] 所有实体类型的表示提取正确
- [ ] 用户访问检查正确
- [ ] 编译通过

---

## T9: STIX核心定义和映射

### 9.1 任务描述
实现STIX核心定义和关系映射。

### 9.2 源文件映射
| 源文件 | 目标文件 |
|--------|----------|
| `database/stix.ts` | `mapping/StixCoreRelationshipsMapping.java` |
| `database/stix-ref.ts` | `mapping/StixRefMapping.java` |
| `database/stix-common-converter.ts` | `converter/StixConverter.java` |

### 9.3 具体任务项

#### T9.1 创建StixCoreRelationshipsMapping.java
```
原文件: database/stix.ts
路径: src/main/java/io/opencti/database/stix/mapping/StixCoreRelationshipsMapping.java
```

内容:
- `stixCoreRelationshipsMapping` - 关系映射表 (300+条规则)
- `cleanStixIds(List<String> ids, int maxStixIds)` - 清理STIX ID
- `onlyStableStixIds(List<String> ids)` - 过滤稳定ID
- `checkStixCoreRelationshipMapping(String fromType, String toType, String relationshipType)` - 检查关系映射
- `isRelationBuiltin(StoreRelation instance)` - 判断内置关系
- `checkRelationshipRef(String fromType, String toType, String relationshipType)` - 检查关系引用

#### T9.2 创建StixRefMapping.java
```
原文件: database/stix-ref.ts
路径: src/main/java/io/opencti/database/stix/mapping/StixRefMapping.java
```

方法:
- `schemaRelationsRefTypesMapping()` - 获取关系引用类型映射

#### T9.3 创建StixConverter.java
```
原文件: database/stix-common-converter.ts
路径: src/main/java/io/opencti/database/stix/converter/StixConverter.java
```

方法:
- `convertStoreToStix(StoreCommon instance, Version version)` - 统一转换入口

### 9.4 验收标准
- [ ] 关系映射表完整
- [ ] 所有映射检查方法正确
- [ ] 编译通过

---

## T10: 整合测试

### 10.1 任务描述
创建单元测试，验证所有转换功能。

### 10.2 具体任务项

#### T10.1 创建StixConverterUtilsTest.java
```
路径: src/test/java/io/opencti/database/stix/converter/StixConverterUtilsTest.java
```

测试:
- 日期转换测试
- 对象清理测试
- STIX验证测试

#### T10.2 创建Stix20ConverterTest.java
```
路径: src/test/java/io/opencti/database/stix/converter/Stix20ConverterTest.java
```

测试:
- 各实体类型转换测试
- 边界条件测试

#### T10.3 创建Stix21ConverterTest.java
```
路径: src/test/java/io/opencti/database/stix/converter/Stix21ConverterTest.java
```

测试:
- SDO转换测试
- SCO转换测试
- SRO转换测试
- SMO转换测试
- Bundle构建测试

#### T10.4 创建StixRepresentativeTest.java
```
路径: src/test/java/io/opencti/database/stix/representative/StixRepresentativeTest.java
```

测试:
- 表示提取测试
- 用户访问测试

#### T10.5 创建StixCoreRelationshipsMappingTest.java
```
路径: src/test/java/io/opencti/database/stix/mapping/StixCoreRelationshipsMappingTest.java
```

测试:
- 关系映射检查测试
- 内置关系判断测试

### 10.3 验收标准
- [ ] 所有测试通过
- [ ] 测试覆盖率 > 80%

---

## 执行顺序

```
T1 (基础模型和常量)
  ├── T2 (转换工具函数)
  │     ├── T3 (STIX 2.0转换器)
  │     └── T4 (STIX 2.1基础构建)
  │           ├── T5 (SDO转换)
  │           ├── T6 (SCO转换)
  │           ├── T7 (SRO/SMO转换)
  │           └── T8 (实体表示)
  └── T9 (核心定义和映射)
        └── T10 (整合测试)
```

## 编译检查点

每个子任务完成后必须执行编译验证:
```bash
cd opencti-java
mvn compile -DskipTests
```
