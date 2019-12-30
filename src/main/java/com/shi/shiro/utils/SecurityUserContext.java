package com.shi.shiro.utils;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*
* 工具类： 获取当前用户ID
* */
public class SecurityUserContext {



    public static String getUserID(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String user_id=String.valueOf(session.getAttribute("userId"));
        return user_id==null ? null : user_id;
    }


}
