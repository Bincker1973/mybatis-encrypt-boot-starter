package cn.bincker.mybatis.plus.encrypt;

import cn.bincker.mybatis.plus.encrypt.entity.Demo;
import cn.bincker.mybatis.plus.encrypt.mapper.DemoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InsertTest {
    @Autowired
    private DemoMapper mapper;

    @Test
    @Transactional
    void accuracy() throws InvocationTargetException, IllegalAccessException {
        var properties = Arrays.stream(BeanUtils.getPropertyDescriptors(Demo.class)).filter(property->{
            var name = property.getName();
            return !name.equals("id") && !name.equals("class");
        }).toList();
        for (int i = 0; i < 20; i++) {
            var demo = new Demo();
            for (PropertyDescriptor property : properties) {
                property.getWriteMethod().invoke(demo, RandomValueUtils.randomValue(property.getPropertyType()));
            }
            mapper.insert(demo);
            var selectDemo = mapper.selectById(demo.getId());
            for (PropertyDescriptor property : properties) {
                var reader = property.getReadMethod();
                assertEquals(reader.invoke(demo), reader.invoke(selectDemo), "property: " + property.getName());
            }
        }
    }
}
