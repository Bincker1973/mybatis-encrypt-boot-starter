package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

public class ShortEncryptConverter extends BaseEncryptConverter<Short> {
    @Override
    public Short convertNonNull(byte[] data) {
        if (data.length != Short.BYTES) throw new InvalidDataException("long need " + Short.BYTES + " bytes but got " + data.length);
        short result = 0;
        for (int i = 0; i < Short.BYTES; i++) {
            result = (short) (result << 8 | data[i] & 0xff);
        }
        return result;
    }

    @Override
    public byte[] convertNonNull(Short object) {
        byte[] result = new byte[Short.BYTES];
        for (int i = 0; i < Short.BYTES; i++) {
            result[Short.BYTES - i - 1] = (byte) (object & 0xff);
            object = (short) (object >> 8);
        }
        return result;
    }
}
