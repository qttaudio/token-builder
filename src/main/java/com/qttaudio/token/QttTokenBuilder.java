package com.qttaudio.token;

import com.qttaudio.token.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class QttTokenBuilder implements Builder {
    public String build(String appKey, String appCert, String channelName, Long uid,
                        Strategy strategy) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        byte[] extra = new Extra(strategy.getTtl()).Marshal();
        byte[] contents = getSignContents(appKey, channelName, uid, extra);
        byte[] sign = Utils.hmacSign(appCert, contents);
        ByteBuffer buf = ByteBuffer.allocate(sign.length + 8 + extra.length).order(ByteOrder.LITTLE_ENDIAN);
        buf.put(sign)
                .putInt(Utils.crc32(channelName))
                .putInt(Utils.crc32(uid.toString()))
                .put(extra);
        StringBuilder builder = new StringBuilder(TAG);
        builder.append(Token.VERSION).append(appKey).append(Utils.base64Encode(buf.array()));
        return builder.toString();
    }

    public byte[] getSignContents(String appKey, String channelName, Long uid, byte[] extra) throws IOException {
        ByteArrayOutputStream contents = new ByteArrayOutputStream();
        contents.write(appKey.getBytes());
        contents.write(channelName.getBytes());
        contents.write(uid.toString().getBytes());
        contents.write(extra);
        return contents.toByteArray();
    }

    private static final String TAG = "Qtt";
}
