package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.converter.EncryptConvertRegister;
import cn.bincker.mybatis.encrypt.converter.EncryptConverter;
import cn.bincker.mybatis.encrypt.core.*;
import cn.bincker.mybatis.encrypt.entity.EncryptProperty;
import cn.bincker.mybatis.encrypt.exception.MybatisEncryptException;
import cn.bincker.mybatis.encrypt.reflection.ReflectionUtils;
import org.apache.ibatis.reflection.MetaClass;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

public class DefaultEncryptExecutor implements EncryptExecutor {
    private final Encryptor encryptor;
    private final EncryptConvertRegister encryptConvertRegister;
    private final Map<Class<?>, Map<String, EncryptProperty>> encryptPropertyCache;
    private final ExecutorService executor;
    private final EncryptKeyProvider keyProvider;

    public DefaultEncryptExecutor(Encryptor encryptor, EncryptConvertRegister encryptConvertRegister, EncryptKeyProvider keyProvider) {
        this.encryptor = encryptor;
        this.encryptConvertRegister = encryptConvertRegister;
        this.keyProvider = keyProvider;
        encryptPropertyCache = new ConcurrentHashMap<>();
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public Encryptor getEncryptor() {
        return encryptor;
    }

    @Override
    public EncryptKeyProvider getKeyProvider() {
        return keyProvider;
    }

    @Override
    public EncryptConvertRegister getConverterRegister() {
        return encryptConvertRegister;
    }

    @Override
    public boolean isEncryptField(Class<?> clazz, String fieldName) {
        return encryptPropertyCache.computeIfAbsent(clazz, ReflectionUtils::getEncryptProperties).containsKey(fieldName);
    }

    @Override
    public Optional<EncryptProperty> getEncryptField(Class<?> clazz, String fieldName) {
        return Optional.ofNullable(encryptPropertyCache.computeIfAbsent(clazz, ReflectionUtils::getEncryptProperties).get(fieldName));
    }

    @Override
    public boolean hasEncryptField(Class<?> clazz) {
        return !encryptPropertyCache.computeIfAbsent(clazz, ReflectionUtils::getEncryptProperties).isEmpty();
    }

    @Override
    public byte[] encrypt(MetaClass metaClass, Class<?> clazz, String fieldName, Object value) {
        if (value == null) return null;
        var type = value.getClass();
        byte[] rawData;
        if (type != byte[].class){
            //noinspection unchecked
            var converter = (EncryptConverter<Object>) encryptConvertRegister.getConverter(type)
                    .orElseThrow(()->new MybatisEncryptException("not found converter: type = " + type));
            rawData = converter.toBinary(value);
        }else {
            rawData = (byte[]) value;
        }
        return encryptor.encrypt(rawData, keyProvider.getKey(clazz, fieldName));
    }

    @Override
    public Future<byte[]> putEncryptTask(MetaClass metaClass, Class<?> clazz, String fieldName, Object value) {
        return executor.submit(()->this.encrypt(metaClass, clazz, fieldName, value));
    }

    @Override
    public Object decrypt(MetaClass metaClass, Object target, String fieldName, byte[] data) {
        if (data == null) return null;
        var cls = target.getClass();
        var decryptedData = encryptor.decrypt(data, keyProvider.getKey(cls, fieldName));
        var propertyOptional = getEncryptField(cls, fieldName);
        if (propertyOptional.isEmpty()) return null;
        var property = propertyOptional.get();
        Class<?> type = property.getter().getReturnType();
        Object result;
        if (type == byte[].class){
            result = decryptedData;
        }else{
            result = encryptConvertRegister.getConverter(type)
                    .orElseThrow(()->new MybatisEncryptException("not found converter: type = " + type))
                    .toObject(decryptedData);
        }
        var invoker = metaClass.getSetInvoker(fieldName);
        try {
            invoker.invoke(target, new Object[]{result});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new MybatisEncryptException(e);
        }
        return result;
    }

    @Override
    public Future<?> putDecryptTask(MetaClass metaClass, Object target, String fieldName, byte[] data) {
        return executor.submit(()->this.decrypt(metaClass, target, fieldName, data));
    }
}
