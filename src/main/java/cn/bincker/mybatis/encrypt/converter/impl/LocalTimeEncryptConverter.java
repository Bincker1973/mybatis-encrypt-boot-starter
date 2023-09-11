package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

import java.time.LocalTime;

public class LocalTimeEncryptConverter extends BaseEncryptConverter<LocalTime> {
    @Override
    public LocalTime convertNonNull(byte[] data) {
        return LocalTime.ofNanoOfDay(BinaryUtils.binary2long(data));
    }

    @Override
    public byte[] convertNonNull(LocalTime object) {
        return BinaryUtils.long2binary(object.toNanoOfDay());
    }
}
