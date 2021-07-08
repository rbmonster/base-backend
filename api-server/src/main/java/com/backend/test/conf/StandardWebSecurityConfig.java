package com.backend.test.conf;

import com.backend.test.security.StandardAuthenticationEntryPoint;
import com.backend.test.security.StandardAuthenticationProvider;
import com.backend.test.security.TestHandler;
import com.backend.test.security.TokenStandardAuthenticationFilter;
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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;
import java.util.regex.Matcher;
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
    private StandardAuthenticationProvider standardAuthenticationProvider;

    @Autowired
    private StandardAuthenticationEntryPoint standardAuthenticationEntryPoint;
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(standardAuthenticationEntryPoint).and()
                .authorizeRequests().antMatchers(permissionProperty.getPermitAllList()).permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().and()
                .addFilterAt(tokenStandardAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
        Map<String, String> resourcePermissions = new HashMap<>();
        for (Map.Entry<String, String> entry : resourcePermissions.entrySet()) {
            urlRegistry = urlRegistry.antMatchers(entry.getKey()).hasAnyAuthority(entry.getValue());
        }
//        urlRegistry.antMatchers("/api/v1/resource").hasAnyAuthority()

//        http.cors().configurationSource(corsConfigurationSource())corsConfigurationSource
//                .and().headers().frameOptions().deny().contentTypeOptions()
//                .and().xssProtection()
//                .and().cacheControl()
//                .and().httpStrictTransportSecurity();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(standardAuthenticationProvider);
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

    private TokenStandardAuthenticationFilter tokenStandardAuthenticationFilter() throws Exception {
        List<AntPathRequestMatcher> matchers = Arrays.stream(permissionProperty.getPermitAllList())
                .map(path -> new AntPathRequestMatcher(path, null)).collect(Collectors.toList());
        RequestMatcher requestMatcher = request -> matchers.stream().noneMatch(matcher -> matcher.matches(request));
        TokenStandardAuthenticationFilter tokenStandardAuthenticationFilter = new TokenStandardAuthenticationFilter(requestMatcher, basicSecurityProperty.getHeader());
        tokenStandardAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        tokenStandardAuthenticationFilter.setAuthenticationSuccessHandler(new TestHandler());
        return tokenStandardAuthenticationFilter;
    }
}
