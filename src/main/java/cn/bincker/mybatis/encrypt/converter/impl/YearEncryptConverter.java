package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

import java.time.Year;

public class YearEncryptConverter extends BaseEncryptConverter<Year>{
    @Override
    public Year convertNonNull(byte[] data) {
        return Year.of(BinaryUtils.binary2int(data));
    }

    @Override
    public byte[] convertNonNull(Year object) {
        return BinaryUtils.int2binary(object.getValue());
    }
}
