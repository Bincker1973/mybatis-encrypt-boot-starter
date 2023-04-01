package cn.bincker.mybatis.plus.encrypt;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ByteBuddyTest {

    public void say(){
        System.out.println("hello world");
    }

    @Test
    void test() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var foo = new ByteBuddy()
                .subclass(SimpleService.class)
                .method(ElementMatchers.named("getValue"))
                .intercept(MethodDelegation.to(new TestInterceptor()))
                .make()
                .load(SimpleService.class.getClassLoader())
                .getLoaded()
                .newInstance();
        System.out.println(foo.getValue());
    }

    public static class TestInterceptor {
        @RuntimeType
        public Object intercept(@AllArguments Object[] args, @SuperCall Callable<Object> callable) throws Exception {
            System.out.println("called");
            return callable.call() + "--hel";
        }
    }

    public static class SimpleService {
        public String getValue() {
            return "hello world";
        }
    }

}
