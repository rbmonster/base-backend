package com.sanwu.origin.service.impl;

import com.sanwu.origin.model.BaseTokenAuthentication;
import com.sanwu.origin.model.PermissionGrantedAuthority;
import com.sanwu.origin.service.ITokenAuthenticationService;
import com.sanwu.origin.service.helper.JwtTokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;

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
    public Authentication authenticate(BaseTokenAuthentication baseTokenAuthentication) {
        String token = baseTokenAuthentication.getToken();
        String userInfoFromToken = jwtTokenHelper.getUserInfoFromToken(token);
        log.info("authenticate success userInfo:{}", userInfoFromToken);
        BaseTokenAuthentication authentication = new BaseTokenAuthentication(token, createRole());
        authentication.setAuthenticated(true);
        return authentication;
    }


    private List<? extends GrantedAuthority> createRole() {
        return Arrays.asList(new PermissionGrantedAuthority("ROLE_ADMIN"));
    }


}
