package io.opencti.database.redis.work;

import io.opencti.database.redis.RedisClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test for WorkManager.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original methods: initializeWork, getWork, updateWorkFigures, isWorkCompleted
 */
@ExtendWith(MockitoExtension.class)
class WorkManagerTest {

    @Mock
    private RedisClient redisClient;

    private WorkManager workManager;

    @BeforeEach
    void setUp() {
        workManager = new WorkManager(redisClient, "opencti:");
    }

    @Test
    @DisplayName("Should initialize work")
    void testInitializeWork() {
        String workId = "work-123";

        workManager.initializeWork(workId);

        verify(redisClient).hset(eq("opencti:work-123"), anyMap());
    }

    @Test
    @DisplayName("Should get work status")
    void testGetWork() {
        String workId = "work-123";
        Map<String, String> workData = new HashMap<>();
        workData.put("status", "running");
        workData.put("import_processed_number", "50");
        workData.put("import_expected_number", "100");

        when(redisClient.hgetall("opencti:work-123")).thenReturn(workData);

        Map<String, String> result = workManager.getWork(workId);

        assertNotNull(result);
        assertEquals("running", result.get("status"));
        assertEquals("50", result.get("import_processed_number"));
    }

    @Test
    @DisplayName("Should update work figures")
    void testUpdateWorkFigures() {
        String workId = "work-123";
        Map<String, String> workData = new HashMap<>();
        workData.put("status", "running");
        workData.put("import_processed_number", "100");
        workData.put("import_expected_number", "100");

        when(redisClient.hgetall("opencti:work-123")).thenReturn(workData);

        WorkStatus status = workManager.updateWorkFigures(workId);

        assertEquals(WorkStatus.COMPLETE, status);
    }

    @Test
    @DisplayName("Should check if work is completed")
    void testIsWorkCompleted() {
        String workId = "work-123";
        Map<String, String> workData = new HashMap<>();
        workData.put("status", "complete");
        workData.put("import_processed_number", "100");
        workData.put("import_expected_number", "100");

        when(redisClient.hgetall("opencti:work-123")).thenReturn(workData);

        WorkStatus status = workManager.isWorkCompleted(workId);

        assertEquals(WorkStatus.COMPLETE, status);
    }

    @Test
    @DisplayName("Should return running status for incomplete work")
    void testIsWorkCompletedRunning() {
        String workId = "work-123";
        Map<String, String> workData = new HashMap<>();
        workData.put("status", "running");
        workData.put("import_processed_number", "50");
        workData.put("import_expected_number", "100");

        when(redisClient.hgetall("opencti:work-123")).thenReturn(workData);

        WorkStatus status = workManager.isWorkCompleted(workId);

        assertEquals(WorkStatus.RUNNING, status);
    }

    @Test
    @DisplayName("Should delete works")
    void testDeleteWorks() {
        String workId1 = "work-123";
        String workId2 = "work-456";

        workManager.deleteWorks(List.of(workId1, workId2));

        verify(redisClient).del("opencti:work-123");
        verify(redisClient).del("opencti:work-456");
    }

    @Test
    @DisplayName("Should get connector status")
    void testGetConnectorStatus() {
        String connectorId = "connector-123";
        when(redisClient.get("opencti:work:connector-123")).thenReturn("connected");

        String status = workManager.getConnectorStatus(connectorId);

        assertNotNull(status);
        assertEquals("connected", status);
    }

    @Test
    @DisplayName("Should update action expectation")
    void testUpdateActionExpectation() {
        String workId = "work-123";
        int expectation = 10;

        workManager.updateActionExpectation(workId, expectation);

        verify(redisClient).hincrby("opencti:work-123", "import_expected_number", 10L);
    }

    @Test
    @DisplayName("Should increment processed count")
    void testIncrementProcessed() {
        String workId = "work-123";

        workManager.incrementProcessed(workId, 5);

        verify(redisClient).hincrby("opencti:work-123", "import_processed_number", 5L);
    }

    @Test
    @DisplayName("Should mark work as error")
    void testMarkError() {
        String workId = "work-123";

        workManager.markError(workId, "Something went wrong");

        verify(redisClient).hset("opencti:work-123", "status", "error");
        verify(redisClient).hset("opencti:work-123", "error", "Something went wrong");
    }

    @Test
    @DisplayName("Should mark work as partial")
    void testMarkPartial() {
        String workId = "work-123";

        workManager.markPartial(workId, "Partial completion");

        verify(redisClient).hset("opencti:work-123", "status", "partial");
        verify(redisClient).hset("opencti:work-123", "message", "Partial completion");
    }

    @Test
    @DisplayName("Should parse WorkStatus from string")
    void testWorkStatusFromString() {
        assertEquals(WorkStatus.RUNNING, WorkStatus.fromString("running"));
        assertEquals(WorkStatus.COMPLETE, WorkStatus.fromString("complete"));
        assertEquals(WorkStatus.ERROR, WorkStatus.fromString("error"));
        assertEquals(WorkStatus.PARTIAL, WorkStatus.fromString("partial"));
        assertEquals(WorkStatus.RUNNING, WorkStatus.fromString("unknown"));
    }
}
