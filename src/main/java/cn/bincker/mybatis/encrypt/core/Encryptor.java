package cn.bincker.mybatis.encrypt.core;

import java.io.InputStream;
import java.io.OutputStream;

public interface Encryptor {
    byte[] encrypt(byte[] data, Object key);
    void encrypt(InputStream in, OutputStream out, Object key);
    byte[] decrypt(byte[] data, Object key);
    void decrypt(InputStream in, OutputStream out, Object key);
    byte[] messageDigest(byte[] data, Object salt);
    byte[] messageDigest(InputStream in, Object salt);
}
