package io.opencti.database.rabbitmq;

import io.opencti.common.config.RabbitMQProperties;
import io.opencti.common.exception.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * RabbitMQ 客户端单元测试
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js
 */
@ExtendWith(MockitoExtension.class)
class RabbitMQClientTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RabbitAdmin rabbitAdmin;

    @Mock
    private RabbitMQManagementClient managementClient;

    @Mock
    private RabbitMQProperties properties;

    private RabbitMQClientImpl rabbitMQClient;

    @BeforeEach
    void setUp() {
        lenient().when(properties.queuePrefix()).thenReturn("");
        lenient().when(properties.hostname()).thenReturn("localhost");
        lenient().when(properties.port()).thenReturn(5672);
        lenient().when(properties.vhost()).thenReturn("/");
        lenient().when(properties.useSsl()).thenReturn(false);
        lenient().when(properties.username()).thenReturn("guest");
        lenient().when(properties.password()).thenReturn("guest");
        lenient().when(properties.getQueueType()).thenReturn("classic");
        
        rabbitMQClient = new RabbitMQClientImpl(
                rabbitTemplate,
                rabbitAdmin,
                managementClient,
                properties
        );
    }

    @Test
    @DisplayName("测试 isAlive - 成功场景")
    void testIsAlive_Success() {
        doNothing().when(rabbitAdmin).declareExchange(any());
        
        boolean result = rabbitMQClient.isAlive();
        
        assertTrue(result);
        verify(rabbitAdmin).declareExchange(any());
    }

    @Test
    @DisplayName("测试 isAlive - 失败场景")
    void testIsAlive_Failure() {
        doThrow(new RuntimeException("Connection refused")).when(rabbitAdmin).declareExchange(any());
        
        assertThrows(DatabaseException.class, () -> rabbitMQClient.isAlive());
    }

    @Test
    @DisplayName("测试 getVersion")
    void testGetVersion() {
        when(managementClient.getRabbitMQVersion()).thenReturn("3.12.0");
        
        String version = rabbitMQClient.getVersion();
        
        assertEquals("3.12.0", version);
        verify(managementClient).getRabbitMQVersion();
    }

    @Test
    @DisplayName("测试 registerConnectorQueues")
    void testRegisterConnectorQueues() {
        doNothing().when(rabbitAdmin).declareExchange(any());
        when(rabbitAdmin.declareQueue(any())).thenReturn("queue-name");
        doNothing().when(rabbitAdmin).declareBinding(any());
        
        ConnectorConfig config = rabbitMQClient.registerConnectorQueues(
                "test-connector",
                "Test Connector",
                "external",
                List.of("stix", "csv")
        );
        
        assertNotNull(config);
        assertEquals("push_test-connector", config.push());
        assertEquals("listen_test-connector", config.listen());
        
        verify(rabbitAdmin, times(2)).declareExchange(any());
        verify(rabbitAdmin, times(2)).declareQueue(any());
        verify(rabbitAdmin, times(2)).declareBinding(any());
    }

    @Test
    @DisplayName("测试 unregisterConnector")
    void testUnregisterConnector() {
        when(rabbitAdmin.deleteQueue(anyString())).thenReturn(true);
        
        rabbitMQClient.unregisterConnector("test-connector");
        
        verify(rabbitAdmin, times(2)).deleteQueue(anyString());
    }

    @Test
    @DisplayName("测试 purgeConnectorQueues")
    void testPurgeConnectorQueues() {
        doNothing().when(managementClient).purgeConnectorQueues(anyString());
        
        rabbitMQClient.purgeConnectorQueues("test-connector");
        
        verify(managementClient).purgeConnectorQueues("test-connector");
    }

    @Test
    @DisplayName("测试 getInternalQueues")
    void testGetInternalQueues() {
        List<QueueConfig> queues = rabbitMQClient.getInternalQueues();
        
        assertNotNull(queues);
        assertTrue(queues.size() >= 5);
        
        boolean hasTooLargeBundle = queues.stream()
                .anyMatch(q -> "too-large-bundle".equals(q.id()));
        assertTrue(hasTooLargeBundle);
        
        boolean hasBackgroundTask = queues.stream()
                .anyMatch(q -> q.id().startsWith("background-task-"));
        assertTrue(hasBackgroundTask);
    }

    @Test
    @DisplayName("测试 initializeInternalQueues")
    void testInitializeInternalQueues() {
        doNothing().when(rabbitAdmin).declareExchange(any());
        when(rabbitAdmin.declareQueue(any())).thenReturn("queue-name");
        doNothing().when(rabbitAdmin).declareBinding(any());
        
        rabbitMQClient.initializeInternalQueues();
        
        verify(rabbitAdmin, atLeast(2)).declareExchange(any());
        verify(rabbitAdmin, atLeast(2)).declareQueue(any());
    }

    @Test
    @DisplayName("测试 send")
    void testSend() {
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());
        
        rabbitMQClient.send("test-exchange", "test-routing", "test-message");
        
        verify(rabbitTemplate).convertAndSend("test-exchange", "test-routing", "test-message");
    }

    @Test
    @DisplayName("测试 pushToConnector")
    void testPushToConnector() {
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(Object.class));
        
        Map<String, Object> message = Map.of("type", "test", "data", "value");
        rabbitMQClient.pushToConnector("test-connector", message);
        
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), any(Object.class));
    }

    @Test
    @DisplayName("测试 pushToWorkerForConnector")
    void testPushToWorkerForConnector() {
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(Object.class));
        
        Map<String, Object> message = Map.of("work_id", "work-123", "status", "running");
        rabbitMQClient.pushToWorkerForConnector("test-connector", message);
        
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), any(Object.class));
    }

    @Test
    @DisplayName("测试 getMetrics")
    void testGetMetrics() {
        RabbitMQMetrics expectedMetrics = new RabbitMQMetrics(
                Map.of("rabbitmq_version", "3.12.0"),
                2,
                List.of(Map.of("name", "push_test", "messages", 10))
        );
        when(managementClient.getMetrics()).thenReturn(expectedMetrics);
        
        RabbitMQMetrics metrics = rabbitMQClient.getMetrics();
        
        assertNotNull(metrics);
        assertEquals("3.12.0", metrics.getRabbitMQVersion());
        assertEquals(2, metrics.consumers());
        assertEquals(1, metrics.queues().size());
    }

    @Test
    @DisplayName("测试 getConnectorQueueSize")
    void testGetConnectorQueueSize() {
        RabbitMQMetrics metrics = new RabbitMQMetrics(
                Map.of(),
                1,
                List.of(
                        Map.of("name", "push_test-connector", "messages", 5),
                        Map.of("name", "listen_test-connector", "messages", 3)
                )
        );
        when(managementClient.getMetrics()).thenReturn(metrics);
        
        int size = rabbitMQClient.getConnectorQueueSize("test-connector");
        
        assertEquals(8, size);
    }

    @Test
    @DisplayName("测试 getBestBackgroundConnectorId")
    void testGetBestBackgroundConnectorId() {
        RabbitMQMetrics metrics = new RabbitMQMetrics(
                Map.of(),
                1,
                List.of(
                        Map.of("name", "push_background-task-0", "messages", 10),
                        Map.of("name", "push_background-task-1", "messages", 2),
                        Map.of("name", "push_background-task-2", "messages", 5)
                )
        );
        when(managementClient.getMetrics()).thenReturn(metrics);
        
        String bestId = rabbitMQClient.getBestBackgroundConnectorId();
        
        assertEquals("background-task-1", bestId);
    }

    @Test
    @DisplayName("测试 getConnectorConfig")
    void testGetConnectorConfig() {
        ConnectorConfig config = rabbitMQClient.getConnectorConfig("test-connector");
        
        assertNotNull(config);
        assertNotNull(config.connection());
        assertEquals("localhost", config.connection().host());
        assertEquals("push_test-connector", config.push());
        assertEquals("listen_test-connector", config.listen());
    }

    @Test
    @DisplayName("测试 getConnectorQueueDetails")
    void testGetConnectorQueueDetails() {
        QueueDetails expectedDetails = new QueueDetails(10, 1024L);
        when(managementClient.getConnectorQueueDetails("test-connector")).thenReturn(expectedDetails);
        
        QueueDetails details = rabbitMQClient.getConnectorQueueDetails("test-connector");
        
        assertNotNull(details);
        assertEquals(10, details.messagesNumber());
        assertEquals(1024L, details.messagesSize());
    }

    @Test
    @DisplayName("测试 unregisterExchanges")
    void testUnregisterExchanges() {
        when(rabbitAdmin.deleteExchange(anyString())).thenReturn(true);
        
        rabbitMQClient.unregisterExchanges();
        
        verify(rabbitAdmin, times(2)).deleteExchange(anyString());
    }

    @Test
    @DisplayName("测试 RabbitMQConstants")
    void testRabbitMQConstants() {
        String prefix = "test_";
        
        assertEquals("test_amqp.connector.exchange", RabbitMQConstants.connectorExchange(prefix));
        assertEquals("test_amqp.worker.exchange", RabbitMQConstants.workerExchange(prefix));
        assertEquals("test_push_", RabbitMQConstants.pushQueuePrefix(prefix));
        assertEquals("test_listen_", RabbitMQConstants.listenQueuePrefix(prefix));
        assertEquals("test_listen_routing_conn1", RabbitMQConstants.listenRouting(prefix, "conn1"));
        assertEquals("test_push_routing_conn1", RabbitMQConstants.pushRouting(prefix, "conn1"));
        assertEquals("test_push_conn1", RabbitMQConstants.pushQueueName(prefix, "conn1"));
        assertEquals("test_listen_conn1", RabbitMQConstants.listenQueueName(prefix, "conn1"));
        assertEquals("background-task-0", RabbitMQConstants.backgroundTaskQueueId(0));
    }

    @Test
    @DisplayName("测试 QueueConfig 工厂方法")
    void testQueueConfigFactoryMethods() {
        QueueConfig backgroundTask = QueueConfig.forBackgroundTask(0);
        assertEquals("background-task-0", backgroundTask.id());
        assertEquals("[TASK] Internal task processing #0", backgroundTask.name());
        assertEquals("internal", backgroundTask.type());
        
        QueueConfig tooLargeBundle = QueueConfig.forTooLargeBundle();
        assertEquals("too-large-bundle", tooLargeBundle.id());
        assertEquals("Bundle too large for ingestion", tooLargeBundle.name());
    }

    @Test
    @DisplayName("测试 RabbitMQConnectionConfig")
    void testRabbitMQConnectionConfig() {
        RabbitMQConnectionConfig config = new RabbitMQConnectionConfig(
                "localhost", "/", false, 5672, "guest", "guest"
        );
        
        assertEquals("", config.getVhostPath());
        assertEquals("amqp://localhost:5672", config.getAmqpUri());
        
        RabbitMQConnectionConfig configWithVhost = new RabbitMQConnectionConfig(
                "localhost", "myvhost", true, 5671, "admin", "password"
        );
        
        assertEquals("/myvhost", configWithVhost.getVhostPath());
        assertEquals("amqps://localhost:5671/myvhost", configWithVhost.getAmqpUri());
    }

    @Test
    @DisplayName("测试 RabbitMQMetrics")
    void testRabbitMQMetrics() {
        RabbitMQMetrics metrics = new RabbitMQMetrics(
                Map.of("rabbitmq_version", "3.12.0"),
                2,
                List.of()
        );
        
        assertEquals("3.12.0", metrics.getRabbitMQVersion());
        
        RabbitMQMetrics emptyMetrics = new RabbitMQMetrics(null, 0, List.of());
        assertEquals("Disconnected", emptyMetrics.getRabbitMQVersion());
    }

    @Test
    @DisplayName("测试 QueueDetails")
    void testQueueDetails() {
        QueueDetails details = new QueueDetails(10, 1024L);
        assertEquals(10, details.messagesNumber());
        assertEquals(1024L, details.messagesSize());
        
        QueueDetails empty = QueueDetails.empty();
        assertEquals(0, empty.messagesNumber());
        assertEquals(0L, empty.messagesSize());
    }
}
