package io.opencti.database.redis.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.opencti.database.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

/**
 * Redis Stream client for event streaming.
 * Original file: opencti-platform/opencti-graphql/src/database/redis-stream.ts
 * Original functions: pushToStream, fetchStreamInfo, createStreamProcessor
 */
public class RedisStreamClient {

    private static final Logger log = LoggerFactory.getLogger(RedisStreamClient.class);
    private static final String STREAM_PREFIX = "stream:";
    private static final String NOTIFICATION_STREAM = "notifications";
    private static final String ACTIVITY_STREAM = "activity";

    private final RedisClient redisClient;
    private final ObjectMapper objectMapper;
    private final String keyPrefix;
    private final int notificationTrimming;
    private final int activityTrimming;

    public RedisStreamClient(RedisClient redisClient, String keyPrefix, int notificationTrimming, int activityTrimming) {
        this.redisClient = redisClient;
        this.keyPrefix = keyPrefix;
        this.notificationTrimming = notificationTrimming;
        this.activityTrimming = activityTrimming;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Push an event to a stream.
     * Original: rawPushToStream function
     */
    public void pushToStream(String streamName, SseEvent event) {
        try {
            String streamKey = buildStreamKey(streamName);
            Map<String, String> fields = event.toMap();
            
            int trimming = getTrimmingForStream(streamName);
            if (trimming > 0) {
                redisClient.xadd(streamKey, "~", trimming, fields);
            } else {
                redisClient.xadd(streamKey, fields);
            }
            
            log.debug("[STREAM] Pushed event to stream: {}", streamName);
        } catch (Exception e) {
            log.error("[STREAM] Failed to push event to stream: {}", streamName, e);
        }
    }

    /**
     * Fetch stream information.
     * Original: rawFetchStreamInfo function
     */
    public StreamInfo fetchStreamInfo(String streamName) {
        try {
            String streamKey = buildStreamKey(streamName);
            Object info = redisClient.xinfo(streamKey);
            return parseStreamInfo(info);
        } catch (Exception e) {
            log.debug("[STREAM] Stream info not available for: {}", streamName);
            return new StreamInfo(0, 0, 0);
        }
    }

    /**
     * Create a stream processor.
     * Original: rawCreateStreamProcessor function
     */
    public StreamProcessor createStreamProcessor(String provider, String streamName, Consumer<List<SseEvent>> callback) {
        String streamKey = buildStreamKey(streamName);
        return new StreamProcessor(redisClient, streamKey, provider, callback, objectMapper);
    }

    /**
     * Store a notification event.
     * Original: rawStoreNotificationEvent function
     */
    public void storeNotificationEvent(NotificationEvent event) {
        try {
            SseEvent sseEvent = SseEvent.builder()
                    .id(UUID.randomUUID().toString())
                    .type(event.type())
                    .data(objectMapper.writeValueAsString(event.data()))
                    .eventId(event.eventId())
                    .createdAt(Instant.now())
                    .build();
            
            pushToStream(NOTIFICATION_STREAM, sseEvent);
        } catch (JsonProcessingException e) {
            log.error("[STREAM] Failed to store notification event", e);
        }
    }

    /**
     * Store an activity event.
     * Original: rawStoreActivityEvent function
     */
    public void storeActivityEvent(ActivityEvent event) {
        try {
            SseEvent sseEvent = SseEvent.builder()
                    .id(UUID.randomUUID().toString())
                    .type(event.type())
                    .data(objectMapper.writeValueAsString(event.data()))
                    .eventId(event.eventId())
                    .createdAt(Instant.now())
                    .build();
            
            pushToStream(ACTIVITY_STREAM, sseEvent);
        } catch (JsonProcessingException e) {
            log.error("[STREAM] Failed to store activity event", e);
        }
    }

    /**
     * Get trimming value for a stream.
     */
    private int getTrimmingForStream(String streamName) {
        if (NOTIFICATION_STREAM.equals(streamName)) {
            return notificationTrimming;
        } else if (ACTIVITY_STREAM.equals(streamName)) {
            return activityTrimming;
        }
        return 0;
    }

    /**
     * Build stream key.
     */
    private String buildStreamKey(String streamName) {
        return keyPrefix + STREAM_PREFIX + streamName;
    }

    /**
     * Parse stream info from Redis response.
     */
    private StreamInfo parseStreamInfo(Object info) {
        return new StreamInfo(0, 0, 0);
    }

    /**
     * Stream info record.
     */
    public record StreamInfo(long length, long radixTreeKeys, long radixTreeNodes) {
    }

    /**
     * Notification event record.
     */
    public record NotificationEvent(String type, Object data, String eventId) {
    }

    /**
     * Activity event record.
     */
    public record ActivityEvent(String type, Object data, String eventId) {
    }
}
