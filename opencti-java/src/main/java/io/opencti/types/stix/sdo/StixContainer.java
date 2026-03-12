package io.opencti.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixDomainObject;
import io.opencti.types.stix.common.StixId;

import java.util.List;

/**
 * STIX Container type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixContainer extends StixDomainObject {
 *   object_refs: Array<StixId>;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixContainerExtension;
 *   };
 * }
 */
public abstract class StixContainer extends StixDomainObject {
    @JsonProperty("object_refs")
    protected List<StixId> objectRefs;
    protected StixContainerExtension containerExtension;

    public List<StixId> getObjectRefs() {
        return objectRefs;
    }

    public void setObjectRefs(List<StixId> objectRefs) {
        this.objectRefs = objectRefs;
    }

    public StixContainerExtension getContainerExtension() {
        return containerExtension;
    }

    public void setContainerExtension(StixContainerExtension containerExtension) {
        this.containerExtension = containerExtension;
    }
}

