package cn.bincker.mybatis.encrypt.core;

import org.apache.ibatis.reflection.MetaClass;

import java.util.concurrent.Future;

public interface EncryptExecutor {
    boolean isEncryptField(Class<?> clazz, String fieldName);

    byte[] encrypt(MetaClass metaClass, Class<?> clazz, String fieldName, Object value);

    Future<byte[]> putEncryptTask(MetaClass metaClass, Class<?> clazz, String fieldName, Object value);

    void decrypt(MetaClass metaClass, Object target, String fieldName, byte[] data);

    Future<?> putDecryptTask(MetaClass metaClass, Object target, String fieldName, byte[] data);
}
