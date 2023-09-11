package cn.bincker.mybatis.encrypt.converter.impl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class BigDecimalEncryptConverter extends BaseEncryptConverter<BigDecimal> {
    @Override
    public BigDecimal convertNonNull(byte[] data) {
        return new BigDecimal(new String(data, StandardCharsets.US_ASCII));
    }

    @Override
    public byte[] convertNonNull(BigDecimal object) {
        return object.toString().getBytes(StandardCharsets.US_ASCII);
    }
}
