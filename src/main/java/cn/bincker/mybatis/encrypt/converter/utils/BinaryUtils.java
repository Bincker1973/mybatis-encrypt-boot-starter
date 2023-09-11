package cn.bincker.mybatis.encrypt.converter.utils;

import cn.bincker.mybatis.encrypt.exception.InvalidDataException;

import java.time.Instant;

public class BinaryUtils {
    private static final int INSTANT_BYTES = Long.BYTES + Integer.BYTES;
    public static Long binary2long(byte[] data){
        if (data.length < Long.BYTES) throw new InvalidDataException("long need " + Long.BYTES + " bytes but got " + data.length);
        long result = 0;
        for (int i = 0; i < Long.BYTES; i++) {
            result = result << 8 | (data[i] & 0xff);
        }
        return result;
    }

    public static byte[] long2binary(Long object){
        byte[] result = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++) {
            result[Long.BYTES - i - 1] = (byte) (object & 0xff);
            object >>= 8;
        }
        return result;
    }

    public static Integer binary2int(byte[] data) {
        if (data.length < Integer.BYTES) throw new InvalidDataException("integer need " + Integer.BYTES + " bytes but got " + data.length);
        int result = 0;
        for (int i = 0; i < Integer.BYTES; i++) {
            result = result << 8 | data[i] & 0xff;
        }
        return result;
    }

    public static byte[] int2binary(Integer object) {
        byte[] result = new byte[Integer.BYTES];
        for (int i = 0; i < Integer.BYTES; i++) {
            result[Integer.BYTES - i - 1] = (byte) (object & 0xff);
            object >>= 8;
        }
        return result;
    }

    public static Instant binary2instant(byte[] data){
        if (data.length < INSTANT_BYTES) throw new InvalidDataException("instant need " + INSTANT_BYTES + " bytes but got " + data.length);
        long epochSecond = 0;
        for (int i = 0; i < Long.BYTES; i++) {
            epochSecond = epochSecond << 8 | data[i] & 0xff;
        }
        int nano = 0;
        for (int i = Long.BYTES; i < INSTANT_BYTES; i++) {
            nano = nano << 8 | data[i] & 0xff;
        }
        return Instant.ofEpochSecond(epochSecond, nano);
    }

    public static byte[] instant2binary(Instant instant){
        byte[] data = new byte[INSTANT_BYTES];
        long epochSecond = instant.getEpochSecond();
        int nano = instant.getNano();
        for (int i = 0; i < Long.BYTES; i++) {
            data[Long.BYTES - i - 1] = (byte) (epochSecond & 0xff);
            epochSecond >>= 8;
        }
        for (int i = Long.BYTES, j = 0; i < INSTANT_BYTES; i++, j++) {
            data[INSTANT_BYTES - j - 1] = (byte) (nano & 0xff);
            nano >>= 8;
        }
        return data;
    }
}
