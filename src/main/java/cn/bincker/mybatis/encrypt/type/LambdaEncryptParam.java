package cn.bincker.mybatis.encrypt.type;

import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.ibatis.reflection.property.PropertyNamer;

public class LambdaEncryptParam<T> extends EncryptParam<T>{
    public LambdaEncryptParam(Class<T> cls, SFunction<T, Object> lambda, Object value) {
        super(cls, PropertyNamer.methodToProperty(LambdaUtils.extract(lambda).getImplMethodName()), value);
    }
}
