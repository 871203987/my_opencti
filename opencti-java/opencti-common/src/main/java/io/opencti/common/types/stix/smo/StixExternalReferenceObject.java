package io.opencti.common.types.stix.smo;

import io.opencti.common.types.stix.StixExternalReference;
import io.opencti.common.types.stix.StixObject;
import io.opencti.common.types.stix.StixOpenctiExtensionSDO;

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
