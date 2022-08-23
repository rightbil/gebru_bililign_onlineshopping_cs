package dev.cs.onlineshopping.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    @ExceptionHandler(value=Exception.class)
    public void  CustomerNotFound(String msg ){

    }
}