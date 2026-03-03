package io.opencti.database.redis.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opencti.database.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Pub/Sub manager for Redis publish/subscribe operations.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original functions: pubSubSubscription, pubSubAsyncIterator, notify
 */
public class PubSubManager {

    private static final Logger log = LoggerFactory.getLogger(PubSubManager.class);
    private static final String CHANNEL_PREFIX = "channel:";

    private final RedisClient redisClient;
    private final ObjectMapper objectMapper;
    private final String keyPrefix;
    private final Map<String, CopyOnWriteArrayList<Consumer<String>>> channelSubscribers = new ConcurrentHashMap<>();

    public PubSubManager(RedisClient redisClient, String keyPrefix) {
        this.redisClient = redisClient;
        this.keyPrefix = keyPrefix;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Subscribe to a channel.
     * Original: pubSubSubscription function
     */
    public void subscribe(String channel, Consumer<String> messageHandler) {
        String prefixedChannel = buildChannelName(channel);
        
        channelSubscribers.computeIfAbsent(prefixedChannel, k -> new CopyOnWriteArrayList<>())
                .add(messageHandler);
        
        redisClient.subscribe(prefixedChannel, message -> {
            notifySubscribers(prefixedChannel, message);
        });
        
        log.debug("[PUBSUB] Subscribed to channel: {}", channel);
    }

    /**
     * Unsubscribe from a channel.
     */
    public void unsubscribe(String channel, Consumer<String> messageHandler) {
        String prefixedChannel = buildChannelName(channel);
        
        CopyOnWriteArrayList<Consumer<String>> handlers = channelSubscribers.get(prefixedChannel);
        if (handlers != null) {
            handlers.remove(messageHandler);
            if (handlers.isEmpty()) {
                channelSubscribers.remove(prefixedChannel);
                redisClient.unsubscribe(prefixedChannel);
            }
        }
        
        log.debug("[PUBSUB] Unsubscribed from channel: {}", channel);
    }

    /**
     * Publish a message to a channel.
     * Original: notify function
     */
    public void publish(String channel, String message) {
        String prefixedChannel = buildChannelName(channel);
        redisClient.publish(prefixedChannel, message);
        log.debug("[PUBSUB] Published to channel: {}", channel);
    }

    /**
     * Publish an object as JSON.
     */
    public <T> void publish(String channel, T object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            publish(channel, json);
        } catch (JsonProcessingException e) {
            log.error("[PUBSUB] Failed to serialize object for channel: {}", channel, e);
        }
    }

    /**
     * Publish a map as JSON.
     */
    public void publish(String channel, Map<String, Object> data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            publish(channel, json);
        } catch (JsonProcessingException e) {
            log.error("[PUBSUB] Failed to serialize map for channel: {}", channel, e);
        }
    }

    /**
     * Notify all subscribers on a channel.
     */
    private void notifySubscribers(String channel, String message) {
        CopyOnWriteArrayList<Consumer<String>> handlers = channelSubscribers.get(channel);
        if (handlers != null) {
            for (Consumer<String> handler : handlers) {
                try {
                    handler.accept(message);
                } catch (Exception e) {
                    log.error("[PUBSUB] Error in subscriber handler for channel: {}", channel, e);
                }
            }
        }
    }

    /**
     * Get subscriber count for a channel.
     */
    public int getSubscriberCount(String channel) {
        String prefixedChannel = buildChannelName(channel);
        CopyOnWriteArrayList<Consumer<String>> handlers = channelSubscribers.get(prefixedChannel);
        return handlers != null ? handlers.size() : 0;
    }

    /**
     * Build channel name with prefix.
     */
    private String buildChannelName(String channel) {
        if (channel.startsWith(keyPrefix)) {
            return channel;
        }
        return keyPrefix + CHANNEL_PREFIX + channel;
    }

    /**
     * Clear all subscribers.
     */
    public void clearAll() {
        for (String channel : channelSubscribers.keySet()) {
            redisClient.unsubscribe(channel);
        }
        channelSubscribers.clear();
        log.info("[PUBSUB] Cleared all subscribers");
    }
}
