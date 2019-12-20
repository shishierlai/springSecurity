
package com.shi.shiro.vo;

import com.shi.orm.core.stereotype.Column;
import com.shi.orm.core.stereotype.Table;
import com.shi.orm.core.stereotype.ValueObject;
/**
* * 角色权限表
**/
@Table(name = "UserPermission")
public class UserPermissionVo extends ValueObject {

    @Column(name = "ID")
    private String id;

    @Column(name = "ROLE_ID")
    private String role_id;

    @Column(name = "PERMISSION_ID")
    private String permission_id;

    
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
    /**
    * 字段名：
    * 数据库类型：CHAR(24)
    **/
    public String getPermission_id() {
        return permission_id;
    }

    /**
    * 字段名：
    * 数据库类型：CHAR(24)
    **/
    public void setPermission_id(String permission_id) {
        this.permission_id = permission_id;
    }
    
}
