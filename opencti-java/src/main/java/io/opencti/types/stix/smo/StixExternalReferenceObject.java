package io.opencti.types.stix.smo;

import io.opencti.types.stix.common.StixExternalReference;
import io.opencti.types.stix.common.StixObject;
import io.opencti.types.stix.common.StixOpenctiExtensionSDO;

/**
 * STIX External Reference Object type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-smo.d.ts
 * Original type:
 * export interface StixExternalReference extends StixInternalExternalReference, StixObject {
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpenctiExtensionSDO;
 *   };
 * }
 */
public class StixExternalReferenceObject extends StixObject {
    public static final String TYPE = "external-reference";

    private StixExternalReference externalReference;
    private StixOpenctiExtensionSDO openctiExtension;

    public StixExternalReferenceObject() {
        this.type = TYPE;
    }

    public StixExternalReference getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(StixExternalReference externalReference) {
        this.externalReference = externalReference;
    }

    public StixOpenctiExtensionSDO getOpenctiExtension() {
        return openctiExtension;
    }

    public void setOpenctiExtension(StixOpenctiExtensionSDO openctiExtension) {
        this.openctiExtension = openctiExtension;
    }
}

