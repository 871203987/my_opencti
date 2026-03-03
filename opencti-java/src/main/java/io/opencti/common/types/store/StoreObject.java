package io.opencti.common.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;
import io.opencti.common.types.stix.StixId;

import java.util.List;
import java.util.Map;

/**
 * Store Object base type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Represents the internal storage format for STIX objects in OpenCTI.
 */
public abstract class StoreObject {
    protected String id;
    protected String internalId;
    protected StixId standardId;
    protected List<String> stixIds;
    protected String type;
    protected String entityType;
    protected Map<String, Object> rawAttributes;
    protected Map<String, List<Object>> rawRelationships;
    protected StixDate createdAt;
    protected StixDate updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("internal_id")
    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    @JsonProperty("standard_id")
    public StixId getStandardId() {
        return standardId;
    }

    public void setStandardId(StixId standardId) {
        this.standardId = standardId;
    }

    @JsonProperty("stix_ids")
    public List<String> getStixIds() {
        return stixIds;
    }

    public void setStixIds(List<String> stixIds) {
        this.stixIds = stixIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("entity_type")
    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @JsonProperty("raw_attributes")
    public Map<String, Object> getRawAttributes() {
        return rawAttributes;
    }

    public void setRawAttributes(Map<String, Object> rawAttributes) {
        this.rawAttributes = rawAttributes;
    }

    @JsonProperty("raw_relationships")
    public Map<String, List<Object>> getRawRelationships() {
        return rawRelationships;
    }

    public void setRawRelationships(Map<String, List<Object>> rawRelationships) {
        this.rawRelationships = rawRelationships;
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
