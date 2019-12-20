
package com.shi.shiro.vo;


import com.shi.orm.core.stereotype.Column;
import com.shi.orm.core.stereotype.Table;
import com.shi.orm.core.stereotype.ValueObject;

/**
* * 角色表
**/
@Table(name = "Role")
public class RoleVo extends ValueObject {

    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    
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
    * 字段名：角色名称
    * 数据库类型：VARCHAR(50)
    **/
    public String getName() {
        return name;
    }

    /**
    * 字段名：角色名称
    * 数据库类型：VARCHAR(50)
    **/
    public void setName(String name) {
        this.name = name;
    }
    
}
