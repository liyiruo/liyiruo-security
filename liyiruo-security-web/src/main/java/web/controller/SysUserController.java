package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 创建用户管理控制层
 * @author liyiruo
 */
@Controller
@RequestMapping(value = "/user")
public class SysUserController {
    private static final String HTML_PREFIX = "system/user/";

    @RequestMapping(value = {"", "/"})
    public String user() {
        return HTML_PREFIX.concat("user-list");
    }
}
