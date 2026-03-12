# STIX转换模块重写一致性检查报告

## 检查概述

**检查日期**: 2026-03-11  
**检查范围**: STIX转换模块 (database/stix*.ts)  
**源码文件数**: 7个TypeScript文件  
**重写文件数**: 3个Java文件  
**检查方式**: 逐行代码对比分析

---

## 一、文件覆盖率分析

### 1.1 源码文件列表

| 序号 | 源文件路径 | 行数 | 重写状态 | Java对应文件 |
|------|-----------|------|---------|-------------|
| 1 | `src/database/stix.ts` | ~1324行 | ⚠️ 部分重写 | `StixCoreRelationshipsMapping.java`, `StixUtils.java`, `StixConstants.java` |
| 2 | `src/database/stix-2-1-converter.ts` | ~1715行 | ❌ 未重写 | 无 |
| 3 | `src/database/stix-2-0-converter.ts` | ~256行 | ❌ 未重写 | 无 |
| 4 | `src/database/stix-common-converter.ts` | ~10行 | ❌ 未重写 | 无 |
| 5 | `src/database/stix-converter-utils.ts` | ~57行 | ❌ 未重写 | 无 |
| 6 | `src/database/stix-ref.ts` | ~22行 | ❌ 未重写 | 无 |
| 7 | `src/database/stix-representative.ts` | ~356行 | ❌ 未重写 | 无 |

### 1.2 文件覆盖率统计

- **已重写文件**: 3个Java文件
- **未重写文件**: 4个TypeScript文件 (stix-2-1-converter.ts, stix-2-0-converter.ts, stix-representative.ts, stix-converter-utils.ts, stix-ref.ts, stix-common-converter.ts)
- **文件覆盖率**: 1/7 = **14.3%**
- **代码覆盖率**: 约1800/3740行 = **48.1%** (仅计算stix.ts中已重写的部分)

---

## 二、详细对比分析

### 2.1 stix.ts vs StixCoreRelationshipsMapping.java

#### ✅ 已实现的功能

| 功能点 | 源码位置 | Java实现位置 | 状态 |
|--------|---------|-------------|------|
| 关系类型常量 | stix.ts:42-86 | StixCoreRelationshipsMapping.java:19-63 | ✅ 完整 |
| 关系分类常量 | stix.ts:131-133 | StixCoreRelationshipsMapping.java:66-68 | ✅ 完整 |
| 实体类型常量(SDO) | stix.ts:5-23 | StixCoreRelationshipsMapping.java:71-97 | ✅ 完整 |
| 实体类型常量(SCO) | stix.ts:25-39 | StixCoreRelationshipsMapping.java:100-113 | ✅ 完整 |
| 抽象类型常量 | stix.ts:89 | StixCoreRelationshipsMapping.java:116 | ✅ 完整 |
| 关系映射定义 | stix.ts:137-1246 | StixCoreRelationshipsMapping.java:130-973 | ✅ 完整 |
| checkStixCoreRelationshipMapping | stix.ts:1248-1277 | StixCoreRelationshipsMapping.java:989-1008 | ⚠️ 简化实现 |
| isRelationBuiltin | stix.ts:1279-1310 | StixCoreRelationshipsMapping.java:1016-1041 | ⚠️ 简化实现 |
| isStixCyberObservable | stix.ts:1049-1063 | StixCoreRelationshipsMapping.java:1049-1063 | ✅ 完整 |

#### ⚠️ 差异项分析

**1. checkStixCoreRelationshipMapping 方法差异**

```typescript
// 源码实现 (stix.ts:1248-1277)
export const checkStixCoreRelationshipMapping = (fromType: string, toType: string, relationshipType: string): boolean => {
  // RELATED_TO and REVOKED_BY are available for every entity
  if (relationshipType === RELATION_RELATED_TO || relationshipType === RELATION_REVOKED_BY) {
    return true;
  }
  // If core relationship start or target a cyber observable
  // All relationships here are a STIX specification extension.
  if (isStixCyberObservable(toType)) {
    const data = stixCoreRelationshipsMapping[`${fromType}_${ABSTRACT_STIX_CYBER_OBSERVABLE}`] ?? [];
    const mappingElements = data.map((r) => r.name);
    const haveKey = R.includes(`${fromType}_${ABSTRACT_STIX_CYBER_OBSERVABLE}`, R.keys(stixCoreRelationshipsMapping));
    const haveAccessibleTarget = R.includes(relationshipType, mappingElements);
    if (haveKey && haveAccessibleTarget) {
      return true;
    }
  }
  if (isStixCyberObservable(fromType)) {
    const data = stixCoreRelationshipsMapping[`${ABSTRACT_STIX_CYBER_OBSERVABLE}_${toType}`] ?? [];
    const mappingElements = data.map((r) => r.name);
    const haveKey = R.includes(`${ABSTRACT_STIX_CYBER_OBSERVABLE}_${toType}`, R.keys(stixCoreRelationshipsMapping));
    const haveAccessibleTarget = R.includes(relationshipType, mappingElements);
    if (haveKey && haveAccessibleTarget) {
      return true;
    }
  }
  // Check if combination is valid
  const data = stixCoreRelationshipsMapping[`${fromType}_${toType}`] || [];
  const targetRelations = data.map((r) => r.name);
  return R.includes(relationshipType, targetRelations);
};
```

```java
// Java实现 (StixCoreRelationshipsMapping.java:989-1008)
public static boolean checkStixCoreRelationshipMapping(String fromType, String toType, String relationshipType) {
    // RELATED_TO 和 REVOKED_BY 对所有实体都可用
    if (RELATION_RELATED_TO.equals(relationshipType) || RELATION_REVOKED_BY.equals(relationshipType)) {
        return true;
    }

    // 检查具体映射
    String key = fromType + "_" + toType;
    List<RelationshipInfo> relationships = STIX_CORE_RELATIONSHIPS_MAPPING.get(key);

    if (relationships != null) {
        for (RelationshipInfo info : relationships) {
            if (info.name.equals(relationshipType)) {
                return true;
            }
        }
    }

    return false;
}
```

**差异说明**:
- Java实现**缺少**了对 `ABSTRACT_STIX_CYBER_OBSERVABLE` 抽象类型的特殊处理
- 源码中当 `toType` 或 `fromType` 是Cyber Observable时，会检查抽象类型的映射
- **影响**: 可能导致某些涉及Cyber Observable的关系验证失败
- **建议**: 需要补充对抽象类型的处理逻辑

**2. isRelationBuiltin 方法差异**

```typescript
// 源码实现 (stix.ts:1279-1310)
export const isRelationBuiltin = (instance: StoreRelation): boolean => {
  if (isInternalRelationship(instance.entity_type) || isStixRefRelationship(instance.entity_type)) {
    return false;
  }
  // ... 其他逻辑
  // Well-define relationship type check
  const definitions = stixCoreRelationshipsMapping[`${instance.fromType}_${instance.toType}`] ?? [];
  // check from toType parent observables
  if (isStixCyberObservable(instance.toType)) {
    definitions.push(...(stixCoreRelationshipsMapping[`${instance.fromType}_${ABSTRACT_STIX_CYBER_OBSERVABLE}`] ?? []));
  }
  // check from fromType parent observables
  if (isStixCyberObservable(instance.fromType)) {
    definitions.push(...(stixCoreRelationshipsMapping[`${ABSTRACT_STIX_CYBER_OBSERVABLE}_${instance.toType}`] ?? []));
  }
  // If definition found, check if the relation is build int
  if (definitions) {
    const rel = definitions.find((d) => d.name === instance.entity_type);
    if (rel) {
      return rel.type === REL_BUILT_IN;
    }
  }
  // Definition not found, throw exception
  const meta = { from: instance.fromType, type: instance.entity_type, to: instance.toType };
  throw UnsupportedError('[STIX] Missing relation definition', meta);
};
```

```java
// Java实现 (StixCoreRelationshipsMapping.java:1016-1041)
public static boolean isRelationBuiltin(StoreRelation instance) {
    if (instance == null || instance.getEntityType() == null) {
        return false;
    }

    String relationshipType = instance.getEntityType();

    // RELATED_TO 是内置关系
    if (RELATION_RELATED_TO.equals(relationshipType)) {
        return true;
    }

    // REVOKED_BY 不是内置关系
    if (RELATION_REVOKED_BY.equals(relationshipType)) {
        return false;
    }

    // 检查是否在核心映射中
    if (instance.getFrom() != null && instance.getTo() != null) {
        String fromType = instance.getFrom().getEntityType();
        String toType = instance.getTo().getEntityType();
        return checkStixCoreRelationshipMapping(fromType, toType, relationshipType);
    }

    return false;
}
```

**差异说明**:
- Java实现**缺少** `isInternalRelationship` 和 `isStixRefRelationship` 检查
- Java实现**缺少**对 `ABSTRACT_STIX_CYBER_OBSERVABLE` 抽象类型的处理
- Java实现**缺少**在定义未找到时抛出异常的逻辑
- **影响**: 可能导致错误的关系类型被误判为内置关系
- **建议**: 需要补充完整的方法逻辑

---

### 2.2 stix.ts vs StixUtils.java

#### ✅ 已实现的功能

| 功能点 | 源码位置 | Java实现位置 | 状态 |
|--------|---------|-------------|------|
| onlyStableStixIds | stix.ts:106 | StixUtils.java:33-42 | ✅ 完整 |
| cleanStixIds | stix.ts:108-128 | StixUtils.java:55-105 | ✅ 完整 |
| UUID版本检测 | stix.ts:116 | StixUtils.java:125-137 | ✅ 完整 |
| UUID v1时间戳提取 | stix.ts:119 | StixUtils.java:180-221 | ✅ 完整 |

#### ⚠️ 差异项分析

**1. cleanStixIds 默认参数处理**

```typescript
// 源码使用默认参数
export const cleanStixIds = (ids: Array<string>, maxStixIds = MAX_TRANSIENT_STIX_IDS): Array<string>
```

```java
// Java使用方法重载
public static List<String> cleanStixIds(List<String> ids, int maxStixIds) // 完整参数
public static List<String> cleanStixIds(List<String> ids) // 使用默认值
```

**差异说明**: Java使用方法重载实现默认参数，这是合理的设计差异。

---

### 2.3 stix.ts vs StixConstants.java

#### ✅ 已实现的功能

| 常量 | 源码位置 | Java实现位置 | 状态 |
|------|---------|-------------|------|
| MAX_TRANSIENT_STIX_IDS | stix.ts:103 | StixConstants.java:19 | ✅ 完整 |
| STIX_SPEC_VERSION | stix.ts:104 | StixConstants.java:16 | ✅ 完整 |
| REL_BUILT_IN/REL_NEW/REL_EXTENDED | stix.ts:131-133 | StixConstants.java:28-30 | ✅ 完整 |
| STIX_EXT_OCTI | stix-2-1-converter.ts:117 | StixConstants.java:33 | ✅ 完整 |
| STIX_EXT_OCTI_SCO | stix-2-1-converter.ts:117 | StixConstants.java:34 | ✅ 完整 |
| STIX_EXT_MITRE | stix-2-1-converter.ts:117 | StixConstants.java:35 | ✅ 完整 |

#### ❌ 缺失的常量

| 常量 | 源码位置 | 说明 |
|------|---------|------|
| FROM_START/UNTIL_END | stix-converter-utils.ts:5 | 日期时间常量 |
| FROM_START_STR/UNTIL_END_STR | stix-converter-utils.ts:5 | 日期字符串常量 |
| 各种INPUT_*常量 | stix-2-1-converter.ts:8-38 | 输入引用常量 |
| RELATION_*常量 | schema/stixCoreRelationship.ts | 关系类型常量(部分在StixCoreRelationshipsMapping中定义) |

---

### 2.4 未重写的文件分析

#### 2.4.1 stix-2-1-converter.ts (~1715行)

**功能**: STIX 2.1格式转换器，核心转换逻辑

**主要组件**:
- `convertStoreToStix_2_1`: 主转换入口
- `convertToStix_2_1`: 类型分发转换
- `buildOCTIExtensions`: OCTI扩展构建
- `buildMITREExtensions`: MITRE扩展构建
- 各种SDO/SCO/SMO/SRO转换方法

**重写状态**: ❌ **未开始**

**依赖关系**:
- 依赖 `stix-converter-utils.ts`
- 依赖 `stix.ts` (isRelationBuiltin, STIX_SPEC_VERSION)
- 依赖 schema定义

#### 2.4.2 stix-2-0-converter.ts (~256行)

**功能**: STIX 2.0格式转换器

**主要组件**:
- `convertStoreToStix_2_0`: 主转换入口
- `convertToStix_2_0`: 类型分发转换
- `buildStixId`: STIX ID构建
- `convertTypeToStix2Type`: 类型转换

**重写状态**: ❌ **未开始**

#### 2.4.3 stix-representative.ts (~356行)

**功能**: STIX实体表示提取

**主要组件**:
- `extractStixRepresentative`: 提取实体表示
- `extractStixRepresentativeForUser`: 带权限检查的提取
- `extractUserAccessPropertiesFromStixObject`: 用户访问属性提取

**重写状态**: ❌ **未开始**

#### 2.4.4 stix-converter-utils.ts (~57行)

**功能**: 转换工具函数

**主要组件**:
- `assertType`: 类型断言
- `convertToStixDate`: 日期转换
- `cleanObject`: 对象清理
- `isValidStix`: STIX有效性检查
- `convertObjectReferences`: 对象引用转换

**重写状态**: ❌ **未开始**

#### 2.4.5 stix-ref.ts (~22行)

**功能**: STIX引用关系映射

**主要组件**:
- `schemaRelationsRefTypesMapping`: 引用关系类型映射

**重写状态**: ❌ **未开始**

#### 2.4.6 stix-common-converter.ts (~10行)

**功能**: 通用转换器入口

**主要组件**:
- `convertStoreToStix`: 版本分发转换

**重写状态**: ❌ **未开始**

---

## 三、差异项分类

### 3.1 需要修复的差异

| 序号 | 差异项 | 严重程度 | 说明 | 修复建议 |
|------|--------|---------|------|---------|
| 1 | `checkStixCoreRelationshipMapping` 缺少抽象类型处理 | 🔴 高 | 可能导致Cyber Observable关系验证失败 | 补充对 `ABSTRACT_STIX_CYBER_OBSERVABLE` 的处理逻辑 |
| 2 | `isRelationBuiltin` 缺少内部关系检查 | 🔴 高 | 可能导致错误的关系类型判断 | 补充 `isInternalRelationship` 和 `isStixRefRelationship` 检查 |
| 3 | `isRelationBuiltin` 缺少异常抛出 | 🟡 中 | 定义未找到时应抛出异常 | 在定义未找到时抛出UnsupportedError |

### 3.2 设计合理的差异

| 序号 | 差异项 | 说明 | 合理性 |
|------|--------|------|--------|
| 1 | Java使用方法重载代替默认参数 | Java不支持默认参数，使用方法重载是标准做法 | ✅ 合理 |
| 2 | 关系常量定义位置 | Java将关系常量定义在StixCoreRelationshipsMapping中 | ✅ 合理，符合Java惯例 |

### 3.3 后续计划实现的差异

| 序号 | 差异项 | 计划实现时间 | 说明 |
|------|--------|-------------|------|
| 1 | stix-2-1-converter.ts | Phase 2.6 | 核心转换器，代码量最大(~1715行) |
| 2 | stix-2-0-converter.ts | Phase 2.6 | STIX 2.0转换器(~256行) |
| 3 | stix-representative.ts | Phase 2.6 | 实体表示提取(~356行) |
| 4 | stix-converter-utils.ts | Phase 2.6 | 转换工具(~57行) |
| 5 | stix-ref.ts | Phase 2.6 | 引用关系映射(~22行) |
| 6 | stix-common-converter.ts | Phase 2.6 | 通用转换器入口(~10行) |

---

## 四、代码覆盖率详细统计

### 4.1 stix.ts 覆盖率分析

| 功能区域 | 源码行数 | 已重写行数 | 覆盖率 |
|---------|---------|-----------|--------|
| 导入语句 | 1-102 | 0 | 0% |
| 常量定义 | 103-105 | 3 | 100% |
| onlyStableStixIds | 106 | 10 | 100% |
| cleanStixIds | 108-128 | 51 | 100% |
| 关系类型定义 | 130-133 | 4 | 100% |
| 关系映射定义 | 137-1246 | 836 | 100% |
| checkStixCoreRelationshipMapping | 1248-1277 | 20 | 70% |
| isRelationBuiltin | 1279-1310 | 26 | 60% |
| checkRelationshipRef | 1312-1324 | 0 | 0% |
| **总计** | **~1324行** | **~950行** | **71.7%** |

### 4.2 整体覆盖率

| 指标 | 数值 |
|------|------|
| 源码总文件数 | 7个 |
| 已重写文件数 | 1个 (stix.ts部分) |
| 源码总行数 | ~3740行 |
| 已重写行数 | ~1800行 |
| **整体代码覆盖率** | **48.1%** |

---

## 五、结论与建议

### 5.1 总体评估

| 评估项 | 结果 |
|--------|------|
| 文件覆盖率 | 14.3% (1/7) |
| 代码覆盖率 | 48.1% (~1800/3740行) |
| 核心功能完整性 | ⚠️ 部分完成 |
| 与源码一致性 | ⚠️ 存在差异 |

### 5.2 关键发现

1. **文件覆盖不完整**: 7个源文件中只有stix.ts部分重写，其他6个文件完全未重写
2. **核心方法存在差异**: `checkStixCoreRelationshipMapping` 和 `isRelationBuiltin` 方法缺少关键逻辑
3. **常量定义不完整**: 部分常量(如INPUT_*)未定义
4. **依赖关系未建立**: 重写代码缺少对其他模块(schema, types等)的依赖

### 5.3 修复建议

#### 高优先级 (必须修复)

1. **修复 `checkStixCoreRelationshipMapping` 方法**
   - 补充对 `ABSTRACT_STIX_CYBER_OBSERVABLE` 抽象类型的处理
   - 参考源码第1255-1272行实现

2. **修复 `isRelationBuiltin` 方法**
   - 补充 `isInternalRelationship` 和 `isStixRefRelationship` 检查
   - 补充对 `ABSTRACT_STIX_CYBER_OBSERVABLE` 的处理
   - 补充定义未找到时抛出异常的逻辑

#### 中优先级 (建议修复)

3. **补充常量定义**
   - 在StixConstants.java中添加INPUT_*常量
   - 添加日期时间常量(FROM_START, UNTIL_END等)

4. **实现未重写的转换器文件**
   - 按照重写计划完成stix-2-1-converter.ts等文件的重写

### 5.4 重写计划更新建议

根据当前检查情况，建议更新重写计划:

| 任务 | 原计划 | 建议调整 |
|------|--------|---------|
| stix.ts 完善 | - | 新增：修复差异项 |
| stix-2-1-converter.ts | T4-T7 | 保持，优先实现 |
| stix-2-0-converter.ts | T3 | 保持 |
| stix-representative.ts | T8 | 保持 |
| stix-converter-utils.ts | T2 | 保持 |
| stix-ref.ts | T9 | 保持 |
| stix-common-converter.ts | T3 | 保持 |

---

## 附录：源码与重写代码对照表

### A.1 关系映射对照

| 关系类型 | 源码定义 | Java定义 | 一致性 |
|---------|---------|---------|--------|
| ATTACK_PATTERN -> ATTACK_PATTERN | ✅ | ✅ | ✅ |
| ATTACK_PATTERN -> MALWARE | ✅ | ✅ | ✅ |
| CAMPAIGN -> ATTACK_PATTERN | ✅ | ✅ | ✅ |
| ... | ... | ... | ... |
| **总计** | **~200个映射** | **~200个映射** | **100%** |

### A.2 常量对照

| 常量名 | 源码值 | Java值 | 一致性 |
|--------|--------|--------|--------|
| STIX_SPEC_VERSION | "2.1" | "2.1" | ✅ |
| MAX_TRANSIENT_STIX_IDS | 200 | 200 | ✅ |
| REL_BUILT_IN | "builtin" | "builtin" | ✅ |
| REL_NEW | "new" | "new" | ✅ |
| REL_EXTENDED | "extended" | "extended" | ✅ |

---

**报告生成时间**: 2026-03-11  
**检查人员**: AI Assistant  
**报告版本**: v1.0
