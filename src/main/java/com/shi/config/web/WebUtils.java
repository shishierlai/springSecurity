package com.shi.config.web;

import com.alibaba.fastjson.annotation.JSONField;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import static javax.xml.bind.JAXBIntrospector.getValue;

/*
* create by shishierlai
* func:统一格式返回数据
* */
public class WebUtils {

    /*
    * 不带数据返回
    * */
    public static Map<String,Object> responseRender(){

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", WebCode.SUCCESS);
        map.put("desc","");
        try {
            return transToMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DevException("bean转map错误："+e.getMessage());
        }
    }

    /*
    * 带数据返回
    * */
    public static Map<String,Object> responseRender(Object obj){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", WebCode.SUCCESS);
        map.put("desc","");
        map.put("data",obj);
        //Map<String,Object> re = null;
        try {
            //转成map返回
            return transToMap(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DevException("bean转map错误："+e.getMessage());
        }
    }

    private static Map<String,Object> transToMap(Map<String,Object> model) throws Exception {
        Map<String,Object> re = new HashMap<String,Object>();
        for(Map.Entry<String,Object> entry:model.entrySet()){
            Object value = entry.getValue();
            if(value==null){
                continue;
            }
            Class className = value.getClass();
            if (className.equals(Integer.class) ||
                    className.equals(String.class) ||
                    className.equals(Long.class) ||
                    className.equals(BigDecimal.class) ||
                    className.equals(Double.class) ||
                    className.equals(Boolean.class)) {
                re.put(entry.getKey(), getValue(value));
            }else if(value.getClass().isArray()){ //Array类型

                re.put(entry.getKey(), transToMap((Object[]) entry.getValue()));
            }else if(value instanceof  Map) { //Map类型
                re.put(entry.getKey(), transToMap((Map)entry.getValue()));
            }else{
                re.put(entry.getKey(),transBean2Map(value));
            }

        }
        return re;
    }

    private static List<Object> transToMap(Object[] arr) throws Exception {
        List<Object> list = new ArrayList<Object>();
        if(arr==null){
            return list;
        }
        for(Object value:arr){
            if(isWrapClass(value.getClass())){
                list.add(value);
            } else{
                list.add(transBean2Map(value));
            }
        }
        return list;
    }

    private static List<Object> transToMap(Collection coll) throws Exception {
        List<Object> list = new ArrayList<Object>();
        if(coll==null){
            return list;
        }
        Iterator it= coll.iterator();
        while (it.hasNext()){
            Object value = it.next();
            if(isWrapClass(value.getClass())){
                list.add(value);
            } else if(value instanceof  Map) {
                list.add(transToMap((Map)value));
            }else if(value.getClass().isArray()){

                list.add(transToMap((Object[]) value));
            }else{
                list.add(transBean2Map(value));
            }
        }
        return list;
    }

    public static Map<String, Object> transBean2Map(Object obj) throws Exception {
        if(obj == null){
            return null;
        }
        Map<String, Object> re = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {


            String key = property.getName();
            Class type =property.getPropertyType();
            // 过滤class属性
            if(key.equals("class")) {
                continue;
            }
            // 没有set方法的一般是直接返回配置的，不转换
            if(property.getWriteMethod()==null){
                continue;
            }
            // 得到property对应的getter方法
            Method getter = property.getReadMethod();
            JSONField jsonConfig = getter.getAnnotation(JSONField.class);
            if(jsonConfig!=null&&!jsonConfig.serialize()) {

                re.put(key,"");
            }else{
                Object value = getter.invoke(obj);
                if (List.class.isAssignableFrom(type)) {
                    re.put(key, transToMap((Collection) value));
                } else {
                    re.put(key, value == null ? "" : value);
                }
            }

        }
        return re;

    }

    private static boolean isWrapClass(Class className) {
        if(Map.class.isAssignableFrom(className)){
            return true;
        }
        if (className.equals(String.class) ||
                className.equals(Integer.class) ||
                className.equals(BigDecimal.class) ||
                className.equals(Byte.class) ||
                className.equals(Long.class) ||
                className.equals(Double.class) ||
                className.equals(Float.class) ||
                className.equals(Character.class) ||
                className.equals(Short.class) ||
                className.equals(Boolean.class)
                ){
            return true;
        }
        return false;
    }
}
