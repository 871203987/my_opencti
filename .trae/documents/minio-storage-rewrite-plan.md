# MinIO 文件存储模块重写计划

## 一、源码分析

### 1.1 源文件清单

| 文件 | 行数 | 说明 |
|------|------|------|
| `src/database/raw-file-storage.ts` | 218行 | 底层S3/MinIO操作 |
| `src/database/file-storage.ts` | 825行 | 高层文件存储操作 |

**总计**: 约1043行TypeScript代码

### 1.2 raw-file-storage.ts 功能分析

| 功能 | 方法名 | 行号 | 复杂度 |
|------|--------|------|--------|
| S3客户端初始化 | `initializeFileStorageClient()` | 67-81 | ⭐⭐⭐ |
| 存储桶初始化 | `initializeBucket()` | 83-94 | ⭐⭐ |
| 存储桶删除 | `deleteBucket()` | 96-104 | ⭐⭐ |
| 存储初始化 | `storageInit()` | 106-109 | ⭐ |
| 存储健康检查 | `isStorageAlive()` | 111 | ⭐ |
| 删除文件 | `deleteFileFromStorage()` | 113-118 | ⭐ |
| 下载文件 | `downloadFile()` | 126-146 | ⭐⭐⭐ |
| 流转字符串 | `streamToString()` | 148-158 | ⭐⭐ |
| 获取文件内容 | `getFileContent()` | 160-169 | ⭐⭐ |
| 复制文件 | `rawCopyFile()` | 171-179 | ⭐⭐ |
| 获取文件大小 | `getFileSize()` | 184-194 | ⭐⭐ |
| 上传文件 | `rawUpload()` | 196-206 | ⭐⭐ |
| 列出对象 | `rawListObjects()` | 208-218 | ⭐⭐⭐ |

### 1.3 file-storage.ts 功能分析

| 功能 | 方法名 | 行号 | 复杂度 |
|------|--------|------|--------|
| 加载文件 | `loadFile()` | 101-175 | ⭐⭐⭐⭐ |
| 删除文件 | `deleteFile()` | 186-209 | ⭐⭐⭐ |
| 批量删除文件 | `deleteFiles()` | 218-225 | ⭐⭐ |
| 原始删除文件 | `deleteRawFiles()` | 235-243 | ⭐⭐ |
| 复制文件 | `copyFile()` | 252-279 | ⭐⭐⭐ |
| 文件转换器 | `storeFileConverter()` | 284-292 | ⭐ |
| 获取文件名 | `getFileName()` | 299-302 | ⭐ |
| 猜测MIME类型 | `guessMimeType()` | 309-327 | ⭐⭐ |
| 文件排除检查 | `isFileObjectExcluded()` | 335-338 | ⭐ |
| 文件适配 | `filesAdaptation()` | 346-358 | ⭐⭐ |
| 文件列表 | `loadedFilesListing()` | 374-414 | ⭐⭐⭐⭐ |
| 上传导入作业 | `uploadJobImport()` | 433-504 | ⭐⭐⭐⭐⭐ |
| 触发导入作业 | `triggerJobImport()` | 516-539 | ⭐⭐⭐ |
| 上传文件 | `upload()` | 555-635 | ⭐⭐⭐⭐⭐ |
| 流转换器 | `streamConverter()` | 642-650 | ⭐⭐ |
| 文件转流 | `fileToReadStream()` | 685-689 | ⭐⭐ |
| 删除对象所有文件 | `deleteAllObjectFiles()` | 699-737 | ⭐⭐⭐⭐ |
| 删除草稿文件 | `deleteAllDraftFiles()` | 745-752 | ⭐⭐⭐ |
| 删除存储桶内容 | `deleteAllBucketContent()` | 760-779 | ⭐⭐⭐ |
| 移动文件 | `moveAllFilesFromEntityToAnother()` | 789-825 | ⭐⭐⭐⭐ |

### 1.4 依赖关系

```
file-storage.ts
├── raw-file-storage.ts (底层S3操作)
├── document-domain.ts (文档索引)
├── rabbitmq.ts (消息队列)
├── middleware-loader.ts (数据加载)
├── work.ts (工作管理)
├── engine.ts (ES引擎)
├── cluster-module.ts (集群模块)
└── draft-utils.ts (草稿工具)
```

## 二、Java实现计划

### 2.1 目录结构

```
src/main/java/io/opencti/database/storage/
├── FileStorageClient.java           # 文件存储客户端接口
├── FileStorageClientImpl.java       # MinIO客户端实现
├── FileStorageConfig.java           # 存储配置类
├── FileMetadata.java                # 文件元数据
├── LoadedFile.java                  # 加载的文件信息
├── S3FileObject.java                # S3文件对象
├── FileStorageService.java          # 文件存储服务接口
├── FileStorageServiceImpl.java      # 文件存储服务实现
├── FileUploadData.java              # 文件上传数据
├── FileUploadOpts.java              # 文件上传选项
└── FileStorageConstants.java        # 常量定义
```

### 2.2 子任务划分

根据重写原则第10条，代码量超过500行需分多个子任务完成。

#### 子任务1: 底层S3客户端 (raw-file-storage.ts)

**预估代码量**: ~350行

| 文件 | 说明 |
|------|------|
| `FileStorageClient.java` | 接口定义 (~80行) |
| `FileStorageClientImpl.java` | 实现类 (~250行) |
| `FileStorageConfig.java` | 配置类 (~50行) |

**功能清单**:
- [ ] S3客户端初始化
- [ ] 存储桶管理 (创建/删除/检查)
- [ ] 文件上传
- [ ] 文件下载
- [ ] 文件删除
- [ ] 文件复制
- [ ] 文件列表
- [ ] 获取文件大小
- [ ] 获取文件内容

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 逐行对比raw-file-storage.ts，确保功能逻辑一致

---

#### 子任务2: 文件元数据模型

**预估代码量**: ~150行

| 文件 | 说明 |
|------|------|
| `FileMetadata.java` | 文件元数据 (~60行) |
| `LoadedFile.java` | 加载文件信息 (~50行) |
| `S3FileObject.java` | S3文件对象 (~40行) |

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 对比TypeScript接口定义，确保属性一致

---

#### 子任务3: 文件存储服务基础功能

**预估代码量**: ~400行

| 文件 | 说明 |
|------|------|
| `FileStorageService.java` | 服务接口 (~100行) |
| `FileStorageServiceImpl.java` | 服务实现 (基础部分) (~300行) |

**功能清单**:
- [ ] loadFile - 加载文件
- [ ] deleteFile - 删除文件
- [ ] deleteFiles - 批量删除
- [ ] copyFile - 复制文件
- [ ] getFileName - 获取文件名
- [ ] guessMimeType - 猜测MIME类型

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 逐行对比file-storage.ts相关方法，确保功能逻辑一致

---

#### 子任务4: 文件存储服务高级功能

**预估代码量**: ~450行

| 文件 | 说明 |
|------|------|
| `FileStorageServiceImpl.java` | 服务实现 (高级部分) (~350行) |
| `FileUploadData.java` | 上传数据 (~40行) |
| `FileUploadOpts.java` | 上传选项 (~60行) |

**功能清单**:
- [ ] upload - 上传文件
- [ ] uploadJobImport - 上传导入作业
- [ ] loadedFilesListing - 文件列表
- [ ] deleteAllObjectFiles - 删除对象所有文件
- [ ] moveAllFilesFromEntityToAnother - 移动文件

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 逐行对比file-storage.ts相关方法，确保功能逻辑一致

---

#### 子任务5: 常量和工具类

**预估代码量**: ~100行

| 文件 | 说明 |
|------|------|
| `FileStorageConstants.java` | 常量定义 (~50行) |
| `FileStorageUtils.java` | 工具方法 (~50行) |

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 源码对比: 确保常量值与源码一致

---

#### 子任务6: 单元测试

**预估代码量**: ~400行

| 文件 | 说明 |
|------|------|
| `FileStorageClientTest.java` | 客户端测试 (~200行) |
| `FileStorageServiceTest.java` | 服务测试 (~200行) |

**验证步骤**:
1. 编译验证: `mvn compile -q`
2. 测试验证: `mvn test -q`

---

#### 子任务7: 更新文档

**更新内容**:

1. **更新 opencti-java/MODULE_OVERVIEW.md**:
   - 添加 `database/storage/` 目录结构
   - 更新模块状态表
   - 添加MinIO模块详情
   - 更新文件清单和代码行数
   - 更新测试覆盖统计

2. **更新 项目重写计划.md**:
   - 更新 Phase 2.3 MinIO 文件存储状态为 ✅ 已完成
   - 添加完成日期
   - 更新下一步行动为 Phase 2.4 Elasticsearch 引擎

**验证步骤**:
1. 确保文档目录结构包含所有文件
2. 确保状态和进度正确更新

---

### 2.3 技术要点

1. **MinIO SDK**: 使用 `io.minio:minio:8.5.15`
2. **AWS S3兼容**: 支持MinIO和AWS S3
3. **凭证管理**: 支持静态凭证和AWS角色
4. **SSL/TLS**: 支持HTTPS连接
5. **分片上传**: 大文件分片上传支持

### 2.4 配置项

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| `minio.endpoint` | MinIO端点 | localhost |
| `minio.port` | 端口 | 9000 |
| `minio.access_key` | 访问密钥 | - |
| `minio.secret_key` | 秘密密钥 | - |
| `minio.session_token` | 会话令牌 | - |
| `minio.bucket_name` | 存储桶名称 | opencti-bucket |
| `minio.bucket_region` | 区域 | us-east-1 |
| `minio.use_ssl` | 使用SSL | false |
| `minio.use_aws_role` | 使用AWS角色 | false |
| `minio.excluded_files` | 排除文件列表 | [.DS_Store] |

## 三、执行顺序

```
子任务1 (底层S3客户端) 
    ↓ 编译验证 + 源码对比
子任务2 (元数据模型)
    ↓ 编译验证 + 源码对比
子任务3 (服务基础功能)
    ↓ 编译验证 + 源码对比
子任务4 (服务高级功能)
    ↓ 编译验证 + 源码对比
子任务5 (常量和工具)
    ↓ 编译验证 + 源码对比
子任务6 (单元测试)
    ↓ 测试验证
子任务7 (更新文档)
    ↓ 文档验证
完成
```

## 四、依赖说明

### 4.1 已完成模块

- `common/config/MinioProperties.java` - 配置属性类已存在
- `database/rabbitmq/` - RabbitMQ客户端已完成
- `database/redis/` - Redis客户端已完成

### 4.2 待依赖模块

以下模块在file-storage.ts中被引用，但尚未实现：
- `document-domain.ts` → 需要在后续Phase实现
- `middleware-loader.ts` → 需要在后续Phase实现
- `work.ts` → 需要在后续Phase实现
- `engine.ts` → 需要在后续Phase实现

**处理方案**: 
- 在当前阶段，这些依赖用接口或占位符实现
- 相关方法标记为TODO，待后续模块完成后补充

## 五、验证标准

### 5.1 编译验证

每个子任务完成后执行:
```bash
cd opencti-java && mvn compile -q
```

### 5.2 源码对比

每个子任务完成后:
- 逐行对比TypeScript源码和Java实现
- 确保功能逻辑一致
- 确保没有多余或缺失功能

### 5.3 测试验证

全部完成后执行:
```bash
cd opencti-java && mvn test -q
```

### 5.4 文档验证

确保:
- MODULE_OVERVIEW.md 目录结构包含所有文件
- 项目重写计划.md 状态正确更新

## 六、预计时间

| 子任务 | 预计时间 |
|--------|----------|
| 子任务1: 底层S3客户端 | 2小时 |
| 子任务2: 元数据模型 | 1小时 |
| 子任务3: 服务基础功能 | 2小时 |
| 子任务4: 服务高级功能 | 2小时 |
| 子任务5: 常量和工具 | 0.5小时 |
| 子任务6: 单元测试 | 2小时 |
| 子任务7: 更新文档 | 0.5小时 |
| **总计** | **10小时** |

## 七、风险提示

1. **依赖未实现模块**: file-storage.ts依赖多个未实现模块，需使用占位符
2. **S3兼容性**: 需要测试MinIO和AWS S3的兼容性
3. **大文件处理**: 分片上传逻辑需要仔细实现
4. **并发安全**: 文件操作需要考虑并发场景
