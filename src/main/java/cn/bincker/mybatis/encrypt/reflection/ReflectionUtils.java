package cn.bincker.mybatis.encrypt.reflection;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import cn.bincker.mybatis.encrypt.annotation.IntegrityCheckFor;
import cn.bincker.mybatis.encrypt.entity.EncryptProperty;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {
    public static Map<String, EncryptProperty> getEncryptProperties(Class<?> clazz){
        var result = new HashMap<String, EncryptProperty>();
        var cls = clazz;
        while (cls != Object.class){
            var methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                if (!Modifier.isPublic(method.getModifiers()) || method.isBridge() || !PropertyNamer.isGetter(method.getName())) continue;
                var propertyName = PropertyNamer.methodToProperty(method.getName());
                if (result.containsKey(propertyName)) continue;
                if (propertyName.startsWith("$") || "serialVersionUID".equals(propertyName) || "class".equals(propertyName)) continue;
                var annotation = getAnnotation(cls, propertyName, method, Encrypt.class);
                if (annotation == null) continue;
                var integrityCheckAnnotation = getAnnotation(cls, propertyName, method, IntegrityCheckFor.class);
                var encryptProperty = new EncryptProperty(propertyName, method, annotation, integrityCheckAnnotation);
                result.put(propertyName, encryptProperty);
            }
            cls = cls.getSuperclass();
        }
        return result;
    }

    private static <T extends Annotation> T getAnnotation(Class<?> clazz, String propertyName, Method getter, Class<T> annotation){
        var result = getter.getAnnotation(annotation);
        if (result != null) return result;
        try {
            var field = clazz.getDeclaredField(propertyName);
            return field.getAnnotation(annotation);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
