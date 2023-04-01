package cn.bincker.mybatis.encrypt.core;

import org.apache.ibatis.reflection.MetaClass;

import java.util.concurrent.Future;

public interface EncryptExecutor {
    boolean isEncryptField(Class<?> clazz, String fieldName);

    byte[] encrypt(Class<?> clazz, String fieldName, Object value);

    Future<byte[]> putEncryptTask(MetaClass metaClass, Class<?> clazz, String fieldName, Object value);

    void decrypt(Object target, String fieldName, byte[] data);

    Future<Void> putDecryptTask(MetaClass metaClass, Object target, String fieldName, byte[] data);
}
