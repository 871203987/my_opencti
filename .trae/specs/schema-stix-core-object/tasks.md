# Tasks - Schema STIX Core Object 模块重写

## 任务列表

- [x] Task 1: 创建STIX核心对象常量类
  - [x] SubTask 1.1: 创建 `io.opencti.schema.stix` 包
  - [x] SubTask 1.2: 定义 INTERNAL_EXPORTABLE_TYPES 列表
  - [x] SubTask 1.3: 定义 stixCoreObjectOptions 选项
  - [x] SubTask 1.4: 编译验证

- [x] Task 2: 实现类型判断方法
  - [x] SubTask 2.1: 实现 isStixCoreObject(type) 方法
  - [x] SubTask 2.2: 实现 isStixObject(type) 方法
  - [x] SubTask 2.3: 实现 isBasicObject(type) 方法
  - [x] SubTask 2.4: 实现 isStixExportableInStreamData(instance) 方法
  - [x] SubTask 2.5: 编译验证

- [x] Task 3: 编写单元测试
  - [x] SubTask 3.1: 测试常量值正确性
  - [x] SubTask 3.2: 测试类型判断方法
  - [x] SubTask 3.3: 测试选项配置
  - [x] SubTask 3.4: 运行测试并确保通过

## 任务依赖
- Task 2 依赖 Task 1
- Task 3 依赖 Task 2

## 实际完成工作量

| 文件 | 代码行数 | 说明 |
|------|----------|------|
| StoreObject.java | ~11行 | 存储对象类型接口桩类 |
| StixCoreObjectSchema.java | ~138行 | STIX核心对象Schema定义 |
| StixCoreObjectSchemaTest.java | ~196行 | 6个测试方法 |
| **总计** | **~345行** | 功能完整 |

## 完成状态
- ✅ 所有任务已完成
- ✅ INTERNAL_EXPORTABLE_TYPES 列表已定义（2种关系类型）
- ✅ STIX_CORE_OBJECTS_ORDERING 选项已定义（4个排序字段）
- ✅ 4个类型判断方法已实现
- ✅ 单元测试已编写
