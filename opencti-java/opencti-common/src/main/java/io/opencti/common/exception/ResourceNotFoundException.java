package io.opencti.common.exception;

import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/errors.js
 * 资源未找到异常类
 */
public class ResourceNotFoundException extends OpenCTIException {

    public ResourceNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }

    public ResourceNotFoundException(String message, Map<String, Object> metadata) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message, metadata);
    }

    public ResourceNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ResourceNotFoundException(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        super(errorCode, message, metadata);
    }

    public static ResourceNotFoundException notFound(String type, String id) {
        return new ResourceNotFoundException(
                ErrorCode.RESOURCE_NOT_FOUND,
                type + " not found: " + id
        );
    }

    public static ResourceNotFoundException notFound(String type, String field, String value) {
        return new ResourceNotFoundException(
                ErrorCode.RESOURCE_NOT_FOUND,
                type + " not found with " + field + ": " + value
        );
    }

    public static ResourceNotFoundException missingReference(String referenceId) {
        return new ResourceNotFoundException(
                ErrorCode.MISSING_REFERENCE_ERROR,
                "Missing reference: " + referenceId
        );
    }
}
