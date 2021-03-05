package com.qttaudio.token;

import com.qttaudio.token.utils.Utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import static com.qttaudio.token.utils.Utils.checkNullParams;

public class TokenVerifier {
    public static boolean verify(final String tok, final String appKey, final String appCert, final String channelName, final Long uid) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        checkNullParams(new HashMap<String, Object>() {{
            put("token", tok);
            put("appKey", appKey);
            put("appCert", appCert);
            put("channelName", channelName);
            put("uid", uid);
        }});
        Token token = Token.parse(tok);
        if (!Token.VERSION.equals(token.getVersion())) {
            return false;
        }
        if (!appKey.equals(token.getAppKey())) {
            return false;
        }
        Builder builder = TokenBuilderFactory.getBuilder(EBuilderType.QTT);
        byte[] contents = builder.getSignContents(appKey, channelName, uid, token.getExtra().Marshal());
        byte[] sign = Utils.hmacSign(appCert, contents);
        if (!Arrays.equals(token.getSign(), sign)) {
            return false;
        }
        if (Utils.crc32(channelName) != token.getChannelCrc()) {
            return false;
        }
        if (Utils.crc32(uid.toString()) != token.getUidCrc()) {
            return false;
        }
        if (token.getExtra().getExpireTime() <= Utils.getTimestamp()) {
            return false;
        }
        return true;
    }
}
