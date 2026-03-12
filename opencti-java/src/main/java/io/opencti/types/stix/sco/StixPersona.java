package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Persona object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixPersona extends StixCyberObject {
    public static final String TYPE = "persona";

    @JsonProperty("persona_name")
    private String personaName;
    @JsonProperty("persona_type")
    private String personaType;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<StixId> objectMarkingRefs;

    public StixPersona() {
        this.type = TYPE;
    }

    public String getPersonaName() { return personaName; }
    public void setPersonaName(String personaName) { this.personaName = personaName; }
    public String getPersonaType() { return personaType; }
    public void setPersonaType(String personaType) { this.personaType = personaType; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<StixId> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<StixId> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
