package cn.bincker.mybatis.encrypt.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

public class EncryptByteArrayTypeHandler<T> extends BaseTypeHandler<T> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null){
            ps.setNull(i, Types.BINARY);
            return;
        }
        if (!parameter.getClass().equals(byte[].class)){
            throw new SQLException("parameter must be byte[] type.");
        }
        ps.setBytes(i, (byte[]) parameter);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return (T) rs.getBytes(columnName);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return (T) rs.getBytes(columnIndex);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (T) cs.getBytes(columnIndex);
    }
}
