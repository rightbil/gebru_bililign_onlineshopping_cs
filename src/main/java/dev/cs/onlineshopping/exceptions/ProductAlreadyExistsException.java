package dev.cs.onlineshopping.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductAlreadyExistsException extends RuntimeException {
    @ExceptionHandler(value=Exception.class)
    public void  CustomerNotFound(String msg ){

    }
}