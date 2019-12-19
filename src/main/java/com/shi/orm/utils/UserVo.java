package com.shi.orm.utils;


import com.shi.orm.core.stereotype.Column;
import com.shi.orm.core.stereotype.Table;
import com.shi.orm.core.stereotype.ValueObject;

@Table(name = "User")
public class UserVo extends ValueObject {

    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NODE_ID")
    private Integer node_id;

    @Column(name = "PARENT_ID")
    private Integer parent_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNode_id() {
        return node_id;
    }

    public void setNode_id(Integer node_id) {
        this.node_id = node_id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }
}
