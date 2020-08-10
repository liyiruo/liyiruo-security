package web.security.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "liyiruo.security")
public class SecurityProperties {

    private AuthenticationProperties authention;

    public AuthenticationProperties getAuthention() {
        return authention;
    }

    public void setAuthention(AuthenticationProperties authention) {
        this.authention = authention;
    }
}

