package com.base.origin.provider;

import com.base.origin.service.ITokenAuthenticationService;
import com.base.origin.service.helper.JwtTokenHelper;
import com.base.origin.model.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: StandardAuthenticationProvider
 * @Author: sanwu
 * @Date: 2021/7/9 0:49
 */
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ITokenAuthenticationService tokenAuthenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication)authentication;
        return tokenAuthenticationService.authenticate(tokenAuthentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(TokenAuthentication.class);
    }
}
