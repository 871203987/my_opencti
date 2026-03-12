package io.opencti.database.stix.converter;

import io.opencti.types.store.StixFile;
import io.opencti.types.store.StoreEntity;
import io.opencti.types.store.StoreObject;
import io.opencti.types.stix.common.StixId;
import io.opencti.database.stix.StixConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-0-converter.ts
 * STIX 2.0鏍煎紡杞崲鍣?
 *
 * @author OpenCTI Java閲嶅啓椤圭洰
 */
@Slf4j
public final class Stix20Converter {

    private Stix20Converter() {
        // 宸ュ叿绫伙紝绂佹瀹炰緥鍖?
    }

    // 鑷畾涔夊疄浣撶被鍨嬪垪琛紙闇€瑕佹坊鍔爔-opencti-鍓嶇紑锛?
    private static final List<String> CUSTOM_ENTITY_TYPES = Arrays.asList(
            "Task",
            "Feedback",
            "Case-Incident",
            "Case-Rfi",
            "Case-Rft"
    );

    /**
     * 鍘熸柟娉? buildStixId
     * 鏋勫缓STIX ID
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-0-converter.ts:buildStixId
     *
     * @param instanceType 瀹炰緥绫诲瀷
     * @param standardId   鏍囧噯ID
     * @return STIX ID
     */
    public static String buildStixId(String instanceType, String standardId) {
        if (standardId == null || standardId.isEmpty()) {
            return null;
        }
        // 鑷畾涔夊鍣ㄧ被鍨嬮渶瑕佹坊鍔爔-opencti-鍓嶇紑
        boolean isCustomContainer = CUSTOM_ENTITY_TYPES.contains(instanceType);
        if (isCustomContainer) {
            return "x-opencti-" + standardId;
        }
        return standardId;
    }

    // Identity瀛愮被鍨?
    private static final List<String> IDENTITY_TYPES = Arrays.asList(
            "Individual", "Organization", "Sector", "System"
    );

    // Location瀛愮被鍨?
    private static final List<String> LOCATION_TYPES = Arrays.asList(
            "City", "Country", "Region", "Position", "Administrative-Area"
    );

    // Threat Actor瀛愮被鍨?
    private static final List<String> THREAT_ACTOR_TYPES = Arrays.asList(
            "Threat-Actor", "Threat-Actor-Group", "Threat-Actor-Individual"
    );

    /**
     * 鍘熸柟娉? convertTypeToStix2Type
     * 灏嗗唴閮ㄧ被鍨嬭浆鎹负STIX 2.0绫诲瀷
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-0-converter.ts:convertTypeToStix2Type
     *
     * @param type 鍐呴儴绫诲瀷
     * @return STIX 2.0绫诲瀷
     */
    public static String convertTypeToStix2Type(String type) {
        if (type == null) {
            return null;
        }

        // Identity绫诲瀷缁熶竴鏄犲皠涓篿dentity
        if (IDENTITY_TYPES.contains(type)) {
            return "identity";
        }

        // Location绫诲瀷缁熶竴鏄犲皠涓簂ocation
        if (LOCATION_TYPES.contains(type)) {
            return "location";
        }

        // StixFile绫诲瀷鏄犲皠涓篺ile
        if ("StixFile".equalsIgnoreCase(type) || "File".equalsIgnoreCase(type)) {
            return "file";
        }

        // 鍏崇郴绫诲瀷鏄犲皠
        if (type.endsWith("Relationship") || type.equals("Relationship")) {
            return "relationship";
        }

        // Sighting绫诲瀷鏄犲皠
        if (type.equals("Sighting") || type.equals("StixSighting")) {
            return "sighting";
        }

        // Threat Actor绫诲瀷缁熶竴鏄犲皠涓簍hreat-actor
        if (THREAT_ACTOR_TYPES.contains(type)) {
            return "threat-actor";
        }

        // 鑷畾涔夊鍣ㄧ被鍨?
        switch (type) {
            case "Task":
                return "x-opencti-task";
            case "Feedback":
                return "x-opencti-feedback";
            case "Case-Incident":
                return "x-opencti-case-incident";
            case "Case-Rfi":
                return "x-opencti-case-rfi";
            case "Case-Rft":
                return "x-opencti-case-rft";
            case "Case-Template":
                return "x-opencti-case-template";
            case "Task-Template":
                return "x-opencti-task-template";
            case "Data-Component":
                return "x-mitre-data-component";
            case "Data-Source":
                return "x-mitre-data-source";
            default:
                return type.toLowerCase().replace("_", "-");
        }
    }

    /**
     * 鍘熸柟娉? buildStixObject
     * 鏋勫缓鍩虹STIX瀵硅薄
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-2-0-converter.ts:buildStixObject
     *
     * @param instance 瀛樺偍瀵硅薄
     * @return 鍩虹STIX瀵硅薄灞炴€ap
     */
    public static Map<String, Object> buildStixObject(StoreObject instance) {
        Map<String, Object> stixObject = new HashMap<>();

        if (instance != null) {
            // 鏋勫缓STIX ID锛堝鐞嗚嚜瀹氫箟瀹瑰櫒绫诲瀷锛?
            if (instance.getStandardId() != null) {
                String stixId = buildStixId(instance.getEntityType(), instance.getStandardId().toString());
                stixObject.put("id", stixId);
            }
            if (instance.getEntityType() != null) {
                stixObject.put("type", convertTypeToStix2Type(instance.getEntityType()));
            }
            stixObject.put("spec_version", "2.0");

            // STIX 2.0鐗规湁鐨凮penCTI鎵╁睍灞炴€?
            if (instance.getInternalId() != null) {
                stixObject.put("x_opencti_id", instance.getInternalId());
            }
            if (instance.getEntityType() != null) {
                stixObject.put("x_opencti_type", instance.getEntityType());
            }
            if (instance.getModifiedAt() != null) {
                stixObject.put("x_opencti_modified_at", StixConverterUtils.convertToStixDate(instance.getModifiedAt()));
            }

            // 鎺堟潈寮曠敤
            if (instance instanceof StoreEntity) {
                StoreEntity entity = (StoreEntity) instance;
                if (entity.getGrantedRefs() != null && !entity.getGrantedRefs().isEmpty()) {
                    stixObject.put("x_opencti_granted_refs", entity.getGrantedRefs());
                }
            }

            // 宸ヤ綔娴両D
            if (instance.getXOpenctiWorkflowId() != null) {
                stixObject.put("x_opencti_workflow_id", instance.getXOpenctiWorkflowId());
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
                    if (file.getId() != null) {
                        fileMap.put("uri", "/storage/get/" + file.getId());
                    }
                    if (file.getMimeType() != null) {
                        fileMap.put("mime_type", file.getMimeType());
                    }
                    if (file.getVersion() != null) {
                        fileMap.put("version", file.getVersion());
                    }
                    if (file.getObjectMarkingRefs() != null && !file.getObjectMarkingRefs().isEmpty()) {
                        List<String> markingRefs = new ArrayList<>();
                        for (StixId ref : file.getObjectMarkingRefs()) {
                            if (ref != null) {
                                markingRefs.add(ref.toString());
                            }
                        }
                        if (!markingRefs.isEmpty()) {
                            fileMap.put("object_marking_refs", markingRefs);
                        }
                    }
                    if (!fileMap.isEmpty()) {
                        files.add(fileMap);
                    }
                }
                if (!files.isEmpty()) {
                    stixObject.put("x_opencti_files", files);
                }
            }
        }

        return stixObject;
    }

    /**
     * 鍘熸柟娉? buildKillChainPhases
     * 鏋勫缓鏉€浼ら摼闃舵鏁扮粍
     *
     * @param instance 瀛樺偍瀹炰綋
     * @return 鏉€浼ら摼闃舵鍒楄〃
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
     * 鏋勫缓澶栭儴寮曠敤鏁扮粍
     *
     * @param instance 瀛樺偍瀹炰綋
     * @return 澶栭儴寮曠敤鍒楄〃
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

    /**
     * 鍘熸柟娉? buildStixDomain
     * 鏋勫缓STIX鍩熷璞″熀纭€灞炴€?
     *
     * @param instance 瀛樺偍瀹炰綋
     * @return STIX鍩熷璞″睘鎬ap
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
        }

        return stixDomain;
    }

    /**
     * 鍘熸柟娉? convertMalwareToStix
     * 杞崲鎭舵剰杞欢瀹炰綋涓篠TIX 2.0鏍煎紡
     *
     * @param instance 瀛樺偍瀹炰綋
     * @return STIX鎭舵剰杞欢瀵硅薄
     */
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
     * 鍘熸柟娉? convertReportToStix
     * 杞崲鎶ュ憡瀹炰綋涓篠TIX 2.0鏍煎紡
     *
     * @param instance 瀛樺偍瀹炰綋
     * @return STIX鎶ュ憡瀵硅薄
     */
    public static Map<String, Object> convertReportToStix(StoreEntity instance) {
        Map<String, Object> report = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getReportTypes() != null && !instance.getReportTypes().isEmpty()) {
                report.put("report_types", instance.getReportTypes());
            }
            if (instance.getPublished() != null) {
                report.put("published", StixConverterUtils.convertToStixDate(instance.getPublished()));
            }
            if (instance.getObjectRefs() != null && !instance.getObjectRefs().isEmpty()) {
                report.put("object_refs", instance.getObjectRefs());
            }
        }

        return report;
    }

    /**
     * 鍘熸柟娉? convertNoteToStix
     * 杞崲绗旇瀹炰綋涓篠TIX 2.0鏍煎紡
     *
     * @param instance 瀛樺偍瀹炰綋
     * @return STIX绗旇瀵硅薄
     */
    public static Map<String, Object> convertNoteToStix(StoreEntity instance) {
        Map<String, Object> note = buildStixDomain(instance);

        if (instance != null) {
            if (instance.getContent() != null) {
                note.put("content", instance.getContent());
            }
            if (instance.getAbstract$() != null) {
                note.put("abstract", instance.getAbstract$());
            }
            if (instance.getAuthors() != null && !instance.getAuthors().isEmpty()) {
                note.put("authors", instance.getAuthors());
            }
            if (instance.getObjectRefs() != null && !instance.getObjectRefs().isEmpty()) {
                note.put("object_refs", instance.getObjectRefs());
            }
        }

        return note;
    }

    /**
     * 鍘熸柟娉? convertObservedDataToStix
     * 杞崲瑙傛祴鏁版嵁瀹炰綋涓篠TIX 2.0鏍煎紡
     *
     * @param instance 瀛樺偍瀹炰綋
     * @return STIX瑙傛祴鏁版嵁瀵硅薄
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
            if (instance.getObjects() != null && !instance.getObjects().isEmpty()) {
                observedData.put("objects", instance.getObjects());
            }
        }

        return observedData;
    }

    /**
     * 鍘熸柟娉? convertOpinionToStix
     * 杞崲鎰忚瀹炰綋涓篠TIX 2.0鏍煎紡
     *
     * @param instance 瀛樺偍瀹炰綋
     * @return STIX鎰忚瀵硅薄
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
        }

        return opinion;
    }

    /**
     * 鍘熸柟娉? convertStoreToStix_2_0
     * 涓昏浆鎹㈠叆鍙?- 灏嗗瓨鍌ㄥ璞¤浆鎹负STIX 2.0鏍煎紡
     *
     * @param instance 瀛樺偍瀵硅薄
     * @return STIX 2.0瀵硅薄
     */
    public static Map<String, Object> convertStoreToStix_2_0(StoreObject instance) {
        if (instance == null) {
            return null;
        }

        String entityType = instance.getEntityType();
        if (entityType == null) {
            return null;
        }

        // 鏍规嵁瀹炰綋绫诲瀷閫夋嫨杞崲鏂规硶
        if (instance instanceof StoreEntity) {
            StoreEntity entity = (StoreEntity) instance;
            switch (entityType) {
                case "Malware":
                    return convertMalwareToStix(entity);
                case "Report":
                    return convertReportToStix(entity);
                case "Note":
                    return convertNoteToStix(entity);
                case "Observed-Data":
                    return convertObservedDataToStix(entity);
                case "Opinion":
                    return convertOpinionToStix(entity);
                default:
                    return buildStixDomain(entity);
            }
        } else {
            return buildStixObject(instance);
        }
    }
}

