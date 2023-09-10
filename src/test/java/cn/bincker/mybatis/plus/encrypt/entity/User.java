package cn.bincker.mybatis.plus.encrypt.entity;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.ByteArrayTypeHandler;

import java.util.Date;

@Data
@TableName(value = "public.user")
public class User{
    @TableId
    private Long id;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @Encrypt
    @TableField(typeHandler = ByteArrayTypeHandler.class)
    private String password;

    @Encrypt
    @TableField(typeHandler = ByteArrayTypeHandler.class)
    private String phone;

    @Encrypt
    @TableField(typeHandler = ByteArrayTypeHandler.class)
    private String realname;

    @Encrypt
    @TableField(typeHandler = ByteArrayTypeHandler.class)
    private String identityCardNumber;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifiedTime;
}
