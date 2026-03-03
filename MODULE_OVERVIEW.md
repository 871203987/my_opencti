# OpenCTI 项目根目录说明文档

## 1. 目录概述

OpenCTI 是一个开源的网络威胁情报平台，由 Filigran 公司开发和维护。该项目采用模块化架构设计，支持 STIX 2.1 标准，提供威胁情报的收集、存储、分析和可视化功能。

## 2. 目录结构

```
opencti/
├── .circleci/           # CircleCI 持续集成配置
├── .devcontainer/       # VS Code 开发容器配置
├── .github/             # GitHub 配置和工作流
├── .trae/               # Trae IDE 配置
├── client-python/       # Python 客户端库
├── docs/                # 项目文档站点
├── logs/                # 运行时日志文件
├── opencti-java/        # Java 微服务架构（分析排除）
├── opencti-platform/    # 平台核心代码
├── opencti-worker/      # Python Worker 模块
├── .drone.yml           # Drone CI 配置
├── .gitattributes       # Git 属性配置
├── .gitignore           # Git 忽略规则
├── .grenrc.js           # GitHub Release 配置
├── .pre-commit-config.yaml  # Pre-commit 钩子配置
├── CODE_OF_CONDUCT.md   # 行为准则
├── CONTRIBUTING.md      # 贡献指南
├── LICENSE              # 许可证文件
├── PROJECT_ARCHITECTURE.md  # 项目架构说明
├── PROJECT_OVERVIEW.md  # 项目概览
├── README.md            # 项目说明
├── SECURITY.md          # 安全政策
└── generatelicenseconfig.json  # 许可证生成配置
```

## 3. 核心文件说明

### 3.1 项目说明文件

| 文件 | 说明 |
|------|------|
| README.md | 项目主说明文档，包含项目介绍、安装指南、贡献方式等 |
| CODE_OF_CONDUCT.md | 贡献者行为准则，基于 Contributor Covenant |
| CONTRIBUTING.md | 贡献指南，说明如何参与项目开发 |
| SECURITY.md | 安全政策，说明漏洞报告流程和支持版本 |
| LICENSE | 许可证文件，包含社区版（Apache 2.0）和企业版双重许可 |

### 3.2 配置文件

| 文件 | 说明 |
|------|------|
| .drone.yml | Drone CI/CD 流水线配置，定义完整的测试和部署流程 |
| .grenrc.js | GitHub Release Notes 生成器配置 |
| .pre-commit-config.yaml | Git pre-commit 钩子配置，包含代码格式化和 lint 检查 |
| .gitattributes | Git 属性配置，设置文本文件换行符为 LF |
| .gitignore | Git 忽略规则，排除 IDE、node_modules、缓存等文件 |
| generatelicenseconfig.json | 第三方许可证生成配置 |

## 4. 许可证说明

OpenCTI 采用双重许可模式：

### 4.1 社区版 (Community Edition)
- 许可证：Apache License, Version 2.0
- 开源免费使用
- 无文件头标注的文件默认属于社区版

### 4.2 企业版 (Enterprise Edition)
- 许可证：OpenCTI Enterprise Edition License
- 需要购买许可证
- 提供额外的企业级功能
- 文件头标注 "OpenCTI Enterprise Edition" 的文件属于企业版

## 5. CI/CD 流程

### 5.1 Drone CI 流水线

项目使用 Drone CI 进行持续集成，主要流程包括：

1. **依赖检出** - 克隆项目依赖
2. **许可证验证** - 验证前后端许可证合规性
3. **许可证生成** - 生成第三方许可证文件
4. **API 测试** - 运行后端 GraphQL API 测试
5. **前端测试** - 运行前端单元测试
6. **E2E 测试** - 运行端到端测试
7. **Python 客户端测试** - 多版本 Python 测试（3.9-3.12）
8. **构建产物上传** - 上传测试结果到 JFrog

### 5.2 测试服务

Drone CI 启动以下服务进行测试：
- Redis 8.4.0 - 缓存服务
- Elasticsearch 8.19.9 - 搜索引擎
- MinIO - 对象存储
- RabbitMQ 4.2.2 - 消息队列
- 多个 OpenCTI 实例 - 用于同步和 API 测试

## 6. Pre-commit 钩子

项目配置了以下 pre-commit 钩子：

| 钩子 | 功能 |
|------|------|
| isort | Python 导入排序 |
| black | Python 代码格式化 |
| eslint | JavaScript/TypeScript 代码检查 |
| check-ast | Python 语法检查 |
| check-json | JSON 格式检查 |
| check-toml | TOML 格式检查 |
| check-xml | XML 格式检查 |
| check-yaml | YAML 格式检查 |
| end-of-file-fixer | 文件末尾换行修复 |
| trailing-whitespace | 行尾空白清理 |

## 7. 贡献规范

### 7.1 提交消息格式

所有提交消息必须遵循格式：`[component] Message (#issuenumber)`

组件标识：
- backend - 后端代码
- frontend - 前端代码
- client-python - Python 客户端
- worker - Worker 模块
- docs - 文档
- tools - 工具
- CI - 持续集成

### 7.2 提交签名

所有提交必须签名，需配置 Git 签名环境。

## 8. 安全漏洞报告

### 8.1 报告方式
- 通过 GitHub Security Advisory 提交
- 邮件：security@filigran.io

### 8.2 报告要求
- 漏洞摘要
- 详细信息（访问权限、影响范围）
- 概念验证代码
- 影响总结

### 8.3 响应时间
- 72 小时内响应
- 验证后尽快发布补丁
- 2-8 周延迟披露期

## 9. 社区和支持

### 9.1 沟通渠道
- Slack 社区：https://community.filigran.io
- 邮箱：contact@filigran.io
- GitHub Issues：问题报告和功能请求

### 9.2 数据收集
- 使用遥测：收集匿名统计数据改进产品
- OpenStreetMap：使用专用地图服务器

## 10. 相关链接

- 官网：https://opencti.io
- 文档：https://docs.opencti.io
- 演示环境：https://demo.opencti.io
- 发布包：https://releases.opencti.io
- Docker Hub：https://hub.docker.com/u/opencti
