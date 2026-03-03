package io.opencti.common.exception;

import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/errors.js
 * 锁超时异常类
 */
public class LockTimeoutException extends OpenCTIException {

    public LockTimeoutException(String message) {
        super(ErrorCode.LOCK_TIMEOUT, message);
    }

    public LockTimeoutException(String message, Map<String, Object> metadata) {
        super(ErrorCode.LOCK_TIMEOUT, message, metadata);
    }

    public LockTimeoutException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public LockTimeoutException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public static LockTimeoutException lockError(String resourceId) {
        return new LockTimeoutException(
                ErrorCode.LOCK_ERROR,
                "Lock error for resource: " + resourceId
        );
    }

    public static LockTimeoutException lockTimeout(String resourceId, long timeoutMs) {
        return new LockTimeoutException(
                ErrorCode.LOCK_TIMEOUT,
                "Lock timeout (" + timeoutMs + "ms) for resource: " + resourceId
        );
    }
}
