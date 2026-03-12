package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX User Account object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixUserAccount extends StixCyberObject {
    public static final String TYPE = "user-account";

    @JsonProperty("user_id")
    private String userId;
    private String credential;
    @JsonProperty("account_login")
    private String accountLogin;
    @JsonProperty("account_type")
    private String accountType;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("is_service_account")
    private Boolean isServiceAccount;
    @JsonProperty("is_privileged")
    private Boolean isPrivileged;
    @JsonProperty("can_escalate_privs")
    private Boolean canEscalatePrivs;
    @JsonProperty("is_disabled")
    private Boolean isDisabled;
    @JsonProperty("account_created")
    private String accountCreated;
    @JsonProperty("account_expires")
    private String accountExpires;
    @JsonProperty("credential_last_changed")
    private String credentialLastChanged;
    @JsonProperty("account_first_login")
    private String accountFirstLogin;
    @JsonProperty("account_last_login")
    private String accountLastLogin;

    public StixUserAccount() {
        this.type = TYPE;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getIsServiceAccount() {
        return isServiceAccount;
    }

    public void setIsServiceAccount(Boolean isServiceAccount) {
        this.isServiceAccount = isServiceAccount;
    }

    public Boolean getIsPrivileged() {
        return isPrivileged;
    }

    public void setIsPrivileged(Boolean isPrivileged) {
        this.isPrivileged = isPrivileged;
    }

    public Boolean getCanEscalatePrivs() {
        return canEscalatePrivs;
    }

    public void setCanEscalatePrivs(Boolean canEscalatePrivs) {
        this.canEscalatePrivs = canEscalatePrivs;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(String accountCreated) {
        this.accountCreated = accountCreated;
    }

    public String getAccountExpires() {
        return accountExpires;
    }

    public void setAccountExpires(String accountExpires) {
        this.accountExpires = accountExpires;
    }

    public String getCredentialLastChanged() {
        return credentialLastChanged;
    }

    public void setCredentialLastChanged(String credentialLastChanged) {
        this.credentialLastChanged = credentialLastChanged;
    }

    public String getAccountFirstLogin() {
        return accountFirstLogin;
    }

    public void setAccountFirstLogin(String accountFirstLogin) {
        this.accountFirstLogin = accountFirstLogin;
    }

    public String getAccountLastLogin() {
        return accountLastLogin;
    }

    public void setAccountLastLogin(String accountLastLogin) {
        this.accountLastLogin = accountLastLogin;
    }
}
