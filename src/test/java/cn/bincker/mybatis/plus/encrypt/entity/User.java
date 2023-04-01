package cn.bincker.mybatis.plus.encrypt.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "public.user", autoResultMap = true)
public class User{
    @TableId
    private Long id;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    private String password;
    private String phone;
    private String realname;
    private String identityCardNumber;
    private Date createdTime;
    private Date LatestModifiedTime;
}
