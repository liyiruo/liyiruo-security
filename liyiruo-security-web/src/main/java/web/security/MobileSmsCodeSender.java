package web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import web.security.authentication.mobile.SmsSend;

/**
 * @author liyiruo
 */
@Component
@Slf4j
public class MobileSmsCodeSender implements SmsSend {
    @Override
    public boolean sendSms(String mobile, String code) {
        log.info("Web应用新的短信验证码接口---向手机号" + mobile + "发送了验证码为:" + code);
        return false;
    }
}
