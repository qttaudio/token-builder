package com.qttaudio.token;

import com.qttaudio.token.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Token {
    public static Token parse(String tok) {
        Token token = new Token();
        token.tag = tok.substring(0, TAG_LEN);
        token.version = tok.substring(TAG_LEN, TAG_LEN + VERSION_LEN);
        token.appKey = tok.substring(TAG_LEN + VERSION_LEN, TAG_LEN + VERSION_LEN + APPKEY_LEN);
        byte[] data = Utils.base64Decode(tok.substring(TAG_LEN + VERSION_LEN + APPKEY_LEN));
        byte[] sign = new byte[SIGN_LEN];
        System.arraycopy(data, 0, sign, 0, SIGN_LEN);
        token.sign = sign;
        ByteBuffer buf = ByteBuffer.wrap(data, SIGN_LEN, data.length - SIGN_LEN).order(ByteOrder.LITTLE_ENDIAN);
        token.channelCrc = buf.getInt();
        token.uidCrc = buf.getInt();
        Extra extra = new Extra();
        byte[] extraData = new byte[data.length - SIGN_LEN - 8];
        System.arraycopy(data, SIGN_LEN + 8, extraData, 0, extraData.length);
        extra.Unmarshal(extraData);
        token.extra = extra;
        return token;
    }

    public String getVersion() {
        return version;
    }

    public String getAppKey() {
        return appKey;
    }

    public byte[] getSign() {
        return sign;
    }

    public int getChannelCrc() {
        return channelCrc;
    }

    public int getUidCrc() {
        return uidCrc;
    }

    public Extra getExtra() {
        return extra;
    }

    private String tag;
    private String version;
    private String appKey;
    private byte[] sign;
    private int channelCrc;
    private int uidCrc;
    private Extra extra;

    private static final int TAG_LEN = 3;
    private static final int VERSION_LEN = 3;
    private static final int APPKEY_LEN = 32;
    private static final int SIGN_LEN = 32;
    public static final String VERSION = "100";
}
