package com.cbl.cityrtgs.common.exception;


public class BaseException extends RuntimeException {

    private String errorCode;

    public BaseException() {
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public int getResponseCode() {
        return 500;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
