# Tasks - Schema Validator 模块重写

## 任务列表

- [x] Task 1: 基础验证方法
  - [x] SubTask 1.1: 创建 `SchemaValidator.java` 文件
  - [x] SubTask 1.2: 实现 `validateAndFormatSchemaAttribute` 方法（属性格式验证）
  - [x] SubTask 1.3: 实现字符串属性验证逻辑
  - [x] SubTask 1.4: 实现布尔属性验证逻辑
  - [x] SubTask 1.5: 实现日期属性验证逻辑
  - [x] SubTask 1.6: 实现数值属性验证逻辑
  - [x] SubTask 1.7: 编译验证

- [x] Task 2: 必填属性验证
  - [x] SubTask 2.1: 实现 `validateMandatoryAttributes` 基础方法
  - [x] SubTask 2.2: 实现 `validateMandatoryAttributesOnCreation` 方法
  - [x] SubTask 2.3: 实现 `validateMandatoryAttributesOnUpdate` 方法
  - [x] SubTask 2.4: 编译验证

- [x] Task 3: 可更新属性验证
  - [x] SubTask 3.1: 实现 `validateUpdatableAttribute` 方法
  - [x] SubTask 3.2: 编译验证

- [x] Task 4: 综合输入验证
  - [x] SubTask 4.1: 实现 `validateInputCreation` 方法
  - [x] SubTask 4.2: 实现 `validateInputUpdate` 方法
  - [x] SubTask 4.3: 编译验证

- [x] Task 5: 编写单元测试
  - [x] SubTask 5.1: 创建 `SchemaValidatorTest.java` 文件
  - [x] SubTask 5.2: 测试属性格式验证
  - [x] SubTask 5.3: 测试必填属性验证
  - [x] SubTask 5.4: 测试可更新属性验证
  - [x] SubTask 5.5: 测试综合输入验证
  - [x] SubTask 5.6: 运行测试并确保通过

## 任务依赖
```
Task 1 (基础验证)
    ↓
Task 2 (必填验证)
    ↓
Task 3 (可更新验证) ─┐
                     ├──→ Task 4 (综合验证) → Task 5 (单元测试)
Task 2 (必填验证) ───┘
```

## 预估工作量

| 文件 | 预估代码行数 | 实际代码行数 | 说明 |
|------|--------------|--------------|------|
| SchemaValidator.java | ~200-250行 | 390行 | 验证方法实现 |
| SchemaValidatorTest.java | ~150-200行 | 387行 | 测试用例 |
| **总计** | **~350-450行** | **777行** | 功能完整 |

## 完成标准
- [x] SchemaValidator.java 文件已创建
- [x] 属性格式验证方法已实现
- [x] 必填属性验证方法已实现
- [x] 可更新属性验证方法已实现
- [x] 综合输入验证方法已实现
- [x] 单元测试已编写并通过（26个测试用例）

## 注意事项
1. 依赖SchemaAttributesDefinition获取属性定义
2. 依赖SchemaRelationsRefDefinition获取关系引用定义
3. 依赖EntitySetting获取实体设置
4. 需要处理权限检查（KNOWLEDGE_KNUPDATE_KNBYPASSFIELDS, KNOWLEDGE_KNUPDATE_KNBYPASSREFERENCE）
5. 日期验证使用ISO 8601格式
6. 多值属性需要特殊处理
7. 需要支持功能验证器（ValidatorRegister）
