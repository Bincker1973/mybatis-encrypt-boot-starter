package cn.bincker.mybatis.encrypt.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface IntegrityCheckFor {
    String name() default  "";
}
