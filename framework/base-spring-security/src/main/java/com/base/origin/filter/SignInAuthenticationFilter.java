package com.base.origin.filter;

import com.alibaba.fastjson.JSON;
import com.base.origin.exception.BaseAuthenticationException;
import com.base.origin.model.SignInModel;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
public class SignInAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SignInAuthenticationFilter(RequestMatcher requestMatcher) {
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String body = StreamUtils.copyToString(request.getInputStream(),  StandardCharsets.UTF_8);
        SignInModel signInModel = JSON.parseObject(body, SignInModel.class);
        if (Objects.isNull(signInModel)) {
            throw new BaseAuthenticationException("unAuth missing the header");
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                signInModel.getUsername(), signInModel.getPassword());
         return this.getAuthenticationManager().authenticate(authRequest);
    }
}
