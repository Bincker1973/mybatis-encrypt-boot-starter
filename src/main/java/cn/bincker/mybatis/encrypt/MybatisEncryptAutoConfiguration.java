package cn.bincker.mybatis.encrypt;

import cn.bincker.mybatis.encrypt.core.EncryptConvertRegister;
import cn.bincker.mybatis.encrypt.core.EncryptKeyProvider;
import cn.bincker.mybatis.encrypt.core.EncryptSaltProvider;
import cn.bincker.mybatis.encrypt.core.Encryptor;
import cn.bincker.mybatis.encrypt.core.impl.AesAndSha256Encryptor;
import cn.bincker.mybatis.encrypt.core.impl.DefaultEncryptConvertRegister;
import cn.bincker.mybatis.encrypt.wrapper.EncryptObjectWrapperFactory;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableConfigurationProperties(MybatisEncryptProperties.class)
@Slf4j
public class MybatisEncryptAutoConfiguration implements ConfigurationCustomizer, com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer {
    @Bean
    @ConditionalOnMissingBean(EncryptConvertRegister.class)
    public EncryptConvertRegister defaultEncryptConvertRegister(){
        return new DefaultEncryptConvertRegister();
    }

    @Bean
    @ConditionalOnMissingBean(Encryptor.class)
    public Encryptor defaultEncryptor(){
        return new AesAndSha256Encryptor();
    }

    @Bean
    @ConditionalOnMissingBean(EncryptKeyProvider.class)
    public EncryptKeyProvider temporaryKeyProvider(){
        log.warn("您尚未提供EncryptKeyProvider Bean，加密的数据并不安全！");
        log.warn("You have not provided an EncryptKeyProvider bean, the encrypted data is not secure!");
        return (clazz, fieldName) -> new SecretKeySpec("bincker".getBytes(), "AES");
    }

    @Bean
    @ConditionalOnMissingBean(EncryptSaltProvider.class)
    public EncryptSaltProvider temporarySaltProvider(){
        log.warn("您尚未提供EncryptSaltProvider Bean，数据完整性可能并不安全！");
        log.warn("You have not provided an EncryptSaltProvider bean, data integrity may not be secure!!");
        return (clazz, fieldName) -> "bincker".getBytes();
    }

    @Override
    public void customize(org.apache.ibatis.session.Configuration configuration) {
        configuration.setObjectWrapperFactory(new EncryptObjectWrapperFactory());
    }

    @Override
    public void customize(MybatisConfiguration configuration) {
        configuration.setObjectWrapperFactory(new EncryptObjectWrapperFactory());
    }
}
