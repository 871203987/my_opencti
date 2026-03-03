package io.opencti.common.types.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;

import java.util.Map;

/**
 * Event type.
 * Original file: opencti-platform/opencti-graphql/src/types/event.d.ts
 * Represents an event in the OpenCTI platform.
 */
public class Event {
    public static final String TYPE = "event";

    private String id;
    private String type;
    private String eventType;
    private String eventMessage;
    private Map<String, Object> eventData;
    private String userId;
    private String userName;
    private StixDate timestamp;
    private String source;
    private String operation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("event_type")
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @JsonProperty("event_message")
    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    @JsonProperty("event_data")
    public Map<String, Object> getEventData() {
        return eventData;
    }

    public void setEventData(Map<String, Object> eventData) {
        this.eventData = eventData;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public StixDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(StixDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public static final String EVENT_TYPE_CREATE = "create";
    public static final String EVENT_TYPE_UPDATE = "update";
    public static final String EVENT_TYPE_DELETE = "delete";
    public static final String EVENT_TYPE_MERGE = "merge";
    public static final String EVENT_TYPE_LOGIN = "login";
    public static final String EVENT_TYPE_LOGOUT = "logout";

    public static final String SOURCE_USER = "user";
    public static final String SOURCE_CONNECTOR = "connector";
    public static final String SOURCE_SYSTEM = "system";
}
