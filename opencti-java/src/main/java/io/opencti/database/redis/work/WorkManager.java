package io.opencti.database.redis.work;

import io.opencti.database.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Work manager for tracking background job progress.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original functions: redisInitializeWork, redisGetWork, redisUpdateWorkFigures, isWorkCompleted
 */
public class WorkManager {

    private static final Logger log = LoggerFactory.getLogger(WorkManager.class);
    private static final String WORK_PREFIX = "work:";
    private static final int WORK_TTL = 86400;

    private final RedisClient redisClient;
    private final String keyPrefix;

    public WorkManager(RedisClient redisClient, String keyPrefix) {
        this.redisClient = redisClient;
        this.keyPrefix = keyPrefix;
    }

    /**
     * Initialize a work entry.
     * Original: redisInitializeWork function
     */
    public void initializeWork(String workId) {
        String workKey = buildWorkKey(workId);
        
        Map<String, String> initialData = new HashMap<>();
        initialData.put("status", WorkStatus.RUNNING.getStatus());
        initialData.put("import_processed_number", "0");
        initialData.put("import_expected_number", "0");
        initialData.put("timestamp", String.valueOf(System.currentTimeMillis()));
        
        redisClient.hset(workKey, initialData);
        redisClient.expire(workKey, WORK_TTL);
        
        log.debug("[WORK] Initialized work: {}", workId);
    }

    /**
     * Get work status.
     * Original: redisGetWork function
     */
    public Map<String, String> getWork(String workId) {
        String workKey = buildWorkKey(workId);
        return redisClient.hgetall(workKey);
    }

    /**
     * Update work progress figures.
     * Original: redisUpdateWorkFigures function
     */
    public WorkStatus updateWorkFigures(String workId) {
        String workKey = buildWorkKey(workId);
        Map<String, String> workData = redisClient.hgetall(workKey);
        
        if (workData.isEmpty()) {
            return WorkStatus.ERROR;
        }
        
        long processed = parseLong(workData.get("import_processed_number"));
        long expected = parseLong(workData.get("import_expected_number"));
        String status = workData.get("status");
        
        if (processed >= expected && expected > 0) {
            redisClient.hset(workKey, "status", WorkStatus.COMPLETE.getStatus());
            return WorkStatus.COMPLETE;
        }
        
        return WorkStatus.fromString(status);
    }

    /**
     * Check if work is completed.
     * Original: isWorkCompleted function
     */
    public WorkStatus isWorkCompleted(String workId) {
        Map<String, String> workData = getWork(workId);
        
        if (workData.isEmpty()) {
            return WorkStatus.ERROR;
        }
        
        String status = workData.get("status");
        return WorkStatus.fromString(status);
    }

    /**
     * Update action expectation count.
     * Original: updateActionExpectation function
     */
    public void updateActionExpectation(String workId, int expectation) {
        String workKey = buildWorkKey(workId);
        redisClient.hset(workKey, "import_expected_number", String.valueOf(expectation));
        log.debug("[WORK] Updated expectation for {}: {}", workId, expectation);
    }

    /**
     * Increment processed count.
     */
    public void incrementProcessed(String workId, long delta) {
        String workKey = buildWorkKey(workId);
        redisClient.hincrby(workKey, "import_processed_number", delta);
    }

    /**
     * Get connector status.
     * Original: getConnectorStatus function
     */
    public String getConnectorStatus(String connectorId) {
        String connectorKey = keyPrefix + "connector:" + connectorId;
        return redisClient.get(connectorKey);
    }

    /**
     * Set connector status.
     */
    public void setConnectorStatus(String connectorId, String status) {
        String connectorKey = keyPrefix + "connector:" + connectorId;
        redisClient.setex(connectorKey, 3600, status);
    }

    /**
     * Delete work entries.
     * Original: deleteWorks function
     */
    public void deleteWorks(List<String> internalIds) {
        for (String internalId : internalIds) {
            String workKey = buildWorkKey(internalId);
            redisClient.del(workKey);
        }
        log.debug("[WORK] Deleted {} work entries", internalIds.size());
    }

    /**
     * Mark work as error.
     */
    public void markError(String workId, String errorMessage) {
        String workKey = buildWorkKey(workId);
        redisClient.hset(workKey, "status", WorkStatus.ERROR.getStatus());
        redisClient.hset(workKey, "error", errorMessage);
        log.error("[WORK] Work {} marked as error: {}", workId, errorMessage);
    }

    /**
     * Mark work as partial.
     */
    public void markPartial(String workId, String message) {
        String workKey = buildWorkKey(workId);
        redisClient.hset(workKey, "status", WorkStatus.PARTIAL.getStatus());
        redisClient.hset(workKey, "message", message);
    }

    /**
     * Build work key.
     */
    private String buildWorkKey(String workId) {
        return keyPrefix + WORK_PREFIX + workId;
    }

    /**
     * Parse long safely.
     */
    private long parseLong(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
