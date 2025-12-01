package com.techlab.ecommerce.exception;


import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends TechlabException {

    public ProductNotFoundException(String queryTerm, Integer errorCode) {
        super("Product could not be found",
                "Not found product searching with this term: <%s>, queryTerm)",
                errorCode,
                HttpStatus.NOT_FOUND);
    }
}
