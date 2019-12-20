package com.shi.config.web;

/*
* create by shishierlai
* func:开发级别错误
* */

public class DevException extends RuntimeException {

    public DevException(String message) {
        super(message);
    }

    public DevException(String message, Throwable cause) {
        super(message, cause);
    }
}
