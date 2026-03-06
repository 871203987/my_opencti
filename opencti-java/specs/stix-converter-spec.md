# STIX转换器模块重写规范 (spec.md)

## 一、模块概述

### 1.1 模块定位
STIX转换器模块是OpenCTI平台的核心数据转换层，负责将内部存储格式(StoreEntity/StoreRelation)与STIX标准格式(STIX 2.0/2.1)之间进行双向转换。

### 1.2 源文件清单

| 源文件 | 行数 | 功能说明 | 复杂度 |
|--------|------|----------|--------|
| `database/stix-2-0-converter.ts` | ~256 | STIX 2.0格式转换器 | ⭐⭐⭐⭐ |
| `database/stix-2-1-converter.ts` | ~1715 | STIX 2.1格式转换器（核心） | ⭐⭐⭐⭐⭐ |
| `database/stix-common-converter.ts` | ~10 | 通用转换器入口 | ⭐⭐ |
| `database/stix-converter-utils.ts` | ~57 | 转换工具函数 | ⭐⭐⭐ |
| `database/stix-ref.ts` | ~22 | STIX引用关系映射 | ⭐⭐ |
| `database/stix-representative.ts` | ~356 | STIX实体表示提取 | ⭐⭐⭐ |
| `database/stix.ts` | ~1324 | STIX核心定义和关系映射 | ⭐⭐⭐⭐⭐ |

**源码总计**: 约3740行TypeScript代码  
**预估Java代码**: 约4500-5000行

### 1.3 目标目录结构

```
src/main/java/io/opencti/database/stix/
├── converter/
│   ├── Stix20Converter.java           # STIX 2.0转换器
│   ├── Stix21Converter.java           # STIX 2.1转换器（核心）
│   ├── StixConverter.java             # 通用转换器入口
│   └── StixConverterUtils.java        # 转换工具函数
├── representative/
│   └── StixRepresentative.java        # STIX实体表示提取
├── mapping/
│   ├── StixCoreRelationshipsMapping.java  # STIX核心关系映射
│   └── StixRefMapping.java            # STIX引用关系映射
├── model/
│   ├── StixBundle.java                # STIX Bundle模型
│   └── StixExtensions.java            # STIX扩展定义
└── StixConstants.java                 # STIX常量定义
```

---

## 二、功能需求

### 2.1 STIX 2.0转换器 (`stix-2-0-converter.ts`)

#### 2.1.1 核心功能

| 功能 | 方法名 | 说明 |
|------|--------|------|
| 构建STIX ID | `buildStixId()` | 根据实体类型生成STIX ID |
| 类型转换 | `convertTypeToStix2Type()` | 将内部类型转换为STIX 2.0类型 |
| 构建Kill Chain Phases | `buildKillChainPhases()` | 构建杀伤链阶段数组 |
| 构建外部引用 | `buildExternalReferences()` | 构建外部引用数组 |
| 构建STIX对象 | `buildStixObject()` | 构建基础STIX对象 |
| 构建STIX域对象 | `buildStixDomain()` | 构建STIX域对象基础属性 |
| 转换恶意软件 | `convertMalwareToStix()` | 转换Malware实体 |
| 转换报告 | `convertReportToStix()` | 转换Report实体 |
| 转换笔记 | `convertNoteToStix()` | 转换Note实体 |
| 转换观测数据 | `convertObservedDataToStix()` | 转换ObservedData实体 |
| 转换意见 | `convertOpinionToStix()` | 转换Opinion实体 |
| 主转换函数 | `convertStoreToStix_2_0()` | 统一入口转换函数 |

#### 2.1.2 自定义实体类型
- Task容器
- Feedback容器
- Case-Incident容器
- Case-RFI容器
- Case-RFT容器

### 2.2 STIX 2.1转换器 (`stix-2-1-converter.ts`)

#### 2.2.1 核心功能

| 功能分类 | 方法名 | 说明 |
|----------|--------|------|
| **基础构建** | `buildStixObject()` | 构建基础STIX对象 |
| | `buildStixDomain()` | 构建STIX域对象 |
| | `buildStixRelationship()` | 构建STIX关系对象 |
| | `buildStixMarkings()` | 构建STIX标记对象 |
| | `buildStixCyberObservable()` | 构建STIX网络可观测对象 |
| **扩展构建** | `buildOCTIExtensions()` | 构建OpenCTI扩展 |
| | `buildMITREExtensions()` | 构建MITRE扩展 |
| **元数据构建** | `buildKillChainPhases()` | 构建杀伤链阶段 |
| | `buildExternalReferences()` | 构建外部引用 |
| | `buildWindowsRegistryValueType()` | 构建注册表值类型 |
| | `buildEmailBodyMultipart()` | 构建邮件多部分体 |
| **SDO转换** | `convertIdentityToStix()` | 转换身份实体 |
| | `convertLocationToStix()` | 转换位置实体 |
| | `convertIncidentToStix()` | 转换事件实体 |
| | `convertCampaignToStix()` | 转换攻击活动 |
| | `convertToolToStix()` | 转换工具 |
| | `convertVulnerabilityToStix()` | 转换漏洞 |
| | `convertThreatActorGroupToStix()` | 转换威胁行为者组 |
| | `convertInfrastructureToStix()` | 转换基础设施 |
| | `convertIntrusionSetToStix()` | 转换入侵集 |
| | `convertCourseOfActionToStix()` | 转换应对措施 |
| | `convertMalwareToStix()` | 转换恶意软件 |
| | `convertAttackPatternToStix()` | 转换攻击模式 |
| | `convertReportToStix()` | 转换报告 |
| | `convertNoteToStix()` | 转换笔记 |
| | `convertObservedDataToStix()` | 转换观测数据 |
| | `convertOpinionToStix()` | 转换意见 |
| **SCO转换** | `convertArtifactToStix()` | 转换工件 |
| | `convertAutonomousSystemToStix()` | 转换自治系统 |
| | `convertCryptocurrencyWalletToStix()` | 转换加密货币钱包 |
| | `convertCryptographicKeyToStix()` | 转换加密密钥 |
| | `convertDirectoryToStix()` | 转换目录 |
| | `convertDomainNameToStix()` | 转换域名 |
| | `convertEmailAddressToStix()` | 转换邮箱地址 |
| | `convertEmailMessageToStix()` | 转换邮件消息 |
| | `convertFileToStix()` | 转换文件 |
| | `convertHostnameToStix()` | 转换主机名 |
| | `convertIPv4AddressToStix()` | 转换IPv4地址 |
| | `convertIPv6AddressToStix()` | 转换IPv6地址 |
| | `convertMacAddressToStix()` | 转换MAC地址 |
| | `convertMutexToStix()` | 转换互斥体 |
| | `convertNetworkTrafficToStix()` | 转换网络流量 |
| | `convertProcessToStix()` | 转换进程 |
| | `convertSoftwareToStix()` | 转换软件 |
| | `convertTextToStix()` | 转换文本 |
| | `convertBankAccountToStix()` | 转换银行账户 |
| | `convertCredentialToStix()` | 转换凭证 |
| | `convertTrackingNumberToStix()` | 转换追踪号 |
| | `convertPhoneNumberToStix()` | 转换电话号码 |
| | `convertMediaContentToStix()` | 转换媒体内容 |
| | `convertPaymentCardToStix()` | 转换支付卡 |
| | `convertURLToStix()` | 转换URL |
| | `convertUserAgentToStix()` | 转换用户代理 |
| | `convertUserAccountToStix()` | 转换用户账户 |
| | `convertWindowsRegistryKeyToStix()` | 转换注册表键 |
| | `convertX509CertificateToStix()` | 转换X509证书 |
| | `convertPersonaToStix()` | 转换角色 |
| | `convertSSHKeyToStix()` | 转换SSH密钥 |
| **SRO转换** | `convertRelationToStix()` | 转换关系 |
| | `convertSightingToStix()` | 转换目击 |
| | `convertInPirRelToStix()` | 转换PIR关系 |
| **SMO转换** | `convertMarkingToStix()` | 转换标记定义 |
| | `convertLabelToStix()` | 转换标签 |
| | `convertKillChainPhaseToStix()` | 转换杀伤链阶段 |
| | `convertExternalReferenceToStix()` | 转换外部引用 |
| **工具方法** | `convertStoreToStix_2_1()` | 主转换入口 |
| | `buildStixBundle()` | 构建STIX Bundle |
| | `idsValuesRemap()` | ID值重映射 |
| | `isTrustedStixId()` | 检查可信STIX ID |
| | `convertTypeToStixType()` | 类型转换 |

#### 2.2.2 支持的实体类型统计

| 分类 | 数量 | 说明 |
|------|------|------|
| SDO (STIX Domain Objects) | 17种 | 身份、位置、攻击模式、攻击活动等 |
| SCO (STIX Cyber Observables) | 37种 | 文件、IP地址、域名、进程等 |
| SRO (STIX Relationship Objects) | 3种 | 关系、目击、PIR关系 |
| SMO (STIX Meta Objects) | 4种 | 标记定义、标签、杀伤链阶段、外部引用 |
| **总计** | **61种** | 所有STIX实体类型 |

### 2.3 转换工具函数 (`stix-converter-utils.ts`)

| 功能 | 方法名 | 说明 |
|------|--------|------|
| 类型断言 | `assertType()` | 断言实体类型匹配 |
| 日期转换 | `convertToStixDate()` | 转换日期为STIX格式 |
| 对象清理 | `cleanObject()` | 清理空属性 |
| STIX验证 | `isValidStix()` | 验证STIX对象有效性 |
| 引用转换 | `convertObjectReferences()` | 转换对象引用 |

### 2.4 STIX实体表示 (`stix-representative.ts`)

| 功能 | 方法名 | 说明 |
|------|--------|------|
| 提取表示 | `extractStixRepresentative()` | 提取STIX对象的代表性字符串 |
| 用户访问提取 | `extractStixRepresentativeForUser()` | 带用户访问检查的提取 |

### 2.5 STIX核心定义 (`stix.ts`)

| 功能 | 方法名 | 说明 |
|------|--------|------|
| 清理STIX ID | `cleanStixIds()` | 清理临时STIX ID |
| 过滤稳定ID | `onlyStableStixIds()` | 只保留稳定ID |
| 关系映射检查 | `checkStixCoreRelationshipMapping()` | 检查关系映射有效性 |
| 内置关系判断 | `isRelationBuiltin()` | 判断是否内置关系 |
| 关系引用检查 | `checkRelationshipRef()` | 检查关系引用有效性 |

---

## 三、技术规范

### 3.1 依赖关系

```
STIX转换器模块依赖:
├── common/types/stix/           # STIX类型定义 (已完成)
├── common/types/store/          # 存储类型定义 (已完成)
├── common/exception/            # 异常类 (已完成)
├── database/elasticsearch/      # ES引擎 (已完成)
└── database/middleware/         # 数据中间件 (已完成)
```

### 3.2 设计模式

#### 3.2.1 策略模式
使用Map注册转换函数，支持动态扩展：
```java
// 转换函数注册
private static final Map<String, ConvertFunction> stixDomainConverters = new HashMap<>();
private static final Map<String, ConvertFunction> stixMetaConverters = new HashMap<>();

public static void registerStixDomainConverter(String type, ConvertFunction fn) {
    stixDomainConverters.put(type, fn);
}
```

#### 3.2.2 构建器模式
用于复杂STIX对象的构建：
```java
public class StixObjectBuilder {
    public StixObject build(StoreObject instance) {
        return StixObject.builder()
            .id(instance.getStandardId())
            .specVersion("2.1")
            .type(convertTypeToStixType(instance.getEntityType()))
            .extensions(buildExtensions(instance))
            .build();
    }
}
```

### 3.3 扩展定义

#### 3.3.1 OpenCTI扩展
```java
public class StixOpenctiExtension {
    private String extensionType = "property-extension";
    private String id;                    // internal_id
    private String type;                  // entity_type
    private String createdAt;
    private String updatedAt;
    private String modifiedAt;
    private List<String> aliases;
    private List<StixFile> files;
    private List<String> stixIds;
    private Boolean isInferred;
    private List<String> grantedRefs;
    private List<String> creatorIds;
    // ... 其他属性
}
```

#### 3.3.2 MITRE扩展
```java
public class StixMitreExtension {
    private String extensionType = "property-extension";
    private String id;                    // x_mitre_id
    private String detection;
    private List<String> permissionsRequired;
    private List<String> platforms;
    private List<String> collectionLayers;
}
```

### 3.4 日期处理

```java
public class StixDateUtils {
    public static final String FROM_START_STR = "1970-01-01T00:00:00.000Z";
    public static final String UNTIL_END_STR = "5138-11-16T09:46:40.000Z";
    
    public static String convertToStixDate(Date date) {
        if (date == null) return null;
        long time = date.getTime();
        if (time == FROM_START || time == UNTIL_END) return null;
        return date.toInstant().toString();
    }
    
    public static String convertToStixDate(String date) {
        if (date == null) return null;
        if (FROM_START_STR.equals(date) || UNTIL_END_STR.equals(date)) return null;
        return date;
    }
}
```

### 3.5 关系映射表

STIX核心关系映射表包含约300+条映射规则，定义了：
- 实体类型之间的关系类型
- 关系类型分类：builtin（内置）、new（新增）、extended（扩展）

---

## 四、重写原则

### 4.1 必须遵守

1. **逻辑一致性**: 必须保持和源码逻辑完全一致
2. **功能完整性**: 不能多也不能少功能
3. **逐行阅读**: 必须先逐行阅读相关源码
4. **注释规范**: 
   - 类必须注释重写的原文件路径
   - 方法必须注释重写的原文件方法路径
5. **结构保持**: 必须保持现有项目结构
6. **分任务完成**: 超过500行代码必须分多个子任务完成
7. **编译验证**: 每个子任务完成后必须进行编译

### 4.2 子任务划分

由于代码量较大（预估4500-5000行），划分为以下子任务：

| 子任务 | 内容 | 预估行数 |
|--------|------|----------|
| T1 | 基础模型和常量定义 | ~300 |
| T2 | 转换工具函数 | ~200 |
| T3 | STIX 2.0转换器 | ~400 |
| T4 | STIX 2.1转换器 - 基础构建 | ~500 |
| T5 | STIX 2.1转换器 - SDO转换 | ~800 |
| T6 | STIX 2.1转换器 - SCO转换 | ~1200 |
| T7 | STIX 2.1转换器 - SRO/SMO转换 | ~400 |
| T8 | STIX实体表示 | ~400 |
| T9 | STIX核心定义和映射 | ~500 |
| T10 | 整合测试 | ~200 |

---

## 五、验收标准

### 5.1 功能验收

- [ ] 所有61种STIX实体类型都能正确转换
- [ ] STIX 2.0和2.1版本都支持
- [ ] 扩展属性正确处理
- [ ] 日期格式正确转换
- [ ] 关系映射完整

### 5.2 代码质量

- [ ] 所有类有正确的原文件路径注释
- [ ] 所有方法有正确的原方法路径注释
- [ ] 代码风格符合Java规范
- [ ] 无编译错误
- [ ] 无运行时异常

### 5.3 测试覆盖

- [ ] 单元测试覆盖所有转换方法
- [ ] 边界条件测试
- [ ] 异常情况测试

---

## 六、风险与应对

| 风险 | 等级 | 应对措施 |
|------|------|----------|
| 实体类型众多，遗漏转换 | 🔴 | 使用清单逐一核对 |
| 扩展属性处理复杂 | 🟡 | 详细阅读源码扩展定义 |
| 日期格式转换边界 | 🟡 | 参考源码常量定义 |
| 关系映射规则庞大 | 🟡 | 使用Map结构保持一致性 |
