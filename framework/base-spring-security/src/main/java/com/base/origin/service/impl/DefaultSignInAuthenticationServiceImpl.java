package com.base.origin.service.impl;

import com.base.origin.model.TokenAuthentication;
import com.base.origin.service.ISignInAuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

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
        authenticationToken.setAuthenticated(true);
        return authenticationToken;
    }
}
