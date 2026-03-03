package io.opencti.database.storage;

import java.util.Arrays;
import java.util.List;

/**
 * 文件存储常量
 * 重写自: opencti-platform/opencti-graphql/src/database/file-storage.ts
 * 
 * 原始常量定义 (行691-692):
 * export const ALL_ROOT_FOLDERS = [SUPPORT_STORAGE_PATH, IMPORT_STORAGE_PATH, EXPORT_STORAGE_PATH, FROM_TEMPLATE_STORAGE_PATH];
 * export const ALL_MERGEABLE_FOLDERS = [IMPORT_STORAGE_PATH, EXPORT_STORAGE_PATH, FROM_TEMPLATE_STORAGE_PATH];
 */
public final class FileStorageConstants {

    private FileStorageConstants() {
    }

    /**
     * 支持存储路径
     * 重写自: document-domain.ts - SUPPORT_STORAGE_PATH
     */
    public static final String SUPPORT_STORAGE_PATH = "support";

    /**
     * 导入存储路径
     * 重写自: document-domain.ts - IMPORT_STORAGE_PATH
     */
    public static final String IMPORT_STORAGE_PATH = "import";

    /**
     * 导出存储路径
     * 重写自: document-domain.ts - EXPORT_STORAGE_PATH
     */
    public static final String EXPORT_STORAGE_PATH = "export";

    /**
     * 模板存储路径
     * 重写自: document-domain.ts - FROM_TEMPLATE_STORAGE_PATH
     */
    public static final String FROM_TEMPLATE_STORAGE_PATH = "fromTemplate";

    /**
     * 嵌入存储路径
     * 重写自: document-domain.ts - EMBEDDED_STORAGE_PATH
     */
    public static final String EMBEDDED_STORAGE_PATH = "embedded";

    /**
     * 所有根文件夹
     * 重写自: file-storage.ts - ALL_ROOT_FOLDERS (行691)
     */
    public static final List<String> ALL_ROOT_FOLDERS = Arrays.asList(
            SUPPORT_STORAGE_PATH,
            IMPORT_STORAGE_PATH,
            EXPORT_STORAGE_PATH,
            FROM_TEMPLATE_STORAGE_PATH
    );

    /**
     * 所有可合并文件夹
     * 重写自: file-storage.ts - ALL_MERGEABLE_FOLDERS (行692)
     */
    public static final List<String> ALL_MERGEABLE_FOLDERS = Arrays.asList(
            IMPORT_STORAGE_PATH,
            EXPORT_STORAGE_PATH,
            FROM_TEMPLATE_STORAGE_PATH
    );

    /**
     * 默认验证模式
     * 重写自: file-storage.ts - defaultValidationMode (行43)
     */
    public static final String DEFAULT_VALIDATION_MODE = "workbench";

    /**
     * 特殊类型扩展
     * 重写自: file-storage.ts - specialTypesExtensions (行45-48)
     */
    public static final java.util.Map<String, String> SPECIAL_TYPES_EXTENSIONS = java.util.Map.of(
            "application/vnd.oasis.stix+json", "json",
            "application/vnd.mitre.navigator+json", "json"
    );

    /**
     * 上传状态 - 完成
     */
    public static final String UPLOAD_STATUS_COMPLETE = "complete";

    /**
     * 上传状态 - 进行中
     */
    public static final String UPLOAD_STATUS_PROGRESS = "progress";

    /**
     * 上传状态 - 错误
     */
    public static final String UPLOAD_STATUS_ERROR = "error";
}
