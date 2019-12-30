package com.shi.shiro.service;


import cn.hutool.core.util.StrUtil;
import com.shi.orm.core.BaseDAO;
import com.shi.orm.core.condition.Condition;
import com.shi.shiro.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private BaseDAO baseDAO;


    public List<String> queryPermissionsByName(String user_name) {
        String sql="select distinct d.code" +
                " from \"User\" a" +
                " inner join \"UserRole\" b on a.id=b.user_id" +
                " inner join \"RolePermission\" c on b.role_id=c.role_id" +
                " INNER JOIN \"Permission\" d ON c.permission_id = d.id "+
                " where a.logincode=#{logincode} and coalesce(a.dr,0)=0 and coalesce(b.dr,0)=0 and  coalesce(c.dr,0)=0";
        Map<String,Object> params = new HashMap<>();
        params.put("logincode",user_name);
        return baseDAO.queryForList(sql,params,String.class);
    }

    public Map queryUserPermission(String userID) {
        String operatorName="";

        String sql="select distinct d.code" +
                " from \"UserRole\" a" +
                " inner join \"RolePermission\" c on a.role_id=c.role_id" +
                " INNER JOIN \"Permission\" d ON c.permission_id = d.id "+
                " where a.user_id=#{userID} and coalesce(a.dr,0)=0 and coalesce(c.dr,0)=0 and  coalesce(d.dr,0)=0";
        Map<String,Object> params = new HashMap<>();
        params.put("userID",userID);

        List<String> permissionList = baseDAO.queryForList(sql,params, String.class);
        UserVo userVo = baseDAO.queryValueByID(UserVo.class, userID);
        if (userVo != null) {
            operatorName = StrUtil.isEmpty(userVo.getLogin_code()) ? "" : userVo.getLogin_code();
            //expireTime = StrUtil.isEmpty(operatorVO.getExpire_time()) ? 0L : DateUtil.parse(operatorVO.getExpire_time()).getTime();
        }
        Map map = new HashMap();
        map.put("operatorName", operatorName);
        map.put("userPermission", permissionList);
        //map.put("expireTime", expireTime);
        return map;

    }

    public UserVo queryUserByLogincode(String login_code) {

        Condition cond=new Condition();
        cond.eq("login_code",login_code);
        UserVo userVo=baseDAO.queryOneValueByCond(UserVo.class,cond);
        return userVo;
    }

    public List<UserVo> queryList() {

       return baseDAO.queryForAllList(UserVo.class);
    }
}
