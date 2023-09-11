package cn.bincker.mybatis.encrypt.converter.impl;

public class StringEncryptConverter extends BaseEncryptConverter<String> {
    @Override
    public String convertNonNull(byte[] data) {
        return new String(data);
    }

    @Override
    public byte[] convertNonNull(String object) {
        return object.getBytes();
    }
}
