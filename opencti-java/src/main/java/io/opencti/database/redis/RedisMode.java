package io.opencti.database.redis;

/**
 * Redis connection mode enumeration.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original type: redisMode: string (cluster | sentinel | single)
 */
public enum RedisMode {
    SINGLE("single"),
    CLUSTER("cluster"),
    SENTINEL("sentinel");

    private final String mode;

    RedisMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public static RedisMode fromString(String mode) {
        if (mode == null || mode.isEmpty()) {
            return SINGLE;
        }
        for (RedisMode redisMode : values()) {
            if (redisMode.mode.equalsIgnoreCase(mode)) {
                return redisMode;
            }
        }
        return SINGLE;
    }
}
