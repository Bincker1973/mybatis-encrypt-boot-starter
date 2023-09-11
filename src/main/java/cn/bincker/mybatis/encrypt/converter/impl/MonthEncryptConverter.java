package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

import java.time.Month;

public class MonthEncryptConverter extends BaseEncryptConverter<Month>{
    @Override
    public Month convertNonNull(byte[] data) {
        return Month.of(BinaryUtils.binary2int(data));
    }

    @Override
    public byte[] convertNonNull(Month object) {
        return BinaryUtils.int2binary(object.getValue());
    }
}
