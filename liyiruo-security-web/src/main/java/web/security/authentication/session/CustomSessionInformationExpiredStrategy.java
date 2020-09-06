package web.security.authentication.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import web.security.config.authentication.CustomAuthenticationFailureHandler;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * session存在达到最大值时，
 *
 * @author liyiruo
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {

        UserDetails userDetails = (UserDetails) event.getSessionInformation().getPrincipal();

        AuthenticationException exception = new AuthenticationServiceException(String.format("[%s用户在另外一台电脑登录，您已被迫下线]", userDetails.getUsername()));
        event.getRequest().setAttribute("toAuthentication", true);
        try {
            customAuthenticationFailureHandler.onAuthenticationFailure(event.getRequest(), event.getResponse(), exception);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
