package io.opencti.types.stix.smo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixObject;
import io.opencti.types.stix.common.StixOpenctiExtensionSDO;

/**
 * STIX Kill Chain Phase Object type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-smo.d.ts
 * Original type:
 * export interface StixKillChainPhase extends StixInternalKillChainPhase, StixObject {
 *   order: number;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpenctiExtensionSDO;
 *   };
 * }
 */
public class StixKillChainPhaseObject extends StixObject {
    public static final String TYPE = "kill-chain-phase";

    @JsonProperty("kill_chain_name")
    private String killChainName;
    @JsonProperty("phase_name")
    private String phaseName;
    private Integer order;
    private StixOpenctiExtensionSDO openctiExtension;

    public StixKillChainPhaseObject() {
        this.type = TYPE;
    }

    public String getKillChainName() {
        return killChainName;
    }

    public void setKillChainName(String killChainName) {
        this.killChainName = killChainName;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public StixOpenctiExtensionSDO getOpenctiExtension() {
        return openctiExtension;
    }

    public void setOpenctiExtension(StixOpenctiExtensionSDO openctiExtension) {
        this.openctiExtension = openctiExtension;
    }
}

