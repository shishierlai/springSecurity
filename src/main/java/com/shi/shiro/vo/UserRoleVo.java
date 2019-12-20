
package com.shi.shiro.vo;

import com.shi.orm.core.stereotype.Column;
import com.shi.orm.core.stereotype.Table;
import com.shi.orm.core.stereotype.ValueObject;
/**
* * 用户角色表
**/
@Table(name = "UserRole")
public class UserRoleVo extends ValueObject {

    @Column(name = "ID")
    private String id;

    @Column(name = "USER_ID")
    private String user_id;

    @Column(name = "ROLE_ID")
    private String role_id;

    
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
    * 字段名：
    * 数据库类型：CHAR(24)
    **/
    public String getUser_id() {
        return user_id;
    }

    /**
    * 字段名：
    * 数据库类型：CHAR(24)
    **/
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    /**
    * 字段名：
    * 数据库类型：CHAR(24)
    **/
    public String getRole_id() {
        return role_id;
    }

    /**
    * 字段名：
    * 数据库类型：CHAR(24)
    **/
    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
    
}
