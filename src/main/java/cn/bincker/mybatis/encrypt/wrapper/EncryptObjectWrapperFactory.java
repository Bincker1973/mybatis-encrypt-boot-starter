package cn.bincker.mybatis.encrypt.wrapper;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import cn.bincker.mybatis.encrypt.core.EncryptConvertRegister;
import cn.bincker.mybatis.encrypt.core.EncryptExecutor;
import cn.bincker.mybatis.encrypt.core.Encryptor;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EncryptObjectWrapperFactory implements ObjectWrapperFactory {
    private final Map<Class<?>, Boolean> encryptClassCacheMap = new ConcurrentHashMap<>();
    private final EncryptExecutor encryptExecutor;

    public EncryptObjectWrapperFactory(EncryptExecutor encryptor) {
        this.encryptExecutor = encryptor;
    }

    @Override
    public boolean hasWrapperFor(Object object) {
        if (object == null) return false;
        return hasEncryptField(object.getClass());
    }

    private boolean hasEncryptField(Class<?> aClass) {
        return encryptClassCacheMap.computeIfAbsent(aClass, clazz->
            Arrays.stream(clazz.getDeclaredFields()).anyMatch(f->f.getAnnotation(Encrypt.class) != null)
        );
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new EncryptObjectWrapper(metaObject, object, encryptExecutor);
    }
}
