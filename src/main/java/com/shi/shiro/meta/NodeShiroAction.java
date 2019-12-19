package com.shi.shiro.meta;


import com.shi.config.stereotype.NodeActionMapping;
import com.shi.config.stereotype.SysNodeAction;

@SysNodeAction
public interface NodeShiroAction {

    @NodeActionMapping(actionName = "查询")
    String SYS_USER_QUERY=NodeShiro.SYS_USER+":query";

    @NodeActionMapping(actionName = "新增")
    String SYS_USER_ADD=NodeShiro.SYS_USER+":add";

    @NodeActionMapping(actionName = "编辑")
    String SYS_USER_UPDATE=NodeShiro.SYS_USER+":update";

    @NodeActionMapping(actionName = "删除")
    String SYS_USER_DELETE=NodeShiro.SYS_USER+":delete";



    @NodeActionMapping(actionName = "查询")
    String SYS_ROLE_QUERY=NodeShiro.SYS_ROLE+":query";

    @NodeActionMapping(actionName = "新增")
    String SYS_ROLE_ADD=NodeShiro.SYS_ROLE+":add";

    @NodeActionMapping(actionName = "编辑")
    String SYS_ROLE_UPDATE=NodeShiro.SYS_ROLE+":update";

    @NodeActionMapping(actionName = "删除")
    String SYS_ROLE_DELETE=NodeShiro.SYS_ROLE+":delete";


    @NodeActionMapping(actionName = "查询")
    String SYS_PERMISSION_QUERY=NodeShiro.SYS_PERMISSION+":query";




}
