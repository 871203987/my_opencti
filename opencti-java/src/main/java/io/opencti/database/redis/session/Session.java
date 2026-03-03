package io.opencti.database.redis.session;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Map;

/**
 * Session object representing a user session.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original type: Session interface
 */
public record Session(
        String id,
        String userId,
        String username,
        String email,
        String source,
        String apiToken,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("expires_at") Instant expiresAt,
        @JsonProperty("last_activity") Instant lastActivity,
        Map<String, Object> metadata
) {
    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String userId;
        private String username;
        private String email;
        private String source;
        private String apiToken;
        private Instant createdAt;
        private Instant expiresAt;
        private Instant lastActivity;
        private Map<String, Object> metadata;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder apiToken(String apiToken) {
            this.apiToken = apiToken;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder lastActivity(Instant lastActivity) {
            this.lastActivity = lastActivity;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Session build() {
            return new Session(id, userId, username, email, source, apiToken, createdAt, expiresAt, lastActivity, metadata);
        }
    }
}
