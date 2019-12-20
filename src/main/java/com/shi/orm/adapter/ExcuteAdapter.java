package com.shi.orm.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcuteAdapter {

    @Autowired
    private AdapterMapper adapterMapper;

    public ExcuteAdapter() {
    }

    public int executeUpdate(String sql, Map<String,Object> values){
        Map<String, Object> req = new HashMap();
        req.put("sql", sql);
        req.put("resultType", Integer.class);
        if (values != null) {
            req.putAll(values);
        }
        return adapterMapper.executeUpdate(req);
    }

    public <T> List<T> executeQuery(String sql, Map<String,Object> values, Class<T> clz){
        Map<String, Object> req = new HashMap();
        req.put("sql", sql);
        req.put("resultType", clz);
        if (values != null) {
            req.putAll(values);
        }

        return adapterMapper.executeQuery(req);
    }



}
