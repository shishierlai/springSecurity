package com.shi.shiro.ctrl;

import com.shi.shiro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class MainController {


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView index(Map model) throws Exception {
        return new ModelAndView(new RedirectView("/login.html"));
    }

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public ModelAndView main(Map model) throws Exception {
        return new ModelAndView(new RedirectView("/person.html#"));
    }



}
