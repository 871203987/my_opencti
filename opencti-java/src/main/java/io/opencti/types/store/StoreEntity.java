package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixDate;
import io.opencti.types.stix.common.StixId;

import java.util.List;
import java.util.Map;

/**
 * Store Entity type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Represents an entity stored in the OpenCTI database.
 */
public class StoreEntity extends StoreObject {
    protected String name;
    protected String description;
    protected List<String> aliases;
    protected StixId createdByRef;
    protected List<StixId> objectMarkingRefs;
    protected List<String> grantedRefs;
    protected List<String> labels;
    protected Integer confidence;
    protected Boolean revoked;
    protected StixDate created;
    protected StixDate modified;
    protected Map<String, Object> extensions;
    protected Boolean isInferred;
    protected List<String> creatorIds;

    // STIX 2.0/2.1 杞崲闇€瑕佺殑棰濆灞炴€?
    protected List<KillChainPhase> killChainPhases;
    protected List<ExternalReference> externalReferences;
    protected Boolean isFamily;
    protected List<String> malwareTypes;
    protected StixDate firstSeen;
    protected StixDate lastSeen;
    protected List<String> architectureExecutionEnvs;
    protected List<String> implementationLanguages;
    protected List<String> capabilities;
    protected List<String> reportTypes;
    protected StixDate published;
    protected List<String> objectRefs;
    protected String content;
    protected String abstract$;
    protected List<String> authors;
    protected StixDate firstObserved;
    protected StixDate lastObserved;
    protected Integer numberObserved;
    protected List<Object> objects;
    protected String explanation;
    protected String opinion;

    // OpenCTI鎵╁睍灞炴€?
    protected List<String> assigneeIds;
    protected List<String> participantIds;
    protected Object authorizedMembers;
    protected List<String> labelsIds;
    protected String createdByRefId;
    protected String createdByRefType;
    protected List<Object> pirInformation;
    protected List<Object> metrics;

    // Identity鐗规湁灞炴€?
    protected String contactInformation;
    protected String identityClass;
    protected List<String> roles;
    protected List<String> sectors;
    protected String xOpenctiFirstname;
    protected String xOpenctiLastname;
    protected String xOpenctiOrganizationType;
    protected String xOpenctiReliability;

    // Location鐗规湁灞炴€?
    protected String latitude;
    protected String longitude;
    protected Double precision;
    protected String region;
    protected String country;
    protected String city;
    protected String streetAddress;
    protected String postalCode;
    protected String xOpenctiLocationType;

    // Campaign鐗规湁灞炴€?
    protected String objective;

    // Tool鐗规湁灞炴€?
    protected List<String> toolTypes;
    protected String toolVersion;

    // Vulnerability鐗规湁灞炴€?(CVSS3)
    protected String xOpenctiCvssVectorString;
    protected Double xOpenctiCvssBaseScore;
    protected String xOpenctiCvssBaseSeverity;
    protected String xOpenctiCvssAttackVector;
    protected String xOpenctiCvssAttackComplexity;
    protected String xOpenctiCvssPrivilegesRequired;
    protected String xOpenctiCvssUserInteraction;
    protected String xOpenctiCvssScope;
    protected String xOpenctiCvssConfidentialityImpact;
    protected String xOpenctiCvssIntegrityImpact;
    protected String xOpenctiCvssAvailabilityImpact;
    protected String xOpenctiCvssExploitCodeMaturity;
    protected String xOpenctiCvssRemediationLevel;
    protected String xOpenctiCvssReportConfidence;
    protected Double xOpenctiCvssTemporalScore;

    // Vulnerability鐗规湁灞炴€?(CVSS2)
    protected String xOpenctiCvssV2VectorString;
    protected Double xOpenctiCvssV2BaseScore;
    protected String xOpenctiCvssV2AccessVector;
    protected String xOpenctiCvssV2AccessComplexity;
    protected String xOpenctiCvssV2Authentication;
    protected String xOpenctiCvssV2ConfidentialityImpact;
    protected String xOpenctiCvssV2IntegrityImpact;
    protected String xOpenctiCvssV2AvailabilityImpact;
    protected String xOpenctiCvssV2Exploitability;
    protected String xOpenctiCvssV2RemediationLevel;
    protected String xOpenctiCvssV2ReportConfidence;
    protected Double xOpenctiCvssV2TemporalScore;

    // Vulnerability鐗规湁灞炴€?(CVSS4)
    protected String xOpenctiCvssV4VectorString;
    protected Double xOpenctiCvssV4BaseScore;
    protected String xOpenctiCvssV4BaseSeverity;
    protected String xOpenctiCvssV4AttackVector;
    protected String xOpenctiCvssV4AttackComplexity;
    protected String xOpenctiCvssV4AttackRequirements;
    protected String xOpenctiCvssV4PrivilegesRequired;
    protected String xOpenctiCvssV4UserInteraction;
    protected String xOpenctiCvssV4ConfidentialityImpactV;
    protected String xOpenctiCvssV4ConfidentialityImpactS;
    protected String xOpenctiCvssV4IntegrityImpactV;
    protected String xOpenctiCvssV4IntegrityImpactS;
    protected String xOpenctiCvssV4AvailabilityImpactV;
    protected String xOpenctiCvssV4AvailabilityImpactS;
    protected String xOpenctiCvssV4ExploitMaturity;

    // Vulnerability鍏朵粬灞炴€?
    protected String xOpenctiCwe;
    protected Boolean xOpenctiCisaKev;
    protected Double xOpenctiEpssScore;
    protected Double xOpenctiEpssPercentile;
    protected Double xOpenctiScore;
    protected StixDate xOpenctiFirstSeenActive;

    // Threat Actor鐗规湁灞炴€?
    protected List<String> threatActorTypes;
    protected String sophistication;
    protected String resourceLevel;
    protected String primaryMotivation;
    protected List<String> secondaryMotivations;
    protected List<String> personalMotivations;
    protected List<String> goals;

    // Infrastructure鐗规湁灞炴€?
    protected List<String> infrastructureTypes;

    // Course of Action鐗规湁灞炴€?
    protected String xMitreId;
    protected String xMitreDetection;
    protected List<String> xMitrePermissionsRequired;
    protected List<String> xMitrePlatforms;
    protected List<String> collectionLayers;

    // Incident鐗规湁灞炴€?
    protected String incidentType;
    protected String severity;
    protected String source;

    // Report鐗规湁灞炴€?
    protected String contentMapping;

    // Note鐗规湁灞炴€?
    protected List<String> noteTypes;
    protected Integer likelihood;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @JsonProperty("created_by_ref")
    public StixId getCreatedByRef() {
        return createdByRef;
    }

    public void setCreatedByRef(StixId createdByRef) {
        this.createdByRef = createdByRef;
    }

    @JsonProperty("object_marking_refs")
    public List<StixId> getObjectMarkingRefs() {
        return objectMarkingRefs;
    }

    public void setObjectMarkingRefs(List<StixId> objectMarkingRefs) {
        this.objectMarkingRefs = objectMarkingRefs;
    }

    @JsonProperty("granted_refs")
    public List<String> getGrantedRefs() {
        return grantedRefs;
    }

    public void setGrantedRefs(List<String> grantedRefs) {
        this.grantedRefs = grantedRefs;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    public StixDate getCreated() {
        return created;
    }

    public void setCreated(StixDate created) {
        this.created = created;
    }

    public StixDate getModified() {
        return modified;
    }

    public void setModified(StixDate modified) {
        this.modified = modified;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    @JsonProperty("is_inferred")
    public Boolean getIsInferred() {
        return isInferred;
    }

    public void setIsInferred(Boolean isInferred) {
        this.isInferred = isInferred;
    }

    @JsonProperty("creator_ids")
    public List<String> getCreatorIds() {
        return creatorIds;
    }

    public void setCreatorIds(List<String> creatorIds) {
        this.creatorIds = creatorIds;
    }

    // STIX 2.0/2.1 杞崲闇€瑕佺殑棰濆灞炴€х殑getter鍜宻etter

    @JsonProperty("kill_chain_phases")
    public List<KillChainPhase> getKillChainPhases() {
        return killChainPhases;
    }

    public void setKillChainPhases(List<KillChainPhase> killChainPhases) {
        this.killChainPhases = killChainPhases;
    }

    @JsonProperty("external_references")
    public List<ExternalReference> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(List<ExternalReference> externalReferences) {
        this.externalReferences = externalReferences;
    }

    @JsonProperty("is_family")
    public Boolean getIsFamily() {
        return isFamily;
    }

    public void setIsFamily(Boolean isFamily) {
        this.isFamily = isFamily;
    }

    @JsonProperty("malware_types")
    public List<String> getMalwareTypes() {
        return malwareTypes;
    }

    public void setMalwareTypes(List<String> malwareTypes) {
        this.malwareTypes = malwareTypes;
    }

    @JsonProperty("first_seen")
    public StixDate getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(StixDate firstSeen) {
        this.firstSeen = firstSeen;
    }

    @JsonProperty("last_seen")
    public StixDate getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(StixDate lastSeen) {
        this.lastSeen = lastSeen;
    }

    @JsonProperty("architecture_execution_envs")
    public List<String> getArchitectureExecutionEnvs() {
        return architectureExecutionEnvs;
    }

    public void setArchitectureExecutionEnvs(List<String> architectureExecutionEnvs) {
        this.architectureExecutionEnvs = architectureExecutionEnvs;
    }

    @JsonProperty("implementation_languages")
    public List<String> getImplementationLanguages() {
        return implementationLanguages;
    }

    public void setImplementationLanguages(List<String> implementationLanguages) {
        this.implementationLanguages = implementationLanguages;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    @JsonProperty("report_types")
    public List<String> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(List<String> reportTypes) {
        this.reportTypes = reportTypes;
    }

    public StixDate getPublished() {
        return published;
    }

    public void setPublished(StixDate published) {
        this.published = published;
    }

    @JsonProperty("object_refs")
    public List<String> getObjectRefs() {
        return objectRefs;
    }

    public void setObjectRefs(List<String> objectRefs) {
        this.objectRefs = objectRefs;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAbstract$() {
        return abstract$;
    }

    public void setAbstract$(String abstract$) {
        this.abstract$ = abstract$;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @JsonProperty("first_observed")
    public StixDate getFirstObserved() {
        return firstObserved;
    }

    public void setFirstObserved(StixDate firstObserved) {
        this.firstObserved = firstObserved;
    }

    @JsonProperty("last_observed")
    public StixDate getLastObserved() {
        return lastObserved;
    }

    public void setLastObserved(StixDate lastObserved) {
        this.lastObserved = lastObserved;
    }

    @JsonProperty("number_observed")
    public Integer getNumberObserved() {
        return numberObserved;
    }

    public void setNumberObserved(Integer numberObserved) {
        this.numberObserved = numberObserved;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    @JsonProperty("assignee_ids")
    public List<String> getAssigneeIds() {
        return assigneeIds;
    }

    public void setAssigneeIds(List<String> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }

    @JsonProperty("participant_ids")
    public List<String> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<String> participantIds) {
        this.participantIds = participantIds;
    }

    @JsonProperty("authorized_members")
    public Object getAuthorizedMembers() {
        return authorizedMembers;
    }

    public void setAuthorizedMembers(Object authorizedMembers) {
        this.authorizedMembers = authorizedMembers;
    }

    @JsonProperty("labels_ids")
    public List<String> getLabelsIds() {
        return labelsIds;
    }

    public void setLabelsIds(List<String> labelsIds) {
        this.labelsIds = labelsIds;
    }

    @JsonProperty("created_by_ref_id")
    public String getCreatedByRefId() {
        return createdByRefId;
    }

    public void setCreatedByRefId(String createdByRefId) {
        this.createdByRefId = createdByRefId;
    }

    @JsonProperty("created_by_ref_type")
    public String getCreatedByRefType() {
        return createdByRefType;
    }

    public void setCreatedByRefType(String createdByRefType) {
        this.createdByRefType = createdByRefType;
    }

    @JsonProperty("pir_information")
    public List<Object> getPirInformation() {
        return pirInformation;
    }

    public void setPirInformation(List<Object> pirInformation) {
        this.pirInformation = pirInformation;
    }

    @JsonProperty("metrics")
    public List<Object> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Object> metrics) {
        this.metrics = metrics;
    }

    // Identity getters/setters
    @JsonProperty("contact_information")
    public String getContactInformation() { return contactInformation; }
    public void setContactInformation(String contactInformation) { this.contactInformation = contactInformation; }

    @JsonProperty("identity_class")
    public String getIdentityClass() { return identityClass; }
    public void setIdentityClass(String identityClass) { this.identityClass = identityClass; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public List<String> getSectors() { return sectors; }
    public void setSectors(List<String> sectors) { this.sectors = sectors; }

    @JsonProperty("x_opencti_firstname")
    public String getXOpenctiFirstname() { return xOpenctiFirstname; }
    public void setXOpenctiFirstname(String xOpenctiFirstname) { this.xOpenctiFirstname = xOpenctiFirstname; }

    @JsonProperty("x_opencti_lastname")
    public String getXOpenctiLastname() { return xOpenctiLastname; }
    public void setXOpenctiLastname(String xOpenctiLastname) { this.xOpenctiLastname = xOpenctiLastname; }

    @JsonProperty("x_opencti_organization_type")
    public String getXOpenctiOrganizationType() { return xOpenctiOrganizationType; }
    public void setXOpenctiOrganizationType(String xOpenctiOrganizationType) { this.xOpenctiOrganizationType = xOpenctiOrganizationType; }

    @JsonProperty("x_opencti_reliability")
    public String getXOpenctiReliability() { return xOpenctiReliability; }
    public void setXOpenctiReliability(String xOpenctiReliability) { this.xOpenctiReliability = xOpenctiReliability; }

    // Location getters/setters
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }

    public Double getPrecision() { return precision; }
    public void setPrecision(Double precision) { this.precision = precision; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    @JsonProperty("street_address")
    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    @JsonProperty("postal_code")
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    @JsonProperty("x_opencti_location_type")
    public String getXOpenctiLocationType() { return xOpenctiLocationType; }
    public void setXOpenctiLocationType(String xOpenctiLocationType) { this.xOpenctiLocationType = xOpenctiLocationType; }

    // Campaign getters/setters
    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    // Tool getters/setters
    @JsonProperty("tool_types")
    public List<String> getToolTypes() { return toolTypes; }
    public void setToolTypes(List<String> toolTypes) { this.toolTypes = toolTypes; }

    @JsonProperty("tool_version")
    public String getToolVersion() { return toolVersion; }
    public void setToolVersion(String toolVersion) { this.toolVersion = toolVersion; }

    // Threat Actor getters/setters
    @JsonProperty("threat_actor_types")
    public List<String> getThreatActorTypes() { return threatActorTypes; }
    public void setThreatActorTypes(List<String> threatActorTypes) { this.threatActorTypes = threatActorTypes; }

    public String getSophistication() { return sophistication; }
    public void setSophistication(String sophistication) { this.sophistication = sophistication; }

    @JsonProperty("resource_level")
    public String getResourceLevel() { return resourceLevel; }
    public void setResourceLevel(String resourceLevel) { this.resourceLevel = resourceLevel; }

    @JsonProperty("primary_motivation")
    public String getPrimaryMotivation() { return primaryMotivation; }
    public void setPrimaryMotivation(String primaryMotivation) { this.primaryMotivation = primaryMotivation; }

    @JsonProperty("secondary_motivations")
    public List<String> getSecondaryMotivations() { return secondaryMotivations; }
    public void setSecondaryMotivations(List<String> secondaryMotivations) { this.secondaryMotivations = secondaryMotivations; }

    @JsonProperty("personal_motivations")
    public List<String> getPersonalMotivations() { return personalMotivations; }
    public void setPersonalMotivations(List<String> personalMotivations) { this.personalMotivations = personalMotivations; }

    public List<String> getGoals() { return goals; }
    public void setGoals(List<String> goals) { this.goals = goals; }

    // Infrastructure getters/setters
    @JsonProperty("infrastructure_types")
    public List<String> getInfrastructureTypes() { return infrastructureTypes; }
    public void setInfrastructureTypes(List<String> infrastructureTypes) { this.infrastructureTypes = infrastructureTypes; }

    // Course of Action getters/setters
    @JsonProperty("x_mitre_id")
    public String getXMitreId() { return xMitreId; }
    public void setXMitreId(String xMitreId) { this.xMitreId = xMitreId; }

    @JsonProperty("x_mitre_detection")
    public String getXMitreDetection() { return xMitreDetection; }
    public void setXMitreDetection(String xMitreDetection) { this.xMitreDetection = xMitreDetection; }

    @JsonProperty("x_mitre_permissions_required")
    public List<String> getXMitrePermissionsRequired() { return xMitrePermissionsRequired; }
    public void setXMitrePermissionsRequired(List<String> xMitrePermissionsRequired) { this.xMitrePermissionsRequired = xMitrePermissionsRequired; }

    @JsonProperty("x_mitre_platforms")
    public List<String> getXMitrePlatforms() { return xMitrePlatforms; }
    public void setXMitrePlatforms(List<String> xMitrePlatforms) { this.xMitrePlatforms = xMitrePlatforms; }

    @JsonProperty("collection_layers")
    public List<String> getCollectionLayers() { return collectionLayers; }
    public void setCollectionLayers(List<String> collectionLayers) { this.collectionLayers = collectionLayers; }

    // Incident getters/setters
    @JsonProperty("incident_type")
    public String getIncidentType() { return incidentType; }
    public void setIncidentType(String incidentType) { this.incidentType = incidentType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    // Report getters/setters
    @JsonProperty("content_mapping")
    public String getContentMapping() { return contentMapping; }
    public void setContentMapping(String contentMapping) { this.contentMapping = contentMapping; }

    // Note getters/setters
    @JsonProperty("note_types")
    public List<String> getNoteTypes() { return noteTypes; }
    public void setNoteTypes(List<String> noteTypes) { this.noteTypes = noteTypes; }

    public Integer getLikelihood() { return likelihood; }
    public void setLikelihood(Integer likelihood) { this.likelihood = likelihood; }

    // CVSS3 getters/setters
    @JsonProperty("x_opencti_cvss_vector_string")
    public String getXOpenctiCvssVectorString() { return xOpenctiCvssVectorString; }
    public void setXOpenctiCvssVectorString(String xOpenctiCvssVectorString) { this.xOpenctiCvssVectorString = xOpenctiCvssVectorString; }

    @JsonProperty("x_opencti_cvss_base_score")
    public Double getXOpenctiCvssBaseScore() { return xOpenctiCvssBaseScore; }
    public void setXOpenctiCvssBaseScore(Double xOpenctiCvssBaseScore) { this.xOpenctiCvssBaseScore = xOpenctiCvssBaseScore; }

    @JsonProperty("x_opencti_cvss_base_severity")
    public String getXOpenctiCvssBaseSeverity() { return xOpenctiCvssBaseSeverity; }
    public void setXOpenctiCvssBaseSeverity(String xOpenctiCvssBaseSeverity) { this.xOpenctiCvssBaseSeverity = xOpenctiCvssBaseSeverity; }

    @JsonProperty("x_opencti_cvss_attack_vector")
    public String getXOpenctiCvssAttackVector() { return xOpenctiCvssAttackVector; }
    public void setXOpenctiCvssAttackVector(String xOpenctiCvssAttackVector) { this.xOpenctiCvssAttackVector = xOpenctiCvssAttackVector; }

    @JsonProperty("x_opencti_cvss_attack_complexity")
    public String getXOpenctiCvssAttackComplexity() { return xOpenctiCvssAttackComplexity; }
    public void setXOpenctiCvssAttackComplexity(String xOpenctiCvssAttackComplexity) { this.xOpenctiCvssAttackComplexity = xOpenctiCvssAttackComplexity; }

    @JsonProperty("x_opencti_cvss_privileges_required")
    public String getXOpenctiCvssPrivilegesRequired() { return xOpenctiCvssPrivilegesRequired; }
    public void setXOpenctiCvssPrivilegesRequired(String xOpenctiCvssPrivilegesRequired) { this.xOpenctiCvssPrivilegesRequired = xOpenctiCvssPrivilegesRequired; }

    @JsonProperty("x_opencti_cvss_user_interaction")
    public String getXOpenctiCvssUserInteraction() { return xOpenctiCvssUserInteraction; }
    public void setXOpenctiCvssUserInteraction(String xOpenctiCvssUserInteraction) { this.xOpenctiCvssUserInteraction = xOpenctiCvssUserInteraction; }

    @JsonProperty("x_opencti_cvss_scope")
    public String getXOpenctiCvssScope() { return xOpenctiCvssScope; }
    public void setXOpenctiCvssScope(String xOpenctiCvssScope) { this.xOpenctiCvssScope = xOpenctiCvssScope; }

    @JsonProperty("x_opencti_cvss_confidentiality_impact")
    public String getXOpenctiCvssConfidentialityImpact() { return xOpenctiCvssConfidentialityImpact; }
    public void setXOpenctiCvssConfidentialityImpact(String xOpenctiCvssConfidentialityImpact) { this.xOpenctiCvssConfidentialityImpact = xOpenctiCvssConfidentialityImpact; }

    @JsonProperty("x_opencti_cvss_integrity_impact")
    public String getXOpenctiCvssIntegrityImpact() { return xOpenctiCvssIntegrityImpact; }
    public void setXOpenctiCvssIntegrityImpact(String xOpenctiCvssIntegrityImpact) { this.xOpenctiCvssIntegrityImpact = xOpenctiCvssIntegrityImpact; }

    @JsonProperty("x_opencti_cvss_availability_impact")
    public String getXOpenctiCvssAvailabilityImpact() { return xOpenctiCvssAvailabilityImpact; }
    public void setXOpenctiCvssAvailabilityImpact(String xOpenctiCvssAvailabilityImpact) { this.xOpenctiCvssAvailabilityImpact = xOpenctiCvssAvailabilityImpact; }

    // CVSS2 getters/setters
    @JsonProperty("x_opencti_cvss_v2_vector_string")
    public String getXOpenctiCvssV2VectorString() { return xOpenctiCvssV2VectorString; }
    public void setXOpenctiCvssV2VectorString(String xOpenctiCvssV2VectorString) { this.xOpenctiCvssV2VectorString = xOpenctiCvssV2VectorString; }

    @JsonProperty("x_opencti_cvss_v2_base_score")
    public Double getXOpenctiCvssV2BaseScore() { return xOpenctiCvssV2BaseScore; }
    public void setXOpenctiCvssV2BaseScore(Double xOpenctiCvssV2BaseScore) { this.xOpenctiCvssV2BaseScore = xOpenctiCvssV2BaseScore; }

    // CVSS4 getters/setters
    @JsonProperty("x_opencti_cvss_v4_vector_string")
    public String getXOpenctiCvssV4VectorString() { return xOpenctiCvssV4VectorString; }
    public void setXOpenctiCvssV4VectorString(String xOpenctiCvssV4VectorString) { this.xOpenctiCvssV4VectorString = xOpenctiCvssV4VectorString; }

    @JsonProperty("x_opencti_cvss_v4_base_score")
    public Double getXOpenctiCvssV4BaseScore() { return xOpenctiCvssV4BaseScore; }
    public void setXOpenctiCvssV4BaseScore(Double xOpenctiCvssV4BaseScore) { this.xOpenctiCvssV4BaseScore = xOpenctiCvssV4BaseScore; }

    // Vulnerability鍏朵粬getters/setters
    @JsonProperty("x_opencti_cwe")
    public String getXOpenctiCwe() { return xOpenctiCwe; }
    public void setXOpenctiCwe(String xOpenctiCwe) { this.xOpenctiCwe = xOpenctiCwe; }

    @JsonProperty("x_opencti_cisa_kev")
    public Boolean getXOpenctiCisaKev() { return xOpenctiCisaKev; }
    public void setXOpenctiCisaKev(Boolean xOpenctiCisaKev) { this.xOpenctiCisaKev = xOpenctiCisaKev; }

    @JsonProperty("x_opencti_epss_score")
    public Double getXOpenctiEpssScore() { return xOpenctiEpssScore; }
    public void setXOpenctiEpssScore(Double xOpenctiEpssScore) { this.xOpenctiEpssScore = xOpenctiEpssScore; }

    @JsonProperty("x_opencti_epss_percentile")
    public Double getXOpenctiEpssPercentile() { return xOpenctiEpssPercentile; }
    public void setXOpenctiEpssPercentile(Double xOpenctiEpssPercentile) { this.xOpenctiEpssPercentile = xOpenctiEpssPercentile; }

    @JsonProperty("x_opencti_score")
    public Double getXOpenctiScore() { return xOpenctiScore; }
    public void setXOpenctiScore(Double xOpenctiScore) { this.xOpenctiScore = xOpenctiScore; }
}

