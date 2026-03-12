package io.opencti.types.stix.sco;

import io.opencti.types.stix.common.*;

/**
 * STIX MAC Address object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixMacAddr extends StixCyberObject {
    public static final String TYPE = "mac-addr";

    private String value;

    public StixMacAddr() {
        this.type = TYPE;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
