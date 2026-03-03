package io.opencti.common.types.stix;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * STIX Kill Chain Phase type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * export type StixKillChainPhase = {
 *   kill_chain_name: string;
 *   phase_name: string;
 * };
 */
public record StixKillChainPhase(
        @JsonProperty("kill_chain_name") String killChainName,
        @JsonProperty("phase_name") String phaseName
) {
    public static StixKillChainPhase of(String killChainName, String phaseName) {
        return new StixKillChainPhase(killChainName, phaseName);
    }
}
