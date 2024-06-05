package com.cbl.cityrtgs.common.exception;


public class ValidationException extends Exception {
    private String code;
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
