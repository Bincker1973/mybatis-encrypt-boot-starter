package cn.bincker.mybatis.plus.encrypt;

import cn.bincker.mybatis.plus.encrypt.entity.User;
import cn.bincker.mybatis.plus.encrypt.mapper.UserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

@SpringBootTest
class MybatisPlusEncryptBootStarterApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    void list() {
        var userList = userMapper.selectList(Wrappers.emptyWrapper());
        var timeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (User user : userList) {
            System.out.printf(
                    "id:%d\tusername:%s\tphone:%s\tpassword:%s\trealname:%s\tidentityCardNumber:%s\tcreatedTime:%s\tlatestModifiedTime:%s\n",
                    user.getId(),
                    user.getUsername(),
                    user.getPhone(),
                    user.getPassword(),
                    user.getRealname(),
                    user.getIdentityCardNumber(),
                    timeFormatter.format(user.getCreatedTime()),
                    timeFormatter.format(user.getLastModifiedTime())
            );
        }
    }

    @Test
    void single() {
        var user = new User();
        user.setIdentityCardNumber("");
        userMapper.selectByIdentityCardNumber(user);
    }
}
