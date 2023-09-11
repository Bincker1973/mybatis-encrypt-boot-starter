package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

import java.util.Date;

public class DateEncryptConverter extends BaseEncryptConverter<Date>{
    @Override
    public Date convertNonNull(byte[] data) {
        return new Date(BinaryUtils.binary2long(data));
    }

    @Override
    public byte[] convertNonNull(Date object) {
        return BinaryUtils.long2binary(object.getTime());
    }
}
