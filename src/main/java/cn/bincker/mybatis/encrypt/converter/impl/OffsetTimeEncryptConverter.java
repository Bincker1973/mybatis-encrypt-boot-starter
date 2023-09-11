package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

import java.nio.ByteBuffer;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public class OffsetTimeEncryptConverter extends BaseEncryptConverter<OffsetTime> {
    private static final int BYTES = Long.BYTES + Integer.BYTES;
    @Override
    public OffsetTime convertNonNull(byte[] data) {
        if (data.length < BYTES) throw new InvalidDataException("OffsetDateTime need " + BYTES + " bytes but got " + data.length);
        var byteBuffer = ByteBuffer.wrap(data);
        return OffsetTime.of(
                LocalTime.ofNanoOfDay(byteBuffer.getLong()),
                ZoneOffset.ofTotalSeconds(byteBuffer.getInt())
        );
    }

    @Override
    public byte[] convertNonNull(OffsetTime object) {
        var byteBuffer = ByteBuffer.allocate(BYTES);
        byteBuffer.putLong(object.toLocalTime().toNanoOfDay());
        byteBuffer.putInt(object.getOffset().getTotalSeconds());
        return byteBuffer.array();
    }
}
