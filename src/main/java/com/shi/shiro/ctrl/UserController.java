package com.shi.shiro.ctrl;


import com.shi.config.web.WebUtils;
import com.shi.shiro.meta.NodeShiroAction;
import com.shi.shiro.service.UserService;
import com.shi.shiro.utils.SecurityUserContext;
import com.shi.shiro.vo.UserVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;



    /**
     * 返回user列表方法
     *
     * @param
     * @return
     */
    @RequiresRoles("admin")
    @RequiresPermissions(NodeShiroAction.SYS_ROLE_QUERY)
    @RequestMapping(value = "/user_list", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> list() {
        List<UserVo> list = userService.queryList();
        //渲染列表数据
        return WebUtils.responseRender(list);
    }





    @RequestMapping(value = "/menu")
    public @ResponseBody
    Map<String, Object> roleMenu2() throws Exception {
        Map map = userService.queryUserPermission(SecurityUserContext.getUserID());
        return WebUtils.responseRender(map);
    }

}
