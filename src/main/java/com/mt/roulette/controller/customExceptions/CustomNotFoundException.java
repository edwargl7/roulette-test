package com.mt.roulette.controller.customExceptions;

public class CustomNotFoundException extends RuntimeException{

    public CustomNotFoundException(String msg){
        super(msg);
    }
}
