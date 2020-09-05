package web.security.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import web.security.config.authentication.CustomAuthenticationFailureHandler;
import web.security.config.authentication.CustomAuthenticationSuccessHandler;

/**
 * 将认证手机短信认证相关组建组合起来
 * @author liyiruo
 */
@Component
public class MobileAuthenticationConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    UserDetailsService mobileUserDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //创建手机号过滤器实例
        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        // 接收 AuthenticationManager 认证管理器
        mobileAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //增加记住我功能
        mobileAuthenticationFilter.setRememberMeServices(http.getSharedObject(RememberMeServices.class));
        //采用哪个成功失败处理器
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        // 为 Provider 指定明确 的mobileUserDetailsService 来查询用户信息
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService);
        // 将 provider 绑定到 HttpSecurity 上面，
        // 并且将 手机认证加到 用户名密码认证之后
        http
                .authenticationProvider(provider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
