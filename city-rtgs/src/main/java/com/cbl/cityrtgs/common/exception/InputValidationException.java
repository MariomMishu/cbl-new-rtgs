package com.cbl.cityrtgs.common.exception;


public class InputValidationException extends ValidationException {
    public InputValidationException(String message) {
        super(message);
    }

    public InputValidationException(String code, String message) {
        super(code, message);
    }
}
