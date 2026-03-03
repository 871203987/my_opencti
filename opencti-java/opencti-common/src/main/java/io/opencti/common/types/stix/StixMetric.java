package io.opencti.common.types.stix;

/**
 * STIX Metric type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 */
public record StixMetric(String name, double value) {
    public static StixMetric of(String name, double value) {
        return new StixMetric(name, value);
    }
}
