package cn.bincker.mybatis.encrypt;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import cn.bincker.mybatis.encrypt.annotation.IntegrityCheckFor;
import cn.bincker.mybatis.encrypt.exception.ProxyException;
import cn.bincker.mybatis.encrypt.proxy.EncryptInterceptor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EncryptObjectFactory implements ObjectFactory {
    private final Map<Class<?>, Class<?>> cachedProxyClassMap = new ConcurrentHashMap<>();
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
            var integrityCheckField = Arrays.stream(fields).filter(f->{
                var integrityCheckAnnotation = f.getAnnotation(IntegrityCheckFor.class);
                if (integrityCheckAnnotation == null) return false;
                return integrityCheckAnnotation.name().equals(field.getName());
            }).findAny();
            defineDecryptSetterMethod(
                    builder,
                    field,
                    annotation,
                    integrityCheckField.orElse(null),
                    integrityCheckField.map(f->f.getAnnotation(IntegrityCheckFor.class)).orElse(null)
            );
            if (!proxy) proxy = true;
        }
        if (!proxy) return type;
        try(var unloaded = builder.make()){
            return unloaded.load(type.getClassLoader()).getLoaded();
        } catch (IOException e) {
            throw new ProxyException(e);
        }
    }

    private <T> void defineDecryptSetterMethod(
            DynamicType.Builder<T> builder,
            Field field,
            Encrypt encrypt,
            Field integrityCheckField,
            IntegrityCheckFor integrityCheckFor
    ) {
        var setterName = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
        builder.defineMethod(setterName, void.class, Visibility.PUBLIC)
                .withParameters(byte[].class)
                .intercept(MethodDelegation.to(new EncryptInterceptor()));
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return false;
    }
}
