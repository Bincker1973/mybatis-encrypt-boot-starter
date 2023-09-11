package cn.bincker.mybatis.encrypt.converter;

public interface EncryptConverter<T> {
    T convert(byte[] data);
    byte[] convert(T object);
}
