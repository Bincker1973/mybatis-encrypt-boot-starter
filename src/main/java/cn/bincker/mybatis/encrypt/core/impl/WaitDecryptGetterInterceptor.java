package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.EncryptExecutor;
import cn.bincker.mybatis.encrypt.core.EncryptFieldGetterInterceptor;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.util.concurrent.Callable;

public class WaitDecryptGetterInterceptor implements EncryptFieldGetterInterceptor {
    private final EncryptExecutor encryptExecutor;

    public WaitDecryptGetterInterceptor(EncryptExecutor encryptExecutor) {
        this.encryptExecutor = encryptExecutor;
    }

    @Override
    @RuntimeType
    public Object intercept(@This Object target, @SuperCall Callable<?> invoker) throws Exception{
        return invoker.call();
    }
}
