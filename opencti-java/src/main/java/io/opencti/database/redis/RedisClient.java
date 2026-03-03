package io.opencti.database.redis;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Redis client interface.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Provides all Redis operations needed by OpenCTI.
 */
public interface RedisClient {

    String get(String key);

    void set(String key, String value);

    void setex(String key, int seconds, String value);

    void set(String key, String value, String nx, int ex);

    void del(String key);

    void del(String... keys);

    Long ttl(String key);

    Boolean expire(String key, int seconds);

    Boolean exists(String key);

    void hset(String key, String field, String value);

    void hset(String key, Map<String, String> data);

    String hget(String key, String field);

    Map<String, String> hgetall(String key);

    Long hdel(String key, String... fields);

    Long hincrby(String key, String field, long amount);

    Long zadd(String key, double score, String member);

    Long zadd(String key, double score, String... members);

    Long zrem(String key, String member);

    Long zrem(String key, String... members);

    List<String> zrange(String key, long start, long stop);

    Long zremrangebyscore(String key, String min, String max);

    Long zcount(String key, double min, double max);

    Long zcard(String key);

    void xadd(String streamKey, Map<String, String> fields);

    void xadd(String streamKey, String maxLen, long count, Map<String, String> fields);

    List<Object> xread(String streamKey, String startId, int count, int blockMs);

    List<Object> xrange(String streamKey, String start, String end, int count);

    Object xinfo(String streamKey);

    Long publish(String channel, String message);

    void subscribe(String channel, Consumer<String> messageHandler);

    void unsubscribe(String channel);

    void execute(Consumer<RedisTransaction> transaction);

    void shutdown();

    boolean isAlive();

    String getVersion();

    /**
     * Redis transaction interface.
     */
    interface RedisTransaction {
        void get(String key);

        void set(String key, String value);

        void hset(String key, String field, String value);

        void hset(String key, Map<String, String> data);

        void hincrby(String key, String field, long amount);

        void zadd(String key, double score, String member);

        void zrem(String key, String member);

        void zremrangebyscore(String key, String min, String max);

        void del(String key);

        void expire(String key, int seconds);
    }
}
