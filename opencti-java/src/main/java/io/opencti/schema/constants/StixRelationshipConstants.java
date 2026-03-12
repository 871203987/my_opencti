package io.opencti.schema.constants;

/**
 * STIX Relationship Constants.
 * Original file: opencti-platform/opencti-graphql/src/schema/stixCoreRelationship.ts
 *
 * This class defines all STIX core relationship type constants.
 */
public class StixRelationshipConstants {

    // region Standard STIX core relationships
    public static final String RELATION_DELIVERS = "delivers";
    public static final String RELATION_TARGETS = "targets";
    public static final String RELATION_USES = "uses";
    public static final String RELATION_ATTRIBUTED_TO = "attributed-to";
    public static final String RELATION_COMPROMISES = "compromises";
    public static final String RELATION_ORIGINATES_FROM = "originates-from";
    public static final String RELATION_INVESTIGATES = "investigates";
    public static final String RELATION_MITIGATES = "mitigates";
    public static final String RELATION_LOCATED_AT = "located-at";
    public static final String RELATION_INDICATES = "indicates";
    public static final String RELATION_BASED_ON = "based-on";
    public static final String RELATION_COMMUNICATES_WITH = "communicates-with";
    public static final String RELATION_CONSISTS_OF = "consists-of";
    public static final String RELATION_CONTROLS = "controls";
    public static final String RELATION_HAS = "has";
    public static final String RELATION_HOSTS = "hosts";
    public static final String RELATION_OWNS = "owns";
    public static final String RELATION_AUTHORED_BY = "authored-by";
    public static final String RELATION_BEACONS_TO = "beacons-to";
    public static final String RELATION_EXFILTRATES_TO = "exfiltrates-to";
    public static final String RELATION_DOWNLOADS = "downloads";
    public static final String RELATION_DROPS = "drops";
    public static final String RELATION_EXPLOITS = "exploits";
    public static final String RELATION_VARIANT_OF = "variant-of";
    public static final String RELATION_CHARACTERIZES = "characterizes";
    public static final String RELATION_ANALYSIS_OF = "analysis-of";
    public static final String RELATION_STATIC_ANALYSIS_OF = "static-analysis-of";
    public static final String RELATION_DYNAMIC_ANALYSIS_OF = "dynamic-analysis-of";
    public static final String RELATION_IMPERSONATES = "impersonates";
    public static final String RELATION_REMEDIATES = "remediates";
    public static final String RELATION_RELATED_TO = "related-to";
    public static final String RELATION_DERIVED_FROM = "derived-from";
    public static final String RELATION_DUPLICATE_OF = "duplicate-of";
    public static final String RELATION_BELONGS_TO = "belongs-to";
    public static final String RELATION_RESOLVES_TO = "resolves-to";
    public static final String RELATION_IDENTIFIES = "identifies";
    public static final String RELATION_PUBLISHES = "publishes";
    public static final String RELATION_COOPERATES_WITH = "cooperates-with";
    public static final String RELATION_PARTICIPATES_IN = "participates-in";
    public static final String RELATION_REVOKED_BY = "revoked-by";
    // endregion

    // region Extended relationships (OpenCTI)
    public static final String RELATION_PART_OF = "part-of";
    public static final String RELATION_AMPLIFIES = "amplifies";
    public static final String RELATION_SUBNARRATIVE_OF = "subnarrative-of";
    public static final String RELATION_EMPLOYED_BY = "employed-by";
    public static final String RELATION_RESIDES_IN = "resides-in";
    public static final String RELATION_CITIZEN_OF = "citizen-of";
    public static final String RELATION_NATIONAL_OF = "national-of";
    public static final String RELATION_KNOWN_AS = "known-as";
    public static final String RELATION_REPORTS_TO = "reports-to";
    public static final String RELATION_SUPPORTS = "supports";
    public static final String RELATION_SHOULD_COVER = "should-cover";
    public static final String RELATION_HAS_COVERED = "has-covered";
    // endregion

    // region Extended relationships (MITRE)
    public static final String RELATION_SUBTECHNIQUE_OF = "subtechnique-of";
    public static final String RELATION_DETECTS = "detects";
    // endregion

    /**
     * Get all STIX core relationship types.
     *
     * @return Array of relationship type constants
     */
    public static String[] getAllStixCoreRelationships() {
        return new String[] {
            RELATION_DELIVERS,
            RELATION_TARGETS,
            RELATION_USES,
            RELATION_ATTRIBUTED_TO,
            RELATION_COMPROMISES,
            RELATION_ORIGINATES_FROM,
            RELATION_INVESTIGATES,
            RELATION_MITIGATES,
            RELATION_LOCATED_AT,
            RELATION_INDICATES,
            RELATION_BASED_ON,
            RELATION_COMMUNICATES_WITH,
            RELATION_CONSISTS_OF,
            RELATION_CONTROLS,
            RELATION_HAS,
            RELATION_HOSTS,
            RELATION_OWNS,
            RELATION_AUTHORED_BY,
            RELATION_BEACONS_TO,
            RELATION_EXFILTRATES_TO,
            RELATION_DOWNLOADS,
            RELATION_DROPS,
            RELATION_EXPLOITS,
            RELATION_VARIANT_OF,
            RELATION_CHARACTERIZES,
            RELATION_ANALYSIS_OF,
            RELATION_STATIC_ANALYSIS_OF,
            RELATION_DYNAMIC_ANALYSIS_OF,
            RELATION_IMPERSONATES,
            RELATION_REMEDIATES,
            RELATION_RELATED_TO,
            RELATION_DERIVED_FROM,
            RELATION_DUPLICATE_OF,
            RELATION_BELONGS_TO,
            RELATION_RESOLVES_TO,
            RELATION_IDENTIFIES,
            RELATION_PUBLISHES,
            RELATION_COOPERATES_WITH,
            RELATION_PARTICIPATES_IN,
            RELATION_REVOKED_BY,
            RELATION_PART_OF,
            RELATION_AMPLIFIES,
            RELATION_SUBNARRATIVE_OF,
            RELATION_EMPLOYED_BY,
            RELATION_RESIDES_IN,
            RELATION_CITIZEN_OF,
            RELATION_NATIONAL_OF,
            RELATION_KNOWN_AS,
            RELATION_REPORTS_TO,
            RELATION_SUPPORTS,
            RELATION_SHOULD_COVER,
            RELATION_HAS_COVERED,
            RELATION_SUBTECHNIQUE_OF,
            RELATION_DETECTS
        };
    }
}
