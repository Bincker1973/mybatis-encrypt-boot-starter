package cn.bincker.mybatis.plus.encrypt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.Random;

public class RandomValueUtils {
    private static final Random random = new Random();
    public static Object randomValue(Class<?> cls){
        if (cls.equals(Boolean.class) || cls.equals(boolean.class)) {
            return random.nextBoolean();
        } else if (cls.equals(Byte.class) || cls.equals(byte.class)) {
            var bytes = new byte[1];
            random.nextBytes(bytes);
            return bytes[0];
        } else if (cls.equals(Short.class) || cls.equals(short.class)) {
            return (short) random.nextInt();
        } else if (cls.equals(Integer.class) || cls.equals(int.class)) {
            return random.nextInt();
        } else if (cls.equals(Long.class) || cls.equals(long.class)) {
            return random.nextLong();
        } else if (cls.equals(Float.class) || cls.equals(float.class)) {
            return random.nextFloat();
        } else if (cls.equals(Double.class) || cls.equals(double.class)) {
            return random.nextDouble();
        } else if (cls.equals(String.class)) {
            return Long.toString(random.nextLong(), 36);
        } else if (cls.equals(BigInteger.class)) {
            return BigInteger.valueOf(random.nextLong());
        } else if (cls.equals(BigDecimal.class)) {
            return BigDecimal.valueOf(random.nextLong(), random.nextInt());
        } else if (cls.equals(Date.class)) {
            return new Date();
        } else if (cls.equals(java.sql.Date.class)) {
            return new java.sql.Date(System.currentTimeMillis());
        } else if (cls.equals(Time.class)) {
            return new Time(System.currentTimeMillis());
        } else if (cls.equals(Timestamp.class)) {
            return Timestamp.from(Instant.now());
        } else if (cls.equals(Instant.class)) {
            return Instant.now();
        } else if (cls.equals(LocalDateTime.class)) {
            return LocalDateTime.now();
        } else if (cls.equals(LocalDate.class)) {
            return LocalDate.now();
        } else if (cls.equals(LocalTime.class)) {
            return LocalTime.now();
        } else if (cls.equals(OffsetDateTime.class)) {
            return OffsetDateTime.now();
        } else if (cls.equals(OffsetTime.class)) {
            return OffsetTime.now();
        } else if (cls.equals(ZonedDateTime.class)) {
            return ZonedDateTime.now();
        } else if (cls.equals(Month.class)) {
            return Month.of(random.nextInt(1, 13));
        } else if (cls.equals(Year.class)) {
            return Year.of(random.nextInt(0, 50000));
        } else if (cls.equals(YearMonth.class)) {
            return YearMonth.of(random.nextInt(0, 50000), random.nextInt(1, 13));
        }else{
            throw new IllegalArgumentException("type: " + cls);
        }
    }
}
