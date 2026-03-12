package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Phone Number object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixPhoneNumber extends StixCyberObject {
    public static final String TYPE = "phone-number";

    private String value;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<StixId> objectMarkingRefs;

    public StixPhoneNumber() {
        this.type = TYPE;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<StixId> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<StixId> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
