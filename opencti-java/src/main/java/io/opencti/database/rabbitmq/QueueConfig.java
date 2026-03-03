package io.opencti.database.rabbitmq;

import java.util.List;

/**
 * 队列配置
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js 第 229-234 行
 * 
 * 源码:
 * await assertQueue(listenQueue, {
 *   exclusive: false,
 *   durable: true,
 *   autoDelete: false,
 *   arguments: { name, config: { id, type, scope }, 'x-queue-type': QUEUE_TYPE },
 * });
 */
public record QueueConfig(
        String id,
        String name,
        String type,
        List<String> scope
) {
    /**
     * 创建内部队列配置
     * 重写源文件: rabbitmq.js 第 256-259 行
     */
    public static QueueConfig forBackgroundTask(int index) {
        return new QueueConfig(
                "background-task-" + index,
                "[TASK] Internal task processing #" + index,
                "internal",
                List.of("BackgroundTask")
        );
    }

    /**
     * 创建死信队列配置
     * 重写源文件: rabbitmq.js 第 267 行
     * const CONNECTOR_QUEUE_BUNDLES_TOO_LARGE = { id: 'too-large-bundle', name: 'Bundle too large for ingestion', type: 'internal', scope: 'dead letter' };
     */
    public static QueueConfig forTooLargeBundle() {
        return new QueueConfig(
                "too-large-bundle",
                "Bundle too large for ingestion",
                "internal",
                List.of("dead letter")
        );
    }
}
