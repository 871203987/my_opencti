package io.opencti.types.stix.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * STIX OpenCTI Extension for SDO.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 */
public record StixOpenctiExtensionSDO(
        @JsonProperty("extension_type") String extensionType,
        String id,
        List<StixFileExtension> files,
        List<String> aliases,
        @JsonProperty("granted_refs") List<StixId> grantedRefs,
        @JsonProperty("granted_refs_ids") List<String> grantedRefsIds,
        @JsonProperty("pir_information") List<Map<String, Object>> pirInformation,
        @JsonProperty("stix_ids") List<StixId> stixIds,
        String type,
        @JsonProperty("created_at") StixDate createdAt,
        @JsonProperty("updated_at") StixDate updatedAt,
        @JsonProperty("modified_at") StixDate modifiedAt,
        @JsonProperty("is_inferred") boolean isInferred,
        @JsonProperty("workflow_id") String workflowId,
        @JsonProperty("assignee_ids") List<String> assigneeIds,
        @JsonProperty("participant_ids") List<String> participantIds,
        @JsonProperty("creator_ids") List<String> creatorIds,
        @JsonProperty("authorized_members") List<AuthorizedMember> authorizedMembers,
        @JsonProperty("labels_ids") List<String> labelsIds,
        @JsonProperty("created_by_ref_id") String createdByRefId,
        @JsonProperty("created_by_ref_type") String createdByRefType,
        @JsonProperty("converter_csv") String converterCsv,
        @JsonProperty("opencti_operation") String openctiOperation,
        List<StixMetric> metrics
) {
    public StixOpenctiExtensionSDO {
        extensionType = StixExtensionConstants.EXTENSION_TYPE_NEW_SDO;
    }
}

