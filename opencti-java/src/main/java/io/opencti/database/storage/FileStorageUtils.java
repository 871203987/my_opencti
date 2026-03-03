package io.opencti.database.storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * 文件存储工具类
 * 重写自: opencti-platform/opencti-graphql/src/database/file-storage.ts
 * 
 * 提供文件操作相关的工具方法
 */
public final class FileStorageUtils {

    private FileStorageUtils() {
    }

    /**
     * 流转字符串
     * 重写自: file-storage.ts - streamConverter() (行642-650)
     * 
     * @param stream 输入流
     * @return 字符串内容
     */
    public static String streamToString(InputStream stream) {
        if (stream == null) {
            return "";
        }
        try {
            return new String(stream.readAllBytes(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert stream to string", e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * 流转字符串(指定编码)
     * 
     * @param stream 输入流
     * @param encoding 编码格式
     * @return 字符串内容
     */
    public static String streamToString(InputStream stream, String encoding) {
        if (stream == null) {
            return "";
        }
        try {
            return new String(stream.readAllBytes(), Charset.forName(encoding));
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert stream to string", e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * 字符串转输入流
     * 
     * @param content 字符串内容
     * @return 输入流
     */
    public static InputStream stringToStream(String content) {
        if (content == null) {
            return new ByteArrayInputStream(new byte[0]);
        }
        return new ByteArrayInputStream(content.getBytes(Charset.defaultCharset()));
    }

    /**
     * 字符串转输入流(指定编码)
     * 
     * @param content 字符串内容
     * @param encoding 编码格式
     * @return 输入流
     */
    public static InputStream stringToStream(String content, String encoding) {
        if (content == null) {
            return new ByteArrayInputStream(new byte[0]);
        }
        return new ByteArrayInputStream(content.getBytes(Charset.forName(encoding)));
    }

    /**
     * 文件转上传数据
     * 重写自: file-storage.ts - fileToReadStream() (行685-689)
     * 
     * @param localFilePath 本地文件路径
     * @param localFileName 本地文件名
     * @param s3FileName S3文件名
     * @param mimeType MIME类型
     * @return 文件上传数据
     */
    public static FileUploadData fileToReadStream(String localFilePath, String localFileName, 
                                                   String s3FileName, String mimeType) {
        try {
            Path fullPath = Paths.get(localFilePath, localFileName);
            byte[] buffer = Files.readAllBytes(fullPath);
            return new FileUploadData(new ByteArrayInputStream(buffer), s3FileName, mimeType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + localFilePath + "/" + localFileName, e);
        }
    }

    /**
     * 从文件路径获取文件名
     * 重写自: file-storage.ts - getFileName() (行299-302)
     * 
     * @param fileId 文件ID或路径
     * @return 文件名(含扩展名)
     */
    public static String getFileName(String fileId) {
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
     * 从文件名获取扩展名
     * 
     * @param fileName 文件名
     * @return 扩展名(含点)，如".json"
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex).toLowerCase();
        }
        return "";
    }

    /**
     * 截断字符串
     * 
     * @param str 原字符串
     * @param maxLength 最大长度
     * @return 截断后的字符串
     */
    public static String truncate(String str, int maxLength) {
        return truncate(str, maxLength, false);
    }

    /**
     * 截断字符串
     * 
     * @param str 原字符串
     * @param maxLength 最大长度
     * @param ellipsis 是否添加省略号
     * @return 截断后的字符串
     */
    public static String truncate(String str, int maxLength, boolean ellipsis) {
        if (str == null) {
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + (ellipsis ? "..." : "");
    }

    /**
     * 构建S3文件路径
     * 
     * @param parts 路径组成部分
     * @return S3路径
     */
    public static String buildPath(String... parts) {
        return String.join("/", parts);
    }

    /**
     * 检查文件路径是否为导入路径
     * 
     * @param filePath 文件路径
     * @return 是否为导入路径
     */
    public static boolean isImportPath(String filePath) {
        return filePath != null && filePath.startsWith(FileStorageConstants.IMPORT_STORAGE_PATH);
    }

    /**
     * 检查文件路径是否为导出路径
     * 
     * @param filePath 文件路径
     * @return 是否为导出路径
     */
    public static boolean isExportPath(String filePath) {
        return filePath != null && filePath.startsWith(FileStorageConstants.EXPORT_STORAGE_PATH);
    }

    /**
     * 检查文件路径是否为支持路径
     * 
     * @param filePath 文件路径
     * @return 是否为支持路径
     */
    public static boolean isSupportPath(String filePath) {
        return filePath != null && filePath.startsWith(FileStorageConstants.SUPPORT_STORAGE_PATH);
    }

    /**
     * 检查文件路径是否为草稿路径
     * 
     * @param filePath 文件路径
     * @param draftId 草稿ID
     * @return 是否为草稿路径
     */
    public static boolean isDraftFile(String filePath, String draftId) {
        if (filePath == null || draftId == null) {
            return false;
        }
        return filePath.startsWith("draft-" + draftId + "/");
    }

    /**
     * 获取草稿文件前缀
     * 
     * @param draftId 草稿ID
     * @return 草稿文件前缀
     */
    public static String getDraftFilePrefix(String draftId) {
        return "draft-" + draftId + "/";
    }

    /**
     * 创建文件标记信息
     * 
     * @param file 文件
     * @return 文件标记Map
     */
    public static Map<String, Object> createFileMarking(LoadedFile file) {
        return Map.of(
            "id", file.getId(),
            "name", file.getName(),
            "version", file.getMetaData() != null && file.getMetaData().getVersion() != null 
                ? file.getMetaData().getVersion() : "",
            "mime_type", file.getMetaData() != null && file.getMetaData().getMimetype() != null 
                ? file.getMetaData().getMimetype() : "",
            "file_markings", file.getMetaData() != null && file.getMetaData().getFileMarkings() != null 
                ? file.getMetaData().getFileMarkings() : List.of()
        );
    }

    /**
     * 验证目录路径格式
     * 重写自: file-storage.ts - loadedFilesListing() (行382-387)
     * 
     * @param directory 目录路径
     * @throws IllegalArgumentException 如果格式无效
     */
    public static void validateDirectoryPath(String directory) {
        if (directory != null && directory.startsWith("/")) {
            throw new IllegalArgumentException("File listing directory must not start with a /");
        }
        if (directory != null && !directory.endsWith("/")) {
            throw new IllegalArgumentException("File listing directory must end with a /");
        }
    }
}
