package io.opencti.common.types.stix;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * STIX Cyber Object type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-common.d.ts
 * Original type:
 * interface StixCyberObject extends StixObject {
 *   object_marking_refs: Array<StixId>;
 *   defanged: boolean;
 *   x_opencti_score?: number;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpenctiExtension;
 *     [STIX_EXT_OCTI_SCO]?: CyberObjectExtension;
 *   };
 * }
 */
public abstract class StixCyberObject extends StixObject {
    protected Boolean defanged;
    protected Integer score;
    protected StixOpenctiExtension openctiExtension;
    protected CyberObjectExtension cyberExtension;

    public Boolean getDefanged() {
        return defanged;
    }

    public void setDefanged(Boolean defanged) {
        this.defanged = defanged;
    }

    @JsonProperty("x_opencti_score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public StixOpenctiExtension getOpenctiExtension() {
        return openctiExtension;
    }

    public void setOpenctiExtension(StixOpenctiExtension openctiExtension) {
        this.openctiExtension = openctiExtension;
    }

    public CyberObjectExtension getCyberExtension() {
        return cyberExtension;
    }

    public void setCyberExtension(CyberObjectExtension cyberExtension) {
        this.cyberExtension = cyberExtension;
    }
}
