package cn.bincker.mybatis.encrypt.proxy;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class EncryptInterceptor {
    @RuntimeType
    public static void intercept(@AllArguments Object[] args) {
    }
}
