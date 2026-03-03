package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;
import io.opencti.common.types.stix.StixDomainObject;

import java.util.List;

/**
 * STIX Threat Actor type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixThreatActor extends StixDomainObject {
 *   name: string;
 *   description: string;
 *   threat_actor_types: Array<string>;
 *   aliases: Array<string>;
 *   first_seen: StixDate;
 *   last_seen: StixDate;
 *   roles: Array<string>;
 *   goals: Array<string>;
 *   sophistication: string;
 *   resource_level: string;
 *   primary_motivation: string;
 *   secondary_motivations: Array<string>;
 *   personal_motivations: Array<string>;
 * }
 */
public class StixThreatActor extends StixDomainObject {
    public static final String TYPE = "threat-actor";

    private String name;
    private String description;
    @JsonProperty("threat_actor_types")
    private List<String> threatActorTypes;
    private List<String> aliases;
    @JsonProperty("first_seen")
    private StixDate firstSeen;
    @JsonProperty("last_seen")
    private StixDate lastSeen;
    private List<String> roles;
    private List<String> goals;
    private String sophistication;
    @JsonProperty("resource_level")
    private String resourceLevel;
    @JsonProperty("primary_motivation")
    private String primaryMotivation;
    @JsonProperty("secondary_motivations")
    private List<String> secondaryMotivations;
    @JsonProperty("personal_motivations")
    private List<String> personalMotivations;

    public StixThreatActor() {
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

    public List<String> getThreatActorTypes() {
        return threatActorTypes;
    }

    public void setThreatActorTypes(List<String> threatActorTypes) {
        this.threatActorTypes = threatActorTypes;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getGoals() {
        return goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    public String getSophistication() {
        return sophistication;
    }

    public void setSophistication(String sophistication) {
        this.sophistication = sophistication;
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

    public List<String> getPersonalMotivations() {
        return personalMotivations;
    }

    public void setPersonalMotivations(List<String> personalMotivations) {
        this.personalMotivations = personalMotivations;
    }
}
