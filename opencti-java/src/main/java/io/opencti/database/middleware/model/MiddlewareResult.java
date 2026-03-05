package io.opencti.database.middleware.model;

import java.util.Map;

/**
 * 中间件操作结果封装
 * 原文件: middleware.js 返回值结构
 * 
 * 用于封装中间件操作的结果，包含操作后的元素、事件和创建标志。
 * 
 * @param <T> 元素类型
 */
public class MiddlewareResult<T> {

    private T element;
    private Object event;
    private boolean isCreation;

    public MiddlewareResult() {
    }

    public MiddlewareResult(T element, Object event, boolean isCreation) {
        this.element = element;
        this.event = event;
        this.isCreation = isCreation;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Object getEvent() {
        return event;
    }

    public void setEvent(Object event) {
        this.event = event;
    }

    public boolean isCreation() {
        return isCreation;
    }

    public void setCreation(boolean creation) {
        isCreation = creation;
    }

    /**
     * 创建成功的结果
     */
    public static <T> MiddlewareResult<T> success(T element) {
        return new MiddlewareResult<>(element, null, false);
    }

    /**
     * 创建成功的结果（带事件）
     */
    public static <T> MiddlewareResult<T> success(T element, Object event) {
        return new MiddlewareResult<>(element, event, false);
    }

    /**
     * 创建新创建的结果
     */
    public static <T> MiddlewareResult<T> created(T element, Object event) {
        return new MiddlewareResult<>(element, event, true);
    }

    /**
     * 创建更新的结果
     */
    public static <T> MiddlewareResult<T> updated(T element, Object event) {
        return new MiddlewareResult<>(element, event, false);
    }

    /**
     * 创建无变化的结果
     */
    public static <T> MiddlewareResult<T> unchanged(T element) {
        return new MiddlewareResult<>(element, null, false);
    }
}
