package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

public class DoubleEncryptConverter extends BaseEncryptConverter<Double> {
    @Override
    public Double convertNonNull(byte[] data) {
        if (data.length != Double.BYTES) throw new InvalidDataException("long need " + Double.BYTES + " bytes but got " + data.length);
        long result = 0;
        for (int i = 0; i < Double.BYTES; i++) {
            result = result << 8 | data[i] & 0xff;
        }
        return Double.longBitsToDouble(result);
    }

    @Override
    public byte[] convertNonNull(Double object) {
        long l = Double.doubleToLongBits(object);
        byte[] result = new byte[Double.BYTES];
        for (int i = 0; i < Double.BYTES; i++) {
            result[Double.BYTES - i - 1] = (byte) (l & 0xff);
            l >>= 8;
        }
        return result;
    }
}
