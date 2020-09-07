package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 创建角色管理控制层
 * @author liyiruo
 */
@Controller
@RequestMapping("/role")
public class SysRoleController {
    private static final String HTML_PREFIX = "system/role/";

    @RequestMapping(value = {"/", ""})
    public String role() {
        return HTML_PREFIX.concat("role-list");
    }
}
