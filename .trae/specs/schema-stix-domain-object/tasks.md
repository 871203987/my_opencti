# Tasks - Schema STIX Domain Object 模块重写

## 任务列表

- [x] Task 1: 创建SDO属性常量
  - [x] SubTask 1.1: 创建 `StixDomainObjectSchema.java` 文件
  - [x] SubTask 1.2: 定义SDO属性常量（ATTRIBUTE_NAME, ATTRIBUTE_DESCRIPTION等8个）
  - [x] SubTask 1.3: 编译验证

- [x] Task 2: 创建SDO实体类型常量
  - [x] SubTask 2.1: 定义攻击相关SDO类型（Attack-Pattern, Campaign等12种）
  - [x] SubTask 2.2: 定义容器相关SDO类型（Note, Report等4种）
  - [x] SubTask 2.3: 定义身份相关SDO类型（Individual, Sector等3种）
  - [x] SubTask 2.4: 定义位置相关SDO类型（City, Country等4种）
  - [x] SubTask 2.5: 定义其他SDO类型（Resolved-Filters）
  - [x] SubTask 2.6: 编译验证

- [x] Task 3: 创建SDO分类列表
  - [x] SubTask 3.1: 定义STIX_DOMAIN_OBJECT_CONTAINER_CASES列表
  - [x] SubTask 3.2: 定义STIX_DOMAIN_OBJECT_CONTAINERS列表
  - [x] SubTask 3.3: 定义STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS列表
  - [x] SubTask 3.4: 定义STIX_DOMAIN_OBJECT_IDENTITIES列表
  - [x] SubTask 3.5: 定义STIX_DOMAIN_OBJECT_LOCATIONS列表
  - [x] SubTask 3.6: 定义STIX_DOMAIN_OBJECT_THREAT_ACTORS列表
  - [x] SubTask 3.7: 定义STIX_DOMAIN_OBJECTS列表
  - [x] SubTask 3.8: 定义STIX_DOMAIN_OBJECT_ALIASED列表
  - [x] SubTask 3.9: 编译验证

- [x] Task 4: 实现SDO类型判断方法
  - [x] SubTask 4.1: 实现 `isStixDomainObject(type)` 方法
  - [x] SubTask 4.2: 实现 `isStixDomainObjectContainer(type)` 方法
  - [x] SubTask 4.3: 实现 `isStixDomainObjectShareableContainer(type)` 方法
  - [x] SubTask 4.4: 实现 `isStixDomainObjectIdentity(type)` 方法
  - [x] SubTask 4.5: 实现 `isStixDomainObjectLocation(type)` 方法
  - [x] SubTask 4.6: 实现 `isStixDomainObjectThreatActor(type)` 方法
  - [x] SubTask 4.7: 实现 `isStixDomainObjectCase(type)` 方法
  - [x] SubTask 4.8: 编译验证

- [x] Task 5: 实现别名相关方法
  - [x] SubTask 5.1: 实现 `isStixObjectAliased(type)` 方法
  - [x] SubTask 5.2: 实现 `registerStixDomainAliased(type)` 方法
  - [x] SubTask 5.3: 实现 `resolveAliasesField(type)` 方法（依赖attribute-definition模块）
  - [x] SubTask 5.4: 编译验证

- [x] Task 6: 实现组织限制列表
  - [x] SubTask 6.1: 定义STIX_ORGANIZATIONS_UNRESTRICTED列表
  - [x] SubTask 6.2: 定义STIX_ORGANIZATIONS_RESTRICTED列表
  - [x] SubTask 6.3: 编译验证

- [x] Task 7: 实现类型注册
  - [x] SubTask 7.1: 实现静态代码块注册所有SDO类型
  - [x] SubTask 7.2: 编译验证

- [x] Task 8: 编写单元测试
  - [x] SubTask 8.1: 创建 `StixDomainObjectSchemaTest.java` 文件
  - [x] SubTask 8.2: 测试SDO属性常量值正确性
  - [x] SubTask 8.3: 测试SDO实体类型常量值正确性
  - [x] SubTask 8.4: 测试SDO分类列表完整性
  - [x] SubTask 8.5: 测试isStixDomainObject方法
  - [x] SubTask 8.6: 测试isStixDomainObjectContainer方法
  - [x] SubTask 8.7: 测试isStixDomainObjectIdentity方法
  - [x] SubTask 8.8: 测试isStixDomainObjectLocation方法
  - [x] SubTask 8.9: 测试isStixObjectAliased方法
  - [x] SubTask 8.10: 测试resolveAliasesField方法
  - [x] SubTask 8.11: 运行测试并确保通过

## 任务依赖
```
Task 1 (属性常量)
    ↓
Task 2 (实体类型常量)
    ↓
Task 3 (分类列表)
    ↓
Task 4 (类型判断方法)
    ↓
Task 5 (别名方法)
    ↓
Task 6 (组织限制列表)
    ↓
Task 7 (类型注册)
    ↓
Task 8 (单元测试)
```

## 预估工作量

| 文件 | 预估代码行数 | 实际代码行数 | 说明 |
|------|--------------|--------------|------|
| StixDomainObjectSchema.java | ~400-500行 | 481行 | 常量定义 + 列表 + 方法 |
| StixDomainObjectSchemaTest.java | ~250-300行 | 357行 | 测试用例 |
| **总计** | **~650-800行** | **838行** | 功能完整 |

## 完成标准
- [x] StixDomainObjectSchema.java 文件已创建
- [x] 8个SDO属性常量已定义
- [x] 29种SDO实体类型常量已定义
- [x] 8个SDO分类列表已定义
- [x] 7个SDO类型判断方法已实现
- [x] 3个别名相关方法已实现
- [x] 2个组织限制列表已定义
- [x] 类型注册已集成
- [x] 单元测试已编写并通过（25个测试用例）

## 注意事项
1. 依赖其他模块的常量需要先检查是否已定义：
   - ENTITY_TYPE_CONTAINER_CASE_INCIDENT/RFI/RFT
   - ENTITY_TYPE_CONTAINER_GROUPING
   - ENTITY_TYPE_CONTAINER_FEEDBACK
   - ENTITY_TYPE_CONTAINER_TASK
   - ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL
   - ENTITY_TYPE_IDENTITY_ORGANIZATION
   - ENTITY_TYPE_IDENTITY_SECURITY_PLATFORM
   - ENTITY_TYPE_DELETE_OPERATION
2. resolveAliasesField方法依赖attribute-definition模块的aliases和xOpenctiAliases
3. 如果依赖模块未完成，可以先创建桩常量或调整任务顺序

## 实现说明
- 所有依赖的实体类型常量（如ENTITY_TYPE_CONTAINER_CASE_INCIDENT等）已在StixDomainObjectSchema.java中直接定义
- 这些常量来自原TypeScript项目中多个模块特定的类型定义文件
- 类型注册通过静态代码块完成，确保类加载时自动注册到SchemaTypesDefinition
