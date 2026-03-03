package io.opencti.database.rabbitmq;

/**
 * RabbitMQ 连接配置
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js 第 50-59 行
 * 
 * 源码:
 * export const config = () => {
 *   return {
 *     host: HOSTNAME,
 *     vhost: VHOST,
 *     use_ssl: USE_SSL,
 *     port: PORT,
 *     user: USERNAME,
 *     pass: PASSWORD,
 *   };
 * };
 */
public record RabbitMQConnectionConfig(
        String host,
        String vhost,
        boolean useSsl,
        int port,
        String user,
        String pass
) {
    /**
     * 获取 vhost 路径
     * 重写源文件: rabbitmq.js 第 36 行
     * const VHOST_PATH = VHOST === '/' ? '' : `/${VHOST}`;
     */
    public String getVhostPath() {
        return "/".equals(vhost) ? "" : "/" + vhost;
    }

    /**
     * 获取 AMQP URI
     * 重写源文件: rabbitmq.js 第 41-44 行
     * const amqpUri = () => {
     *   const ssl = USE_SSL ? 's' : '';
     *   return `amqp${ssl}://${HOSTNAME}:${PORT}${VHOST_PATH}`;
     * };
     */
    public String getAmqpUri() {
        String ssl = useSsl ? "s" : "";
        return "amqp" + ssl + "://" + host + ":" + port + getVhostPath();
    }
}
