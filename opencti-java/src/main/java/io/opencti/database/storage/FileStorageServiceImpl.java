package io.opencti.database.storage;

import io.opencti.database.storage.FileStorageClient.ListObjectsResult;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;

/**
 * 文件存储服务实现类
 * 重写自: opencti-platform/opencti-graphql/src/database/file-storage.ts
 * 
 * 提供高层文件存储操作
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    private final FileStorageClient storageClient;
    private final FileStorageConfig config;

    public FileStorageServiceImpl(FileStorageClient storageClient, FileStorageConfig config) {
        this.storageClient = storageClient;
        this.config = config;
    }

    /**
     * 加载文件
     * 重写自: file-storage.ts - loadFile() (行101-175)
     */
    @Override
    public LoadedFile loadFile(String fileS3Path, LoadFileOptions opts) {
        try {
            if (fileS3Path == null || fileS3Path.isEmpty()) {
                throw new IllegalArgumentException("File path not specified");
            }

            // TODO: 检查用户权限 - 依赖未实现的模块
            // 01. Check if user as enough capability to get support packages
            // 01.1. Check if user as enough capability to load import / export / template knowledge files
            // 01.2. Check if user as enough capability to load import/global files

            // TODO: 从数据库获取文档 - 依赖未实现的document-domain模块
            // 02. Check if the referenced document is accessible
            // const document = await documentFindById(context, user, fileS3Path, { ignoreDuplicates: true });

            // TODO: 检查实体访问权限 - 依赖未实现的middleware-loader模块
            // 03. Check if metadata contains an entity_id

            // 临时实现：直接从S3获取文件信息
            long fileSize = 0;
            try {
                fileSize = storageClient.getFileSize(fileS3Path);
            } catch (Exception e) {
                if (opts.dontThrow()) {
                    return null;
                }
                throw new RuntimeException("File not found or restricted: " + fileS3Path, e);
            }

            FileMetadata metaData = new FileMetadata();
            metaData.setMimetype(guessMimeType(fileS3Path));
            metaData.setFilename(getFileName(fileS3Path));

            LoadedFile file = new LoadedFile();
            file.setId(fileS3Path);
            file.setName(getFileName(fileS3Path));
            file.setSize(fileSize);
            file.setInformation("");
            file.setLastModified(Date.from(Instant.now()));
            file.setLastModifiedSinceMin(ChronoUnit.MINUTES.between(Instant.now(), Instant.now()));
            file.setMetaData(metaData);
            file.setUploadStatus("complete");

            return file;
        } catch (Exception err) {
            if (opts.dontThrow()) {
                return null;
            }
            throw err;
        }
    }

    /**
     * 删除文件
     * 重写自: file-storage.ts - deleteFile() (行186-209)
     */
    @Override
    public LoadedFile deleteFile(String id) {
        // TODO: 检查草稿上下文 - 依赖未实现的draft-utils模块
        // const draftContext = getDraftContext(context, user);
        // if (draftContext && !isDraftFile(id, draftContext)) {
        //     throw UnsupportedError('Cannot delete non draft imports in draft');
        // }

        LoadedFile file = loadFile(id, LoadFileOptions.defaults());
        log.debug("[FILE STORAGE] delete file {} ", id);

        // Delete in S3
        storageClient.deleteFileFromStorage(id);

        // TODO: Delete associated works - 依赖未实现的work模块
        // await deleteWorkForFile(context, user, id);

        // TODO: Delete index file - 依赖未实现的document-domain模块
        // await deleteDocumentIndex(context, user, id);

        // TODO: delete in index if file has been indexed - 依赖未实现的engine模块
        // const isFileIndexModuleActivated = await isModuleActivated('FILE_INDEX_MANAGER');
        // if (isFileIndexModuleActivated && isAttachmentProcessorEnabled()) {
        //     await elDeleteFilesByIds([id]);
        // }

        return file;
    }

    /**
     * 批量删除文件
     * 重写自: file-storage.ts - deleteFiles() (行218-225)
     */
    @Override
    public boolean deleteFiles(List<String> ids) {
        log.debug("[FILE STORAGE] delete files {}", ids);
        for (String id : ids) {
            deleteFile(id);
        }
        return true;
    }

    /**
     * 原始删除文件
     * 重写自: file-storage.ts - deleteRawFiles() (行235-243)
     */
    @Override
    public boolean deleteRawFiles(List<String> ids) {
        log.debug("[FILE STORAGE] raw delete files {}", ids);
        for (String id : ids) {
            storageClient.deleteFileFromStorage(id);
        }
        return true;
    }

    /**
     * 复制文件
     * 重写自: file-storage.ts - copyFile() (行252-279)
     */
    @Override
    public LoadedFile copyFile(String sourceId, String targetId, Object sourceDocument, String targetEntityId) {
        try {
            storageClient.copyFile(sourceId, targetId);

            // TODO: Register in elastic - 依赖未实现的document-domain模块
            // const targetMetadata = { ...sourceDocument.metaData, entity_id: targetEntityId };

            LoadedFile file = new LoadedFile();
            file.setId(targetId);
            file.setName(sourceId.substring(sourceId.lastIndexOf('/') + 1));
            file.setSize(storageClient.getFileSize(targetId));
            file.setInformation("");
            file.setLastModified(new Date());
            file.setUploadStatus("complete");

            log.info("[FILE STORAGE] Copy file to S3 in success, sourceId: {}, targetId: {}", sourceId, targetId);
            return file;
        } catch (Exception err) {
            log.error("[FILE STORAGE] Cannot copy file in S3, sourceId: {}, targetId: {}", sourceId, targetId, err);
            return null;
        }
    }

    /**
     * 文件转换器
     * 重写自: file-storage.ts - storeFileConverter() (行284-292)
     */
    @Override
    public Map<String, Object> storeFileConverter(LoadedFile file) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", file.getId());
        result.put("name", file.getName());
        result.put("version", file.getMetaData() != null ? file.getMetaData().getVersion() : null);
        result.put("mime_type", file.getMetaData() != null ? file.getMetaData().getMimetype() : null);
        result.put("file_markings", file.getMetaData() != null ? 
                file.getMetaData().getFileMarkings() != null ? file.getMetaData().getFileMarkings() : Collections.emptyList() 
                : Collections.emptyList());
        return result;
    }

    /**
     * 获取文件名
     * 重写自: file-storage.ts - getFileName() (行299-302)
     */
    @Override
    public String getFileName(String fileId) {
        if (fileId == null || fileId.isEmpty()) {
            return "";
        }
        int lastSlash = fileId.lastIndexOf('/');
        if (lastSlash >= 0 && lastSlash < fileId.length() - 1) {
            return fileId.substring(lastSlash + 1);
        }
        return fileId;
    }

    /**
     * 猜测MIME类型
     * 重写自: file-storage.ts - guessMimeType() (行309-327)
     */
    @Override
    public String guessMimeType(String fileId) {
        String fileName = getFileName(fileId);
        
        // 常见MIME类型映射
        Map<String, String> mimeTypes = new HashMap<>();
        mimeTypes.put(".json", "application/json");
        mimeTypes.put(".xml", "application/xml");
        mimeTypes.put(".pdf", "application/pdf");
        mimeTypes.put(".zip", "application/zip");
        mimeTypes.put(".txt", "text/plain");
        mimeTypes.put(".csv", "text/csv");
        mimeTypes.put(".html", "text/html");
        mimeTypes.put(".jpg", "image/jpeg");
        mimeTypes.put(".jpeg", "image/jpeg");
        mimeTypes.put(".png", "image/png");
        mimeTypes.put(".gif", "image/gif");
        mimeTypes.put(".stix", "application/vnd.oasis.stix+json");
        
        // 特殊类型扩展
        Map<String, String> specialTypes = new HashMap<>();
        specialTypes.put("application/vnd.oasis.stix+json", "json");
        specialTypes.put("application/vnd.mitre.navigator+json", "json");

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            String ext = fileName.substring(dotIndex).toLowerCase();
            if (mimeTypes.containsKey(ext)) {
                return mimeTypes.get(ext);
            }
        }

        // TODO: 从配置获取自定义MIME类型映射
        // const appMimes = nconf.get('app:filename_to_mimes') || {};

        return "application/octet-stream";
    }

    /**
     * 检查文件是否被排除
     * 重写自: file-storage.ts - isFileObjectExcluded() (行335-338)
     */
    @Override
    public boolean isFileObjectExcluded(String id) {
        String fileName = getFileName(id);
        List<String> excludedFiles = config.getExcludedFiles();
        return excludedFiles.stream()
                .map(String::toLowerCase)
                .anyMatch(excluded -> excluded.equalsIgnoreCase(fileName));
    }

    /**
     * 文件列表
     * 重写自: file-storage.ts - loadedFilesListing() (行374-414)
     */
    @Override
    public List<LoadedFile> loadedFilesListing(String directory, FilesListingOptions opts) {
        List<LoadedFile> files = new ArrayList<>();
        
        if (directory != null && directory.startsWith("/")) {
            throw new IllegalArgumentException("File listing directory must not start with a /");
        }
        if (directory != null && !directory.endsWith("/")) {
            throw new IllegalArgumentException("File listing directory must end with a /");
        }

        boolean truncated = true;
        String continuationToken = null;

        while (truncated) {
            try {
                ListObjectsResult response = storageClient.listObjects(
                        directory, opts.recursive(), continuationToken);
                
                List<S3FileObject> resultFiles = filesAdaptation(response.contents());
                
                List<LoadedFile> resultLoaded = new ArrayList<>();
                for (S3FileObject f : resultFiles) {
                    LoadedFile loaded = loadFile(f.getKey(), new LoadFileOptions(opts.dontThrow()));
                    if (loaded != null) {
                        resultLoaded.add(loaded);
                    }
                }

                if (opts.callback() != null) {
                    opts.callback().accept(resultLoaded);
                } else {
                    files.addAll(resultLoaded);
                }

                truncated = response.isTruncated();
                continuationToken = response.nextContinuationToken();
            } catch (Exception err) {
                log.error("[FILE STORAGE] Storage files read fail", err);
                truncated = false;
            }
        }

        return files;
    }

    /**
     * 文件适配
     * 重写自: file-storage.ts - filesAdaptation() (行346-358)
     */
    private List<S3FileObject> filesAdaptation(List<Item> objects) {
        List<S3FileObject> storageObjects = new ArrayList<>();
        for (Item obj : objects) {
            if (obj.key() != null) {
                S3FileObject s3Obj = new S3FileObject(obj.key(), guessMimeType(obj.key()));
                if (!isFileObjectExcluded(s3Obj.getKey())) {
                    storageObjects.add(s3Obj);
                }
            }
        }
        return storageObjects;
    }

    /**
     * 上传文件
     * 重写自: file-storage.ts - upload() (行555-635)
     */
    @Override
    public UploadResult upload(String filePath, FileUploadData fileUpload, FileUploadOpts opts) {
        // TODO: 完整实现 - 依赖多个未实现模块
        // - validateMarking
        // - documentFindById
        // - getDraftContext
        // - indexFileToDocument
        // - triggerJobImport

        FileMetadata metadata = new FileMetadata();
        if (opts.getMeta() != null) {
            Map<String, Object> meta = opts.getMeta();
            if (meta.containsKey("version")) {
                metadata.setVersion(String.valueOf(meta.get("version")));
            }
            if (meta.containsKey("mimetype")) {
                metadata.setMimetype(String.valueOf(meta.get("mimetype")));
            }
        }
        if (metadata.getVersion() == null) {
            metadata.setVersion(String.valueOf(System.currentTimeMillis()));
        }

        String filename = fileUpload.getFilename();
        // 截断文件名
        String truncatedFileName = truncate(filename, 200, false);
        
        String key = filePath + "/" + truncatedFileName.toLowerCase();

        // TODO: 草稿上下文处理
        // const draftContext = getDraftContext(context, user);
        // if (draftContext) {
        //     const draftPrefix = getDraftFilePrefix(draftContext);
        //     key = draftPrefix + key;
        // }

        // 上传文件
        try (InputStream readStream = fileUpload.createReadStream()) {
            storageClient.upload(key, readStream);
        } catch (IOException e) {
            log.error("[FILE STORAGE] Failed to close upload stream", e);
        }

        long fileSize = storageClient.getFileSize(key);

        metadata.setFilename(truncatedFileName);
        metadata.setMimetype(fileUpload.getMimeType() != null ? fileUpload.getMimeType() : guessMimeType(key));
        metadata.setEncoding("7bit");

        LoadedFile file = new LoadedFile();
        file.setId(key);
        file.setName(truncatedFileName);
        file.setSize(fileSize);
        file.setInformation("");
        file.setLastModified(new Date());
        file.setMetaData(metadata);
        file.setUploadStatus("complete");

        // TODO: Register in elastic
        // await indexFileToDocument(context, file);

        // TODO: Trigger import
        // if (!noTriggerImport && isFilePathForImportEnrichment) {
        //     await triggerJobImport(context, user, file, jobImportContextEntities);
        // }

        return new UploadResult(file, false);
    }

    /**
     * 字符串截断
     */
    private String truncate(String str, int maxLength, boolean ellipsis) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + (ellipsis ? "..." : "");
    }

    /**
     * 上传导入作业
     * 重写自: file-storage.ts - uploadJobImport() (行433-504)
     */
    @Override
    public List<Object> uploadJobImport(LoadedFile file, String entityId, JobImportOptions opts) {
        // TODO: 完整实现 - 依赖未实现的模块
        // - connectorsForImport
        // - createWork
        // - pushToConnector

        log.info("[FILE STORAGE] uploadJobImport for file: {}, entityId: {}", file.getId(), entityId);
        return Collections.emptyList();
    }

    /**
     * 上传到存储
     * 重写自: file-storage.ts - uploadToStorage() (行674-676)
     */
    @Override
    public UploadResult uploadToStorage(String filePath, FileUploadData fileUpload, FileUploadOpts opts) {
        return upload(filePath, fileUpload, opts);
    }

    /**
     * 流转换器
     * 重写自: file-storage.ts - streamConverter() (行642-650)
     */
    @Override
    public String streamConverter(InputStream stream) {
        try {
            return new String(stream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert stream to string", e);
        }
    }

    /**
     * 文件转流
     * 重写自: file-storage.ts - fileToReadStream() (行685-689)
     */
    @Override
    public FileUploadData fileToReadStream(String localFilePath, String localFileName, String s3FileName, String mimeType) {
        try {
            Path fullPath = Paths.get(localFilePath, localFileName);
            byte[] buffer = Files.readAllBytes(fullPath);
            return new FileUploadData(new ByteArrayInputStream(buffer), s3FileName, mimeType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + localFilePath + "/" + localFileName, e);
        }
    }

    /**
     * 删除对象所有文件
     * 重写自: file-storage.ts - deleteAllObjectFiles() (行699-737)
     */
    @Override
    public boolean deleteAllObjectFiles(Object element) {
        // TODO: 完整实现 - 依赖未实现的模块
        // - allFilesForPaths
        // - deleteWorkForSource
        log.debug("[FILE STORAGE] deleting all storage files for element");
        return true;
    }

    /**
     * 删除草稿文件
     * 重写自: file-storage.ts - deleteAllDraftFiles() (行745-752)
     */
    @Override
    public boolean deleteAllDraftFiles(String draftId) {
        // TODO: 完整实现 - 依赖未实现的模块
        log.debug("[FILE STORAGE] deleting all storage files for draft {}", draftId);
        return true;
    }

    /**
     * 删除存储桶内容
     * 重写自: file-storage.ts - deleteAllBucketContent() (行760-779)
     */
    @Override
    public boolean deleteAllBucketContent() {
        List<String> allRootFolders = Arrays.asList(
                FileStorageConstants.SUPPORT_STORAGE_PATH,
                FileStorageConstants.IMPORT_STORAGE_PATH,
                FileStorageConstants.EXPORT_STORAGE_PATH,
                FileStorageConstants.FROM_TEMPLATE_STORAGE_PATH
        );

        for (String folder : allRootFolders) {
            List<LoadedFile> allFiles = loadedFilesListing(folder + "/", 
                    new FilesListingOptions(true, null, true));
            
            List<String> ids = new ArrayList<>();
            for (LoadedFile file : allFiles) {
                log.info("[FILE STORAGE] preparing for delete: {}", file.getId());
                if (file.getId() != null) {
                    ids.add(file.getId());
                }
            }
            
            log.info("[FILE STORAGE] deleting {} files in {}/", ids.size(), folder);
            deleteFiles(ids);
        }

        return true;
    }

    /**
     * 移动文件
     * 重写自: file-storage.ts - moveAllFilesFromEntityToAnother() (行789-825)
     */
    @Override
    public List<Map<String, Object>> moveAllFilesFromEntityToAnother(Object sourceEntity, Object targetEntity) {
        // TODO: 完整实现 - 依赖未实现的模块
        // - getDraftContext
        // - allFilesForPaths
        log.info("[FILE STORAGE] moveAllFilesFromEntityToAnother");
        return Collections.emptyList();
    }
}
