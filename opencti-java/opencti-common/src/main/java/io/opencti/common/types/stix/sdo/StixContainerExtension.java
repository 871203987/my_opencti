package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixId;

import java.util.List;

/**
 * STIX Container Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 */
public record StixContainerExtension(
        @JsonProperty("object_refs_inferred") List<StixId> objectRefsInferred
) {
}
