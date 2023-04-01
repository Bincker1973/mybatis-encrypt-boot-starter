package cn.bincker.mybatis.encrypt.exception;

/**
 * 全局基类异常
 */
public class MybatisEncryptException extends RuntimeException{
    public MybatisEncryptException() {
    }

    public MybatisEncryptException(String message) {
        super(message);
    }

    public MybatisEncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public MybatisEncryptException(Throwable cause) {
        super(cause);
    }

    public MybatisEncryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
