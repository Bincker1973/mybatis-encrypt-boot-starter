package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

public class IntegerEncryptConverter extends BaseEncryptConverter<Integer> {
    @Override
    public Integer convertNonNull(byte[] data) {
        return BinaryUtils.binary2int(data);
    }

    @Override
    public byte[] convertNonNull(Integer object) {
        return BinaryUtils.int2binary(object);
    }
}
