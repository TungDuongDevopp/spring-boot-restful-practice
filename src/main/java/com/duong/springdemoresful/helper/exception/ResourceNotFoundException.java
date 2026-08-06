package com.duong.springdemoresful.helper.exception;

public class ResourceNotFoundException  extends  RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
