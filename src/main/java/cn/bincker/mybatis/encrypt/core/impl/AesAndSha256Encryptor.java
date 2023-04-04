package cn.bincker.mybatis.encrypt.core.impl;

import cn.bincker.mybatis.encrypt.core.Encryptor;
import cn.bincker.mybatis.encrypt.exception.MybatisEncryptException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AesAndSha256Encryptor implements Encryptor {
    public static final String CIPHER_TRANSFORMATION = "AES";
    public static final String MESSAGE_DIGEST_ALGORITHM = "sha256";


    @Override
    public byte[] encrypt(byte[] data, Object key) {
        try {
            var cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, (Key) key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new MybatisEncryptException(e);
        }
    }

    @Override
    public void encrypt(InputStream in, OutputStream out, Object key) {
        try {
            var cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, (Key) key);
            byte[] buffer = new byte[256];
            int len;
            while ((len = in.read(buffer)) > 0){
                out.write(cipher.update(buffer, 0, len));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException e) {
            throw new MybatisEncryptException(e);
        }
    }

    @Override
    public byte[] decrypt(byte[] data, Object key) {
        try {
            var cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, (Key) key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new MybatisEncryptException(e);
        }
    }

    @Override
    public void decrypt(InputStream in, OutputStream out, Object key) {
        try {
            var cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, (Key) key);
            byte[] buffer = new byte[256];
            int len;
            while ((len = in.read(buffer)) > 0){
                out.write(cipher.update(buffer, 0, len));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException e) {
            throw new MybatisEncryptException(e);
        }
    }

    @Override
    public byte[] messageDigest(byte[] data, Object salt) {
        try {
            var digest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
            digest.update((byte[]) salt);
            digest.update(data);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new MybatisEncryptException(e);
        }
    }

    @Override
    public byte[] messageDigest(InputStream in, Object salt) {
        try {
            var digest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
            digest.update((byte[]) salt);
            byte[] buffer = new byte[256];
            int len;
            while ((len = in.read(buffer)) > 0){
                digest.update(buffer, 0, len);
            }
            return digest.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new MybatisEncryptException(e);
        }
    }
}
