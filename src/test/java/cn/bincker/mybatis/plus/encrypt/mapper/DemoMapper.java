package cn.bincker.mybatis.plus.encrypt.mapper;

import cn.bincker.mybatis.plus.encrypt.entity.Demo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@SuppressWarnings("unused")
@Mapper
public interface DemoMapper extends BaseMapper<Demo> {
    List<Demo> queryByBooleanTypeField(Demo demo);
    List<Demo> queryByBooleanField(Demo demo);
    List<Demo> queryByByteTypeField(Demo demo);
    List<Demo> queryByByteField(Demo demo);
    List<Demo> queryByShortTypeField(Demo demo);
    List<Demo> queryByShortField(Demo demo);
    List<Demo> queryByIntegerField(Demo demo);
    List<Demo> queryByIntField(Demo demo);
    List<Demo> queryByLongTypeField(Demo demo);
    List<Demo> queryByLongField(Demo demo);
    List<Demo> queryByFloatTypeField(Demo demo);
    List<Demo> queryByFloatField(Demo demo);
    List<Demo> queryByDoubleTypeField(Demo demo);
    List<Demo> queryByDoubleField(Demo demo);
    List<Demo> queryByStringField(Demo demo);
    List<Demo> queryByBigIntegerField(Demo demo);
    List<Demo> queryByBigDecimalField(Demo demo);
    List<Demo> queryByDateTypeField(Demo demo);
    List<Demo> queryByDateField(Demo demo);
    List<Demo> queryByTimeField(Demo demo);
    List<Demo> queryByTimestampField(Demo demo);
    List<Demo> queryByInstantField(Demo demo);
    List<Demo> queryByLocalDateTimeField(Demo demo);
    List<Demo> queryByLocalDateField(Demo demo);
    List<Demo> queryByLocalTimeField(Demo demo);
    List<Demo> queryByOffsetDateTimeField(Demo demo);
    List<Demo> queryByOffsetTimeField(Demo demo);
    List<Demo> queryByZonedDateTimeField(Demo demo);
    List<Demo> queryByMonthField(Demo demo);
    List<Demo> queryByYearField(Demo demo);
    List<Demo> queryByYearMonthField(Demo demo);
    List<Demo> queryByByteArrayField(Demo demo);
}
