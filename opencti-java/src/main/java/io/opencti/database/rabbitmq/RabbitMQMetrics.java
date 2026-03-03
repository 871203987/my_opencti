package io.opencti.database.rabbitmq;

import java.util.List;
import java.util.Map;

/**
 * RabbitMQ 指标
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js 第 165-180 行
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
public record RabbitMQMetrics(
        Map<String, Object> overview,
        int consumers,
        List<Map<String, Object>> queues
) {
    /**
     * 获取 RabbitMQ 版本
     * 重写源文件: rabbitmq.js 第 370-374 行
     * export const getRabbitMQVersion = (context) => {
     *   return metrics(context, SYSTEM_USER)
     *     .then((data) => data.overview.rabbitmq_version)
     *     .catch(() => 'Disconnected');
     * };
     */
    public String getRabbitMQVersion() {
        if (overview == null) {
            return "Disconnected";
        }
        Object version = overview.get("rabbitmq_version");
        return version != null ? version.toString() : "Unknown";
    }
}
