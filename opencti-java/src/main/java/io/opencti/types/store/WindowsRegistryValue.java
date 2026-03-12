package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Windows Registry Value type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Represents a value in a Windows registry key.
 */
public class WindowsRegistryValue {
    protected String name;
    protected String data;
    protected String dataType;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    @JsonProperty("data_type")
    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }
}

