package web.security.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 手机认证过滤器
 */
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String mobileParameter = "mobile";
    private boolean postOnly = true;

    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher("/mobile/form", "POST"));
    }

    protected MobileAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected MobileAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException {


        //如果请求方式不是支持的请求方式，抛出异常
        if (postOnly && !request.getMethod().equalsIgnoreCase("POST")) {
            throw new AuthenticationServiceException("请求方式不被支持：" + request.getMethod());
        }

        String mobile = obtainMobile(request);
        mobile = mobile == null ? "" : mobile;
        mobile = mobile.trim();
        MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    //此处的注解 解决了AuthenticationManager 未找到的错误
    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    public void setMobileParameter(String mobileParameter) {
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getMobileParameter() {
        return mobileParameter;
    }
}
