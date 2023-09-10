package cn.bincker.mybatis.encrypt.entity;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import cn.bincker.mybatis.encrypt.annotation.IntegrityCheckFor;

import java.lang.reflect.Method;

public record EncryptProperty(
        String name,
        Method getter,
        Encrypt annotation,
        IntegrityCheckFor integrityCheckAnnotation
){}
