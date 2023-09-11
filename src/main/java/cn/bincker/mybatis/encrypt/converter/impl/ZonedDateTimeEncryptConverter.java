package cn.bincker.mybatis.encrypt.converter.impl;

import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeEncryptConverter extends BaseEncryptConverter<ZonedDateTime> {
    private static final int BYTES = Long.BYTES + Integer.BYTES + Integer.BYTES;
    @Override
    public ZonedDateTime convertNonNull(byte[] data) {
        if (data.length < BYTES) throw new InvalidDataException("OffsetDateTime need " + BYTES + " bytes but got " + data.length);
        var byteBuffer = ByteBuffer.wrap(data);
        long epochMilli = byteBuffer.getLong();
        int nano = byteBuffer.getInt();
        int offsetSeconds = byteBuffer.getInt();
        byte[] zoneIdData = new byte[byteBuffer.limit() - byteBuffer.position()];
        byteBuffer.get(zoneIdData);
        String zoneIdStr = new String(zoneIdData);
        ZoneId zoneId = ZoneId.of(zoneIdStr);
        return ZonedDateTime.ofLocal(
                LocalDateTime.ofEpochSecond(epochMilli, nano, ZoneOffset.UTC),
                zoneId,
                ZoneOffset.ofTotalSeconds(offsetSeconds)
        );
    }

    @Override
    public byte[] convertNonNull(ZonedDateTime object) {
        byte[] zoneIdBytes = object.getZone().getId().getBytes();
        var byteBuffer = ByteBuffer.allocate(BYTES + zoneIdBytes.length);
        byteBuffer.putLong(object.toLocalDateTime().toEpochSecond(ZoneOffset.UTC));
        byteBuffer.putInt(object.getNano());
        byteBuffer.putInt(object.getOffset().getTotalSeconds());
        byteBuffer.put(zoneIdBytes);
        return byteBuffer.array();
    }
}
