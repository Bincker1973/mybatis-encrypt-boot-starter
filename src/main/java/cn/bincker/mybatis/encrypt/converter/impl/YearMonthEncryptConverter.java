package cn.bincker.mybatis.encrypt.converter.impl;

import java.nio.ByteBuffer;
import java.time.YearMonth;

public class YearMonthEncryptConverter extends BaseEncryptConverter<YearMonth>{
    private static final int BYTES = Integer.BYTES + Integer.BYTES;
    @Override
    public YearMonth convertNonNull(byte[] data) {
        var byteBuffer = ByteBuffer.wrap(data);
        return YearMonth.of(byteBuffer.getInt(), byteBuffer.getInt());
    }

    @Override
    public byte[] convertNonNull(YearMonth object) {
        var byteBuffer = ByteBuffer.allocate(BYTES);
        byteBuffer.putInt(object.getYear());
        byteBuffer.putInt(object.getMonthValue());
        return byteBuffer.array();
    }
}
