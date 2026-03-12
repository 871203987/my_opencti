package io.opencti.database.stix.converter;

import io.opencti.types.store.*;
import io.opencti.types.stix.common.StixDate;
import io.opencti.database.stix.StixConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * STIX 2.1 Cyber Observable (SCO) Converter.
 * Original file: opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts
 * Converts StoreCyberObservable objects to STIX 2.1 SCO format.
 */
@Slf4j
public class Stix21ScoConverter {

    /**
     * 鍘熸柟娉? convertArtifactToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertArtifactToStix
     */
    public static Map<String, Object> convertArtifactToStix(StoreCyberObservable instance) {
        Map<String, Object> artifact = buildScoBase(instance, "artifact");

        if (instance.getMimeType() != null) {
            artifact.put("mime_type", instance.getMimeType());
        }
        if (instance.getPayloadBin() != null) {
            artifact.put("payload_bin", instance.getPayloadBin());
        }
        if (instance.getUrl() != null) {
            artifact.put("url", instance.getUrl());
        }
        if (instance.getHashes() != null && !instance.getHashes().isEmpty()) {
            artifact.put("hashes", instance.getHashes());
        }
        if (instance.getEncryptionAlgorithm() != null) {
            artifact.put("encryption_algorithm", instance.getEncryptionAlgorithm());
        }
        if (instance.getDecryptionKey() != null) {
            artifact.put("decryption_key", instance.getDecryptionKey());
        }

        addScoExtensions(artifact, instance);
        return artifact;
    }

    /**
     * 鍘熸柟娉? convertAutonomousSystemToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertAutonomousSystemToStix
     */
    public static Map<String, Object> convertAutonomousSystemToStix(StoreCyberObservable instance) {
        Map<String, Object> autonomousSystem = buildScoBase(instance, "autonomous-system");

        if (instance.getNumber() != null) {
            autonomousSystem.put("number", instance.getNumber());
        }
        if (instance.getName() != null) {
            autonomousSystem.put("name", instance.getName());
        }
        if (instance.getRir() != null) {
            autonomousSystem.put("rir", instance.getRir());
        }

        addScoExtensions(autonomousSystem, instance);
        return autonomousSystem;
    }

    /**
     * 鍘熸柟娉? convertDirectoryToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertDirectoryToStix
     */
    public static Map<String, Object> convertDirectoryToStix(StoreCyberObservable instance) {
        Map<String, Object> directory = buildScoBase(instance, "directory");

        if (instance.getPath() != null) {
            directory.put("path", instance.getPath());
        }
        if (instance.getPathEnc() != null) {
            directory.put("path_enc", instance.getPathEnc());
        }
        if (instance.getCtime() != null) {
            directory.put("ctime", StixConverterUtils.convertToStixDate(instance.getCtime()));
        }
        if (instance.getMtime() != null) {
            directory.put("mtime", StixConverterUtils.convertToStixDate(instance.getMtime()));
        }
        if (instance.getAtime() != null) {
            directory.put("atime", StixConverterUtils.convertToStixDate(instance.getAtime()));
        }

        addScoExtensions(directory, instance);
        return directory;
    }

    /**
     * 鍘熸柟娉? convertDomainNameToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertDomainNameToStix
     */
    public static Map<String, Object> convertDomainNameToStix(StoreCyberObservable instance) {
        Map<String, Object> domainName = buildScoBase(instance, "domain-name");

        if (instance.getValue() != null) {
            domainName.put("value", instance.getValue());
        }

        addScoExtensions(domainName, instance);
        return domainName;
    }

    /**
     * 鍘熸柟娉? convertEmailAddrToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertEmailAddrToStix
     */
    public static Map<String, Object> convertEmailAddrToStix(StoreCyberObservable instance) {
        Map<String, Object> emailAddr = buildScoBase(instance, "email-addr");

        if (instance.getValue() != null) {
            emailAddr.put("value", instance.getValue());
        }
        if (instance.getDisplayName() != null) {
            emailAddr.put("display_name", instance.getDisplayName());
        }

        addScoExtensions(emailAddr, instance);
        return emailAddr;
    }

    /**
     * 鍘熸柟娉? convertEmailMessageToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertEmailMessageToStix
     */
    public static Map<String, Object> convertEmailMessageToStix(StoreCyberObservable instance) {
        Map<String, Object> emailMessage = buildScoBase(instance, "email-message");

        if (instance.getIsMultipart() != null) {
            emailMessage.put("is_multipart", instance.getIsMultipart());
        }
        if (instance.getAttributeDate() != null) {
            emailMessage.put("date", StixConverterUtils.convertToStixDate(instance.getAttributeDate()));
        }
        if (instance.getContentType() != null) {
            emailMessage.put("content_type", instance.getContentType());
        }
        if (instance.getMessageId() != null) {
            emailMessage.put("message_id", instance.getMessageId());
        }
        if (instance.getSubject() != null) {
            emailMessage.put("subject", instance.getSubject());
        }
        if (instance.getReceivedLines() != null && !instance.getReceivedLines().isEmpty()) {
            emailMessage.put("received_lines", instance.getReceivedLines());
        }
        if (instance.getBody() != null) {
            emailMessage.put("body", instance.getBody());
        }

        addScoExtensions(emailMessage, instance);
        return emailMessage;
    }

    /**
     * 鍘熸柟娉? convertFileToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertFileToStix
     */
    public static Map<String, Object> convertFileToStix(StoreCyberObservable instance) {
        Map<String, Object> file = buildScoBase(instance, "file");

        if (instance.getName() != null) {
            file.put("name", instance.getName());
        }
        if (instance.getNameEnc() != null) {
            file.put("name_enc", instance.getNameEnc());
        }
        if (instance.getHashes() != null && !instance.getHashes().isEmpty()) {
            file.put("hashes", instance.getHashes());
        }
        if (instance.getSize() != null) {
            file.put("size", instance.getSize());
        }
        if (instance.getMagicNumberHex() != null) {
            file.put("magic_number_hex", instance.getMagicNumberHex());
        }
        if (instance.getMimeType() != null) {
            file.put("mime_type", instance.getMimeType());
        }
        if (instance.getCtime() != null) {
            file.put("ctime", StixConverterUtils.convertToStixDate(instance.getCtime()));
        }
        if (instance.getMtime() != null) {
            file.put("mtime", StixConverterUtils.convertToStixDate(instance.getMtime()));
        }
        if (instance.getAtime() != null) {
            file.put("atime", StixConverterUtils.convertToStixDate(instance.getAtime()));
        }

        addScoExtensions(file, instance);
        return file;
    }

    /**
     * 鍘熸柟娉? convertIPv4AddrToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertIPv4AddrToStix
     */
    public static Map<String, Object> convertIPv4AddrToStix(StoreCyberObservable instance) {
        Map<String, Object> ipv4Addr = buildScoBase(instance, "ipv4-addr");

        if (instance.getValue() != null) {
            ipv4Addr.put("value", instance.getValue());
        }

        addScoExtensions(ipv4Addr, instance);
        return ipv4Addr;
    }

    /**
     * 鍘熸柟娉? convertIPv6AddrToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertIPv6AddrToStix
     */
    public static Map<String, Object> convertIPv6AddrToStix(StoreCyberObservable instance) {
        Map<String, Object> ipv6Addr = buildScoBase(instance, "ipv6-addr");

        if (instance.getValue() != null) {
            ipv6Addr.put("value", instance.getValue());
        }

        addScoExtensions(ipv6Addr, instance);
        return ipv6Addr;
    }

    /**
     * 鍘熸柟娉? convertMACAddrToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertMACAddrToStix
     */
    public static Map<String, Object> convertMACAddrToStix(StoreCyberObservable instance) {
        Map<String, Object> macAddr = buildScoBase(instance, "mac-addr");

        if (instance.getValue() != null) {
            macAddr.put("value", instance.getValue());
        }

        addScoExtensions(macAddr, instance);
        return macAddr;
    }

    /**
     * 鍘熸柟娉? convertMutexToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertMutexToStix
     */
    public static Map<String, Object> convertMutexToStix(StoreCyberObservable instance) {
        Map<String, Object> mutex = buildScoBase(instance, "mutex");

        if (instance.getName() != null) {
            mutex.put("name", instance.getName());
        }

        addScoExtensions(mutex, instance);
        return mutex;
    }

    /**
     * 鍘熸柟娉? convertNetworkTrafficToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertNetworkTrafficToStix
     */
    public static Map<String, Object> convertNetworkTrafficToStix(StoreCyberObservable instance) {
        Map<String, Object> networkTraffic = buildScoBase(instance, "network-traffic");

        if (instance.getStart() != null) {
            networkTraffic.put("start", StixConverterUtils.convertToStixDate(instance.getStart()));
        }
        if (instance.getEnd() != null) {
            networkTraffic.put("end", StixConverterUtils.convertToStixDate(instance.getEnd()));
        }
        if (instance.getIsActive() != null) {
            networkTraffic.put("is_active", instance.getIsActive());
        }
        if (instance.getSrcPort() != null) {
            networkTraffic.put("src_port", instance.getSrcPort());
        }
        if (instance.getDstPort() != null) {
            networkTraffic.put("dst_port", instance.getDstPort());
        }
        if (instance.getProtocols() != null && !instance.getProtocols().isEmpty()) {
            networkTraffic.put("protocols", instance.getProtocols());
        }
        if (instance.getSrcByteCount() != null) {
            networkTraffic.put("src_byte_count", instance.getSrcByteCount());
        }
        if (instance.getDstByteCount() != null) {
            networkTraffic.put("dst_byte_count", instance.getDstByteCount());
        }
        if (instance.getSrcPackets() != null) {
            networkTraffic.put("src_packets", instance.getSrcPackets());
        }
        if (instance.getDstPackets() != null) {
            networkTraffic.put("dst_packets", instance.getDstPackets());
        }

        addScoExtensions(networkTraffic, instance);
        return networkTraffic;
    }

    /**
     * 鍘熸柟娉? convertProcessToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertProcessToStix
     */
    public static Map<String, Object> convertProcessToStix(StoreCyberObservable instance) {
        Map<String, Object> process = buildScoBase(instance, "process");

        if (instance.getIsHidden() != null) {
            process.put("is_hidden", instance.getIsHidden());
        }
        if (instance.getPid() != null) {
            process.put("pid", instance.getPid());
        }
        if (instance.getCreatedTime() != null) {
            process.put("created", StixConverterUtils.convertToStixDate(instance.getCreatedTime()));
        }
        if (instance.getCwd() != null) {
            process.put("cwd", instance.getCwd());
        }
        if (instance.getCommandLine() != null) {
            process.put("command_line", instance.getCommandLine());
        }
        if (instance.getEnvironmentVariables() != null && !instance.getEnvironmentVariables().isEmpty()) {
            process.put("environment_variables", instance.getEnvironmentVariables());
        }

        addScoExtensions(process, instance);
        return process;
    }

    /**
     * 鍘熸柟娉? convertSoftwareToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertSoftwareToStix
     */
    public static Map<String, Object> convertSoftwareToStix(StoreCyberObservable instance) {
        Map<String, Object> software = buildScoBase(instance, "software");

        if (instance.getName() != null) {
            software.put("name", instance.getName());
        }
        if (instance.getCpe() != null) {
            software.put("cpe", instance.getCpe());
        }
        if (instance.getSwid() != null) {
            software.put("swid", instance.getSwid());
        }
        if (instance.getLanguages() != null && !instance.getLanguages().isEmpty()) {
            software.put("languages", instance.getLanguages());
        }
        if (instance.getVendor() != null) {
            software.put("vendor", instance.getVendor());
        }
        if (instance.getVersion() != null) {
            software.put("version", instance.getVersion());
        }

        addScoExtensions(software, instance);
        return software;
    }

    /**
     * 鍘熸柟娉? convertURLToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertURLToStix
     */
    public static Map<String, Object> convertURLToStix(StoreCyberObservable instance) {
        Map<String, Object> url = buildScoBase(instance, "url");

        if (instance.getValue() != null) {
            url.put("value", instance.getValue());
        }

        addScoExtensions(url, instance);
        return url;
    }

    /**
     * 鍘熸柟娉? convertUserAccountToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertUserAccountToStix
     */
    public static Map<String, Object> convertUserAccountToStix(StoreCyberObservable instance) {
        Map<String, Object> userAccount = buildScoBase(instance, "user-account");

        if (instance.getUserId() != null) {
            userAccount.put("user_id", instance.getUserId());
        }
        if (instance.getCredential() != null) {
            userAccount.put("credential", instance.getCredential());
        }
        if (instance.getAccountLogin() != null) {
            userAccount.put("account_login", instance.getAccountLogin());
        }
        if (instance.getAccountType() != null) {
            userAccount.put("account_type", instance.getAccountType());
        }
        if (instance.getAccountDisplayName() != null) {
            userAccount.put("display_name", instance.getAccountDisplayName());
        }
        if (instance.getIsServiceAccount() != null) {
            userAccount.put("is_service_account", instance.getIsServiceAccount());
        }
        if (instance.getIsPrivileged() != null) {
            userAccount.put("is_privileged", instance.getIsPrivileged());
        }
        if (instance.getCanEscalatePrivs() != null) {
            userAccount.put("can_escalate_privs", instance.getCanEscalatePrivs());
        }
        if (instance.getIsDisabled() != null) {
            userAccount.put("is_disabled", instance.getIsDisabled());
        }
        if (instance.getAccountCreated() != null) {
            userAccount.put("account_created", StixConverterUtils.convertToStixDate(instance.getAccountCreated()));
        }
        if (instance.getAccountExpires() != null) {
            userAccount.put("account_expires", StixConverterUtils.convertToStixDate(instance.getAccountExpires()));
        }
        if (instance.getCredentialLastChanged() != null) {
            userAccount.put("credential_last_changed", StixConverterUtils.convertToStixDate(instance.getCredentialLastChanged()));
        }
        if (instance.getAccountFirstLogin() != null) {
            userAccount.put("account_first_login", StixConverterUtils.convertToStixDate(instance.getAccountFirstLogin()));
        }
        if (instance.getAccountLastLogin() != null) {
            userAccount.put("account_last_login", StixConverterUtils.convertToStixDate(instance.getAccountLastLogin()));
        }

        addScoExtensions(userAccount, instance);
        return userAccount;
    }

    /**
     * 鍘熸柟娉? convertWindowsRegistryKeyToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertWindowsRegistryKeyToStix
     */
    public static Map<String, Object> convertWindowsRegistryKeyToStix(StoreCyberObservable instance) {
        Map<String, Object> windowsRegistryKey = buildScoBase(instance, "windows-registry-key");

        if (instance.getRegistryKey() != null) {
            windowsRegistryKey.put("key", instance.getRegistryKey());
        }
        if (instance.getCtime() != null) {
            windowsRegistryKey.put("modified_time", StixConverterUtils.convertToStixDate(instance.getCtime()));
        }
        if (instance.getRegistryValues() != null && !instance.getRegistryValues().isEmpty()) {
            windowsRegistryKey.put("values", instance.getRegistryValues());
        }

        addScoExtensions(windowsRegistryKey, instance);
        return windowsRegistryKey;
    }

    /**
     * 鍘熸柟娉? convertX509CertificateToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertX509CertificateToStix
     */
    public static Map<String, Object> convertX509CertificateToStix(StoreCyberObservable instance) {
        Map<String, Object> x509Certificate = buildScoBase(instance, "x509-certificate");

        if (instance.getIsSelfSigned() != null) {
            x509Certificate.put("is_self_signed", instance.getIsSelfSigned());
        }
        if (instance.getX509Hashes() != null && !instance.getX509Hashes().isEmpty()) {
            x509Certificate.put("hashes", instance.getX509Hashes());
        }
        if (instance.getSerialNumber() != null) {
            x509Certificate.put("serial_number", instance.getSerialNumber());
        }
        if (instance.getSignatureAlgorithm() != null) {
            x509Certificate.put("signature_algorithm", instance.getSignatureAlgorithm());
        }
        if (instance.getIssuer() != null) {
            x509Certificate.put("issuer", instance.getIssuer());
        }
        if (instance.getValidityNotBefore() != null) {
            x509Certificate.put("validity_not_before", StixConverterUtils.convertToStixDate(instance.getValidityNotBefore()));
        }
        if (instance.getValidityNotAfter() != null) {
            x509Certificate.put("validity_not_after", StixConverterUtils.convertToStixDate(instance.getValidityNotAfter()));
        }
        if (instance.getX509Subject() != null) {
            x509Certificate.put("subject", instance.getX509Subject());
        }
        if (instance.getSubjectPublicKeyAlgorithm() != null) {
            x509Certificate.put("subject_public_key_algorithm", instance.getSubjectPublicKeyAlgorithm());
        }
        if (instance.getSubjectPublicKeyModulus() != null) {
            x509Certificate.put("subject_public_key_modulus", instance.getSubjectPublicKeyModulus());
        }
        if (instance.getSubjectPublicKeyExponent() != null) {
            x509Certificate.put("subject_public_key_exponent", instance.getSubjectPublicKeyExponent());
        }
        // X509 v3 extensions
        Map<String, Object> x509V3Extensions = new HashMap<>();
        if (instance.getBasicConstraints() != null) {
            x509V3Extensions.put("basic_constraints", instance.getBasicConstraints());
        }
        if (instance.getNameConstraints() != null) {
            x509V3Extensions.put("name_constraints", instance.getNameConstraints());
        }
        if (instance.getPolicyConstraints() != null) {
            x509V3Extensions.put("policy_constraints", instance.getPolicyConstraints());
        }
        if (instance.getKeyUsage() != null) {
            x509V3Extensions.put("key_usage", instance.getKeyUsage());
        }
        if (instance.getExtendedKeyUsage() != null) {
            x509V3Extensions.put("extended_key_usage", instance.getExtendedKeyUsage());
        }
        if (instance.getSubjectKeyIdentifier() != null) {
            x509V3Extensions.put("subject_key_identifier", instance.getSubjectKeyIdentifier());
        }
        if (instance.getAuthorityKeyIdentifier() != null) {
            x509V3Extensions.put("authority_key_identifier", instance.getAuthorityKeyIdentifier());
        }
        if (instance.getSubjectAlternativeName() != null) {
            x509V3Extensions.put("subject_alternative_name", instance.getSubjectAlternativeName());
        }
        if (instance.getIssuerAlternativeName() != null) {
            x509V3Extensions.put("issuer_alternative_name", instance.getIssuerAlternativeName());
        }
        if (instance.getSubjectDirectoryAttributes() != null) {
            x509V3Extensions.put("subject_directory_attributes", instance.getSubjectDirectoryAttributes());
        }
        if (instance.getCrlDistributionPoints() != null) {
            x509V3Extensions.put("crl_distribution_points", instance.getCrlDistributionPoints());
        }
        if (instance.getInhibitAnyPolicy() != null) {
            x509V3Extensions.put("inhibit_any_policy", instance.getInhibitAnyPolicy());
        }
        if (instance.getPrivateKeyUsagePeriodNotBefore() != null) {
            x509V3Extensions.put("private_key_usage_period_not_before", StixConverterUtils.convertToStixDate(instance.getPrivateKeyUsagePeriodNotBefore()));
        }
        if (instance.getPrivateKeyUsagePeriodNotAfter() != null) {
            x509V3Extensions.put("private_key_usage_period_not_after", StixConverterUtils.convertToStixDate(instance.getPrivateKeyUsagePeriodNotAfter()));
        }
        if (instance.getCertificatePolicies() != null) {
            x509V3Extensions.put("certificate_policies", instance.getCertificatePolicies());
        }
        if (instance.getPolicyMappings() != null) {
            x509V3Extensions.put("policy_mappings", instance.getPolicyMappings());
        }
        if (!x509V3Extensions.isEmpty()) {
            x509Certificate.put("x509_v3_extensions", x509V3Extensions);
        }

        addScoExtensions(x509Certificate, instance);
        return x509Certificate;
    }

    // ==================== 鏂板SCO杞崲鍣?====================

    /**
     * 鍘熸柟娉? convertCryptocurrencyWalletToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertCryptocurrencyWalletToStix
     */
    public static Map<String, Object> convertCryptocurrencyWalletToStix(StoreCyberObservable instance) {
        Map<String, Object> wallet = buildScoBase(instance, "cryptocurrency-wallet");

        if (instance.getValue() != null) {
            wallet.put("value", instance.getValue());
        }

        addNewScoExtensions(wallet, instance);
        return wallet;
    }

    /**
     * 鍘熸柟娉? convertCryptographicKeyToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertCryptographicKeyToStix
     */
    public static Map<String, Object> convertCryptographicKeyToStix(StoreCyberObservable instance) {
        Map<String, Object> key = buildScoBase(instance, "cryptographic-key");

        if (instance.getValue() != null) {
            key.put("value", instance.getValue());
        }

        addNewScoExtensions(key, instance);
        return key;
    }

    /**
     * 鍘熸柟娉? convertHostnameToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertHostnameToStix
     */
    public static Map<String, Object> convertHostnameToStix(StoreCyberObservable instance) {
        Map<String, Object> hostname = buildScoBase(instance, "hostname");

        if (instance.getValue() != null) {
            hostname.put("value", instance.getValue());
        }

        addNewScoExtensions(hostname, instance);
        return hostname;
    }

    /**
     * 鍘熸柟娉? convertTextToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertTextToStix
     */
    public static Map<String, Object> convertTextToStix(StoreCyberObservable instance) {
        Map<String, Object> text = buildScoBase(instance, "text");

        if (instance.getValue() != null) {
            text.put("value", instance.getValue());
        }

        addNewScoExtensions(text, instance);
        return text;
    }

    /**
     * 鍘熸柟娉? convertBankAccountToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertBankAccountToStix
     */
    public static Map<String, Object> convertBankAccountToStix(StoreCyberObservable instance) {
        Map<String, Object> bankAccount = buildScoBase(instance, "bank-account");

        if (instance.getIban() != null) {
            bankAccount.put("iban", instance.getIban());
        }
        if (instance.getBic() != null) {
            bankAccount.put("bic", instance.getBic());
        }
        if (instance.getAccountNumber() != null) {
            bankAccount.put("account_number", instance.getAccountNumber());
        }

        addNewScoExtensions(bankAccount, instance);
        return bankAccount;
    }

    /**
     * 鍘熸柟娉? convertCredentialToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertCredentialToStix
     */
    public static Map<String, Object> convertCredentialToStix(StoreCyberObservable instance) {
        Map<String, Object> credential = buildScoBase(instance, "credential");

        if (instance.getValue() != null) {
            credential.put("value", instance.getValue());
        }

        addNewScoExtensions(credential, instance);
        return credential;
    }

    /**
     * 鍘熸柟娉? convertTrackingNumberToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertTrackingNumberToStix
     */
    public static Map<String, Object> convertTrackingNumberToStix(StoreCyberObservable instance) {
        Map<String, Object> trackingNumber = buildScoBase(instance, "tracking-number");

        if (instance.getValue() != null) {
            trackingNumber.put("value", instance.getValue());
        }

        addNewScoExtensions(trackingNumber, instance);
        return trackingNumber;
    }

    /**
     * 鍘熸柟娉? convertPhoneNumberToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertPhoneNumberToStix
     */
    public static Map<String, Object> convertPhoneNumberToStix(StoreCyberObservable instance) {
        Map<String, Object> phoneNumber = buildScoBase(instance, "phone-number");

        if (instance.getValue() != null) {
            phoneNumber.put("value", instance.getValue());
        }

        addNewScoExtensions(phoneNumber, instance);
        return phoneNumber;
    }

    /**
     * 鍘熸柟娉? convertMediaContentToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertMediaContentToStix
     */
    public static Map<String, Object> convertMediaContentToStix(StoreCyberObservable instance) {
        Map<String, Object> mediaContent = buildScoBase(instance, "media-content");

        if (instance.getTitle() != null) {
            mediaContent.put("title", instance.getTitle());
        }
        if (instance.getContent() != null) {
            mediaContent.put("content", instance.getContent());
        }
        if (instance.getMediaCategory() != null) {
            mediaContent.put("media_category", instance.getMediaCategory());
        }
        if (instance.getUrl() != null) {
            mediaContent.put("url", instance.getUrl());
        }
        if (instance.getPublicationDate() != null) {
            mediaContent.put("publication_date", StixConverterUtils.convertToStixDate(instance.getPublicationDate()));
        }

        addNewScoExtensions(mediaContent, instance);
        return mediaContent;
    }

    /**
     * 鍘熸柟娉? convertPaymentCardToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertPaymentCardToStix
     */
    public static Map<String, Object> convertPaymentCardToStix(StoreCyberObservable instance) {
        Map<String, Object> paymentCard = buildScoBase(instance, "payment-card");

        if (instance.getCardNumber() != null) {
            paymentCard.put("card_number", instance.getCardNumber());
        }
        if (instance.getExpirationDate() != null) {
            paymentCard.put("expiration_date", StixConverterUtils.convertToStixDate(instance.getExpirationDate()));
        }
        if (instance.getCvv() != null) {
            paymentCard.put("cvv", instance.getCvv());
        }
        if (instance.getHolderName() != null) {
            paymentCard.put("holder_name", instance.getHolderName());
        }

        addNewScoExtensions(paymentCard, instance);
        return paymentCard;
    }

    /**
     * 鍘熸柟娉? convertUserAgentToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertUserAgentToStix
     */
    public static Map<String, Object> convertUserAgentToStix(StoreCyberObservable instance) {
        Map<String, Object> userAgent = buildScoBase(instance, "user-agent");

        if (instance.getValue() != null) {
            userAgent.put("value", instance.getValue());
        }

        addNewScoExtensions(userAgent, instance);
        return userAgent;
    }

    /**
     * 鍘熸柟娉? convertPersonaToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertPersonaToStix
     */
    public static Map<String, Object> convertPersonaToStix(StoreCyberObservable instance) {
        Map<String, Object> persona = buildScoBase(instance, "persona");

        if (instance.getPersonaName() != null) {
            persona.put("persona_name", instance.getPersonaName());
        }
        if (instance.getPersonaType() != null) {
            persona.put("persona_type", instance.getPersonaType());
        }

        addNewScoExtensions(persona, instance);
        return persona;
    }

    /**
     * 鍘熸柟娉? convertSSHKeyToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertSSHKeyToStix
     */
    public static Map<String, Object> convertSSHKeyToStix(StoreCyberObservable instance) {
        Map<String, Object> sshKey = buildScoBase(instance, "ssh-key");

        if (instance.getKeyType() != null) {
            sshKey.put("key_type", instance.getKeyType());
        }
        if (instance.getPublicKey() != null) {
            sshKey.put("public_key", instance.getPublicKey());
        }
        if (instance.getFingerprintSha256() != null) {
            sshKey.put("fingerprint_sha256", instance.getFingerprintSha256());
        }
        if (instance.getFingerprintMd5() != null) {
            sshKey.put("fingerprint_md5", instance.getFingerprintMd5());
        }
        if (instance.getKeyLength() != null) {
            sshKey.put("key_length", instance.getKeyLength());
        }
        if (instance.getComment() != null) {
            sshKey.put("comment", instance.getComment());
        }
        if (instance.getCreated() != null) {
            sshKey.put("created", StixConverterUtils.convertToStixDate(instance.getCreated()));
        }
        if (instance.getExpirationDate() != null) {
            sshKey.put("expiration_date", StixConverterUtils.convertToStixDate(instance.getExpirationDate()));
        }

        addNewScoExtensions(sshKey, instance);
        return sshKey;
    }

    /**
     * 鍘熸柟娉? convertWindowsRegistryValueToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertWindowsRegistryValueToStix
     */
    public static Map<String, Object> convertWindowsRegistryValueToStix(StoreCyberObservable instance) {
        Map<String, Object> registryValue = buildScoBase(instance, "windows-registry-value-type");

        if (instance.getName() != null) {
            registryValue.put("name", instance.getName());
        }
        if (instance.getData() != null) {
            registryValue.put("data", instance.getData());
        }
        if (instance.getDataType() != null) {
            registryValue.put("data_type", instance.getDataType());
        }

        addNewScoExtensions(registryValue, instance);
        return registryValue;
    }

    /**
     * 鍘熸柟娉? convertEmailMimePartToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertEmailMimePartToStix
     */
    public static Map<String, Object> convertEmailMimePartToStix(StoreCyberObservable instance) {
        Map<String, Object> mimePart = buildScoBase(instance, "email-message-body-multipart");

        if (instance.getContentType() != null) {
            mimePart.put("content_type", instance.getContentType());
        }
        if (instance.getContentDisposition() != null) {
            mimePart.put("content_disposition", instance.getContentDisposition());
        }
        if (instance.getBody() != null) {
            mimePart.put("body", instance.getBody());
        }

        addNewScoExtensions(mimePart, instance);
        return mimePart;
    }

    // ==================== Helper Methods ====================

    /**
     * 鏋勫缓SCO鍩虹瀵硅薄
     */
    private static Map<String, Object> buildScoBase(StoreCyberObservable instance, String type) {
        Map<String, Object> sco = new HashMap<>();

        if (instance != null) {
            if (instance.getStandardId() != null) {
                sco.put("id", instance.getStandardId().toString());
            }
            sco.put("type", type);
            sco.put("spec_version", "2.1");
        }

        return sco;
    }

    /**
     * 娣诲姞SCO鎵╁睍灞炴€?     */
    private static void addScoExtensions(Map<String, Object> sco, StoreCyberObservable instance) {
        if (instance == null) {
            return;
        }

        Map<String, Object> extensions = new HashMap<>();
        Map<String, Object> openctiExt = new HashMap<>();

        openctiExt.put("extension_type", "property-extension");

        if (instance.getInternalId() != null) {
            openctiExt.put("id", instance.getInternalId());
        }
        if (instance.getEntityType() != null) {
            openctiExt.put("type", instance.getEntityType());
        }
        if (instance.getCreatedAt() != null) {
            openctiExt.put("created_at", StixConverterUtils.convertToStixDate(instance.getCreatedAt()));
        }
        if (instance.getUpdatedAt() != null) {
            openctiExt.put("updated_at", StixConverterUtils.convertToStixDate(instance.getUpdatedAt()));
        }
        if (instance.getXOpenctiDescription() != null) {
            openctiExt.put("description", instance.getXOpenctiDescription());
        }
        if (instance.getXOpenctiScore() != null) {
            openctiExt.put("score", instance.getXOpenctiScore());
        }
        if (instance.getXOpenctiAdditionalNames() != null && !instance.getXOpenctiAdditionalNames().isEmpty()) {
            openctiExt.put("additional_names", instance.getXOpenctiAdditionalNames());
        }

        if (!openctiExt.isEmpty()) {
            extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
            sco.put("extensions", extensions);
        }
    }

    /**
     * 娣诲姞鏂癝CO鎵╁睍灞炴€э紙鐢ㄤ簬鏂板畾涔夌殑SCO绫诲瀷锛?     */
    private static void addNewScoExtensions(Map<String, Object> sco, StoreCyberObservable instance) {
        if (instance == null) {
            return;
        }

        Map<String, Object> extensions = new HashMap<>();
        Map<String, Object> openctiExt = new HashMap<>();
        Map<String, Object> openctiScoExt = new HashMap<>();

        openctiExt.put("extension_type", "property-extension");
        openctiScoExt.put("extension_type", "new-sco");

        if (instance.getInternalId() != null) {
            openctiExt.put("id", instance.getInternalId());
        }
        if (instance.getEntityType() != null) {
            openctiExt.put("type", instance.getEntityType());
        }
        if (instance.getCreatedAt() != null) {
            openctiExt.put("created_at", StixConverterUtils.convertToStixDate(instance.getCreatedAt()));
        }
        if (instance.getUpdatedAt() != null) {
            openctiExt.put("updated_at", StixConverterUtils.convertToStixDate(instance.getUpdatedAt()));
        }
        if (instance.getXOpenctiDescription() != null) {
            openctiExt.put("description", instance.getXOpenctiDescription());
        }
        if (instance.getXOpenctiScore() != null) {
            openctiExt.put("score", instance.getXOpenctiScore());
        }

        if (!openctiExt.isEmpty()) {
            extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
        }
        if (!openctiScoExt.isEmpty()) {
            extensions.put(StixConstants.STIX_EXT_OCTI_SCO, openctiScoExt);
        }
        if (!extensions.isEmpty()) {
            sco.put("extensions", extensions);
        }
    }
}

