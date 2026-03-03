package io.opencti.database.redis.work;

/**
 * Work status enumeration.
 * Original file: opencti-platform/opencti-graphql/src/database/redis.ts
 * Original type: WorkStatus type
 */
public enum WorkStatus {
    RUNNING("running"),
    COMPLETE("complete"),
    ERROR("error"),
    PARTIAL("partial");

    private final String status;

    WorkStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static WorkStatus fromString(String status) {
        for (WorkStatus workStatus : values()) {
            if (workStatus.status.equalsIgnoreCase(status)) {
                return workStatus;
            }
        }
        return RUNNING;
    }
}
