package io.opencti.database.redis;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Redis client implementation using Lettuce.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original functions: All Redis operations
 */
public class RedisClientImpl implements RedisClient {

    private static final Logger log = LoggerFactory.getLogger(RedisClientImpl.class);

    private final RedisConfig config;
    private final String provider;
    private final RedisMode mode;
    private final String keyPrefix;

    private Object rawClient;
    private Object rawConnection;
    private StatefulRedisPubSubConnection<String, String> pubSubConnection;
    private final Map<String, List<Consumer<String>>> subscribers = new ConcurrentHashMap<>();

    public RedisClientImpl(RedisConfig config, String provider, boolean autoReconnect) {
        this.config = config;
        this.provider = provider;
        this.mode = config.getProperties().getRedisMode();
        this.keyPrefix = config.getProperties().keyPrefix();
        initialize(autoReconnect);
    }

    @SuppressWarnings("unchecked")
    private void initialize(boolean autoReconnect) {
        try {
            if (mode == RedisMode.CLUSTER) {
                List<RedisURI> uris = config.createClusterUris(provider);
                RedisClusterClient clusterClient = RedisClusterClient.create(uris);
                this.rawClient = clusterClient;
                this.rawConnection = clusterClient.connect();
                log.info("[REDIS] Redis cluster client initialized for provider: {}", provider);
            } else {
                RedisURI uri = config.createRedisUri(provider);
                io.lettuce.core.RedisClient singleClient = io.lettuce.core.RedisClient.create(uri);
                singleClient.setOptions(config.createClientOptions(autoReconnect));
                this.rawClient = singleClient;
                this.rawConnection = singleClient.connect();
                log.info("[REDIS] Redis single client initialized for provider: {}", provider);
            }
        } catch (Exception e) {
            log.error("[REDIS] Failed to initialize Redis client for provider: {}", provider, e);
            throw new RuntimeException("Failed to initialize Redis client", e);
        }
    }

    private String prefixKey(String key) {
        if (key == null) return null;
        if (key.startsWith(keyPrefix)) return key;
        return keyPrefix + key;
    }

    @SuppressWarnings("unchecked")
    private RedisCommands<String, String> getSyncCommands() {
        if (mode == RedisMode.CLUSTER) {
            StatefulRedisClusterConnection<String, String> conn = (StatefulRedisClusterConnection<String, String>) rawConnection;
            return (RedisCommands<String, String>) (Object) conn.sync();
        } else {
            StatefulRedisConnection<String, String> conn = (StatefulRedisConnection<String, String>) rawConnection;
            return conn.sync();
        }
    }

    @Override
    public String get(String key) {
        return getSyncCommands().get(prefixKey(key));
    }

    @Override
    public void set(String key, String value) {
        getSyncCommands().set(prefixKey(key), value);
    }

    @Override
    public void setex(String key, int seconds, String value) {
        getSyncCommands().setex(prefixKey(key), seconds, value);
    }

    @Override
    public void set(String key, String value, String nx, int ex) {
        SetArgs args = new SetArgs();
        if ("NX".equalsIgnoreCase(nx)) {
            args.nx();
        } else if ("XX".equalsIgnoreCase(nx)) {
            args.xx();
        }
        args.ex(Duration.ofSeconds(ex));
        getSyncCommands().set(prefixKey(key), value, args);
    }

    @Override
    public void del(String key) {
        getSyncCommands().del(prefixKey(key));
    }

    @Override
    public void del(String... keys) {
        String[] prefixedKeys = Arrays.stream(keys).map(this::prefixKey).toArray(String[]::new);
        getSyncCommands().del(prefixedKeys);
    }

    @Override
    public Long ttl(String key) {
        return getSyncCommands().ttl(prefixKey(key));
    }

    @Override
    public Boolean expire(String key, int seconds) {
        return getSyncCommands().expire(prefixKey(key), Duration.ofSeconds(seconds));
    }

    @Override
    public Boolean exists(String key) {
        return getSyncCommands().exists(prefixKey(key)) > 0;
    }

    @Override
    public void hset(String key, String field, String value) {
        getSyncCommands().hset(prefixKey(key), field, value);
    }

    @Override
    public void hset(String key, Map<String, String> data) {
        Map<String, String> prefixedKey = new HashMap<>();
        prefixedKey.putAll(data);
        getSyncCommands().hset(prefixKey(key), prefixedKey);
    }

    @Override
    public String hget(String key, String field) {
        return getSyncCommands().hget(prefixKey(key), field);
    }

    @Override
    public Map<String, String> hgetall(String key) {
        return getSyncCommands().hgetall(prefixKey(key));
    }

    @Override
    public Long hdel(String key, String... fields) {
        return getSyncCommands().hdel(prefixKey(key), fields);
    }

    @Override
    public Long hincrby(String key, String field, long amount) {
        return getSyncCommands().hincrby(prefixKey(key), field, amount);
    }

    @Override
    public Long zadd(String key, double score, String member) {
        return getSyncCommands().zadd(prefixKey(key), score, member);
    }

    @Override
    public Long zadd(String key, double score, String... members) {
        ScoredValue<String>[] values = new ScoredValue[members.length];
        for (int i = 0; i < members.length; i++) {
            values[i] = ScoredValue.just(score, members[i]);
        }
        return getSyncCommands().zadd(prefixKey(key), values);
    }

    @Override
    public Long zrem(String key, String member) {
        return getSyncCommands().zrem(prefixKey(key), member);
    }

    @Override
    public Long zrem(String key, String... members) {
        return getSyncCommands().zrem(prefixKey(key), members);
    }

    @Override
    public List<String> zrange(String key, long start, long stop) {
        return getSyncCommands().zrange(prefixKey(key), start, stop);
    }

    @Override
    public Long zremrangebyscore(String key, String min, String max) {
        return getSyncCommands().zremrangebyscore(prefixKey(key), min, max);
    }

    @Override
    public Long zcount(String key, double min, double max) {
        return getSyncCommands().zcount(prefixKey(key), min, max);
    }

    @Override
    public Long zcard(String key) {
        return getSyncCommands().zcard(prefixKey(key));
    }

    @Override
    public void xadd(String streamKey, Map<String, String> fields) {
        getSyncCommands().xadd(prefixKey(streamKey), fields);
    }

    @Override
    public void xadd(String streamKey, String maxLen, long count, Map<String, String> fields) {
        XAddArgs args = new XAddArgs();
        if (maxLen != null && maxLen.startsWith("~")) {
            args.maxlen(count).approximateTrimming();
        } else {
            args.maxlen(count);
        }
        getSyncCommands().xadd(prefixKey(streamKey), args, fields);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> xread(String streamKey, String startId, int count, int blockMs) {
        XReadArgs.StreamOffset<String> offset = XReadArgs.StreamOffset.from(prefixKey(streamKey), startId);
        XReadArgs args = new XReadArgs().count(count).block(Duration.ofMillis(blockMs));
        List<StreamMessage<String, String>> messages = getSyncCommands().xread(args, offset);
        if (messages == null) return Collections.emptyList();
        return new ArrayList<>(messages);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> xrange(String streamKey, String start, String end, int count) {
        Range<String> range = Range.create(start, end);
        Limit limit = Limit.create(0, count);
        List<StreamMessage<String, String>> messages = getSyncCommands().xrange(prefixKey(streamKey), range, limit);
        if (messages == null) return Collections.emptyList();
        return new ArrayList<>(messages);
    }

    @Override
    public Object xinfo(String streamKey) {
        return getSyncCommands().xinfoStream(prefixKey(streamKey));
    }

    @Override
    public Long publish(String channel, String message) {
        return getSyncCommands().publish(prefixKey(channel), message);
    }

    @Override
    public void subscribe(String channel, Consumer<String> messageHandler) {
        String prefixedChannel = prefixKey(channel);
        subscribers.computeIfAbsent(prefixedChannel, k -> new CopyOnWriteArrayList<>()).add(messageHandler);
        
        if (pubSubConnection == null) {
            initPubSub();
        }
        
        if (mode == RedisMode.CLUSTER) {
            ((StatefulRedisClusterPubSubConnection<String, String>) pubSubConnection).sync().subscribe(prefixedChannel);
        } else {
            pubSubConnection.sync().subscribe(prefixedChannel);
        }
    }

    @SuppressWarnings("unchecked")
    private void initPubSub() {
        if (mode == RedisMode.CLUSTER) {
            RedisClusterClient clusterClient = (RedisClusterClient) rawClient;
            pubSubConnection = clusterClient.connectPubSub();
            ((StatefulRedisClusterPubSubConnection<String, String>) pubSubConnection).addListener(new RedisPubSubListener<String, String>() {
                @Override
                public void message(String channel, String message) {
                    notifySubscribers(channel, message);
                }

                @Override
                public void message(String pattern, String channel, String message) {
                    notifySubscribers(channel, message);
                }

                @Override
                public void subscribed(String channel, long count) {
                    log.debug("[REDIS] Subscribed to channel: {}", channel);
                }

                @Override
                public void unsubscribed(String channel, long count) {
                    log.debug("[REDIS] Unsubscribed from channel: {}", channel);
                }

                @Override
                public void psubscribed(String pattern, long count) {
                }

                @Override
                public void punsubscribed(String pattern, long count) {
                }
            });
        } else {
            io.lettuce.core.RedisClient singleClient = (io.lettuce.core.RedisClient) rawClient;
            pubSubConnection = singleClient.connectPubSub();
            pubSubConnection.addListener(new RedisPubSubListener<String, String>() {
                @Override
                public void message(String channel, String message) {
                    notifySubscribers(channel, message);
                }

                @Override
                public void message(String pattern, String channel, String message) {
                    notifySubscribers(channel, message);
                }

                @Override
                public void subscribed(String channel, long count) {
                    log.debug("[REDIS] Subscribed to channel: {}", channel);
                }

                @Override
                public void unsubscribed(String channel, long count) {
                    log.debug("[REDIS] Unsubscribed from channel: {}", channel);
                }

                @Override
                public void psubscribed(String pattern, long count) {
                }

                @Override
                public void punsubscribed(String pattern, long count) {
                }
            });
        }
    }

    private void notifySubscribers(String channel, String message) {
        List<Consumer<String>> handlers = subscribers.get(channel);
        if (handlers != null) {
            for (Consumer<String> handler : handlers) {
                try {
                    handler.accept(message);
                } catch (Exception e) {
                    log.error("[REDIS] Error in subscriber handler for channel: {}", channel, e);
                }
            }
        }
    }

    @Override
    public void unsubscribe(String channel) {
        String prefixedChannel = prefixKey(channel);
        subscribers.remove(prefixedChannel);
        
        if (pubSubConnection != null) {
            if (mode == RedisMode.CLUSTER) {
                ((StatefulRedisClusterPubSubConnection<String, String>) pubSubConnection).sync().unsubscribe(prefixedChannel);
            } else {
                pubSubConnection.sync().unsubscribe(prefixedChannel);
            }
        }
    }

    @Override
    public void execute(Consumer<RedisTransaction> transaction) {
        RedisCommands<String, String> commands = getSyncCommands();
        commands.multi();
        try {
            transaction.accept(new RedisTransactionImpl(commands));
            commands.exec();
        } catch (Exception e) {
            commands.discard();
            throw new RuntimeException("Redis transaction failed", e);
        }
    }

    @Override
    public void shutdown() {
        try {
            if (pubSubConnection != null) {
                pubSubConnection.close();
            }
            if (rawConnection != null) {
                if (mode == RedisMode.CLUSTER) {
                    ((StatefulRedisClusterConnection<String, String>) rawConnection).close();
                } else {
                    ((StatefulRedisConnection<String, String>) rawConnection).close();
                }
            }
            if (rawClient != null) {
                if (mode == RedisMode.CLUSTER) {
                    ((RedisClusterClient) rawClient).shutdown();
                } else {
                    ((io.lettuce.core.RedisClient) rawClient).shutdown();
                }
            }
            log.info("[REDIS] Redis client shutdown for provider: {}", provider);
        } catch (Exception e) {
            log.error("[REDIS] Error during shutdown for provider: {}", provider, e);
        }
    }

    @Override
    public boolean isAlive() {
        try {
            getSyncCommands().get("test-key");
            return true;
        } catch (Exception e) {
            log.error("[REDIS] Redis health check failed", e);
            return false;
        }
    }

    @Override
    public String getVersion() {
        try {
            String info = getSyncCommands().info("server");
            String[] lines = info.split("\r\n");
            for (String line : lines) {
                if (line.startsWith("redis_version:")) {
                    return line.split(":")[1];
                }
            }
            return "unknown";
        } catch (Exception e) {
            log.error("[REDIS] Failed to get Redis version", e);
            return "unknown";
        }
    }

    private static class RedisTransactionImpl implements RedisTransaction {
        private final RedisCommands<String, String> commands;

        RedisTransactionImpl(RedisCommands<String, String> commands) {
            this.commands = commands;
        }

        @Override
        public void get(String key) {
            commands.get(key);
        }

        @Override
        public void set(String key, String value) {
            commands.set(key, value);
        }

        @Override
        public void hset(String key, String field, String value) {
            commands.hset(key, field, value);
        }

        @Override
        public void hset(String key, Map<String, String> data) {
            commands.hset(key, data);
        }

        @Override
        public void hincrby(String key, String field, long amount) {
            commands.hincrby(key, field, amount);
        }

        @Override
        public void zadd(String key, double score, String member) {
            commands.zadd(key, score, member);
        }

        @Override
        public void zrem(String key, String member) {
            commands.zrem(key, member);
        }

        @Override
        public void zremrangebyscore(String key, String min, String max) {
            commands.zremrangebyscore(key, min, max);
        }

        @Override
        public void del(String key) {
            commands.del(key);
        }

        @Override
        public void expire(String key, int seconds) {
            commands.expire(key, Duration.ofSeconds(seconds));
        }
    }
}
