package cn.bincker.mybatis.encrypt.wrapper;

import cn.bincker.mybatis.encrypt.core.EncryptExecutor;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

public class EncryptObjectWrapperFactory implements ObjectWrapperFactory {
    private final EncryptExecutor encryptExecutor;

    public EncryptObjectWrapperFactory(EncryptExecutor encryptor) {
        this.encryptExecutor = encryptor;
    }

    @Override
    public boolean hasWrapperFor(Object object) {
        if (object == null) return false;
        return encryptExecutor.hasEncryptField(object.getClass());
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new EncryptObjectWrapper(metaObject, object, encryptExecutor);
    }
}
