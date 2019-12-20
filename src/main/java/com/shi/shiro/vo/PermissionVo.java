
package com.shi.shiro.vo;

import com.shi.orm.core.stereotype.Column;
import com.shi.orm.core.stereotype.Table;
import com.shi.orm.core.stereotype.ValueObject;
/**
* * 岗位表
**/
@Table(name = "Permission")
public class PermissionVo extends ValueObject {

    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    
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
    * 字段名：权限名称
    * 数据库类型：VARCHAR(50)
    **/
    public String getName() {
        return name;
    }

    /**
    * 字段名：权限名称
    * 数据库类型：VARCHAR(50)
    **/
    public void setName(String name) {
        this.name = name;
    }
    /**
    * 字段名：权限编码
    * 数据库类型：VARCHAR(50)
    **/
    public String getCode() {
        return code;
    }

    /**
    * 字段名：权限编码
    * 数据库类型：VARCHAR(50)
    **/
    public void setCode(String code) {
        this.code = code;
    }
    
}
