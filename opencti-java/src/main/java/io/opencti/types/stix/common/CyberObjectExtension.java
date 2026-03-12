package io.opencti.types.stix.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Cyber Object Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * interface CyberObjectExtension {
 *   extension_type?: 'property-extension';
 *   labels?: Array<string>;
 *   description?: string;
 *   score?: number;
 *   created_by_ref?: StixId;
 *   external_references?: Array<StixInternalExternalReference>;
 * }
 */
public record CyberObjectExtension(
        @JsonProperty("extension_type") String extensionType,
        List<String> labels,
        String description,
        Integer score,
        @JsonProperty("created_by_ref") StixId createdByRef,
        @JsonProperty("external_references") List<StixExternalReference> externalReferences
) {
    public static CyberObjectExtension of() {
        return new CyberObjectExtension(
                StixExtensionConstants.EXTENSION_TYPE_PROPERTY_EXTENSION,
                List.of(), null, null, null, List.of()
        );
    }

    public static CyberObjectExtension of(List<String> labels, String description, Integer score) {
        return new CyberObjectExtension(
                StixExtensionConstants.EXTENSION_TYPE_PROPERTY_EXTENSION,
                labels, description, score, null, List.of()
        );
    }
}

