package cn.bincker.mybatis.encrypt;

import cn.bincker.mybatis.encrypt.converter.EncryptConvertRegister;
import cn.bincker.mybatis.encrypt.core.*;
import cn.bincker.mybatis.encrypt.core.impl.AesAndSha256Encryptor;
import cn.bincker.mybatis.encrypt.converter.impl.DefaultEncryptConvertRegister;
import cn.bincker.mybatis.encrypt.core.impl.DefaultEncryptExecutor;
import cn.bincker.mybatis.encrypt.reflection.EncryptReflectorFactory;
import cn.bincker.mybatis.encrypt.reflection.factory.EncryptObjectFactory;
import cn.bincker.mybatis.encrypt.type.EncryptTypeHandlerConfigurator;
import cn.bincker.mybatis.encrypt.wrapper.EncryptObjectWrapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableConfigurationProperties(MybatisEncryptProperties.class)
@Slf4j
public class MybatisEncryptAutoConfiguration {
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
        return (clazz, fieldName) -> new SecretKeySpec("hello_bincker_12".getBytes(), "AES");
    }

    @Bean
    @ConditionalOnMissingBean(EncryptSaltProvider.class)
    public EncryptSaltProvider temporarySaltProvider(){
        log.warn("您尚未提供EncryptSaltProvider Bean，数据完整性可能并不安全！");
        log.warn("You have not provided an EncryptSaltProvider bean, data integrity may not be secure!!");
        return (clazz, fieldName) -> "bincker".getBytes();
    }

    @Bean
    public DefaultEncryptExecutor defaultEncryptExecutor(Encryptor encryptor, EncryptConvertRegister convertRegister, EncryptKeyProvider encryptKeyProvider){
        return new DefaultEncryptExecutor(encryptor, convertRegister, encryptKeyProvider);
    }

    @Bean
    @ConditionalOnMissingClass("com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer")
    public ConfigurationCustomizer encryptMybatisCustomizer(EncryptExecutor encryptExecutor){
        return configuration -> configure(configuration, encryptExecutor);
    }

    @Bean
    @ConditionalOnClass(com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer.class)
    public com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer encryptMybatisPlusCustomizer(EncryptExecutor encryptExecutor){
        return configuration -> {
            configure(configuration, encryptExecutor);
            EncryptTypeHandlerConfigurator.configureWithMybatisPlus(configuration, encryptExecutor);
        };
    }

    private void configure(org.apache.ibatis.session.Configuration configuration, EncryptExecutor encryptExecutor){
        configuration.setObjectWrapperFactory(new EncryptObjectWrapperFactory(encryptExecutor));
        configuration.setObjectFactory(new EncryptObjectFactory());
        configuration.setReflectorFactory(new EncryptReflectorFactory());
        EncryptTypeHandlerConfigurator.configure(configuration, encryptExecutor);
    }
}
