package io.opencti.database.elasticsearch.query;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ES查询构建器
 * 重写自: opencti-platform/opencti-graphql/src/database/engine.ts
 * 
 * 提供查询条件的构建方法
 */
public class QueryBuilder {

    private final List<Query> mustQueries = new ArrayList<>();
    private final List<Query> mustNotQueries = new ArrayList<>();
    private final List<Query> shouldQueries = new ArrayList<>();
    private final List<Query> filterQueries = new ArrayList<>();
    private Integer minimumShouldMatch;

    /**
     * 添加must条件
     */
    public QueryBuilder must(Query query) {
        mustQueries.add(query);
        return this;
    }

    /**
     * 添加must_not条件
     */
    public QueryBuilder mustNot(Query query) {
        mustNotQueries.add(query);
        return this;
    }

    /**
     * 添加should条件
     */
    public QueryBuilder should(Query query) {
        shouldQueries.add(query);
        return this;
    }

    /**
     * 添加filter条件
     */
    public QueryBuilder filter(Query query) {
        filterQueries.add(query);
        return this;
    }

    /**
     * 设置minimum_should_match
     */
    public QueryBuilder minimumShouldMatch(int value) {
        this.minimumShouldMatch = value;
        return this;
    }

    /**
     * 构建bool查询
     * 重写自: engine.ts - buildLocalMustFilter() (行2136-2300)
     */
    public Query build() {
        BoolQuery.Builder boolBuilder = new BoolQuery.Builder();
        
        if (!mustQueries.isEmpty()) {
            boolBuilder.must(mustQueries);
        }
        if (!mustNotQueries.isEmpty()) {
            boolBuilder.mustNot(mustNotQueries);
        }
        if (!shouldQueries.isEmpty()) {
            boolBuilder.should(shouldQueries);
            if (minimumShouldMatch != null) {
                boolBuilder.minimumShouldMatch(String.valueOf(minimumShouldMatch));
            }
        }
        if (!filterQueries.isEmpty()) {
            boolBuilder.filter(filterQueries);
        }
        
        return Query.of(q -> q.bool(boolBuilder.build()));
    }

    // ==================== 静态工厂方法 ====================

    /**
     * 创建match_all查询
     */
    public static Query matchAll() {
        return Query.of(q -> q.matchAll(m -> m));
    }

    /**
     * 创建term查询
     * 重写自: engine.ts - term查询
     */
    public static Query term(String field, String value) {
        return Query.of(q -> q.term(t -> t.field(field).value(value)));
    }

    /**
     * 创建term查询（数值）
     */
    public static Query term(String field, long value) {
        return Query.of(q -> q.term(t -> t.field(field).value(value)));
    }

    /**
     * 创建term查询（布尔值，兼容测试代码）
     */
    public static Query term(String field, boolean value) {
        return Query.of(q -> q.term(t -> t.field(field).value(value)));
    }

    /**
     * 创建terms查询
     * 重写自: engine.ts - terms查询
     * ES 8.x需要将List<String>转换为List<FieldValue>
     */
    public static Query terms(String field, List<String> values) {
        List<FieldValue> fieldValues = values.stream()
            .map(FieldValue::of)
            .toList();
        return Query.of(q -> q.terms(t -> t.field(field).terms(ts -> ts.value(fieldValues))));
    }

    /**
     * 创建range查询
     * ES 8.x使用untyped()方法构建range查询
     */
    public static Query range(String field, Object gte, Object lte) {
        return Query.of(q -> q.range(r -> r
            .untyped(u -> {
                u.field(field);
                if (gte != null) {
                    u.gte(JsonData.of(gte));
                }
                if (lte != null) {
                    u.lte(JsonData.of(lte));
                }
                return u;
            })
        ));
    }

    /**
     * 创建exists查询
     */
    public static Query exists(String field) {
        return Query.of(q -> q.exists(e -> e.field(field)));
    }

    /**
     * 创建match查询
     */
    public static Query match(String field, String query) {
        return Query.of(q -> q.match(m -> m.field(field).query(query)));
    }

    /**
     * 创建wildcard查询
     * 重写自: engine.ts - wildcard查询
     */
    public static Query wildcard(String field, String value) {
        return Query.of(q -> q.wildcard(w -> w.field(field).value(value)));
    }

    /**
     * 创建prefix查询
     */
    public static Query prefix(String field, String value) {
        return Query.of(q -> q.prefix(p -> p.field(field).value(value)));
    }

    /**
     * 创建regexp查询
     */
    public static Query regexp(String field, String value) {
        return Query.of(q -> q.regexp(r -> r.field(field).value(value)));
    }

    /**
     * 创建nested查询
     * 重写自: engine.ts - nested查询 (行605-614)
     */
    public static Query nested(String path, Query query) {
        return Query.of(q -> q.nested(n -> n.path(path).query(query)));
    }

    /**
     * 创建bool查询（无参版本，兼容测试代码）
     */
    public static Query bool() {
        return Query.of(q -> q.bool(b -> b));
    }

    /**
     * 创建bool查询
     */
    public static Query bool(List<Query> must, List<Query> mustNot, List<Query> should) {
        BoolQuery.Builder builder = new BoolQuery.Builder();
        if (must != null && !must.isEmpty()) {
            builder.must(must);
        }
        if (mustNot != null && !mustNot.isEmpty()) {
            builder.mustNot(mustNot);
        }
        if (should != null && !should.isEmpty()) {
            builder.should(should);
        }
        return Query.of(q -> q.bool(builder.build()));
    }

    /**
     * 创建ids查询
     */
    public static Query ids(List<String> ids) {
        return Query.of(q -> q.ids(i -> i.values(ids)));
    }

    /**
     * 创建query_string查询
     * 重写自: engine.ts - query_string查询
     */
    public static Query queryString(String query, List<String> fields) {
        return Query.of(q -> q.queryString(qs -> qs.query(query).fields(fields)));
    }

    /**
     * 创建multi_match查询
     */
    public static Query multiMatch(String query, List<String> fields) {
        return Query.of(q -> q.multiMatch(mm -> mm.query(query).fields(fields)));
    }

    /**
     * 创建has_child查询
     */
    public static Query hasChild(String type, Query query) {
        return Query.of(q -> q.hasChild(hc -> hc.type(type).query(query)));
    }

    /**
     * 创建has_parent查询
     */
    public static Query hasParent(String parentType, Query query) {
        return Query.of(q -> q.hasParent(hp -> hp.parentType(parentType).query(query)));
    }

    /**
     * 构建全文搜索should条件
     * 重写自: engine.ts - elGenerateFullTextSearchShould() (行2008-2086)
     * 
     * @param search 搜索字符串
     * @param fields 搜索字段列表
     * @return should查询列表
     */
    public static List<Query> generateFullTextSearchShould(String search, List<String> fields) {
        List<Query> shouldQueries = new ArrayList<>();
        
        if (search == null || search.isEmpty()) {
            return shouldQueries;
        }
        
        // 转义特殊字符
        String escapedSearch = specialElasticCharsEscape(search);
        
        // 添加各字段的匹配查询
        for (String field : fields) {
            // 精确匹配
            shouldQueries.add(Query.of(q -> q.term(t -> t.field(field + ".keyword").value(escapedSearch))));
            // 通配符匹配
            shouldQueries.add(Query.of(q -> q.wildcard(w -> w.field(field + ".keyword").value("*" + escapedSearch.toLowerCase() + "*"))));
        }
        
        return shouldQueries;
    }

    /**
     * 特殊字符转义
     * 重写自: engine.ts - specialElasticCharsEscape() (行1703-1719)
     * 
     * @param query 原始查询字符串
     * @return 转义后的字符串
     */
    public static String specialElasticCharsEscape(String query) {
        if (query == null) {
            return "";
        }
        
        // ES特殊字符: \+-=&|><!(){}[]^"~*?:\\/
        return query
                .replace("\\", "\\\\")
                .replace("+", "\\+")
                .replace("-", "\\-")
                .replace("=", "\\=")
                .replace("&", "\\&")
                .replace("|", "\\|")
                .replace(">", "\\>")
                .replace("<", "\\<")
                .replace("!", "\\!")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("^", "\\^")
                .replace("\"", "\\\"")
                .replace("~", "\\~")
                .replace("*", "\\*")
                .replace("?", "\\?")
                .replace(":", "\\:")
                .replace("/", "\\/");
    }

    /**
     * 构建空查询
     */
    public static Query empty() {
        return matchAll();
    }

    // ==================== 测试代码兼容方法 ====================

    /**
     * 添加must条件（兼容测试代码）
     */
    public static Query addMust(Query boolQuery, Query mustQuery) {
        if (boolQuery == null || boolQuery.bool() == null) {
            return Query.of(q -> q.bool(b -> b.must(mustQuery)));
        }
        BoolQuery original = boolQuery.bool();
        BoolQuery.Builder builder = new BoolQuery.Builder();
        if (original.must() != null) {
            builder.must(original.must());
        }
        builder.must(mustQuery);
        if (original.mustNot() != null) {
            builder.mustNot(original.mustNot());
        }
        if (original.should() != null) {
            builder.should(original.should());
        }
        if (original.filter() != null) {
            builder.filter(original.filter());
        }
        return Query.of(q -> q.bool(builder.build()));
    }

    /**
     * 添加should条件（兼容测试代码）
     */
    public static Query addShould(Query boolQuery, Query shouldQuery) {
        if (boolQuery == null || boolQuery.bool() == null) {
            return Query.of(q -> q.bool(b -> b.should(shouldQuery)));
        }
        BoolQuery original = boolQuery.bool();
        BoolQuery.Builder builder = new BoolQuery.Builder();
        if (original.must() != null) {
            builder.must(original.must());
        }
        if (original.mustNot() != null) {
            builder.mustNot(original.mustNot());
        }
        if (original.should() != null) {
            builder.should(original.should());
        }
        builder.should(shouldQuery);
        if (original.filter() != null) {
            builder.filter(original.filter());
        }
        return Query.of(q -> q.bool(builder.build()));
    }

    /**
     * 添加must_not条件（兼容测试代码）
     */
    public static Query addMustNot(Query boolQuery, Query mustNotQuery) {
        if (boolQuery == null || boolQuery.bool() == null) {
            return Query.of(q -> q.bool(b -> b.mustNot(mustNotQuery)));
        }
        BoolQuery original = boolQuery.bool();
        BoolQuery.Builder builder = new BoolQuery.Builder();
        if (original.must() != null) {
            builder.must(original.must());
        }
        if (original.mustNot() != null) {
            builder.mustNot(original.mustNot());
        }
        builder.mustNot(mustNotQuery);
        if (original.should() != null) {
            builder.should(original.should());
        }
        if (original.filter() != null) {
            builder.filter(original.filter());
        }
        return Query.of(q -> q.bool(builder.build()));
    }

    /**
     * 添加filter条件（兼容测试代码）
     */
    public static Query addFilter(Query boolQuery, Query filterQuery) {
        if (boolQuery == null || boolQuery.bool() == null) {
            return Query.of(q -> q.bool(b -> b.filter(filterQuery)));
        }
        BoolQuery original = boolQuery.bool();
        BoolQuery.Builder builder = new BoolQuery.Builder();
        if (original.must() != null) {
            builder.must(original.must());
        }
        if (original.mustNot() != null) {
            builder.mustNot(original.mustNot());
        }
        if (original.should() != null) {
            builder.should(original.should());
        }
        if (original.filter() != null) {
            builder.filter(original.filter());
        }
        builder.filter(filterQuery);
        return Query.of(q -> q.bool(builder.build()));
    }
}
