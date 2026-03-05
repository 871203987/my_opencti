# 数据中间件 (Middleware) 重写检查清单

## 总体进度

| 指标 | 状态 |
|------|------|
| 总任务数 | 11 |
| 已完成 | 0 |
| 进行中 | 0 |
| 待开始 | 11 |
| 完成率 | 0% |

---

## 子任务检查清单

### T1: 基础模型和接口定义
- [ ] 创建目录结构
- [ ] MiddlewareContext.java
- [ ] MiddlewareResult.java
- [ ] InputResolveResult.java
- [ ] UpdateInput.java
- [ ] CreateInput.java
- [ ] DeleteOptions.java
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T2: 加载器模块
- [ ] MiddlewareLoader.java 接口
- [ ] MiddlewareLoaderImpl.java 实现
- [ ] batchLoader()
- [ ] loadElementMetaDependencies()
- [ ] loadElementsWithDependencies()
- [ ] storeLoadByIdsWithRefs()
- [ ] storeLoadByIdWithRefs()
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T3: STIX 加载模块
- [ ] StixLoader.java
- [ ] stixLoadById()
- [ ] stixLoadByIds()
- [ ] stixLoadByFilters()
- [ ] stixBundleByIdStringify()
- [ ] stixLoadByIdStringify()
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T4: 图形统计模块
- [ ] MiddlewareStatistics.java
- [ ] timeSeriesHistory()
- [ ] timeSeriesEntities()
- [ ] timeSeriesRelations()
- [ ] distributionHistory()
- [ ] distributionEntities()
- [ ] distributionRelations()
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T5: 输入解析模块
- [ ] InputResolver.java
- [ ] validateCreatedBy()
- [ ] inputResolveRefs()
- [ ] transformPatchToInput()
- [ ] depsKeys()
- [ ] idVocabulary()
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T6: 更新模块
- [ ] MiddlewareUpdater.java
- [ ] updateAttributeMetaResolved()
- [ ] updateAttributeFromLoadedWithRefs()
- [ ] updateAttribute()
- [ ] patchAttribute()
- [ ] patchAttributeFromLoadedWithRefs()
- [ ] generateUpdateMessage()
- [ ] buildChanges()
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T7: 创建模块
- [ ] MiddlewareCreator.java
- [ ] createEntity()
- [ ] createEntityRaw()
- [ ] createRelation()
- [ ] createRelationRaw()
- [ ] createRelations()
- [ ] createInferredEntity()
- [ ] createInferredRelation()
- [ ] getExistingEntities()
- [ ] getExistingRelations()
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T8: 删除模块
- [ ] MiddlewareDeleter.java
- [ ] internalDeleteElementById()
- [ ] deleteElementById()
- [ ] deleteInferredRuleElement()
- [ ] deleteRelationsByFromAndTo()
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T9: 合并模块
- [ ] MiddlewareMerger.java
- [ ] mergeEntities()
- [ ] mergeEntitiesRaw()
- [ ] hashMergeValidation()
- [ ] filterTargetByExisting()
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T10: 规则和访问控制模块
- [ ] MiddlewareRules.java
- [ ] createRuleDataPatch()
- [ ] upsertEntityRule()
- [ ] upsertRelationRule()
- [ ] MiddlewareAccess.java
- [ ] canRequestAccess()
- [ ] buildRestrictedEntity()
- [ ] 编译验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

### T11: 工具方法和整合
- [ ] MiddlewareUtils.java
- [ ] updatedInputsToData()
- [ ] mergeInstanceWithInputs()
- [ ] partialInstanceWithInputs()
- [ ] MiddlewareService.java 接口
- [ ] MiddlewareServiceImpl.java 实现
- [ ] 编译验证
- [ ] 最终测试验证

**状态**: ⏳ 待开始
**开始时间**: -
**完成时间**: -

---

## 源码对照检查

### 导出函数对照表

| 源码函数 | Java实现 | 状态 |
|----------|----------|------|
| canRequestAccess | MiddlewareAccess.canRequestAccess() | ⏳ |
| batchLoader | MiddlewareLoader.batchLoader() | ⏳ |
| topEntitiesOrRelationsList | MiddlewareLoader.topEntitiesOrRelationsList() | ⏳ |
| pageEntitiesOrRelationsConnection | MiddlewareLoader.pageEntitiesOrRelationsConnection() | ⏳ |
| fullEntitiesOrRelationsList | MiddlewareLoader.fullEntitiesOrRelationsList() | ⏳ |
| fullEntitiesOrRelationsConnection | MiddlewareLoader.fullEntitiesOrRelationsConnection() | ⏳ |
| loadEntity | MiddlewareLoader.loadEntity() | ⏳ |
| loadElementsWithDependencies | MiddlewareLoader.loadElementsWithDependencies() | ⏳ |
| storeLoadByIdsWithRefs | MiddlewareLoader.storeLoadByIdsWithRefs() | ⏳ |
| storeLoadByIdWithRefs | MiddlewareLoader.storeLoadByIdWithRefs() | ⏳ |
| stixLoadById | StixLoader.stixLoadById() | ⏳ |
| stixLoadByIds | StixLoader.stixLoadByIds() | ⏳ |
| stixBundleByIdStringify | StixLoader.stixBundleByIdStringify() | ⏳ |
| stixLoadByIdStringify | StixLoader.stixLoadByIdStringify() | ⏳ |
| stixLoadByFilters | StixLoader.stixLoadByFilters() | ⏳ |
| buildRestrictedEntity | MiddlewareAccess.buildRestrictedEntity() | ⏳ |
| timeSeriesHistory | MiddlewareStatistics.timeSeriesHistory() | ⏳ |
| timeSeriesEntities | MiddlewareStatistics.timeSeriesEntities() | ⏳ |
| timeSeriesRelations | MiddlewareStatistics.timeSeriesRelations() | ⏳ |
| distributionHistory | MiddlewareStatistics.distributionHistory() | ⏳ |
| distributionEntities | MiddlewareStatistics.distributionEntities() | ⏳ |
| distributionRelations | MiddlewareStatistics.distributionRelations() | ⏳ |
| validateCreatedBy | InputResolver.validateCreatedBy() | ⏳ |
| inputResolveRefs | InputResolver.inputResolveRefs() | ⏳ |
| updatedInputsToData | MiddlewareUtils.updatedInputsToData() | ⏳ |
| mergeInstanceWithInputs | MiddlewareUtils.mergeInstanceWithInputs() | ⏳ |
| hashMergeValidation | MiddlewareMerger.hashMergeValidation() | ⏳ |
| mergeEntities | MiddlewareMerger.mergeEntities() | ⏳ |
| transformPatchToInput | InputResolver.transformPatchToInput() | ⏳ |
| generateUpdateMessage | MiddlewareUpdater.generateUpdateMessage() | ⏳ |
| buildChanges | MiddlewareUpdater.buildChanges() | ⏳ |
| updateAttributeMetaResolved | MiddlewareUpdater.updateAttributeMetaResolved() | ⏳ |
| updateAttributeFromLoadedWithRefs | MiddlewareUpdater.updateAttributeFromLoadedWithRefs() | ⏳ |
| updateAttribute | MiddlewareUpdater.updateAttribute() | ⏳ |
| patchAttribute | MiddlewareUpdater.patchAttribute() | ⏳ |
| patchAttributeFromLoadedWithRefs | MiddlewareUpdater.patchAttributeFromLoadedWithRefs() | ⏳ |
| getExistingRelations | MiddlewareCreator.getExistingRelations() | ⏳ |
| createRelationRaw | MiddlewareCreator.createRelationRaw() | ⏳ |
| createRelation | MiddlewareCreator.createRelation() | ⏳ |
| createInferredRelation | MiddlewareCreator.createInferredRelation() | ⏳ |
| createRelations | MiddlewareCreator.createRelations() | ⏳ |
| getExistingEntities | MiddlewareCreator.getExistingEntities() | ⏳ |
| createEntity | MiddlewareCreator.createEntity() | ⏳ |
| createInferredEntity | MiddlewareCreator.createInferredEntity() | ⏳ |
| internalDeleteElementById | MiddlewareDeleter.internalDeleteElementById() | ⏳ |
| deleteElementById | MiddlewareDeleter.deleteElementById() | ⏳ |
| deleteInferredRuleElement | MiddlewareDeleter.deleteInferredRuleElement() | ⏳ |
| deleteRelationsByFromAndTo | MiddlewareDeleter.deleteRelationsByFromAndTo() | ⏳ |

**完成进度**: 0/48 (0%)

---

## 编译验证记录

| 日期 | 子任务 | 结果 | 备注 |
|------|--------|------|------|
| - | - | - | - |

---

## 问题追踪

| 问题ID | 描述 | 状态 | 解决方案 |
|--------|------|------|----------|
| - | - | - | - |

---

## 备注

1. 每个子任务完成后必须进行编译验证
2. 每个子任务完成后更新此检查清单
3. 遇到问题及时记录到问题追踪表
4. 所有子任务完成后进行最终集成测试
