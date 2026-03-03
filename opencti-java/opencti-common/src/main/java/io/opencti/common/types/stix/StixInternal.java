package io.opencti.common.types.stix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * STIX Internal type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * interface StixInternal extends StixObject {
 *   name: string;
 * }
 */
public class StixInternal extends StixObject {
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
