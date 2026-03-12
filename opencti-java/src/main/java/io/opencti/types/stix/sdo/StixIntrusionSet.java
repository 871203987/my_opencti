package io.opencti.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixDate;
import io.opencti.types.stix.common.StixDomainObject;

import java.util.List;

/**
 * STIX Intrusion Set type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixIntrusionSet extends StixDomainObject {
 *   name: string;
 *   description: string;
 *   aliases: Array<string>;
 *   first_seen: StixDate;
 *   last_seen: StixDate;
 *   goals: Array<string>;
 *   resource_level: string;
 *   primary_motivation: string;
 *   secondary_motivations: Array<string>;
 * }
 */
public class StixIntrusionSet extends StixDomainObject {
    public static final String TYPE = "intrusion-set";

    private String name;
    private String description;
    private List<String> aliases;
    @JsonProperty("first_seen")
    private StixDate firstSeen;
    @JsonProperty("last_seen")
    private StixDate lastSeen;
    private List<String> goals;
    @JsonProperty("resource_level")
    private String resourceLevel;
    @JsonProperty("primary_motivation")
    private String primaryMotivation;
    @JsonProperty("secondary_motivations")
    private List<String> secondaryMotivations;

    public StixIntrusionSet() {
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

    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    public String getResourceLevel() {
        return resourceLevel;
    }

    public void setResourceLevel(String resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public String getPrimaryMotivation() {
        return primaryMotivation;
    }

    public void setPrimaryMotivation(String primaryMotivation) {
        this.primaryMotivation = primaryMotivation;
    }

    public List<String> getSecondaryMotivations() {
        return secondaryMotivations;
    }

    public void setSecondaryMotivations(List<String> secondaryMotivations) {
        this.secondaryMotivations = secondaryMotivations;
    }
}

