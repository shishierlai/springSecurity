package com.shi.shiro.ex;

import com.shi.config.web.BusiException;

/**
 * Created by wuby on 2018/11/27.
 */
public class LoginFailException extends BusiException {

    private String login_code;

    public LoginFailException(String login_code,String message) {
        super(message);
        this.login_code=login_code;
    }

    @Override
    public Integer getCode() {
        return 2001;
    }

}
