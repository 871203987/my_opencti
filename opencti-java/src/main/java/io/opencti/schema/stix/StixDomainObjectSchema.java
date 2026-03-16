


package io.opencti.schema.stix;

import io.opencti.schema.SchemaTypesDefinition;
import io.opencti.schema.general.SchemaGeneral;
import io.opencti.schema.internal.InternalObjectSchema;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * STIX 域对象 (SDO) Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/stixDomainObject.ts
 *
 * 该文件定义了STIX域对象(SDO)的类型常量（约20+种实体类型），以及SDO类型判断方法、
 * 容器对象判断方法、位置对象判断方法等。这是构建完整STIX实体体系的核心模块。
 */
public final class StixDomainObjectSchema {

    private StixDomainObjectSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== SDO属性常量 ====================
    // 原代码: export const ATTRIBUTE_NAME = 'name';
    public static final String ATTRIBUTE_NAME = "name";

    // 原代码: export const ATTRIBUTE_ABSTRACT = 'attribute_abstract';
    public static final String ATTRIBUTE_ABSTRACT = "attribute_abstract";

    // 原代码: export const ATTRIBUTE_EXPLANATION = 'explanation';
    public static final String ATTRIBUTE_EXPLANATION = "explanation";

    // 原代码: export const ATTRIBUTE_DESCRIPTION = 'description';
    public static final String ATTRIBUTE_DESCRIPTION = "description";

    // 原代码: export const ATTRIBUTE_DESCRIPTION_OPENCTI = 'x_opencti_description';
    public static final String ATTRIBUTE_DESCRIPTION_OPENCTI = "x_opencti_description";

    // 原代码: export const ATTRIBUTE_ALIASES = 'aliases';
    public static final String ATTRIBUTE_ALIASES = "aliases";

    // 原代码: export const ATTRIBUTE_ALIASES_OPENCTI = 'x_opencti_aliases';
    public static final String ATTRIBUTE_ALIASES_OPENCTI = "x_opencti_aliases";

    // 原代码: export const ATTRIBUTE_ADDITIONAL_NAMES = 'x_opencti_additional_names';
    public static final String ATTRIBUTE_ADDITIONAL_NAMES = "x_opencti_additional_names";

    // ==================== 从其他模块导入的实体类型常量 ====================
    // 这些常量在源码中从各个模块导入，这里直接定义

    // 原代码: 从case-types导入
    public static final String ENTITY_TYPE_CONTAINER_CASE = "Case";
    public static final String ENTITY_TYPE_CONTAINER_CASE_INCIDENT = "Case-Incident";
    public static final String ENTITY_TYPE_CONTAINER_CASE_RFI = "Case-Rfi";
    public static final String ENTITY_TYPE_CONTAINER_CASE_RFT = "Case-Rft";

    // 原代码: 从task-types导入
    public static final String ENTITY_TYPE_CONTAINER_TASK = "Task";

    // 原代码: 从grouping-types导入
    public static final String ENTITY_TYPE_CONTAINER_GROUPING = "Grouping";

    // 原代码: 从feedback-types导入
    public static final String ENTITY_TYPE_CONTAINER_FEEDBACK = "Feedback";

    // 原代码: 从threatActorIndividual-types导入
    public static final String ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL = "Threat-Actor-Individual";

    // 原代码: 从organization-types导入
    public static final String ENTITY_TYPE_IDENTITY_ORGANIZATION = "Organization";

    // 原代码: 从securityPlatform-types导入
    public static final String ENTITY_TYPE_IDENTITY_SECURITY_PLATFORM = "Security-Platform";

    // 原代码: 从deleteOperation-types导入
    public static final String ENTITY_TYPE_DELETE_OPERATION = "DeleteOperation";

    // ==================== SDO实体类型常量 ====================
    // 原代码: export const ENTITY_TYPE_ATTACK_PATTERN = 'Attack-Pattern';
    public static final String ENTITY_TYPE_ATTACK_PATTERN = "Attack-Pattern";

    // 原代码: export const ENTITY_TYPE_CAMPAIGN = 'Campaign';
    public static final String ENTITY_TYPE_CAMPAIGN = "Campaign";

    // 原代码: export const ENTITY_TYPE_CONTAINER_NOTE = 'Note';
    public static final String ENTITY_TYPE_CONTAINER_NOTE = "Note";

    // 原代码: export const ENTITY_TYPE_CONTAINER_OBSERVED_DATA = 'Observed-Data';
    public static final String ENTITY_TYPE_CONTAINER_OBSERVED_DATA = "Observed-Data";

    // 原代码: export const ENTITY_TYPE_CONTAINER_OPINION = 'Opinion';
    public static final String ENTITY_TYPE_CONTAINER_OPINION = "Opinion";

    // 原代码: export const ENTITY_TYPE_CONTAINER_REPORT = 'Report';
    public static final String ENTITY_TYPE_CONTAINER_REPORT = "Report";

    // 原代码: export const ENTITY_TYPE_COURSE_OF_ACTION = 'Course-Of-Action';
    public static final String ENTITY_TYPE_COURSE_OF_ACTION = "Course-Of-Action";

    // 原代码: export const ENTITY_TYPE_IDENTITY_INDIVIDUAL = 'Individual';
    public static final String ENTITY_TYPE_IDENTITY_INDIVIDUAL = "Individual";

    // 原代码: export const ENTITY_TYPE_IDENTITY_SECTOR = 'Sector';
    public static final String ENTITY_TYPE_IDENTITY_SECTOR = "Sector";

    // 原代码: export const ENTITY_TYPE_IDENTITY_SYSTEM = 'System';
    public static final String ENTITY_TYPE_IDENTITY_SYSTEM = "System";

    // 原代码: export const ENTITY_TYPE_INFRASTRUCTURE = 'Infrastructure';
    public static final String ENTITY_TYPE_INFRASTRUCTURE = "Infrastructure";

    // 原代码: export const ENTITY_TYPE_INTRUSION_SET = 'Intrusion-Set';
    public static final String ENTITY_TYPE_INTRUSION_SET = "Intrusion-Set";

    // 原代码: export const ENTITY_TYPE_LOCATION_CITY = 'City';
    public static final String ENTITY_TYPE_LOCATION_CITY = "City";

    // 原代码: export const ENTITY_TYPE_LOCATION_COUNTRY = 'Country';
    public static final String ENTITY_TYPE_LOCATION_COUNTRY = "Country";

    // 原代码: export const ENTITY_TYPE_LOCATION_REGION = 'Region';
    public static final String ENTITY_TYPE_LOCATION_REGION = "Region";

    // 原代码: export const ENTITY_TYPE_LOCATION_POSITION = 'Position';
    public static final String ENTITY_TYPE_LOCATION_POSITION = "Position";

    // 原代码: export const ENTITY_TYPE_MALWARE = 'Malware';
    public static final String ENTITY_TYPE_MALWARE = "Malware";

    // 原代码: export const ENTITY_TYPE_THREAT_ACTOR_GROUP = 'Threat-Actor-Group';
    public static final String ENTITY_TYPE_THREAT_ACTOR_GROUP = "Threat-Actor-Group";

    // 原代码: export const ENTITY_TYPE_TOOL = 'Tool';
    public static final String ENTITY_TYPE_TOOL = "Tool";

    // 原代码: export const ENTITY_TYPE_VULNERABILITY = 'Vulnerability';
    public static final String ENTITY_TYPE_VULNERABILITY = "Vulnerability";

    // 原代码: export const ENTITY_TYPE_INCIDENT = 'Incident';
    public static final String ENTITY_TYPE_INCIDENT = "Incident";

    // 原代码: export const ENTITY_TYPE_DATA_COMPONENT = 'Data-Component';
    public static final String ENTITY_TYPE_DATA_COMPONENT = "Data-Component";

    // 原代码: export const ENTITY_TYPE_DATA_SOURCE = 'Data-Source';
    public static final String ENTITY_TYPE_DATA_SOURCE = "Data-Source";

    // 原代码: export const ENTITY_TYPE_RESOLVED_FILTERS = 'Resolved-Filters';
    public static final String ENTITY_TYPE_RESOLVED_FILTERS = "Resolved-Filters";

    // ==================== SDO分类列表 ====================
    // 原代码: export const STIX_DOMAIN_OBJECT_CONTAINER_CASES = [...]
    public static final Set<String> STIX_DOMAIN_OBJECT_CONTAINER_CASES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ENTITY_TYPE_CONTAINER_CASE_INCIDENT,
            ENTITY_TYPE_CONTAINER_CASE_RFI,
            ENTITY_TYPE_CONTAINER_CASE_RFT
    )));

    // 原代码: const STIX_DOMAIN_OBJECT_CONTAINERS = [...]
    public static final Set<String> STIX_DOMAIN_OBJECT_CONTAINERS;

    // 原代码: const STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS = [...]
    public static final Set<String> STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS;

    // 原代码: const STIX_DOMAIN_OBJECT_IDENTITIES = [...]
    public static final Set<String> STIX_DOMAIN_OBJECT_IDENTITIES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ENTITY_TYPE_IDENTITY_INDIVIDUAL,
            ENTITY_TYPE_IDENTITY_SECTOR,
            ENTITY_TYPE_IDENTITY_SYSTEM
    )));

    // 原代码: const STIX_DOMAIN_OBJECT_LOCATIONS = [...]
    public static final Set<String> STIX_DOMAIN_OBJECT_LOCATIONS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ENTITY_TYPE_LOCATION_CITY,
            ENTITY_TYPE_LOCATION_COUNTRY,
            ENTITY_TYPE_LOCATION_REGION,
            ENTITY_TYPE_LOCATION_POSITION
    )));

    // 原代码: const STIX_DOMAIN_OBJECT_THREAT_ACTORS = [...]
    public static final Set<String> STIX_DOMAIN_OBJECT_THREAT_ACTORS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ENTITY_TYPE_THREAT_ACTOR_GROUP,
            ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL
    )));

    // 原代码: export const STIX_DOMAIN_OBJECTS = [...]
    public static final Set<String> STIX_DOMAIN_OBJECTS;

    // 原代码: const STIX_DOMAIN_OBJECT_ALIASED = [...]
    public static final Set<String> STIX_DOMAIN_OBJECT_ALIASED = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ENTITY_TYPE_COURSE_OF_ACTION,
            ENTITY_TYPE_ATTACK_PATTERN,
            ENTITY_TYPE_CAMPAIGN,
            ENTITY_TYPE_INFRASTRUCTURE,
            ENTITY_TYPE_INTRUSION_SET,
            ENTITY_TYPE_MALWARE,
            ENTITY_TYPE_THREAT_ACTOR_GROUP,
            ENTITY_TYPE_TOOL,
            ENTITY_TYPE_INCIDENT,
            ENTITY_TYPE_VULNERABILITY
    )));

    // ==================== 组织限制列表 ====================
    // 原代码: export const STIX_ORGANIZATIONS_UNRESTRICTED = [...]
    public static final Set<String> STIX_ORGANIZATIONS_UNRESTRICTED = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            SchemaGeneral.ABSTRACT_INTERNAL_OBJECT,
            SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP,
            SchemaGeneral.ABSTRACT_STIX_META_OBJECT,
            SchemaGeneral.ABSTRACT_STIX_REF_RELATIONSHIP,
            ENTITY_TYPE_IDENTITY_ORGANIZATION,
            ENTITY_TYPE_IDENTITY_SECTOR,
            SchemaGeneral.ENTITY_TYPE_LOCATION,
            InternalObjectSchema.ENTITY_TYPE_WORK,
            InternalObjectSchema.ENTITY_TYPE_TAXII_COLLECTION,
            InternalObjectSchema.ENTITY_TYPE_INTERNAL_FILE
    )));

    // 原代码: export const STIX_ORGANIZATIONS_RESTRICTED = [...]
    public static final Set<String> STIX_ORGANIZATIONS_RESTRICTED = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ENTITY_TYPE_DELETE_OPERATION
    )));

    // ==================== 静态代码块初始化列表 ====================
    static {
        // 初始化STIX_DOMAIN_OBJECT_CONTAINERS
        Set<String> containers = new HashSet<>(STIX_DOMAIN_OBJECT_CONTAINER_CASES);
        containers.add(ENTITY_TYPE_CONTAINER_NOTE);
        containers.add(ENTITY_TYPE_CONTAINER_OBSERVED_DATA);
        containers.add(ENTITY_TYPE_CONTAINER_OPINION);
        containers.add(ENTITY_TYPE_CONTAINER_REPORT);
        containers.add(ENTITY_TYPE_CONTAINER_GROUPING);
        containers.add(ENTITY_TYPE_CONTAINER_FEEDBACK);
        containers.add(ENTITY_TYPE_CONTAINER_TASK);
        STIX_DOMAIN_OBJECT_CONTAINERS = Collections.unmodifiableSet(containers);

        // 初始化STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS
        Set<String> shareableContainers = new HashSet<>(Arrays.asList(
                ENTITY_TYPE_CONTAINER_OBSERVED_DATA,
                ENTITY_TYPE_CONTAINER_GROUPING,
                ENTITY_TYPE_CONTAINER_REPORT
        ));
        shareableContainers.addAll(STIX_DOMAIN_OBJECT_CONTAINER_CASES);
        STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS = Collections.unmodifiableSet(shareableContainers);

        // 初始化STIX_DOMAIN_OBJECTS
        Set<String> domainObjects = new HashSet<>(Arrays.asList(
                ENTITY_TYPE_ATTACK_PATTERN,
                ENTITY_TYPE_CAMPAIGN,
                ENTITY_TYPE_CONTAINER_NOTE,
                ENTITY_TYPE_CONTAINER_OBSERVED_DATA,
                ENTITY_TYPE_CONTAINER_OPINION,
                ENTITY_TYPE_CONTAINER_REPORT,
                ENTITY_TYPE_COURSE_OF_ACTION,
                ENTITY_TYPE_IDENTITY_INDIVIDUAL,
                ENTITY_TYPE_IDENTITY_SECTOR,
                ENTITY_TYPE_IDENTITY_SYSTEM,
                ENTITY_TYPE_INFRASTRUCTURE,
                ENTITY_TYPE_INTRUSION_SET,
                ENTITY_TYPE_LOCATION_CITY,
                ENTITY_TYPE_LOCATION_COUNTRY,
                ENTITY_TYPE_LOCATION_REGION,
                ENTITY_TYPE_LOCATION_POSITION,
                ENTITY_TYPE_MALWARE,
                ENTITY_TYPE_THREAT_ACTOR_GROUP,
                ENTITY_TYPE_TOOL,
                ENTITY_TYPE_VULNERABILITY,
                ENTITY_TYPE_INCIDENT,
                ENTITY_TYPE_DATA_COMPONENT,
                ENTITY_TYPE_DATA_SOURCE
        ));
        STIX_DOMAIN_OBJECTS = Collections.unmodifiableSet(domainObjects);

        // 注册类型到SchemaTypesDefinition
        SchemaTypesDefinition.register(SchemaGeneral.ENTITY_TYPE_CONTAINER, STIX_DOMAIN_OBJECT_CONTAINERS);
        SchemaTypesDefinition.register(SchemaGeneral.ENTITY_TYPE_IDENTITY, STIX_DOMAIN_OBJECT_IDENTITIES);
        SchemaTypesDefinition.register(SchemaGeneral.ENTITY_TYPE_LOCATION, STIX_DOMAIN_OBJECT_LOCATIONS);
        SchemaTypesDefinition.register(SchemaGeneral.ENTITY_TYPE_THREAT_ACTOR, STIX_DOMAIN_OBJECT_THREAT_ACTORS);
        SchemaTypesDefinition.register(SchemaGeneral.ABSTRACT_STIX_DOMAIN_OBJECT, STIX_DOMAIN_OBJECTS);
    }

    // ==================== SDO类型判断方法 ====================

    /**
     * 判断是否为容器
     * 原代码: export const isStixDomainObjectContainer = (type: string): boolean =>
     *   schemaTypesDefinition.isTypeIncludedIn(type, ENTITY_TYPE_CONTAINER) || type === ENTITY_TYPE_CONTAINER;
     *
     * @param type 类型
     * @return 如果是容器返回 true，否则返回 false
     */
    public static boolean isStixDomainObjectContainer(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ENTITY_TYPE_CONTAINER)
                || type.equals(SchemaGeneral.ENTITY_TYPE_CONTAINER);
    }

    /**
     * 判断是否为可共享容器
     * 原代码: export const isStixDomainObjectShareableContainer = (type: string): boolean => {
     *   return STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS.includes(type);
     * };
     *
     * @param type 类型
     * @return 如果是可共享容器返回 true，否则返回 false
     */
    public static boolean isStixDomainObjectShareableContainer(String type) {
        return type != null && STIX_DOMAIN_OBJECT_SHAREABLE_CONTAINERS.contains(type);
    }

    /**
     * 判断是否为身份
     * 原代码: export const isStixDomainObjectIdentity = (type: string): boolean => {
     *   return schemaTypesDefinition.isTypeIncludedIn(type, ENTITY_TYPE_IDENTITY) || type === ENTITY_TYPE_IDENTITY;
     * };
     *
     * @param type 类型
     * @return 如果是身份返回 true，否则返回 false
     */
    public static boolean isStixDomainObjectIdentity(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ENTITY_TYPE_IDENTITY)
                || type.equals(SchemaGeneral.ENTITY_TYPE_IDENTITY);
    }

    /**
     * 判断是否为位置
     * 原代码: export const isStixDomainObjectLocation = (type: string): boolean =>
     *   schemaTypesDefinition.isTypeIncludedIn(type, ENTITY_TYPE_LOCATION) || type === ENTITY_TYPE_LOCATION;
     *
     * @param type 类型
     * @return 如果是位置返回 true，否则返回 false
     */
    public static boolean isStixDomainObjectLocation(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ENTITY_TYPE_LOCATION)
                || type.equals(SchemaGeneral.ENTITY_TYPE_LOCATION);
    }

    /**
     * 判断是否为威胁行为者
     * 原代码: export const isStixDomainObjectThreatActor = (type: string): boolean =>
     *   schemaTypesDefinition.isTypeIncludedIn(type, ENTITY_TYPE_THREAT_ACTOR) || type === ENTITY_TYPE_THREAT_ACTOR;
     *
     * @param type 类型
     * @return 如果是威胁行为者返回 true，否则返回 false
     */
    public static boolean isStixDomainObjectThreatActor(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ENTITY_TYPE_THREAT_ACTOR)
                || type.equals(SchemaGeneral.ENTITY_TYPE_THREAT_ACTOR);
    }

    /**
     * 判断是否为SDO
     * 原代码: export const isStixDomainObject = (type: string): boolean => {
     *   return schemaTypesDefinition.isTypeIncludedIn(type, ABSTRACT_STIX_DOMAIN_OBJECT)
     *     || isStixDomainObjectIdentity(type)
     *     || isStixDomainObjectLocation(type)
     *     || isStixDomainObjectContainer(type)
     *     || isStixDomainObjectThreatActor(type)
     *     || type === ABSTRACT_STIX_DOMAIN_OBJECT;
     * };
     *
     * @param type 类型
     * @return 如果是SDO返回 true，否则返回 false
     */
    public static boolean isStixDomainObject(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ABSTRACT_STIX_DOMAIN_OBJECT)
                || isStixDomainObjectIdentity(type)
                || isStixDomainObjectLocation(type)
                || isStixDomainObjectContainer(type)
                || isStixDomainObjectThreatActor(type)
                || type.equals(SchemaGeneral.ABSTRACT_STIX_DOMAIN_OBJECT);
    }

    /**
     * 判断是否为Case
     * 原代码: export const isStixDomainObjectCase = (type: string): boolean =>
     *   schemaTypesDefinition.isTypeIncludedIn(type, ENTITY_TYPE_CONTAINER_CASE) || type === ENTITY_TYPE_CONTAINER_CASE;
     *
     * @param type 类型
     * @return 如果是Case返回 true，否则返回 false
     */
    public static boolean isStixDomainObjectCase(String type) {
        if (type == null) {
            return false;
        }
        return SchemaTypesDefinition.isTypeIncludedIn(type, ENTITY_TYPE_CONTAINER_CASE)
                || type.equals(ENTITY_TYPE_CONTAINER_CASE);
    }

    // ==================== 别名相关方法 ====================

    /**
     * 判断是否支持别名
     * 原代码: export const isStixObjectAliased = (type: string): boolean => {
     *   return STIX_DOMAIN_OBJECT_ALIASED.includes(type)
     *     || (isStixDomainObjectIdentity(type) && type !== ENTITY_TYPE_IDENTITY_SECURITY_PLATFORM)
     *     || isStixDomainObjectLocation(type);
     * };
     *
     * @param type 类型
     * @return 如果支持别名返回 true，否则返回 false
     */
    public static boolean isStixObjectAliased(String type) {
        if (type == null) {
            return false;
        }
        return STIX_DOMAIN_OBJECT_ALIASED.contains(type)
                || (isStixDomainObjectIdentity(type) && !type.equals(ENTITY_TYPE_IDENTITY_SECURITY_PLATFORM))
                || isStixDomainObjectLocation(type);
    }

    /**
     * 注册别名类型
     * 原代码: export const registerStixDomainAliased = (type: string) => {
     *   STIX_DOMAIN_OBJECT_ALIASED.push(type);
     * };
     *
     * 注意：由于STIX_DOMAIN_OBJECT_ALIASED是不可变集合，这里创建一个新的可变集合来存储
     */
    private static final Set<String> DYNAMIC_ALIASED_TYPES = new HashSet<>();

    public static void registerStixDomainAliased(String type) {
        if (type != null) {
            DYNAMIC_ALIASED_TYPES.add(type);
        }
    }

    /**
     * 检查类型是否在动态别名集合中
     * @param type 类型
     * @return 如果在动态别名集合中返回 true
     */
    public static boolean isDynamicallyAliased(String type) {
        return type != null && DYNAMIC_ALIASED_TYPES.contains(type);
    }

    /**
     * 解析别名字段
     * 原代码: export const resolveAliasesField = (type: string): AttributeDefinition => {
     *   if (type === ENTITY_TYPE_COURSE_OF_ACTION || type === ENTITY_TYPE_VULNERABILITY
     *       || type === ENTITY_TYPE_CONTAINER_GROUPING || isStixDomainObjectIdentity(type)
     *       || isStixDomainObjectLocation(type)) {
     *     return xOpenctiAliases;
     *   }
     *   return aliases;
     * };
     *
     * 注意：由于attribute-definition模块尚未实现，这里返回字符串标识
     *
     * @param type 类型
     * @return 别名字段名称
     */
    public static String resolveAliasesField(String type) {
        if (type == null) {
            return ATTRIBUTE_ALIASES;
        }
        if (type.equals(ENTITY_TYPE_COURSE_OF_ACTION)
                || type.equals(ENTITY_TYPE_VULNERABILITY)
                || type.equals(ENTITY_TYPE_CONTAINER_GROUPING)
                || isStixDomainObjectIdentity(type)
                || isStixDomainObjectLocation(type)) {
            return ATTRIBUTE_ALIASES_OPENCTI;
        }
        return ATTRIBUTE_ALIASES;
    }
}
