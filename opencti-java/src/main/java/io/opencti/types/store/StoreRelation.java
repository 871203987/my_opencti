package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixDate;
import io.opencti.types.stix.common.StixId;

import java.util.List;

/**
 * Store Relation type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Represents a relationship stored in the OpenCTI database.
 */
public class StoreRelation extends StoreObject {
    protected String relationshipType;
    protected String description;
    protected StixId sourceRef;
    protected StixId targetRef;
    protected String sourceType;
    protected String targetType;
    protected String sourceName;
    protected String targetName;
    protected StixDate startTime;
    protected StixDate stopTime;
    protected Integer confidence;
    protected StixId createdByRef;
    protected List<StixId> objectMarkingRefs;
    protected List<String> grantedRefs;
    protected Boolean isInferred;

    // SRO额外属性
    protected StoreObject from;
    protected StoreObject to;
    protected Integer attributeCount;
    protected Boolean summary;
    protected Boolean xOpenctiNegative;
    protected List<KillChainPhase> killChainPhases;
    protected String coverage;

    public StoreRelation() {
        this.entity_type = "relationship";
    }

    @JsonProperty("relationship_type")
    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("source_ref")
    public StixId getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(StixId sourceRef) {
        this.sourceRef = sourceRef;
    }

    @JsonProperty("target_ref")
    public StixId getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(StixId targetRef) {
        this.targetRef = targetRef;
    }

    @JsonProperty("source_type")
    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    @JsonProperty("target_type")
    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    @JsonProperty("source_name")
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @JsonProperty("target_name")
    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @JsonProperty("start_time")
    public StixDate getStartTime() {
        return startTime;
    }

    public void setStartTime(StixDate startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("stop_time")
    public StixDate getStopTime() {
        return stopTime;
    }

    public void setStopTime(StixDate stopTime) {
        this.stopTime = stopTime;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
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

    @JsonProperty("is_inferred")
    public Boolean getIsInferred() {
        return isInferred;
    }

    public void setIsInferred(Boolean isInferred) {
        this.isInferred = isInferred;
    }

    public StoreObject getFrom() {
        return from;
    }

    public void setFrom(StoreObject from) {
        this.from = from;
    }

    public StoreObject getTo() {
        return to;
    }

    public void setTo(StoreObject to) {
        this.to = to;
    }

    @JsonProperty("attribute_count")
    public Integer getAttributeCount() {
        return attributeCount;
    }

    public void setAttributeCount(Integer attributeCount) {
        this.attributeCount = attributeCount;
    }

    public Boolean getSummary() {
        return summary;
    }

    public void setSummary(Boolean summary) {
        this.summary = summary;
    }

    @JsonProperty("x_opencti_negative")
    public Boolean getXOpenctiNegative() {
        return xOpenctiNegative;
    }

    public void setXOpenctiNegative(Boolean xOpenctiNegative) {
        this.xOpenctiNegative = xOpenctiNegative;
    }

    @JsonProperty("kill_chain_phases")
    public List<KillChainPhase> getKillChainPhases() {
        return killChainPhases;
    }

    public void setKillChainPhases(List<KillChainPhase> killChainPhases) {
        this.killChainPhases = killChainPhases;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }
}

