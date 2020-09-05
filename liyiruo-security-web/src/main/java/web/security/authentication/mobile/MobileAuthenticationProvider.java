package web.security.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 手机认证提供者
 * 提供给底层 ProviderManager 使用
 * @author liyiruo
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        //获取用户输入的手机号
        String mobile = (String) mobileAuthenticationToken.getPrincipal();
        //根据手机号查询用户信息
        UserDetails user = userDetailsService.loadUserByUsername(mobile);
        //如果没有查到，抛出异常
        if (user == null) {
            throw new AuthenticationServiceException("手机号未注册");
        }
        //如果查询到信息 就重新构建MobileAuthenticationToken实例 -用户信息 -用户权限
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(mobile, user.getAuthorities());
        authenticationToken.setDetails(user);
        return authenticationToken;
    }

    /**
     * 通过此方法,来判断 采用哪一个 AuthenticationProvider
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
