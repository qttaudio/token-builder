package com.qttaudio.token;

import com.qttaudio.token.utils.Utils;

import java.util.HashMap;

import static com.qttaudio.token.EBuilderType.QTT;
import static com.qttaudio.token.utils.Utils.checkEmptyParams;
import static com.qttaudio.token.utils.Utils.checkNullParams;

public class TokenBuilderHelper {
    /**
     * build builds a token from the specified parameters.
     *
     * @param appKey      The app key assigned by qtt.
     * @param appCert     The app certificate assigned by qtt.
     * @param channelName The channel name to be joined in.
     * @param uid         The user id. A positive unique integer.
     * @param ttl         The ttl, e.g. the time to live of the token.
     *                    If it's 0, it won't timeout;
     *                    if it's positive, it lives up to the time in seconds from now on;
     *                    it's not valid for negatives.
     * @return The generated token in string.
     * @throws Exception
     */
    public static String build(String appKey, String appCert, String channelName, Long uid, Long ttl) throws Exception {
        checkParams(appKey, appCert, channelName, uid);
        Builder builder = TokenBuilderFactory.getBuilder(builderType);
        return builder.build(appKey, appCert, channelName, uid, new Strategy(ttl));
    }

    public static String build(String appKey, String appCert, String channelName, Long uid) throws Exception {
        checkParams(appKey, appCert, channelName, uid);
        Builder builder = TokenBuilderFactory.getBuilder(builderType);
        return builder.build(appKey, appCert, channelName, uid, defaultStrategy);
    }

    public static void setBuilderType(EBuilderType type) {
        builderType = type;
    }

    public static void setTtl(Long ttl) {
        defaultStrategy.setTtl(ttl);
    }

    private static void checkParams(final String appKey, final String appCert, final String channelName, final Long uid) {
        checkNullParams(new HashMap<String, Object>() {{
            put("appKey", appKey);
            put("appCert", appCert);
            put("channelName", channelName);
            put("uid", uid);
        }});
        checkEmptyParams(new HashMap<String, String>() {{
            put("appKey", appKey);
            put("appCert", appCert);
            put("channelName", channelName);
        }});
        if (!Utils.isUUID(appKey)) {
            throw new RuntimeException("app key not uuid");
        }
        if (!Utils.isUUID(appCert)) {
            throw new RuntimeException("app certificate not uuid");
        }
    }

    private static Strategy defaultStrategy = new Strategy();
    private static EBuilderType builderType = QTT;
}
