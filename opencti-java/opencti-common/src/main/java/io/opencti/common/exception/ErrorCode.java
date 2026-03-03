package io.opencti.common.exception;

import lombok.Getter;

/**
 * 重写自: opencti-graphql/src/config/errors.js
 * 错误码枚举定义
 */
@Getter
public enum ErrorCode {

    // Authentication Errors (401)
    AUTH_FAILURE("AUTH_FAILURE", "Authentication failed", 401),
    AUTH_REQUIRED("AUTH_REQUIRED", "Authentication required", 401),
    OTP_REQUIRED("OTP_REQUIRED", "Two-factor authentication required", 401),
    SESSION_EXPIRED("SESSION_EXPIRED", "Session has expired", 401),

    // Authorization Errors (403)
    FORBIDDEN_ACCESS("FORBIDDEN_ACCESS", "Access denied", 403),
    CAPABILITY_NOT_FOUND("CAPABILITY_NOT_FOUND", "Capability not found", 403),

    // Resource Errors (404)
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Resource not found", 404),
    MISSING_REFERENCE_ERROR("MISSING_REFERENCE_ERROR", "Missing reference error", 404),

    // Validation Errors (400)
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation error", 400),
    INVALID_INPUT("INVALID_INPUT", "Invalid input", 400),
    INVALID_PATH("INVALID_PATH", "Invalid path", 400),
    INVALID_DATA("INVALID_DATA", "Invalid data", 400),
    DUPLICATE_ENTRY("DUPLICATE_ENTRY", "Duplicate entry", 400),

    // Functional Errors (500)
    FUNCTIONAL_ERROR("FUNCTIONAL_ERROR", "Functional error", 500),
    UNSUPPORTED_OPERATION("UNSUPPORTED_OPERATION", "Unsupported operation", 500),
    ALREADY_DELETED("ALREADY_DELETED", "Element already deleted", 500),
    MERGE_ERROR("MERGE_ERROR", "Merge operation failed", 500),

    // Database Errors (500)
    DATABASE_ERROR("DATABASE_ERROR", "Database error", 500),
    DATABASE_INDEX_ERROR("DATABASE_INDEX_ERROR", "Database index error", 500),
    DATABASE_TIMEOUT("DATABASE_TIMEOUT", "Database operation timeout", 500),

    // Lock Errors (500)
    LOCK_ERROR("LOCK_ERROR", "Lock error", 500),
    LOCK_TIMEOUT("LOCK_TIMEOUT", "Lock timeout", 500),

    // Configuration Errors (500)
    CONFIGURATION_ERROR("CONFIGURATION_ERROR", "Configuration error", 500),
    MISSING_CONFIGURATION("MISSING_CONFIGURATION", "Missing configuration", 500),

    // External Service Errors (500)
    EXTERNAL_SERVICE_ERROR("EXTERNAL_SERVICE_ERROR", "External service error", 500),
    PYTHON_ERROR("PYTHON_ERROR", "Python execution error", 500),

    // Rate Limiting (429)
    RATE_LIMIT_EXCEEDED("RATE_LIMIT_EXCEEDED", "Rate limit exceeded", 429),

    // Unknown Error (500)
    UNKNOWN_ERROR("UNKNOWN_ERROR", "An unknown error has occurred", 500);

    private final String code;
    private final String message;
    private final int httpStatus;

    ErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return UNKNOWN_ERROR;
    }
}
