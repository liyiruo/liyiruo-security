package web.security.authentication.mobile;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送短信接口的实现类
 * @author liyiruo
 */
@Slf4j
public class SmsCodeSender implements SmsSend {
    @Override
    public boolean sendSms(String mobile, String code) {
        String sendContent = String.format("你好，验证码%s，请勿泄露他人。", code);
        log.info("向手机号为{},发送的短信内容为{}",mobile,sendContent);
        return true;
    }
}
