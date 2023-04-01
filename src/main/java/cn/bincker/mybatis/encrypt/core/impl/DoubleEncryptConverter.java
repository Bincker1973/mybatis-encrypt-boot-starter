package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.EncryptConverter;
import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

public class DoubleEncryptConverter implements EncryptConverter<Double> {
    @Override
    public Double convert(byte[] data) {
        if (data.length != Double.BYTES) throw new InvalidDataException("long need " + Double.BYTES + " bytes but got " + data.length);
        long result = 0;
        for (int i = 0; i < Double.BYTES; i++) {
            result |= data[i] << (Double.BYTES - i - 1);
        }
        return Double.longBitsToDouble(result);
    }

    @Override
    public byte[] convert(Double object) {
        long l = Double.doubleToLongBits(object);
        byte[] result = new byte[Double.BYTES];
        for (int i = 0; i < Double.BYTES; i++) {
            result[i] = (byte) (l >> (Double.BYTES - i - 1) & 0xff);
        }
        return result;
    }
}
