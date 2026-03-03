package io.opencti.common.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 重写自: opencti-graphql/src/config/conf.js
 * 应用配置属性
 */
public record AppProperties(
    @Min(1) int port,
    String basePath,
    @NotBlank String baseUrl,
    String title,
    String description,
    String platformTitle,
    String platformFavicon,
    String platformEnterpriseEditionLicense,
    boolean platformDemo,
    boolean platformEnableTelemetry,
    String platformVersion,
    String adminEmail,
    String adminPassword,
    String adminToken,
    boolean adminOnboarding,
    String defaultLanguage,
    boolean defaultMarking,
    String defaultMarkingValue,
    boolean defaultHiddenTypes,
    boolean enforceReferences,
    boolean enforceConsensus,
    String mapTileServerDark,
    String mapTileServerLight,
    boolean referenceAttachment,
    boolean platformTraces,
    String platformIdentifier,
    String playgroundUrl,
    boolean enabledDevFeatures,
    String xtmHubUrl,
    boolean xtmHubEnableRegistration,
    String xtmHubRegistrationToken,
    boolean xtmHubEnableSync,
    boolean xtmHubEnableShare,
    boolean xtmHubEnableAiInsights,
    String xtmHubAiInsightsModel,
    String xtmHubAiInsightsProvider,
    @Valid AppLogsProperties appLogs,
    @Valid HealthProperties health,
    @Valid SessionProperties session,
    @Valid CookieProperties cookie,
    @Valid SyncProperties sync,
    @Valid TaskProperties task,
    @Valid ExpirationProperties expiration,
    @Valid ActivityProperties activity,
    @Valid AiProperties ai
) {
    public record AppLogsProperties(
        String logsLevel,
        boolean logsFiles,
        boolean logsConsole,
        @Min(1) int logsMaxFiles,
        String logsDirectory,
        List<String> logsRedactedInputs,
        boolean logsExtendedErrors
    ) {}

    public record HealthProperties(
        boolean healthAccessRestricted,
        String healthAccessKey
    ) {}

    public record SessionProperties(
        @NotBlank String sessionSecret,
        @Min(1) int sessionTimeout,
        int sessionCookieSecure,
        String sessionSameSite,
        int sessionExpiration
    ) {}

    public record CookieProperties(
        String cookiePrefix,
        boolean cookieSecure,
        boolean cookieHttpOnly,
        String cookieSameSite,
        String cookieDomain,
        int cookieMaxAge
    ) {}

    public record SyncProperties(
        @Min(1) int syncBufferThreshold,
        @Min(1) int syncBufferTime,
        @Min(1) int syncMaxConcurrent
    ) {}

    public record TaskProperties(
        @Min(1) int taskScheduler,
        @Min(1) int taskSchedulerBatchSize,
        @Min(1) int taskSchedulerMaxBuffer
    ) {}

    public record ExpirationProperties(
        @Min(1) long expirationScheduler,
        @Min(1) long expirationTimeout
    ) {}

    public record ActivityProperties(
        boolean activityEnable,
        @Min(1) int activityMaxConcurrent,
        @Min(1) int activityBufferSize,
        @Min(1) int activityBufferTime
    ) {}

    public record AiProperties(
        boolean aiEnabled,
        String aiType,
        String aiEndpoint,
        String aiApiKey,
        String aiModel,
        String aiModelContextWindow,
        String aiProvider,
        boolean aiHasTokenizer,
        Map<String, Object> aiOptions
    ) {}
}
