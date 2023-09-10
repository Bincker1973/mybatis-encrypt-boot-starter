package cn.bincker.mybatis.encrypt.reflection;

import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.util.MapUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EncryptReflectorFactory implements ReflectorFactory {

    private boolean classCacheEnabled = true;
    private final ConcurrentMap<Class<?>, EncryptReflector> reflectorMap = new ConcurrentHashMap<>();

    public EncryptReflectorFactory() {
    }

    @Override
    public boolean isClassCacheEnabled() {
        return classCacheEnabled;
    }

    @Override
    public void setClassCacheEnabled(boolean classCacheEnabled) {
        this.classCacheEnabled = classCacheEnabled;
    }

    @Override
    public Reflector findForClass(Class<?> type) {
        if (classCacheEnabled) {
            // synchronized (type) removed see issue #461
            return MapUtil.computeIfAbsent(reflectorMap, type, EncryptReflector::new);
        } else {
            return new EncryptReflector(type);
        }
    }
}
