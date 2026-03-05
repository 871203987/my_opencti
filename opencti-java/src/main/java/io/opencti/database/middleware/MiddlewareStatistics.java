package io.opencti.database.middleware;

import io.opencti.database.middleware.model.MiddlewareContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 中间件统计模块
 * 原文件: database/middleware.js:630-742
 * 
 * 提供时间序列和分布统计功能。
 */
@Component
public class MiddlewareStatistics {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareStatistics.class);

    private static final String ID_INTERNAL = "internal_id";
    private static final String REL_INDEX_PREFIX = "rel_";
    private static final int DEFAULT_LIMIT = 10;
    private static final int DEFAULT_RELATION_LIMIT = 50;

    private final MiddlewareLoader middlewareLoader;

    public MiddlewareStatistics(MiddlewareLoader middlewareLoader) {
        this.middlewareLoader = middlewareLoader;
    }

    /**
     * 历史时间序列
     * 原文件: middleware.js:630-634 timeSeriesHistory
     */
    public List<Map<String, Object>> timeSeriesHistory(
            MiddlewareContext context, Object user,
            List<String> types, Map<String, Object> args) {
        Date startDate = args != null ? (Date) args.get("startDate") : null;
        Date endDate = args != null ? (Date) args.get("endDate") : null;
        String interval = args != null ? (String) args.getOrDefault("interval", "day") : "day";

        List<Map<String, Object>> histogramData = elHistogramCount(context, user, 
                Collections.singletonList("read_index_history"), args);
        return fillTimeSeries(startDate, endDate, interval, histogramData);
    }

    /**
     * 实体时间序列
     * 原文件: middleware.js:635-640 timeSeriesEntities
     */
    public List<Map<String, Object>> timeSeriesEntities(
            MiddlewareContext context, Object user,
            List<String> types, Map<String, Object> args) {
        Map<String, Object> timeSeriesArgs = buildEntityFilters(types, args);
        
        List<String> indices;
        if (args != null && Boolean.TRUE.equals(args.get("onlyInferred"))) {
            indices = Collections.singletonList("read_data_indices_inferred");
        } else {
            indices = Collections.singletonList("read_data_indices");
        }
        
        List<Map<String, Object>> histogramData = elHistogramCount(context, user, indices, timeSeriesArgs);
        
        Date startDate = args != null ? (Date) args.get("startDate") : null;
        Date endDate = args != null ? (Date) args.get("endDate") : null;
        String interval = args != null ? (String) args.getOrDefault("interval", "day") : "day";
        
        return fillTimeSeries(startDate, endDate, interval, histogramData);
    }

    /**
     * 关系时间序列
     * 原文件: middleware.js:641-647 timeSeriesRelations
     */
    public List<Map<String, Object>> timeSeriesRelations(
            MiddlewareContext context, Object user, Map<String, Object> args) {
        Date startDate = args != null ? (Date) args.get("startDate") : null;
        Date endDate = args != null ? (Date) args.get("endDate") : null;
        String interval = args != null ? (String) args.getOrDefault("interval", "day") : "day";
        
        List<String> relationshipTypes;
        if (args != null && args.get("relationship_type") != null) {
            Object rt = args.get("relationship_type");
            if (rt instanceof List) {
                relationshipTypes = (List<String>) rt;
            } else {
                relationshipTypes = Collections.singletonList(rt.toString());
            }
        } else {
            relationshipTypes = Arrays.asList("stix-core-relationship", "object", "stix-sighting-relationship");
        }

        Map<String, Object> timeSeriesArgs = buildEntityFilters(relationshipTypes, args);
        
        List<String> indices;
        if (args != null && Boolean.TRUE.equals(args.get("onlyInferred"))) {
            indices = Collections.singletonList("index_inferred_relationships");
        } else {
            indices = Collections.singletonList("read_relationships_indices");
        }
        
        List<Map<String, Object>> histogramData = elHistogramCount(context, user, indices, timeSeriesArgs);
        return fillTimeSeries(startDate, endDate, interval, histogramData);
    }

    /**
     * 历史分布统计
     * 原文件: middleware.js:648-682 distributionHistory
     */
    public List<Map<String, Object>> distributionHistory(
            MiddlewareContext context, Object user,
            List<String> types, Map<String, Object> args) {
        int limit = args != null ? (int) args.getOrDefault("limit", DEFAULT_LIMIT) : DEFAULT_LIMIT;
        String order = args != null ? (String) args.getOrDefault("order", "desc") : "desc";
        String field = args != null ? (String) args.get("field") : null;

        if (field == null) {
            throw new IllegalArgumentException("Field is required for distribution");
        }

        if (field.contains(".") && !field.endsWith("internal_id") && 
                !field.contains("context_data") && !field.contains("opinions_metrics")) {
            throw new IllegalArgumentException(
                    "Distribution entities does not support relation aggregation field: " + field);
        }

        String finalField = field;
        if (field.contains(".") && !field.contains("context_data") && !field.contains("opinions_metrics")) {
            finalField = REL_INDEX_PREFIX + field;
        }
        if ("name".equals(field)) {
            finalField = ID_INTERNAL;
        }

        Map<String, Object> aggregationArgs = new HashMap<>();
        if (args != null) {
            aggregationArgs.putAll(args);
        }
        aggregationArgs.put("field", finalField);

        List<Map<String, Object>> distributionData = elAggregationCount(context, user,
                Collections.singletonList("read_index_history"), aggregationArgs);

        Comparator<Map<String, Object>> orderingFunction = "asc".equals(order) 
                ? Comparator.comparing(m -> (Long) m.get("value"))
                : Comparator.comparing(m -> (Long) m.get("value"), Comparator.reverseOrder());

        if (field.contains(ID_INTERNAL) || "creator_id".equals(field) || "user_id".equals(field) || 
                "group_ids".equals(field) || "organization_ids".equals(field) || field.contains(".id") || 
                field.contains("_id")) {
            return convertAggregateDistributions(context, user, limit, orderingFunction, distributionData);
        }

        if ("name".equals(field) || "context_data.id".equals(field)) {
            List<Map<String, Object>> hits = convertAggregateDistributions(context, user, limit, orderingFunction, distributionData);
            List<Map<String, Object>> result = new ArrayList<>();
            for (Map<String, Object> hit : hits) {
                Map<String, Object> entity = (Map<String, Object>) hit.get("entity");
                Map<String, Object> item = new HashMap<>();
                item.put("label", entity != null ? entity.getOrDefault("name", 
                        extractEntityRepresentativeName(entity)) : hit.get("label"));
                item.put("value", hit.get("value"));
                item.put("entity", entity);
                result.add(item);
            }
            return result;
        }

        return distributionData.stream()
                .sorted(orderingFunction)
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * 实体分布统计
     * 原文件: middleware.js:683-721 distributionEntities
     */
    public List<Map<String, Object>> distributionEntities(
            MiddlewareContext context, Object user,
            List<String> types, Map<String, Object> args) {
        Map<String, Object> distributionArgs = buildEntityFilters(types, args);
        
        int limit = args != null ? (int) args.getOrDefault("limit", DEFAULT_LIMIT) : DEFAULT_LIMIT;
        String order = args != null ? (String) args.getOrDefault("order", "desc") : "desc";
        String field = args != null ? (String) args.get("field") : null;

        if (field == null) {
            throw new IllegalArgumentException("Field is required for distribution");
        }

        boolean aggregationNotSupported = field.contains(".") 
                && !field.endsWith("internal_id")
                && !field.contains("opinions_metrics");

        if (aggregationNotSupported) {
            throw new IllegalArgumentException(
                    "Distribution entities does not support relation aggregation field: " + field);
        }

        String finalField = field;
        if (field.contains(".") && !field.contains("opinions_metrics")) {
            finalField = REL_INDEX_PREFIX + field;
        }
        if ("name".equals(field)) {
            finalField = ID_INTERNAL;
        }

        Map<String, Object> aggregationArgs = new HashMap<>(distributionArgs);
        aggregationArgs.put("field", finalField);

        List<String> indices;
        if (args != null && Boolean.TRUE.equals(args.get("onlyInferred"))) {
            indices = Collections.singletonList("read_data_indices_inferred");
        } else {
            indices = Collections.singletonList("read_data_indices");
        }

        List<Map<String, Object>> distributionData = elAggregationCount(context, user, indices, aggregationArgs);

        Comparator<Map<String, Object>> orderingFunction = "asc".equals(order) 
                ? Comparator.comparing(m -> (Long) m.get("value"))
                : Comparator.comparing(m -> (Long) m.get("value"), Comparator.reverseOrder());

        if (field.contains(ID_INTERNAL) || "creator_id".equals(field) || "x_opencti_workflow_id".equals(field)) {
            return convertAggregateDistributions(context, user, limit, orderingFunction, distributionData);
        }

        if ("name".equals(field)) {
            List<Map<String, Object>> hits = convertAggregateDistributions(context, user, limit, orderingFunction, distributionData);
            List<Map<String, Object>> result = new ArrayList<>();
            for (Map<String, Object> hit : hits) {
                Map<String, Object> entity = (Map<String, Object>) hit.get("entity");
                Map<String, Object> item = new HashMap<>();
                item.put("label", entity != null ? entity.getOrDefault("name", 
                        extractEntityRepresentativeName(entity)) : hit.get("label"));
                item.put("value", hit.get("value"));
                item.put("entity", entity);
                result.add(item);
            }
            return result;
        }

        return distributionData.stream()
                .sorted(orderingFunction)
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * 关系分布统计
     * 原文件: middleware.js:722-742 distributionRelations
     */
    public List<Map<String, Object>> distributionRelations(
            MiddlewareContext context, Object user, Map<String, Object> args) {
        String field = args != null ? (String) args.get("field") : null;
        
        if (field == null) {
            throw new IllegalArgumentException("Field is required for distribution");
        }

        int limit = args != null ? (int) args.getOrDefault("limit", DEFAULT_RELATION_LIMIT) : DEFAULT_RELATION_LIMIT;
        String order = args != null ? (String) args.getOrDefault("order", "desc") : "desc";
        String dateAttribute = args != null ? (String) args.getOrDefault("dateAttribute", "created_at") : "created_at";
        
        List<String> relationshipTypes;
        if (args != null && args.get("relationship_type") != null) {
            Object rt = args.get("relationship_type");
            if (rt instanceof List) {
                relationshipTypes = (List<String>) rt;
            } else {
                relationshipTypes = Collections.singletonList(rt.toString());
            }
        } else {
            relationshipTypes = Collections.singletonList("stix-core-relationship");
        }

        String finalField = field;
        if (field.contains(".") && !field.contains("pirExplanation.name")) {
            finalField = REL_INDEX_PREFIX + field;
        }

        Map<String, Object> opts = new HashMap<>();
        if (args != null) {
            opts.putAll(args);
        }
        opts.put("dateAttribute", dateAttribute);
        opts.put("field", finalField);

        Map<String, Object> distributionArgs = buildAggregationRelationFilter(relationshipTypes, opts);

        List<String> indices;
        if (args != null && Boolean.TRUE.equals(args.get("onlyInferred"))) {
            indices = Collections.singletonList("read_index_inferred_relationships");
        } else {
            indices = Collections.singletonList("read_relationships_indices");
        }

        List<Map<String, Object>> distributionData = elAggregationRelationsCount(context, user, indices, distributionArgs);

        Comparator<Map<String, Object>> orderingFunction = "asc".equals(order) 
                ? Comparator.comparing(m -> (Long) m.get("value"))
                : Comparator.comparing(m -> (Long) m.get("value"), Comparator.reverseOrder());

        if (field.contains(ID_INTERNAL) || "creator_id".equals(field) || 
                "x_opencti_workflow_id".equals(field) || field.contains("author_id")) {
            return convertAggregateDistributions(context, user, limit, orderingFunction, distributionData);
        }

        return distributionData.stream()
                .sorted(orderingFunction)
                .limit(limit)
                .collect(Collectors.toList());
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 填充时间序列
     * 原文件: middleware.js:fillTimeSeries
     */
    private List<Map<String, Object>> fillTimeSeries(Date startDate, Date endDate, 
            String interval, List<Map<String, Object>> histogramData) {
        if (startDate == null || endDate == null) {
            return histogramData;
        }

        List<Map<String, Object>> result = new ArrayList<>();
        LocalDateTime start = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        
        ChronoUnit chronoUnit = getChronoUnit(interval);
        LocalDateTime current = start;
        
        Map<String, Long> histogramMap = new HashMap<>();
        for (Map<String, Object> item : histogramData) {
            String date = (String) item.get("date");
            Long value = (Long) item.get("value");
            if (date != null && value != null) {
                histogramMap.put(date, value);
            }
        }

        DateTimeFormatter formatter = getFormatter(interval);
        
        while (!current.isAfter(end)) {
            String dateKey = current.format(formatter);
            Map<String, Object> item = new HashMap<>();
            item.put("date", dateKey);
            item.put("value", histogramMap.getOrDefault(dateKey, 0L));
            result.add(item);
            current = current.plus(1, chronoUnit);
        }

        return result;
    }

    private ChronoUnit getChronoUnit(String interval) {
        return switch (interval) {
            case "year" -> ChronoUnit.YEARS;
            case "month" -> ChronoUnit.MONTHS;
            case "week" -> ChronoUnit.WEEKS;
            case "hour" -> ChronoUnit.HOURS;
            case "minute" -> ChronoUnit.MINUTES;
            default -> ChronoUnit.DAYS;
        };
    }

    private DateTimeFormatter getFormatter(String interval) {
        return switch (interval) {
            case "year" -> DateTimeFormatter.ofPattern("yyyy");
            case "month" -> DateTimeFormatter.ofPattern("yyyy-MM");
            case "week" -> DateTimeFormatter.ofPattern("yyyy-ww");
            case "hour" -> DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH");
            case "minute" -> DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            default -> DateTimeFormatter.ofPattern("yyyy-MM-dd");
        };
    }

    /**
     * 构建实体过滤器
     */
    private Map<String, Object> buildEntityFilters(List<String> types, Map<String, Object> args) {
        Map<String, Object> result = args != null ? new HashMap<>(args) : new HashMap<>();
        result.put("types", types);
        return result;
    }

    /**
     * 构建关系聚合过滤器
     */
    private Map<String, Object> buildAggregationRelationFilter(List<String> types, Map<String, Object> args) {
        Map<String, Object> result = args != null ? new HashMap<>(args) : new HashMap<>();
        result.put("types", types);
        return result;
    }

    /**
     * 转换聚合分布
     * 原文件: middleware.js:600-628 convertAggregateDistributions
     */
    private List<Map<String, Object>> convertAggregateDistributions(
            MiddlewareContext context, Object user, int limit,
            Comparator<Map<String, Object>> orderingFunction,
            List<Map<String, Object>> distributionData) {
        
        List<Map<String, Object>> sorted = distributionData.stream()
                .sorted(orderingFunction)
                .limit(limit)
                .collect(Collectors.toList());

        List<String> labels = sorted.stream()
                .map(d -> (String) d.get("label"))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, Map<String, Object>> resolvedLabels = elFindByIds(context, getSystemUser(), 
                labels, Map.of("toMap", true));

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> d : sorted) {
            String label = (String) d.get("label");
            if (label != null) {
                Map<String, Object> entity = resolvedLabels.get(label.toLowerCase());
                if (entity != null) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("label", label);
                    item.put("value", d.get("value"));
                    item.put("entity", entity);
                    result.add(item);
                }
            }
        }

        return result;
    }

    private String extractEntityRepresentativeName(Map<String, Object> entity) {
        if (entity == null) return "Unknown";
        Object name = entity.get("name");
        if (name != null) return name.toString();
        Object id = entity.get("id");
        return id != null ? id.toString() : "Unknown";
    }

    private Object getSystemUser() {
        return Map.of("id", "system");
    }

    // ES 操作占位方法
    private List<Map<String, Object>> elHistogramCount(MiddlewareContext context, Object user,
            List<String> indices, Map<String, Object> args) {
        return Collections.emptyList();
    }

    private List<Map<String, Object>> elAggregationCount(MiddlewareContext context, Object user,
            List<String> indices, Map<String, Object> args) {
        return Collections.emptyList();
    }

    private List<Map<String, Object>> elAggregationRelationsCount(MiddlewareContext context, Object user,
            List<String> indices, Map<String, Object> args) {
        return Collections.emptyList();
    }

    private Map<String, Map<String, Object>> elFindByIds(MiddlewareContext context, Object user,
            List<String> ids, Map<String, Object> opts) {
        return new HashMap<>();
    }
}
