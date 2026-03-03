package io.opencti.common.types.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;

import java.util.List;
import java.util.Map;

/**
 * Group type.
 * Original file: opencti-platform/opencti-graphql/src/types/group.d.ts
 * Represents a group in the OpenCTI platform.
 */
public class Group {
    public static final String TYPE = "Group";

    private String id;
    private String name;
    private String description;
    private List<String> defaultMarkingDefinitions;
    private Map<String, Object> groupConfidenceLevel;
    private Boolean autoNewMarking;
    private List<String> members;
    private List<String> roles;
    private List<String> capabilities;
    private StixDate createdAt;
    private StixDate updatedAt;

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

    @JsonProperty("default_marking_definitions")
    public List<String> getDefaultMarkingDefinitions() {
        return defaultMarkingDefinitions;
    }

    public void setDefaultMarkingDefinitions(List<String> defaultMarkingDefinitions) {
        this.defaultMarkingDefinitions = defaultMarkingDefinitions;
    }

    @JsonProperty("group_confidence_level")
    public Map<String, Object> getGroupConfidenceLevel() {
        return groupConfidenceLevel;
    }

    public void setGroupConfidenceLevel(Map<String, Object> groupConfidenceLevel) {
        this.groupConfidenceLevel = groupConfidenceLevel;
    }

    @JsonProperty("auto_new_marking")
    public Boolean getAutoNewMarking() {
        return autoNewMarking;
    }

    public void setAutoNewMarking(Boolean autoNewMarking) {
        this.autoNewMarking = autoNewMarking;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
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
}
