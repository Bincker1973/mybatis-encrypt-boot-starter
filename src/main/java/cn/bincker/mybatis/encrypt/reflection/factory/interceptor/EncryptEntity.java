package cn.bincker.mybatis.encrypt.reflection.factory.interceptor;

import java.lang.reflect.Method;
import java.util.concurrent.Future;

public interface EncryptEntity {
    void $setDecryptFuture(Method getter, Future<?> future);
}
