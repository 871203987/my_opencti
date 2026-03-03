package io.opencti.database.redis.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opencti.database.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Stream processor for consuming Redis Stream events.
 * Original file: opencti-platform/opencti-graphql/src/database/redis-stream.ts
 * Original type: StreamProcessor class
 */
public class StreamProcessor {

    private static final Logger log = LoggerFactory.getLogger(StreamProcessor.class);
    private static final int BATCH_SIZE = 100;
    private static final int BLOCK_MS = 5000;

    private final RedisClient redisClient;
    private final String streamKey;
    private final String provider;
    private final Consumer<List<SseEvent>> callback;
    private final ObjectMapper objectMapper;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final ExecutorService executor;
    private String lastId = "0";

    public StreamProcessor(RedisClient redisClient, String streamKey, String provider, 
                          Consumer<List<SseEvent>> callback, ObjectMapper objectMapper) {
        this.redisClient = redisClient;
        this.streamKey = streamKey;
        this.provider = provider;
        this.callback = callback;
        this.objectMapper = objectMapper;
        this.executor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "stream-processor-" + provider);
            t.setDaemon(true);
            return t;
        });
    }

    /**
     * Start processing the stream.
     */
    public void start() {
        if (running.compareAndSet(false, true)) {
            executor.submit(this::processLoop);
            log.info("[STREAM] Started processor for: {}", streamKey);
        }
    }

    /**
     * Stop processing the stream.
     */
    public void stop() {
        if (running.compareAndSet(true, false)) {
            executor.shutdown();
            log.info("[STREAM] Stopped processor for: {}", streamKey);
        }
    }

    /**
     * Main processing loop.
     */
    private void processLoop() {
        while (running.get()) {
            try {
                List<SseEvent> events = readEvents();
                if (!events.isEmpty()) {
                    callback.accept(events);
                }
            } catch (Exception e) {
                log.error("[STREAM] Error processing stream: {}", streamKey, e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /**
     * Read events from the stream.
     */
    @SuppressWarnings("unchecked")
    private List<SseEvent> readEvents() {
        List<SseEvent> events = new ArrayList<>();
        
        try {
            List<Object> messages = redisClient.xread(streamKey, lastId, BATCH_SIZE, BLOCK_MS);
            
            if (messages != null) {
                for (Object message : messages) {
                    if (message instanceof List<?> msgList) {
                        if (msgList.size() >= 2) {
                            String id = (String) msgList.get(0);
                            lastId = id;
                            
                            Object fields = msgList.get(1);
                            if (fields instanceof List<?> fieldList) {
                                Map<String, String> fieldMap = parseFields(fieldList);
                                SseEvent event = SseEvent.fromMap(fieldMap);
                                events.add(event);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.debug("[STREAM] No new events in stream: {}", streamKey);
        }
        
        return events;
    }

    /**
     * Parse field list to map.
     */
    private Map<String, String> parseFields(List<?> fieldList) {
        Map<String, String> map = new java.util.HashMap<>();
        for (int i = 0; i < fieldList.size() - 1; i += 2) {
            String key = String.valueOf(fieldList.get(i));
            String value = String.valueOf(fieldList.get(i + 1));
            map.put(key, value);
        }
        return map;
    }

    /**
     * Check if processor is running.
     */
    public boolean isRunning() {
        return running.get();
    }

    /**
     * Get current position.
     */
    public String getPosition() {
        return lastId;
    }
}
