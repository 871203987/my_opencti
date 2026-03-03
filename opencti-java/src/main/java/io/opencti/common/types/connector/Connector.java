package io.opencti.common.types.connector;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;

import java.util.List;
import java.util.Map;

/**
 * Connector type.
 * Original file: opencti-platform/opencti-graphql/src/types/connector.d.ts
 * Represents a connector in the OpenCTI platform.
 */
public class Connector {
    public static final String TYPE = "Connector";

    private String id;
    private String name;
    private String description;
    private String connectorType;
    private Boolean active;
    private Boolean auto;
    private String state;
    private List<String> scope;
    private StixDate lastRun;
    private StixDate nextRun;
    private Integer duration;
    private String user;
    private Map<String, Object> config;
    private StixDate createdAt;
    private StixDate updatedAt;
    private List<String> works;
    private String connectorState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("connector_type")
    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    @JsonProperty("last_run")
    public StixDate getLastRun() {
        return lastRun;
    }

    public void setLastRun(StixDate lastRun) {
        this.lastRun = lastRun;
    }

    @JsonProperty("next_run")
    public StixDate getNextRun() {
        return nextRun;
    }

    public void setNextRun(StixDate nextRun) {
        this.nextRun = nextRun;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    @JsonProperty("created_at")
    public StixDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(StixDate createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public StixDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(StixDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getWorks() {
        return works;
    }

    public void setWorks(List<String> works) {
        this.works = works;
    }

    @JsonProperty("connector_state")
    public String getConnectorState() {
        return connectorState;
    }

    public void setConnectorState(String connectorState) {
        this.connectorState = connectorState;
    }

    public static final String TYPE_EXTERNAL_IMPORT = "EXTERNAL_IMPORT";
    public static final String TYPE_INTERNAL_IMPORT_FILE = "INTERNAL_IMPORT_FILE";
    public static final String TYPE_INTERNAL_ENRICHMENT = "INTERNAL_ENRICHMENT";
    public static final String TYPE_INTERNAL_EXPORT_FILE = "INTERNAL_EXPORT_FILE";
    public static final String TYPE_STREAM = "STREAM";
}
