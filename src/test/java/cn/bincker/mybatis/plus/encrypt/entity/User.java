package cn.bincker.mybatis.plus.encrypt.entity;

import cn.bincker.mybatis.encrypt.annotation.Encrypt;
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
    @Encrypt
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @Encrypt
    private String password;
    private String phone;
    private String realname;
    private String identityCardNumber;
    private Date createdTime;
    private Date LatestModifiedTime;
}
