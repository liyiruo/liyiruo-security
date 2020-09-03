package web.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import web.security.authentication.mobile.SmsCodeSender;
import web.security.authentication.mobile.SmsSend;

@Configuration
public class SeurityConfigBean {
    //如果容器中没有短信服务提供接口，使用这个默认接口
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }

}
