package com.shi.shiro.service;

import com.shi.orm.core.BaseDAO;
import com.shi.orm.utils.GenerateID;
import com.shi.shiro.utils.PasswordHelpter;
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

    public void register(String user_name, String password) {
        //获取salt  用于加密
        String salt= GenerateID.getInstance().generateShortUuid();
        String encrypyPsd= PasswordHelpter.encryptPassword(password,salt);
        UserVo user=new UserVo();
        user.setUser_name(user_name);
        user.setLogin_code(user_name);
        user.setSalt(salt);
        user.setPassword(encrypyPsd);
        addOrUpdateUser(user);

    }

    private void addOrUpdateUser(UserVo user) {
        baseDAO.insertOrUpdateValueObject(user);
    }
}
