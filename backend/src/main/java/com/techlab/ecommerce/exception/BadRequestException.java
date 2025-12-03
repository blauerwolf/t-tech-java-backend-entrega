package com.techlab.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends TechlabException {

    public BadRequestException(String message) {
        super("Product could not be found",
                "Not found product searching with this term: <%s>, message)",
                HttpStatus.BAD_REQUEST);
    }
}
