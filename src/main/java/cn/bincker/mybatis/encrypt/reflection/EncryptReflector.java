package cn.bincker.mybatis.encrypt.reflection;

import org.apache.ibatis.reflection.Reflector;

import java.util.Set;

public class EncryptReflector extends Reflector {
    private final Set<String> cache;
    public EncryptReflector(Class<?> clazz) {
        super(clazz);
        cache = ReflectionUtils.getEncryptProperties(clazz).keySet();
    }

    @Override
    public Class<?> getGetterType(String propertyName) {
        if (cache.contains(propertyName)) return byte[].class;
        return super.getGetterType(propertyName);
    }
}
