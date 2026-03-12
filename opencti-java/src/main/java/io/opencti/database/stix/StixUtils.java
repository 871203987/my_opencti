package io.opencti.database.stix;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * STIX宸ュ叿绫? * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts
 * 鎻愪緵STIX ID绠＄悊鍜岄€氱敤宸ュ叿鏂规硶
 *
 * @author OpenCTI Java閲嶅啓椤圭洰
 */
@Slf4j
public final class StixUtils {

    private StixUtils() {
        // 宸ュ叿绫伙紝绂佹瀹炰緥鍖?
    }

    /**
     * 鍘熸柟娉? onlyStableStixIds
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts:onlyStableStixIds
     *
     * 杩囨护鍙繚鐣欑ǔ瀹氱殑STIX ID锛圲UID鐗堟湰涓嶄负1鐨処D锛?     * 绋冲畾鐨処D鏄寚闈炰复鏃剁殑銆佹寔涔呭寲鐨処D
     *
     * @param ids STIX ID鍒楄〃
     * @return 绋冲畾鐨凷TIX ID鍒楄〃
     */
    public static List<String> onlyStableStixIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return ids.stream()
                .filter(Objects::nonNull)
                .filter(id -> !isTransientStixId(id))
                .collect(Collectors.toList());
    }

    /**
     * 鍘熸柟娉? cleanStixIds
     * 鍘熸枃浠? opencti-platform/opencti-graphql/src/database/stix.ts:cleanStixIds
     *
     * 娓呯悊STIX ID鍒楄〃锛屼繚鐣欑ǔ瀹氱殑ID鍜屾渶鏂扮殑涓存椂ID
     * 涓存椂ID锛圲UID v1锛夋寜鏃堕棿鎴虫帓搴忥紝鍙繚鐣欐渶鏂扮殑maxStixIds涓?     *
     * @param ids STIX ID鍒楄〃
     * @param maxStixIds 鏈€澶т繚鐣欑殑涓存椂ID鏁伴噺锛岄粯璁?00
     * @return 娓呯悊鍚庣殑STIX ID鍒楄〃
     */
    public static List<String> cleanStixIds(List<String> ids, int maxStixIds) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> keptIds = new ArrayList<>();
        List<TransientIdInfo> transientIds = new ArrayList<>();

        // 澶勭悊杈撳叆锛岀‘淇濇槸鍒楄〃
        List<String> wIds = ids instanceof List ? ids : Collections.singletonList(ids.get(0));

        for (String stixId : wIds) {
            if (stixId == null || stixId.isEmpty()) {
                continue;
            }

            String[] segments = stixId.split("--");
            if (segments.length != 2) {
                continue;
            }

            String uuid = segments[1];

            if (isTransientStixId(stixId)) {
                // 涓存椂ID锛圲UID v1锛夛紝鎻愬彇鏃堕棿鎴?
                Long timestamp = extractTimestampFromUuidV1(uuid);
                if (timestamp != null) {
                    transientIds.add(new TransientIdInfo(stixId, uuid, timestamp));
                }
            } else {
                // 绋冲畾ID
                keptIds.add(stixId);
            }
        }

        // 鎸夋椂闂存埑闄嶅簭鎺掑簭涓存椂ID锛堟渶鏂扮殑鍦ㄥ墠锛?        transientIds.sort((a, b) -> Long.compare(b.timestamp, a.timestamp));

        // 鍙繚鐣欐渶鏂扮殑maxStixIds涓复鏃禝D
        List<TransientIdInfo> keptTimedIds = transientIds.size() > maxStixIds
                ? transientIds.subList(0, maxStixIds)
                : transientIds;

        // 鍚堝苟缁撴灉
        List<String> result = new ArrayList<>(keptIds);
        result.addAll(keptTimedIds.stream()
                .map(info -> info.id)
                .collect(Collectors.toList()));

        return result;
    }

    /**
     * 鍘熸柟娉? cleanStixIds
     * 浣跨敤榛樿鏈€澶т复鏃禝D鏁伴噺(200)
     *
     * @param ids STIX ID鍒楄〃
     * @return 娓呯悊鍚庣殑STIX ID鍒楄〃
     */
    public static List<String> cleanStixIds(List<String> ids) {
        return cleanStixIds(ids, StixConstants.MAX_TRANSIENT_STIX_IDS);
    }

    /**
     * 妫€鏌ユ槸鍚︿负涓存椂STIX ID锛圲UID鐗堟湰1锛?     * UUID v1鏄熀浜庢椂闂存埑鐨勶紝琚涓烘槸涓存椂鐨?     *
     * @param stixId STIX ID
     * @return 鏄惁涓轰复鏃禝D
     */
    public static boolean isTransientStixId(String stixId) {
        if (stixId == null || stixId.isEmpty()) {
            return false;
        }

        String[] segments = stixId.split("--");
        if (segments.length != 2) {
            return false;
        }

        String uuid = segments[1];
        return getUuidVersion(uuid) == 1;
    }

    /**
     * 鑾峰彇UUID鐗堟湰鍙?     * UUID鏍煎紡: xxxxxxxx-xxxx-Mxxx-Nxxx-xxxxxxxxxxxx
     * M鏄増鏈彿锛堢13涓瓧绗︼紝绱㈠紩12锛?     *
     * @param uuid UUID瀛楃涓?     * @return 鐗堟湰鍙?1-5)锛屽鏋滄牸寮忎笉姝ｇ‘杩斿洖-1
     */
    public static int getUuidVersion(String uuid) {
        if (uuid == null || uuid.length() < 14) {
            return -1;
        }

        // 绉婚櫎鍙兘鐨勮繛瀛楃锛岃幏鍙栧師濮婾UID
        String cleanUuid = uuid.replace("-", "");
        if (cleanUuid.length() != 32) {
            // 灏濊瘯浠庡甫杩炲瓧绗︾殑鏍煎紡鑾峰彇
            char versionChar = uuid.charAt(14);
            try {
                return Integer.parseInt(String.valueOf(versionChar));
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        // 鐗堟湰鍦ㄧ13涓瓧绗︼紙绱㈠紩12锛?
        char versionChar = cleanUuid.charAt(12);
        try {
            return Integer.parseInt(String.valueOf(versionChar));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 浠嶶UID v1鎻愬彇鏃堕棿鎴?     * UUID v1鍖呭惈涓€涓?0浣嶇殑鏃堕棿鎴?     *
     * @param uuid UUID瀛楃涓?     * @return 鏃堕棿鎴筹紙姣锛夛紝濡傛灉涓嶆槸v1鎴栨牸寮忛敊璇繑鍥瀗ull
     */
    public static Long extractTimestampFromUuidV1(String uuid) {
        if (getUuidVersion(uuid) != 1) {
            return null;
        }

        try {
            // 绉婚櫎杩炲瓧绗?
            String cleanUuid = uuid.replace("-", "");
            if (cleanUuid.length() != 32) {
                return null;
            }

            // UUID v1鏃堕棿鎴冲竷灞€:
            // time_low (32 bits) + time_mid (16 bits) + time_high_and_version (16 bits)
            // 瀹為檯涓婃槸涓€涓?0浣嶇殑鏃堕棿鎴筹紝浠?582骞?0鏈?5鏃ュ紑濮嬭绠?
            // 鎻愬彇鏃堕棿鎴崇殑鍚勪釜閮ㄥ垎
            String timeLow = cleanUuid.substring(0, 8);
            String timeMid = cleanUuid.substring(8, 12);
            String timeHigh = cleanUuid.substring(12, 16);

            // 绉婚櫎鐗堟湰鍙凤紙time_high鐨勫墠4浣嶏級
            int timeHighValue = Integer.parseInt(timeHigh, 16);
            timeHighValue &= 0x0FFF; // 娓呴櫎鐗堟湰浣?
            // 缁勫悎60浣嶆椂闂存埑
            long timestamp = ((long) timeHighValue << 48)
                    | (Long.parseLong(timeMid, 16) << 32)
                    | Long.parseLong(timeLow, 16);

            // UUID鏃堕棿鎴虫槸浠?582-10-15寮€濮嬬殑100绾崇闂撮殧鏁?            // 杞崲涓篣nix鏃堕棿鎴筹紙姣锛?            // 1582-10-15鍒?970-01-01鐨勯棿闅? 122192928000000000 (100ns units)
            long uuidEpochOffset = 0x01B21DD213814000L;
            long unixTimestamp = (timestamp - uuidEpochOffset) / 10000;

            return unixTimestamp;
        } catch (Exception e) {
            log.warn("Failed to extract timestamp from UUID v1: {}", uuid, e);
            return null;
        }
    }

    /**
     * 涓存椂ID淇℃伅鍐呴儴绫?     */
    private static class TransientIdInfo {
        final String id;
        final String uuid;
        final long timestamp;

        TransientIdInfo(String id, String uuid, long timestamp) {
            this.id = id;
            this.uuid = uuid;
            this.timestamp = timestamp;
        }
    }
}

