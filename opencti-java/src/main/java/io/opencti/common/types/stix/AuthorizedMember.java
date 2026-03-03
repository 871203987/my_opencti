package io.opencti.common.types.stix;

/**
 * Authorized Member type.
 * Original file: opencti-platform/opencti-graphql/src/utils/access.ts
 */
public record AuthorizedMember(
        String id,
        String accessRight
) {
    public static AuthorizedMember of(String id, String accessRight) {
        return new AuthorizedMember(id, accessRight);
    }
}
