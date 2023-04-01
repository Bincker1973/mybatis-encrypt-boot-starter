package cn.bincker.mybatis.encrypt.core;

@FunctionalInterface
public interface EncryptSaltProvider {
    Object getSalt(Class<?> clazz, String fieldName);
}
