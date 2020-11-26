package com.qttaudio.token;

import com.qttaudio.token.utils.Utils;

import java.util.HashMap;

import static com.qttaudio.token.EBuilderType.QTT;
import static com.qttaudio.token.utils.Utils.checkEmptyParams;
import static com.qttaudio.token.utils.Utils.checkNullParams;

public class TokenBuilder {
    public static String build(String appKey, String appSecret, String channelName, Long uid, Long ttl) throws Exception {
        checkParams(appKey, appSecret, channelName, uid);
        Builder builder = TokenBuilderFactory.getBuilder(builderType);
        return builder.build(appKey, appSecret, channelName, uid, new Strategy(ttl));
    }

    public static String build(String appKey, String appSecret, String channelName, Long uid) throws Exception {
        checkParams(appKey, appSecret, channelName, uid);
        Builder builder = TokenBuilderFactory.getBuilder(builderType);
        return builder.build(appKey, appSecret, channelName, uid, defaultStrategy);
    }

    public static void setBuilderType(EBuilderType type) {
        builderType = type;
    }

    public static void setTtl(Long ttl) {
        defaultStrategy.setTtl(ttl);
    }

    private static void checkParams(final String appKey, final String appSecret, final String channelName, final Long uid) {
        checkNullParams(new HashMap<String, Object>(){{
            put("appKey", appKey);
            put("appSecret", appSecret);
            put("channelName", channelName);
            put("uid", uid);
        }});
        checkEmptyParams(new HashMap<String, String>(){{
            put("appKey", appKey);
            put("appSecret", appSecret);
            put("channelName", channelName);
        }});
        if (!Utils.isUUID(appKey)) {
            throw new RuntimeException("app key not uuid");
        }
        if (!Utils.isUUID(appSecret)) {
            throw new RuntimeException("app secret not uuid");
        }
    }

    private static Strategy defaultStrategy = new Strategy();
    private static EBuilderType builderType = QTT;
}
