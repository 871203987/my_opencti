package io.opencti.common.types.stix.smo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixMarkingsObject;

/**
 * STIX Marking Definition type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-smo.d.ts
 * Original type:
 * export interface StixMarkingDefinition extends StixMarkingsObject {
 *   name: string;
 *   definition_type: string;
 *   extensions: {
 *     [STIX_EXT_OCTI]: MarkingDefinitionExtension;
 *   };
 * }
 */
public class StixMarkingDefinition extends StixMarkingsObject {
    public static final String TYPE = "marking-definition";

    public static final String TLP_WHITE = "tlp-white";
    public static final String TLP_GREEN = "tlp-green";
    public static final String TLP_AMBER = "tlp-amber";
    public static final String TLP_AMBER_STRICT = "tlp-amber-strict";
    public static final String TLP_RED = "tlp-red";
    public static final String TLP_CLEAR = "tlp-clear";

    private String name;
    @JsonProperty("definition_type")
    private String definitionType;
    private MarkingDefinitionExtension markingExtension;

    public StixMarkingDefinition() {
        this.type = TYPE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinitionType() {
        return definitionType;
    }

    public void setDefinitionType(String definitionType) {
        this.definitionType = definitionType;
    }

    public MarkingDefinitionExtension getMarkingExtension() {
        return markingExtension;
    }

    public void setMarkingExtension(MarkingDefinitionExtension markingExtension) {
        this.markingExtension = markingExtension;
    }
}
