package web.security.config.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 从配置文件里获取到以"liyiruo.security"为前缀的数据 给AuthenticationProperties 赋值
 *
 * @author liyiruo
 */
@Component
@ConfigurationProperties(prefix = "liyiruo.security")
public class SecurityProperties {
    @Autowired
    private AuthenticationProperties authention;

    public AuthenticationProperties getAuthention() {
        return authention;
    }

    public void setAuthention(AuthenticationProperties authention) {
        this.authention = authention;
    }
}

