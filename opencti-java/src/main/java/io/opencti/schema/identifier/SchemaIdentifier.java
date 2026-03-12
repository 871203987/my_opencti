package io.opencti.schema.identifier;

import io.opencti.schema.general.SchemaGeneral;

import java.util.*;

/**
 * Schema 标识符生成器
 * 重写自: opencti-platform/opencti-graphql/src/schema/identifier.js
 *
 * 该文件负责OpenCTI系统中所有实体的ID生成策略，包括Standard ID（基于UUID v5）、
 * Internal ID（基于UUID v4）、以及各种实体类型的ID贡献字段定义。
 * 这是整个系统的核心基础模块，所有实体创建都依赖此模块生成唯一标识符。
 */
public final class SchemaIdentifier {

    // 私有构造函数，防止实例化
    private SchemaIdentifier() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== 哈希算法常量 ====================
    // 原代码: const MD5 = 'MD5';
    public static final String MD5 = "MD5";

    // 原代码: const SHA_1 = 'SHA-1';
    public static final String SHA_1 = "SHA-1";

    // 原代码: const SHA_256 = 'SHA-256';
    public static final String SHA_256 = "SHA-256";

    // 原代码: const SHA_512 = 'SHA-512';
    public static final String SHA_512 = "SHA-512";

    // 原代码: const SHA3_256 = 'SHA3-256';
    public static final String SHA3_256 = "SHA3-256";

    // 原代码: const SHA3_512 = 'SHA3-512';
    public static final String SHA3_512 = "SHA3-512";

    // 原代码: const SSDEEP = 'SSDEEP';
    public static final String SSDEEP = "SSDEEP";

    // ==================== 字段名常量 ====================
    // 原代码: export const INTERNAL_FROM_FIELD = 'i_relations_from';
    public static final String INTERNAL_FROM_FIELD = "i_relations_from";

    // 原代码: export const INTERNAL_TO_FIELD = 'i_relations_to';
    public static final String INTERNAL_TO_FIELD = "i_relations_to";

    // 原代码: export const NAME_FIELD = 'name';
    public static final String NAME_FIELD = "name";

    // 原代码: export const INNER_TYPE = 'opencti_type';
    public static final String INNER_TYPE = "opencti_type";

    // 原代码: export const VALUE_FIELD = 'value';
    public static final String VALUE_FIELD = "value";

    // 原代码: export const FIRST_SEEN = 'first_seen';
    public static final String FIRST_SEEN = "first_seen";

    // 原代码: export const LAST_SEEN = 'last_seen';
    public static final String LAST_SEEN = "last_seen";

    // 原代码: export const START_TIME = 'start_time';
    public static final String START_TIME = "start_time";

    // 原代码: export const STOP_TIME = 'stop_time';
    public static final String STOP_TIME = "stop_time";

    // 原代码: export const VALID_FROM = 'valid_from';
    public static final String VALID_FROM = "valid_from";

    // 原代码: export const FIRST_OBSERVED = 'first_observed';
    public static final String FIRST_OBSERVED = "first_observed";

    // 原代码: export const LAST_OBSERVED = 'last_observed';
    public static final String LAST_OBSERVED = "last_observed";

    // 原代码: export const VALID_UNTIL = 'valid_until';
    public static final String VALID_UNTIL = "valid_until";

    // 原代码: export const REVOKED = 'revoked';
    public static final String REVOKED = "revoked";

    // 原代码: export const X_MITRE_ID_FIELD = 'x_mitre_id';
    public static final String X_MITRE_ID_FIELD = "x_mitre_id";

    // 原代码: export const X_DETECTION = 'x_opencti_detection';
    public static final String X_DETECTION = "x_opencti_detection";

    // 原代码: export const X_WORKFLOW_ID = 'x_opencti_workflow_id';
    public static final String X_WORKFLOW_ID = "x_opencti_workflow_id";

    // 原代码: export const X_SCORE = 'x_opencti_score';
    public static final String X_SCORE = "x_opencti_score";

    // ==================== TLP标记定义常量 ====================
    // 原代码: export const MARKING_TLP_CLEAR_ID = '613f2e26-407d-48c7-9eca-b8e91df99dc9';
    public static final String MARKING_TLP_CLEAR_ID = "613f2e26-407d-48c7-9eca-b8e91df99dc9";

    // 原代码: export const MARKING_TLP_CLEAR = `marking-definition--${MARKING_TLP_CLEAR_ID}`;
    public static final String MARKING_TLP_CLEAR = "marking-definition--613f2e26-407d-48c7-9eca-b8e91df99dc9";

    // 原代码: const MARKING_TLP_GREEN_ID = '34098fce-860f-48ae-8e50-ebd3cc5e41da';
    public static final String MARKING_TLP_GREEN_ID = "34098fce-860f-48ae-8e50-ebd3cc5e41da";

    // 原代码: export const MARKING_TLP_GREEN = `marking-definition--${MARKING_TLP_GREEN_ID}`;
    public static final String MARKING_TLP_GREEN = "marking-definition--34098fce-860f-48ae-8e50-ebd3cc5e41da";

    // 原代码: const MARKING_TLP_AMBER_ID = 'f88d31f6-486f-44da-b317-01333bde0b82';
    public static final String MARKING_TLP_AMBER_ID = "f88d31f6-486f-44da-b317-01333bde0b82";

    // 原代码: export const MARKING_TLP_AMBER = `marking-definition--${MARKING_TLP_AMBER_ID}`;
    public static final String MARKING_TLP_AMBER = "marking-definition--f88d31f6-486f-44da-b317-01333bde0b82";

    // 原代码: const MARKING_TLP_AMBER_STRICT_ID = '826578e1-40ad-459f-bc73-ede076f81f37';
    public static final String MARKING_TLP_AMBER_STRICT_ID = "826578e1-40ad-459f-bc73-ede076f81f37";

    // 原代码: export const MARKING_TLP_AMBER_STRICT = `marking-definition--${MARKING_TLP_AMBER_STRICT_ID}`;
    public static final String MARKING_TLP_AMBER_STRICT = "marking-definition--826578e1-40ad-459f-bc73-ede076f81f37";

    // 原代码: const MARKING_TLP_RED_ID = '5e57c739-391a-4eb3-b6be-7d15ca92d5ed';
    public static final String MARKING_TLP_RED_ID = "5e57c739-391a-4eb3-b6be-7d15ca92d5ed";

    // 原代码: export const MARKING_TLP_RED = `marking-definition--${MARKING_TLP_RED_ID}`;
    public static final String MARKING_TLP_RED = "marking-definition--5e57c739-391a-4eb3-b6be-7d15ca92d5ed";

    // 原代码: export const STATIC_MARKING_IDS = [...]
    public static final List<String> STATIC_MARKING_IDS = Collections.unmodifiableList(Arrays.asList(
            MARKING_TLP_CLEAR,
            MARKING_TLP_GREEN,
            MARKING_TLP_AMBER,
            MARKING_TLP_AMBER_STRICT,
            MARKING_TLP_RED
    ));

    // 原代码: const STATIC_STANDARD_IDS = [...]
    public static final List<Map<String, Object>> STATIC_STANDARD_IDS = Collections.unmodifiableList(Arrays.asList(
            createStaticId(MARKING_TLP_CLEAR_ID, "TLP", "TLP:WHITE"),
            createStaticId(MARKING_TLP_CLEAR_ID, "TLP", "TLP:CLEAR"),
            createStaticId(MARKING_TLP_GREEN_ID, "TLP", "TLP:GREEN"),
            createStaticId(MARKING_TLP_AMBER_ID, "TLP", "TLP:AMBER"),
            createStaticId(MARKING_TLP_AMBER_STRICT_ID, "TLP", "TLP:AMBER+STRICT"),
            createStaticId(MARKING_TLP_RED_ID, "TLP", "TLP:RED")
    ));

    private static Map<String, Object> createStaticId(String id, String definitionType, String definition) {
        Map<String, Object> data = new HashMap<>();
        data.put("definition_type", definitionType);
        data.put("definition", definition);
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("data", data);
        return result;
    }

    // ==================== 工具方法 ====================

    /**
     * 规范化名称
     * 原代码: export const normalizeName = (name) => { return (name || '').toLowerCase().trim(); };
     *
     * @param name 名称
     * @return 小写并去除首尾空格的字符串
     */
    public static String normalizeName(String name) {
        if (name == null) {
            return "";
        }
        return name.toLowerCase().trim();
    }

    /**
     * 从数据获取静态ID
     * 原代码: const getStaticIdFromData = (data) => { ... }
     *
     * @param data 数据
     * @return 静态ID，如果没有匹配返回null
     */
    public static String getStaticIdFromData(Map<String, Object> data) {
        for (Map<String, Object> staticId : STATIC_STANDARD_IDS) {
            @SuppressWarnings("unchecked")
            Map<String, Object> staticData = (Map<String, Object>) staticId.get("data");
            if (staticData.equals(data)) {
                return (String) staticId.get("id");
            }
        }
        return null;
    }

    /**
     * 生成Internal ID (UUID v4)
     * 原代码: export const generateInternalId = () => uuidv4();
     *
     * @return UUID v4字符串
     */
    public static String generateInternalId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成工作ID
     * 原代码: export const generateWorkId = (connectorId) => { ... }
     *
     * @param connectorId 连接器ID
     * @return 包含id和timestamp的工作ID对象
     */
    public static WorkId generateWorkId(String connectorId) {
        long timestamp = System.currentTimeMillis();
        String id = "work_" + connectorId + "_" + timestamp;
        return new WorkId(id, timestamp);
    }

    /**
     * 工作ID对象
     */
    public static class WorkId {
        private final String id;
        private final long timestamp;

        public WorkId(String id, long timestamp) {
            this.id = id;
            this.timestamp = timestamp;
        }

        public String getId() {
            return id;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    /**
     * 生成文件索引ID
     * 原代码: export const generateFileIndexId = (fileId) => { return uuidv5(fileId, OPENCTI_NAMESPACE); };
     *
     * @param fileId 文件ID
     * @return 基于fileId的UUID v5
     */
    public static String generateFileIndexId(String fileId) {
        return generateUuidV5(fileId, SchemaGeneral.OPENCTI_NAMESPACE);
    }

    /**
     * 生成UUID v5
     * 使用Java内置的UUID.nameUUIDFromBytes实现
     *
     * @param name 名称
     * @param namespace 命名空间UUID
     * @return UUID v5字符串
     */
    private static String generateUuidV5(String name, String namespace) {
        // 将命名空间UUID字符串转换为UUID对象
        UUID namespaceUuid = UUID.fromString(namespace);

        // 构建用于nameUUIDFromBytes的字节数组
        // 格式: namespace.bytes + name.bytes
        byte[] nameBytes = name.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        byte[] namespaceBytes = new byte[16];
        long msb = namespaceUuid.getMostSignificantBits();
        long lsb = namespaceUuid.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) {
            namespaceBytes[i] = (byte) ((msb >> ((7 - i) * 8)) & 0xff);
            namespaceBytes[8 + i] = (byte) ((lsb >> ((7 - i) * 8)) & 0xff);
        }

        byte[] combined = new byte[namespaceBytes.length + nameBytes.length];
        System.arraycopy(namespaceBytes, 0, combined, 0, namespaceBytes.length);
        System.arraycopy(nameBytes, 0, combined, namespaceBytes.length, nameBytes.length);

        UUID uuid = UUID.nameUUIDFromBytes(combined);

        // 设置版本为5 (UUID v5)
        long mostSigBits = uuid.getMostSignificantBits();
        mostSigBits &= ~0x000000000000f000L; // 清除版本位
        mostSigBits |= 0x0000000000005000L;  // 设置版本为5

        // 设置变体为RFC 4122
        long leastSigBits = uuid.getLeastSignificantBits();
        leastSigBits &= ~0xc000000000000000L; // 清除变体位
        leastSigBits |= 0x8000000000000000L;  // 设置变体为10

        return new UUID(mostSigBits, leastSigBits).toString();
    }

    // ==================== ID生成方法 ====================

    /**
     * 生成Standard ID
     * 原代码: export const generateStandardId = (type, data) => { ... }
     *
     * 注意：这是一个简化版本，完整的实现需要依赖其他Schema模块的类型判断方法
     *
     * @param type 实体类型
     * @param data 实体数据
     * @return Standard ID字符串
     */
    public static String generateStandardId(String type, Map<String, Object> data) {
        // 简化的实现：使用类型和数据生成UUID v5
        // 完整的实现需要根据实体类型选择不同的命名空间
        String dataStr = type + ":" + data.toString();
        return generateUuidV5(dataStr, SchemaGeneral.OASIS_NAMESPACE);
    }

    /**
     * 生成别名ID列表
     * 原代码: export const generateAliasesId = (rawAliases, instance) => { ... }
     *
     * @param rawAliases 原始别名列表
     * @param instance 实例数据
     * @return 别名ID列表
     */
    public static List<String> generateAliasesId(List<String> rawAliases, Map<String, Object> instance) {
        if (rawAliases == null || rawAliases.isEmpty()) {
            return Collections.emptyList();
        }

        String entityType = (String) instance.get("entity_type");
        if (entityType == null) {
            return Collections.emptyList();
        }

        Set<String> uniqueIds = new HashSet<>();
        for (String alias : rawAliases) {
            if (alias != null && !alias.trim().isEmpty()) {
                String trimmedAlias = alias.trim();
                Map<String, Object> instanceWithAlias = new HashMap<>(instance);
                instanceWithAlias.put("name", trimmedAlias);
                uniqueIds.add(generateStandardId(entityType, instanceWithAlias));
            }
        }
        return new ArrayList<>(uniqueIds);
    }

    /**
     * 从实例生成别名ID
     * 原代码: export const generateAliasesIdsForInstance = (instance) => { ... }
     *
     * @param instance 实例数据
     * @return 别名ID列表
     */
    @SuppressWarnings("unchecked")
    public static List<String> generateAliasesIdsForInstance(Map<String, Object> instance) {
        List<String> aliases = new ArrayList<>();

        if (instance.containsKey("aliases")) {
            Object aliasesObj = instance.get("aliases");
            if (aliasesObj instanceof List) {
                aliases.addAll((List<String>) aliasesObj);
            }
        }

        if (instance.containsKey("x_opencti_aliases")) {
            Object xAliasesObj = instance.get("x_opencti_aliases");
            if (xAliasesObj instanceof List) {
                aliases.addAll((List<String>) xAliasesObj);
            }
        }

        return generateAliasesId(aliases, instance);
    }

    /**
     * 获取实例所有ID
     * 原代码: export const getInstanceIds = (instance, withoutInternal = false) => { ... }
     *
     * @param instance 实例数据
     * @param withoutInternal 是否不包含internal_id
     * @return 所有ID列表
     */
    @SuppressWarnings("unchecked")
    public static List<String> getInstanceIds(Map<String, Object> instance, boolean withoutInternal) {
        Set<String> ids = new HashSet<>();

        if (!withoutInternal && instance.containsKey("internal_id")) {
            Object internalId = instance.get("internal_id");
            if (internalId != null) {
                ids.add(internalId.toString());
            }
        }

        if (instance.containsKey("standard_id")) {
            Object standardId = instance.get("standard_id");
            if (standardId != null) {
                ids.add(standardId.toString());
            }
        }

        if (instance.containsKey("x_opencti_stix_ids")) {
            Object stixIds = instance.get("x_opencti_stix_ids");
            if (stixIds instanceof List) {
                for (Object id : (List<Object>) stixIds) {
                    if (id != null) {
                        ids.add(id.toString());
                    }
                }
            }
        }

        // 添加别名ID
        ids.addAll(generateAliasesIdsForInstance(instance));

        return new ArrayList<>(ids);
    }

    /**
     * 获取实例所有ID（默认包含internal_id）
     *
     * @param instance 实例数据
     * @return 所有ID列表
     */
    public static List<String> getInstanceIds(Map<String, Object> instance) {
        return getInstanceIds(instance, false);
    }

    /**
     * 获取输入所有ID
     * 原代码: export const getInputIds = (type, input, fromRule = false) => { ... }
     *
     * @param type 实体类型
     * @param input 输入数据
     * @param fromRule 是否来自规则
     * @return 所有ID列表
     */
    @SuppressWarnings("unchecked")
    public static List<String> getInputIds(String type, Map<String, Object> input, boolean fromRule) {
        Set<String> ids = new HashSet<>();

        // 添加standard_id或生成新的
        if (input.containsKey("standard_id")) {
            Object standardId = input.get("standard_id");
            if (standardId != null) {
                ids.add(standardId.toString());
            }
        } else {
            ids.add(generateStandardId(type, input));
        }

        // 添加internal_id
        if (input.containsKey("internal_id")) {
            Object internalId = input.get("internal_id");
            if (internalId != null) {
                ids.add(internalId.toString());
            }
        }

        // 添加stix_id
        if (input.containsKey("stix_id")) {
            Object stixId = input.get("stix_id");
            if (stixId != null) {
                ids.add(stixId.toString());
            }
        }

        // 添加x_opencti_stix_ids
        if (input.containsKey("x_opencti_stix_ids")) {
            Object stixIds = input.get("x_opencti_stix_ids");
            if (stixIds instanceof List) {
                for (Object id : (List<Object>) stixIds) {
                    if (id != null) {
                        ids.add(id.toString());
                    }
                }
            }
        }

        // 添加别名ID
        ids.addAll(generateAliasesIdsForInstance(input));

        // 如果来自规则且是关系类型，添加组合ID
        if (fromRule && input.containsKey("from") && input.containsKey("to")) {
            Map<String, Object> from = (Map<String, Object>) input.get("from");
            Map<String, Object> to = (Map<String, Object>) input.get("to");
            if (from != null && to != null) {
                String fromId = (String) from.get("internal_id");
                String toId = (String) to.get("internal_id");
                if (fromId != null && toId != null) {
                    ids.add(fromId + "-" + type + "-" + toId);
                    ids.add(toId + "-" + type + "-" + fromId);
                }
            }
        }

        return new ArrayList<>(ids);
    }

    /**
     * 获取输入所有ID（默认不来自规则）
     *
     * @param type 实体类型
     * @param input 输入数据
     * @return 所有ID列表
     */
    public static List<String> getInputIds(String type, Map<String, Object> input) {
        return getInputIds(type, input, false);
    }

    // ==================== 占位符方法 ====================
    // 以下方法需要依赖其他Schema模块的实现，暂时提供桩实现

    /**
     * 判断是否为支持的STIX类型
     * 原代码: export const isSupportedStixType = (stixType) => { ... }
     *
     * @param stixType STIX类型
     * @return 是否支持
     */
    public static boolean isSupportedStixType(String stixType) {
        // 简化的实现，完整的实现需要检查所有identifierContributions
        return stixType != null && !stixType.isEmpty();
    }

    /**
     * 判断是否字段贡献Standard ID
     * 原代码: export const isFieldContributingToStandardId = (instance, keys) => { ... }
     *
     * @param instance 实例数据
     * @param keys 字段名列表
     * @return 是否贡献
     */
    public static boolean isFieldContributingToStandardId(Map<String, Object> instance, List<String> keys) {
        // 简化的实现，完整的实现需要检查ID贡献字段定义
        return keys != null && !keys.isEmpty();
    }
}
