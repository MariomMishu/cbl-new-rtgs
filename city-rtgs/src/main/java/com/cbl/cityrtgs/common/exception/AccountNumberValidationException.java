package com.cbl.cityrtgs.common.exception;


public class AccountNumberValidationException extends ValidationException {
    public AccountNumberValidationException(String message) {
        super(message);
    }

    public AccountNumberValidationException(String code, String message) {
        super(code, message);
    }
}
