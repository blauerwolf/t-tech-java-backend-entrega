package com.techlab.ecommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TechlabException extends RuntimeException {
    protected String title;
    protected Integer errorCode;
    protected HttpStatus status;

    public TechlabException(String title, String message, Integer errorCode, HttpStatus status) {
        super(message);
        this.title = title;
        this.errorCode = errorCode;
        this.status = status;
    }


}
