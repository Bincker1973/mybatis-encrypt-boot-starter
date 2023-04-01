package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.EncryptConverter;

public class StringEncryptConverter implements EncryptConverter<String> {
    @Override
    public String convert(byte[] data) {
        return new String(data);
    }

    @Override
    public byte[] convert(String object) {
        return object.getBytes();
    }
}
