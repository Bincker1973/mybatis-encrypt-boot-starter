package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeEncryptConverter extends BaseEncryptConverter<LocalDateTime> {
    @Override
    public LocalDateTime convertNonNull(byte[] data) {
        return LocalDateTime.ofInstant(BinaryUtils.binary2instant(data), ZoneOffset.UTC);
    }

    @Override
    public byte[] convertNonNull(LocalDateTime object) {
        return BinaryUtils.instant2binary(object.toInstant(ZoneOffset.UTC));
    }
}
