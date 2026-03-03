package io.opencti.database.rabbitmq;

/**
 * RabbitMQ 常量定义
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js 第 15-30 行
 */
public final class RabbitMQConstants {

    private RabbitMQConstants() {
    }

    /**
     * 获取连接器交换机名称
     * 重写源文件: rabbitmq.js 第 15 行
     * export const CONNECTOR_EXCHANGE = `${RABBIT_QUEUE_PREFIX}amqp.connector.exchange`;
     */
    public static String connectorExchange(String prefix) {
        return prefix + "amqp.connector.exchange";
    }

    /**
     * 获取工作交换机名称
     * 重写源文件: rabbitmq.js 第 16 行
     * export const WORKER_EXCHANGE = `${RABBIT_QUEUE_PREFIX}amqp.worker.exchange`;
     */
    public static String workerExchange(String prefix) {
        return prefix + "amqp.worker.exchange";
    }

    /**
     * 获取推送队列前缀
     * 重写源文件: rabbitmq.js 第 29 行
     * const RABBITMQ_PUSH_QUEUE_PREFIX = `${RABBIT_QUEUE_PREFIX}push_`;
     */
    public static String pushQueuePrefix(String prefix) {
        return prefix + "push_";
    }

    /**
     * 获取监听队列前缀
     * 重写源文件: rabbitmq.js 第 30 行
     * const RABBITMQ_LISTEN_QUEUE_PREFIX = `${RABBIT_QUEUE_PREFIX}listen_`;
     */
    public static String listenQueuePrefix(String prefix) {
        return prefix + "listen_";
    }

    /**
     * 获取监听路由键
     * 重写源文件: rabbitmq.js 第 216 行
     * export const listenRouting = (connectorId) => `${RABBIT_QUEUE_PREFIX}listen_routing_${connectorId}`;
     */
    public static String listenRouting(String prefix, String connectorId) {
        return prefix + "listen_routing_" + connectorId;
    }

    /**
     * 获取推送路由键
     * 重写源文件: rabbitmq.js 第 217 行
     * export const pushRouting = (connectorId) => `${RABBIT_QUEUE_PREFIX}push_routing_${connectorId}`;
     */
    public static String pushRouting(String prefix, String connectorId) {
        return prefix + "push_routing_" + connectorId;
    }

    /**
     * 获取推送队列名称
     * 重写源文件: rabbitmq.js 第 221 行
     * const pushQueue = `${RABBIT_QUEUE_PREFIX}push_${id}`;
     */
    public static String pushQueueName(String prefix, String connectorId) {
        return pushQueuePrefix(prefix) + connectorId;
    }

    /**
     * 获取监听队列名称
     * 重写源文件: rabbitmq.js 第 220 行
     * const listenQueue = `${RABBIT_QUEUE_PREFIX}listen_${id}`;
     */
    public static String listenQueueName(String prefix, String connectorId) {
        return listenQueuePrefix(prefix) + connectorId;
    }

    /**
     * 获取后台任务队列名称
     * 重写源文件: rabbitmq.js 第 256 行
     * { id: `background-task-${i}`, name: `[TASK] Internal task processing #${i}`, type: 'internal', scope: ENTITY_TYPE_BACKGROUND_TASK }
     */
    public static String backgroundTaskQueueId(int index) {
        return "background-task-" + index;
    }
}
