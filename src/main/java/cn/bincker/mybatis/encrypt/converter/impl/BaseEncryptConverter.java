package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.EncryptConverter;

public abstract class BaseEncryptConverter<T> implements EncryptConverter<T> {
    @Override
    public T convert(byte[] data) {
        if (data == null) return null;
        return convertNonNull(data);
    }

    @Override
    public byte[] convert(T object) {
        if (object == null) return null;
        return convertNonNull(object);
    }

    public abstract T convertNonNull(byte[] data);

    public abstract byte[] convertNonNull(T object);
}
