
package com.shi.shiro.vo;


import com.shi.orm.core.stereotype.Column;
import com.shi.orm.core.stereotype.Table;
import com.shi.orm.core.stereotype.ValueObject;

/**
* * 用户表
**/
@Table(name = "User")
public class UserVo extends ValueObject {

    @Column(name = "ID")
    private String id;

    @Column(name = "USER_NAME")
    private String user_name;

    @Column(name = "LOGIN_CODE")
    private String login_code;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "SALT")
    private String salt;

    
    /**
    * 字段名：
    * 数据库类型：CHAR(24)
    **/
    public String getId() {
        return id;
    }

    /**
    * 字段名：
    * 数据库类型：CHAR(24)
    **/
    public void setId(String id) {
        this.id = id;
    }
    /**
    * 字段名：用户名称
    * 数据库类型：VARCHAR(50)
    **/
    public String getUser_name() {
        return user_name;
    }

    /**
    * 字段名：用户名称
    * 数据库类型：VARCHAR(50)
    **/
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    /**
    * 字段名：登录名
    * 数据库类型：VARCHAR(50)
    **/
    public String getLogin_code() {
        return login_code;
    }

    /**
    * 字段名：登录名
    * 数据库类型：VARCHAR(50)
    **/
    public void setLogin_code(String login_code) {
        this.login_code = login_code;
    }
    /**
    * 字段名：密码
    * 数据库类型：VARCHAR(64)
    **/
    public String getPassword() {
        return password;
    }

    /**
    * 字段名：密码
    * 数据库类型：VARCHAR(64)
    **/
    public void setPassword(String password) {
        this.password = password;
    }
    /**
    * 字段名：密码加盐
    * 数据库类型：VARCHAR(32)
    **/
    public String getSalt() {
        return salt;
    }

    /**
    * 字段名：密码加盐
    * 数据库类型：VARCHAR(32)
    **/
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
}
