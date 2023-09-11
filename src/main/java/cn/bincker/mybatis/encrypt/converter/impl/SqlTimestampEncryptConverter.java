package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

import java.nio.ByteBuffer;
import java.sql.Timestamp;

public class SqlTimestampEncryptConverter extends BaseEncryptConverter<Timestamp> {
    private static final int BYTES = Long.BYTES + Integer.BYTES;
    @Override
    public Timestamp convertNonNull(byte[] data) {
        if (data.length < BYTES) throw new InvalidDataException("timestamp need " + Short.BYTES + " bytes but got " + data.length);
        var byteBuffer = ByteBuffer.wrap(data);
        var timestamp = new Timestamp(byteBuffer.getLong());
        timestamp.setNanos(byteBuffer.getInt());
        return timestamp;
    }

    @Override
    public byte[] convertNonNull(Timestamp object) {
        var byteBuffer = ByteBuffer.allocate(BYTES);
        byteBuffer.putLong(object.getTime());
        byteBuffer.putInt(object.getNanos());
        return byteBuffer.array();
    }
}
