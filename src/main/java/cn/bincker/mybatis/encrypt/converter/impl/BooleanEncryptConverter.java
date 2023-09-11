package cn.bincker.mybatis.encrypt.converter.impl;

public class BooleanEncryptConverter extends BaseEncryptConverter<Boolean> {
    @Override
    public Boolean convertNonNull(byte[] data) {
        return data[0] == 1;
    }

    @Override
    public byte[] convertNonNull(Boolean object) {
        return new byte[]{object ? 1 : (byte) 0};
    }
}
