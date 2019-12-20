package com.shi.shiro.ctrl;


import com.shi.config.web.WebUtils;
import com.shi.shiro.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/api/")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> test(){
        System.out.println("test");
        return WebUtils.responseRender();

    }


}
