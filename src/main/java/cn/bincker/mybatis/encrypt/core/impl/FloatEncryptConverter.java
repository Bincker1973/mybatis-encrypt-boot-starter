package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.EncryptConverter;
import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

public class FloatEncryptConverter implements EncryptConverter<Float> {
    @Override
    public Float convert(byte[] data) {
        if (data.length != Float.BYTES) throw new InvalidDataException("long need " + Float.BYTES + " bytes but got " + data.length);
        int result = 0;
        for (int i = 0; i < Float.BYTES; i++) {
            result |= data[i] << (Float.BYTES - i - 1);
        }
        return Float.intBitsToFloat(result);
    }

    @Override
    public byte[] convert(Float object) {
        int integer = Float.floatToIntBits(object);
        byte[] result = new byte[Float.BYTES];
        for (int i = 0; i < Float.BYTES; i++) {
            result[i] = (byte) (integer >> (Float.BYTES - i - 1) & 0xff);
        }
        return result;
    }
}
