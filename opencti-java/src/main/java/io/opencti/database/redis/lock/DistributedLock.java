package io.opencti.database.redis.lock;

import io.opencti.database.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Distributed lock implementation.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original type: Lock interface and lockResource function
 */
public class DistributedLock {

    private static final Logger log = LoggerFactory.getLogger(DistributedLock.class);

    private final RedisClient redisClient;
    private final List<String> resources;
    private final String lockId;
    private final long ttl;
    private final String keyPrefix;
    private final AtomicBoolean locked = new AtomicBoolean(true);
    private final AbortSignal abortSignal;

    public DistributedLock(RedisClient redisClient, List<String> resources, String lockId, long ttl, String keyPrefix) {
        this.redisClient = redisClient;
        this.resources = resources;
        this.lockId = lockId;
        this.ttl = ttl;
        this.keyPrefix = keyPrefix;
        this.abortSignal = new AbortSignal();
    }

    /**
     * Unlock all resources.
     * Original: lock.unlock function
     */
    public void unlock() {
        if (!locked.compareAndSet(true, false)) {
            return;
        }

        for (String resource : resources) {
            String lockKey = buildLockKey(resource);
            String currentValue = redisClient.get(lockKey);
            
            if (lockId.equals(currentValue)) {
                redisClient.del(lockKey);
                log.debug("[LOCK] Released lock on resource: {}", resource);
            }
        }
    }

    /**
     * Extend the lock TTL.
     * Original: lock.extend function
     */
    public void extend() {
        if (!locked.get()) {
            throw new IllegalStateException("Lock has been released");
        }

        for (String resource : resources) {
            String lockKey = buildLockKey(resource);
            String currentValue = redisClient.get(lockKey);
            
            if (lockId.equals(currentValue)) {
                redisClient.expire(lockKey, (int) (ttl / 1000));
                log.debug("[LOCK] Extended lock on resource: {}", resource);
            }
        }
    }

    /**
     * Get the abort signal for this lock.
     * Original: lock.signal
     */
    public AbortSignal getSignal() {
        return abortSignal;
    }

    /**
     * Check if the lock is still held.
     */
    public boolean isLocked() {
        return locked.get();
    }

    /**
     * Build lock key for a resource.
     * 原始逻辑: 使用 {locks}: 前缀
     */
    private String buildLockKey(String resource) {
        return keyPrefix + "{locks}:" + resource;
    }

    /**
     * Abort signal for lock operations.
     * Original: AbortSignal class
     */
    public static class AbortSignal {
        private volatile boolean aborted = false;

        public void abort() {
            this.aborted = true;
        }

        public boolean isAborted() {
            return aborted;
        }
    }
}
