package cn.bincker.mybatis.encrypt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(MybatisEncryptProperties.PREFIX)
@Data
public class MybatisEncryptProperties {
    public static final String PREFIX = "mybatis.encrypt";

    private Boolean enable;
}
