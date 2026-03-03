package io.opencti.database.redis.stream;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * SSE Event for Redis Stream.
 * Original file: opencti-platform/opencti-graphql/src/database/redis-stream.ts
 * Original type: SseEvent interface
 */
public record SseEvent(
        String id,
        String type,
        String data,
        @JsonProperty("event_id") String eventId,
        @JsonProperty("created_at") Instant createdAt
) {
    public static Builder builder() {
        return new Builder();
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (id != null) map.put("id", id);
        if (type != null) map.put("type", type);
        if (data != null) map.put("data", data);
        if (eventId != null) map.put("event_id", eventId);
        if (createdAt != null) map.put("created_at", createdAt.toString());
        return map;
    }

    public static SseEvent fromMap(Map<String, String> map) {
        return new SseEvent(
                map.get("id"),
                map.get("type"),
                map.get("data"),
                map.get("event_id"),
                map.get("created_at") != null ? Instant.parse(map.get("created_at")) : null
        );
    }

    public static class Builder {
        private String id;
        private String type;
        private String data;
        private String eventId;
        private Instant createdAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public SseEvent build() {
            return new SseEvent(id, type, data, eventId, createdAt);
        }
    }
}
