package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.EncryptConverter;
import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

public class ShortEncryptConverter implements EncryptConverter<Short> {
    @Override
    public Short convert(byte[] data) {
        if (data.length != Short.BYTES) throw new InvalidDataException("long need " + Short.BYTES + " bytes but got " + data.length);
        short result = 0;
        for (int i = 0; i < Short.BYTES; i++) {
            result |= data[i] << (Short.BYTES - i - 1);
        }
        return result;
    }

    @Override
    public byte[] convert(Short object) {
        byte[] result = new byte[Short.BYTES];
        for (int i = 0; i < Short.BYTES; i++) {
            result[i] = (byte) (object >> (Short.BYTES - i - 1) & 0xff);
        }
        return result;
    }
}
