package web.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

@Configuration
public class ReloadMessageConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource  (){

        ReloadableResourceBundleMessageSource source=new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:org/springframework/security/messages_zh_CN");
        return source;
    };

}
