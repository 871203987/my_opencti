# OpenCTI Worker 模块说明文档

## 1. 模块概述

opencti-worker 是 OpenCTI 平台的后台任务处理模块，负责从 RabbitMQ 消息队列中消费消息并执行数据处理任务。该模块作为连接器和平台之间的桥梁，处理 STIX 数据导入、事件处理和 Webhook 回调等操作。

## 2. 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Python | 3.9+ | 运行时环境 |
| pycti | 6.9.5 | OpenCTI Python 客户端 |
| pika | - | RabbitMQ 客户端 |
| OpenTelemetry | 1.35.0 | 遥测和监控 |
| Prometheus | 0.56b0 | 指标导出 |

## 3. 目录结构

```
opencti-worker/
├── src/
│   ├── worker.py                    # 主入口文件
│   ├── push_handler.py              # Push 消息处理器
│   ├── listen_handler.py            # Listen 消息处理器
│   ├── message_queue_consumer.py    # 消息队列消费者
│   ├── thread_pool_selector.py      # 线程池选择器
│   ├── config.yml.sample            # 配置文件示例
│   ├── requirements.txt             # Python 依赖
│   ├── .flake8                      # Flake8 配置
│   └── .isort.cfg                   # isort 配置
└── Dockerfile                       # Docker 构建文件
```

## 4. 核心文件说明

### 4.1 worker.py - 主入口

Worker 类是模块的核心，负责：

| 功能 | 说明 |
|------|------|
| 配置加载 | 从 config.yml 或环境变量加载配置 |
| API 客户端初始化 | 创建 OpenCTIApiClient 连接平台 |
| 连接器发现 | 定期获取已注册的连接器列表 |
| 队列消费管理 | 为每个连接器创建和管理消息消费者 |
| 遥测支持 | Prometheus 指标暴露 |
| 优雅关闭 | 处理 SIGINT/SIGTERM 信号 |

**配置项：**

| 配置项 | 环境变量 | 默认值 | 说明 |
|--------|----------|--------|------|
| opencti.url | OPENCTI_URL | - | OpenCTI API URL |
| opencti.token | OPENCTI_TOKEN | - | API Token |
| opencti.ssl_verify | OPENCTI_SSL_VERIFY | false | SSL 验证 |
| opencti.json_logging | OPENCTI_JSON_LOGGING | true | JSON 日志格式 |
| opencti.execution_pool_size | OPENCTI_EXECUTION_POOL_SIZE | 2 | 执行线程池大小 |
| opencti.realtime_execution_pool_size | OPENCTI_REALTIME_EXECUTION_POOL_SIZE | 3 | 实时执行线程池大小 |
| worker.listen_pool_size | WORKER_LISTEN_POOL_SIZE | 5 | 监听线程池大小 |
| worker.log_level | WORKER_LOG_LEVEL | info | 日志级别 |
| worker.telemetry_enabled | WORKER_TELEMETRY_ENABLED | false | 遥测开关 |
| worker.telemetry_prometheus_port | WORKER_PROMETHEUS_TELEMETRY_PORT | 14270 | Prometheus 端口 |
| worker.objects_max_refs | WORKER_OBJECTS_MAX_REFS | 0 | 对象最大引用数 |

### 4.2 push_handler.py - Push 消息处理器

PushHandler 类处理来自连接器的 Push 消息：

**处理的消息类型：**

| 类型 | 说明 |
|------|------|
| bundle | STIX 2.x 数据包导入 |
| event | OpenCTI 内部事件 |

**支持的事件操作：**

| 操作 | 说明 |
|------|------|
| create | 创建实体 |
| update | 更新实体 |
| merge | 合并实体 |
| delete | 删除实体 |
| restore | 从回收站恢复 |
| delete_force | 强制删除 |
| share | 分享实体 |
| unshare | 取消分享 |
| rule_apply | 应用规则 |
| rule_clear | 清除规则 |
| rules_rescan | 重新扫描规则 |
| enrichment | 数据富化 |
| clear_access_restriction | 清除访问限制 |
| revert_draft | 撤销草稿 |

**处理流程：**

1. 解析消息 JSON
2. 设置 API 请求头（applicant_id、playbook_id 等）
3. 检查 Work 是否有效
4. 根据 event_type 处理：
   - bundle: 导入 STIX 数据，大包拆分后重新入队
   - event: 执行对应操作
5. 返回 ack/nack/requeue

### 4.3 listen_handler.py - Listen 消息处理器

ListenHandler 类处理 Webhook 回调消息：

**功能：**
- 接收来自平台的 Listen 消息
- 通过 HTTP POST 转发到连接器的回调 URL
- 使用连接器的 API Token 进行认证

**错误处理：**
- 连接错误或超时：返回 requeue，等待重试
- 其他错误：返回 nack，丢弃消息

### 4.4 message_queue_consumer.py - 消息队列消费者

MessageQueueConsumer 类封装 RabbitMQ 消费逻辑：

**功能：**
- 建立与 RabbitMQ 的连接
- 创建独立线程消费队列
- 使用线程池处理消息
- 支持 ack/nack/requeue 操作

**属性：**

| 属性 | 说明 |
|------|------|
| consumer_type | 消费者类型（push/listen） |
| queue_name | 队列名称 |
| pika_parameters | RabbitMQ 连接参数 |
| submit_fn | 线程池提交函数 |
| handle_message | 消息处理函数 |

### 4.5 thread_pool_selector.py - 线程池选择器

ThreadPoolSelector 类管理双线程池：

**功能：**
- 维护默认线程池和实时线程池
- 根据连接器优先级选择线程池
- 池满时自动切换到另一个池

**选择逻辑：**

```
if is_realtime:
    if realtime_pool_full and not default_pool_full:
        → default_pool
    else:
        → realtime_pool
else:
    if default_pool_full and not realtime_pool_full:
        → realtime_pool
    else:
        → default_pool
```

## 5. 架构设计

### 5.1 消息流程

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  Connector  │────→│  RabbitMQ   │────→│   Worker    │
└─────────────┘     └─────────────┘     └─────────────┘
                                               │
                                               ▼
                                        ┌─────────────┐
                                        │ OpenCTI API │
                                        └─────────────┘
```

### 5.2 线程模型

```
Worker (主线程)
├── Push Consumers (多个)
│   ├── ThreadPoolExecutor (default)
│   └── ThreadPoolExecutor (realtime)
└── Listen Consumers (多个)
    └── ThreadPoolExecutor (listen)
```

### 5.3 队列类型

| 队列类型 | 说明 |
|----------|------|
| Push Queue | 连接器推送数据到平台 |
| Listen Queue | 平台发送事件到连接器 |

## 6. 遥测指标

Worker 暴露以下 Prometheus 指标：

| 指标名 | 类型 | 说明 |
|--------|------|------|
| opencti_bundles_global_counter | Counter | 处理的 Bundle 数量 |
| opencti_bundles_processing_time_gauge | Histogram | Bundle 处理时间 |
| opencti_max_ingestion_units | Gauge | 最大摄取单元数 |
| opencti_running_ingestion_units | Gauge | 运行中的摄取单元数 |

## 7. 消息确认机制

| 返回值 | 说明 |
|--------|------|
| ack | 消息处理成功，从队列移除 |
| nack | 消息处理失败，丢弃消息 |
| requeue | 消息处理失败，重新入队 |

## 8. 启动和关闭

### 8.1 启动流程

1. 加载配置文件或环境变量
2. 初始化 OpenCTI API 客户端
3. 启动遥测服务（如果启用）
4. 进入主循环：
   - 获取连接器列表
   - 为每个连接器创建消费者
   - 清理无效队列的消费者
   - 每 60 秒刷新一次

### 8.2 关闭流程

1. 接收 SIGINT/SIGTERM 信号
2. 请求所有消费者停止
3. 等待所有消费者完成
4. 关闭遥测服务
5. 设置退出事件

## 9. 配置示例

```yaml
---
opencti:
  url: 'http://localhost:8080'
  token: 'ChangeMe'
  json_logging: true

worker:
  log_level: 'info'
  telemetry_enabled: false
  objects_max_refs: 0
```

## 10. 依赖关系

```
opencti-worker
├── pycti (OpenCTI Python 客户端)
├── pika (RabbitMQ 客户端)
└── opentelemetry (遥测)
```

## 11. 运行方式

### 11.1 直接运行

```bash
cd src
python worker.py
```

### 11.2 Docker 运行

```bash
docker run -d \
  -e OPENCTI_URL=http://opencti:8080 \
  -e OPENCTI_TOKEN=ChangeMe \
  opencti/worker
```

## 12. 错误处理

### 12.1 连接器不可用

- Worker 继续运行
- 定期重试获取连接器列表
- 记录错误日志

### 12.2 消息处理失败

- 技术错误：nack 并丢弃
- 临时错误（超时、网关错误）：requeue 重试
- 重试间隔：10-30 秒随机抖动

### 12.3 大 Bundle 处理

- 自动拆分大 Bundle
- 拆分后重新入队
- 过大项发送到死信队列
