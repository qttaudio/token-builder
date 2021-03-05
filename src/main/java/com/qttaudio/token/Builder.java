package com.qttaudio.token;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface Builder {
    String build(String appKey, String appCert, String channelName, Long uid, Strategy strategy) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

    byte[] getSignContents(String appKey, String channelName, Long uid, byte[] extra) throws IOException;
}
