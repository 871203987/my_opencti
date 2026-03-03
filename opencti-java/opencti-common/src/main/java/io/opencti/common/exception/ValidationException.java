package io.opencti.common.exception;

import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/errors.js
 * 验证异常类
 */
public class ValidationException extends OpenCTIException {

    public ValidationException(String message) {
        super(ErrorCode.VALIDATION_ERROR, message);
    }

    public ValidationException(String message, Map<String, Object> metadata) {
        super(ErrorCode.VALIDATION_ERROR, message, metadata);
    }

    public ValidationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ValidationException(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        super(errorCode, message, metadata);
    }

    public static ValidationException invalidInput(String field, String reason) {
        return new ValidationException(
                ErrorCode.INVALID_INPUT,
                "Invalid input for field '" + field + "': " + reason
        );
    }

    public static ValidationException invalidPath(String path) {
        return new ValidationException(
                ErrorCode.INVALID_PATH,
                "Invalid path: " + path
        );
    }

    public static ValidationException invalidData(String reason) {
        return new ValidationException(
                ErrorCode.INVALID_DATA,
                "Invalid data: " + reason
        );
    }

    public static ValidationException duplicateEntry(String field, String value) {
        return new ValidationException(
                ErrorCode.DUPLICATE_ENTRY,
                "Duplicate entry for field '" + field + "' with value: " + value
        );
    }

    public static ValidationException requiredField(String field) {
        return new ValidationException(
                ErrorCode.INVALID_INPUT,
                "Required field '" + field + "' is missing"
        );
    }
}
