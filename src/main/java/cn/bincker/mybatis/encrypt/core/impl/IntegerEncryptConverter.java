package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.EncryptConverter;
import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

public class IntegerEncryptConverter implements EncryptConverter<Integer> {
    @Override
    public Integer convert(byte[] data) {
        if (data.length != Integer.BYTES) throw new InvalidDataException("integer need " + Integer.BYTES + " bytes but got " + data.length);
        int result = 0;
        for (int i = 0; i < Integer.BYTES; i++) {
            result |= data[i] << (Integer.BYTES - i - 1);
        }
        return result;
    }

    @Override
    public byte[] convert(Integer object) {
        byte[] result = new byte[Integer.BYTES];
        for (int i = 0; i < Integer.BYTES; i++) {
            result[i] = (byte) (object >> (Integer.BYTES - i - 1) & 0xff);
        }
        return result;
    }
}
