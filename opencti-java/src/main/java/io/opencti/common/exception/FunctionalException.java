package io.opencti.common.exception;

import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/errors.js
 * 功能异常类
 */
public class FunctionalException extends OpenCTIException {

    public FunctionalException(String message) {
        super(ErrorCode.FUNCTIONAL_ERROR, message);
    }

    public FunctionalException(String message, Map<String, Object> metadata) {
        super(ErrorCode.FUNCTIONAL_ERROR, message, metadata);
    }

    public FunctionalException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public FunctionalException(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        super(errorCode, message, metadata);
    }

    public FunctionalException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public static FunctionalException unsupportedOperation(String operation) {
        return new FunctionalException(
                ErrorCode.UNSUPPORTED_OPERATION,
                "Unsupported operation: " + operation
        );
    }

    public static FunctionalException alreadyDeleted(String id) {
        return new FunctionalException(
                ErrorCode.ALREADY_DELETED,
                "Element already deleted: " + id
        );
    }

    public static FunctionalException mergeError(String sourceId, String targetId) {
        return new FunctionalException(
                ErrorCode.MERGE_ERROR,
                "Failed to merge " + sourceId + " into " + targetId
        );
    }
}
