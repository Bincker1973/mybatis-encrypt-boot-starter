package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.EncryptConverter;
import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

public class LongEncryptConverter implements EncryptConverter<Long> {
    @Override
    public Long convert(byte[] data) {
        if (data.length != Long.BYTES) throw new InvalidDataException("long need " + Long.BYTES + " bytes but got " + data.length);
        long result = 0;
        for (int i = 0; i < Long.BYTES; i++) {
            result |= data[i] << (Long.BYTES - i - 1);
        }
        return result;
    }

    @Override
    public byte[] convert(Long object) {
        byte[] result = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++) {
            result[i] = (byte) (object >> (Long.BYTES - i - 1) & 0xff);
        }
        return result;
    }
}
