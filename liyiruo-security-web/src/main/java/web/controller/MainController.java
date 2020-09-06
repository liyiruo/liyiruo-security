package web.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class MainController {

    @RequestMapping({"/", "", "index"})
    public String index(Map<String, Object> map) {
        // 方式1: 获取登录用户信息 Object principal =
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            map.put("username", userDetails.getUsername());
        }
        return "index";
    }

    @RequestMapping("/user/info")
    @ResponseBody
    public Object userInfo(Authentication authentication) {
        return authentication.getPrincipal();
    }


    @RequestMapping("/user/info2")
    @ResponseBody
    public Object userInfo2(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }


    @RequestMapping("/login/page")
    public String toLogin() {
        return "login";
    }

}
