package io.opencti.database.redis.lock;

/**
 * Lock options for distributed locking.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original type: LockOptions interface
 */
public record LockOptions(
        long ttl,
        String draftId,
        boolean reentrant
) {
    public static final long DEFAULT_TTL = 30000;

    public LockOptions {
        if (ttl <= 0) {
            ttl = DEFAULT_TTL;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long ttl = DEFAULT_TTL;
        private String draftId;
        private boolean reentrant = false;

        public Builder ttl(long ttl) {
            this.ttl = ttl;
            return this;
        }

        public Builder draftId(String draftId) {
            this.draftId = draftId;
            return this;
        }

        public Builder reentrant(boolean reentrant) {
            this.reentrant = reentrant;
            return this;
        }

        public LockOptions build() {
            return new LockOptions(ttl, draftId, reentrant);
        }
    }
}
