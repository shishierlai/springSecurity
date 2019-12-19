package com.shi.orm.utils;

import java.util.UUID;

/*
*
* 自动生成ID工具类
* */
public class GenerateID {
    private static GenerateID me = new GenerateID();

    public GenerateID() {
    }

    public static GenerateID getInstance() {
        return me;
    }


    public String generateID() {
        return (new GenerateIObjectID()).toHexString();
    }

    public static void main(String[] args) {
        for(int i = 0; i < 5; ++i) {
            System.out.println(GenerateID.getInstance().generateID());
        }

    }

}
