# OpenCTI Front 前端模块说明文档

## 1. 模块概述

opencti-front 是 OpenCTI 平台的前端应用，基于 React 18 和 TypeScript 构建，使用 Material-UI 组件库，通过 Relay 框架与 GraphQL 后端通信。提供威胁情报的可视化、分析和管理功能。

## 2. 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| React | 19.0.0 | UI 框架 |
| TypeScript | 5.9.3 | 类型安全 |
| Material-UI | 7.2.0 | UI 组件库 |
| Relay | 20.1.0 | GraphQL 客户端 |
| React Router | 7.7.1 | 路由管理 |
| CKEditor 5 | 45.2.0 | 富文本编辑器 |
| Recharts | 3.1.2 | 图表库 |
| react-force-graph | 1.48.2 | 知识图谱可视化 |
| Moment.js | 2.30.1 | 日期处理 |
| React Intl | 7.1.11 | 国际化 |

## 3. 目录结构

```
opencti-front/
├── lang/                    # 国际化语言文件
│   ├── front/               # 前端翻译
│   └── back/                # 后端翻译
├── public/                  # 公共资源
│   └── index.html           # HTML 入口
├── src/
│   ├── components/          # 公共组件
│   ├── private/             # 私有（认证后）组件
│   ├── public/              # 公共（未认证）组件
│   ├── relay/               # Relay 配置
│   ├── static/              # 静态资源
│   ├── utils/               # 工具函数
│   ├── app.tsx              # 应用路由配置
│   └── front.tsx            # 应用入口
└── tests_e2e/               # 端到端测试
```

## 4. 核心文件说明

### 4.1 入口文件

| 文件 | 说明 |
|------|------|
| front.tsx | 应用入口，初始化 Relay 环境和 React 根组件 |
| app.tsx | 路由配置，定义公开路由和私有路由 |

### 4.2 Relay 配置 (src/relay/)

| 文件 | 说明 |
|------|------|
| environment.jsx | Relay 环境配置，包含网络层、订阅、错误处理 |
| uploadMiddleware.js | 文件上传中间件 |
| relayTypes.ts | Relay 类型定义 |

### 4.3 公共组件 (src/components/)

#### 4.3.1 仪表板组件 (dashboard/)

| 文件 | 说明 |
|------|------|
| WidgetContainer.tsx | Widget 容器组件 |
| WidgetNumber.tsx | 数字 Widget |
| WidgetDonut.tsx | 环形图 Widget |
| WidgetHorizontalBars.tsx | 横向条形图 Widget |
| WidgetMultiLines.tsx | 多线图 Widget |
| WidgetMultiHeatMap.tsx | 热力图 Widget |
| WidgetTimeline.tsx | 时间线 Widget |
| WidgetTree.tsx | 树形 Widget |
| WidgetWordCloud.tsx | 词云 Widget |
| WidgetRadar.tsx | 雷达图 Widget |
| WidgetScatter.tsx | 散点图 Widget |
| WidgetListCoreObjects.tsx | 核心对象列表 Widget |
| WidgetListRelationships.tsx | 关系列表 Widget |
| WidgetListAudits.tsx | 审计列表 Widget |

#### 4.3.2 数据表格组件 (dataGrid/)

| 文件 | 说明 |
|------|------|
| DataTable.tsx | 数据表格主组件 |
| DataTableFilters.tsx | 表格过滤器 |
| DataTablePagination.tsx | 表格分页 |
| DataTableWithoutFragment.tsx | 无 Fragment 数据表格 |
| components/DataTableBody.tsx | 表格主体 |
| components/DataTableHeader.tsx | 表格头部 |
| components/DataTableLine.tsx | 表格行 |

#### 4.3.3 图谱组件 (graph/)

| 文件 | 说明 |
|------|------|
| Graph.tsx | 知识图谱主组件 |
| GraphContainerKnowledge.tsx | 知识图谱容器 |
| GraphContainerCorrelation.tsx | 关联图谱容器 |
| GraphToolbar.tsx | 图谱工具栏 |
| GraphContext.tsx | 图谱上下文 |
| SimpleGraph2D.tsx | 简单 2D 图谱 |
| components/EntityDetails.tsx | 实体详情 |
| components/RelationshipDetails.tsx | 关系详情 |
| components/GraphToolbar*.tsx | 工具栏功能组件 |

#### 4.3.4 过滤器组件 (filters/)

| 文件 | 说明 |
|------|------|
| FiltersModel.ts | 过滤器模型 |
| FilterValues.tsx | 过滤值组件 |
| FilterChipPopover.tsx | 过滤器弹出框 |
| DateRangeFilter.tsx | 日期范围过滤器 |
| DisplayFilterGroup.tsx | 过滤器组显示 |

#### 4.3.5 字段组件 (fields/)

| 文件 | 说明 |
|------|------|
| RichTextField.tsx | 富文本字段 |
| MarkdownField.jsx | Markdown 字段 |
| SelectField.tsx | 选择字段 |
| SliderField.jsx | 滑块字段 |
| SwitchField.jsx | 开关字段 |
| RatingField.tsx | 评分字段 |
| BulkTextField/ | 批量文本字段 |

#### 4.3.6 列表组件 (list_lines/, list_cards/)

| 文件 | 说明 |
|------|------|
| list_lines/ListLines.jsx | 列表行组件 |
| list_lines/ListLinesContent.jsx | 列表内容 |
| list_cards/ListCards.jsx | 卡片列表组件 |
| list_cards/ListCardsContent.jsx | 卡片内容 |

#### 4.3.7 其他核心组件

| 文件 | 说明 |
|------|------|
| AppIntlProvider.tsx | 国际化提供者 |
| AppThemeProvider.tsx | 主题提供者 |
| ThemeDark.ts | 暗色主题 |
| ThemeLight.ts | 亮色主题 |
| FilterIconButton.tsx | 过滤器图标按钮 |
| ExportButtons.jsx | 导出按钮 |
| CKEditor.tsx | CKEditor 封装 |
| Loader.tsx | 加载组件 |
| Message.tsx | 消息提示组件 |
| Alert.tsx | 警告组件 |
| DeleteDialog.tsx | 删除确认对话框 |
| Breadcrumbs.tsx | 面包屑导航 |

### 4.4 私有组件 (src/private/)

#### 4.4.1 核心文件

| 文件 | 说明 |
|------|------|
| Root.tsx | 私有路由根组件，加载用户和设置数据 |
| Index.tsx | 主布局组件，包含导航栏和内容区 |

#### 4.4.2 导航组件 (private/components/nav/)

| 文件 | 说明 |
|------|------|
| TopBar.tsx | 顶部导航栏 |
| LeftBar.tsx | 左侧导航菜单 |

#### 4.4.3 分析组件 (private/components/analyses/)

| 目录 | 说明 |
|------|------|
| reports/ | 报告管理 |
| groupings/ | 分组管理 |
| notes/ | 笔记管理 |
| opinions/ | 意见管理 |
| malware_analyses/ | 恶意软件分析 |
| external_references/ | 外部引用 |
| security_coverages/ | 安全覆盖 |

#### 4.4.4 威胁组件 (private/components/threats/)

| 目录 | 说明 |
|------|------|
| threat_actors/ | 威胁行为者 |
| intrusions/ | 入侵集 |
| campaigns/ | 攻击活动 |

#### 4.4.5 武器库组件 (private/components/arsenal/)

| 目录 | 说明 |
|------|------|
| malwares/ | 恶意软件 |
| channels/ | 渠道 |
| tools/ | 工具 |
| vulnerabilities/ | 漏洞 |

#### 4.4.6 案例组件 (private/components/cases/)

| 目录 | 说明 |
|------|------|
| case_incidents/ | 事件案例 |
| case_rfis/ | 信息请求案例 |
| case_rfts/ | 威胁请求案例 |
| feedbacks/ | 反馈 |
| tasks/ | 任务 |

#### 4.4.7 数据组件 (private/components/data/)

| 目录 | 说明 |
|------|------|
| connectors/ | 连接器管理 |
| csvMapper/ | CSV 映射器 |
| feeds/ | 订阅源 |
| forms/ | 表单 |
| import/ | 数据导入 |
| ingestionCsv/ | CSV 摄取 |
| ingestionJson/ | JSON 摄取 |
| ingestionRss/ | RSS 摄取 |

#### 4.4.8 设置组件 (private/components/settings/)

| 目录 | 说明 |
|------|------|
| activity/ | 活动审计 |
| groups/ | 用户组 |
| users/ | 用户管理 |
| roles/ | 角色管理 |
| marking_definitions/ | 标记定义 |
| labels/ | 标签管理 |
| status_templates/ | 状态模板 |
| retention/ | 保留策略 |
| file_indexing/ | 文件索引 |
| dissemination_lists/ | 分发列表 |
| exclusion_lists/ | 排除列表 |

#### 4.4.9 通用组件 (private/components/common/)

| 目录 | 说明 |
|------|------|
| ai/ | AI 功能组件 |
| audits/ | 审计图表组件 |
| bulk/ | 批量操作组件 |
| cards/ | 卡片组件 |
| charts/ | 图表组件 |
| containers/ | 容器组件 |
| danger_zone/ | 危险区域组件 |
| draft/ | 草稿组件 |
| drawer/ | 抽屉组件 |
| entreprise_edition/ | 企业版组件 |
| files/ | 文件管理组件 |
| form/ | 表单字段组件 |
| identities/ | 身份组件 |
| lists/ | 列表过滤器组件 |
| location/ | 位置地图组件 |
| menus/ | 菜单组件 |
| model/ | 模型组件 |
| style/ | 样式组件 |

### 4.5 公共组件 (src/public/)

| 目录 | 说明 |
|------|------|
| components/Login.tsx | 登录页面 |
| components/Oauth2Callback.tsx | OAuth2 回调 |
| components/SystemBanners.tsx | 系统横幅 |
| PublicRoot.tsx | 公共路由根组件 |

### 4.6 工具函数 (src/utils/)

| 目录/文件 | 说明 |
|------|------|
| hooks/ | 自定义 Hooks |
| hooks/useAuth.ts | 认证 Hook |
| hooks/useHelper.ts | 辅助函数 Hook |
| hooks/useDraftContext.ts | 草稿上下文 Hook |
| BrowserLanguage.ts | 浏览器语言检测 |
| platformModulesHelper.ts | 平台模块辅助 |
| Time.ts | 时间处理 |
| utils.ts | 通用工具函数 |
| Graph.ts | 图谱工具 |

## 5. 核心功能

### 5.1 路由结构

```
/dashboard/*          - 主仪表板
/search/*             - 搜索
/id/:id               - 实体详情
/search_bulk          - 批量搜索
/analyses/*           - 分析
/cases/*              - 案例
/events/*             - 事件
/threats/*            - 威胁
/arsenal/*            - 武器库
/techniques/*         - 技术
/entities/*           - 实体
/locations/*          - 位置
/data/*               - 数据管理
/trash/*              - 回收站
/pirs/*               - PIR
/workspaces/*         - 工作区
/settings/*           - 设置
/audits/*             - 审计
/profile/*            - 个人资料
/observations/*       - 观测
/xtm-hub/*            - XTM Hub
/public/*             - 公共页面（登录等）
```

### 5.2 认证流程

1. 用户访问应用
2. AuthBoundary 检查认证状态
3. 未认证用户重定向到登录页
4. 认证成功后加载用户数据和设置
5. 创建 UserContext 提供全局状态

### 5.3 国际化

支持语言：
- 德语 (de-de)
- 英语 (en-us)
- 西班牙语 (es-es)
- 法语 (fr-fr)
- 意大利语 (it-it)
- 日语 (ja-jp)
- 韩语 (ko-kr)
- 中文 (zh-cn)
- 俄语 (ru-ru)

### 5.4 主题系统

- 支持亮色/暗色主题
- 可自定义主题颜色
- 支持自定义 Logo
- 主题存储在数据库中

### 5.5 数据获取

- 使用 Relay 进行数据获取
- 支持实时订阅 (WebSocket)
- 支持乐观更新
- 自动错误处理和通知

## 6. 状态管理

### 6.1 全局状态 (UserContext)

```typescript
interface UserContextType {
  me: RootMe_data$data;           // 当前用户
  settings: RootSettings$data;     // 平台设置
  bannerSettings: BannerSettings;  // 横幅设置
  entitySettings: EntitySettings;  // 实体设置
  platformModuleHelpers: ModuleHelper; // 模块辅助
  schema: SchemaType;              // Schema 信息
  isXTMHubAccessible: boolean;     // XTM Hub 可访问性
  about: About;                    // 平台版本信息
  themes: Themes;                  // 主题列表
}
```

### 6.2 消息系统 (MESSAGING$)

```javascript
MESSAGING$ = {
  messages: Subject,          // 消息流
  notifyError: Function,      // 错误通知
  notifySuccess: Function,    // 成功通知
  notifyNLQ: Function,        // NLQ 通知
  toggleNav: Subject,         // 导航切换
  redirect: Subject,          // 重定向
}
```

## 7. 组件开发规范

### 7.1 命名规范

- 组件文件使用 PascalCase
- Hook 文件使用 camelCase
- 类型定义使用 PascalCase

### 7.2 文件组织

```
ComponentName/
├── ComponentName.tsx       # 主组件
├── ComponentNameEdition.tsx # 编辑组件
├── ComponentNameCreation.tsx # 创建组件
├── ComponentNameDetails.tsx  # 详情组件
├── ComponentNamePopover.tsx  # 弹出菜单
└── __generated__/           # Relay 生成类型
```

### 7.3 GraphQL 查询

- 使用 Relay Fragment 组合数据
- 查询定义在组件文件中
- 自动生成 TypeScript 类型

## 8. 构建和开发

### 8.1 开发命令

| 命令 | 说明 |
|------|------|
| yarn start | 开发模式启动 |
| yarn build | 生产构建 |
| yarn lint | 代码检查 |
| yarn test | 运行测试 |
| yarn relay | 生成 Relay 类型 |

### 8.2 环境变量

| 变量 | 说明 |
|------|------|
| REACT_APP_BASE_PATH | 应用基础路径 |

## 9. 依赖关系

```
opencti-front
└── opencti-graphql (后端 API)
```
