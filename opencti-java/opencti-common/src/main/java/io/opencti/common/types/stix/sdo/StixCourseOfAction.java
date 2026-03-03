package io.opencti.common.types.stix.sdo;

import io.opencti.common.types.stix.StixDomainObject;
import io.opencti.common.types.stix.StixKillChainPhase;
import io.opencti.common.types.stix.StixMitreExtension;
import io.opencti.common.types.stix.StixOpenctiExtension;

import java.util.List;

/**
 * STIX Course of Action type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixCourseOfAction extends StixDomainObject {
 *   name: string;
 *   description: string;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpenctiExtension;
 *     [STIX_EXT_MITRE]: StixMitreExtension;
 *   };
 * }
 */
public class StixCourseOfAction extends StixDomainObject {
    public static final String TYPE = "course-of-action";

    private String name;
    private String description;
    private StixOpenctiExtension openctiExtension;
    private StixMitreExtension mitreExtension;

    public StixCourseOfAction() {
        this.type = TYPE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StixOpenctiExtension getOpenctiExtension() {
        return openctiExtension;
    }

    public void setOpenctiExtension(StixOpenctiExtension openctiExtension) {
        this.openctiExtension = openctiExtension;
    }

    public StixMitreExtension getMitreExtension() {
        return mitreExtension;
    }

    public void setMitreExtension(StixMitreExtension mitreExtension) {
        this.mitreExtension = mitreExtension;
    }
}
