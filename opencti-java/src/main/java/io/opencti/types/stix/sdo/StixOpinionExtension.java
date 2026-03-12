package io.opencti.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixId;

import java.util.List;

/**
 * STIX Opinion Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 */
public record StixOpinionExtension(
        String content,
        @JsonProperty("content_mapping") String contentMapping,
        @JsonProperty("object_refs_inferred") List<StixId> objectRefsInferred
) {
}

