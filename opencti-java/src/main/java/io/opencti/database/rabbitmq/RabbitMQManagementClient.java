package io.opencti.database.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opencti.common.config.RabbitMQProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RabbitMQ Management API 客户端
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js 第 61-74 行, 80-108 行, 165-180 行
 * 
 * 源码:
 * const amqpHttpClient = async () => {
 *   const ssl = USE_SSL_MGMT ? 's' : '';
 *   const baseURL = `http${ssl}://${HOSTNAME_MGMT}:${PORT_MGMT}`;
 *   const httpClientOptions = {
 *     baseURL,
 *     responseType: 'json',
 *     rejectUnauthorized: RABBITMQ_MGMT_REJECT_UNAUTHORIZED,
 *     auth: {
 *       username: USERNAME,
 *       password: PASSWORD,
 *     },
 *   };
 *   return getHttpClient(httpClientOptions);
 * };
 */
@Component
public class RabbitMQManagementClient {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQManagementClient.class);

    private final RestClient restClient;
    private final RabbitMQProperties properties;
    private final String queuePrefix;
    private final ObjectMapper objectMapper;

    public RabbitMQManagementClient(RabbitMQProperties properties) {
        this.properties = properties;
        this.queuePrefix = properties.queuePrefix() != null ? properties.queuePrefix() : "";
        this.objectMapper = new ObjectMapper();
        
        String protocol = properties.managementSsl() ? "https" : "http";
        String baseUrl = String.format("%s://%s:%d", 
                protocol, 
                properties.hostname(), 
                properties.getPortManagement());
        
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
        
        log.info("[RABBITMQ] Management API client configured for {}", baseUrl);
    }

    /**
     * 获取 vhost 路径编码
     * 重写源文件: rabbitmq.js 第 36 行, 82 行
     * 
     * 源码:
     * const VHOST_PATH = VHOST === '/' ? '' : `/${VHOST}`;
     * const pathPushQueue = `/api/queues${isEmptyField(VHOST_PATH) ? '/%2F' : VHOST_PATH}/${RABBITMQ_PUSH_QUEUE_PREFIX}${connector.id}/contents`;
     */
    private String getVhostPath() {
        String vhost = properties.vhost();
        if (vhost == null || vhost.isEmpty() || "/".equals(vhost)) {
            return "/%2F";
        }
        return "/" + vhost;
    }

    /**
     * 清空连接器队列
     * 重写源文件: rabbitmq.js 第 80-87 行
     * 
     * 源码:
     * export const purgeConnectorQueues = async (connector) => {
     *   const httpClient = await amqpHttpClient();
     *   const pathPushQueue = `/api/queues${isEmptyField(VHOST_PATH) ? '/%2F' : VHOST_PATH}/${RABBITMQ_PUSH_QUEUE_PREFIX}${connector.id}/contents`;
     *   const pathListenQueue = `/api/queues${isEmptyField(VHOST_PATH) ? '/%2F' : VHOST_PATH}/${RABBITMQ_LISTEN_QUEUE_PREFIX}${connector.id}/contents`;
     * 
     *   await httpClient.delete(pathPushQueue).then((response) => response.data);
     *   await httpClient.delete(pathListenQueue).then((response) => response.data);
     * };
     */
    public void purgeConnectorQueues(String connectorId) {
        String pushQueuePath = String.format("/api/queues%s/%s/contents", 
                getVhostPath(), 
                RabbitMQConstants.pushQueueName(queuePrefix, connectorId));
        
        String listenQueuePath = String.format("/api/queues%s/%s/contents", 
                getVhostPath(), 
                RabbitMQConstants.listenQueueName(queuePrefix, connectorId));
        
        try {
            restClient.delete()
                    .uri(pushQueuePath)
                    .retrieve()
                    .toBodilessEntity();
            log.debug("[RABBITMQ] Purged push queue for connector: {}", connectorId);
        } catch (RestClientException e) {
            log.warn("[RABBITMQ] Failed to purge push queue for connector {}: {}", connectorId, e.getMessage());
        }
        
        try {
            restClient.delete()
                    .uri(listenQueuePath)
                    .retrieve()
                    .toBodilessEntity();
            log.debug("[RABBITMQ] Purged listen queue for connector: {}", connectorId);
        } catch (RestClientException e) {
            log.warn("[RABBITMQ] Failed to purge listen queue for connector {}: {}", connectorId, e.getMessage());
        }
    }

    /**
     * 获取队列详情
     * 重写源文件: rabbitmq.js 第 89-108 行
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
     *     logApp.warn('Get connector queue details fail', { cause: e, connectorId });
     *     return {
     *       messages_number: 0,
     *       messages_size: 0,
     *     };
     *   }
     * };
     */
    public QueueDetails getConnectorQueueDetails(String connectorId) {
        String queuePath = String.format("/api/queues%s/%s", 
                getVhostPath(), 
                RabbitMQConstants.pushQueueName(queuePrefix, connectorId));
        
        try {
            Map<String, Object> response = restClient.get()
                    .uri(queuePath)
                    .retrieve()
                    .body(Map.class);
            
            if (response != null) {
                int messages = ((Number) response.getOrDefault("messages", 0)).intValue();
                long messageBytes = ((Number) response.getOrDefault("message_bytes", 0L)).longValue();
                
                log.debug("[RABBITMQ] Queue details for {}: messages={}, bytes={}", 
                        connectorId, messages, messageBytes);
                
                return new QueueDetails(messages, messageBytes);
            }
        } catch (RestClientException e) {
            log.warn("[RABBITMQ] Failed to get queue details for connector {}: {}", connectorId, e.getMessage());
        }
        
        return QueueDetails.empty();
    }

    /**
     * 获取 RabbitMQ 指标
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
    @SuppressWarnings("unchecked")
    public RabbitMQMetrics getMetrics() {
        try {
            Map<String, Object> overview = restClient.get()
                    .uri("/api/overview")
                    .retrieve()
                    .body(Map.class);
            
            List<Map<String, Object>> allQueues = restClient.get()
                    .uri("/api/queues" + getVhostPath())
                    .retrieve()
                    .body(List.class);
            
            List<Map<String, Object>> platformQueues = new ArrayList<>();
            int consumers = 0;
            
            if (allQueues != null) {
                for (Object obj : allQueues) {
                    Map<String, Object> queue = (Map<String, Object>) obj;
                    String queueName = (String) queue.get("name");
                    
                    if (queueName != null && queueName.startsWith(queuePrefix)) {
                        platformQueues.add(queue);
                        
                        if (queueName.startsWith(RabbitMQConstants.pushQueuePrefix(queuePrefix))) {
                            Object consumersObj = queue.get("consumers");
                            int queueConsumers = consumersObj != null ? ((Number) consumersObj).intValue() : 0;
                            if (queueConsumers > 0) {
                                consumers = queueConsumers;
                            }
                        }
                    }
                }
            }
            
            return new RabbitMQMetrics(overview, consumers, platformQueues);
            
        } catch (RestClientException e) {
            log.error("[RABBITMQ] Failed to get metrics: {}", e.getMessage());
            return new RabbitMQMetrics(null, 0, List.of());
        }
    }

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
    public String getRabbitMQVersion() {
        try {
            RabbitMQMetrics metrics = getMetrics();
            return metrics.getRabbitMQVersion();
        } catch (Exception e) {
            log.error("[RABBITMQ] Failed to get version: {}", e.getMessage());
            return "Disconnected";
        }
    }
}
