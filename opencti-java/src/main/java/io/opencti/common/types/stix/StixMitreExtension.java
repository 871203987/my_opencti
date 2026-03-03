package io.opencti.common.types.stix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * STIX MITRE Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * interface StixMitreExtension {
 *   extension_type: 'property-extension' | 'new-sdo';
 *   id: string;
 *   detection: string;
 *   permissions_required: Array<string>;
 *   platforms: Array<string>;
 *   collection_layers: Array<string>;
 * }
 */
public record StixMitreExtension(
        @JsonProperty("extension_type") String extensionType,
        String id,
        String detection,
        @JsonProperty("permissions_required") List<String> permissionsRequired,
        List<String> platforms,
        @JsonProperty("collection_layers") List<String> collectionLayers
) {
    public static StixMitreExtension of(String id) {
        return new StixMitreExtension(
                StixExtensionConstants.EXTENSION_TYPE_PROPERTY_EXTENSION,
                id, null, List.of(), List.of(), List.of()
        );
    }
}
