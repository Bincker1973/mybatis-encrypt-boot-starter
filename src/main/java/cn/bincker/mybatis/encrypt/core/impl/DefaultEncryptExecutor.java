package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import cn.bincker.mybatis.encrypt.annotation.IntegrityCheckFor;
import cn.bincker.mybatis.encrypt.core.EncryptConvertRegister;
import cn.bincker.mybatis.encrypt.core.EncryptExecutor;
import cn.bincker.mybatis.encrypt.core.Encryptor;
import cn.bincker.mybatis.encrypt.exception.MybatisEncryptException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class DefaultEncryptExecutor implements EncryptExecutor {
    private final Encryptor encryptor;
    private final EncryptConvertRegister encryptConvertRegister;
    private final Map<FieldKey, EncryptFieldInfo> encryptFieldCache;

    public DefaultEncryptExecutor(Encryptor encryptor, EncryptConvertRegister encryptConvertRegister) {
        this.encryptor = encryptor;
        this.encryptConvertRegister = encryptConvertRegister;
        encryptFieldCache = new ConcurrentHashMap<>();
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
    public byte[] encrypt(Class<?> clazz, String fieldName, Object value) {
        return new byte[0];
    }

    @Override
    public Future<byte[]> putEncryptTask(Class<?> clazz, String fieldName, Object value) {
        return null;
    }

    @Override
    public void decrypt(Object target, String fieldName, byte[] data) {

    }

    @Override
    public Future<Void> putDecryptTask(Object target, String fieldName, byte[] data) {
        return null;
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
