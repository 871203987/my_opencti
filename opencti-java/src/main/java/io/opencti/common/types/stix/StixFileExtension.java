package io.opencti.common.types.stix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * STIX File Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 */
public record StixFileExtension(
        String name,
        String uri,
        String version,
        @JsonProperty("mime_type") String mimeType,
        @JsonProperty("object_marking_refs") List<StixId> objectMarkingRefs,
        String data
) {
}
