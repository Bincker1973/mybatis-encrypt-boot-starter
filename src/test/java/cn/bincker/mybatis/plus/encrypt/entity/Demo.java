package cn.bincker.mybatis.plus.encrypt.entity;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import cn.bincker.mybatis.encrypt.type.EncryptByteArrayTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.Date;

@Data
@TableName(value = "public.demo")
public class Demo {
    @TableId
    private Long id;

    @Encrypt
    private Boolean booleanTypeField;

    @Encrypt
    private boolean booleanField;

    @Encrypt
    private Byte byteTypeField;

    @Encrypt
    private byte byteField;

    @Encrypt
    private Short shortTypeField;

    @Encrypt
    private short shortField;

    @Encrypt
    private Integer integerField;

    @Encrypt
    private int intField;

    @Encrypt
    private Long longTypeField;

    @Encrypt
    private long longField;

    @Encrypt
    private Float floatTypeField;

    @Encrypt
    private float floatField;

    @Encrypt
    private Double doubleTypeField;

    @Encrypt
    private double doubleField;

    @Encrypt
    private String stringField;

    @Encrypt
    private BigInteger bigIntegerField;

    @Encrypt
    private BigDecimal bigDecimalField;

    @Encrypt
    private Date dateTypeField;

    @Encrypt
    private java.sql.Date dateField;

    @Encrypt
    private java.sql.Time timeField;

    @Encrypt
    private java.sql.Timestamp timestampField;

    @Encrypt
    private Instant instantField;

    @Encrypt
    private LocalDateTime localDateTimeField;

    @Encrypt
    private LocalDate localDateField;

    @Encrypt
    private LocalTime localTimeField;

    @Encrypt
    private OffsetDateTime offsetDateTimeField;

    @Encrypt
    private OffsetTime offsetTimeField;

    @Encrypt
    private ZonedDateTime zonedDateTimeField;

    @Encrypt
    private Month monthField;

    @Encrypt
    private Year yearField;

    @Encrypt
    private YearMonth yearMonthField;

    @Encrypt
    @TableField(typeHandler = EncryptByteArrayTypeHandler.class)
    private byte[] byteArrayField;
}
