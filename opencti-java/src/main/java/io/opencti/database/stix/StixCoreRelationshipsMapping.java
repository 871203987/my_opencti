package io.opencti.database.stix;

import io.opencti.types.store.StoreRelation;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * STIX Core Relationships Mapping.
 * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts
 * 瀹氫箟鎵€鏈夋湁鏁堢殑STIX鏍稿績鍏崇郴鏄犲皠
 *
 * @author OpenCTI Java閲嶅啓椤圭洰
 */
@Slf4j
public class StixCoreRelationshipsMapping {

    // ==================== 鍏崇郴绫诲瀷甯搁噺 ====================
    public static final String RELATION_USES = "uses";
    public static final String RELATION_TARGETS = "targets";
    public static final String RELATION_ATTRIBUTED_TO = "attributed-to";
    public static final String RELATION_INDICATES = "indicates";
    public static final String RELATION_MITIGATES = "mitigates";
    public static final String RELATION_LOCATED_AT = "located-at";
    public static final String RELATION_BASED_ON = "based-on";
    public static final String RELATION_COMMUNICATES_WITH = "communicates-with";
    public static final String RELATION_CONSISTS_OF = "consists-of";
    public static final String RELATION_CONTROLS = "controls";
    public static final String RELATION_DELIVERS = "delivers";
    public static final String RELATION_DERIVED_FROM = "derived-from";
    public static final String RELATION_DETECTS = "detects";
    public static final String RELATION_DOWNLOADS = "downloads";
    public static final String RELATION_DROPS = "drops";
    public static final String RELATION_EXFILTRATES_TO = "exfiltrates-to";
    public static final String RELATION_EXPLOITS = "exploits";
    public static final String RELATION_HAS = "has";
    public static final String RELATION_HOSTS = "hosts";
    public static final String RELATION_IMPERSONATES = "impersonates";
    public static final String RELATION_INVESTIGATES = "investigates";
    public static final String RELATION_ORIGINATES_FROM = "originates-from";
    public static final String RELATION_OWNS = "owns";
    public static final String RELATION_RELATED_TO = "related-to";
    public static final String RELATION_REMEDIATES = "remediates";
    public static final String RELATION_RESOLVES_TO = "resolves-to";
    public static final String RELATION_REVOKED_BY = "revoked-by";
    public static final String RELATION_VARIANT_OF = "variant-of";
    public static final String RELATION_SUBTECHNIQUE_OF = "subtechnique-of";
    public static final String RELATION_PART_OF = "part-of";
    public static final String RELATION_BELONGS_TO = "belongs-to";
    public static final String RELATION_BEACONS_TO = "beacons-to";
    public static final String RELATION_COMPROMISES = "compromises";
    public static final String RELATION_COOPERATES_WITH = "cooperates-with";
    public static final String RELATION_AUTHORED_BY = "authored-by";
    public static final String RELATION_PUBLISHES = "publishes";
    public static final String RELATION_PARTICIPATES_IN = "participates-in";
    public static final String RELATION_AMPLIFIES = "amplifies";
    public static final String RELATION_SUPPORTS = "supports";
    public static final String RELATION_TECHNOLOGY = "technology";
    public static final String RELATION_TECHNOLOGY_TO = "technology-to";
    public static final String RELATION_TECHNOLOGY_FROM = "technology-from";
    public static final String RELATION_TRANSFERRED_TO = "transferred-to";
    public static final String RELATION_DEMONSTRATES = "demonstrates";
    public static final String RELATION_SHOULD_COVER = "should-cover";

    // ==================== 鍏崇郴绫诲瀷鍒嗙被 ====================
    public static final String REL_BUILT_IN = "builtin";
    public static final String REL_NEW = "new";
    public static final String REL_EXTENDED = "extended";

    // ==================== 瀹炰綋绫诲瀷甯搁噺 - SDO ====================
    public static final String ENTITY_TYPE_ATTACK_PATTERN = "Attack-Pattern";
    public static final String ENTITY_TYPE_CAMPAIGN = "Campaign";
    public static final String ENTITY_TYPE_COURSE_OF_ACTION = "Course-Of-Action";
    public static final String ENTITY_TYPE_IDENTITY_INDIVIDUAL = "Individual";
    public static final String ENTITY_TYPE_IDENTITY_ORGANIZATION = "Organization";
    public static final String ENTITY_TYPE_IDENTITY_SECTOR = "Sector";
    public static final String ENTITY_TYPE_IDENTITY_SYSTEM = "System";
    public static final String ENTITY_TYPE_IDENTITY_SECURITY_PLATFORM = "Security-Platform";
    public static final String ENTITY_TYPE_INCIDENT = "Incident";
    public static final String ENTITY_TYPE_INFRASTRUCTURE = "Infrastructure";
    public static final String ENTITY_TYPE_INTRUSION_SET = "Intrusion-Set";
    public static final String ENTITY_TYPE_LOCATION_POSITION = "Position";
    public static final String ENTITY_TYPE_LOCATION_CITY = "City";
    public static final String ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA = "Administrative-Area";
    public static final String ENTITY_TYPE_LOCATION_COUNTRY = "Country";
    public static final String ENTITY_TYPE_LOCATION_REGION = "Region";
    public static final String ENTITY_TYPE_MALWARE = "Malware";
    public static final String ENTITY_TYPE_THREAT_ACTOR = "Threat-Actor";
    public static final String ENTITY_TYPE_THREAT_ACTOR_GROUP = "Threat-Actor-Group";
    public static final String ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL = "Threat-Actor-Individual";
    public static final String ENTITY_TYPE_TOOL = "Tool";
    public static final String ENTITY_TYPE_VULNERABILITY = "Vulnerability";
    public static final String ENTITY_TYPE_INDICATOR = "Indicator";
    public static final String ENTITY_TYPE_CHANNEL = "Channel";
    public static final String ENTITY_TYPE_NARRATIVE = "Narrative";
    public static final String ENTITY_TYPE_EVENT = "Event";
    public static final String ENTITY_TYPE_CONTAINER_OBSERVED_DATA = "Observed-Data";

    // ==================== 瀹炰綋绫诲瀷甯搁噺 - SCO ====================
    public static final String ENTITY_IPV4_ADDR = "IPv4-Addr";
    public static final String ENTITY_IPV6_ADDR = "IPv6-Addr";
    public static final String ENTITY_MAC_ADDR = "MAC-Addr";
    public static final String ENTITY_DOMAIN_NAME = "Domain-Name";
    public static final String ENTITY_HASHED_OBSERVABLE_STIX_FILE = "StixFile";
    public static final String ENTITY_HASHED_OBSERVABLE_ARTIFACT = "Artifact";
    public static final String ENTITY_URL = "URL";
    public static final String ENTITY_AUTONOMOUS_SYSTEM = "Autonomous-System";
    public static final String ENTITY_SOFTWARE = "Software";
    public static final String ENTITY_USER_ACCOUNT = "User-Account";
    public static final String ENTITY_MEDIA_CONTENT = "Media-Content";
    public static final String ENTITY_HOSTNAME = "Hostname";
    public static final String ENTITY_TEXT = "Text";
    public static final String ENTITY_BANK_ACCOUNT = "Bank-Account";

    // ==================== 鎶借薄绫诲瀷 ====================
    public static final String ABSTRACT_STIX_CYBER_OBSERVABLE = "Stix-Cyber-Observable";

    // ==================== 鍏崇郴淇℃伅绫?====================
    public static class RelationshipInfo {
        public final String name;
        public final String type;

        public RelationshipInfo(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

    // STIX鏍稿績鍏崇郴鏄犲皠 - 鍖呭惈鍏崇郴绫诲瀷淇℃伅
    private static final Map<String, List<RelationshipInfo>> STIX_CORE_RELATIONSHIPS_MAPPING = new HashMap<>();

    static {
        initializeMappings();
    }

    /**
     * 鍒濆鍖栨墍鏈夊叧绯绘槧灏?     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts:stixCoreRelationshipsMapping
     */
    private static void initializeMappings() {
        // ==================== ATTACK_PATTERN ====================
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_SUBTECHNIQUE_OF, REL_NEW),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_DELIVERS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_ATTACK_PATTERN, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));

        // ==================== CAMPAIGN ====================
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_CHANNEL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_COMPROMISES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_INTRUSION_SET,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_THREAT_ACTOR,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_THREAT_ACTOR_GROUP,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CAMPAIGN, ENTITY_TYPE_CAMPAIGN,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== CHANNEL ====================
        addMappingWithType(ENTITY_TYPE_CHANNEL, ENTITY_TYPE_CHANNEL,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_CHANNEL, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_CHANNEL, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_CHANNEL, ENTITY_TYPE_THREAT_ACTOR_GROUP,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_CHANNEL, ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_CHANNEL, ENTITY_TYPE_INTRUSION_SET,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_CHANNEL, ENTITY_MEDIA_CONTENT,
                new RelationshipInfo(RELATION_AMPLIFIES, REL_NEW));

        // ==================== COURSE_OF_ACTION ====================
        addMappingWithType(ENTITY_TYPE_COURSE_OF_ACTION, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_MITIGATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_COURSE_OF_ACTION, ENTITY_TYPE_INDICATOR,
                new RelationshipInfo(RELATION_INVESTIGATES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_MITIGATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_COURSE_OF_ACTION, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_MITIGATES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_REMEDIATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_COURSE_OF_ACTION, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_MITIGATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_COURSE_OF_ACTION, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_MITIGATES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_REMEDIATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_COURSE_OF_ACTION, ENTITY_TYPE_COURSE_OF_ACTION,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== EVENT ====================
        addMappingWithType(ENTITY_TYPE_EVENT, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== INDIVIDUAL ====================
        addMappingWithType(ENTITY_TYPE_IDENTITY_INDIVIDUAL, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_PART_OF, REL_NEW),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_INDIVIDUAL, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_PART_OF, REL_NEW));
        addMappingWithType(ENTITY_TYPE_IDENTITY_INDIVIDUAL, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_INDIVIDUAL, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_INDIVIDUAL, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_INDIVIDUAL, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_INDIVIDUAL, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_INDIVIDUAL, ENTITY_MEDIA_CONTENT,
                new RelationshipInfo(RELATION_PUBLISHES, REL_NEW));

        // ==================== SECTOR ====================
        addMappingWithType(ENTITY_TYPE_IDENTITY_SECTOR, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_PART_OF, REL_NEW),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SECTOR, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SECTOR, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SECTOR, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SECTOR, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SECTOR, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));

        // ==================== SYSTEM ====================
        addMappingWithType(ENTITY_TYPE_IDENTITY_SYSTEM, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SYSTEM, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SYSTEM, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SYSTEM, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SYSTEM, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SYSTEM, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SYSTEM, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_HAS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_IDENTITY_SYSTEM, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== INCIDENT ====================
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_CHANNEL,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_CAMPAIGN,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_COMPROMISES, REL_EXTENDED),
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_INTRUSION_SET,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_EXTENDED),
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_EXTENDED),
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_EXTENDED),
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_EXTENDED),
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_EXTENDED),
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_THREAT_ACTOR,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_THREAT_ACTOR_GROUP,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INCIDENT, ENTITY_TYPE_INCIDENT,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== INDICATOR ====================
        addMappingWithType(ENTITY_TYPE_INDICATOR, ABSTRACT_STIX_CYBER_OBSERVABLE,
                new RelationshipInfo(RELATION_BASED_ON, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_INDICATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_CAMPAIGN,
                new RelationshipInfo(RELATION_INDICATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_CONTAINER_OBSERVED_DATA,
                new RelationshipInfo(RELATION_BASED_ON, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_INCIDENT,
                new RelationshipInfo(RELATION_INDICATES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_INDICATOR,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_INDICATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_INTRUSION_SET,
                new RelationshipInfo(RELATION_INDICATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_INDICATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_THREAT_ACTOR,
                new RelationshipInfo(RELATION_INDICATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_THREAT_ACTOR_GROUP,
                new RelationshipInfo(RELATION_INDICATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL,
                new RelationshipInfo(RELATION_INDICATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_INDICATES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INDICATOR, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_INDICATES, REL_EXTENDED));

        // ==================== INFRASTRUCTURE ====================
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ABSTRACT_STIX_CYBER_OBSERVABLE,
                new RelationshipInfo(RELATION_CONSISTS_OF, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_DOMAIN_NAME,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_IPV4_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_IPV6_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_CONTAINER_OBSERVED_DATA,
                new RelationshipInfo(RELATION_CONSISTS_OF, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_BUILT_IN),
                new RelationshipInfo(RELATION_CONSISTS_OF, REL_BUILT_IN),
                new RelationshipInfo(RELATION_CONTROLS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_SUPPORTS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_CONTROLS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DELIVERS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_HOSTS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_HOSTS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_HAS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_URL,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_SOFTWARE,
                new RelationshipInfo(RELATION_CONSISTS_OF, REL_EXTENDED),
                new RelationshipInfo(RELATION_HOSTS, REL_EXTENDED),
                new RelationshipInfo(RELATION_TECHNOLOGY, REL_EXTENDED),
                new RelationshipInfo(RELATION_TECHNOLOGY_TO, REL_EXTENDED),
                new RelationshipInfo(RELATION_TECHNOLOGY_FROM, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INFRASTRUCTURE, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_DETECTS, REL_NEW));

        // ==================== INTRUSION_SET ====================
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_CHANNEL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_COMPROMISES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_HOSTS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_OWNS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_THREAT_ACTOR,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_THREAT_ACTOR_GROUP,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_HASHED_OBSERVABLE_STIX_FILE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_INTRUSION_SET, ENTITY_TYPE_INTRUSION_SET,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== LOCATION mappings ====================
        addMappingWithType(ENTITY_TYPE_LOCATION_POSITION, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_LOCATION_POSITION, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_LOCATION_POSITION, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_LOCATION_POSITION, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_LOCATION_POSITION, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        addMappingWithType(ENTITY_TYPE_LOCATION_CITY, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_LOCATION_CITY, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_LOCATION_CITY, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_LOCATION_CITY, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        addMappingWithType(ENTITY_TYPE_LOCATION_COUNTRY, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_LOCATION_COUNTRY, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        addMappingWithType(ENTITY_TYPE_LOCATION_REGION, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        addMappingWithType(ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== MALWARE ====================
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_DOMAIN_NAME,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_HASHED_OBSERVABLE_STIX_FILE,
                new RelationshipInfo(RELATION_DOWNLOADS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DROPS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_IPV4_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_IPV6_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_BEACONS_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_EXFILTRATES_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_INTRUSION_SET,
                new RelationshipInfo(RELATION_AUTHORED_BY, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_ORIGINATES_FROM, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_CONTROLS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DOWNLOADS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DROPS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_VARIANT_OF, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_THREAT_ACTOR,
                new RelationshipInfo(RELATION_AUTHORED_BY, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL,
                new RelationshipInfo(RELATION_AUTHORED_BY, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_THREAT_ACTOR_GROUP,
                new RelationshipInfo(RELATION_AUTHORED_BY, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_DOWNLOADS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DROPS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_EXPLOITS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_MALWARE, ENTITY_URL,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_BUILT_IN));

        // ==================== NARRATIVE ====================
        addMappingWithType(ENTITY_TYPE_NARRATIVE, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== ORGANIZATION ====================
        addMappingWithType(ENTITY_TYPE_IDENTITY_ORGANIZATION, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_IDENTITY_ORGANIZATION, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_OWNS, REL_EXTENDED));

        // ==================== SECURITY_PLATFORM ====================
        addMappingWithType(ENTITY_TYPE_IDENTITY_SECURITY_PLATFORM, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_SHOULD_COVER, REL_NEW));

        // ==================== THREAT_ACTOR ====================
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_CHANNEL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_CAMPAIGN,
                new RelationshipInfo(RELATION_PARTICIPATES_IN, REL_NEW));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_IMPERSONATES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_IMPERSONATES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_COMPROMISES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_HOSTS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_OWNS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR, ENTITY_HASHED_OBSERVABLE_STIX_FILE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));

        // ==================== THREAT_ACTOR_GROUP ====================
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_CHANNEL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_CAMPAIGN,
                new RelationshipInfo(RELATION_PARTICIPATES_IN, REL_NEW));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_IMPERSONATES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_IMPERSONATES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_COMPROMISES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_HOSTS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_OWNS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_THREAT_ACTOR_GROUP,
                new RelationshipInfo(RELATION_PART_OF, REL_NEW),
                new RelationshipInfo(RELATION_COOPERATES_WITH, REL_NEW),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL,
                new RelationshipInfo(RELATION_COOPERATES_WITH, REL_NEW),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_GROUP, ENTITY_HASHED_OBSERVABLE_STIX_FILE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));

        // ==================== THREAT_ACTOR_INDIVIDUAL ====================
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_CHANNEL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_CAMPAIGN,
                new RelationshipInfo(RELATION_PARTICIPATES_IN, REL_NEW));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_IMPERSONATES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_ATTRIBUTED_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_IMPERSONATES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_COMPROMISES, REL_BUILT_IN),
                new RelationshipInfo(RELATION_HOSTS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_OWNS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL,
                new RelationshipInfo(RELATION_COOPERATES_WITH, REL_NEW),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_USES, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL, ENTITY_HASHED_OBSERVABLE_STIX_FILE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));

        // ==================== TOOL ====================
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_IDENTITY_SECTOR,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_EVENT,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_INFRASTRUCTURE,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_IDENTITY_SYSTEM,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_MALWARE,
                new RelationshipInfo(RELATION_DELIVERS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DROPS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_HAS, REL_BUILT_IN),
                new RelationshipInfo(RELATION_TARGETS, REL_BUILT_IN));
        addMappingWithType(ENTITY_TYPE_TOOL, ENTITY_TYPE_TOOL,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== VULNERABILITY ====================
        addMappingWithType(ENTITY_TYPE_VULNERABILITY, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ==================== SCO mappings ====================
        // BANK_ACCOUNT
        addMappingWithType(ENTITY_BANK_ACCOUNT, ENTITY_BANK_ACCOUNT,
                new RelationshipInfo(RELATION_TRANSFERRED_TO, REL_EXTENDED));

        // HOSTNAME
        addMappingWithType(ENTITY_HOSTNAME, ENTITY_HASHED_OBSERVABLE_ARTIFACT,
                new RelationshipInfo(RELATION_DROPS, REL_EXTENDED));
        addMappingWithType(ENTITY_HOSTNAME, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_HOSTNAME, ENTITY_DOMAIN_NAME,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_EXTENDED),
                new RelationshipInfo(RELATION_RESOLVES_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_HOSTNAME, ENTITY_IPV4_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_EXTENDED),
                new RelationshipInfo(RELATION_RESOLVES_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_HOSTNAME, ENTITY_IPV6_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_EXTENDED),
                new RelationshipInfo(RELATION_RESOLVES_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_HOSTNAME, ENTITY_HASHED_OBSERVABLE_STIX_FILE,
                new RelationshipInfo(RELATION_DROPS, REL_EXTENDED));
        addMappingWithType(ENTITY_HOSTNAME, ENTITY_HOSTNAME,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // URL
        addMappingWithType(ENTITY_URL, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_URL, ENTITY_URL,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // TEXT
        addMappingWithType(ENTITY_TEXT, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_TEXT, ENTITY_TEXT,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // ARTIFACT
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_ARTIFACT, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_ARTIFACT, ENTITY_HASHED_OBSERVABLE_ARTIFACT,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_ARTIFACT, ENTITY_IPV4_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_EXTENDED));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_ARTIFACT, ENTITY_IPV6_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_EXTENDED));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_ARTIFACT, ENTITY_DOMAIN_NAME,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_EXTENDED));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_ARTIFACT, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));

        // IPV4_ADDR
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_MAC_ADDR,
                new RelationshipInfo(RELATION_RESOLVES_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_AUTONOMOUS_SYSTEM,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV4_ADDR, ENTITY_IPV4_ADDR,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // IPV6_ADDR
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_TYPE_LOCATION_POSITION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_TYPE_LOCATION_CITY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_TYPE_LOCATION_ADMINISTRATIVE_AREA,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_TYPE_LOCATION_COUNTRY,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_TYPE_LOCATION_REGION,
                new RelationshipInfo(RELATION_LOCATED_AT, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_MAC_ADDR,
                new RelationshipInfo(RELATION_RESOLVES_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_AUTONOMOUS_SYSTEM,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_IPV6_ADDR, ENTITY_IPV6_ADDR,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // DOMAIN_NAME
        addMappingWithType(ENTITY_DOMAIN_NAME, ENTITY_DOMAIN_NAME,
                new RelationshipInfo(RELATION_RESOLVES_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_DOMAIN_NAME, ENTITY_HOSTNAME,
                new RelationshipInfo(RELATION_RESOLVES_TO, REL_BUILT_IN),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_DOMAIN_NAME, ENTITY_IPV4_ADDR,
                new RelationshipInfo(RELATION_RESOLVES_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_DOMAIN_NAME, ENTITY_IPV6_ADDR,
                new RelationshipInfo(RELATION_RESOLVES_TO, REL_BUILT_IN));
        addMappingWithType(ENTITY_DOMAIN_NAME, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_DOMAIN_NAME, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));
        addMappingWithType(ENTITY_DOMAIN_NAME, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_BELONGS_TO, REL_EXTENDED));

        // STIX_FILE
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_STIX_FILE, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_STIX_FILE, ENTITY_TYPE_ATTACK_PATTERN,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_STIX_FILE, ENTITY_HASHED_OBSERVABLE_STIX_FILE,
                new RelationshipInfo(RELATION_DROPS, REL_EXTENDED),
                new RelationshipInfo(RELATION_DOWNLOADS, REL_EXTENDED),
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_STIX_FILE, ENTITY_IPV4_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_EXTENDED));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_STIX_FILE, ENTITY_IPV6_ADDR,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_EXTENDED));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_STIX_FILE, ENTITY_DOMAIN_NAME,
                new RelationshipInfo(RELATION_COMMUNICATES_WITH, REL_EXTENDED));
        addMappingWithType(ENTITY_HASHED_OBSERVABLE_STIX_FILE, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_TARGETS, REL_EXTENDED),
                new RelationshipInfo(RELATION_DEMONSTRATES, REL_EXTENDED));

        // SOFTWARE
        addMappingWithType(ENTITY_SOFTWARE, ENTITY_TYPE_VULNERABILITY,
                new RelationshipInfo(RELATION_HAS, REL_EXTENDED),
                new RelationshipInfo(RELATION_REMEDIATES, REL_EXTENDED));
        addMappingWithType(ENTITY_SOFTWARE, ENTITY_SOFTWARE,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));

        // MEDIA_CONTENT
        addMappingWithType(ENTITY_MEDIA_CONTENT, ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                new RelationshipInfo(RELATION_AUTHORED_BY, REL_EXTENDED));
        addMappingWithType(ENTITY_MEDIA_CONTENT, ENTITY_TYPE_IDENTITY_ORGANIZATION,
                new RelationshipInfo(RELATION_AUTHORED_BY, REL_EXTENDED));
        addMappingWithType(ENTITY_MEDIA_CONTENT, ENTITY_USER_ACCOUNT,
                new RelationshipInfo(RELATION_AUTHORED_BY, REL_EXTENDED));
        addMappingWithType(ENTITY_MEDIA_CONTENT, ENTITY_TYPE_NARRATIVE,
                new RelationshipInfo(RELATION_USES, REL_EXTENDED));
        addMappingWithType(ENTITY_MEDIA_CONTENT, ENTITY_MEDIA_CONTENT,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_EXTENDED));

        // USER_ACCOUNT
        addMappingWithType(ENTITY_USER_ACCOUNT, ENTITY_MEDIA_CONTENT,
                new RelationshipInfo(RELATION_PUBLISHES, REL_NEW));
        addMappingWithType(ENTITY_USER_ACCOUNT, ENTITY_USER_ACCOUNT,
                new RelationshipInfo(RELATION_DERIVED_FROM, REL_BUILT_IN));
    }

    /**
     * 娣诲姞鍏崇郴鏄犲皠锛堝甫绫诲瀷淇℃伅锛?     */
    private static void addMappingWithType(String fromType, String toType, RelationshipInfo... relationships) {
        String key = fromType + "_" + toType;
        STIX_CORE_RELATIONSHIPS_MAPPING.put(key, Arrays.asList(relationships));
    }

    /**
     * 鍘熸柟娉? checkStixCoreRelationshipMapping
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts:checkStixCoreRelationshipMapping
     *
     * 妫€鏌TIX鏍稿績鍏崇郴鏄犲皠鏄惁鏈夋晥
     * 淇璇存槑: 琛ュ厖浜嗗ABSTRACT_STIX_CYBER_OBSERVABLE鎶借薄绫诲瀷鐨勫鐞嗭紝涓庢簮鐮佷繚鎸佷竴鑷?     */
    public static boolean checkStixCoreRelationshipMapping(String fromType, String toType, String relationshipType) {
        // RELATED_TO 鍜?REVOKED_BY 瀵规墍鏈夊疄浣撻兘鍙敤
        if (RELATION_RELATED_TO.equals(relationshipType) || RELATION_REVOKED_BY.equals(relationshipType)) {
            return true;
        }

        
        // 濡傛灉鏍稿績鍏崇郴鐨勭洰鏍囩被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
        // 杩欎簺鍏崇郴鏄疭TIX瑙勮寖鐨勬墿灞?
        if (isStixCyberObservable(toType)) {
            String abstractKey = fromType + "_" + ABSTRACT_STIX_CYBER_OBSERVABLE;
            List<RelationshipInfo> abstractRelationships = STIX_CORE_RELATIONSHIPS_MAPPING.get(abstractKey);
            if (abstractRelationships != null) {
                for (RelationshipInfo info : abstractRelationships) {
                    if (info.name.equals(relationshipType)) {
                        return true;
                    }
                }
            }
        }

        // 濡傛灉鏍稿績鍏崇郴鐨勬簮绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
        if (isStixCyberObservable(fromType)) {
            String abstractKey = ABSTRACT_STIX_CYBER_OBSERVABLE + "_" + toType;
            List<RelationshipInfo> abstractRelationships = STIX_CORE_RELATIONSHIPS_MAPPING.get(abstractKey);
            if (abstractRelationships != null) {
                for (RelationshipInfo info : abstractRelationships) {
                    if (info.name.equals(relationshipType)) {
                        return true;
                    }
                }
            }
        }

        // 妫€鏌ュ叿浣撴槧灏?        
        String key = fromType + "_" + toType;
        List<RelationshipInfo> relationships = STIX_CORE_RELATIONSHIPS_MAPPING.get(key);

        if (relationships != null) {
            for (RelationshipInfo info : relationships) {
                if (info.name.equals(relationshipType)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 鍘熸柟娉? isRelationBuiltin
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts:isRelationBuiltin
     *
     * 妫€鏌ユ槸鍚︿负鍐呯疆鍏崇郴
     * 淇璇存槑:
     * 1. 琛ュ厖浜唅sInternalRelationship鍜宨sStixRefRelationship妫€鏌?     * 2. 琛ュ厖浜嗗ABSTRACT_STIX_CYBER_OBSERVABLE鎶借薄绫诲瀷鐨勫鐞?     * 3. 琛ュ厖浜嗗畾涔夋湭鎵惧埌鏃舵姏鍑哄紓甯哥殑閫昏緫
     */
    public static boolean isRelationBuiltin(StoreRelation instance) {
        if (instance == null || instance.getEntityType() == null) {
            return false;
        }

        String relationshipType = instance.getEntityType();

        // 鍐呴儴鍏崇郴鍜屽紩鐢ㄥ叧绯讳笉鏄唴缃叧绯?        
        if (isInternalRelationship(relationshipType) || isStixRefRelationship(relationshipType)) {
            return false;
        }

        // RELATED_TO 鏄唴缃叧绯?        
        if (RELATION_RELATED_TO.equals(relationshipType)) {
            return true;
        }

        // REVOKED_BY 涓嶆槸鍐呯疆鍏崇郴
        if (RELATION_REVOKED_BY.equals(relationshipType)) {
            return false;
        }

        // 妫€鏌rom鍜宼o鏄惁宸茶В鏋?        
        if (instance.getFrom() == null || instance.getTo() == null) {
            return false;
        }

        String fromType = instance.getFrom().getEntityType();
        String toType = instance.getTo().getEntityType();

        // 鑾峰彇瀹氫箟鍒楄〃
        List<RelationshipInfo> definitions = new ArrayList<>();

        // 妫€鏌ュ叿浣撶被鍨嬫槧灏?
        String key = fromType + "_" + toType;
        List<RelationshipInfo> specificDefinitions = STIX_CORE_RELATIONSHIPS_MAPPING.get(key);
        if (specificDefinitions != null) {
            definitions.addAll(specificDefinitions);
        }

        // 濡傛灉鐩爣绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
            if (isStixCyberObservable(toType)) {
            String abstractKey = fromType + "_" + ABSTRACT_STIX_CYBER_OBSERVABLE;
            List<RelationshipInfo> abstractDefinitions = STIX_CORE_RELATIONSHIPS_MAPPING.get(abstractKey);
            if (abstractDefinitions != null) {
                definitions.addAll(abstractDefinitions);
            }
        }

        // 濡傛灉婧愮被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
            if (isStixCyberObservable(fromType)) {
            String abstractKey = ABSTRACT_STIX_CYBER_OBSERVABLE + "_" + toType;
            List<RelationshipInfo> abstractDefinitions = STIX_CORE_RELATIONSHIPS_MAPPING.get(abstractKey);
            if (abstractDefinitions != null) {
                definitions.addAll(abstractDefinitions);
            }
        }

        // 濡傛灉鎵惧埌瀹氫箟锛屾鏌ュ叧绯荤被鍨嬫槸鍚︿负鍐呯疆
        if (!definitions.isEmpty()) {
            for (RelationshipInfo rel : definitions) {
                if (rel.name.equals(relationshipType)) {
                    return REL_BUILT_IN.equals(rel.type);
                }
            }
        }

        // 瀹氫箟鏈壘鍒帮紝鎶涘嚭寮傚父
        // 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts:1307-1309
        throw new UnsupportedOperationException(
            String.format("[STIX] Missing relation definition: from=%s, type=%s, to=%s", fromType, relationshipType, toType)
        );
    }

    /**
     * 妫€鏌ユ槸鍚︿负鍐呴儴鍏崇郴
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/schema/internalRelationship.ts:30-31
     *
     * @param type 鍏崇郴绫诲瀷
     * @return 鏄惁涓哄唴閮ㄥ叧绯?     */
    private static boolean isInternalRelationship(String type) {
        if (type == null) {
            return false;
        }
        // 妫€鏌ユ槸鍚︿负鍐呴儴鍏崇郴绫诲瀷鎴栨娊璞″唴閮ㄥ叧绯荤被鍨?
        return type.equals(StixConstants.ABSTRACT_INTERNAL_RELATIONSHIP) ||
               type.equals(StixConstants.RELATION_MIGRATES) ||
               type.equals(StixConstants.RELATION_MEMBER_OF) ||
               type.equals(StixConstants.RELATION_PARTICIPATE_TO) ||
               type.equals(StixConstants.RELATION_ALLOWED_BY) ||
               type.equals(StixConstants.RELATION_HAS_ROLE) ||
               type.equals(StixConstants.RELATION_HAS_CAPABILITY) ||
               type.equals(StixConstants.RELATION_HAS_CAPABILITY_IN_DRAFT) ||
               type.equals(StixConstants.RELATION_ACCESSES_TO) ||
               type.equals(StixConstants.RELATION_IN_PIR);
    }

    /**
     * 妫€鏌ユ槸鍚︿负STIX寮曠敤鍏崇郴
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/schema/stixRefRelationship.ts:961
     *
     * @param type 鍏崇郴绫诲瀷
     * @return 鏄惁涓篠TIX寮曠敤鍏崇郴
     */
    private static boolean isStixRefRelationship(String type) {
        if (type == null) {
            return false;
        }
        // 妫€鏌ユ槸鍚︿负寮曠敤鍏崇郴绫诲瀷鎴栨娊璞″紩鐢ㄥ叧绯荤被鍨?
        // 寮曠敤鍏崇郴鍖呮嫭: object-marking, granted-to, created-by, external-references, kill-chain-phases, labels绛?
        return type.equals(StixConstants.ABSTRACT_STIX_REF_RELATIONSHIP) ||
               type.equals("object-marking") ||
               type.equals("granted-to") ||
               type.equals("created-by") ||
               type.equals("external-references") ||
               type.equals("kill-chain-phases") ||
               type.equals("labels") ||
               type.equals("objects") ||
               type.startsWith("obs_") ||  // 鍙娴嬪璞″紩鐢ㄥ叧绯诲墠缂€
               type.startsWith("input-");   // 杈撳叆寮曠敤鍏崇郴鍓嶇紑
    }

    /**
     * 鍘熸柟娉? isStixCyberObservable
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts:isStixCyberObservable
     *
     * 妫€鏌ユ槸鍚︿负STIX Cyber Observable
     */
    public static boolean isStixCyberObservable(String entityType) {
        if (entityType == null) {
            return false;
        }

        Set<String> scoTypes = new HashSet<>(Arrays.asList(
                ENTITY_IPV4_ADDR, ENTITY_IPV6_ADDR, ENTITY_MAC_ADDR,
                ENTITY_DOMAIN_NAME, ENTITY_HASHED_OBSERVABLE_STIX_FILE,
                ENTITY_HASHED_OBSERVABLE_ARTIFACT, ENTITY_URL, ENTITY_AUTONOMOUS_SYSTEM,
                ENTITY_SOFTWARE, ENTITY_USER_ACCOUNT, ENTITY_MEDIA_CONTENT,
                ENTITY_HOSTNAME, ENTITY_TEXT, ENTITY_BANK_ACCOUNT
        ));

        return scoTypes.contains(entityType);
    }

    /**
     * 鍘熸柟娉? getValidRelationships
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts:getValidRelationships
     *
     * 鑾峰彇鎵€鏈夋湁鏁堢殑鍏崇郴绫诲瀷
     */
    public static List<String> getValidRelationships(String fromType, String toType) {
        String key = fromType + "_" + toType;
        List<RelationshipInfo> relationships = STIX_CORE_RELATIONSHIPS_MAPPING.get(key);

        if (relationships == null) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        for (RelationshipInfo info : relationships) {
            result.add(info.name);
        }
        return result;
    }

    /**
     * 鑾峰彇鍏崇郴绫诲瀷鍒嗙被
     *
     * @param fromType 婧愬疄浣撶被鍨?     * @param toType 鐩爣瀹炰綋绫诲瀷
     * @param relationshipType 鍏崇郴绫诲瀷
     * @return 鍏崇郴鍒嗙被(builtin/new/extended)锛屽鏋滀笉瀛樺湪杩斿洖null
     */
    public static String getRelationshipTypeCategory(String fromType, String toType, String relationshipType) {
        String key = fromType + "_" + toType;
        List<RelationshipInfo> relationships = STIX_CORE_RELATIONSHIPS_MAPPING.get(key);

        if (relationships != null) {
            for (RelationshipInfo info : relationships) {
                if (info.name.equals(relationshipType)) {
                    return info.type;
                }
            }
        }
        return null;
    }
}

