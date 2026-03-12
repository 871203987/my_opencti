package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX IPv4 Address object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixIpv4Addr extends StixCyberObject {
    public static final String TYPE = "ipv4-addr";

    private String value;
    @JsonProperty("resolves_to_refs")
    private List<String> resolvesToRefs;
    @JsonProperty("belongs_to_refs")
    private List<String> belongsToRefs;

    public StixIpv4Addr() {
        this.type = TYPE;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getResolvesToRefs() {
        return resolvesToRefs;
    }

    public void setResolvesToRefs(List<String> resolvesToRefs) {
        this.resolvesToRefs = resolvesToRefs;
    }

    public List<String> getBelongsToRefs() {
        return belongsToRefs;
    }

    public void setBelongsToRefs(List<String> belongsToRefs) {
        this.belongsToRefs = belongsToRefs;
    }
}
