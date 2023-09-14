import cn.bincker.mybatis.plus.encrypt.entity.Demo;
import cn.bincker.mybatis.plus.encrypt.mapper.DemoMapper;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestUtils {
    public static String propertyNameToFieldName(String propertyName){
        var result = new StringBuilder();
        for (char c : propertyName.toCharArray()) {
            if (Character.isUpperCase(c)){
                result.append('_');
            }
            result.append(c);
        }
        return result.toString();
    }

    public static List<PropertyDescriptor> getProperties() {
        return Arrays.stream(BeanUtils.getPropertyDescriptors(Demo.class)).filter(property -> {
            var name = property.getName();
            return !name.equals("id") && !name.equals("class");
        }).toList();
    }

    public static Demo insertSingle(Collection<PropertyDescriptor> properties, DemoMapper mapper) throws IllegalAccessException, InvocationTargetException {
        var demo = new Demo();
        for (PropertyDescriptor property : properties) {
            property.getWriteMethod().invoke(demo, RandomValueUtils.randomValue(property.getPropertyType()));
        }
        mapper.insert(demo);
        return demo;
    }
}
