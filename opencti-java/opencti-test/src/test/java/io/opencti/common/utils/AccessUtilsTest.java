package io.opencti.common.utils;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 重写自: opencti-graphql/tests/01-unit/utils/test_access.js
 * 访问控制工具类测试
 */
class AccessUtilsTest {

    @Test
    void testIsBypass() {
        assertTrue(AccessUtils.isBypass(Set.of(AccessConstants.BYPASS)));
        assertTrue(AccessUtils.isBypass(Set.of(AccessConstants.BYPASS, AccessConstants.KNOWLEDGE)));
        assertFalse(AccessUtils.isBypass(Set.of(AccessConstants.KNOWLEDGE)));
        assertFalse(AccessUtils.isBypass(Set.of()));
        assertFalse(AccessUtils.isBypass(null));
    }

    @Test
    void testHasCapability() {
        Set<String> capabilities = Set.of(AccessConstants.KNOWLEDGE, AccessConstants.KNOWLEDGE_KNUPDATE);
        
        assertTrue(AccessUtils.hasCapability(capabilities, AccessConstants.KNOWLEDGE));
        assertTrue(AccessUtils.hasCapability(capabilities, AccessConstants.KNOWLEDGE_KNUPDATE));
        assertFalse(AccessUtils.hasCapability(capabilities, AccessConstants.BYPASS));
        assertFalse(AccessUtils.hasCapability(null, AccessConstants.KNOWLEDGE));
        assertFalse(AccessUtils.hasCapability(capabilities, null));
    }

    @Test
    void testHasCapabilityWithWildcard() {
        Set<String> capabilities = Set.of(AccessConstants.KNOWLEDGE, AccessConstants.KNOWLEDGE_KNUPDATE);
        
        assertTrue(AccessUtils.hasCapabilityWithWildcard(capabilities, "KNOWLEDGE*"));
        assertTrue(AccessUtils.hasCapabilityWithWildcard(capabilities, AccessConstants.KNOWLEDGE));
        assertFalse(AccessUtils.hasCapabilityWithWildcard(capabilities, "SETTINGS*"));
    }

    @Test
    void testIsOnlyOrgAdmin() {
        assertTrue(AccessUtils.isOnlyOrgAdmin(Set.of(AccessConstants.VIRTUAL_ORGANIZATION_ADMIN)));
        assertFalse(AccessUtils.isOnlyOrgAdmin(Set.of(AccessConstants.VIRTUAL_ORGANIZATION_ADMIN, AccessConstants.KNOWLEDGE)));
        assertFalse(AccessUtils.isOnlyOrgAdmin(Set.of(AccessConstants.KNOWLEDGE)));
        assertFalse(AccessUtils.isOnlyOrgAdmin(null));
        assertFalse(AccessUtils.isOnlyOrgAdmin(Set.of()));
    }

    @Test
    void testIsOrganizationAllowed() {
        Set<String> allowedOrgs = Set.of("org1", "org2", "org3");
        
        assertTrue(AccessUtils.isOrganizationAllowed(allowedOrgs, "org1"));
        assertTrue(AccessUtils.isOrganizationAllowed(allowedOrgs, "org2"));
        assertFalse(AccessUtils.isOrganizationAllowed(allowedOrgs, "org4"));
        assertTrue(AccessUtils.isOrganizationAllowed(null, "org1"));
        assertTrue(AccessUtils.isOrganizationAllowed(Set.of(), "org1"));
    }

    @Test
    void testIsMarkingAllowed() {
        Set<String> allowedMarkings = Set.of("marking1", "marking2");
        
        assertTrue(AccessUtils.isMarkingAllowed(allowedMarkings, "marking1"));
        assertFalse(AccessUtils.isMarkingAllowed(allowedMarkings, "marking3"));
        assertTrue(AccessUtils.isMarkingAllowed(null, "marking1"));
        assertTrue(AccessUtils.isMarkingAllowed(Set.of(), "marking1"));
    }

    @Test
    void testCanEdit() {
        Set<String> bypassCaps = Set.of(AccessConstants.BYPASS);
        Set<String> editCaps = Set.of(AccessConstants.KNOWLEDGE_KNUPDATE);
        Set<String> noEditCaps = Set.of(AccessConstants.KNOWLEDGE);
        
        assertTrue(AccessUtils.canEdit(bypassCaps, "entity1"));
        assertTrue(AccessUtils.canEdit(editCaps, "entity1"));
        assertFalse(AccessUtils.canEdit(noEditCaps, "entity1"));
    }

    @Test
    void testCanDelete() {
        Set<String> bypassCaps = Set.of(AccessConstants.BYPASS);
        Set<String> deleteCaps = Set.of(AccessConstants.KNOWLEDGE_KNUPDATE_KNDELETE);
        Set<String> noDeleteCaps = Set.of(AccessConstants.KNOWLEDGE_KNUPDATE);
        
        assertTrue(AccessUtils.canDelete(bypassCaps, "entity1"));
        assertTrue(AccessUtils.canDelete(deleteCaps, "entity1"));
        assertFalse(AccessUtils.canDelete(noDeleteCaps, "entity1"));
    }

    @Test
    void testCanManageAccess() {
        Set<String> bypassCaps = Set.of(AccessConstants.BYPASS);
        Set<String> manageCaps = Set.of(AccessConstants.KNOWLEDGE_KNUPDATE_KNMANAGEAUTHMEMBERS);
        Set<String> noManageCaps = Set.of(AccessConstants.KNOWLEDGE_KNUPDATE);
        
        assertTrue(AccessUtils.canManageAccess(bypassCaps, "entity1"));
        assertTrue(AccessUtils.canManageAccess(manageCaps, "entity1"));
        assertFalse(AccessUtils.canManageAccess(noManageCaps, "entity1"));
    }
}
