package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixId;

import java.util.List;

/**
 * STIX File representation with references.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Represents a file associated with a STIX object.
 */
public class StixFile {
    protected String id;
    protected String name;
    protected String mimeType;
    protected String version;
    protected String uri;
    protected String data;
    protected List<StixId> objectMarkingRefs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("mime_type")
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @JsonProperty("object_marking_refs")
    public List<StixId> getObjectMarkingRefs() {
        return objectMarkingRefs;
    }

    public void setObjectMarkingRefs(List<StixId> objectMarkingRefs) {
        this.objectMarkingRefs = objectMarkingRefs;
    }
}

