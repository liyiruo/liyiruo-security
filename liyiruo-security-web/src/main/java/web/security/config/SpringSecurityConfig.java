package web.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器:
     * 1、认证信息提供方式(用户名、密码、当前用户的资源权限)
     * 2、可采用内存存储方式，也可能采用数据库方式等
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String password = passwordEncoder().encode("123");
        log.info("加密后的密码=====>{}",password);
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password(password)
                .authorities("admin");
    }

    /**
     * 资源权限配置(过滤器链):
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式:httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * * 5、自定义 spring security 过滤器
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()//认证请求
                .anyRequest()
                .authenticated();//所有进入应用的http请求都要进行认证
    }
}
