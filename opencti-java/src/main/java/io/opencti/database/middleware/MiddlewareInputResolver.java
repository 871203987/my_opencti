package io.opencti.database.middleware;

import io.opencti.database.middleware.model.MiddlewareContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 中间件输入解析模块
 * 原文件: database/middleware.js:745-988
 * 
 * 提供输入数据解析和验证功能，包括引用解析、词汇表处理等。
 */
@Component
public class MiddlewareInputResolver {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareInputResolver.class);

    private static final String INPUT_LABELS = "objectLabel";
    private static final String INPUT_MARKINGS = "objectMarking";
    private static final String INPUT_CREATED_BY = "createdBy";
    private static final String ENTITY_TYPE_VOCABULARY = "Vocabulary";

    private final MiddlewareLoader middlewareLoader;

    public MiddlewareInputResolver(MiddlewareLoader middlewareLoader) {
        this.middlewareLoader = middlewareLoader;
    }

    /**
     * 获取依赖键列表
     * 原文件: middleware.js:746-755 depsKeys
     */
    public List<Map<String, Object>> depsKeys(String type) {
        List<Map<String, Object>> keys = new ArrayList<>();
        
        // 注册的依赖键
        keys.addAll(getRegisteredDepsKeys());
        
        // 关系依赖
        keys.add(Map.of("src", "fromId", "dst", "from"));
        keys.add(Map.of("src", "toId", "dst", "to"));
        
        // Schema关系引用
        List<String> inputNames = getSchemaRelationRefInputNames(type);
        for (String inputName : inputNames) {
            Map<String, Object> key = new HashMap<>();
            key.put("src", inputName);
            keys.add(key);
        }
        
        return keys;
    }

    /**
     * 词汇表ID处理
     * 原文件: middleware.js:757-759 idVocabulary
     */
    public String idVocabulary(String nameOrId, String category) {
        if (isAnId(nameOrId)) {
            return nameOrId;
        }
        return generateStandardId(ENTITY_TYPE_VOCABULARY, Map.of("name", nameOrId, "category", category));
    }

    /**
     * 验证创建者
     * 原文件: middleware.js:769-780 validateCreatedBy
     */
    public void validateCreatedBy(MiddlewareContext context, Object user, String createdById) {
        if (createdById != null) {
            Map<String, Object> createdByEntity = internalLoadById(context, user, createdById);
            if (createdByEntity != null && createdByEntity.containsKey("entity_type")) {
                String entityType = (String) createdByEntity.get("entity_type");
                if (!isStixDomainObjectIdentity(entityType)) {
                    throw new IllegalArgumentException(
                            "CreatedBy relation must be an Identity entity. createdBy: " + createdById);
                }
            }
        }
    }

    /**
     * 解析输入引用
     * 原文件: middleware.js:782-988 inputResolveRefs
     * 
     * 这是中间件最复杂的函数之一，负责解析输入中的所有引用关系。
     */
    public Map<String, Object> inputResolveRefs(
            MiddlewareContext context, Object user,
            Map<String, Object> input, String type, Object entitySetting) {
        
        Map<String, List<Map<String, Object>>> fetchingIdsMap = new HashMap<>();
        List<String> expectedIds = new ArrayList<>();
        
        Map<String, Object> cleanedInput = new HashMap<>(input);
        cleanedInput.put("_index", inferIndexFromConceptType(type));
        
        String embeddedFromResolution = null;
        List<Map<String, Object>> dependencyKeys = depsKeys(type);

        for (Map<String, Object> depKey : dependencyKeys) {
            String src = (String) depKey.get("src");
            String dst = (String) depKey.get("dst");
            List<String> depTypes = (List<String>) depKey.getOrDefault("types", Collections.emptyList());
            String destKey = dst != null ? dst : src;
            Object id = input.get(src);
            
            boolean isValidType = !depTypes.isEmpty() ? depTypes.contains(type) : true;
            boolean isAlreadyResolved = isAlreadyResolved(id);
            
            if (isValidType && id != null && !isEmpty(id) && !isAlreadyResolved) {
                boolean isListing = id instanceof List;
                boolean hasOpenVocab = isEntityFieldAnOpenVocabulary(destKey, type);
                
                if (INPUT_LABELS.equals(src)) {
                    // 处理标签
                    List<?> labels = (List<?>) id;
                    Set<String> uniqueLabels = new HashSet<>();
                    for (Object label : labels) {
                        String labelId = idLabel(label.toString());
                        uniqueLabels.add(labelId);
                    }
                    
                    for (String labelId : uniqueLabels) {
                        Map<String, Object> labelElement = new HashMap<>();
                        labelElement.put("id", labelId);
                        labelElement.put("destKey", destKey);
                        labelElement.put("multiple", true);
                        
                        fetchingIdsMap.computeIfAbsent(labelId, k -> new ArrayList<>()).add(labelElement);
                    }
                } else if (hasOpenVocab) {
                    // 处理开放词汇表
                    List<?> ids = isListing ? (List<?>) id : Collections.singletonList(id);
                    Map<String, Object> vocabCategory = getVocabularyCategoryForField(destKey, type);
                    String category = (String) vocabCategory.get("category");
                    Map<String, Object> field = (Map<String, Object>) vocabCategory.get("field");
                    
                    for (Object i : ids) {
                        if (field.containsKey("composite") && field.containsKey("multiple")) {
                            throw new IllegalArgumentException(
                                    "Composite vocab only support single definition, field: " + field);
                        }
                        
                        String vocabularyId;
                        if (field.containsKey("composite")) {
                            Map<String, Object> compositeData = (Map<String, Object>) i;
                            vocabularyId = idVocabulary((String) compositeData.get(field.get("composite")), category);
                        } else {
                            vocabularyId = idVocabulary(i.toString(), category);
                        }
                        
                        Map<String, Object> vocabularyElement = new HashMap<>();
                        vocabularyElement.put("id", vocabularyId);
                        vocabularyElement.put("destKey", destKey);
                        vocabularyElement.put("vocab", Map.of("field", field, "data", i));
                        vocabularyElement.put("multiple", isListing);
                        
                        fetchingIdsMap.computeIfAbsent(vocabularyId, k -> new ArrayList<>()).add(vocabularyElement);
                    }
                } else if (isListing) {
                    // 处理列表类型
                    List<?> idList = (List<?>) id;
                    for (Object i : idList) {
                        Map<String, Object> listingElement = new HashMap<>();
                        listingElement.put("id", i);
                        listingElement.put("destKey", destKey);
                        listingElement.put("multiple", true);
                        
                        String key = i.toString();
                        List<Map<String, Object>> existing = fetchingIdsMap.get(key);
                        if (existing != null) {
                            boolean found = existing.stream().anyMatch(e -> destKey.equals(e.get("destKey")));
                            if (!found) {
                                existing.add(listingElement);
                            }
                        } else {
                            List<Map<String, Object>> list = new ArrayList<>();
                            list.add(listingElement);
                            fetchingIdsMap.put(key, list);
                        }
                        expectedIds.add(key);
                    }
                } else {
                    // 处理单个值
                    String singleId = (String) id;
                    if ("from".equals(dst) && isStixRefRelationship(type)) {
                        embeddedFromResolution = singleId;
                    } else {
                        Map<String, Object> singleElement = new HashMap<>();
                        singleElement.put("id", singleId);
                        singleElement.put("destKey", destKey);
                        singleElement.put("multiple", false);
                        
                        fetchingIdsMap.computeIfAbsent(singleId, k -> new ArrayList<>()).add(singleElement);
                    }
                    if (!expectedIds.contains(singleId)) {
                        expectedIds.add(singleId);
                    }
                }
                cleanedInput.put(src, null);
            }
        }

        // 解析ID
        List<String> idsToFetch = new ArrayList<>(fetchingIdsMap.keySet());
        List<Map<String, Object>> resolvedElements = internalFindByIds(context, user, idsToFetch);
        
        Map<String, Object> embeddedFrom = null;
        if (embeddedFromResolution != null) {
            Map<String, Object> element = new HashMap<>();
            element.put("id", embeddedFromResolution);
            element.put("destKey", "from");
            element.put("multiple", false);
            fetchingIdsMap.put(embeddedFromResolution, Collections.singletonList(element));
            embeddedFrom = middlewareLoader.storeLoadByIdWithRefs(context, user, embeddedFromResolution, null);
        }
        
        if (embeddedFrom != null) {
            resolvedElements.add(embeddedFrom);
        }

        // 构建解析映射
        Map<String, Map<String, Object>> resolutionsMap = new HashMap<>();
        Set<String> resolvedIds = new HashSet<>();
        
        for (Map<String, Object> resolvedElement : resolvedElements) {
            List<String> instanceIds = getInstanceIds(resolvedElement);
            List<Map<String, Object>> matchingConfigs = new ArrayList<>();
            
            for (String instanceId : instanceIds) {
                resolvedIds.add(instanceId);
                if (fetchingIdsMap.containsKey(instanceId)) {
                    matchingConfigs.addAll(fetchingIdsMap.get(instanceId));
                }
            }
            
            for (Map<String, Object> c : matchingConfigs) {
                Map<String, Object> data = new HashMap<>(resolvedElement);
                data.put("i_group", c);
                String dataKey = resolvedElement.get("internal_id") + "|" + c.get("destKey");
                resolutionsMap.put(dataKey, data);
            }
        }

        // 按类型分组
        Map<String, List<Map<String, Object>>> groupByTypeElements = new HashMap<>();
        for (Map<String, Object> value : resolutionsMap.values()) {
            Map<String, Object> iGroup = (Map<String, Object>) value.get("i_group");
            String destKey = (String) iGroup.get("destKey");
            groupByTypeElements.computeIfAbsent(destKey, k -> new ArrayList<>()).add(value);
        }

        // 解析结果
        List<Map<String, Object>> resolved = new ArrayList<>();
        for (Map.Entry<String, List<Map<String, Object>>> entry : groupByTypeElements.entrySet()) {
            String k = entry.getKey();
            List<Map<String, Object>> val = entry.getValue();
            
            Map<String, Object> attr = getSchemaAttribute(type, k);
            Map<String, Object> ref = getSchemaRelationRef(type, k);
            
            if (ref == null && attr == null) {
                throw new UnsupportedOperationException(
                        "Invalid attribute resolution, key: " + k + ", type: " + type);
            }
            
            boolean isMultiple = (attr != null && Boolean.TRUE.equals(attr.get("multiple"))) ||
                    (ref != null && Boolean.TRUE.equals(ref.get("multiple")));
            
            if (!isMultiple) {
                // 单值
                List<Map<String, Object>> rawValues = val;
                if (rawValues.size() > 1) {
                    throw new UnsupportedOperationException(
                            "Input resolve refs expect single value, key: " + k);
                }
                
                Map<String, Object> rawValue = rawValues.get(0);
                Map<String, Object> iGroup = (Map<String, Object>) rawValue.get("i_group");
                Map<String, Object> vocab = iGroup != null ? (Map<String, Object>) iGroup.get("vocab") : null;
                
                Map<String, Object> result = new HashMap<>();
                if (vocab != null) {
                    Map<String, Object> vocabField = (Map<String, Object>) vocab.get("field");
                    if (vocabField.containsKey("composite")) {
                        Object vocabData = vocab.get("data");
                        Map<String, Object> compositeResult = new HashMap<>((Map<String, Object>) vocabData);
                        compositeResult.put((String) vocabField.get("composite"), rawValue.get("name"));
                        result.put(k, compositeResult);
                    } else {
                        result.put(k, rawValue.get("name"));
                    }
                } else {
                    result.put(k, rawValue);
                }
                resolved.add(result);
            } else {
                // 多值
                List<Object> result = new ArrayList<>();
                for (Map<String, Object> rawValue : val) {
                    Map<String, Object> iGroup = (Map<String, Object>) rawValue.get("i_group");
                    Map<String, Object> vocab = iGroup != null ? (Map<String, Object>) iGroup.get("vocab") : null;
                    
                    if (vocab != null) {
                        Map<String, Object> vocabField = (Map<String, Object>) vocab.get("field");
                        if (vocabField.containsKey("composite")) {
                            Object vocabData = vocab.get("data");
                            Map<String, Object> compositeResult = new HashMap<>((Map<String, Object>) vocabData);
                            compositeResult.put((String) vocabField.get("composite"), rawValue.get("name"));
                            result.add(compositeResult);
                        } else {
                            result.add(rawValue.get("name"));
                        }
                    } else {
                        result.add(rawValue);
                    }
                }
                resolved.add(Map.of(k, result));
            }
        }

        // 检查未解析的ID
        List<String> unresolvedIds = expectedIds.stream()
                .filter(id -> !resolvedIds.contains(id))
                .collect(Collectors.toList());
        
        List<String> expectedUnresolvedIds = unresolvedIds.stream()
                .filter(u -> u.equals(input.get("fromId")) || u.equals(input.get("toId")))
                .collect(Collectors.toList());
        
        if (!expectedUnresolvedIds.isEmpty()) {
            throw new RuntimeException("Missing reference error, unresolvedIds: " + expectedUnresolvedIds);
        }

        // 合并结果
        Map<String, Object> complete = new HashMap<>(cleanedInput);
        complete.put("entity_type", type);
        
        Map<String, Object> mergedResolved = new HashMap<>();
        for (Map<String, Object> r : resolved) {
            mergedResolved.putAll(r);
        }
        
        Map<String, Object> inputResolved = new HashMap<>(complete);
        inputResolved.putAll(mergedResolved);

        // 检查标记权限
        if (!isBypassUser(user) && inputResolved.containsKey(INPUT_MARKINGS)) {
            List<Map<String, Object>> inputMarkings = (List<Map<String, Object>>) inputResolved.get(INPUT_MARKINGS);
            List<String> inputMarkingIds = inputMarkings.stream()
                    .map(m -> (String) m.get("internal_id"))
                    .collect(Collectors.toList());
            
            List<String> userMarkingIds = getUserAllowedMarkingIds(user);
            
            if (!userMarkingIds.containsAll(inputMarkingIds)) {
                throw new RuntimeException(
                        "User trying to create the data has missing markings");
            }
        }

        // 检查created_by
        Map<String, Object> inputCreatedBy = (Map<String, Object>) inputResolved.get(INPUT_CREATED_BY);
        if (inputCreatedBy != null) {
            String createdByType = (String) inputCreatedBy.get("entity_type");
            if (!isStixDomainObjectIdentity(createdByType)) {
                throw new IllegalArgumentException(
                        "CreatedBy relation must be an Identity entity, entityType: " + createdByType);
            }
        }

        return inputResolved;
    }

    /**
     * 转换补丁为输入
     * 原文件: middleware.js:1635-1648 transformPatchToInput
     */
    public List<Map<String, Object>> transformPatchToInput(
            Map<String, Object> patch, Map<String, String> operations) {
        List<Map<String, Object>> inputs = new ArrayList<>();
        
        for (Map.Entry<String, Object> entry : patch.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String operation = operations != null ? operations.getOrDefault(key, "replace") : "replace";
            
            List<Object> valueList;
            if (value instanceof List) {
                valueList = (List<Object>) value;
            } else {
                valueList = Collections.singletonList(value);
            }
            
            Map<String, Object> input = new HashMap<>();
            input.put("key", key);
            input.put("value", valueList);
            input.put("operation", operation);
            inputs.add(input);
        }
        
        return inputs;
    }

    // ==================== 私有辅助方法 ====================

    private boolean isAlreadyResolved(Object id) {
        if (id instanceof List) {
            List<?> list = (List<?>) id;
            if (!list.isEmpty()) {
                Object first = list.get(0);
                return first instanceof Map && ((Map<?, ?>) first).containsKey("_id");
            }
            return false;
        }
        return id instanceof Map && ((Map<?, ?>) id).containsKey("_id");
    }

    private boolean isEmpty(Object value) {
        if (value == null) return true;
        if (value instanceof String) return ((String) value).isEmpty();
        if (value instanceof Collection) return ((Collection<?>) value).isEmpty();
        if (value instanceof Map) return ((Map<?, ?>) value).isEmpty();
        return false;
    }

    private boolean isAnId(String value) {
        return value != null && value.contains("--");
    }

    private String generateStandardId(String type, Map<String, Object> data) {
        return type + "--" + UUID.randomUUID().toString();
    }

    private String inferIndexFromConceptType(String type) {
        return type.toLowerCase();
    }

    private String idLabel(String label) {
        if (isAnId(label)) {
            return label;
        }
        return "label--" + label.toLowerCase().replace(" ", "-");
    }

    private boolean isEntityFieldAnOpenVocabulary(String field, String type) {
        return false;
    }

    private Map<String, Object> getVocabularyCategoryForField(String field, String type) {
        return Map.of("category", "generic", "field", Map.of());
    }

    private boolean isStixDomainObjectIdentity(String entityType) {
        return "Identity".equals(entityType) || 
               "Individual".equals(entityType) ||
               "Organization".equals(entityType) ||
               "Sector".equals(entityType) ||
               "System".equals(entityType) ||
               "Country".equals(entityType) ||
               "Region".equals(entityType) ||
               "City".equals(entityType);
    }

    private boolean isStixRefRelationship(String type) {
        return type != null && type.startsWith("stix-ref-relationship");
    }

    private List<Map<String, Object>> getRegisteredDepsKeys() {
        return Collections.emptyList();
    }

    private List<String> getSchemaRelationRefInputNames(String type) {
        return Collections.emptyList();
    }

    private Map<String, Object> internalLoadById(MiddlewareContext context, Object user, String id) {
        return middlewareLoader.storeLoadByIdWithRefs(context, user, id, null);
    }

    private List<Map<String, Object>> internalFindByIds(MiddlewareContext context, Object user, List<String> ids) {
        Map<String, Map<String, Object>> map = middlewareLoader.elFindByIds(context, user, ids, Map.of());
        return new ArrayList<>(map.values());
    }

    private List<String> getInstanceIds(Map<String, Object> element) {
        List<String> ids = new ArrayList<>();
        String internalId = (String) element.get("internal_id");
        if (internalId != null) ids.add(internalId);
        String standardId = (String) element.get("standard_id");
        if (standardId != null && !standardId.equals(internalId)) ids.add(standardId);
        String stixId = (String) element.get("stix_id");
        if (stixId != null && !stixId.equals(internalId) && !stixId.equals(standardId)) ids.add(stixId);
        return ids;
    }

    private Map<String, Object> getSchemaAttribute(String type, String key) {
        return null;
    }

    private Map<String, Object> getSchemaRelationRef(String type, String key) {
        return Map.of("multiple", true);
    }

    private boolean isBypassUser(Object user) {
        return false;
    }

    private List<String> getUserAllowedMarkingIds(Object user) {
        return Collections.emptyList();
    }
}
