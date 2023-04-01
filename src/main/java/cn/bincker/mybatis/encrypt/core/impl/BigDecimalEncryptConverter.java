package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.EncryptConverter;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class BigDecimalEncryptConverter implements EncryptConverter<BigDecimal> {
    @Override
    public BigDecimal convert(byte[] data) {
        return new BigDecimal(new String(data, StandardCharsets.US_ASCII));
    }

    @Override
    public byte[] convert(BigDecimal object) {
        return object.toString().getBytes(StandardCharsets.US_ASCII);
    }
}
