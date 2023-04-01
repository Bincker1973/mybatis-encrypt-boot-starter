package cn.bincker.mybatis.encrypt.core;

public interface EncryptConvertRegister {
    <T> void register(Class<T> type, EncryptConverter<T> converter);
    <T> EncryptConverter<T> getConverter(Class<T> clazz);
}
