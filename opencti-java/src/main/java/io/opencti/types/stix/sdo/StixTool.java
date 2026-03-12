package io.opencti.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixDomainObject;
import io.opencti.types.stix.common.StixKillChainPhase;

import java.util.List;

/**
 * STIX Tool type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixTool extends StixDomainObject {
 *   name: string;
 *   description: string;
 *   tool_types: Array<string>;
 *   aliases: Array<string>;
 *   kill_chain_phases: Array<StixKillChainPhase>;
 *   tool_version: string;
 * }
 */
public class StixTool extends StixDomainObject {
    public static final String TYPE = "tool";

    private String name;
    private String description;
    @JsonProperty("tool_types")
    private List<String> toolTypes;
    private List<String> aliases;
    @JsonProperty("kill_chain_phases")
    private List<StixKillChainPhase> killChainPhases;
    @JsonProperty("tool_version")
    private String toolVersion;

    public StixTool() {
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

    public List<String> getToolTypes() {
        return toolTypes;
    }

    public void setToolTypes(List<String> toolTypes) {
        this.toolTypes = toolTypes;
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

    public String getToolVersion() {
        return toolVersion;
    }

    public void setToolVersion(String toolVersion) {
        this.toolVersion = toolVersion;
    }
}

