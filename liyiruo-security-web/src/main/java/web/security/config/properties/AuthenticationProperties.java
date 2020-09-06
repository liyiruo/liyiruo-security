package web.security.config.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AuthenticationProperties {
    // application.yml 没配置取默认值
    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/form";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};
    private LoginResponseType loginType = LoginResponseType.REDIRECT;
    private String imageCodeUrl = "/code/image";
    private String mobileCodeUrl = "/code/mobile";
    private String mobilePage = "/mobile/page";
    private Integer tokenValiditySeconds = 60*60*24*7;
}
