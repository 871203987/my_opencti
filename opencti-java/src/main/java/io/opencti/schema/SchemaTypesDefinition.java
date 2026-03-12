package io.opencti.schema;

import java.util.*;

/**
 * Schema 类型定义管理器
 * 用于注册和管理各种实体类型
 * 
 * 这是一个桩类，完整的实现将在后续迭代中完善
 */
public final class SchemaTypesDefinition {

    // 存储类型定义映射
    private static final Map<String, Set<String>> typeDefinitions = new HashMap<>();

    // 私有构造函数，防止实例化
    private SchemaTypesDefinition() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * 注册类型
     * 
     * @param abstractType 抽象类型
     * @param concreteTypes 具体类型列表
     */
    public static void register(String abstractType, Collection<String> concreteTypes) {
        typeDefinitions.computeIfAbsent(abstractType, k -> new HashSet<>()).addAll(concreteTypes);
    }

    /**
     * 判断类型是否包含在抽象类型中
     * 
     * @param type 类型
     * @param abstractType 抽象类型
     * @return 是否包含
     */
    public static boolean isTypeIncludedIn(String type, String abstractType) {
        Set<String> types = typeDefinitions.get(abstractType);
        return types != null && types.contains(type);
    }

    /**
     * 获取抽象类型的所有具体类型
     * 
     * @param abstractType 抽象类型
     * @return 具体类型集合
     */
    public static Set<String> getTypes(String abstractType) {
        return typeDefinitions.getOrDefault(abstractType, Collections.emptySet());
    }
}
