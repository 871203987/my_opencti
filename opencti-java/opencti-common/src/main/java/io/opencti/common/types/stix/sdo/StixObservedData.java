package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDate;

/**
 * STIX Observed Data type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixObservedData extends StixContainer {
 *   first_observed: StixDate;
 *   last_observed: StixDate;
 *   number_observed: number;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixObservedDataExtension;
 *   };
 * }
 */
public class StixObservedData extends StixContainer {
    public static final String TYPE = "observed-data";

    @JsonProperty("first_observed")
    private StixDate firstObserved;
    @JsonProperty("last_observed")
    private StixDate lastObserved;
    @JsonProperty("number_observed")
    private Integer numberObserved;
    private StixObservedDataExtension observedDataExtension;

    public StixObservedData() {
        this.type = TYPE;
    }

    public StixDate getFirstObserved() {
        return firstObserved;
    }

    public void setFirstObserved(StixDate firstObserved) {
        this.firstObserved = firstObserved;
    }

    public StixDate getLastObserved() {
        return lastObserved;
    }

    public void setLastObserved(StixDate lastObserved) {
        this.lastObserved = lastObserved;
    }

    public Integer getNumberObserved() {
        return numberObserved;
    }

    public void setNumberObserved(Integer numberObserved) {
        this.numberObserved = numberObserved;
    }

    public StixObservedDataExtension getObservedDataExtension() {
        return observedDataExtension;
    }

    public void setObservedDataExtension(StixObservedDataExtension observedDataExtension) {
        this.observedDataExtension = observedDataExtension;
    }
}
