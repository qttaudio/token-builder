package com.qttaudio.token;

class TokenBuilderFactory {
    static Builder getBuilder(EBuilderType type) throws RuntimeException {
        switch (type) {
            case QTT:
                return new QttTokenBuilder();
            default:
                throw new RuntimeException(String.format("token builder type %s not support", type));
        }
    }
}
