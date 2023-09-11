package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.converter.EncryptConvertRegister;
import cn.bincker.mybatis.encrypt.converter.EncryptConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultEncryptConvertRegister implements EncryptConvertRegister {
    private final Map<Class<?>, EncryptConverter<?>> converterMap = new HashMap<>();

    public DefaultEncryptConvertRegister() {
        register(Boolean.class, new BooleanEncryptConverter());
        register(boolean.class, new BooleanEncryptConverter());

        register(Byte.class, new ByteEncryptConverter());
        register(byte.class, new ByteEncryptConverter());

        register(Short.class, new ShortEncryptConverter());
        register(short.class, new ShortEncryptConverter());

        register(Integer.class, new IntegerEncryptConverter());
        register(int.class, new IntegerEncryptConverter());

        register(Long.class, new LongEncryptConverter());
        register(long.class, new LongEncryptConverter());

        register(Float.class, new FloatEncryptConverter());
        register(float.class, new FloatEncryptConverter());

        register(Double.class, new DoubleEncryptConverter());
        register(double.class, new DoubleEncryptConverter());

        register(String.class, new StringEncryptConverter());

        register(BigInteger.class, new BigIntegerEncryptConverter());

        register(BigDecimal.class, new BigDecimalEncryptConverter());


        register(Date.class, new DateEncryptConverter());

        register(java.sql.Date.class, new SqlDateEncryptConverter());
        register(java.sql.Time.class, new SqlTimeEncryptConverter());
        register(java.sql.Timestamp.class, new SqlTimestampEncryptConverter());


        register(Instant.class, new InstantEncryptConverter());
        register(LocalDateTime.class, new LocalDateTimeEncryptConverter());
        register(LocalDate.class, new LocalDateEncryptConverter());
        register(LocalTime.class, new LocalTimeEncryptConverter());
        register(OffsetDateTime.class, new OffsetDateTimeEncryptConverter());
        register(OffsetTime.class, new OffsetTimeEncryptConverter());
        register(ZonedDateTime.class, new ZonedDateTimeEncryptConverter());
        register(Month.class, new MonthEncryptConverter());
        register(Year.class, new YearEncryptConverter());
        register(YearMonth.class, new YearMonthEncryptConverter());
    }

    @Override
    public synchronized  <T> void register(Class<T> type, EncryptConverter<T> converter) {
        converterMap.put(type, converter);
    }

    @Override
    public <T> Optional<EncryptConverter<T>> getConverter(Class<T> clazz) {
        //noinspection unchecked
        return Optional.ofNullable((EncryptConverter<T>) converterMap.get(clazz));
    }
}
