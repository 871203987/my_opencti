package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixId;

import java.util.List;

/**
 * STIX Observed Data Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixObservedDataExtension extends StixOpenctiExtension {
 *   content: string;
 *   content_mapping: string;
 *   object_refs_inferred?: Array<StixId>;
 * }
 */
public record StixObservedDataExtension(
        String content,
        @JsonProperty("content_mapping") String contentMapping,
        @JsonProperty("object_refs_inferred") List<StixId> objectRefsInferred
) {
}
