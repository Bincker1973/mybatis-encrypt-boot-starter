package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

import java.time.Instant;

public class InstantEncryptConverter extends BaseEncryptConverter<Instant> {
    @Override
    public Instant convertNonNull(byte[] data) {
        return BinaryUtils.binary2instant(data);
    }

    @Override
    public byte[] convertNonNull(Instant object) {
        return BinaryUtils.instant2binary(object);
    }
}
