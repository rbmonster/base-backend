package com.backend.test.conf;

import com.backend.test.security.SignInAuthenticationProvider;
import com.backend.test.security.StandardAuthenticationEntryPoint;
import com.backend.test.security.JwtAuthenticationProvider;
import com.backend.test.security.SignInAuthenticationSuccessHandler;
import com.backend.test.filter.SignInAuthenticationFilter;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

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
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private SignInAuthenticationProvider signInAuthenticationProvider;

    @Autowired
    private StandardAuthenticationEntryPoint standardAuthenticationEntryPoint;

    @Autowired
    private SignInAuthenticationSuccessHandler signInAuthenticationSuccessHandler;

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
                .addFilterAt(signInAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
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
        auth.authenticationProvider(jwtAuthenticationProvider);
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

    private SignInAuthenticationFilter signInAuthenticationFilter() throws Exception {
//        List<AntPathRequestMatcher> matchers = Arrays.stream(permissionProperty.getPermitAllList())
//                .map(path -> new AntPathRequestMatcher(path, null)).collect(Collectors.toList());
//        RequestMatcher requestMatcher = request -> matchers.stream().noneMatch(matcher -> matcher.matches(request));

        SignInAuthenticationFilter tokenStandardAuthenticationFilter = new SignInAuthenticationFilter(new AntPathRequestMatcher("/login", "POST"), basicSecurityProperty.getHeader());
        tokenStandardAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        tokenStandardAuthenticationFilter.setAuthenticationSuccessHandler(signInAuthenticationSuccessHandler);
        return tokenStandardAuthenticationFilter;
    }
}
