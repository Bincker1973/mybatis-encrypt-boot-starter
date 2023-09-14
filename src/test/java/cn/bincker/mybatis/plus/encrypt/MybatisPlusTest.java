package cn.bincker.mybatis.plus.encrypt;

import cn.bincker.mybatis.encrypt.type.LambdaEncryptParam;
import cn.bincker.mybatis.plus.encrypt.entity.Demo;
import cn.bincker.mybatis.plus.encrypt.mapper.DemoMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import static cn.bincker.mybatis.plus.encrypt.TestUtils.getProperties;
import static cn.bincker.mybatis.plus.encrypt.TestUtils.insertSingle;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MybatisPlusTest {
    private final List<PropertyDescriptor> properties = getProperties();
    @Autowired
    private DemoMapper mapper;
    @Test
    @Transactional
    void lambdaQuery() throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < 10; i++) {
            insertSingle(properties, mapper);
        }
        var demo = insertSingle(properties, mapper);
        validLambdaQuery(demo, Demo::getBooleanTypeField, "booleanTypeField");
        validLambdaQuery(demo, Demo::isBooleanField, "booleanField");
        validLambdaQuery(demo, Demo::getByteTypeField, "byteTypeField");
        validLambdaQuery(demo, Demo::getByteField, "byteField");
        validLambdaQuery(demo, Demo::getShortTypeField, "shortTypeField");
        validLambdaQuery(demo, Demo::getShortField, "shortField");
        validLambdaQuery(demo, Demo::getIntegerField, "integerField");
        validLambdaQuery(demo, Demo::getIntField, "intField");
        validLambdaQuery(demo, Demo::getLongTypeField, "longTypeField");
        validLambdaQuery(demo, Demo::getLongField, "longField");
        validLambdaQuery(demo, Demo::getFloatTypeField, "floatTypeField");
        validLambdaQuery(demo, Demo::getFloatField, "floatField");
        validLambdaQuery(demo, Demo::getDoubleTypeField, "doubleTypeField");
        validLambdaQuery(demo, Demo::getDoubleField, "doubleField");
        validLambdaQuery(demo, Demo::getStringField, "stringField");
        validLambdaQuery(demo, Demo::getBigIntegerField, "bigIntegerField");
        validLambdaQuery(demo, Demo::getBigDecimalField, "bigDecimalField");
        validLambdaQuery(demo, Demo::getDateTypeField, "dateTypeField");
        validLambdaQuery(demo, Demo::getDateField, "dateField");
        validLambdaQuery(demo, Demo::getTimeField, "timeField");
        validLambdaQuery(demo, Demo::getTimestampField, "timestampField");
        validLambdaQuery(demo, Demo::getInstantField, "instantField");
        validLambdaQuery(demo, Demo::getLocalDateTimeField, "localDateTimeField");
        validLambdaQuery(demo, Demo::getLocalDateField, "localDateField");
        validLambdaQuery(demo, Demo::getLocalTimeField, "localTimeField");
        validLambdaQuery(demo, Demo::getOffsetDateTimeField, "offsetDateTimeField");
        validLambdaQuery(demo, Demo::getOffsetTimeField, "offsetTimeField");
        validLambdaQuery(demo, Demo::getZonedDateTimeField, "zonedDateTimeField");
        validLambdaQuery(demo, Demo::getMonthField, "monthField");
        validLambdaQuery(demo, Demo::getYearField, "yearField");
        validLambdaQuery(demo, Demo::getYearMonthField, "yearMonthField");
    }

    private void validLambdaQuery(Demo demo, SFunction<Demo, ?> fun, String property){
        var result = mapper.selectList(
                Wrappers.lambdaQuery(Demo.class)
                        .eq(
                                fun,
                                new LambdaEncryptParam<>(Demo.class, fun, fun.apply(demo))
                        )
        );
        assertFalse(result.isEmpty(), "query by lambda param fail: property=" + property);
        assertTrue(result.stream().anyMatch(d-> Objects.equals(d.getId(), demo.getId())), "original object can not be found by property=" + property);
    }
}
