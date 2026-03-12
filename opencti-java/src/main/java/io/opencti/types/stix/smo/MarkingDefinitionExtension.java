package io.opencti.types.stix.smo;

/**
 * STIX Marking Definition Extension type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-smo.d.ts
 */
public record MarkingDefinitionExtension(
        String color,
        int order
) {
    public static MarkingDefinitionExtension of(String color, int order) {
        return new MarkingDefinitionExtension(color, order);
    }
}

