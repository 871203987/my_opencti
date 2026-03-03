package io.opencti.database.rabbitmq;

import java.util.List;

/**
 * RabbitMQ 客户端接口
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js
 */
public interface RabbitMQClient {

    // ===== 连接管理 =====

    /**
     * 检查 RabbitMQ 是否存活
     * 重写源文件: rabbitmq.js 第 350-359 行
     * 
     * 源码:
     * export const rabbitMQIsAlive = async () => {
     *   return amqpExecute(async (channel) => {
     *     const assertExchange = util.promisify(channel.assertExchange).bind(channel);
     *     return assertExchange(CONNECTOR_EXCHANGE, 'direct', { durable: true });
     *   }).catch((e) => {
     *     throw DatabaseError('RabbitMQ seems down', { cause: e });
     *   });
     * };
     */
    boolean isAlive();

    /**
     * 获取 RabbitMQ 版本
     * 重写源文件: rabbitmq.js 第 370-374 行
     * 
     * 源码:
     * export const getRabbitMQVersion = (context) => {
     *   return metrics(context, SYSTEM_USER)
     *     .then((data) => data.overview.rabbitmq_version)
     *     .catch(() => 'Disconnected');
     * };
     */
    String getVersion();

    // ===== 队列管理 =====

    /**
     * 注册连接器队列
     * 重写源文件: rabbitmq.js 第 219-250 行
     * 
     * 源码:
     * export const registerConnectorQueues = async (id, name, type, scope) => {
     *   const listenQueue = `${RABBIT_QUEUE_PREFIX}listen_${id}`;
     *   const pushQueue = `${RABBIT_QUEUE_PREFIX}push_${id}`;
     *   await amqpExecute(async (channel) => {
     *     // 01. Ensure exchange exists
     *     const assertExchange = util.promisify(channel.assertExchange).bind(channel);
     *     await assertExchange(CONNECTOR_EXCHANGE, 'direct', { durable: true });
     *     await assertExchange(WORKER_EXCHANGE, 'direct', { durable: true });
     *     // 02. Ensure listen queue exists
     *     const assertQueue = util.promisify(channel.assertQueue).bind(channel);
     *     await assertQueue(listenQueue, {
     *       exclusive: false,
     *       durable: true,
     *       autoDelete: false,
     *       arguments: { name, config: { id, type, scope }, 'x-queue-type': QUEUE_TYPE },
     *     });
     *     // 03. bind queue for each connector scope
     *     const bindQueue = util.promisify(channel.bindQueue).bind(channel);
     *     await bindQueue(listenQueue, CONNECTOR_EXCHANGE, listenRouting(id), {});
     *     // 04. Create stix push queue
     *     await assertQueue(pushQueue, {
     *       exclusive: false,
     *       durable: true,
     *       autoDelete: false,
     *       arguments: { name, config: { id, type, scope }, 'x-queue-type': QUEUE_TYPE },
     *     });
     *     // 05. Bind push queue to direct default exchange
     *     await bindQueue(pushQueue, WORKER_EXCHANGE, pushRouting(id), {});
     *     return true;
     *   });
     *   return connectorConfig(id);
     * };
     */
    ConnectorConfig registerConnectorQueues(String id, String name, String type, List<String> scope);

    /**
     * 注销连接器
     * 重写源文件: rabbitmq.js 第 327-337 行
     * 
     * 源码:
     * export const unregisterConnector = async (id) => {
     *   const listen = await amqpExecute(async (channel) => {
     *     const deleteQueue = util.promisify(channel.deleteQueue).bind(channel);
     *     return deleteQueue(`${RABBIT_QUEUE_PREFIX}listen_${id}`, {});
     *   });
     *   const push = await amqpExecute(async (channel) => {
     *     const deleteQueue = util.promisify(channel.deleteQueue).bind(channel);
     *     return deleteQueue(`${RABBIT_QUEUE_PREFIX}push_${id}`, {});
     *   });
     *   return { listen, push };
     * };
     */
    void unregisterConnector(String id);

    /**
     * 清空连接器队列
     * 重写源文件: rabbitmq.js 第 80-87 行
     * 
     * 源码:
     * export const purgeConnectorQueues = async (connector) => {
     *   const httpClient = await amqpHttpClient();
     *   const pathPushQueue = `/api/queues${isEmptyField(VHOST_PATH) ? '/%2F' : VHOST_PATH}/${RABBITMQ_PUSH_QUEUE_PREFIX}${connector.id}/contents`;
     *   const pathListenQueue = `/api/queues${isEmptyField(VHOST_PATH) ? '/%2F' : VHOST_PATH}/${RABBITMQ_LISTEN_QUEUE_PREFIX}${connector.id}/contents`;
     *   await httpClient.delete(pathPushQueue).then((response) => response.data);
     *   await httpClient.delete(pathListenQueue).then((response) => response.data);
     * };
     */
    void purgeConnectorQueues(String connectorId);

    /**
     * 初始化内部队列
     * 重写源文件: rabbitmq.js 第 274-280 行
     * 
     * 源码:
     * export const initializeInternalQueues = async () => {
     *   const internalQueues = getInternalQueues();
     *   for (let i = 0; i < internalQueues.length; i += 1) {
     *     const internalQueue = internalQueues[i];
     *     await registerConnectorQueues(internalQueue.id, internalQueue.name, internalQueue.type, internalQueue.scope);
     *   }
     * };
     */
    void initializeInternalQueues();

    /**
     * 强制队列一致性
     * 重写源文件: rabbitmq.js 第 305-325 行
     * 
     * 源码:
     * export const enforceQueuesConsistency = async (context, user) => {
     *   // List all current platform connectors and ensure queues are correctly setup
     *   const connectors = await fullEntitiesList(context, user, [ENTITY_TYPE_CONNECTOR]);
     *   for (let index = 0; index < connectors.length; index += 1) {
     *     const connector = connectors[index];
     *     const scopes = connector.connector_scope ? connector.connector_scope.split(',') : [];
     *     await registerConnectorQueues(connector.internal_id, connector.name, connector.connector_type, scopes);
     *   }
     *   // List all current platform playbooks and ensure queues are correctly setup
     *   const playbooksQueues = await getInternalPlaybookQueues(context, user);
     *   ...
     * };
     */
    void enforceQueuesConsistency();

    // ===== 消息操作 =====

    /**
     * 发送消息
     * 重写源文件: rabbitmq.js 第 158-163 行
     * 
     * 源码:
     * export const send = (exchangeName, routingKey, message) => {
     *   return amqpExecute(async (channel) => {
     *     const publish = util.promisify(channel.publish).bind(channel);
     *     return publish(exchangeName, routingKey, Buffer.from(message), { deliveryMode: 2 });
     *   });
     * };
     */
    void send(String exchangeName, String routingKey, String message);

    /**
     * 推送消息到连接器
     * 重写源文件: rabbitmq.js 第 366-368 行
     * 
     * 源码:
     * export const pushToConnector = (connectorId, message) => {
     *   return send(CONNECTOR_EXCHANGE, listenRouting(connectorId), JSON.stringify(message));
     * };
     */
    void pushToConnector(String connectorId, Object message);

    /**
     * 推送工作到连接器
     * 重写源文件: rabbitmq.js 第 361-364 行
     * 
     * 源码:
     * export const pushToWorkerForConnector = (connectorId, message) => {
     *   const routingKey = pushRouting(connectorId);
     *   return send(WORKER_EXCHANGE, routingKey, JSON.stringify(message));
     * };
     */
    void pushToWorkerForConnector(String connectorId, Object message);

    // ===== 指标 =====

    /**
     * 获取指标
     * 重写源文件: rabbitmq.js 第 165-180 行
     * 
     * 源码:
     * export const metrics = async (context, user) => {
     *   const metricApi = async () => {
     *     const httpClient = await amqpHttpClient();
     *     const overview = await httpClient.get('/api/overview').then((response) => response.data);
     *     const queues = await httpClient.get(`/api/queues${VHOST_PATH}`).then((response) => response.data);
     *     // Compute number of push queues
     *     const platformQueues = queues.filter((q) => q.name.startsWith(RABBIT_QUEUE_PREFIX));
     *     const pushQueues = platformQueues.filter((q) => q.name.startsWith(`${RABBIT_QUEUE_PREFIX}push_`) && q.consumers > 0);
     *     const consumers = pushQueues.length > 0 ? pushQueues[0].consumers : 0;
     *     return { overview, consumers, queues: platformQueues };
     *   };
     *   ...
     * };
     */
    RabbitMQMetrics getMetrics();

    /**
     * 获取连接器队列大小
     * 重写源文件: rabbitmq.js 第 183-191 行
     * 
     * 源码:
     * export const getConnectorQueueSize = async (context, user, connectorId) => {
     *   let stats = metricsCache.get('cached_metrics');
     *   if (!stats) {
     *     stats = await metrics(context, user);
     *     metricsCache.set('cached_metrics', stats);
     *   }
     *   const targetQueues = stats.queues.filter((queue) => queue.name.includes(connectorId));
     *   return targetQueues.length > 0 ? targetQueues.reduce((a, b) => (a.messages ?? 0) + (b.messages ?? 0)) : 0;
     * };
     */
    int getConnectorQueueSize(String connectorId);

    /**
     * 获取最佳后台连接器 ID
     * 重写源文件: rabbitmq.js 第 192-202 行
     * 
     * 源码:
     * export const getBestBackgroundConnectorId = async (context, user) => {
     *   let stats = metricsCache.get('cached_metrics');
     *   if (!stats) {
     *     stats = await metrics(context, user);
     *     metricsCache.set('cached_metrics', stats);
     *   }
     *   // Find the least used push queue
     *   const targetQueues = stats.queues.filter((queue) => queue.name.startsWith(`${RABBIT_QUEUE_PREFIX}push_background-task`));
     *   const bestQueue = targetQueues.sort((a, b) => (a.messages ?? 0) - (b.messages ?? 0))[0];
     *   return bestQueue.name.substring(`${RABBIT_QUEUE_PREFIX}push_`.length);
     * };
     */
    String getBestBackgroundConnectorId();

    // ===== 配置 =====

    /**
     * 获取连接器配置
     * 重写源文件: rabbitmq.js 第 204-214 行
     * 
     * 源码:
     * export const connectorConfig = (id, listen_callback_uri = undefined) => ({
     *   connection: config(),
     *   push: `${RABBIT_QUEUE_PREFIX}push_${id}`,
     *   push_routing: pushRouting(id),
     *   push_exchange: WORKER_EXCHANGE,
     *   listen: `${RABBIT_QUEUE_PREFIX}listen_${id}`,
     *   listen_routing: listenRouting(id),
     *   listen_exchange: CONNECTOR_EXCHANGE,
     *   listen_callback_uri,
     *   dead_letter_routing: listenRouting(CONNECTOR_QUEUE_BUNDLES_TOO_LARGE.id),
     * });
     */
    ConnectorConfig getConnectorConfig(String connectorId);

    /**
     * 获取队列详情
     * 重写源文件: rabbitmq.js 第 89-108 行
     */
    QueueDetails getConnectorQueueDetails(String connectorId);

    /**
     * 获取内部队列列表
     * 重写源文件: rabbitmq.js 第 269-272 行
     * 
     * 源码:
     * export const getInternalQueues = () => {
     *   const backgroundTaskConnectorQueues = getInternalBackgroundTaskQueues();
     *   return [CONNECTOR_QUEUE_BUNDLES_TOO_LARGE, ...DEPRECATED_INTERNAL_QUEUES, ...backgroundTaskConnectorQueues];
     * };
     */
    List<QueueConfig> getInternalQueues();

    /**
     * 删除交换机
     * 重写源文件: rabbitmq.js 第 339-348 行
     * 
     * 源码:
     * export const unregisterExchanges = async () => {
     *   await amqpExecute(async (channel) => {
     *     const deleteExchange = util.promisify(channel.deleteExchange).bind(channel);
     *     return deleteExchange(CONNECTOR_EXCHANGE, {});
     *   });
     *   await amqpExecute(async (channel) => {
     *     const deleteExchange = util.promisify(channel.deleteExchange).bind(channel);
     *     return deleteExchange(WORKER_EXCHANGE, {});
     *   });
     * };
     */
    void unregisterExchanges();
}
