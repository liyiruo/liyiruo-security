package web.security.config.authentication;

import com.liyiruo.base.result.LiyiruoResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import web.security.config.properties.LoginResponseType;
import web.security.config.properties.SecurityProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败后的处理器
 *
 * @author liyiruo
 */
@Slf4j
@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    SecurityProperties properties;

   /* @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        LiyiruoResult result = LiyiruoResult.build(
                HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        httpServletResponse.getWriter().write(result.toJsonString());
    }*/

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("loginType==>{}", properties.getAuthention().getLoginType());
        if (properties.getAuthention().getLoginType().equals(LoginResponseType.JSON)) {
            LiyiruoResult result = LiyiruoResult.build(
                    HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        }

        if (properties.getAuthention().getLoginType().equals(LoginResponseType.REDIRECT)) {

            /*super.setDefaultFailureUrl(properties.getAuthention().getLoginPage().concat("?error"));
            super.onAuthenticationFailure(request, response, exception);
*/
            //获取上次来是的路径 增加这段代码前，手机登录认证失败后
            //会回到密码登录页面；增加这段代码 手机认证失败则回到手机认证页面
            String refer = request.getHeader("Referer");
            log.info("refer==>{}", refer);
            String lastUrl = StringUtils.substringBefore(refer, "?");
            super.setDefaultFailureUrl(lastUrl.concat("?error"));
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
