package io.opencti.database.middleware;

/**
 * 中间件常量定义
 * 重写自: opencti-graphql/src/database/middleware.js:248
 *         opencti-graphql/src/schema/general.js:42
 *         opencti-graphql/src/rules/rules-utils.js
 */
public final class MiddlewareConstants {

    private MiddlewareConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ==================== 批量操作常量 ====================
    
    /**
     * 最大批量大小
     * 重写自: middleware.js:247 MAX_BATCH_SIZE
     */
    public static final int MAX_BATCH_SIZE = 500;

    /**
     * 每条规则最大解释数
     * 重写自: middleware.js:248 MAX_EXPLANATIONS_PER_RULE
     */
    public static final int MAX_EXPLANATIONS_PER_RULE = 100;

    // ==================== 规则常量 ====================
    
    /**
     * 规则字段前缀
     * 重写自: general.js:42 RULE_PREFIX
     */
    public static final String RULE_PREFIX = "i_rule_";

    /**
     * 关系索引前缀
     * 重写自: general.js REL_INDEX_PREFIX
     */
    public static final String REL_INDEX_PREFIX = "rel_";

    // ==================== 时间常量 ====================
    
    /**
     * 起始时间字符串
     * 重写自: middleware.js FROM_START_STR
     */
    public static final String FROM_START_STR = "1970-01-01T00:00:00.000Z";

    // ==================== 规则属性操作类型 ====================
    
    /**
     * 规则属性操作枚举
     * 重写自: rules-utils.js:13 OPERATIONS
     */
    public enum RuleOperation {
        MIN("MIN"),
        MAX("MAX"),
        AVG("AVG"),
        SUM("SUM"),
        AGG("AGG");

        private final String value;

        RuleOperation(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static RuleOperation fromValue(String value) {
            for (RuleOperation op : values()) {
                if (op.value.equalsIgnoreCase(value)) {
                    return op;
                }
            }
            return null;
        }
    }

    /**
     * 规则支持的属性定义
     * 重写自: rules-utils.js:14-41 supportedAttributes
     */
    public static class SupportedAttribute {
        private final String name;
        private final RuleOperation operation;

        public SupportedAttribute(String name, RuleOperation operation) {
            this.name = name;
            this.operation = operation;
        }

        public String getName() {
            return name;
        }

        public RuleOperation getOperation() {
            return operation;
        }
    }
}
