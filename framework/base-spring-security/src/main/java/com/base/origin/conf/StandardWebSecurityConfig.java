package com.base.origin.conf;

import com.base.origin.filter.TokenAuthenticationFilter;
import com.base.origin.handler.BaseAccessDeniedHandler;
import com.base.origin.handler.SignInAuthenticationFailHandler;
import com.base.origin.provider.SignInAuthenticationProvider;
import com.base.origin.handler.BaseAuthenticationEntryPoint;
import com.base.origin.provider.TokenAuthenticationProvider;
import com.base.origin.handler.SignInAuthenticationSuccessHandler;
import com.base.origin.filter.SignInAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: StandardWebSecurityConfiguration
 * @Author: sanwu
 * @Date: 2021/7/8 23:49
 */
@Configuration
@EnableWebSecurity
public class StandardWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PermissionProperty permissionProperty;

    @Autowired
    private BasicSecurityProperty basicSecurityProperty;

    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;

    @Autowired
    private SignInAuthenticationProvider signInAuthenticationProvider;

    @Autowired
    private BaseAuthenticationEntryPoint baseAuthenticationEntryPoint;

    @Autowired
    private SignInAuthenticationSuccessHandler signInAuthenticationSuccessHandler;

    @Autowired
    private SignInAuthenticationFailHandler signInAuthenticationFailHandler;

    @Autowired
    private BaseAccessDeniedHandler baseAccessDeniedHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().accessDeniedHandler(baseAccessDeniedHandler).and()
                .exceptionHandling().authenticationEntryPoint(baseAuthenticationEntryPoint).and()
                .authorizeRequests().antMatchers(permissionProperty.getPermitAllList()).permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().and()
                .addFilterAt(signInAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(baseTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
        Map<String, String> resourcePermissions = new HashMap<>();
        for (Map.Entry<String, String> entry : resourcePermissions.entrySet()) {
            urlRegistry = urlRegistry.antMatchers(entry.getKey()).hasAnyAuthority(entry.getValue());
        }
        urlRegistry.antMatchers("/api/v1/resource").hasAnyAuthority();

        http.cors().configurationSource(corsConfigurationSource())
                .and().headers().frameOptions().deny().contentTypeOptions()
                .and().xssProtection()
                .and().cacheControl()
                .and().httpStrictTransportSecurity();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider);
        auth.authenticationProvider(signInAuthenticationProvider);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin","Content-Type", "Accept", "Authorization"));
        corsConfiguration.setExposedHeaders(Collections.singletonList("Content-Type"));
//        corsConfiguration.setAllowedOrigins();
        corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.OPTIONS.name(), HttpMethod.GET.name(), HttpMethod.POST.name()));
        UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * 只匹配POST的登陆请求
     * @return
     * @throws Exception
     */
    private SignInAuthenticationFilter signInAuthenticationFilter() throws Exception {
        AntPathRequestMatcher loginMatcher = new AntPathRequestMatcher("/login", HttpMethod.POST.name());
        SignInAuthenticationFilter tokenStandardAuthenticationFilter = new SignInAuthenticationFilter(loginMatcher);
        tokenStandardAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        tokenStandardAuthenticationFilter.setAuthenticationSuccessHandler(signInAuthenticationSuccessHandler);
        tokenStandardAuthenticationFilter.setAuthenticationFailureHandler(signInAuthenticationFailHandler);
        return tokenStandardAuthenticationFilter;
    }

    /**
     * 匹配不是permitAll的所有请求
     * @return
     * @throws Exception
     */
    private TokenAuthenticationFilter baseTokenAuthenticationFilter() throws Exception {
        Set<AntPathRequestMatcher> permitAllMatchers = Arrays.stream(permissionProperty.getPermitAllList())
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toSet());
        RequestMatcher requestMatcher = request -> permitAllMatchers.stream().noneMatch(matcher-> matcher.matches(request));
        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter(requestMatcher, basicSecurityProperty.getHeader());
        tokenAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        tokenAuthenticationFilter.setAuthenticationSuccessHandler(signInAuthenticationSuccessHandler);
        return tokenAuthenticationFilter;
    }


    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
