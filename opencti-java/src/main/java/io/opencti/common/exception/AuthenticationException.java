package io.opencti.common.exception;

import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/errors.js
 * 认证异常类
 */
public class AuthenticationException extends OpenCTIException {

    public AuthenticationException(String message) {
        super(ErrorCode.AUTH_FAILURE, message);
    }

    public AuthenticationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AuthenticationException(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        super(errorCode, message, metadata);
    }

    public AuthenticationException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public static AuthenticationException authRequired() {
        return new AuthenticationException(ErrorCode.AUTH_REQUIRED, ErrorCode.AUTH_REQUIRED.getMessage());
    }

    public static AuthenticationException otpRequired() {
        return new AuthenticationException(ErrorCode.OTP_REQUIRED, ErrorCode.OTP_REQUIRED.getMessage());
    }

    public static AuthenticationException sessionExpired() {
        return new AuthenticationException(ErrorCode.SESSION_EXPIRED, ErrorCode.SESSION_EXPIRED.getMessage());
    }

    public static AuthenticationException forbidden() {
        return new AuthenticationException(ErrorCode.FORBIDDEN_ACCESS, ErrorCode.FORBIDDEN_ACCESS.getMessage());
    }

    public static AuthenticationException capabilityNotFound(String capability) {
        return new AuthenticationException(
                ErrorCode.CAPABILITY_NOT_FOUND,
                "Capability not found: " + capability
        );
    }
}
