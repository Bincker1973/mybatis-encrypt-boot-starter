package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.EncryptConvertRegister;
import cn.bincker.mybatis.encrypt.core.EncryptConverter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DefaultEncryptConvertRegister implements EncryptConvertRegister {
    private final Map<Class<?>, EncryptConverter<?>> converterMap = new HashMap<>();

    public DefaultEncryptConvertRegister() {
        register(Short.class, new ShortEncryptConverter());
        register(Integer.class, new IntegerEncryptConverter());
        register(Long.class, new LongEncryptConverter());
        register(Float.class, new FloatEncryptConverter());
        register(Double.class, new DoubleEncryptConverter());
        register(BigDecimal.class, new BigDecimalEncryptConverter());
        register(String.class, new StringEncryptConverter());
    }

    @Override
    public synchronized  <T> void register(Class<T> type, EncryptConverter<T> converter) {
        converterMap.put(type, converter);
    }

    @Override
    public <T> EncryptConverter<T> getConverter(Class<T> clazz) {
        //noinspection unchecked
        return (EncryptConverter<T>) converterMap.get(clazz);
    }
}
