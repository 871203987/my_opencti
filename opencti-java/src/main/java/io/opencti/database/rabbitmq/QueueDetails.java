package io.opencti.database.rabbitmq;

/**
 * 队列详情
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js 第 89-108 行
 * 
 * 源码:
 * export const getConnectorQueueDetails = async (connectorId) => {
 *   try {
 *     const httpClient = await amqpHttpClient();
 *     const pathRabbit = `/api/queues${isEmptyField(VHOST_PATH) ? '/%2F' : VHOST_PATH}/${RABBITMQ_PUSH_QUEUE_PREFIX}${connectorId}`;
 * 
 *     const queueDetailResponse = await httpClient.get(pathRabbit).then((response) => response.data);
 *     logApp.debug('Rabbit HTTP API response', { queueDetailResponse });
 *     return {
 *       messages_number: queueDetailResponse.messages || 0,
 *       messages_size: queueDetailResponse.message_bytes || 0,
 *     };
 *   } catch (e) {
 *     ...
 *     return {
 *       messages_number: 0,
 *       messages_size: 0,
 *     };
 *   }
 * };
 */
public record QueueDetails(
        int messagesNumber,
        long messagesSize
) {
    /**
     * 创建空的队列详情（用于错误情况）
     */
    public static QueueDetails empty() {
        return new QueueDetails(0, 0L);
    }
}
