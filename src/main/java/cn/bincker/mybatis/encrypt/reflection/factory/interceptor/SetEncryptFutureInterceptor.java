package cn.bincker.mybatis.encrypt.reflection.factory.interceptor;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class SetEncryptFutureInterceptor {
    public static final String encryptFutureFieldName = "$encryptFutureMap";
    @SuppressWarnings("unused")
    @RuntimeType
    public static void intercept(
            @This Object that,
            @FieldValue(encryptFutureFieldName)Map<Method, Future<?>> futureMap,
            @Argument(0) Method getter,
            @Argument(1) Future<?> future
    ){
        if (futureMap == null){
            futureMap = new HashMap<>();
            try {
                var field = that.getClass().getDeclaredField(encryptFutureFieldName);
                field.setAccessible(true);
                field.set(that, futureMap);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        futureMap.put(getter, future);
    }
}
