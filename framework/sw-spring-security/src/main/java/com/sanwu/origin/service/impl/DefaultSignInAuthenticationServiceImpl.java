package com.sanwu.origin.service.impl;

import com.sanwu.origin.exception.BaseAuthenticationException;
import com.sanwu.origin.service.ISignInAuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Arrays;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: DefaultSignInAuthenticationServiceImpl
 * @Author: sanwu
 * @Date: 2021/7/15 0:42
 */
public class DefaultSignInAuthenticationServiceImpl implements ISignInAuthenticationService {

    @Override
    public Authentication authenticate(UsernamePasswordAuthenticationToken authenticationToken) {
        String username = String.valueOf(authenticationToken.getPrincipal());
        String password = String.valueOf(authenticationToken.getCredentials());
        if (!"123".equals(username)) {
            throw new BaseAuthenticationException("sdf");
        }
        // TODO  do login logic
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());
        return authenticationToken;
    }
}
