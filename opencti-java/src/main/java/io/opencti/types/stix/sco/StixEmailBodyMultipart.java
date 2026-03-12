package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Email Body Multipart object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixEmailBodyMultipart extends StixCyberObject {
    public static final String TYPE = "email-body-multipart";

    @JsonProperty("content_type")
    private String contentType;
    @JsonProperty("content_disposition")
    private String contentDisposition;
    private String body;
    @JsonProperty("body_raw_ref")
    private String bodyRawRef;
    private List<String> labels;
    private String description;
    @JsonProperty("created_by_ref")
    private String createdByRef;

    public StixEmailBodyMultipart() {
        this.type = TYPE;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyRawRef() {
        return bodyRawRef;
    }

    public void setBodyRawRef(String bodyRawRef) {
        this.bodyRawRef = bodyRawRef;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(String createdByRef) {
        this.createdByRef = createdByRef;
    }
}
