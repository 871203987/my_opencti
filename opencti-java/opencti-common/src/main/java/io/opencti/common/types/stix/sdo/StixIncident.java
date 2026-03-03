package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;
import io.opencti.common.types.stix.StixDomainObject;
import io.opencti.common.types.stix.StixOpenctiExtensionSDO;

import java.util.List;

/**
 * STIX Incident type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * interface StixIncident extends StixDomainObject {
 *   name: string;
 *   incident_type: string;
 *   description: string;
 *   first_seen: StixDate;
 *   last_seen: StixDate;
 *   objective: string;
 *   aliases: Array<string>;
 *   source: string;
 *   severity: string;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpenctiExtensionSDO;
 *   };
 * }
 */
public class StixIncident extends StixDomainObject {
    public static final String TYPE = "incident";

    private String name;
    @JsonProperty("incident_type")
    private String incidentType;
    private String description;
    @JsonProperty("first_seen")
    private StixDate firstSeen;
    @JsonProperty("last_seen")
    private StixDate lastSeen;
    private String objective;
    private List<String> aliases;
    private String source;
    private String severity;
    private StixOpenctiExtensionSDO openctiExtension;

    public StixIncident() {
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

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public StixOpenctiExtensionSDO getOpenctiExtension() {
        return openctiExtension;
    }

    public void setOpenctiExtension(StixOpenctiExtensionSDO openctiExtension) {
        this.openctiExtension = openctiExtension;
    }
}
