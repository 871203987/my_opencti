package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;
import io.opencti.common.types.stix.StixDomainObject;
import io.opencti.common.types.stix.StixKillChainPhase;

import java.util.List;

/**
 * STIX Infrastructure type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixInfrastructure extends StixDomainObject {
 *   name: string;
 *   description: string;
 *   infrastructure_types: Array<string>;
 *   aliases: Array<string>;
 *   kill_chain_phases: Array<StixKillChainPhase>;
 *   first_seen: StixDate;
 *   last_seen: StixDate;
 * }
 */
public class StixInfrastructure extends StixDomainObject {
    public static final String TYPE = "infrastructure";

    private String name;
    private String description;
    @JsonProperty("infrastructure_types")
    private List<String> infrastructureTypes;
    private List<String> aliases;
    @JsonProperty("kill_chain_phases")
    private List<StixKillChainPhase> killChainPhases;
    @JsonProperty("first_seen")
    private StixDate firstSeen;
    @JsonProperty("last_seen")
    private StixDate lastSeen;

    public StixInfrastructure() {
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

    public List<String> getInfrastructureTypes() {
        return infrastructureTypes;
    }

    public void setInfrastructureTypes(List<String> infrastructureTypes) {
        this.infrastructureTypes = infrastructureTypes;
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

    public StixDate getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(StixDate firstSeen) {
        this.firstSeen = firstSeen;
    }

    public StixDate getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(StixDate lastSeen) {
        this.lastSeen = lastSeen;
    }
}
