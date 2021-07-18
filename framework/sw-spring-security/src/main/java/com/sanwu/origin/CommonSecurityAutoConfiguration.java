package com.sanwu.origin;

import com.sanwu.origin.service.ISignInAuthenticationService;
import com.sanwu.origin.service.ITokenAuthenticationService;
import com.sanwu.origin.service.impl.DefaultSignInAuthenticationServiceImpl;
import com.sanwu.origin.service.impl.DefaultTokenAuthenticationService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: CommonSecurityAutoConfiguration
 * @Author: sanwu
 * @Date: 2021/7/15 0:39
 */
@ComponentScan(basePackages = {
        "com.sanwu.origin"
})
@Configuration
public class CommonSecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ISignInAuthenticationService signInAuthenticationService(){
        return new DefaultSignInAuthenticationServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ITokenAuthenticationService tokenAuthenticationService(){
        return new DefaultTokenAuthenticationService();
    }
}
