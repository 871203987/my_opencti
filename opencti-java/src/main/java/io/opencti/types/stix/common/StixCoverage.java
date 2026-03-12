package io.opencti.types.stix.common;

/**
 * STIX Coverage type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * export type StixCoverage = {
 *   name: string;
 *   score: number;
 * };
 */
public record StixCoverage(String name, double score) {
    public static StixCoverage of(String name, double score) {
        return new StixCoverage(name, score);
    }
}

