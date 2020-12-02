package com.mt.roulette.controller.customExceptions;

public class CustomInvalidDataException extends RuntimeException{

    public CustomInvalidDataException(String msg){
        super(msg);
    }
}
