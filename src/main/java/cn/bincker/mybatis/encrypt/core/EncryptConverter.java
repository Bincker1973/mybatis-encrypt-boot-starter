package cn.bincker.mybatis.encrypt.core;

public interface EncryptConverter<T> {
    T convert(byte[] data);
    byte[] convert(T object);
}
