package com.backend.test.security;

import com.backend.test.exception.StandardAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: TokenAuthenticationFilter
 * @Author: sanwu
 * @Date: 2021/7/9 0:09
 */
public class TokenStandardAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String headerName;

    public TokenStandardAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, String headerName) {
        super(requiresAuthenticationRequestMatcher);
        this.headerName = headerName;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(headerName);
        if (StringUtils.isEmpty(token)) {
            throw new StandardAuthenticationException("unAuth missing the header");
        }
        List<PermissionGrantedAuthority> permissionGrantedAuthorityList = Arrays.asList(new PermissionGrantedAuthority("USER"));
        StandardAuthentication standardAuthentication = new StandardAuthentication(token, permissionGrantedAuthorityList);
        return this.getAuthenticationManager().authenticate(standardAuthentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
