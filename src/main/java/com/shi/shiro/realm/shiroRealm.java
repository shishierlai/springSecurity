package com.shi.shiro.realm;

import com.shi.config.web.DevException;
import com.shi.shiro.service.UserService;
import com.shi.shiro.utils.PasswordHelpter;
import com.shi.shiro.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class shiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(shiroRealm.class);

    @Autowired
    private UserService userService;

    /*
    * 授权
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("======用户授权认证======");

        String user_name=principalCollection.getPrimaryPrincipal().toString();
        SimpleAuthorizationInfo authenticationInfo=new SimpleAuthorizationInfo();
        Set<String> roleNames=new HashSet<>();
        if(user_name.equals("admin")){
            roleNames.add(user_name);
            authenticationInfo.setRoles(roleNames);
        }
        Set<String> permissionsNames=new HashSet<>();
        List<String> permissions=userService.queryPermissionsByName(user_name);
        if(permissions!=null){
            permissionsNames.addAll(permissions);
        }
        authenticationInfo.setStringPermissions(permissionsNames);

        return authenticationInfo;
    }

    /*
    * shiro认证登录
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("======用户登陆认证======");
        String login_code= (String) authenticationToken.getPrincipal();
        UserVo userVo=userService.queryUserByLogincode(login_code);


        if(userVo!=null){
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            String origin_password=new String((char[]) token.getCredentials());
            logger.info("====origin-password="+origin_password);
            String salt_password= PasswordHelpter.encryptPassword(origin_password,userVo.getSalt());
            logger.info("====salt_password="+salt_password);
            return new SimpleAuthenticationInfo(userVo.getLogin_code(),userVo.getPassword(),getName());
        }

        return null;
    }



    public String getName() {
        return "shiroRealm";
    }
}
