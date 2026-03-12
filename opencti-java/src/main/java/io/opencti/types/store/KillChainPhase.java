package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Kill Chain Phase type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 */
public class KillChainPhase {
    private String killChainName;
    private String phaseName;

    @JsonProperty("kill_chain_name")
    public String getKillChainName() {
        return killChainName;
    }

    public void setKillChainName(String killChainName) {
        this.killChainName = killChainName;
    }

    @JsonProperty("phase_name")
    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }
}

