package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.Map;

/**
 * STIX X509 Certificate object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixX509Certificate extends StixCyberObject {
    public static final String TYPE = "x509-certificate";

    @JsonProperty("is_self_signed")
    private Boolean isSelfSigned;
    private Map<String, String> hashes;
    private String version;
    @JsonProperty("serial_number")
    private String serialNumber;
    @JsonProperty("signature_algorithm")
    private String signatureAlgorithm;
    private String issuer;
    @JsonProperty("validity_not_before")
    private String validityNotBefore;
    @JsonProperty("validity_not_after")
    private String validityNotAfter;
    private String subject;
    @JsonProperty("subject_public_key_algorithm")
    private String subjectPublicKeyAlgorithm;
    @JsonProperty("subject_public_key_modulus")
    private String subjectPublicKeyModulus;
    @JsonProperty("subject_public_key_exponent")
    private Integer subjectPublicKeyExponent;
    @JsonProperty("x509_v3_extensions")
    private X509V3Extensions x509V3Extensions;

    public StixX509Certificate() {
        this.type = TYPE;
    }

    public Boolean getIsSelfSigned() {
        return isSelfSigned;
    }

    public void setIsSelfSigned(Boolean isSelfSigned) {
        this.isSelfSigned = isSelfSigned;
    }

    public Map<String, String> getHashes() {
        return hashes;
    }

    public void setHashes(Map<String, String> hashes) {
        this.hashes = hashes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getValidityNotBefore() {
        return validityNotBefore;
    }

    public void setValidityNotBefore(String validityNotBefore) {
        this.validityNotBefore = validityNotBefore;
    }

    public String getValidityNotAfter() {
        return validityNotAfter;
    }

    public void setValidityNotAfter(String validityNotAfter) {
        this.validityNotAfter = validityNotAfter;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectPublicKeyAlgorithm() {
        return subjectPublicKeyAlgorithm;
    }

    public void setSubjectPublicKeyAlgorithm(String subjectPublicKeyAlgorithm) {
        this.subjectPublicKeyAlgorithm = subjectPublicKeyAlgorithm;
    }

    public String getSubjectPublicKeyModulus() {
        return subjectPublicKeyModulus;
    }

    public void setSubjectPublicKeyModulus(String subjectPublicKeyModulus) {
        this.subjectPublicKeyModulus = subjectPublicKeyModulus;
    }

    public Integer getSubjectPublicKeyExponent() {
        return subjectPublicKeyExponent;
    }

    public void setSubjectPublicKeyExponent(Integer subjectPublicKeyExponent) {
        this.subjectPublicKeyExponent = subjectPublicKeyExponent;
    }

    public X509V3Extensions getX509V3Extensions() {
        return x509V3Extensions;
    }

    public void setX509V3Extensions(X509V3Extensions x509V3Extensions) {
        this.x509V3Extensions = x509V3Extensions;
    }

    /**
     * X509 V3 Extensions
     */
    public static class X509V3Extensions {
        @JsonProperty("basic_constraints")
        private String basicConstraints;
        @JsonProperty("name_constraints")
        private String nameConstraints;
        @JsonProperty("policy_constraints")
        private String policyConstraints;
        @JsonProperty("key_usage")
        private String keyUsage;
        @JsonProperty("extended_key_usage")
        private String extendedKeyUsage;
        @JsonProperty("subject_key_identifier")
        private String subjectKeyIdentifier;
        @JsonProperty("authority_key_identifier")
        private String authorityKeyIdentifier;
        @JsonProperty("subject_alternative_name")
        private String subjectAlternativeName;
        @JsonProperty("issuer_alternative_name")
        private String issuerAlternativeName;
        @JsonProperty("subject_directory_attributes")
        private String subjectDirectoryAttributes;
        @JsonProperty("crl_distribution_points")
        private String crlDistributionPoints;
        @JsonProperty("inhibit_any_policy")
        private String inhibitAnyPolicy;
        @JsonProperty("private_key_usage_period_not_before")
        private String privateKeyUsagePeriodNotBefore;
        @JsonProperty("private_key_usage_period_not_after")
        private String privateKeyUsagePeriodNotAfter;
        @JsonProperty("certificate_policies")
        private String certificatePolicies;
        @JsonProperty("policy_mappings")
        private String policyMappings;

        // Getters and Setters
        public String getBasicConstraints() { return basicConstraints; }
        public void setBasicConstraints(String basicConstraints) { this.basicConstraints = basicConstraints; }
        public String getNameConstraints() { return nameConstraints; }
        public void setNameConstraints(String nameConstraints) { this.nameConstraints = nameConstraints; }
        public String getPolicyConstraints() { return policyConstraints; }
        public void setPolicyConstraints(String policyConstraints) { this.policyConstraints = policyConstraints; }
        public String getKeyUsage() { return keyUsage; }
        public void setKeyUsage(String keyUsage) { this.keyUsage = keyUsage; }
        public String getExtendedKeyUsage() { return extendedKeyUsage; }
        public void setExtendedKeyUsage(String extendedKeyUsage) { this.extendedKeyUsage = extendedKeyUsage; }
        public String getSubjectKeyIdentifier() { return subjectKeyIdentifier; }
        public void setSubjectKeyIdentifier(String subjectKeyIdentifier) { this.subjectKeyIdentifier = subjectKeyIdentifier; }
        public String getAuthorityKeyIdentifier() { return authorityKeyIdentifier; }
        public void setAuthorityKeyIdentifier(String authorityKeyIdentifier) { this.authorityKeyIdentifier = authorityKeyIdentifier; }
        public String getSubjectAlternativeName() { return subjectAlternativeName; }
        public void setSubjectAlternativeName(String subjectAlternativeName) { this.subjectAlternativeName = subjectAlternativeName; }
        public String getIssuerAlternativeName() { return issuerAlternativeName; }
        public void setIssuerAlternativeName(String issuerAlternativeName) { this.issuerAlternativeName = issuerAlternativeName; }
        public String getSubjectDirectoryAttributes() { return subjectDirectoryAttributes; }
        public void setSubjectDirectoryAttributes(String subjectDirectoryAttributes) { this.subjectDirectoryAttributes = subjectDirectoryAttributes; }
        public String getCrlDistributionPoints() { return crlDistributionPoints; }
        public void setCrlDistributionPoints(String crlDistributionPoints) { this.crlDistributionPoints = crlDistributionPoints; }
        public String getInhibitAnyPolicy() { return inhibitAnyPolicy; }
        public void setInhibitAnyPolicy(String inhibitAnyPolicy) { this.inhibitAnyPolicy = inhibitAnyPolicy; }
        public String getPrivateKeyUsagePeriodNotBefore() { return privateKeyUsagePeriodNotBefore; }
        public void setPrivateKeyUsagePeriodNotBefore(String privateKeyUsagePeriodNotBefore) { this.privateKeyUsagePeriodNotBefore = privateKeyUsagePeriodNotBefore; }
        public String getPrivateKeyUsagePeriodNotAfter() { return privateKeyUsagePeriodNotAfter; }
        public void setPrivateKeyUsagePeriodNotAfter(String privateKeyUsagePeriodNotAfter) { this.privateKeyUsagePeriodNotAfter = privateKeyUsagePeriodNotAfter; }
        public String getCertificatePolicies() { return certificatePolicies; }
        public void setCertificatePolicies(String certificatePolicies) { this.certificatePolicies = certificatePolicies; }
        public String getPolicyMappings() { return policyMappings; }
        public void setPolicyMappings(String policyMappings) { this.policyMappings = policyMappings; }
    }
}
