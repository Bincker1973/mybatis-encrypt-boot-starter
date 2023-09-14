package cn.bincker.mybatis.encrypt.converter;

public interface EncryptConverter<T> {
    T toObject(byte[] data);
    byte[] toBinary(T object);
}
