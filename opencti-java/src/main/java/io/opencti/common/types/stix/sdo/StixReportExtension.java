package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixId;

import java.util.List;

/**
 * STIX Report Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 */
public record StixReportExtension(
        String content,
        @JsonProperty("content_mapping") String contentMapping,
        @JsonProperty("object_refs_inferred") List<StixId> objectRefsInferred,
        String reliability
) {
}
