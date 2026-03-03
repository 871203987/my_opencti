package io.opencti.database.redis.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.opencti.database.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;

/**
 * Session manager for handling user sessions.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original functions: setSession, getSession, killSession, getSessions, extendSession
 */
public class SessionManager {

    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);
    private static final String SESSION_PREFIX = "session:";
    private static final String SESSION_LIST_KEY = "platform_sessions";

    private final RedisClient redisClient;
    private final ObjectMapper objectMapper;

    public SessionManager(RedisClient redisClient) {
        this.redisClient = redisClient;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Set a session in Redis.
     * Original: setSession function
     */
    public void setSession(String key, Session session, int expirationSeconds) {
        try {
            String sessionKey = SESSION_PREFIX + key;
            String sessionJson = objectMapper.writeValueAsString(session);
            
            redisClient.setex(sessionKey, expirationSeconds, sessionJson);
            redisClient.execute(tx -> {
                tx.zadd(SESSION_LIST_KEY, Instant.now().getEpochSecond() + expirationSeconds, key);
            });
            
            log.debug("[SESSION] Set session: {} for user: {}", key, session.userId());
        } catch (JsonProcessingException e) {
            log.error("[SESSION] Failed to serialize session: {}", key, e);
            throw new RuntimeException("Failed to serialize session", e);
        }
    }

    /**
     * Get a session from Redis.
     * Original: getSession function
     */
    public Optional<Session> getSession(String key) {
        try {
            String sessionKey = SESSION_PREFIX + key;
            String sessionJson = redisClient.get(sessionKey);
            
            if (sessionJson == null || sessionJson.isEmpty()) {
                return Optional.empty();
            }
            
            Session session = objectMapper.readValue(sessionJson, Session.class);
            
            if (session.isExpired()) {
                killSession(key);
                return Optional.empty();
            }
            
            return Optional.of(session);
        } catch (JsonProcessingException e) {
            log.error("[SESSION] Failed to deserialize session: {}", key, e);
            return Optional.empty();
        }
    }

    /**
     * Kill a session.
     * Original: killSession function
     */
    public Session killSession(String key) {
        try {
            Optional<Session> session = getSession(key);
            
            String sessionKey = SESSION_PREFIX + key;
            redisClient.del(sessionKey);
            redisClient.execute(tx -> {
                tx.zrem(SESSION_LIST_KEY, key);
            });
            
            log.debug("[SESSION] Killed session: {}", key);
            return session.orElse(null);
        } catch (Exception e) {
            log.error("[SESSION] Failed to kill session: {}", key, e);
            return null;
        }
    }

    /**
     * Get all session keys.
     * Original: getSessionKeys function
     */
    public List<String> getSessionKeys() {
        cleanExpiredSessions();
        return redisClient.zrange(SESSION_LIST_KEY, 0, -1);
    }

    /**
     * Get all sessions.
     * Original: getSessions function
     */
    public List<Session> getSessions() {
        List<String> keys = getSessionKeys();
        List<Session> sessions = new ArrayList<>();
        
        for (String key : keys) {
            getSession(key).ifPresent(sessions::add);
        }
        
        return sessions;
    }

    /**
     * Extend a session's expiration time.
     * Original: extendSession function
     */
    public void extendSession(String sessionId, int extensionSeconds) {
        try {
            Optional<Session> sessionOpt = getSession(sessionId);
            if (sessionOpt.isEmpty()) {
                log.warn("[SESSION] Cannot extend non-existent session: {}", sessionId);
                return;
            }
            
            Session session = sessionOpt.get();
            Session extendedSession = Session.builder()
                    .id(session.id())
                    .userId(session.userId())
                    .username(session.username())
                    .email(session.email())
                    .source(session.source())
                    .apiToken(session.apiToken())
                    .createdAt(session.createdAt())
                    .expiresAt(Instant.now().plusSeconds(extensionSeconds))
                    .lastActivity(Instant.now())
                    .metadata(session.metadata())
                    .build();
            
            setSession(sessionId, extendedSession, extensionSeconds);
            log.debug("[SESSION] Extended session: {} by {} seconds", sessionId, extensionSeconds);
        } catch (Exception e) {
            log.error("[SESSION] Failed to extend session: {}", sessionId, e);
        }
    }

    /**
     * Clear all sessions.
     */
    public void clearSessions() {
        List<String> keys = getSessionKeys();
        for (String key : keys) {
            killSession(key);
        }
        log.info("[SESSION] Cleared {} sessions", keys.size());
    }

    /**
     * Clean expired sessions.
     * Original: cleanExpiredSessions logic
     */
    private void cleanExpiredSessions() {
        long now = Instant.now().getEpochSecond();
        redisClient.zremrangebyscore(SESSION_LIST_KEY, "0", String.valueOf(now));
    }

    /**
     * Get sessions for a specific user.
     */
    public List<Session> getSessionsForUser(String userId) {
        List<Session> allSessions = getSessions();
        return allSessions.stream()
                .filter(s -> userId.equals(s.userId()))
                .toList();
    }

    /**
     * Kill all sessions for a specific user.
     */
    public void killSessionsForUser(String userId) {
        List<Session> userSessions = getSessionsForUser(userId);
        for (Session session : userSessions) {
            if (session.id() != null) {
                killSession(session.id());
            }
        }
        log.info("[SESSION] Killed {} sessions for user: {}", userSessions.size(), userId);
    }
}
