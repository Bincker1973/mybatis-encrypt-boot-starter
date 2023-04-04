package cn.bincker.mybatis.encrypt.core;

import java.util.concurrent.Callable;

public interface EncryptFieldGetterInterceptor {
    Object intercept(Object target, Callable<?> invoker) throws Exception;
}
