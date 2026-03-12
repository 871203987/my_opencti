package io.opencti.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixDate;
import io.opencti.types.stix.common.StixId;

import java.util.List;

/**
 * STIX Report type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixReport extends StixContainer {
 *   name: string;
 *   description: string;
 *   report_types: Array<string>;
 *   published: StixDate;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixReportExtension;
 *   };
 * }
 */
public class StixReport extends StixContainer {
    public static final String TYPE = "report";

    @JsonProperty("report_types")
    private List<String> reportTypes;
    private StixDate published;
    private StixReportExtension reportExtension;

    public StixReport() {
        this.type = TYPE;
    }

    public List<String> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(List<String> reportTypes) {
        this.reportTypes = reportTypes;
    }

    public StixDate getPublished() {
        return published;
    }

    public void setPublished(StixDate published) {
        this.published = published;
    }

    public StixReportExtension getReportExtension() {
        return reportExtension;
    }

    public void setReportExtension(StixReportExtension reportExtension) {
        this.reportExtension = reportExtension;
    }
}

