package io.opencti.common.types.stix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * STIX Domain Object type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * interface StixDomainObject extends StixObject {
 *   created_by_ref: StixId | undefined;
 *   created: StixDate;
 *   modified: StixDate;
 *   revoked: boolean;
 *   labels: Array<string>;
 *   confidence: number;
 *   lang: string;
 *   external_references?: Array<StixInternalExternalReference>;
 *   object_marking_refs: Array<StixId>;
 * }
 */
public abstract class StixDomainObject extends StixObject {
    protected String name;
    protected String description;
    protected StixId createdByRef;
    protected StixDate created;
    protected StixDate modified;
    protected Boolean revoked;
    protected List<String> labels;
    protected Integer confidence;
    protected String lang;
    protected List<StixExternalReference> externalReferences;

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

    public Boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @JsonProperty("external_references")
    public List<StixExternalReference> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(List<StixExternalReference> externalReferences) {
        this.externalReferences = externalReferences;
    }
}
