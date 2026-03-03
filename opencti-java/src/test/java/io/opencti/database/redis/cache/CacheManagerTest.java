package io.opencti.database.redis.cache;

import io.opencti.database.redis.RedisClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test for CacheManager.
 * Original file: opencti-platform/opencti-graphql/src/database/cache.ts
 * Original methods: writeCacheForEntity, resetCacheForEntity, getEntitiesMapFromCache, getEntitiesListFromCache
 */
@ExtendWith(MockitoExtension.class)
class CacheManagerTest {

    @Mock
    private RedisClient redisClient;

    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager = new CacheManager(redisClient, "opencti:");
    }

    @Test
    @DisplayName("Should write cache for entity")
    void testWriteCacheForEntity() {
        String entityType = "Malware";
        String entityId = "malware-123";
        String entityData = "{\"name\":\"BadMalware\",\"type\":\"malware\"}";

        cacheManager.writeCache(entityType, entityId, entityData);

        verify(redisClient).setex(contains("cache:" + entityType + ":" + entityId), anyInt(), anyString());
        verify(redisClient).execute(any());
    }

    @Test
    @DisplayName("Should reset cache for entity type")
    void testResetCacheForEntity() {
        String entityType = "Malware";
        when(redisClient.zrange(contains("index:" + entityType), anyLong(), anyLong()))
            .thenReturn(List.of("malware-123", "malware-456"));

        cacheManager.resetCache(entityType);

        verify(redisClient).del(contains("cache:" + entityType + ":malware-123"));
        verify(redisClient).del(contains("cache:" + entityType + ":malware-456"));
    }

    @Test
    @DisplayName("Should get entity from cache")
    void testGetEntityFromCache() {
        String entityType = "Malware";
        String entityId = "malware-123";
        String entityData = "\"BadMalware\"";

        when(redisClient.get(contains("cache:" + entityType + ":" + entityId))).thenReturn(entityData);

        Optional<String> result = cacheManager.getEntity(entityType, entityId, String.class);

        assertTrue(result.isPresent());
        assertEquals("BadMalware", result.get());
    }

    @Test
    @DisplayName("Should return empty for non-existing entity")
    void testGetNonExistingEntity() {
        String entityType = "Malware";
        String entityId = "nonexistent";

        when(redisClient.get(contains("cache:" + entityType + ":" + entityId))).thenReturn(null);

        Optional<String> result = cacheManager.getEntity(entityType, entityId, String.class);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should get entities list from cache")
    void testGetEntitiesListFromCache() {
        String entityType = "Malware";
        when(redisClient.zrange(contains("index:" + entityType), anyLong(), anyLong()))
            .thenReturn(List.of("malware-123", "malware-456"));
        when(redisClient.get(contains("cache:" + entityType + ":malware-123")))
            .thenReturn("\"BadMalware\"");
        when(redisClient.get(contains("cache:" + entityType + ":malware-456")))
            .thenReturn("\"WorseMalware\"");

        List<String> result = cacheManager.getEntitiesList(entityType, String.class);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should get entities map from cache")
    void testGetEntitiesMapFromCache() {
        String entityType = "Malware";
        when(redisClient.zrange(contains("index:" + entityType), anyLong(), anyLong()))
            .thenReturn(List.of("malware-123", "malware-456"));
        when(redisClient.get(contains("cache:" + entityType + ":malware-123")))
            .thenReturn("\"BadMalware\"");
        when(redisClient.get(contains("cache:" + entityType + ":malware-456")))
            .thenReturn("\"WorseMalware\"");

        Map<String, String> result = cacheManager.getEntitiesMap(entityType, String.class);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey("malware-123"));
        assertTrue(result.containsKey("malware-456"));
    }

    @Test
    @DisplayName("Should check if entity exists in cache")
    void testExistsInCache() {
        String entityType = "Malware";
        String entityId = "malware-123";

        when(redisClient.exists(contains("cache:" + entityType + ":" + entityId))).thenReturn(true);

        boolean exists = cacheManager.exists(entityType, entityId);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Should delete entity from cache")
    void testDeleteEntityFromCache() {
        String entityType = "Malware";
        String entityId = "malware-123";

        cacheManager.deleteEntity(entityType, entityId);

        verify(redisClient).del(contains("cache:" + entityType + ":" + entityId));
    }

    @Test
    @DisplayName("Should refresh cache for entity")
    void testRefreshCache() {
        String entityType = "Malware";
        String entityId = "malware-123";
        String entityData = "UpdatedMalware";

        cacheManager.refreshCache(entityType, entityId, entityData);

        verify(redisClient).setex(contains("cache:" + entityType + ":" + entityId), anyInt(), anyString());
    }

    @Test
    @DisplayName("Should clear all local caches")
    void testClearAll() {
        cacheManager.clearAll();
    }
}
