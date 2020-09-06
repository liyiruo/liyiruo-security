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
        log.info("页面传来的手机号是{}", mobile);
        // 1. 通过手机号查询用户信息
        String name = "zhangsan";
        // 2. 如果有此用户，则查询用户权限
        //这里封装用户信息的时候 应该封装的是用户 而不是手机号
        //此处应该是根据手机号查询到一个用户名
        // 3. 封装用户信息
        User user = new User(name, "",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
        log.info("user={}",user);
        return user;
    }
}
