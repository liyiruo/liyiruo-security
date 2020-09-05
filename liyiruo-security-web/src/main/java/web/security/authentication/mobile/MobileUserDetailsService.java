package web.security.authentication.mobile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 通过手机号查询用户信息
 *
 * @author liyiruo
 */
@Slf4j
@Component("mobileUserDetailsService")
public class MobileUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        log.info("查询到的手机号是「」{}", mobile);
        // 1. 通过手机号查询用户信息
        // 2. 如果有此用户，则查询用户权限
        // 3. 封装用户信息
        User user = new User(mobile, "",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
        return user;
    }
}
