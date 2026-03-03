package io.opencti.common.types.stix.sro;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixCoverage;
import io.opencti.common.types.stix.StixExtensionConstants;
import io.opencti.common.types.stix.StixKillChainPhase;

import java.util.List;

/**
 * STIX Relation Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sro.d.ts
 */
public record RelationExtension(
        @JsonProperty("extension_type") String extensionType,
        @JsonProperty("source_value") String sourceValue,
        @JsonProperty("source_ref") String sourceRef,
        @JsonProperty("source_type") String sourceType,
        @JsonProperty("source_ref_object_marking_refs") List<String> sourceRefObjectMarkingRefs,
        @JsonProperty("source_ref_granted_refs") List<String> sourceRefGrantedRefs,
        @JsonProperty("source_ref_pir_refs") List<String> sourceRefPirRefs,
        @JsonProperty("target_value") String targetValue,
        @JsonProperty("target_ref") String targetRef,
        @JsonProperty("target_type") String targetType,
        @JsonProperty("target_ref_object_marking_refs") List<String> targetRefObjectMarkingRefs,
        @JsonProperty("target_ref_granted_refs") List<String> targetRefGrantedRefs,
        @JsonProperty("target_ref_pir_refs") List<String> targetRefPirRefs,
        @JsonProperty("kill_chain_phases") List<StixKillChainPhase> killChainPhases,
        List<StixCoverage> coverage,
        @JsonProperty("pir_score") Double pirScore
) {
    public RelationExtension {
        if (extensionType == null) {
            extensionType = StixExtensionConstants.EXTENSION_TYPE_PROPERTY_EXTENSION;
        }
    }

    public static RelationExtension of(String sourceRef, String sourceType, String targetRef, String targetType) {
        return new RelationExtension(
                StixExtensionConstants.EXTENSION_TYPE_PROPERTY_EXTENSION,
                null, sourceRef, sourceType, List.of(), List.of(), null,
                null, targetRef, targetType, List.of(), List.of(), null,
                List.of(), List.of(), null
        );
    }
}
