package cn.bincker.mybatis.encrypt.type;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.Date;

public class EncryptTypeHandlerConfigurator {
    public static void configure(Configuration configuration){
        var registry = configuration.getTypeHandlerRegistry();
        registry.register(Boolean.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(boolean.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

        registry.register(Byte.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(byte.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

        registry.register(Short.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(short.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

        registry.register(Integer.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(int.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

        registry.register(Long.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(long.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

        registry.register(Float.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(float.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

        registry.register(Double.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(double.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

//        registry.register(Reader.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(String.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());


        registry.register(BigInteger.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

        registry.register(BigDecimal.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

//        registry.register(InputStream.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
//        registry.register(Byte[].class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
//        registry.register(byte[].class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());


        registry.register(Date.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());

        registry.register(java.sql.Date.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(java.sql.Time.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(java.sql.Timestamp.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());


        registry.register(Instant.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(LocalDateTime.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(LocalDate.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(LocalTime.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(OffsetDateTime.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(OffsetTime.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(ZonedDateTime.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(Month.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(Year.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
        registry.register(YearMonth.class, JdbcType.BINARY, new EncryptByteArrayTypeHandler<>());
    }
}
