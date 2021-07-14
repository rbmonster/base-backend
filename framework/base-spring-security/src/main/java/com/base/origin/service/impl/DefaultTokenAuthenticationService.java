package com.base.origin.service.impl;

import com.base.origin.model.TokenAuthentication;
import com.base.origin.service.ITokenAuthenticationService;
import com.base.origin.service.helper.JwtTokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: DefaultTokenAuthenticationService
 * @Author: sanwu
 * @Date: 2021/7/15 0:55
 */
@Slf4j
public class DefaultTokenAuthenticationService implements ITokenAuthenticationService {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public String generateToken(String subject) {
        return jwtTokenHelper.generateToken(subject);
    }

    @Override
    public Authentication authenticate(TokenAuthentication tokenAuthentication) {
        String token = tokenAuthentication.getToken();
        String userInfoFromToken = jwtTokenHelper.getUserInfoFromToken(token);
        log.info("authenticate success userInfo:{}", userInfoFromToken);
        tokenAuthentication.setAuthenticated(true);
        return tokenAuthentication;
    }


}
