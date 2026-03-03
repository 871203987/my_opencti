package io.opencti.database.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opencti.common.config.RabbitMQProperties;
import io.opencti.common.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * RabbitMQ 客户端实现
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js
 */
@Component
public class RabbitMQClientImpl implements RabbitMQClient {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQClientImpl.class);

    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;
    private final RabbitMQManagementClient managementClient;
    private final RabbitMQProperties properties;
    private final String queuePrefix;
    private final ObjectMapper objectMapper;
    
    private final Map<String, RabbitMQMetrics> metricsCache = new ConcurrentHashMap<>();
    private volatile long metricsCacheTime = 0;
    private static final long METRICS_CACHE_TTL_MS = TimeUnit.SECONDS.toMillis(15);

    public RabbitMQClientImpl(
            RabbitTemplate rabbitTemplate,
            RabbitAdmin rabbitAdmin,
            RabbitMQManagementClient managementClient,
            RabbitMQProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitAdmin = rabbitAdmin;
        this.managementClient = managementClient;
        this.properties = properties;
        this.queuePrefix = properties.queuePrefix() != null ? properties.queuePrefix() : "";
        this.objectMapper = new ObjectMapper();
        
        log.info("[RABBITMQ] Client initialized with queue prefix: {}", queuePrefix);
    }

    @Override
    public boolean isAlive() {
        try {
            String connectorExchange = RabbitMQConstants.connectorExchange(queuePrefix);
            rabbitAdmin.declareExchange(new DirectExchange(connectorExchange, true, false));
            return true;
        } catch (Exception e) {
            log.error("[RABBITMQ] Health check failed: {}", e.getMessage());
            throw new DatabaseException("RabbitMQ seems down", e);
        }
    }

    @Override
    public String getVersion() {
        return managementClient.getRabbitMQVersion();
    }

    @Override
    public ConnectorConfig registerConnectorQueues(String id, String name, String type, List<String> scope) {
        String connectorExchange = RabbitMQConstants.connectorExchange(queuePrefix);
        String workerExchange = RabbitMQConstants.workerExchange(queuePrefix);
        String listenQueue = RabbitMQConstants.listenQueueName(queuePrefix, id);
        String pushQueue = RabbitMQConstants.pushQueueName(queuePrefix, id);
        
        try {
            DirectExchange connectorExchangeObj = new DirectExchange(connectorExchange, true, false);
            rabbitAdmin.declareExchange(connectorExchangeObj);
            
            DirectExchange workerExchangeObj = new DirectExchange(workerExchange, true, false);
            rabbitAdmin.declareExchange(workerExchangeObj);
            
            Map<String, Object> queueArguments = new HashMap<>();
            queueArguments.put("name", name);
            queueArguments.put("config", Map.of("id", id, "type", type, "scope", scope));
            queueArguments.put("x-queue-type", properties.getQueueType());
            
            Queue listenQueueObj = QueueBuilder.durable(listenQueue)
                    .withArguments(queueArguments)
                    .build();
            rabbitAdmin.declareQueue(listenQueueObj);
            
            Binding listenBinding = new Binding(
                    listenQueue,
                    Binding.DestinationType.QUEUE,
                    connectorExchange,
                    RabbitMQConstants.listenRouting(queuePrefix, id),
                    new HashMap<>()
            );
            rabbitAdmin.declareBinding(listenBinding);
            
            Queue pushQueueObj = QueueBuilder.durable(pushQueue)
                    .withArguments(queueArguments)
                    .build();
            rabbitAdmin.declareQueue(pushQueueObj);
            
            Binding pushBinding = new Binding(
                    pushQueue,
                    Binding.DestinationType.QUEUE,
                    workerExchange,
                    RabbitMQConstants.pushRouting(queuePrefix, id),
                    new HashMap<>()
            );
            rabbitAdmin.declareBinding(pushBinding);
            
            log.info("[RABBITMQ] Registered queues for connector: {} (listen={}, push={})", 
                    id, listenQueue, pushQueue);
            
            return getConnectorConfig(id);
            
        } catch (Exception e) {
            log.error("[RABBITMQ] Failed to register queues for connector {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to register connector queues", e);
        }
    }

    @Override
    public void unregisterConnector(String id) {
        String listenQueue = RabbitMQConstants.listenQueueName(queuePrefix, id);
        String pushQueue = RabbitMQConstants.pushQueueName(queuePrefix, id);
        
        try {
            rabbitAdmin.deleteQueue(listenQueue);
            log.debug("[RABBITMQ] Deleted listen queue: {}", listenQueue);
            
            rabbitAdmin.deleteQueue(pushQueue);
            log.debug("[RABBITMQ] Deleted push queue: {}", pushQueue);
            
            log.info("[RABBITMQ] Unregistered connector: {}", id);
            
        } catch (Exception e) {
            log.error("[RABBITMQ] Failed to unregister connector {}: {}", id, e.getMessage());
            throw new DatabaseException("Failed to unregister connector", e);
        }
    }

    @Override
    public void purgeConnectorQueues(String connectorId) {
        managementClient.purgeConnectorQueues(connectorId);
    }

    @Override
    public void initializeInternalQueues() {
        List<QueueConfig> internalQueues = getInternalQueues();
        
        for (QueueConfig queue : internalQueues) {
            try {
                registerConnectorQueues(queue.id(), queue.name(), queue.type(), queue.scope());
            } catch (Exception e) {
                log.error("[RABBITMQ] Failed to initialize internal queue {}: {}", queue.id(), e.getMessage());
            }
        }
        
        log.info("[RABBITMQ] Initialized {} internal queues", internalQueues.size());
    }

    @Override
    public void enforceQueuesConsistency() {
        log.info("[RABBITMQ] Enforcing queues consistency");
        
        initializeInternalQueues();
        
        log.info("[RABBITMQ] Queue consistency enforcement completed");
    }

    @Override
    public void send(String exchangeName, String routingKey, String message) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
            log.debug("[RABBITMQ] Sent message to exchange={}, routingKey={}", exchangeName, routingKey);
        } catch (Exception e) {
            log.error("[RABBITMQ] Failed to send message: {}", e.getMessage());
            throw new DatabaseException("Failed to send message to RabbitMQ", e);
        }
    }

    @Override
    public void pushToConnector(String connectorId, Object message) {
        String connectorExchange = RabbitMQConstants.connectorExchange(queuePrefix);
        String routingKey = RabbitMQConstants.listenRouting(queuePrefix, connectorId);
        
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            send(connectorExchange, routingKey, jsonMessage);
            log.debug("[RABBITMQ] Pushed message to connector: {}", connectorId);
        } catch (JsonProcessingException e) {
            log.error("[RABBITMQ] Failed to serialize message: {}", e.getMessage());
            throw new DatabaseException("Failed to serialize message", e);
        }
    }

    @Override
    public void pushToWorkerForConnector(String connectorId, Object message) {
        String workerExchange = RabbitMQConstants.workerExchange(queuePrefix);
        String routingKey = RabbitMQConstants.pushRouting(queuePrefix, connectorId);
        
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            send(workerExchange, routingKey, jsonMessage);
            log.debug("[RABBITMQ] Pushed work to connector: {}", connectorId);
        } catch (JsonProcessingException e) {
            log.error("[RABBITMQ] Failed to serialize message: {}", e.getMessage());
            throw new DatabaseException("Failed to serialize message", e);
        }
    }

    @Override
    public RabbitMQMetrics getMetrics() {
        return managementClient.getMetrics();
    }

    @Override
    public int getConnectorQueueSize(String connectorId) {
        RabbitMQMetrics stats = getCachedMetrics();
        
        int totalMessages = 0;
        for (Map<String, Object> queue : stats.queues()) {
            String queueName = (String) queue.get("name");
            if (queueName != null && queueName.contains(connectorId)) {
                Object messagesObj = queue.get("messages");
                totalMessages += messagesObj != null ? ((Number) messagesObj).intValue() : 0;
            }
        }
        
        return totalMessages;
    }

    @Override
    public String getBestBackgroundConnectorId() {
        RabbitMQMetrics stats = getCachedMetrics();
        
        String bestQueueName = null;
        int minMessages = Integer.MAX_VALUE;
        
        String pushPrefix = RabbitMQConstants.pushQueuePrefix(queuePrefix) + "background-task";
        
        for (Map<String, Object> queue : stats.queues()) {
            String queueName = (String) queue.get("name");
            if (queueName != null && queueName.startsWith(pushPrefix)) {
                Object messagesObj = queue.get("messages");
                int messages = messagesObj != null ? ((Number) messagesObj).intValue() : 0;
                
                if (messages < minMessages) {
                    minMessages = messages;
                    bestQueueName = queueName;
                }
            }
        }
        
        if (bestQueueName != null) {
            return bestQueueName.substring(RabbitMQConstants.pushQueuePrefix(queuePrefix).length());
        }
        
        return "background-task-0";
    }

    @Override
    public ConnectorConfig getConnectorConfig(String connectorId) {
        RabbitMQConnectionConfig connectionConfig = new RabbitMQConnectionConfig(
                properties.hostname(),
                properties.vhost(),
                properties.useSsl(),
                properties.port(),
                properties.username(),
                properties.password()
        );
        
        return ConnectorConfig.create(queuePrefix, connectorId, connectionConfig, null);
    }

    @Override
    public QueueDetails getConnectorQueueDetails(String connectorId) {
        return managementClient.getConnectorQueueDetails(connectorId);
    }

    @Override
    public List<QueueConfig> getInternalQueues() {
        List<QueueConfig> queues = new ArrayList<>();
        
        queues.add(QueueConfig.forTooLargeBundle());
        
        int maxQueues = 4;
        for (int i = 0; i < maxQueues; i++) {
            queues.add(QueueConfig.forBackgroundTask(i));
        }
        
        return queues;
    }

    @Override
    public void unregisterExchanges() {
        String connectorExchange = RabbitMQConstants.connectorExchange(queuePrefix);
        String workerExchange = RabbitMQConstants.workerExchange(queuePrefix);
        
        try {
            rabbitAdmin.deleteExchange(connectorExchange);
            log.debug("[RABBITMQ] Deleted exchange: {}", connectorExchange);
            
            rabbitAdmin.deleteExchange(workerExchange);
            log.debug("[RABBITMQ] Deleted exchange: {}", workerExchange);
            
            log.info("[RABBITMQ] Unregistered exchanges");
            
        } catch (Exception e) {
            log.error("[RABBITMQ] Failed to unregister exchanges: {}", e.getMessage());
            throw new DatabaseException("Failed to unregister exchanges", e);
        }
    }

    /**
     * 获取缓存的指标
     * 重写源文件: rabbitmq.js 第 182 行
     * 
     * 源码:
     * const metricsCache = new LRUCache({ ttl: 15000, max: 1 }); // 15 seconds cache
     */
    private RabbitMQMetrics getCachedMetrics() {
        long now = System.currentTimeMillis();
        
        if (now - metricsCacheTime < METRICS_CACHE_TTL_MS && !metricsCache.isEmpty()) {
            return metricsCache.values().iterator().next();
        }
        
        RabbitMQMetrics metrics = getMetrics();
        metricsCache.clear();
        metricsCache.put("cached_metrics", metrics);
        metricsCacheTime = now;
        
        return metrics;
    }
}
