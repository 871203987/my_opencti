# 数据中间件 (Middleware) 重写任务清单

## 任务概览

| 任务ID | 任务名称 | 状态 | 优先级 | 预估代码行数 |
|--------|----------|------|--------|--------------|
| T1 | 基础模型和接口定义 | ⏳ 待开始 | P0 | ~500 |
| T2 | 加载器模块 | ⏳ 待开始 | P0 | ~600 |
| T3 | STIX 加载模块 | ⏳ 待开始 | P0 | ~400 |
| T4 | 图形统计模块 | ⏳ 待开始 | P1 | ~400 |
| T5 | 输入解析模块 | ⏳ 待开始 | P0 | ~700 |
| T6 | 更新模块 | ⏳ 待开始 | P0 | ~900 |
| T7 | 创建模块 | ⏳ 待开始 | P0 | ~800 |
| T8 | 删除模块 | ⏳ 待开始 | P0 | ~500 |
| T9 | 合并模块 | ⏳ 待开始 | P1 | ~600 |
| T10 | 规则和访问控制模块 | ⏳ 待开始 | P1 | ~400 |
| T11 | 工具方法和整合 | ⏳ 待开始 | P0 | ~400 |

---

## T1: 基础模型和接口定义

### 1.1 创建目录结构
- [ ] 创建 `database/middleware/` 目录
- [ ] 创建 `database/middleware/model/` 子目录

### 1.2 实现模型类
- [ ] `MiddlewareContext.java` - 中间件上下文
  - 原文件: middleware.js (context 参数)
  - 包含: 用户信息、请求ID、草稿ID、事件ID等
  
- [ ] `MiddlewareResult.java` - 操作结果封装
  - 原文件: middleware.js 返回值结构
  - 包含: element, event, isCreation 字段

- [ ] `InputResolveResult.java` - 输入解析结果
  - 原文件: middleware.js:782-988 inputResolveRefs 返回值

- [ ] `UpdateInput.java` - 更新输入模型
  - 原文件: middleware.js 输入结构
  - 包含: key, value, operation, previous, object_path 字段

- [ ] `CreateInput.java` - 创建输入模型
  - 原文件: middleware.js 创建输入结构

- [ ] `DeleteOptions.java` - 删除选项
  - 原文件: middleware.js:3441 opts 参数

### 1.3 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T2: 加载器模块

### 2.1 创建接口和实现
- [ ] `MiddlewareLoader.java` - 加载器接口
  - 原文件: middleware.js 加载相关函数

- [ ] `MiddlewareLoaderImpl.java` - 加载器实现

### 2.2 实现加载方法
- [ ] `batchLoader()` - 批量加载器
  - 原文件: middleware.js:283-296
  - 功能: 创建 DataLoader 实例用于批量加载

- [ ] `loadElementMetaDependencies()` - 加载元素元数据依赖
  - 原文件: middleware.js:338-407
  - 功能: 加载元素的标记、标签等元数据依赖

- [ ] `loadElementsWithDependencies()` - 加载元素及其依赖
  - 原文件: middleware.js:409-485
  - 功能: 加载元素并解析所有依赖关系

- [ ] `storeLoadByIdsWithRefs()` - 批量加载并解析引用
  - 原文件: middleware.js:503-507
  - 功能: 根据ID列表加载元素并解析引用

- [ ] `storeLoadByIdWithRefs()` - 单个加载并解析引用
  - 原文件: middleware.js:508-511
  - 功能: 根据ID加载单个元素并解析引用

### 2.3 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T3: STIX 加载模块

### 3.1 创建 STIX 加载器
- [ ] `StixLoader.java` - STIX 加载器

### 3.2 实现 STIX 加载方法
- [ ] `stixLoadById()` - 根据ID加载STIX对象
  - 原文件: middleware.js:512-516
  - 功能: 加载并转换为STIX格式

- [ ] `stixLoadByIds()` - 批量加载STIX对象
  - 原文件: middleware.js:531-546
  - 功能: 批量加载并转换为STIX格式

- [ ] `stixLoadByFilters()` - 根据过滤条件加载STIX对象
  - 原文件: middleware.js:560-563
  - 功能: 根据过滤条件加载STIX对象

- [ ] `stixBundleByIdStringify()` - 获取STIX Bundle字符串
  - 原文件: middleware.js:547-553
  - 功能: 获取实体的STIX Bundle JSON字符串

- [ ] `stixLoadByIdStringify()` - 获取STIX对象字符串
  - 原文件: middleware.js:555-559
  - 功能: 获取实体的STIX JSON字符串

### 3.3 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T4: 图形统计模块

### 4.1 创建统计模块
- [ ] `MiddlewareStatistics.java` - 统计模块

### 4.2 实现统计方法
- [ ] `timeSeriesHistory()` - 历史时间序列
  - 原文件: middleware.js:630-634
  - 功能: 获取历史记录的时间序列统计

- [ ] `timeSeriesEntities()` - 实体时间序列
  - 原文件: middleware.js:635-640
  - 功能: 获取实体的时间序列统计

- [ ] `timeSeriesRelations()` - 关系时间序列
  - 原文件: middleware.js:641-647
  - 功能: 获取关系的时间序列统计

- [ ] `distributionHistory()` - 历史分布统计
  - 原文件: middleware.js:648-682
  - 功能: 获取历史记录的分布统计

- [ ] `distributionEntities()` - 实体分布统计
  - 原文件: middleware.js:683-721
  - 功能: 获取实体的分布统计

- [ ] `distributionRelations()` - 关系分布统计
  - 原文件: middleware.js:722-742
  - 功能: 获取关系的分布统计

### 4.3 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T5: 输入解析模块

### 5.1 创建输入解析器
- [ ] `InputResolver.java` - 输入解析器

### 5.2 实现解析方法
- [ ] `validateCreatedBy()` - 验证创建者
  - 原文件: middleware.js:769-780
  - 功能: 验证 createdBy 引用是否为 Identity 类型

- [ ] `inputResolveRefs()` - 解析输入引用
  - 原文件: middleware.js:782-988
  - 功能: 解析输入中的所有引用关系
  - 复杂度: ⭐⭐⭐⭐⭐ (最复杂的方法之一)

- [ ] `transformPatchToInput()` - 转换补丁为输入
  - 原文件: middleware.js:1635-1648
  - 功能: 将补丁对象转换为输入格式

- [ ] `depsKeys()` - 获取依赖键
  - 原文件: middleware.js:746-755
  - 功能: 获取类型的依赖键列表

- [ ] `idVocabulary()` - 词汇表ID处理
  - 原文件: middleware.js:757-759
  - 功能: 处理词汇表ID生成

### 5.3 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T6: 更新模块

### 6.1 创建更新模块
- [ ] `MiddlewareUpdater.java` - 更新模块

### 6.2 实现更新方法
- [ ] `updateAttributeMetaResolved()` - 更新属性(已解析元数据)
  - 原文件: middleware.js:2160-2552
  - 功能: 核心更新方法，处理属性更新和元数据关系
  - 复杂度: ⭐⭐⭐⭐⭐ (最复杂的方法)

- [ ] `updateAttributeFromLoadedWithRefs()` - 从已加载对象更新
  - 原文件: middleware.js:2554-2580
  - 功能: 从已加载的对象更新属性

- [ ] `updateAttribute()` - 更新属性
  - 原文件: middleware.js:2598-2613
  - 功能: 根据ID更新实体属性

- [ ] `patchAttribute()` - 补丁更新属性
  - 原文件: middleware.js:2615-2618
  - 功能: 使用补丁对象更新属性

- [ ] `patchAttributeFromLoadedWithRefs()` - 从已加载对象补丁更新
  - 原文件: middleware.js:2620-2623
  - 功能: 从已加载的对象使用补丁更新

- [ ] `generateUpdateMessage()` - 生成更新消息
  - 原文件: middleware.js:2024-2063
  - 功能: 生成更新操作的描述消息

- [ ] `buildChanges()` - 构建变更记录
  - 原文件: middleware.js:2088-2158
  - 功能: 构建属性变更记录

### 6.3 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T7: 创建模块

### 7.1 创建创建模块
- [ ] `MiddlewareCreator.java` - 创建模块

### 7.2 实现创建方法
- [ ] `createEntity()` - 创建实体
  - 原文件: middleware.js:3390-3401
  - 功能: 创建STIX实体的主入口

- [ ] `createEntityRaw()` - 创建实体(原始)
  - 原文件: middleware.js:3168-3388
  - 功能: 创建实体的核心实现
  - 复杂度: ⭐⭐⭐⭐⭐

- [ ] `createRelation()` - 创建关系
  - 原文件: middleware.js:3111-3114
  - 功能: 创建关系的主入口

- [ ] `createRelationRaw()` - 创建关系(原始)
  - 原文件: middleware.js:2919-3110
  - 功能: 创建关系的核心实现
  - 复杂度: ⭐⭐⭐⭐⭐

- [ ] `createRelations()` - 批量创建关系
  - 原文件: middleware.js:3141-3150
  - 功能: 批量创建关系

- [ ] `createInferredEntity()` - 创建推理实体
  - 原文件: middleware.js:3403-3416
  - 功能: 创建推理规则生成的实体

- [ ] `createInferredRelation()` - 创建推理关系
  - 原文件: middleware.js:3115-3139
  - 功能: 创建推理规则生成的关系

- [ ] `getExistingEntities()` - 获取已存在实体
  - 原文件: middleware.js:3155-3166
  - 功能: 检查已存在的实体

- [ ] `getExistingRelations()` - 获取已存在关系
  - 原文件: middleware.js:2866-2917
  - 功能: 检查已存在的关系

### 7.3 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T8: 删除模块

### 8.1 创建删除模块
- [ ] `MiddlewareDeleter.java` - 删除模块

### 8.2 实现删除方法
- [ ] `internalDeleteElementById()` - 内部删除元素
  - 原文件: middleware.js:3441-3552
  - 功能: 删除元素的核心实现
  - 复杂度: ⭐⭐⭐⭐⭐

- [ ] `deleteElementById()` - 删除元素
  - 原文件: middleware.js:3553-3560
  - 功能: 删除元素的公开接口

- [ ] `deleteInferredRuleElement()` - 删除推理元素
  - 原文件: middleware.js:3561-3615
  - 功能: 删除推理规则生成的元素

- [ ] `deleteRelationsByFromAndTo()` - 根据起止点删除关系
  - 原文件: middleware.js:3616-3666
  - 功能: 根据起点和终点删除关系

### 8.3 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T9: 合并模块

### 9.1 创建合并模块
- [ ] `MiddlewareMerger.java` - 合并模块

### 9.2 实现合并方法
- [ ] `mergeEntities()` - 合并实体
  - 原文件: middleware.js:1582-1633
  - 功能: 合并多个实体
  - 复杂度: ⭐⭐⭐⭐⭐

- [ ] `mergeEntitiesRaw()` - 合并实体(原始)
  - 原文件: middleware.js:1268-1549
  - 功能: 合并实体的核心实现

- [ ] `hashMergeValidation()` - 哈希合并验证
  - 原文件: middleware.js:1206-1221
  - 功能: 验证哈希可观测对象的合并

- [ ] `filterTargetByExisting()` - 过滤已存在目标
  - 原文件: middleware.js:1227-1266
  - 功能: 过滤已存在的目标关系

### 9.3 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T10: 规则和访问控制模块

### 10.1 创建规则模块
- [ ] `MiddlewareRules.java` - 规则模块

### 10.2 实现规则方法
- [ ] `createRuleDataPatch()` - 创建规则数据补丁
  - 原文件: middleware.js:2638-2695
  - 功能: 根据规则生成数据补丁

- [ ] `upsertEntityRule()` - Upsert实体规则
  - 原文件: middleware.js:2711-2723
  - 功能: 更新或插入推理实体

- [ ] `upsertRelationRule()` - Upsert关系规则
  - 原文件: middleware.js:2724-2744
  - 功能: 更新或插入推理关系

### 10.3 创建访问控制模块
- [ ] `MiddlewareAccess.java` - 访问控制模块

### 10.4 实现访问控制方法
- [ ] `canRequestAccess()` - 检查是否可请求访问
  - 原文件: middleware.js:252-280
  - 功能: 检查用户是否可以请求访问受限元素

- [ ] `buildRestrictedEntity()` - 构建受限实体
  - 原文件: middleware.js:580-597
  - 功能: 构建受限视图的实体

### 10.5 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

---

## T11: 工具方法和整合

### 11.1 创建工具类
- [ ] `MiddlewareUtils.java` - 工具方法

### 11.2 实现工具方法
- [ ] `updatedInputsToData()` - 更新输入转数据
  - 原文件: middleware.js:1038-1048
  - 功能: 将更新输入转换为数据对象

- [ ] `mergeInstanceWithInputs()` - 合并实例与输入
  - 原文件: middleware.js:1049-1055
  - 功能: 将输入合并到实例

- [ ] `partialInstanceWithInputs()` - 部分合并
  - 原文件: middleware.js:1056-1065
  - 功能: 部分合并输入到实例

### 11.3 创建主服务
- [ ] `MiddlewareService.java` - 主服务接口
  - 整合所有模块的接口

- [ ] `MiddlewareServiceImpl.java` - 主服务实现
  - 整合所有模块的实现

### 11.4 编译验证
- [ ] 执行 `mvn compile` 确保无编译错误

### 11.5 最终验证
- [ ] 执行 `mvn test` 确保所有测试通过
- [ ] 与源码对比验证功能一致性

---

## 验证检查清单

### 编译验证
- [ ] T1 编译通过
- [ ] T2 编译通过
- [ ] T3 编译通过
- [ ] T4 编译通过
- [ ] T5 编译通过
- [ ] T6 编译通过
- [ ] T7 编译通过
- [ ] T8 编译通过
- [ ] T9 编译通过
- [ ] T10 编译通过
- [ ] T11 编译通过

### 功能验证
- [ ] 加载功能与源码一致
- [ ] 创建功能与源码一致
- [ ] 更新功能与源码一致
- [ ] 删除功能与源码一致
- [ ] 合并功能与源码一致

### 文档更新
- [ ] 更新 MODULE_OVERVIEW.md
- [ ] 更新项目重写计划.md
