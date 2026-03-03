package io.opencti.common.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;
import io.opencti.common.types.stix.StixId;

import java.util.List;
import java.util.Map;

/**
 * Store Entity type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Represents an entity stored in the OpenCTI database.
 */
public class StoreEntity extends StoreObject {
    protected String name;
    protected String description;
    protected List<String> aliases;
    protected StixId createdByRef;
    protected List<StixId> objectMarkingRefs;
    protected List<String> grantedRefs;
    protected List<String> labels;
    protected Integer confidence;
    protected Boolean revoked;
    protected StixDate created;
    protected StixDate modified;
    protected Map<String, Object> extensions;
    protected Boolean isInferred;
    protected List<String> creatorIds;

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

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @JsonProperty("created_by_ref")
    public StixId getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(StixId createdByRef) {
        this.createdByRef = createdByRef;
    }

    @JsonProperty("object_marking_refs")
    public List<StixId> getObjectMarkingRefs() {
        return objectMarkingRefs;
    }

    public void setObjectMarkingRefs(List<StixId> objectMarkingRefs) {
        this.objectMarkingRefs = objectMarkingRefs;
    }

    @JsonProperty("granted_refs")
    public List<String> getGrantedRefs() {
        return grantedRefs;
    }

    public void setGrantedRefs(List<String> grantedRefs) {
        this.grantedRefs = grantedRefs;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    public StixDate getCreated() {
        return created;
    }

    public void setCreated(StixDate created) {
        this.created = created;
    }

    public StixDate getModified() {
        return modified;
    }

    public void setModified(StixDate modified) {
        this.modified = modified;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    @JsonProperty("is_inferred")
    public Boolean getIsInferred() {
        return isInferred;
    }

    public void setIsInferred(Boolean isInferred) {
        this.isInferred = isInferred;
    }

    @JsonProperty("creator_ids")
    public List<String> getCreatorIds() {
        return creatorIds;
    }

    public void setCreatorIds(List<String> creatorIds) {
        this.creatorIds = creatorIds;
    }
}
