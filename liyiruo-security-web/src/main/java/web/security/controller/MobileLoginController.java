package web.security.controller;

import com.liyiruo.base.result.LiyiruoResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.security.authentication.mobile.SmsSend;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liyiruo
 */
@Slf4j
@Controller
public class MobileLoginController {
    public static final String SESSION_KEY = "SESSION_KEY_MOBILE_CODE";
    //进入手机登录页面
    @RequestMapping("/mobile/page")
    public String toMobilePage() {
        return "login-mobile";
    }


    @Autowired
    private SmsSend smsSend;

    //发送验证码的功能
    @ResponseBody
    @RequestMapping("code/mobile")
    public LiyiruoResult smsCode(HttpServletRequest request) {
        String code = RandomStringUtils.randomNumeric(4);
        log.info("code===>{}",code);
        request.getSession().setAttribute(SESSION_KEY, code);
        String mobile = request.getParameter("mobile");
        smsSend.sendSms(mobile, code);
        return LiyiruoResult.ok();
    }
}
