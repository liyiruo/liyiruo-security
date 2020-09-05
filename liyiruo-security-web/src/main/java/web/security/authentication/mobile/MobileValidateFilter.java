package web.security.authentication.mobile;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import web.security.authentication.code.ValidateCodeException;
import web.security.config.authentication.CustomAuthenticationFailureHandler;
import web.security.controller.MobileLoginController;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 手机验证码验证的过滤器
 *
 * @author liyiruo
 */
@Component
@Slf4j
public class MobileValidateFilter extends OncePerRequestFilter {
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {


        log.info("httpServletRequest.getRequestURI()=>{}",httpServletRequest.getRequestURI());
        //什么情况下去校验手机验证码
        if ("/mobile/form".equals(httpServletRequest.getRequestURI())
                && "post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            try {
                validate(httpServletRequest);
            } catch (AuthenticationException e) {
                customAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                //一定要返回
                return;
            }
        }
        //1。非手机验证码登录，则直接放行
        //2。手机验证通过后放行
        //3。手机验证不通过时，不走这行代码
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    public void validate(HttpServletRequest request) throws AuthenticationException {
        //获取session里的code
        String code = (String) request.getSession().getAttribute(MobileLoginController.SESSION_KEY);
        //获取页面传来的验证码
        String inputCode = request.getParameter("code");
        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (!inputCode.equalsIgnoreCase(code)) {//inputCode不能为空
            throw new ValidateCodeException("验证码输入错误");
        }
    }

}
