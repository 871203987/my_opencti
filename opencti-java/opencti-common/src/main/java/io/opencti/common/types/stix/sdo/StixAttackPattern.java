package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.*;

import java.util.List;

/**
 * STIX Attack Pattern type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixAttackPattern extends StixDomainObject {
 *   name: string;
 *   description: string;
 *   aliases: Array<string>;
 *   kill_chain_phases: Array<StixKillChainPhase>;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpenctiExtension;
 *     [STIX_EXT_MITRE]: StixMitreExtension;
 *   };
 * }
 */
public class StixAttackPattern extends StixDomainObject {
    public static final String TYPE = "attack-pattern";

    private String name;
    private String description;
    private List<String> aliases;
    @JsonProperty("kill_chain_phases")
    private List<StixKillChainPhase> killChainPhases;
    private StixOpenctiExtension openctiExtension;
    private StixMitreExtension mitreExtension;

    public StixAttackPattern() {
        this.type = TYPE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public List<StixKillChainPhase> getKillChainPhases() {
        return killChainPhases;
    }

    public void setKillChainPhases(List<StixKillChainPhase> killChainPhases) {
        this.killChainPhases = killChainPhases;
    }

    public StixOpenctiExtension getOpenctiExtension() {
        return openctiExtension;
    }

    public void setOpenctiExtension(StixOpenctiExtension openctiExtension) {
        this.openctiExtension = openctiExtension;
    }

    public StixMitreExtension getMitreExtension() {
        return mitreExtension;
    }

    public void setMitreExtension(StixMitreExtension mitreExtension) {
        this.mitreExtension = mitreExtension;
    }
}
