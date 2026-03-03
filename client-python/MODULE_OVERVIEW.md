# OpenCTI Python 客户端 (pycti) 说明文档

## 1. 模块概述

pycti 是 OpenCTI 平台的官方 Python 客户端库，提供与 OpenCTI API 交互的完整接口。该库支持 STIX 2.1 标准，提供实体管理、关系操作、数据导入导出、连接器开发等功能。

## 2. 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Python | 3.7+ | 运行时环境 |
| requests | - | HTTP 客户端 |
| pika | - | RabbitMQ 客户端 |
| FastAPI | - | 连接器 HTTP 服务 |
| Pydantic | - | 数据验证 |
| OpenTelemetry | - | 遥测和监控 |

## 3. 目录结构

```
client-python/
├── pycti/                    # 主包
│   ├── api/                  # API 客户端
│   ├── connector/            # 连接器框架
│   ├── entities/             # STIX 实体类
│   └── utils/                # 工具函数
├── tests/                    # 测试套件
│   ├── 01-unit/              # 单元测试
│   ├── 02-integration/       # 集成测试
│   ├── cases/                # 测试用例
│   └── data/                 # 测试数据
├── examples/                 # 示例代码
├── docs/                     # 文档
└── pyproject.toml            # 项目配置
```

## 4. 核心模块说明

### 4.1 API 模块 (pycti/api/)

| 文件 | 说明 |
|------|------|
| opencti_api_client.py | 主 API 客户端，包含所有实体和操作 |
| opencti_api_connector.py | 连接器管理 API |
| opencti_api_work.py | Work 任务管理 API |
| opencti_api_draft.py | 草稿管理 API |
| opencti_api_notification.py | 通知管理 API |
| opencti_api_pir.py | PIR 管理 API |
| opencti_api_playbook.py | Playbook 管理 API |
| opencti_api_public_dashboard.py | 公共仪表板 API |
| opencti_api_trash.py | 回收站 API |
| opencti_api_workspace.py | 工作区 API |
| opencti_api_internal_file.py | 内部文件 API |

### 4.2 连接器模块 (pycti/connector/)

| 文件 | 说明 |
|------|------|
| opencti_connector.py | 连接器基类和类型定义 |
| opencti_connector_helper.py | 连接器辅助类，处理配置、消息队列等 |
| opencti_metric_handler.py | 指标处理器，用于遥测 |

### 4.3 实体模块 (pycti/entities/)

#### 4.3.1 STIX 域对象 (SDO)

| 文件 | 实体类型 | 说明 |
|------|----------|------|
| opencti_attack_pattern.py | AttackPattern | 攻击模式 |
| opencti_campaign.py | Campaign | 攻击活动 |
| opencti_case_incident.py | CaseIncident | 事件案例 |
| opencti_case_rfi.py | CaseRfi | 信息请求案例 |
| opencti_case_rft.py | CaseRft | 威胁请求案例 |
| opencti_channel.py | Channel | 渠道 |
| opencti_course_of_action.py | CourseOfAction | 应对措施 |
| opencti_data_component.py | DataComponent | 数据组件 |
| opencti_data_source.py | DataSource | 数据源 |
| opencti_feedback.py | Feedback | 反馈 |
| opencti_grouping.py | Grouping | 分组 |
| opencti_identity.py | Identity | 身份（组织、个人、系统） |
| opencti_incident.py | Incident | 安全事件 |
| opencti_indicator.py | Indicator | 指标 |
| opencti_infrastructure.py | Infrastructure | 基础设施 |
| opencti_intrusion_set.py | IntrusionSet | 入侵集 |
| opencti_language.py | Language | 语言 |
| opencti_location.py | Location | 位置（区域、国家、城市） |
| opencti_malware.py | Malware | 恶意软件 |
| opencti_malware_analysis.py | MalwareAnalysis | 恶意软件分析 |
| opencti_narrative.py | Narrative | 叙事 |
| opencti_note.py | Note | 笔记 |
| opencti_observed_data.py | ObservedData | 观测数据 |
| opencti_opinion.py | Opinion | 意见 |
| opencti_report.py | Report | 报告 |
| opencti_security_coverage.py | SecurityCoverage | 安全覆盖 |
| opencti_task.py | Task | 任务 |
| opencti_threat_actor.py | ThreatActor | 威胁行为者 |
| opencti_threat_actor_group.py | ThreatActorGroup | 威胁行为者组 |
| opencti_threat_actor_individual.py | ThreatActorIndividual | 威胁行为者个人 |
| opencti_tool.py | Tool | 工具 |
| opencti_vulnerability.py | Vulnerability | 漏洞 |

#### 4.3.2 STIX 网络可观测对象 (SCO)

| 文件 | 说明 |
|------|------|
| opencti_stix_cyber_observable.py | 网络可观测对象主类 |
| stix_cyber_observable/opencti_stix_cyber_observable_properties.py | 属性定义 |

#### 4.3.3 STIX 关系对象

| 文件 | 说明 |
|------|------|
| opencti_stix_core_relationship.py | 核心关系 |
| opencti_stix_sighting_relationship.py | 目击关系 |
| opencti_stix_nested_ref_relationship.py | 嵌套引用关系 |

#### 4.3.4 STIX 元对象 (SMO)

| 文件 | 说明 |
|------|------|
| opencti_external_reference.py | 外部引用 |
| opencti_kill_chain_phase.py | 杀伤链阶段 |
| opencti_label.py | 标签 |
| opencti_marking_definition.py | 标记定义 |

#### 4.3.5 管理对象

| 文件 | 说明 |
|------|------|
| opencti_capability.py | 权限能力 |
| opencti_group.py | 用户组 |
| opencti_role.py | 角色 |
| opencti_settings.py | 平台设置 |
| opencti_user.py | 用户 |

#### 4.3.6 通用对象

| 文件 | 说明 |
|------|------|
| opencti_stix.py | STIX 通用操作 |
| opencti_stix_core_object.py | STIX 核心对象 |
| opencti_stix_domain_object.py | STIX 域对象基类 |
| opencti_stix_object_or_stix_relationship.py | 对象或关系联合类型 |
| opencti_event.py | 事件处理 |
| opencti_vocabulary.py | 词汇表 |

### 4.4 工具模块 (pycti/utils/)

| 文件 | 说明 |
|------|------|
| constants.py | 常量定义（实体类型、关系类型等） |
| opencti_logger.py | 日志处理器 |
| opencti_stix2.py | STIX 2.x 数据处理 |
| opencti_stix2_splitter.py | STIX Bundle 拆分器 |
| opencti_stix2_update.py | STIX 对象更新工具 |
| opencti_stix2_utils.py | STIX 工具函数 |
| opencti_stix2_identifier.py | STIX 标识符生成 |

## 5. 核心类说明

### 5.1 OpenCTIApiClient

主 API 客户端类，提供与 OpenCTI 平台的所有交互功能。

**初始化参数：**

| 参数 | 类型 | 说明 |
|------|------|------|
| url | str | OpenCTI API URL |
| token | str | API Token |
| log_level | str | 日志级别（默认 info） |
| ssl_verify | bool/str | SSL 验证（默认 False） |
| proxies | dict | 代理配置 |
| json_logging | bool | JSON 日志格式 |
| bundle_send_to_queue | bool | 发送 Bundle 到队列 |
| cert | str/tuple | 客户端证书 |
| custom_headers | str | 自定义请求头 |
| perform_health_check | bool | 执行健康检查 |
| requests_timeout | int | 请求超时（秒） |
| provider | str | 客户端提供者标识 |

**主要属性：**

```python
api.stix2                    # STIX 2.x 处理器
api.attack_pattern           # 攻击模式操作
api.campaign                 # 攻击活动操作
api.malware                  # 恶意软件操作
api.indicator                # 指标操作
api.threat_actor             # 威胁行为者操作
api.vulnerability            # 漏洞操作
api.report                   # 报告操作
api.connector                # 连接器操作
api.work                     # Work 操作
# ... 更多实体
```

### 5.2 OpenCTIConnectorHelper

连接器辅助类，简化连接器开发。

**主要功能：**
- 配置管理
- 消息队列连接
- 状态管理
- 指标上报
- 日志处理

**配置获取：**

```python
from pycti import OpenCTIConnectorHelper, get_config_variable

config = {
    "connector": {
        "id": "my-connector",
        "name": "My Connector",
        "type": "EXTERNAL_IMPORT"
    }
}

helper = OpenCTIConnectorHelper(config)
value = get_config_variable("ENV_VAR", ["section", "key"], config)
```

### 5.3 OpenCTIStix2

STIX 2.x 数据处理类。

**主要方法：**

| 方法 | 说明 |
|------|------|
| import_bundle_from_json | 从 JSON 导入 Bundle |
| import_bundle | 导入 Bundle 对象 |
| export_entity | 导出实体为 STIX |
| parse_stix | 解析 STIX 数据 |
| format_date | 格式化日期 |

## 6. 连接器类型

| 类型 | 说明 |
|------|------|
| EXTERNAL_IMPORT | 外部数据导入 |
| INTERNAL_IMPORT_FILE | 内部文件导入 |
| INTERNAL_ENRICHMENT | 内部数据富化 |
| INTERNAL_EXPORT_FILE | 内部文件导出 |
| STREAM | 数据流订阅 |

## 7. 使用示例

### 7.1 基本使用

```python
from pycti import OpenCTIApiClient

# 创建客户端
api = OpenCTIApiClient(
    url="http://localhost:8080",
    token="your-api-token"
)

# 创建实体
malware = api.malware.create(
    name="BadApp",
    description="A malicious application",
    is_family=False
)

# 创建关系
api.stix_core_relationship.create(
    fromId=malware["id"],
    toId=target_id,
    relationship_type="targets"
)

# 查询实体
indicators = api.indicator.list(
    filters=[{"key": "pattern_type", "values": ["stix"]}]
)
```

### 7.2 导入 STIX Bundle

```python
import json
from pycti import OpenCTIApiClient

api = OpenCTIApiClient(url, token)

# 从文件导入
with open("bundle.json", "r") as f:
    bundle = json.load(f)
    api.stix2.import_bundle(bundle)

# 从 JSON 字符串导入
api.stix2.import_bundle_from_json(json_string)
```

### 7.3 创建连接器

```python
from pycti import OpenCTIConnectorHelper

class MyConnector:
    def __init__(self):
        self.helper = OpenCTIConnectorHelper({})
    
    def _process_message(self, data):
        # 处理消息
        return "完成"
    
    def start(self):
        self.helper.listen(self._process_message)

if __name__ == "__main__":
    connector = MyConnector()
    connector.start()
```

## 8. 测试

### 8.1 测试结构

```
tests/
├── 01-unit/              # 单元测试
│   ├── api/              # API 测试
│   ├── metric/           # 指标测试
│   ├── stix/             # STIX 测试
│   └── utils/            # 工具测试
├── 02-integration/       # 集成测试
│   ├── connector/        # 连接器测试
│   ├── entities/         # 实体 CRUD 测试
│   └── utils/            # STIX CRUD 测试
└── data/                 # 测试数据
```

### 8.2 运行测试

```bash
# 运行所有测试
pytest tests/

# 运行单元测试
pytest tests/01-unit/

# 运行集成测试
pytest tests/02-integration/
```

## 9. 示例代码

examples/ 目录包含 40+ 个示例脚本：

| 示例 | 说明 |
|------|------|
| create_intrusion_set.py | 创建入侵集 |
| create_indicator_of_campaign.py | 创建攻击活动指标 |
| import_stix2_file.py | 导入 STIX 文件 |
| export_report_stix2.py | 导出报告为 STIX |
| get_all_indicators_using_pagination.py | 分页获取指标 |
| add_label_to_malware.py | 给恶意软件添加标签 |
| ask_enrichment_of_observable.py | 请求可观测对象富化 |

## 10. 常量定义

### 10.1 实体类型

```python
from pycti.utils.constants import (
    IdentityTypes,        # 身份类型
    LocationTypes,        # 位置类型
    ThreatActorTypes,     # 威胁行为者类型
    StixCyberObservableTypes,  # 可观测对象类型
)
```

### 10.2 STIX 扩展

```python
from pycti import (
    STIX_EXT_OCTI,        # OpenCTI 扩展
    STIX_EXT_OCTI_SCO,    # OpenCTI SCO 扩展
    STIX_EXT_MITRE,       # MITRE 扩展
)
```

## 11. 错误处理

| 错误类型 | 说明 |
|----------|------|
| LOCK_ERROR | 锁定错误 |
| MISSING_REFERENCE_ERROR | 缺少引用错误 |
| Bad Gateway | 网关错误 |
| DRAFT_LOCKED | 草稿锁定 |
| Request timed out | 请求超时 |

## 12. 遥测指标

pycti 暴露以下 OpenTelemetry 指标：

| 指标名 | 类型 | 说明 |
|--------|------|------|
| opencti_bundles_timeout_error_counter | Counter | 超时错误数 |
| opencti_bundles_lock_error_counter | Counter | 锁定错误数 |
| opencti_bundles_missing_reference_error_counter | Counter | 缺少引用错误数 |
| opencti_bundles_bad_gateway_error_counter | Counter | 网关错误数 |
| opencti_bundles_technical_error_counter | Counter | 技术错误数 |
| opencti_bundles_success_counter | Counter | 成功处理数 |

## 13. 安装

```bash
# 从 PyPI 安装
pip install pycti

# 从源码安装
git clone https://github.com/OpenCTI-Platform/client-python
cd client-python
pip install -e .
```

## 14. 依赖关系

```
pycti
├── requests (HTTP 客户端)
├── pika (RabbitMQ)
├── python-magic (文件类型检测)
├── datefinder (日期解析)
├── cachetools (缓存)
├── opentelemetry-api (遥测)
└── pydantic (数据验证)
```
