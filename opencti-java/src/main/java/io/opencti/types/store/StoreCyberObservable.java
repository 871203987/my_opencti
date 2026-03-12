package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixDate;
import io.opencti.types.stix.common.StixId;

import java.util.List;
import java.util.Map;

/**
 * Store Cyber Observable type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Represents a cyber observable entity stored in the OpenCTI database.
 */
public class StoreCyberObservable extends StoreObject {

    // Common SCO properties
    protected String value;
    protected String name;
    protected String description;
    protected List<String> labels;
    protected List<StixId> objectMarkingRefs;
    protected StixId createdByRef;
    protected List<ExternalReference> externalReferences;
    protected Boolean defanged;

    // OpenCTI SCO extensions
    protected String xOpenctiDescription;
    protected Double xOpenctiScore;
    protected List<String> xOpenctiAdditionalNames;

    // Artifact specific
    protected String mimeType;
    protected String payloadBin;
    protected String url;
    protected Map<String, String> hashes;
    protected String encryptionAlgorithm;
    protected String decryptionKey;

    // Autonomous System specific
    protected Integer number;
    protected String rir;

    // Directory specific
    protected String path;
    protected String pathEnc;
    protected StixDate ctime;
    protected StixDate mtime;
    protected StixDate atime;
    protected List<StoreCyberObservable> contains;

    // Domain Name specific
    protected List<StoreCyberObservable> resolvesTo;

    // Email Address specific
    protected String displayName;
    protected List<StoreCyberObservable> belongsTo;

    // Email Message specific
    protected Boolean isMultipart;
    protected StixDate attributeDate;
    protected String contentType;
    protected String contentDisposition;
    protected StoreCyberObservable emailFrom;
    protected StoreCyberObservable sender;
    protected List<StoreCyberObservable> emailTo;
    protected List<StoreCyberObservable> cc;
    protected List<StoreCyberObservable> bcc;
    protected String messageId;
    protected String subject;
    protected List<String> receivedLines;
    protected String body;
    protected List<EmailBodyMultipart> bodyMultipart;
    protected StoreCyberObservable rawEmail;

    // File specific
    protected Long size;
    protected String nameEnc;
    protected String magicNumberHex;
    protected StoreCyberObservable parentDirectory;
    protected StoreCyberObservable content;

    // IPv4/IPv6 Address specific
    protected List<StoreCyberObservable> belongsToRefs;

    // MAC Address specific - uses value

    // Mutex specific - uses name

    // Network Traffic specific
    protected StixDate start;
    protected StixDate end;
    protected Boolean isActive;
    protected StoreCyberObservable src;
    protected StoreCyberObservable dst;
    protected Integer srcPort;
    protected Integer dstPort;
    protected List<String> protocols;
    protected Long srcByteCount;
    protected Long dstByteCount;
    protected Integer srcPackets;
    protected Integer dstPackets;
    protected Map<String, Object> ipfix;
    protected StoreCyberObservable srcPayload;
    protected StoreCyberObservable dstPayload;
    protected List<StoreCyberObservable> encapsulates;
    protected StoreCyberObservable encapsulatedBy;

    // Process specific
    protected Boolean isHidden;
    protected Integer pid;
    protected StixDate createdTime;
    protected String cwd;
    protected String commandLine;
    protected Map<String, String> environmentVariables;
    protected List<StoreCyberObservable> openedConnections;
    protected StoreCyberObservable creatorUser;
    protected StoreCyberObservable image;
    protected StoreCyberObservable parent;
    protected List<StoreCyberObservable> children;

    // Windows Process Extension
    protected Boolean aslrEnabled;
    protected Boolean depEnabled;
    protected String priority;
    protected String ownerSid;
    protected String windowTitle;
    protected Map<String, Object> startupInfo;
    protected String integrityLevel;

    // Windows Service Extension
    protected String serviceName;
    protected List<String> serviceDescriptions;
    protected String serviceDisplayName;
    protected String groupName;
    protected String startType;
    protected List<StoreCyberObservable> serviceDlls;
    protected String serviceType;
    protected String serviceStatus;

    // Windows Process Extension getters/setters
    @JsonProperty("aslr_enabled")
    public Boolean getAslrEnabled() { return aslrEnabled; }
    public void setAslrEnabled(Boolean aslrEnabled) { this.aslrEnabled = aslrEnabled; }

    @JsonProperty("dep_enabled")
    public Boolean getDepEnabled() { return depEnabled; }
    public void setDepEnabled(Boolean depEnabled) { this.depEnabled = depEnabled; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    @JsonProperty("owner_sid")
    public String getOwnerSid() { return ownerSid; }
    public void setOwnerSid(String ownerSid) { this.ownerSid = ownerSid; }

    @JsonProperty("window_title")
    public String getWindowTitle() { return windowTitle; }
    public void setWindowTitle(String windowTitle) { this.windowTitle = windowTitle; }

    @JsonProperty("startup_info")
    public Map<String, Object> getStartupInfo() { return startupInfo; }
    public void setStartupInfo(Map<String, Object> startupInfo) { this.startupInfo = startupInfo; }

    @JsonProperty("integrity_level")
    public String getIntegrityLevel() { return integrityLevel; }
    public void setIntegrityLevel(String integrityLevel) { this.integrityLevel = integrityLevel; }

    // Windows Service Extension getters/setters
    @JsonProperty("service_name")
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    @JsonProperty("service_descriptions")
    public List<String> getServiceDescriptions() { return serviceDescriptions; }
    public void setServiceDescriptions(List<String> serviceDescriptions) { this.serviceDescriptions = serviceDescriptions; }

    @JsonProperty("service_display_name")
    public String getServiceDisplayName() { return serviceDisplayName; }
    public void setServiceDisplayName(String serviceDisplayName) { this.serviceDisplayName = serviceDisplayName; }

    @JsonProperty("group_name")
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    @JsonProperty("start_type")
    public String getStartType() { return startType; }
    public void setStartType(String startType) { this.startType = startType; }

    @JsonProperty("service_dlls")
    public List<StoreCyberObservable> getServiceDlls() { return serviceDlls; }
    public void setServiceDlls(List<StoreCyberObservable> serviceDlls) { this.serviceDlls = serviceDlls; }

    @JsonProperty("service_type")
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    @JsonProperty("service_status")
    public String getServiceStatus() { return serviceStatus; }
    public void setServiceStatus(String serviceStatus) { this.serviceStatus = serviceStatus; }

    // Software specific
    protected String cpe;
    protected String swid;
    protected List<String> languages;
    protected String vendor;
    protected String version;
    protected String xOpenctiProduct;

    // URL specific - uses value

    // User Account specific
    protected String userId;
    protected String credential;
    protected String accountLogin;
    protected String accountType;
    protected String accountDisplayName;
    protected Boolean isServiceAccount;
    protected Boolean isPrivileged;
    protected Boolean canEscalatePrivs;
    protected Boolean isDisabled;
    protected StixDate accountCreated;
    protected StixDate accountExpires;
    protected StixDate credentialLastChanged;
    protected StixDate accountFirstLogin;
    protected StixDate accountLastLogin;

    // Windows Registry Key specific
    protected String registryKey;
    protected List<WindowsRegistryValue> registryValues;

    // Windows Registry Value specific
    protected String data;
    protected String dataType;

    // X509 Certificate specific
    protected Boolean isSelfSigned;
    protected Map<String, String> x509Hashes;
    protected String serialNumber;
    protected String signatureAlgorithm;
    protected String issuer;
    protected StixDate validityNotBefore;
    protected StixDate validityNotAfter;
    protected String x509Subject;
    protected String subjectPublicKeyAlgorithm;
    protected String subjectPublicKeyModulus;
    protected String subjectPublicKeyExponent;
    protected String xOpenctiIssuerType;
    protected String xOpenctiSubjectType;

    // X509 v3 Extensions
    protected String basicConstraints;
    protected String nameConstraints;
    protected String policyConstraints;
    protected String keyUsage;
    protected String extendedKeyUsage;
    protected String subjectKeyIdentifier;
    protected String authorityKeyIdentifier;
    protected String subjectAlternativeName;
    protected String issuerAlternativeName;
    protected String subjectDirectoryAttributes;
    protected String crlDistributionPoints;
    protected String inhibitAnyPolicy;
    protected String certificatePolicies;
    protected String policyMappings;
    protected StixDate privateKeyUsagePeriodNotBefore;
    protected StixDate privateKeyUsagePeriodNotAfter;

    // Cryptocurrency Wallet specific
    protected String xOpenctiWalletType;

    // Cryptocurrency Transaction specific
    protected String transactionHash;
    protected String transactionUrl;
    protected String transactionStatus;
    protected String transactionType;
    protected String blockHash;
    protected Integer blockNumber;
    protected String fromWallet;
    protected String toWallet;
    protected Double amount;
    protected String currency;
    protected Double gasPrice;
    protected Double gasUsed;
    protected StixDate transactionDate;

    // Cryptographic Key specific
    protected String keyType;
    protected String keyAlgorithm;
    protected String keySize;
    protected String keyContent;

    // Hostname specific
    protected String hostnameType;

    // Text specific
    protected String textType;
    protected String encoding;

    // Bank Account specific
    protected String iban;
    protected String bic;
    protected String accountNumber;

    // Phone Number specific
    protected String phoneNumberType;
    protected String countryCode;
    protected String nationalNumber;

    // Payment Card specific
    protected String cardNumber;
    protected String expirationDate;
    protected String cvv;
    protected String holderName;

    // Media Content specific
    protected String mediaType;
    protected String mediaUrl;
    protected String mediaContent;
    protected String mediaEncoding;
    protected String title;
    protected String mediaCategory;
    protected StixDate publicationDate;

    // Tracking specific
    protected String trackingType;
    protected String trackingId;

    // User Agent specific
    protected String userAgentString;
    protected String userAgentFamily;
    protected String userAgentVersion;

    // Persona specific
    protected String personaType;
    protected String personaName;

    // SSH Key specific
    protected String publicKey;
    protected String fingerprintSha256;
    protected String fingerprintMd5;
    protected Integer keyLength;
    protected String comment;
    protected StixDate created;

    // Credential specific
    protected String credentialType;

    // Common getters/setters
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    @JsonProperty("x_opencti_description")
    public String getXOpenctiDescription() { return xOpenctiDescription; }
    public void setXOpenctiDescription(String xOpenctiDescription) { this.xOpenctiDescription = xOpenctiDescription; }

    @JsonProperty("x_opencti_score")
    public Double getXOpenctiScore() { return xOpenctiScore; }
    public void setXOpenctiScore(Double xOpenctiScore) { this.xOpenctiScore = xOpenctiScore; }

    @JsonProperty("x_opencti_additional_names")
    public List<String> getXOpenctiAdditionalNames() { return xOpenctiAdditionalNames; }
    public void setXOpenctiAdditionalNames(List<String> xOpenctiAdditionalNames) { this.xOpenctiAdditionalNames = xOpenctiAdditionalNames; }

    // Artifact getters/setters
    @JsonProperty("mime_type")
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    @JsonProperty("payload_bin")
    public String getPayloadBin() { return payloadBin; }
    public void setPayloadBin(String payloadBin) { this.payloadBin = payloadBin; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Map<String, String> getHashes() { return hashes; }
    public void setHashes(Map<String, String> hashes) { this.hashes = hashes; }

    @JsonProperty("encryption_algorithm")
    public String getEncryptionAlgorithm() { return encryptionAlgorithm; }
    public void setEncryptionAlgorithm(String encryptionAlgorithm) { this.encryptionAlgorithm = encryptionAlgorithm; }

    @JsonProperty("decryption_key")
    public String getDecryptionKey() { return decryptionKey; }
    public void setDecryptionKey(String decryptionKey) { this.decryptionKey = decryptionKey; }

    // Autonomous System getters/setters
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public String getRir() { return rir; }
    public void setRir(String rir) { this.rir = rir; }

    // Directory getters/setters
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    @JsonProperty("path_enc")
    public String getPathEnc() { return pathEnc; }
    public void setPathEnc(String pathEnc) { this.pathEnc = pathEnc; }

    public StixDate getCtime() { return ctime; }
    public void setCtime(StixDate ctime) { this.ctime = ctime; }

    public StixDate getMtime() { return mtime; }
    public void setMtime(StixDate mtime) { this.mtime = mtime; }

    public StixDate getAtime() { return atime; }
    public void setAtime(StixDate atime) { this.atime = atime; }

    public List<StoreCyberObservable> getContains() { return contains; }
    public void setContains(List<StoreCyberObservable> contains) { this.contains = contains; }

    // Domain Name getters/setters
    public List<StoreCyberObservable> getResolvesTo() { return resolvesTo; }
    public void setResolvesTo(List<StoreCyberObservable> resolvesTo) { this.resolvesTo = resolvesTo; }

    // Email Address getters/setters
    @JsonProperty("display_name")
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public List<StoreCyberObservable> getBelongsTo() { return belongsTo; }
    public void setBelongsTo(List<StoreCyberObservable> belongsTo) { this.belongsTo = belongsTo; }

    // Email Message getters/setters
    @JsonProperty("is_multipart")
    public Boolean getIsMultipart() { return isMultipart; }
    public void setIsMultipart(Boolean isMultipart) { this.isMultipart = isMultipart; }

    @JsonProperty("attribute_date")
    public StixDate getAttributeDate() { return attributeDate; }
    public void setAttributeDate(StixDate attributeDate) { this.attributeDate = attributeDate; }

    @JsonProperty("content_type")
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    @JsonProperty("content_disposition")
    public String getContentDisposition() { return contentDisposition; }
    public void setContentDisposition(String contentDisposition) { this.contentDisposition = contentDisposition; }

    @JsonProperty("email_from")
    public StoreCyberObservable getEmailFrom() { return emailFrom; }
    public void setEmailFrom(StoreCyberObservable emailFrom) { this.emailFrom = emailFrom; }

    public StoreCyberObservable getSender() { return sender; }
    public void setSender(StoreCyberObservable sender) { this.sender = sender; }

    @JsonProperty("email_to")
    public List<StoreCyberObservable> getEmailTo() { return emailTo; }
    public void setEmailTo(List<StoreCyberObservable> emailTo) { this.emailTo = emailTo; }

    public List<StoreCyberObservable> getCc() { return cc; }
    public void setCc(List<StoreCyberObservable> cc) { this.cc = cc; }

    public List<StoreCyberObservable> getBcc() { return bcc; }
    public void setBcc(List<StoreCyberObservable> bcc) { this.bcc = bcc; }

    @JsonProperty("message_id")
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    @JsonProperty("received_lines")
    public List<String> getReceivedLines() { return receivedLines; }
    public void setReceivedLines(List<String> receivedLines) { this.receivedLines = receivedLines; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    @JsonProperty("body_multipart")
    public List<EmailBodyMultipart> getBodyMultipart() { return bodyMultipart; }
    public void setBodyMultipart(List<EmailBodyMultipart> bodyMultipart) { this.bodyMultipart = bodyMultipart; }

    @JsonProperty("raw_email")
    public StoreCyberObservable getRawEmail() { return rawEmail; }
    public void setRawEmail(StoreCyberObservable rawEmail) { this.rawEmail = rawEmail; }

    // File getters/setters
    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }

    @JsonProperty("name_enc")
    public String getNameEnc() { return nameEnc; }
    public void setNameEnc(String nameEnc) { this.nameEnc = nameEnc; }

    @JsonProperty("magic_number_hex")
    public String getMagicNumberHex() { return magicNumberHex; }
    public void setMagicNumberHex(String magicNumberHex) { this.magicNumberHex = magicNumberHex; }

    @JsonProperty("parent_directory")
    public StoreCyberObservable getParentDirectory() { return parentDirectory; }
    public void setParentDirectory(StoreCyberObservable parentDirectory) { this.parentDirectory = parentDirectory; }

    public StoreCyberObservable getContent() { return content; }
    public void setContent(StoreCyberObservable content) { this.content = content; }

    // IPv4/IPv6 getters/setters
    @JsonProperty("belongs_to_refs")
    public List<StoreCyberObservable> getBelongsToRefs() { return belongsToRefs; }
    public void setBelongsToRefs(List<StoreCyberObservable> belongsToRefs) { this.belongsToRefs = belongsToRefs; }

    // Network Traffic getters/setters
    public StixDate getStart() { return start; }
    public void setStart(StixDate start) { this.start = start; }

    public StixDate getEnd() { return end; }
    public void setEnd(StixDate end) { this.end = end; }

    @JsonProperty("is_active")
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public StoreCyberObservable getSrc() { return src; }
    public void setSrc(StoreCyberObservable src) { this.src = src; }

    public StoreCyberObservable getDst() { return dst; }
    public void setDst(StoreCyberObservable dst) { this.dst = dst; }

    @JsonProperty("src_port")
    public Integer getSrcPort() { return srcPort; }
    public void setSrcPort(Integer srcPort) { this.srcPort = srcPort; }

    @JsonProperty("dst_port")
    public Integer getDstPort() { return dstPort; }
    public void setDstPort(Integer dstPort) { this.dstPort = dstPort; }

    public List<String> getProtocols() { return protocols; }
    public void setProtocols(List<String> protocols) { this.protocols = protocols; }

    @JsonProperty("src_byte_count")
    public Long getSrcByteCount() { return srcByteCount; }
    public void setSrcByteCount(Long srcByteCount) { this.srcByteCount = srcByteCount; }

    @JsonProperty("dst_byte_count")
    public Long getDstByteCount() { return dstByteCount; }
    public void setDstByteCount(Long dstByteCount) { this.dstByteCount = dstByteCount; }

    @JsonProperty("src_packets")
    public Integer getSrcPackets() { return srcPackets; }
    public void setSrcPackets(Integer srcPackets) { this.srcPackets = srcPackets; }

    @JsonProperty("dst_packets")
    public Integer getDstPackets() { return dstPackets; }
    public void setDstPackets(Integer dstPackets) { this.dstPackets = dstPackets; }

    public Map<String, Object> getIpfix() { return ipfix; }
    public void setIpfix(Map<String, Object> ipfix) { this.ipfix = ipfix; }

    @JsonProperty("src_payload")
    public StoreCyberObservable getSrcPayload() { return srcPayload; }
    public void setSrcPayload(StoreCyberObservable srcPayload) { this.srcPayload = srcPayload; }

    @JsonProperty("dst_payload")
    public StoreCyberObservable getDstPayload() { return dstPayload; }
    public void setDstPayload(StoreCyberObservable dstPayload) { this.dstPayload = dstPayload; }

    public List<StoreCyberObservable> getEncapsulates() { return encapsulates; }
    public void setEncapsulates(List<StoreCyberObservable> encapsulates) { this.encapsulates = encapsulates; }

    @JsonProperty("encapsulated_by")
    public StoreCyberObservable getEncapsulatedBy() { return encapsulatedBy; }
    public void setEncapsulatedBy(StoreCyberObservable encapsulatedBy) { this.encapsulatedBy = encapsulatedBy; }

    // Process getters/setters
    @JsonProperty("is_hidden")
    public Boolean getIsHidden() { return isHidden; }
    public void setIsHidden(Boolean isHidden) { this.isHidden = isHidden; }

    public Integer getPid() { return pid; }
    public void setPid(Integer pid) { this.pid = pid; }

    @JsonProperty("created_time")
    public StixDate getCreatedTime() { return createdTime; }
    public void setCreatedTime(StixDate createdTime) { this.createdTime = createdTime; }

    public String getCwd() { return cwd; }
    public void setCwd(String cwd) { this.cwd = cwd; }

    @JsonProperty("command_line")
    public String getCommandLine() { return commandLine; }
    public void setCommandLine(String commandLine) { this.commandLine = commandLine; }

    @JsonProperty("environment_variables")
    public Map<String, String> getEnvironmentVariables() { return environmentVariables; }
    public void setEnvironmentVariables(Map<String, String> environmentVariables) { this.environmentVariables = environmentVariables; }

    @JsonProperty("opened_connections")
    public List<StoreCyberObservable> getOpenedConnections() { return openedConnections; }
    public void setOpenedConnections(List<StoreCyberObservable> openedConnections) { this.openedConnections = openedConnections; }

    @JsonProperty("creator_user")
    public StoreCyberObservable getCreatorUser() { return creatorUser; }
    public void setCreatorUser(StoreCyberObservable creatorUser) { this.creatorUser = creatorUser; }

    public StoreCyberObservable getImage() { return image; }
    public void setImage(StoreCyberObservable image) { this.image = image; }

    public StoreCyberObservable getParent() { return parent; }
    public void setParent(StoreCyberObservable parent) { this.parent = parent; }

    public List<StoreCyberObservable> getChildren() { return children; }
    public void setChildren(List<StoreCyberObservable> children) { this.children = children; }

    // Software getters/setters
    public String getCpe() { return cpe; }
    public void setCpe(String cpe) { this.cpe = cpe; }

    public String getSwid() { return swid; }
    public void setSwid(String swid) { this.swid = swid; }

    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }

    public String getVendor() { return vendor; }
    public void setVendor(String vendor) { this.vendor = vendor; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    @JsonProperty("x_opencti_product")
    public String getXOpenctiProduct() { return xOpenctiProduct; }
    public void setXOpenctiProduct(String xOpenctiProduct) { this.xOpenctiProduct = xOpenctiProduct; }

    // X509 Certificate getters/setters
    @JsonProperty("is_self_signed")
    public Boolean getIsSelfSigned() { return isSelfSigned; }
    public void setIsSelfSigned(Boolean isSelfSigned) { this.isSelfSigned = isSelfSigned; }

    @JsonProperty("serial_number")
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    @JsonProperty("signature_algorithm")
    public String getSignatureAlgorithm() { return signatureAlgorithm; }
    public void setSignatureAlgorithm(String signatureAlgorithm) { this.signatureAlgorithm = signatureAlgorithm; }

    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }

    @JsonProperty("validity_not_before")
    public StixDate getValidityNotBefore() { return validityNotBefore; }
    public void setValidityNotBefore(StixDate validityNotBefore) { this.validityNotBefore = validityNotBefore; }

    @JsonProperty("validity_not_after")
    public StixDate getValidityNotAfter() { return validityNotAfter; }
    public void setValidityNotAfter(StixDate validityNotAfter) { this.validityNotAfter = validityNotAfter; }

    @JsonProperty("x509_subject")
    public String getX509Subject() { return x509Subject; }
    public void setX509Subject(String x509Subject) { this.x509Subject = x509Subject; }

    @JsonProperty("subject_public_key_algorithm")
    public String getSubjectPublicKeyAlgorithm() { return subjectPublicKeyAlgorithm; }
    public void setSubjectPublicKeyAlgorithm(String subjectPublicKeyAlgorithm) { this.subjectPublicKeyAlgorithm = subjectPublicKeyAlgorithm; }

    @JsonProperty("subject_public_key_modulus")
    public String getSubjectPublicKeyModulus() { return subjectPublicKeyModulus; }
    public void setSubjectPublicKeyModulus(String subjectPublicKeyModulus) { this.subjectPublicKeyModulus = subjectPublicKeyModulus; }

    @JsonProperty("subject_public_key_exponent")
    public String getSubjectPublicKeyExponent() { return subjectPublicKeyExponent; }
    public void setSubjectPublicKeyExponent(String subjectPublicKeyExponent) { this.subjectPublicKeyExponent = subjectPublicKeyExponent; }

    @JsonProperty("x_opencti_issuer_type")
    public String getXOpenctiIssuerType() { return xOpenctiIssuerType; }
    public void setXOpenctiIssuerType(String xOpenctiIssuerType) { this.xOpenctiIssuerType = xOpenctiIssuerType; }

    @JsonProperty("x_opencti_subject_type")
    public String getXOpenctiSubjectType() { return xOpenctiSubjectType; }
    public void setXOpenctiSubjectType(String xOpenctiSubjectType) { this.xOpenctiSubjectType = xOpenctiSubjectType; }

    // Windows Registry Key getters/setters
    @JsonProperty("registry_key")
    public String getRegistryKey() { return registryKey; }
    public void setRegistryKey(String registryKey) { this.registryKey = registryKey; }

    @JsonProperty("registry_values")
    public List<WindowsRegistryValue> getRegistryValues() { return registryValues; }
    public void setRegistryValues(List<WindowsRegistryValue> registryValues) { this.registryValues = registryValues; }

    // X509 Certificate getters/setters
    @JsonProperty("x509_hashes")
    public Map<String, String> getX509Hashes() { return x509Hashes; }
    public void setX509Hashes(Map<String, String> x509Hashes) { this.x509Hashes = x509Hashes; }

    // User Account getters/setters
    @JsonProperty("user_id")
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getCredential() { return credential; }
    public void setCredential(String credential) { this.credential = credential; }

    @JsonProperty("account_login")
    public String getAccountLogin() { return accountLogin; }
    public void setAccountLogin(String accountLogin) { this.accountLogin = accountLogin; }

    @JsonProperty("account_type")
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    @JsonProperty("account_display_name")
    public String getAccountDisplayName() { return accountDisplayName; }
    public void setAccountDisplayName(String accountDisplayName) { this.accountDisplayName = accountDisplayName; }

    @JsonProperty("is_service_account")
    public Boolean getIsServiceAccount() { return isServiceAccount; }
    public void setIsServiceAccount(Boolean isServiceAccount) { this.isServiceAccount = isServiceAccount; }

    @JsonProperty("is_privileged")
    public Boolean getIsPrivileged() { return isPrivileged; }
    public void setIsPrivileged(Boolean isPrivileged) { this.isPrivileged = isPrivileged; }

    @JsonProperty("can_escalate_privs")
    public Boolean getCanEscalatePrivs() { return canEscalatePrivs; }
    public void setCanEscalatePrivs(Boolean canEscalatePrivs) { this.canEscalatePrivs = canEscalatePrivs; }

    @JsonProperty("is_disabled")
    public Boolean getIsDisabled() { return isDisabled; }
    public void setIsDisabled(Boolean isDisabled) { this.isDisabled = isDisabled; }

    @JsonProperty("account_created")
    public StixDate getAccountCreated() { return accountCreated; }
    public void setAccountCreated(StixDate accountCreated) { this.accountCreated = accountCreated; }

    @JsonProperty("account_expires")
    public StixDate getAccountExpires() { return accountExpires; }
    public void setAccountExpires(StixDate accountExpires) { this.accountExpires = accountExpires; }

    @JsonProperty("credential_last_changed")
    public StixDate getCredentialLastChanged() { return credentialLastChanged; }
    public void setCredentialLastChanged(StixDate credentialLastChanged) { this.credentialLastChanged = credentialLastChanged; }

    @JsonProperty("account_first_login")
    public StixDate getAccountFirstLogin() { return accountFirstLogin; }
    public void setAccountFirstLogin(StixDate accountFirstLogin) { this.accountFirstLogin = accountFirstLogin; }

    @JsonProperty("account_last_login")
    public StixDate getAccountLastLogin() { return accountLastLogin; }
    public void setAccountLastLogin(StixDate accountLastLogin) { this.accountLastLogin = accountLastLogin; }

    // Mutex getters/setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // X509 v3 Extensions getters/setters
    @JsonProperty("basic_constraints")
    public String getBasicConstraints() { return basicConstraints; }
    public void setBasicConstraints(String basicConstraints) { this.basicConstraints = basicConstraints; }

    @JsonProperty("name_constraints")
    public String getNameConstraints() { return nameConstraints; }
    public void setNameConstraints(String nameConstraints) { this.nameConstraints = nameConstraints; }

    @JsonProperty("policy_constraints")
    public String getPolicyConstraints() { return policyConstraints; }
    public void setPolicyConstraints(String policyConstraints) { this.policyConstraints = policyConstraints; }

    @JsonProperty("key_usage")
    public String getKeyUsage() { return keyUsage; }
    public void setKeyUsage(String keyUsage) { this.keyUsage = keyUsage; }

    @JsonProperty("extended_key_usage")
    public String getExtendedKeyUsage() { return extendedKeyUsage; }
    public void setExtendedKeyUsage(String extendedKeyUsage) { this.extendedKeyUsage = extendedKeyUsage; }

    @JsonProperty("subject_key_identifier")
    public String getSubjectKeyIdentifier() { return subjectKeyIdentifier; }
    public void setSubjectKeyIdentifier(String subjectKeyIdentifier) { this.subjectKeyIdentifier = subjectKeyIdentifier; }

    @JsonProperty("authority_key_identifier")
    public String getAuthorityKeyIdentifier() { return authorityKeyIdentifier; }
    public void setAuthorityKeyIdentifier(String authorityKeyIdentifier) { this.authorityKeyIdentifier = authorityKeyIdentifier; }

    @JsonProperty("subject_alternative_name")
    public String getSubjectAlternativeName() { return subjectAlternativeName; }
    public void setSubjectAlternativeName(String subjectAlternativeName) { this.subjectAlternativeName = subjectAlternativeName; }

    @JsonProperty("issuer_alternative_name")
    public String getIssuerAlternativeName() { return issuerAlternativeName; }
    public void setIssuerAlternativeName(String issuerAlternativeName) { this.issuerAlternativeName = issuerAlternativeName; }

    @JsonProperty("subject_directory_attributes")
    public String getSubjectDirectoryAttributes() { return subjectDirectoryAttributes; }
    public void setSubjectDirectoryAttributes(String subjectDirectoryAttributes) { this.subjectDirectoryAttributes = subjectDirectoryAttributes; }

    @JsonProperty("crl_distribution_points")
    public String getCrlDistributionPoints() { return crlDistributionPoints; }
    public void setCrlDistributionPoints(String crlDistributionPoints) { this.crlDistributionPoints = crlDistributionPoints; }

    @JsonProperty("inhibit_any_policy")
    public String getInhibitAnyPolicy() { return inhibitAnyPolicy; }
    public void setInhibitAnyPolicy(String inhibitAnyPolicy) { this.inhibitAnyPolicy = inhibitAnyPolicy; }

    @JsonProperty("certificate_policies")
    public String getCertificatePolicies() { return certificatePolicies; }
    public void setCertificatePolicies(String certificatePolicies) { this.certificatePolicies = certificatePolicies; }

    @JsonProperty("policy_mappings")
    public String getPolicyMappings() { return policyMappings; }
    public void setPolicyMappings(String policyMappings) { this.policyMappings = policyMappings; }

    @JsonProperty("private_key_usage_period_not_before")
    public StixDate getPrivateKeyUsagePeriodNotBefore() { return privateKeyUsagePeriodNotBefore; }
    public void setPrivateKeyUsagePeriodNotBefore(StixDate privateKeyUsagePeriodNotBefore) { this.privateKeyUsagePeriodNotBefore = privateKeyUsagePeriodNotBefore; }

    @JsonProperty("private_key_usage_period_not_after")
    public StixDate getPrivateKeyUsagePeriodNotAfter() { return privateKeyUsagePeriodNotAfter; }
    public void setPrivateKeyUsagePeriodNotAfter(StixDate privateKeyUsagePeriodNotAfter) { this.privateKeyUsagePeriodNotAfter = privateKeyUsagePeriodNotAfter; }

    // Media Content getters/setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @JsonProperty("media_category")
    public String getMediaCategory() { return mediaCategory; }
    public void setMediaCategory(String mediaCategory) { this.mediaCategory = mediaCategory; }

    @JsonProperty("publication_date")
    public StixDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(StixDate publicationDate) { this.publicationDate = publicationDate; }

    // SSH Key getters/setters
    @JsonProperty("public_key")
    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }

    @JsonProperty("fingerprint_sha256")
    public String getFingerprintSha256() { return fingerprintSha256; }
    public void setFingerprintSha256(String fingerprintSha256) { this.fingerprintSha256 = fingerprintSha256; }

    @JsonProperty("fingerprint_md5")
    public String getFingerprintMd5() { return fingerprintMd5; }
    public void setFingerprintMd5(String fingerprintMd5) { this.fingerprintMd5 = fingerprintMd5; }

    @JsonProperty("key_length")
    public Integer getKeyLength() { return keyLength; }
    public void setKeyLength(Integer keyLength) { this.keyLength = keyLength; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public StixDate getCreated() { return created; }
    public void setCreated(StixDate created) { this.created = created; }

    @JsonProperty("key_type")
    public String getKeyType() { return keyType; }
    public void setKeyType(String keyType) { this.keyType = keyType; }

    // Windows Registry Value getters/setters
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    @JsonProperty("data_type")
    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }

    // Bank Account getters/setters
    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public String getBic() { return bic; }
    public void setBic(String bic) { this.bic = bic; }

    @JsonProperty("account_number")
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    // Payment Card getters/setters
    @JsonProperty("card_number")
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    @JsonProperty("expiration_date")
    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    @JsonProperty("holder_name")
    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    // Persona getters/setters
    @JsonProperty("persona_name")
    public String getPersonaName() { return personaName; }
    public void setPersonaName(String personaName) { this.personaName = personaName; }

    @JsonProperty("persona_type")
    public String getPersonaType() { return personaType; }
    public void setPersonaType(String personaType) { this.personaType = personaType; }
}

