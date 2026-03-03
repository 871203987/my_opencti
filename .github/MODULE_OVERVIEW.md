# OpenCTI CI/CD 配置说明文档

## 1. 概述

本文档涵盖 OpenCTI 项目的持续集成和持续部署配置，包括 GitHub Actions、CircleCI 和开发容器配置。

## 2. GitHub 配置 (.github/)

### 2.1 目录结构

```
.github/
├── ISSUE_TEMPLATE/          # Issue 模板
│   ├── bug_report.md        # Bug 报告模板
│   ├── bug_report_pycti.yaml # pycti Bug 报告表单
│   ├── documentation.yaml   # 文档问题表单
│   ├── feature_request.md   # 功能请求模板
│   ├── feature_request_pycti.yaml # pycti 功能请求表单
│   └── question.md          # 问题模板
├── img/                     # 图片资源
│   ├── logo_filigran.png
│   ├── logo_opencti.png
│   └── screenshot.png
├── workflows/               # GitHub Actions 工作流
│   ├── auto-set-labels.yml
│   ├── check-verified-commit.yml
│   ├── ci-docker-build.yml
│   ├── ci-license-check.yml
│   ├── ci-main.yml
│   ├── ci-test-api.yml
│   ├── ci-test-client-python.yml
│   ├── ci-test-frontend.yml
│   ├── codeql-analysis.yml
│   └── test-feature-branch.yml
├── PULL_REQUEST_TEMPLATE.md # PR 模板
└── codeql-config.yml        # CodeQL 配置
```

### 2.2 GitHub Actions 工作流

#### 2.2.1 主 CI 流程 (ci-main.yml)

主 CI 流程协调所有测试：

```yaml
jobs:
  wf-build-image:      # 构建测试镜像
  wf-api-test:         # API 测试
  wf-frontend-test:    # 前端测试
  wf-client-python-test: # Python 客户端测试
  wf-license-check:    # 许可证检查
```

#### 2.2.2 其他工作流

| 工作流 | 触发条件 | 说明 |
|--------|----------|------|
| ci-docker-build.yml | push, PR | 构建 Docker 镜像 |
| ci-test-api.yml | workflow_call | API 测试 |
| ci-test-frontend.yml | workflow_call | 前端测试 |
| ci-test-client-python.yml | workflow_call | Python 客户端测试 |
| ci-license-check.yml | workflow_call | 许可证合规检查 |
| auto-set-labels.yml | issues, PR | 自动设置标签 |
| check-verified-commit.yml | push, PR | 验证提交签名 |
| codeql-analysis.yml | schedule, push | 代码安全分析 |
| test-feature-branch.yml | push | 功能分支测试 |

### 2.3 Issue 模板

#### 2.3.1 Bug 报告模板

包含以下字段：
- 问题描述
- 环境信息（OS、版本、客户端）
- 复现步骤
- 预期输出
- 实际输出
- 附加信息

#### 2.3.2 功能请求模板

用于提交新功能建议。

#### 2.3.3 问题模板

用于一般性问题咨询。

### 2.4 PR 模板

PR 模板包含：
- 变更描述
- 相关 Issue
- 检查清单：
  - 代码完成度
  - 功能测试
  - 测试用例
  - 文档更新
  - 代码重构

## 3. 开发容器配置 (.devcontainer/)

### 3.1 目录结构

```
.devcontainer/
├── Dockerfile              # 开发容器镜像
├── config.json             # OpenCTI 配置
├── devcontainer.json       # VS Code 容器配置
└── install.dependencies.sh # 依赖安装脚本
```

### 3.2 开发容器配置 (devcontainer.json)

```json
{
    "name": "Workbench",
    "remoteUser": "vscode",
    "build": { "dockerfile": "Dockerfile" },
    "containerEnv": {
        "NODE_ENV": "development",
        "CONF": "/opencti/.devcontainer/config.json"
    },
    "extensions": [
        "ms-azuretools.vscode-docker",
        "esbenp.prettier-vscode",
        "dsznajder.es7-react-js-snippets"
    ]
}
```

### 3.3 开发容器镜像 (Dockerfile)

基于 Ubuntu 22.04，包含：
- build-essential - 构建工具
- Python 3 + pip - Python 环境
- Node.js 16 - Node.js 环境
- Docker CE + Compose - 容器工具
- Git + 网络工具 - 开发工具

### 3.4 OpenCTI 开发配置 (config.json)

开发环境默认配置：

| 配置项 | 值 | 说明 |
|--------|-----|------|
| app.port | 4000 | 服务端口 |
| app.base_url | http://localhost:4000/ | 基础 URL |
| redis.hostname | redis | Redis 主机 |
| elasticsearch.url | http://elasticsearch:9200 | ES URL |
| minio.endpoint | minio | MinIO 端点 |
| rabbitmq.hostname | rabbitmq | RabbitMQ 主机 |
| smtp.hostname | postfix | SMTP 主机 |
| admin.password | changeme | 管理员密码 |

## 4. CircleCI 配置 (.circleci/)

### 4.1 配置文件

```
.circleci/
└── config.yml              # CircleCI 配置
```

### 4.2 CircleCI 任务

#### 4.2.1 Python 客户端任务

| 任务 | 说明 |
|------|------|
| ensure_formatting_pycti | 代码格式检查（black, isort） |
| linter_pycti | 代码质量检查（flake8） |
| build_pycti | 构建 Python 包 |
| deploy_pycti | 部署到 PyPI |

#### 4.2.2 平台构建任务

| 任务 | 说明 |
|------|------|
| build_frontend | 构建前端 |
| build_platform | 构建后端平台 |
| build_platform_rolling | 构建滚动版本 |

#### 4.2.3 部署任务

| 任务 | 说明 |
|------|------|
| wait_for_connector_release | 等待连接器发布 |
| deploy_docker_rpm | 部署 RPM 包 |
| deploy_docker_deb | 部署 DEB 包 |

### 4.3 CircleCI Orbs

| Orb | 版本 | 用途 |
|-----|------|------|
| slack | 6.1.2 | Slack 通知 |
| kubernetes | 1.3.1 | Kubernetes 部署 |

### 4.4 工作流触发

- **标签发布**: 当推送 tag 时触发部署
- **主分支**: master 分支推送触发构建
- **滚动发布**: 定期构建最新版本

## 5. CI/CD 流程图

```
┌─────────────────────────────────────────────────────────────┐
│                     Push / PR                                │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                   GitHub Actions                             │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │ Build Image │─▶│  API Test   │  │ Frontend    │         │
│  └─────────────┘  └─────────────┘  │ Test        │         │
│         │                          └─────────────┘         │
│         ▼                                                    │
│  ┌─────────────┐  ┌─────────────┐                           │
│  │ Python Test │  │License Check│                           │
│  └─────────────┘  └─────────────┘                           │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     CircleCI                                 │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │ Format Check│  │ Lint Check  │  │ Build       │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
│                              │                               │
│                              ▼                               │
│                      ┌─────────────┐                        │
│                      │   Deploy    │                        │
│                      └─────────────┘                        │
└─────────────────────────────────────────────────────────────┘
```

## 6. 本地开发环境

### 6.1 使用开发容器

1. 安装 VS Code 和 Dev Containers 扩展
2. 打开项目目录
3. 点击 "Reopen in Container"
4. 等待容器构建完成

### 6.2 开发容器网络

开发容器会自动：
- 创建 `opencti_default` Docker 网络
- 连接到该网络以便与其他服务通信

### 6.3 推荐扩展

| 扩展 | 用途 |
|------|------|
| ms-azuretools.vscode-docker | Docker 管理 |
| esbenp.prettier-vscode | 代码格式化 |
| dsznajder.es7-react-js-snippets | React 代码片段 |

## 7. 安全配置

### 7.1 CodeQL 分析

- 定期扫描代码安全漏洞
- 配置文件：codeql-config.yml

### 7.2 提交签名验证

- 所有提交必须签名
- 通过 check-verified-commit.yml 验证

## 8. 通知配置

### 8.1 Slack 通知

CircleCI 配置了 Slack 通知：
- 构建失败时发送通知
- 仅在 master 分支触发

## 9. 密钥管理

### 9.1 GitHub Secrets

| Secret | 用途 |
|--------|------|
| DOCKER_USERNAME | Docker Hub 用户名 |
| DOCKER_PASSWORD | Docker Hub 密码 |
| PYPI_PASSWORD | PyPI 发布令牌 |
| JFROG_* | JFrog 相关密钥 |

### 9.2 CircleCI Contexts

敏感信息通过 CircleCI Contexts 管理。

## 10. 依赖关系

```
.github/workflows
├── ci-docker-build.yml (镜像构建)
├── ci-test-api.yml (API 测试)
├── ci-test-frontend.yml (前端测试)
└── ci-test-client-python.yml (Python 测试)

.devcontainer
├── Dockerfile (容器镜像)
├── config.json (OpenCTI 配置)
└── devcontainer.json (VS Code 配置)

.circleci
└── config.yml (CircleCI 流水线)
```
