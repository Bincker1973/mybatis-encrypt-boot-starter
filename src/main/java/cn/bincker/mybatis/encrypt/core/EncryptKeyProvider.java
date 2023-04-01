package cn.bincker.mybatis.encrypt.core;

@FunctionalInterface
public interface EncryptKeyProvider {
    Object getPassword(Class<?> clazz, String fieldName);
}
