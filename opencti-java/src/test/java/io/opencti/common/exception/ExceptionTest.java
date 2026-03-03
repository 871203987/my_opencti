package io.opencti.common.exception;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 重写自: opencti-graphql/tests/01-unit/config/test_errors.js
 * 异常类测试
 */
class ExceptionTest {

    @Test
    void testOpenCTIExceptionBasic() {
        OpenCTIException exception = new OpenCTIException(ErrorCode.UNKNOWN_ERROR, "Test error");
        assertEquals(ErrorCode.UNKNOWN_ERROR, exception.getErrorCode());
        assertEquals("Test error", exception.getMessage());
        assertEquals(500, exception.getHttpStatus());
    }

    @Test
    void testOpenCTIExceptionWithCause() {
        Exception cause = new RuntimeException("Cause");
        OpenCTIException exception = new OpenCTIException(ErrorCode.DATABASE_ERROR, "Database error", cause);
        assertEquals(ErrorCode.DATABASE_ERROR, exception.getErrorCode());
        assertEquals("Database error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testOpenCTIExceptionWithMetadata() {
        Map<String, Object> metadata = Map.of("key", "value", "count", 42);
        OpenCTIException exception = new OpenCTIException(ErrorCode.VALIDATION_ERROR, "Validation failed", metadata);
        assertTrue(exception.hasMetadata());
        assertEquals("value", exception.getMetadata().get("key"));
        assertEquals(42, exception.getMetadata().get("count"));
    }

    @Test
    void testAuthenticationException() {
        AuthenticationException exception = AuthenticationException.authRequired();
        assertEquals(ErrorCode.AUTH_REQUIRED, exception.getErrorCode());
        assertEquals(401, exception.getHttpStatus());
    }

    @Test
    void testAuthenticationExceptionForbidden() {
        AuthenticationException exception = AuthenticationException.forbidden();
        assertEquals(ErrorCode.FORBIDDEN_ACCESS, exception.getErrorCode());
        assertEquals(403, exception.getHttpStatus());
    }

    @Test
    void testDatabaseException() {
        DatabaseException exception = DatabaseException.connectionFailed("http://localhost:9200");
        assertEquals(ErrorCode.DATABASE_ERROR, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("localhost:9200"));
    }

    @Test
    void testDatabaseExceptionTimeout() {
        DatabaseException exception = DatabaseException.timeout("search");
        assertEquals(ErrorCode.DATABASE_TIMEOUT, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("search"));
    }

    @Test
    void testFunctionalException() {
        FunctionalException exception = FunctionalException.unsupportedOperation("delete");
        assertEquals(ErrorCode.UNSUPPORTED_OPERATION, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("delete"));
    }

    @Test
    void testValidationException() {
        ValidationException exception = ValidationException.requiredField("name");
        assertEquals(ErrorCode.INVALID_INPUT, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("name"));
    }

    @Test
    void testValidationExceptionDuplicate() {
        ValidationException exception = ValidationException.duplicateEntry("email", "test@example.com");
        assertEquals(ErrorCode.DUPLICATE_ENTRY, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("email"));
        assertTrue(exception.getMessage().contains("test@example.com"));
    }

    @Test
    void testConfigurationException() {
        ConfigurationException exception = ConfigurationException.missingConfiguration("database.url");
        assertEquals(ErrorCode.MISSING_CONFIGURATION, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("database.url"));
    }

    @Test
    void testResourceNotFoundException() {
        ResourceNotFoundException exception = ResourceNotFoundException.notFound("Malware", "malware-123");
        assertEquals(ErrorCode.RESOURCE_NOT_FOUND, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("Malware"));
        assertTrue(exception.getMessage().contains("malware-123"));
    }

    @Test
    void testLockTimeoutException() {
        LockTimeoutException exception = LockTimeoutException.lockTimeout("report-456", 5000);
        assertEquals(ErrorCode.LOCK_TIMEOUT, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("report-456"));
        assertTrue(exception.getMessage().contains("5000"));
    }

    @Test
    void testErrorCodeFromCode() {
        assertEquals(ErrorCode.AUTH_FAILURE, ErrorCode.fromCode("AUTH_FAILURE"));
        assertEquals(ErrorCode.DATABASE_ERROR, ErrorCode.fromCode("DATABASE_ERROR"));
        assertEquals(ErrorCode.UNKNOWN_ERROR, ErrorCode.fromCode("NON_EXISTENT_CODE"));
    }

    @Test
    void testErrorCodeProperties() {
        ErrorCode code = ErrorCode.AUTH_FAILURE;
        assertEquals("AUTH_FAILURE", code.getCode());
        assertEquals("Authentication failed", code.getMessage());
        assertEquals(401, code.getHttpStatus());
    }
}
