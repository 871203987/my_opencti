package io.opencti.types.stix.sco;

import io.opencti.types.stix.common.*;

/**
 * STIX URL object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixUrl extends StixCyberObject {
    public static final String TYPE = "url";

    private String value;

    public StixUrl() {
        this.type = TYPE;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
