package com.cbl.cityrtgs.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
}
