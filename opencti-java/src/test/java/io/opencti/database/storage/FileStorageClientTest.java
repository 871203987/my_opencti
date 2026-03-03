package io.opencti.database.storage;

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.messages.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 文件存储客户端单元测试
 * 重写源文件: opencti-platform/opencti-graphql/src/database/raw-file-storage.ts
 */
@ExtendWith(MockitoExtension.class)
class FileStorageClientTest {

    @Mock
    private FileStorageConfig config;

    @Mock
    private MinioClient minioClient;

    private FileStorageClientImpl storageClient;

    @BeforeEach
    void setUp() {
        lenient().when(config.getEndpoint()).thenReturn("http://localhost:9000");
        lenient().when(config.getRegion()).thenReturn("us-east-1");
        lenient().when(config.getAccessKey()).thenReturn("minioadmin");
        lenient().when(config.getSecretKey()).thenReturn("minioadmin");
        lenient().when(config.getBucketName()).thenReturn("opencti-bucket");
        lenient().when(config.isUseSsl()).thenReturn(false);
        lenient().when(config.getExcludedFiles()).thenReturn(List.of(".DS_Store"));

        storageClient = new FileStorageClientImpl(config);
        // 使用反射设置minioClient
        try {
            var field = FileStorageClientImpl.class.getDeclaredField("minioClient");
            field.setAccessible(true);
            field.set(storageClient, minioClient);
        } catch (Exception e) {
            fail("Failed to set minioClient field: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("测试 getBucketName")
    void testGetBucketName() {
        String bucketName = storageClient.getBucketName();
        assertEquals("opencti-bucket", bucketName);
    }

    @Test
    @DisplayName("测试 streamToString")
    void testStreamToString() {
        String content = "Hello, World!";
        InputStream stream = new ByteArrayInputStream(content.getBytes());
        
        String result = storageClient.streamToString(stream, "UTF-8");
        
        assertEquals(content, result);
    }

    @Test
    @DisplayName("测试 streamToString - 空流")
    void testStreamToString_EmptyStream() {
        InputStream stream = new ByteArrayInputStream(new byte[0]);
        
        String result = storageClient.streamToString(stream, "UTF-8");
        
        assertEquals("", result);
    }

    @Test
    @DisplayName("测试 streamToString - null流")
    void testStreamToString_NullStream() {
        assertThrows(IllegalArgumentException.class, () -> {
            storageClient.streamToString(null, "UTF-8");
        });
    }

    @Test
    @DisplayName("测试 guessMimeType - 通过服务")
    void testGuessMimeType() {
        FileStorageServiceImpl service = new FileStorageServiceImpl(storageClient, config);
        
        assertEquals("application/json", service.guessMimeType("test.json"));
        assertEquals("application/pdf", service.guessMimeType("document.pdf"));
        assertEquals("text/plain", service.guessMimeType("readme.txt"));
        assertEquals("text/csv", service.guessMimeType("data.csv"));
        assertEquals("application/octet-stream", service.guessMimeType("unknown.xyz"));
    }

    @Test
    @DisplayName("测试 getFileName")
    void testGetFileName() {
        FileStorageServiceImpl service = new FileStorageServiceImpl(storageClient, config);
        
        assertEquals("test.txt", service.getFileName("import/path/test.txt"));
        assertEquals("document.pdf", service.getFileName("export/document.pdf"));
        assertEquals("file.json", service.getFileName("file.json"));
        assertEquals("", service.getFileName(""));
    }

    @Test
    @DisplayName("测试 isFileObjectExcluded - 排除文件")
    void testIsFileObjectExcluded_Excluded() {
        FileStorageServiceImpl service = new FileStorageServiceImpl(storageClient, config);
        
        assertTrue(service.isFileObjectExcluded("path/.DS_Store"));
        assertTrue(service.isFileObjectExcluded(".DS_Store"));
    }

    @Test
    @DisplayName("测试 isFileObjectExcluded - 非排除文件")
    void testIsFileObjectExcluded_NotExcluded() {
        FileStorageServiceImpl service = new FileStorageServiceImpl(storageClient, config);
        
        assertFalse(service.isFileObjectExcluded("path/test.txt"));
        assertFalse(service.isFileObjectExcluded("document.pdf"));
    }

    @Test
    @DisplayName("测试 storeFileConverter")
    void testStoreFileConverter() {
        FileStorageServiceImpl service = new FileStorageServiceImpl(storageClient, config);
        
        FileMetadata metadata = new FileMetadata();
        metadata.setVersion("1.0");
        metadata.setMimetype("application/json");
        metadata.setFileMarkings(List.of("TLP:WHITE"));
        
        LoadedFile file = new LoadedFile();
        file.setId("test/file.json");
        file.setName("file.json");
        file.setMetaData(metadata);
        
        var result = service.storeFileConverter(file);
        
        assertEquals("test/file.json", result.get("id"));
        assertEquals("file.json", result.get("name"));
        assertEquals("1.0", result.get("version"));
        assertEquals("application/json", result.get("mime_type"));
        assertNotNull(result.get("file_markings"));
    }

    @Test
    @DisplayName("测试 deleteRawFiles")
    void testDeleteRawFiles() {
        List<String> ids = List.of("file1.txt", "file2.txt");
        
        boolean result = storageClient.deleteRawFiles(ids);
        
        assertTrue(result);
    }

    @Test
    @DisplayName("测试 FileMetadata 属性")
    void testFileMetadata() {
        FileMetadata metadata = new FileMetadata();
        metadata.setVersion("1.0");
        metadata.setMimetype("application/json");
        metadata.setEncoding("UTF-8");
        metadata.setFilename("test.json");
        metadata.setCreatorId("user-123");
        metadata.setEntityId("entity-456");
        metadata.setMessages(List.of("msg1", "msg2"));
        metadata.setErrors(List.of("error1"));
        metadata.setFileMarkings(List.of("TLP:WHITE"));
        metadata.setOrder(1);
        metadata.setDescription("Test file");
        metadata.setInCarousel(true);
        
        assertEquals("1.0", metadata.getVersion());
        assertEquals("application/json", metadata.getMimetype());
        assertEquals("UTF-8", metadata.getEncoding());
        assertEquals("test.json", metadata.getFilename());
        assertEquals("user-123", metadata.getCreatorId());
        assertEquals("entity-456", metadata.getEntityId());
        assertEquals(2, metadata.getMessages().size());
        assertEquals(1, metadata.getErrors().size());
        assertEquals(1, metadata.getFileMarkings().size());
        assertEquals(1, metadata.getOrder());
        assertEquals("Test file", metadata.getDescription());
        assertTrue(metadata.getInCarousel());
    }

    @Test
    @DisplayName("测试 LoadedFile 属性")
    void testLoadedFile() {
        FileMetadata metadata = new FileMetadata();
        metadata.setMimetype("application/json");
        
        LoadedFile file = new LoadedFile();
        file.setId("test/file.json");
        file.setName("file.json");
        file.setSize(1024L);
        file.setInformation("Test information");
        file.setLastModified(new java.util.Date());
        file.setMetaData(metadata);
        file.setUploadStatus("complete");
        file.setInternalId("internal-123");
        
        assertEquals("test/file.json", file.getId());
        assertEquals("file.json", file.getName());
        assertEquals(1024L, file.getSize());
        assertEquals("Test information", file.getInformation());
        assertNotNull(file.getLastModified());
        assertEquals("application/json", file.getMetaData().getMimetype());
        assertEquals("complete", file.getUploadStatus());
        assertEquals("internal-123", file.getInternalId());
    }

    @Test
    @DisplayName("测试 S3FileObject")
    void testS3FileObject() {
        S3FileObject obj = new S3FileObject("path/file.txt", "text/plain");
        
        assertEquals("path/file.txt", obj.getKey());
        assertEquals("text/plain", obj.getMimeType());
    }

    @Test
    @DisplayName("测试 FileUploadData")
    void testFileUploadData() {
        InputStream stream = new ByteArrayInputStream("test content".getBytes());
        FileUploadData data = new FileUploadData(stream, "test.txt", "text/plain");
        
        assertNotNull(data.createReadStream());
        assertEquals("test.txt", data.getFilename());
        assertEquals("text/plain", data.getMimeType());
    }

    @Test
    @DisplayName("测试 FileUploadOpts Builder")
    void testFileUploadOptsBuilder() {
        FileUploadOpts opts = FileUploadOpts.builder()
                .noTriggerImport(true)
                .errorOnExisting(false)
                .fileMarkings(List.of("TLP:WHITE"))
                .build();
        
        assertTrue(opts.isNoTriggerImport());
        assertFalse(opts.isErrorOnExisting());
        assertEquals(1, opts.getFileMarkings().size());
    }

    @Test
    @DisplayName("测试 FileStorageConstants")
    void testFileStorageConstants() {
        assertEquals("support", FileStorageConstants.SUPPORT_STORAGE_PATH);
        assertEquals("import", FileStorageConstants.IMPORT_STORAGE_PATH);
        assertEquals("export", FileStorageConstants.EXPORT_STORAGE_PATH);
        assertEquals("fromTemplate", FileStorageConstants.FROM_TEMPLATE_STORAGE_PATH);
        assertEquals("embedded", FileStorageConstants.EMBEDDED_STORAGE_PATH);
        
        assertEquals(4, FileStorageConstants.ALL_ROOT_FOLDERS.size());
        assertEquals(3, FileStorageConstants.ALL_MERGEABLE_FOLDERS.size());
        
        assertEquals("complete", FileStorageConstants.UPLOAD_STATUS_COMPLETE);
        assertEquals("progress", FileStorageConstants.UPLOAD_STATUS_PROGRESS);
        assertEquals("error", FileStorageConstants.UPLOAD_STATUS_ERROR);
    }

    @Test
    @DisplayName("测试 LoadFileOptions")
    void testLoadFileOptions() {
        FileStorageService.LoadFileOptions opts = FileStorageService.LoadFileOptions.defaults();
        assertFalse(opts.dontThrow());
        
        FileStorageService.LoadFileOptions customOpts = new FileStorageService.LoadFileOptions(true);
        assertTrue(customOpts.dontThrow());
    }

    @Test
    @DisplayName("测试 FilesListingOptions")
    void testFilesListingOptions() {
        FileStorageService.FilesListingOptions opts = FileStorageService.FilesListingOptions.defaults();
        assertFalse(opts.recursive());
        assertNull(opts.callback());
        assertFalse(opts.dontThrow());
    }

    @Test
    @DisplayName("测试 JobImportOptions")
    void testJobImportOptions() {
        FileStorageService.JobImportOptions opts = FileStorageService.JobImportOptions.defaults();
        assertFalse(opts.manual());
        assertNull(opts.connectorId());
        assertNull(opts.configuration());
        assertFalse(opts.bypassValidation());
        assertNull(opts.validationMode());
        assertFalse(opts.forceValidation());
    }

    @Test
    @DisplayName("测试 UploadResult")
    void testUploadResult() {
        LoadedFile file = new LoadedFile();
        file.setId("test/file.txt");
        
        FileStorageService.UploadResult result = new FileStorageService.UploadResult(file, false);
        
        assertEquals("test/file.txt", result.upload().getId());
        assertFalse(result.untouched());
    }

    @Test
    @DisplayName("测试 ListObjectsResult")
    void testListObjectsResult() {
        FileStorageClient.ListObjectsResult result = new FileStorageClient.ListObjectsResult(
                List.of(),
                false,
                null
        );
        
        assertTrue(result.contents().isEmpty());
        assertFalse(result.isTruncated());
        assertNull(result.nextContinuationToken());
    }
}
