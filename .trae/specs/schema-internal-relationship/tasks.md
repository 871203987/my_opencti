# Tasks - Schema Internal Relationship 模块重写

## 任务列表

- [x] Task 1: 创建内部关系常量类
  - [x] SubTask 1.1: 创建 `io.opencti.schema.internal` 包（已存在）
  - [x] SubTask 1.2: 定义内部关系类型常量 (migrates, member-of, participate-to等9种)
  - [x] SubTask 1.3: 定义 INTERNAL_RELATIONSHIPS 列表
  - [x] SubTask 1.4: 编译验证

- [x] Task 2: 实现类型判断方法
  - [x] SubTask 2.1: 实现 isInternalRelationship(type) 方法
  - [x] SubTask 2.2: 实现 isStoreRelationPir(instance) 方法
  - [x] SubTask 2.3: 编译验证

- [x] Task 3: 实现类型注册
  - [x] SubTask 3.1: 实现静态代码块注册
  - [x] SubTask 3.2: 编译验证

- [x] Task 4: 编写单元测试
  - [x] SubTask 4.1: 测试常量值正确性
  - [x] SubTask 4.2: 测试类型判断方法
  - [x] SubTask 4.3: 运行测试并确保通过

## 任务依赖
- Task 2 依赖 Task 1
- Task 3 依赖 Task 2
- Task 4 依赖 Task 3

## 实际完成工作量

| 文件 | 代码行数 | 说明 |
|------|----------|------|
| StoreCommon.java | ~30行 | 存储通用类型接口桩类 |
| StoreRelationPir.java | ~11行 | PIR关系存储类型接口桩类 |
| InternalRelationshipSchema.java | ~99行 | 内部关系Schema定义 |
| InternalRelationshipSchemaTest.java | ~127行 | 5个测试方法 |
| **总计** | **~267行** | 功能完整 |

## 完成状态
- ✅ 所有任务已完成
- ✅ 9种内部关系类型常量已定义
- ✅ INTERNAL_RELATIONSHIPS 列表已定义
- ✅ 2个类型判断方法已实现
- ✅ 类型注册已集成
- ✅ 单元测试已编写
