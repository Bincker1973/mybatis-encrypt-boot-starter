package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.utils.BinaryUtils;

public class LongEncryptConverter extends BaseEncryptConverter<Long> {
    @Override
    public Long convertNonNull(byte[] data) {
        return BinaryUtils.binary2long(data);
    }

    @Override
    public byte[] convertNonNull(Long object) {
        return BinaryUtils.long2binary(object);
    }
}
