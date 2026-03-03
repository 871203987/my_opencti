package io.opencti.database.redis.session;

import io.opencti.database.redis.RedisClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Test for SessionManager.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original methods: setSession, getSession, killSession, getSessions, extendSession
 */
@ExtendWith(MockitoExtension.class)
class SessionManagerTest {

    @Mock
    private RedisClient redisClient;

    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManager(redisClient);
    }

    @Test
    @DisplayName("Should set session with expiration")
    void testSetSession() {
        Session session = Session.builder()
            .id("session-123")
            .userId("user-456")
            .username("testuser")
            .email("user@example.com")
            .source("local")
            .createdAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build();

        sessionManager.setSession("session-123", session, 3600);

        verify(redisClient).setex(contains("session:session-123"), eq(3600), anyString());
    }

    @Test
    @DisplayName("Should get existing session")
    void testGetSession() {
        String sessionJson = "{\"id\":\"session-123\",\"userId\":\"user-456\",\"username\":\"testuser\"," +
            "\"email\":\"user@example.com\",\"source\":\"local\"," +
            "\"created_at\":\"2024-01-01T00:00:00Z\",\"expires_at\":\"2099-01-01T01:00:00Z\"}";

        when(redisClient.get(contains("session:session-123"))).thenReturn(sessionJson);

        Optional<Session> result = sessionManager.getSession("session-123");

        assertTrue(result.isPresent());
        assertEquals("session-123", result.get().id());
        assertEquals("testuser", result.get().username());
    }

    @Test
    @DisplayName("Should return empty for non-existing session")
    void testGetNonExistingSession() {
        when(redisClient.get(contains("session:nonexistent"))).thenReturn(null);

        Optional<Session> result = sessionManager.getSession("nonexistent");

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should kill session directly")
    void testKillSession() {
        doNothing().when(redisClient).del(anyString());
        when(redisClient.get(anyString())).thenReturn(null);

        sessionManager.killSession("session-123");

        verify(redisClient).del(contains("session:session-123"));
    }

    @Test
    @DisplayName("Should extend session")
    void testExtendSession() {
        String sessionJson = "{\"id\":\"session-123\",\"userId\":\"user-456\",\"username\":\"testuser\"," +
            "\"email\":\"user@example.com\",\"source\":\"local\"," +
            "\"created_at\":\"2024-01-01T00:00:00Z\",\"expires_at\":\"2099-01-01T01:00:00Z\"}";

        when(redisClient.get(contains("session:session-123"))).thenReturn(sessionJson);

        sessionManager.extendSession("session-123", 3600);

        verify(redisClient).setex(contains("session:session-123"), eq(3600), anyString());
    }

    @Test
    @DisplayName("Should get session keys")
    void testGetSessionKeys() {
        when(redisClient.zrange(anyString(), anyLong(), anyLong())).thenReturn(java.util.List.of("session-1", "session-2"));

        java.util.List<String> keys = sessionManager.getSessionKeys();

        assertNotNull(keys);
        assertEquals(2, keys.size());
    }

    @Test
    @DisplayName("Should clear all sessions")
    void testClearSessions() {
        when(redisClient.zrange(anyString(), anyLong(), anyLong())).thenReturn(java.util.List.of("session-1"));
        when(redisClient.get(anyString())).thenReturn(null);

        sessionManager.clearSessions();

        verify(redisClient).del(contains("session:session-1"));
    }
}
