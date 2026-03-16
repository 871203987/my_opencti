package io.opencti.schema.utils;

import io.opencti.schema.general.SchemaGeneral;
import io.opencti.schema.internal.InternalObjectSchema;
import io.opencti.schema.internal.InternalRelationshipSchema;
import io.opencti.schema.stix.StixCoreObjectSchema;
import io.opencti.schema.stix.StixCoreRelationshipSchema;
import io.opencti.schema.stix.StixCyberObservableSchema;
import io.opencti.schema.stix.StixDomainObjectSchema;
import io.opencti.schema.stix.StixMetaObjectSchema;
import io.opencti.schema.stix.StixRefRelationshipSchema;
import io.opencti.schema.stix.StixRelationshipSchema;
import io.opencti.schema.stix.StixSightingRelationshipSchema;

import static io.opencti.schema.stix.StixDomainObjectSchema.ENTITY_TYPE_THREAT_ACTOR_GROUP;
import static io.opencti.schema.stix.StixSightingRelationshipSchema.STIX_SIGHTING_RELATIONSHIP;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Schema 工具类
 * 重写自: opencti-platform/opencti-graphql/src/schema/schemaUtils.js
 *
 * 提供Schema相关的工具函数，包括ID验证、类型转换、父类型获取等功能。
 */
public final class SchemaUtils {

    private SchemaUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== ID验证方法 ====================

    // 原代码: export const isStixId = (id) => id.match(/[a-z-]+--[\w-]{36}/g);
    private static final Pattern STIX_ID_PATTERN = Pattern.compile("^[a-z-]+--[\\w-]{36}$");

    /**
     * 验证是否为STIX ID格式
     * STIX ID格式: [type]--[UUID]，例如: attack-pattern--12345678-1234-1234-1234-123456789abc
     *
     * @param id ID字符串
     * @return 如果是STIX ID格式返回true，否则返回false
     */
    public static boolean isStixId(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        return STIX_ID_PATTERN.matcher(id).matches();
    }

    // 原代码: export const isInternalId = (id) => validator.isUUID(id);
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
    );

    /**
     * 验证是否为UUID格式（内部ID）
     *
     * @param id ID字符串
     * @return 如果是UUID格式返回true，否则返回false
     */
    public static boolean isInternalId(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        return UUID_PATTERN.matcher(id).matches();
    }

    // 原代码: export const isAnId = (id) => { return isStixId(id) || isInternalId(id); };
    /**
     * 验证是否为有效的ID（STIX ID或内部ID）
     *
     * @param id ID字符串
     * @return 如果是有效ID返回true，否则返回false
     */
    public static boolean isAnId(String id) {
        return isStixId(id) || isInternalId(id);
    }

    // ==================== 哈希方法 ====================

    // 原代码:
    // export const shortHash = (element) => {
    //   const crypt = crypto.createHash('sha256');
    //   const hash = crypt.update(JSON.stringify(element)).digest('hex');
    //   return hash.slice(0, 8);
    // };
    /**
     * 计算元素的短哈希值（SHA256前8位）
     *
     * @param element 任意对象
     * @return 8位十六进制哈希字符串
     */
    public static String shortHash(Object element) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String jsonString = element == null ? "null" : element.toString();
            byte[] hashBytes = digest.digest(jsonString.getBytes(StandardCharsets.UTF_8));

            // 转换为十六进制字符串，取前8位
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < 4; i++) { // 4 bytes = 8 hex characters
                String hex = Integer.toHexString(0xff & hashBytes[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    // ==================== 日期验证方法 ====================

    // 原代码:
    // export const isValidDate = (stringDate) => {
    //   const dateParsed = Date.parse(stringDate);
    //   if (!dateParsed) return false;
    //   const dateInstance = new Date(dateParsed);
    //   return dateInstance.toISOString() === stringDate;
    // };
    /**
     * 验证日期字符串是否为有效的ISO 8601格式
     *
     * @param stringDate 日期字符串
     * @return 如果是有效的ISO 8601格式返回true，否则返回false
     */
    public static boolean isValidDate(String stringDate) {
        if (stringDate == null || stringDate.isEmpty()) {
            return false;
        }
        try {
            Instant instant = Instant.parse(stringDate);
            // 验证转换后的日期字符串与原字符串一致
            return instant.toString().equals(stringDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // ==================== 类型转换方法 ====================

    // 原代码: const pascalize = (str) => { ... }
    /**
     * 将字符串转换为PascalCase格式（首字母大写，单词间用连字符连接）
     *
     * @param str 输入字符串
     * @return PascalCase格式字符串
     */
    public static String pascalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // 分割单词（按非字母数字字符分割）
        String[] words = str.split("[^a-zA-Z0-9]+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1).toLowerCase());
                }
                result.append("-");
            }
        }

        // 移除末尾的连字符
        if (result.length() > 0 && result.charAt(result.length() - 1) == '-') {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }

    // 原代码: export const generateInternalType = (entity) => { ... }
    /**
     * 从STIX实体生成内部类型
     *
     * @param entity STIX实体对象，包含type字段
     * @return 内部类型字符串
     */
    @SuppressWarnings("unchecked")
    public static String generateInternalType(Map<String, Object> entity) {
        if (entity == null || !entity.containsKey("type")) {
            return null;
        }

        String type = (String) entity.get("type");

        switch (type) {
            case SchemaGeneral.STIX_TYPE_SIGHTING:
            case "Stix-Sighting-Relationship":
            case "stix-sighting-relationship":
                return STIX_SIGHTING_RELATIONSHIP;

            case SchemaGeneral.STIX_TYPE_RELATION:
                return SchemaGeneral.ABSTRACT_STIX_CORE_RELATIONSHIP;

            case "identity":
                String identityClass = (String) entity.get("identity_class");
                if ("class".equals(identityClass)) {
                    return "Sector";
                }
                return pascalize(identityClass);

            case "threat-actor":
                String resourceLevel = (String) entity.get("resource_level");
                if ("individual".equals(resourceLevel)) {
                    // 注意：这里需要ENTITY_TYPE_THREAT_ACTOR_INDIVIDUAL，但当前SchemaGeneral中未定义
                    // 暂时返回Threat-Actor-Individual
                    return "Threat-Actor-Individual";
                }
                return ENTITY_TYPE_THREAT_ACTOR_GROUP;

            case "location":
                String locationType = (String) entity.get("x_opencti_location_type");
                return locationType != null ? locationType : "Location";

            case "ipv4-addr":
                return StixCyberObservableSchema.ENTITY_IPV4_ADDR;

            case "ipv6-addr":
                return StixCyberObservableSchema.ENTITY_IPV6_ADDR;

            case "stixfile":
            case "file":
                return StixCyberObservableSchema.ENTITY_HASHED_OBSERVABLE_STIX_FILE;

            case "ssh-key":
                return StixCyberObservableSchema.ENTITY_SSH_KEY;

            default:
                return pascalize(type);
        }
    }

    // 原代码: export const convertStixToInternalTypes = (type) => { ... }
    /**
     * 将STIX类型转换为内部类型列表（包含父类型）
     * 注意：此方法可能生成抽象类型，请谨慎使用
     *
     * @param type STIX类型字符串
     * @return 内部类型列表（包含父类型）
     */
    public static List<String> convertStixToInternalTypes(String type) {
        List<String> result = new ArrayList<>();

        switch (type) {
            case SchemaGeneral.STIX_TYPE_SIGHTING:
            case "Stix-Sighting-Relationship":
            case "stix-sighting-relationship":
                result.add(STIX_SIGHTING_RELATIONSHIP);
                result.addAll(getParentTypes(STIX_SIGHTING_RELATIONSHIP));
                break;

            case SchemaGeneral.STIX_TYPE_RELATION:
                result.add(STIX_SIGHTING_RELATIONSHIP);
                result.addAll(getParentTypes(SchemaGeneral.ABSTRACT_STIX_CORE_RELATIONSHIP));
                break;

            case "identity":
                result.add(SchemaGeneral.ENTITY_TYPE_IDENTITY);
                result.addAll(getParentTypes(SchemaGeneral.ENTITY_TYPE_IDENTITY));
                break;

            case "threat-actor":
                result.add(SchemaGeneral.ENTITY_TYPE_THREAT_ACTOR);
                result.addAll(getParentTypes(SchemaGeneral.ENTITY_TYPE_THREAT_ACTOR));
                break;

            case "location":
                result.add(SchemaGeneral.ENTITY_TYPE_LOCATION);
                result.addAll(getParentTypes(SchemaGeneral.ENTITY_TYPE_LOCATION));
                break;

            case "ipv4-addr":
                result.add(StixCyberObservableSchema.ENTITY_IPV4_ADDR);
                result.addAll(getParentTypes(StixCyberObservableSchema.ENTITY_IPV4_ADDR));
                break;

            case "ipv6-addr":
                result.add(StixCyberObservableSchema.ENTITY_IPV6_ADDR);
                result.addAll(getParentTypes(StixCyberObservableSchema.ENTITY_IPV6_ADDR));
                break;

            case "file":
                result.add(StixCyberObservableSchema.ENTITY_HASHED_OBSERVABLE_STIX_FILE);
                result.addAll(getParentTypes(StixCyberObservableSchema.ENTITY_HASHED_OBSERVABLE_STIX_FILE));
                break;

            case "ssh-key":
                result.add(StixCyberObservableSchema.ENTITY_SSH_KEY);
                result.addAll(getParentTypes(StixCyberObservableSchema.ENTITY_SSH_KEY));
                break;

            default:
                result.add(pascalize(type));
                break;
        }

        return result;
    }

    // ==================== 父类型获取方法 ====================

    // 原代码: export const getParentTypes = (type) => { ... }
    /**
     * 获取类型的所有父类型
     *
     * @param type 类型字符串
     * @return 父类型列表
     * @throws IllegalArgumentException 如果类型不支持
     */
    public static List<String> getParentTypes(String type) {
        List<String> parentTypes = new ArrayList<>();

        if (StixCoreObjectSchema.isBasicObject(type)) {
            parentTypes.add(SchemaGeneral.ABSTRACT_BASIC_OBJECT);

            if (InternalObjectSchema.isInternalObject(type)) {
                parentTypes.add(SchemaGeneral.ABSTRACT_INTERNAL_OBJECT);
            } else if (StixCoreObjectSchema.isStixObject(type)) {
                parentTypes.add(SchemaGeneral.ABSTRACT_STIX_OBJECT);

                if (StixMetaObjectSchema.isStixMetaObject(type)) {
                    parentTypes.add(SchemaGeneral.ABSTRACT_STIX_META_OBJECT);
                } else if (StixCoreObjectSchema.isStixCoreObject(type)) {
                    parentTypes.add(SchemaGeneral.ABSTRACT_STIX_CORE_OBJECT);

                    if (StixDomainObjectSchema.isStixDomainObject(type)) {
                        parentTypes.add(SchemaGeneral.ABSTRACT_STIX_DOMAIN_OBJECT);

                        if (StixDomainObjectSchema.isStixDomainObjectContainer(type)) {
                            parentTypes.add(SchemaGeneral.ENTITY_TYPE_CONTAINER);
                        }
                        if (StixDomainObjectSchema.isStixDomainObjectIdentity(type)) {
                            parentTypes.add(SchemaGeneral.ENTITY_TYPE_IDENTITY);
                        }
                        if (StixDomainObjectSchema.isStixDomainObjectLocation(type)) {
                            parentTypes.add(SchemaGeneral.ENTITY_TYPE_LOCATION);
                        }
                        if (StixDomainObjectSchema.isStixDomainObjectCase(type)) {
                            // 注意：ENTITY_TYPE_CONTAINER_CASE未在SchemaGeneral中定义
                            parentTypes.add("Case");
                        }
                        if (StixDomainObjectSchema.isStixDomainObjectThreatActor(type)) {
                            parentTypes.add(SchemaGeneral.ENTITY_TYPE_THREAT_ACTOR);
                        }
                    }

                    if (StixCyberObservableSchema.isStixCyberObservable(type)) {
                        parentTypes.add(SchemaGeneral.ABSTRACT_STIX_CYBER_OBSERVABLE);
                    }
                }
            }
        } else if (StixRelationshipSchema.isBasicRelationship(type)) {
            parentTypes.add(SchemaGeneral.ABSTRACT_BASIC_RELATIONSHIP);

            if (InternalRelationshipSchema.isInternalRelationship(type)) {
                parentTypes.add(SchemaGeneral.ABSTRACT_INTERNAL_RELATIONSHIP);
            } else if (StixRelationshipSchema.isStixRelationship(type)) {
                parentTypes.add(SchemaGeneral.ABSTRACT_STIX_RELATIONSHIP);

                if (StixRefRelationshipSchema.isStixRefRelationship(type)) {
                    parentTypes.add(SchemaGeneral.ABSTRACT_STIX_REF_RELATIONSHIP);
                }
                if (StixCoreRelationshipSchema.isStixCoreRelationship(type)) {
                    parentTypes.add(SchemaGeneral.ABSTRACT_STIX_CORE_RELATIONSHIP);
                }
            }
        }

        if (parentTypes.isEmpty()) {
            throw new IllegalArgumentException("Type " + type + " not supported");
        }

        return parentTypes;
    }

    // 原代码:
    // export const keepMostRestrictiveTypes = (entityTypes) => {
    //   let restrictedEntityTypes = [...entityTypes];
    //   for (let i = 0; i < entityTypes.length; i += 1) {
    //     const type = entityTypes[i];
    //     const parentTypes = getParentTypes(type).filter((p) => p !== type);
    //     restrictedEntityTypes = restrictedEntityTypes.filter((t) => !parentTypes.includes(t));
    //   }
    //   return restrictedEntityTypes;
    // };
    /**
     * 过滤掉冗余的父类型，只保留最具体的类型
     * 例如: [Report, Container, Stix-Relationship] => [Report, Stix-Relationship]
     * （因为Report是Container的子类型）
     *
     * @param entityTypes 实体类型列表
     * @return 过滤后的类型列表
     */
    public static List<String> keepMostRestrictiveTypes(List<String> entityTypes) {
        if (entityTypes == null || entityTypes.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> restrictedEntityTypes = new ArrayList<>(entityTypes);

        for (String type : entityTypes) {
            try {
                List<String> parentTypes = getParentTypes(type);
                // 过滤掉当前类型本身，只保留其他父类型
                List<String> otherParentTypes = new ArrayList<>();
                for (String parentType : parentTypes) {
                    if (!parentType.equals(type)) {
                        otherParentTypes.add(parentType);
                    }
                }

                // 从结果列表中移除所有父类型
                restrictedEntityTypes.removeAll(otherParentTypes);
            } catch (IllegalArgumentException e) {
                // 如果类型不支持，跳过
            }
        }

        return restrictedEntityTypes;
    }
}
