package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

/**
 * STIX Windows Registry Value Type object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixWindowsRegistryValueType extends StixCyberObject {
    public static final String TYPE = "windows-registry-value-type";

    private String name;
    private String data;
    @JsonProperty("data_type")
    private String dataType;

    public StixWindowsRegistryValueType() {
        this.type = TYPE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
