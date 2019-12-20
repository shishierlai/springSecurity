package com.shi.shiro.service;


import com.shi.orm.core.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BaseDAO baseDAO;
}
