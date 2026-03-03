package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;
import io.opencti.common.types.stix.StixDomainObject;

import java.util.List;

/**
 * STIX Campaign type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixCampaign extends StixDomainObject {
 *   name: string;
 *   description: string;
 *   aliases: Array<string>;
 *   first_seen: StixDate;
 *   last_seen: StixDate;
 *   objective: string;
 * }
 */
public class StixCampaign extends StixDomainObject {
    public static final String TYPE = "campaign";

    private String name;
    private String description;
    private List<String> aliases;
    @JsonProperty("first_seen")
    private StixDate firstSeen;
    @JsonProperty("last_seen")
    private StixDate lastSeen;
    private String objective;

    public StixCampaign() {
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

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }
}
