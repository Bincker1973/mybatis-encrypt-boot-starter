import cn.bincker.mybatis.encrypt.type.EncryptParam;
import cn.bincker.mybatis.plus.encrypt.entity.Demo;
import cn.bincker.mybatis.plus.encrypt.mapper.DemoMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import static cn.bincker.mybatis.plus.encrypt.TestUtils.getProperties;
import static cn.bincker.mybatis.plus.encrypt.TestUtils.insertSingle;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SimpleTest {
    private static final List<PropertyDescriptor> properties = getProperties();
    @Autowired
    private DemoMapper mapper;

    @Test
    @Transactional
    void accuracy() throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < 20; i++) {
            var demo = insertSingle(properties, mapper);
            var selectDemo = mapper.selectById(demo.getId());
            for (PropertyDescriptor property : properties) {
                var reader = property.getReadMethod();
                if (property.getPropertyType().equals(byte[].class)){
                    assertArrayEquals((byte[])reader.invoke(demo), (byte[])reader.invoke(selectDemo), "property: " + property.getName());
                }else{
                    assertEquals(reader.invoke(demo), reader.invoke(selectDemo), "property: " + property.getName());
                }
            }
        }
    }

    @Test
    @Transactional
    void params() throws InvocationTargetException, IllegalAccessException {
        var map = new HashMap<Long,Demo>();
        for (int i = 0; i < 20; i++) {
            var demo = insertSingle(properties, mapper);
            map.put(demo.getId(), demo);
        }
        //noinspection OptionalGetWithoutIsPresent
        var foo = map.values().stream().findFirst().get();
        for (PropertyDescriptor property : properties) {
            var reader = property.getReadMethod();
            var value = reader.invoke(foo);
            var result = mapper.selectList(Wrappers.<Demo>query().eq(
                    TestUtils.propertyNameToFieldName(property.getName()),
                    new EncryptParam<>(Demo.class, property.getName(), value)
            ));
            assertFalse(result.isEmpty(), "not found result by property: " + property.getName());
            assertTrue(result.stream().anyMatch(demo -> demo.getId().equals(foo.getId())), "not found this result by property: " + property.getName());

            for (Demo demo : result) {
                if (property.getPropertyType().equals(byte[].class)){
                    assertArrayEquals((byte[]) reader.invoke(demo), (byte[]) reader.invoke(foo), "property " + property.getName() + " not equals");
                }else{
                    assertEquals(reader.invoke(demo), reader.invoke(foo), "property " + property.getName() + " not equals");
                }
            }
        }
    }

    @Test
    @Transactional
    void objectParams() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        var map = new HashMap<Long,Demo>();
        for (int i = 0; i < 20; i++) {
            var demo = insertSingle(properties, mapper);
            map.put(demo.getId(), demo);
        }
        //noinspection OptionalGetWithoutIsPresent
        var foo = map.values().stream().findFirst().get();
        for (PropertyDescriptor property : properties) {
            var reader = property.getReadMethod();
            var value = reader.invoke(foo);
            var query = new Demo();
            property.getWriteMethod().invoke(query, value);
            var queryMethod = mapper.getClass().getMethod("queryBy" + Character.toUpperCase(property.getName().charAt(0)) + property.getName().substring(1), Demo.class);
            //noinspection unchecked
            var result = (List<Demo>) queryMethod.invoke(mapper, query);
            assertFalse(result.isEmpty(), "not found result by property: " + property.getName());
            assertTrue(result.stream().anyMatch(demo -> demo.getId().equals(foo.getId())), "not found this result by property: " + property.getName());
            for (Demo demo : result) {
                if (property.getPropertyType().equals(byte[].class)){
                    assertArrayEquals((byte[])reader.invoke(demo), (byte[])reader.invoke(foo), "property: " + property.getName());
                }else{
                    assertEquals(reader.invoke(demo), reader.invoke(foo), "property: " + property.getName());
                }
            }
        }
    }
}
