package com.shi.config.componnet;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shi.config.stereotype.JsonArg;
import com.shi.config.stereotype.ParseObject;
import com.shi.orm.core.stereotype.ValueObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;


public class ArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String JSONBODYATTRIBUTE = "JSON_REQUEST_BODY";

    public ArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonArg.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String body = this.getRequestBody(webRequest);
        if (body != null && body.length() != 0) {
            String arg = (parameter.getParameterAnnotation(JsonArg.class)).value();
            if (StringUtils.isEmpty(arg)) {
                arg = parameter.getParameterName();
            }

            JSONObject obj = JSON.parseObject(body);
            if (!obj.containsKey(arg)) {
                return null;
            } else {
                this.superTrim(obj);
                Class bean = ((JsonArg)parameter.getParameterAnnotation(JsonArg.class)).bean();
                if (!ValueObject.class.isAssignableFrom(bean) && !ParseObject.class.isAssignableFrom(bean)) {
                    if (parameter.getParameterType().getName().equals(BigDecimal.class.getName())) {
                        Object value = obj.get(arg);
                        return !(value instanceof BigDecimal) ? new BigDecimal(value.toString()) : value;
                    } else {
                        return obj.get(arg);
                    }
                } else if (parameter.getParameterType().isAssignableFrom(List.class)) {
                    try {
                        return JSON.parseArray(obj.get(arg).toString(), bean);
                    } catch (Exception var10) {
                        var10.printStackTrace();
                        throw var10;
                    }
                } else {
                    return JSON.parseObject(obj.getJSONObject(arg).toJSONString(), bean);
                }
            }
        } else {
            return null;
        }
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = (HttpServletRequest)webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = (String)servletRequest.getAttribute("JSON_REQUEST_BODY");
        if (jsonBody == null) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new InputStreamReader(servletRequest.getInputStream(), "UTF-8"));
                String line = null;

                while((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                jsonBody = buffer.toString();
                servletRequest.setAttribute("JSON_REQUEST_BODY", jsonBody);
                String var7 = jsonBody;
                return var7;
            } catch (IOException var16) {
                throw new RuntimeException(var16);
            } finally {
                if (null != reader) {
                    try {
                        reader.close();
                    } catch (IOException var15) {
                        var15.printStackTrace();
                    }
                }

            }
        } else {
            return jsonBody;
        }
    }

    private void superTrim(JSONObject obj) {
        Iterator var2 = obj.keySet().iterator();

        while(true) {
            while(var2.hasNext()) {
                String key = (String)var2.next();
                Object value = obj.get(key);
                if (value instanceof String) {
                    String str = (String)value;
                    obj.put(key, str.trim());
                } else if (value instanceof JSONObject) {
                    this.superTrim((JSONObject)value);
                } else if (value instanceof JSONArray) {
                    JSONArray arr = (JSONArray)value;

                    for(int i = 0; i < arr.size(); ++i) {
                        Object itemValue = arr.get(i);
                        if (itemValue instanceof JSONObject) {
                            this.superTrim((JSONObject)itemValue);
                        } else if (value instanceof String) {
                            String str = (String)value;
                            arr.set(i, str.trim());
                        }
                    }
                }
            }

            return;
        }
    }
}

