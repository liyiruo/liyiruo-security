package web.security.authentication.session;

import com.liyiruo.base.result.LiyiruoResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session 认证失效策略
 * @author liyiruo
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
         //将浏览器的session清除
        cancelCookie(httpServletRequest,httpServletResponse);
        //返回错误提示信息
        LiyiruoResult result = LiyiruoResult.build(HttpStatus.UNAUTHORIZED.value(), "登录以超时，请重新登录");
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        httpServletResponse.getWriter().write(result.toJsonString());
    }

    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String path = request.getContextPath();
        return path.length() > 0 ? path : "/";
    }
}
