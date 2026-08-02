package com.duong.springdemoresful.helper;

public class ResourceNotFoundException  extends  RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
