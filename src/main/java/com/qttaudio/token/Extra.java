package com.qttaudio.token;

import com.qttaudio.token.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class Extra implements Marshaler {
    public Extra() {
        expireTime = Long.MAX_VALUE;
        salt = Utils.randomInt();
    }

    public Extra(Long ttl) {
        if (ttl == 0) {
            expireTime = Long.MAX_VALUE;
        } else {
            expireTime = Utils.getTimestamp() + ttl;
        }
        salt = Utils.randomInt();
    }

    @Override
    public byte[] Marshal() {
        ByteBuffer buf = ByteBuffer.allocate(128).order(ByteOrder.LITTLE_ENDIAN);
        buf.putLong(expireTime);
        buf.putInt(salt);
        byte[] data = new byte[buf.position()];
        buf.rewind();
        buf.get(data, 0, data.length);
        return data;
    }

    @Override
    public void Unmarshal(byte[] data) {
        ByteBuffer buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        expireTime = buf.getLong();
        salt = buf.getInt();
    }

    public Long getExpireTime() {
        return expireTime;
    }

    private Long expireTime;
    private int salt;
}
