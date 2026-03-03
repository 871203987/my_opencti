package io.opencti.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 重写自: opencti-graphql/src/utils/sorting.ts
 * 排序工具类
 */
public final class SortingUtils {

    private SortingUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 重写自: opencti-graphql/src/utils/sorting.ts - buildElasticSortingForAttributeCriteria
     * 构建Elasticsearch排序条件
     *
     * @param orderCriteria 排序字段
     * @param orderMode     排序模式（asc/desc）
     * @param pirId         PIR ID（可选）
     * @return Elasticsearch排序条件Map
     */
    public static Map<String, Object> buildElasticSortingForAttributeCriteria(
            String orderCriteria,
            String orderMode,
            String pirId) {

        Map<String, Object> sorting = new HashMap<>();

        if (orderCriteria == null || orderCriteria.isEmpty()) {
            return sorting;
        }

        String mode = orderMode != null ? orderMode : "asc";

        // 处理PIR排序
        if ("pir_score".equals(orderCriteria) || "last_pir_score_date".equals(orderCriteria)) {
            if (pirId == null || pirId.isEmpty()) {
                throw new IllegalArgumentException("PIR ID is required for PIR ordering");
            }
            Map<String, Object> pirSorting = new HashMap<>();
            pirSorting.put("order", mode);
            pirSorting.put("missing", "_last");

            Map<String, Object> nested = new HashMap<>();
            nested.put("path", "pir_information");

            Map<String, Object> filter = new HashMap<>();
            Map<String, Object> term = new HashMap<>();
            term.put("pir_information.pir_id.keyword", pirId);
            filter.put("term", term);
            nested.put("filter", filter);

            pirSorting.put("nested", nested);
            sorting.put("pir_information." + orderCriteria, pirSorting);
            return sorting;
        }

        // 默认字段排序
        Map<String, Object> fieldSorting = new HashMap<>();
        fieldSorting.put("order", mode);
        fieldSorting.put("missing", "_last");

        // 对于日期、数字、布尔类型字段
        if (isDateNumericOrBooleanField(orderCriteria)) {
            sorting.put(orderCriteria, fieldSorting);
        } else {
            // 对于文本字段使用keyword子字段
            sorting.put(orderCriteria + ".keyword", fieldSorting);
        }

        return sorting;
    }

    /**
     * 判断字段是否为日期、数字或布尔类型
     *
     * @param fieldName 字段名
     * @return 如果是日期、数字或布尔类型则返回true
     */
    private static boolean isDateNumericOrBooleanField(String fieldName) {
        if (fieldName == null) {
            return false;
        }
        String lowerName = fieldName.toLowerCase();
        return lowerName.contains("date") ||
                lowerName.contains("time") ||
                lowerName.contains("timestamp") ||
                lowerName.contains("count") ||
                lowerName.contains("number") ||
                lowerName.contains("score") ||
                lowerName.contains("confidence") ||
                lowerName.contains("is_") ||
                lowerName.endsWith("_at");
    }

    /**
     * 构建多字段排序
     *
     * @param orderCriteriaList 排序字段列表
     * @param orderMode         排序模式
     * @return 排序条件列表
     */
    public static Map<String, Object>[] buildMultiFieldSorting(
            String[] orderCriteriaList,
            String orderMode) {

        if (orderCriteriaList == null || orderCriteriaList.length == 0) {
            return new Map[0];
        }

        @SuppressWarnings("unchecked")
        Map<String, Object>[] result = new Map[orderCriteriaList.length];
        for (int i = 0; i < orderCriteriaList.length; i++) {
            result[i] = buildElasticSortingForAttributeCriteria(
                    orderCriteriaList[i],
                    orderMode,
                    null
            );
        }
        return result;
    }
}
