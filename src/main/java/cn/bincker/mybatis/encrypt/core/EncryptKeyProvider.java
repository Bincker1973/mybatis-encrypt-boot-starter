package cn.bincker.mybatis.encrypt.core;

@FunctionalInterface
public interface EncryptKeyProvider {
    Object getKey(Class<?> clazz, String fieldName);
}
