package io.opencti.database.middleware;

import io.opencti.database.middleware.model.MiddlewareContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static io.opencti.database.middleware.MiddlewareConstants.FROM_START_STR;

/**
 * 中间件访问控制模块
 * 原文件: database/middleware.js:252-280, 580-597
 * 
 * 提供访问控制相关功能，包括权限检查、受限实体构建等。
 */
@Component
public class MiddlewareAccess {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareAccess.class);

    private final MiddlewareLoader middlewareLoader;

    public MiddlewareAccess(MiddlewareLoader middlewareLoader) {
        this.middlewareLoader = middlewareLoader;
    }

    /**
     * 检查是否可请求访问
     * 重写自: middleware.js:252-280 canRequestAccess
     * 
     * @param context 中间件上下文
     * @param user 用户对象
     * @param elements 要检查的元素列表
     * @return 需要请求访问的元素列表
     */
    public List<Map<String, Object>> canRequestAccess(
            MiddlewareContext context, Object user,
            List<Map<String, Object>> elements) {
        
        // 获取平台设置
        Map<String, Object> settings = getEntityFromCache(context, user, "Settings");
        boolean hasPlatformOrg = settings != null && settings.get("platform_organization") != null;
        
        List<Map<String, Object>> elementsThatRequiresAccess = new ArrayList<>();
        
        for (Map<String, Object> currentElement : elements) {
            // 检查组织是否允许访问
            if (!isOrganizationAllowed(context, currentElement, user, hasPlatformOrg)) {
                // 检查组是否有标记权限，否则请求访问RFI将无用
                Map<String, Object> requestAccessSettings = loadEntity(context, user, 
                        Collections.singletonList("EntitySetting"),
                        buildRequestAccessFilters());
                
                if (requestAccessSettings != null) {
                    Map<String, Object> requestAccessWorkflow = (Map<String, Object>) 
                            requestAccessSettings.get("request_access_workflow");
                    
                    if (requestAccessWorkflow != null) {
                        List<String> approvalAdmins = (List<String>) requestAccessWorkflow.get("approval_admin");
                        
                        if (approvalAdmins != null && !approvalAdmins.isEmpty()) {
                            String adminGroupId = approvalAdmins.get(0);
                            
                            // 获取管理员组的标记
                            List<Map<String, Object>> adminGroupMarkings = fullEntitiesThroughRelationsToList(
                                    context, user, adminGroupId, "accesses_to", "MarkingDefinition");
                            
                            List<String> authorizedGroupMarkings = adminGroupMarkings.stream()
                                    .map(m -> (String) m.get("internal_id"))
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());
                            
                            // 检查标记是否允许
                            if (isMarkingAllowed(currentElement, authorizedGroupMarkings)) {
                                elementsThatRequiresAccess.add(currentElement);
                            }
                        }
                    }
                }
            }
        }
        
        return elementsThatRequiresAccess;
    }

    /**
     * 构建受限实体
     * 重写自: middleware.js:580-597 buildRestrictedEntity
     * 
     * 受限实体需要能够通过API查询，我们保留所有实体属性，但限制其值。
     * 
     * @param resolvedEntity 已解析的实体
     * @return 受限实体
     */
    public Map<String, Object> buildRestrictedEntity(Map<String, Object> resolvedEntity) {
        // 首先创建实体的深拷贝
        Map<String, Object> restrictedEntity = deepCopy(resolvedEntity);
        
        // 对实体的每个属性，限制其值：用假默认值混淆真实值
        for (String item : new ArrayList<>(restrictedEntity.keySet())) {
            Object value = restrictedEntity.get(item);
            restrictedEntity.put(item, value != null ? restrictValue(value) : value);
        }
        
        // 返回带有额外受限数据的受限实体
        Map<String, Object> result = new HashMap<>(restrictedEntity);
        result.put("id", resolvedEntity.get("internal_id"));
        result.put("name", "Restricted");
        result.put("entity_type", resolvedEntity.get("entity_type"));
        result.put("parent_types", resolvedEntity.get("parent_types"));
        
        // 设置代表性信息
        Map<String, String> representative = new HashMap<>();
        representative.put("main", "Restricted");
        representative.put("secondary", "Restricted");
        result.put("representative", representative);
        
        return result;
    }

    /**
     * 限制值
     * 重写自: middleware.js:567-576 restrictValue
     * 
     * @param entityValue 实体值
     * @return 限制后的值
     */
    private Object restrictValue(Object entityValue) {
        // 如果是数组，返回空数组
        if (entityValue instanceof List) {
            return Collections.emptyList();
        }
        
        // 如果是有效日期，返回起始时间
        if (isValidDate(entityValue)) {
            return FROM_START_STR;
        }
        
        // 根据类型返回限制值
        if (entityValue instanceof String) {
            return "Restricted";
        } else if (entityValue instanceof Map) {
            return null;
        } else {
            return null;
        }
    }

    /**
     * 检查用户是否有权限访问元素
     */
    public boolean hasAccess(Object user, Map<String, Object> element, String operation) {
        if (isBypassUser(user)) {
            return true;
        }

        // 检查组织权限
        if (!isOrganizationAllowed(null, element, user, true)) {
            return false;
        }

        // 检查标记权限
        if (!hasRequiredMarkings(user, element)) {
            return false;
        }

        return true;
    }

    /**
     * 验证用户访问操作
     */
    public void validateUserAccessOperation(Object user, Map<String, Object> element, String operation) {
        if (!hasAccess(user, element, operation)) {
            throw new RuntimeException("Access denied for operation: " + operation + 
                    " on element: " + element.get("internal_id"));
        }
    }

    /**
     * 过滤用户可访问的元素
     */
    public List<Map<String, Object>> filterAccessibleElements(
            Object user, List<Map<String, Object>> elements, String operation) {
        return elements.stream()
                .filter(e -> hasAccess(user, e, operation))
                .collect(Collectors.toList());
    }

    // ==================== 私有辅助方法 ====================

    private Map<String, Object> buildRequestAccessFilters() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("mode", "and");
        
        List<Map<String, Object>> filterList = new ArrayList<>();
        Map<String, Object> filter = new HashMap<>();
        filter.put("key", Collections.singletonList("target_type"));
        filter.put("values", Collections.singletonList("ContainerCaseRfi"));
        filterList.add(filter);
        
        filters.put("filters", filterList);
        filters.put("filterGroups", Collections.emptyList());
        
        return filters;
    }

    private boolean isOrganizationAllowed(MiddlewareContext context, Map<String, Object> element, 
            Object user, boolean hasPlatformOrg) {
        if (user == null) {
            return false;
        }

        // 如果没有平台组织限制，允许访问
        if (!hasPlatformOrg) {
            return true;
        }

        // 获取用户组织
        List<String> userOrganizations = getUserOrganizations(user);
        if (userOrganizations.isEmpty()) {
            return true; // 没有组织限制的用户可以访问所有
        }

        // 获取元素组织
        List<String> elementOrganizations = getElementOrganizations(element);
        if (elementOrganizations.isEmpty()) {
            return true; // 没有组织限制的元素可以被所有访问
        }

        // 检查是否有交集
        for (String org : userOrganizations) {
            if (elementOrganizations.contains(org)) {
                return true;
            }
        }

        return false;
    }

    private boolean isMarkingAllowed(Map<String, Object> element, List<String> authorizedMarkings) {
        if (authorizedMarkings == null || authorizedMarkings.isEmpty()) {
            return false;
        }
        
        List<String> elementMarkings = getElementMarkings(element);
        if (elementMarkings.isEmpty()) {
            return true;
        }
        
        // 检查是否有任何交集
        for (String marking : elementMarkings) {
            if (authorizedMarkings.contains(marking)) {
                return true;
            }
        }
        
        return false;
    }

    private boolean hasRequiredMarkings(Object user, Map<String, Object> element) {
        if (user == null) {
            return false;
        }

        // 获取用户标记
        List<String> userMarkings = getUserMarkings(user);
        if (userMarkings.isEmpty()) {
            return true; // 没有标记限制的用户可以访问所有
        }

        // 获取元素标记
        List<String> elementMarkings = getElementMarkings(element);
        if (elementMarkings.isEmpty()) {
            return true; // 没有标记限制的元素可以被所有访问
        }

        // 检查用户是否拥有所有元素标记
        return userMarkings.containsAll(elementMarkings);
    }

    private boolean isBypassUser(Object user) {
        if (user instanceof Map) {
            Map<?, ?> userMap = (Map<?, ?>) user;
            Object capabilities = userMap.get("capabilities");
            if (capabilities instanceof List) {
                return ((List<?>) capabilities).contains("BYPASS");
            }
        }
        return false;
    }

    private List<String> getUserOrganizations(Object user) {
        if (user instanceof Map) {
            Map<?, ?> userMap = (Map<?, ?>) user;
            Object organizations = userMap.get("organizations");
            if (organizations instanceof List) {
                return ((List<?>) organizations).stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    private List<String> getElementOrganizations(Map<String, Object> element) {
        Object organizations = element.get("authorized_members");
        if (organizations instanceof List) {
            return ((List<?>) organizations).stream()
                    .filter(m -> m instanceof Map)
                    .map(m -> (Map<?, ?>) m)
                    .filter(m -> "Organization".equals(m.get("entity_type")))
                    .map(m -> m.get("internal_id"))
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private List<String> getUserMarkings(Object user) {
        if (user instanceof Map) {
            Map<?, ?> userMap = (Map<?, ?>) user;
            Object markings = userMap.get("allowed_marking");
            if (markings instanceof List) {
                return ((List<?>) markings).stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    private List<String> getElementMarkings(Map<String, Object> element) {
        Object markings = element.get("object_marking_refs");
        if (markings instanceof List) {
            return ((List<?>) markings).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private boolean isValidDate(Object value) {
        if (value instanceof String) {
            String strValue = (String) value;
            try {
                ZonedDateTime.parse(strValue, DateTimeFormatter.ISO_DATE_TIME);
                return true;
            } catch (Exception e) {
                try {
                    ZonedDateTime.parse(strValue);
                    return true;
                } catch (Exception e2) {
                    return false;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> deepCopy(Map<String, Object> original) {
        Map<String, Object> copy = new HashMap<>();
        for (Map.Entry<String, Object> entry : original.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                copy.put(entry.getKey(), deepCopy((Map<String, Object>) value));
            } else if (value instanceof List) {
                copy.put(entry.getKey(), new ArrayList<>((List<?>) value));
            } else {
                copy.put(entry.getKey(), value);
            }
        }
        return copy;
    }

    // ==================== 占位方法（需要后续实现） ====================

    /**
     * 从缓存获取实体
     * TODO: 实现缓存获取逻辑
     */
    private Map<String, Object> getEntityFromCache(MiddlewareContext context, Object user, String entityType) {
        // 占位实现
        return null;
    }

    /**
     * 加载实体
     * TODO: 实现实体加载逻辑
     */
    private Map<String, Object> loadEntity(MiddlewareContext context, Object user, 
            List<String> entityTypes, Map<String, Object> filters) {
        // 占位实现
        return null;
    }

    /**
     * 通过关系获取完整实体列表
     * TODO: 实现关系查询逻辑
     */
    private List<Map<String, Object>> fullEntitiesThroughRelationsToList(
            MiddlewareContext context, Object user, String fromId, 
            String relationType, String toType) {
        // 占位实现
        return Collections.emptyList();
    }
}
