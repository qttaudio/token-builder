package com.qttaudio.token.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

public class Utils {
    public static final long HMAC_SHA256_LENGTH = 32;
    public static final int VERSION_LENGTH = 3;
    public static final int APP_ID_LENGTH = 32;

    public static byte[] hmacSign(String keyString, byte[] msg) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec keySpec = new SecretKeySpec(keyString.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(keySpec);
        return mac.doFinal(msg);
    }

    /*
    public static byte[] pack(PackableEx packableEx) {
        ByteBuf buffer = new ByteBuf();
        packableEx.marshal(buffer);
        return buffer.asBytes();
    }

    public static void unpack(byte[] data, PackableEx packableEx) {
        ByteBuf buffer = new ByteBuf(data);
        packableEx.unmarshal(buffer);
    }
     */

    public static String base64Encode(byte[] data) {
        byte[] encodedBytes = Base64.encodeBase64(data);
        return new String(encodedBytes);
    }

    public static byte[] base64Decode(String data) {
        return Base64.decodeBase64(data.getBytes());
    }

    public static int crc32(String data) {
        // get bytes from string
        byte[] bytes = data.getBytes();
        return crc32(bytes);
    }

    public static int crc32(byte[] bytes) {
        CRC32 checksum = new CRC32();
        checksum.update(bytes);
        return (int)checksum.getValue();
    }

    public static int getTimestamp() {
        return (int)((new Date().getTime())/1000);
    }

    public static int randomInt() {
        return new SecureRandom().nextInt();
    }

    public static boolean isUUID(String uuid) {
        if (uuid.length() != 32) {
            return false;
        }

        return uuid.matches("\\p{XDigit}+");
    }

    public static void checkEmptyParams(HashMap<String, String> params) {
        for (Map.Entry<String, String> e : params.entrySet()) {
            if (e.getValue().isEmpty()) {
                throw new RuntimeException(String.format("empty parameter %s", e.getKey()));
            }
        }
    }

    public static void checkNullParams(HashMap<String, Object> params) {
        for (Map.Entry<String, Object> e : params.entrySet()) {
            if (e.getValue() == null) {
                throw new RuntimeException(String.format("null parameter %s", e.getKey()));
            }
        }
    }
}
