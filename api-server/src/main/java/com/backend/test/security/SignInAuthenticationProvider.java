package com.backend.test.security;

import com.backend.test.service.ISignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: SignInAuthenticationProvider
 * @Author: sanwu
 * @Date: 2021/7/12 23:41
 */
@Component
public class SignInAuthenticationProvider  implements AuthenticationProvider {

    @Autowired
    private ISignInService signInService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                () -> "authentication type error");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)authentication;
        String username = String.valueOf(usernamePasswordAuthenticationToken.getPrincipal());
        String password = String.valueOf(usernamePasswordAuthenticationToken.getCredentials());
        boolean result = signInService.signIn(username, password);
        usernamePasswordAuthenticationToken.setAuthenticated(result);
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
