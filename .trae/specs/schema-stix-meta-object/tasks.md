# Tasks - Schema STIX Meta Object 模块重写

## 任务列表

- [x] Task 1: 创建STIX元对象类型常量
  - [x] SubTask 1.1: 创建 `StixMetaObjectSchema.java` 文件
  - [x] SubTask 1.2: 定义ENTITY_TYPE_LABEL常量
  - [x] SubTask 1.3: 定义ENTITY_TYPE_EXTERNAL_REFERENCE常量
  - [x] SubTask 1.4: 定义ENTITY_TYPE_KILL_CHAIN_PHASE常量
  - [x] SubTask 1.5: 定义ENTITY_TYPE_MARKING_DEFINITION常量
  - [x] SubTask 1.6: 编译验证

- [x] Task 2: 创建STIX元对象列表
  - [x] SubTask 2.1: 定义STIX_EMBEDDED_OBJECT列表
  - [x] SubTask 2.2: 定义STIX_META_OBJECT列表
  - [x] SubTask 2.3: 编译验证

- [x] Task 3: 实现类型注册
  - [x] SubTask 3.1: 实现静态代码块注册STIX_EMBEDDED_OBJECT到ABSTRACT_STIX_EMBEDDED_OBJECT
  - [x] SubTask 3.2: 实现静态代码块注册STIX_META_OBJECT到ABSTRACT_STIX_META_OBJECT
  - [x] SubTask 3.3: 编译验证

- [x] Task 4: 实现类型判断方法
  - [x] SubTask 4.1: 实现 `isStixMetaObject(type)` 方法
  - [x] SubTask 4.2: 编译验证

- [x] Task 5: 编写单元测试
  - [x] SubTask 5.1: 创建 `StixMetaObjectSchemaTest.java` 文件
  - [x] SubTask 5.2: 测试类型常量值正确性
  - [x] SubTask 5.3: 测试STIX_EMBEDDED_OBJECT列表完整性
  - [x] SubTask 5.4: 测试STIX_META_OBJECT列表完整性
  - [x] SubTask 5.5: 测试isStixMetaObject方法
  - [x] SubTask 5.6: 运行测试并确保通过

## 任务依赖
```
Task 1 (类型常量)
    ↓
Task 2 (元对象列表)
    ↓
Task 3 (类型注册)
    ↓
Task 4 (类型判断方法)
    ↓
Task 5 (单元测试)
```

## 预估工作量

| 文件 | 预估代码行数 | 实际代码行数 | 说明 |
|------|--------------|--------------|------|
| StixMetaObjectSchema.java | ~60-80行 | 79行 | 常量定义 + 列表 + 方法 |
| StixMetaObjectSchemaTest.java | ~80-100行 | 137行 | 测试用例 |
| **总计** | **~140-180行** | **216行** | 功能完整 |

## 完成标准
- [x] StixMetaObjectSchema.java 文件已创建
- [x] 4个STIX元对象类型常量已定义
- [x] 2个元对象列表已定义（STIX_EMBEDDED_OBJECT, STIX_META_OBJECT）
- [x] 类型注册已集成到SchemaTypesDefinition
- [x] isStixMetaObject方法已实现
- [x] 单元测试已编写并通过（19个测试用例）

## 注意事项
1. 依赖SchemaTypesDefinition和SchemaGeneral中的常量：
   - ABSTRACT_STIX_META_OBJECT
   - ABSTRACT_STIX_EMBEDDED_OBJECT (在Java中使用字符串字面量"Stix-Embedded-Object")
2. 类型注册通过静态代码块完成，确保类加载时自动注册
3. isStixMetaObject方法需要同时检查类型是否在列表中以及是否等于抽象类型本身
4. isStixMetaObject方法已添加null值检查，避免NullPointerException
