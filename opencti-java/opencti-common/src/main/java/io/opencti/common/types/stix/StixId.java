package io.opencti.common.types.stix;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * STIX Identifier type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type: export type StixId = `${string}--${v4 | v5}`;
 */
public record StixId(String type, UUID uuid) {

    private static final Pattern STIX_ID_PATTERN = Pattern.compile("^([a-z][a-z0-9-]+)--([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})$", Pattern.CASE_INSENSITIVE);

    public StixId {
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(uuid, "uuid cannot be null");
    }

    public static StixId of(String type, UUID uuid) {
        return new StixId(type, uuid);
    }

    public static StixId of(String type) {
        return new StixId(type, UUID.randomUUID());
    }

    public static StixId parse(String stixId) {
        if (stixId == null || stixId.isEmpty()) {
            throw new IllegalArgumentException("STIX ID cannot be null or empty");
        }
        var matcher = STIX_ID_PATTERN.matcher(stixId);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid STIX ID format: " + stixId);
        }
        String type = matcher.group(1);
        UUID uuid = UUID.fromString(matcher.group(2));
        return new StixId(type, uuid);
    }

    public static boolean isValid(String stixId) {
        if (stixId == null || stixId.isEmpty()) {
            return false;
        }
        return STIX_ID_PATTERN.matcher(stixId).matches();
    }

    @Override
    public String toString() {
        return type + "--" + uuid;
    }

    public String getValue() {
        return toString();
    }
}
