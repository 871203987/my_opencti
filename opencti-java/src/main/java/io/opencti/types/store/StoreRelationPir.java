package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Store Relation PIR type.
 * Original file: opencti-platform/opencti-graphql/src/modules/pir/pir-types.ts
 * Represents a PIR (Priority Intelligence Requirement) relationship in the OpenCTI database.
 */
public class StoreRelationPir extends StoreObject {
    protected StoreObject from;
    protected StoreObject to;
    protected String relationship_type;
    protected String description;
    protected String start_time;
    protected String stop_time;
    protected Integer pir_score;
    protected String pir_explanation;
    protected String coverage;

    @JsonProperty("from")
    public StoreObject getFrom() {
        return from;
    }

    public void setFrom(StoreObject from) {
        this.from = from;
    }

    @JsonProperty("to")
    public StoreObject getTo() {
        return to;
    }

    public void setTo(StoreObject to) {
        this.to = to;
    }

    @JsonProperty("relationship_type")
    public String getRelationshipType() {
        return relationship_type;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationship_type = relationshipType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("start_time")
    public String getStartTime() {
        return start_time;
    }

    public void setStartTime(String startTime) {
        this.start_time = startTime;
    }

    @JsonProperty("stop_time")
    public String getStopTime() {
        return stop_time;
    }

    public void setStopTime(String stopTime) {
        this.stop_time = stopTime;
    }

    @JsonProperty("pir_score")
    public Integer getPirScore() {
        return pir_score;
    }

    public void setPirScore(Integer pirScore) {
        this.pir_score = pirScore;
    }

    @JsonProperty("pir_explanation")
    public String getPirExplanation() {
        return pir_explanation;
    }

    public void setPirExplanation(String pirExplanation) {
        this.pir_explanation = pirExplanation;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }
}
