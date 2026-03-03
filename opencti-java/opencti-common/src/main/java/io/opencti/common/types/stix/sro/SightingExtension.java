package io.opencti.common.types.stix.sro;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;
import io.opencti.common.types.stix.StixExtensionConstants;
import io.opencti.common.types.stix.StixId;

import java.util.List;

/**
 * STIX Sighting Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sro.d.ts
 */
public record SightingExtension(
        @JsonProperty("extension_type") String extensionType,
        @JsonProperty("sighting_of_value") String sightingOfValue,
        @JsonProperty("sighting_of_ref") StixId sightingOfRef,
        @JsonProperty("sighting_of_type") String sightingOfType,
        @JsonProperty("sighting_of_ref_object_marking_refs") List<String> sightingOfRefObjectMarkingRefs,
        @JsonProperty("sighting_of_ref_granted_refs") List<String> sightingOfRefGrantedRefs,
        @JsonProperty("where_sighted_values") List<String> whereSightedValues,
        @JsonProperty("where_sighted_refs") List<StixId> whereSightedRefs,
        @JsonProperty("where_sighted_types") List<String> whereSightedTypes,
        @JsonProperty("where_sighted_refs_object_marking_refs") List<String> whereSightedRefsObjectMarkingRefs,
        @JsonProperty("where_sighted_refs_granted_refs") List<String> whereSightedRefsGrantedRefs,
        Boolean negative
) {
    public SightingExtension {
        if (extensionType == null) {
            extensionType = StixExtensionConstants.EXTENSION_TYPE_PROPERTY_EXTENSION;
        }
    }

    public static SightingExtension of(StixId sightingOfRef, String sightingOfType) {
        return new SightingExtension(
                StixExtensionConstants.EXTENSION_TYPE_PROPERTY_EXTENSION,
                null, sightingOfRef, sightingOfType, List.of(), List.of(),
                List.of(), List.of(), List.of(), List.of(), List.of(), false
        );
    }
}
