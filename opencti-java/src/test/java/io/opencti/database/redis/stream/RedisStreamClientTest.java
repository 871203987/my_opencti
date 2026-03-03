package io.opencti.database.redis.stream;

import io.opencti.database.redis.RedisClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test for RedisStreamClient.
 * Original file: opencti-platform/opencti-graphql/src/database/redis-stream.ts
 * Original methods: pushToStream, fetchStreamInfo, storeNotificationEvent, storeActivityEvent
 */
@ExtendWith(MockitoExtension.class)
class RedisStreamClientTest {

    @Mock
    private RedisClient redisClient;

    private RedisStreamClient streamClient;

    @BeforeEach
    void setUp() {
        streamClient = new RedisStreamClient(redisClient, "opencti:", 50000, 50000);
    }

    @Test
    @DisplayName("Should push event to stream")
    void testPushToStream() {
        SseEvent event = SseEvent.builder()
            .id("event-123")
            .type("test-event")
            .data("{\"key\":\"value\"}")
            .eventId("evt-123")
            .createdAt(Instant.now())
            .build();

        streamClient.pushToStream("test-stream", event);

        verify(redisClient).xadd(contains("stream:test-stream"), anyMap());
    }

    @Test
    @DisplayName("Should push event with trimming")
    void testPushToStreamWithTrimming() {
        SseEvent event = SseEvent.builder()
            .id("event-123")
            .type("test-event")
            .data("{\"key\":\"value\"}")
            .eventId("evt-123")
            .createdAt(Instant.now())
            .build();

        streamClient.pushToStream("notifications", event);

        verify(redisClient).xadd(contains("stream:notifications"), eq("~"), eq(50000L), anyMap());
    }

    @Test
    @DisplayName("Should fetch stream info")
    void testFetchStreamInfo() {
        when(redisClient.xinfo(contains("stream:"))).thenReturn(Map.of("length", 100L));

        RedisStreamClient.StreamInfo info = streamClient.fetchStreamInfo("test-stream");

        assertNotNull(info);
        verify(redisClient).xinfo(contains("stream:test-stream"));
    }

    @Test
    @DisplayName("Should store notification event")
    void testStoreNotificationEvent() {
        RedisStreamClient.NotificationEvent event = new RedisStreamClient.NotificationEvent(
            "notification",
            Map.of("message", "Test notification"),
            "evt-123"
        );

        streamClient.storeNotificationEvent(event);

        verify(redisClient).xadd(contains("stream:notifications"), eq("~"), eq(50000L), anyMap());
    }

    @Test
    @DisplayName("Should store activity event")
    void testStoreActivityEvent() {
        RedisStreamClient.ActivityEvent event = new RedisStreamClient.ActivityEvent(
            "activity",
            Map.of("action", "create", "entity_id", "entity-123"),
            "evt-123"
        );

        streamClient.storeActivityEvent(event);

        verify(redisClient).xadd(contains("stream:activity"), eq("~"), eq(50000L), anyMap());
    }

    @Test
    @DisplayName("Should create stream processor")
    void testCreateStreamProcessor() {
        java.util.function.Consumer<List<SseEvent>> callback = events -> {};
        
        StreamProcessor processor = streamClient.createStreamProcessor("provider", "test-stream", callback);

        assertNotNull(processor);
    }

    @Test
    @DisplayName("Should create SseEvent with all fields")
    void testSseEventCreation() {
        Instant now = Instant.now();
        
        SseEvent event = SseEvent.builder()
            .id("event-123")
            .type("test-type")
            .data("{\"key\":\"value\"}")
            .eventId("evt-123")
            .createdAt(now)
            .build();

        assertEquals("event-123", event.id());
        assertEquals("test-type", event.type());
        assertEquals("{\"key\":\"value\"}", event.data());
        assertEquals("evt-123", event.eventId());
        assertEquals(now, event.createdAt());
    }

    @Test
    @DisplayName("Should convert SseEvent to map")
    void testSseEventToMap() {
        Instant now = Instant.now();
        
        SseEvent event = SseEvent.builder()
            .id("event-123")
            .type("test-type")
            .data("{\"key\":\"value\"}")
            .eventId("evt-123")
            .createdAt(now)
            .build();

        Map<String, String> map = event.toMap();

        assertNotNull(map);
        assertEquals("event-123", map.get("id"));
        assertEquals("test-type", map.get("type"));
        assertEquals("{\"key\":\"value\"}", map.get("data"));
    }

    @Test
    @DisplayName("Should create SseEvent from map")
    void testSseEventFromMap() {
        Map<String, String> map = Map.of(
            "id", "event-123",
            "type", "test-type",
            "data", "{\"key\":\"value\"}",
            "event_id", "evt-123",
            "created_at", "2024-01-01T00:00:00Z"
        );

        SseEvent event = SseEvent.fromMap(map);

        assertNotNull(event);
        assertEquals("event-123", event.id());
        assertEquals("test-type", event.type());
        assertEquals("{\"key\":\"value\"}", event.data());
    }
}
