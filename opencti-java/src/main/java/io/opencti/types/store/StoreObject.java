package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixId;

import java.util.List;

/**
 * Store Object base type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Represents a base object stored in the OpenCTI database.
 */
public class StoreObject implements StoreCommon {
    protected String id;
    protected String internal_id;
    protected String standard_id;
    protected String entity_type;
    protected String parent_types;
    protected String base_type;
    protected List<String> x_opencti_stix_ids;
    protected String spec_version;
    protected String created_at;
    protected String updated_at;
    protected String revoked;
    protected String confidence;
    protected String lang;
    protected List<String> creators;
    protected List<String> granted_refs;
    protected String x_opencti_workflow_id;
    protected String objectMarking;
    protected String objectLabel;
    protected String objectAssignee;
    protected String objectParticipant;
    protected String objectOrganization;
    protected List<ExternalReference> externalReferences;
    protected String x_opencti_description;
    protected String x_opencti_score;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("internal_id")
    public String getInternalId() {
        return internal_id;
    }

    public void setInternalId(String internal_id) {
        this.internal_id = internal_id;
    }

    @JsonProperty("standard_id")
    public String getStandardId() {
        return standard_id;
    }

    public void setStandardId(String standard_id) {
        this.standard_id = standard_id;
    }

    @JsonProperty("entity_type")
    public String getEntityType() {
        return entity_type;
    }

    public void setEntityType(String entity_type) {
        this.entity_type = entity_type;
    }

    @JsonProperty("parent_types")
    public String getParentTypes() {
        return parent_types;
    }

    public void setParentTypes(String parent_types) {
        this.parent_types = parent_types;
    }

    @JsonProperty("base_type")
    public String getBaseType() {
        return base_type;
    }

    public void setBaseType(String base_type) {
        this.base_type = base_type;
    }

    @JsonProperty("x_opencti_stix_ids")
    public List<String> getXOpenctiStixIds() {
        return x_opencti_stix_ids;
    }

    public void setXOpenctiStixIds(List<String> x_opencti_stix_ids) {
        this.x_opencti_stix_ids = x_opencti_stix_ids;
    }

    @JsonProperty("spec_version")
    public String getSpecVersion() {
        return spec_version;
    }

    public void setSpecVersion(String spec_version) {
        this.spec_version = spec_version;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    @JsonProperty("revoked")
    public Boolean getRevoked() {
        return revoked != null ? Boolean.parseBoolean(revoked) : null;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked != null ? revoked.toString() : null;
    }

    @JsonProperty("confidence")
    public Integer getConfidence() {
        return confidence != null ? Integer.parseInt(confidence) : null;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence != null ? confidence.toString() : null;
    }

    @JsonProperty("lang")
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @JsonProperty("creators")
    public List<String> getCreators() {
        return creators;
    }

    public void setCreators(List<String> creators) {
        this.creators = creators;
    }

    @JsonProperty("granted_refs")
    public List<String> getGrantedRefs() {
        return granted_refs;
    }

    public void setGrantedRefs(List<String> granted_refs) {
        this.granted_refs = granted_refs;
    }

    @JsonProperty("x_opencti_workflow_id")
    public String getXOpenctiWorkflowId() {
        return x_opencti_workflow_id;
    }

    public void setXOpenctiWorkflowId(String x_opencti_workflow_id) {
        this.x_opencti_workflow_id = x_opencti_workflow_id;
    }

    @JsonProperty("objectMarking")
    public String getObjectMarking() {
        return objectMarking;
    }

    public void setObjectMarking(String objectMarking) {
        this.objectMarking = objectMarking;
    }

    @JsonProperty("objectLabel")
    public String getObjectLabel() {
        return objectLabel;
    }

    public void setObjectLabel(String objectLabel) {
        this.objectLabel = objectLabel;
    }

    @JsonProperty("objectAssignee")
    public String getObjectAssignee() {
        return objectAssignee;
    }

    public void setObjectAssignee(String objectAssignee) {
        this.objectAssignee = objectAssignee;
    }

    @JsonProperty("objectParticipant")
    public String getObjectParticipant() {
        return objectParticipant;
    }

    public void setObjectParticipant(String objectParticipant) {
        this.objectParticipant = objectParticipant;
    }

    @JsonProperty("objectOrganization")
    public String getObjectOrganization() {
        return objectOrganization;
    }

    public void setObjectOrganization(String objectOrganization) {
        this.objectOrganization = objectOrganization;
    }

    @JsonProperty("externalReferences")
    public List<ExternalReference> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(List<ExternalReference> externalReferences) {
        this.externalReferences = externalReferences;
    }

    @JsonProperty("x_opencti_description")
    public String getXOpenctiDescription() {
        return x_opencti_description;
    }

    public void setXOpenctiDescription(String x_opencti_description) {
        this.x_opencti_description = x_opencti_description;
    }

    @JsonProperty("x_opencti_score")
    public Double getXOpenctiScore() {
        return x_opencti_score != null ? Double.parseDouble(x_opencti_score) : null;
    }

    public void setXOpenctiScore(Double x_opencti_score) {
        this.x_opencti_score = x_opencti_score != null ? x_opencti_score.toString() : null;
    }

    // 这些方法在子类中实现，这里提供默认实现
    public String getModifiedAt() {
        return updated_at;
    }

    public void setModifiedAt(String modifiedAt) {
        this.updated_at = modifiedAt;
    }

    public List<String> getXOpenctiAliases() {
        return null;
    }

    public void setXOpenctiAliases(List<String> aliases) {
    }

    public List<Object> getXOpenctiFiles() {
        return null;
    }

    public void setXOpenctiFiles(List<Object> files) {
    }

    public List<String> getStixIds() {
        return x_opencti_stix_ids;
    }

    public void setStixIds(List<String> stixIds) {
        this.x_opencti_stix_ids = stixIds;
    }

    public Boolean getXOpenctiInferred() {
        return null;
    }

    public void setXOpenctiInferred(Boolean inferred) {
    }
}
