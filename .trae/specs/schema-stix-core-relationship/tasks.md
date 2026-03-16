# Tasks - Schema STIX Core Relationship 模块重写

## 任务列表

- [x] Task 1: 创建STIX核心关系常量类
  - [x] SubTask 1.1: 创建 `StixCoreRelationshipSchema.java` 文件
  - [x] SubTask 1.2: 定义标准STIX关系类型常量（40种）
  - [x] SubTask 1.3: 定义OpenCTI扩展关系类型常量（15种）
  - [x] SubTask 1.4: 定义MITRE扩展关系类型常量（3种）
  - [x] SubTask 1.5: 定义STIX_CORE_RELATIONSHIPS列表（58种）
  - [x] SubTask 1.6: 编译验证

- [x] Task 2: 实现关系类型判断方法
  - [x] SubTask 2.1: 实现 isStixCoreRelationship(type) 方法
  - [x] SubTask 2.2: 定义 stixCoreRelationshipOptions 选项
  - [x] SubTask 2.3: 编译验证

- [x] Task 3: 编写单元测试
  - [x] SubTask 3.1: 测试关系类型常量值正确性
  - [x] SubTask 3.2: 测试STIX_CORE_RELATIONSHIPS列表完整性
  - [x] SubTask 3.3: 测试isStixCoreRelationship方法
  - [x] SubTask 3.4: 运行测试并确保通过

## 任务依赖
- Task 2 依赖 Task 1
- Task 3 依赖 Task 2

## 实际工作量

| 文件 | 实际代码行数 | 说明 |
|------|--------------|------|
| StixCoreRelationshipSchema.java | ~313行 | 58种关系常量 + 列表 + 判断方法 |
| StixCoreRelationshipSchemaTest.java | ~209行 | 常量测试 + 方法测试 |
| **总计** | **~522行** | 功能完整 |

## 完成标准
- [x] 58种STIX核心关系类型常量已定义（与源码一致）
- [x] STIX_CORE_RELATIONSHIPS列表包含所有58种关系
- [x] isStixCoreRelationship方法已实现
- [x] stixCoreRelationshipOptions选项已定义
- [x] 单元测试已编写并通过（15个测试全部通过）

## 注意事项
源码中定义了58种关系常量，但STIX_CORE_RELATIONSHIPS列表包含了全部58种。原始分析有误，实际不是61种而是58种。
