package cn.bincker.mybatis.encrypt.type;

import lombok.Data;

@Data
public class EncryptParam<T> {
    private final Class<?> cls;
    private final String property;
    private final Object value;

    public EncryptParam(Class<T> cls, String property, Object value) {
        this.cls = cls;
        this.property = property;
        this.value = value;
    }
}
