package com.shi.shiro.meta;


import com.shi.config.stereotype.node.Module;
import com.shi.config.stereotype.node.NodeMapping;
import com.shi.config.stereotype.node.SysNode;

@SysNode
public interface NodeShiro {

    @Module(moduleName = "系统设置")
    String MODULE_SYS="sys";

    @NodeMapping(nodeName = "用户管理",role = "admin")
    String SYS_USER=MODULE_SYS+":user";

    @NodeMapping(nodeName = "角色管理",role = "admin")
    String SYS_ROLE=MODULE_SYS+":role";

    @NodeMapping(nodeName = "权限管理",role = "admin")
    String SYS_PERMISSION=MODULE_SYS+ ":permission";


}
