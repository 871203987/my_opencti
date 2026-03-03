package io.opencti.common.types.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;

import java.util.List;
import java.util.Map;

/**
 * User type.
 * Original file: opencti-platform/opencti-graphql/src/types/user.d.ts
 * Represents a user in the OpenCTI platform.
 */
public class User {
    public static final String TYPE = "User";

    private String id;
    private String name;
    private String email;
    private String firstname;
    private String lastname;
    private String description;
    private String accountStatus;
    private List<String> roles;
    private List<String> groups;
    private List<String> capabilities;
    private List<String> organizations;
    private String defaultHiddenTypes;
    private Map<String, Object> userConfidenceLevel;
    private StixDate createdAt;
    private StixDate updatedAt;
    private StixDate lastLogin;
    private Integer loginAttempts;
    private Boolean isAdmin;
    private Boolean effectiveDefaultHiddenTypes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("account_status")
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    public List<String> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<String> organizations) {
        this.organizations = organizations;
    }

    @JsonProperty("default_hidden_types")
    public String getDefaultHiddenTypes() {
        return defaultHiddenTypes;
    }

    public void setDefaultHiddenTypes(String defaultHiddenTypes) {
        this.defaultHiddenTypes = defaultHiddenTypes;
    }

    @JsonProperty("user_confidence_level")
    public Map<String, Object> getUserConfidenceLevel() {
        return userConfidenceLevel;
    }

    public void setUserConfidenceLevel(Map<String, Object> userConfidenceLevel) {
        this.userConfidenceLevel = userConfidenceLevel;
    }

    @JsonProperty("created_at")
    public StixDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(StixDate createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public StixDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(StixDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("last_login")
    public StixDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(StixDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    @JsonProperty("login_attempts")
    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    @JsonProperty("is_admin")
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @JsonProperty("effective_default_hidden_types")
    public Boolean getEffectiveDefaultHiddenTypes() {
        return effectiveDefaultHiddenTypes;
    }

    public void setEffectiveDefaultHiddenTypes(Boolean effectiveDefaultHiddenTypes) {
        this.effectiveDefaultHiddenTypes = effectiveDefaultHiddenTypes;
    }
}
