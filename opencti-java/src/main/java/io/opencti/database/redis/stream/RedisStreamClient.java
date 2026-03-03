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
 * 
 * 键名格式（与 TypeScript 源码一致）:
 * - Live Stream: {prefix}stream
 * - Notification Stream: {prefix}notifications
 * - Activity Stream: {prefix}activity
 */
public class RedisStreamClient {

    private static final Logger log = LoggerFactory.getLogger(RedisStreamClient.class);
    
    // Stream names (from stream-utils.ts)
    public static final String LIVE_STREAM_NAME = "stream";
    public static final String NOTIFICATION_STREAM_NAME = "notifications";
    public static final String ACTIVITY_STREAM_NAME = "activity";

    private final RedisClient redisClient;
    private final ObjectMapper objectMapper;
    private final String keyPrefix;
    private final long trimming;
    private final long notificationTrimming;
    private final long activityTrimming;

    public RedisStreamClient(RedisClient redisClient, String keyPrefix, long trimming, long notificationTrimming) {
        this.redisClient = redisClient;
        this.keyPrefix = keyPrefix;
        this.trimming = trimming;
        this.notificationTrimming = notificationTrimming;
        this.activityTrimming = notificationTrimming; // activityTrimming 默认使用 notificationTrimming
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Push an event to a stream.
     * Original: rawPushToStream function
     * 原始逻辑: 键名是 {prefix}{streamName}
     */
    public void pushToStream(String streamName, SseEvent event) {
        try {
            String streamKey = buildStreamKey(streamName);
            Map<String, String> fields = event.toMap();
            
            long streamTrimming = getTrimmingForStream(streamName);
            if (streamTrimming > 0) {
                redisClient.xadd(streamKey, "~", streamTrimming, fields);
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
            
            pushToStream(NOTIFICATION_STREAM_NAME, sseEvent);
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
            
            pushToStream(ACTIVITY_STREAM_NAME, sseEvent);
        } catch (JsonProcessingException e) {
            log.error("[STREAM] Failed to store activity event", e);
        }
    }

    /**
     * Get trimming value for a stream.
     * 原始逻辑: 使用配置的 trimming 值
     */
    private long getTrimmingForStream(String streamName) {
        if (NOTIFICATION_STREAM_NAME.equals(streamName)) {
            return notificationTrimming;
        } else if (ACTIVITY_STREAM_NAME.equals(streamName)) {
            return activityTrimming;
        }
        return trimming;
    }

    /**
     * Build stream key.
     * 原始逻辑: 直接使用 {prefix}{streamName}，不加额外前缀
     */
    private String buildStreamKey(String streamName) {
        return keyPrefix + streamName;
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
