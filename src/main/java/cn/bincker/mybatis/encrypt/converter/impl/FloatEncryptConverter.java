package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

public class FloatEncryptConverter extends BaseEncryptConverter<Float> {
    @Override
    public Float convertNonNull(byte[] data) {
        if (data.length != Float.BYTES) throw new InvalidDataException("long need " + Float.BYTES + " bytes but got " + data.length);
        int result = 0;
        for (int i = 0; i < Float.BYTES; i++) {
            result = result << 8 | data[i] & 0xff;
        }
        return Float.intBitsToFloat(result);
    }

    @Override
    public byte[] convertNonNull(Float object) {
        int integer = Float.floatToIntBits(object);
        byte[] result = new byte[Float.BYTES];
        for (int i = 0; i < Float.BYTES; i++) {
            result[Float.BYTES - i - 1] = (byte) (integer & 0xff);
            integer >>= 8;
        }
        return result;
    }
}
