package web.security.config.authentication;

import com.alibaba.fastjson.JSON;
import com.liyiruo.base.result.LiyiruoResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web.security.config.properties.LoginResponseType;
import web.security.config.properties.SecurityProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后的处理器
 *
 * @author liyiruo
 */
@Slf4j
@Component("customAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    SecurityProperties properties;
    //这个一块注释的代码，是处理成功后的一个处理逻辑
   /* @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        // 当认证成功后，响应 JSON 数据给前端
        LiyiruoResult result = LiyiruoResult.ok("认证成功");
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        httpServletResponse.getWriter().write(result.toJsonString());
    }*/

    //这一块的代码 是根据配置的响应类型 来确定响应的方式
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
//        super.onAuthenticationSuccess(request, response, authentication);


        log.info("properties===>{}",properties.getAuthention().getLoginType());
        //如果是json
        if (LoginResponseType.JSON.equals(properties.getAuthention().getLoginType())) {
            // 认证成功后，响应JSON字符串
            LiyiruoResult result = LiyiruoResult.ok("认证成功");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        }

        if (LoginResponseType.REDIRECT.equals(properties.getAuthention().getLoginType())) {
            //重定向到上次请求的地址上，引发跳转到认证页面的地址
            log.info("==>{}", JSON.toJSONString(authentication));
            super.onAuthenticationSuccess(request, response, authentication);
        }


    }
}
