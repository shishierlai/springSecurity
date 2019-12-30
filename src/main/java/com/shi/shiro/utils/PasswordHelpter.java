package com.shi.shiro.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelpter {

    private static String algorithmName="md5";

    private static final int hashInterations=10;

    public static String encryptPassword(String password,String salt){
        String Md5password=new SimpleHash(algorithmName,password, ByteSource.Util.bytes(salt),hashInterations).toHex();
        return Md5password;
    }

    public static void main(String[] args) {
        String psd="123";
        String md5=encryptPassword(psd,"123");
        System.out.println(md5);
    }
}
