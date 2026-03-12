package io.opencti.types.stix.common;

import java.util.List;

/**
 * STIX Bundle type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * export type StixBundle = {
 *   id: string;
 *   spec_version: string;
 *   type: 'bundle';
 *   objects: StixObject[];
 * };
 */
public record StixBundle(
        String id,
        String specVersion,
        String type,
        List<StixObject> objects
) {
    public static final String TYPE = "bundle";

    public StixBundle {
        type = TYPE;
        specVersion = "2.1";
    }

    public static StixBundle of(List<StixObject> objects) {
        return new StixBundle(
                "bundle--" + java.util.UUID.randomUUID(),
                "2.1",
                TYPE,
                objects
        );
    }
}

