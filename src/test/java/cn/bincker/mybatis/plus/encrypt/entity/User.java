package cn.bincker.mybatis.plus.encrypt.entity;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "public.user")
public class User{
    @TableId
    private Long id;

    private String username;

    @Encrypt
    private String password;

    @Encrypt
    private String phone;

    @Encrypt
    private String realname;

    @Encrypt
    private String identityCardNumber;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifiedTime;
}
