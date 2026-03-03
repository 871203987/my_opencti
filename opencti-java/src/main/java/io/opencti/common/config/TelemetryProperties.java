package io.opencti.common.config;

import jakarta.validation.Valid;

/**
 * 重写自: opencti-graphql/src/config/tracing.ts
 * 遥测配置属性（OpenTelemetry）
 */
public record TelemetryProperties(
    boolean enabled,
    String exporterOtlp,
    String exporterZipkin,
    String serviceName,
    String serviceVersion,
    double samplingRate,
    boolean propagateContext,
    @Valid MetricsProperties metrics,
    @Valid TracingProperties tracing
) {
    public record MetricsProperties(
        boolean enabled,
        String exporterOtlp,
        int exporterPrometheus,
        boolean exportSystemMetrics
    ) {}

    public record TracingProperties(
        boolean enabled,
        String exporterOtlp,
        String exporterZipkin,
        double samplingRate
    ) {}
}
