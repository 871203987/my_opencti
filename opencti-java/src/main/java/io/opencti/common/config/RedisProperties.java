package io.opencti.common.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 重写自: opencti-platform/opencti-graphql/config/default.json
 * Redis配置属性
 * 
 * 原始配置项:
 * - redis:mode - single/cluster/sentinel
 * - redis:namespace - 键前缀
 * - redis:hostname - 主机名
 * - redis:port - 端口
 * - redis:hostnames - 集群节点列表
 * - redis:ca - CA证书路径数组
 * - redis:use_ssl - 是否使用SSL
 * - redis:username - 用户名
 * - redis:password - 密码
 * - redis:database - 数据库索引
 * - redis:trimming - 裁剪大小
 * - redis:scale_reads - 读取策略
 * - redis:nat_map - NAT映射
 * - redis:host_ip_family - IP协议版本
 */
public record RedisProperties(
    String mode,
    String namespace,
    @NotBlank String hostname,
    @Min(1) int port,
    List<String> hostnames,
    String username,
    String password,
    @Min(0) int database,
    boolean useSsl,
    List<String> ca,
    String cert,
    String key,
    String keyPassword,
    @Min(1) int maxRetries,
    @Min(1) int connectionTimeout,
    @Min(1) int operationTimeout,
    @Min(1) int clusterMaxRedirects,
    String masterName,
    String sentinelHosts,
    String sentinelMasterName,
    String sentinelPassword,
    String sentinelUsername,
    String sentinelRole,
    Boolean sentinelTls,
    Boolean sentinelFailoverDetector,
    Boolean sentinelUpdateSentinels,
    String sentinelPreferredSlaves,
    @Min(1) int poolMaxTotal,
    @Min(1) int poolMaxIdle,
    @Min(0) int poolMinIdle,
    @Min(1) int streamBatchSize,
    @Min(1) int streamMaxConsumers,
    @Min(1) int streamMaxPendingEntries,
    @Min(1) long trimming,
    String scaleReads,
    List<String> natMap,
    Integer hostIpFamily
) {
    /**
     * 获取键前缀
     * 原始逻辑: REDIS_PREFIX = namespace ? `${namespace}:` : ''
     */
    public String getKeyPrefix() {
        return namespace != null && !namespace.isEmpty() ? namespace + ":" : "";
    }
    
    /**
     * 获取Redis模式
     */
    public RedisMode getRedisMode() {
        if (mode == null || mode.isEmpty()) {
            return RedisMode.SINGLE;
        }
        return switch (mode.toLowerCase()) {
            case "cluster" -> RedisMode.CLUSTER;
            case "sentinel" -> RedisMode.SENTINEL;
            default -> RedisMode.SINGLE;
        };
    }
    
    /**
     * Redis模式枚举
     */
    public enum RedisMode {
        SINGLE, CLUSTER, SENTINEL
    }
}
