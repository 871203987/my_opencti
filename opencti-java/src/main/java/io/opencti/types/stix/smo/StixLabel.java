package io.opencti.types.stix.smo;

import io.opencti.types.stix.common.StixObject;
import io.opencti.types.stix.common.StixOpenctiExtensionSDO;

/**
 * STIX Label type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-smo.d.ts
 * Original type:
 * export interface StixLabel extends StixObject {
 *   value: string;
 *   color: string;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpenctiExtensionSDO;
 *   };
 * }
 */
public class StixLabel extends StixObject {
    public static final String TYPE = "label";

    private String value;
    private String color;
    private StixOpenctiExtensionSDO openctiExtension;

    public StixLabel() {
        this.type = TYPE;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public StixOpenctiExtensionSDO getOpenctiExtension() {
        return openctiExtension;
    }

    public void setOpenctiExtension(StixOpenctiExtensionSDO openctiExtension) {
        this.openctiExtension = openctiExtension;
    }
}

