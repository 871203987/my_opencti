package io.opencti.common.exception;

import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/errors.js
 * 配置异常类
 */
public class ConfigurationException extends OpenCTIException {

    public ConfigurationException(String message) {
        super(ErrorCode.CONFIGURATION_ERROR, message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(ErrorCode.CONFIGURATION_ERROR, message, cause);
    }

    public ConfigurationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ConfigurationException(ErrorCode errorCode, String message, Map<String, Object> metadata) {
        super(errorCode, message, metadata);
    }

    public static ConfigurationException missingConfiguration(String configKey) {
        return new ConfigurationException(
                ErrorCode.MISSING_CONFIGURATION,
                "Missing required configuration: " + configKey
        );
    }

    public static ConfigurationException invalidConfiguration(String configKey, String reason) {
        return new ConfigurationException(
                ErrorCode.CONFIGURATION_ERROR,
                "Invalid configuration for '" + configKey + "': " + reason
        );
    }
}
