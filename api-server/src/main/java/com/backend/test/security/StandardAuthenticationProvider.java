package com.backend.test.security;

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
public class StandardAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        StandardAuthentication standardAuthentication = (StandardAuthentication)authentication;
        String token = standardAuthentication.getToken();
        String userInfoFromToken = jwtTokenHelper.getUserInfoFromToken(token);
        // do some validation logic
        standardAuthentication.setAuthenticated(true);
        return standardAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(StandardAuthentication.class);
    }
}
