package cn.bincker.mybatis.plus.encrypt.mapper;

import cn.bincker.mybatis.plus.encrypt.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectByIdentityCardNumber(@Param("user") User user);
}
