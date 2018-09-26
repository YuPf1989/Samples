package com.rain.commonlib.net.Exception;

/**
 *http://www.jianshu.com/p/c105a4177982
 */

public class ApiException extends RuntimeException {
    private int errorCode;

    public ApiException(int code, String msg) {
        super(msg);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
