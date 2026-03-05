package io.opencti.database.middleware.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * 中间件上下文
 * 原文件: middleware.js (context 参数)
 * 
 * 包含请求处理的上下文信息，如用户信息、请求ID、草稿ID、事件ID等。
 */
public class MiddlewareContext {

    private String id;
    private String eventId;
    private String draftId;
    private String userId;
    private String userSource;
    private Integer callRetryNumber;
    private boolean synchronizedUpsert;
    private String previousStandard;
    private Map<String, Object> additionalProperties;

    public MiddlewareContext() {
        this.additionalProperties = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public Integer getCallRetryNumber() {
        return callRetryNumber;
    }

    public void setCallRetryNumber(Integer callRetryNumber) {
        this.callRetryNumber = callRetryNumber;
    }

    public boolean isSynchronizedUpsert() {
        return synchronizedUpsert;
    }

    public void setSynchronizedUpsert(boolean synchronizedUpsert) {
        this.synchronizedUpsert = synchronizedUpsert;
    }

    public String getPreviousStandard() {
        return previousStandard;
    }

    public void setPreviousStandard(String previousStandard) {
        this.previousStandard = previousStandard;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public Object getAdditionalProperty(String key) {
        return additionalProperties.get(key);
    }

    public void setAdditionalProperty(String key, Object value) {
        additionalProperties.put(key, value);
    }

    /**
     * 创建默认上下文
     */
    public static MiddlewareContext createDefault() {
        MiddlewareContext context = new MiddlewareContext();
        context.setId(java.util.UUID.randomUUID().toString());
        context.setEventId(java.util.UUID.randomUUID().toString());
        return context;
    }

    /**
     * 创建带用户ID的上下文
     */
    public static MiddlewareContext createWithUser(String userId) {
        MiddlewareContext context = createDefault();
        context.setUserId(userId);
        return context;
    }
}
