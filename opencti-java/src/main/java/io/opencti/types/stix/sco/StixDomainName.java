package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Domain Name object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 * Original type:
 * export interface StixDomainName extends StixCyberObject {
 *   value: string;
 *   resolves_to_refs: Array<StixId>; // optional
 * }
 */
public class StixDomainName extends StixCyberObject {
    public static final String TYPE = "domain-name";

    private String value;
    @JsonProperty("resolves_to_refs")
    private List<String> resolvesToRefs;

    public StixDomainName() {
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
}
