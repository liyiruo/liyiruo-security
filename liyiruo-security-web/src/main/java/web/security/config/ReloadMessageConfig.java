package web.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 错误信息提示汉化版
 */
@Configuration
public class ReloadMessageConfig {
    @Bean
    public ReloadableResourceBundleMessageSource messageSource  (){
        ReloadableResourceBundleMessageSource source=new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:org/springframework/security/messages_zh_CN");
        return source;
    };
}
