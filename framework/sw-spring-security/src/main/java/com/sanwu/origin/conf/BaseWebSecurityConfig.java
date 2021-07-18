package com.sanwu.origin.conf;

import com.sanwu.origin.authentication.token.BaseTokenFilter;
import com.sanwu.origin.handler.BaseAccessDeniedHandler;
import com.sanwu.origin.authentication.handler.SignInAuthenticationFailHandler;
import com.sanwu.origin.authentication.provider.SignInAuthenticationProvider;
import com.sanwu.origin.handler.BaseAuthenticationEntryPoint;
import com.sanwu.origin.authentication.provider.TokenAuthenticationProvider;
import com.sanwu.origin.authentication.handler.SignInAuthenticationSuccessHandler;
import com.sanwu.origin.authentication.filter.SignInAuthenticationFilter;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
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
public class BaseWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PermissionProperty permissionProperty;

    @Autowired
    private BaseSecurityProperty basicSecurityProperty;

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
                .addFilterAt(baseTokenAuthenticationFilter(), BasicAuthenticationFilter.class);
        http.csrf().disable();
//        http.exceptionHandling().defaultAuthenticationEntryPointFor()
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
        Map<String, String> resourcePermissions = new HashMap<>();
        for (Map.Entry<String, String> entry : resourcePermissions.entrySet()) {
            urlRegistry = urlRegistry.antMatchers(entry.getKey()).hasAnyAuthority(entry.getValue());
        }
        urlRegistry.antMatchers("/api/v1/resource").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().anyRequest().denyAll();

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
        corsConfiguration.setAllowedOrigins(Arrays.asList("chrome-extension://aejoelaoggembcahagimdiliamlcdmfm"));
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
    private BaseTokenFilter baseTokenAuthenticationFilter() throws Exception {
//        Set<AntPathRequestMatcher> permitAllMatchers = Arrays.stream(permissionProperty.getPermitAllList())
//                .map(AntPathRequestMatcher::new)
//                .collect(Collectors.toSet());
//        RequestMatcher requestMatcher = request -> permitAllMatchers.stream().noneMatch(matcher-> matcher.matches(request));
        BaseTokenFilter baseTokenFilter = new BaseTokenFilter(this.authenticationManagerBean());
        return baseTokenFilter;
    }


    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
