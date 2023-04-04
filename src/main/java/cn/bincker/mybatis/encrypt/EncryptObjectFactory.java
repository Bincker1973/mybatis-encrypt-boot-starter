package cn.bincker.mybatis.encrypt;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import cn.bincker.mybatis.encrypt.core.EncryptFieldGetterInterceptor;
import cn.bincker.mybatis.encrypt.exception.ProxyException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.factory.ObjectFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EncryptObjectFactory implements ObjectFactory {
    private final Map<Class<?>, Class<?>> cachedProxyClassMap = new ConcurrentHashMap<>();
    private final EncryptFieldGetterInterceptor encryptFieldGetterInterceptor;

    public EncryptObjectFactory(EncryptFieldGetterInterceptor encryptFieldGetterInterceptor) {
        this.encryptFieldGetterInterceptor = encryptFieldGetterInterceptor;
    }

    @Override
    public <T> T create(Class<T> type) {
        return create(type, Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        //noinspection unchecked
        var clazz = (Class<T>) cachedProxyClassMap.computeIfAbsent(type, this::createProxy);
        try {
            var constructor = clazz.getDeclaredConstructor(constructorArgTypes.toArray(Class[]::new));
            try {
                return constructor.newInstance(constructorArgs);
            } catch (IllegalAccessException e) {
                constructor.setAccessible(true);
                return constructor.newInstance(constructorArgs);
            }
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new ReflectionException(
                    "Error instantiating " + type +
                            " with invalid types (" + constructorArgTypes + ") or values (" + constructorArgs + "). ",
                    e
            );
        }
    }

    private <T> Class<? extends T> createProxy(Class<T> type){
        var builder = new ByteBuddy().subclass(type);
        var fields = type.getDeclaredFields();
        var proxy = false;
        for (Field field : fields) {
            var annotation = field.getAnnotation(Encrypt.class);
            if (annotation == null) continue;
            var getterMethodName = "get";
            getterMethodName += field.getName().substring(0, 1).toUpperCase();
            getterMethodName += field.getName().substring(1);
            builder.method(
                    ElementMatchers.named(getterMethodName).and(
                            ElementMatchers.takesNoArguments()
                    )
            ).intercept(MethodDelegation.to(encryptFieldGetterInterceptor));
            if (!proxy) proxy = true;
        }
        if (!proxy) return type;
        try(var unloaded = builder.make()){
            return unloaded.load(type.getClassLoader()).getLoaded();
        } catch (IOException e) {
            throw new ProxyException(e);
        }
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return false;
    }
}
