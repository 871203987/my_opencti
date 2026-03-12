package io.opencti.database.stix.converter;

import io.opencti.types.store.*;
import io.opencti.types.stix.common.StixId;
import io.opencti.database.stix.StixConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * STIX 2.1 Relationship Object (SRO) Converter.
 * Original file: opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts
 * Converts StoreRelation and StoreRelationPir objects to STIX 2.1 SRO format.
 */
@Slf4j
public class Stix21SroConverter {

    /**
     * Original method: convertRelationToStix
     * Original file: opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertRelationToStix
     */
    public static Map<String, Object> convertRelationToStix(StoreRelation instance) {
        Map<String, Object> relation = buildSroBase(instance, "relationship");

        if (instance == null) {
            return relation;
        }

        // Check instance integrity
        if (instance.getFrom() == null || instance.getTo() == null) {
            log.warn("Cannot convert relation without resolved from/to");
            return relation;
        }

        StoreObject resolvedFrom = instance.getFrom();
        StoreObject resolvedTo = instance.getTo();

        // Basic relationship properties
        if (instance.getRelationshipType() != null) {
            relation.put("relationship_type", instance.getRelationshipType());
        }
        if (instance.getDescription() != null) {
            relation.put("description", instance.getDescription());
        }
        if (resolvedFrom.getStandardId() != null) {
            relation.put("source_ref", resolvedFrom.getStandardId().toString());
        }
        if (resolvedTo.getStandardId() != null) {
            relation.put("target_ref", resolvedTo.getStandardId().toString());
        }
        if (instance.getStartTime() != null) {
            relation.put("start_time", StixConverterUtils.convertToStixDate(instance.getStartTime()));
        }
        if (instance.getStopTime() != null) {
            relation.put("stop_time", StixConverterUtils.convertToStixDate(instance.getStopTime()));
        }

        // OpenCTI extensions
        @SuppressWarnings("unchecked")
        Map<String, Object> extensions = (Map<String, Object>) relation.getOrDefault("extensions", new HashMap<>());
        @SuppressWarnings("unchecked")
        Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

        // Determine if it's a built-in relationship type
        boolean isBuiltin = isRelationBuiltin(instance.getRelationshipType());
        openctiExt.put("extension_type", isBuiltin ? "property-extension" : "new-sro");

        // Source entity information
        openctiExt.put("source_value", extractEntityRepresentativeName(resolvedFrom));
        if (resolvedFrom.getInternalId() != null) {
            openctiExt.put("source_ref", resolvedFrom.getInternalId());
        }
        if (resolvedFrom.getEntityType() != null) {
            openctiExt.put("source_type", resolvedFrom.getEntityType());
        }

        // Target entity information
        openctiExt.put("target_value", extractEntityRepresentativeName(resolvedTo));
        if (resolvedTo.getInternalId() != null) {
            openctiExt.put("target_ref", resolvedTo.getInternalId());
        }
        if (resolvedTo.getEntityType() != null) {
            openctiExt.put("target_type", resolvedTo.getEntityType());
        }

        // Kill Chain Phases
        if (instance.getKillChainPhases() != null && !instance.getKillChainPhases().isEmpty()) {
            List<Map<String, String>> killChainPhases = new ArrayList<>();
            for (KillChainPhase phase : instance.getKillChainPhases()) {
                Map<String, String> phaseMap = new HashMap<>();
                if (phase.getKillChainName() != null) {
                    phaseMap.put("kill_chain_name", phase.getKillChainName());
                }
                if (phase.getPhaseName() != null) {
                    phaseMap.put("phase_name", phase.getPhaseName());
                }
                killChainPhases.add(phaseMap);
            }
            if (!killChainPhases.isEmpty()) {
                openctiExt.put("kill_chain_phases", killChainPhases);
            }
        }

        // Coverage
        if (instance.getCoverage() != null) {
            openctiExt.put("coverage", instance.getCoverage());
        }

        extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
        relation.put("extensions", extensions);

        return relation;
    }

    /**
     * Original method: convertSightingToStix
     * Original file: opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertSightingToStix
     */
    public static Map<String, Object> convertSightingToStix(StoreRelation instance) {
        Map<String, Object> sighting = buildSroBase(instance, "sighting");

        if (instance == null) {
            return sighting;
        }

        // Check instance integrity
        if (instance.getFrom() == null || instance.getTo() == null) {
            log.warn("Cannot convert sighting without resolved from/to");
            return sighting;
        }

        StoreObject resolvedFrom = instance.getFrom();
        StoreObject resolvedTo = instance.getTo();

        // Basic Sighting properties
        if (instance.getDescription() != null) {
            sighting.put("description", instance.getDescription());
        }
        // Sighting uses start_time and stop_time as first_seen and last_seen
        if (instance.getStartTime() != null) {
            sighting.put("first_seen", StixConverterUtils.convertToStixDate(instance.getStartTime()));
        }
        if (instance.getStopTime() != null) {
            sighting.put("last_seen", StixConverterUtils.convertToStixDate(instance.getStopTime()));
        }
        if (instance.getAttributeCount() != null) {
            sighting.put("count", instance.getAttributeCount());
        }
        if (resolvedFrom.getStandardId() != null) {
            sighting.put("sighting_of_ref", resolvedFrom.getStandardId().toString());
        }
        if (resolvedTo.getStandardId() != null) {
            sighting.put("where_sighted_refs", Collections.singletonList(resolvedTo.getStandardId().toString()));
        }
        if (instance.getSummary() != null) {
            sighting.put("summary", instance.getSummary());
        }

        // OpenCTI extensions
        @SuppressWarnings("unchecked")
        Map<String, Object> extensions = (Map<String, Object>) sighting.getOrDefault("extensions", new HashMap<>());
        @SuppressWarnings("unchecked")
        Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

        // Sighting source entity information
        openctiExt.put("sighting_of_value", extractEntityRepresentativeName(resolvedFrom));
        if (resolvedFrom.getInternalId() != null) {
            openctiExt.put("sighting_of_ref", resolvedFrom.getInternalId());
        }
        if (resolvedFrom.getEntityType() != null) {
            openctiExt.put("sighting_of_type", resolvedFrom.getEntityType());
        }

        // Where sighted target entity information
        openctiExt.put("where_sighted_values", Collections.singletonList(extractEntityRepresentativeName(resolvedTo)));
        if (resolvedTo.getInternalId() != null) {
            openctiExt.put("where_sighted_refs", Collections.singletonList(resolvedTo.getInternalId()));
        }
        if (resolvedTo.getEntityType() != null) {
            openctiExt.put("where_sighted_types", Collections.singletonList(resolvedTo.getEntityType()));
        }

        // Negative sighting
        if (instance.getXOpenctiNegative() != null) {
            openctiExt.put("negative", instance.getXOpenctiNegative());
        }

        extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
        sighting.put("extensions", extensions);

        return sighting;
    }

    /**
     * Original method: convertInPirRelToStix
     * Original file: opencti-platform/opencti-graphql/src/database/stix-2-1-converter.ts:convertInPirRelToStix
     */
    public static Map<String, Object> convertInPirRelToStix(StoreRelationPir instance) {
        Map<String, Object> pirRel = buildSroBase(instance, "relationship");

        if (instance == null) {
            return pirRel;
        }

        // Check instance integrity
        if (instance.getFrom() == null || instance.getTo() == null) {
            log.warn("Cannot convert PIR relation without resolved from/to");
            return pirRel;
        }

        StoreObject resolvedFrom = instance.getFrom();
        StoreObject resolvedTo = instance.getTo();

        // Basic relationship properties
        if (instance.getRelationshipType() != null) {
            pirRel.put("relationship_type", instance.getRelationshipType());
        }
        if (instance.getDescription() != null) {
            pirRel.put("description", instance.getDescription());
        }
        if (resolvedFrom.getStandardId() != null) {
            pirRel.put("source_ref", resolvedFrom.getStandardId().toString());
        }
        if (resolvedTo.getStandardId() != null) {
            pirRel.put("target_ref", resolvedTo.getStandardId().toString());
        }
        if (instance.getStartTime() != null) {
            pirRel.put("start_time", StixConverterUtils.convertToStixDate(instance.getStartTime()));
        }
        if (instance.getStopTime() != null) {
            pirRel.put("stop_time", StixConverterUtils.convertToStixDate(instance.getStopTime()));
        }

        // OpenCTI extensions
        @SuppressWarnings("unchecked")
        Map<String, Object> extensions = (Map<String, Object>) pirRel.getOrDefault("extensions", new HashMap<>());
        @SuppressWarnings("unchecked")
        Map<String, Object> openctiExt = (Map<String, Object>) extensions.getOrDefault(StixConstants.STIX_EXT_OCTI, new HashMap<>());

        // Determine if it's a built-in relationship type
        boolean isBuiltin = isRelationBuiltin(instance.getRelationshipType());
        openctiExt.put("extension_type", isBuiltin ? "property-extension" : "new-sro");

        // Source entity information
        openctiExt.put("source_value", extractEntityRepresentativeName(resolvedFrom));
        if (resolvedFrom.getInternalId() != null) {
            openctiExt.put("source_ref", resolvedFrom.getInternalId());
        }
        if (resolvedFrom.getEntityType() != null) {
            openctiExt.put("source_type", resolvedFrom.getEntityType());
        }

        // Target entity information
        openctiExt.put("target_value", extractEntityRepresentativeName(resolvedTo));
        if (resolvedTo.getInternalId() != null) {
            openctiExt.put("target_ref", resolvedTo.getInternalId());
        }
        if (resolvedTo.getEntityType() != null) {
            openctiExt.put("target_type", resolvedTo.getEntityType());
        }

        // PIR specific properties
        if (instance.getPirScore() != null) {
            openctiExt.put("pir_score", instance.getPirScore());
        }
        if (instance.getPirExplanation() != null) {
            openctiExt.put("pir_explanation", instance.getPirExplanation());
        }

        // Coverage
        if (instance.getCoverage() != null) {
            openctiExt.put("coverage", instance.getCoverage());
        }

        extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
        pirRel.put("extensions", extensions);

        return pirRel;
    }

    // ==================== Helper Methods ====================

    /**
     * Build SRO base object
     */
    private static Map<String, Object> buildSroBase(StoreObject instance, String type) {
        Map<String, Object> sro = new HashMap<>();

        if (instance != null) {
            if (instance.getStandardId() != null) {
                sro.put("id", instance.getStandardId().toString());
            }
            sro.put("type", type);
            sro.put("spec_version", "2.1");

            // Creation time
            if (instance.getCreatedAt() != null) {
                sro.put("created", StixConverterUtils.convertToStixDate(instance.getCreatedAt()));
            }
            // Modification time
            if (instance.getUpdatedAt() != null) {
                sro.put("modified", StixConverterUtils.convertToStixDate(instance.getUpdatedAt()));
            }

            // Add OpenCTI base extension
            Map<String, Object> extensions = new HashMap<>();
            Map<String, Object> openctiExt = new HashMap<>();

            openctiExt.put("extension_type", "property-extension");

            if (instance.getInternalId() != null) {
                openctiExt.put("id", instance.getInternalId());
            }
            if (instance.getEntityType() != null) {
                openctiExt.put("type", instance.getEntityType());
            }

            extensions.put(StixConstants.STIX_EXT_OCTI, openctiExt);
            sro.put("extensions", extensions);
        }

        return sro;
    }

    /**
     * Determine if it's a built-in relationship type
     */
    private static boolean isRelationBuiltin(String relationshipType) {
        if (relationshipType == null) {
            return false;
        }

        // STIX 2.1 standard built-in relationship types
        Set<String> builtinTypes = new HashSet<>(Arrays.asList(
                "uses", "targets", "attributed-to", "indicates", "mitigates",
                "located-at", "based-on", "communicates-with", "consists-of",
                "controls", "delivers", "derived-from", "detects", "downloads",
                "drops", "exfiltrates-to", "exploits", "has", "hosts",
                "impersonates", "indicates", "infects", "involved-in",
                "originates-from", "owns", "related-to", "remediates",
                "resolves-to", "variant-of"
        ));

        return builtinTypes.contains(relationshipType.toLowerCase());
    }

    /**
     * Extract entity representative name
     */
    private static String extractEntityRepresentativeName(StoreObject entity) {
        if (entity == null) {
            return null;
        }

        // Try to get name
        if (entity instanceof StoreEntity) {
            StoreEntity storeEntity = (StoreEntity) entity;
            if (storeEntity.getName() != null) {
                return storeEntity.getName();
            }
        }

        // Try to get value (for SCO)
        if (entity instanceof StoreCyberObservable) {
            StoreCyberObservable sco = (StoreCyberObservable) entity;
            if (sco.getValue() != null) {
                return sco.getValue();
            }
            if (sco.getName() != null) {
                return sco.getName();
            }
        }

        // Return standard ID as fallback
        if (entity.getStandardId() != null) {
            return entity.getStandardId().toString();
        }

        return null;
    }
}
