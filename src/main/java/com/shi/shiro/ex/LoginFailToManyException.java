package com.shi.shiro.ex;

import com.shi.config.web.BusiException;

/**
 * Created by wuby on 2018/11/27.
 */
public class LoginFailToManyException extends BusiException {

    private String login_code;

    public LoginFailToManyException(String login_code) {
        super("密码错误次数过多，请稍后再试");
        this.login_code=login_code;
    }

    @Override
    public Integer getCode() {
        return 2001;
    }

}
