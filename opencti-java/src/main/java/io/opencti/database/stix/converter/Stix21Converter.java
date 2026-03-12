package io.opencti.database.stix.converter;

import io.opencti.types.store.StixFile;
import io.opencti.types.store.StoreEntity;
import io.opencti.types.store.StoreObject;
import io.opencti.types.stix.common.StixId;
import io.opencti.types.stix.common.StixObject;
import io.opencti.database.stix.StixConstants;
import io.opencti.types.stix.common.StixBundle;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts
 * STIX 2.1鏍煎紡杞崲鍣?
 *
 * @author OpenCTI Java閲嶅啓椤圭洰
 */
@Slf4j
public final class Stix21Converter {

    private Stix21Converter() {
        // 宸ュ叿绫伙紝绂佹瀹炰緥鍖?
    }

    // 杞崲鍣ㄦ敞鍐岃〃
    private static final Map<String, Function<StoreEntity, Map<String, Object>>> STIX_DOMAIN_CONVERTERS = new HashMap<>();
    private static final Map<String, Function<StoreEntity, Map<String, Object>>> STIX_META_CONVERTERS = new HashMap<>();

    static {
        // 娉ㄥ唽SDO杞崲鍣?
        registerStixDomainConverter("Identity", Stix21Converter::convertIdentityToStix);
        registerStixDomainConverter("Location", Stix21Converter::convertLocationToStix);
        registerStixDomainConverter("Incident", Stix21Converter::convertIncidentToStix);
        registerStixDomainConverter("Campaign", Stix21Converter::convertCampaignToStix);
        registerStixDomainConverter("Tool", Stix21Converter::convertToolToStix);
        registerStixDomainConverter("Vulnerability", Stix21Converter::convertVulnerabilityToStix);
        registerStixDomainConverter("Threat-Actor-Group", Stix21Converter::convertThreatActorGroupToStix);
        registerStixDomainConverter("Infrastructure", Stix21Converter::convertInfrastructureToStix);
        registerStixDomainConverter("Intrusion-Set", Stix21Converter::convertIntrusionSetToStix);
        registerStixDomainConverter("Course-Of-Action", Stix21Converter::convertCourseOfActionToStix);
        registerStixDomainConverter("Malware", Stix21Converter::convertMalwareToStix);
        registerStixDomainConverter("Attack-Pattern", Stix21Converter::convertAttackPatternToStix);
        registerStixDomainConverter("Report", Stix21Converter::convertReportToStix);
        registerStixDomainConverter("Note", Stix21Converter::convertNoteToStix);
        registerStixDomainConverter("Observed-Data", Stix21Converter::convertObservedDataToStix);
        registerStixDomainConverter("Opinion", Stix21Converter::convertOpinionToStix);

        // 娉ㄥ唽SMO杞崲鍣?
        registerStixMetaConverter("Marking-Definition", Stix21Converter::convertMarkingToStix);
        registerStixMetaConverter("Label", Stix21Converter::convertLabelToStix);
        registerStixMetaConverter("Kill-Chain-Phase", Stix21Converter::convertKillChainPhaseToStix);
        registerStixMetaConverter("External-Reference", Stix21Converter::convertExternalReferenceToStix);
    }

    /**
     * 鍘熸柟娉? registerStixDomainConverter
     * 娉ㄥ唽STIX鍩熷璞¤浆鎹㈠櫒
     */
    public static void registerStixDomainConverter(String type, Function<StoreEntity, Map<String, Object>> converter) {
        STIX_DOMAIN_CONVERTERS.put(type, converter);
    }

    /**
     * 鍘熸柟娉? registerStixMetaConverter
     * 娉ㄥ唽STIX鍏冨璞¤浆鎹㈠櫒
     */
    public static void registerStixMetaConverter(String type, Function<StoreEntity, Map<String, Object>> converter) {
        STIX_META_CONVERTERS.put(type, converter);
    }

    /**
     * 鍘熸柟娉? isTrustedStixId
     * 妫€鏌ユ槸鍚︿负鍙俊STIX ID
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:isTrustedStixId
     *
     * 鍙俊STIX ID鏄寚UUID鐗堟湰涓嶄负1鐨処D锛堥潪涓存椂ID锛?
     */
    public static boolean isTrustedStixId(String stixId) {
        if (stixId == null || stixId.isEmpty()) {
            return false;
        }
        // 妫€鏌ユ牸寮? type--uuid
        String[] segments = stixId.split("--");
        if (segments.length != 2) {
            return false;
        }
        String uuid = segments[1];
        // 妫€鏌UID鐗堟湰: 鐗堟湰1鏄椂闂存埑-based锛堜复鏃剁殑锛夛紝鍏朵粬鐗堟湰鏄ǔ瀹氱殑
        // UUID鏍煎紡: xxxxxxxx-xxxx-Mxxx-Nxxx-xxxxxxxxxxxx
        // M鏄増鏈彿
        if (uuid.length() < 14) {
            return false;
        }
        char versionChar = uuid.charAt(14);
        // 鐗堟湰1鏄复鏃禝D锛屽叾浠栫増鏈?2,3,4,5)鏄ǔ瀹氱殑
        return versionChar != '1';
    }

    /**
     * 鍘熸柟娉? convertTypeToStixType
     * 灏嗗唴閮ㄧ被鍨嬭浆鎹负STIX绫诲瀷
     */
    public static String convertTypeToStixType(String type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case "Task":
                return "x-opencti-task";
            case "Feedback":
                return "x-opencti-feedback";
            case "Case-Incident":
                return "x-opencti-incident";
            case "Case-Rfi":
                return "x-opencti-rfi";
            case "Case-Rft":
                return "x-opencti-rft";
            case "Case-Template":
                return "x-opencti-case-template";
            case "Task-Template":
                return "x-opencti-task-template";
            case "Data-Component":
                return "x-mitre-data-component";
            case "Data-Source":
                return "x-mitre-data-source";
            default:
                return type.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
        }
    }

    /**
     * 鍘熸柟娉? buildStixObject
     * 鏋勫缓鍩虹STIX瀵硅薄
     */
    public static Map<String, Object> buildStixObject(StoreObject instance) {
        Map<String, Object> stixObject = new HashMap<>();

        if (instance != null) {
            if (instance.getStandardId() != null) {
                stixObject.put("id", instance.getStandardId());
            }
            if (instance.getEntityType() != null) {
                stixObject.put("type", convertTypeToStixType(instance.getEntityType()));
            }
            stixObject.put("spec_version", StixConstants.STIX_SPEC_VERSION);
            if (instance.getCreatedAt() != null) {
                stixObject.put("created", instance.getCreatedAt().toString());
            }
            if (instance.getUpdatedAt() != null) {
                stixObject.put("modified", instance.getUpdatedAt().toString());
            }
        }

        return stixObject;
    }

    /**
     * 鍘熸柟娉? buildStixDomain
     * 鏋勫缓STIX鍩熷璞?
     */
    public static Map<String, Object> buildStixDomain(StoreEntity instance) {
        Map<String, Object> stixDomain = buildStixObject(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                stixDomain.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                stixDomain.put("description", instance.getDescription());
            }
            if (instance.getAliases() != null && !instance.getAliases().isEmpty()) {
                stixDomain.put("aliases", instance.getAliases());
            }
            if (instance.getConfidence() != null) {
                stixDomain.put("confidence", instance.getConfidence());
            }
            if (instance.getRevoked() != null) {
                stixDomain.put("revoked", instance.getRevoked());
            }
            if (instance.getLabels() != null && !instance.getLabels().isEmpty()) {
                stixDomain.put("labels", instance.getLabels());
            }

            // 鏉€浼ら摼闃舵
            List<Map<String, String>> killChainPhases = buildKillChainPhases(instance);
            if (!killChainPhases.isEmpty()) {
                stixDomain.put("kill_chain_phases", killChainPhases);
            }

            // 澶栭儴寮曠敤
            List<Map<String, Object>> externalReferences = buildExternalReferences(instance);
            if (!externalReferences.isEmpty()) {
                stixDomain.put("external_references", externalReferences);
            }

            if (instance.getObjectMarkingRefs() != null && !instance.getObjectMarkingRefs().isEmpty()) {
                List<String> markingRefs = new ArrayList<>();
                for (StixId ref : instance.getObjectMarkingRefs()) {
                    if (ref != null) {
                        markingRefs.add(ref.toString());
                    }
                }
                if (!markingRefs.isEmpty()) {
                    stixDomain.put("object_marking_refs", markingRefs);
                }
            }
            if (instance.getCreatedByRef() != null) {
                stixDomain.put("created_by_ref", instance.getCreatedByRef().toString());
            }

            Map<String, Object> extensions = buildOCTIExtensions(instance);
            if (!extensions.isEmpty()) {
                stixDomain.put("extensions", extensions);
            }
        }

        return stixDomain;
    }

    /**
     * 鍘熸柟娉? buildOCTIExtensions
     * 鏋勫缓OpenCTI鎵╁睍
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:buildOCTIExtensions
     */
    public static Map<String, Object> buildOCTIExtensions(StoreObject instance) {
        Map<String, Object> extensions = new HashMap<>();

        if (instance == null) {
            return extensions;
        }

        Map<String, Object> openctiExt = new HashMap<>();
        openctiExt.put("extension_type", StixConstants.EXTENSION_TYPE_PROPERTY);

        // 鍩虹灞炴€?
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
        if (instance.getModifiedAt() != null) {
            openctiExt.put("modified_at", StixConverterUtils.convertToStixDate(instance.getModifiedAt()));
        }

        // 鍒悕
        if (instance.getXOpenctiAliases() != null && !instance.getXOpenctiAliases().isEmpty()) {
            openctiExt.put("aliases", instance.getXOpenctiAliases());
        } else {
            openctiExt.put("aliases", Collections.emptyList());
        }

        // 鏂囦欢
        if (instance.getXOpenctiFiles() != null && !instance.getXOpenctiFiles().isEmpty()) {
            List<Map<String, Object>> files = new ArrayList<>();
            for (Object fileObj : instance.getXOpenctiFiles()) {
                if (!(fileObj instanceof StixFile)) continue;
                StixFile file = (StixFile) fileObj;
                Map<String, Object> fileMap = new HashMap<>();
                if (file.getName() != null) {
                    fileMap.put("name", file.getName());
                }
                if (file.getVersion() != null) {
                    fileMap.put("version", file.getVersion());
                }
                if (file.getMimeType() != null) {
                    fileMap.put("mime_type", file.getMimeType());
                }
                if (file.getObjectMarkingRefs() != null && !file.getObjectMarkingRefs().isEmpty()) {
                    List<String> markingRefs = file.getObjectMarkingRefs().stream()
                            .map(StixId::toString)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (!markingRefs.isEmpty()) {
                        fileMap.put("object_marking_refs", markingRefs);
                    }
                }
                if (file.getData() != null && !file.getData().isEmpty()) {
                    fileMap.put("data", file.getData());
                    fileMap.put("uri", "unknown");
                } else if (file.getId() != null) {
                    fileMap.put("uri", "/storage/get/" + file.getId());
                }
                if (!fileMap.isEmpty()) {
                    files.add(fileMap);
                }
            }
            if (!files.isEmpty()) {
                openctiExt.put("files", files);
            }
        }

        // STIX IDs - 杩囨护鎺塙UID v1鐨勪复鏃禝D
        if (instance.getStixIds() != null && !instance.getStixIds().isEmpty()) {
            List<String> trustedIds = instance.getStixIds().stream()
                    .filter(Stix21Converter::isTrustedStixId)
                    .collect(Collectors.toList());
            if (!trustedIds.isEmpty()) {
                openctiExt.put("stix_ids", trustedIds);
            }
        }

        // 鏄惁鎺ㄦ柇
        if (instance.getXOpenctiInferred() != null) {
            openctiExt.put("is_inferred", instance.getXOpenctiInferred());
        }

        // 鎵╁睍灞炴€э紙浠呭綋instance鏄疭toreEntity鏃讹級
        if (instance instanceof StoreEntity) {
            StoreEntity entity = (StoreEntity) instance;

            // 鎺堟潈寮曠敤
            if (entity.getGrantedRefs() != null && !entity.getGrantedRefs().isEmpty()) {
                openctiExt.put("granted_refs", entity.getGrantedRefs());
            }

            // 鍒涘缓鑰匢D
            if (entity.getCreatorIds() != null && !entity.getCreatorIds().isEmpty()) {
                openctiExt.put("creator_ids", entity.getCreatorIds());
            }

            // 鎺堟潈寮曠敤ID
            if (entity.getGrantedRefs() != null) {
                openctiExt.put("granted_refs_ids", entity.getGrantedRefs());
            }

            // 鎸囨淳浜篒D
            if (entity.getAssigneeIds() != null && !entity.getAssigneeIds().isEmpty()) {
                openctiExt.put("assignee_ids", entity.getAssigneeIds());
            }

            // 鍙備笌鑰匢D
            if (entity.getParticipantIds() != null && !entity.getParticipantIds().isEmpty()) {
                openctiExt.put("participant_ids", entity.getParticipantIds());
            }

            // 鎺堟潈鎴愬憳
            if (entity.getAuthorizedMembers() != null) {
                openctiExt.put("authorized_members", entity.getAuthorizedMembers());
            }

            // 宸ヤ綔娴両D
            if (entity.getXOpenctiWorkflowId() != null) {
                openctiExt.put("workflow_id", entity.getXOpenctiWorkflowId());
            }

            // 鏍囩ID
            if (entity.getLabelsIds() != null && !entity.getLabelsIds().isEmpty()) {
                openctiExt.put("labels_ids", entity.getLabelsIds());
            }

            // 鍒涘缓鑰呭紩鐢↖D鍜岀被鍨?
            if (entity.getCreatedByRefId() != null) {
                openctiExt.put("created_by_ref_id", entity.getCreatedByRefId());
            }
            if (entity.getCreatedByRefType() != null) {
                openctiExt.put("created_by_ref_type", entity.getCreatedByRefType());
            }

            // PIR淇℃伅
            if (entity.getPirInformation() != null && !entity.getPirInformation().isEmpty()) {
                openctiExt.put("pir_information", entity.getPirInformation());
            }

            // 鎸囨爣
            if (entity.getMetrics() != null && !entity.getMetrics().isEmpty()) {
                openctiExt.put("metrics", entity.getMetrics());
            }
        }

        if (!openctiExt.isEmpty()) {
            extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
        }

        return extensions;
    }

    /**
     * 鍘熸柟娉? buildMITREExtensions
     * 鏋勫缓MITRE鎵╁睍
     */
    public static Map<String, Object> buildMITREExtensions(StoreEntity instance) {
        Map<String, Object> extensions = new HashMap<>();

        if (instance == null) {
            return extensions;
        }

        Map<String, Object> mitreExt = new HashMap<>();
        mitreExt.put("extension_type", StixConstants.EXTENSION_TYPE_PROPERTY);

        // MITRE鎵╁睍灞炴€?
        // 娉ㄦ剰锛氳繖浜涘睘鎬ч渶瑕佷粠instance涓幏鍙栵紝浣哠toreEntity鐩墠娌℃湁杩欎簺灞炴€?
        // 濡傛灉闇€瑕侊紝鍙互鍚庣画娣诲姞

        if (!mitreExt.isEmpty()) {
            extensions.put(StixConstants.STIX_EXT_MITRE, mitreExt);
        }

        return extensions;
    }

    /**
     * 鍘熸柟娉? buildKillChainPhases
     * 鏋勫缓鏉€浼ら摼闃舵
     */
    public static List<Map<String, String>> buildKillChainPhases(StoreEntity instance) {
        if (instance == null || instance.getKillChainPhases() == null) {
            return Collections.emptyList();
        }

        List<Map<String, String>> phases = new ArrayList<>();
        for (var phase : instance.getKillChainPhases()) {
            if (phase != null) {
                Map<String, String> phaseMap = new HashMap<>();
                phaseMap.put("kill_chain_name", phase.getKillChainName());
                phaseMap.put("phase_name", phase.getPhaseName());
                phases.add(phaseMap);
            }
        }
        return phases;
    }

    /**
     * 鍘熸柟娉? buildExternalReferences
     * 鏋勫缓澶栭儴寮曠敤
     */
    public static List<Map<String, Object>> buildExternalReferences(StoreEntity instance) {
        if (instance == null || instance.getExternalReferences() == null) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> refs = new ArrayList<>();
        for (var ref : instance.getExternalReferences()) {
            if (ref != null) {
                Map<String, Object> refMap = new HashMap<>();
                refMap.put("source_name", ref.getSourceName());
                if (ref.getDescription() != null) {
                    refMap.put("description", ref.getDescription());
                }
                if (ref.getUrl() != null) {
                    refMap.put("url", ref.getUrl());
                }
                if (ref.getHashes() != null && !ref.getHashes().isEmpty()) {
                    refMap.put("hashes", ref.getHashes());
                }
                if (ref.getExternalId() != null) {
                    refMap.put("external_id", ref.getExternalId());
                }
                refs.add(refMap);
            }
        }
        return refs;
    }

    // ==================== SDO杞崲鏂规硶 ====================

    /**
     * 鍘熸柟娉? convertIdentityToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertIdentityToStix
     */
    public static Map<String, Object> convertIdentityToStix(StoreEntity instance) {
        Map<String, Object> identity = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                identity.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                identity.put("description", instance.getDescription());
            }
            if (instance.getContactInformation() != null) {
                identity.put("contact_information", instance.getContactInformation());
            }
            if (instance.getIdentityClass() != null) {
                identity.put("identity_class", instance.getIdentityClass());
            }
            if (instance.getRoles() != null && !instance.getRoles().isEmpty()) {
                identity.put("roles", instance.getRoles());
            }
            if (instance.getSectors() != null && !instance.getSectors().isEmpty()) {
                identity.put("sectors", instance.getSectors());
            }

            // 娣诲姞OpenCTI鎵╁睍
            @SuppressWarnings("unchecked")
            Map<String, Object> extensions = (Map<String, Object>) identity.getOrDefault("extensions", new HashMap<>());
            @SuppressWarnings("unchecked")
            Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

            if (instance.getXOpenctiFirstname() != null) {
                openctiExt.put("firstname", instance.getXOpenctiFirstname());
            }
            if (instance.getXOpenctiLastname() != null) {
                openctiExt.put("lastname", instance.getXOpenctiLastname());
            }
            if (instance.getXOpenctiOrganizationType() != null) {
                openctiExt.put("organization_type", instance.getXOpenctiOrganizationType());
            }
            if (instance.getXOpenctiReliability() != null) {
                openctiExt.put("reliability", instance.getXOpenctiReliability());
            }
            if (instance.getXOpenctiScore() != null) {
                openctiExt.put("score", instance.getXOpenctiScore());
            }

            if (!openctiExt.isEmpty()) {
                extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
                identity.put("extensions", extensions);
            }
        }

        return identity;
    }

    /**
     * 鍘熸柟娉? convertLocationToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertLocationToStix
     */
    public static Map<String, Object> convertLocationToStix(StoreEntity instance) {
        Map<String, Object> location = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                location.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                location.put("description", instance.getDescription());
            }
            if (instance.getLatitude() != null) {
                location.put("latitude", Double.parseDouble(instance.getLatitude()));
            }
            if (instance.getLongitude() != null) {
                location.put("longitude", Double.parseDouble(instance.getLongitude()));
            }
            if (instance.getPrecision() != null) {
                location.put("precision", instance.getPrecision());
            }
            if (instance.getRegion() != null) {
                location.put("region", instance.getRegion());
            }
            if (instance.getCountry() != null) {
                location.put("country", instance.getCountry());
            }
            if (instance.getCity() != null) {
                location.put("city", instance.getCity());
            }
            if (instance.getStreetAddress() != null) {
                location.put("street_address", instance.getStreetAddress());
            }
            if (instance.getPostalCode() != null) {
                location.put("postal_code", instance.getPostalCode());
            }

            // 娣诲姞OpenCTI鎵╁睍
            @SuppressWarnings("unchecked")
            Map<String, Object> extensions = (Map<String, Object>) location.getOrDefault("extensions", new HashMap<>());
            @SuppressWarnings("unchecked")
            Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

            if (instance.getXOpenctiLocationType() != null) {
                openctiExt.put("location_type", instance.getXOpenctiLocationType());
            }

            if (!openctiExt.isEmpty()) {
                extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
                location.put("extensions", extensions);
            }
        }

        return location;
    }

    /**
     * 鍘熸柟娉? convertIncidentToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertIncidentToStix
     */
    public static Map<String, Object> convertIncidentToStix(StoreEntity instance) {
        Map<String, Object> incident = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                incident.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                incident.put("description", instance.getDescription());
            }
            if (instance.getFirstSeen() != null) {
                incident.put("first_seen", StixConverterUtils.convertToStixDate(instance.getFirstSeen()));
            }
            if (instance.getLastSeen() != null) {
                incident.put("last_seen", StixConverterUtils.convertToStixDate(instance.getLastSeen()));
            }
            if (instance.getAliases() != null && !instance.getAliases().isEmpty()) {
                incident.put("aliases", instance.getAliases());
            }
            if (instance.getObjective() != null) {
                incident.put("objective", instance.getObjective());
            }
            if (instance.getIncidentType() != null) {
                incident.put("incident_type", instance.getIncidentType());
            }
            if (instance.getSeverity() != null) {
                incident.put("severity", instance.getSeverity());
            }
            if (instance.getSource() != null) {
                incident.put("source", instance.getSource());
            }

            // 娣诲姞OpenCTI鎵╁睍
            @SuppressWarnings("unchecked")
            Map<String, Object> extensions = (Map<String, Object>) incident.getOrDefault("extensions", new HashMap<>());
            @SuppressWarnings("unchecked")
            Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

            openctiExt.put("extension_type", "new-sdo");

            if (!openctiExt.isEmpty()) {
                extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
                incident.put("extensions", extensions);
            }
        }

        return incident;
    }

    /**
     * 鍘熸柟娉? convertCampaignToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertCampaignToStix
     */
    public static Map<String, Object> convertCampaignToStix(StoreEntity instance) {
        Map<String, Object> campaign = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                campaign.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                campaign.put("description", instance.getDescription());
            }
            if (instance.getAliases() != null && !instance.getAliases().isEmpty()) {
                campaign.put("aliases", instance.getAliases());
            }
            if (instance.getFirstSeen() != null) {
                campaign.put("first_seen", StixConverterUtils.convertToStixDate(instance.getFirstSeen()));
            }
            if (instance.getLastSeen() != null) {
                campaign.put("last_seen", StixConverterUtils.convertToStixDate(instance.getLastSeen()));
            }
            if (instance.getObjective() != null) {
                campaign.put("objective", instance.getObjective());
            }
        }

        return campaign;
    }

    /**
     * 鍘熸柟娉? convertToolToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertToolToStix
     */
    public static Map<String, Object> convertToolToStix(StoreEntity instance) {
        Map<String, Object> tool = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                tool.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                tool.put("description", instance.getDescription());
            }
            if (instance.getToolTypes() != null && !instance.getToolTypes().isEmpty()) {
                tool.put("tool_types", instance.getToolTypes());
            }
            if (instance.getAliases() != null && !instance.getAliases().isEmpty()) {
                tool.put("aliases", instance.getAliases());
            }

            List<Map<String, String>> killChainPhases = buildKillChainPhases(instance);
            if (!killChainPhases.isEmpty()) {
                tool.put("kill_chain_phases", killChainPhases);
            }

            if (instance.getToolVersion() != null) {
                tool.put("tool_version", instance.getToolVersion());
            }
        }

        return tool;
    }

    /**
     * 鍘熸柟娉? convertVulnerabilityToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertVulnerabilityToStix
     */
    public static Map<String, Object> convertVulnerabilityToStix(StoreEntity instance) {
        Map<String, Object> vulnerability = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                vulnerability.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                vulnerability.put("description", instance.getDescription());
            }

            // 娣诲姞OpenCTI鎵╁睍锛堝寘鍚獵VSS璇勫垎绛夛級
            @SuppressWarnings("unchecked")
            Map<String, Object> extensions = (Map<String, Object>) vulnerability.getOrDefault("extensions", new HashMap<>());
            @SuppressWarnings("unchecked")
            Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

            // CVSS3灞炴€?
            if (instance.getXOpenctiCvssVectorString() != null) {
                openctiExt.put("cvss_vector", instance.getXOpenctiCvssVectorString());
            }
            if (instance.getXOpenctiCvssBaseScore() != null) {
                openctiExt.put("cvss_base_score", instance.getXOpenctiCvssBaseScore());
            }
            if (instance.getXOpenctiCvssBaseSeverity() != null) {
                openctiExt.put("cvss_base_severity", instance.getXOpenctiCvssBaseSeverity());
            }
            if (instance.getXOpenctiCvssAttackVector() != null) {
                openctiExt.put("cvss_attack_vector", instance.getXOpenctiCvssAttackVector());
            }
            if (instance.getXOpenctiCvssAttackComplexity() != null) {
                openctiExt.put("cvss_attack_complexity", instance.getXOpenctiCvssAttackComplexity());
            }
            if (instance.getXOpenctiCvssPrivilegesRequired() != null) {
                openctiExt.put("cvss_privileges_required", instance.getXOpenctiCvssPrivilegesRequired());
            }
            if (instance.getXOpenctiCvssUserInteraction() != null) {
                openctiExt.put("cvss_user_interaction", instance.getXOpenctiCvssUserInteraction());
            }
            if (instance.getXOpenctiCvssScope() != null) {
                openctiExt.put("cvss_scope", instance.getXOpenctiCvssScope());
            }
            if (instance.getXOpenctiCvssConfidentialityImpact() != null) {
                openctiExt.put("cvss_confidentiality_impact", instance.getXOpenctiCvssConfidentialityImpact());
            }
            if (instance.getXOpenctiCvssIntegrityImpact() != null) {
                openctiExt.put("cvss_integrity_impact", instance.getXOpenctiCvssIntegrityImpact());
            }
            if (instance.getXOpenctiCvssAvailabilityImpact() != null) {
                openctiExt.put("cvss_availability_impact", instance.getXOpenctiCvssAvailabilityImpact());
            }

            // CVSS2灞炴€?
            if (instance.getXOpenctiCvssV2VectorString() != null) {
                openctiExt.put("cvss_v2_vector", instance.getXOpenctiCvssV2VectorString());
            }
            if (instance.getXOpenctiCvssV2BaseScore() != null) {
                openctiExt.put("cvss_v2_base_score", instance.getXOpenctiCvssV2BaseScore());
            }

            // CVSS4灞炴€?
            if (instance.getXOpenctiCvssV4VectorString() != null) {
                openctiExt.put("cvss_v4_vector", instance.getXOpenctiCvssV4VectorString());
            }
            if (instance.getXOpenctiCvssV4BaseScore() != null) {
                openctiExt.put("cvss_v4_base_score", instance.getXOpenctiCvssV4BaseScore());
            }

            // 鍏朵粬灞炴€?
            if (instance.getXOpenctiCwe() != null) {
                openctiExt.put("cwe", instance.getXOpenctiCwe());
            }
            if (instance.getXOpenctiCisaKev() != null) {
                openctiExt.put("cisa_kev", instance.getXOpenctiCisaKev());
            }
            if (instance.getXOpenctiEpssScore() != null) {
                openctiExt.put("epss_score", instance.getXOpenctiEpssScore());
            }
            if (instance.getXOpenctiEpssPercentile() != null) {
                openctiExt.put("epss_percentile", instance.getXOpenctiEpssPercentile());
            }
            if (instance.getXOpenctiScore() != null) {
                openctiExt.put("score", instance.getXOpenctiScore());
            }

            if (!openctiExt.isEmpty()) {
                extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
                vulnerability.put("extensions", extensions);
            }
        }

        return vulnerability;
    }

    /**
     * 鍘熸柟娉? convertThreatActorGroupToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertThreatActorGroupToStix
     */
    public static Map<String, Object> convertThreatActorGroupToStix(StoreEntity instance) {
        Map<String, Object> threatActor = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                threatActor.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                threatActor.put("description", instance.getDescription());
            }
            if (instance.getThreatActorTypes() != null && !instance.getThreatActorTypes().isEmpty()) {
                threatActor.put("threat_actor_types", instance.getThreatActorTypes());
            }
            if (instance.getAliases() != null && !instance.getAliases().isEmpty()) {
                threatActor.put("aliases", instance.getAliases());
            }
            if (instance.getFirstSeen() != null) {
                threatActor.put("first_seen", StixConverterUtils.convertToStixDate(instance.getFirstSeen()));
            }
            if (instance.getLastSeen() != null) {
                threatActor.put("last_seen", StixConverterUtils.convertToStixDate(instance.getLastSeen()));
            }
            if (instance.getRoles() != null && !instance.getRoles().isEmpty()) {
                threatActor.put("roles", instance.getRoles());
            }
            if (instance.getGoals() != null && !instance.getGoals().isEmpty()) {
                threatActor.put("goals", instance.getGoals());
            }
            if (instance.getSophistication() != null) {
                threatActor.put("sophistication", instance.getSophistication());
            }
            if (instance.getResourceLevel() != null) {
                threatActor.put("resource_level", instance.getResourceLevel());
            }
            if (instance.getPrimaryMotivation() != null) {
                threatActor.put("primary_motivation", instance.getPrimaryMotivation());
            }
            if (instance.getSecondaryMotivations() != null && !instance.getSecondaryMotivations().isEmpty()) {
                threatActor.put("secondary_motivations", instance.getSecondaryMotivations());
            }
            if (instance.getPersonalMotivations() != null && !instance.getPersonalMotivations().isEmpty()) {
                threatActor.put("personal_motivations", instance.getPersonalMotivations());
            }
        }

        return threatActor;
    }

    /**
     * 鍘熸柟娉? convertInfrastructureToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertInfrastructureToStix
     */
    public static Map<String, Object> convertInfrastructureToStix(StoreEntity instance) {
        Map<String, Object> infrastructure = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                infrastructure.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                infrastructure.put("description", instance.getDescription());
            }
            if (instance.getInfrastructureTypes() != null && !instance.getInfrastructureTypes().isEmpty()) {
                infrastructure.put("infrastructure_types", instance.getInfrastructureTypes());
            }
            if (instance.getAliases() != null && !instance.getAliases().isEmpty()) {
                infrastructure.put("aliases", instance.getAliases());
            }

            List<Map<String, String>> killChainPhases = buildKillChainPhases(instance);
            if (!killChainPhases.isEmpty()) {
                infrastructure.put("kill_chain_phases", killChainPhases);
            }

            if (instance.getFirstSeen() != null) {
                infrastructure.put("first_seen", StixConverterUtils.convertToStixDate(instance.getFirstSeen()));
            }
            if (instance.getLastSeen() != null) {
                infrastructure.put("last_seen", StixConverterUtils.convertToStixDate(instance.getLastSeen()));
            }
        }

        return infrastructure;
    }

    /**
     * 鍘熸柟娉? convertIntrusionSetToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertIntrusionSetToStix
     */
    public static Map<String, Object> convertIntrusionSetToStix(StoreEntity instance) {
        Map<String, Object> intrusionSet = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                intrusionSet.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                intrusionSet.put("description", instance.getDescription());
            }
            if (instance.getAliases() != null && !instance.getAliases().isEmpty()) {
                intrusionSet.put("aliases", instance.getAliases());
            }
            if (instance.getFirstSeen() != null) {
                intrusionSet.put("first_seen", StixConverterUtils.convertToStixDate(instance.getFirstSeen()));
            }
            if (instance.getLastSeen() != null) {
                intrusionSet.put("last_seen", StixConverterUtils.convertToStixDate(instance.getLastSeen()));
            }
            if (instance.getGoals() != null && !instance.getGoals().isEmpty()) {
                intrusionSet.put("goals", instance.getGoals());
            }
            if (instance.getResourceLevel() != null) {
                intrusionSet.put("resource_level", instance.getResourceLevel());
            }
            if (instance.getPrimaryMotivation() != null) {
                intrusionSet.put("primary_motivation", instance.getPrimaryMotivation());
            }
            if (instance.getSecondaryMotivations() != null && !instance.getSecondaryMotivations().isEmpty()) {
                intrusionSet.put("secondary_motivations", instance.getSecondaryMotivations());
            }
        }

        return intrusionSet;
    }

    /**
     * 鍘熸柟娉? convertCourseOfActionToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertCourseOfActionToStix
     */
    public static Map<String, Object> convertCourseOfActionToStix(StoreEntity instance) {
        Map<String, Object> courseOfAction = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                courseOfAction.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                courseOfAction.put("description", instance.getDescription());
            }

            // 娣诲姞MITRE鎵╁睍
            Map<String, Object> mitreExt = buildMITREExtensions(instance);
            if (!mitreExt.isEmpty()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> extensions = (Map<String, Object>) courseOfAction.getOrDefault("extensions", new HashMap<>());
                extensions.put(StixConstants.STIX_EXT_MITRE, mitreExt);
                courseOfAction.put("extensions", extensions);
            }
        }

        return courseOfAction;
    }

    public static Map<String, Object> convertMalwareToStix(StoreEntity instance) {
        Map<String, Object> malware = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getIsFamily() != null) {
                malware.put("is_family", instance.getIsFamily());
            }
            if (instance.getMalwareTypes() != null && !instance.getMalwareTypes().isEmpty()) {
                malware.put("malware_types", instance.getMalwareTypes());
            }
            if (instance.getFirstSeen() != null) {
                malware.put("first_seen", StixConverterUtils.convertToStixDate(instance.getFirstSeen()));
            }
            if (instance.getLastSeen() != null) {
                malware.put("last_seen", StixConverterUtils.convertToStixDate(instance.getLastSeen()));
            }
            if (instance.getArchitectureExecutionEnvs() != null && !instance.getArchitectureExecutionEnvs().isEmpty()) {
                malware.put("architecture_execution_envs", instance.getArchitectureExecutionEnvs());
            }
            if (instance.getImplementationLanguages() != null && !instance.getImplementationLanguages().isEmpty()) {
                malware.put("implementation_languages", instance.getImplementationLanguages());
            }
            if (instance.getCapabilities() != null && !instance.getCapabilities().isEmpty()) {
                malware.put("capabilities", instance.getCapabilities());
            }
        }

        return malware;
    }

    /**
     * 鍘熸柟娉? convertAttackPatternToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertAttackPatternToStix
     */
    public static Map<String, Object> convertAttackPatternToStix(StoreEntity instance) {
        Map<String, Object> attackPattern = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                attackPattern.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                attackPattern.put("description", instance.getDescription());
            }
            if (instance.getAliases() != null && !instance.getAliases().isEmpty()) {
                attackPattern.put("aliases", instance.getAliases());
            }

            List<Map<String, String>> killChainPhases = buildKillChainPhases(instance);
            if (!killChainPhases.isEmpty()) {
                attackPattern.put("kill_chain_phases", killChainPhases);
            }

            // 娣诲姞MITRE鎵╁睍
            Map<String, Object> mitreExt = buildMITREExtensions(instance);
            if (!mitreExt.isEmpty()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> extensions = (Map<String, Object>) attackPattern.getOrDefault("extensions", new HashMap<>());
                extensions.put(StixConstants.STIX_EXT_MITRE, mitreExt);
                attackPattern.put("extensions", extensions);
            }
        }

        return attackPattern;
    }

    /**
     * 鍘熸柟娉? convertReportToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertReportToStix
     */
    public static Map<String, Object> convertReportToStix(StoreEntity instance) {
        Map<String, Object> report = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getName() != null) {
                report.put("name", instance.getName());
            }
            if (instance.getDescription() != null) {
                report.put("description", instance.getDescription());
            }
            if (instance.getReportTypes() != null && !instance.getReportTypes().isEmpty()) {
                report.put("report_types", instance.getReportTypes());
            }
            if (instance.getPublished() != null) {
                report.put("published", StixConverterUtils.convertToStixDate(instance.getPublished()));
            }
            if (instance.getObjectRefs() != null && !instance.getObjectRefs().isEmpty()) {
                report.put("object_refs", instance.getObjectRefs());
            }

            // 娣诲姞OpenCTI鎵╁睍
            @SuppressWarnings("unchecked")
            Map<String, Object> extensions = (Map<String, Object>) report.getOrDefault("extensions", new HashMap<>());
            @SuppressWarnings("unchecked")
            Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

            openctiExt.put("extension_type", "property-extension");

            if (instance.getContent() != null) {
                openctiExt.put("content", instance.getContent());
            }
            if (instance.getContentMapping() != null) {
                openctiExt.put("content_mapping", instance.getContentMapping());
            }
            if (instance.getXOpenctiReliability() != null) {
                openctiExt.put("reliability", instance.getXOpenctiReliability());
            }

            if (!openctiExt.isEmpty()) {
                extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
                report.put("extensions", extensions);
            }
        }

        return report;
    }

    /**
     * 鍘熸柟娉? convertNoteToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertNoteToStix
     */
    public static Map<String, Object> convertNoteToStix(StoreEntity instance) {
        Map<String, Object> note = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getAbstract$() != null) {
                note.put("abstract", instance.getAbstract$());
            }
            if (instance.getContent() != null) {
                note.put("content", instance.getContent());
            }
            if (instance.getAuthors() != null && !instance.getAuthors().isEmpty()) {
                note.put("authors", instance.getAuthors());
            }
            if (instance.getObjectRefs() != null && !instance.getObjectRefs().isEmpty()) {
                note.put("object_refs", instance.getObjectRefs());
            }
            if (instance.getNoteTypes() != null && !instance.getNoteTypes().isEmpty()) {
                note.put("note_types", instance.getNoteTypes());
            }
            if (instance.getLikelihood() != null) {
                note.put("likelihood", instance.getLikelihood());
            }

            // 娣诲姞OpenCTI鎵╁睍
            @SuppressWarnings("unchecked")
            Map<String, Object> extensions = (Map<String, Object>) note.getOrDefault("extensions", new HashMap<>());
            @SuppressWarnings("unchecked")
            Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

            openctiExt.put("extension_type", "property-extension");

            if (instance.getContentMapping() != null) {
                openctiExt.put("content_mapping", instance.getContentMapping());
            }

            if (!openctiExt.isEmpty()) {
                extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
                note.put("extensions", extensions);
            }
        }

        return note;
    }

    /**
     * 鍘熸柟娉? convertObservedDataToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertObservedDataToStix
     */
    public static Map<String, Object> convertObservedDataToStix(StoreEntity instance) {
        Map<String, Object> observedData = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getFirstObserved() != null) {
                observedData.put("first_observed", StixConverterUtils.convertToStixDate(instance.getFirstObserved()));
            }
            if (instance.getLastObserved() != null) {
                observedData.put("last_observed", StixConverterUtils.convertToStixDate(instance.getLastObserved()));
            }
            if (instance.getNumberObserved() != null) {
                observedData.put("number_observed", instance.getNumberObserved());
            }
            if (instance.getObjectRefs() != null && !instance.getObjectRefs().isEmpty()) {
                observedData.put("object_refs", instance.getObjectRefs());
            }

            // 娣诲姞OpenCTI鎵╁睍
            @SuppressWarnings("unchecked")
            Map<String, Object> extensions = (Map<String, Object>) observedData.getOrDefault("extensions", new HashMap<>());
            @SuppressWarnings("unchecked")
            Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

            openctiExt.put("extension_type", "property-extension");

            if (instance.getContent() != null) {
                openctiExt.put("content", instance.getContent());
            }
            if (instance.getContentMapping() != null) {
                openctiExt.put("content_mapping", instance.getContentMapping());
            }

            if (!openctiExt.isEmpty()) {
                extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
                observedData.put("extensions", extensions);
            }
        }

        return observedData;
    }

    /**
     * 鍘熸柟娉? convertOpinionToStix
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertOpinionToStix
     */
    public static Map<String, Object> convertOpinionToStix(StoreEntity instance) {
        Map<String, Object> opinion = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getExplanation() != null) {
                opinion.put("explanation", instance.getExplanation());
            }
            if (instance.getAuthors() != null && !instance.getAuthors().isEmpty()) {
                opinion.put("authors", instance.getAuthors());
            }
            if (instance.getOpinion() != null) {
                opinion.put("opinion", instance.getOpinion());
            }
            if (instance.getObjectRefs() != null && !instance.getObjectRefs().isEmpty()) {
                opinion.put("object_refs", instance.getObjectRefs());
            }

            // 娣诲姞OpenCTI鎵╁睍
            @SuppressWarnings("unchecked")
            Map<String, Object> extensions = (Map<String, Object>) opinion.getOrDefault("extensions", new HashMap<>());
            @SuppressWarnings("unchecked")
            Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

            openctiExt.put("extension_type", "property-extension");

            if (instance.getContent() != null) {
                openctiExt.put("content", instance.getContent());
            }
            if (instance.getContentMapping() != null) {
                openctiExt.put("content_mapping", instance.getContentMapping());
            }

            if (!openctiExt.isEmpty()) {
                extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
                opinion.put("extensions", extensions);
            }
        }

        return opinion;
    }

    // ==================== SMO杞崲鏂规硶 ====================

    public static Map<String, Object> convertMarkingToStix(StoreEntity instance) {
        Map<String, Object> marking = buildStixObject(instance);
        marking.put("type", "marking-definition");
        return marking;
    }

    public static Map<String, Object> convertLabelToStix(StoreEntity instance) {
        Map<String, Object> label = buildStixObject(instance);
        label.put("type", "label");
        return label;
    }

    public static Map<String, Object> convertKillChainPhaseToStix(StoreEntity instance) {
        Map<String, Object> killChainPhase = buildStixObject(instance);
        killChainPhase.put("type", "kill-chain-phase");
        return killChainPhase;
    }

    public static Map<String, Object> convertExternalReferenceToStix(StoreEntity instance) {
        Map<String, Object> externalReference = buildStixObject(instance);
        externalReference.put("type", "external-reference");
        return externalReference;
    }

    // ==================== 涓昏浆鎹㈠叆鍙?====================

    public static Map<String, Object> convertStoreToStix_2_1(StoreObject instance) {
        if (instance == null) {
            return null;
        }

        String entityType = instance.getEntityType();
        if (entityType == null) {
            return null;
        }

        if (instance instanceof StoreEntity) {
            StoreEntity entity = (StoreEntity) instance;

            Function<StoreEntity, Map<String, Object>> domainConverter = STIX_DOMAIN_CONVERTERS.get(entityType);
            if (domainConverter != null) {
                return domainConverter.apply(entity);
            }

            Function<StoreEntity, Map<String, Object>> metaConverter = STIX_META_CONVERTERS.get(entityType);
            if (metaConverter != null) {
                return metaConverter.apply(entity);
            }

            return buildStixDomain(entity);
        }

        return buildStixObject(instance);
    }

    public static StixBundle buildStixBundle(List<Map<String, Object>> stixObjects) {
        // 将 Map<String, Object> 转换为 StixObject 列表
        List<StixObject> objects = new ArrayList<>();
        if (stixObjects != null) {
            for (Map<String, Object> obj : stixObjects) {
                // 这里需要根据实际情况进行转换
                // 暂时使用空对象
                String idStr = (String) obj.get("id");
                String typeStr = (String) obj.get("type");
                objects.add(new StixObject() {
                    @Override
                    public String getType() { return typeStr; }
                    @Override
                    public StixId getId() { 
                        if (idStr == null) return null;
                        try {
                            return StixId.parse(idStr);
                        } catch (IllegalArgumentException e) {
                            return null;
                        }
                    }
                });
            }
        }
        return StixBundle.of(objects);
    }
}

