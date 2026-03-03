package io.opencti.common.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 重写自: opencti-platform/opencti-graphql/config/default.json
 * RabbitMQ消息队列配置属性
 * 
 * 原始配置项:
 * - queue_prefix: 队列前缀
 * - hostname: 主机名
 * - vhost: 虚拟主机
 * - use_ssl: 是否使用SSL
 * - use_ssl_ca: SSL CA证书列表
 * - port: 端口
 * - port_management: 管理端口
 * - management_ssl: 管理端口是否使用SSL
 * - username: 用户名
 * - password: 密码
 * - queue_type: 队列类型 (classic/quorum)
 */
public record RabbitMQProperties(
    @NotBlank String hostname,
    @Min(1) int port,
    String username,
    String password,
    String vhost,
    boolean useSsl,
    List<String> useSslCa,
    @Min(1) int connectionTimeout,
    @Min(1) int heartbeatInterval,
    @Min(1) int prefetchCount,
    @Min(1) int maxRetries,
    @Min(1) int retryDelay,
    @Min(1) int queueMaxPriority,
    String exchangeName,
    String queuePrefix,
    @Min(1) int consumerConcurrency,
    boolean useQuorumQueues,
    // 新增配置项（与 TypeScript 源码一致）
    @Min(1) int portManagement,
    boolean managementSsl
) {
    /**
     * 获取管理端口
     * 默认值: 15672
     */
    public int getPortManagement() {
        return portManagement > 0 ? portManagement : 15672;
    }
    
    /**
     * 获取队列类型
     * useQuorumQueues=true 对应 quorum, false 对应 classic
     */
    public String getQueueType() {
        return useQuorumQueues ? "quorum" : "classic";
    }
}
