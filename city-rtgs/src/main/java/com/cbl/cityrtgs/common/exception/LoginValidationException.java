package com.cbl.cityrtgs.common.exception;


public class LoginValidationException extends ValidationException {
    public LoginValidationException(String message) {
        super(message);
    }

    public LoginValidationException(String code, String message) {
        super(code, message);
    }
}
