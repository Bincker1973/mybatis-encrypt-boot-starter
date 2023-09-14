package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.EncryptConverter;

public abstract class BaseEncryptConverter<T> implements EncryptConverter<T> {
    @Override
    public T toObject(byte[] data) {
        if (data == null) return null;
        return convertNonNull(data);
    }

    @Override
    public byte[] toBinary(T object) {
        if (object == null) return null;
        return convertNonNull(object);
    }

    abstract T convertNonNull(byte[] data);

    abstract byte[] convertNonNull(T object);
}
