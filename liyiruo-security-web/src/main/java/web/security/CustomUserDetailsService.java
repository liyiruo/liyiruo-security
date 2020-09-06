package web.security;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 这个类的功能是从数据库查询用户信息
 * 在@{link SpringSecurityConfig} 里调用新的认证方式
 *
 * @author liyiruo
 */
@Component("customUserDetailsService")
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("当前登录人的登录名为：{}", username);
        //从数据库查询出信息，根据用户名
        if (!"liyiruo".equalsIgnoreCase(username)) {
            throw new UsernameNotFoundException("用户名或密码不存在");
        }
        // 如果有此用户信息, 假设数据库查询到的用户密码是 1234
        String password = passwordEncoder.encode("1234");
        //查询用户权限
        //3.封装用户信息
        UserDetails userDetails = new User("liyishi", password, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
        log.info("userDetails.toString()==>{}",userDetails.toString());
        return userDetails;
    }
}
