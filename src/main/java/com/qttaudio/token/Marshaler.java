package com.qttaudio.token;

public interface Marshaler {
    byte[] Marshal();
    void Unmarshal(byte[] data);
}
