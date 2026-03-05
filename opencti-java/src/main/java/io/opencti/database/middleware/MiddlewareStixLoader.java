package io.opencti.database.middleware;

import io.opencti.database.middleware.model.MiddlewareContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * STIX 加载器
 * 原文件: database/middleware.js:512-563
 * 
 * 提供 STIX 格式数据加载功能，包括 STIX 2.0 和 STIX 2.1 格式转换。
 */
@Component
public class MiddlewareStixLoader {

    private static final Logger log = LoggerFactory.getLogger(MiddlewareStixLoader.class);

    private static final String STIX_VERSION_2_0 = "stix_2_0";
    private static final String STIX_VERSION_2_1 = "stix_2_1";
    private static final String STIX_EXT_OCTI = "extension-definition--x-opencti";

    private final MiddlewareLoader middlewareLoader;

    public MiddlewareStixLoader(MiddlewareLoader middlewareLoader) {
        this.middlewareLoader = middlewareLoader;
    }

    /**
     * 根据ID加载STIX对象
     * 原文件: middleware.js:512-516 stixLoadById
     */
    public Object stixLoadById(MiddlewareContext context, Object user, String id, Map<String, Object> opts) {
        Map<String, Object> instance = middlewareLoader.storeLoadByIdWithRefs(context, user, id, opts);
        if (instance == null) {
            return null;
        }
        String version = opts != null ? (String) opts.getOrDefault("version", STIX_VERSION_2_1) : STIX_VERSION_2_1;
        return convertStoreToStix(instance, version);
    }

    /**
     * 批量加载STIX对象
     * 原文件: middleware.js:531-546 stixLoadByIds
     */
    public List<Object> stixLoadByIds(MiddlewareContext context, Object user, 
            List<String> ids, Map<String, Object> opts) {
        boolean resolveStixFiles = opts != null && (boolean) opts.getOrDefault("resolveStixFiles", false);
        String version = opts != null ? (String) opts.getOrDefault("version", STIX_VERSION_2_1) : STIX_VERSION_2_1;

        List<Map<String, Object>> elements = middlewareLoader.storeLoadByIdsWithRefs(context, user, ids, opts);

        Map<String, Map<String, Object>> loadedInstancesMap = new HashMap<>();
        for (Map<String, Object> instance : elements) {
            List<String> instanceIds = extractIdsFromStoreObject(instance);
            for (String instanceId : instanceIds) {
                loadedInstancesMap.put(instanceId, instance);
            }
        }

        if (resolveStixFiles) {
            List<Object> results = new ArrayList<>();
            for (String id : ids) {
                Map<String, Object> instance = loadedInstancesMap.get(id);
                if (instance != null) {
                    results.add(convertStoreToStixWithResolvedFiles(instance, version));
                }
            }
            return results;
        }

        List<Object> results = new ArrayList<>();
        for (String id : ids) {
            Map<String, Object> instance = loadedInstancesMap.get(id);
            if (instance != null) {
                results.add(convertStoreToStix(instance, version));
            }
        }
        return results;
    }

    /**
     * 根据过滤条件加载STIX对象
     * 原文件: middleware.js:560-563 stixLoadByFilters
     */
    public List<Object> stixLoadByFilters(MiddlewareContext context, Object user,
            List<String> types, Map<String, Object> args) {
        List<Map<String, Object>> elements = middlewareLoader.loadByFiltersWithDependencies(context, user, types, args);
        if (elements == null || elements.isEmpty()) {
            return Collections.emptyList();
        }
        List<Object> results = new ArrayList<>();
        for (Map<String, Object> element : elements) {
            results.add(convertStoreToStix21(element));
        }
        return results;
    }

    /**
     * 获取STIX Bundle字符串
     * 原文件: middleware.js:547-553 stixBundleByIdStringify
     */
    public String stixBundleByIdStringify(MiddlewareContext context, Object user, String type, String id) {
        Object resolver = getBundleResolver(type);
        if (resolver == null) {
            return null;
        }
        return resolveBundle(context, user, type, id);
    }

    /**
     * 获取STIX对象字符串
     * 原文件: middleware.js:555-559 stixLoadByIdStringify
     */
    public String stixLoadByIdStringify(MiddlewareContext context, Object user, String id, Map<String, Object> opts) {
        String version = opts != null ? (String) opts.getOrDefault("version", STIX_VERSION_2_1) : STIX_VERSION_2_1;
        Object data = stixLoadById(context, user, id, Map.of("version", version));
        return data != null ? toJsonString(data) : "";
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 将存储对象转换为STIX格式
     * 原文件: middleware.js:convertStoreToStix
     */
    private Object convertStoreToStix(Map<String, Object> instance, String version) {
        if (STIX_VERSION_2_0.equals(version)) {
            return convertStoreToStix20(instance);
        }
        return convertStoreToStix21(instance);
    }

    /**
     * 将存储对象转换为STIX 2.0格式
     */
    private Object convertStoreToStix20(Map<String, Object> instance) {
        Map<String, Object> stix = new HashMap<>(instance);
        stix.put("type", instance.get("entity_type"));
        stix.remove("internal_id");
        stix.remove("standard_id");
        return stix;
    }

    /**
     * 将存储对象转换为STIX 2.1格式
     */
    private Object convertStoreToStix21(Map<String, Object> instance) {
        Map<String, Object> stix = new HashMap<>(instance);
        stix.put("type", instance.get("entity_type"));
        
        Map<String, Object> extensions = new HashMap<>();
        Map<String, Object> octiExtension = new HashMap<>();
        octiExtension.put("id", instance.get("internal_id"));
        octiExtension.put("standard_id", instance.get("standard_id"));
        octiExtension.put("type", instance.get("entity_type"));
        
        Object files = instance.get("x_opencti_files");
        if (files != null) {
            octiExtension.put("files", files);
        }
        
        extensions.put(STIX_EXT_OCTI, octiExtension);
        stix.put("extensions", extensions);
        
        stix.remove("internal_id");
        stix.remove("standard_id");
        
        return stix;
    }

    /**
     * 将存储对象转换为STIX格式并解析文件
     * 原文件: middleware.js:517-530 convertStoreToStixWithResolvedFiles
     */
    private Object convertStoreToStixWithResolvedFiles(Map<String, Object> instance, String version) {
        Object instanceInStix = convertStoreToStix(instance, version);
        
        if (instanceInStix instanceof Map) {
            Map<String, Object> stixMap = (Map<String, Object>) instanceInStix;
            Map<String, Object> extensions = (Map<String, Object>) stixMap.get("extensions");
            if (extensions != null) {
                List<Map<String, Object>> files = (List<Map<String, Object>>) extensions.get("files");
                if (files != null) {
                    for (Map<String, Object> file : files) {
                        String uri = (String) file.get("uri");
                        if (uri != null) {
                            String fileId = uri.replace("/storage/get/", "");
                            file.put("data", getFileContent(fileId, "base64"));
                            file.put("no_trigger_import", true);
                        }
                    }
                }
            }
        }
        
        return instanceInStix;
    }

    /**
     * 从存储对象中提取所有ID
     * 原文件: middleware.js:extractIdsFromStoreObject
     */
    private List<String> extractIdsFromStoreObject(Map<String, Object> instance) {
        List<String> ids = new ArrayList<>();
        
        String internalId = (String) instance.get("internal_id");
        if (internalId != null) {
            ids.add(internalId);
        }
        
        String standardId = (String) instance.get("standard_id");
        if (standardId != null && !standardId.equals(internalId)) {
            ids.add(standardId);
        }
        
        String stixId = (String) instance.get("stix_id");
        if (stixId != null && !stixId.equals(internalId) && !stixId.equals(standardId)) {
            ids.add(stixId);
        }
        
        return ids;
    }

    /**
     * 获取Bundle解析器
     */
    private Object getBundleResolver(String type) {
        return null;
    }

    /**
     * 解析Bundle
     */
    private String resolveBundle(MiddlewareContext context, Object user, String type, String id) {
        return null;
    }

    /**
     * 获取文件内容
     */
    private String getFileContent(String fileId, String encoding) {
        return null;
    }

    /**
     * 转换为JSON字符串
     */
    private String toJsonString(Object data) {
        return data.toString();
    }
}
