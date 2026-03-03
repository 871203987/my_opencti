package io.opencti.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 重写自: opencti-graphql/src/utils/base64.ts
 * Base64工具类
 */
public final class Base64Utils {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Base64Utils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 重写自: opencti-graphql/src/utils/base64.ts - toB64
     * 将对象转换为Base64编码的JSON字符串
     *
     * @param data 要编码的对象
     * @return Base64编码的字符串
     */
    public static String encode(Object data) {
        if (data == null) {
            return null;
        }
        try {
            String json = OBJECT_MAPPER.writeValueAsString(data);
            return encodeString(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    /**
     * 将字符串编码为Base64
     *
     * @param data 要编码的字符串
     * @return Base64编码的字符串
     */
    public static String encodeString(String data) {
        if (data == null) {
            return null;
        }
        return ENCODER.encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将字节数组编码为Base64
     *
     * @param data 要编码的字节数组
     * @return Base64编码的字符串
     */
    public static String encodeBytes(byte[] data) {
        if (data == null) {
            return null;
        }
        return ENCODER.encodeToString(data);
    }

    /**
     * 重写自: opencti-graphql/src/utils/base64.ts - fromB64
     * 从Base64编码的JSON字符串解码为对象
     *
     * @param data   Base64编码的字符串
     * @param clazz  目标类型
     * @param <T>    泛型类型
     * @return 解码后的对象
     */
    public static <T> T decode(String data, Class<T> clazz) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            String json = decodeToString(data);
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON to object", e);
        }
    }

    /**
     * 将Base64编码的字符串解码为原始字符串
     *
     * @param data Base64编码的字符串
     * @return 解码后的字符串
     */
    public static String decodeToString(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        byte[] decoded = DECODER.decode(data);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    /**
     * 将Base64编码的字符串解码为字节数组
     *
     * @param data Base64编码的字符串
     * @return 解码后的字节数组
     */
    public static byte[] decodeToBytes(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return DECODER.decode(data);
    }
}
