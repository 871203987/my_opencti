package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * STIX Identity Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixIdentityExtension extends StixOpenctiExtension {
 *   firstname: string;
 *   lastname: string;
 *   organization_type: string;
 *   reliability: string;
 *   score: number;
 * }
 */
public record StixIdentityExtension(
        String firstname,
        String lastname,
        @JsonProperty("organization_type") String organizationType,
        String reliability,
        int score
) {
}
