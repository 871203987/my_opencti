package io.opencti.database.middleware;

import io.opencti.database.middleware.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static io.opencti.database.middleware.MiddlewareConstants.*;

/**
 * 中间件规则模块
 * 原文件: database/middleware.js:2626-2744
 * 
 * 提供推理规则相关的数据处理功能。
 */
@Component
public class MiddlewareRules {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareRules.class);

    private final MiddlewareLoader middlewareLoader;
    private final MiddlewareCreator middlewareCreator;
    private final MiddlewareUpdater middlewareUpdater;

    public MiddlewareRules(MiddlewareLoader middlewareLoader, MiddlewareCreator middlewareCreator,
            MiddlewareUpdater middlewareUpdater) {
        this.middlewareLoader = middlewareLoader;
        this.middlewareCreator = middlewareCreator;
        this.middlewareUpdater = middlewareUpdater;
    }

    // ==================== 规则属性行为定义 ====================
    
    /**
     * 获取实体类型支持的规则属性
     * 重写自: rules-utils.js:14-41 supportedAttributes
     */
    public List<SupportedAttribute> getSupportedAttributes(String entityType) {
        List<SupportedAttribute> attributes = new ArrayList<>();
        
        // STIX目击关系
        if (isStixSightingRelationship(entityType)) {
            attributes.add(new SupportedAttribute("first_seen", RuleOperation.MIN));
            attributes.add(new SupportedAttribute("last_seen", RuleOperation.MAX));
            attributes.add(new SupportedAttribute("attribute_count", RuleOperation.SUM));
            attributes.add(new SupportedAttribute("objectMarking", RuleOperation.AGG));
        }
        // 基础关系
        else if (isBasicRelationship(entityType)) {
            attributes.add(new SupportedAttribute("start_time", RuleOperation.MIN));
            attributes.add(new SupportedAttribute("stop_time", RuleOperation.MAX));
            attributes.add(new SupportedAttribute("objectMarking", RuleOperation.AGG));
        }
        // 事件类型 (RuleSightingIncident)
        else if ("Incident".equals(entityType)) {
            attributes.add(new SupportedAttribute("first_seen", RuleOperation.MIN));
            attributes.add(new SupportedAttribute("last_seen", RuleOperation.MAX));
            attributes.add(new SupportedAttribute("objectMarking", RuleOperation.AGG));
        }
        // STIX核心对象
        else if (isStixCoreObject(entityType)) {
            attributes.add(new SupportedAttribute("objectMarking", RuleOperation.AGG));
        }
        
        return attributes;
    }

    // ==================== 规则辅助函数 ====================

    /**
     * 获取所有规则字段中的指定属性值
     * 重写自: middleware.js:2627-2636 getAllRulesField
     * 
     * @param instance 实体实例
     * @param field 要获取的字段名
     * @return 所有规则中该字段的值列表
     */
    public List<Object> getAllRulesField(Map<String, Object> instance, String field) {
        List<Object> result = new ArrayList<>();
        
        // 遍历所有以规则前缀开头的键
        for (String key : instance.keySet()) {
            if (key.startsWith(RULE_PREFIX)) {
                Object ruleValue = instance.get(key);
                if (ruleValue != null) {
                    // 规则值可能是列表
                    if (ruleValue instanceof List) {
                        List<?> ruleList = (List<?>) ruleValue;
                        for (Object rule : ruleList) {
                            if (rule instanceof Map) {
                                Map<?, ?> ruleMap = (Map<?, ?>) rule;
                                Object data = ruleMap.get("data");
                                if (data instanceof Map) {
                                    Map<?, ?> dataMap = (Map<?, ?>) data;
                                    Object fieldValue = dataMap.get(field);
                                    if (fieldValue != null) {
                                        if (fieldValue instanceof List) {
                                            result.addAll((List<?>) fieldValue);
                                        } else {
                                            result.add(fieldValue);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }

    /**
     * 转换规则时间值为ZonedDateTime列表
     * 重写自: middleware.js:2637 convertRulesTimeValues
     * 
     * @param timeValues 时间字符串列表
     * @return ZonedDateTime列表
     */
    public List<ZonedDateTime> convertRulesTimeValues(List<String> timeValues) {
        return timeValues.stream()
                .map(this::parseDateTime)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 创建规则数据补丁
     * 重写自: middleware.js:2638-2695 createRuleDataPatch
     * 
     * @param instance 实体实例
     * @return 包含计算属性的补丁
     */
    public Map<String, Object> createRuleDataPatch(Map<String, Object> instance) {
        Map<String, Object> patch = new HashMap<>();
        
        // 计算规则权重
        int weight = 0;
        for (String key : instance.keySet()) {
            if (key.startsWith(RULE_PREFIX)) {
                Object ruleValue = instance.get(key);
                if (ruleValue instanceof List) {
                    weight += ((List<?>) ruleValue).size();
                }
            }
        }
        
        // 权重仅对关系有用
        String entityType = (String) instance.get("entity_type");
        if (isBasicRelationship(entityType)) {
            patch.put("i_inference_weight", weight);
        }
        
        // 获取支持的属性并计算
        List<SupportedAttribute> supportedAttributes = getSupportedAttributes(entityType);
        for (SupportedAttribute supportedAttribute : supportedAttributes) {
            String attribute = supportedAttribute.getName();
            RuleOperation operation = supportedAttribute.getOperation();
            
            List<Object> values = getAllRulesField(instance, attribute);
            if (!values.isEmpty()) {
                switch (operation) {
                    case AVG:
                        if (isNumericAttribute(attribute)) {
                            patch.put(attribute, computeAverage(values));
                        } else {
                            throw new UnsupportedOperationException("Can apply avg on non numeric attribute");
                        }
                        break;
                        
                    case SUM:
                        if (isNumericAttribute(attribute)) {
                            patch.put(attribute, computeSum(values));
                        } else {
                            throw new UnsupportedOperationException("Can apply sum on non numeric attribute");
                        }
                        break;
                        
                    case MIN:
                        if (isNumericAttribute(attribute)) {
                            patch.put(attribute, computeMin(values));
                        } else if (isDateAttribute(attribute)) {
                            List<ZonedDateTime> timeValues = convertRulesTimeValues(
                                    values.stream().map(Object::toString).collect(Collectors.toList())
                            );
                            if (!timeValues.isEmpty()) {
                                ZonedDateTime minTime = Collections.min(timeValues);
                                patch.put(attribute, formatDateTime(minTime));
                            }
                        } else {
                            throw new UnsupportedOperationException("Can apply min on non numeric or date attribute");
                        }
                        break;
                        
                    case MAX:
                        if (isNumericAttribute(attribute)) {
                            patch.put(attribute, computeMax(values));
                        } else if (isDateAttribute(attribute)) {
                            List<ZonedDateTime> timeValues = convertRulesTimeValues(
                                    values.stream().map(Object::toString).collect(Collectors.toList())
                            );
                            if (!timeValues.isEmpty()) {
                                ZonedDateTime maxTime = Collections.max(timeValues);
                                patch.put(attribute, formatDateTime(maxTime));
                            }
                        } else {
                            throw new UnsupportedOperationException("Can apply max on non numeric or date attribute");
                        }
                        break;
                        
                    case AGG:
                        // 去重聚合
                        List<Object> uniqueValues = values.stream()
                                .distinct()
                                .collect(Collectors.toList());
                        patch.put(attribute, uniqueValues);
                        break;
                }
            }
        }
        
        return patch;
    }

    /**
     * 获取规则解释数量
     * 重写自: middleware.js:2697-2699 getRuleExplanationsSize
     * 
     * @param fromRule 规则字段名
     * @param instance 实体实例
     * @return 解释数量
     */
    public int getRuleExplanationsSize(String fromRule, Map<String, Object> instance) {
        Object ruleValue = instance.get(fromRule);
        if (ruleValue instanceof List) {
            return ((List<?>) ruleValue).size();
        }
        return 0;
    }

    /**
     * 创建Upsert规则补丁
     * 重写自: middleware.js:2701-2710 createUpsertRulePatch
     * 
     * @param instance 实体实例
     * @param input 输入数据
     * @param opts 选项 (包含fromRule和fromRuleDeletion)
     * @return 补丁数据
     */
    public Map<String, Object> createUpsertRulePatch(
            Map<String, Object> instance, 
            Map<String, Object> input, 
            Map<String, Object> opts) {
        
        String fromRule = opts != null ? (String) opts.get("fromRule") : null;
        boolean fromRuleDeletion = opts != null && Boolean.TRUE.equals(opts.get("fromRuleDeletion"));
        
        // 限制规则元素数量
        List<Object> updatedRule;
        Object inputRuleValue = input.get(fromRule);
        
        if (fromRuleDeletion) {
            updatedRule = inputRuleValue instanceof List ? (List<Object>) inputRuleValue : new ArrayList<>();
        } else {
            if (inputRuleValue instanceof List) {
                List<Object> ruleList = (List<Object>) inputRuleValue;
                // 取最后MAX_EXPLANATIONS_PER_RULE个元素
                int start = Math.max(0, ruleList.size() - MAX_EXPLANATIONS_PER_RULE);
                updatedRule = new ArrayList<>(ruleList.subList(start, ruleList.size()));
            } else {
                updatedRule = new ArrayList<>();
            }
        }
        
        Map<String, Object> rulePatch = new HashMap<>();
        rulePatch.put(fromRule, updatedRule);
        
        // 合并实例和规则补丁
        Map<String, Object> ruleInstance = new HashMap<>(instance);
        ruleInstance.putAll(rulePatch);
        
        // 创建内部补丁
        Map<String, Object> innerPatch = createRuleDataPatch(ruleInstance);
        
        // 合并所有补丁
        Map<String, Object> result = new HashMap<>(rulePatch);
        result.putAll(innerPatch);
        
        return result;
    }

    /**
     * Upsert实体规则
     * 重写自: middleware.js:2711-2723 upsertEntityRule
     */
    public <T> MiddlewareResult<T> upsertEntityRule(
            MiddlewareContext context, Object user,
            Map<String, Object> instance, Map<String, Object> input, Map<String, Object> opts) {
        
        String fromRule = opts != null ? (String) opts.get("fromRule") : null;
        if (fromRule == null) {
            return MiddlewareResult.unchanged((T) instance);
        }

        // 如果已有最大解释数，不做任何操作
        int ruleExplanationsSize = getRuleExplanationsSize(fromRule, instance);
        if (ruleExplanationsSize == MAX_EXPLANATIONS_PER_RULE) {
            return MiddlewareResult.unchanged((T) instance);
        }

        log.debug("Upsert inferred entity for rule: {}", fromRule);
        
        // 创建补丁
        Map<String, Object> patch = createUpsertRulePatch(instance, input, opts);
        
        // 转换为输入格式并更新
        List<Map<String, Object>> inputs = transformPatchToInput(patch);
        return middlewareUpdater.updateAttributeFromLoadedWithRefs(context, user, instance, inputs, opts);
    }

    /**
     * Upsert关系规则
     * 重写自: middleware.js:2724-2744 upsertRelationRule
     */
    public <T> MiddlewareResult<T> upsertRelationRule(
            MiddlewareContext context, Object user,
            Map<String, Object> instance, Map<String, Object> input, Map<String, Object> opts) {
        
        String fromRule = opts != null ? (String) opts.get("fromRule") : null;
        boolean fromRuleDeletion = opts != null && Boolean.TRUE.equals(opts.get("fromRuleDeletion"));
        
        if (fromRule == null) {
            return MiddlewareResult.unchanged((T) instance);
        }

        // 如果已有最大解释数且不是删除操作，不做任何操作
        int ruleExplanationsSize = getRuleExplanationsSize(fromRule, instance);
        if (!fromRuleDeletion && ruleExplanationsSize == MAX_EXPLANATIONS_PER_RULE) {
            return MiddlewareResult.unchanged((T) instance);
        }

        log.debug("Upsert inferred relation for rule: {}", fromRule);
        
        // 更新规则
        Object inputRuleValue = input.get(fromRule);
        List<Object> updatedRule;
        
        if (inputRuleValue instanceof List) {
            updatedRule = new ArrayList<>((List<Object>) inputRuleValue);
        } else {
            updatedRule = new ArrayList<>();
        }
        
        if (!fromRuleDeletion && inputRuleValue instanceof List) {
            // 获取要保留的规则哈希
            Set<String> keepRuleHashes = ((List<Object>) inputRuleValue).stream()
                    .filter(i -> i instanceof Map)
                    .map(i -> (Map<?, ?>) i)
                    .map(m -> (String) m.get("hash"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            
            // 保留不在新列表中的旧规则
            Object instanceRuleValue = instance.get(fromRule);
            if (instanceRuleValue instanceof List) {
                for (Object rule : (List<?>) instanceRuleValue) {
                    if (rule instanceof Map) {
                        Map<?, ?> ruleMap = (Map<?, ?>) rule;
                        String hash = (String) ruleMap.get("hash");
                        if (hash != null && !keepRuleHashes.contains(hash)) {
                            updatedRule.add(rule);
                        }
                    }
                }
            }
        }
        
        // 更新输入并创建补丁
        Map<String, Object> updatedInput = new HashMap<>(input);
        updatedInput.put(fromRule, updatedRule);
        
        Map<String, Object> patch = createUpsertRulePatch(instance, updatedInput, opts);
        
        // 转换为输入格式并更新
        List<Map<String, Object>> inputs = transformPatchToInput(patch);
        return middlewareUpdater.updateAttributeFromLoadedWithRefs(context, user, instance, inputs, opts);
    }

    // ==================== 私有辅助方法 ====================

    private List<Map<String, Object>> transformPatchToInput(Map<String, Object> patch) {
        List<Map<String, Object>> inputs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : patch.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            List<Object> valueList;
            if (value instanceof List) {
                valueList = (List<Object>) value;
            } else {
                valueList = Collections.singletonList(value);
            }
            
            Map<String, Object> input = new HashMap<>();
            input.put("key", key);
            input.put("value", valueList);
            input.put("operation", "replace");
            inputs.add(input);
        }
        return inputs;
    }

    private boolean isNumericAttribute(String attribute) {
        return "confidence".equals(attribute) || "attribute_count".equals(attribute) 
                || "i_inference_weight".equals(attribute) || "x_opencti_score".equals(attribute);
    }

    private boolean isDateAttribute(String attribute) {
        return "first_seen".equals(attribute) || "last_seen".equals(attribute)
                || "start_time".equals(attribute) || "stop_time".equals(attribute)
                || "created".equals(attribute) || "modified".equals(attribute);
    }

    private boolean isStixSightingRelationship(String entityType) {
        return "stix-sighting-relationship".equals(entityType) || "Stix-Sighting-Relationship".equals(entityType);
    }

    private boolean isBasicRelationship(String entityType) {
        return "stix-core-relationship".equals(entityType) || "Stix-Core-Relationship".equals(entityType)
                || "relationship".equals(entityType) || "Relationship".equals(entityType);
    }

    private boolean isStixCoreObject(String entityType) {
        // 简化实现，实际需要检查是否为STIX核心对象类型
        return entityType != null && !entityType.startsWith("Internal-");
    }

    private double computeAverage(List<Object> values) {
        if (values.isEmpty()) return 0;
        double sum = 0;
        int count = 0;
        for (Object v : values) {
            if (v instanceof Number) {
                sum += ((Number) v).doubleValue();
                count++;
            } else if (v instanceof String) {
                try {
                    sum += Double.parseDouble((String) v);
                    count++;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return count > 0 ? sum / count : 0;
    }

    private double computeSum(List<Object> values) {
        double sum = 0;
        for (Object v : values) {
            if (v instanceof Number) {
                sum += ((Number) v).doubleValue();
            } else if (v instanceof String) {
                try {
                    sum += Double.parseDouble((String) v);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return sum;
    }

    private double computeMin(List<Object> values) {
        double min = Double.MAX_VALUE;
        for (Object v : values) {
            if (v instanceof Number) {
                min = Math.min(min, ((Number) v).doubleValue());
            } else if (v instanceof String) {
                try {
                    min = Math.min(min, Double.parseDouble((String) v));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return min == Double.MAX_VALUE ? 0 : min;
    }

    private double computeMax(List<Object> values) {
        double max = Double.MIN_VALUE;
        for (Object v : values) {
            if (v instanceof Number) {
                max = Math.max(max, ((Number) v).doubleValue());
            } else if (v instanceof String) {
                try {
                    max = Math.max(max, Double.parseDouble((String) v));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return max == Double.MIN_VALUE ? 0 : max;
    }

    private ZonedDateTime parseDateTime(String dateStr) {
        try {
            return ZonedDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            try {
                return Instant.parse(dateStr).atZone(ZoneId.of("UTC"));
            } catch (Exception e2) {
                return null;
            }
        }
    }

    private String formatDateTime(ZonedDateTime dateTime) {
        return dateTime.withZoneSameInstant(ZoneId.of("UTC"))
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
