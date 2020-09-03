package web.security.authentication.mobile;

/**
 * 发送短信的接口
 * @author liyiruo
 */
public interface SmsSend {
    boolean sendSms(String mobile, String code);
}
