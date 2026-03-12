package io.opencti.database.stix;

/**
 * Original file: opencti-platform/opencti-graphql/src/database/stix.ts
 * STIX Constants Definition
 *
 * @author OpenCTI Java Rewrite Project
 */
public final class StixConstants {

    private StixConstants() {
        // Utility class, prevent instantiation
    }

    // STIX Specification Version
    public static final String STIX_SPEC_VERSION = "2.1";

    // Maximum Transient STIX ID Count
    public static final int MAX_TRANSIENT_STIX_IDS = 200;

    // Date and Time Constants
    public static final long FROM_START = 0L; // 1970-01-01T00:00:00.000Z
    public static final long UNTIL_END = 100000000000000L; // 5138-11-16T09:46:40.000Z
    public static final String FROM_START_STR = "1970-01-01T00:00:00.000Z";
    public static final String UNTIL_END_STR = "5138-11-16T09:46:40.000Z";

    // Relationship Type Definitions
    public static final String REL_BUILT_IN = "builtin";
    public static final String REL_NEW = "new";
    public static final String REL_EXTENDED = "extended";

    // STIX Extension Definitions
    public static final String STIX_EXT_OCTI = "extension-definition--ea279b3d-ef0f-5a38-9d0d-6b11fe7f8f4f";
    public static final String STIX_EXT_OCTI_SCO = "extension-definition--950eb0c8-658a-4f5b-a1d2-707d338415cc";
    public static final String STIX_EXT_MITRE = "extension-definition--322b8f8b-ab4d-4043-a55d-827a2a0c4d2e";

    // STIX ID Prefixes
    public static final String STIX_ID_PREFIX_BUNDLE = "bundle--";
    public static final String STIX_ID_PREFIX_INDICATOR = "indicator--";
    public static final String STIX_ID_PREFIX_MALWARE = "malware--";
    public static final String STIX_ID_PREFIX_ATTACK_PATTERN = "attack-pattern--";
    public static final String STIX_ID_PREFIX_CAMPAIGN = "campaign--";
    public static final String STIX_ID_PREFIX_COURSE_OF_ACTION = "course-of-action--";
    public static final String STIX_ID_PREFIX_IDENTITY = "identity--";
    public static final String STIX_ID_PREFIX_INFRASTRUCTURE = "infrastructure--";
    public static final String STIX_ID_PREFIX_INTRUSION_SET = "intrusion-set--";
    public static final String STIX_ID_PREFIX_LOCATION = "location--";
    public static final String STIX_ID_PREFIX_NOTE = "note--";
    public static final String STIX_ID_PREFIX_OBSERVED_DATA = "observed-data--";
    public static final String STIX_ID_PREFIX_OPINION = "opinion--";
    public static final String STIX_ID_PREFIX_REPORT = "report--";
    public static final String STIX_ID_PREFIX_THREAT_ACTOR = "threat-actor--";
    public static final String STIX_ID_PREFIX_TOOL = "tool--";
    public static final String STIX_ID_PREFIX_VULNERABILITY = "vulnerability--";
    public static final String STIX_ID_PREFIX_RELATIONSHIP = "relationship--";
    public static final String STIX_ID_PREFIX_SIGHTING = "sighting--";
    public static final String STIX_ID_PREFIX_MARKING_DEFINITION = "marking-definition--";
    public static final String STIX_ID_PREFIX_LABEL = "label--";
    public static final String STIX_ID_PREFIX_EXTERNAL_REFERENCE = "external-reference--";
    public static final String STIX_ID_PREFIX_KILL_CHAIN_PHASE = "kill-chain-phase--";

    // SCO Type Prefixes
    public static final String STIX_ID_PREFIX_ARTIFACT = "artifact--";
    public static final String STIX_ID_PREFIX_AUTONOMOUS_SYSTEM = "autonomous-system--";
    public static final String STIX_ID_PREFIX_DIRECTORY = "directory--";
    public static final String STIX_ID_PREFIX_DOMAIN_NAME = "domain-name--";
    public static final String STIX_ID_PREFIX_EMAIL_ADDR = "email-addr--";
    public static final String STIX_ID_PREFIX_EMAIL_MESSAGE = "email-message--";
    public static final String STIX_ID_PREFIX_FILE = "file--";
    public static final String STIX_ID_PREFIX_IPV4_ADDR = "ipv4-addr--";
    public static final String STIX_ID_PREFIX_IPV6_ADDR = "ipv6-addr--";
    public static final String STIX_ID_PREFIX_MAC_ADDR = "mac-addr--";
    public static final String STIX_ID_PREFIX_MUTEX = "mutex--";
    public static final String STIX_ID_PREFIX_NETWORK_TRAFFIC = "network-traffic--";
    public static final String STIX_ID_PREFIX_PROCESS = "process--";
    public static final String STIX_ID_PREFIX_SOFTWARE = "software--";
    public static final String STIX_ID_PREFIX_URL = "url--";
    public static final String STIX_ID_PREFIX_USER_ACCOUNT = "user-account--";
    public static final String STIX_ID_PREFIX_WINDOWS_REGISTRY_KEY = "windows-registry-key--";
    public static final String STIX_ID_PREFIX_X509_CERTIFICATE = "x509-certificate--";

    // OpenCTI Custom Type Prefixes
    public static final String STIX_ID_PREFIX_OPENCTI = "x-opencti-";
    public static final String STIX_ID_PREFIX_MITRE = "x-mitre-";

    // Extension Types
    public static final String EXTENSION_TYPE_PROPERTY = "property-extension";
    public static final String EXTENSION_TYPE_NEW_SDO = "new-sdo";
    public static final String EXTENSION_TYPE_NEW_SCO = "new-sco";
    public static final String EXTENSION_TYPE_NEW_SRO = "new-sro";

    // Relationship Type Constants
    public static final String RELATION_RELATED_TO = "related-to";
    public static final String RELATION_REVOKED_BY = "revoked-by";
    public static final String RELATION_DERIVED_FROM = "derived-from";
    public static final String RELATION_DUPLICATE_OF = "duplicate-of";

    // Abstract Relationship Type Constants
    public static final String ABSTRACT_INTERNAL_RELATIONSHIP = "Internal-Relationship";
    public static final String ABSTRACT_STIX_REF_RELATIONSHIP = "Stix-Ref-Relationship";
    public static final String ABSTRACT_STIX_CORE_RELATIONSHIP = "Stix-Core-Relationship";
    public static final String ABSTRACT_STIX_SIGHTING_RELATIONSHIP = "Stix-Sighting-Relationship";

    // Internal Relationship Type Constants
    public static final String RELATION_MIGRATES = "migrates";
    public static final String RELATION_MEMBER_OF = "member-of";
    public static final String RELATION_PARTICIPATE_TO = "participate-to";
    public static final String RELATION_ALLOWED_BY = "allowed-by";
    public static final String RELATION_HAS_ROLE = "has-role";
    public static final String RELATION_HAS_CAPABILITY = "has-capability";
    public static final String RELATION_HAS_CAPABILITY_IN_DRAFT = "has-capability-in-draft";
    public static final String RELATION_ACCESSES_TO = "accesses-to";
    public static final String RELATION_IN_PIR = "in-pir";
}
