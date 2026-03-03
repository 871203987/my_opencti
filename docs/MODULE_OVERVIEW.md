# OpenCTI 文档站点说明文档

## 1. 模块概述

OpenCTI 文档站点是基于 MkDocs Material 主题构建的静态文档网站，提供 OpenCTI 平台的完整使用文档。文档涵盖部署配置、用户指南、管理配置、API 参考和开发指南等内容。

## 2. 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| MkDocs | - | 静态站点生成器 |
| Material 主题 | - | 文档主题 |
| Python | - | 构建环境 |
| mike | - | 版本管理 |
| glightbox | - | 图片灯箱效果 |

## 3. 目录结构

```
docs/
├── docs/                    # 文档源文件
│   ├── administration/      # 管理文档
│   ├── deployment/          # 部署文档
│   ├── development/         # 开发文档
│   ├── reference/           # 参考文档
│   ├── usage/               # 使用文档
│   ├── assets/              # 静态资源
│   └── index.md             # 首页
├── overrides/               # 主题覆盖
├── mkdocs.yml               # MkDocs 配置
└── README.md                # 说明文件
```

## 4. 文档结构说明

### 4.1 部署与设置 (Deployment & Setup)

| 目录 | 说明 |
|------|------|
| deployment/overview.md | 平台概述 |
| deployment/installation.md | 安装指南 |
| deployment/configuration.md | 配置说明 |
| deployment/authentication.md | 认证配置 |
| deployment/telemetry.md | 遥测配置 |
| deployment/upgrade.md | 升级指南 |
| deployment/connectors.md | 连接器部署 |
| deployment/integrations.md | 集成说明 |
| deployment/managers.md | 平台管理器 |
| deployment/clustering.md | 集群部署 |
| deployment/rollover.md | 索引滚动 |
| deployment/map.md | 地图服务器 |
| deployment/ai-import-document.md | AI 文档导入 |
| deployment/troubleshooting.md | 故障排除 |
| deployment/breaking-changes/ | 重大变更说明 |

### 4.2 用户指南 (User Guide)

| 目录 | 说明 |
|------|------|
| usage/getting-started.md | 入门指南 |
| usage/data-model.md | 数据模型 |
| usage/nested.md | 嵌套对象 |
| usage/containers.md | 容器 |
| usage/deduplication.md | 去重 |
| usage/reliability-confidence.md | 可靠性和置信度 |
| usage/dates.md | 日期含义 |
| usage/copilot.md | CTI Copilot |
| usage/overview.md | 知识概览 |
| usage/search.md | 搜索功能 |
| usage/insights.md | 洞察和摘要 |
| usage/exploring-*.md | 各类实体探索 |
| usage/pivoting.md | 透视调查 |
| usage/import/ | 数据导入 |
| usage/import-files.md | 文件导入 |
| usage/manual-creation.md | 手动创建 |
| usage/draftWorkspaces.md | 草稿工作区 |
| usage/workbench.md | 分析工作台 |
| usage/inferences.md | 推理规则 |
| usage/enrichment.md | 数据富化 |
| usage/merging.md | 合并对象 |
| usage/refine-content.md | 内容精炼 |
| usage/case-management.md | 案例管理 |
| usage/notifications.md | 通知告警 |
| usage/background-tasks.md | 后台任务 |
| usage/dashboards.md | 自定义仪表板 |
| usage/workflows.md | 工作流 |
| usage/indicators-lifecycle.md | 指标生命周期 |
| usage/automation.md | 自动化 |
| usage/security-coverage.md | 安全覆盖 |
| usage/delete-restore.md | 删除和恢复 |
| usage/feeds.md | 订阅源 |
| usage/export.md | 导出功能 |

### 4.3 管理文档 (Administration)

| 目录 | 说明 |
|------|------|
| administration/introduction.md | 管理简介 |
| administration/enterprise.md | 企业版 |
| administration/merging.md | 合并和去重 |
| administration/csv-mappers.md | CSV 映射器 |
| administration/json-mappers.md | JSON 映射器 |
| administration/parameters.md | 平台参数 |
| administration/policies.md | 安全策略 |
| administration/users.md | 用户和 RBAC |
| administration/protect-sensitive-configuration.md | 保护敏感配置 |
| administration/segregation.md | 标记限制 |
| administration/organization-segregation.md | 组织隔离 |
| administration/authorized-members.md | 授权成员 |
| administration/request-access.md | 访问请求 |
| administration/dissemination-list.md | 分发列表 |
| administration/email-templates.md | 邮件模板 |
| administration/entities.md | 实体类型 |
| administration/reasoning.md | 规则引擎 |
| administration/notifiers.md | 通知器 |
| administration/retentions.md | 保留策略 |
| administration/decay-rules.md | 衰减规则 |
| administration/decay-exclusion-rules.md | 衰减排除规则 |
| administration/exclusion-lists.md | 排除列表 |
| administration/ontologies.md | 分类法 |
| administration/audit/ | 审计日志 |
| administration/file-indexing.md | 文件索引 |
| administration/support-package.md | 支持包 |
| administration/hub.md | XTM Hub |

### 4.4 参考文档 (Reference)

| 目录 | 说明 |
|------|------|
| reference/data-model.md | 数据模型参考 |
| reference/data-processing.md | 数据处理 |
| reference/taxonomy.md | 分类法 |
| reference/artificial-intelligence.md | 人工智能 |
| reference/api.md | GraphQL API |
| reference/filters.md | 过滤器 |
| reference/streaming.md | 数据流 |
| reference/usage-telemetry.md | 使用遥测 |
| reference/fips.md | FIPS 140-2 安装 |

### 4.5 开发文档 (Development)

| 目录 | 说明 |
|------|------|
| development/environment_ubuntu.md | Ubuntu 开发环境 |
| development/environment_windows.md | Windows 开发环境 |
| development/platform.md | 平台开发 |
| development/python.md | Python 库开发 |
| development/connectors.md | 连接器开发 |
| development/integration-manager.md | 集成管理器开发 |
| development/api-usage.md | API 使用（Playground） |

## 5. MkDocs 配置

### 5.1 站点配置

```yaml
site_name: OpenCTI Documentation
site_description: Documentation about OpenCTI
site_author: Filigran
site_url: https://docs.opencti.io
```

### 5.2 主题配置

- 主题：Material
- Logo：assets/logo.png
- 字体：IBM Plex Sans（文本）、Roboto Mono（代码）
- 功能：导航标签、路径、页脚、编辑按钮、代码复制

### 5.3 插件

| 插件 | 说明 |
|------|------|
| mike | 版本管理 |
| search | 搜索功能 |
| git-committers | Git 提交者信息 |
| git-revision-date-localized | 本地化修订日期 |
| glightbox | 图片灯箱 |

### 5.4 Markdown 扩展

- attr_list - 属性列表
- tables - 表格
- admonition - 提示框
- pymdownx.superfences - 超级围栏（支持 Mermaid）
- pymdownx.highlight - 代码高亮
- pymdownx.tabbed - 标签页
- pymdownx.emoji - Emoji 支持
- pymdownx.tasklist - 任务列表

## 6. 图片资源

文档包含大量截图和示意图，位于各模块的 assets/ 目录：

- administration/assets/ - 管理功能截图
- deployment/assets/ - 部署相关截图
- development/assets/ - 开发环境截图
- reference/assets/ - API 参考截图
- usage/assets/ - 用户界面截图

## 7. 构建和部署

### 7.1 本地预览

```bash
# 安装依赖
pip install mkdocs-material

# 启动本地服务器
mkdocs serve

# 构建静态站点
mkdocs build
```

### 7.2 版本发布

使用 mike 进行版本管理：

```bash
# 发布新版本
mike deploy 6.9 latest

# 设置默认版本
mike set-default latest
```

## 8. 文档贡献

### 8.1 编辑文档

1. 在 docs/docs/ 目录下找到对应文件
2. 使用 Markdown 格式编辑
3. 提交 Pull Request

### 8.2 添加图片

1. 将图片放入对应模块的 assets/ 目录
2. 在 Markdown 中引用：`![描述](assets/image.png)`

### 8.3 导航配置

在 mkdocs.yml 的 nav 部分添加新页面：

```yaml
nav:
  - 页面名称: path/to/page.md
```

## 9. 集成服务

### 9.1 Google Analytics

- 追踪 ID：G-DB4K9LZPDZ

### 9.2 社交链接

- GitHub：https://github.com/OpenCTI-Platform
- Slack：https://community.filigran.io
- LinkedIn：https://linkedin.com/company/filigran
- Twitter：https://twitter.com/FiligranHQ

## 10. 文档版本

文档支持多版本发布，通过 mike 管理：

- latest - 最新版本
- 具体版本号 - 如 6.9、6.8 等

## 11. 依赖关系

```
docs
├── mkdocs (静态站点生成)
├── mkdocs-material (主题)
├── mike (版本管理)
├── pymdown-extensions (Markdown 扩展)
└── glightbox (图片灯箱)
```
