package com.sanwu.origin.authentication.token;

import com.sanwu.origin.model.BaseTokenAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: BaseTokenFilter
 * @Author: sanwu
 * @Date: 2021/7/18 15:18
 */
@Slf4j
public class BaseTokenFilter extends OncePerRequestFilter {

    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;
    private boolean ignoreFailure = false;
    private ITokenAuthenticationConverter authenticationConverter;

    public BaseTokenFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.ignoreFailure = true;
        this.authenticationConverter = new DefaultAuthenticationConverter(new DefaultAuthenticationDetailsSource());
    }

    public BaseTokenFilter(AuthenticationEntryPoint authenticationEntryPoint,
                           AuthenticationManager authenticationManager,
                           ITokenAuthenticationConverter authenticationConverter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationManager = authenticationManager;
        this.authenticationConverter = authenticationConverter;
    }

    /**
     * 使用converter 获取 token
     * 1. 为空继续执行chain的调用
     * 2. 判断是否需要认证（沿用BasicAuthenticationFilter）
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try{
            BaseTokenAuthentication authRequest = authenticationConverter.convert(request);
            if (Objects.isNull(authRequest)) {
                chain.doFilter(request, response);
                return;
            }
            String token = authRequest.getToken();
            log.debug("Bearer Authentication Authorization header found for token '" + token + "'");

            if (authenticationIsRequired(token)) {
                Authentication authResult = this.authenticationManager
                        .authenticate(authRequest);
                log.debug("Authentication success: " + authResult);
                SecurityContextHolder.getContext().setAuthentication(authResult);
                this.onSuccessfulAuthentication(request, response, authResult);
            }
        }
        catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            log.debug("Authentication request for failed!", failed);
            onUnsuccessfulAuthentication(request, response, failed);
            if (this.ignoreFailure) {
                chain.doFilter(request, response);
            }
            else {
                this.authenticationEntryPoint.commence(request, response, failed);
            }
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean authenticationIsRequired(String username) {
        // Only reauthenticate if username doesn't match SecurityContextHolder and user
        // isn't authenticated
        // (see SEC-53)
        Authentication existingAuth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }

        if (existingAuth instanceof BaseTokenAuthentication) {
            return true;
        }

        // Handle unusual condition where an AnonymousAuthenticationToken is already
        // present
        // This shouldn't happen very often, as BasicProcessingFitler is meant to be
        // earlier in the filter
        // chain than AnonymousAuthenticationFilter. Nevertheless, presence of both an
        // AnonymousAuthenticationToken
        // together with a BASIC authentication request header should indicate
        // reauthentication using the
        // BASIC protocol is desirable. This behaviour is also consistent with that
        // provided by form and digest,
        // both of which force re-authentication if the respective header is detected (and
        // in doing so replace
        // any existing AnonymousAuthenticationToken). See SEC-610.
        if (existingAuth instanceof AnonymousAuthenticationToken) {
            return true;
        }

        return false;
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, Authentication authResult) throws IOException {
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response, AuthenticationException failed) throws IOException {
    }

    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return this.authenticationEntryPoint;
    }

    protected AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    protected boolean isIgnoreFailure() {
        return this.ignoreFailure;
    }
}
