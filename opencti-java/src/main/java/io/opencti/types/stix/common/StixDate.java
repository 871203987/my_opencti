package io.opencti.types.stix.common;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * STIX Date type wrapper.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type: export type StixDate = string | undefined;
 */
public record StixDate(String value) {

    private static final DateTimeFormatter STIX_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    public static StixDate of(String value) {
        return new StixDate(value);
    }

    public static StixDate of(Instant instant) {
        if (instant == null) {
            return new StixDate(null);
        }
        return new StixDate(STIX_FORMATTER.format(instant));
    }

    public static StixDate now() {
        return of(Instant.now());
    }

    public Optional<Instant> toInstant() {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Instant.parse(value));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    public boolean isPresent() {
        return value != null && !value.isEmpty();
    }

    public static StixDate empty() {
        return new StixDate(null);
    }
}

