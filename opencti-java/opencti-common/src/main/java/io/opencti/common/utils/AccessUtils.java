package io.opencti.common.utils;

import java.util.Set;

/**
 * 重写自: opencti-graphql/src/utils/access.ts
 * 访问控制工具类
 *
 * TODO: 依赖用户类型定义（Phase 1.3 types/）
 */
public final class AccessUtils {

    private AccessUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 重写自: opencti-graphql/src/utils/access.ts - isBypass
     * 检查用户是否拥有BYPASS权限
     *
     * @param capabilities 用户权限列表
     * @return 如果用户拥有BYPASS权限则返回true
     */
    public static boolean isBypass(Set<String> capabilities) {
        return capabilities != null && capabilities.contains(AccessConstants.BYPASS);
    }

    /**
     * 重写自: opencti-graphql/src/utils/access.ts - hasCapability
     * 检查用户是否拥有指定权限
     *
     * @param capabilities 用户权限列表
     * @param capability   要检查的权限
     * @return 如果用户拥有该权限则返回true
     */
    public static boolean hasCapability(Set<String> capabilities, String capability) {
        if (capabilities == null || capability == null) {
            return false;
        }
        return capabilities.contains(capability);
    }

    /**
     * 重写自: opencti-graphql/src/utils/access.ts - isUserHasCapability
     * 检查用户是否拥有指定权限（支持通配符）
     *
     * @param capabilities 用户权限列表
     * @param capability   要检查的权限（支持KNOWLEDGE*等通配符）
     * @return 如果用户拥有匹配的权限则返回true
     */
    public static boolean hasCapabilityWithWildcard(Set<String> capabilities, String capability) {
        if (capabilities == null || capability == null) {
            return false;
        }
        if (capabilities.contains(capability)) {
            return true;
        }
        if (capability.endsWith("*")) {
            String prefix = capability.substring(0, capability.length() - 1);
            for (String cap : capabilities) {
                if (cap.startsWith(prefix)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 重写自: opencti-graphql/src/utils/access.ts - isOnlyOrgAdmin
     * 检查用户是否仅为组织管理员
     *
     * @param capabilities 用户权限列表
     * @return 如果用户仅有组织管理员权限则返回true
     */
    public static boolean isOnlyOrgAdmin(Set<String> capabilities) {
        if (capabilities == null || capabilities.isEmpty()) {
            return false;
        }
        return capabilities.size() == 1 && 
               capabilities.contains(AccessConstants.VIRTUAL_ORGANIZATION_ADMIN);
    }

    /**
     * 重写自: opencti-graphql/src/utils/access.ts - isOrganizationAllowed
     * 检查组织是否被允许
     *
     * @param allowedOrganizations 允许的组织列表
     * @param organizationId       要检查的组织ID
     * @return 如果组织被允许则返回true
     */
    public static boolean isOrganizationAllowed(Set<String> allowedOrganizations, String organizationId) {
        if (allowedOrganizations == null || allowedOrganizations.isEmpty()) {
            return true;
        }
        return allowedOrganizations.contains(organizationId);
    }

    /**
     * 重写自: opencti-graphql/src/utils/access.ts - isMarkingAllowed
     * 检查数据标记是否被允许
     *
     * @param allowedMarkings 允许的标记列表
     * @param markingId        要检查的标记ID
     * @return 如果标记被允许则返回true
     */
    public static boolean isMarkingAllowed(Set<String> allowedMarkings, String markingId) {
        if (allowedMarkings == null || allowedMarkings.isEmpty()) {
            return true;
        }
        return allowedMarkings.contains(markingId);
    }

    /**
     * 检查用户是否可以编辑实体
     *
     * @param capabilities 用户权限列表
     * @param entityId      实体ID
     * @return 如果用户可以编辑则返回true
     */
    public static boolean canEdit(Set<String> capabilities, String entityId) {
        return isBypass(capabilities) || hasCapability(capabilities, AccessConstants.KNOWLEDGE_KNUPDATE);
    }

    /**
     * 检查用户是否可以删除实体
     *
     * @param capabilities 用户权限列表
     * @param entityId      实体ID
     * @return 如果用户可以删除则返回true
     */
    public static boolean canDelete(Set<String> capabilities, String entityId) {
        return isBypass(capabilities) || hasCapability(capabilities, AccessConstants.KNOWLEDGE_KNUPDATE_KNDELETE);
    }

    /**
     * 检查用户是否可以管理访问权限
     *
     * @param capabilities 用户权限列表
     * @param entityId      实体ID
     * @return 如果用户可以管理访问权限则返回true
     */
    public static boolean canManageAccess(Set<String> capabilities, String entityId) {
        return isBypass(capabilities) || hasCapability(capabilities, AccessConstants.KNOWLEDGE_KNUPDATE_KNMANAGEAUTHMEMBERS);
    }
}
