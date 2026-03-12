package io.opencti.types.stix.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * STIX External Reference type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-smo.d.ts
 * Original type:
 * export interface StixInternalExternalReference {
 *   source_name: string;
 *   description: string;
 *   url: string;
 *   hashes: object;
 *   external_id: string;
 * }
 */
public record StixExternalReference(
        @JsonProperty("source_name") String sourceName,
        String description,
        String url,
        Map<String, String> hashes,
        @JsonProperty("external_id") String externalId
) {
    public static StixExternalReference of(String sourceName) {
        return new StixExternalReference(sourceName, null, null, Map.of(), null);
    }

    public static StixExternalReference of(String sourceName, String url) {
        return new StixExternalReference(sourceName, null, url, Map.of(), null);
    }

    public static StixExternalReference of(String sourceName, String url, String externalId) {
        return new StixExternalReference(sourceName, null, url, Map.of(), externalId);
    }
}

