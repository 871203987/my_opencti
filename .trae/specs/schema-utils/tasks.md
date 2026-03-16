# Tasks - Schema Utils 模块重写

## 任务列表

- [x] Task 1: ID验证方法
  - [x] SubTask 1.1: 创建 `SchemaUtils.java` 文件
  - [x] SubTask 1.2: 实现 `isStixId(id)` 方法（STIX ID格式验证）
  - [x] SubTask 1.3: 实现 `isInternalId(id)` 方法（UUID格式验证）
  - [x] SubTask 1.4: 实现 `isAnId(id)` 方法（综合ID验证）
  - [x] SubTask 1.5: 编译验证

- [x] Task 2: 哈希和日期方法
  - [x] SubTask 2.1: 实现 `shortHash(element)` 方法（SHA256短哈希）
  - [x] SubTask 2.2: 实现 `isValidDate(stringDate)` 方法（ISO日期验证）
  - [x] SubTask 2.3: 编译验证

- [x] Task 3: 类型转换方法
  - [x] SubTask 3.1: 实现 `generateInternalType(entity)` 方法（STIX转内部类型）
  - [x] SubTask 3.2: 实现 `convertStixToInternalTypes(type)` 方法（批量类型转换）
  - [x] SubTask 3.3: 编译验证

- [x] Task 4: 父类型获取方法
  - [x] SubTask 4.1: 实现 `getParentTypes(type)` 方法（获取所有父类型）
  - [x] SubTask 4.2: 实现 `keepMostRestrictiveTypes(entityTypes)` 方法（过滤冗余类型）
  - [x] SubTask 4.3: 编译验证

- [x] Task 5: 编写单元测试
  - [x] SubTask 5.1: 创建 `SchemaUtilsTest.java` 文件
  - [x] SubTask 5.2: 测试ID验证方法
  - [x] SubTask 5.3: 测试哈希和日期方法
  - [x] SubTask 5.4: 测试类型转换方法
  - [x] SubTask 5.5: 测试父类型获取方法
  - [x] SubTask 5.6: 运行测试并确保通过

## 任务依赖
```
Task 1 (ID验证)
    ↓
Task 2 (哈希和日期)
    ↓
Task 3 (类型转换)
    ↓
Task 4 (父类型获取)
    ↓
Task 5 (单元测试)
```

## 预估工作量

| 文件 | 预估代码行数 | 实际代码行数 | 说明 |
|------|--------------|--------------|------|
| SchemaUtils.java | ~150-200行 | 388行 | 工具方法实现 |
| SchemaUtilsTest.java | ~100-150行 | 240行 | 测试用例 |
| **总计** | **~250-350行** | **628行** | 功能完整 |

## 完成标准
- [x] SchemaUtils.java 文件已创建
- [x] 3个ID验证方法已实现
- [x] 1个哈希方法已实现
- [x] 1个日期验证方法已实现
- [x] 2个类型转换方法已实现
- [x] 2个父类型相关方法已实现
- [x] 单元测试已编写并通过（19个测试用例）

## 注意事项
1. 依赖多个Schema模块中的类型判断方法
2. isStixId使用正则表达式匹配 `[a-z-]+--[\w-]{36}` 格式
3. isInternalId使用UUID格式验证
4. shortHash使用SHA256算法，取前8位
5. isValidDate需要验证ISO 8601格式
6. generateInternalType需要处理多种特殊类型映射
7. getParentTypes需要递归构建父类型层次结构
