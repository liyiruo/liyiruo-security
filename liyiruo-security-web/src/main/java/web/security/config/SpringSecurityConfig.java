package web.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import web.security.config.properties.SecurityProperties;

@EnableWebSecurity
@Configuration
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 注入的这个securityProperties，
     * 是将原本写死的数据配置信息，写在application.yml配置文件里。
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 之前登录信息是写死在内存中的，注入这个类，
     * 返回的是从数据库查询到的信息
     *
     * @return
     */
    @Autowired
    private UserDetailsService customUserDetailsService;
    /**
     *注入认证成功后的处理器
     */
    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;
    /**
     *注入认证失败后的处理器
     */
    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器:
     * 1、认证信息提供方式(用户名、密码、当前用户的资源权限)
     * 2、可采用内存存储方式，也可能采用数据库方式等
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //采用内存存储的方式
        /*
        String password = passwordEncoder().encode("123");
        log.info("加密后的密码=====>{}", password);
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password(password)
                .authorities("admin");
        */

        //采用数据库方式
        auth.userDetailsService(customUserDetailsService);

    }

    /**
     * 资源权限配置(过滤器链):
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式:httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 使用form表单登录，
         * 设置表单所在的页面 loginPage
         * 设置页面的上用户名和密码 name 属性  usernameParameter  passwordParameter
         * 设置登录后的进入的页面 loginProcessingUrl
         */

        //httpBasic 浏览器弹出一个认证框的方式认证
//        http.httpBasic()
        //formLogin 表单认证
        http.formLogin()
                .loginPage(securityProperties.getAuthention().getLoginPage())//指定登录页面 URL 需要在controller里
                .loginProcessingUrl(securityProperties.getAuthention().getLoginProcessingUrl())//认证成功后进入的页面 默认是/login
                .usernameParameter(securityProperties.getAuthention().getUsernameParameter())// 默认用户名的属性名是 username
                .passwordParameter(securityProperties.getAuthention().getPasswordParameter())// 默认密码的属性名是 password

                .successHandler(customAuthenticationSuccessHandler)//认证成功后的处理器
                .failureHandler(customAuthenticationFailureHandler)//认证失败后的处理器

                .and()
                .authorizeRequests()//认证请求
                .antMatchers(securityProperties.getAuthention().getLoginPage())
                .permitAll()
                .anyRequest()
                .authenticated()//所有进入应用的http请求都要进行认证
                .and().csrf().disable() //关闭 CSRF 攻击
        ;
    }


    /**
     * 释放静态资源
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(securityProperties.getAuthention().getStaticPaths());
    }
}
