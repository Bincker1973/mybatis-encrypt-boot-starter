package cn.bincker.mybatis.encrypt.reflection.factory.interceptor;

import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Future;

public class WaitDecryptGetterInterceptor {
    @SuppressWarnings("unused")
    @RuntimeType
    public static Object intercept(
            @Origin Method method,
            @FieldValue(SetEncryptFutureInterceptor.encryptFutureFieldName)Map<Method, Future<?>> futureMap
    ) throws Exception{
        var future = futureMap.get(method);
        if (future == null) return null;
        return future.get();
    }
}
