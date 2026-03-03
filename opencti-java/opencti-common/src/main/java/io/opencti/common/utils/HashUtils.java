package io.opencti.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 重写自: opencti-graphql/src/utils/hash.ts
 * 哈希工具类
 */
public final class HashUtils {

    private HashUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 重写自: opencti-graphql/src/utils/hash.ts - hashSHA256
     * 使用SHA-256算法哈希字符串
     *
     * @param input 要哈希的字符串
     * @return 哈希后的十六进制字符串
     */
    public static String hashSHA256(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    /**
     * 重写自: opencti-graphql/src/utils/hash.ts - compareHashSHA256
     * 比较字符串与SHA-256哈希值
     *
     * @param input 要比较的字符串
     * @param hash  参考哈希值
     * @return 如果字符串的哈希值与参考哈希值匹配则返回true
     */
    public static boolean compareHashSHA256(String input, String hash) {
        if (input == null || hash == null) {
            return false;
        }
        return hashSHA256(input).equals(hash);
    }

    /**
     * 使用MD5算法哈希字符串
     *
     * @param input 要哈希的字符串
     * @return 哈希后的十六进制字符串
     */
    public static String hashMD5(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * 使用SHA-1算法哈希字符串
     *
     * @param input 要哈希的字符串
     * @return 哈希后的十六进制字符串
     */
    public static String hashSHA1(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not found", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
