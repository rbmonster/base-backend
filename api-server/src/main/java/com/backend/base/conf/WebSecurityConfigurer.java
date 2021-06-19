package com.backend.base.conf;

import com.backend.base.security.DyFilter;
import com.backend.base.security.auth.RestAuthenticationEntryPoint;
import com.backend.base.security.auth.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Order(2)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;



//    @Autowired
//    private IPermissionService permissionService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
            .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint ).and()
            .authorizeRequests()
            .antMatchers("/api/v1/admin/login/**").permitAll()
            .antMatchers("/api/v1/front/wxAuthor/**").permitAll()
            .antMatchers("/api/v1/admin/wxAuthor/**").permitAll()
            .antMatchers("/api/v1/front/wxMessage/**").permitAll()
            .antMatchers("/upload/**").permitAll();


        // API权限控制
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
//        Map<String, String> resourcePermissions = permissionService.getResourcePermissions();
        Map<String, String> resourcePermissions = new HashMap<>();
        for (Map.Entry<String, String> entry : resourcePermissions.entrySet()) {
            urlRegistry = urlRegistry.antMatchers(entry.getKey()).hasAnyAuthority(entry.getValue());
        }

        http.authorizeRequests()
            .anyRequest().authenticated().and()
            .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class);

        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        // TokenAuthenticationFilter will ignore the below paths
        web.ignoring().antMatchers(
                HttpMethod.POST,
                "/api/v1/admin/login/toLogin"
        ).antMatchers(
                HttpMethod.POST,
                "/api/v1/front/wxAuthor/**"
        ).antMatchers(
                HttpMethod.POST,
                "/api/v1/admin/wxAuthor/**"
        ).antMatchers(
                HttpMethod.POST,
                "/api/v1/front/wxMessage/**"
        ).antMatchers(
                HttpMethod.GET,
                "/static/**"
        ).antMatchers(
                HttpMethod.GET,
                "/upload/**"
        );
    }

}
