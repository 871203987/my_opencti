package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

/**
 * STIX Email Address object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 * Original type:
 * export interface StixEmailAddress extends StixCyberObject {
 *   value: string;
 *   display_name: string; // optional
 *   belongs_to_ref: StixId; // optional
 * }
 */
public class StixEmailAddress extends StixCyberObject {
    public static final String TYPE = "email-addr";

    private String value;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("belongs_to_ref")
    private String belongsToRef;

    public StixEmailAddress() {
        this.type = TYPE;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBelongsToRef() {
        return belongsToRef;
    }

    public void setBelongsToRef(String belongsToRef) {
        this.belongsToRef = belongsToRef;
    }
}
