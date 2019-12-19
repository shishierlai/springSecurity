package com.shi.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class shiroRealm extends AuthorizingRealm {


    /*
    * 授权
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /*
    * 登录
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username= (String) authenticationToken.getPrincipal();
        String passworad= (String) authenticationToken.getCredentials();
        if(!"admin".equals(username)){
            return null;
        }

        return new SimpleAuthenticationInfo(username,passworad,getName());
    }


    public String getName() {
        return "shiroRealm";
    }
}
