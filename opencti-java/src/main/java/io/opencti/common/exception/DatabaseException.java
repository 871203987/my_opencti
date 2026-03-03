package io.opencti.common.exception;

import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/errors.js
 * 数据库异常类
 */
public class DatabaseException extends OpenCTIException {

    public DatabaseException(String message) {
        super(ErrorCode.DATABASE_ERROR, message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(ErrorCode.DATABASE_ERROR, message, cause);
    }

    public DatabaseException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public DatabaseException(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        super(errorCode, message, metadata);
    }

    public DatabaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public static DatabaseException indexError(String index, String reason) {
        return new DatabaseException(
                ErrorCode.DATABASE_INDEX_ERROR,
                "Index error on '" + index + "': " + reason
        );
    }

    public static DatabaseException timeout(String operation) {
        return new DatabaseException(
                ErrorCode.DATABASE_TIMEOUT,
                "Database operation timeout: " + operation
        );
    }

    public static DatabaseException connectionFailed(String url) {
        return new DatabaseException(
                ErrorCode.DATABASE_ERROR,
                "Failed to connect to database: " + url
        );
    }
}
