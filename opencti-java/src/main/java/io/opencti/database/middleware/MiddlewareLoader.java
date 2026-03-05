package io.opencti.database.middleware;

import io.opencti.database.elasticsearch.ElasticsearchClient;
import io.opencti.database.middleware.model.MiddlewareContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 中间件加载器
 * 原文件: database/middleware.js
 * 
 * 提供数据加载功能，包括批量加载、依赖解析、引用解析等。
 */
@Component
public class MiddlewareLoader {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareLoader.class);
    private static final int MAX_BATCH_SIZE = 100;
    private static final int ES_MAX_PAGINATION = 500;

    private final ElasticsearchClient elasticsearchClient;

    public MiddlewareLoader(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    /**
     * 批量加载器
     * 原文件: middleware.js:283-296 batchLoader
     * 
     * 创建一个 DataLoader 实例用于批量加载元素。
     */
    public <T> DataLoader<T> batchLoader(LoaderFunction<T> loader, MiddlewareContext context, Object user) {
        return new DataLoaderImpl<>(loader, context, user, MAX_BATCH_SIZE);
    }

    /**
     * 顶级实体或关系列表
     * 原文件: middleware.js:308-312 topEntitiesOrRelationsList
     */
    public List<Map<String, Object>> topEntitiesOrRelationsList(
            MiddlewareContext context, Object user,
            List<String> thingsTypes, Map<String, Object> args) {
        List<String> indices = args != null ? (List<String>) args.getOrDefault("indices", 
                Collections.singletonList("read_data_indices")) : Collections.singletonList("read_data_indices");
        Map<String, Object> paginateArgs = buildThingsFilters(thingsTypes, args);
        paginateArgs.put("connectionFormat", false);
        return elPaginate(context, user, indices, paginateArgs);
    }

    /**
     * 分页实体或关系连接
     * 原文件: middleware.js:313-317 pageEntitiesOrRelationsConnection
     */
    public Map<String, Object> pageEntitiesOrRelationsConnection(
            MiddlewareContext context, Object user,
            List<String> thingsTypes, Map<String, Object> args) {
        List<String> indices = args != null ? (List<String>) args.getOrDefault("indices", 
                Collections.singletonList("read_data_indices")) : Collections.singletonList("read_data_indices");
        Map<String, Object> paginateArgs = buildThingsFilters(thingsTypes, args);
        paginateArgs.put("connectionFormat", true);
        return elPaginateConnection(context, user, indices, paginateArgs);
    }

    /**
     * 完整实体或关系列表
     * 原文件: middleware.js:318-322 fullEntitiesOrRelationsList
     */
    public List<Map<String, Object>> fullEntitiesOrRelationsList(
            MiddlewareContext context, Object user,
            List<String> thingsTypes, Map<String, Object> args) {
        List<String> indices = args != null ? (List<String>) args.getOrDefault("indices", 
                Collections.singletonList("read_data_indices")) : Collections.singletonList("read_data_indices");
        Map<String, Object> paginateArgs = buildThingsFilters(thingsTypes, args);
        return elList(context, user, indices, paginateArgs);
    }

    /**
     * 完整实体或关系连接
     * 原文件: middleware.js:323-327 fullEntitiesOrRelationsConnection
     */
    public Map<String, Object> fullEntitiesOrRelationsConnection(
            MiddlewareContext context, Object user,
            List<String> thingsTypes, Map<String, Object> args) {
        List<String> indices = args != null ? (List<String>) args.getOrDefault("indices", 
                Collections.singletonList("read_data_indices")) : Collections.singletonList("read_data_indices");
        Map<String, Object> paginateArgs = buildThingsFilters(thingsTypes, args);
        return elConnection(context, user, indices, paginateArgs);
    }

    /**
     * 加载单个实体
     * 原文件: middleware.js:328-335 loadEntity
     */
    public Map<String, Object> loadEntity(
            MiddlewareContext context, Object user,
            List<String> entityTypes, Map<String, Object> args) {
        List<Map<String, Object>> entities = topEntitiesList(context, user, entityTypes, args);
        if (entities.size() > 1) {
            throw new RuntimeException("Expect only one response, entityTypes: " + entityTypes);
        }
        return entities.isEmpty() ? null : entities.get(0);
    }

    /**
     * 加载元素元数据依赖
     * 原文件: middleware.js:338-407 loadElementMetaDependencies
     * 
     * 加载元素的标记、标签等元数据依赖关系。
     */
    public Map<String, Map<String, Object>> loadElementMetaDependencies(
            MiddlewareContext context, Object user,
            List<Map<String, Object>> elements, Map<String, Object> args) {
        boolean onlyMarking = args != null ? (boolean) args.getOrDefault("onlyMarking", true) : true;

        List<Map<String, Object>> workingElements = elements;
        Map<String, Map<String, Object>> workingElementsMap = new HashMap<>();
        for (Map<String, Object> element : workingElements) {
            workingElementsMap.put((String) element.get("internal_id"), element);
        }
        List<String> workingIds = new ArrayList<>(workingElementsMap.keySet());

        List<String> relTypes = onlyMarking 
                ? Collections.singletonList("object-marking")
                : getStixRefRelationshipTypes();

        List<Map<String, Object>> refsRelations = new ArrayList<>();
        List<List<String>> groupOfWorkingIds = splitEvery(workingIds, ES_MAX_PAGINATION);

        for (List<String> fromIds : groupOfWorkingIds) {
            Map<String, Object> relationFilter = new HashMap<>();
            relationFilter.put("mode", "and");
            relationFilter.put("filters", Collections.singletonList(
                    Map.of("key", Collections.singletonList("fromId"), "values", fromIds)));
            relationFilter.put("filterGroups", Collections.emptyList());

            List<Map<String, Object>> relations = fullRelationsList(context, user, relTypes,
                    Map.of("baseData", true, "filters", relationFilter));
            refsRelations.addAll(relations);
        }

        Map<String, List<Map<String, Object>>> refsPerElements = new HashMap<>();
        for (Map<String, Object> r : refsRelations) {
            String fromId = (String) r.get("fromId");
            refsPerElements.computeIfAbsent(fromId, k -> new ArrayList<>()).add(r);
        }

        Set<String> toResolvedIds = new HashSet<>();
        Set<String> toResolvedTypes = new HashSet<>();
        for (Map<String, Object> rel : refsRelations) {
            toResolvedIds.add((String) rel.get("toId"));
            toResolvedTypes.add((String) rel.get("toType"));
        }

        Map<String, Map<String, Object>> toResolvedElements = elFindByIds(context, user,
                new ArrayList<>(toResolvedIds), Map.of("type", new ArrayList<>(toResolvedTypes), "toMap", true));

        Map<String, Map<String, Object>> loadedElementMap = new HashMap<>();

        for (Map.Entry<String, List<Map<String, Object>>> entry : refsPerElements.entrySet()) {
            String refId = entry.getKey();
            List<Map<String, Object>> dependencies = entry.getValue();
            Map<String, Object> element = workingElementsMap.get(refId);

            if (element != null) {
                Map<String, Object> data = new HashMap<>();
                Map<String, List<Map<String, Object>>> grouped = new HashMap<>();
                
                for (Map<String, Object> dep : dependencies) {
                    String entityType = (String) dep.get("entity_type");
                    grouped.computeIfAbsent(entityType, k -> new ArrayList<>()).add(dep);
                }

                for (Map.Entry<String, List<Map<String, Object>>> groupEntry : grouped.entrySet()) {
                    String key = groupEntry.getKey();
                    List<Map<String, Object>> values = groupEntry.getValue();
                    List<Map<String, Object>> invalidRelations = new ArrayList<>();
                    List<Map<String, Object>> resolvedElementsWithRelation = new ArrayList<>();

                    for (Map<String, Object> v : values) {
                        String toId = (String) v.get("toId");
                        Map<String, Object> resolvedElement = toResolvedElements.get(toId);
                        if (resolvedElement != null) {
                            Map<String, Object> elementWithRelation = new HashMap<>(resolvedElement);
                            elementWithRelation.put("i_relation", v);
                            resolvedElementsWithRelation.add(elementWithRelation);
                        } else {
                            Map<String, Object> invalid = new HashMap<>();
                            invalid.put("relation_id", v.get("id"));
                            invalid.put("target_id", toId);
                            invalidRelations.add(invalid);
                        }
                    }

                    if (!invalidRelations.isEmpty()) {
                        log.info("Targets of loadElementMetaDependencies not found: {}", invalidRelations);
                    }

                    String inputKey = convertDatabaseNameToInputName((String) element.get("entity_type"), key);
                    Map<String, Object> metaRefKey = getRelationRef((String) element.get("entity_type"), inputKey);
                    
                    if (metaRefKey == null) {
                        throw new UnsupportedOperationException(
                                "Schema validation failure when loading dependencies, key: " + key + 
                                ", inputKey: " + inputKey + ", type: " + element.get("entity_type"));
                    }

                    boolean multiple = metaRefKey.containsKey("multiple") && (boolean) metaRefKey.get("multiple");
                    
                    if (multiple) {
                        List<String> ids = new ArrayList<>();
                        for (Map<String, Object> r : resolvedElementsWithRelation) {
                            ids.add((String) r.get("internal_id"));
                        }
                        data.put(key, ids);
                        data.put(inputKey, resolvedElementsWithRelation);
                    } else {
                        Map<String, Object> first = resolvedElementsWithRelation.isEmpty() ? null : resolvedElementsWithRelation.get(0);
                        data.put(key, first != null ? first.get("internal_id") : null);
                        data.put(inputKey, first);
                    }
                }
                loadedElementMap.put(refId, data);
            }
        }

        return loadedElementMap;
    }

    /**
     * 加载元素及其依赖
     * 原文件: middleware.js:409-485 loadElementsWithDependencies
     */
    public List<Map<String, Object>> loadElementsWithDependencies(
            MiddlewareContext context, Object user,
            List<Map<String, Object>> elements, Map<String, Object> opts) {
        List<String> fileMarkings = new ArrayList<>();
        List<Map<String, Object>> elementsToDeps = new ArrayList<>(elements);
        List<String> targetsToResolved = new ArrayList<>();

        for (Map<String, Object> e : elements) {
            String baseType = (String) e.get("base_type");
            boolean isRelation = "relation".equals(baseType);
            
            if (isRelation) {
                Map<String, Object> fromElement = new HashMap<>();
                fromElement.put("internal_id", e.get("fromId"));
                fromElement.put("entity_type", e.get("fromType"));
                elementsToDeps.add(fromElement);

                Map<String, Object> toElement = new HashMap<>();
                toElement.put("internal_id", e.get("toId"));
                toElement.put("entity_type", e.get("toType"));
                elementsToDeps.add(toElement);

                targetsToResolved.add((String) e.get("fromId"));
                targetsToResolved.add((String) e.get("toId"));
            }

            List<Map<String, Object>> files = (List<Map<String, Object>>) e.get("x_opencti_files");
            if (files != null) {
                for (Map<String, Object> f : files) {
                    List<String> fMarkings = (List<String>) f.get("file_markings");
                    if (fMarkings != null) {
                        fileMarkings.addAll(fMarkings);
                    }
                }
            }
        }

        Map<String, Map<String, Object>> depsElementsMap = loadElementMetaDependencies(context, user, elementsToDeps, opts);

        Map<String, Map<String, Object>> fromAndToMap = new HashMap<>();
        if (!targetsToResolved.isEmpty()) {
            fromAndToMap = elFindByIds(context, getSystemUser(), targetsToResolved, Map.of("toMap", true));
        }

        Map<String, Map<String, Object>> fileMarkingsMap = new HashMap<>();
        if (!fileMarkings.isEmpty()) {
            fileMarkingsMap = elFindByIds(context, getSystemUser(), 
                    new ArrayList<>(new HashSet<>(fileMarkings)), 
                    Map.of("type", Collections.singletonList("Marking-Definition"), "toMap", true, "baseData", true));
        }

        List<Map<String, Object>> loadedElements = new ArrayList<>();

        for (Map<String, Object> element : elements) {
            List<Map<String, Object>> files = new ArrayList<>();
            List<Map<String, Object>> originalFiles = (List<Map<String, Object>>) element.get("x_opencti_files");
            
            if (originalFiles != null && !fileMarkingsMap.isEmpty()) {
                for (Map<String, Object> f : originalFiles) {
                    List<String> fMarkings = (List<String>) f.get("file_markings");
                    if (fMarkings != null) {
                        Map<String, Object> fileWithMarkings = new HashMap<>(f);
                        List<Map<String, Object>> resolvedMarkings = new ArrayList<>();
                        for (String m : fMarkings) {
                            Map<String, Object> resolved = fileMarkingsMap.get(m);
                            if (resolved != null) {
                                resolvedMarkings.add(resolved);
                            }
                        }
                        fileWithMarkings.put("objectMarking", resolvedMarkings);
                        files.add(fileWithMarkings);
                    } else {
                        files.add(f);
                    }
                }
            }

            Map<String, Object> deps = depsElementsMap.getOrDefault(element.get("id"), new HashMap<>());
            if (!files.isEmpty()) {
                deps.put("x_opencti_files", files);
            }

            String baseType = (String) element.get("base_type");
            boolean isRelation = "relation".equals(baseType);

            if (isRelation) {
                Map<String, Object> rawFrom = fromAndToMap.get(element.get("fromId"));
                Map<String, Object> rawTo = fromAndToMap.get(element.get("toId"));

                if (rawFrom == null || rawTo == null) {
                    String validFrom = rawFrom == null ? "invalid" : "valid";
                    String validTo = rawTo == null ? "invalid" : "valid";
                    String detail = "From " + element.get("fromId") + " is " + validFrom + 
                            ", To " + element.get("toId") + " is " + validTo;
                    log.warn("Auto delete of invalid relation, id: {}, detail: {}", element.get("id"), detail);
                    elDeleteElements(context, getSystemUser(), Collections.singletonList(element));
                } else {
                    Map<String, Object> from = new HashMap<>(rawFrom);
                    from.putAll(depsElementsMap.getOrDefault(element.get("fromId"), new HashMap<>()));
                    
                    Map<String, Object> to = new HashMap<>(rawTo);
                    to.putAll(depsElementsMap.getOrDefault(element.get("toId"), new HashMap<>()));

                    boolean canAccessFrom = isUserCanAccessStoreElement(context, user, from);
                    boolean canAccessTo = isUserCanAccessStoreElement(context, user, to);

                    if (canAccessFrom && canAccessTo) {
                        Map<String, Object> loaded = new HashMap<>(element);
                        loaded.put("from", from);
                        loaded.put("to", to);
                        loaded.putAll(deps);
                        loadedElements.add(loaded);
                    }
                }
            } else {
                Map<String, Object> loaded = new HashMap<>(element);
                loaded.putAll(deps);
                loadedElements.add(loaded);
            }
        }

        return loadedElements;
    }

    /**
     * 根据ID加载并解析依赖
     * 原文件: middleware.js:486-492 loadByIdsWithDependencies
     */
    public List<Map<String, Object>> loadByIdsWithDependencies(
            MiddlewareContext context, Object user,
            List<String> ids, Map<String, Object> opts) {
        Map<String, Map<String, Object>> elementsMap = elFindByIds(context, user, ids, opts);
        List<Map<String, Object>> elements = new ArrayList<>(elementsMap.values());
        if (!elements.isEmpty()) {
            return loadElementsWithDependencies(context, user, elements, opts);
        }
        return Collections.emptyList();
    }

    /**
     * 根据过滤条件加载并解析依赖
     * 原文件: middleware.js:493-501 loadByFiltersWithDependencies
     */
    public List<Map<String, Object>> loadByFiltersWithDependencies(
            MiddlewareContext context, Object user,
            List<String> types, Map<String, Object> args) {
        List<String> indices = args != null 
                ? (List<String>) args.getOrDefault("indices", Collections.singletonList("read_data_indices"))
                : Collections.singletonList("read_data_indices");
        Map<String, Object> paginateArgs = buildEntityFilters(types, args);
        List<Map<String, Object>> elements = elList(context, user, indices, paginateArgs);
        
        if (!elements.isEmpty()) {
            Map<String, Object> opts = new HashMap<>(args);
            opts.put("onlyMarking", false);
            return loadElementsWithDependencies(context, user, elements, opts);
        }
        return Collections.emptyList();
    }

    /**
     * 根据ID加载并解析引用
     * 原文件: middleware.js:503-507 storeLoadByIdsWithRefs
     */
    public List<Map<String, Object>> storeLoadByIdsWithRefs(
            MiddlewareContext context, Object user,
            List<String> ids, Map<String, Object> opts) {
        Map<String, Object> loadOpts = opts != null ? new HashMap<>(opts) : new HashMap<>();
        loadOpts.put("onlyMarking", false);
        return loadByIdsWithDependencies(context, user, ids, loadOpts);
    }

    /**
     * 根据ID加载单个元素并解析引用
     * 原文件: middleware.js:508-511 storeLoadByIdWithRefs
     */
    public Map<String, Object> storeLoadByIdWithRefs(
            MiddlewareContext context, Object user,
            String id, Map<String, Object> opts) {
        List<Map<String, Object>> elements = storeLoadByIdsWithRefs(context, user, 
                Collections.singletonList(id), opts);
        return elements.isEmpty() ? null : elements.get(0);
    }

    // ==================== 私有辅助方法 ====================

    private List<Map<String, Object>> topEntitiesList(
            MiddlewareContext context, Object user,
            List<String> entityTypes, Map<String, Object> args) {
        return topEntitiesOrRelationsList(context, user, entityTypes, args);
    }

    private List<Map<String, Object>> fullRelationsList(
            MiddlewareContext context, Object user,
            List<String> relTypes, Map<String, Object> args) {
        return fullEntitiesOrRelationsList(context, user, relTypes, args);
    }

    private Map<String, Object> buildThingsFilters(List<String> thingsTypes, Map<String, Object> args) {
        Map<String, Object> result = args != null ? new HashMap<>(args) : new HashMap<>();
        result.put("types", thingsTypes);
        return result;
    }

    private Map<String, Object> buildEntityFilters(List<String> types, Map<String, Object> args) {
        Map<String, Object> result = args != null ? new HashMap<>(args) : new HashMap<>();
        result.put("types", types);
        return result;
    }

    private <T> List<List<T>> splitEvery(List<T> list, int size) {
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            result.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return result;
    }

    private List<String> getStixRefRelationshipTypes() {
        return Collections.singletonList("stix-ref-relationship");
    }

    private String convertDatabaseNameToInputName(String entityType, String key) {
        return key;
    }

    private Map<String, Object> getRelationRef(String entityType, String inputKey) {
        return Map.of("multiple", true);
    }

    private Object getSystemUser() {
        return Map.of("id", "system");
    }

    private boolean isUserCanAccessStoreElement(MiddlewareContext context, Object user, Map<String, Object> element) {
        return true;
    }

    // ES 操作占位方法
    private List<Map<String, Object>> elPaginate(MiddlewareContext context, Object user, 
            List<String> indices, Map<String, Object> args) {
        return Collections.emptyList();
    }

    private Map<String, Object> elPaginateConnection(MiddlewareContext context, Object user, 
            List<String> indices, Map<String, Object> args) {
        return Map.of("edges", Collections.emptyList(), "pageInfo", Map.of());
    }

    private List<Map<String, Object>> elList(MiddlewareContext context, Object user, 
            List<String> indices, Map<String, Object> args) {
        return Collections.emptyList();
    }

    private Map<String, Object> elConnection(MiddlewareContext context, Object user, 
            List<String> indices, Map<String, Object> args) {
        return Map.of("edges", Collections.emptyList(), "pageInfo", Map.of());
    }

    public Map<String, Map<String, Object>> elFindByIds(MiddlewareContext context, Object user, 
            List<String> ids, Map<String, Object> opts) {
        return new HashMap<>();
    }

    private void elDeleteElements(MiddlewareContext context, Object user, List<Map<String, Object>> elements) {
    }

    // ==================== 内部接口和类 ====================

    /**
     * 加载器函数接口
     */
    @FunctionalInterface
    public interface LoaderFunction<T> {
        List<T> load(MiddlewareContext context, Object user, List<T> elements);
    }

    /**
     * 数据加载器接口
     */
    public interface DataLoader<T> {
        T load(T element);
    }

    /**
     * 数据加载器实现
     */
    private static class DataLoaderImpl<T> implements DataLoader<T> {
        private final LoaderFunction<T> loader;
        private final MiddlewareContext context;
        private final Object user;
        private final int maxBatchSize;
        private final Map<T, CompletableFuture<T>> cache = new ConcurrentHashMap<>();

        public DataLoaderImpl(LoaderFunction<T> loader, MiddlewareContext context, Object user, int maxBatchSize) {
            this.loader = loader;
            this.context = context;
            this.user = user;
            this.maxBatchSize = maxBatchSize;
        }

        @Override
        public T load(T element) {
            List<T> result = loader.load(context, user, Collections.singletonList(element));
            return result.isEmpty() ? null : result.get(0);
        }
    }
}
