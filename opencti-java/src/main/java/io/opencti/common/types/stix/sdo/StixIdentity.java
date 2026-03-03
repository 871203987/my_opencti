package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDomainObject;

import java.util.List;

/**
 * STIX Identity type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixIdentity extends StixDomainObject {
 *   name: string;
 *   description: string;
 *   roles: Array<string>;
 *   identity_class: string;
 *   sectors: Array<string>;
 *   contact_information: string;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixIdentityExtension;
 *   };
 * }
 */
public class StixIdentity extends StixDomainObject {
    public static final String TYPE = "identity";

    public static final String CLASS_INDIVIDUAL = "individual";
    public static final String CLASS_GROUP = "group";
    public static final String CLASS_SYSTEM = "system";
    public static final String CLASS_ORGANIZATION = "organization";
    public static final String CLASS_CLASS = "class";
    public static final String CLASS_UNKNOWN = "unknown";

    private List<String> roles;
    @JsonProperty("identity_class")
    private String identityClass;
    private List<String> sectors;
    @JsonProperty("contact_information")
    private String contactInformation;
    private StixIdentityExtension identityExtension;

    public StixIdentity() {
        this.type = TYPE;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getIdentityClass() {
        return identityClass;
    }

    public void setIdentityClass(String identityClass) {
        this.identityClass = identityClass;
    }

    public List<String> getSectors() {
        return sectors;
    }

    public void setSectors(List<String> sectors) {
        this.sectors = sectors;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public StixIdentityExtension getIdentityExtension() {
        return identityExtension;
    }

    public void setIdentityExtension(StixIdentityExtension identityExtension) {
        this.identityExtension = identityExtension;
    }
}
