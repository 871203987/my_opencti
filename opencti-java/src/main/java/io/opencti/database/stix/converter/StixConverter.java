package io.opencti.database.stix.converter;

import io.opencti.types.store.StoreObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix-common-converter.ts
 * STIX閫氱敤杞崲鍣ㄥ叆鍙?
 *
 * @author OpenCTI Java閲嶅啓椤圭洰
 */
@Slf4j
public final class StixConverter {

    private StixConverter() {
        // 宸ュ叿绫伙紝绂佹瀹炰緥鍖?
    }

    /**
     * STIX鐗堟湰鏋氫妇
     */
    public enum Version {
        V2_0("2.0"),
        V2_1("2.1");

        private final String value;

        Version(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Version fromString(String version) {
            if (version == null) {
                return V2_1; // 榛樿浣跨敤2.1
            }
            switch (version) {
                case "2.0":
                    return V2_0;
                case "2.1":
                default:
                    return V2_1;
            }
        }
    }

    /**
     * 鍘熸柟娉? convertStoreToStix
     * 缁熶竴杞崲鍏ュ彛 - 灏嗗瓨鍌ㄥ璞¤浆鎹负STIX鏍煎紡
     *
     * @param instance 瀛樺偍瀵硅薄
     * @param version  STIX鐗堟湰
     * @return STIX瀵硅薄
     */
    public static Map<String, Object> convertStoreToStix(StoreObject instance, Version version) {
        if (instance == null) {
            return null;
        }

        if (version == null) {
            version = Version.V2_1; // 榛樿浣跨敤2.1
        }

        switch (version) {
            case V2_0:
                return Stix20Converter.convertStoreToStix_2_0(instance);
            case V2_1:
            default:
                return Stix21Converter.convertStoreToStix_2_1(instance);
        }
    }

    /**
     * 鍘熸柟娉? convertStoreToStix
     * 缁熶竴杞崲鍏ュ彛 - 灏嗗瓨鍌ㄥ璞¤浆鎹负STIX鏍煎紡锛堥粯璁?.1鐗堟湰锛?
     *
     * @param instance 瀛樺偍瀵硅薄
     * @return STIX瀵硅薄
     */
    public static Map<String, Object> convertStoreToStix(StoreObject instance) {
        return convertStoreToStix(instance, Version.V2_1);
    }

    /**
     * 鍘熸柟娉? convertStoreToStix
     * 缁熶竴杞崲鍏ュ彛 - 灏嗗瓨鍌ㄥ璞¤浆鎹负STIX鏍煎紡锛堟寚瀹氱増鏈瓧绗︿覆锛?
     *
     * @param instance       瀛樺偍瀵硅薄
     * @param versionString  STIX鐗堟湰瀛楃涓?
     * @return STIX瀵硅薄
     */
    public static Map<String, Object> convertStoreToStix(StoreObject instance, String versionString) {
        return convertStoreToStix(instance, Version.fromString(versionString));
    }
}

