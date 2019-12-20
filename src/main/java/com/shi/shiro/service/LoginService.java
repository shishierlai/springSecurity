package com.shi.shiro.service;

import com.shi.orm.core.BaseDAO;
import com.shi.shiro.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private BaseDAO baseDAO;

    public UserVo getUser(String id) {
       return baseDAO.queryValueByID(UserVo.class,id);
    }
}
