# Tasks - Schema Attribute Definition 模块重写

## 任务列表

- [x] Task 1: 基础类型定义
  - [x] SubTask 1.1: 创建 `AttributeDefinitionSchema.java` 文件
  - [x] SubTask 1.2: 定义AttrType枚举（STRING, DATE, NUMERIC, BOOLEAN, OBJECT, REF）
  - [x] SubTask 1.3: 定义MandatoryType枚举（INTERNAL, EXTERNAL, CUSTOMIZABLE, NO）
  - [x] SubTask 1.4: 定义AttributeFormat枚举
  - [x] SubTask 1.5: 编译验证

- [x] Task 2: 属性定义类
  - [x] SubTask 2.1: 定义基础AttributeDefinition接口/抽象类
  - [x] SubTask 2.2: 定义DateAttribute类
  - [x] SubTask 2.3: 定义BooleanAttribute类
  - [x] SubTask 2.4: 定义NumericAttribute类
  - [x] SubTask 2.5: 定义StringAttribute相关类（TextAttribute, EnumAttribute, VocabAttribute, IdAttribute, JsonAttribute）
  - [x] SubTask 2.6: 定义ComplexAttribute相关类（ObjectAttribute, NestedObjectAttribute, FlatObjectAttribute）
  - [x] SubTask 2.7: 定义RefAttribute类
  - [x] SubTask 2.8: 编译验证

- [x] Task 3: 全局属性定义
  - [x] SubTask 3.1: 定义ID属性（id, internalId, standardId, draftIds）
  - [x] SubTask 3.2: 定义时间属性（createdAt, updatedAt, refreshedAt, created, modified）
  - [x] SubTask 3.3: 定义其他全局属性（iAttributes, creators, iAliasedIds, lastEventId, files等）
  - [x] SubTask 3.4: 编译验证

- [x] Task 4: STIX属性定义
  - [x] SubTask 4.1: 定义ID相关属性（xOpenctiStixIds）
  - [x] SubTask 4.2: 定义别名属性（aliases, xOpenctiAliases）
  - [x] SubTask 4.3: 定义状态属性（revoked, confidence, lang）
  - [x] SubTask 4.4: 定义其他STIX属性（identityClass, xOpenctiReliability等）
  - [x] SubTask 4.5: 编译验证

- [x] Task 5: 类型守卫方法
  - [x] SubTask 5.1: 实现isNumericAttribute方法
  - [x] SubTask 5.2: 实现isDateAttribute方法
  - [x] SubTask 5.3: 实现isBooleanAttribute方法
  - [x] SubTask 5.4: 实现isStringAttribute方法
  - [x] SubTask 5.5: 实现isComplexAttribute方法
  - [x] SubTask 5.6: 实现isRefAttribute方法
  - [x] SubTask 5.7: 编译验证

- [x] Task 6: 编写单元测试
  - [x] SubTask 6.1: 创建 `AttributeDefinitionSchemaTest.java` 文件
  - [x] SubTask 6.2: 测试枚举定义
  - [x] SubTask 6.3: 测试属性类创建
  - [x] SubTask 6.4: 测试全局属性定义
  - [x] SubTask 6.5: 测试类型守卫方法
  - [x] SubTask 6.6: 运行测试并确保通过

## 任务依赖
```
Task 1 (基础类型)
    ↓
Task 2 (属性定义类)
    ↓
Task 3 (全局属性) ─┐
                   ├──→ Task 5 (类型守卫) → Task 6 (单元测试)
Task 4 (STIX属性) ─┘
```

## 预估工作量

| 子任务 | 预估代码行数 | 实际代码行数 | 说明 |
|--------|--------------|--------------|------|
| T1: 基础类型 | ~50行 | ~50行 | 枚举定义 |
| T2: 属性定义类 | ~300行 | ~380行 | 各种Attribute类 |
| T3: 全局属性 | ~250行 | ~200行 | 全局属性实例 |
| T4: STIX属性 | ~150行 | ~280行 | STIX属性实例 |
| T5: 类型守卫 | ~50行 | ~30行 | 类型判断方法 |
| T6: 单元测试 | ~200行 | ~360行 | 测试用例 |
| **总计** | **~1000行** | **~1300行** | 功能完整 |

## 完成标准
- [x] 所有枚举类型已定义
- [x] 所有属性定义类已实现
- [x] 所有全局属性已定义
- [x] 所有STIX属性已定义
- [x] 所有类型守卫方法已实现
- [x] 单元测试已编写并通过（32个测试用例）

## 注意事项
1. 依赖SchemaTypesDefinition和SchemaGeneral中的常量
2. 类型注册通过静态代码块完成，确保类加载时自动注册
3. 方法已处理null值，避免NullPointerException
4. 常量命名保持与TypeScript源码一致
