package cn.bincker.mybatis.encrypt.core;

import java.util.Optional;

public interface EncryptConvertRegister {
    <T> void register(Class<T> type, EncryptConverter<T> converter);
    <T> Optional<EncryptConverter<T>> getConverter(Class<T> clazz);
}
