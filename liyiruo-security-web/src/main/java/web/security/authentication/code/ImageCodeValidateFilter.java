package web.security.authentication.code;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import web.security.config.authentication.CustomAuthenticationFailureHandler;
import web.security.config.properties.SecurityProperties;
import web.security.controller.CustomLoginController;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liyiruo
 */
@Slf4j
@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    SecurityProperties securityProperties;
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. 如果是post方式 的登录请求，则校验验证的
        if (securityProperties.getAuthention().getLoginProcessingUrl().equals(request.getRequestURI()) && request.getMethod().equalsIgnoreCase("POST")) {
            try {
                validate(request);
            } catch (ValidateCodeException e) {
              //验证码异常回显一直有问题，问题是在这里吗？
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        //放行请求
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) throws AuthenticationException {
        //获取session存的验证码
        String sessionCode = (String) request.getSession().getAttribute(CustomLoginController.SESSION_KEY);
        String inputCode = request.getParameter("code");
        log.info("sessionCode==>{}",sessionCode);
        log.info("inputCode==>{}",inputCode);
        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (!sessionCode.equals(inputCode)) {
            throw new ValidateCodeException("验证码输入错误");
        }
    }
}
