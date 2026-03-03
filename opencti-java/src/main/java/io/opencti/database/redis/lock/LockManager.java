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
    private static final String LOCK_PREFIX = "lock:";
    private static final String DELETION_PREFIX = "deletions:";
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
     */
    public void addDeletions(List<String> internalIds, String draftId) {
        String deletionKey = buildDeletionKey(draftId);
        long timestamp = System.currentTimeMillis();
        
        redisClient.execute(tx -> {
            for (String internalId : internalIds) {
                tx.zadd(deletionKey, timestamp, internalId);
            }
            tx.expire(deletionKey, 3600);
        });
        
        log.debug("[LOCK] Added {} deletions for draft: {}", internalIds.size(), draftId);
    }

    /**
     * Fetch latest deletions since a timestamp.
     * Original: redisFetchLatestDeletions function
     */
    public List<String> fetchLatestDeletions(String draftId, long sinceTimestamp) {
        String deletionKey = buildDeletionKey(draftId);
        String min = String.valueOf(sinceTimestamp);
        String max = String.valueOf(System.currentTimeMillis());
        
        List<String> deletions = redisClient.zrange(deletionKey, 0, -1);
        
        redisClient.zremrangebyscore(deletionKey, "0", min);
        
        return deletions;
    }

    /**
     * Clear deletions for a draft.
     */
    public void clearDeletions(String draftId) {
        String deletionKey = buildDeletionKey(draftId);
        redisClient.del(deletionKey);
        log.debug("[LOCK] Cleared deletions for draft: {}", draftId);
    }

    /**
     * Build lock key for a resource.
     */
    private String buildLockKey(String resource) {
        return keyPrefix + LOCK_PREFIX + resource;
    }

    /**
     * Build deletion key for a draft.
     */
    private String buildDeletionKey(String draftId) {
        return keyPrefix + DELETION_PREFIX + (draftId != null ? draftId : "default");
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
