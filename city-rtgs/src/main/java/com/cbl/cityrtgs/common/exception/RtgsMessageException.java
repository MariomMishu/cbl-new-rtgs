package com.cbl.cityrtgs.common.exception;


public class RtgsMessageException extends RuntimeException {

    private String errorCode;

    public RtgsMessageException() {
    }

    public RtgsMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public RtgsMessageException(String message) {
        super(message);
    }

    public RtgsMessageException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public RtgsMessageException(Throwable cause) {
        super(cause);
    }

    public int getResponseCode() {
        return 500;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
