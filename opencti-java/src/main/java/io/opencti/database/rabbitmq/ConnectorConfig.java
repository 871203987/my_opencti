package io.opencti.database.rabbitmq;

/**
 * 连接器配置
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js 第 204-214 行
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
public record ConnectorConfig(
        RabbitMQConnectionConfig connection,
        String push,
        String pushRouting,
        String pushExchange,
        String listen,
        String listenRouting,
        String listenExchange,
        String listenCallbackUri,
        String deadLetterRouting
) {
    /**
     * 创建连接器配置
     * 重写源文件: rabbitmq.js 第 204-214 行
     */
    public static ConnectorConfig create(
            String queuePrefix,
            String connectorId,
            RabbitMQConnectionConfig connection,
            String listenCallbackUri) {
        return new ConnectorConfig(
                connection,
                RabbitMQConstants.pushQueueName(queuePrefix, connectorId),
                RabbitMQConstants.pushRouting(queuePrefix, connectorId),
                RabbitMQConstants.workerExchange(queuePrefix),
                RabbitMQConstants.listenQueueName(queuePrefix, connectorId),
                RabbitMQConstants.listenRouting(queuePrefix, connectorId),
                RabbitMQConstants.connectorExchange(queuePrefix),
                listenCallbackUri,
                RabbitMQConstants.listenRouting(queuePrefix, "too-large-bundle")
        );
    }
}
