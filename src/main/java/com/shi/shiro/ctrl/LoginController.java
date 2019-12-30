package com.shi.shiro.ctrl;


import cn.hutool.core.net.NetUtil;
import com.shi.config.stereotype.JsonArg;
import com.shi.config.web.DevException;
import com.shi.config.web.WebUtils;
import com.shi.shiro.ex.LoginFailException;
import com.shi.shiro.service.LoginService;

import com.shi.shiro.utils.PasswordHelpter;
import com.shi.shiro.utils.SecurityUserContext;
import com.shi.shiro.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;


    @RequestMapping("index")
    public ModelAndView index() {

        ModelAndView mv = new ModelAndView(new RedirectView("login.html"));

        return mv;
    }
    @RequestMapping("unauth")
    public ModelAndView noAllow() {
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("message", "没有权限");
        return mv;
    }

    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response,
                                   @JsonArg("login_code") String login_code, @JsonArg("login_password") String login_password) throws LoginFailException {


        if(StringUtils.isEmpty(login_code)){
            throw new DevException("输入的用户名不能为空！");
        }
        if(StringUtils.isEmpty(login_password)){
            throw new DevException("输入的密码不能为空！");
        }

        UserVo loginUser = loginService.getUserByLoginName(login_code);
        if(loginUser==null){
            log.info("\t用户不存在：{}", login_code);
            throw new UnknownAccountException("用户不存在");

        }
        String entype_password= PasswordHelpter.encryptPassword(login_password,loginUser.getSalt());
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(login_code,entype_password);
        try{
            subject.login(usernamePasswordToken);
            log.info("\t登录成功,用户：{},IP：{}", login_code, NetUtil.getLocalhostStr());

        }catch (UnknownAccountException e){
            //e.printStackTrace();
            log.info("\t登录失败（用户名/密码错误）,用户：{}", login_code);
            throw new LoginFailException(login_code, "用户或密码错误");

        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            log.info("\t登录失败（未知用户名）,用户：{}", login_code);
            throw new LoginFailException(login_code, "用户或密码错误");
        }catch (ExcessiveAttemptsException e) {
            // TODO: handle exception
            log.info("\t登录失败多次，账户锁定10分钟,用户：{}", login_code);

        } catch (AuthenticationException e) {
            // 其他错误，比如锁定，如果想单独处理请单独catch处理
            log.info("\t其他错误,用户：{}", login_code);
        }
        //如果登录成功
        if(subject.isAuthenticated()){
            //保存用户相关信息
            sessionHandle(login_code, request);
        }

        return WebUtils.responseRender();

    }

    private void sessionHandle(String login_code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo loginUser = loginService.getUserByLoginName(login_code);
        if(loginUser != null){
            session.setAttribute("userId", loginUser.getId());
            session.setAttribute("username", loginUser.getUser_name());
            session.setAttribute("login_code", loginUser.getLogin_code());

        }
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

    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        HttpSession session = request.getSession();
        session.invalidate();
        return new ModelAndView(new RedirectView("index"));
    }

    @RequestMapping("test")
    public Map<String,Object> test(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        log.info("datatest===="+session.getAttribute("userId"));
        log.info("datatest id===="+session.getId());

        return WebUtils.responseRender();
    }




}
