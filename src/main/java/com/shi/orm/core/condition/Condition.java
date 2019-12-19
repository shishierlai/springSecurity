package com.shi.orm.core.condition;

import java.util.ArrayList;
import java.util.List;

public class Condition {

    private List<ICompareBean> compares=new ArrayList<>();

    public Condition() {

    }

    public List<ICompareBean> getCompares() {
        return this.compares;
    }

    public Condition eq(String field, Object value){
        this.compares.add(new CompareBeanImpl(CompareBeanImpl.Type.EQ,field,value));
        return this;
    }

}
