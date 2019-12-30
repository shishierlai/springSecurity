package com.shi.config.web;

/**
 * Created by wuby on 2017/6/22.
 */
public class BusiException extends Exception {

    public BusiException(String message, Exception e) {
        super(message,e);
    }

    public BusiException(String message) {
        super(message);
    }

    public Object getData(){
        return null;
    }

    public Integer getCode(){
        return 9500;
    }
}
