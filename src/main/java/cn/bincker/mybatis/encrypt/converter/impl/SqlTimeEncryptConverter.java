package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

import java.sql.Time;

public class SqlTimeEncryptConverter extends BaseEncryptConverter<Time> {
    @Override
    public Time convertNonNull(byte[] data) {
        return new Time(BinaryUtils.binary2long(data));
    }

    @Override
    public byte[] convertNonNull(Time object) {
        return BinaryUtils.long2binary(object.getTime());
    }
}
