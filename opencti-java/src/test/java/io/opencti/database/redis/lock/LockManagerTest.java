package io.opencti.database.redis.lock;

import io.opencti.database.redis.RedisClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test for LockManager.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original methods: lockResource, addDeletions, fetchLatestDeletions
 */
@ExtendWith(MockitoExtension.class)
class LockManagerTest {

    @Mock
    private RedisClient redisClient;

    private LockManager lockManager;

    @BeforeEach
    void setUp() {
        lockManager = new LockManager(redisClient, "opencti:");
    }

    @Test
    @DisplayName("Should acquire lock successfully")
    void testAcquireLock() {
        String resourceId = "resource-123";
        String lockKey = "opencti:{locks}:resource-123";
        
        doNothing().when(redisClient).set(eq(lockKey), anyString(), eq("NX"), anyInt());

        LockOptions options = LockOptions.builder().ttl(30000).build();
        DistributedLock lock = lockManager.lockResource(List.of(resourceId), options);

        assertNotNull(lock);
        verify(redisClient).set(eq(lockKey), anyString(), eq("NX"), anyInt());
    }

    @Test
    @DisplayName("Should release lock on unlock")
    void testReleaseLock() {
        String resourceId = "resource-123";
        String lockValue = UUID.randomUUID().toString();
        String lockKey = "opencti:{locks}:resource-123";

        when(redisClient.get(lockKey)).thenReturn(lockValue);

        DistributedLock lock = new DistributedLock(redisClient, List.of(resourceId), lockValue, 30000, "opencti:");
        lock.unlock();

        verify(redisClient).del(lockKey);
    }

    @Test
    @DisplayName("Should extend lock expiration")
    void testExtendLock() {
        String resourceId = "resource-123";
        String lockValue = UUID.randomUUID().toString();
        String lockKey = "opencti:{locks}:resource-123";

        when(redisClient.get(lockKey)).thenReturn(lockValue);
        when(redisClient.expire(lockKey, 30)).thenReturn(true);

        DistributedLock lock = new DistributedLock(redisClient, List.of(resourceId), lockValue, 30000, "opencti:");
        lock.extend();

        verify(redisClient).expire(lockKey, 30);
    }

    @Test
    @DisplayName("Should add deletions to Redis")
    void testAddDeletions() {
        List<String> internalIds = List.of("id1", "id2", "id3");
        String draftId = "draft-123";

        lockManager.addDeletions(internalIds, draftId);

        verify(redisClient).execute(any());
    }

    @Test
    @DisplayName("Should fetch latest deletions")
    void testFetchLatestDeletions() {
        when(redisClient.zrange(eq("opencti:platform-deletions"), anyLong(), anyLong()))
            .thenReturn(List.of("id1", "id2"));

        List<String> deletions = lockManager.fetchLatestDeletions();

        assertNotNull(deletions);
        assertEquals(2, deletions.size());
    }

    @Test
    @DisplayName("Should create LockOptions with default values")
    void testLockOptionsDefaults() {
        LockOptions options = LockOptions.builder().build();

        assertEquals(30000, options.ttl());
        assertNull(options.draftId());
        assertFalse(options.reentrant());
    }

    @Test
    @DisplayName("Should create LockOptions with custom values")
    void testLockOptionsCustom() {
        LockOptions options = LockOptions.builder()
            .ttl(60000)
            .draftId("draft-123")
            .reentrant(true)
            .build();

        assertEquals(60000, options.ttl());
        assertEquals("draft-123", options.draftId());
        assertTrue(options.reentrant());
    }

    @Test
    @DisplayName("Should get abort signal from lock")
    void testGetAbortSignal() {
        DistributedLock lock = new DistributedLock(redisClient, List.of("resource-1"), "lock-id", 30000, "opencti:");
        
        DistributedLock.AbortSignal signal = lock.getSignal();
        
        assertNotNull(signal);
        assertFalse(signal.isAborted());
        signal.abort();
        assertTrue(signal.isAborted());
    }
}
