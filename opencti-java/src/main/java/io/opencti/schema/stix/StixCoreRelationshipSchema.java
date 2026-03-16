package io.opencti.schema.stix;

import io.opencti.schema.general.SchemaGeneral;

import java.util.*;

/**
 * STIX 核心关系 Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/stixCoreRelationship.ts
 *
 * 该文件定义了STIX核心关系的61种关系类型常量，包括标准STIX关系、OpenCTI扩展关系和MITRE扩展关系，
 * 以及关系类型判断方法。这是构建完整STIX关系体系的基础模块。
 */
public final class StixCoreRelationshipSchema {

    // 私有构造函数，防止实例化
    private StixCoreRelationshipSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== 标准STIX关系 ====================
    // region Standard STIX core

    /** 原代码: export const RELATION_DELIVERS = 'delivers'; */
    public static final String RELATION_DELIVERS = "delivers";

    /** 原代码: export const RELATION_TARGETS = 'targets'; */
    public static final String RELATION_TARGETS = "targets";

    /** 原代码: export const RELATION_USES = 'uses'; */
    public static final String RELATION_USES = "uses";

    /** 原代码: export const RELATION_ATTRIBUTED_TO = 'attributed-to'; */
    public static final String RELATION_ATTRIBUTED_TO = "attributed-to";

    /** 原代码: export const RELATION_COMPROMISES = 'compromises'; */
    public static final String RELATION_COMPROMISES = "compromises";

    /** 原代码: export const RELATION_ORIGINATES_FROM = 'originates-from'; */
    public static final String RELATION_ORIGINATES_FROM = "originates-from";

    /** 原代码: export const RELATION_INVESTIGATES = 'investigates'; */
    public static final String RELATION_INVESTIGATES = "investigates";

    /** 原代码: export const RELATION_MITIGATES = 'mitigates'; */
    public static final String RELATION_MITIGATES = "mitigates";

    /** 原代码: export const RELATION_LOCATED_AT = 'located-at'; */
    public static final String RELATION_LOCATED_AT = "located-at";

    /** 原代码: export const RELATION_INDICATES = 'indicates'; */
    public static final String RELATION_INDICATES = "indicates";

    /** 原代码: export const RELATION_BASED_ON = 'based-on'; */
    public static final String RELATION_BASED_ON = "based-on";

    /** 原代码: export const RELATION_COMMUNICATES_WITH = 'communicates-with'; */
    public static final String RELATION_COMMUNICATES_WITH = "communicates-with";

    /** 原代码: export const RELATION_CONSISTS_OF = 'consists-of'; */
    public static final String RELATION_CONSISTS_OF = "consists-of";

    /** 原代码: export const RELATION_CONTROLS = 'controls'; */
    public static final String RELATION_CONTROLS = "controls";

    /** 原代码: export const RELATION_HAS = 'has'; */
    public static final String RELATION_HAS = "has";

    /** 原代码: export const RELATION_HOSTS = 'hosts'; */
    public static final String RELATION_HOSTS = "hosts";

    /** 原代码: export const RELATION_OWNS = 'owns'; */
    public static final String RELATION_OWNS = "owns";

    /** 原代码: export const RELATION_AUTHORED_BY = 'authored-by'; */
    public static final String RELATION_AUTHORED_BY = "authored-by";

    /** 原代码: export const RELATION_BEACONS_TO = 'beacons-to'; */
    public static final String RELATION_BEACONS_TO = "beacons-to";

    /** 原代码: export const RELATION_EXFILTRATES_TO = 'exfiltrates-to'; */
    public static final String RELATION_EXFILTRATES_TO = "exfiltrates-to";

    /** 原代码: export const RELATION_DOWNLOADS = 'downloads'; */
    public static final String RELATION_DOWNLOADS = "downloads";

    /** 原代码: export const RELATION_DROPS = 'drops'; */
    public static final String RELATION_DROPS = "drops";

    /** 原代码: export const RELATION_EXPLOITS = 'exploits'; */
    public static final String RELATION_EXPLOITS = "exploits";

    /** 原代码: export const RELATION_VARIANT_OF = 'variant-of'; */
    public static final String RELATION_VARIANT_OF = "variant-of";

    /** 原代码: export const RELATION_CHARACTERIZES = 'characterizes'; */
    public static final String RELATION_CHARACTERIZES = "characterizes";

    /** 原代码: export const RELATION_ANALYSIS_OF = 'analysis-of'; */
    public static final String RELATION_ANALYSIS_OF = "analysis-of";

    /** 原代码: export const RELATION_STATIC_ANALYSIS_OF = 'static-analysis-of'; */
    public static final String RELATION_STATIC_ANALYSIS_OF = "static-analysis-of";

    /** 原代码: export const RELATION_DYNAMIC_ANALYSIS_OF = 'dynamic-analysis-of'; */
    public static final String RELATION_DYNAMIC_ANALYSIS_OF = "dynamic-analysis-of";

    /** 原代码: export const RELATION_IMPERSONATES = 'impersonates'; */
    public static final String RELATION_IMPERSONATES = "impersonates";

    /** 原代码: export const RELATION_REMEDIATES = 'remediates'; */
    public static final String RELATION_REMEDIATES = "remediates";

    /** 原代码: export const RELATION_RELATED_TO = 'related-to'; */
    public static final String RELATION_RELATED_TO = "related-to";

    /** 原代码: export const RELATION_DERIVED_FROM = 'derived-from'; */
    public static final String RELATION_DERIVED_FROM = "derived-from";

    /** 原代码: export const RELATION_DUPLICATE_OF = 'duplicate-of'; */
    public static final String RELATION_DUPLICATE_OF = "duplicate-of";

    /** 原代码: export const RELATION_BELONGS_TO = 'belongs-to'; */
    public static final String RELATION_BELONGS_TO = "belongs-to";

    /** 原代码: export const RELATION_RESOLVES_TO = 'resolves-to'; */
    public static final String RELATION_RESOLVES_TO = "resolves-to";

    /** 原代码: export const RELATION_TECHNOLOGY = 'technology'; */
    public static final String RELATION_TECHNOLOGY = "technology";

    /** 原代码: export const RELATION_TECHNOLOGY_TO = 'technology-to'; */
    public static final String RELATION_TECHNOLOGY_TO = "technology-to";

    /** 原代码: export const RELATION_TECHNOLOGY_FROM = 'technology-from'; */
    public static final String RELATION_TECHNOLOGY_FROM = "technology-from";

    /** 原代码: export const RELATION_TRANSFERRED_TO = 'transferred-to'; */
    public static final String RELATION_TRANSFERRED_TO = "transferred-to";

    /** 原代码: export const RELATION_DEMONSTRATES = 'demonstrates'; */
    public static final String RELATION_DEMONSTRATES = "demonstrates";

    // endregion

    // ==================== OpenCTI扩展关系 ====================
    // region Extended relationships

    /** 原代码: export const RELATION_PART_OF = 'part-of'; // Extension (OpenCTI) */
    public static final String RELATION_PART_OF = "part-of";

    /** 原代码: export const RELATION_COOPERATES_WITH = 'cooperates-with'; // Extension (OpenCTI) */
    public static final String RELATION_COOPERATES_WITH = "cooperates-with";

    /** 原代码: export const RELATION_PARTICIPATES_IN = 'participates-in'; // Extension (OpenCTI) */
    public static final String RELATION_PARTICIPATES_IN = "participates-in";

    /** 原代码: export const RELATION_PUBLISHES = 'publishes'; // Extension (OpenCTI) */
    public static final String RELATION_PUBLISHES = "publishes";

    /** 原代码: export const RELATION_AMPLIFIES = 'amplifies'; // Extension (OpenCTI) */
    public static final String RELATION_AMPLIFIES = "amplifies";

    /** 原代码: export const RELATION_SUBNARRATIVE_OF = 'subnarrative-of'; // Extension (OpenCTI) */
    public static final String RELATION_SUBNARRATIVE_OF = "subnarrative-of";

    /** 原代码: export const RELATION_EMPLOYED_BY = 'employed-by'; // Extension (OpenCTI) */
    public static final String RELATION_EMPLOYED_BY = "employed-by";

    /** 原代码: export const RELATION_RESIDES_IN = 'resides-in'; // Extension (OpenCTI) */
    public static final String RELATION_RESIDES_IN = "resides-in";

    /** 原代码: export const RELATION_CITIZEN_OF = 'citizen-of'; // Extension (OpenCTI) */
    public static final String RELATION_CITIZEN_OF = "citizen-of";

    /** 原代码: export const RELATION_NATIONAL_OF = 'national-of'; // Extension (OpenCTI) */
    public static final String RELATION_NATIONAL_OF = "national-of";

    /** 原代码: export const RELATION_KNOWN_AS = 'known-as'; // Extension (OpenCTI) */
    public static final String RELATION_KNOWN_AS = "known-as";

    /** 原代码: export const RELATION_REPORTS_TO = 'reports-to'; // Extension (OpenCTI) */
    public static final String RELATION_REPORTS_TO = "reports-to";

    /** 原代码: export const RELATION_SUPPORTS = 'supports'; // Extension (OpenCTI) */
    public static final String RELATION_SUPPORTS = "supports";

    /** 原代码: export const RELATION_SHOULD_COVER = 'should-cover'; // Extension (OpenCTI) */
    public static final String RELATION_SHOULD_COVER = "should-cover";

    /** 原代码: export const RELATION_HAS_COVERED = 'has-covered'; // Extension (OpenCTI) */
    public static final String RELATION_HAS_COVERED = "has-covered";

    // endregion

    // ==================== MITRE扩展关系 ====================
    // region MITRE extensions

    /** 原代码: export const RELATION_SUBTECHNIQUE_OF = 'subtechnique-of'; // Extension (MITRE) */
    public static final String RELATION_SUBTECHNIQUE_OF = "subtechnique-of";

    /** 原代码: export const RELATION_REVOKED_BY = 'revoked-by'; // Extension (MITRE) */
    public static final String RELATION_REVOKED_BY = "revoked-by";

    /** 原代码: export const RELATION_DETECTS = 'detects'; // Extension (MITRE) */
    public static final String RELATION_DETECTS = "detects";

    // endregion

    // ==================== STIX核心关系类型列表 ====================
    // 原代码: export const STIX_CORE_RELATIONSHIPS = [...]

    /**
     * STIX核心关系类型完整列表，包含58种关系类型
     * 原代码: export const STIX_CORE_RELATIONSHIPS = [...]
     */
    public static final List<String> STIX_CORE_RELATIONSHIPS = Collections.unmodifiableList(Arrays.asList(
            RELATION_DELIVERS,
            RELATION_TARGETS,
            RELATION_USES,
            RELATION_BEACONS_TO,
            RELATION_ATTRIBUTED_TO,
            RELATION_EXFILTRATES_TO,
            RELATION_COMPROMISES,
            RELATION_DOWNLOADS,
            RELATION_EXPLOITS,
            RELATION_CHARACTERIZES,
            RELATION_ANALYSIS_OF,
            RELATION_STATIC_ANALYSIS_OF,
            RELATION_DYNAMIC_ANALYSIS_OF,
            RELATION_DERIVED_FROM,
            RELATION_DUPLICATE_OF,
            RELATION_ORIGINATES_FROM,
            RELATION_INVESTIGATES,
            RELATION_LOCATED_AT,
            RELATION_BASED_ON,
            RELATION_HOSTS,
            RELATION_OWNS,
            RELATION_AUTHORED_BY,
            RELATION_COMMUNICATES_WITH,
            RELATION_MITIGATES,
            RELATION_CONTROLS,
            RELATION_HAS,
            RELATION_CONSISTS_OF,
            RELATION_INDICATES,
            RELATION_VARIANT_OF,
            RELATION_IMPERSONATES,
            RELATION_REMEDIATES,
            RELATION_RELATED_TO,
            RELATION_DROPS,
            RELATION_PART_OF,
            RELATION_COOPERATES_WITH,
            RELATION_PARTICIPATES_IN,
            RELATION_SUBTECHNIQUE_OF,
            RELATION_REVOKED_BY,
            RELATION_BELONGS_TO,
            RELATION_RESOLVES_TO,
            RELATION_TECHNOLOGY,
            RELATION_TECHNOLOGY_TO,
            RELATION_TECHNOLOGY_FROM,
            RELATION_TRANSFERRED_TO,
            RELATION_DEMONSTRATES,
            RELATION_DETECTS,
            RELATION_PUBLISHES,
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
            RELATION_HAS_COVERED
    ));

    // ==================== 关系类型判断方法 ====================

    /**
     * 判断是否为STIX核心关系
     * 原代码: export const isStixCoreRelationship = (type: string): boolean => schemaTypesDefinition.isTypeIncludedIn(type, ABSTRACT_STIX_CORE_RELATIONSHIP)
     *   || type === ABSTRACT_STIX_CORE_RELATIONSHIP;
     *
     * @param type 类型
     * @return 如果是STIX核心关系返回 true，否则返回 false
     */
    public static boolean isStixCoreRelationship(String type) {
        return type != null && (STIX_CORE_RELATIONSHIPS.contains(type)
                || type.equals(SchemaGeneral.ABSTRACT_STIX_CORE_RELATIONSHIP));
    }

    // ==================== STIX核心关系选项 ====================
    // 原代码: export const stixCoreRelationshipOptions = { StixCoreRelationshipsOrdering: {} };

    /**
     * STIX核心关系选项配置
     * 原代码: export const stixCoreRelationshipOptions = { StixCoreRelationshipsOrdering: {} };
     */
    public static final Map<String, Object> STIX_CORE_RELATIONSHIP_OPTIONS;

    static {
        Map<String, Object> options = new HashMap<>();
        // StixCoreRelationshipsOrdering 当前为空对象
        options.put("StixCoreRelationshipsOrdering", Collections.emptyMap());
        STIX_CORE_RELATIONSHIP_OPTIONS = Collections.unmodifiableMap(options);
    }
}
