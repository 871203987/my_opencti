package io.opencti.types.stix.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * STIX Object base type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * interface StixObject {
 *   id: StixId;
 *   type: string;
 *   spec_version: string;
 *   object_marking_refs?: Array<StixId>;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpenctiExtension;
 *   };
 * }
 */
public abstract class StixObject {
    protected StixId id;
    protected String type;
    protected String specVersion;
    protected List<StixId> objectMarkingRefs;
    protected Map<String, Object> extensions;

    protected StixObject() {
        this.specVersion = "2.1";
    }

    public StixId getId() {
        return id;
    }

    public void setId(StixId id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("spec_version")
    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    @JsonProperty("object_marking_refs")
    public List<StixId> getObjectMarkingRefs() {
        return objectMarkingRefs;
    }

    public void setObjectMarkingRefs(List<StixId> objectMarkingRefs) {
        this.objectMarkingRefs = objectMarkingRefs;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }
}

