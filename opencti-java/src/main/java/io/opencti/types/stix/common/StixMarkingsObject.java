package io.opencti.types.stix.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * STIX Markings Object type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * interface StixMarkingsObject extends StixObject {
 *   created_by_ref: StixId | undefined;
 *   created: StixDate;
 *   modified: StixDate;
 *   external_references?: Array<StixInternalExternalReference>;
 *   object_marking_refs: Array<StixId>;
 * }
 */
public abstract class StixMarkingsObject extends StixObject {
    protected StixId createdByRef;
    protected StixDate created;
    protected StixDate modified;
    protected List<StixExternalReference> externalReferences;

    @JsonProperty("created_by_ref")
    public StixId getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(StixId createdByRef) {
        this.createdByRef = createdByRef;
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

    @JsonProperty("external_references")
    public List<StixExternalReference> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(List<StixExternalReference> externalReferences) {
        this.externalReferences = externalReferences;
    }
}

