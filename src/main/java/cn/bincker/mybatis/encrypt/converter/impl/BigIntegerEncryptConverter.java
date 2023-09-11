package cn.bincker.mybatis.encrypt.converter.impl;

import java.math.BigInteger;

public class BigIntegerEncryptConverter extends BaseEncryptConverter<BigInteger> {
    @Override
    public BigInteger convertNonNull(byte[] data) {
        return new BigInteger(data);
    }

    @Override
    public byte[] convertNonNull(BigInteger object) {
        return object.toByteArray();
    }
}
