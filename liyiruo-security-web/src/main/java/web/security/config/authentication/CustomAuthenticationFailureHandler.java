package web.security.config.authentication;

import com.liyiruo.base.result.LiyiruoResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败后的处理器
 * @author liyiruo
 */
@Component("customAuthenticationFailureHandler")
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        LiyiruoResult result = LiyiruoResult.build(
                HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        httpServletResponse.getWriter().write(result.toJsonString());
    }
}
