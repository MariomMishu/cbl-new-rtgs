package com.cbl.cityrtgs.common.exception;

public class CurrencyValidationException extends ValidationException {
    public CurrencyValidationException(String code, String message) {
        super(code, message);
    }
}
