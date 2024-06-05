package com.cbl.cityrtgs.common.exception;


public class TxnTimeValidationException extends ValidationException {

    public TxnTimeValidationException(String code, String message) {
        super(code, message);
    }
}
