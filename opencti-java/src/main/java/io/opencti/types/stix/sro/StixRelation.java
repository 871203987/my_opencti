package io.opencti.types.stix.sro;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixDate;
import io.opencti.types.stix.common.StixId;
import io.opencti.types.stix.common.StixRelationshipObject;

/**
 * STIX Relation type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sro.d.ts
 * Original type:
 * export interface StixRelation extends StixRelationshipObject {
 *   relationship_type: string;
 *   description: string;
 *   source_ref: StixId;
 *   target_ref: StixId;
 *   start_time: StixDate;
 *   stop_time: StixDate;
 *   extensions: {
 *     [STIX_EXT_OCTI]: RelationExtension;
 *   };
 * }
 */
public class StixRelation extends StixRelationshipObject {
    public static final String TYPE = "relationship";

    @JsonProperty("relationship_type")
    private String relationshipType;
    private String description;
    @JsonProperty("source_ref")
    private StixId sourceRef;
    @JsonProperty("target_ref")
    private StixId targetRef;
    @JsonProperty("start_time")
    private StixDate startTime;
    @JsonProperty("stop_time")
    private StixDate stopTime;
    private RelationExtension relationExtension;

    public StixRelation() {
        this.type = TYPE;
    }

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

    public StixId getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(StixId sourceRef) {
        this.sourceRef = sourceRef;
    }

    public StixId getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(StixId targetRef) {
        this.targetRef = targetRef;
    }

    public StixDate getStartTime() {
        return startTime;
    }

    public void setStartTime(StixDate startTime) {
        this.startTime = startTime;
    }

    public StixDate getStopTime() {
        return stopTime;
    }

    public void setStopTime(StixDate stopTime) {
        this.stopTime = stopTime;
    }

    public RelationExtension getRelationExtension() {
        return relationExtension;
    }

    public void setRelationExtension(RelationExtension relationExtension) {
        this.relationExtension = relationExtension;
    }
}

