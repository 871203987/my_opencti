package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;
import java.util.Map;

/**
 * STIX Email Message object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixEmailMessage extends StixCyberObject {
    public static final String TYPE = "email-message";

    @JsonProperty("is_multipart")
    private Boolean isMultipart;
    private String date;
    @JsonProperty("content_type")
    private String contentType;
    @JsonProperty("from_ref")
    private String fromRef;
    @JsonProperty("sender_ref")
    private String senderRef;
    @JsonProperty("to_refs")
    private List<String> toRefs;
    @JsonProperty("cc_refs")
    private List<String> ccRefs;
    @JsonProperty("bcc_refs")
    private List<String> bccRefs;
    @JsonProperty("message_id")
    private String messageId;
    private String subject;
    @JsonProperty("received_lines")
    private List<String> receivedLines;
    @JsonProperty("additional_header_fields")
    private Map<String, Object> additionalHeaderFields;
    private String body;
    @JsonProperty("body_multipart")
    private List<StixEmailBodyMultipart> bodyMultipart;
    @JsonProperty("raw_email_ref")
    private String rawEmailRef;
    @JsonProperty("contains_refs")
    private List<String> containsRefs;

    public StixEmailMessage() {
        this.type = TYPE;
    }

    public Boolean getIsMultipart() {
        return isMultipart;
    }

    public void setIsMultipart(Boolean isMultipart) {
        this.isMultipart = isMultipart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFromRef() {
        return fromRef;
    }

    public void setFromRef(String fromRef) {
        this.fromRef = fromRef;
    }

    public String getSenderRef() {
        return senderRef;
    }

    public void setSenderRef(String senderRef) {
        this.senderRef = senderRef;
    }

    public List<String> getToRefs() {
        return toRefs;
    }

    public void setToRefs(List<String> toRefs) {
        this.toRefs = toRefs;
    }

    public List<String> getCcRefs() {
        return ccRefs;
    }

    public void setCcRefs(List<String> ccRefs) {
        this.ccRefs = ccRefs;
    }

    public List<String> getBccRefs() {
        return bccRefs;
    }

    public void setBccRefs(List<String> bccRefs) {
        this.bccRefs = bccRefs;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getReceivedLines() {
        return receivedLines;
    }

    public void setReceivedLines(List<String> receivedLines) {
        this.receivedLines = receivedLines;
    }

    public Map<String, Object> getAdditionalHeaderFields() {
        return additionalHeaderFields;
    }

    public void setAdditionalHeaderFields(Map<String, Object> additionalHeaderFields) {
        this.additionalHeaderFields = additionalHeaderFields;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<StixEmailBodyMultipart> getBodyMultipart() {
        return bodyMultipart;
    }

    public void setBodyMultipart(List<StixEmailBodyMultipart> bodyMultipart) {
        this.bodyMultipart = bodyMultipart;
    }

    public String getRawEmailRef() {
        return rawEmailRef;
    }

    public void setRawEmailRef(String rawEmailRef) {
        this.rawEmailRef = rawEmailRef;
    }

    public List<String> getContainsRefs() {
        return containsRefs;
    }

    public void setContainsRefs(List<String> containsRefs) {
        this.containsRefs = containsRefs;
    }
}
