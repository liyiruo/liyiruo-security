package web.security.config.authentication;

import com.liyiruo.base.result.LiyiruoResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后的处理器
 *
 * @author liyiruo
 */
@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        // 当认证成功后，响应 JSON 数据给前端
        LiyiruoResult result = LiyiruoResult.ok("认证成功");
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        httpServletResponse.getWriter().write(result.toJsonString());
    }
}
