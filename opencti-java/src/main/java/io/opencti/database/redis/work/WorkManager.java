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
 * 
 * 键名格式（与 TypeScript 源码一致）:
 * - Work 数据: 直接使用 workId 作为键名
 * - Connector 状态: work:{connectorId}
 */
public class WorkManager {

    private static final Logger log = LoggerFactory.getLogger(WorkManager.class);
    private static final String CONNECTOR_STATUS_PREFIX = "work:";
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
     * 原始逻辑: 直接使用 workId 作为键名
     */
    public void initializeWork(String workId) {
        String workKey = buildWorkKey(workId);
        
        Map<String, String> initialData = new HashMap<>();
        initialData.put("is_initialized", "true");
        
        redisClient.hset(workKey, initialData);
        
        log.debug("[WORK] Initialized work: {}", workId);
    }

    /**
     * Get work status.
     * Original: redisGetWork function
     * 原始逻辑: 直接使用 workId 作为键名
     */
    public Map<String, String> getWork(String workId) {
        String workKey = buildWorkKey(workId);
        return redisClient.hgetall(workKey);
    }

    /**
     * Update work progress figures.
     * Original: redisUpdateWorkFigures function
     * 原始逻辑: 如果 workId 包含 '_', 则更新 connector 状态
     */
    public WorkStatus updateWorkFigures(String workId) {
        String workKey = buildWorkKey(workId);
        
        if (workId.contains("_")) {
            String[] parts = workId.split("_");
            if (parts.length >= 2) {
                String connectorId = parts[1];
                String connectorKey = keyPrefix + CONNECTOR_STATUS_PREFIX + connectorId;
                redisClient.set(connectorKey, workId);
            }
        }
        
        long timestamp = System.currentTimeMillis();
        redisClient.execute(tx -> {
            tx.hincrby(workKey, "import_processed_number", 1);
            tx.hset(workKey, "import_last_processed", String.valueOf(timestamp));
        });
        
        return isWorkCompleted(workId);
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
        
        String processedStr = workData.get("import_processed_number");
        String expectedStr = workData.get("import_expected_number");
        
        long processed = parseLong(processedStr);
        long expected = parseLong(expectedStr);
        
        if (expected > 0 && processed >= expected) {
            return WorkStatus.COMPLETE;
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
        redisClient.hincrby(workKey, "import_expected_number", expectation);
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
     * 原始逻辑: 使用 work:{connectorId} 作为键名
     */
    public String getConnectorStatus(String connectorId) {
        String connectorKey = keyPrefix + CONNECTOR_STATUS_PREFIX + connectorId;
        return redisClient.get(connectorKey);
    }

    /**
     * Set connector status.
     */
    public void setConnectorStatus(String connectorId, String status) {
        String connectorKey = keyPrefix + CONNECTOR_STATUS_PREFIX + connectorId;
        redisClient.set(connectorKey, status);
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
     * 原始逻辑: 直接使用 workId，不加前缀
     */
    private String buildWorkKey(String workId) {
        return keyPrefix + workId;
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
