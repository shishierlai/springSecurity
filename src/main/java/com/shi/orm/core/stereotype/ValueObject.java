package com.shi.orm.core.stereotype;

public abstract class ValueObject {
    private String ts;
    private String create_ts;
    private int state;

    public ValueObject() {
    }

    public abstract String getId();

    public abstract void setId(String var1);

    public String getTs() {
        return this.ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getCreate_ts() {
        return this.create_ts;
    }

    public void setCreate_ts(String create_ts) {
        this.create_ts = create_ts;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
