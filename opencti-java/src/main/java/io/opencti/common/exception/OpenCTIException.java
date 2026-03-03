package io.opencti.common.exception;

import lombok.Getter;

import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/errors.js
 * OpenCTI基础异常类，所有自定义异常的父类
 */
@Getter
public class OpenCTIException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Map<String, Object> metadata;
    private final Integer httpStatus;

    public OpenCTIException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.metadata = null;
        this.httpStatus = errorCode.getHttpStatus();
    }

    public OpenCTIException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.metadata = null;
        this.httpStatus = errorCode.getHttpStatus();
    }

    public OpenCTIException(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        super(message);
        this.errorCode = errorCode;
        this.metadata = metadata;
        this.httpStatus = errorCode.getHttpStatus();
    }

    public OpenCTIException(ErrorCode errorCode, String message, Map<String, Object> metadata, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.metadata = metadata;
        this.httpStatus = errorCode.getHttpStatus();
    }

    public boolean hasMetadata() {
        return metadata != null && !metadata.isEmpty();
    }
}
