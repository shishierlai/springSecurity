package com.shi.config.web;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandler implements HandlerExceptionResolver {


    private final static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);
  
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String contentType=response.getContentType();
        if(contentType==null){
            HandlerMethod m = (HandlerMethod) handler;
            if(m.getMethod().getReturnType().getName().equals(ModelAndView.class.getName())){
                contentType = "html";
            }
            //get请求没有content-type
            contentType="json";
        }
        Integer code = WebCode.ERROR_500;
        String msg = "";
        Object data = null;
        if(ex instanceof UnauthorizedException){
            msg = "没有权限"+ex.getMessage();
        }else if(ex instanceof BusiException){
            code = ((BusiException) ex).getCode();
            msg = ex.getMessage();
            data = ((BusiException) ex).getData();
        }else{
            msg = ex.getMessage();
        }
        log.error("ExceptionHandler log");
        log.error(msg,ex);
        if(contentType!=null&&(contentType.toLowerCase().contains("json")||contentType.toLowerCase().contains("multipart/form-data"))){
            ModelAndView mv = new ModelAndView();
            ex.printStackTrace();
            FastJsonJsonView view = new FastJsonJsonView();
            Map<String, Object> attributes = new HashMap<String, Object>();
            //TODO 根据异常单独处理 如权限、orm 、业务等
            attributes.put("code", code);
            attributes.put("desc", msg);
            if(data!=null){
                attributes.put("data",data);
            }
            view.setAttributesMap(attributes);
            mv.setView(view);
            return mv;
        }else{
            return new ModelAndView("sys/error_500");
        }
    }   
  
}  