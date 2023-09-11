package cn.bincker.mybatis.encrypt.converter.impl;

public class ByteEncryptConverter extends BaseEncryptConverter<Byte> {
    @Override
    public Byte convertNonNull(byte[] data) {
        return data[0];
    }

    @Override
    public byte[] convertNonNull(Byte object) {
        return new byte[]{object};
    }
}
