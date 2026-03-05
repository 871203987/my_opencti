package io.opencti.database.middleware;

import io.opencti.database.middleware.model.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 中间件工具方法模块
 * 原文件: database/middleware.js:1038-1065
 * 
 * 提供各种辅助工具方法。
 */
@Component
public class MiddlewareUtils {

    private final MiddlewareLoader middlewareLoader;

    public MiddlewareUtils(MiddlewareLoader middlewareLoader) {
        this.middlewareLoader = middlewareLoader;
    }

    /**
     * 更新输入转数据
     * 原文件: middleware.js:1038-1048 updatedInputsToData
     */
    public Map<String, Object> updatedInputsToData(List<Map<String, Object>> inputs) {
        Map<String, Object> data = new HashMap<>();
        
        for (Map<String, Object> input : inputs) {
            String key = (String) input.get("key");
            Object value = input.get("value");
            String operation = (String) input.getOrDefault("operation", "replace");
            
            if ("add".equals(operation)) {
                List<Object> existing = (List<Object>) data.getOrDefault(key, new ArrayList<>());
                if (value instanceof List) {
                    existing.addAll((List<?>) value);
                } else {
                    existing.add(value);
                }
                data.put(key, existing);
            } else if ("remove".equals(operation)) {
                List<Object> existing = (List<Object>) data.getOrDefault(key, new ArrayList<>());
                if (value instanceof List) {
                    existing.removeAll((List<?>) value);
                } else {
                    existing.remove(value);
                }
                data.put(key, existing);
            } else {
                // replace
                if (value instanceof List && !((List<?>) value).isEmpty()) {
                    data.put(key, ((List<?>) value).get(0));
                } else {
                    data.put(key, value);
                }
            }
        }
        
        return data;
    }

    /**
     * 合并实例与输入
     * 原文件: middleware.js:1049-1055 mergeInstanceWithInputs
     */
    public Map<String, Object> mergeInstanceWithInputs(
            Map<String, Object> instance, List<Map<String, Object>> inputs) {
        
        Map<String, Object> result = new HashMap<>(instance);
        Map<String, Object> data = updatedInputsToData(inputs);
        result.putAll(data);
        
        return result;
    }

    /**
     * 部分合并实例与输入
     * 原文件: middleware.js:1056-1065 partialInstanceWithInputs
     */
    public Map<String, Object> partialInstanceWithInputs(
            Map<String, Object> instance, List<Map<String, Object>> inputs) {
        
        Map<String, Object> result = new HashMap<>();
        
        for (Map<String, Object> input : inputs) {
            String key = (String) input.get("key");
            Object value = input.get("value");
            
            if (instance.containsKey(key)) {
                if (value instanceof List && !((List<?>) value).isEmpty()) {
                    result.put(key, ((List<?>) value).get(0));
                } else {
                    result.put(key, value);
                }
            }
        }
        
        return result;
    }

    /**
     * 合并多个Map
     */
    public Map<String, Object> mergeMaps(Map<String, Object>... maps) {
        Map<String, Object> result = new HashMap<>();
        for (Map<String, Object> map : maps) {
            if (map != null) {
                result.putAll(map);
            }
        }
        return result;
    }

    /**
     * 深度合并Map
     */
    public Map<String, Object> deepMerge(Map<String, Object> base, Map<String, Object> override) {
        Map<String, Object> result = new HashMap<>(base);
        
        for (Map.Entry<String, Object> entry : override.entrySet()) {
            String key = entry.getKey();
            Object overrideValue = entry.getValue();
            Object baseValue = result.get(key);
            
            if (baseValue instanceof Map && overrideValue instanceof Map) {
                result.put(key, deepMerge((Map<String, Object>) baseValue, (Map<String, Object>) overrideValue));
            } else if (baseValue instanceof List && overrideValue instanceof List) {
                List<Object> merged = new ArrayList<>((List<?>) baseValue);
                for (Object item : (List<?>) overrideValue) {
                    if (!merged.contains(item)) {
                        merged.add(item);
                    }
                }
                result.put(key, merged);
            } else {
                result.put(key, overrideValue);
            }
        }
        
        return result;
    }

    /**
     * 提取实体ID列表
     */
    public List<String> extractIds(List<Map<String, Object>> elements) {
        return elements.stream()
                .map(e -> (String) e.get("internal_id"))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 检查字段是否为空
     */
    public boolean isEmptyField(Object value) {
        if (value == null) return true;
        if (value instanceof String) return ((String) value).isEmpty();
        if (value instanceof Collection) return ((Collection<?>) value).isEmpty();
        if (value instanceof Map) return ((Map<?, ?>) value).isEmpty();
        return false;
    }

    /**
     * 检查字段是否非空
     */
    public boolean isNotEmptyField(Object value) {
        return !isEmptyField(value);
    }

    /**
     * 安全获取Map值
     */
    public <T> T getMapValue(Map<String, Object> map, String key, Class<T> type, T defaultValue) {
        Object value = map.get(key);
        if (value == null) {
            return defaultValue;
        }
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        return defaultValue;
    }

    /**
     * 转换为列表
     */
    public List<Object> toList(Object value) {
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof List) {
            return (List<Object>) value;
        }
        return Collections.singletonList(value);
    }

    /**
     * 转换为字符串列表
     */
    public List<String> toStringList(Object value) {
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof List) {
            return ((List<?>) value).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.singletonList(value.toString());
    }

    /**
     * 生成UUID
     */
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成标准ID
     */
    public String generateStandardId(String type, Map<String, Object> data) {
        return type + "--" + generateUuid();
    }

    /**
     * 检查是否为有效ID
     */
    public boolean isValidId(String id) {
        return id != null && id.contains("--");
    }

    /**
     * 从ID提取类型
     */
    public String extractTypeFromId(String id) {
        if (id == null || !id.contains("--")) {
            return null;
        }
        return id.substring(0, id.indexOf("--"));
    }

    /**
     * 获取当前时间戳
     */
    public Date now() {
        return new Date();
    }

    /**
     * 格式化日期为ISO字符串
     */
    public String formatIsoDate(Date date) {
        if (date == null) return null;
        return date.toInstant().toString();
    }

    /**
     * 解析ISO日期字符串
     */
    public Date parseIsoDate(String dateStr) {
        if (dateStr == null) return null;
        return Date.from(java.time.Instant.parse(dateStr));
    }
}
