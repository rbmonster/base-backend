package com.sanwu.origin.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: StandardAuthentation
 * @Author: sanwu
 * @Date: 2021/7/9 0:43
 */
public class BaseTokenAuthentication extends AbstractAuthenticationToken {

    private String token;
    private UserDetails principle;

    public BaseTokenAuthentication(String token) {
        super(null);
        this.token = token;
        this.setAuthenticated(false);
    }

    public BaseTokenAuthentication(String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.setAuthenticated(false);
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return principle;
    }
}
