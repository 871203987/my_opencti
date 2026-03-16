package io.opencti.schema.stix;

import io.opencti.schema.SchemaTypesDefinition;
import io.opencti.schema.general.SchemaGeneral;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * STIX 网络可观测对象 (Stix-Cyber-Observable) Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/stixCyberObservable.ts
 *
 * 该文件定义了STIX网络可观测对象的类型常量，包括标准STIX类型和OpenCTI自定义类型，
 * 共37种类型。这些可观测对象是威胁情报分析的基础数据。
 */
public final class StixCyberObservableSchema {

    private StixCyberObservableSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== 标准STIX网络可观测对象类型常量 ====================
    // 原代码: export const ENTITY_AUTONOMOUS_SYSTEM = 'Autonomous-System';
    public static final String ENTITY_AUTONOMOUS_SYSTEM = "Autonomous-System";

    // 原代码: export const ENTITY_DIRECTORY = 'Directory';
    public static final String ENTITY_DIRECTORY = "Directory";

    // 原代码: export const ENTITY_DOMAIN_NAME = 'Domain-Name';
    public static final String ENTITY_DOMAIN_NAME = "Domain-Name";

    // 原代码: export const ENTITY_EMAIL_ADDR = 'Email-Addr';
    public static final String ENTITY_EMAIL_ADDR = "Email-Addr";

    // 原代码: export const ENTITY_EMAIL_MESSAGE = 'Email-Message';
    public static final String ENTITY_EMAIL_MESSAGE = "Email-Message";

    // 原代码: export const ENTITY_EMAIL_MIME_PART_TYPE = 'Email-Mime-Part-Type';
    public static final String ENTITY_EMAIL_MIME_PART_TYPE = "Email-Mime-Part-Type";

    // 原代码: export const ENTITY_HASHED_OBSERVABLE_ARTIFACT = 'Artifact';
    public static final String ENTITY_HASHED_OBSERVABLE_ARTIFACT = "Artifact";

    // 原代码: export const ENTITY_HASHED_OBSERVABLE_STIX_FILE = 'StixFile';
    public static final String ENTITY_HASHED_OBSERVABLE_STIX_FILE = "StixFile";

    // 原代码: export const ENTITY_HASHED_OBSERVABLE_X509_CERTIFICATE = 'X509-Certificate';
    public static final String ENTITY_HASHED_OBSERVABLE_X509_CERTIFICATE = "X509-Certificate";

    // 原代码: export const ENTITY_IPV4_ADDR = 'IPv4-Addr';
    public static final String ENTITY_IPV4_ADDR = "IPv4-Addr";

    // 原代码: export const ENTITY_IPV6_ADDR = 'IPv6-Addr';
    public static final String ENTITY_IPV6_ADDR = "IPv6-Addr";

    // 原代码: export const ENTITY_MAC_ADDR = 'Mac-Addr';
    public static final String ENTITY_MAC_ADDR = "Mac-Addr";

    // 原代码: export const ENTITY_MUTEX = 'Mutex';
    public static final String ENTITY_MUTEX = "Mutex";

    // 原代码: export const ENTITY_NETWORK_TRAFFIC = 'Network-Traffic';
    public static final String ENTITY_NETWORK_TRAFFIC = "Network-Traffic";

    // 原代码: export const ENTITY_PROCESS = 'Process';
    public static final String ENTITY_PROCESS = "Process";

    // 原代码: export const ENTITY_SOFTWARE = 'Software';
    public static final String ENTITY_SOFTWARE = "Software";

    // 原代码: export const ENTITY_URL = 'Url';
    public static final String ENTITY_URL = "Url";

    // 原代码: export const ENTITY_USER_ACCOUNT = 'User-Account';
    public static final String ENTITY_USER_ACCOUNT = "User-Account";

    // 原代码: export const ENTITY_WINDOWS_REGISTRY_KEY = 'Windows-Registry-Key';
    public static final String ENTITY_WINDOWS_REGISTRY_KEY = "Windows-Registry-Key";

    // 原代码: export const ENTITY_WINDOWS_REGISTRY_VALUE_TYPE = 'Windows-Registry-Value-Type';
    public static final String ENTITY_WINDOWS_REGISTRY_VALUE_TYPE = "Windows-Registry-Value-Type";

    // ==================== OpenCTI自定义网络可观测对象类型常量 ====================
    // 原代码: export const ENTITY_CRYPTOGRAPHIC_KEY = 'Cryptographic-Key'; // Custom
    public static final String ENTITY_CRYPTOGRAPHIC_KEY = "Cryptographic-Key";

    // 原代码: export const ENTITY_CRYPTOGRAPHIC_WALLET = 'Cryptocurrency-Wallet'; // Custom
    public static final String ENTITY_CRYPTOGRAPHIC_WALLET = "Cryptocurrency-Wallet";

    // 原代码: export const ENTITY_HOSTNAME = 'Hostname'; // Custom
    public static final String ENTITY_HOSTNAME = "Hostname";

    // 原代码: export const ENTITY_TEXT = 'Text'; // Custom
    public static final String ENTITY_TEXT = "Text";

    // 原代码: export const ENTITY_CREDENTIAL = 'Credential'; // Custom
    public static final String ENTITY_CREDENTIAL = "Credential";

    // 原代码: export const ENTITY_USER_AGENT = 'User-Agent'; // Custom
    public static final String ENTITY_USER_AGENT = "User-Agent";

    // 原代码: export const ENTITY_BANK_ACCOUNT = 'Bank-Account'; // Custom
    public static final String ENTITY_BANK_ACCOUNT = "Bank-Account";

    // 原代码: export const ENTITY_TRACKING_NUMBER = 'Tracking-Number'; // Custom
    public static final String ENTITY_TRACKING_NUMBER = "Tracking-Number";

    // 原代码: export const ENTITY_PHONE_NUMBER = 'Phone-Number'; // Custom
    public static final String ENTITY_PHONE_NUMBER = "Phone-Number";

    // 原代码: export const ENTITY_PAYMENT_CARD = 'Payment-Card'; // Custom
    public static final String ENTITY_PAYMENT_CARD = "Payment-Card";

    // 原代码: export const ENTITY_MEDIA_CONTENT = 'Media-Content'; // Custom
    public static final String ENTITY_MEDIA_CONTENT = "Media-Content";

    // 原代码: export const ENTITY_PERSONA = 'Persona'; // Custom
    public static final String ENTITY_PERSONA = "Persona";

    // 原代码: export const ENTITY_SSH_KEY = 'SSH-Key'; // Custom
    public static final String ENTITY_SSH_KEY = "SSH-Key";

    // ==================== STIX网络可观测对象列表 ====================
    // 原代码: const STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES = [...]
    public static final List<String> STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES = Collections.unmodifiableList(Arrays.asList(
            ENTITY_HASHED_OBSERVABLE_ARTIFACT,
            ENTITY_HASHED_OBSERVABLE_STIX_FILE,
            ENTITY_HASHED_OBSERVABLE_X509_CERTIFICATE
    ));

    // 原代码: export const STIX_CYBER_OBSERVABLES = [...]
    public static final List<String> STIX_CYBER_OBSERVABLES = Collections.unmodifiableList(Arrays.asList(
            ENTITY_AUTONOMOUS_SYSTEM,
            ENTITY_DIRECTORY,
            ENTITY_DOMAIN_NAME,
            ENTITY_EMAIL_ADDR,
            ENTITY_EMAIL_MESSAGE,
            ENTITY_EMAIL_MIME_PART_TYPE,
            ENTITY_HASHED_OBSERVABLE_ARTIFACT,
            ENTITY_HASHED_OBSERVABLE_STIX_FILE,
            ENTITY_HASHED_OBSERVABLE_X509_CERTIFICATE,
            ENTITY_IPV4_ADDR,
            ENTITY_IPV6_ADDR,
            ENTITY_MAC_ADDR,
            ENTITY_MUTEX,
            ENTITY_NETWORK_TRAFFIC,
            ENTITY_PROCESS,
            ENTITY_SOFTWARE,
            ENTITY_URL,
            ENTITY_USER_ACCOUNT,
            ENTITY_WINDOWS_REGISTRY_KEY,
            ENTITY_WINDOWS_REGISTRY_VALUE_TYPE,
            ENTITY_CRYPTOGRAPHIC_KEY,
            ENTITY_CRYPTOGRAPHIC_WALLET,
            ENTITY_HOSTNAME,
            ENTITY_USER_AGENT,
            ENTITY_TEXT,
            ENTITY_BANK_ACCOUNT,
            ENTITY_CREDENTIAL,
            ENTITY_PHONE_NUMBER,
            ENTITY_TRACKING_NUMBER,
            ENTITY_PAYMENT_CARD,
            ENTITY_MEDIA_CONTENT,
            ENTITY_PERSONA,
            ENTITY_SSH_KEY
    ));

    // ==================== 静态代码块：类型注册 ====================
    static {
        // 原代码: schemaTypesDefinition.register(ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE, STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES);
        SchemaTypesDefinition.register(SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE, STIX_CYBER_OBSERVABLES_HASHED_OBSERVABLES);

        // 原代码: schemaTypesDefinition.register(ABSTRACT_STIX_CYBER_OBSERVABLE, STIX_CYBER_OBSERVABLES);
        SchemaTypesDefinition.register(SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE, STIX_CYBER_OBSERVABLES);
    }

    // ==================== 类型判断方法 ====================
    // 原代码:
    // export const isStixCyberObservableHashedObservable = (type: string) => schemaTypesDefinition.isTypeIncludedIn(type, ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE)
    //   || type === ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE;
    /**
     * 判断给定类型是否为支持哈希的STIX网络可观测对象
     *
     * @param type 类型字符串
     * @return 如果是支持哈希的可观测对象返回true，否则返回false
     */
    public static boolean isStixCyberObservableHashedObservable(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE)
                || type.equals(SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE_HASHED_OBSERVABLE);
    }

    // 原代码:
    // export const isStixCyberObservable = (type: string) => schemaTypesDefinition.isTypeIncludedIn(type, ABSTRACT_STIX_CYBER_OBSERVABLE)
    //   || type === ABSTRACT_STIX_CYBER_OBSERVABLE;
    /**
     * 判断给定类型是否为STIX网络可观测对象
     *
     * @param type 类型字符串
     * @return 如果是STIX网络可观测对象返回true，否则返回false
     */
    public static boolean isStixCyberObservable(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE)
                || type.equals(SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE);
    }
}
