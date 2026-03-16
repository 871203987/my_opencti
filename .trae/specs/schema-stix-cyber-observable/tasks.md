# Tasks - Schema STIX Cyber Observable 模块重写

## 任务列表

- [x] Task 1: 创建STIX网络可观测对象类型常量
  - [x] SubTask 1.1: 创建 `StixCyberObservableSchema.java` 文件
  - [x] SubTask 1.2: 定义标准STIX类型常量（Autonomous-System, Directory, Domain-Name等20种）
  - [x] SubTask 1.3: 定义OpenCTI自定义类型常量（Cryptographic-Key, Cryptocurrency-Wallet等13种）
  - [x] SubTask 1.4: 编译验证

- [x] Task 2: 创建STIX网络可观测对象列表
  - [x] SubTask 2.1: 定义STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES列表（3种）
  - [x] SubTask 2.2: 定义STIX_CYBER_OBSERVABLES列表（33种）
  - [x] SubTask 2.3: 编译验证

- [x] Task 3: 实现类型注册
  - [x] SubTask 3.1: 实现静态代码块注册STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES到ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE
  - [x] SubTask 3.2: 实现静态代码块注册STIX_CYBER_OBSERVABLES到ABSTRACT_STIX_CYBER_OBSERVABLE
  - [x] SubTask 3.3: 编译验证

- [x] Task 4: 实现类型判断方法
  - [x] SubTask 4.1: 实现 `isStixCyberObservableHashedObservable(type)` 方法
  - [x] SubTask 4.2: 实现 `isStixCyberObservable(type)` 方法
  - [x] SubTask 4.3: 编译验证

- [x] Task 5: 编写单元测试
  - [x] SubTask 5.1: 创建 `StixCyberObservableSchemaTest.java` 文件
  - [x] SubTask 5.2: 测试类型常量值正确性（抽样测试）
  - [x] SubTask 5.3: 测试STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES列表完整性
  - [x] SubTask 5.4: 测试STIX_CYBER_OBSERVABLES列表完整性
  - [x] SubTask 5.5: 测试isStixCyberObservable方法
  - [x] SubTask 5.6: 测试isStixCyberObservableHashedObservable方法
  - [x] SubTask 5.7: 运行测试并确保通过

## 任务依赖
```
Task 1 (类型常量)
    ↓
Task 2 (可观测对象列表)
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
| StixCyberObservableSchema.java | ~120-150行 | 212行 | 常量定义 + 列表 + 方法 |
| StixCyberObservableSchemaTest.java | ~100-120行 | 210行 | 测试用例 |
| **总计** | **~220-270行** | **422行** | 功能完整 |

## 完成标准
- [x] StixCyberObservableSchema.java 文件已创建
- [x] 33个STIX网络可观测对象类型常量已定义（20标准 + 13自定义）
- [x] 2个可观测对象列表已定义（STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES, STIX_CYBER_OBSERVABLES）
- [x] 类型注册已集成到SchemaTypesDefinition
- [x] isStixCyberObservable方法已实现
- [x] isStixCyberObservableHashedObservable方法已实现
- [x] 单元测试已编写并通过（29个测试用例）

## 类型清单

### 标准STIX类型（20种）
1. Autonomous-System
2. Directory
3. Domain-Name
4. Email-Addr
5. Email-Message
6. Email-Mime-Part-Type
7. Artifact
8. StixFile
9. X509-Certificate
10. IPv4-Addr
11. IPv6-Addr
12. Mac-Addr
13. Mutex
14. Network-Traffic
15. Process
16. Software
17. Url
18. User-Account
19. Windows-Registry-Key
20. Windows-Registry-Value-Type

### OpenCTI自定义类型（13种）
1. Cryptographic-Key
2. Cryptocurrency-Wallet
3. Hostname
4. Text
5. Credential
6. User-Agent
7. Bank-Account
8. Tracking-Number
9. Phone-Number
10. Payment-Card
11. Media-Content
12. Persona
13. SSH-Key

## 注意事项
1. 依赖SchemaTypesDefinition和SchemaGeneral中的常量：
   - ABSTRACT_STIX_CYBER_OBSERVABLE
   - ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE
2. 类型注册通过静态代码块完成，确保类加载时自动注册
3. 方法已处理null值，避免NullPointerException
4. 常量命名保持与TypeScript源码一致
