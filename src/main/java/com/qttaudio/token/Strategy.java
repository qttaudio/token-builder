package com.qttaudio.token;

public class Strategy {
    public Strategy() {}

    public Strategy(Long ttl) {
        this.ttl = ttl;
    }

    public Long getTtl() {
        if (ttl == null) {
            ttl = 0L;
        }
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    private Long ttl;
}
