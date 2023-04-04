package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import cn.bincker.mybatis.encrypt.annotation.IntegrityCheckFor;
import cn.bincker.mybatis.encrypt.core.*;
import cn.bincker.mybatis.encrypt.exception.MybatisEncryptException;
import org.apache.ibatis.reflection.MetaClass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

public class DefaultEncryptExecutor implements EncryptExecutor {
    private final Encryptor encryptor;
    private final EncryptConvertRegister encryptConvertRegister;
    private final Map<FieldKey, EncryptFieldInfo> encryptFieldCache;
    private final ExecutorService executor;
    private final EncryptKeyProvider keyProvider;

    public DefaultEncryptExecutor(Encryptor encryptor, EncryptConvertRegister encryptConvertRegister, EncryptKeyProvider keyProvider) {
        this.encryptor = encryptor;
        this.encryptConvertRegister = encryptConvertRegister;
        this.keyProvider = keyProvider;
        encryptFieldCache = new ConcurrentHashMap<>();
        executor = Executors.newWorkStealingPool();
    }

    @Override
    public boolean isEncryptField(Class<?> clazz, String fieldName) {
        return encryptFieldCache.computeIfAbsent(new FieldKey(clazz, fieldName), fieldKey -> {
            var info = new EncryptFieldInfo(fieldKey.clazz, fieldKey.fieldName);
            if (info.annotation == null) return null;
            return info;
        }) != null;
    }

    @Override
    public byte[] encrypt(MetaClass metaClass, Class<?> clazz, String fieldName, Object value) {
        var type = metaClass.getGetterType(fieldName);
        byte[] rawData;
        if (type != byte[].class){
            //noinspection unchecked
            var converter = (EncryptConverter<Object>) encryptConvertRegister.getConverter(type)
                    .orElseThrow(()->new MybatisEncryptException("not found converter: type = " + type));
            rawData = converter.convert(value);
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
    public void decrypt(MetaClass metaClass, Object target, String fieldName, byte[] data) {
        var decryptedData = encryptor.decrypt(data, keyProvider.getKey(target.getClass(), fieldName));
        Class<?> type;
        try {
            type = target.getClass().getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            throw new MybatisEncryptException(e);
        }
        Object result;
        if (type == byte[].class){
            result = decryptedData;
        }else{
            result = encryptConvertRegister.getConverter(type)
                    .orElseThrow(()->new MybatisEncryptException("not found converter: type = " + type))
                    .convert(decryptedData);
        }
        var invoker = metaClass.getSetInvoker(fieldName);
        try {
            invoker.invoke(target, new Object[]{result});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new MybatisEncryptException(e);
        }
    }

    @Override
    public Future<?> putDecryptTask(MetaClass metaClass, Object target, String fieldName, byte[] data) {
        return executor.submit(()->this.decrypt(metaClass, target, fieldName, data));
    }

    private record FieldKey(Class<?> clazz, String fieldName) {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || obj.getClass() != getClass()) return false;
            return Objects.equals(clazz, ((FieldKey) obj).clazz) && Objects.equals(fieldName, ((FieldKey) obj).fieldName);
        }
    }

    private static class EncryptFieldInfo{
        private final Class<?> clazz;
        private final String fieldName;
        private final Field field;
        private final Encrypt annotation;
        private final Field integrityCheckField;
        private final IntegrityCheckFor integrityCheckAnnotation;

        public EncryptFieldInfo(Class<?> clazz, String fieldName) {
            this.clazz = clazz;
            this.fieldName = fieldName;
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                throw new MybatisEncryptException("not found field: " + fieldName, e);
            }
            annotation = field.getAnnotation(Encrypt.class);
            if (annotation == null){
                integrityCheckField = null;
                integrityCheckAnnotation = null;
                return;
            }
            var fields = clazz.getDeclaredFields();
            Field foundField = null;
            IntegrityCheckFor foundAnnotation = null;
            for (var f : fields) {
                var a = f.getAnnotation(IntegrityCheckFor.class);
                if (a != null && a.name().equals(fieldName)){
                    foundField = f;
                    foundAnnotation = a;
                    break;
                }
            }
            integrityCheckField = foundField;
            integrityCheckAnnotation = foundAnnotation;
        }
    }
}
