package io.opencti.types.stix.sco;

import io.opencti.types.stix.common.*;

/**
 * STIX Mutex object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixMutex extends StixCyberObject {
    public static final String TYPE = "mutex";

    private String name;

    public StixMutex() {
        this.type = TYPE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
