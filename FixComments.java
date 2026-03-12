import java.io.*;
import java.nio.file.*;

public class FixComments {
    public static void main(String[] args) throws IOException {
        String filePath = "d:\\project\\open_cti\\opencti\\opencti-java\\src\\main\\java\\io\\opencti\\database\\stix\\StixCoreRelationshipsMapping.java";
        String content = Files.readString(Path.of(filePath));
        
        // Fix the issue where comments and code are on the same line
        // Pattern: // comment // comment if (...)
        
        // Fix 1: Line ~993
        content = content.replace(
            "// 濡傛灉鏍稿績鍏崇郴鐨勭洰鏍囩被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        // 杩欎簺鍏崇郴鏄疭TIX瑙勮寖鐨勬墿灞?        if (isStixCyberObservable(toType)) {",
            "// 濡傛灉鏍稿績鍏崇郴鐨勭洰鏍囩被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?\n        // 杩欎簺鍏崇郴鏄疭TIX瑙勮寖鐨勬墿灞?\n        if (isStixCyberObservable(toType)) {"
        );
        
        // Fix 2: Line ~1003
        content = content.replace(
            "// 濡傛灉鏍稿績鍏崇郴鐨勬簮绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        if (isStixCyberObservable(fromType)) {",
            "// 濡傛灉鏍稿績鍏崇郴鐨勬簮绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?\n        if (isStixCyberObservable(fromType)) {"
        );
        
        // Fix 3: Line ~1071
        content = content.replace(
            "// 濡傛灉鐩爣绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        if (isStixCyberObservable(toType)) {",
            "// 濡傛灉鐩爣绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?\n            if (isStixCyberObservable(toType)) {"
        );
        
        // Fix 4: Line ~1079
        content = content.replace(
            "// 濡傛灉婧愮被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        if (isStixCyberObservable(fromType)) {",
            "// 濡傛灉婧愮被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?\n            if (isStixCyberObservable(fromType)) {"
        );
        
        Files.writeString(Path.of(filePath), content);
        System.out.println("Fixed!");
    }
}
