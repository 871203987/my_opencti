package io.opencti.database.stix.converter;

import io.opencti.common.exception.FunctionalException;
import io.opencti.types.store.StoreEntity;
import io.opencti.types.stix.common.StixDate;
import io.opencti.types.stix.common.StixObject;
import io.opencti.database.stix.StixConstants;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-converter-utils.ts
 * STIX杞崲宸ュ叿鍑芥暟
 *
 * @author OpenCTI Java閲嶅啓椤圭洰
 */
@Slf4j
public final class StixConverterUtils {

    private StixConverterUtils() {
        // 宸ュ叿绫伙紝绂佹瀹炰緥鍖?
    }

    /**
     * 鍘熸柟娉? assertType
     * 鏂█瀹炰綋绫诲瀷鍖归厤
     *
     * @param expectedType 鏈熸湜绫诲瀷
     * @param actualType   瀹為檯绫诲瀷
     * @throws FunctionalException 绫诲瀷涓嶅尮閰嶆椂鎶涘嚭
     */
    public static void assertType(String expectedType, String actualType) {
        if (!expectedType.equals(actualType)) {
            throw new FunctionalException("Incompatible type", Map.of(
                    "instanceType", actualType,
                    "type", expectedType
            ));
        }
    }

    /**
     * 鍘熸柟娉? convertToStixDate
     * 杞崲鏃ユ湡涓篠TIX鏍煎紡
     *
     * @param date Date瀵硅薄
     * @return STIX鏃ユ湡瀛楃涓诧紝鎴杗ull濡傛灉鏄竟鐣屾棩鏈?     */
    public static String convertToStixDate(Date date) {
        if (date == null) {
            return null;
        }
        long time = date.getTime();
        if (time == StixConstants.FROM_START || time == StixConstants.UNTIL_END) {
            return null;
        }
        return DateTimeFormatter.ISO_INSTANT.format(date.toInstant());
    }

    /**
     * 鍘熸柟娉? convertToStixDate
     * 杞崲鏃ユ湡瀛楃涓蹭负STIX鏍煎紡
     *
     * @param date 鏃ユ湡瀛楃涓?     * @return STIX鏃ユ湡瀛楃涓诧紝鎴杗ull濡傛灉鏄竟鐣屾棩鏈?     */
    public static String convertToStixDate(String date) {
        if (date == null) {
            return null;
        }
        if (StixConstants.FROM_START_STR.equals(date) || StixConstants.UNTIL_END_STR.equals(date)) {
            return null;
        }
        return date;
    }

    /**
     * 鍘熸柟娉? convertToStixDate
     * 杞崲Instant涓篠TIX鏍煎紡
     *
     * @param instant Instant瀵硅薄
     * @return STIX鏃ユ湡瀛楃涓诧紝鎴杗ull濡傛灉鏄竟鐣屾棩鏈?     */
    public static String convertToStixDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        long time = instant.toEpochMilli();
        if (time == StixConstants.FROM_START || time == StixConstants.UNTIL_END) {
            return null;
        }
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }

    /**
     * 鍘熸柟娉? convertToStixDate
     * 杞崲StixDate涓篠TIX鏍煎紡
     *
     * @param stixDate StixDate瀵硅薄
     * @return STIX鏃ユ湡瀛楃涓诧紝鎴杗ull濡傛灉鏄竟鐣屾棩鏈?     */
    public static String convertToStixDate(StixDate stixDate) {
        if (stixDate == null) {
            return null;
        }
        // StixDate閫氬父浠ュ瓧绗︿覆褰㈠紡瀛樺偍
        String dateStr = stixDate.toString();
        return convertToStixDate(dateStr);
    }

    /**
     * 鍘熸柟娉? cleanObject
     * 娓呯悊瀵硅薄涓殑绌哄睘鎬?     *
     * @param data 杈撳叆瀵硅薄
     * @param <T>  瀵硅薄绫诲瀷
     * @return 娓呯悊鍚庣殑瀵硅薄
     */
    public static <T extends StixObject> T cleanObject(T data) {
        if (data == null) {
            return null;
        }
        // 鍦↗ava涓紝鎴戜滑浣跨敤@JsonInclude(JsonInclude.Include.NON_NULL)娉ㄨВ
        // 鏉ヨ嚜鍔ㄥ鐞嗙┖灞炴€х殑搴忓垪鍖栵紝鎵€浠ヨ繖閲屼笉闇€瑕侀澶栧鐞?
        return data;
    }

    /**
     * 鍘熸柟娉? isValidStix
     * 楠岃瘉STIX瀵硅薄鏈夋晥鎬?     *
     * @param data STIX瀵硅薄
     * @return 鏄惁鏈夋晥
     */
    public static boolean isValidStix(StixObject data) {
        if (data == null) {
            return false;
        }
        // 鍩烘湰楠岃瘉锛氭鏌ュ繀闇€瀛楁
        return data.getId() != null && data.getId() instanceof io.opencti.types.stix.common.StixId
                && data.getType() != null && !data.getType().isEmpty()
                && data.getSpecVersion() != null && !data.getSpecVersion().isEmpty();
    }

    /**
     * 鍘熸柟娉? convertObjectReferences
     * 杞崲瀵硅薄寮曠敤
     *
     * @param instance   瀛樺偍瀹炰綋
     * @param isInferred 鏄惁鍙繑鍥炴帹鏂殑寮曠敤
     * @return 寮曠敤ID鍒楄〃
     */
    public static List<String> convertObjectReferences(StoreEntity instance, boolean isInferred) {
        // StoreEntity娌℃湁getObjects鏂规硶锛屾鏂规硶闇€瑕佹牴鎹疄闄呮儏鍐佃皟鏁?
        // 鏆傛椂杩斿洖绌哄垪琛?
        return Collections.emptyList();
    }

    /**
     * 鍘熸柟娉? convertObjectReferences
     * 杞崲瀵硅薄寮曠敤锛堥粯璁ら潪鎺ㄦ柇锛?     *
     * @param instance 瀛樺偍瀹炰綋
     * @return 寮曠敤ID鍒楄〃
     */
    public static List<String> convertObjectReferences(StoreEntity instance) {
        return convertObjectReferences(instance, false);
    }

    /**
     * 杞崲瀵硅薄寮曠敤
     * 鍘熸柟娉? convertObjectReferences
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-converter-utils.ts:convertObjectReferences
     *
     * 灏嗗唴閮ㄥ紩鐢↖D杞崲涓篠TIX ID鏍煎紡
     *
     * @param references 寮曠敤鍒楄〃
     * @return 杞崲鍚庣殑寮曠敤鍒楄〃
     */
    public static List<String> convertObjectReferences(List<String> references) {
        if (references == null || references.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (String ref : references) {
            if (ref == null || ref.isEmpty()) {
                continue;
            }

            // 濡傛灉宸茬粡鏄疭TIX ID鏍煎紡锛堝寘鍚?-锛夛紝鐩存帴娣诲姞
            if (ref.contains("--")) {
                result.add(ref);
                continue;
            }

            // 濡傛灉鏄疷UID鏍煎紡锛屽皾璇曟瀯寤篠TIX ID
            // 鏍煎紡: type--uuid
            if (ref.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
                // 鏃犳硶纭畾绫诲瀷锛岃烦杩?                continue;
            }

            // 鍏朵粬鎯呭喌锛屽皾璇曡В鏋愪负STIX ID
            result.add(ref);
        }

        return result;
    }

    /**
     * 杞崲瀵硅薄寮曠敤锛堝甫绫诲瀷锛?     * 鍘熸柟娉? convertObjectReferences
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-converter-utils.ts:convertObjectReferences
     *
     * @param references 寮曠敤鍒楄〃
     * @param defaultType 榛樿绫诲瀷
     * @return 杞崲鍚庣殑寮曠敤鍒楄〃
     */
    public static List<String> convertObjectReferences(List<String> references, String defaultType) {
        if (references == null || references.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (String ref : references) {
            if (ref == null || ref.isEmpty()) {
                continue;
            }

            // 濡傛灉宸茬粡鏄疭TIX ID鏍煎紡锛堝寘鍚?-锛夛紝鐩存帴娣诲姞
            if (ref.contains("--")) {
                result.add(ref);
                continue;
            }

            // 濡傛灉鏄疷UID鏍煎紡锛屼娇鐢ㄩ粯璁ょ被鍨嬫瀯寤篠TIX ID
            if (ref.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
                if (defaultType != null && !defaultType.isEmpty()) {
                    result.add(defaultType + "--" + ref);
                }
                continue;
            }

            // 鍏朵粬鎯呭喌锛屽皾璇曡В鏋愪负STIX ID
            result.add(ref);
        }

        return result;
    }



    /**
     * 鍘熸柟娉? buildStixId
     * 鏋勫缓STIX ID
     *
     * @param prefix STIX ID鍓嶇紑
     * @param uuid   UUID
     * @return STIX ID
     */
    public static String buildStixId(String prefix, String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            return prefix + UUID.randomUUID().toString();
        }
        // 濡傛灉uuid宸茬粡鍖呭惈鍓嶇紑锛岀洿鎺ヨ繑鍥?
        if (uuid.startsWith(prefix)) {
            return uuid;
        }
        return prefix + uuid;
    }

    /**
     * 娓呯悊绌哄€糓ap
     *
     * @param map 杈撳叆Map
     * @param <K> 閿被鍨?     * @param <V> 鍊肩被鍨?     * @return 娓呯悊鍚庣殑Map
     */
    public static <K, V> Map<K, V> cleanMap(Map<K, V> map) {
        if (map == null) {
            return null;
        }
        Map<K, V> result = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * 娓呯悊绌哄€煎垪琛?     *
     * @param list 杈撳叆鍒楄〃
     * @param <T>  鍏冪礌绫诲瀷
     * @return 娓呯悊鍚庣殑鍒楄〃
     */
    public static <T> List<T> cleanList(List<T> list) {
        if (list == null) {
            return null;
        }
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }
}

