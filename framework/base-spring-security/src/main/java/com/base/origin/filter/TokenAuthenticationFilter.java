package com.base.origin.filter;

import com.base.origin.exception.BaseAuthenticationException;
import com.base.origin.model.TokenAuthentication;
import com.base.origin.model.PermissionGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: BaseTokenAuthenticationFilter
 * @Author: sanwu
 * @Date: 2021/7/12 23:14
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String headerName;

    public TokenAuthenticationFilter(RequestMatcher requestMatcher, String headerName){
        super(requestMatcher);
        this.headerName = headerName;
    }

    /**
     * TODO 授权
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(headerName);
        if (StringUtils.isEmpty(token)) {
            throw new BaseAuthenticationException("attemptAuthentication failed, missing the header");
        }
        List<PermissionGrantedAuthority> permissionGrantedAuthorityList = Arrays.asList(new PermissionGrantedAuthority("USER"));
        TokenAuthentication tokenAuthentication = new TokenAuthentication(token, permissionGrantedAuthorityList);
        return this.getAuthenticationManager().authenticate(tokenAuthentication);
    }

    /**
     * token认证成功后，要继续执行Filter，才能传递请求到controller中
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
