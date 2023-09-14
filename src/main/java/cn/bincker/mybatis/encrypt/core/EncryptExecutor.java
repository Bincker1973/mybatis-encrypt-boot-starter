package cn.bincker.mybatis.encrypt.core;

import cn.bincker.mybatis.encrypt.converter.EncryptConvertRegister;
import cn.bincker.mybatis.encrypt.entity.EncryptProperty;
import org.apache.ibatis.reflection.MetaClass;

import java.util.Optional;
import java.util.concurrent.Future;

public interface EncryptExecutor {
    Encryptor getEncryptor();
    EncryptKeyProvider getKeyProvider();
    EncryptConvertRegister getConverterRegister();
    boolean isEncryptField(Class<?> clazz, String fieldName);

    Optional<EncryptProperty> getEncryptField(Class<?> clazz, String fieldName);

    boolean hasEncryptField(Class<?> clazz);

    byte[] encrypt(MetaClass metaClass, Class<?> clazz, String fieldName, Object value);

    @SuppressWarnings("unused")
    Future<byte[]> putEncryptTask(MetaClass metaClass, Class<?> clazz, String fieldName, Object value);

    Object decrypt(MetaClass metaClass, Object target, String fieldName, byte[] data);

    Future<?> putDecryptTask(MetaClass metaClass, Object target, String fieldName, byte[] data);
}
