package io.opencti.common.types.stix.sro;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;
import io.opencti.common.types.stix.StixId;
import io.opencti.common.types.stix.StixRelationshipObject;

import java.util.List;

/**
 * STIX Sighting type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sro.d.ts
 * Original type:
 * export interface StixSighting extends StixRelationshipObject {
 *   description: string;
 *   first_seen: StixDate;
 *   last_seen: StixDate;
 *   count: number;
 *   sighting_of_ref: StixId;
 *   observed_data_refs: Array<StixId>;
 *   where_sighted_refs: Array<StixId>;
 *   summary: string;
 *   extensions: {
 *     [STIX_EXT_OCTI]: SightingExtension;
 *   };
 * }
 */
public class StixSighting extends StixRelationshipObject {
    public static final String TYPE = "sighting";

    private String description;
    @JsonProperty("first_seen")
    private StixDate firstSeen;
    @JsonProperty("last_seen")
    private StixDate lastSeen;
    private Integer count;
    @JsonProperty("sighting_of_ref")
    private StixId sightingOfRef;
    @JsonProperty("observed_data_refs")
    private List<StixId> observedDataRefs;
    @JsonProperty("where_sighted_refs")
    private List<StixId> whereSightedRefs;
    private String summary;
    private SightingExtension sightingExtension;

    public StixSighting() {
        this.type = TYPE;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StixDate getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(StixDate firstSeen) {
        this.firstSeen = firstSeen;
    }

    public StixDate getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(StixDate lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public StixId getSightingOfRef() {
        return sightingOfRef;
    }

    public void setSightingOfRef(StixId sightingOfRef) {
        this.sightingOfRef = sightingOfRef;
    }

    public List<StixId> getObservedDataRefs() {
        return observedDataRefs;
    }

    public void setObservedDataRefs(List<StixId> observedDataRefs) {
        this.observedDataRefs = observedDataRefs;
    }

    public List<StixId> getWhereSightedRefs() {
        return whereSightedRefs;
    }

    public void setWhereSightedRefs(List<StixId> whereSightedRefs) {
        this.whereSightedRefs = whereSightedRefs;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public SightingExtension getSightingExtension() {
        return sightingExtension;
    }

    public void setSightingExtension(SightingExtension sightingExtension) {
        this.sightingExtension = sightingExtension;
    }
}
