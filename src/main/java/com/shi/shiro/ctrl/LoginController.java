package com.shi.shiro.ctrl;


import cn.hutool.core.net.NetUtil;
import com.shi.config.stereotype.JsonArg;
import com.shi.config.web.DevException;
import com.shi.config.web.WebUtils;
import com.shi.shiro.service.LoginService;

import com.shi.shiro.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;


    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> test(HttpServletRequest request, HttpServletResponse response,
                                   @JsonArg("login_code") String login_code, @JsonArg("login_password") String login_password) {


        if(StringUtils.isEmpty(login_code)){
            throw new DevException("输入的用户名不能为空！");
        }
        if(StringUtils.isEmpty(login_password)){
            throw new DevException("输入的密码不能为空！");
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(login_code,login_password);
        try{
            subject.login(usernamePasswordToken);
            log.info("\t登录成功,用户：{},IP：{}", login_code, NetUtil.getLocalhostStr());

        }catch (Exception e){
            e.printStackTrace();
            log.info("\t登录失败（密码错误）,用户：{}", login_code);
        }

        return WebUtils.responseRender();

    }





    @RequestMapping("register")
    @ResponseBody
    public Map<String,Object> register(@JsonArg String user_name,@JsonArg String password){
        if(StringUtils.isEmpty(user_name)){
            throw new DevException("输入的用户名不能为空！");
        }
        if(StringUtils.isEmpty(password)){
            throw new DevException("输入的密码不能为空！");
        }
        loginService.register(user_name,password);
        return WebUtils.responseRender();

    }




}
