package io.opencti.database.redis.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.opencti.database.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache manager for entity caching.
 * Original file: opencti-platform/opencti-graphql/src/database/cache.ts
 * Original functions: writeCacheForEntity, resetCacheForEntity, getEntitiesMapFromCache, getEntitiesListFromCache
 */
public class CacheManager {

    private static final Logger log = LoggerFactory.getLogger(CacheManager.class);
    private static final String CACHE_PREFIX = "cache:";
    private static final int CACHE_TTL = 3600;

    private final RedisClient redisClient;
    private final ObjectMapper objectMapper;
    private final String keyPrefix;
    private final Map<String, Object> localCache = new ConcurrentHashMap<>();

    public CacheManager(RedisClient redisClient, String keyPrefix) {
        this.redisClient = redisClient;
        this.keyPrefix = keyPrefix;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Write entity to cache.
     * Original: writeCacheForEntity function
     */
    public <T> void writeCache(String entityType, String key, T data) {
        try {
            String cacheKey = buildCacheKey(entityType, key);
            String json = objectMapper.writeValueAsString(data);
            
            redisClient.setex(cacheKey, CACHE_TTL, json);
            localCache.put(cacheKey, data);
            
            addToEntityIndex(entityType, key);
            
            log.debug("[CACHE] Wrote {}:{} to cache", entityType, key);
        } catch (JsonProcessingException e) {
            log.error("[CACHE] Failed to write cache for {}:{}", entityType, key, e);
        }
    }

    /**
     * Reset cache for an entity type.
     * Original: resetCacheForEntity function
     */
    public void resetCache(String entityType) {
        String indexKey = buildIndexKey(entityType);
        List<String> keys = redisClient.zrange(indexKey, 0, -1);
        
        for (String key : keys) {
            String cacheKey = buildCacheKey(entityType, key);
            redisClient.del(cacheKey);
            localCache.remove(cacheKey);
        }
        
        redisClient.del(indexKey);
        log.info("[CACHE] Reset cache for entity type: {}", entityType);
    }

    /**
     * Get entity from cache.
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getEntity(String entityType, String key, Class<T> clazz) {
        String cacheKey = buildCacheKey(entityType, key);
        
        Object cached = localCache.get(cacheKey);
        if (cached != null) {
            return Optional.of((T) cached);
        }
        
        String json = redisClient.get(cacheKey);
        if (json == null || json.isEmpty()) {
            return Optional.empty();
        }
        
        try {
            T entity = objectMapper.readValue(json, clazz);
            localCache.put(cacheKey, entity);
            return Optional.of(entity);
        } catch (JsonProcessingException e) {
            log.error("[CACHE] Failed to deserialize {}:{} from cache", entityType, key, e);
            return Optional.empty();
        }
    }

    /**
     * Get entities list from cache.
     * Original: getEntitiesListFromCache function
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getEntitiesList(String entityType, Class<T> clazz) {
        String indexKey = buildIndexKey(entityType);
        List<String> keys = redisClient.zrange(indexKey, 0, -1);
        
        List<T> entities = new ArrayList<>();
        for (String key : keys) {
            getEntity(entityType, key, clazz).ifPresent(entities::add);
        }
        
        return entities;
    }

    /**
     * Get entities map from cache.
     * Original: getEntitiesMapFromCache function
     */
    public <T> Map<String, T> getEntitiesMap(String entityType, Class<T> clazz) {
        String indexKey = buildIndexKey(entityType);
        List<String> keys = redisClient.zrange(indexKey, 0, -1);
        
        Map<String, T> entities = new HashMap<>();
        for (String key : keys) {
            getEntity(entityType, key, clazz).ifPresent(entity -> entities.put(key, entity));
        }
        
        return entities;
    }

    /**
     * Refresh cache for an entity.
     * Original: refreshCache function
     */
    public <T> void refreshCache(String entityType, String key, T data) {
        writeCache(entityType, key, data);
    }

    /**
     * Delete entity from cache.
     */
    public void deleteEntity(String entityType, String key) {
        String cacheKey = buildCacheKey(entityType, key);
        redisClient.del(cacheKey);
        localCache.remove(cacheKey);
        
        String indexKey = buildIndexKey(entityType);
        redisClient.execute(tx -> {
            tx.zrem(indexKey, key);
        });
        
        log.debug("[CACHE] Deleted {}:{} from cache", entityType, key);
    }

    /**
     * Check if entity exists in cache.
     */
    public boolean exists(String entityType, String key) {
        String cacheKey = buildCacheKey(entityType, key);
        return localCache.containsKey(cacheKey) || redisClient.exists(cacheKey);
    }

    /**
     * Clear all caches.
     */
    public void clearAll() {
        localCache.clear();
        log.info("[CACHE] Cleared all local caches");
    }

    /**
     * Build cache key.
     */
    private String buildCacheKey(String entityType, String key) {
        return keyPrefix + CACHE_PREFIX + entityType + ":" + key;
    }

    /**
     * Build index key for entity type.
     */
    private String buildIndexKey(String entityType) {
        return keyPrefix + CACHE_PREFIX + "index:" + entityType;
    }

    /**
     * Add key to entity index.
     */
    private void addToEntityIndex(String entityType, String key) {
        String indexKey = buildIndexKey(entityType);
        redisClient.execute(tx -> {
            tx.zadd(indexKey, System.currentTimeMillis(), key);
            tx.expire(indexKey, CACHE_TTL);
        });
    }
}
