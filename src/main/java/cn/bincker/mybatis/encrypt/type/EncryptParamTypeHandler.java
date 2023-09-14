package cn.bincker.mybatis.encrypt.type;

import cn.bincker.mybatis.encrypt.converter.EncryptConvertRegister;
import cn.bincker.mybatis.encrypt.converter.EncryptConverter;
import cn.bincker.mybatis.encrypt.core.EncryptExecutor;
import cn.bincker.mybatis.encrypt.core.EncryptKeyProvider;
import cn.bincker.mybatis.encrypt.core.Encryptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

@Slf4j
public class EncryptParamTypeHandler<T extends EncryptParam<?>> extends BaseTypeHandler<T> {
    private final EncryptConvertRegister convertRegister;
    private final Encryptor encryptor;
    private final EncryptKeyProvider keyProvider;

    public EncryptParamTypeHandler(EncryptExecutor encryptExecutor) {
        convertRegister = encryptExecutor.getConverterRegister();
        encryptor = encryptExecutor.getEncryptor();
        keyProvider = encryptExecutor.getKeyProvider();
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        var value = parameter.getValue();
        if (value == null){
            ps.setNull(i, Types.BINARY);
            return;
        }
        byte[] data;
        if (value.getClass() != byte[].class) {
            var converter = convertRegister.getConverter(value.getClass());
            if (converter.isEmpty()) throw new IllegalArgumentException("illegal param type: " + value.getClass());
            //noinspection unchecked
            data = ((EncryptConverter<Object>)converter.get()).toBinary(value);
        }else{
            data = (byte[]) value;
        }
        ps.setBytes(i, encryptor.encrypt(data, keyProvider.getKey(parameter.getCls(), parameter.getProperty())));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) {
        throw new UnsupportedOperationException();
    }
}
