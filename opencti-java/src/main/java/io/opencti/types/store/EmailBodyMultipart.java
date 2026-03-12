package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Email Body Multipart type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Represents a multipart body in an email message.
 */
public class EmailBodyMultipart {
    protected String contentType;
    protected String contentDisposition;
    protected String body;
    protected String bodyRawRef;

    @JsonProperty("content_type")
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    @JsonProperty("content_disposition")
    public String getContentDisposition() { return contentDisposition; }
    public void setContentDisposition(String contentDisposition) { this.contentDisposition = contentDisposition; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    @JsonProperty("body_raw_ref")
    public String getBodyRawRef() { return bodyRawRef; }
    public void setBodyRawRef(String bodyRawRef) { this.bodyRawRef = bodyRawRef; }
}

