package io.opencti.common.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 重写自: opencti-graphql/src/utils/http-client.ts
 * HTTP客户端工具类
 */
public final class HttpClientUtils {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private HttpClientUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * 重写自: opencti-graphql/src/utils/http-client.ts - get
     * 执行HTTP GET请求
     *
     * @param url     请求URL
     * @param headers 请求头
     * @return 响应内容
     */
    public static ResponseEntity<String> get(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        return REST_TEMPLATE.exchange(
                url,
                HttpMethod.GET,
                new org.springframework.http.HttpEntity<>(httpHeaders),
                String.class
        );
    }

    /**
     * 重写自: opencti-graphql/src/utils/http-client.ts - post
     * 执行HTTP POST请求
     *
     * @param url     请求URL
     * @param data    请求体
     * @param headers 请求头
     * @return 响应内容
     */
    public static ResponseEntity<String> post(String url, Object data, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        return REST_TEMPLATE.exchange(
                url,
                HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(data, httpHeaders),
                String.class
        );
    }

    /**
     * 重写自: opencti-graphql/src/utils/http-client.ts - delete
     * 执行HTTP DELETE请求
     *
     * @param url     请求URL
     * @param headers 请求头
     * @return 响应内容
     */
    public static ResponseEntity<String> delete(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        return REST_TEMPLATE.exchange(
                url,
                HttpMethod.DELETE,
                new org.springframework.http.HttpEntity<>(httpHeaders),
                String.class
        );
    }

    /**
     * 重写自: opencti-graphql/src/utils/http-client.ts - head
     * 执行HTTP HEAD请求
     *
     * @param url     请求URL
     * @param headers 请求头
     * @return 响应内容
     */
    public static ResponseEntity<String> head(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        return REST_TEMPLATE.exchange(
                url,
                HttpMethod.HEAD,
                new org.springframework.http.HttpEntity<>(httpHeaders),
                String.class
        );
    }

    private static HttpHeaders createHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::add);
        }
        return httpHeaders;
    }
}
