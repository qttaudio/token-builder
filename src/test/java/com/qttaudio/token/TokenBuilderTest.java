package com.qttaudio.token;

import org.junit.Assert;
import org.junit.Test;

public class TokenBuilderTest {

  @Test
  public void build() throws Exception {
    String appKey = "27539780c4f5be815027bf9c864679b4";
    String appSecret = "031a4f0c1b63e5cffef53e572650a991";
    String channel = "88888";
    Long uid = 123456L;

    // token won't expire
    String token = TokenBuilder.build(appKey, appSecret, channel, uid);
    Assert.assertTrue(TokenVerifier.verify(token, appKey, appSecret, channel, uid));

    // token with expire time
    token = TokenBuilder.build(appKey, appSecret, channel, uid, 1L);
    Assert.assertTrue(TokenVerifier.verify(token, appKey, appSecret, channel, uid));
    Thread.sleep(1000);
    Assert.assertFalse(TokenVerifier.verify(token, appKey, appSecret, channel, uid));
  }
}