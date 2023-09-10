package cn.bincker.mybatis.plus.encrypt;

import cn.bincker.mybatis.encrypt.reflection.ReflectionUtils;
import cn.bincker.mybatis.encrypt.reflection.factory.interceptor.EncryptEntity;
import cn.bincker.mybatis.encrypt.reflection.factory.interceptor.SetEncryptFutureInterceptor;
import cn.bincker.mybatis.encrypt.reflection.factory.interceptor.WaitDecryptGetterInterceptor;
import cn.bincker.mybatis.plus.encrypt.entity.User;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.Future;

public class ByteBuddyTest {

    @Test
    void test() throws InstantiationException, IllegalAccessException, NoSuchMethodException {
        var builder = new ByteBuddy().subclass(User.class);
        try {
            builder = builder.implement(EncryptEntity.class)
                    .defineField(SetEncryptFutureInterceptor.encryptFutureFieldName, Map.class, Modifier.PRIVATE)
                    .method(ElementMatchers.is(EncryptEntity.class.getMethod("$setDecryptFuture", Method.class, Future.class)))
                    .intercept(MethodDelegation.to(SetEncryptFutureInterceptor.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        var properties = ReflectionUtils.getEncryptProperties(User.class).values();
        for (var property : properties) {
            builder = builder.method(ElementMatchers.is(property.getter()))
                    .intercept(MethodDelegation.to(WaitDecryptGetterInterceptor.class));
        }
        //noinspection deprecation
        var foo = builder.make().load(User.class.getClassLoader()).getLoaded().newInstance();
        ((EncryptEntity) foo).$setDecryptFuture(User.class.getMethod("getPassword"), null);
        System.out.println(foo.getPassword());
    }
}
