package io.opencti.database.storage;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 文件存储服务接口
 * 重写自: opencti-platform/opencti-graphql/src/database/file-storage.ts
 * 
 * 提供高层文件存储操作，包括:
 * - 文件加载、删除、复制
 * - 文件上传和导入作业
 * - 文件列表和元数据管理
 */
public interface FileStorageService {

    /**
     * 加载文件
     * 重写自: file-storage.ts - loadFile() (行101-175)
     * 
     * 从数据库获取文件元数据，或从S3获取
     * @param fileS3Path 文件S3路径
     * @param opts 选项
     * @return 加载的文件信息
     */
    LoadedFile loadFile(String fileS3Path, LoadFileOptions opts);

    /**
     * 删除文件
     * 重写自: file-storage.ts - deleteFile() (行186-209)
     * 
     * 从存储中删除文件，移除关联的工作和索引
     * @param id 文件ID
     * @return 被删除的文件信息
     */
    LoadedFile deleteFile(String id);

    /**
     * 批量删除文件
     * 重写自: file-storage.ts - deleteFiles() (行218-225)
     * 
     * @param ids 文件ID列表
     * @return 是否成功
     */
    boolean deleteFiles(List<String> ids);

    /**
     * 原始删除文件
     * 重写自: file-storage.ts - deleteRawFiles() (行235-243)
     * 
     * 仅从S3删除文件，不清理索引
     * @param ids 文件ID列表
     * @return 是否成功
     */
    boolean deleteRawFiles(List<String> ids);

    /**
     * 复制文件
     * 重写自: file-storage.ts - copyFile() (行252-279)
     * 
     * 从一个位置复制文件到另一个位置
     * @param sourceId 源文件ID
     * @param targetId 目标文件ID
     * @param sourceDocument 源文档
     * @param targetEntityId 目标实体ID
     * @return 新文件信息
     */
    LoadedFile copyFile(String sourceId, String targetId, Object sourceDocument, String targetEntityId);

    /**
     * 文件转换器
     * 重写自: file-storage.ts - storeFileConverter() (行284-292)
     * 
     * 将LoadedFile转换为x_opencti_file格式
     * @param file 加载的文件
     * @return 转换后的Map
     */
    Map<String, Object> storeFileConverter(LoadedFile file);

    /**
     * 获取文件名
     * 重写自: file-storage.ts - getFileName() (行299-302)
     * 
     * 从S3文件完整路径获取文件名(含扩展名)
     * @param fileId 文件ID
     * @return 文件名
     */
    String getFileName(String fileId);

    /**
     * 猜测MIME类型
     * 重写自: file-storage.ts - guessMimeType() (行309-327)
     * 
     * 根据文件名猜测MIME类型
     * @param fileId 文件ID
     * @return MIME类型
     */
    String guessMimeType(String fileId);

    /**
     * 检查文件是否被排除
     * 重写自: file-storage.ts - isFileObjectExcluded() (行335-338)
     * 
     * @param id 文件ID
     * @return 是否被排除
     */
    boolean isFileObjectExcluded(String id);

    /**
     * 文件列表
     * 重写自: file-storage.ts - loadedFilesListing() (行374-414)
     * 
     * 列出目录下的文件
     * @param directory 目录路径
     * @param opts 选项
     * @return 文件列表
     */
    List<LoadedFile> loadedFilesListing(String directory, FilesListingOptions opts);

    /**
     * 上传文件
     * 重写自: file-storage.ts - upload() (行555-635)
     * 
     * 上传文件到存储，包含元数据、验证和可选的自动导入触发
     * @param filePath 文件路径
     * @param fileUpload 文件上传数据
     * @param opts 上传选项
     * @return 上传结果
     */
    UploadResult upload(String filePath, FileUploadData fileUpload, FileUploadOpts opts);

    /**
     * 上传导入作业
     * 重写自: file-storage.ts - uploadJobImport() (行433-504)
     * 
     * 创建并调度文件的导入作业
     * @param file 文件
     * @param entityId 实体ID
     * @param opts 选项
     * @return 连接器列表
     */
    List<Object> uploadJobImport(LoadedFile file, String entityId, JobImportOptions opts);

    /**
     * 上传到存储
     * 重写自: file-storage.ts - uploadToStorage() (行674-676)
     * 
     * @param filePath 文件路径
     * @param fileUpload 文件上传数据
     * @param opts 上传选项
     * @return 上传结果
     */
    UploadResult uploadToStorage(String filePath, FileUploadData fileUpload, FileUploadOpts opts);

    /**
     * 流转换器
     * 重写自: file-storage.ts - streamConverter() (行642-650)
     * 
     * 将可读流转换为字符串
     * @param stream 输入流
     * @return 字符串内容
     */
    String streamConverter(InputStream stream);

    /**
     * 文件转流
     * 重写自: file-storage.ts - fileToReadStream() (行685-689)
     * 
     * 创建从文件系统读取文件的流
     * @param localFilePath 本地文件路径
     * @param localFileName 本地文件名
     * @param s3FileName S3文件名
     * @param mimeType MIME类型
     * @return 文件上传数据
     */
    FileUploadData fileToReadStream(String localFilePath, String localFileName, String s3FileName, String mimeType);

    /**
     * 删除对象所有文件
     * 重写自: file-storage.ts - deleteAllObjectFiles() (行699-737)
     * 
     * 删除存储中与元素相关的所有文件
     * @param element 元素
     * @return 是否成功
     */
    boolean deleteAllObjectFiles(Object element);

    /**
     * 删除草稿文件
     * 重写自: file-storage.ts - deleteAllDraftFiles() (行745-752)
     * 
     * @param draftId 草稿ID
     * @return 是否成功
     */
    boolean deleteAllDraftFiles(String draftId);

    /**
     * 删除存储桶内容
     * 重写自: file-storage.ts - deleteAllBucketContent() (行760-779)
     * 
     * 用于测试清理
     * @return 是否成功
     */
    boolean deleteAllBucketContent();

    /**
     * 移动文件
     * 重写自: file-storage.ts - moveAllFilesFromEntityToAnother() (行789-825)
     * 
     * 将源实体的所有文件移动到目标实体
     * @param sourceEntity 源实体
     * @param targetEntity 目标实体
     * @return 更新的文件列表
     */
    List<Map<String, Object>> moveAllFilesFromEntityToAnother(Object sourceEntity, Object targetEntity);

    /**
     * 加载文件选项
     */
    record LoadFileOptions(boolean dontThrow) {
        public static LoadFileOptions defaults() {
            return new LoadFileOptions(false);
        }
    }

    /**
     * 文件列表选项
     */
    record FilesListingOptions(
        boolean recursive,
        java.util.function.Consumer<List<LoadedFile>> callback,
        boolean dontThrow
    ) {
        public static FilesListingOptions defaults() {
            return new FilesListingOptions(false, null, false);
        }
    }

    /**
     * 作业导入选项
     */
    record JobImportOptions(
        boolean manual,
        String connectorId,
        String configuration,
        boolean bypassValidation,
        String validationMode,
        boolean forceValidation
    ) {
        public static JobImportOptions defaults() {
            return new JobImportOptions(false, null, null, false, null, false);
        }
    }

    /**
     * 上传结果
     */
    record UploadResult(LoadedFile upload, boolean untouched) {}
}
