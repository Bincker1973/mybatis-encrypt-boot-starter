package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeEncryptConverter extends BaseEncryptConverter<OffsetDateTime> {
    private static final int BYTES = Long.BYTES + Integer.BYTES + Integer.BYTES;
    @Override
    public OffsetDateTime convertNonNull(byte[] data) {
        if (data.length < BYTES) throw new InvalidDataException("OffsetDateTime need " + BYTES + " bytes but got " + data.length);
        var byteBuffer = ByteBuffer.wrap(data);
        return OffsetDateTime.of(
                LocalDateTime.ofEpochSecond(byteBuffer.getLong(), byteBuffer.getInt(), ZoneOffset.UTC),
                ZoneOffset.ofTotalSeconds(byteBuffer.getInt())
        );
    }

    @Override
    public byte[] convertNonNull(OffsetDateTime object) {
        var byteBuffer = ByteBuffer.allocate(BYTES);
        byteBuffer.putLong(object.toLocalDateTime().toEpochSecond(ZoneOffset.UTC));
        byteBuffer.putInt(object.getNano());
        byteBuffer.putInt(object.getOffset().getTotalSeconds());
        return byteBuffer.array();
    }
}
