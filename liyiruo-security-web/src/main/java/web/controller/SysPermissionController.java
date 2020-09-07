package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/permission")
public class SysPermissionController {
    private static final String HTML_PREFIX = "system/permission/";

    @RequestMapping(value = {"", "/"})
    public String permission() {
        return HTML_PREFIX.concat("permission-list");
    }
}
