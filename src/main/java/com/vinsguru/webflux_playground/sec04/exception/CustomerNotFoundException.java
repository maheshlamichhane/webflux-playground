package com.vinsguru.webflux_playground.sec04.exception;

public class CustomerNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Customer [id=%d] is not found";

    public CustomerNotFoundException(Integer id){
        super(MESSAGE.formatted(id));
    }
}
