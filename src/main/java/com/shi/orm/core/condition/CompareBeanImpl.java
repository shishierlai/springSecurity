package com.shi.orm.core.condition;

public class CompareBeanImpl implements ICompareBean {

    private CompareBeanImpl.Type type;

    private String field;

    private Object value;


    public CompareBeanImpl(Type type, String field, Object value) {
        this.type = type;
        this.field = field;
        this.value = value;
    }

    public static enum Type {
        EQ;

        private Type() {
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
