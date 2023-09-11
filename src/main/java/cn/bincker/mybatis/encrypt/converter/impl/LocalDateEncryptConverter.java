package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

import java.time.LocalDate;

public class LocalDateEncryptConverter extends BaseEncryptConverter<LocalDate> {
    @Override
    public LocalDate convertNonNull(byte[] data) {
        return LocalDate.ofEpochDay(BinaryUtils.binary2long(data));
    }

    @Override
    public byte[] convertNonNull(LocalDate object) {
        return BinaryUtils.long2binary(object.toEpochDay());
    }
}
