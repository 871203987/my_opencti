package io.opencti.common.utils;

/**
 * 重写自: opencti-graphql/src/utils/access.ts
 * 访问控制常量类
 */
public final class AccessConstants {

    public static final String DEFAULT_INVALID_CONF_VALUE = "ChangeMe";

    // 权限常量
    public static final String BYPASS = "BYPASS";
    public static final String KNOWLEDGE_KNUPDATE_KNBYPASSREFERENCE = "KNOWLEDGE_KNUPDATE_KNBYPASSREFERENCE";
    public static final String KNOWLEDGE_KNUPDATE_KNBYPASSFIELDS = "KNOWLEDGE_KNUPDATE_KNBYPASSFIELDS";
    public static final String SETTINGS_SET_ACCESSES = "SETTINGS_SETACCESSES";
    public static final String SETTINGS_SETPARAMETERS = "SETTINGS_SETPARAMETERS";
    public static final String SETTINGS_SETMANAGEXTMHUB = "SETTINGS_SETMANAGEXTMHUB";
    public static final String SETTINGS_SUPPORT = "SETTINGS_SUPPORT";
    public static final String TAXIIAPI_SETCOLLECTIONS = "TAXIIAPI_SETCOLLECTIONS";
    public static final String INGESTION_SETINGESTIONS = "INGESTION_SETINGESTIONS";
    public static final String CSVMAPPERS = "CSVMAPPERS";
    public static final String KNOWLEDGE = "KNOWLEDGE";
    public static final String KNOWLEDGE_KNPARTICIPATE = "KNOWLEDGE_KNPARTICIPATE";
    public static final String KNOWLEDGE_KNUPDATE = "KNOWLEDGE_KNUPDATE";
    public static final String KNOWLEDGE_ORGANIZATION_RESTRICT = "KNOWLEDGE_KNUPDATE_KNORGARESTRICT";
    public static final String KNOWLEDGE_KNUPDATE_KNDELETE = "KNOWLEDGE_KNUPDATE_KNDELETE";
    public static final String KNOWLEDGE_KNUPDATE_KNMERGE = "KNOWLEDGE_KNUPDATE_KNMERGE";
    public static final String KNOWLEDGE_KNUPDATE_KNMANAGEAUTHMEMBERS = "KNOWLEDGE_KNUPDATE_KNMANAGEAUTHMEMBERS";
    public static final String KNOWLEDGE_KNUPLOAD = "KNOWLEDGE_KNUPLOAD";
    public static final String KNOWLEDGE_KNASKIMPORT = "KNOWLEDGE_KNASKIMPORT";
    public static final String KNOWLEDGE_KNENRICHMENT = "KNOWLEDGE_KNENRICHMENT";
    public static final String KNOWLEDGE_KNDISSEMINATION = "KNOWLEDGE_KNDISSEMINATION";
    public static final String VIRTUAL_ORGANIZATION_ADMIN = "VIRTUAL_ORGANIZATION_ADMIN";
    public static final String SETTINGS_SETACCESSES = "SETTINGS_SETACCESSES";
    public static final String SETTINGS_SECURITYACTIVITY = "SETTINGS_SECURITYACTIVITY";
    public static final String SETTINGS_SETLABELS = "SETTINGS_SETLABELS";
    public static final String PIRAPI = "PIRAPI";
    public static final String AUTOMATION = "AUTOMATION";
    public static final String AUTOMATION_AUTMANAGE = "AUTOMATION_AUTMANAGE";

    // 系统用户UUID
    public static final String OPENCTI_SYSTEM_UUID = "6c0d83b5-17b6-4c2c-8a0d-61f0e91b1c6d";
    public static final String CONTAINER_SHARING_USER_UUID = "cc3aef5c-6f05-434b-8bdf-8d370046f017";
    public static final String RETENTION_MANAGER_USER_UUID = "82ed2c6c-eb27-498e-b904-4f2abc04e05f";
    public static final String EXPIRATION_MANAGER_USER_UUID = "21763151-f598-4f49-97c5-9051b2d25a5c";
    public static final String RULE_MANAGER_USER_UUID = "f9d7b43f-b208-4c56-8637-375a1ce84943";
    public static final String AUTOMATION_MANAGER_USER_UUID = "c49fe040-2dad-412d-af07-ce639204ad55";
    public static final String DECAY_MANAGER_USER_UUID = "7f176d74-9084-4d23-8138-22ac78549547";
    public static final String GARBAGE_COLLECTION_MANAGER_USER_UUID = "c30d12be-d5fb-4724-88e7-8a7c9a4516c2";
    public static final String TELEMETRY_MANAGER_USER_UUID = "c30d12be-d5fb-4724-88e7-8a7c9a4516c3";
    public static final String REDACTED_USER_UUID = "31afac4e-6b99-44a0-b91b-e04738d31461";
    public static final String RESTRICTED_USER_UUID = "27d2b0af-4d1e-42ae-a50c-9691bf57f35d";
    public static final String PIR_MANAGER_USER_UUID = "1e20b6e5-e0f7-46f2-bacb-c37e4f8707a2";
    public static final String HUB_REGISTRATION_MANAGER_USER_UUID = "e16d7175-17c7-4dae-bd3c-48c939f47dfb";

    // 角色常量
    public static final String ROLE_DEFAULT = "Default";
    public static final String ROLE_ADMINISTRATOR = "Administrator";

    // 访问权限级别
    public static final String MEMBER_ACCESS_ALL = "ALL";
    public static final String MEMBER_ACCESS_CREATOR = "CREATOR";
    public static final String MEMBER_ACCESS_RIGHT_ADMIN = "admin";
    public static final String MEMBER_ACCESS_RIGHT_EDIT = "edit";
    public static final String MEMBER_ACCESS_RIGHT_USE = "use";
    public static final String MEMBER_ACCESS_RIGHT_VIEW = "view";

    private AccessConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 访问操作枚举
     */
    public enum AccessOperation {
        EDIT("edit"),
        DELETE("delete"),
        MANAGE_ACCESS("manage-access"),
        MANAGE_AUTHORITIES_ACCESS("manage-authorities-access");

        private final String value;

        AccessOperation(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 成员访问权限枚举
     */
    public enum MemberAccessRight {
        VIEW("view"),
        USE("use"),
        EDIT("edit"),
        ADMIN("admin");

        private final String value;

        MemberAccessRight(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static MemberAccessRight fromValue(String value) {
            for (MemberAccessRight right : values()) {
                if (right.value.equalsIgnoreCase(value)) {
                    return right;
                }
            }
            return VIEW;
        }
    }
}
