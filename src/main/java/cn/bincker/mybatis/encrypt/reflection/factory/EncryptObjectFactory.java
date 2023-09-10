package cn.bincker.mybatis.encrypt.reflection.factory;

import cn.bincker.mybatis.encrypt.exception.ProxyException;
import cn.bincker.mybatis.encrypt.reflection.ReflectionUtils;
import cn.bincker.mybatis.encrypt.reflection.factory.interceptor.EncryptEntity;
import cn.bincker.mybatis.encrypt.reflection.factory.interceptor.SetEncryptFutureInterceptor;
import cn.bincker.mybatis.encrypt.reflection.factory.interceptor.WaitDecryptGetterInterceptor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.factory.ObjectFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class EncryptObjectFactory implements ObjectFactory {
    private final Map<Class<?>, Class<?>> cachedProxyClassMap = new ConcurrentHashMap<>();

    @Override
    public <T> T create(Class<T> type) {
        return create(resolveInterface(type), Collections.emptyList(), Collections.emptyList());
    }

    private <T> Class<T> resolveInterface(Class<T> type) {
        Class<?> classToCreate;
        if (type == List.class || type == Collection.class || type == Iterable.class) {
            classToCreate = ArrayList.class;
        } else if (type == Map.class) {
            classToCreate = HashMap.class;
        } else if (type == SortedSet.class) { // issue #510 Collections Support
            classToCreate = TreeSet.class;
        } else if (type == Set.class) {
            classToCreate = HashSet.class;
        } else {
            classToCreate = type;
        }
        //noinspection unchecked
        return (Class<T>) classToCreate;
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        //noinspection unchecked
        var clazz = (Class<T>) cachedProxyClassMap.computeIfAbsent(type, this::createProxy);
        try {
            var constructor = clazz.getDeclaredConstructor(constructorArgTypes.toArray(Class[]::new));
            try {
                return constructor.newInstance(constructorArgs.toArray(new Object[0]));
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
        if (type.isArray() || Collection.class.isAssignableFrom(type)) return type;

        var builder = new ByteBuddy().subclass(type);
//        定义设置解密结果代理方法
        try {
            builder = builder.implement(EncryptEntity.class)
                    .defineField(SetEncryptFutureInterceptor.encryptFutureFieldName, Map.class, Modifier.PRIVATE)
                    .method(ElementMatchers.is(EncryptEntity.class.getMethod("$setDecryptFuture", Method.class, Future.class)))
                    .intercept(MethodDelegation.to(SetEncryptFutureInterceptor.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
//        定义getter代理方法
        var properties = ReflectionUtils.getEncryptProperties(type).values();
        if (properties.isEmpty()) return type;
        for (var property : properties) {
            builder = builder.method(ElementMatchers.is(property.getter()))
                    .intercept(MethodDelegation.to(WaitDecryptGetterInterceptor.class));
        }
        try(var unloaded = builder.make()){
            return unloaded.load(type.getClassLoader()).getLoaded();
        } catch (IOException e) {
            throw new ProxyException(e);
        }
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }
}
