package io.opencti.database.redis.lock;

import io.opencti.database.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Lock manager for distributed locking.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original functions: lockResource, redisAddDeletions, redisFetchLatestDeletions
 */
public class LockManager {

    private static final Logger log = LoggerFactory.getLogger(LockManager.class);
    private static final String LOCK_PREFIX = "{locks}:";
    private static final String DELETION_KEY = "platform-deletions";
    private static final int MAX_RETRIES = 10;
    private static final long RETRY_DELAY_MS = 100;

    private final RedisClient redisClient;
    private final String keyPrefix;

    public LockManager(RedisClient redisClient, String keyPrefix) {
        this.redisClient = redisClient;
        this.keyPrefix = keyPrefix;
    }

    /**
     * Acquire a distributed lock on resources.
     * Original: lockResource function
     */
    public DistributedLock lockResource(List<String> resources, LockOptions options) {
        String lockId = UUID.randomUUID().toString();
        List<String> lockedResources = new ArrayList<>();

        try {
            for (String resource : resources) {
                String lockKey = buildLockKey(resource);
                boolean acquired = tryAcquireLock(lockKey, lockId, options.ttl());
                
                if (!acquired) {
                    if (options.reentrant()) {
                        String currentValue = redisClient.get(lockKey);
                        if (currentValue != null && currentValue.equals(lockId)) {
                            lockedResources.add(resource);
                            continue;
                        }
                    }
                    releaseLocks(lockedResources, lockId);
                    throw new LockAcquisitionException("Failed to acquire lock on resource: " + resource);
                }
                lockedResources.add(resource);
            }

            log.debug("[LOCK] Acquired lock on {} resources", resources.size());
            return new DistributedLock(redisClient, resources, lockId, options.ttl(), keyPrefix);
        } catch (Exception e) {
            releaseLocks(lockedResources, lockId);
            throw e;
        }
    }

    /**
     * Try to acquire a lock with retries.
     */
    private boolean tryAcquireLock(String lockKey, String lockId, long ttl) {
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                redisClient.set(lockKey, lockId, "NX", (int) (ttl / 1000));
                return true;
            } catch (Exception e) {
                if (i < MAX_RETRIES - 1) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Release all locks.
     */
    private void releaseLocks(List<String> resources, String lockId) {
        for (String resource : resources) {
            String lockKey = buildLockKey(resource);
            String currentValue = redisClient.get(lockKey);
            if (lockId.equals(currentValue)) {
                redisClient.del(lockKey);
            }
        }
    }

    /**
     * Add deletions to the deletion tracking list.
     * Original: redisAddDeletions function
     * 原始逻辑: 键名是 {prefix}platform-deletions
     */
    public void addDeletions(List<String> internalIds, String draftId) {
        long timestamp = System.currentTimeMillis();
        String deletionKey = buildDeletionKey();
        
        redisClient.execute(tx -> {
            tx.zremrangebyscore(deletionKey, "-inf", String.valueOf(timestamp - 5000));
            
            for (String internalId : internalIds) {
                String id = draftId != null ? internalId + draftId : internalId;
                tx.zadd(deletionKey, timestamp, id);
            }
        });
        
        log.debug("[LOCK] Added {} deletions", internalIds.size());
    }

    /**
     * Fetch latest deletions.
     * Original: redisFetchLatestDeletions function
     * 原始逻辑: 返回最近5秒内的删除记录
     */
    public List<String> fetchLatestDeletions() {
        long timestamp = System.currentTimeMillis();
        String deletionKey = buildDeletionKey();
        
        redisClient.zremrangebyscore(deletionKey, "-inf", String.valueOf(timestamp - 5000));
        
        return redisClient.zrange(deletionKey, 0, -1);
    }

    /**
     * Clear all deletions.
     */
    public void clearDeletions() {
        String deletionKey = buildDeletionKey();
        redisClient.del(deletionKey);
        log.debug("[LOCK] Cleared all deletions");
    }

    /**
     * Build lock key for a resource.
     */
    private String buildLockKey(String resource) {
        return keyPrefix + LOCK_PREFIX + resource;
    }

    /**
     * Build deletion key.
     * 原始逻辑: 使用 {prefix}platform-deletions
     */
    private String buildDeletionKey() {
        return keyPrefix + DELETION_KEY;
    }

    /**
     * Exception thrown when lock acquisition fails.
     */
    public static class LockAcquisitionException extends RuntimeException {
        public LockAcquisitionException(String message) {
            super(message);
        }
    }
}
