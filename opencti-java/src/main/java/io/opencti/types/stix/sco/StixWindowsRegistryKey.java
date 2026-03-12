package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Windows Registry Key object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixWindowsRegistryKey extends StixCyberObject {
    public static final String TYPE = "windows-registry-key";

    private String key;
    private List<StixWindowsRegistryValueType> values;
    @JsonProperty("modified_time")
    private String modifiedTime;
    @JsonProperty("creator_user_ref")
    private String creatorUserRef;
    @JsonProperty("number_of_subkeys")
    private Integer numberOfSubkeys;

    public StixWindowsRegistryKey() {
        this.type = TYPE;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<StixWindowsRegistryValueType> getValues() {
        return values;
    }

    public void setValues(List<StixWindowsRegistryValueType> values) {
        this.values = values;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreatorUserRef() {
        return creatorUserRef;
    }

    public void setCreatorUserRef(String creatorUserRef) {
        this.creatorUserRef = creatorUserRef;
    }

    public Integer getNumberOfSubkeys() {
        return numberOfSubkeys;
    }

    public void setNumberOfSubkeys(Integer numberOfSubkeys) {
        this.numberOfSubkeys = numberOfSubkeys;
    }
}
